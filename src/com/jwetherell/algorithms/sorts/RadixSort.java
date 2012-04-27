package com.jwetherell.algorithms.sorts;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 * Radix sort is a non-comparative integer sorting algorithm that sorts data with integer keys by grouping keys 
 * by the individual digits which share the same significant position and value. A positional notation is required, 
 * but because integers can represent strings of characters (e.g., names or dates) and specially formatted floating 
 * point numbers, radix sort is not limited to integers.
 * Family: Bucket.
 * Space: 10 Buckets with at most n integers per bucket.
 * Stable: True.
 * 
 * Average case = O(n*k)
 * Worst case = O(n*k)
 * Best case = O(n*k)
 * NOTE: n is the number of digits and k is the average bucket size
 * 
 * http://en.wikipedia.org/wiki/Radix_sort
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RadixSort {
    
    private static final int numberOfBuckets = 10;

    private RadixSort() { }

    @SuppressWarnings("unchecked")
    public static Integer[] sort(Integer[] unsorted) {
        Queue<Integer>[] buckets = new ArrayDeque[numberOfBuckets];
        // 10 for base 10 numbers
        for (int i=0; i<numberOfBuckets; i++) {
            buckets[i] = new ArrayDeque<Integer>();
        }    

        int numberOfDigits = getMaxNumberOfDigits(unsorted); //Max number of digits
        int divisor = 1;
        int digit = 0;
        for (int n=0; n<numberOfDigits; n++) {
            for (int d : unsorted) {
                digit = getDigit(d,divisor);
                buckets[digit].add(d);
            }
            int index = 0;
            for (Queue<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    int integer = bucket.remove();
                    unsorted[index++] = integer;
                }
            }
            divisor *= 10;
        }

        return unsorted;
    }

    private static int getMaxNumberOfDigits(Integer[] unsorted) {
        int max = Integer.MIN_VALUE;
        int temp = 0;
        for (int i : unsorted) {
            temp = (int)Math.log10(i)+1;
            if (temp>max) max=temp;
        }
        return max;
    }
    
    private static int getDigit(int integer, int divisor) {
        return (integer / divisor) % 10;
    }
}
