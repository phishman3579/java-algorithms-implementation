package com.jwetherell.algorithms.mathematics.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.mathematics.RamerDouglasPeucker;

public class RamerDouglasPeuckerTest {

    @Test
    public void test1() {
        final List<Double[]> list = new ArrayList<Double[]>();
        list.add(new Double[]{3.14d, 5.2d});
        list.add(new Double[]{5.7d,  8.1d});
        list.add(new Double[]{4.6d, -1.3d});

        final List<Double[]> simplified = RamerDouglasPeucker.douglasPeucker(list, 5.0d);
        final List<Double[]> expected = new ArrayList<Double[]>();
        expected.add(list.get(0));
        expected.add(list.get(2));
        Assert.assertTrue(simplified.size()==2 && simplified.equals(expected));
    }

    @Test
    public void test2() {
        final List<Double[]> list = new ArrayList<Double[]>();
        list.add(new Double[]{0.0d,0.0d});
        list.add(new Double[]{2.5d, 3.0d});
        list.add(new Double[]{5.0d, 0.0d});

        final List<Double[]> simplified = RamerDouglasPeucker.douglasPeucker(list, 2.9d);
        final List<Double[]> expected = new ArrayList<Double[]>();
        expected.add(list.get(0));
        expected.add(list.get(1));
        expected.add(list.get(2));
        Assert.assertTrue(simplified.size()==3 && simplified.equals(expected));
    }

    @Test
    public void test3() {
        final List<Double[]> list = new ArrayList<Double[]>();
        list.add(new Double[]{0.0d,0.0d});
        list.add(new Double[]{2.5d, 3.0d});
        list.add(new Double[]{5.0d, 0.0d});

        final List<Double[]> simplified = RamerDouglasPeucker.douglasPeucker(list, 3.0d);
        final List<Double[]> expected = new ArrayList<Double[]>();
        expected.add(list.get(0));
        expected.add(list.get(2));
        Assert.assertTrue(simplified.size()==2 && simplified.equals(expected));
    }
}
