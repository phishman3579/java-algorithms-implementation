package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.GreatestCommonDivisor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GCDTest {

    @Test
    public void testRecursiveGCD() {
        assertEquals(3, GreatestCommonDivisor.gcdUsingRecursion(15, 138));
        assertEquals(79, GreatestCommonDivisor.gcdUsingRecursion(79, -79));
        assertEquals(750, GreatestCommonDivisor.gcdUsingRecursion(-750 * 1000000009L, -750 * 123));
    }

    @Test
    public void testEuclideanGCD() {
        long x = 1989;
        long y = 867;
        long gcd = GreatestCommonDivisor.gcdUsingEuclides(x, y);
        long check = 51;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = 567;
        y = 56;
        gcd = GreatestCommonDivisor.gcdUsingEuclides(x, y);
        check = 7;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = 10002345;
        y = 67885;
        gcd = GreatestCommonDivisor.gcdUsingEuclides(x, y);
        check = 5;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));
    }

    @Test
    public void testRecursiveGCDWithSmallestBoundaryValue() {
        long x = Long.MIN_VALUE;
        long y = 4611686018427387900L;
        long gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        long check = 4;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = Long.MIN_VALUE + 1;
        y = 4611686018427387900L;
        gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        check = 7;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = Long.MAX_VALUE;
        y = 4611686018427387900L;
        gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        check = 7;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = Long.MAX_VALUE - 1;
        y = 4611686018427387900L;
        gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        check = 6;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = 1;
        y = 4611686018427387900L;
        gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        check = 1;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

        x = 0;
        y = 4611686018427387900L;
        gcd = GreatestCommonDivisor.gcdUsingRecursion(x, y);
        check = 4611686018427387900L;
        assertTrue("Euclids GCD error. expected=" + check + " got=" + gcd, (gcd == check));

    }
}
