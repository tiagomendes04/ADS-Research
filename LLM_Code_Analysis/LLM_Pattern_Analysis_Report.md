# LLM-Generated Code: Design Pattern Analysis Report

**Generated:** 2025-11-12 00:46:10

---

## Executive Summary

This report analyzes design pattern usage across **6 LLM models** with **600 generated Java files** (100 files per model).

### Key Findings

- **18.2%** of generated code contains identifiable design patterns
- **10** unique patterns detected out of 23 GoF patterns
- **Builder** is the most common pattern (43 occurrences across all models)
- Pattern distribution: **Creational (44.7%)**, Structural (9.8%), **Behavioral (45.5%)**
- Significant variance between models: **10% to 39%** pattern detection rate

---

## Aggregate Metrics (All Models)

| Metric | Value |
|--------|-------|
| Total Files Analyzed | 600 |
| Files with Patterns | 109 (18.17%) |
| Files without Patterns | 491 (81.83%) |
| Unique Patterns Detected | 10 |

### Most Frequent Patterns

| Rank | Pattern | Occurrences | % of All Files | Category |
|------|---------|-------------|----------------|----------|
| 1 | Builder | 44 | 7.33% | Creational |
| 2 | State | 29 | 4.83% | Behavioral |
| 3 | Command | 24 | 4.00% | Behavioral |
| 4 | Factory | 11 | 1.83% | Creational |
| 5 | Adapter | 10 | 1.67% | Structural |
| 6 | Iterator | 6 | 1.00% | Behavioral |
| 7 | Template | 4 | 0.67% | Behavioral |
| 8 | Facade | 1 | 0.17% | Structural |
| 9 | Strategy | 1 | 0.17% | Behavioral |
| 10 | Singleton | 1 | 0.17% | Creational |

### Category Distribution

| Category | Occurrences | % of Pattern Occurrences |
|----------|-------------|-------------------------|
| Creational | 56 | 42.75% |
| Structural | 11 | 8.40% |
| Behavioral | 64 | 48.85% |

---

## Model Comparison

### Pattern Detection Rate Rankings

| Rank | Model | Files with Patterns | Detection Rate | Unique Patterns |
|------|-------|---------------------|----------------|------------------|
| 1 | gpt_oss_120b | 41/100 | 41.00% | 8 |
| 2 | qwen2_5_7b_instruct | 19/100 | 19.00% | 7 |
| 3 | qwen2_5_coder_32b_instruct | 15/100 | 15.00% | 7 |
| 4 | llama_3_1_8B_Instruct | 12/100 | 12.00% | 5 |
| 5 | gemma_2_2b_it | 11/100 | 11.00% | 6 |
| 6 | llama_3_2_1B_Instruct | 11/100 | 11.00% | 5 |

### Detailed Model Analysis

#### gpt_oss_120b

**Overview:**
- Files with patterns: 41/100 (41.00%)
- Files with multiple patterns: 10
- Average patterns per file (with patterns): 1.24
- Unique patterns: 8

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 17 | 17.00% |
| State | 14 | 14.00% |
| Command | 9 | 9.00% |
| Iterator | 4 | 4.00% |
| Factory | 3 | 3.00% |
| Template | 2 | 2.00% |
| Adapter | 1 | 1.00% |
| Facade | 1 | 1.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 20 | 39.22% |
| Structural | 2 | 3.92% |
| Behavioral | 29 | 56.86% |

#### qwen2_5_7b_instruct

**Overview:**
- Files with patterns: 19/100 (19.00%)
- Files with multiple patterns: 2
- Average patterns per file (with patterns): 1.11
- Unique patterns: 7

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 9 | 9.00% |
| State | 5 | 5.00% |
| Adapter | 2 | 2.00% |
| Command | 2 | 2.00% |
| Strategy | 1 | 1.00% |
| Singleton | 1 | 1.00% |
| Factory | 1 | 1.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 11 | 52.38% |
| Structural | 2 | 9.52% |
| Behavioral | 8 | 38.10% |

#### qwen2_5_coder_32b_instruct

**Overview:**
- Files with patterns: 15/100 (15.00%)
- Files with multiple patterns: 5
- Average patterns per file (with patterns): 1.40
- Unique patterns: 7

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 9 | 9.00% |
| Command | 4 | 4.00% |
| State | 2 | 2.00% |
| Iterator | 2 | 2.00% |
| Adapter | 2 | 2.00% |
| Template | 1 | 1.00% |
| Factory | 1 | 1.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 10 | 47.62% |
| Structural | 2 | 9.52% |
| Behavioral | 9 | 42.86% |

#### llama_3_1_8B_Instruct

