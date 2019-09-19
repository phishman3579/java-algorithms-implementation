package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.Division;
import com.jwetherell.algorithms.mathematics.Multiplication;

/**
 *
 * @author Dung
 */
public class MultiplicationTest {
    // Kiểm thử cho trường hợp x và y bình thường
    @Test
    public void testMultiplyUsingShift1() {
        long expResult = 12;
        long result = Multiplication.multiplyUsingShift(3, 4);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x bình thường và y max
    @Test
    public void testMultiplyUsingShift2() {
        long expResult = 3*Integer.MAX_VALUE;
        long result = Multiplication.multiplyUsingShift(3, Integer.MAX_VALUE);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x bình thường và y ngay trên max
    @Test
    public void testMultiplyUsingShift3() {
        long expResult = 3*(Integer.MAX_VALUE + 1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MAX_VALUE + 1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x bình thường và y ngay dưới max
    @Test
    public void testMultiplyUsingShift4() {
        long expResult = 3*(Integer.MAX_VALUE - 1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MAX_VALUE - 1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x bình thường và y min
    @Test
    public void testMultiplyUsingShift5() {
        long expResult = 3*(Integer.MIN_VALUE );
        long result = Multiplication.multiplyUsingShift(3, Integer.MIN_VALUE);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x bình thường và y ngay dưới min
    @Test
    public void testMultiplyUsingShift6() {
        long expResult = 3*(Integer.MIN_VALUE - 1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MIN_VALUE - 1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x bình thường và y ngay trên min
    @Test
    public void testMultiplyUsingShift7() {
        long expResult = 3*(Integer.MIN_VALUE+1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MIN_VALUE+1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x min và y bình thường
    @Test
    public void testMultiplyUsingShift8() {
        long expResult = 3*(Integer.MIN_VALUE);
        long result = Multiplication.multiplyUsingShift(3, Integer.MIN_VALUE);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x dưới min và y bình thường
    @Test
    public void testMultiplyUsingShift9() {
        long expResult = 3*(Integer.MIN_VALUE-1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MIN_VALUE-1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x trên min và y bình thường
    @Test
    public void testMultiplyUsingShift10() {
        long expResult = 3*(Integer.MIN_VALUE+1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MIN_VALUE+1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x max và y bình thường
    @Test
    public void testMultiplyUsingShift11() {
        long expResult = 3*(Integer.MAX_VALUE);
        long result = Multiplication.multiplyUsingShift(3, Integer.MAX_VALUE);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x dưới max và y bình thường
    @Test
    public void testMultiplyUsingShift12() {
        long expResult = 3*(Integer.MAX_VALUE-1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MAX_VALUE-1);
        assertEquals(expResult, result);
    }
    // Kiểm thử cho trường hợp x trên max và y bình thường
    @Test
    public void testMultiplyUsingShift13() {
        long expResult = 3*(Integer.MAX_VALUE+1);
        long result = Multiplication.multiplyUsingShift(3, Integer.MAX_VALUE+1);
        assertEquals(expResult, result);
    }
}
