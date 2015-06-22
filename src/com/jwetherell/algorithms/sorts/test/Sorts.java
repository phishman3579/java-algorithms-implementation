package com.jwetherell.algorithms.sorts.test;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.jwetherell.algorithms.sorts.AmericanFlagSort;
import com.jwetherell.algorithms.sorts.BubbleSort;
import com.jwetherell.algorithms.sorts.CountingSort;
import com.jwetherell.algorithms.sorts.HeapSort;
import com.jwetherell.algorithms.sorts.InsertionSort;
import com.jwetherell.algorithms.sorts.MergeSort;
import com.jwetherell.algorithms.sorts.QuickSort;
import com.jwetherell.algorithms.sorts.RadixSort;
import com.jwetherell.algorithms.sorts.ShellSort;

public class Sorts {

    private static final Random RANDOM = new Random();
    private static final int SIZE = 10000;

    private static Integer[] unsorted = null;
    private static Integer[] sorted = null;
    private static Integer[] reverse = null;

    static {
        unsorted = new Integer[SIZE];
        int i = 0;
        while (i < unsorted.length) {
            int j = RANDOM.nextInt(unsorted.length * 10);
            unsorted[i++] = j;
        }

        sorted = new Integer[SIZE];
        for (i = 0; i < sorted.length; i++)
            sorted[i] = i;

        reverse = new Integer[SIZE];
        for (i = (reverse.length - 1); i >= 0; i--)
            reverse[i] = (SIZE - 1) - i;
    }

    @Test
    public void testInsertionSorts() {
        // Insertion sort
        Integer[] result = InsertionSort.sort(unsorted.clone());
        assertTrue("Inerstion sort unsorted error. result="+print(result), check(result));
        result = InsertionSort.sort(sorted.clone());
        assertTrue("Inerstion sort sorted error. result="+print(result), check(result));
        result = InsertionSort.sort(reverse.clone());
        assertTrue("Inerstion sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testBubbleSorts() {
        // Bubble sort
        Integer[] result = BubbleSort.sort(unsorted.clone());
        assertTrue("Bubble sort unsorted error. result="+print(result), check(result));
        result = BubbleSort.sort(sorted.clone());
        assertTrue("Bubble sort sorted error. result="+print(result), check(result));
        result = BubbleSort.sort(reverse.clone());
        assertTrue("Bubble sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testShellsSorts() {
        int[] shells = new int[] { 10, 5, 3, 1 };
        // Shell's sort
        Integer[] result = ShellSort.sort(shells, unsorted.clone());
        assertTrue("Shell's sort unsorted error. result="+print(result), check(result));
        result = ShellSort.sort(shells, sorted.clone());
        assertTrue("Shell's sort sorted error. result="+print(result), check(result));
        result = ShellSort.sort(shells, reverse.clone());
        assertTrue("Shell's sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testMergeSortsInPlace() {
        // Merge sort
        Integer[] result = MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, unsorted.clone());
        assertTrue("Merge sort unsorted error. result="+print(result), check(result));
        result = MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, sorted.clone());
        assertTrue("Merge sort sorted error. result="+print(result), check(result));
        result = MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, reverse.clone());
        assertTrue("merge sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testMergeSortsNotInPlace() {
        // Merge sort
        Integer[] result = MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, unsorted.clone());
        assertTrue("Merge sort unsorted error. result="+print(result), check(result));
        result = MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, sorted.clone());
        assertTrue("Merge sort sorted error. result="+print(result), check(result));
        result = MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, reverse.clone());
        assertTrue("merge sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testQuickSorts() {
        // Quicksort
        Integer[] result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, unsorted.clone());
        assertTrue("Quick sort pivot firt unsorted error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, sorted.clone());
        assertTrue("Quick sort pivot firt sorted error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, reverse.clone());
        assertTrue("Quick sort pivot firt reverse error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, unsorted.clone());
        assertTrue("Quick sort pivot middle unsorted error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, sorted.clone());
        assertTrue("Quick sort pivot middle sorted error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, reverse.clone());
        assertTrue("Quick sort pivot middle reverse error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, unsorted.clone());
        assertTrue("Quick sort pivot random unsorted error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, sorted.clone());
        assertTrue("Quick sort pivot random sorted error. result="+print(result), check(result));
        result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, reverse.clone());
        assertTrue("Quick sort pivot random reverse error. result="+print(result), check(result));
    }

    @Test
    public void testHeapSorts() {
        // Heapsort
        Integer[] result = HeapSort.sort(unsorted.clone());
        assertTrue("Heap sort unsorted error. result="+print(result), check(result));
        result = HeapSort.sort(sorted.clone());
        assertTrue("Heap sort sorted error. result="+print(result), check(result));
        result = HeapSort.sort(reverse.clone());
        assertTrue("Heap sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testCountingSorts() {
        // Counting sort
        Integer[] result = CountingSort.sort(unsorted.clone());
        assertTrue("Counting sort unsorted error. result="+print(result), check(result));
        result = CountingSort.sort(sorted.clone());
        assertTrue("Counting sort sorted error. result="+print(result), check(result));
        result = CountingSort.sort(reverse.clone());
        assertTrue("Counting sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testRadixSorts() {
        // Radix sort
        Integer[] result = RadixSort.sort(unsorted.clone());
        assertTrue("Radix sort unsorted error. result="+print(result), check(result));
        result = RadixSort.sort(sorted.clone());
        assertTrue("Radix sort sorted error. result="+print(result), check(result));
        result = RadixSort.sort(reverse.clone());
        assertTrue("Radix sort reverse error. result="+print(result), check(result));
    }

    @Test
    public void testAmericanFlagSorts() {
        // American Flag sort
        Integer[] result = AmericanFlagSort.sort(unsorted.clone());
        assertTrue("American flag sort unsorted error. result="+print(result), check(result));
        result = AmericanFlagSort.sort(sorted.clone());
        assertTrue("American flag sort sorted error. result="+print(result), check(result));
        result = AmericanFlagSort.sort(reverse.clone());
        assertTrue("American flag sort reverse error. result="+print(result), check(result));
    }

    private static final boolean check(Integer[] array) {
        for (int i = 1; i<array.length; i++) {
            if (array[i-1]>array[i])
                return false;
        }
        return true;
    }

    private static final String print(Integer[] array) {
        return print(array, 0, array.length);
    }

    private static final String print(Integer[] array, int start, int length) {
        final Integer[] clone = array.clone();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i<length; i++) {
            int e = clone[start+i];
            builder.append(e+" ");
        }
        return builder.toString();
    }
}
