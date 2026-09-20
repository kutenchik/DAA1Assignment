package org.example;

public class Metrics {

    private long comparisons;
    private int maxDepth;
    private long startTime;
    private long endTime;

    public void addComparison() {
        comparisons++;
    }

    public void updateDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getTimeMs() {
        return (endTime - startTime) / 1000000.0;
    }
}