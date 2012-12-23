package com.jwetherell.algorithms.mathematics;

public class Multiplication {

    public static final long multiplication(int a, int b) {
        long result = ((long) a) * ((long) b);
        return result;
    }

    public static final long multiplyUsingLoop(int a, int b) {
        int absB = Math.abs(b);
        long result = a;
        for (int i = 1; i < absB; i++) {
            result += a;
        }
        return (b < 0) ? -result : result;
    }

    public static final long multiplyUsingRecursion(int a, int b) {
        int absB = Math.abs(b);
        long result = a;
        if (absB == 1)
            return result;

        result += multiplyUsingRecursion(a, absB - 1);
        return (b < 0) ? -result : result;
    }

    public static final long multiplyUsingShift(int a, int b) {
        int absA = Math.abs(a);
        int absB = Math.abs(b);

        long result = 0L;
        while (absA > 0) {
            if ((absA & 1) > 0)
                result += absB; // Is odd
            absA >>= 1;
            absB <<= 1;
        }

        return (a > 0 && b > 0 || a < 0 && b < 0) ? result : -result;
    }

    public static final long multiplyUsingLogs(int a, int b) {
        long absA = Math.abs(a);
        long absB = Math.abs(b);
        long result = Math.round(Math.pow(10, (Math.log10(absA) + Math.log10(absB))));
        return (a > 0 && b > 0 || a < 0 && b < 0) ? result : -result;
    }
}
