"""
Script to analyze design patterns detected in LLM-generated code.
Extracts relevant metrics for studying which design patterns are most
commonly introduced by different LLM models.
"""

import os
import re
from pathlib import Path
from collections import defaultdict, Counter
import json


# Pattern categorization
PATTERN_CATEGORIES = {
    # Creational patterns
    'Factory': 'Creational',
    'Builder': 'Creational',
    'Singleton': 'Creational',
    'Prototype': 'Creational',
    
    # Structural patterns
    'Adapter': 'Structural',
    'Bridge': 'Structural',
    'Composite': 'Structural',
    'Decorator': 'Structural',
    'Facade': 'Structural',
    'Flyweight': 'Structural',
    'Proxy': 'Structural',
    
    # Behavioral patterns
    'Chain': 'Behavioral',
    'Command': 'Behavioral',
    'Interpreter': 'Behavioral',
    'Iterator': 'Behavioral',
    'Mediator': 'Behavioral',
    'Memento': 'Behavioral',
    'Observer': 'Behavioral',
    'State': 'Behavioral',
    'Strategy': 'Behavioral',
    'Template': 'Behavioral',
    'Visitor': 'Behavioral'
}


def parse_dpf_results(results_file_path):
    """
    Parse the DesignPatternFinder Results.txt file.
    Returns a dictionary: {model_name: {file_name: set(patterns)}}
    """
    detections = defaultdict(lambda: defaultdict(set))
    current_file = None
    current_model = None
    
    with open(results_file_path, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            
            # Skip empty lines and the header
            if not line or line.startswith('Found'):
                continue
            
            # Check if this is a file path line (contains .java)
            if line.endswith('.java') and Path(line).is_file():
                current_file = line
                # Extract model name from path
                # e.g., ...\generated_code\gemma_2_2b_it\prompt_12.java
                match = re.search(r'generated_code[/\\]([^/\\]+)[/\\]', line)
                if match:
                    current_model = match.group(1)
                    file_name = Path(line).name
            
            # Check if this is a patterns line
            elif line.startswith('Possible patterns:'):
                if current_file and current_model:
                    patterns_str = line.replace('Possible patterns:', '').strip()
                    patterns = [p.strip() for p in patterns_str.split(',')]
                    file_name = Path(current_file).name
                    detections[current_model][file_name] = set(patterns)
    
    return dict(detections)


def calculate_model_metrics(model_detections, total_files_per_model):
    """
    Calculate comprehensive metrics for a single model.
    """
    metrics = {
        'total_files': total_files_per_model,
        'files_with_patterns': len(model_detections),
        'files_without_patterns': total_files_per_model - len(model_detections),
        'pattern_frequency': Counter(),
        'category_frequency': Counter(),
        'files_with_multiple_patterns': 0,
        'total_pattern_occurrences': 0,
        'unique_patterns_detected': set(),
        'pattern_combinations': Counter()
    }
    
    for file_name, patterns in model_detections.items():
        num_patterns = len(patterns)
        metrics['total_pattern_occurrences'] += num_patterns
        
        if num_patterns > 1:
            metrics['files_with_multiple_patterns'] += 1
            # Track pattern combinations
            combo = ', '.join(sorted(patterns))
            metrics['pattern_combinations'][combo] += 1
        
        for pattern in patterns:
            metrics['pattern_frequency'][pattern] += 1
            metrics['unique_patterns_detected'].add(pattern)
            
            # Categorize pattern
            category = PATTERN_CATEGORIES.get(pattern, 'Unknown')
            metrics['category_frequency'][category] += 1
    
    # Calculate percentages
    if metrics['total_files'] > 0:
        metrics['percentage_with_patterns'] = (metrics['files_with_patterns'] / metrics['total_files']) * 100
        metrics['percentage_without_patterns'] = (metrics['files_without_patterns'] / metrics['total_files']) * 100
    
    if metrics['files_with_patterns'] > 0:
        metrics['avg_patterns_per_file_with_patterns'] = metrics['total_pattern_occurrences'] / metrics['files_with_patterns']
    else:
        metrics['avg_patterns_per_file_with_patterns'] = 0
    
    # Convert set to list for JSON serialization
    metrics['unique_patterns_detected'] = sorted(list(metrics['unique_patterns_detected']))
    
    return metrics


def calculate_aggregate_metrics(all_detections, total_files_per_model):
    """
    Calculate aggregate metrics across all models.
    """
    aggregate = {
        'total_files_all_models': sum(total_files_per_model.values()),
        'total_files_with_patterns': 0,
        'pattern_frequency': Counter(),
        'category_frequency': Counter(),
        'unique_patterns_across_all': set(),
        'model_pattern_diversity': {},
        'pattern_by_model': defaultdict(lambda: defaultdict(int))
    }
    
    for model, detections in all_detections.items():
        aggregate['total_files_with_patterns'] += len(detections)
        
        for file_name, patterns in detections.items():
            for pattern in patterns:
                aggregate['pattern_frequency'][pattern] += 1
                aggregate['unique_patterns_across_all'].add(pattern)
                aggregate['pattern_by_model'][pattern][model] += 1
                
                category = PATTERN_CATEGORIES.get(pattern, 'Unknown')
                aggregate['category_frequency'][category] += 1
    
    # Calculate aggregate percentages
    if aggregate['total_files_all_models'] > 0:
        aggregate['percentage_with_patterns'] = (aggregate['total_files_with_patterns'] / aggregate['total_files_all_models']) * 100
    
    # Convert sets and defaultdicts for JSON
    aggregate['unique_patterns_across_all'] = sorted(list(aggregate['unique_patterns_across_all']))
    aggregate['pattern_by_model'] = {k: dict(v) for k, v in aggregate['pattern_by_model'].items()}
    
    return aggregate


def print_results(all_metrics, aggregate_metrics):
    """
    Print comprehensive analysis results.
    """
    print("=" * 100)
    print("LLM DESIGN PATTERN ANALYSIS")
    print("=" * 100)
    print()
    
    # Aggregate Overview
    print("=" * 100)
    print("AGGREGATE METRICS ACROSS ALL MODELS")
    print("=" * 100)
    print()
    print(f"Total files analyzed: {aggregate_metrics['total_files_all_models']}")
    print(f"Files with at least one pattern: {aggregate_metrics['total_files_with_patterns']} ({aggregate_metrics['percentage_with_patterns']:.2f}%)")
    print(f"Unique patterns detected across all models: {len(aggregate_metrics['unique_patterns_across_all'])}")
    print()
    
    print("-" * 100)
    print("TOP 10 MOST FREQUENT PATTERNS (Across All Models)")
    print("-" * 100)
    for pattern, count in aggregate_metrics['pattern_frequency'].most_common(10):
        percentage = (count / aggregate_metrics['total_files_all_models']) * 100
        print(f"{pattern:20s}: {count:4d} occurrences ({percentage:5.2f}% of all files)")
    print()
    
    print("-" * 100)
    print("PATTERN DISTRIBUTION BY CATEGORY (Across All Models)")
    print("-" * 100)
    total_category_occurrences = sum(aggregate_metrics['category_frequency'].values())
    for category in ['Creational', 'Structural', 'Behavioral', 'Unknown']:
        count = aggregate_metrics['category_frequency'][category]
        if total_category_occurrences > 0:
            percentage = (count / total_category_occurrences) * 100
            print(f"{category:15s}: {count:4d} occurrences ({percentage:5.2f}% of all pattern occurrences)")
    print()
    
    # Per-Model Analysis
    print("=" * 100)
    print("PER-MODEL ANALYSIS")
    print("=" * 100)
    print()
    
    # Sort models by percentage of files with patterns
    sorted_models = sorted(all_metrics.items(), 
                          key=lambda x: x[1]['percentage_with_patterns'], 
                          reverse=True)
    
    for model, metrics in sorted_models:
        print(f"\n{'=' * 100}")
        print(f"MODEL: {model}")
        print(f"{'=' * 100}")
        print()
        print(f"Total files: {metrics['total_files']}")
        print(f"Files with patterns: {metrics['files_with_patterns']} ({metrics['percentage_with_patterns']:.2f}%)")
        print(f"Files without patterns: {metrics['files_without_patterns']} ({metrics['percentage_without_patterns']:.2f}%)")
        print(f"Files with multiple patterns: {metrics['files_with_multiple_patterns']}")
        print(f"Total pattern occurrences: {metrics['total_pattern_occurrences']}")
        print(f"Average patterns per file (with patterns): {metrics['avg_patterns_per_file_with_patterns']:.2f}")
        print(f"Unique patterns detected: {len(metrics['unique_patterns_detected'])}")
        print()
        
        if metrics['pattern_frequency']:
            print(f"-" * 100)
            print(f"Pattern Frequency for {model}")
            print(f"-" * 100)
            for pattern, count in metrics['pattern_frequency'].most_common():
                percentage = (count / metrics['total_files']) * 100
                print(f"  {pattern:20s}: {count:3d} occurrences ({percentage:5.2f}% of files)")
            print()
        
        if metrics['category_frequency']:
            print(f"-" * 100)
            print(f"Category Distribution for {model}")
            print(f"-" * 100)
            total_cat = sum(metrics['category_frequency'].values())
            for category in ['Creational', 'Structural', 'Behavioral', 'Unknown']:
                count = metrics['category_frequency'][category]
                if count > 0:
                    percentage = (count / total_cat) * 100
                    print(f"  {category:15s}: {count:3d} occurrences ({percentage:5.2f}% of patterns)")
            print()
        
        if metrics['pattern_combinations']:
            print(f"-" * 100)
            print(f"Pattern Combinations for {model} (Top 5)")
            print(f"-" * 100)
            for combo, count in metrics['pattern_combinations'].most_common(5):
                print(f"  {combo}: {count} files")
            print()
    
    # Comparative Analysis
    print("=" * 100)
    print("COMPARATIVE ANALYSIS")
    print("=" * 100)
    print()
    
    print("-" * 100)
    print("Model Rankings by Pattern Detection Rate")
    print("-" * 100)
    for rank, (model, metrics) in enumerate(sorted_models, 1):
        print(f"{rank}. {model:30s}: {metrics['percentage_with_patterns']:5.2f}% of files with patterns")
    print()
    
    # Pattern prevalence across models
    print("-" * 100)
    print("Pattern Prevalence Across Models")
    print("-" * 100)
    print(f"{'Pattern':<20s} | {'Total':<6s} | " + " | ".join([f"{m[:8]:>8s}" for m in sorted(all_metrics.keys())]))
    print("-" * 100)
    
    all_patterns = sorted(aggregate_metrics['unique_patterns_across_all'])
    for pattern in all_patterns:
        total = aggregate_metrics['pattern_frequency'][pattern]
        model_counts = [str(aggregate_metrics['pattern_by_model'][pattern].get(m, 0)) for m in sorted(all_metrics.keys())]
        print(f"{pattern:<20s} | {total:<6d} | " + " | ".join([f"{c:>8s}" for c in model_counts]))
    print()
    
    # Most diverse model
    print("-" * 100)
    print("Pattern Diversity by Model")
    print("-" * 100)
    for model, metrics in sorted(all_metrics.items(), 
                                  key=lambda x: len(x[1]['unique_patterns_detected']), 
                                  reverse=True):
        print(f"{model:30s}: {len(metrics['unique_patterns_detected'])} unique patterns detected")
    print()


def save_detailed_results(all_metrics, aggregate_metrics, output_file):
    """
    Save detailed results to a JSON file.
    """
    results = {
        'aggregate_metrics': aggregate_metrics,
        'per_model_metrics': all_metrics
    }
    
    # Convert Counters to dicts for JSON serialization
    results['aggregate_metrics']['pattern_frequency'] = dict(aggregate_metrics['pattern_frequency'])
    results['aggregate_metrics']['category_frequency'] = dict(aggregate_metrics['category_frequency'])
    
    for model in results['per_model_metrics']:
        results['per_model_metrics'][model]['pattern_frequency'] = dict(results['per_model_metrics'][model]['pattern_frequency'])
        results['per_model_metrics'][model]['category_frequency'] = dict(results['per_model_metrics'][model]['category_frequency'])
        results['per_model_metrics'][model]['pattern_combinations'] = dict(results['per_model_metrics'][model]['pattern_combinations'])
    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(results, f, indent=2)
    
    print(f"\nDetailed results saved to: {output_file}")


def main():
    # File paths
    script_dir = Path(__file__).parent
    results_file = script_dir / "DesignPatternFinder Results.txt"
    output_file = script_dir / "llm_pattern_analysis.json"
    # generated_code is one level up from LLM_Code_Analysis folder
    generated_code_dir = script_dir.parent / "generated_code"
    
    print("Parsing DesignPatternFinder results...")
    all_detections = parse_dpf_results(results_file)
    
    # Count total files per model
    total_files_per_model = {}
    for model_dir in generated_code_dir.iterdir():
        if model_dir.is_dir():
            model_name = model_dir.name
            java_files = list(model_dir.glob('*.java'))
            total_files_per_model[model_name] = len(java_files)
    
    print(f"Found {len(all_detections)} models with pattern detections")
    print()
    
    # Calculate per-model metrics
    all_metrics = {}
    for model in total_files_per_model.keys():
        model_detections = all_detections.get(model, {})
        all_metrics[model] = calculate_model_metrics(model_detections, total_files_per_model[model])
    
    # Calculate aggregate metrics
    aggregate_metrics = calculate_aggregate_metrics(all_detections, total_files_per_model)
    
    # Print results
    print_results(all_metrics, aggregate_metrics)
    
    # Save detailed results
    save_detailed_results(all_metrics, aggregate_metrics, output_file)
    
    print("\n" + "=" * 100)
    print("Analysis complete!")
    print("=" * 100)


if __name__ == "__main__":
    main()
