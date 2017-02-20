package com.jwetherell.algorithms.search;

/**
 * Lower bound search algorithm.
 * Lower bound is kind of binary search algorithm but:
 * -If searched element doesn't exist function returns index of first element which is bigger than searched value.
 * -If searched element is bigger than any array element function returns first index after last element.
 * -If searched element is lower than any array element function returns index of first element.
 * -If there are many values equals searched value function returns first occurrence.
 * Behaviour for unsorted arrays is unspecified.
 * Complexity O(log n).
 *
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LowerBound {

    private LowerBound() { }

    public static int lowerBound(int[] array, int length, int value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value <= array[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}
