package com.jwetherell.algorithms.sequence.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.jwetherell.algorithms.sequence.LongestIncreasingSubsequence;
import com.jwetherell.algorithms.sequence.LongestPalindromicSubsequence;

import org.junit.Test;

import com.jwetherell.algorithms.sequence.FibonacciSequence;
import com.jwetherell.algorithms.sequence.LargestSumContiguousSubarray;
import com.jwetherell.algorithms.sequence.LongestCommonSubsequence;
import com.jwetherell.algorithms.sequence.SubsequenceCounter;
import com.jwetherell.algorithms.sequence.ArithmeticProgression;

public class Sequences {

    @Test
    public void testArithmeticProgression() {
        // TOTAL OF A SEQUENCE OF NUMBERS
        int start = 14;
        int length = 10000;
        int check = 50135000;
        long result = ArithmeticProgression.sequenceTotalUsingLoop(start, length);
        assertTrue("Sequence Total Using Loop error. result=" + result + " check=" + check, (result == check));

        result = ArithmeticProgression.sequenceTotalUsingTriangularNumbers(start, length);
        assertTrue("Sequence Total Using Triangular Numbers error. result=" + result + " check=" + check, (result == check));
    }

    @Test
    public void testFibonacci() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 25;
        int check = 75025;
        long result = FibonacciSequence.fibonacciSequenceUsingLoop(element);
        assertTrue("Fibonacci Sequence Using Loop error. result=" + result + " check=" + check, (result == check));

        result = FibonacciSequence.fibonacciSequenceUsingRecursion(element);
        assertTrue("Fibonacci Sequence Using Recursion error. result=" + result + " check=" + check, (result == check));

        result = FibonacciSequence.fibonacciSequenceUsingMatrixMultiplication(element);
        assertTrue("Fibonacci Sequence Using Matrix error. result=" + result + " check=" + check, (result == check));

        result = FibonacciSequence.fibonacciSequenceUsingBinetsFormula(element);
        assertTrue("Fibonacci Sequence Using Binet's formula error. result=" + result + " check=" + check, (result == check));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFibonacciLoopExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingLoop(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFibonacciRecursionExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingRecursion(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFibonacciMatrixExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingMatrixMultiplication(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFibonacciBinetsExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingBinetsFormula(element);
    }

    @Test
    public void testLongestCommonSubSequences() {
        // LONGEST COMMON SUBSEQUENCE
        int resultLength = 2;
        Set<String> resultSequence = new HashSet<String>();
        resultSequence.add("AC");
        resultSequence.add("GC");
        resultSequence.add("GA");
        char[] seq1 = new char[]{'G', 'A', 'C'};
        char[] seq2 = new char[]{'A', 'G', 'C', 'A', 'T'};
        LongestCommonSubsequence.MatrixPair pair = LongestCommonSubsequence.getLCS(seq1, seq2);
        assertTrue("Longest common subsequence error. " +
                        "resultLength=" + resultLength + " seqLength=" + pair.getLongestSequenceLength() + " " +
                        "resultSequence=" + resultSequence + " sequence=" + pair.getLongestSequences(),
                (resultLength == pair.getLongestSequenceLength() &&
                        resultSequence.equals(pair.getLongestSequences())));

        resultLength = 3;
        resultSequence.clear();
        resultSequence.add("GAX");
        resultSequence.add("ACT");
        resultSequence.add("GCT");
        resultSequence.add("GAT");
        resultSequence.add("ACX");
        resultSequence.add("GCX");
        seq1 = new char[]{'G', 'A', 'C', 'V', 'X', 'T'};
        seq2 = new char[]{'A', 'G', 'C', 'A', 'T', 'X'};
        pair = LongestCommonSubsequence.getLCS(seq1, seq2);
        assertTrue("Longest common subsequence error. " +
                        "resultLength=" + resultLength + " seqLength=" + pair.getLongestSequenceLength() + " " +
                        "resultSequence=" + resultSequence + " sequence=" + pair.getLongestSequences(),
                (resultLength == pair.getLongestSequenceLength() &&
                        resultSequence.equals(pair.getLongestSequences())));
    }

    @Test
    public void testLongestIncreasingSubsequence() {
        ArrayList<int[]> sequences = new ArrayList<int[]>();
        ArrayList<int[]> sequencesLis = new ArrayList<int[]>();

        sequences.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        sequencesLis.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

        sequences.add(new int[]{0, 1, 2, 2, 2, 2, 2, 2, 9});
        sequencesLis.add(new int[]{0, 1, 2, 9});

        sequences.add(new int[]{0, 1, 2, 2, 2, 2, 2, 2, 2, 9});
        sequencesLis.add(new int[]{0, 1, 2, 9});

        sequences.add(new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7});
        sequencesLis.add(new int[]{7});

        sequences.add(new int[]{8});
        sequencesLis.add(new int[]{8});

        sequences.add(new int[]{172, 191, 179, 185, 188});
        sequencesLis.add(new int[]{172, 179, 185, 188});

        sequences.add(new int[]{1, 2, 3, 1, 2, 3, 1, 2, 3});
        sequencesLis.add(new int[]{1, 2, 3});

        sequences.add(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15});
        sequencesLis.add(new int[]{0, 2, 6, 9, 11, 15});

        assertTrue("Longest increasing subsequence error. Sequences size=" + sequences.size() + " SequencesList size:" + sequencesLis.size(), sequences.size() == sequencesLis.size());

        for (int i = 0; i < sequences.size(); ++i) {
            int[] resultSequence = LongestIncreasingSubsequence.getLongestIncreasingSubsequence(sequences.get(i));
            assertTrue("Longest increasing subsequence error. Expected subsequence=" + Arrays.toString(sequencesLis.get(i)) + " result subsequence=" + Arrays.toString(resultSequence), Arrays.equals(resultSequence, sequencesLis.get(i)));
        }
    }

    @Test
    public void testSubsequenceCount() {
        final String a = "GeeksforGeeks";
        final String b = "Gks";
        final int result = SubsequenceCounter.getCount(a.toCharArray(), b.toCharArray());
        assertTrue("Subsequence Counter, expected="+4+" result="+result, result==4);
    }

    @Test
    public void testLargestSumContiguousSubarray() {
        final int result = LargestSumContiguousSubarray.getLargestSumContiguousSubarray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        assertTrue("Largest Sum of Contiguous Subarray, expected="+6+" result="+result, result==6);
        final int result1 = LargestSumContiguousSubarray.getLargestSumContiguousSubarray(new int[]{-2, -1, -3, -4, -1, -2, -10, -5, -4});
        assertTrue("Largest Sum of Contiguous Subarray, expected="+-1+" result1="+result1, result1==-1);
    }

    @Test
    public void testLongestPalindromicSubsequence() {
        final int result = LongestPalindromicSubsequence.getLongestPalindromeSubsequence("AABCDEBAZ");
        assertTrue("Longest Palindromic Subsequence, expected="+5+" result="+result, result==5);
        final int result1 = LongestPalindromicSubsequence.getLongestPalindromeSubsequence("subsequence");
        assertTrue("Longest Palindromic Subsequence, expected="+3+" result1="+result1, result1==3);
    }
    
}
