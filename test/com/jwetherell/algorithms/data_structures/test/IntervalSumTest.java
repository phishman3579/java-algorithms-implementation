package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.IntervalSum;

public class IntervalSumTest {

    @Test
    public void properSumAllElementsTest() {
        final IntervalSum sub = new IntervalSum();
        for (int i = 0; i<=100; i++)
            sub.add(i);
        for (int i = 0; i<=100; i++)
            assertEquals(i*(i+1)/2, sub.sum(i));
        assertEquals(100*101/2, sub.sum());
    }

    @Test
    public void randomGeneratedTest() {
        final Random generator = new Random(42);
        final List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i<=100; i++)
            list.add(i);
        final IntervalSum sum = new IntervalSum(list);
        for (int i = 0; i<1000000; i++) {
            final int pos = generator.nextInt(100);
            final int val = generator.nextInt(2000000) - 1000000;
            sum.set(pos, val);
            list.set(pos, val);
            assertEquals(val, sum.get(pos));
        }

        int s = 0;
        final List<Integer> prefSum = new ArrayList<Integer>();
        prefSum.add(s);
        for (Integer val: list) {
            s += val;
            prefSum.add(s);
        }

        for (int i = 0; i<100; i++) {
            for (int j = i; j<100; j++) {
                assertEquals(prefSum.get(j+1) - prefSum.get(i), sum.sum(i, j));
            }
        }
    }

    @Test
    public void setIndexOutOfRangeTest() {
        final IntervalSum sum = new IntervalSum(100);
        boolean thrown = false;
        try {
            sum.set(101, 10);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void sumIndexOutOfRangeTest() {
        final IntervalSum sum = new IntervalSum(100);
        boolean thrown = false;
        try {
            sum.sum(101);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void endBeforeStartTest() {
        final IntervalSum sum = new IntervalSum(100);
        boolean thrown = false;
        try {
            sum.sum(101, 100);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
