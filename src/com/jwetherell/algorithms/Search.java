package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.search.BinarySearch;
import com.jwetherell.algorithms.search.LinearSearch;
import com.jwetherell.algorithms.search.InterpolationSearch;
import com.jwetherell.algorithms.search.QuickSelect;


public class Search {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");
    private static final int SIZE = 9999;
    private static final int offset = 123;

    private static int[] sorted = null;

    public static void main(String[] args) {
        System.out.println("Generating sorted array.");
        sorted = new int[SIZE];
        for (int i = offset; i < offset + sorted.length; i++) {
            sorted[i - offset] = i;
        }
        System.out.println("Generated sorted array.");
        System.out.println();

        int valueInArray = sorted[SIZE - (SIZE / 4)];
        int valueNotInArray = sorted[SIZE - 1] + offset;

        {
            System.out.println("Brute Force.");
            long before = System.nanoTime();
            int result = LinearSearch.find(valueInArray, sorted);
            long after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            result = LinearSearch.find(valueNotInArray, sorted);
            after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Binary Search.");
            long before = System.nanoTime();
            int result = BinarySearch.find(valueInArray, sorted, false);
            long after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            result = BinarySearch.find(valueNotInArray, sorted, false);
            after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Optimized Binary Search.");
            long before = System.nanoTime();
            int result = BinarySearch.find(valueInArray, sorted, true);
            long after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            result = BinarySearch.find(valueNotInArray, sorted, true);
            after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Interpolation Search.");
            long before = System.nanoTime();
            int result = InterpolationSearch.find(valueInArray, sorted);
            long after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            result = InterpolationSearch.find(valueNotInArray, sorted);
            after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Quick Select");
            long before = System.nanoTime();
            int result = QuickSelect.find(valueInArray, sorted);
            long after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            result = QuickSelect.find(valueNotInArray, sorted);
            after = System.nanoTime();
            System.out.println("result=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }

}
