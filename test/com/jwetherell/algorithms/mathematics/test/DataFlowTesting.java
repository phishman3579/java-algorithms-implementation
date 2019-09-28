package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.Division;
import com.jwetherell.algorithms.mathematics.Multiplication;

/**
 *
 * @author Dung
 */

public class DataFlowTesting {
    
    @Test
    public void testMultiplyUsingShift1() {
	long expected1=0;
        assertEquals(expected1, Multiplication.multiplyUsingShift(0, 9));
    }
    @Test
    public void testMultiplyUsingShift2() {
	long expected2=7;
        assertEquals(expected2, Multiplication.multiplyUsingShift(1, 7));
    }
    @Test
    public void testMultiplyUsingShift3() {
	long expected3=-7;
        assertEquals(expected3, Multiplication.multiplyUsingShift(1, -7));
    }
    @Test
    public void testMultiplyUsingShift4() {
	long expected4=18;
        assertEquals(expected4, Multiplication.multiplyUsingShift(2, 9));
    }
    @Test
    public void testMultiplyUsingShift5() {
	long expected5=-18;
        assertEquals(expected5, Multiplication.multiplyUsingShift(-2, 9));
    }


    
}
