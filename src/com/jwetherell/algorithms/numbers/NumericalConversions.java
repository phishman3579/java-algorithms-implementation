package com.jwetherell.algorithms.numbers;

public class NumericalConversions {
    public static final String toBinaryUsingShiftsAndModulusNC(Object obj) {
        long number = 0;
        long temp = 0;
        if (obj instanceof Integer) {
            number = (int) obj;
            temp = 0;
        } else if (obj instanceof Long) {
            number = (long) obj;
            temp = 0l;
        }
        if (number < 0) throw new IllegalArgumentException("Method argument cannot be negative. number=" + number);
        StringBuilder builder = new StringBuilder();
        while (number > 0) {
            temp = number;
            number = (temp >> 1);
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }
}
