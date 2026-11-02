# DAA Assignment 4 — Dynamic Programming and String Algorithms

## Project Overview

This project implements and compares five algorithms for Edit Distance and String Search as part of Design and Analysis of Algorithms.

The experiment focuses on execution time, operation counts, and algorithm performance on different input types.

## Implemented Algorithms

**Edit Distance**
- Recursive Edit Distance
- Table Edit Distance (Dynamic Programming)
- Two Rows Edit Distance (Space Optimization)

**String Search**
- Naive String Search
- Knuth-Morris-Pratt (KMP) with LPS

## Technologies

- Java 25
- Maven
- JUnit 5
- Python 3
- Matplotlib

## How to Run

**Run all tests:**

```bash
mvn clean test
```

**Run the experiments:**

Open `ExperimentRunner.java` in IntelliJ IDEA and run its `main()` method.

The experiment saves measurements to `results/results.csv`.

**Generate the plots:**

```bash
python plot_results.py
```

Three PNG charts will be generated in `results/plots/`.

## Experimental Results

The experiments compare algorithm performance across different string lengths and input types.

- Recursive Edit Distance becomes expensive on difficult inputs.
- Dynamic programming avoids repeated recursive calculations.
- Two Rows reduces memory usage compared with the full table.
- KMP performs fewer character comparisons than Naive Search on the selected worst-case inputs.

See [REPORT.md](REPORT.md) for the experimental design, graphs, and conclusions.

## AI Use Acknowledgement

I used ChatGPT to assist with code implementation, understanding algorithms, debugging errors, and organizing the experiments and report. I ran the code, checked the tests, and reviewed the experimental results with AI assistance.

## Author

Aruzhan Marat

SE-2525 — Astana IT University