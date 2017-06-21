package com.jwetherell.algorithms.mathematics;

/**
 * Recursive function of exponentiation is just an implementation of definition.
 * https://en.wikipedia.org/wiki/Exponentiation
 * Complexity  - O(N) where N is exponent.
 * <p>
 * Fast exponentiation's complexity is O(lg N)
 * https://en.wikipedia.org/wiki/Exponentiation_by_squaring
 * <p>
 * Modular exponentiation is similar.
 * https://en.wikipedia.org/wiki/Modular_exponentiation
 * Here is implemented fast version of this algorithm so a complexity is also O(lg N)
 * <br>
 *
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 */


public class Exponentiation {

    public static int recursiveExponentiation(int base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent == 1) return base;

        return recursiveExponentiation(base, exponent - 1) * base;

    }

    public static int fastRecursiveExponentiation(int base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent == 1) return base;

        int resultOnHalfExponent = fastRecursiveExponentiation(base, exponent / 2);

        if ((exponent % 2) == 0)
            return resultOnHalfExponent * resultOnHalfExponent;
        else
            return resultOnHalfExponent * resultOnHalfExponent * base;

    }

    public static int fastRecursiveExponentiationModulo(int base, int exponent, int mod) {
        if (exponent == 0) return 1;
        if (exponent == 1) return base;

        int resultOnHalfExponent = fastRecursiveExponentiation(base, exponent / 2);

        if ((exponent % 2) == 0)
            return (resultOnHalfExponent * resultOnHalfExponent) % mod;
        else
            return (((resultOnHalfExponent * resultOnHalfExponent) % mod) * base) % mod;

    }


}
