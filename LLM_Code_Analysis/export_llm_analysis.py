"""
Generate CSV exports and summary statistics for LLM pattern analysis.
This complements the main analysis with exportable data formats.
"""

import json
import csv
from pathlib import Path


def export_to_csv(analysis_file):
    """
    Export analysis results to multiple CSV files for easy data manipulation.
    """
    script_dir = Path(__file__).parent
    
    with open(analysis_file, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    # 1. Model Summary CSV
    with open(script_dir / 'llm_model_summary.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(['Model', 'Total Files', 'Files With Patterns', 'Percentage With Patterns', 
                        'Files With Multiple Patterns', 'Unique Patterns', 'Avg Patterns Per File'])
        
        for model, metrics in sorted(data['per_model_metrics'].items()):
            writer.writerow([
                model,
                metrics['total_files'],
                metrics['files_with_patterns'],
                f"{metrics['percentage_with_patterns']:.2f}",
                metrics['files_with_multiple_patterns'],
                len(metrics['unique_patterns_detected']),
                f"{metrics['avg_patterns_per_file_with_patterns']:.2f}"
            ])
    
    # 2. Pattern Frequency by Model CSV
    all_patterns = sorted(data['aggregate_metrics']['unique_patterns_across_all'])
    with open(script_dir / 'llm_pattern_frequency.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        header = ['Pattern'] + sorted(data['per_model_metrics'].keys()) + ['Total']
        writer.writerow(header)
        
        for pattern in all_patterns:
            row = [pattern]
            for model in sorted(data['per_model_metrics'].keys()):
                count = data['per_model_metrics'][model]['pattern_frequency'].get(pattern, 0)
                row.append(count)
            row.append(data['aggregate_metrics']['pattern_frequency'][pattern])
            writer.writerow(row)
    
    # 3. Category Distribution by Model CSV
    categories = ['Creational', 'Structural', 'Behavioral']
    with open(script_dir / 'llm_category_distribution.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        header = ['Category'] + sorted(data['per_model_metrics'].keys()) + ['Total']
        writer.writerow(header)
        
        for category in categories:
            row = [category]
            for model in sorted(data['per_model_metrics'].keys()):
                count = data['per_model_metrics'][model]['category_frequency'].get(category, 0)
                row.append(count)
            row.append(data['aggregate_metrics']['category_frequency'][category])
            writer.writerow(row)
    
    # 4. Detailed pattern-model matrix with percentages
    with open(script_dir / 'llm_pattern_percentage_matrix.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        header = ['Pattern'] + sorted(data['per_model_metrics'].keys())
        writer.writerow(header)
        
        for pattern in all_patterns:
            row = [pattern]
            for model in sorted(data['per_model_metrics'].keys()):
                count = data['per_model_metrics'][model]['pattern_frequency'].get(pattern, 0)
                total_files = data['per_model_metrics'][model]['total_files']
                percentage = (count / total_files) * 100 if total_files > 0 else 0
                row.append(f"{percentage:.2f}")
            writer.writerow(row)
    
    print("CSV files generated:")
    print("  - llm_model_summary.csv")
    print("  - llm_pattern_frequency.csv")
    print("  - llm_category_distribution.csv")
    print("  - llm_pattern_percentage_matrix.csv")


def print_key_insights(analysis_file):
    """
    Print key insights and takeaways from the analysis.
    """
    with open(analysis_file, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    print("\n" + "=" * 100)
    print("KEY INSIGHTS")
    print("=" * 100)
    print()
    
    # 1. Overall pattern adoption
    total_files = data['aggregate_metrics']['total_files_all_models']
    files_with_patterns = data['aggregate_metrics']['total_files_with_patterns']
    percentage = data['aggregate_metrics']['percentage_with_patterns']
    
    print(f"1. OVERALL PATTERN ADOPTION")
    print(f"   - Only {percentage:.1f}% ({files_with_patterns}/{total_files}) of LLM-generated code contains identifiable design patterns")
    print(f"   - This suggests LLMs tend to generate simpler, more straightforward code by default")
    print()
    
    # 2. Model performance
    best_model = max(data['per_model_metrics'].items(), 
                     key=lambda x: x[1]['percentage_with_patterns'])
    worst_model = min(data['per_model_metrics'].items(), 
                      key=lambda x: x[1]['percentage_with_patterns'])
    
    print(f"2. MODEL PERFORMANCE VARIANCE")
    print(f"   - Best: {best_model[0]} with {best_model[1]['percentage_with_patterns']:.1f}% pattern detection")
    print(f"   - Worst: {worst_model[0]} with {worst_model[1]['percentage_with_patterns']:.1f}% pattern detection")
    print(f"   - Variance: {best_model[1]['percentage_with_patterns'] - worst_model[1]['percentage_with_patterns']:.1f} percentage points")
    print()
    
    # 3. Most common patterns
    top_3_patterns = sorted(data['aggregate_metrics']['pattern_frequency'].items(), 
                           key=lambda x: x[1], reverse=True)[:3]
    
    print(f"3. MOST COMMONLY DETECTED PATTERNS")
    for i, (pattern, count) in enumerate(top_3_patterns, 1):
        percentage = (count / total_files) * 100
        print(f"   {i}. {pattern}: {count} occurrences ({percentage:.2f}% of all files)")
    print()
    
    # 4. Category distribution
    cat_total = sum(data['aggregate_metrics']['category_frequency'].values())
    print(f"4. CATEGORY DISTRIBUTION")
    for category in ['Creational', 'Structural', 'Behavioral']:
        count = data['aggregate_metrics']['category_frequency'][category]
        percentage = (count / cat_total) * 100 if cat_total > 0 else 0
        print(f"   - {category}: {percentage:.1f}% of all pattern occurrences")
    print()
    
    # 5. Pattern combinations
    print(f"5. PATTERN COMBINATIONS")
    multi_pattern_files = sum(m['files_with_multiple_patterns'] 
                             for m in data['per_model_metrics'].values())
    print(f"   - {multi_pattern_files} files contain multiple design patterns")
    print(f"   - This represents {(multi_pattern_files/files_with_patterns)*100:.1f}% of files with patterns")
    print()
    
    # 6. Model-specific insights
    print(f"6. MODEL-SPECIFIC OBSERVATIONS")
    for model, metrics in sorted(data['per_model_metrics'].items(), 
                                  key=lambda x: x[1]['percentage_with_patterns'], 
                                  reverse=True):
        top_pattern = max(metrics['pattern_frequency'].items(), 
                         key=lambda x: x[1]) if metrics['pattern_frequency'] else ('None', 0)
        print(f"   - {model}: Favors '{top_pattern[0]}' pattern ({top_pattern[1]} occurrences)")
    print()
    
    # 7. Diversity analysis
    total_unique = len(data['aggregate_metrics']['unique_patterns_across_all'])
    gof_total = 23  # Total GoF patterns
    print(f"7. PATTERN DIVERSITY")
    print(f"   - {total_unique} out of 23 GoF patterns detected ({(total_unique/gof_total)*100:.1f}%)")
    print(f"   - Missing patterns suggest certain design patterns are rarely/never generated by LLMs")
    print()


def main():
    script_dir = Path(__file__).parent
    analysis_file = script_dir / "llm_pattern_analysis.json"
    
    if not analysis_file.exists():
        print("Error: llm_pattern_analysis.json not found. Run analyze_llm_patterns.py first.")
        return
    
    print("Generating CSV exports...")
    export_to_csv(analysis_file)
    
    print_key_insights(analysis_file)
    
    print("\n" + "=" * 100)
    print("Export complete!")
    print("=" * 100)


if __name__ == "__main__":
    main()
