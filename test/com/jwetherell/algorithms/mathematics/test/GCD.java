package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GCD {

    @Test
    public void gcdTest() {
        assertEquals(3, Utils.getGreatestCommonDivisor(15, 138));
        assertEquals(79, Utils.getGreatestCommonDivisor(79, -79));
        assertEquals(750, Utils.getGreatestCommonDivisor(-750*1000000009L, -750*123));
    }
}
