package com.jwetherell.algorithms.mathematics;

/**
 * In mathematics, the greatest common divisor (gcd) of two or more integers, when at least one of them is not 
 * zero, is the largest positive integer that is a divisor of both numbers. 
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Greatest_common_divisor">Greatest Common Divisor (Wikipedia)</a>
 * <br>
 * @author Szymon Stankiewicz <mail@stankiewicz.me>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class GreatestCommonDivisor {

    /**
     * Calculate greatest common divisor of two numbers using recursion.
     * <p>
     * Time complexity O(log(a+b))
     * <br>
     * @param a Long integer
     * @param b Long integer
     * @return greatest common divisor of a and b
     */
    public static long gcdUsingRecursion(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        return a == 0 ? b : gcdUsingRecursion(b%a, a);
    }

    /**
     * A much more efficient method is the Euclidean algorithm, which uses a division algorithm such as long division 
     * in combination with the observation that the gcd of two numbers also divides their difference.
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm">Euclidean Algorithm (Wikipedia)</a>
     */
    public static final long gcdUsingEuclides(long x, long y) {
        long greater = x;
        long smaller = y;
        if (y > x) {
            greater = y;
            smaller = x;
        }

        long result = 0;
        while (true) {
            if (smaller == greater) {
                result = smaller; // smaller == greater
                break;
            }

            greater -= smaller;
            if (smaller > greater) {
                long temp = smaller;
                smaller = greater;
                greater = temp;
            }
        }
        return result;
    }
}
