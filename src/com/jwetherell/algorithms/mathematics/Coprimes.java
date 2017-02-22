package com.jwetherell.algorithms.mathematics;

/**
 * @author Szymon Stankiewicz
 */
public class Coprimes {

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
