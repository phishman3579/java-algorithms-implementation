package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.GreatestCommonDivisor;


public class GCDTest {

    @Test
    public void testRecursiveGCD() {
        assertEquals(3, GreatestCommonDivisor.gcdUsingRecursion(15, 138));
        assertEquals(79, GreatestCommonDivisor.gcdUsingRecursion(79, -79));
        assertEquals(750, GreatestCommonDivisor.gcdUsingRecursion(-750*1000000009L, -750*123));
    }

    @Test
    public void testEuclideanGCD() {
        long x = 1989;
        long y = 867;
        long gcd = GreatestCommonDivisor.gcdUsingEuclides(x, y);
        long check = 51;
        assertTrue("Euclids GCD error. expected="+check+" got="+gcd, (gcd==check));

        x = 567;
        y = 56;
        gcd = GreatestCommonDivisor.gcdUsingEuclides(x, y);
        check = 7;
        assertTrue("Euclids GCD error. expected="+check+" got="+gcd, (gcd==check));

        x = 10002345;
        y = 67885;
        gcd = GreatestCommonDivisor.gcdUsingEuclides(x, y);
        check = 5;
        assertTrue("Euclids GCD error. expected="+check+" got="+gcd, (gcd==check));
    }

    @Test
    public void testRecursiveGCDWithSmallestBoundaryValue01() {
        long x = Long.MIN_VALUE;
        long y = 4611686018427387900L;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 4;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSmallestBoundaryValue02() {
        long x = Long.MIN_VALUE + 1;
        long y = 4611686018427387900L;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 7;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSmallestBoundaryValue03() {
        long x = 60060L;
        long y = Long.MIN_VALUE;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 4;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSmallestBoundaryValue04() {
        long x = 60060L;
        long y = Long.MIN_VALUE + 1;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 7;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithBiggestBoundaryValue01() {
        long x = Long.MAX_VALUE;
        long y = 4611686018427387900L;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 7;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithBiggestBoundaryValue02() {
        long x = Long.MAX_VALUE - 1;
        long y = 4611686018427387900L;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 6;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithBiggestBoundaryValue03() {
        long x = 124200L;
        long y = Long.MAX_VALUE;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 1;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithBiggestBoundaryValue04() {
        long x = 124200L;
        long y = Long.MAX_VALUE - 1;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 6;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSpecialValue01() {
        long x = Long.MAX_VALUE;
        long y = Long.MAX_VALUE -1;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 1;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSpecialValue02() {
        long x = Long.MIN_VALUE;
        long y = Long.MIN_VALUE +1;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 1;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSpecialValue03() {
        long x = 0;
        long y = Long.MIN_VALUE;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = -9223372036854775808L;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSpecialValue04() {
        long x = 1;
        long y = Long.MIN_VALUE;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 1;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSpecialValue05() {
        long x = 0;
        long y = Long.MAX_VALUE;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 9223372036854775807L;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSpecialValue06() {
        long x = 6464500;
        long y = 1546340;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 1;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }
}
