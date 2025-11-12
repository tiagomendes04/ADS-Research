# LLM Pattern Analysis Scripts

This directory contains scripts and results for analyzing design patterns in LLM-generated code.

## Generated Files

### Analysis Scripts

1. **`analyze_llm_patterns.py`** - Design pattern analysis
   - Parses DesignPatternFinder results for LLM-generated code
   - Calculates comprehensive metrics per model and aggregate statistics
   - Generates detailed JSON output

2. **`export_llm_analysis.py`** - CSV export and key insights
   - Exports analysis results to multiple CSV formats
   - Generates key insights summary
   - Easier data manipulation for spreadsheets

3. **`generate_report.py`** - Markdown report generator
   - Creates a comprehensive markdown report
   - Includes tables, rankings, and detailed analysis
   - Professional format for presentations/papers

4. **`analyze_code_metrics.py`** - Lines of code analysis
   - Counts LOC for each generated file (excludes comments/empty lines)
   - Calculates statistical metrics: min, Q25, median, Q75, max, mean, std
   - Generates CSV and markdown report with insights

### Output Files

#### JSON Data
- **`llm_pattern_analysis.json`** - Complete analysis results in JSON format
  - Per-model metrics
  - Aggregate metrics
  - Pattern frequencies and distributions

#### CSV Exports
- **`llm_model_summary.csv`** - Summary statistics per model
- **`llm_pattern_frequency.csv`** - Pattern occurrence count by model
- **`llm_category_distribution.csv`** - Category (Creational/Structural/Behavioral) distribution
- **`llm_pattern_percentage_matrix.csv`** - Pattern percentages by model
- **`code_metrics_loc.csv`** - Lines of code statistics per model

#### Reports
- **`LLM_Pattern_Analysis_Report.md`** - Comprehensive design pattern analysis report
  - Executive summary
  - Detailed per-model analysis
  - Comparative statistics
  - Key insights and conclusions

- **`Code_Metrics_Report.md`** - Lines of code analysis report
  - Statistical summary per model
  - Code length comparisons
  - Consistency analysis
  - Distribution insights

## Directory Structure

```
ADS-Research/
├── generated_code/              # LLM-generated Java files (one folder per model)
├── LLM_Code_Analysis/           # This directory - all analysis files
│   ├── analyze_llm_patterns.py  # Main analysis script
│   ├── export_llm_analysis.py   # CSV export script
│   ├── generate_report.py       # Report generator
│   ├── DesignPatternFinder Results.txt  # DPF tool output
│   ├── llm_pattern_analysis.json        # Analysis results
│   ├── llm_*.csv                         # CSV exports
│   └── LLM_Pattern_Analysis_Report.md   # Markdown report
└── ...
```

## Usage

### Running the Complete Analysis

**From within the LLM_Code_Analysis directory:**

```bash
# Navigate to the analysis directory
cd LLM_Code_Analysis

# 1. Run main analysis
python analyze_llm_patterns.py

# 2. Generate CSV exports and insights
python export_llm_analysis.py

# 3. Generate markdown report
python generate_report.py
```

### Prerequisites

- Python 3.x
- DesignPatternFinder results must exist in this directory: `DesignPatternFinder Results.txt`
- Generated code must be in: `../generated_code/` folder (one level up) with subfolders per model

## Key Metrics Analyzed

### Per-Model Metrics
- Total files analyzed
- Files containing patterns
- Pattern detection rate (%)
- Files with multiple patterns
- Unique patterns detected
- Average patterns per file
- Pattern frequency distribution
- Category distribution (Creational/Structural/Behavioral)
- Pattern combinations

### Aggregate Metrics
- Overall pattern detection rate
- Top 10 most frequent patterns
- Pattern distribution by category
- Pattern prevalence across models
- Model rankings by detection rate
- Pattern diversity analysis

## Key Findings Summary

Based on 600 files across 6 LLM models:

- **17.5%** of LLM-generated code contains identifiable design patterns
- **Builder** is the most common pattern (43 occurrences, 7.17% of files)
- **State** and **Command** are the next most common (24 and 23 occurrences)
- **Creational** and **Behavioral** patterns are nearly equal (44.7% vs 45.5%)
- **Structural** patterns are less common (9.8%)
- Only **9 out of 23** GoF patterns were detected
- Model performance varies significantly: **10% to 39%** detection rate
- **gpt_oss_120b** has the highest pattern detection rate (39%)
- **llama_3_2_1B_Instruct** has the lowest (10%)

## Pattern Categories

### Detected Patterns (9)
- **Creational:** Builder, Factory, Singleton
- **Structural:** Adapter
- **Behavioral:** Command, State, Template, Iterator, Strategy

### Undetected Patterns (14)
- **Creational:** Prototype
- **Structural:** Bridge, Composite, Decorator, Facade, Flyweight, Proxy
- **Behavioral:** Chain, Interpreter, Mediator, Memento, Observer, Visitor

## Research Implications

1. **LLMs favor simpler implementations** - 82.5% of code has no identifiable patterns
2. **Pattern bias exists** - Some patterns (Builder) are heavily favored over others
3. **Model architecture matters** - 29 percentage point variance between models
4. **Training data impact** - Missing patterns suggest underrepresentation in training
5. **Category balance** - Equal Creational/Behavioral suggests no category-specific difficulty

## Future Work

- Analyze correlation between prompts and pattern generation
- Investigate why certain patterns are never/rarely generated
- Compare against human-written code patterns
- Study impact of prompt engineering on pattern generation
- Analyze pattern quality and correctness (not just detection)
