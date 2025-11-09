import os
import re
from pathlib import Path

# Input and output directories
GENERATED_ROOT = Path("generated_code")
PROJECTS_ROOT = Path("generated_projects")

# Ensure output folder exists
PROJECTS_ROOT.mkdir(exist_ok=True)

# Regex to find ```java ... ```
FENCE_PATTERN = re.compile(r"```java(.*?)```", re.DOTALL | re.MULTILINE)
CLASS_PATTERN = re.compile(r"public\s+(class|enum|interface)\s+(\w+)")

for ai_folder in GENERATED_ROOT.iterdir():
    if not ai_folder.is_dir():
        continue

    print(f"Processing AI folder: {ai_folder.name}")
    ai_project_root = PROJECTS_ROOT / ai_folder.name
    ai_project_root.mkdir(exist_ok=True)

    for file in ai_folder.iterdir():
        if not file.is_file() or not file.suffix in (".java", ".txt"):
            continue

        project_name = file.stem
        project_folder = ai_project_root / project_name
        src_folder = project_folder / "src"
        bin_folder = project_folder / "bin"
        src_folder.mkdir(parents=True, exist_ok=True)
        bin_folder.mkdir(exist_ok=True)

        print(f"  Creating project: {project_folder}")

        content = file.read_text(encoding="utf-8", errors="ignore")

        matches = FENCE_PATTERN.findall(content)
        if not matches:
            print(f"    No ```java blocks found in {file.name}, skipping.")
            continue

        for i, block in enumerate(matches, start=1):
            code = block.strip()

            # Try to extract class/enum/interface name
            match = CLASS_PATTERN.search(code)
            if match:
                file_name = f"{match.group(2)}.java"
            else:
                file_name = f"File{i}.java"

            file_path = src_folder / file_name
            with open(file_path, "w", encoding="utf-8") as f:
                f.write(code)

            print(f"    Created {file_path}")

print(f"\n✅ All AI projects generated under '{PROJECTS_ROOT.resolve()}'")
