package com.jwetherell.algorithms.mathematics;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * <br>
 * Created on 04.07.2017.
 *
 * @author lucjanroslanowski
 */
public class DiscreteLogarithmTest {

    @Test
    public void shouldCountDiscreteLogarithm() {
        final DiscreteLogarithm discreteLogarithm = new DiscreteLogarithm();
        final long a = 3;
        final long b = 4;
        final long p = 7;
        final long expectedX = 4;

        long x = discreteLogarithm.countDiscreteLogarithm(a, b, p);

        assertTrue(x == expectedX);
    }

    @Test
    public void shouldCountDiscreteLogarithm2() {
        final DiscreteLogarithm discreteLogarithm = new DiscreteLogarithm();
        final long a = 2;
        final long b = 64;
        final long p = 101;
        final long expectedX = 6;

        long x = discreteLogarithm.countDiscreteLogarithm(a, b, p);

        assertTrue(x == expectedX);
    }

    @Test
    public void shouldNotCountDiscreteLogarithm() {
        final DiscreteLogarithm discreteLogarithm = new DiscreteLogarithm();
        final long a = 4;
        final long b = 5;
        final long p = 7;
        final long expectedX = -1;

        long x = discreteLogarithm.countDiscreteLogarithm(a, b, p);

        assertTrue(x == expectedX);
    }
}