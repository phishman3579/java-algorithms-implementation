package com.jwetherell.algorithms.sequence;

/**
 * In computer science, the longest increasing subsequence problem is to find a subsequence of a given sequence in which the subsequence's elements are in sorted order, lowest to highest, and in 
 * which the subsequence is as long as possible. This subsequence is not necessarily contiguous, or unique. 
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Longest_increasing_subsequence">Longest Increasing Subsequence Problem (Wikipedia)</a>
 * <br>
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() { }

    /**
     * Longest increasing subsequence solved using dynamic programming. 
     */
    public static int[] getLongestIncreasingSubsequence(int[] X) {
        final int[] P = new int[X.length];
        final int[] M = new int[X.length+1];
        int L = 0;
        for (int i=0; i<X.length; i++) {
            // Binary search for the largest positive j ≤ L such that X[M[j]] < X[i]
            int lo = 1;
            int hi = L;
            while (lo <= hi) {
                final int mid = (int) Math.ceil((lo + hi) / 2);
                if (X[M[mid]] < X[i])
                    lo = mid+1;
                else
                    hi = mid-1;
            }

            // After searching, lo is 1 greater than the length of the longest prefix of X[i]
            final int newL = lo;

            // The predecessor of X[i] is the last index of the subsequence of length newL-1
            P[i] = M[newL-1];
            M[newL] = i;
            
            if (newL > L) {
                // If we found a subsequence longer than any we've found yet, update L
                L = newL;
            }
        }

        // Reconstruct the longest increasing subsequence
        final int[] S = new int[L];
        int k = M[L];
        for (int i=L-1; i>=0; i--) {
          S[i] = X[k];
          k = P[k];
        }

        return S;
    }
}
