package com.jwetherell.algorithms.search;

import java.util.Random;

/**
 * In computer science, quickselect is a selection algorithm to find the k-th smallest element in an unordered list. It is related to the quicksort sorting algorithm.
 * <p>
 * Worst-case performance  О(n2)<br>
 * Best-case performance   О(n)<br>
 * Average performance     O(n)<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Quickselect">Quickselect (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class QuickSelect {

    private static final Random RANDOM = new Random();

    private static int[] unsorted = null;
    private static int[] temp = null;

    public static final int find(int value, int[] array) {
        unsorted = array;
        temp = new int[unsorted.length];
        try {
            int tempLength = unsorted.length;
            int length = tempLength;
            int pivot = unsorted[0];
            while (length > 0) {
                length = tempLength;
                pivot = unsorted[RANDOM.nextInt(length)];
                tempLength = 0;
                for (int i = 0; i < length; i++) {
                    int iValue = unsorted[i];
                    if (value == iValue)
                        return i;
                    else if (value > pivot && iValue > pivot)
                        temp[tempLength++] = iValue;
                    else if (value < pivot && iValue < pivot)
                        temp[tempLength++] = iValue;
                }
                unsorted = temp;
                length = tempLength;
            }
            return Integer.MAX_VALUE;
        } finally {
            QuickSelect.unsorted = null;
            QuickSelect.temp = null;
        }
    }
}
