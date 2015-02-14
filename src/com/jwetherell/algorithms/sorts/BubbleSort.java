package com.jwetherell.algorithms.sorts;

/**
 * Bubble sort is a simple sorting algorithm that works by repeatedly stepping
 * through the list to be sorted, comparing each pair of adjacent items and
 * swapping them if they are in the wrong order. The pass through the list is
 * repeated until no swaps are needed, which indicates that the list is sorted.
 * 
 * Family: Exchanging. 
 * Space: In-place. 
 * Stable: True.
 * 
 * Average case = O(n^2) Worst case = O(n^2) Best case = O(n)
 * 
 * http://en.wikipedia.org/wiki/Bubble_sort
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BubbleSort<T extends Comparable<T>> {

    private BubbleSort() { }

    public static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        boolean swapped = true;
        int length = unsorted.length;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < length; i++) {
                if (unsorted[i].compareTo(unsorted[i - 1]) < 0) {
                    swap(i, i - 1, unsorted);
                    swapped = true;
                }
            }
            length--;
        }
        return unsorted;
    }

    private static <T extends Comparable<T>> void swap(int index1, int index2, T[] unsorted) {
        T value = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = value;
    }
}
