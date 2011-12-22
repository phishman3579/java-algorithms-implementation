package com.jwetherell.algorithms.sorts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Radix sort: a non-comparative integer sorting algorithm.
 * Family: Bucket.
 * Space: 10 Buckets with at most n integers per bucket.
 * Stable: True.
 * 
 * Average case = O(n*k)
 * Worst case = O(n*k)
 * Best case = O(n*k)
 * NOTE: n is the number of keys and k is the average key length
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RadixSort {
    private static int[] unsorted = null;

    private static List<Queue<Integer>> buckets = new ArrayList<Queue<Integer>>();
    
    private RadixSort() { }

    public static int[] sort(int[] unsorted) {
        RadixSort.unsorted = unsorted;

        for (int i=0; i<10; i++) {
            Queue<Integer> bucket = new LinkedList<Integer>();
            buckets.add(i, bucket);
        }
        
        int numberOfDigits = findMaxNumberOfDigits();
        for (int n=0; n<numberOfDigits; n++) {
            for (int i=0; i<RadixSort.unsorted.length; i++) {
                int e = RadixSort.unsorted[i];
                int digit = getDigitAt(e,n);
                buckets.get(digit).add(e);
            }
            int i=0;
            for (Queue<Integer> bucket : buckets) {
                for (int integer : bucket) {
                    RadixSort.unsorted[i++] = integer;
                }
                bucket.clear();
            }
        }

        return RadixSort.unsorted;
    }

    private static int getDigitAt(int integer, int pos) {
        int div = (int)Math.floor(Math.pow(10,pos));
        int mod = (int)Math.floor(Math.pow(10,pos+1));  
        int i = integer % mod;
        if (div>0) i /= div;
        return i;
    }
    
    private static int findMaxNumberOfDigits() {
        int max = Integer.MIN_VALUE;
        for (int e : unsorted) {
            String str = String.valueOf(e);
            int length = str.length();
            if (length>max) max = length;
        }
        return max;
    }
}
