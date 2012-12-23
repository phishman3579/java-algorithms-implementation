package com.jwetherell.algorithms.search;

public class LinearSearch {

    public static final int find(int value, int[] array) {
        for (int i = 0; i < array.length; i++) {
            int iValue = array[i];
            if (value == iValue)
                return i;
        }
        return Integer.MAX_VALUE;
    }
}
