package com.jwetherell.algorithms.sorts;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class BubbleSortTest2 {
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
    public void testRandomBubbleSorts() {
        // Bubble sort
        Integer[] result = BubbleSort.sort(unsorted.clone());
        assertTrue("Bubble sort unsorted error. result="+print(result), check(result));

    }
    @Test
    public void testBubbleSorts() {
        // Bubble sort
        Integer[] result = BubbleSort.sort(unsorted.clone());
        result = BubbleSort.sort(sorted.clone());
        assertTrue("Bubble sort sorted error. result="+print(result), check(result));
    }
    @Test
    public void testBubbleSort() {
        // Bubble sort
        Integer[] result = BubbleSort.sort(unsorted.clone());
        result = BubbleSort.sort(reverse.clone());
        assertTrue("Bubble sort reverse error. result="+print(result), check(result));
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