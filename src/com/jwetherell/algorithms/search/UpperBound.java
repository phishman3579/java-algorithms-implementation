package com.jwetherell.algorithms.search;

/**
 * Upper bound search algorithm.<br>
 * Upper bound is kind of binary search algorithm but:<br>
 * -It returns index of first element which is grater than searched value.<br>
 * -If searched element is bigger than any array element function returns first index after last element.<br>
 * <br>
 * Behaviour for unsorted arrays is unspecified.
 * <p>
 * Complexity O(log n).
 * <br>
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class UpperBound {

    private UpperBound() { }

    public static int upperBound(int[] array, int length, int value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value >= array[mid]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
