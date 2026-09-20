package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            1000,
            10000,
            100000,
            1000000
    };

    private static final String[] INPUTS = {
            "random",
            "sorted",
            "duplicates"
    };

    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("results.csv");

        writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

        for (String input : INPUTS) {
            for (int n : SIZES) {
                int[] base = createArray(n, input);

                runMergeSort(writer, base, input);
                runQuickSort(writer, base, input);
                runQuickSelect(writer, base, input);
            }
        }

        writer.close();

        System.out.println("results.csv created");
    }

    private static void runMergeSort(FileWriter writer, int[] base,
                                     String input) throws IOException {
        Result[] results = new Result[RUNS];

        for (int i = 0; i < RUNS; i++) {
            int[] a = base.clone();
            Metrics metrics = new Metrics();

            metrics.start();
            MergeSort.sort(a, metrics);
            metrics.stop();

            results[i] = new Result(
                    metrics.getTimeMs(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth()
            );
        }

        Result median = getMedian(results);

        writeResult(
                writer,
                "MergeSort",
                input,
                base.length,
                median
        );
    }

    private static void runQuickSort(FileWriter writer, int[] base,
                                     String input) throws IOException {
        Result[] results = new Result[RUNS];

        for (int i = 0; i < RUNS; i++) {
            int[] a = base.clone();
            Metrics metrics = new Metrics();

            metrics.start();
            QuickSort.sort(a, metrics);
            metrics.stop();

            results[i] = new Result(
                    metrics.getTimeMs(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth()
            );
        }

        Result median = getMedian(results);

        writeResult(
                writer,
                "QuickSort",
                input,
                base.length,
                median
        );
    }

    private static void runQuickSelect(FileWriter writer, int[] base,
                                       String input) throws IOException {
        Result[] results = new Result[RUNS];

        for (int i = 0; i < RUNS; i++) {
            int[] a = base.clone();
            Metrics metrics = new Metrics();

            int k = a.length / 2;

            metrics.start();
            QuickSelect.select(a, k, metrics);
            metrics.stop();

            results[i] = new Result(
                    metrics.getTimeMs(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth()
            );
        }

        Result median = getMedian(results);

        writeResult(
                writer,
                "QuickSelect",
                input,
                base.length,
                median
        );
    }

    private static int[] createArray(int n, String type) {
        Random random = new Random(1);
        int[] a = new int[n];

        if (type.equals("random")) {
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(-1_000_000, 1_000_000);
            }
        }

        if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
        }

        if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(0, 10);
            }
        }

        return a;
    }

    private static Result getMedian(Result[] results) {
        Arrays.sort(results, (a, b) ->
                Double.compare(a.time, b.time));

        return results[RUNS / 2];
    }

    private static void writeResult(FileWriter writer,
                                    String algorithm,
                                    String input,
                                    int n,
                                    Result result) throws IOException {

        writer.write(
                algorithm + "," +
                        input + "," +
                        n + "," +
                        result.time + "," +
                        result.comparisons + "," +
                        result.depth + "\n"
        );
    }

    private static class Result {

        double time;
        long comparisons;
        int depth;

        Result(double time, long comparisons, int depth) {
            this.time = time;
            this.comparisons = comparisons;
            this.depth = depth;
        }
    }
}