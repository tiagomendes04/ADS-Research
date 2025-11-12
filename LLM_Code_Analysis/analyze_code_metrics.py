"""
Analyze code metrics for LLM-generated Java files.
Calculates lines of code statistics per model.
"""

import numpy as np
import pandas as pd
from pathlib import Path
import csv


def count_lines_of_code(file_path):
    """
    Count lines of code in a Java file.
    Excludes empty lines and comment-only lines.
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        loc = 0
        in_block_comment = False
        
        for line in lines:
            stripped = line.strip()
            
            # Skip empty lines
            if not stripped:
                continue
            
            # Handle block comments
            if '/*' in stripped:
                in_block_comment = True
            if '*/' in stripped:
                in_block_comment = False
                continue
            if in_block_comment:
                continue
            
            # Skip single-line comments
            if stripped.startswith('//'):
                continue
            
            # Count as line of code
            loc += 1
        
        return loc
    except Exception as e:
        print(f"Error reading {file_path}: {e}")
        return 0


def analyze_model_metrics(model_dir):
    """
    Analyze LOC metrics for all Java files in a model directory.
    Returns dict with statistics.
    """
    java_files = list(model_dir.glob('*.java'))
    
    if not java_files:
        return None
    
    loc_counts = [count_lines_of_code(f) for f in java_files]
    loc_counts = [loc for loc in loc_counts if loc > 0]  # Filter out failed reads
    
    if not loc_counts:
        return None
    
    metrics = {
        'model': model_dir.name,
        'total_files': len(java_files),
        'valid_files': len(loc_counts),
        'min': int(np.min(loc_counts)),
        'q25': int(np.percentile(loc_counts, 25)),
        'median': int(np.percentile(loc_counts, 50)),
        'q75': int(np.percentile(loc_counts, 75)),
        'max': int(np.max(loc_counts)),
        'mean': round(np.mean(loc_counts), 2),
        'std': round(np.std(loc_counts), 2),
        'total_loc': sum(loc_counts)
    }
    
    return metrics


def print_metrics_table(all_metrics):
    """
    Print a nicely formatted table of metrics.
    """
    print("=" * 120)
    print("LINES OF CODE ANALYSIS - LLM GENERATED JAVA FILES")
    print("=" * 120)
    print()
    
    # Header
    print(f"{'Model':<30} | {'Files':<6} | {'Min':<5} | {'Q25':<5} | {'Median':<6} | {'Q75':<5} | {'Max':<5} | {'Mean':<7} | {'Std':<7} | {'Total':<7}")
    print("-" * 120)
    
    # Sort by median LOC
    sorted_metrics = sorted(all_metrics, key=lambda x: x['median'], reverse=True)
    
    for m in sorted_metrics:
        print(f"{m['model']:<30} | {m['valid_files']:<6} | {m['min']:<5} | {m['q25']:<5} | "
              f"{m['median']:<6} | {m['q75']:<5} | {m['max']:<5} | {m['mean']:<7.2f} | "
              f"{m['std']:<7.2f} | {m['total_loc']:<7}")
    
    print()
    
    # Summary statistics
    print("=" * 120)
    print("SUMMARY STATISTICS")
    print("=" * 120)
    print()
    
    total_files = sum(m['valid_files'] for m in all_metrics)
    total_loc = sum(m['total_loc'] for m in all_metrics)
    avg_loc_per_file = total_loc / total_files if total_files > 0 else 0
    
    print(f"Total models analyzed: {len(all_metrics)}")
    print(f"Total files analyzed: {total_files}")
    print(f"Total lines of code: {total_loc:,}")
    print(f"Average LOC per file (all models): {avg_loc_per_file:.2f}")
    print()
    
    # Model with most/least code
    most_code = max(all_metrics, key=lambda x: x['median'])
    least_code = min(all_metrics, key=lambda x: x['median'])
    
    print(f"Model with most code (median): {most_code['model']} ({most_code['median']} lines)")
    print(f"Model with least code (median): {least_code['model']} ({least_code['median']} lines)")
    print()


def save_to_csv(all_metrics, output_file):
    """
    Save metrics to CSV file.
    """
    if not all_metrics:
        print("No metrics to save.")
        return
    
    with open(output_file, 'w', newline='', encoding='utf-8') as f:
        fieldnames = ['model', 'total_files', 'valid_files', 'min', 'q25', 'median', 
                      'q75', 'max', 'mean', 'std', 'total_loc']
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        
        writer.writeheader()
        for metrics in sorted(all_metrics, key=lambda x: x['median'], reverse=True):
            writer.writerow(metrics)
    
    print(f"Metrics saved to: {output_file}")


def generate_detailed_report(all_metrics, output_file):
    """
    Generate a detailed markdown report.
    """
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("# LLM Code Metrics Analysis - Lines of Code\n\n")
        f.write("## Overview\n\n")
        
        total_files = sum(m['valid_files'] for m in all_metrics)
        total_loc = sum(m['total_loc'] for m in all_metrics)
        
        f.write(f"- **Total Models**: {len(all_metrics)}\n")
        f.write(f"- **Total Files**: {total_files}\n")
        f.write(f"- **Total Lines of Code**: {total_loc:,}\n")
        f.write(f"- **Average LOC per File**: {total_loc/total_files:.2f}\n\n")
        
        f.write("## Lines of Code Statistics by Model\n\n")
        f.write("| Model | Files | Min | Q25 | Median | Q75 | Max | Mean | Std Dev | Total LOC |\n")
        f.write("|-------|-------|-----|-----|--------|-----|-----|------|---------|----------|\n")
        
        for m in sorted(all_metrics, key=lambda x: x['median'], reverse=True):
            f.write(f"| {m['model']} | {m['valid_files']} | {m['min']} | {m['q25']} | "
                   f"{m['median']} | {m['q75']} | {m['max']} | {m['mean']:.2f} | "
                   f"{m['std']:.2f} | {m['total_loc']:,} |\n")
        
        f.write("\n## Key Insights\n\n")
        
        # Find extremes
        most_code = max(all_metrics, key=lambda x: x['median'])
        least_code = min(all_metrics, key=lambda x: x['median'])
        most_consistent = min(all_metrics, key=lambda x: x['std'])
        least_consistent = max(all_metrics, key=lambda x: x['std'])
        
        f.write(f"### Code Length\n\n")
        f.write(f"- **Longest median LOC**: {most_code['model']} with {most_code['median']} lines\n")
        f.write(f"- **Shortest median LOC**: {least_code['model']} with {least_code['median']} lines\n")
        f.write(f"- **Difference**: {most_code['median'] - least_code['median']} lines\n\n")
        
        f.write(f"### Consistency\n\n")
        f.write(f"- **Most consistent**: {most_consistent['model']} (std dev: {most_consistent['std']:.2f})\n")
        f.write(f"- **Least consistent**: {least_consistent['model']} (std dev: {least_consistent['std']:.2f})\n\n")
        
        f.write("### Distribution Analysis\n\n")
        
        all_medians = [m['median'] for m in all_metrics]
        f.write(f"- **Average median LOC across models**: {np.mean(all_medians):.2f}\n")
        f.write(f"- **Range of medians**: {min(all_medians)} - {max(all_medians)} lines\n\n")
    
    print(f"Detailed report saved to: {output_file}")


def main():
    # Paths
    script_dir = Path(__file__).parent
    generated_code_dir = script_dir.parent / "generated_code"
    csv_output = script_dir / "code_metrics_loc.csv"
    report_output = script_dir / "Code_Metrics_Report.md"
    
    print("Analyzing lines of code for LLM-generated Java files...\n")
    
    if not generated_code_dir.exists():
        print(f"Error: {generated_code_dir} does not exist!")
        return
    
    # Analyze each model
    all_metrics = []
    model_dirs = [d for d in generated_code_dir.iterdir() if d.is_dir()]
    
    if not model_dirs:
        print("No model directories found!")
        return
    
    for model_dir in sorted(model_dirs):
        print(f"Analyzing {model_dir.name}...", end=" ")
        metrics = analyze_model_metrics(model_dir)
        
        if metrics:
            all_metrics.append(metrics)
            print(f"✓ ({metrics['valid_files']} files, median: {metrics['median']} LOC)")
        else:
            print("✗ (no valid files)")
    
    print()
    
    if not all_metrics:
        print("No metrics collected!")
        return
    
    # Display results
    print_metrics_table(all_metrics)
    
    # Save results
    save_to_csv(all_metrics, csv_output)
    generate_detailed_report(all_metrics, report_output)
    
    print("\n" + "=" * 120)
    print("Analysis complete!")
    print("=" * 120)


if __name__ == "__main__":
    main()
