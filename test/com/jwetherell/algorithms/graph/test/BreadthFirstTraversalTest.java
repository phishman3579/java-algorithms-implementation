package com.jwetherell.algorithms.graph.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.graph.BreadthFirstTraversal;

public class BreadthFirstTraversalTest {

    private static final List<Graph.Vertex<Integer>>    vertices    = new ArrayList<Graph.Vertex<Integer>>();
    private static final List<Graph.Edge<Integer>>      edges       = new ArrayList<Graph.Edge<Integer>>();

    private static final Graph.Vertex<Integer>          v0          = new Graph.Vertex<Integer>(0);
    private static final Graph.Vertex<Integer>          v1          = new Graph.Vertex<Integer>(1);
    private static final Graph.Vertex<Integer>          v2          = new Graph.Vertex<Integer>(2);
    private static final Graph.Vertex<Integer>          v3          = new Graph.Vertex<Integer>(3);

    static {
        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        edges.add(new Graph.Edge<Integer>(0, v0, v1));
        edges.add(new Graph.Edge<Integer>(0, v0, v2));
        edges.add(new Graph.Edge<Integer>(0, v1, v2));
        edges.add(new Graph.Edge<Integer>(0, v2, v0));
        edges.add(new Graph.Edge<Integer>(0, v2, v3));
        edges.add(new Graph.Edge<Integer>(0, v3, v3));
    }

    private static final Graph<Integer>                 graph       = new Graph<Integer>(Graph.TYPE.DIRECTED, vertices, edges);

    @Test
    public void test1() {
        final Graph.Vertex<Integer>[] result = BreadthFirstTraversal.breadthFirstTraversal(graph, v2);
        Assert.assertTrue(result[0].getValue()==2);
        Assert.assertTrue(result[1].getValue()==0);
        Assert.assertTrue(result[2].getValue()==3);
        Assert.assertTrue(result[3].getValue()==1);
    }

    @Test
    public void test2() {
        final Graph.Vertex<Integer>[] result = BreadthFirstTraversal.breadthFirstTraversal(graph, v0);
        Assert.assertTrue(result[0].getValue()==0);
        Assert.assertTrue(result[1].getValue()==1);
        Assert.assertTrue(result[2].getValue()==2);
        Assert.assertTrue(result[3].getValue()==3);
    }
}

