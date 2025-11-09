from pathlib import Path
from collections import defaultdict, Counter
import json

# Path to the raw DPF output
input_file = Path("DesignPatternFinder Results.txt")
output_file = Path("dpf_summary.json")

# Dictionary for each AI: { AI_name: {pattern_name: count, ...}, ... }
ai_patterns = defaultdict(lambda: defaultdict(int))

# Read the file and populate ai_patterns
with input_file.open("r", encoding="utf-8") as f:
    for line in f:
        line = line.strip()
        if line.startswith("C:\\"):  # line with file path
            parts = line.split("\\")
            try:
                ai_name = parts[parts.index("generated_projects") + 1]
            except (ValueError, IndexError):
                continue
        elif line.startswith("Possible patterns:"):
            patterns_str = line.replace("Possible patterns:", "").strip()
            if patterns_str:
                patterns = [p.strip() for p in patterns_str.split(",")]
                for pattern in patterns:
                    ai_patterns[ai_name][pattern] += 1

# Compute total counts per pattern across all AIs
total_counts = Counter()
for counts in ai_patterns.values():
    total_counts.update(counts)

# Create an ordered list of patterns by total count (descending)
ordered_patterns = sorted(total_counts.items(), key=lambda x: x[1], reverse=True)

# Prepare final structure for JSON
output_data = {
    "ai_patterns": {ai: dict(counts) for ai, counts in ai_patterns.items()},
    "ordered_patterns": [{"pattern": p, "count": c} for p, c in ordered_patterns]
}

# Save to JSON
with output_file.open("w", encoding="utf-8") as f:
    json.dump(output_data, f, indent=4)

print(f"✅ JSON summary written to {output_file}")
