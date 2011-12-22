package com.jwetherell.algorithms.sorts;

import java.util.Random;


/**
 * Quick sort of either randomized or length/2 pivot. Divide and conquer.
 * Family: 
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
        int i = start;
        int j = finish;
        while (i <= j) {
            while (unsorted[i] < pivotValue) i++;
            while (unsorted[j] > pivotValue) j--;
            if (i <= j) {
                swap(i,j);
                i++;
                j--;
            }
        }
        if (start < j) {
            int pivot = getRandom((j-start)+1);
            sort(pivot, start, j);
        }
        if (i < finish) {
            int pivot = getRandom((finish-i)+1);
            sort(pivot, i, finish);
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
