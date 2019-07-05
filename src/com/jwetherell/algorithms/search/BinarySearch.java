package com.jwetherell.algorithms.search;

/**
 * In computer science, binary search, also known as half-interval search or logarithmic search, is a search algorithm that finds the position of a target value within a sorted array. Binary search 
 * compares the target value to the middle element of the array; if they are unequal, the half in which the target cannot lie is eliminated and the search continues on the remaining half until it is 
 * successful or the remaining half is empty.
 * <p>
 * Worst-case performance      O(log n)<br>
 * Best-case performance       O(1)<br>
 * Average performance         O(log n)<br>
 * Worst-case space complexity O(1)<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Binary_search_algorithm">Binary Search (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
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


    public static final int find(int value, int[] array) {
        BinarySearch.sorted = array;
        try {
            return loopFind(value,0,BinarySearch.sorted.length-1);
        } finally {
            BinarySearch.sorted = null;
        }
    }

    private static int loopFind(int value, int start, int end){
        int mid = (start + end) / 2;
        while(start != end) {
            if(sorted[mid] > value) {
                end = mid - 1;
            }
            else if(sorted[mid] < value) {
                start = mid + 1;
            }
            else return mid;
            mid = (start + end) / 2;
        }
        if(sorted[start] == value) return start;
        return Integer.MAX_VALUE;
    }

    private static int recursiveFind(int value, int start, int end, boolean optimize) {
        if (start == end) {
            int lastValue = sorted[start]; // start==end
            if (value == lastValue)
                return start; // start==end
            return Integer.MAX_VALUE;
        }

        final int low = start;
        final int high = end + 1; // zero indexed, so add one.
        final int middle = low + ((high - low) / 2);

        final int middleValue = sorted[middle];
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
