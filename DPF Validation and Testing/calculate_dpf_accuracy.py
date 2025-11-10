"""
Script to calculate the accuracy of DesignPatternFinder tool
by comparing its detection results against the ground truth from
the gof-java-design-patterns repository structure.
"""

import os
import re
from pathlib import Path
from collections import defaultdict
import json


# Pattern name mappings to handle variations
PATTERN_MAPPINGS = {
    'abstract-factory': 'Factory',  # DPF detects as "Factory"
    'adapter': 'Adapter',
    'bridge': 'Bridge',
    'builder': 'Builder',
    'chain-of-responsibility': 'Chain',  # DPF uses "Chain"
    'command': 'Command',
    'composite': 'Composite',
    'decorator': 'Decorator',
    'facade': 'Facade',
    'factory-pattern': 'Factory',
    'flyweight': 'Flyweight',
    'iterator': 'Iterator',
    'observer': 'Observer',
    'prototype': 'Prototype',
    'proxy': 'Proxy',
    'singleton': 'Singleton',
    'state': 'State',
    'strategy': 'Strategy',
    'template-method': 'Template'  # DPF might use "Template"
}


def normalize_path(path):
    """Normalize Windows path for comparison"""
    return str(Path(path)).lower()


def parse_results_file(results_file_path):
    """
    Parse the DesignPatternFinder Results.txt file.
    Returns a dictionary: {normalized_file_path: set(detected_patterns)}
    """
    detections = {}
    current_file = None
    
    with open(results_file_path, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            
            # Skip empty lines and the header
            if not line or line.startswith('Found'):
                continue
            
            # Check if this is a file path line (contains .java)
            if line.endswith('.java'):
                current_file = normalize_path(line)
                detections[current_file] = set()
            
            # Check if this is a patterns line
            elif line.startswith('Possible patterns:'):
                if current_file:
                    patterns_str = line.replace('Possible patterns:', '').strip()
                    patterns = [p.strip() for p in patterns_str.split(',')]
                    detections[current_file] = set(patterns)
    
    return detections


def scan_ground_truth_repo(repo_path):
    """
    Scan the gof-java-design-patterns repository to find all Java files
    and their expected pattern based on folder structure.
    Returns a dictionary: {normalized_file_path: expected_pattern}
    """
    ground_truth = {}
    repo_path = Path(repo_path)
    
    # Iterate through pattern folders
    for pattern_folder in repo_path.iterdir():
        if not pattern_folder.is_dir():
            continue
        
        folder_name = pattern_folder.name
        
        # Skip non-pattern folders
        if folder_name in ['.git', 'src', 'target', '__pycache__']:
            continue
        
        # Get the expected pattern name from the mapping
        if folder_name not in PATTERN_MAPPINGS:
            print(f"Warning: Unknown pattern folder '{folder_name}'")
            continue
        
        expected_pattern = PATTERN_MAPPINGS[folder_name]
        
        # Find all .java files in this pattern folder
        for java_file in pattern_folder.rglob('*.java'):
            normalized_path = normalize_path(str(java_file))
            ground_truth[normalized_path] = expected_pattern
    
    return ground_truth


def calculate_metrics(ground_truth, detections):
    """
    Calculate accuracy metrics by comparing ground truth with detections.
    """
    results = {
        'total_files': len(ground_truth),
        'detected_files': 0,
        'correct_detections': 0,
        'incorrect_detections': 0,
        'missed_files': 0,
        'per_pattern_stats': defaultdict(lambda: {
            'total': 0,
            'detected': 0,
            'correct': 0,
            'false_positives': 0,
            'missed': 0
        }),
        'detailed_results': []
    }
    
    # Check each file in ground truth
    for file_path, expected_pattern in ground_truth.items():
        detected_patterns = detections.get(file_path, set())
        
        pattern_stats = results['per_pattern_stats'][expected_pattern]
        pattern_stats['total'] += 1
        
        file_result = {
            'file': file_path,
            'expected': expected_pattern,
            'detected': list(detected_patterns),
            'status': ''
        }
        
        if not detected_patterns:
            # File was not detected at all
            results['missed_files'] += 1
            pattern_stats['missed'] += 1
            file_result['status'] = 'MISSED'
        else:
            # File was detected
            results['detected_files'] += 1
            pattern_stats['detected'] += 1
            
            if expected_pattern in detected_patterns:
                # Correct detection (even if there are other patterns too)
                results['correct_detections'] += 1
                pattern_stats['correct'] += 1
                file_result['status'] = 'CORRECT'
                
                # Check for additional false positives
                extra_patterns = detected_patterns - {expected_pattern}
                if extra_patterns:
                    file_result['status'] = 'CORRECT_WITH_EXTRAS'
                    file_result['extra_patterns'] = list(extra_patterns)
            else:
                # Incorrect detection
                results['incorrect_detections'] += 1
                pattern_stats['false_positives'] += 1
                file_result['status'] = 'INCORRECT'
        
        results['detailed_results'].append(file_result)
    
    # Calculate overall metrics
    if results['total_files'] > 0:
        results['detection_rate'] = results['detected_files'] / results['total_files']
        results['accuracy'] = results['correct_detections'] / results['total_files']
    
    # Calculate overall precision across all patterns
    # Precision: of all positive predictions (detections), how many were correct?
    if results['detected_files'] > 0:
        results['overall_precision'] = results['correct_detections'] / results['detected_files']
    else:
        results['overall_precision'] = 0.0
    
    # Recall: of all actual positives (ground truth), how many did we find?
    # This is the same as accuracy in this case
    results['overall_recall'] = results['accuracy']
    
    # F1 Score: harmonic mean of precision and recall
    if results['overall_precision'] + results['overall_recall'] > 0:
        results['overall_f1_score'] = 2 * (results['overall_precision'] * results['overall_recall']) / \
                                       (results['overall_precision'] + results['overall_recall'])
    else:
        results['overall_f1_score'] = 0.0
    
    # Calculate per-pattern metrics
    for pattern, stats in results['per_pattern_stats'].items():
        if stats['total'] > 0:
            stats['detection_rate'] = stats['detected'] / stats['total']
            stats['accuracy'] = stats['correct'] / stats['total']
            
            # Precision: of all detections for this pattern, how many were correct?
            if stats['detected'] > 0:
                stats['precision'] = stats['correct'] / stats['detected']
            else:
                stats['precision'] = 0.0
            
            # Recall: same as accuracy in this context (correct / total)
            stats['recall'] = stats['accuracy']
            
            # F1 Score
            if stats['precision'] + stats['recall'] > 0:
                stats['f1_score'] = 2 * (stats['precision'] * stats['recall']) / (stats['precision'] + stats['recall'])
            else:
                stats['f1_score'] = 0.0
    
    # Calculate macro-averaged metrics (average of per-pattern metrics)
    num_patterns = len(results['per_pattern_stats'])
    if num_patterns > 0:
        results['macro_avg_precision'] = sum(s['precision'] for s in results['per_pattern_stats'].values()) / num_patterns
        results['macro_avg_recall'] = sum(s['recall'] for s in results['per_pattern_stats'].values()) / num_patterns
        results['macro_avg_f1'] = sum(s['f1_score'] for s in results['per_pattern_stats'].values()) / num_patterns
    else:
        results['macro_avg_precision'] = 0.0
        results['macro_avg_recall'] = 0.0
        results['macro_avg_f1'] = 0.0
    
    # Micro-averaged metrics are the same as overall metrics in this case
    # (since we're treating each file as an instance)
    results['micro_avg_precision'] = results['overall_precision']
    results['micro_avg_recall'] = results['overall_recall']
    results['micro_avg_f1'] = results['overall_f1_score']
    
    return results


def print_results(results):
    """Print the results in a readable format"""
    print("=" * 80)
    print("DESIGN PATTERN FINDER - ACCURACY ANALYSIS")
    print("=" * 80)
    print()
    
    print(f"Total Java files in ground truth: {results['total_files']}")
    print(f"Files detected by tool: {results['detected_files']}")
    print(f"Missed files: {results['missed_files']}")
    print()
    
    print(f"Correct detections: {results['correct_detections']}")
    print(f"Incorrect detections: {results['incorrect_detections']}")
    print()
    
    print(f"Overall Detection Rate: {results['detection_rate']:.2%}")
    print(f"Overall Accuracy (Recall): {results['accuracy']:.2%}")
    print()
    
    print("=" * 80)
    print("OVERALL TOOL PERFORMANCE METRICS")
    print("=" * 80)
    print()
    print(f"Precision: {results['overall_precision']:.2%}")
    print(f"  (Of all files where a pattern was detected, how many had the correct pattern?)")
    print()
    print(f"Recall: {results['overall_recall']:.2%}")
    print(f"  (Of all files in the ground truth, how many were correctly identified?)")
    print()
    print(f"F1 Score: {results['overall_f1_score']:.2%}")
    print(f"  (Harmonic mean of Precision and Recall)")
    print()
    
    print("-" * 80)
    print("MACRO-AVERAGED METRICS (average across all patterns)")
    print("-" * 80)
    print(f"Macro Avg Precision: {results['macro_avg_precision']:.2%}")
    print(f"Macro Avg Recall: {results['macro_avg_recall']:.2%}")
    print(f"Macro Avg F1 Score: {results['macro_avg_f1']:.2%}")
    print(f"  (Treats all patterns equally, regardless of their frequency)")
    print()
    
    print("-" * 80)
    print("MICRO-AVERAGED METRICS (weighted by pattern frequency)")
    print("-" * 80)
    print(f"Micro Avg Precision: {results['micro_avg_precision']:.2%}")
    print(f"Micro Avg Recall: {results['micro_avg_recall']:.2%}")
    print(f"Micro Avg F1 Score: {results['micro_avg_f1']:.2%}")
    print(f"  (Treats all files equally, patterns with more files have more weight)")
    print()
    
    print("=" * 80)
    print("PER-PATTERN STATISTICS")
    print("=" * 80)
    print()
    
    # Sort patterns by name
    sorted_patterns = sorted(results['per_pattern_stats'].items())
    
    for pattern, stats in sorted_patterns:
        print(f"\n{pattern}:")
        print(f"  Total files: {stats['total']}")
        print(f"  Detected: {stats['detected']}/{stats['total']}")
        print(f"  Correct: {stats['correct']}/{stats['total']}")
        print(f"  Missed: {stats['missed']}")
        print(f"  False Positives: {stats['false_positives']}")
        print(f"  Detection Rate: {stats['detection_rate']:.2%}")
        print(f"  Accuracy: {stats['accuracy']:.2%}")
        print(f"  Precision: {stats['precision']:.2%}")
        print(f"  Recall: {stats['recall']:.2%}")
        print(f"  F1 Score: {stats['f1_score']:.2%}")
    
    print()
    print("=" * 80)
    print("SAMPLE INCORRECT/MISSED DETECTIONS")
    print("=" * 80)
    
    # Show some examples of problems
    incorrect_count = 0
    missed_count = 0
    
    for result in results['detailed_results']:
        if result['status'] == 'INCORRECT' and incorrect_count < 5:
            print(f"\nINCORRECT: {Path(result['file']).name}")
            print(f"  Expected: {result['expected']}")
            print(f"  Detected: {', '.join(result['detected']) if result['detected'] else 'None'}")
            incorrect_count += 1
        
        elif result['status'] == 'MISSED' and missed_count < 5:
            print(f"\nMISSED: {Path(result['file']).name}")
            print(f"  Expected: {result['expected']}")
            missed_count += 1


def save_detailed_results(results, output_file):
    """Save detailed results to a JSON file"""
    # Convert defaultdict to regular dict for JSON serialization
    results_copy = dict(results)
    results_copy['per_pattern_stats'] = dict(results_copy['per_pattern_stats'])
    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(results_copy, f, indent=2)
    
    print(f"\nDetailed results saved to: {output_file}")


def main():
    # File paths
    script_dir = Path(__file__).parent
    results_file = script_dir / "DesignPatternFinder Results.txt"
    repo_path = script_dir / "gof-java-design-patterns"
    output_file = script_dir / "dpf_accuracy_analysis.json"
    
    print("Parsing DesignPatternFinder results...")
    detections = parse_results_file(results_file)
    print(f"Found {len(detections)} files with detections")
    
    print("\nScanning ground truth repository...")
    ground_truth = scan_ground_truth_repo(repo_path)
    print(f"Found {len(ground_truth)} Java files in pattern folders")
    
    print("\nCalculating accuracy metrics...")
    results = calculate_metrics(ground_truth, detections)
    
    print_results(results)
    
    save_detailed_results(results, output_file)
    
    print("\n" + "=" * 80)
    print("Analysis complete!")
    print("=" * 80)


if __name__ == "__main__":
    main()
