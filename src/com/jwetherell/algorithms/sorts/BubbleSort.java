package com.jwetherell.algorithms.sorts;


/**
 * Bubble sort.
 * Family: Exchanging.
 * Space: In-place.
 * Stable: True.
 * 
 * Average case = O(n^2)
 * Worst case = O(n^2)
 * Best case = O(n)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class BubbleSort {
    private static int[] unsorted = null;

    private BubbleSort() { }
    
    public static int[] sort(int[] unsorted) {
        BubbleSort.unsorted = unsorted;

        boolean swapped = true;
        int length = BubbleSort.unsorted.length;
        while (swapped) {
            swapped = false;
            for (int i=1; i<length; i++) {
                if (BubbleSort.unsorted[i] < BubbleSort.unsorted[i-1]) {
                    swap(i,i-1);
                    swapped = true;
                }
            }
            length--;
        }
        
        return BubbleSort.unsorted;
    }
    
    private static void swap(int index1, int index2) {
        int value = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = value;
    }
}
