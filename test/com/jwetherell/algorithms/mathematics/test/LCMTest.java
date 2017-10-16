package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.LeastCommonMultiple;


public class LCMTest {

    @Test
    public void testRecursiveLCM() {
        assertEquals(0, LeastCommonMultiple.lcmUsingRecursion(0, 0));
        assertEquals(0, LeastCommonMultiple.lcmUsingRecursion(0, 228));
        assertEquals(12, LeastCommonMultiple.lcmUsingRecursion(3, 12));
        assertEquals(566, LeastCommonMultiple.lcmUsingRecursion(2, 283));
        assertEquals(79, LeastCommonMultiple.lcmUsingRecursion(79, -79));
        assertEquals(Long.MAX_VALUE, LeastCommonMultiple.lcmUsingRecursion(Long.MAX_VALUE, Long.MAX_VALUE));
    }

}
