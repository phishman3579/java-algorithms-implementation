package com.jwetherell.algorithms.data_structures;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class IntervalSumArrayTest {

    @Test
    public void properSumAllElementsTest() {
        IntervalSumArray sub = new IntervalSumArray();
        for(int i = 0; i<=100; i++)
            sub.add(i);
        for(int i = 0; i<=100; i++)
            assertEquals(i*(i+1)/2, sub.sum(i));
        assertEquals(100*101/2, sub.sum());
    }

    @Test
    public void randomGeneratedTest() {
        Random generator = new Random(42);
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i<=100; i++)
            list.add(i);
        IntervalSumArray sum = new IntervalSumArray(list);
        for(int i = 0; i<1000000; i++) {
            int pos = generator.nextInt(100);
            int val = generator.nextInt(2000000) - 1000000;
            sum.set(pos, val);
            list.set(pos, val);
            assertEquals(val, sum.get(pos));
        }

        int s = 0;
        List<Integer> prefSum = new ArrayList<>();
        prefSum.add(s);
        for(Integer val: list) {
            s += val;
            prefSum.add(s);
        }

        for(int i = 0; i<=100; i++) {
            for(int j = i; j<=100; j++) {
                assertEquals(prefSum.get(j+1) - prefSum.get(i), sum.sum(i, j));
            }
        }
    }

    @Test
    public void setIndexOutOfRangeTest() {
        IntervalSumArray sum = new IntervalSumArray(100);
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
        IntervalSumArray sum = new IntervalSumArray(100);
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
        IntervalSumArray sum = new IntervalSumArray(100);
        boolean thrown = false;
        try {
            sum.sum(101, 100);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

}