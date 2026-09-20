package org.example;

import java.util.Random;

public class QuickSelect {

    private static final Random random = new Random();

    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k is out of range");
        }

        int left = 0;
        int right = a.length - 1;

        while (left <= right) {
            int pivot = a[random.nextInt(left, right + 1)];

            int[] middle = partition(a, left, right, pivot);

            if (k < middle[0]) {
                right = middle[0] - 1;
            } else if (k > middle[1]) {
                left = middle[1] + 1;
            } else {
                return a[k];
            }
        }

        throw new IllegalStateException();
    }

    private static int[] partition(int[] a, int left, int right, int pivot) {
        int i = left;
        int lt = left;
        int gt = right;

        while (i <= gt) {
            if (a[i] < pivot) {
                swap(a, i, lt);
                i++;
                lt++;
            } else if (a[i] > pivot) {
                swap(a, i, gt);
                gt--;
            } else {
                i++;
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