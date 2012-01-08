package com.jwetherell.algorithms.sorts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Radix sort: a non-comparative integer sorting algorithm.
 * Family: Bucket.
 * Space: 2 Buckets with at most n integers per bucket.
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
    private static final int BUCKET_SIZE = 32;

    private static final int[] masks = new int[BUCKET_SIZE];
    static {
        for (int i=0; i<BUCKET_SIZE; i++) {
            masks[i] = (int)Math.pow(2, i);
        }
    }
    
    private static final List<Queue<Integer>> buckets = new ArrayList<Queue<Integer>>();
    static {
        // 2 for binary
        for (int i=0; i<2; i++) {
            Queue<Integer> bucket = new LinkedList<Integer>();
            buckets.add(i, bucket);
        }    
    }
    
    private static int[] unsorted = null;

    private RadixSort() { }

    public static int[] sort(int[] unsorted) {
        RadixSort.unsorted = unsorted;

        int numberOfDigits = getNumberOfDigits(); //Max number of digits
        int digit = 0;
        for (int n=0; n<numberOfDigits; n++) {
            for (int d : RadixSort.unsorted) {
                digit = getDigitAt(d,n);
                buckets.get(digit).add(d);
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

    private static int getNumberOfDigits() {
        int max = Integer.MIN_VALUE;
        int temp = 0;
        for (int i : RadixSort.unsorted) {
            temp = (int)(Math.log10(i)/Math.log10(2))+1;
            if (temp>max) max=temp;
        }
        return max;
    }
    
    private static int getDigitAt(int integer, int pos) {
        int i = integer & masks[pos];
        i = i >> pos;
        return i;
    }
}
