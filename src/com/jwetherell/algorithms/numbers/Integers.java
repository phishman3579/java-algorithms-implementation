package com.jwetherell.algorithms.numbers;

import java.math.BigDecimal;

public class Integers {
    
    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal TWO = new BigDecimal(2);
    
    public static final String toBinaryUsingDivideAndModulus(int integer) {
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer>0) {
            temp = integer;
            integer = temp/2;
            builder.append(temp%2);
        }
        return builder.reverse().toString();
    }
    
    public static final String toBinaryUsingShiftsAndModulus(int integer) {
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer>0) {
            temp = integer;
            integer = (temp>>1);
            builder.append(temp%2);
        }
        return builder.reverse().toString();
    }
    
    public static final String toBinaryUsingBigDecimal(int integer) {
        StringBuilder builder = new StringBuilder();
        BigDecimal number = new BigDecimal(integer);
        BigDecimal[] decimals = null;
        while (number.compareTo(ZERO)>0) {
            decimals = number.divideAndRemainder(TWO);
            number = decimals[0];
            builder.append(decimals[1]);
        }
        return builder.reverse().toString();
    }
    
    public static final String toBinaryUsingDivideAndDouble(int integer) {
        StringBuilder builder = new StringBuilder();
        double temp = 0d;
        while (integer>0) {
            temp = ((double)integer)/2d;
            integer = (int)temp;
            builder.append((temp>integer)?1:0);
        }
        return builder.reverse().toString();
    }
}
