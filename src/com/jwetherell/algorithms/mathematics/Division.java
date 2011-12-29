package com.jwetherell.algorithms.mathematics;

public class Division {
    
    public static final long division(int a, int b) {
        long result = ((long)a)/((long)b);
        return result;
    }
    
    public static final long divisionUsingLoop(int a, int b) {
        long temp = a;
        int counter = 0;
        while (temp>=0) {
            temp -= b;
            if (temp>=0) counter++;
        }
        return counter;
    }
    
    public static final long divisionUsingMultiplication(int a, int b) {
        int temp = b;
        int counter = 0;
        while (temp <= a) {
            temp = temp<<1;
            counter++;
        }
        a -= b<<(counter-1);
        long result = (long)Math.pow(2, counter-1);
        if (b <= a) result += divisionUsingMultiplication(a,b);
        return result;
    }

    public static final long divisionUsingShift(int x, int y) {
        int a, b, q, counter;

        q = 0;
        if (y != 0) {
            while (x >= y) {
                a = x >> 1;
                b = y;
                counter = 1;
                while (a >= b) {
                    b <<= 1;
                    counter <<= 1;
                }
                x -= b;
                q += counter;
            }
        }
        return q;
    }
    
    public static final long divisionUsingLogs(int a, int b) {
        long absA = Math.abs(a);
        long absB = Math.abs(b);
        long result = Math.round(Math.pow(10, (Math.log10(absA)-Math.log10(absB))));
        return (a>0&&b>0 || a<0&&b<0)?result:-result;
    }
}
