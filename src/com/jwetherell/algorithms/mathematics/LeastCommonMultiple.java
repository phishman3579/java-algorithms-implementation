package com.jwetherell.algorithms.mathematics;

import static com.jwetherell.algorithms.mathematics.GreatestCommonDivisor.gcdUsingRecursion;

/**
 * In mathematics, the least common multiple (lcm) of two or more integers
 * is the smallest positive integer that can be divided by both numbers.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Least_common_multiple">Least Common Multiple (Wikipedia)</a>
 * <br>
 * @author Egor Zhdan <egor.zhdan@gmail.com>
 */
public class LeastCommonMultiple {

    /**
     * Calculate least common multiply of two numbers using recursively calculated GCD.
     * <p>
     * Time complexity O(log(a+b))
     * <br>
     * @param a Long integer
     * @param b Long integer
     * @return least common multiply of a and b
     */
    public static long lcmUsingRecursion(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0 && b == 0) return 0;
        return a / gcdUsingRecursion(a, b) * b;
    }

}
