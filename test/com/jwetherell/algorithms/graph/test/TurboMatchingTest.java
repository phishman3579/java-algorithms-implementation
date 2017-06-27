package com.jwetherell.algorithms.graph.test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.graph.TurboMatching;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class TurboMatchingTest {

    private final Graph.Vertex<Integer> v_a1 = new Graph.Vertex<Integer>(1);
    private final Graph.Vertex<Integer> v_a2 = new Graph.Vertex<Integer>(2);
    private final Graph.Vertex<Integer> v_a3 = new Graph.Vertex<Integer>(3);
    private final Graph.Vertex<Integer> v_b1 = new Graph.Vertex<Integer>(4);
    private final Graph.Vertex<Integer> v_b2 = new Graph.Vertex<Integer>(5);
    private final Graph.Vertex<Integer> v_b3 = new Graph.Vertex<Integer>(6);

    private List<Graph.Vertex<Integer>> vertices = new ArrayList<Graph.Vertex<Integer>>();

    {
        vertices.add(v_a1);
        vertices.add(v_a2);
        vertices.add(v_a3);
        vertices.add(v_b1);
        vertices.add(v_b2);
        vertices.add(v_b3);
    }
    @Test
    public void testFullBipartiteGraph(){
        List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        {
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b1));
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b2));
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b3));
            edges.add(new Graph.Edge<Integer>(1, v_a2, v_b1));
            edges.add(new Graph.Edge<Integer>(1, v_a2, v_b2));
            edges.add(new Graph.Edge<Integer>(1, v_a2, v_b3));
            edges.add(new Graph.Edge<Integer>(1, v_a3, v_b1));
            edges.add(new Graph.Edge<Integer>(1, v_a3, v_b2));
            edges.add(new Graph.Edge<Integer>(1, v_a3, v_b3));
        }

        final Graph<Integer> graph = new Graph<Integer>(vertices, edges);

        TurboMatching.MatchingResult<Integer> matchingResult = TurboMatching.getMaximumMatching(graph);
        assertTrue(matchingResult.getSize() == 3);
        for(Graph.Vertex<Integer> vertex : vertices){
            assertTrue(matchingResult.getMate().get(matchingResult.getMate().get(vertex)).equals(vertex));
        }
    }

    @Test
    public void testSingleEdgeForVertex(){
        List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        {
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b1));
            edges.add(new Graph.Edge<Integer>(1, v_a2, v_b2));
            edges.add(new Graph.Edge<Integer>(1, v_a3, v_b3));
        }

        final Graph<Integer> graph = new Graph<Integer>(vertices, edges);

        TurboMatching.MatchingResult<Integer> matchingResult = TurboMatching.getMaximumMatching(graph);

        assertTrue(matchingResult.getSize() == 3);
        assertTrue(matchingResult.getMate().get(v_a1).equals(v_b1));
        assertTrue(matchingResult.getMate().get(v_a2).equals(v_b2));
        assertTrue(matchingResult.getMate().get(v_a3).equals(v_b3));
        assertTrue(matchingResult.getMate().get(v_b1).equals(v_a1));
        assertTrue(matchingResult.getMate().get(v_b2).equals(v_a2));
        assertTrue(matchingResult.getMate().get(v_b3).equals(v_a3));
    }

    @Test
    public void testEmptyGraph(){
        List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        {
        }

        final Graph<Integer> graph = new Graph<Integer>(vertices, edges);

        TurboMatching.MatchingResult<Integer> matchingResult = TurboMatching.getMaximumMatching(graph);

        assertTrue(matchingResult.getSize() == 0);
        assertTrue(matchingResult.getMate().isEmpty());
    }

    @Test
    public void testTwoMatched(){
        List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        {
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b1));
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b3));
            edges.add(new Graph.Edge<Integer>(1, v_a2, v_b2));
            edges.add(new Graph.Edge<Integer>(1, v_a3, v_b2));
        }

        final Graph<Integer> graph = new Graph<Integer>(vertices, edges);
        TurboMatching.MatchingResult<Integer> matchingResult = TurboMatching.getMaximumMatching(graph);

        assertTrue(matchingResult.getSize() == 2);
        assertTrue(matchingResult.getMate().containsKey(v_a1));
        assertTrue(matchingResult.getMate().containsKey(v_b2));
        assertTrue(matchingResult.getMate().containsValue(v_a1));
        assertTrue(matchingResult.getMate().containsValue(v_b2));
    }

    @Test
    public void testOneMatched(){
        List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        {
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b1));
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b2));
            edges.add(new Graph.Edge<Integer>(1, v_a1, v_b3));
        }

        final Graph<Integer> graph = new Graph<Integer>(vertices, edges);
        TurboMatching.MatchingResult<Integer> matchingResult = TurboMatching.getMaximumMatching(graph);

        assertTrue(matchingResult.getSize() == 1);
        assertTrue(matchingResult.getMate().containsKey(v_a1));
        assertTrue(matchingResult.getMate().containsValue(v_a1));
        assertFalse(matchingResult.getMate().containsKey(v_a2));
        assertFalse(matchingResult.getMate().containsValue(v_a2));
        assertFalse(matchingResult.getMate().containsKey(v_a3));
        assertFalse(matchingResult.getMate().containsValue(v_a3));
    }


}