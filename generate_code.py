"""
generate_code_all_models.py
Runs all selected Hugging Face models locally to generate Java code for prompts.
Each model output is stored in generated_code/<model_name>/prompt_<n>.java
pip install torch transformers accelerate bitsandbytes safetensors
"""

from transformers import AutoTokenizer, AutoModelForCausalLM
import torch
from pathlib import Path
import json
import textwrap

# ------------------------------------------------------------
# 🔧 CONFIGURATION
# ------------------------------------------------------------
# You will paste your 100 PROMPTS in this array later.
PROMPTS = [
    # "Implement a simple logging system in Java that can write to console or file...",
    # "Create a Java program for a basic e-commerce cart that adds items...",
    # ...
]

# Folder for saving generated files
OUTPUT_ROOT = Path("generated_code")
OUTPUT_ROOT.mkdir(exist_ok=True)

# Models to use (Hugging Face IDs)
MODELS = {
    "openai_gpt2_large": "openai-community/gpt2-large",
    "openai_gpt_oss_20B": "openai-community/gpt-oss-20b",
    "llama_3.1_8B_Instruct": "meta-llama/Llama-3.1-8B-Instruct",
    "llama_3.2_1B_Instruct": "meta-llama/Llama-3.2-1B-Instruct",
    "mistral_7B_Instruct_v0.2": "mistralai/Mistral-7B-Instruct-v0.2",
    "dolphin_Yi_1.5_34B": "cognitivecomputations/dolphin-2.9.1-yi-1.5-34b",
    "qwen_2.5_7B_Instruct": "Qwen/Qwen2.5-7B-Instruct",
    "qwen_3_8B": "Qwen/Qwen3-8B",
    "qwen_3_32B": "Qwen/Qwen3-32B",
}

# ------------------------------------------------------------
# ⚙️ Helper Functions
# ------------------------------------------------------------
def load_model(model_id: str):
    """Loads a model and tokenizer (quantized if possible)."""
    print(f"\n🚀 Loading model: {model_id}")
    try:
        model = AutoModelForCausalLM.from_pretrained(
            model_id,
            device_map="auto",
            torch_dtype=torch.float16,
            load_in_8bit=True,
            cache_dir=str(Path.home() / ".cache" / "huggingface"),
        )
    except Exception as e:
        print(f"⚠️ 8-bit load failed for {model_id}, retrying in 4-bit or full precision: {e}")
        try:
            model = AutoModelForCausalLM.from_pretrained(
                model_id,
                device_map="auto",
                torch_dtype=torch.float16,
                load_in_4bit=True,
                cache_dir=str(Path.home() / ".cache" / "huggingface"),
            )
        except Exception as e2:
            print(f"⚠️ 4-bit load also failed for {model_id}, loading in FP16: {e2}")
            model = AutoModelForCausalLM.from_pretrained(
                model_id,
                device_map="auto",
                torch_dtype=torch.float16,
                cache_dir=str(Path.home() / ".cache" / "huggingface"),
            )

    tokenizer = AutoTokenizer.from_pretrained(
        model_id, cache_dir=str(Path.home() / ".cache" / "huggingface")
    )

    # Ensure we have a pad token
    if tokenizer.pad_token is None:
        tokenizer.pad_token = tokenizer.eos_token

    return model, tokenizer


def generate_response(model, tokenizer, prompt: str, max_new_tokens=400):
    """Generate response text for a given prompt."""
    inputs = tokenizer(prompt, return_tensors="pt").to(model.device)
    outputs = model.generate(
        **inputs,
        max_new_tokens=max_new_tokens,
        do_sample=True,
        temperature=0.7,
        top_p=0.95,
        pad_token_id=tokenizer.eos_token_id,
    )
    return tokenizer.decode(outputs[0], skip_special_tokens=True)


# ------------------------------------------------------------
# 🚀 Main Execution
# ------------------------------------------------------------
def main():
    if not PROMPTS:
        print("⚠️ No prompts defined yet. Please paste your prompt list inside PROMPTS[] in this script.")
        return

    summary = {}

    for model_name, model_id in MODELS.items():
        model_dir = OUTPUT_ROOT / model_name
        model_dir.mkdir(exist_ok=True, parents=True)

        model, tokenizer = load_model(model_id)
        print(f"✅ Loaded {model_name}")

        model_summary = []
        for i, prompt in enumerate(PROMPTS, start=1):
            print(f"🧠 [{model_name}] Generating for prompt {i}/{len(PROMPTS)} ...")

            try:
                response = generate_response(model, tokenizer, prompt)
            except Exception as e:
                response = f"⚠️ Generation failed: {e}"

            # Save output
            filename = model_dir / f"prompt_{i}.java"
            with open(filename, "w", encoding="utf-8") as f:
                f.write(f"// Prompt: {prompt}\n\n{response}")

            model_summary.append(filename.name)

        summary[model_name] = model_summary

    # Save a summary JSON
    summary_file = OUTPUT_ROOT / "generation_summary.json"
    with open(summary_file, "w", encoding="utf-8") as f:
        json.dump(summary, f, indent=2)

    print("\n✅ Generation completed for all models.")
    print(f"Results saved in: {OUTPUT_ROOT.resolve()}")


if __name__ == "__main__":
    main()
