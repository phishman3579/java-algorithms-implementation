package com.jwetherell.algorithms.numbers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Integers extends NumericalConversions{

    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal TWO = new BigDecimal(2);

    public static final String toBinaryUsingDivideAndModulus(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer > 0) {
            temp = integer;
            integer = temp / 2;
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingShiftsAndModulus(int numberToConvert) {
        return toBinaryUsingShiftsAndModulusNC(numberToConvert);

    }

    public static final String toBinaryUsingBigDecimal(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
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

    public static final String toBinaryUsingDivideAndDouble(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
        StringBuilder builder = new StringBuilder();
        double temp = 0d;
        while (integer > 0) {
            temp = integer/2d;
            integer = (int) temp;
            builder.append((temp > integer) ? 1 : 0);
        }
        return builder.reverse().toString();
    }

    public static final boolean powerOfTwoUsingLoop(int numberToCheck) {
        int number = numberToCheck;
        if (number == 0)
            return false;
        while (number % 2 == 0) {
            number /= 2;
        }
        if (number > 1)
            return false;
        return true;
    }

    public static final boolean powerOfTwoUsingRecursion(int numberToCheck) {
        int number = numberToCheck;
        if (number == 1)
            return true;
        if (number == 0 || number % 2 != 0)
            return false;
        return powerOfTwoUsingRecursion(number / 2);
    }

    public static final boolean powerOfTwoUsingLog(int numberToCheck) {
        int number = numberToCheck;
        double doubleLog = Math.log10(number) / Math.log10(2);
        int intLog = (int) doubleLog;
        if (Double.compare(doubleLog, intLog) == 0)
            return true;
        return false;
    }

    public static final boolean powerOfTwoUsingBits(int numberToCheck) {
        int number = numberToCheck;
        if (number != 0 && ((number & (number - 1)) == 0))
            return true;
        return false;
    }

    // Intergers to English - refactored into another class; Refactoring Technique used: Extract Class
}
