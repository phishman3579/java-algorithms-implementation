package com.jwetherell.algorithms.sequence.test;
package org.junit.runner.notification;
import org.junit.runner.Description.*;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.jwetherell.algorithms.sequence.FibonacciSequence;
import org.junit.Test;

import com.jwetherell.algorithms.sequence.FibonacciSequence;

public class FibonacciTestcase {
    int element;
    int expected;

    @Test(expected = IllegalArgumentException.class)
    public void testFibonacciLoopExceptions() {
        element = 93;
        FibonacciSequence.fibonacciSequenceUsingLoop(element);
    }

    @Test
    public void testFibonacciLoopForVar_r_1st(){
        element = 3;
        expected = 2;assertTrue(FibonacciSequence.fibonacciSequenceUsingLoop(element)==expected);
    }

    @Test
    public void testFibonacciLoopForVar_n_1st(){
        element = -1;
        assertNull(FibonacciSequence.fibonacciSequenceUsingLoop(element));
    }

    @Test
    public void testFibonacciLoopForVar_n_2nd(){
        element = 0;
        assertNull(FibonacciSequence.fibonacciSequenceUsingLoop(element));
    }

    @Test
    public void testFibonacciLoopForVar_counter(){
        element = 1;
        expected = 1;
        assertTrue(FibonacciSequence.fibonacciSequenceUsingLoop(element)==expected);
    }

    @Test
    public void testFibonacciLoopForVar_array(){
        element = 2;
        expected = 1;
        assertTrue(FibonacciSequence.fibonacciSequenceUsingLoop(element)==expected);
    }

}
