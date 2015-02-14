package com.jwetherell.algorithms.sorts;

/**
 * Insertion sort is a simple sorting algorithm: a comparison sort in which the
 * sorted array (or list) is built one entry at a time. It is much less
 * efficient on large lists than more advanced algorithms such as quicksort,
 * heapsort, or merge sort. 
 * 
 * Family: Insertion. 
 * Space: In-place. 
 * Stable: True.
 * 
 * Average case = O(n^2) Worst case = O(n^2) Best case = O(n)
 * 
 * http://en.wikipedia.org/wiki/Insertion_sort
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class InsertionSort<T extends Comparable<T>> {

    private InsertionSort() { }

    public static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        int length = unsorted.length;
        for (int i = 1; i < length; i++) {
            sort(i, unsorted);
        }
        return unsorted;
    }

    private static <T extends Comparable<T>> void sort(int i, T[] unsorted) {
        for (int j = i; j > 0; j--) {
            T jthElement = unsorted[j];
            T jMinusOneElement = unsorted[j - 1];
            if (jthElement.compareTo(jMinusOneElement) < 0) {
                unsorted[j - 1] = jthElement;
                unsorted[j] = jMinusOneElement;
            } else {
                break;
            }
        }
    }
}
