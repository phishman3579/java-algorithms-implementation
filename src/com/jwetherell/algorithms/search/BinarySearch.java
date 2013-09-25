package com.jwetherell.algorithms.search;

public class BinarySearch {

    private static final int SWITCH_TO_BRUTE_FORCE = 200;

    private static int[] sorted = null;

    // Assuming the array is sorted
    public static final int find(int value, int[] array, boolean optimize) {
        BinarySearch.sorted = array;
        try {
            return recursiveFind(value, 0, BinarySearch.sorted.length - 1, optimize);
        } finally {
            BinarySearch.sorted = null;
        }
    }

    private static int recursiveFind(int value, int start, int end, boolean optimize) {
        if (start == end) {
            int lastValue = sorted[start]; // start==end
            if (value == lastValue)
                return start; // start==end
            return Integer.MAX_VALUE;
        }

        int low = start;
        int high = end + 1; // zero indexed, so add one.
        int middle = low + ((high - low) / 2);

        int middleValue = sorted[middle];
        if (value == middleValue)
            return middle;
        if (value > middleValue) {
            if (optimize && (end - middle) <= SWITCH_TO_BRUTE_FORCE)
                return linearSearch(value, middle + 1, end);
            return recursiveFind(value, middle + 1, end, optimize);
        }
        if (optimize && (end - middle) <= SWITCH_TO_BRUTE_FORCE)
            return linearSearch(value, start, middle - 1);
        return recursiveFind(value, start, middle - 1, optimize);
    }

    private static final int linearSearch(int value, int start, int end) {
        for (int i = start; i <= end; i++) {
            int iValue = sorted[i];
            if (value == iValue)
                return i;
        }
        return Integer.MAX_VALUE;
    }
}
