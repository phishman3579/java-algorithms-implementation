package com.jwetherell.algorithms.graph.test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.graph.PushRelabel;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

/**
 * @author mrowka
 */
public class PushRelabelTest {

    private static class ExampleInput {

        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);

        final Map<Graph.Edge<Integer>, Long> capacities = new TreeMap<Graph.Edge<Integer>, Long>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(1, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(1, v1, v3);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(1, v2, v3);
        final Graph.Edge<Integer> e3_2 = new Graph.Edge<Integer>(1, v3, v2);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(1, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(1, v3, v4);

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
    public void testPushRelabel() {
        ExampleInput exampleInput = new ExampleInput();
        assertTrue(PushRelabel.getMaximumFlow(exampleInput.capacities, exampleInput.source, exampleInput.sink) == 7);
    }
}
