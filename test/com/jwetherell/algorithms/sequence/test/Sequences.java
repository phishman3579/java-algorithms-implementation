package com.jwetherell.algorithms.sequence.test;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.jwetherell.algorithms.sequence.FibonacciSequence;
import com.jwetherell.algorithms.sequence.LongestCommonSubsequence;
import com.jwetherell.algorithms.sequence.TotalOfSequence;

public class Sequences {

    @Test
    public void testSequenceTotal() {
        // TOTAL OF A SEQUENCE OF NUMBERS
        int start = 14;
        int length = 10000;
        int check = 50135000;
        long result = TotalOfSequence.sequenceTotalUsingLoop(start, length);
        assertTrue("Sequence Total Using Loop error. result="+result+" check="+check, (result==check));

        result = TotalOfSequence.sequenceTotalUsingTriangularNumbers(start, length);
        assertTrue("Sequence Total Using Triangular Numbers error. result="+result+" check="+check, (result==check));
    }

    @Test
    public void testFibonacci() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 25;
        int check = 75025;
        long result = FibonacciSequence.fibonacciSequenceUsingLoop(element);
        assertTrue("Fibonacci Sequence Using Loop error. result="+result+" check="+check, (result==check));

        result = FibonacciSequence.fibonacciSequenceUsingRecursion(element);
        assertTrue("Fibonacci Sequence Using Recursion error. result="+result+" check="+check, (result==check));

        result = FibonacciSequence.fibonacciSequenceUsingMatrixMultiplication(element);
        assertTrue("Fibonacci Sequence Using Matrix error. result="+result+" check="+check, (result==check));

        result = FibonacciSequence.fibonacciSequenceUsingBinetsFormula(element);
        assertTrue("Fibonacci Sequence Using Binet's formula error. result="+result+" check="+check, (result==check));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFibonacciLoopExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingLoop(element);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFibonacciRecursionExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingRecursion(element);
   }

    @Test(expected=IllegalArgumentException.class)
    public void testFibonacciMatrixExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingMatrixMultiplication(element);
    }

    @Test(expected=IllegalArgumentException.class)
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
        char[] seq1 = new char[] { 'G', 'A', 'C' };
        char[] seq2 = new char[] { 'A', 'G', 'C', 'A', 'T' };
        LongestCommonSubsequence.MatrixPair pair = LongestCommonSubsequence.getLCS(seq1, seq2);
        assertTrue("Longest common subsequence error. "+
                   "resultLength="+resultLength+" seqLength="+pair.getLongestSequenceLength()+" "+
                   "resultSequence="+resultSequence+" sequence="+pair.getLongestSequences(), 
                   (resultLength==pair.getLongestSequenceLength() && 
                    resultSequence.equals(pair.getLongestSequences())));

        resultLength = 3;
        resultSequence.clear();
        resultSequence.add("GAX");
        resultSequence.add("ACT");
        resultSequence.add("GCT");
        resultSequence.add("GAT");
        resultSequence.add("ACX");
        resultSequence.add("GCX");
        seq1 = new char[] { 'G', 'A', 'C', 'V', 'X', 'T' };
        seq2 = new char[] { 'A', 'G', 'C', 'A', 'T', 'X' };
        pair = LongestCommonSubsequence.getLCS(seq1, seq2);
        assertTrue("Longest common subsequence error. "+
                   "resultLength="+resultLength+" seqLength="+pair.getLongestSequenceLength()+" "+
                   "resultSequence="+resultSequence+" sequence="+pair.getLongestSequences(), 
                   (resultLength==pair.getLongestSequenceLength() && 
                    resultSequence.equals(pair.getLongestSequences())));
    }
}
