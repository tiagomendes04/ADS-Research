import os
import subprocess
from pathlib import Path

# Root directory containing generated_projects/<AI>/<project>/
root = Path(__file__).resolve().parent / "generated_projects"

print(f"🔍 Searching for AI project folders under: {root}")

if not root.exists():
    print("❌ Folder 'generated_projects' not found.")
    exit(1)

# Iterate over AI folders
for ai_folder in root.iterdir():
    if not ai_folder.is_dir():
        continue
    print(f"\n🧠 Processing AI: {ai_folder.name}")

    # Iterate over each project inside the AI folder
    for project in ai_folder.iterdir():
        if not project.is_dir():
            continue

        src_path = project / "src"
        bin_path = project / "bin"

        if not src_path.exists():
            print(f"    ⚠️ No src folder found in {project.name}, skipping.")
            continue

        java_files = list(src_path.rglob("*.java"))
        if not java_files:
            print(f"    ⚠️ No .java files found in {src_path}, skipping.")
            continue

        # Create bin directory if it doesn't exist
        bin_path.mkdir(parents=True, exist_ok=True)

        print(f"    📦 Compiling project: {project.name}")
        javac_cmd = ["javac", "-d", str(bin_path)] + [str(f) for f in java_files]

        try:
            subprocess.run(javac_cmd, check=True)
            print(f"    ✅ Compilation succeeded for {project.name}")
        except subprocess.CalledProcessError:
            print(f"    ❌ Compilation failed for {project.name}")

print("\n✅ Compilation complete for all projects.")
