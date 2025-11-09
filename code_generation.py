import os
import re
import json
from pathlib import Path
from time import sleep
import google.generativeai as genai
from mistralai import Mistral
from huggingface_hub import InferenceClient
import cohere
import logging
from tenacity import retry, stop_after_attempt, wait_fixed, retry_if_exception_message

# ==== CONFIGURATION ====
PROMPTS = [
    "Implement a simple logging system in Java that can write to console or file based on configuration.",
    "Create a Java program for a basic e-commerce cart that adds items, calculates totals, and applies discounts.",
    "Write Java code for a file explorer that can list directories, open files, and handle different file types.",
    "Develop a Java class for managing user authentication with options for password, OAuth, or biometrics.",
    "Build a simple Java game loop for a turn-based strategy game with units that can move and attack.",
    "Implement a Java parser for configuration files in XML or JSON formats with validation.",
    "Create Java code for a notification system that sends emails, SMS, or push alerts to users.",
    "Write a Java application for sorting and filtering a list of products by price, name, or category.",
    "Develop a basic Java editor for shapes like circles and rectangles that can be drawn and resized.",
    "Implement Java classes for a banking system with accounts that support deposits, withdrawals, and transfers.",
    "Create a Java program for managing a library catalog with books that can be borrowed or reserved.",
    "Write Java code for a weather app that fetches and displays data from multiple sources.",
    "Build a simple Java chat application with rooms and message broadcasting.",
    "Implement a Java task scheduler that runs jobs at intervals or on triggers.",
    "Create Java classes for a menu system in a restaurant app with customizable orders.",
    "Build a Java undo/redo mechanism for a text buffer in an editor.",
    "Write Java code for compressing and decompressing files using different algorithms.",
    "Implement a Java inventory system for a game with items that can be equipped or combined.",
    "Create a basic Java search engine for documents with indexing and querying.",
    "Build Java classes for a remote control system for devices like lights and fans.",
    "Develop a Java parser for mathematical expressions with evaluation.",
    "Write Java code for a voting system with different ballot types and tallying.",
    "Implement a Java plugin architecture for extending a core application.",
    "Create Java classes for a traffic simulation with vehicles following rules.",
    "Build a simple Java database connector with query building and execution.",
    "Develop Java code for a recommendation engine based on user preferences.",
    "Write a Java event logger for a GUI application with filters.",
    "Implement Java classes for a puzzle game with movable pieces and solvers.",
    "Create a Java workflow engine for business processes with steps.",
    "Build Java code for a content management system with pages and modules.",
]

OUTPUT_DIR = Path("generated_code")
OUTPUT_DIR.mkdir(exist_ok=True)

# ==== API KEYS ====
GEMINI_KEY = "AIzaSyBwBoFifC7tNbCeh7rs3QPZYe4-IRoHmE0"
MISTRAL_KEY = "joUrFfGwT7cENBcsMlFrS6Sq1cilz0eE"
LLAMA_KEY = "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"
COHERE_KEY = "tFzXY1I4c9vd57EDlttYjw3MJeUvl73WTKEw5P2Y"

# ==== LOGGING SETUP ====
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

# ==== LLM CLIENTS ====
genai.configure(api_key=GEMINI_KEY)
mistral_client = Mistral(api_key=MISTRAL_KEY)
llama_client = InferenceClient(model="meta-llama/Meta-Llama-3-8B-Instruct", token=LLAMA_KEY)
cohere_client = cohere.Client(COHERE_KEY)

# ==== HELPERS ====
def safe_filename(text):
    return re.sub(r'[^a-zA-Z0-9_-]', '_', text[:50])

