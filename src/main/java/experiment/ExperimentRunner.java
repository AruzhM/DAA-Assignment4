package experiment;
import editdistance.RecursiveEditDistance;
import editdistance.TableEditDistance;
import editdistance.TwoRowsEditDistance;
import stringsearch.NaiveSearch;
import stringsearch.KMPSearch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentRunner {

    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("results"));

        List<String> rows = new ArrayList<>();

        rows.add("algorithm,input,n,time_ms,operations,run");

        InputGenerator generator = new InputGenerator(42);

        int[] recursiveSizes = {2, 4, 6, 8};
        int[] dpSizes = {100, 200, 400, 800};
        int[] searchSizes = {10000, 20000, 40000, 80000};

        for (int n : recursiveSizes) {
            RecursiveEditDistance recursive = new RecursiveEditDistance();

            String identical = "a".repeat(n);
            String different = "b".repeat(n);

            benchmark(rows, "recursive", "identical", n,
                    () -> recursive.distance(identical, identical),
                    recursive::getOperations);

            benchmark(rows, "recursive", "different", n,
                    () -> recursive.distance(identical, different),
                    recursive::getOperations);
        }

        for (int n : dpSizes) {
            String identical = "a".repeat(n);
            String different = "b".repeat(n);

            TableEditDistance table = new TableEditDistance();
            TwoRowsEditDistance twoRows = new TwoRowsEditDistance();

            benchmark(rows, "table", "identical", n,
                    () -> table.distance(identical, identical),
                    table::getOperations);

            benchmark(rows, "two_rows", "identical", n,
                    () -> twoRows.distance(identical, identical),
                    twoRows::getOperations);

            benchmark(rows, "table", "different", n,
                    () -> table.distance(identical, different),
                    table::getOperations);

            benchmark(rows, "two_rows", "different", n,
                    () -> twoRows.distance(identical, different),
                    twoRows::getOperations);
        }

        for (int n : searchSizes) {
            String randomText = generator.randomString(n, 4);
            String randomPattern = generator.randomString(10, 4);

            runSearch(rows, "random", randomText, randomPattern, n);

            String worstText = "a".repeat(n);
            String worstPattern = generator.worstCasePattern(10);

            runSearch(rows, "worst_case", worstText, worstPattern, n);
        }

        Files.write(Path.of("results/results.csv"), rows);
        System.out.println("Results saved to results/results.csv");
    }

    private static void runEdit(List<String> rows, String input,
                                String a, String b, int n) {
        RecursiveEditDistance recursive = new RecursiveEditDistance();
        TableEditDistance table = new TableEditDistance();
        TwoRowsEditDistance twoRows = new TwoRowsEditDistance();

        benchmark(rows, "recursive", input, n,
                () -> recursive.distance(a, b),
                recursive::getOperations);

        benchmark(rows, "table", input, n,
                () -> table.distance(a, b),
                table::getOperations);

        benchmark(rows, "two_rows", input, n,
                () -> twoRows.distance(a, b),
                twoRows::getOperations);
    }

    private static void runSearch(List<String> rows, String input,
                                  String text, String pattern, int n) {
        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        benchmark(rows, "naive", input, n,
                () -> naive.search(text, pattern),
                naive::getComparisons);

        benchmark(rows, "kmp", input, n,
                () -> kmp.search(text, pattern),
                kmp::getComparisons);
    }


    private static void benchmark(List<String> rows, String algorithm,
                                  String input, int n,
                                  Runnable action,
                                  java.util.function.LongSupplier counter) {

        for (int i = 0; i < 3; i++) {
            action.run();
        }

        List<Long> times = new ArrayList<>();

        for (int i = 1; i <= RUNS; i++) {
            long start = System.nanoTime();
            action.run();
            long end = System.nanoTime();

            long elapsed = end - start;
            long operations = counter.getAsLong();

            times.add(elapsed);

            rows.add(algorithm + "," + input + "," + n + ","
                    + (elapsed / 1_000_000.0) + ","
                    + operations + "," + i);
        }

        Collections.sort(times);
        double medianMs = times.get(RUNS / 2) / 1_000_000.0;

        System.out.println(algorithm + " | " + input
                + " | n=" + n + " | median=" + medianMs + " ms");
    }

}