**Overview:**
- Files with patterns: 12/100 (12.00%)
- Files with multiple patterns: 2
- Average patterns per file (with patterns): 1.17
- Unique patterns: 5

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 4 | 4.00% |
| Command | 4 | 4.00% |
| State | 2 | 2.00% |
| Factory | 2 | 2.00% |
| Adapter | 2 | 2.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 6 | 42.86% |
| Structural | 2 | 14.29% |
| Behavioral | 6 | 42.86% |

#### gemma_2_2b_it

**Overview:**
- Files with patterns: 11/100 (11.00%)
- Files with multiple patterns: 1
- Average patterns per file (with patterns): 1.09
- Unique patterns: 6

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Command | 3 | 3.00% |
| State | 3 | 3.00% |
| Builder | 2 | 2.00% |
| Factory | 2 | 2.00% |
| Adapter | 1 | 1.00% |
| Template | 1 | 1.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 4 | 33.33% |
| Structural | 1 | 8.33% |
| Behavioral | 7 | 58.33% |

#### llama_3_2_1B_Instruct

**Overview:**
- Files with patterns: 11/100 (11.00%)
- Files with multiple patterns: 1
- Average patterns per file (with patterns): 1.09
- Unique patterns: 5

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| State | 3 | 3.00% |
| Builder | 3 | 3.00% |
| Factory | 2 | 2.00% |
| Command | 2 | 2.00% |
| Adapter | 2 | 2.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 5 | 41.67% |
| Structural | 2 | 16.67% |
| Behavioral | 5 | 41.67% |

---

## Pattern-Model Distribution Matrix

| Pattern | gemma_2_2b_it | gpt_oss_120b | llama_3_1_8B_Instruct | llama_3_2_1B_Instruct | qwen2_5_7b_instruct | qwen2_5_coder_32b_instruct | **Total** |
|---------|-------|-------|-------|-------|-------|-------|-------|
| Adapter | 1 | 1 | 2 | 2 | 2 | 2 | **10** |
| Builder | 2 | 17 | 4 | 3 | 9 | 9 | **44** |
| Command | 3 | 9 | 4 | 2 | 2 | 4 | **24** |
| Facade | 0 | 1 | 0 | 0 | 0 | 0 | **1** |
| Factory | 2 | 3 | 2 | 2 | 1 | 1 | **11** |
| Iterator | 0 | 4 | 0 | 0 | 0 | 2 | **6** |
| Singleton | 0 | 0 | 0 | 0 | 1 | 0 | **1** |
| State | 3 | 14 | 2 | 3 | 5 | 2 | **29** |
| Strategy | 0 | 0 | 0 | 0 | 1 | 0 | **1** |
| Template | 1 | 2 | 0 | 0 | 0 | 1 | **4** |

---

## Key Insights and Observations

### 1. Low Overall Pattern Adoption
Only 41.7% of LLM-generated code contains identifiable design patterns. This suggests that LLMs tend to generate simpler, more direct implementations rather than applying complex design patterns, which may be appropriate for many use cases.

### 2. Significant Model Variance
The best-performing model (gpt_oss_120b) achieves 41.0% pattern detection, while the lowest (gemma_2_2b_it) achieves only 11.0%. This 30.0 percentage point variance suggests different training approaches and model architectures significantly impact pattern generation.

### 3. Builder Pattern Dominance
Builder is the most common pattern with 44 occurrences (7.17% of all files). This may reflect the pattern's utility for object construction in Java, or it could indicate that Builder is more commonly represented in training data.

### 4. Balanced Category Distribution
Creational (44.7%) and Behavioral (45.5%) patterns are nearly equally represented, while Structural patterns (9.8%) are significantly less common. This may suggest that LLMs find it easier to generate object creation and behavior patterns than structural organization patterns.

### 5. Limited Pattern Diversity
Only 10 out of 23 GoF patterns were detected. Notable absences include Observer, Facade, Proxy, and others. This limited diversity suggests certain patterns are rarely generated by LLMs, possibly due to:
- Underrepresentation in training data
- Complexity that makes them harder to generate
- Prompts that don't specifically request these patterns

### 6. Pattern Combinations
21 files contain multiple design patterns (19.3% of files with patterns). This indicates that when patterns are used, they're sometimes combined, which is a sign of more sophisticated code generation.

---

## Conclusions

1. **LLMs generate relatively simple code** - The 17.5% pattern detection rate suggests most generated code follows straightforward implementations rather than complex design patterns.

2. **Model choice matters** - With a 29 percentage point variance between best and worst models, selecting the right LLM for pattern-rich code generation is important.

3. **Pattern bias exists** - Certain patterns (Builder, State, Command) are heavily favored, while others are rarely generated, indicating potential biases in training data or model behavior.

4. **Category balance** - The near-equal distribution between Creational and Behavioral patterns suggests LLMs handle different pattern types reasonably well, though Structural patterns lag behind.

5. **Further research needed** - Understanding why certain patterns are favored and how to encourage generation of underrepresented patterns could improve LLM code generation capabilities.

---

*End of Report*
