# LLM-Generated Code: Design Pattern Analysis Report

**Generated:** 2025-11-11 21:53:26

---

## Executive Summary

This report analyzes design pattern usage across **6 LLM models** with **600 generated Java files** (100 files per model).

### Key Findings

- **17.5%** of generated code contains identifiable design patterns
- **9** unique patterns detected out of 23 GoF patterns
- **Builder** is the most common pattern (43 occurrences across all models)
- Pattern distribution: **Creational (44.7%)**, Structural (9.8%), **Behavioral (45.5%)**
- Significant variance between models: **10% to 39%** pattern detection rate

---

## Aggregate Metrics (All Models)

| Metric | Value |
|--------|-------|
| Total Files Analyzed | 600 |
| Files with Patterns | 105 (17.50%) |
| Files without Patterns | 495 (82.50%) |
| Unique Patterns Detected | 9 |

### Most Frequent Patterns

| Rank | Pattern | Occurrences | % of All Files | Category |
|------|---------|-------------|----------------|----------|
| 1 | Builder | 43 | 7.17% | Creational |
| 2 | State | 24 | 4.00% | Behavioral |
| 3 | Command | 23 | 3.83% | Behavioral |
| 4 | Adapter | 12 | 2.00% | Structural |
| 5 | Factory | 11 | 1.83% | Creational |
| 6 | Template | 4 | 0.67% | Behavioral |
| 7 | Iterator | 4 | 0.67% | Behavioral |
| 8 | Strategy | 1 | 0.17% | Behavioral |
| 9 | Singleton | 1 | 0.17% | Creational |

### Category Distribution

| Category | Occurrences | % of Pattern Occurrences |
|----------|-------------|-------------------------|
| Creational | 55 | 44.72% |
| Structural | 12 | 9.76% |
| Behavioral | 56 | 45.53% |

---

## Model Comparison

### Pattern Detection Rate Rankings

| Rank | Model | Files with Patterns | Detection Rate | Unique Patterns |
|------|-------|---------------------|----------------|------------------|
| 1 | gpt_oss_120b | 39/100 | 39.00% | 7 |
| 2 | qwen2_5_7b_instruct | 19/100 | 19.00% | 7 |
| 3 | qwen2_5_coder_32b | 14/100 | 14.00% | 6 |
| 4 | llama_3_1_8B_Instruct | 12/100 | 12.00% | 5 |
| 5 | gemma_2_2b_it | 11/100 | 11.00% | 6 |
| 6 | llama_3_2_1B_Instruct | 10/100 | 10.00% | 5 |

### Detailed Model Analysis

#### gpt_oss_120b

**Overview:**
- Files with patterns: 39/100 (39.00%)
- Files with multiple patterns: 9
- Average patterns per file (with patterns): 1.26
- Unique patterns: 7

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 18 | 18.00% |
| State | 10 | 10.00% |
| Command | 9 | 9.00% |
| Iterator | 4 | 4.00% |
| Factory | 3 | 3.00% |
| Adapter | 3 | 3.00% |
| Template | 2 | 2.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 21 | 42.86% |
| Structural | 3 | 6.12% |
| Behavioral | 25 | 51.02% |

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

#### qwen2_5_coder_32b

**Overview:**
- Files with patterns: 14/100 (14.00%)
- Files with multiple patterns: 2
- Average patterns per file (with patterns): 1.14
- Unique patterns: 6

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 7 | 7.00% |
| Command | 3 | 3.00% |
| State | 2 | 2.00% |
| Adapter | 2 | 2.00% |
| Template | 1 | 1.00% |
| Factory | 1 | 1.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 8 | 50.00% |
| Structural | 2 | 12.50% |
| Behavioral | 6 | 37.50% |

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
- Files with patterns: 10/100 (10.00%)
- Files with multiple patterns: 1
- Average patterns per file (with patterns): 1.10
- Unique patterns: 5

**Pattern Frequency:**

