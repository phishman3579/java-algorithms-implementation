package com.jwetherell.algorithms.sorts;


/**
 * Counting sort: a non-comparative integer sorting algorithm.
 * Family: Counting.
 * Space: An Array of length r.
 * Stable: True.
 * 
 * Average case = O(n+r)
 * Worst case = O(n+r)
 * Best case = O(n+r)
 * NOTE: r is the range of numbers from 0 ro r to be sorted.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class CountingSort {
    private static int[] unsorted = null;
    private static int[] counts = null;
    
    private CountingSort() { }
    
    public static int[] sort(int[] unsorted, int maxValue) {
        CountingSort.unsorted = unsorted;
        
        counts = new int[maxValue];
        updateCounts();
        populateCounts();
        
        return CountingSort.unsorted;
    }

    
    private static void updateCounts() {
        for (int e : unsorted) counts[e]++;
    }
    
    private static void populateCounts() {
        int index=0;
        for (int i=0; i<counts.length; i++) {
            int e = counts[i];
            if (e>0) unsorted[index++] = i;
        }
    }
}
