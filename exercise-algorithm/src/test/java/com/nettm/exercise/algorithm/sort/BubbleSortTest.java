package com.nettm.exercise.algorithm.sort;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class BubbleSortTest {

    private BubbleSort bs = new BubbleSort();

    @Test
    public void sort1() {
        int[] result1 = bs.sort(SortData.num1);
        assertEquals(result1, SortData.num1_sort);

        int[] result2 = bs.sort(SortData.num2);
        assertEquals(result2, SortData.num2_sort);

        int[] result3 = bs.sort(SortData.num3);
        assertEquals(result3, SortData.num3_sort);

        int[] result4 = bs.sort(SortData.num4);
        assertEquals(result4, SortData.num4_sort);

        int[] result5 = bs.sort(SortData.num5);
        assertEquals(result5, SortData.num5_sort);

        int[] result6 = bs.sort(SortData.num6);
        assertEquals(result6, SortData.num6_sort);
    }
}