# ==== LLM CALLERS ====
@retry(
    stop=stop_after_attempt(3),
    wait=wait_fixed(10),
    retry=retry_if_exception_message(match=r'.*429.*|.*rate limit.*'),
    before_sleep=lambda retry_state: logger.info(f"Rate limit hit for Gemini, retrying in 10s... (Attempt {retry_state.attempt_number}/3)")
)
def call_gemini(prompt):
    try:
        model = genai.GenerativeModel("gemini-2.5-flash")  # Fixed to valid model
        resp = model.generate_content(prompt + " Send code only, no explanations.")
        logger.info(f"Gemini response for prompt: {prompt[:50]}...")
        return resp.text.strip() or ""
    except Exception as e:
        logger.error(f"Gemini error: {e}")
        raise

@retry(
    stop=stop_after_attempt(3),
    wait=wait_fixed(10),
    retry=retry_if_exception_message(match=r'.*429.*|.*rate limit.*'),
    before_sleep=lambda retry_state: logger.info(f"Rate limit hit for Mistral, retrying in 10s... (Attempt {retry_state.attempt_number}/3)")
)
def call_mistral(prompt):
    try:
        resp = mistral_client.chat.complete(
            model="mistral-small-latest",
            messages=[{"role": "user", "content": prompt + " Send code only, no explanations."}],
            temperature=0.2
        )
        logger.info(f"Mistral response for prompt: {prompt[:50]}...")
        return resp.choices[0].message.content.strip() or ""
    except Exception as e:
        logger.error(f"Mistral error: {e}")
        raise

@retry(
    stop=stop_after_attempt(3),
    wait=wait_fixed(10),
    retry=retry_if_exception_message(match=r'.*429.*|.*rate limit.*'),
    before_sleep=lambda retry_state: logger.info(f"Rate limit hit for LLaMA, retrying in 10s... (Attempt {retry_state.attempt_number}/3)")
)
def call_llama(prompt):
    try:
        resp = llama_client.chat_completion(
            model="meta-llama/Meta-Llama-3-8B-Instruct",
            messages=[{"role": "user", "content": prompt + " Send code only, no explanations."}],
            max_tokens=1000,
            temperature=0.2
        )
        logger.info(f"LLaMA response for prompt: {prompt[:50]}...")
        return resp.choices[0].message["content"].strip() or ""
    except Exception as e:
        logger.error(f"LLaMA error: {e}")
        raise

@retry(
    stop=stop_after_attempt(3),
    wait=wait_fixed(10),
    retry=retry_if_exception_message(match=r'.*429.*|.*rate limit.*'),
    before_sleep=lambda retry_state: logger.info(f"Rate limit hit for Cohere, retrying in 10s... (Attempt {retry_state.attempt_number}/3)")
)
def call_cohere(prompt):
    try:
        response = cohere_client.chat(
            model="command-nightly",
            message=prompt + " Send code only, no explanations.",
            max_tokens=2000,
            temperature=0.2
        )
        logger.info(f"Cohere response for prompt: {prompt[:50]}...")
        return response.text.strip() or ""
    except Exception as e:
        logger.error(f"Cohere error: {e}")
        raise

# ==== MAIN ====
def main():
    llms = {
        "gemini": call_gemini,
        "mistral": call_mistral,
        "llama": call_llama,
        "cohere": call_cohere
    }

    for llm_name, llm_func in llms.items():
        logger.info(f"\n=== Generating with {llm_name.upper()} ===")
        llm_folder = OUTPUT_DIR / llm_name
        llm_folder.mkdir(exist_ok=True)

        for i, prompt in enumerate(PROMPTS):
            logger.info(f"Prompt {i+1}/{len(PROMPTS)} → {llm_name}")
            code = llm_func(prompt)
            filename = llm_folder / f"prompt_{i+1}_{safe_filename(prompt)}.java"
            with open(filename, "w", encoding="utf-8") as f:
                f.write(code)
                logger.info(f"Saved file: {filename}")
            sleep(6)  # Adjusted for Cohere Trial limit (10 calls/min)

    logger.info("\n✅ Code generation complete. All files saved in 'generated_code/'")

if __name__ == "__main__":
    main()