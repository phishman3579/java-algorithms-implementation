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
 * NOTE: r is the range of numbers (0 to r) to be sorted.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class CountingSort {
    private static int[] unsorted = null;
    private static int[] counts = null;
    
    private CountingSort() { }
    
    public static int[] sort(int[] unsorted) {
        CountingSort.unsorted = unsorted;
        
        int maxValue = findMax(CountingSort.unsorted);
        counts = new int[maxValue+1];
        updateCounts();
        populateCounts();
        
        try {
            return CountingSort.unsorted;
        } finally {
            CountingSort.unsorted = null;
        }
    }
    
    private static int findMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int i : array) {
            if (i>max) max=i;
        }
        return max;
    }
    
    private static void updateCounts() {
        for (int e : unsorted) counts[e]++;
    }
    
    private static void populateCounts() {
        int index=0;
        for (int i=0; i<counts.length; i++) {
            int e = counts[i];
            while (e>0) {
                unsorted[index++] = i;
                e--;
            }
        }
    }
}
