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
    private static final int SIZE = 100000;

    private static final boolean showResult = false;
    private static final boolean showComparison = true;
    private static final boolean checkResults = true;

    private static int insertionCount = 0;
    private static final double[] insertionResults = new double[1 * 3];
    private static int bubbleCount = 0;
    private static final double[] bubbleResults = new double[1 * 3];
    private static int shellCount = 0;
    private static final double[] shellResults = new double[1 * 3];
    private static int mergeInPlaceCount = 0;
    private static final double[] mergeInPlaceResults = new double[1 * 3];
    private static int mergeNotInPlaceCount = 0;
    private static final double[] mergeNotInPlaceResults = new double[1 * 3];
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
    private static final boolean showMergeInPlace = true;
    private static final boolean showMergeNotInPlace = true;
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
            long bInsertion = System.nanoTime();
            Integer[] result = InsertionSort.sort(unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("InsertionSort failed.");
            long aInsertion = System.nanoTime();
            double diff = (aInsertion - bInsertion) / 1000000d / 1000d;
            System.out.println("Random: InsertionSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                insertionResults[insertionCount++] = diff;
            putOutTheGarbage();

            bInsertion = System.nanoTime();
            result = InsertionSort.sort(sorted.clone());
            if (checkResults && !check(result))
                System.err.println("InsertionSort failed.");
            aInsertion = System.nanoTime();
            diff = (aInsertion - bInsertion) / 1000000d / 1000d;
            System.out.println("Sorted: InsertionSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                insertionResults[insertionCount++] = diff;
            putOutTheGarbage();

            bInsertion = System.nanoTime();
            result = InsertionSort.sort(reverse.clone());
            if (checkResults && !check(result))
                System.err.println("InsertionSort failed.");
            aInsertion = System.nanoTime();
            diff = (aInsertion - bInsertion) / 1000000d / 1000d;
            System.out.println("Reverse sorted: InsertionSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                insertionResults[insertionCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showBubble) {
            // Bubble sort
            long bBubble = System.nanoTime();
            Integer[] result = BubbleSort.sort(unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("BubbleSort failed.");
            long aBubble = System.nanoTime();
            double diff = (aBubble - bBubble) / 1000000d / 1000d;
            System.out.println("Random: BubbleSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                bubbleResults[bubbleCount++] = diff;
            putOutTheGarbage();

            bBubble = System.nanoTime();
            result = BubbleSort.sort(sorted.clone());
            if (checkResults && !check(result))
                System.err.println("BubbleSort failed.");
            aBubble = System.nanoTime();
            diff = (aBubble - bBubble) / 1000000d / 1000d;
            System.out.println("Sorted: BubbleSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                bubbleResults[bubbleCount++] = diff;
            putOutTheGarbage();

            bBubble = System.nanoTime();
            result = BubbleSort.sort(reverse.clone());
            if (checkResults && !check(result))
                System.err.println("BubbleSort failed.");
            aBubble = System.nanoTime();
            diff = (aBubble - bBubble) / 1000000d / 1000d;
            System.out.println("Reverse sorted: BubbleSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                bubbleResults[bubbleCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showShell) {
            int[] shells = new int[] { 10, 5, 3, 1 };
            // Shell's sort
            long bShell = System.nanoTime();
            Integer[] result = ShellSort.sort(shells, unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("ShellSort failed.");
            long aShell = System.nanoTime();
            double diff = (aShell - bShell) / 1000000d / 1000d;
            System.out.println("Random: ShellSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                shellResults[shellCount++] = diff;
            putOutTheGarbage();

            bShell = System.nanoTime();
            result = ShellSort.sort(shells, sorted.clone());
            if (checkResults && !check(result))
                System.err.println("ShellSort failed.");
            aShell = System.nanoTime();
            diff = (aShell - bShell) / 1000000d / 1000d;
            System.out.println("Sorted: ShellSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                shellResults[shellCount++] = diff;
            putOutTheGarbage();

            bShell = System.nanoTime();
            result = ShellSort.sort(shells, reverse.clone());
            if (checkResults && !check(result))
                System.err.println("ShellSort failed.");
            aShell = System.nanoTime();
            diff = (aShell - bShell) / 1000000d / 1000d;
            System.out.println("Reverse sorted: ShellSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                shellResults[shellCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showMergeNotInPlace) {
            // Merge sort
            long bMerge = System.nanoTime();
            Integer[] result = MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("MergeSort failed.");
            long aMerge = System.nanoTime();
            double diff = (aMerge - bMerge) / 1000000d / 1000d;
            System.out.println("Random: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                mergeNotInPlaceResults[mergeNotInPlaceCount++] = diff;
            putOutTheGarbage();

            bMerge = System.nanoTime();
            result = MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, sorted.clone());
            if (checkResults && !check(result))
                System.err.println("MergeSort failed.");
            aMerge = System.nanoTime();
            diff = (aMerge - bMerge) / 1000000d / 1000d;
            System.out.println("Sorted: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                mergeNotInPlaceResults[mergeNotInPlaceCount++] = diff;
            putOutTheGarbage();

            bMerge = System.nanoTime();
            result = MergeSort.sort(MergeSort.SPACE_TYPE.NOT_IN_PLACE, reverse.clone());
            if (checkResults && !check(result))
                System.err.println("MergeSort failed.");
            aMerge = System.nanoTime();
            diff = (aMerge - bMerge) / 1000000d / 1000d;
            System.out.println("Reverse sorted: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                mergeNotInPlaceResults[mergeNotInPlaceCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showMergeInPlace) {
            // Merge sort
            long bMerge = System.nanoTime();
            Integer[] result = MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("MergeSort failed.");
            long aMerge = System.nanoTime();
            double diff = (aMerge - bMerge) / 1000000d / 1000d;
            System.out.println("Random: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                mergeInPlaceResults[mergeInPlaceCount++] = diff;
            putOutTheGarbage();

            bMerge = System.nanoTime();
            result = MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, sorted.clone());
            if (checkResults && !check(result))
                System.err.println("MergeSort failed.");
            aMerge = System.nanoTime();
            diff = (aMerge - bMerge) / 1000000d / 1000d;
            System.out.println("Sorted: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                mergeInPlaceResults[mergeInPlaceCount++] = diff;
            putOutTheGarbage();

            bMerge = System.nanoTime();
            result = MergeSort.sort(MergeSort.SPACE_TYPE.IN_PLACE, reverse.clone());
            if (checkResults && !check(result))
                System.err.println("MergeSort failed.");
            aMerge = System.nanoTime();
            diff = (aMerge - bMerge) / 1000000d / 1000d;
            System.out.println("Reverse sorted: MergeSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                mergeInPlaceResults[mergeInPlaceCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showQuick) {
            // Quicksort
            long bQuick = System.nanoTime();
            Integer[] result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("QuickSort failed.");
            long aQuick = System.nanoTime();
            double diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Random: QuickSort first element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, sorted.clone());
            if (checkResults && !check(result))
                System.err.println("QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Sorted: QuickSort first element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.FIRST, reverse.clone());
            if (checkResults && !check(result))
                System.err.println("QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Reverse sorted: QuickSort first element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Random: QuickSort middle element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, sorted.clone());
            if (checkResults && !check(result))
                System.err.println("QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Sorted: QuickSort middle element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.MIDDLE, reverse.clone());
            if (checkResults && !check(result))
                System.err.println("QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Reverse sorted: QuickSort middle element pivot=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("Random QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Random: Randomized QuickSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, sorted.clone());
            if (checkResults && !check(result))
                System.err.println("Random QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Sorted: Randomized QuickSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            bQuick = System.nanoTime();
            result = QuickSort.sort(QuickSort.PIVOT_TYPE.RANDOM, reverse.clone());
            if (checkResults && !check(result))
                System.err.println("Random QuickSort failed.");
            aQuick = System.nanoTime();
            diff = (aQuick - bQuick) / 1000000d / 1000d;
            System.out.println("Reverse sorted: Randomized QuickSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                quickResults[quickCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showHeap) {
            // Heapsort
            long bHeap = System.nanoTime();
            Integer[] result = HeapSort.sort(unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("HeapSort failed.");
            long aHeap = System.nanoTime();
            double diff = (aHeap - bHeap) / 1000000d / 1000d;
            System.out.println("Random: HeapSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                heapResults[heapCount++] = diff;
            putOutTheGarbage();

            bHeap = System.nanoTime();
            result = HeapSort.sort(sorted.clone());
            if (checkResults && !check(result))
                System.err.println("HeapSort failed.");
            aHeap = System.nanoTime();
            diff = (aHeap - bHeap) / 1000000d / 1000d;
            System.out.println("Sorted: HeapSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                heapResults[heapCount++] = diff;
            putOutTheGarbage();

            bHeap = System.nanoTime();
            result = HeapSort.sort(reverse.clone());
            if (checkResults && !check(result))
                System.err.println("HeapSort failed.");
            aHeap = System.nanoTime();
            diff = (aHeap - bHeap) / 1000000d / 1000d;
            System.out.println("Reverse sorted: HeapSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                heapResults[heapCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showCounting) {
            // Counting sort
            long bCounting = System.nanoTime();
            Integer[] result = CountingSort.sort(unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("CountingSort failed.");
            long aCounting = System.nanoTime();
            double diff = (aCounting - bCounting) / 1000000d / 1000d;
            System.out.println("Random: CountingSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                countingResults[countingCount++] = diff;
            putOutTheGarbage();

            bCounting = System.nanoTime();
            result = CountingSort.sort(sorted.clone());
            if (checkResults && !check(result))
                System.err.println("CountingSort failed.");
            aCounting = System.nanoTime();
            diff = (aCounting - bCounting) / 1000000d / 1000d;
            System.out.println("Sorted: CountingSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                countingResults[countingCount++] = diff;
            putOutTheGarbage();

            bCounting = System.nanoTime();
            result = CountingSort.sort(reverse.clone());
            if (checkResults && !check(result))
                System.err.println("CountingSort failed.");
            aCounting = System.nanoTime();
            diff = (aCounting - bCounting) / 1000000d / 1000d;
            System.out.println("Reverse sorted: CountingSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                countingResults[countingCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showRadix) {
            // Radix sort
            long bRadix = System.nanoTime();
            Integer[] result = RadixSort.sort(unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("RadixSort failed.");
            long aRadix = System.nanoTime();
            double diff = (aRadix - bRadix) / 1000000d / 1000d;
            System.out.println("Random: RadixSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                radixResults[radixCount++] = diff;
            putOutTheGarbage();

            bRadix = System.nanoTime();
            result = RadixSort.sort(sorted.clone());
            if (checkResults && !check(result))
                System.err.println("RadixSort failed.");
            aRadix = System.nanoTime();
            diff = (aRadix - bRadix) / 1000000d / 1000d;
            System.out.println("Sorted: RadixSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                radixResults[radixCount++] = diff;
            putOutTheGarbage();

            bRadix = System.nanoTime();
            result = RadixSort.sort(reverse.clone());
            if (checkResults && !check(result))
                System.err.println("RadixSort failed.");
            aRadix = System.nanoTime();
            diff = (aRadix - bRadix) / 1000000d / 1000d;
            System.out.println("Reverse sorted: RadixSort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                radixResults[radixCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showAmericanFlag) {
            // American Flag sort
            long bRadix = System.nanoTime();
            Integer[] result = AmericanFlagSort.sort(unsorted.clone());
            if (checkResults && !check(result))
                System.err.println("AmericanFlag sort failed.");
            long aRadix = System.nanoTime();
            double diff = (aRadix - bRadix) / 1000000d / 1000d;
            System.out.println("Random: AmericanFlag sort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(unsorted, result);
            if (showComparison)
                americanFlagResults[americanFlagCount++] = diff;
            putOutTheGarbage();

            bRadix = System.nanoTime();
            result = AmericanFlagSort.sort(sorted.clone());
            if (checkResults && !check(result))
                System.err.println("AmericanFlag sort failed.");
            aRadix = System.nanoTime();
            diff = (aRadix - bRadix) / 1000000d / 1000d;
            System.out.println("Sorted: AmericanFlag sort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(sorted, result);
            if (showComparison)
                americanFlagResults[americanFlagCount++] = diff;
            putOutTheGarbage();

            bRadix = System.nanoTime();
            result = AmericanFlagSort.sort(reverse.clone());
            if (checkResults && !check(result))
                System.err.println("AmericanFlag sort failed.");
            aRadix = System.nanoTime();
            diff = (aRadix - bRadix) / 1000000d / 1000d;
            System.out.println("Reverse sorted: AmericanFlag sort=" + FORMAT.format(diff) + " secs");
            if (showResult)
                showResult(reverse, result);
            if (showComparison)
                americanFlagResults[americanFlagCount++] = diff;
            putOutTheGarbage();

            System.out.println();
            System.out.flush();
        }

        if (showComparison)
            showComparison();
    }

    private static final void showComparison() {
        StringBuilder resultsBuilder = new StringBuilder();
        String format = "%-32s%-15s%-15s%-15s\n";
        Formatter formatter = new Formatter(resultsBuilder, Locale.US);

        formatter.format(format, "Algorithm","Random","Sorted","Reverse Sorted");
        if (showInsertion) {
            int i = 0;
            formatter.format(format, "Insertion sort", FORMAT.format(insertionResults[i++]), FORMAT.format(insertionResults[i++]), FORMAT.format(insertionResults[i++]));
        }
        if (showBubble) {
            int i = 0;
            formatter.format(format, "Bubble sort", FORMAT.format(bubbleResults[i++]), FORMAT.format(bubbleResults[i++]), FORMAT.format(bubbleResults[i++]));
        }
        if (showShell) {
            int i = 0;
            formatter.format(format, "Shell sort", FORMAT.format(shellResults[i++]), FORMAT.format(shellResults[i++]), FORMAT.format(shellResults[i++]));
        }
        if (showMergeInPlace) {
            int i = 0;
            formatter.format(format, "Merge (in-place) sort", FORMAT.format(mergeInPlaceResults[i++]), FORMAT.format(mergeInPlaceResults[i++]), FORMAT.format(mergeInPlaceResults[i++]));
        }
        if (showMergeNotInPlace) {
            int i = 0;
            formatter.format(format, "Merge (not-in-place) sort", FORMAT.format(mergeNotInPlaceResults[i++]), FORMAT.format(mergeNotInPlaceResults[i++]), FORMAT.format(mergeNotInPlaceResults[i++]));
        }
        if (showQuick) {
            int i = 0;
            formatter.format(format, "Quicksort with first as pivot", FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]));
            formatter.format(format, "Quicksort with middle as pivot", FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]));
            formatter.format(format, "Quicksort with random as pivot", FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]), FORMAT.format(quickResults[i++]));
        }
        if (showHeap) {
            int i = 0;
            formatter.format(format, "Heap sort", FORMAT.format(heapResults[i++]), FORMAT.format(heapResults[i++]), FORMAT.format(heapResults[i++]));
        }
        if (showCounting) {
            int i = 0;
            formatter.format(format, "Counting sort", FORMAT.format(countingResults[i++]), FORMAT.format(countingResults[i++]), FORMAT.format(countingResults[i++]));
        }
        if (showRadix) {
            int i = 0;
            formatter.format(format, "Radix sort", FORMAT.format(radixResults[i++]), FORMAT.format(radixResults[i++]), FORMAT.format(radixResults[i++]));
        }
        if (showAmericanFlag) {
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
