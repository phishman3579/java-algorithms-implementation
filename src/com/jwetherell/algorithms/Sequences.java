package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.sequence.FibonacciSequence;
import com.jwetherell.algorithms.sequence.LongestCommonSubsequence;
import com.jwetherell.algorithms.sequence.TotalOfSequence;


public class Sequences {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        {
            // TOTAL OF A SEQUENCE OF NUMBERS
            int start = 14;
            int length = 10000;
            System.out.println("Computing sequence total using a loop.");
            long before = System.nanoTime();
            long result = TotalOfSequence.sequenceTotalUsingLoop(start, length);
            long after = System.nanoTime();
            System.out.println("start=" + start + " length=" + length + " result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing sequence total using algorithm.");
            before = System.nanoTime();
            result = TotalOfSequence.sequenceTotalUsingTriangularNumbers(start, length);
            after = System.nanoTime();
            System.out.println("start=" + start + " length=" + length + " result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            // COMPUTE FIBONACCI SEQUENCE
            System.out.println("Computing Fibonacci sequence total using a loop.");
            int element = 25;
            long before = System.nanoTime();
            long result = FibonacciSequence.fibonacciSequenceUsingLoop(element);
            long after = System.nanoTime();
            System.out.println("element=" + element + " result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing Fibonacci sequence total using Recursion.");
            before = System.nanoTime();
            result = FibonacciSequence.fibonacciSequenceUsingRecursion(element);
            after = System.nanoTime();
            System.out.println("element=" + element + " result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing Fibonacci sequence total using Matrix.");
            before = System.nanoTime();
            result = FibonacciSequence.fibonacciSequenceUsingMatrixMultiplication(element);
            after = System.nanoTime();
            System.out.println("element=" + element + " result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing Fibonacci sequence total using Binet's formula.");
            before = System.nanoTime();
            result = FibonacciSequence.fibonacciSequenceUsingBinetsFormula(element);
            after = System.nanoTime();
            System.out.println("element=" + element + " result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            // LONGEST COMMON SUBSEQUENCE
            System.out.println("Computing longest common subsequence.");
            char[] seq1 = new char[] { 'G', 'A', 'C' };
            char[] seq2 = new char[] { 'A', 'G', 'C', 'A', 'T' };
            LongestCommonSubsequence.MatrixPair pair = LongestCommonSubsequence.getLCS(seq1, seq2);
            System.out.println(pair.getLengthMatrixString());
            System.out.println(pair.getSequenceMatrixString());
            System.out.println("Longest sequence length = " + pair.getLongestSequenceLength());
            System.out.println("Longest sequences = " + pair.getLongestSequences());
            System.out.println();

            seq1 = new char[] { 'G', 'A', 'C', 'V', 'X', 'T' };
            seq2 = new char[] { 'A', 'G', 'C', 'A', 'T', 'X' };
            pair = LongestCommonSubsequence.getLCS(seq1, seq2);
            System.out.println(pair.getLengthMatrixString());
            System.out.println(pair.getSequenceMatrixString());
            System.out.println("Longest sequence length = " + pair.getLongestSequenceLength());
            System.out.println("Longest sequences = " + pair.getLongestSequences());
            System.out.println();
        }
    }
}
