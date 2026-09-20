import org.example.Metrics;
import org.example.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {

    @Test
    void testEmptyArray() {
        int[] a = {};

        QuickSort.sort(a);

        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void testOneElement() {
        int[] a = {5};

        QuickSort.sort(a);

        assertArrayEquals(new int[]{5}, a);
    }

    @Test
    void testAlreadySorted() {
        int[] a = {1, 2, 3, 4, 5, 6};

        QuickSort.sort(a);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, a);
    }

    @Test
    void testAllEqual() {
        int[] a = {7, 7, 7, 7, 7};

        QuickSort.sort(a);

        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, a);
    }

    @Test
    void testDuplicates() {
        int[] a = {3, 1, 3, 2, 3, 1, 2, 3};

        QuickSort.sort(a);

        assertArrayEquals(new int[]{1, 1, 2, 2, 3, 3, 3, 3}, a);
    }

    @Test
    void testRandomArrays() {
        Random random = new Random(1);

        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(1000);

            int[] a = new int[size];

            for (int i = 0; i < size; i++) {
                a[i] = random.nextInt(-10000,10000 );
            }

            int[] expected = a.clone();

            Arrays.sort(expected);
            QuickSort.sort(a);

            assertArrayEquals(expected, a);
        }
    }

    @Test
    void testRecursionDepth() {
        int n = 100000;
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        Metrics metrics = new Metrics();

        QuickSort.sort(a, metrics);

        double limit = 2 * (Math.log(n) / Math.log(2));

        assertTrue(metrics.getMaxDepth() <= limit);
    }
}