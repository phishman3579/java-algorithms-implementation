package com.jwetherell.algorithms.mathematics;

/**
 * In mathematics, modular arithmetic is a system of arithmetic for integers, where numbers "wrap around" 
 * upon reaching a certain value—the modulus (plural moduli). The modern approach to modular arithmetic was 
 * developed by Carl Friedrich Gauss in his book Disquisitiones Arithmeticae, published in 1801.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Modular_arithmetic">Modular Arithmetic (Wikipedia)</a>
 * <br>
 * @author Szymon Stankiewicz <mail@stankiewicz.me>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Modular {

    private static long modularAbs(long n, long mod) {
        n %= mod;
        if (n < 0)
            n += mod;
        return n;
    }


    /**
     * Adds two numbers in modulo arithmetic.
     * This function is safe for large numbers and won't overflow long.
     *
     * @param a
     * @param b
     * @param mod grater than 0
     * @return (a+b)%mod
     */
    public static long add(long a, long b, long mod) {
        if(mod <= 0)
            throw new IllegalArgumentException("Mod argument is not grater then 0");
        a = modularAbs(a, mod);
        b = modularAbs(b, mod);
        if(b > mod-a) {
            return b - (mod - a);
        }
        return (a + b)%mod;
    }

    /**
     * Subtract two numbers in modulo arithmetic.
     * This function is safe for large numbers and won't overflow or underflow long.
     *
     * @param a
     * @param b
     * @param mod grater than 0
     * @return (a-b)%mod
     */
    public static long subtract(long a, long b, long mod) {
        if(mod <= 0)
            throw new IllegalArgumentException("Mod argument is not grater then 0");
        return add(a, -b, mod);
    }

    /**
     * Multiply two numbers in modulo arithmetic.
     * This function is safe for large numbers and won't overflow or underflow long.
     *
     * Complexity O(log b)
     *
     * @param a
     * @param b
     * @param mod grater than 0
     * @return (a*b)%mod
     */

    public static long multiply(long a, long b, long mod) {
        if(mod <= 0)
            throw new IllegalArgumentException("Mod argument is not grater then 0");
        a = modularAbs(a, mod);
        b = modularAbs(b, mod);
        if(b == 0) return 0;
        return add(multiply(add(a, a, mod), b/2, mod), (b%2 == 1 ? a : 0), mod);
    }

    /**
     * Calculate power in modulo arithmetic.
     * This function is safe for large numbers and won't overflow or underflow long.
     *
     * Complexity O(log a * log b)
     *
     * @param a
     * @param b integer grater or equal to zero
     * @param mod grater than 0
     * @return (a^b)%mod
     */
    public static long pow(long a, long b, long mod) {
        if(mod <= 0)
            throw new IllegalArgumentException("Mod argument is not grater then 0");
        if (b < 0)
            throw new IllegalArgumentException("Exponent have to be grater or equal to zero");
        a = modularAbs(a, mod);
        if (a == 0 && b == 0)
            throw new IllegalArgumentException("0^0 expression");
        if (a == 0)
            return 0;
        long res = 1;
        while(b > 0) {
            if(b%2 == 1) res = multiply(res, a, mod);
            a = multiply(a, a, mod);
            b /= 2;
        }
        return res;
    }

    /**
     * Divide two numbers in modulo arithmetic.
     * This function is safe for large numbers and won't overflow or underflow long.
     * b and mod have to be coprime.
     *
     * Complexity O(sqrt(mod))
     *
     * @param a
     * @param b non zero
     * @param mod grater than 0
     * @return (a/b)%mod
     */

    public static long divide(long a, long b, long mod) {
        a = modularAbs(a, mod);
        b = modularAbs(b, mod);
        if(mod <= 0)
            throw new IllegalArgumentException("Mod argument is not grater then 0");
        if (b == 0)
            throw new IllegalArgumentException("Dividing by zero");
        if (GreatestCommonDivisor.gcdUsingRecursion(b, mod) != 1) {
            throw new IllegalArgumentException("b and mod are not coprime");
        }
        if (a == 0) {
            return 0;
        }
        if (b == 1) {
            return a;
        }

        long reverted = pow(b, Coprimes.getNumberOfCoprimes(mod)-1, mod);
        return multiply(reverted, a, mod);

    }
}