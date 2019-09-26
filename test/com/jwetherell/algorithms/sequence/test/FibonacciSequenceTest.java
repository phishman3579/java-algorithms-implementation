package com.jwetherell.algorithms.sequence.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.jwetherell.algorithms.sequence.FibonacciSequence;

public class FibonacciSequenceTest {

    @Test
    public void testFibonacci() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 25;
        int check = 75025;
        long result = FibonacciSequence.fibonacciSequenceUsingLoop(element);
        assertTrue("Fibonacci Sequence Using Loop error. result=" + result + " check=" + check, (result == check));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFibonacciLoopExceptions() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 93;
        FibonacciSequence.fibonacciSequenceUsingLoop(element);
    }
     
    @Test
    public void testFibonacci1stElement() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 0;
	int check = 1;
        long result = FibonacciSequence.fibonacciSequenceUsingLoop(element);
        assertTrue("Fibonacci Sequence Using Loop error. result=" + result + " check=" + check, (result == check));
    }

    @Test
    public void testFibonacci2ndElement() {
        // COMPUTE FIBONACCI SEQUENCE
        int element = 1;
	int check = 1;
        long result = FibonacciSequence.fibonacciSequenceUsingLoop(element);
        assertTrue("Fibonacci Sequence Using Loop error. result=" + result + " check=" + check, (result == check));
    }
}
