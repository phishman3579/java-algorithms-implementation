package com.jwetherell.algorithms.numbers;

public class Longs {
    
    public static final String toBinaryUsingDivideAndModulus(long integer) {
        StringBuilder builder = new StringBuilder();
        while (integer>0) {
            long temp = integer;
            integer = temp/2;
            builder.append(temp%2);
        }
        return builder.reverse().toString();
    }
    
    public static final String toBinaryUsingShiftsAndModulus(long integer) {
        StringBuilder builder = new StringBuilder();
        while (integer>0) {
            long temp = integer;
            integer = (temp>>1);
            builder.append(temp%2);
        }
        return builder.reverse().toString();
    }
}
