package com.jwetherell.algorithms.numbers;

import java.math.BigDecimal;


public class Integers {

    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal TWO = new BigDecimal(2);

    public static final String toBinaryUsingDivideAndModulus(int integer) {
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer > 0) {
            temp = integer;
            integer = temp / 2;
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingShiftsAndModulus(int integer) {
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer > 0) {
            temp = integer;
            integer = (temp >> 1);
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingBigDecimal(int integer) {
        StringBuilder builder = new StringBuilder();
        BigDecimal number = new BigDecimal(integer);
        BigDecimal[] decimals = null;
        while (number.compareTo(ZERO) > 0) {
            decimals = number.divideAndRemainder(TWO);
            number = decimals[0];
            builder.append(decimals[1]);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingDivideAndDouble(int integer) {
        StringBuilder builder = new StringBuilder();
        double temp = 0d;
        while (integer > 0) {
            temp = ((double) integer) / 2d;
            integer = (int) temp;
            builder.append((temp > integer) ? 1 : 0);
        }
        return builder.reverse().toString();
    }

    public static final int euclidsGreatestCommonDivsor(int x, int y) {
        int greater = x;
        int smaller = y;
        if (y > x) {
            greater = y;
            smaller = x;
        }

        int result = 0;
        while (true) {
            if (smaller == greater) {
                result = smaller; // smaller == greater
                break;
            }

            greater -= smaller;
            if (smaller > greater) {
                int temp = smaller;
                smaller = greater;
                greater = temp;
            }
        }
        return result;
    }

    public static final boolean powerOfTwoUsingLoop(int number) {
        if (number == 0) return false;
        while (number % 2 == 0) {
            number /= 2;
        }
        if (number > 1) return false;
        return true;
    }

    public static final boolean powerOfTwoUsingRecursion(int number) {
        if (number == 1) return true;
        if (number == 0 || number % 2 != 0) return false;
        return powerOfTwoUsingRecursion(number / 2);
    }

    public static final boolean powerOfTwoUsingLog(int number) {
        double doubleLog = Math.log10(number) / Math.log10(2);
        int intLog = (int) doubleLog;
        if (doubleLog == intLog) return true;
        return false;
    }

    public static final boolean powerOfTwoUsingBits(int number) {
        if (number != 0 && ((number & (number - 1)) == 0)) return true;
        return false;
    }
}
