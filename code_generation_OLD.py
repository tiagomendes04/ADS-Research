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
    "Develop a Java application for managing a personal finance tracker with budgets, expenses, and reports.",
    "Create a Java program for a quiz system with multiple question types and scoring.",
    "Write Java code for a social media feed that supports posts, likes, and comments.",
    "Implement a Java email client with inbox management, sending, and attachments.",
    "Build a Java library for managing events and subscriptions for a pub/sub system.",
    "Create Java classes for a vehicle rental system with bookings and availability management.",
    "Write a Java application for a hotel booking system with room allocation and cancellations.",
    "Develop Java code for a music player with playlists, shuffle, and repeat functionality.",
    "Implement a Java recommendation system for movies based on ratings and genres.",
    "Create Java classes for a restaurant reservation system with table management.",
    "Build a Java program for an online polling system with multiple-choice and ranking questions.",
    "Write Java code for a basic inventory tracking system with stock updates and alerts.",
    "Develop a Java scheduler for recurring tasks with configurable time intervals.",
    "Implement a Java parser for CSV files with validation and error reporting.",
    "Create Java classes for a messaging app with group chats and message notifications.",
    "Build a Java application for a library with book lending, return reminders, and reservations.",
    "Write Java code for a banking ATM simulation with deposits, withdrawals, and PIN validation.",
    "Develop a Java game engine for a board game with pieces, moves, and scoring.",
    "Implement a Java system for processing customer support tickets with priorities and status tracking.",
    "Create a Java program for a ride-sharing application with driver and rider management.",
    "Build Java code for a simple email newsletter system with subscriptions and sending schedules.",
    "Write a Java application for a calorie tracker with meals, exercises, and reports.",
    "Develop Java classes for a photo gallery app with albums, tags, and search.",
    "Implement a Java plugin system for a text editor with extensible features.",
    "Create Java code for a multiplayer card game with turn management and scorekeeping.",
    "Build a Java workflow system for order processing with different states and transitions.",
    "Write a Java program for an online store with shopping cart, checkout, and payment simulation.",
    "Develop Java classes for a simple IoT home automation system with device control.",
    "Implement a Java library for unit conversions between different measurement types.",
    "Create a Java application for a code snippet manager with tags and search.",
    "Build Java code for a weather notification system with thresholds and alerts.",
    "Write Java classes for a movie ticket booking system with seating selection.",
    "Develop a Java program for a fitness tracker with activity logging and analytics.",
    "Implement a Java system for online exam proctoring with monitoring and reporting.",
    "Create Java code for a text-based adventure game with rooms, items, and puzzles.",
    "Build a Java program for a budgeting app that categorizes transactions and sets limits.",
    "Write Java classes for a travel itinerary planner with destinations, dates, and activities.",
    "Develop a Java library for geometric shape calculations with area and perimeter functions.",
    "Implement a Java program for a recipe manager with ingredients, steps, and categories.",
    "Create Java classes for a basic forum system with threads, posts, and user management.",
    "Build a Java application for a news aggregator with sources, filtering, and notifications.",
    "Write Java code for a task management app with deadlines, priorities, and subtasks.",
    "Develop Java classes for a quiz game with timers, scoring, and question randomization.",
    "Implement a Java file backup system with versioning and restore capabilities.",
    "Create a Java program for a virtual library with e-books, lending, and searches.",
    "Build Java code for a workflow approval system with reviewers and notifications.",
    "Write a Java system for a simple chat bot with responses and conversation history.",
    "Develop Java classes for a smart thermostat simulation with temperature control.",
    "Implement a Java library for matrix operations with addition, multiplication, and inversion.",
    "Create Java code for a scheduling assistant with meetings, reminders, and conflict detection.",
    "Build a Java application for a virtual whiteboard with shapes, drawings, and annotations.",
    "Write Java classes for a simple auction system with bidding, timers, and winner selection.",
    "Develop a Java program for managing employee records with roles, salaries, and departments.",
    "Implement Java code for a basic voting app with multiple ballots and tallying.",
    "Create a Java system for library overdue notifications with emails and reminders.",
    "Build Java classes for a parking lot simulation with slots, cars, and payment tracking.",
    "Write a Java program for a digital notebook with notes, tags, and search functionality.",
    "Develop a Java application for a simple CRM system with contacts, tasks, and notes.",
    "Implement Java code for a calendar app with events, reminders, and recurring schedules.",
    "Create Java classes for a ticket booking system for concerts or events.",
    "Build a Java program for a plant watering scheduler with notifications and history.",
    "Write Java code for a simple inventory system for a warehouse with stock levels.",
    "Develop a Java program for a simple text adventure engine with rooms, items, and commands.",
    "Implement a Java system for a basic recipe search engine with filtering and ratings.",
    "Create Java classes for a classroom management system with students, courses, and grades.",
    "Build a Java application for a habit tracker with streaks, goals, and notifications.",
    "Write Java code for a movie recommendation system with user ratings and genre filters.",
    "Develop Java classes for a simple fitness app with workouts, routines, and progress tracking.",
    "Implement a Java system for monitoring server logs and generating alerts.",
    "Create a Java program for a library digital catalog with search and reservation features.",
    "Build Java code for a hotel check-in system with rooms, guests, and billing.",
    "Write Java classes for a multiplayer trivia game with rounds, questions, and scoring.",
    "Develop a Java application for a student assignment submission system with deadlines.",
    "Implement a Java workflow system for handling document approvals with reviewers and status.",
    "Create a Java program for a basic stock trading simulator with buy, sell, and portfolio tracking.",
    "Build Java code for a simple email spam filter using keyword matching and scoring.",
    "Write Java classes for a virtual pet simulation with feeding, playing, and health tracking."
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