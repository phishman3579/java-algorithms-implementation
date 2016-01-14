package com.jwetherell.algorithms.search.timing;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.search.BinarySearch;
import com.jwetherell.algorithms.search.LinearSearch;
import com.jwetherell.algorithms.search.InterpolationSearch;
import com.jwetherell.algorithms.search.QuickSelect;

public class SearchTiming {

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
            System.out.println("Linear Search.");
            long before = System.nanoTime();
            LinearSearch.find(valueInArray, sorted);
            long after = System.nanoTime();
            System.out.println("Linear Search. found in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            LinearSearch.find(valueNotInArray, sorted);
            after = System.nanoTime();
            System.out.println("Linear Search. not found in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Binary Search.");
            long before = System.nanoTime();
            BinarySearch.find(valueInArray, sorted, false);
            long after = System.nanoTime();
            System.out.println("Binary Search. found in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            BinarySearch.find(valueNotInArray, sorted, false);
            after = System.nanoTime();
            System.out.println("Binary Search. not found in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Optimized Binary Search.");
            long before = System.nanoTime();
            BinarySearch.find(valueInArray, sorted, true);
            long after = System.nanoTime();
            System.out.println("Optimized Binary Search. found in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            BinarySearch.find(valueNotInArray, sorted, true);
            after = System.nanoTime();
            System.out.println("Optimized Binary Search. not found in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Interpolation Search.");
            long before = System.nanoTime();
            InterpolationSearch.find(valueInArray, sorted);
            long after = System.nanoTime();
            System.out.println("Interpolation Search. found in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            InterpolationSearch.find(valueNotInArray, sorted);
            after = System.nanoTime();
            System.out.println("Interpolation Search. not found in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            System.out.println("Quick Select.");
            long before = System.nanoTime();
            QuickSelect.find(valueInArray, sorted);
            long after = System.nanoTime();
            System.out.println("Quick Select. found in " + FORMAT.format(after - before) + " ns");
            before = System.nanoTime();
            QuickSelect.find(valueNotInArray, sorted);
            after = System.nanoTime();
            System.out.println("Quick Select. not found in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }
}
