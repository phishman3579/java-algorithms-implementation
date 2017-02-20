package com.jwetherell.algorithms.sequence;

import com.jwetherell.algorithms.search.LowerBound;

import java.util.Arrays;

/**
 * Finding longest increasing subsequence. Dynamic programming.
 * <p>
 * https://en.wikipedia.org/wiki/Longest_increasing_subsequence
 *
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() { }

    public static int[] getLongestIncreasingSubsequence(int[] sequence) {
        final int[] resultSequence = new int[sequence.length];

        int resultLength = 0;
        for (int i = 0; i < sequence.length; ++i) {
            // try to find the best place for new element in sorted result
            final int pos = LowerBound.lowerBound(resultSequence, resultLength, sequence[i]);//O(log n)
            // if the best place is at the end increase result length
            if (pos >= resultLength)
                resultLength++;
            resultSequence[pos] = sequence[i];
        }

        return Arrays.copyOfRange(resultSequence, 0, resultLength);
    }
}
