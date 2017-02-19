package com.jwetherell.algorithms.search.test;

import static org.junit.Assert.assertTrue;

import com.jwetherell.algorithms.search.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class Search {

    private static final int SIZE = 9999;
    private static final int offset = 123;

    private static int[] sorted = new int[SIZE];

    static {
        for (int i = offset; i < offset + sorted.length; i++) {
            sorted[i - offset] = i;
        }
    }

    private static int valueIndex = SIZE - (SIZE / 4);
    private static int valueInArray = sorted[valueIndex];
    private static int valueNotInArray = sorted[SIZE - 1] + offset;

    @Test
    public void testBruteForceSearch() {
        int index = LinearSearch.find(valueInArray, sorted);
        assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
        index = LinearSearch.find(valueNotInArray, sorted);
        assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }

    @Test
    public void testBinarySearch() {
        int index = BinarySearch.find(valueInArray, sorted, false);
        assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
        index = BinarySearch.find(valueNotInArray, sorted, false);
        assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }

    @Test
    public void testOptimizedBinarySearch() {
        int index = BinarySearch.find(valueInArray, sorted, true);
        assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
        index = BinarySearch.find(valueNotInArray, sorted, true);
        assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }

    @Test
    public void testInterpolationSearch() {
        int index = InterpolationSearch.find(valueInArray, sorted);
        assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
        index = InterpolationSearch.find(valueNotInArray, sorted);
        assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }

    @Test
    public void testQuickSelect() {
        int index = QuickSelect.find(valueInArray, sorted);
        assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
        index = QuickSelect.find(valueNotInArray, sorted);
        assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }

    @Test
    public void testLowerBound() {
        ArrayList<int[]> sequences = new ArrayList<int[]>();
        ArrayList<Integer> soughtElements = new ArrayList<Integer>();
        ArrayList<Integer> resultIndexes = new ArrayList<Integer>();

        sequences.add(new int[]{0, 1, 2, 3, 3, 3, 3, 8, 9});
        soughtElements.add(-1);
        resultIndexes.add(0);

        sequences.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        soughtElements.add(4);
        resultIndexes.add(4);

        sequences.add(new int[]{0, 1, 2, 3, 3, 3, 3, 3, 8, 9});
        soughtElements.add(3);
        resultIndexes.add(3);

        sequences.add(new int[]{0, 1, 2, 3, 3, 3, 3, 8, 9});
        soughtElements.add(3);
        resultIndexes.add(3);


        sequences.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        soughtElements.add(44);
        resultIndexes.add(10);

        assertTrue("Lower bound error. Sequences size=" + sequences.size() + " sough elements size=" + soughtElements.size() + " results size:" + resultIndexes.size(), sequences.size() == resultIndexes.size() && sequences.size() == soughtElements.size());

        for (int i = 0; i < sequences.size(); ++i) {
            int resultIndex = LowerBound.lowerBound(sequences.get(i), sequences.get(i).length, soughtElements.get(i));
            assertTrue("Lower bound error. Sequence=" + Arrays.toString(sequences.get(i)) + " sought element=" + soughtElements.get(i) + " expected result=" + resultIndexes.get(i) + " got result=" + resultIndex, resultIndex == resultIndexes.get(i));
        }

    }

    @Test
    public void testUpperBound() {
        ArrayList<int[]> sequences = new ArrayList<int[]>();
        ArrayList<Integer> soughtElements = new ArrayList<Integer>();
        ArrayList<Integer> resultIndexes = new ArrayList<Integer>();

        sequences.add(new int[]{0, 1, 2, 3, 3, 3, 3, 8, 9});
        soughtElements.add(-1);
        resultIndexes.add(0);

        sequences.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        soughtElements.add(4);
        resultIndexes.add(5);

        sequences.add(new int[]{0, 1, 2, 3, 3, 3, 3, 3, 8, 9});
        soughtElements.add(3);
        resultIndexes.add(8);

        sequences.add(new int[]{0, 1, 2, 3, 3, 3, 3, 8, 9});
        soughtElements.add(3);
        resultIndexes.add(7);

        sequences.add(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        soughtElements.add(44);
        resultIndexes.add(10);

        assertTrue("Upper bound error. Sequences size=" + sequences.size() + " sough elements size=" + soughtElements.size() + " results size:" + resultIndexes.size(), sequences.size() == resultIndexes.size() && sequences.size() == soughtElements.size());

        for (int i = 0; i < sequences.size(); ++i) {
            int resultIndex = UpperBound.upperBound(sequences.get(i), sequences.get(i).length, soughtElements.get(i));
            assertTrue("Upper bound error. Sequence=" + Arrays.toString(sequences.get(i)) + " sought element=" + soughtElements.get(i) + " expected result=" + resultIndexes.get(i) + " got result=" + resultIndex, resultIndex == resultIndexes.get(i));
        }

    }
}
