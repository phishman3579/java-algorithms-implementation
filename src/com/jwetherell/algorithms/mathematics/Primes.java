package com.jwetherell.algorithms.mathematics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Primes {

    public static final Map<Long, Long> getPrimeFactorization(long number) {
        Map<Long, Long> map = new HashMap<Long, Long>();
        long n = number;
        int c = 0;
        // for each potential factor i
        for (long i = 2; i * i <= n; i++) {
            c = 0;
            // if i is a factor of N, repeatedly divide it out
            while (n % i == 0) {
                n = n / i;
                c++;
            }
            Long p = map.get(i);
            if (p == null)
                p = new Long(0);
            p += c;
            map.put(i, p);
        }
        if (n > 1) {
            Long p = map.get(n);
            if (p == null)
                p = new Long(0);
            p += 1;
            map.put(n, p);
        }
        return map;
    }

    /*
     * isPrime() using the square root properties
     * 
     * 1 is not a prime. All primes except 2 are odd. All primes greater than 3
     * can be written in the form 6k+/-1. Any number n can have only one
     * primefactor greater than n . The consequence for primality testing of a
     * number n is: if we cannot find a number f less than or equal n that
     * divides n then n is prime: the only primefactor of n is n itself
     */
    public static final boolean isPrime(long number) {
        if (number == 1)
            return false;
        if (number < 4)
            return true; // 2 and 3 are prime
        if (number % 2 == 0)
            return false; // short circuit
        if (number < 9)
            return true; // we have already excluded 4, 6 and 8.
                         // (testing for 5 & 7)
        if (number % 3 == 0)
            return false; // short circuit
        long r = (long) (Math.sqrt(number)); // n rounded to the greatest integer
                                            // r so that r*r<=n
        int f = 5;
        while (f <= r) {
            if (number % f == 0)
                return false;
            if (number % (f + 2) == 0)
                return false;
            f += 6;
        }
        return true;
    }

    /*
     * Sieve of Eratosthenes
     */
    private static boolean[] sieve = null;

    public static final boolean sieveOfEratosthenes(int number) {
        if (number == 1) {
            return false;
        }
        if (sieve == null || number >= sieve.length) {
            int start = 2;
            if (sieve == null) {
                sieve = new boolean[number+1];
            } else if (number >= sieve.length) {
                sieve = Arrays.copyOf(sieve, number+1);
            }

            for (int i = start; i <= Math.sqrt(number); i++) {
                if (!sieve[i]) {
                    for (int j = i*2; j <= number; j += i) {
                        sieve[j] = true;
                    }
                }
            }
        }
        return !sieve[number];
    }
}
