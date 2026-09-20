package org.example;

import java.util.Random;

public class QuickSort {

    private static final Random random = new Random();

    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }

        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int left, int right) {
        while (left < right) {
            int pivot = a[random.nextInt(left, right + 1)];

            int[] middle = partition(a, left, right, pivot);

            int leftSize = middle[0] - left;
            int rightSize = right - middle[1];

            //recursivno sortiruem snachala menshuu chast
            //potom sdvigaem granitsu chtoby otsortirovat bolshuu chast
            if (leftSize < rightSize) {
                sort(a, left, middle[0] - 1);
                left = middle[1] + 1;
            } else {
                sort(a, middle[1] + 1, right);
                right = middle[0] - 1;
            }
        }
    }

    private static int[] partition(int[] a, int left, int right, int pivot) {
        int i = left;
        // less/greater  than pivot
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