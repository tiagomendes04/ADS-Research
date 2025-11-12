"""
LLM Code Generation Script - Robust Multi-Model Support

FEATURES:
- Bulletproof streaming with retry mechanism
- Automatic fallback to non-streaming on failure
- Model auto-detection (chat vs text-generation)
- Configurable max_tokens (default: 3072)
- Basic Java code validation
- Comprehensive error handling
- Progress logging with character counts

IMPROVEMENTS:
- Fixed Qwen 2.5 Coder model name (now: Qwen2.5-Coder-32B-Instruct)
- Added CHAT_MODELS set for explicit model type specification
- Enhanced streaming to handle different response formats
- Added retry logic with delays for rate limiting
- Content length validation (warns if < 50 chars)
- Better error messages with truncated details
"""
from openai import OpenAI
from pathlib import Path
import json
from prompts import prompts as PROMPTS
import time

# ------------------------------------------------------------
# CONFIG
# ------------------------------------------------------------
OUTPUT_ROOT = Path("generated_code")
OUTPUT_ROOT.mkdir(exist_ok=True)
HF_TOKEN = ""

MODELS = {
    #"gemma_2_2b_it": "google/gemma-2-2b-it",
    "gpt_oss_120b": "openai/gpt-oss-120b",
    #"llama_3_2_1B_Instruct": "meta-llama/Llama-3.2-1B-Instruct",
    #"llama_3_1_8B_Instruct": "meta-llama/Llama-3.1-8B-Instruct",
    #"qwen2_5_7b_instruct": "Qwen/Qwen2.5-7B-Instruct",
    #"qwen2_5_coder_32b_instruct": "Qwen/Qwen2.5-Coder-32B-Instruct",
}

# Specify which models are chat-based (most instruct models are)
# If not specified, will try auto-detection
CHAT_MODELS = {
    "meta-llama/Llama-3.2-1B-Instruct",
    "meta-llama/Llama-3.1-8B-Instruct", 
    "mistralai/Mistral-7B-Instruct-v0.2",
    "Qwen/Qwen2.5-7B-Instruct",
    "Qwen/Qwen2.5-Coder-32B-Instruct",
    "google/gemma-2-2b-it",
}

PROMPT_INDEX_START = 97  # ← Your test

client = OpenAI(base_url="https://router.huggingface.co/v1", api_key=HF_TOKEN)

# ------------------------------------------------------------
# SAFE STREAMING (No IndexError)
# ------------------------------------------------------------
MAX_TOKENS = 3072

def safe_stream(model_id: str, messages: list) -> str:
    try:
        full = ""
        stream = client.chat.completions.create(
            model=model_id,
            messages=messages,
            max_tokens=MAX_TOKENS,
            temperature=0.7,
            top_p=0.95,
            stream=True,
        )
        for chunk in stream:
            # Handle different response formats
            if hasattr(chunk, 'choices') and chunk.choices:
                delta = chunk.choices[0].delta
                if hasattr(delta, 'content') and delta.content:
                    full += delta.content
            # Some models return content directly
            elif hasattr(chunk, 'content') and chunk.content:
                full += chunk.content
        
        return full.strip() if full else None
    except Exception as e:
        print(f" [stream failed: {type(e).__name__}: {str(e)[:50]}]", end="")
        return None

# ------------------------------------------------------------
# NON-STREAMING FALLBACK
# ------------------------------------------------------------
def non_stream(model_id: str, messages: list) -> str:
    try:
        response = client.chat.completions.create(
            model=model_id,
            messages=messages,
            max_tokens=MAX_TOKENS,
            temperature=0.7,
            top_p=0.95,
            stream=False,
        )
        return response.choices[0].message.content.strip()
    except Exception as e:
        print(f"\n    Fallback Error: {e}")
        return f"API Error: {e}"

# ------------------------------------------------------------
# GENERATE (Streaming → Fallback → Safe) with Retry
# ------------------------------------------------------------
def generate_response(model_id: str, prompt: str, max_retries: int = 3) -> str:
    print(f"  → Asking {model_id.split('/')[-1]}...", end="", flush=True)
    messages = [{"role": "user", "content": prompt}]

    for attempt in range(max_retries):
        # Try streaming
        result = safe_stream(model_id, messages)
        if result is not None and len(result) > 50:  # Ensure we got substantial content
            print(f" Done (stream, {len(result)} chars).")
            return result
        
        # If streaming failed or returned too little, try fallback
        if attempt < max_retries - 1:
            print(f" [retry {attempt+1}]...", end="", flush=True)
            time.sleep(3)  # Wait before retry
    
    # Final fallback attempt
    result = non_stream(model_id, messages)
    if result:
        print(f" Done (fallback, {len(result)} chars).")
    else:
        print(" Failed.")
    return result

# ------------------------------------------------------------
# TEXT-GENERATION (gpt2, etc.)
# ------------------------------------------------------------
def generate_text(model_id: str, prompt: str) -> str:
    print(f"  → Asking {model_id} (text)...", end="", flush=True)
    try:
        response = client.completions.create(
            model=model_id,
            prompt=prompt,
            max_tokens=MAX_TOKENS,
            temperature=0.7,
            top_p=0.95,
        )
        print(" Done.")
        return response.choices[0].text.strip()
    except Exception as e:
        print(f"\n    Error: {e}")
        return f"API Error: {e}"

# ------------------------------------------------------------
# MAIN
# ------------------------------------------------------------
def main():
    if not PROMPTS:
        print("No prompts.py")
        return

    total = len(PROMPTS)
    summary = {}

    print(f"Running {len(MODELS)} models on {total} prompts...\n")

    for name, model_id in MODELS.items():
        print("="*70)
        print(f" MODEL: {name.upper()} → {model_id}")
        print("="*70)

        # Check if model is chat-based
        if model_id in CHAT_MODELS:
            is_chat = True
        else:
            # Try auto-detection as fallback
            try:
                client.chat.completions.create(model=model_id, messages=[{"role": "user", "content": "Hi"}], max_tokens=1, stream=False)
                is_chat = True
            except:
                is_chat = False

        print(f"  Task: {'chat' if is_chat else 'text-generation'}")

        dir_ = OUTPUT_ROOT / name
        dir_.mkdir(parents=True, exist_ok=True)
        files = []

        for i, prompt in enumerate(PROMPTS, start=1):
            if i < PROMPT_INDEX_START:
                continue

            print(f"  Prompt {i}/{total}...", end="")
            code = generate_response(model_id, prompt) if is_chat else generate_text(model_id, prompt)

            # Basic validation - check if it looks like Java code
            if not code or len(code) < 50:
                print(f" WARNING: Generated code too short ({len(code) if code else 0} chars)")
            elif "class" not in code.lower() and "public" not in code.lower():
                print(f" WARNING: Generated code may not be valid Java")

            path = dir_ / f"prompt_{i}.java"
            path.write_text(code if code else "// Generation failed", encoding="utf-8")
            files.append(path.name)

            time.sleep(2.0)

        summary[name] = files
        print(f"Completed: {name}\n")

    sfile = OUTPUT_ROOT / "summary.json"
    sfile.write_text(json.dumps(summary, indent=2), encoding="utf-8")

    print("\nALL DONE!")
    print(f"Results: {OUTPUT_ROOT.resolve()}")

if __name__ == "__main__":
    main()