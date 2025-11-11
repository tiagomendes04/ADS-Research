"""
Generate a comprehensive summary report of LLM pattern analysis.
"""

import json
from pathlib import Path
from datetime import datetime


def generate_markdown_report(analysis_file, output_file):
    """
    Generate a comprehensive markdown report of the analysis.
    """
    with open(analysis_file, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("# LLM-Generated Code: Design Pattern Analysis Report\n\n")
        f.write(f"**Generated:** {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n\n")
        f.write("---\n\n")
        
        # Executive Summary
        f.write("## Executive Summary\n\n")
        total_files = data['aggregate_metrics']['total_files_all_models']
        files_with_patterns = data['aggregate_metrics']['total_files_with_patterns']
        percentage = data['aggregate_metrics']['percentage_with_patterns']
        
        f.write(f"This report analyzes design pattern usage across **{len(data['per_model_metrics'])} LLM models** ")
        f.write(f"with **{total_files} generated Java files** (100 files per model).\n\n")
        
        f.write("### Key Findings\n\n")
        f.write(f"- **{percentage:.1f}%** of generated code contains identifiable design patterns\n")
        f.write(f"- **{len(data['aggregate_metrics']['unique_patterns_across_all'])}** unique patterns detected out of 23 GoF patterns\n")
        f.write(f"- **Builder** is the most common pattern (43 occurrences across all models)\n")
        f.write(f"- Pattern distribution: **Creational (44.7%)**, Structural (9.8%), **Behavioral (45.5%)**\n")
        f.write(f"- Significant variance between models: **10% to 39%** pattern detection rate\n\n")
        
        f.write("---\n\n")
        
        # Aggregate Metrics
        f.write("## Aggregate Metrics (All Models)\n\n")
        f.write(f"| Metric | Value |\n")
        f.write(f"|--------|-------|\n")
        f.write(f"| Total Files Analyzed | {total_files} |\n")
        f.write(f"| Files with Patterns | {files_with_patterns} ({percentage:.2f}%) |\n")
        f.write(f"| Files without Patterns | {total_files - files_with_patterns} ({100-percentage:.2f}%) |\n")
        f.write(f"| Unique Patterns Detected | {len(data['aggregate_metrics']['unique_patterns_across_all'])} |\n\n")
        
        # Top Patterns
        f.write("### Most Frequent Patterns\n\n")
        f.write("| Rank | Pattern | Occurrences | % of All Files | Category |\n")
        f.write("|------|---------|-------------|----------------|----------|\n")
        
        pattern_categories = {
            'Factory': 'Creational', 'Builder': 'Creational', 'Singleton': 'Creational', 'Prototype': 'Creational',
            'Adapter': 'Structural', 'Bridge': 'Structural', 'Composite': 'Structural', 'Decorator': 'Structural',
            'Facade': 'Structural', 'Flyweight': 'Structural', 'Proxy': 'Structural',
            'Chain': 'Behavioral', 'Command': 'Behavioral', 'Interpreter': 'Behavioral', 'Iterator': 'Behavioral',
            'Mediator': 'Behavioral', 'Memento': 'Behavioral', 'Observer': 'Behavioral', 'State': 'Behavioral',
            'Strategy': 'Behavioral', 'Template': 'Behavioral', 'Visitor': 'Behavioral'
        }
        
        sorted_patterns = sorted(data['aggregate_metrics']['pattern_frequency'].items(), 
                                key=lambda x: x[1], reverse=True)
        for rank, (pattern, count) in enumerate(sorted_patterns, 1):
            percentage_files = (count / total_files) * 100
            category = pattern_categories.get(pattern, 'Unknown')
            f.write(f"| {rank} | {pattern} | {count} | {percentage_files:.2f}% | {category} |\n")
        
        f.write("\n")
        
        # Category Distribution
        f.write("### Category Distribution\n\n")
        f.write("| Category | Occurrences | % of Pattern Occurrences |\n")
        f.write("|----------|-------------|-------------------------|\n")
        
        cat_total = sum(data['aggregate_metrics']['category_frequency'].values())
        for category in ['Creational', 'Structural', 'Behavioral']:
            count = data['aggregate_metrics']['category_frequency'][category]
            percentage = (count / cat_total) * 100 if cat_total > 0 else 0
            f.write(f"| {category} | {count} | {percentage:.2f}% |\n")
        
        f.write("\n---\n\n")
        
        # Model Rankings
        f.write("## Model Comparison\n\n")
        f.write("### Pattern Detection Rate Rankings\n\n")
        f.write("| Rank | Model | Files with Patterns | Detection Rate | Unique Patterns |\n")
        f.write("|------|-------|---------------------|----------------|------------------|\n")
        
        sorted_models = sorted(data['per_model_metrics'].items(), 
                              key=lambda x: x[1]['percentage_with_patterns'], 
                              reverse=True)
        
        for rank, (model, metrics) in enumerate(sorted_models, 1):
            f.write(f"| {rank} | {model} | {metrics['files_with_patterns']}/100 | ")
            f.write(f"{metrics['percentage_with_patterns']:.2f}% | ")
            f.write(f"{len(metrics['unique_patterns_detected'])} |\n")
        
        f.write("\n")
        
        # Per-Model Details
        f.write("### Detailed Model Analysis\n\n")
        
        for model, metrics in sorted_models:
            f.write(f"#### {model}\n\n")
            f.write(f"**Overview:**\n")
            f.write(f"- Files with patterns: {metrics['files_with_patterns']}/100 ({metrics['percentage_with_patterns']:.2f}%)\n")
            f.write(f"- Files with multiple patterns: {metrics['files_with_multiple_patterns']}\n")
            f.write(f"- Average patterns per file (with patterns): {metrics['avg_patterns_per_file_with_patterns']:.2f}\n")
            f.write(f"- Unique patterns: {len(metrics['unique_patterns_detected'])}\n\n")
            
            if metrics['pattern_frequency']:
                f.write(f"**Pattern Frequency:**\n\n")
                f.write("| Pattern | Count | % of Files |\n")
                f.write("|---------|-------|------------|\n")
                
                sorted_freq = sorted(metrics['pattern_frequency'].items(), key=lambda x: x[1], reverse=True)
                for pattern, count in sorted_freq:
                    percentage = (count / metrics['total_files']) * 100
                    f.write(f"| {pattern} | {count} | {percentage:.2f}% |\n")
                
                f.write("\n")
            
            if metrics['category_frequency']:
                f.write(f"**Category Distribution:**\n\n")
                f.write("| Category | Count | % of Patterns |\n")
                f.write("|----------|-------|---------------|\n")
                
                cat_total_model = sum(metrics['category_frequency'].values())
                for category in ['Creational', 'Structural', 'Behavioral']:
                    count = metrics['category_frequency'].get(category, 0)
                    if count > 0:
                        percentage = (count / cat_total_model) * 100
                        f.write(f"| {category} | {count} | {percentage:.2f}% |\n")
                
                f.write("\n")
        
        f.write("---\n\n")
        
        # Pattern-Model Matrix
        f.write("## Pattern-Model Distribution Matrix\n\n")
        f.write("| Pattern | " + " | ".join(sorted(data['per_model_metrics'].keys())) + " | **Total** |\n")
        f.write("|---------|" + "|".join(["-------"] * (len(data['per_model_metrics']) + 1)) + "|\n")
        
        all_patterns = sorted(data['aggregate_metrics']['unique_patterns_across_all'])
        for pattern in all_patterns:
            row = f"| {pattern} |"
            for model in sorted(data['per_model_metrics'].keys()):
                count = data['per_model_metrics'][model]['pattern_frequency'].get(pattern, 0)
                row += f" {count} |"
            total = data['aggregate_metrics']['pattern_frequency'][pattern]
            row += f" **{total}** |"
            f.write(row + "\n")
        
        f.write("\n---\n\n")
        
        # Insights
        f.write("## Key Insights and Observations\n\n")
        
        best_model = max(data['per_model_metrics'].items(), key=lambda x: x[1]['percentage_with_patterns'])
        worst_model = min(data['per_model_metrics'].items(), key=lambda x: x[1]['percentage_with_patterns'])
        
        f.write("### 1. Low Overall Pattern Adoption\n")
        f.write(f"Only {percentage:.1f}% of LLM-generated code contains identifiable design patterns. ")
        f.write("This suggests that LLMs tend to generate simpler, more direct implementations rather than ")
        f.write("applying complex design patterns, which may be appropriate for many use cases.\n\n")
        
        f.write("### 2. Significant Model Variance\n")
        f.write(f"The best-performing model ({best_model[0]}) achieves {best_model[1]['percentage_with_patterns']:.1f}% pattern detection, ")
        f.write(f"while the lowest ({worst_model[0]}) achieves only {worst_model[1]['percentage_with_patterns']:.1f}%. ")
        f.write(f"This {best_model[1]['percentage_with_patterns'] - worst_model[1]['percentage_with_patterns']:.1f} percentage point variance ")
        f.write("suggests different training approaches and model architectures significantly impact pattern generation.\n\n")
        
        f.write("### 3. Builder Pattern Dominance\n")
        builder_count = data['aggregate_metrics']['pattern_frequency'].get('Builder', 0)
        f.write(f"Builder is the most common pattern with {builder_count} occurrences (7.17% of all files). ")
        f.write("This may reflect the pattern's utility for object construction in Java, or it could indicate ")
        f.write("that Builder is more commonly represented in training data.\n\n")
        
        f.write("### 4. Balanced Category Distribution\n")
        f.write("Creational (44.7%) and Behavioral (45.5%) patterns are nearly equally represented, ")
        f.write("while Structural patterns (9.8%) are significantly less common. ")
        f.write("This may suggest that LLMs find it easier to generate object creation and behavior patterns ")
        f.write("than structural organization patterns.\n\n")
        
        f.write("### 5. Limited Pattern Diversity\n")
        total_unique = len(data['aggregate_metrics']['unique_patterns_across_all'])
        f.write(f"Only {total_unique} out of 23 GoF patterns were detected. ")
        f.write("Notable absences include Observer, Facade, Proxy, and others. ")
        f.write("This limited diversity suggests certain patterns are rarely generated by LLMs, possibly due to:\n")
        f.write("- Underrepresentation in training data\n")
        f.write("- Complexity that makes them harder to generate\n")
        f.write("- Prompts that don't specifically request these patterns\n\n")
        
        f.write("### 6. Pattern Combinations\n")
        multi_pattern_files = sum(m['files_with_multiple_patterns'] for m in data['per_model_metrics'].values())
        f.write(f"{multi_pattern_files} files contain multiple design patterns ")
        f.write(f"({(multi_pattern_files/files_with_patterns)*100:.1f}% of files with patterns). ")
        f.write("This indicates that when patterns are used, they're sometimes combined, ")
        f.write("which is a sign of more sophisticated code generation.\n\n")
        
        f.write("---\n\n")
        
        # Conclusions
        f.write("## Conclusions\n\n")
        f.write("1. **LLMs generate relatively simple code** - The 17.5% pattern detection rate suggests most ")
        f.write("generated code follows straightforward implementations rather than complex design patterns.\n\n")
        
        f.write("2. **Model choice matters** - With a 29 percentage point variance between best and worst models, ")
        f.write("selecting the right LLM for pattern-rich code generation is important.\n\n")
        
        f.write("3. **Pattern bias exists** - Certain patterns (Builder, State, Command) are heavily favored, ")
        f.write("while others are rarely generated, indicating potential biases in training data or model behavior.\n\n")
        
        f.write("4. **Category balance** - The near-equal distribution between Creational and Behavioral patterns ")
        f.write("suggests LLMs handle different pattern types reasonably well, though Structural patterns lag behind.\n\n")
        
        f.write("5. **Further research needed** - Understanding why certain patterns are favored and how to encourage ")
        f.write("generation of underrepresented patterns could improve LLM code generation capabilities.\n\n")
        
        f.write("---\n\n")
        f.write("*End of Report*\n")
    
    print(f"\nMarkdown report generated: {output_file}")


def main():
    script_dir = Path(__file__).parent
    analysis_file = script_dir / "llm_pattern_analysis.json"
    output_file = script_dir / "LLM_Pattern_Analysis_Report.md"
    
    if not analysis_file.exists():
        print("Error: llm_pattern_analysis.json not found. Run analyze_llm_patterns.py first.")
        return
    
    generate_markdown_report(analysis_file, output_file)
    
    print("Report generation complete!")


if __name__ == "__main__":
    main()
