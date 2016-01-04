package com.jwetherell.algorithms.data_structures.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class GraphTests {

    @Test
    public void testCostVertexPair() {
        List<Edge<Integer>> s1 = new ArrayList<Graph.Edge<Integer>>(3);
        s1.add(new Edge<Integer>(1, new Vertex<Integer>(10), new Vertex<Integer>(20)));
        s1.add(new Edge<Integer>(2, new Vertex<Integer>(20), new Vertex<Integer>(30)));
        Graph.CostPathPair<Integer> p1 = new Graph.CostPathPair<Integer>(1, s1);

        List<Edge<Integer>> s2 = new ArrayList<Graph.Edge<Integer>>(3);
        s2.add(new Edge<Integer>(2, new Vertex<Integer>(10), new Vertex<Integer>(20)));
        s2.add(new Edge<Integer>(1, new Vertex<Integer>(20), new Vertex<Integer>(30)));
        Graph.CostPathPair<Integer> p2 = new Graph.CostPathPair<Integer>(1, s2);

        List<Edge<Integer>> s3 = new ArrayList<Graph.Edge<Integer>>(3);
        s3.add(new Edge<Integer>(2, new Vertex<Integer>(10), new Vertex<Integer>(20)));
        s3.add(new Edge<Integer>(1, new Vertex<Integer>(20), new Vertex<Integer>(30)));
        Graph.CostPathPair<Integer> p3 = new Graph.CostPathPair<Integer>(1, s3);

        Assert.assertFalse(p1.equals(p2));
        Assert.assertFalse(p2.equals(p1));
        Assert.assertTrue(p2.equals(p3));
    }
}
