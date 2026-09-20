import org.example.MergeSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

    @Test
    void testEmptyArray() {
        int[] a = {};
        MergeSort.sort(a);

        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void testOneElement() {
        int[] a = {5};
        MergeSort.sort(a);

        assertArrayEquals(new int[]{5}, a);
    }

    @Test
    void testAlreadySorted() {
        int[] a = {1, 2, 3, 4, 5};
        MergeSort.sort(a);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    void testAllEqual() {
        int[] a = {7, 7, 7, 7, 7};
        MergeSort.sort(a);

        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, a);
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
            MergeSort.sort(a);

            assertArrayEquals(expected, a);
        }
    }
}