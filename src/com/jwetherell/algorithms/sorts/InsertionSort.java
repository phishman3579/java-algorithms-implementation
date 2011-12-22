package com.jwetherell.algorithms.sorts;


/**
 * Insertion sort.
 * Family: Insertion.
 * Space: In-place.
 * Stable: True.
 * 
 * Average case = O(n^2)
 * Worst case = O(n^2)
 * Best case = O(n)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class InsertionSort {
    private static int[] unsorted = null;

    private InsertionSort() { }
    
    public static int[] sort(int[] unsorted) {
        InsertionSort.unsorted = unsorted;
        int length = InsertionSort.unsorted.length;
        
        for (int i=1; i<length; i++) {
            sort(i);
        }

        return InsertionSort.unsorted;
    }
    
    private static void sort(int i) {
        for (int j=i; j>0; j--) {
            if (unsorted[j] < unsorted[j-1]) {
                int a = unsorted[j-1];
                unsorted[j-1]=unsorted[j];
                unsorted[j]=a;
            } else {
                break;
            }
        }
    }
}
