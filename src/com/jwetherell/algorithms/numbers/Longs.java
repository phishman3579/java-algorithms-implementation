package com.jwetherell.algorithms.numbers;

import java.math.BigDecimal;

public class Longs {

    public static final String toBinaryUsingDivideAndModulus(long numberToConvert) {
        long longNumber = numberToConvert;
        if (longNumber<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+longNumber);
        StringBuilder builder = new StringBuilder();
        long temp = 0l;
        while (longNumber > 0) {
            temp = longNumber;
            longNumber = temp / 2;
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingShiftsAndModulus(long numberToConvert) {
        long longNumber = numberToConvert;
        if (longNumber<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+longNumber);
        StringBuilder builder = new StringBuilder();
        long temp = 0l;
        while (longNumber > 0) {
            temp = longNumber;
            longNumber = (temp >> 1);
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingBigDecimal(long numberToConvert) {
        long longNumber = numberToConvert;
        if (longNumber<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+longNumber);
        StringBuilder builder = new StringBuilder();
        BigDecimal zero = new BigDecimal(0);
        BigDecimal two = new BigDecimal(2);
        BigDecimal number = new BigDecimal(longNumber);
        BigDecimal[] decimals = null;
        while (number.compareTo(zero) > 0) {
            decimals = number.divideAndRemainder(two);
            number = decimals[0];
            builder.append(decimals[1]);
        }
        return builder.reverse().toString();
    }
}
