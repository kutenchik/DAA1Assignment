package org.example;

import java.util.Random;

public class QuickSort {

    private static final Random random = new Random();

    public static void sort(int[] a) {
        sort(a, new Metrics());
    }

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) {
            return;
        }

        sort(a, 0, a.length - 1, metrics, 1);
    }

    private static void sort(int[] a, int left, int right, Metrics metrics, int depth) {
        metrics.updateDepth(depth);

        while (left < right) {
            int pivot = a[random.nextInt(left, right + 1)];

            int[] middle = partition(a, left, right, pivot, metrics);

            int leftSize = middle[0] - left;
            int rightSize = right - middle[1];

            //recursivno sortiruem snachala menshuu chast
            //potom sdvigaem granitsu chtoby otsortirovat bolshuu chast
            if (leftSize < rightSize) {
                sort(a, left, middle[0] - 1, metrics, depth + 1);
                left = middle[1] + 1;
            } else {
                sort(a, middle[1] + 1, right, metrics, depth + 1);
                right = middle[0] - 1;
            }
        }
    }

    private static int[] partition(int[] a, int left, int right, int pivot, Metrics metrics) {
        int i = left;
        // less/greater  than pivot
        int lt = left;
        int gt = right;

        while (i <= gt) {
            metrics.addComparison();

            if (a[i] < pivot) {
                swap(a, i, lt);
                i++;
                lt++;
            } else {
                metrics.addComparison();

                if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }
        }

        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}