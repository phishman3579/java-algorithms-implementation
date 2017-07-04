package com.jwetherell.algorithms.mathematics.test;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.DiscreteLogarithm;

import static org.junit.Assert.assertTrue;

public class DiscreteLogarithmTest {

    @Test
    public void shouldCountDiscreteLogarithm() {
        final long a = 3;
        final long b = 4;
        final long p = 7;
        final long expectedX = 4;

        final long x = DiscreteLogarithm.countDiscreteLogarithm(a, b, p);
        assertTrue(x == expectedX);
    }

    @Test
    public void shouldCountDiscreteLogarithm2() {
        final long a = 2;
        final long b = 64;
        final long p = 101;
        final long expectedX = 6;

        final long x = DiscreteLogarithm.countDiscreteLogarithm(a, b, p);
        assertTrue(x == expectedX);
    }

    @Test
    public void shouldNotCountDiscreteLogarithm() {
        final long a = 4;
        final long b = 5;
        final long p = 7;
        final long expectedX = -1;

        final long x = DiscreteLogarithm.countDiscreteLogarithm(a, b, p);
        assertTrue(x == expectedX);
    }
}