import csv
import statistics
from collections import defaultdict
from pathlib import Path
import matplotlib.pyplot as plt

results = defaultdict(list)

with open("results/results.csv", newline="") as file:
    reader = csv.DictReader(file)

    for row in reader:
        key = (
            row["algorithm"],
            row["input"],
            int(row["n"])
        )
        results[key].append({
            "time": float(row["time_ms"]),
            "operations": int(row["operations"])
        })

def median(algorithm, input_type, n, metric):
    values = [
        row[metric]
        for row in results[(algorithm, input_type, n)]
    ]
    return statistics.median(values)

output = Path("results/plots")
output.mkdir(parents=True, exist_ok=True)

sizes = [2, 4, 6, 8]

for input_type in ["identical", "different"]:
    values = [
        median("recursive", input_type, n, "operations")
        for n in sizes
    ]
    plt.plot(sizes, values, marker="o", label=input_type)

plt.yscale("log")
plt.title("Recursive Edit Distance: Operation Growth")
plt.xlabel("String length (characters)")
plt.ylabel("Recursive calls (log scale)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(output / "recursive_growth.png", dpi=200)
plt.close()

sizes = [100, 200, 400, 800]

for algorithm in ["table", "two_rows"]:
    values = [
        median(algorithm, "different", n, "time")
        for n in sizes
    ]
    plt.plot(sizes, values, marker="o", label=algorithm)

plt.title("Dynamic Programming: Runtime Comparison")
plt.xlabel("String length (characters)")
plt.ylabel("Median execution time (ms)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(output / "dp_comparison.png", dpi=200)
plt.close()

sizes = [10000, 20000, 40000, 80000]

for algorithm in ["naive", "kmp"]:
    values = [
        median(algorithm, "worst_case", n, "operations")
        for n in sizes
    ]
    plt.plot(sizes, values, marker="o", label=algorithm)

plt.title("String Search: Worst-Case Comparisons")
plt.xlabel("Text length (characters)")
plt.ylabel("Character comparisons (count)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(output / "search_comparison.png", dpi=200)
plt.close()

print("Total measured runs:", sum(map(len, results.values())))
print("All 3 plots saved to results/plots/")
