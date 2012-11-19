package com.jwetherell.algorithms.sequence;

public class TotalOfSequence {

    public static final long sequenceTotalUsingLoop(int start, int length) {
        long result = 0L;
        while (length > 0) {
            result += start++;
            length--;
        }
        return result;
    }

    public static final long sequenceTotalUsingTriangularNumbers(int start, int length) {
        // n*(n+1)/2
        long result = length * (length + 1) / 2;
        result += (start - 1) * length;
        return result;
    }

}
