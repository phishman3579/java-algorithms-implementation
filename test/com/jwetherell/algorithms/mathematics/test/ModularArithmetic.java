package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.Modular;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModularArithmetic {

    @Test
    public void sumTest() {
        assertEquals(4,
                Modular.add(-3, 22, 5));

        assertEquals(Long.MAX_VALUE-2,
                Modular.add(Long.MAX_VALUE-1, Long.MAX_VALUE-1, Long.MAX_VALUE));

        assertEquals(2,
                Modular.add(1-Long.MAX_VALUE, 1-Long.MAX_VALUE, Long.MAX_VALUE));


        assertEquals(0,
                Modular.add(Long.MAX_VALUE/2, Long.MAX_VALUE/2 + 1, Long.MAX_VALUE));

        assertEquals(0,
                Modular.add(-1000, -10000000, 10));

        try {
            Modular.add(1, 1,0);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void subtractTest() {
        assertEquals(0,
                Modular.subtract(-22, 3, 5));

        assertEquals(Long.MAX_VALUE-1,
                Modular.subtract(Long.MAX_VALUE-2, Long.MAX_VALUE-1, Long.MAX_VALUE));

        assertEquals(Long.MAX_VALUE-1,
                Modular.subtract(1-Long.MAX_VALUE, 2, Long.MAX_VALUE));

        assertEquals(0,
                Modular.subtract(-1000, -10000000, 10));

        try {
            Modular.subtract(1, 1,0);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void multiplyTest() {
        assertEquals(10,
                Modular.multiply(Long.MAX_VALUE-2, Long.MAX_VALUE-5, Long.MAX_VALUE));

        assertEquals(3,
                Modular.multiply(-5, -7, 32));

        try {
            Modular.multiply(1, 1,0);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }

    }

    @Test
    public void powerTest() {
        assertEquals(1,
                Modular.pow(3, 1000000006, 1000000007));

        assertEquals(8,
                Modular.pow(2, 66, Long.MAX_VALUE));

        assertEquals(1,
                Modular.pow(123, 0, 1111));

        assertEquals(0,
                Modular.pow(0, 123, 2));

        try {
            Modular.pow(5, 0,5);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }

        try {
            Modular.pow(5, -5,5);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }

        try {
            Modular.pow(5, 5,0);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void divideTest() {
        try {
            Modular.divide(11, 6, 120);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }

        try {
            Modular.divide(2, 2, 0);
            assertTrue("No exception", false);
        } catch (Exception ignored) {
        }

        assertEquals(1,
                Modular.divide(7, 7, 125));

        assertEquals(97,
                Modular.divide(Modular.multiply(97, 25, 1023), 25, 1023));

        assertEquals(Long.MAX_VALUE-11,
                Modular.divide(Modular.multiply(Long.MAX_VALUE-11, Long.MAX_VALUE-12, Long.MAX_VALUE),
                        Long.MAX_VALUE-12,
                        Long.MAX_VALUE));
    }
}