package com.jwetherell.algorithms.sorts;


/**
 * Merge sort.
 * Family: Merging.
 * Space: In-place.
 * Stable: True.
 * 
 * Average case = O(n*log n)
 * Worst case = O(n*log n)
 * Best case = O(n*log n)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class MergeSort {
    private static int[] unsorted = null;    

    private MergeSort() { }
    
    public static int[] sort(int[] unsorted) {
        MergeSort.unsorted = unsorted;
        
        sort(0, MergeSort.unsorted.length);

        return MergeSort.unsorted;
    }
    
    private static void sort(int start, int length) {
        if (length>2) {
            int aLength = (int)Math.floor(length/2);
            int bLength = length-aLength;
            
            sort(start, aLength);
            sort(start+aLength, bLength);
            merge(start, aLength, start+aLength, bLength);
        } else if (length==2) {
            int e = unsorted[start+1];
            if (e < unsorted[start]) {
                unsorted[start+1] = unsorted[start];
                unsorted[start] = e;
            }
        }
    }
    
    private static void merge(int aStart, int aLength, int bStart, int bLength) {
        int count = 0;
        int[] output = new int[aLength+bLength];
        int i=aStart;
        int j=bStart;
        while (i<aStart+aLength || j<bStart+bLength) {
            int a = (i<(aStart+aLength))?unsorted[i]:Integer.MAX_VALUE;
            int b = (j<(bStart+bLength))?unsorted[j]:Integer.MAX_VALUE;
            if (b < a) {
                output[count++] = b;
                j++;
            } else {
                output[count++] = a;
                i++;
            }
        }
        int x=0;
        for (int y=aStart; y<(aStart+aLength+bLength); y++) {
            unsorted[y]=output[x++];
        }
    }
}
