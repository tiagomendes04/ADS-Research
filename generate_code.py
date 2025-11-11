"""
FINAL API SCRIPT: Bulletproof streaming + fallback
- No IndexError
- No NoneType.strip()
- Clean logs
- Full .java files
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
    #"llama_3_2_1B_Instruct": "meta-llama/Llama-3.2-1B-Instruct",
    #"llama_3_1_8B_Instruct": "meta-llama/Llama-3.1-8B-Instruct",
    #"mistral_7B_Instruct_v0_2": "mistralai/Mistral-7B-Instruct-v0.2",
    #"qwen_3_8B": "Qwen/Qwen3-8B",
    "qwen2_5_coder_32b": "Qwen/Qwen2-5-Coder-32B",
}

PROMPT_INDEX_START = 1  # ← Your test

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
            delta = chunk.choices[0].delta if chunk.choices else None
            if delta and delta.content:
                full += delta.content
        return full.strip() if full else None
    except Exception as e:
        print(f" [stream failed: {type(e).__name__}]", end="")
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
# GENERATE (Streaming → Fallback → Safe)
# ------------------------------------------------------------
def generate_response(model_id: str, prompt: str) -> str:
    print(f"  → Asking {model_id}...", end="", flush=True)
    messages = [{"role": "user", "content": prompt}]

    # Try streaming
    result = safe_stream(model_id, messages)
    if result is not None:
        print(" Done (stream).")
        return result

    # Fallback
    result = non_stream(model_id, messages)
    print(" Done (fallback).")
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

        # Auto-detect
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

            path = dir_ / f"prompt_{i}.java"
            path.write_text(code, encoding="utf-8")
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