package com.nettm.exercise.algorithm.sort;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class BubbleSortTest {

	private BubbleSort bs = new BubbleSort();

	@Test
	public void sort1() {
		int[] result = bs.sort(SortData.num1);
		assertEquals(result, SortData.num1_sort);
	}
}
