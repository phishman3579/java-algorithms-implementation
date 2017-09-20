package com.jwetherell.algorithms.data_structures;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of array holding integer values which allows to count sum of elements in given
 * interval in O(log n) complexity.
 * <br>
 * @author Szymon Stankiewicz <dakurels@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class IntervalSum {

    private List<Integer> values = new ArrayList<Integer>();
    private List<Integer> prefSums = new ArrayList<Integer>();

    /**
     * Creates empty IntervalSumArray
     */
    public IntervalSum() {
        values.add(0);
        prefSums.add(0);
    }

    /**
     * Creates IntervalSumArray of given size filled with zeros.
     *
     * Complexity O(size).
     *
     * @param size size of IntervalSumArray
     */
    public IntervalSum(int size) {
        for (int i = 0; i<size; i++) {
            values.add(0);
            prefSums.add(0);
        }
    }

    /**
     * Creates IntervalSumArray filled with given values.
     *
     * Complexity O(n log n).
     *
     * @param values sequence of values for IntervalSumArray.
     */
    public IntervalSum(Iterable<Integer> values) {
        for (Integer v: values)
            add(v);
    }

    private static int greatestPowerOfTwoDividing(int x) {
        return x & (-x);
    }

    private static int successor(int x) {
        return x + greatestPowerOfTwoDividing(x);
    }

    private static int predecessor(int x) {
        return x - greatestPowerOfTwoDividing(x);
    }

    /**
     * @return size of IntervalSumArray
     */
    public int size() {
        return prefSums.size() - 1;
    }

    /**
     * Adds value at the end of IntervalSumArray.
     *
     * Complexity O(log n).
     *
     * @param val value to be added at the end of array.
     */
    public void add(int val) {
        values.add(val);
        for (int i = 1; i<greatestPowerOfTwoDividing(size()+1); i*=2)
            val += prefSums.get(size() + 1 - i);
        prefSums.add(val);
    }

    /**
     * Set value at given index.
     *
     * Complexity O(log n)
     *
     * @param index index to be updated
     * @param val new value
     */
    public void set(int index, int val) {
        if (index < 0 || index >= size()) 
            throw new IndexOutOfBoundsException();
        index++;
        int diff = val - values.get(index);
        values.set(index, val);
        while (index <= size()) {
            int oldPrefSum = prefSums.get(index);
            prefSums.set(index, oldPrefSum + diff);
            index = successor(index);
        }
    }

    /**
     * Return value with given index.
     *
     * Complexity O(1)
     *
     * @param index index of array.
     * @return value at given index.
     */
    public int get(int index) {
        return values.get(index+1);
    }

    /**
     * Return sum of values from 0 to end inclusively.
     *
     * Complexity O(log n)
     *
     * @param end end of interval
     * @return sum of values in interval
     */
    public int sum(int end) {
        if (end < 0 || end >= size()) 
            throw new IndexOutOfBoundsException();
        end++;
        int s = 0;
        while (end > 0) {
            s += prefSums.get(end);
            end = predecessor(end);
        }
        return s;
    }

    /**
     * Return sum of all values inclusively.
     *
     * Complexity O(log n)
     *
     * @return sum of values in array
     */
    public int sum() {
        return sum(size()-1);
    }

    /**
     * Return sum of values from start to end inclusively.
     *
     * Complexity O(log n)
     *
     * @param start start of interval
     * @param end end of interval
     * @return sum of values in interval
     */
    public int sum(int start, int end) {
        if (start > end) 
            throw new IllegalArgumentException("Start must be less then end");
        int startPrefSum = start == 0 ? 0 : sum(start-1);
        int endPrefSum = sum(end);
        return endPrefSum - startPrefSum;
    }
}
