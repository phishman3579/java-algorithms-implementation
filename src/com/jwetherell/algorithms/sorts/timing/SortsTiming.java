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
    private static final boolean showResult = false;
    private static final boolean checkResults = true;

    private static int insertionCount = 0;
    private static double[] insertionResults = null;
    private static int bubbleCount = 0;
    private static double[] bubbleResults = null;
    private static int shellCount = 0;
    private static double[] shellResults = null;
    private static int mergeCount = 0;
    private static double[] mergeResults = null;
    private static int quickCount = 0;
    private static double[] quickResults = null;
    private static int heapCount = 0;
    private static double[] heapResults = null;
    private static int countingCount = 0;
    private static double[] countingResults = null;
    private static int radixCount = 0;
    private static double[] radixResults = null;
    private static int americanFlagCount = 0;
    private static double[] americanFlagResults = null;

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

        insertionResults = new double[1 * 3];
        insertionCount = runTest(new Insertion(), unsorted, sorted, insertionResults, insertionCount);
        showComparison();

        bubbleResults = new double[1 * 3];
        bubbleCount = runTest(new Bubble(), unsorted, sorted, bubbleResults, bubbleCount);
        showComparison();

        shellResults = new double[1 * 3];
        shellCount = runTest(new Shell(), unsorted, sorted, shellResults, shellCount);
        showComparison();

        mergeResults = new double[2 * 3];
        mergeCount = runTest(new MergeNotInPlace(), unsorted, sorted, mergeResults, mergeCount);
        mergeCount = runTest(new MergeInPlace(), unsorted, sorted, mergeResults, mergeCount);
        showComparison();

        quickResults = new double[3 * 3];
        quickCount = runTest(new QuickFirst(), unsorted, sorted, quickResults, quickCount);
        quickCount = runTest(new QuickMiddle(), unsorted, sorted, quickResults, quickCount);
        quickCount = runTest(new QuickRandom(), unsorted, sorted, quickResults, quickCount);
        showComparison();

        heapResults = new double[1 * 3];
        heapCount = runTest(new Heap(), unsorted, sorted, heapResults, heapCount);
        showComparison();

        countingResults = new double[1 * 3];
        countingCount = runTest(new Counting(), unsorted, sorted, countingResults, countingCount);
        showComparison();

        radixResults = new double[1 * 3];
        radixCount = runTest(new Radix(), unsorted, sorted, radixResults, radixCount);
        showComparison();

        americanFlagResults = new double[1 * 3];
        americanFlagCount = runTest(new AmericanFlag(), unsorted, sorted, americanFlagResults, americanFlagCount);
        showComparison();
    }

    private static final int runTest(Testable testable, Integer[] unsorted, Integer[] sorted, double[] results, int count) {
        long bInsertion = System.nanoTime();
        Integer[] result = testable.sort(unsorted.clone());
        if (checkResults && !check(result))
            System.err.println(testable.getName()+" failed.");
        long aInsertion = System.nanoTime();
        double diff = (aInsertion - bInsertion) / 1000000d / 1000d;
        System.out.println("Random: "+testable.getName()+"=" + FORMAT.format(diff) + " secs");
        if (showResult)
            showResult(unsorted, result);
        results[count++] = diff;
        putOutTheGarbage();

        bInsertion = System.nanoTime();
        result = InsertionSort.sort(sorted.clone());
        if (checkResults && !check(result))
            System.err.println(testable.getName()+" failed.");
        aInsertion = System.nanoTime();
        diff = (aInsertion - bInsertion) / 1000000d / 1000d;
        System.out.println("Sorted: "+testable.getName()+"=" + FORMAT.format(diff) + " secs");
        if (showResult)
            showResult(sorted, result);
        results[count++] = diff;
        putOutTheGarbage();

        bInsertion = System.nanoTime();
        result = InsertionSort.sort(reverse.clone());
        if (checkResults && !check(result))
            System.err.println(testable.getName()+"InsertionSort.");
        aInsertion = System.nanoTime();
        diff = (aInsertion - bInsertion) / 1000000d / 1000d;
        System.out.println("Reverse sorted: "+testable.getName()+"=" + FORMAT.format(diff) + " secs");
        if (showResult)
            showResult(reverse, result);
        results[count++] = diff;
        putOutTheGarbage();

        System.out.println();
        System.out.flush();

        return count;
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
        if (insertionResults!=null) {
            int i = 0;
            formatter.format(format, "Insertion sort", FORMAT.format(insertionResults[i++]), FORMAT.format(insertionResults[i++]), FORMAT.format(insertionResults[i++]));
        }
        if (bubbleResults!=null) {
            int i = 0;
            formatter.format(format, "Bubble sort", FORMAT.format(bubbleResults[i++]), FORMAT.format(bubbleResults[i++]), FORMAT.format(bubbleResults[i++]));
        }
        if (shellResults!=null) {
            int i = 0;
            formatter.format(format, "Shell sort", FORMAT.format(shellResults[i++]), FORMAT.format(shellResults[i++]), FORMAT.format(shellResults[i++]));
        }
        if (mergeResults!=null) {
            int i = 0;
            formatter.format(format, "Merge (in-place) sort", FORMAT.format(mergeResults[i++]), FORMAT.format(mergeResults[i++]), FORMAT.format(mergeResults[i++]));
            formatter.format(format, "Merge (not-in-place) sort", FORMAT.format(mergeResults[i++]), FORMAT.format(mergeResults[i++]), FORMAT.format(mergeResults[i++]));
        }
        if (quickResults!=null) {
            int i = 0;
            formatter.format(format, "Quicksort with first as pivot", FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]));
            formatter.format(format, "Quicksort with middle as pivot", FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]));
            formatter.format(format, "Quicksort with random as pivot", FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]));
        }
        if (heapResults!=null) {
            int i = 0;
            formatter.format(format, "Heap sort", FORMAT.format(heapResults[i++]), FORMAT.format(heapResults[i++]), FORMAT.format(heapResults[i++]));
        }
        if (countingResults!=null) {
            int i = 0;
            formatter.format(format, "Counting sort", FORMAT.format(countingResults[i++]), FORMAT.format(countingResults[i++]), FORMAT.format(countingResults[i++]));
        }
        if (radixResults!=null) {
            int i = 0;
            formatter.format(format, "Radix sort", FORMAT.format(radixResults[i++]), FORMAT.format(radixResults[i++]), FORMAT.format(radixResults[i++]));
        }
        if (americanFlagResults!=null) {
            int i = 0;
            formatter.format(format, "American Flag sort", FORMAT.format(americanFlagResults[i++]), FORMAT.format(americanFlagResults[i++]), FORMAT.format(americanFlagResults[i++]));
        }
        formatter.close();
        System.out.println(resultsBuilder.toString());
    }

    private static final void showResult(Integer[] u, Integer[] r) {
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
