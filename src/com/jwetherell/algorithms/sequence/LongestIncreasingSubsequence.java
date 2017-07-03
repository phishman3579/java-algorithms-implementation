package com.jwetherell.algorithms.sequence;

import com.jwetherell.algorithms.search.LowerBound;

import java.util.Arrays;

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
    public static int[] getLongestIncreasingSubsequence(int[] sequence) {
        final int[] resultSequence = new int[sequence.length];

        int resultLength = 0;
        for (int i = 0; i < sequence.length; ++i) {
            // try to find the best place for new element in sorted result
            final int pos = LowerBound.lowerBound(resultSequence, resultLength, sequence[i]); //O(log n)
            // if the best place is at the end increase result length
            if (pos >= resultLength)
                resultLength++;
            resultSequence[pos] = sequence[i];
        }

        return Arrays.copyOfRange(resultSequence, 0, resultLength);
    }
}
