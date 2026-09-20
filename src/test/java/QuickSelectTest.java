import org.example.QuickSelect;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuickSelectTest {

    @Test
    void testOneElement() {
        int[] a = {5};

        assertEquals(5, QuickSelect.select(a, 0));
    }

    @Test
    void testSortedArray() {
        int[] a = {1, 2, 3, 4, 5};

        assertEquals(1, QuickSelect.select(a, 0));
        assertEquals(3, QuickSelect.select(a, 2));
        assertEquals(5, QuickSelect.select(a, 4));
    }

    @Test
    void testDuplicates() {
        int[] a = {4, 2, 4, 1, 4, 2};

        assertEquals(1, QuickSelect.select(a.clone(), 0));
        assertEquals(2, QuickSelect.select(a.clone(), 2));
        assertEquals(4, QuickSelect.select(a.clone(), 5));
    }

    @Test
    void testRandomArrays() {
        Random random = new Random(1);

        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(1, 1001);
            int[] a = new int[size];

            for (int i = 0; i < size; i++) {
                a[i] = random.nextInt(-10000, 10000);
            }

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int k = random.nextInt(0, size);

            assertEquals(sorted[k], QuickSelect.select(a, k));
        }
    }

    @Test
    void testEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{}, 0)
        );
    }

    @Test
    void testInvalidK() {
        int[] a = {1, 2, 3};

        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(a, -1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(a, 3)
        );
    }
}