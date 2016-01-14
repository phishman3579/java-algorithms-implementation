package com.jwetherell.algorithms.sorts.timing;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;
import java.util.Random;

import com.jwetherell.algorithms.sorts.AmericanFlagSort;
import com.jwetherell.algorithms.sorts.BubbleSort;
import com.jwetherell.algorithms.sorts.CountingSort;
import com.jwetherell.algorithms.sorts.HeapSort;
import com.jwetherell.algorithms.sorts.InsertionSort;
import com.jwetherell.algorithms.sorts.MergeSort;
import com.jwetherell.algorithms.sorts.QuickSort;
import com.jwetherell.algorithms.sorts.RadixSort;
import com.jwetherell.algorithms.sorts.ShellSort;

public class SortsTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.###");
    private static final int SIZE = 1024*100;
    private static final boolean showResultingArray = false;
    private static final boolean checkResults = true;

    private static int count = 0;
    private static String[] names = new String[SIZE];
    private static double[][] results = new double[SIZE][];

    private static Integer[] unsorted = null;
    private static Integer[] sorted = null;
    private static Integer[] reverse = null;

    public static void main(String[] args) {
        System.out.println("Generating random array.");
        Random random = new Random();
        unsorted = new Integer[SIZE];
        int i = 0;
        while (i < unsorted.length) {
            int j = random.nextInt(unsorted.length * 10);
            unsorted[i++] = j;
        }
        System.out.println("Generated random array.");

        System.out.println("Generating sorted array.");
        sorted = new Integer[SIZE];
        for (i = 0; i < sorted.length; i++) {
            sorted[i] = i;
        }
        System.out.println("Generated sorted array.");

        System.out.println("Generating reverse sorted array.");
        reverse = new Integer[SIZE];
        for (i = (reverse.length - 1); i >= 0; i--) {
            reverse[i] = (SIZE - 1) - i;
        }
        System.out.println("Generated reverse sorted array.");
        System.out.println();
        System.out.flush();

        System.out.println("Starting sorts...");
        System.out.println();
        System.out.flush();

        results[count] = new double[1 * 3];
        count = runTest(new Insertion(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new Bubble(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new Shell(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new MergeInPlace(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new MergeNotInPlace(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new QuickFirst(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new QuickMiddle(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new QuickRandom(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new Heap(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new Counting(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new Radix(), unsorted, sorted, names, results[count], count);
        showComparison();

        results[count] = new double[1 * 3];
        count = runTest(new AmericanFlag(), unsorted, sorted, names, results[count], count);
        showComparison();
    }

    private static final int runTest(Testable testable, Integer[] unsorted, Integer[] sorted, String[] names, double[] results, int count) {
        names[count] = testable.getName();

        long bInsertion = System.nanoTime();
        Integer[] result = testable.sort(unsorted.clone());
        if (checkResults && !check(result))
            System.err.println(testable.getName()+" failed.");
        long aInsertion = System.nanoTime();
        double diff = (aInsertion - bInsertion) / 1000000d / 1000d;
        System.out.println("Random: "+testable.getName()+"=" + FORMAT.format(diff) + " secs");
        if (showResultingArray)
            showResultingArray(unsorted, result);
        results[0] = diff;
        putOutTheGarbage();

        bInsertion = System.nanoTime();
        result = testable.sort(sorted.clone());
        if (checkResults && !check(result))
            System.err.println(testable.getName()+" failed.");
        aInsertion = System.nanoTime();
        diff = (aInsertion - bInsertion) / 1000000d / 1000d;
        System.out.println("Sorted: "+testable.getName()+"=" + FORMAT.format(diff) + " secs");
        if (showResultingArray)
            showResultingArray(sorted, result);
        results[1] = diff;
        putOutTheGarbage();

        bInsertion = System.nanoTime();
        result = testable.sort(reverse.clone());
        if (checkResults && !check(result))
            System.err.println(testable.getName()+" failed.");
        aInsertion = System.nanoTime();
        diff = (aInsertion - bInsertion) / 1000000d / 1000d;
        System.out.println("Reverse sorted: "+testable.getName()+"=" + FORMAT.format(diff) + " secs");
        if (showResultingArray)
            showResultingArray(reverse, result);
        results[2] = diff;
        putOutTheGarbage();

        System.out.println();
        System.out.flush();

        return count+1;
    }

    public static abstract class Testable {
        String input = null;
        public String getInput() {
            return input;
        }
        public abstract String getName();
        public abstract Integer[] sort(Integer[] input);
    }

    private static class AmericanFlag extends Testable {
        @Override
        public String getName() {
            return "AmericanFlag sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return AmericanFlagSort.sort(input);
        }
    }

    private static class Bubble extends Testable {
        @Override
        public String getName() {
            return "Bubble sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return BubbleSort.sort(input);
        }
    }

    private static class Counting extends Testable {
        @Override
        public String getName() {
            return "Counting sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return CountingSort.sort(input);
        }
    }

    private static class Heap extends Testable {
        @Override
        public String getName() {
            return "Heap sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return HeapSort.sort(input);
        }
    }

    private static class Insertion extends Testable {
        @Override
        public String getName() {
            return "Insertion sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return InsertionSort.sort(input);
        }
    }

    private static class MergeInPlace extends Testable {
        @Override
        public String getName() {
            return "MergeInPlace sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, input);
        }
    }

    private static class MergeNotInPlace extends Testable {
        @Override
        public String getName() {
            return "MergeInPlace sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, input);
        }
    }

    private static class QuickFirst extends Testable {
        @Override
        public String getName() {
            return "Quick (first) sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, input);
        }
    }

    private static class QuickMiddle extends Testable {
        @Override
        public String getName() {
            return "Quick (middle) sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, input);
        }
    }

    private static class QuickRandom extends Testable {
        @Override
        public String getName() {
            return "Quick (random) sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, input);
        }
    }

    private static class Radix extends Testable {
        @Override
        public String getName() {
            return "Radix sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return RadixSort.sort(input);
        }
    }

    private static class Shell extends Testable {
        int[] shells = new int[] { 10, 5, 3, 1 };
 
        @Override
        public String getName() {
            return "Shell sort";
        }

        @Override
        public Integer[] sort(Integer[] input) {
            return ShellSort.sort(shells, input);
        }
    }

    private static final void showComparison() {
        StringBuilder resultsBuilder = new StringBuilder();
        resultsBuilder.append("Number of integers = ").append(SIZE).append("\n");
        String format = "%-32s%-15s%-15s%-15s\n";
        Formatter formatter = new Formatter(resultsBuilder, Locale.US);

        formatter.format(format, "Algorithm","Random","Sorted","Reverse Sorted");
        for (int i=0; i<names.length; i++) {
            if (names[i]==null)
                break;
            formatter.format(format, names[i], FORMAT.format(results[i][0]), FORMAT.format(results[i][1]), FORMAT.format(results[i][2]));
        }
        formatter.close();
        System.out.println(resultsBuilder.toString());
    }

    private static final void showResultingArray(Integer[] u, Integer[] r) {
        System.out.println("Unsorted: " + print(u));
        System.out.println("Sorted: " + print(r));
        System.out.flush();
    }

    private static final boolean check(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i])
                return false;
        }
        return true;
    }

    public static final String print(Integer[] array) {
        return print(array, 0, array.length);
    }

    public static final String print(Integer[] array, int start, int length) {
        final Integer[] clone = array.clone();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int e = clone[start + i];
            builder.append(e + " ");
        }
        return builder.toString();
    }

    public static final String printWithPivot(Integer[] array, int pivotIndex, int start, int length) {
        final Integer[] clone = array.clone();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int e = clone[start + i];
            if (i == pivotIndex)
                builder.append("`" + e + "` ");
            else
                builder.append(e + " ");
        }
        return builder.toString();
    }

    private static final void putOutTheGarbage() {
        collectGarbage();
        collectGarbage();
        collectGarbage();
    }

    private static final long fSLEEP_INTERVAL = 100;

    private static final void collectGarbage() {
        try {
            System.gc();
            System.gc();
            System.gc();
            Thread.sleep(fSLEEP_INTERVAL);
            System.runFinalization();
            Thread.sleep(fSLEEP_INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
