package com.jwetherell.algorithms.mathematics.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.mathematics.RamerDouglasPeucker;

public class RamerDouglasPeuckerTests {

    @Test
    public void test() {
        final List<Double[]> list = new ArrayList<Double[]>();
        list.add(new Double[]{3.14d, 5.2d});
        list.add(new Double[]{5.7d,  8.1d});
        list.add(new Double[]{4.6d, -1.3d});

        final List<Double[]> simplified = RamerDouglasPeucker.douglasPeucker(list, 5.0f);
        Assert.assertTrue(simplified.size()==2);
    }
}
