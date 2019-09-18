 
package com.jwetherell.algorithms.sorts.testQuickSort;

import static org.junit.Assert.*;

import com.jwetherell.algorithms.sorts.QuickSort;

public class Test {

	@Test
	public void testQuickSortII() {
		Quicksort test = new QuickSort();
		int[] test1 = {3,99,2,4},equal1 = {2,3,4,99}, test2 = {4,3,2,1}, equal2 = {1,2,3,4};
		int[] result1 = test.quickSort(test1,0,3), result2 = test.quickSort(test2,0,3);
		assertArrayEquals(result1, equal1);
		assertArrayEquals(result2, equal2);
	}
}
