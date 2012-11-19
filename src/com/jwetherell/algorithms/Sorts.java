package com.jwetherell.algorithms;

import java.text.DecimalFormat;
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


public class Sorts {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");
    private static final int SIZE = 1000;

    private static final boolean showResult = false;
    private static final boolean showComparison = true;
    private static final boolean checkResults = true;

    private static int insertionCount = 0;
    private static final double[] insertionResults = new double[1 * 3];
    private static int bubbleCount = 0;
    private static final double[] bubbleResults = new double[1 * 3];
    private static int shellCount = 0;
    private static final double[] shellResults = new double[1 * 3];
    private static int mergeCount = 0;
    private static final double[] mergeResults = new double[1 * 3];
    private static int quickCount = 0;
    private static final double[] quickResults = new double[3 * 3];
    private static int heapCount = 0;
    private static final double[] heapResults = new double[1 * 3];
    private static int countingCount = 0;
    private static final double[] countingResults = new double[1 * 3];
    private static int radixCount = 0;
    private static final double[] radixResults = new double[1 * 3];
    private static int americanFlagCount = 0;
    private static final double[] americanFlagResults = new double[1 * 3];

    private static final boolean showInsertion = true;
    private static final boolean showBubble = true;
    private static final boolean showShell = true;
    private static final boolean showMerge = true;
    private static final boolean showQuick = true;
    private static final boolean showHeap = true;
    private static final boolean showCounting = true;
    private static final boolean showRadix = true;
    private static final boolean showAmericanFlag = true;

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
        if (showInsertion) {
            // Insertion sort
            long bInsertion = System.currentTimeMillis();
            Integer[] result = InsertionSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("InsertionSort failed.");
            long aInsertion = System.currentTimeMillis();
            double diff = (aInsertion - bInsertion) / 1000d;
            System.out.println("Random: InsertionSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) insertionResults[insertionCount++] = diff;
            System.gc();

            bInsertion = System.currentTimeMillis();
            result = InsertionSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("InsertionSort failed.");
            aInsertion = System.currentTimeMillis();
            diff = (aInsertion - bInsertion) / 1000d;
            System.out.println("Sorted: InsertionSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) insertionResults[insertionCount++] = diff;
            System.gc();

            bInsertion = System.currentTimeMillis();
            result = InsertionSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("InsertionSort failed.");
            aInsertion = System.currentTimeMillis();
            diff = (aInsertion - bInsertion) / 1000d;
            System.out.println("Reverse sorted: InsertionSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) insertionResults[insertionCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showBubble) {
            // Bubble sort
            long bBubble = System.currentTimeMillis();
            Integer[] result = BubbleSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("BubbleSort failed.");
            long aBubble = System.currentTimeMillis();
            double diff = (aBubble - bBubble) / 1000d;
            System.out.println("Random: BubbleSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) bubbleResults[bubbleCount++] = diff;
            System.gc();

            bBubble = System.currentTimeMillis();
            result = BubbleSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("BubbleSort failed.");
            aBubble = System.currentTimeMillis();
            diff = (aBubble - bBubble) / 1000d;
            System.out.println("Sorted: BubbleSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) bubbleResults[bubbleCount++] = diff;
            System.gc();

            bBubble = System.currentTimeMillis();
            result = BubbleSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("BubbleSort failed.");
            aBubble = System.currentTimeMillis();
            diff = (aBubble - bBubble) / 1000d;
            System.out.println("Reverse sorted: BubbleSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) bubbleResults[bubbleCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showShell) {
            int[] shells = new int[] { 10, 5, 3, 1 };
            // Shell's sort
            long bShell = System.currentTimeMillis();
            Integer[] result = ShellSort.sort(shells, unsorted.clone());
            if (checkResults && !check(result)) System.err.println("ShellSort failed.");
            long aShell = System.currentTimeMillis();
            double diff = (aShell - bShell) / 1000d;
            System.out.println("Random: ShellSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) shellResults[shellCount++] = diff;
            System.gc();

            bShell = System.currentTimeMillis();
            result = ShellSort.sort(shells, sorted.clone());
            if (checkResults && !check(result)) System.err.println("ShellSort failed.");
            aShell = System.currentTimeMillis();
            diff = (aShell - bShell) / 1000d;
            System.out.println("Sorted: ShellSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) shellResults[shellCount++] = diff;
            System.gc();

            bShell = System.currentTimeMillis();
            result = ShellSort.sort(shells, reverse.clone());
            if (checkResults && !check(result)) System.err.println("ShellSort failed.");
            aShell = System.currentTimeMillis();
            diff = (aShell - bShell) / 1000d;
            System.out.println("Reverse sorted: ShellSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) shellResults[shellCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showMerge) {
            // Merge sort
            long bMerge = System.currentTimeMillis();
            Integer[] result = MergeSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("MergeSort failed.");
            long aMerge = System.currentTimeMillis();
            double diff = (aMerge - bMerge) / 1000d;
            System.out.println("Random: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) mergeResults[mergeCount++] = diff;
            System.gc();

            bMerge = System.currentTimeMillis();
            result = MergeSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("MergeSort failed.");
            aMerge = System.currentTimeMillis();
            diff = (aMerge - bMerge) / 1000d;
            System.out.println("Sorted: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) mergeResults[mergeCount++] = diff;
            System.gc();

            bMerge = System.currentTimeMillis();
            result = MergeSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("MergeSort failed.");
            aMerge = System.currentTimeMillis();
            diff = (aMerge - bMerge) / 1000d;
            System.out.println("Reverse sorted: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) mergeResults[mergeCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showQuick) {
            // Quicksort
            long bQuick = System.currentTimeMillis();
            Integer[] result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, unsorted.clone());
            if (checkResults && !check(result)) System.err.println("QuickSort failed.");
            long aQuick = System.currentTimeMillis();
            double diff = (aQuick - bQuick) / 1000d;
            System.out.println("Random: QuickSort first element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, sorted.clone());
            if (checkResults && !check(result)) System.err.println("QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Sorted: QuickSort first element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, reverse.clone());
            if (checkResults && !check(result)) System.err.println("QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Reverse sorted: QuickSort first element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, unsorted.clone());
            if (checkResults && !check(result)) System.err.println("QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Random: QuickSort middle element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, sorted.clone());
            if (checkResults && !check(result)) System.err.println("QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Sorted: QuickSort middle element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, reverse.clone());
            if (checkResults && !check(result)) System.err.println("QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Reverse sorted: QuickSort middle element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, unsorted.clone());
            if (checkResults && !check(result)) System.err.println("Random QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Random: Randomized QuickSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, sorted.clone());
            if (checkResults && !check(result)) System.err.println("Random QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Sorted: Randomized QuickSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            bQuick = System.currentTimeMillis();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, reverse.clone());
            if (checkResults && !check(result)) System.err.println("Random QuickSort failed.");
            aQuick = System.currentTimeMillis();
            diff = (aQuick - bQuick) / 1000d;
            System.out.println("Reverse sorted: Randomized QuickSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) quickResults[quickCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showHeap) {
            // Heapsort
            long bHeap = System.currentTimeMillis();
            Integer[] result = HeapSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("HeapSort failed.");
            long aHeap = System.currentTimeMillis();
            double diff = (aHeap - bHeap) / 1000d;
            System.out.println("Random: HeapSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) heapResults[heapCount++] = diff;
            System.gc();

            bHeap = System.currentTimeMillis();
            result = HeapSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("HeapSort failed.");
            aHeap = System.currentTimeMillis();
            diff = (aHeap - bHeap) / 1000d;
            System.out.println("Sorted: HeapSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) heapResults[heapCount++] = diff;
            System.gc();

            bHeap = System.currentTimeMillis();
            result = HeapSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("HeapSort failed.");
            aHeap = System.currentTimeMillis();
            diff = (aHeap - bHeap) / 1000d;
            System.out.println("Reverse sorted: HeapSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) heapResults[heapCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showCounting) {
            // Counting sort
            long bCounting = System.currentTimeMillis();
            Integer[] result = CountingSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("CountingSort failed.");
            long aCounting = System.currentTimeMillis();
            double diff = (aCounting - bCounting) / 1000d;
            System.out.println("Random: CountingSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) countingResults[countingCount++] = diff;
            System.gc();

            bCounting = System.currentTimeMillis();
            result = CountingSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("CountingSort failed.");
            aCounting = System.currentTimeMillis();
            diff = (aCounting - bCounting) / 1000d;
            System.out.println("Sorted: CountingSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) countingResults[countingCount++] = diff;
            System.gc();

            bCounting = System.currentTimeMillis();
            result = CountingSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("CountingSort failed.");
            aCounting = System.currentTimeMillis();
            diff = (aCounting - bCounting) / 1000d;
            System.out.println("Reverse sorted: CountingSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) countingResults[countingCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showRadix) {
            // Radix sort
            long bRadix = System.currentTimeMillis();
            Integer[] result = RadixSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("RadixSort failed.");
            long aRadix = System.currentTimeMillis();
            double diff = (aRadix - bRadix) / 1000d;
            System.out.println("Random: RadixSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) radixResults[radixCount++] = diff;
            System.gc();

            bRadix = System.currentTimeMillis();
            result = RadixSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("RadixSort failed.");
            aRadix = System.currentTimeMillis();
            diff = (aRadix - bRadix) / 1000d;
            System.out.println("Sorted: RadixSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) radixResults[radixCount++] = diff;
            System.gc();

            bRadix = System.currentTimeMillis();
            result = RadixSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("RadixSort failed.");
            aRadix = System.currentTimeMillis();
            diff = (aRadix - bRadix) / 1000d;
            System.out.println("Reverse sorted: RadixSort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) radixResults[radixCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showAmericanFlag) {
            // American Flag sort
            long bRadix = System.currentTimeMillis();
            Integer[] result = AmericanFlagSort.sort(unsorted.clone());
            if (checkResults && !check(result)) System.err.println("AmericanFlag sort failed.");
            long aRadix = System.currentTimeMillis();
            double diff = (aRadix - bRadix) / 1000d;
            System.out.println("Random: AmericanFlag sort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(unsorted, result);
            if (showComparison) americanFlagResults[americanFlagCount++] = diff;
            System.gc();

            bRadix = System.currentTimeMillis();
            result = AmericanFlagSort.sort(sorted.clone());
            if (checkResults && !check(result)) System.err.println("AmericanFlag sort failed.");
            aRadix = System.currentTimeMillis();
            diff = (aRadix - bRadix) / 1000d;
            System.out.println("Sorted: AmericanFlag sort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(sorted, result);
            if (showComparison) americanFlagResults[americanFlagCount++] = diff;
            System.gc();

            bRadix = System.currentTimeMillis();
            result = AmericanFlagSort.sort(reverse.clone());
            if (checkResults && !check(result)) System.err.println("AmericanFlag sort failed.");
            aRadix = System.currentTimeMillis();
            diff = (aRadix - bRadix) / 1000d;
            System.out.println("Reverse sorted: AmericanFlag sort=" + FORMAT.format(diff) + " secs");
            if (showResult) showResult(reverse, result);
            if (showComparison) americanFlagResults[americanFlagCount++] = diff;
            System.gc();

            System.out.println();
            System.out.flush();
        }

        if (showComparison) showComparison();
    }

    private static final void showComparison() {
        System.out.println("Algorithm\t\t\tRandom\tSorted\tReverse Sorted");
        if (showInsertion) {
            int i = 0;
            System.out.println("Insertion sort\t\t\t" + insertionResults[i++] + "\t" + insertionResults[i++] + "\t" + insertionResults[i++]);
        }
        if (showBubble) {
            int i = 0;
            System.out.println("Bubble sort\t\t\t" + bubbleResults[i++] + "\t" + bubbleResults[i++] + "\t" + bubbleResults[i++]);
        }
        if (showShell) {
            int i = 0;
            System.out.println("Shell sort\t\t\t" + shellResults[i++] + "\t" + shellResults[i++] + "\t" + shellResults[i++]);
        }
        if (showMerge) {
            int i = 0;
            System.out.println("Merge sort\t\t\t" + mergeResults[i++] + "\t" + mergeResults[i++] + "\t" + mergeResults[i++]);
        }
        if (showQuick) {
            int i = 0;
            System.out.println("Quicksort with first as pivot\t" + quickResults[i++] + "\t" + quickResults[i++] + "\t" + quickResults[i++]);
            System.out.println("Quicksort with middle as pivot\t" + quickResults[i++] + "\t" + quickResults[i++] + "\t" + quickResults[i++]);
            System.out.println("Quicksort with random as pivot\t" + quickResults[i++] + "\t" + quickResults[i++] + "\t" + quickResults[i++]);
        }
        if (showHeap) {
            int i = 0;
            System.out.println("Heap sort\t\t\t" + heapResults[i++] + "\t" + heapResults[i++] + "\t" + heapResults[i++]);
        }
        if (showCounting) {
            int i = 0;
            System.out.println("Counting sort\t\t\t" + countingResults[i++] + "\t" + countingResults[i++] + "\t" + countingResults[i++]);
        }
        if (showRadix) {
            int i = 0;
            System.out.println("Radix sort\t\t\t" + radixResults[i++] + "\t" + radixResults[i++] + "\t" + radixResults[i++]);
        }
        if (showAmericanFlag) {
            int i = 0;
            System.out.println("American Flag sort\t\t" + americanFlagResults[i++] + "\t" + americanFlagResults[i++] + "\t" + americanFlagResults[i++]);
        }
    }

    private static final void showResult(Integer[] unsorted, Integer[] result) {
        System.out.println("Unsorted: " + print(unsorted));
        System.out.println("Sorted: " + print(result));
        System.out.flush();
    }

    private static final boolean check(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) return false;
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
            if (i == pivotIndex) builder.append("`" + e + "` ");
            else builder.append(e + " ");
        }
        return builder.toString();
    }
}
