package com.jwetherell.algorithms.mathematics;

/**
 * In number theory, two integers a and b are said to be relatively prime, mutually prime, or coprime (also spelled 
 * co-prime) if the only positive integer that divides both of them is 1. That is, the only common positive factor 
 * of the two numbers is 1. This is equivalent to their greatest common divisor being 1.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Coprime_integers">Mutually Prime / Co-prime (Wikipedia)</a>
 * <br>
 * @author Szymon Stankiewicz <mail@stankiewicz.me>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Coprimes {

    private Coprimes() { }

    /**
     *
     * Euler's totient function. Because this function is multiplicative such implementation is possible.
     * <p>
     * Time complexity: O(sqrt(n))
     * <p>
     * @param n Long integer
     * @return number of coprimes smaller or equal to n
     */
    public static long getNumberOfCoprimes(long n) {
        if(n < 1)
            return 0;
        long res = 1;
        for(int i = 2; i*i <= n; i++) {
            int times = 0;
            while(n%i == 0) {
                res *= (times > 0 ? i : i-1);
                n /= i;
                times++;
            }
        }
        if(n > 1)
            res *= n-1;
        return res;
    }
}
