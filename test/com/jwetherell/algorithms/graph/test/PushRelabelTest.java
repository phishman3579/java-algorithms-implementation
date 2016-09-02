package com.jwetherell.algorithms.graph.test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.graph.PushRelabel;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class PushRelabelTest {

    private static class ExampleInput1 {

        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);

        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(1, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(1, v1, v3);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(1, v2, v3);
        final Graph.Edge<Integer> e3_2 = new Graph.Edge<Integer>(1, v3, v2);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(1, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(1, v3, v4);

        final Map<Graph.Edge<Integer>, Long> capacities = new TreeMap<Graph.Edge<Integer>, Long>();
        {
            capacities.put(e1_2, 3L);
            capacities.put(e1_3, 5L);
            capacities.put(e3_2, 3L);
            capacities.put(e2_3, 2L);
            capacities.put(e2_4, 7L);
            capacities.put(e3_4, 1L);
        }

        final Graph.Vertex<Integer> source = v1;
        final Graph.Vertex<Integer> sink = v4;
    }

    @Test
    public void testPushRelabel1() {
        final ExampleInput1 exampleInput = new ExampleInput1();
        assertTrue(PushRelabel.getMaximumFlow(exampleInput.capacities, exampleInput.source, exampleInput.sink) == 7);
    }

    private static class ExampleInput2 {

        final Graph.Vertex<Integer> v0 = new Graph.Vertex<Integer>(0);
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        final Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);

        final Graph.Edge<Integer> e0_1 = new Graph.Edge<Integer>(1, v0, v1);
        final Graph.Edge<Integer> e0_2 = new Graph.Edge<Integer>(1, v0, v2);
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(1, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(1, v1, v3);
        final Graph.Edge<Integer> e2_1 = new Graph.Edge<Integer>(1, v2, v1);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(1, v2, v4);
        final Graph.Edge<Integer> e3_2 = new Graph.Edge<Integer>(1, v3, v2);
        final Graph.Edge<Integer> e3_5 = new Graph.Edge<Integer>(1, v3, v5);
        final Graph.Edge<Integer> e4_3 = new Graph.Edge<Integer>(1, v4, v3);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(1, v4, v5);

        final Map<Graph.Edge<Integer>, Long> capacities = new TreeMap<Graph.Edge<Integer>, Long>();
        {
            capacities.put(e0_1, 16L);
            capacities.put(e0_2, 13L);
            capacities.put(e1_2, 10L);
            capacities.put(e1_3, 12L);
            capacities.put(e2_1, 4L);
            capacities.put(e2_4, 14L);
            capacities.put(e3_2, 9L);
            capacities.put(e3_5, 20L);
            capacities.put(e4_3, 7L);
            capacities.put(e4_5, 4L);
        }

        final Graph.Vertex<Integer> source = v0;
        final Graph.Vertex<Integer> sink = v5;
    }

    @Test
    public void testPushRelabel2() {
        final ExampleInput2 exampleInput = new ExampleInput2();
        assertTrue(PushRelabel.getMaximumFlow(exampleInput.capacities, exampleInput.source, exampleInput.sink) == 23);
    }
}
