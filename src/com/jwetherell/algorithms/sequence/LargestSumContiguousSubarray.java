package com.jwetherell.algorithms.sequence;

/**
 * Given an array of integers, we want to find the largest sum of contiguous
 * subarray.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_subarray_problem">Maximum Subarray Problem (Wikipedia)</a>
 * <br>
 * @author Miguel Stephane KAKANAKOU <Skakanakou@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LargestSumContiguousSubarray {

    private LargestSumContiguousSubarray() { }

    /**
     * Largest sum of contiguous subarray using Kadane's algorithm.
     * 
     * @param A
     *            the given Array of integer
     * @return
     */
    public static int getLargestSumContiguousSubarray(int[] A) {
        if (A == null)
            throw new NullPointerException("The given array is null");

        int max_so_far = A[0];
        int max_ending_here = A[0];
        for (int i = 1; i < A.length; i++) {
            max_ending_here = Math.max(A[i], max_ending_here + A[i]);
            max_so_far = Math.max(max_so_far, max_ending_here);
        }
        return max_so_far;
    }

}
