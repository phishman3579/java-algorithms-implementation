package com.jwetherell.algorithms.numbers;

public class Integers {
    
    public static final String toBinaryUsingDivideAndModulus(int integer) {
        StringBuilder builder = new StringBuilder();
        while (integer>0) {
            int temp = integer;
            integer = temp/2;
            builder.append(temp%2);
        }
        return builder.reverse().toString();
    }
    
    public static final String toBinaryUsingShiftsAndModulus(int integer) {
        StringBuilder builder = new StringBuilder();
        while (integer>0) {
            int temp = integer;
            integer = (temp>>1);
            builder.append(temp%2);
        }
        return builder.reverse().toString();
    }
}
