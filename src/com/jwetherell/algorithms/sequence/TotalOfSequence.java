package com.jwetherell.algorithms.sequence;

public class TotalOfSequence {

    public static final long sequenceTotalUsingLoop(int number, int size) {
        int start = number;
        int length = size;
        long result = 0L;
        while (length > 0) {
            result += start++;
            length--;
        }
        return result;
    }

    public static final long sequenceTotalUsingTriangularNumbers(int number, int size) {
        // n*(n+1)/2
        int start = number;
        int length = size;
        long result = length * (length + 1) / 2;
        result += (start - 1) * length;
        return result;
    }
}
