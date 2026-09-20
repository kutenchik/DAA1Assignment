package org.example;

public class MergeSort {

    public static void sort(int[] a) {
        sort(a, new Metrics());
    }

    public static void sort(int[] a, Metrics metrics) {
        //esli net massiva ili pustoi/tok 1 element to otsortirovan
        if (a == null || a.length < 2) {
            return;
        }

        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, metrics, 1);
    }

    private static void sort(int[] a, int[] buffer, int left, int right, Metrics metrics, int depth) {
        metrics.updateDepth(depth);

        if (right - left + 1 <= 15) {
            insertionSort(a, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        //popolam delim massiv
        sort(a, buffer, left, mid, metrics, depth + 1);
        sort(a, buffer, mid + 1, right, metrics, depth + 1);

        merge(a, buffer, left, mid, right, metrics);
    }

    private static void merge(int[] a, int[] buffer, int left, int mid, int right, Metrics metrics) {
        //i-idet po levoi polovine , j-po pravoi, k-index kuda poidet chislo
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.addComparison();
            if (a[i] <= a[j]) {
                buffer[k++] = a[i++];
            } else {
                buffer[k++] = a[j++];
            }
        }

        while (i <= mid) {
            buffer[k++] = a[i++];
        }

        while (j <= right) {
            buffer[k++] = a[j++];
        }

        for (i = left; i <= right; i++) {
            a[i] = buffer[i];
        }
    }

    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int value = a[i];
            int j = i - 1;

            while (j >= left) {
                metrics.addComparison();

                if (a[j] <= value) {
                    break;
                }

                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = value;
        }
    }
}