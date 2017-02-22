package com.jwetherell.algorithms.mathematics;

public class Coprimes {

    public static long getNumberOfSmallerCoprimes(long n) {
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
