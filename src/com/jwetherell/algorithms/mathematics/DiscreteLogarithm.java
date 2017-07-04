package com.jwetherell.algorithms.mathematics;

import java.util.HashMap;

import static java.lang.Math.sqrt;

/**
 * In mathematics, a discrete logarithm is an integer k exponent solving the equation bk = g, where b and g are 
 * elements of a group. Discrete logarithms are thus the group-theoretic analogue of ordinary logarithms, which 
 * solve the same equation for real numbers b and g, where b is the base of the logarithm and g is the value whose 
 * logarithm is being taken.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Discrete_logarithm">Discrete Logarithm (Wikipedia)</a>
 * <br>
 * @author Lucjan Rosłanowski <lucjanroslanowski@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class DiscreteLogarithm {

    public static final long NO_SOLUTION = -1;

    private static final HashMap<Long, Long> set = new HashMap<Long, Long>();

    private DiscreteLogarithm() { }

    private static final long pow(long a, long x, long p) {
        if (x == 0)
            return 1;

        if (x == 1)
            return a % p;

        if (x % 2 != 0)
            return (a * pow(a, x - 1, p)) % p;

        final long temp = pow(a, x / 2, p) % p;
        return (temp * temp) % p;
    }

    private static final long getDiscreteLogarithm(HashMap<Long, Long> set, long s, long a, long p) {
        for (long i = 0; i < s; ++i) {
            long el = pow(a, (i * s) % p, p);
            el = pow(el, p - 2, p);

            if (set.containsKey(el))
                return i * s + set.get(el);
        }
        return NO_SOLUTION;
    }

    private static final void generateSet(long a, long b_1, long p, long s, HashMap<Long, Long> set) {
        set.clear();
        for (long i = 0; i < s; ++i) {
            final long first = (pow(a, i, p) * b_1) % p;
            if (!set.containsKey(first))
                set.put(first, i);
        }
    }

    /**
     * Returns DiscreteLogarithm.NO_SOLUTION when a solution cannot be found
     */
    public static final long countDiscreteLogarithm(final long a, final long b, final long p) {
        final long s = (long) sqrt(p) + 1;
        final long b_1 = pow(b, p - 2, p);

        generateSet(a, b_1, p, s, set);
        return getDiscreteLogarithm(set, s,a,p);
    }
}
