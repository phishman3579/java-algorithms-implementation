package com.jwetherell.algorithms.mathematics;

import java.util.HashMap;

import static java.lang.Math.sqrt;

/**
 * https://en.wikipedia.org/wiki/Discrete_logarithm
 * a^x = b mod p
 * x = ?
 * <br>
 * Created on 03.07.2017.
 *
 * @author lucjanroslanowski
 */
public class DiscreteLogarithm {
    private static long NO_SOLUTION = -1;
    private HashMap<Long, Long> set = new HashMap<>();

    private long pow(long a, long x, long p) {
        if (x == 0) {
            return 1;
        }

        if (x == 1) {
            return a % p;
        }

        if (x % 2 != 0) {
            return (a * pow(a, x - 1, p)) % p;
        } else {
            long temp = pow(a, x / 2, p) % p;
            return (temp * temp) % p;
        }
    }


    private long getDiscreteLogarithm(long s, long a, long p) {
        for (long i = 0; i < s; ++i) {
            long el = pow(a, (i * s) % p, p);
            el = pow(el, p - 2, p);

            if (set.containsKey(el)) {
                return i * s + set.get(el);
            }
        }
        return NO_SOLUTION;
    }

    private void generateSet(long a, long b_1, long p, long s) {
        for (long i = 0; i < s; ++i) {
            long first = (pow(a, i, p) * b_1) % p;
            if (!set.containsKey(first)) {
                set.put(first, i);
            }
        }
    }

    public long countDiscreteLogarithm(final long a, final long b, final long p) {
        long s = (long) sqrt(p) + 1;
        long b_1 = pow(b, p - 2, p);
        generateSet(a, b_1, p, s);
        return getDiscreteLogarithm(s,a,p);
    }
}