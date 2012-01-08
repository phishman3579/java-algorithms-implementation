package com.jwetherell.algorithms.sorts;

import java.util.Random;


/**
 * Quick sort of either randomized or length/2 pivot.
 * Family: Divide and conquer.
 * Space: In-place.
 * Stable: False.
 * 
 * Average case = O(n)
 * Worst case = O(n^2)
 * Best case = O(n*log n)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class QuickSort {
    private static final Random RANDOM = new Random();
    
    private static int[] unsorted = null;

    public static enum PIVOT_TYPE { FIRST, MIDDLE, RANDOM };
    public static PIVOT_TYPE type = PIVOT_TYPE.RANDOM;
    
    private QuickSort() { }
    
    public static int[] sort(PIVOT_TYPE type, int[] unsorted) {
        QuickSort.unsorted = unsorted;
        
        int pivot = getRandom(QuickSort.unsorted.length);
        sort(pivot, 0, QuickSort.unsorted.length-1);

        return QuickSort.unsorted;
    }

    private static void sort(int pivotIndex, int start, int finish) {
        pivotIndex = start+pivotIndex;
        int pivotValue = unsorted[pivotIndex];
        int s = start;
        int f = finish;
        while (s <= f) {
            while (unsorted[s] < pivotValue) s++;
            while (unsorted[f] > pivotValue) f--;
            if (s <= f) {
                swap(s,f);
                s++;
                f--;
            }
        }
        if (start < f) {
            pivotIndex = getRandom((f-start)+1);
            sort(pivotIndex, start, f);
        }
        if (s < finish) {
            pivotIndex = getRandom((finish-s)+1);
            sort(pivotIndex, s, finish);
        }
    }

    private static final int getRandom(int length) {
        if (type==PIVOT_TYPE.RANDOM && length>0) return RANDOM.nextInt(length);
        if (type==PIVOT_TYPE.FIRST && length>0) return 0;
        else return length/2;
    }
    
    private static void swap(int index1, int index2) {
        int parent = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = parent;
    }
}
