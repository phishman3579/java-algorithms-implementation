package com.jwetherell.algorithms.mathematics;

/**
 * @author Szymon Stankiewicz
 */
public class Utils {

    /**
     *
     * Calculate greatest common divisor of two numbers
     * <p>
     * Time complexity O(log(a+b))
     * <p>
     * @param a Long integer
     * @param b Long integer
     * @return greatest common divisor of a and b
     */
    public static long getGreatestCommonDivisor(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        return a == 0 ? b : getGreatestCommonDivisor(b%a, a);
    }
}