| Pattern | Count | % of Files |
|---------|-------|------------|
| Builder | 3 | 3.00% |
| State | 2 | 2.00% |
| Factory | 2 | 2.00% |
| Command | 2 | 2.00% |
| Adapter | 2 | 2.00% |

**Category Distribution:**

| Category | Count | % of Patterns |
|----------|-------|---------------|
| Creational | 5 | 45.45% |
| Structural | 2 | 18.18% |
| Behavioral | 4 | 36.36% |

---

## Pattern-Model Distribution Matrix

| Pattern | gemma_2_2b_it | gpt_oss_120b | llama_3_1_8B_Instruct | llama_3_2_1B_Instruct | qwen2_5_7b_instruct | qwen2_5_coder_32b | **Total** |
|---------|-------|-------|-------|-------|-------|-------|-------|
| Adapter | 1 | 3 | 2 | 2 | 2 | 2 | **12** |
| Builder | 2 | 18 | 4 | 3 | 9 | 7 | **43** |
| Command | 3 | 9 | 4 | 2 | 2 | 3 | **23** |
| Factory | 2 | 3 | 2 | 2 | 1 | 1 | **11** |
| Iterator | 0 | 4 | 0 | 0 | 0 | 0 | **4** |
| Singleton | 0 | 0 | 0 | 0 | 1 | 0 | **1** |
| State | 3 | 10 | 2 | 2 | 5 | 2 | **24** |
| Strategy | 0 | 0 | 0 | 0 | 1 | 0 | **1** |
| Template | 1 | 2 | 0 | 0 | 0 | 1 | **4** |

---

## Key Insights and Observations

### 1. Low Overall Pattern Adoption
Only 36.4% of LLM-generated code contains identifiable design patterns. This suggests that LLMs tend to generate simpler, more direct implementations rather than applying complex design patterns, which may be appropriate for many use cases.

### 2. Significant Model Variance
The best-performing model (gpt_oss_120b) achieves 39.0% pattern detection, while the lowest (llama_3_2_1B_Instruct) achieves only 10.0%. This 29.0 percentage point variance suggests different training approaches and model architectures significantly impact pattern generation.

### 3. Builder Pattern Dominance
Builder is the most common pattern with 43 occurrences (7.17% of all files). This may reflect the pattern's utility for object construction in Java, or it could indicate that Builder is more commonly represented in training data.

### 4. Balanced Category Distribution
Creational (44.7%) and Behavioral (45.5%) patterns are nearly equally represented, while Structural patterns (9.8%) are significantly less common. This may suggest that LLMs find it easier to generate object creation and behavior patterns than structural organization patterns.

### 5. Limited Pattern Diversity
Only 9 out of 23 GoF patterns were detected. Notable absences include Observer, Facade, Proxy, and others. This limited diversity suggests certain patterns are rarely generated by LLMs, possibly due to:
- Underrepresentation in training data
- Complexity that makes them harder to generate
- Prompts that don't specifically request these patterns

### 6. Pattern Combinations
17 files contain multiple design patterns (16.2% of files with patterns). This indicates that when patterns are used, they're sometimes combined, which is a sign of more sophisticated code generation.

---

## Conclusions

1. **LLMs generate relatively simple code** - The 17.5% pattern detection rate suggests most generated code follows straightforward implementations rather than complex design patterns.

2. **Model choice matters** - With a 29 percentage point variance between best and worst models, selecting the right LLM for pattern-rich code generation is important.

3. **Pattern bias exists** - Certain patterns (Builder, State, Command) are heavily favored, while others are rarely generated, indicating potential biases in training data or model behavior.

4. **Category balance** - The near-equal distribution between Creational and Behavioral patterns suggests LLMs handle different pattern types reasonably well, though Structural patterns lag behind.

5. **Further research needed** - Understanding why certain patterns are favored and how to encourage generation of underrepresented patterns could improve LLM code generation capabilities.

---

*End of Report*
