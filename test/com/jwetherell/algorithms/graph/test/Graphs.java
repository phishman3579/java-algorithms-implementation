package com.jwetherell.algorithms.graph.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.graph.*;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.TYPE;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class Graphs {

    // Undirected
    private static class UndirectedGraph {
        final List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        final Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
        final Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
        final Graph.Vertex<Integer> v7 = new Graph.Vertex<Integer>(7);
        final Graph.Vertex<Integer> v8 = new Graph.Vertex<Integer>(8);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
            verticies.add(v7);
            verticies.add(v8);
        }

        final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        final Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        final Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        final Graph.Edge<Integer> e5_6 = new Graph.Edge<Integer>(9, v5, v6);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
        final Graph.Edge<Integer> e1_7 = new Graph.Edge<Integer>(1, v1, v7);
        final Graph.Edge<Integer> e1_8 = new Graph.Edge<Integer>(1, v1, v8);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e5_6);
            edges.add(e4_5);
            edges.add(e1_7);
            edges.add(e1_8);
        }

        final Graph<Integer> graph = new Graph<Integer>(verticies, edges);
    }

    // Directed
    private static class DirectedGraph {
        final List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        final Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
        final Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
        final Graph.Vertex<Integer> v7 = new Graph.Vertex<Integer>(7);
        final Graph.Vertex<Integer> v8 = new Graph.Vertex<Integer>(8);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
            verticies.add(v7);
            verticies.add(v8);
        }

        final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        final Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        final Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        final Graph.Edge<Integer> e6_5 = new Graph.Edge<Integer>(9, v6, v5);
        final Graph.Edge<Integer> e6_8 = new Graph.Edge<Integer>(14, v6, v8);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
        final Graph.Edge<Integer> e4_7 = new Graph.Edge<Integer>(16, v4, v7);
        final Graph.Edge<Integer> e1_8 = new Graph.Edge<Integer>(30, v1, v8);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e6_5);
            edges.add(e6_8);
            edges.add(e4_5);
            edges.add(e4_7);
            edges.add(e1_8);
        }

        final Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
    }

    // Directed with negative weights
    private static class DirectedWithNegativeWeights {
        final List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
        }

        final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        final Graph.Edge<Integer> e1_4 = new Graph.Edge<Integer>(2, v1, v4);  // w->z
        final Graph.Edge<Integer> e2_1 = new Graph.Edge<Integer>(6, v2, v1);  // x->w
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(3, v2, v3);  // x->y
        final Graph.Edge<Integer> e3_1 = new Graph.Edge<Integer>(4, v3, v1);  // y->w
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(5, v3, v4);  // y->z
        final Graph.Edge<Integer> e4_2 = new Graph.Edge<Integer>(-7, v4, v2); // z->x
        final Graph.Edge<Integer> e4_3 = new Graph.Edge<Integer>(-3, v4, v3); // z->y
        {
            edges.add(e1_4);
            edges.add(e2_1);
            edges.add(e2_3);
            edges.add(e3_1);
            edges.add(e3_4);
            edges.add(e4_2);
            edges.add(e4_3);
        }

        final Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
    }

    // Ideal undirected path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getIdealUndirectedPath(UndirectedGraph undirected) {
        final HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealUndirectedPath = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        {
            final int cost = 11;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            list.add(undirected.e3_6);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v6, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            list.add(undirected.e3_6);
            list.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v5, path);
        }
        {
            final int cost = 9;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v3, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            list.add(undirected.e3_4);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v4, path);
        }
        {
            final int cost = 7;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_2);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v2, path);
        }
        {
            final int cost = 0;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v1, path);
        }
        {
            final int cost = 1;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_7);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v7, path);
        }
        {
            final int cost = 1;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_8);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v8, path);
        }
        return idealUndirectedPath;
    }

    // Ideal undirected PathPair
    private Graph.CostPathPair<Integer> getIdealUndirectedPathPair(UndirectedGraph undirected) {
        final int cost = 20;
        final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
        list.add(undirected.e1_3);
        list.add(undirected.e3_6);
        list.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
        return (new Graph.CostPathPair<Integer>(cost, list));
    }

    // Ideal directed path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getIdealDirectedPath(DirectedGraph directed) {
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealDirectedPath = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        {
            final int cost = 11;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_6);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v6, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_6);
            list.add(new Graph.Edge<Integer>(9, directed.v6, directed.v5));
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v5, path);
        }
        {
            final int cost = 36;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_4);
            list.add(directed.e4_7);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v7, path);
        }
        {
            final int cost = 9;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v3, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_4);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v4, path);
        }
        {
            final int cost = 7;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_2);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v2, path);
        }
        {
            final int cost = 0;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v1, path);
        }
        {
            final int cost = 25;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_6);
            list.add(directed.e6_8);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v8, path);
        }
        return idealDirectedPath;
    }

    // Ideal directed Path Pair
    private Graph.CostPathPair<Integer> getIdealPathPair(DirectedGraph directed) {
        final int cost = 20;
        final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
        list.add(directed.e1_3);
        list.add(directed.e3_6);
        list.add(new Graph.Edge<Integer>(9, directed.v6, directed.v5));
        return (new Graph.CostPathPair<Integer>(cost, list));
    }

    // Ideal directed with negative weight path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getIdealDirectedNegWeight(DirectedWithNegativeWeights directedWithNegWeights) {
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealDirectedNegWeight = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        {
            final int cost = -2;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directedWithNegWeights.e1_4);
            list.add(directedWithNegWeights.e4_2);
            list.add(directedWithNegWeights.e2_3);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v3, path);
        }
        {
            final int cost = 2;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directedWithNegWeights.e1_4);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v4, path);
        }
        {
            final int cost = -5;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directedWithNegWeights.e1_4);
            list.add(directedWithNegWeights.e4_2);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v2, path);
        }
        {
            final int cost = 0;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v1, path);
        }
        return idealDirectedNegWeight;
    }

    // Ideal pair
    private Graph.CostPathPair<Integer> getIdealDirectedWithNegWeightsPathPair(DirectedWithNegativeWeights directedWithNegWeights) {
        final int cost = -2;
        final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
        list.add(directedWithNegWeights.e1_4);
        list.add(directedWithNegWeights.e4_2);
        list.add(directedWithNegWeights.e2_3);
        return (new Graph.CostPathPair<Integer>(cost, list));
    }

    @Test
    public void testDijstraUndirected() {
        final UndirectedGraph undirected = new UndirectedGraph();
        final Graph.Vertex<Integer> start = undirected.v1;
        final Graph.Vertex<Integer> end = undirected.v5;
        {   // UNDIRECTED GRAPH
            final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(undirected.graph, start);

            // Compare results
            for (Graph.Vertex<Integer> v : map1.keySet()) {
                final Graph.CostPathPair<Integer> path1 = map1.get(v);
                final Graph.CostPathPair<Integer> path2 = getIdealUndirectedPath(undirected).get(v);
                assertTrue("Dijstra's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }

            final Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(undirected.graph, start, end);
            assertTrue("No path from " + start.getValue() + " to " + end.getValue(), (pair1 != null));

            assertTrue("Dijstra's shortest path error. pair="+pair1+" pair="+getIdealUndirectedPathPair(undirected), pair1.equals(getIdealUndirectedPathPair(undirected)));
        }
    }

    @Test
    public void testBellmanFordUndirected() {
        final UndirectedGraph undirected = new UndirectedGraph();
        final Graph.Vertex<Integer> start = undirected.v1;
        final Graph.Vertex<Integer> end = undirected.v5;
        {
            final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(undirected.graph, start);

            // Compare results
            for (Graph.Vertex<Integer> v : map2.keySet()) {
                final Graph.CostPathPair<Integer> path1 = map2.get(v);
                final Graph.CostPathPair<Integer> path2 = getIdealUndirectedPath(undirected).get(v);
                assertTrue("Bellman-Ford's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }

            final Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(undirected.graph, start, end);
            assertTrue("Bellman-Ford's shortest path error. pair="+pair2+" result="+getIdealUndirectedPathPair(undirected), pair2.equals(getIdealUndirectedPathPair(undirected)));
        }
    }

    @Test
    public void testPrimUndirected() {
        final UndirectedGraph undirected = new UndirectedGraph();

        Graph.Vertex<Integer> start = undirected.v1;
        {
            final Graph.CostPathPair<Integer> resultMST = Prim.getMinimumSpanningTree(undirected.graph, start);
            {
                // Ideal MST
                final int cost = 35;
                final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
                list.add(undirected.e1_7);
                list.add(undirected.e1_8);
                list.add(undirected.e1_2);
                list.add(undirected.e1_3);
                list.add(undirected.e3_6);
                list.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
                list.add(new Graph.Edge<Integer>(6, undirected.v5, undirected.v4));
                final Graph.CostPathPair<Integer> idealMST = new Graph.CostPathPair<Integer>(cost, list);

                assertTrue("Prim's minimum spanning tree error. resultMST="+resultMST+" idealMST="+idealMST, resultMST.equals(idealMST));
            }

            // Prim on a graph with cycles
            final List<Vertex<Integer>> cyclicVerticies = new ArrayList<Vertex<Integer>>();
            final Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            cyclicVerticies.add(cv1);
            final Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            cyclicVerticies.add(cv2);
            final Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            cyclicVerticies.add(cv3);
            final Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            cyclicVerticies.add(cv4);
            final Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            cyclicVerticies.add(cv5);

            final List<Edge<Integer>> cyclicEdges = new ArrayList<Edge<Integer>>();
            final Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(3, cv1, cv2);
            cyclicEdges.add(ce1_2);
            final Graph.Edge<Integer> ce2_3 = new Graph.Edge<Integer>(2, cv2, cv3);
            cyclicEdges.add(ce2_3);
            final Graph.Edge<Integer> ce3_4 = new Graph.Edge<Integer>(4, cv3, cv4);
            cyclicEdges.add(ce3_4);
            final Graph.Edge<Integer> ce4_1 = new Graph.Edge<Integer>(1, cv4, cv1);
            cyclicEdges.add(ce4_1);
            final Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(1, cv4, cv5);
            cyclicEdges.add(ce4_5);

            final Graph<Integer> cyclicUndirected = new Graph<Integer>(TYPE.UNDIRECTED, cyclicVerticies, cyclicEdges);

            start = cv1;

            final Graph.CostPathPair<Integer> pair4 = Prim.getMinimumSpanningTree(cyclicUndirected, start);
            {
                // Ideal MST
                final int cost = 7;
                final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
                list.add(new Graph.Edge<Integer>(1, cv1, cv4));
                list.add(ce4_5);
                list.add(ce1_2);
                list.add(ce2_3);
                final Graph.CostPathPair<Integer> result4 = new Graph.CostPathPair<Integer>(cost, list);

                assertTrue("Prim's minimum spanning tree error. pair4="+pair4+" result4="+result4, pair4.equals(result4));
            }
        }
    }

    @Test
    public void testKruskalUndirected() {
        final UndirectedGraph undirected = new UndirectedGraph();
        {
            final Graph.CostPathPair<Integer> resultMST = Kruskal.getMinimumSpanningTree(undirected.graph);
            {
                // Ideal MST
                final int cost = 35;
                final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
                list.add(undirected.e1_7);
                list.add(undirected.e1_8);
                list.add(undirected.e1_2);
                list.add(undirected.e1_3);
                list.add(undirected.e3_6);
                list.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
                list.add(new Graph.Edge<Integer>(6, undirected.v5, undirected.v4));
                final Graph.CostPathPair<Integer> idealMST = new Graph.CostPathPair<Integer>(cost, list);

                assertTrue("Kruskal's minimum spanning tree error. resultMST=" + resultMST + " idealMST=" + idealMST, resultMST.getCost() == idealMST.getCost());
            }

            // Kruskal on a graph with cycles
            final List<Vertex<Integer>> cyclicVertices = new ArrayList<Vertex<Integer>>();
            final Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            cyclicVertices.add(cv1);
            final Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            cyclicVertices.add(cv2);
            final Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            cyclicVertices.add(cv3);
            final Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            cyclicVertices.add(cv4);
            final Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            cyclicVertices.add(cv5);

            final List<Edge<Integer>> cyclicEdges = new ArrayList<Edge<Integer>>();
            final Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(3, cv1, cv2);
            cyclicEdges.add(ce1_2);
            final Graph.Edge<Integer> ce2_3 = new Graph.Edge<Integer>(2, cv2, cv3);
            cyclicEdges.add(ce2_3);
            final Graph.Edge<Integer> ce3_4 = new Graph.Edge<Integer>(4, cv3, cv4);
            cyclicEdges.add(ce3_4);
            final Graph.Edge<Integer> ce4_1 = new Graph.Edge<Integer>(1, cv4, cv1);
            cyclicEdges.add(ce4_1);
            final Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(1, cv4, cv5);
            cyclicEdges.add(ce4_5);

            final Graph<Integer> cyclicUndirected = new Graph<Integer>(TYPE.UNDIRECTED, cyclicVertices, cyclicEdges);


            final Graph.CostPathPair<Integer> pair4 = Kruskal.getMinimumSpanningTree(cyclicUndirected);
            {
                // Ideal MST
                final int cost = 7;
                final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
                list.add(new Graph.Edge<Integer>(1, cv1, cv4));
                list.add(ce4_5);
                list.add(ce1_2);
                list.add(ce2_3);
                final Graph.CostPathPair<Integer> result4 = new Graph.CostPathPair<Integer>(cost, list);

                assertTrue("Kruskal's minimum spanning tree error. pair4=" + pair4 + " result4=" + result4, pair4.getCost() == result4.getCost());
            }
        }
    }

    @Test
    public void testDijkstraDirected() {
        final DirectedGraph directed = new DirectedGraph();
        final Graph.Vertex<Integer> start = directed.v1;
        final Graph.Vertex<Integer> end = directed.v5;
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(directed.graph, start);

        // Compare results
        for (Graph.Vertex<Integer> v : map1.keySet()) {
            final Graph.CostPathPair<Integer> path1 = map1.get(v);
            final Graph.CostPathPair<Integer> path2 = getIdealDirectedPath(directed).get(v);
            assertTrue("Dijstra's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
        }

        final Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(directed.graph, start, end);
        assertTrue("No path from "+start.getValue()+" to "+end.getValue(), (pair1!=null));

        // Compare pair
        assertTrue("Dijstra's shortest path error. pair1="+pair1+" idealPathPair="+getIdealPathPair(directed), pair1.equals(getIdealPathPair(directed)));
    }

    @Test
    public void testBellmanFordDirected() {
        final DirectedGraph directed = new DirectedGraph();
        final Graph.Vertex<Integer> start = directed.v1;
        final Graph.Vertex<Integer> end = directed.v5;

        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed.graph, start);

        // Compare results
        for (Graph.Vertex<Integer> v : map2.keySet()) {
            final Graph.CostPathPair<Integer> path1 = map2.get(v);
            final Graph.CostPathPair<Integer> path2 = getIdealDirectedPath(directed).get(v);
            assertTrue("Bellman-Ford's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
        }

        final Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed.graph, start, end);
        assertTrue("No path from "+start.getValue()+" to "+end.getValue(), pair2!=null);

        // Compare pair
        assertTrue("Bellman-Ford's shortest path error. pair2="+pair2+" idealPathPair="+getIdealPathPair(directed), pair2.equals(getIdealPathPair(directed)));
    }

    @Test
    public void testDijstraDirectedWihtNegativeWeights() {
        final DirectedWithNegativeWeights directedWithNegWeights = new DirectedWithNegativeWeights();
        {   // DIRECTED GRAPH (WITH NEGATIVE WEIGHTS)
            final Graph.Vertex<Integer> start = directedWithNegWeights.v1;
            final Graph.Vertex<Integer> end = directedWithNegWeights.v3;
            final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = BellmanFord.getShortestPaths(directedWithNegWeights.graph, start);

            // Compare results
            for (Graph.Vertex<Integer> v : map1.keySet()) {
                final Graph.CostPathPair<Integer> path1 = map1.get(v);
                final Graph.CostPathPair<Integer> path2 = getIdealDirectedNegWeight(directedWithNegWeights).get(v);
                assertTrue("Bellman-Ford's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }

            final Graph.CostPathPair<Integer> pair1 = BellmanFord.getShortestPath(directedWithNegWeights.graph, start, end);
            assertTrue("No path from " + start.getValue() + " to " + end.getValue(), pair1 != null);

            // Compare pair
            assertTrue("Bellman-Ford's shortest path error. pair1="+pair1+" result2="+getIdealDirectedWithNegWeightsPathPair(directedWithNegWeights), pair1.equals(getIdealDirectedWithNegWeightsPathPair(directedWithNegWeights)));
        }
    }

    @Test
    public void testJohnonsonsAllPairsShortestPathOnDirecteWithNegWeights() {
        final DirectedWithNegativeWeights directedWithNegWeights = new DirectedWithNegativeWeights();
        {
            final Map<Vertex<Integer>, Map<Vertex<Integer>, List<Edge<Integer>>>> path = Johnson.getAllPairsShortestPaths(directedWithNegWeights.graph);
            assertTrue("Directed graph contains a negative weight cycle.", (path != null));

            final Map<Vertex<Integer>, Map<Vertex<Integer>, List<Edge<Integer>>>> result = new HashMap<Vertex<Integer>, Map<Vertex<Integer>, List<Edge<Integer>>>>();
            {
                {   // vertex 3
                    final Map<Vertex<Integer>, List<Edge<Integer>>> m = new HashMap<Vertex<Integer>, List<Edge<Integer>>>();
                    final List<Edge<Integer>> s3 = new ArrayList<Edge<Integer>>();
                    m.put(directedWithNegWeights.v3, s3);
                    final List<Edge<Integer>> s4 = new ArrayList<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e3_4);
                    m.put(directedWithNegWeights.v4, s4);
                    final List<Edge<Integer>> s2 = new ArrayList<Edge<Integer>>();
                    s2.add(directedWithNegWeights.e3_4);
                    s2.add(directedWithNegWeights.e4_2);
                    m.put(directedWithNegWeights.v2, s2);
                    final List<Edge<Integer>> s1 = new ArrayList<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e3_1);
                    m.put(directedWithNegWeights.v1, s1);
                    result.put(directedWithNegWeights.v3, m);
                }
                {   // vertex 4
                    final Map<Vertex<Integer>, List<Edge<Integer>>> m = new HashMap<Vertex<Integer>, List<Edge<Integer>>>();
                    final List<Edge<Integer>> s3 = new ArrayList<Edge<Integer>>();
                    s3.add(directedWithNegWeights.e4_2);
                    s3.add(directedWithNegWeights.e2_3);
                    m.put(directedWithNegWeights.v3, s3);
                    final List<Edge<Integer>> s4 = new ArrayList<Edge<Integer>>();
                    m.put(directedWithNegWeights.v4, s4);
                    final List<Edge<Integer>> s2 = new ArrayList<Edge<Integer>>();
                    s2.add(directedWithNegWeights.e4_2);
                    m.put(directedWithNegWeights.v2, s2);
                    final List<Edge<Integer>> s1 = new ArrayList<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e4_2);
                    s1.add(directedWithNegWeights.e2_1);
                    m.put(directedWithNegWeights.v1, s1);
                    result.put(directedWithNegWeights.v4, m);
                }
                {   // vertex 2
                    final Map<Vertex<Integer>, List<Edge<Integer>>> m = new HashMap<Vertex<Integer>, List<Edge<Integer>>>();
                    final List<Edge<Integer>> s3 = new ArrayList<Edge<Integer>>();
                    s3.add(directedWithNegWeights.e2_3);
                    m.put(directedWithNegWeights.v3, s3);
                    final List<Edge<Integer>> s4 = new ArrayList<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e2_1);
                    s4.add(directedWithNegWeights.e1_4);
                    m.put(directedWithNegWeights.v4, s4);
                    final List<Edge<Integer>> s2 = new ArrayList<Edge<Integer>>();
                    m.put(directedWithNegWeights.v2, s2);
                    final List<Edge<Integer>> s1 = new ArrayList<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e2_1);
                    m.put(directedWithNegWeights.v1, s1);
                    result.put(directedWithNegWeights.v2, m);
                }
                {   // vertex 1
                    final Map<Vertex<Integer>, List<Edge<Integer>>> m = new HashMap<Vertex<Integer>, List<Edge<Integer>>>();
                    final List<Edge<Integer>> s3 = new ArrayList<Edge<Integer>>();
                    s3.add(directedWithNegWeights.e1_4);
                    s3.add(directedWithNegWeights.e4_2);
                    s3.add(directedWithNegWeights.e2_3);
                    m.put(directedWithNegWeights.v3, s3);
                    final List<Edge<Integer>> s4 = new ArrayList<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e1_4);
                    m.put(directedWithNegWeights.v4, s4);
                    final List<Edge<Integer>> s2 = new ArrayList<Edge<Integer>>();
                    s2.add(directedWithNegWeights.e1_4);
                    s2.add(directedWithNegWeights.e4_2);
                    m.put(directedWithNegWeights.v2, s2);
                    final List<Edge<Integer>> s1 = new ArrayList<Edge<Integer>>();
                    m.put(directedWithNegWeights.v1, s1);
                    result.put(directedWithNegWeights.v1, m);
                }
            }

            // Compare results
            for (Vertex<Integer> vertex1 : path.keySet()) {
                final Map<Vertex<Integer>, List<Edge<Integer>>> map1 = path.get(vertex1);
                final Map<Vertex<Integer>, List<Edge<Integer>>> map2 = result.get(vertex1);
                for (Vertex<Integer> vertex2 : map1.keySet()) {
                    final List<Edge<Integer>> list1 = map1.get(vertex2);
                    final List<Edge<Integer>> list2 = map2.get(vertex2);
                    final Iterator<Edge<Integer>> iter1 = list1.iterator();
                    final Iterator<Edge<Integer>> iter2 = list2.iterator();
                    assertTrue("Johnson's all-pairs shortest path error. size3="+list1.size()+" size4="+list2.size(), list1.size()==list2.size());

                    while (iter1.hasNext() && iter2.hasNext()) {
                        Edge<Integer> e1 = (Edge<Integer>) iter1.next();
                        Edge<Integer> e2 = (Edge<Integer>) iter2.next();
                        assertTrue("Johnson's all-pairs shortest path error. e1.from="+e1.getFromVertex()+" e2.from="+e2.getFromVertex(),
                                   e1.getFromVertex().equals(e2.getFromVertex()));
                        assertTrue("Johnson's all-pairs shortest path error. e1.to="+e1.getToVertex()+" e2.to="+e2.getToVertex(),
                                   e1.getToVertex().equals(e2.getToVertex()));
                    }
                }
            }
        }
    }

    @Test
    public void testFloydWarshallonDirectedWithNegWeights() {
        final DirectedWithNegativeWeights directedWithNegWeights = new DirectedWithNegativeWeights();
        {
            final Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> pathWeights = FloydWarshall.getAllPairsShortestPaths(directedWithNegWeights.graph);
            final Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> result = new HashMap<Vertex<Integer>, Map<Vertex<Integer>, Integer>>();
            {
                // Ideal weights
                {   // Vertex 3
                    final Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
                    {
                        // Vertex 3
                        m.put(directedWithNegWeights.v3, 0);
                        // Vertex 4
                        m.put(directedWithNegWeights.v4, 5);
                        // Vertex 2
                        m.put(directedWithNegWeights.v2, -2);
                        // Vertex 1
                        m.put(directedWithNegWeights.v1, 4);
                    }
                    result.put(directedWithNegWeights.v3, m);
                }
                {   // Vertex 4
                    final Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
                    {
                        // Vertex 3
                        m.put(directedWithNegWeights.v3, -4);
                        // Vertex 4
                        m.put(directedWithNegWeights.v4, 0);
                        // Vertex 2
                        m.put(directedWithNegWeights.v2, -7);
                        // Vertex 1
                        m.put(directedWithNegWeights.v1, -1);
                    }
                    result.put(directedWithNegWeights.v4, m);
                }
                {   // Vertex 2
                    final Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
                    {
                        // Vertex 3
                        m.put(directedWithNegWeights.v3, 3);
                        // Vertex 4
                        m.put(directedWithNegWeights.v4, 8);
                        // Vertex 2
                        m.put(directedWithNegWeights.v2, 0);
                        // Vertex 1
                        m.put(directedWithNegWeights.v1, 6);
                    }
                    result.put(directedWithNegWeights.v2, m);
                }
                {   // Vertex 1
                    final Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
                    {
                        // Vertex 3
                        m.put(directedWithNegWeights.v3, -2);
                        // Vertex 4
                        m.put(directedWithNegWeights.v4, 2);
                        // Vertex 2
                        m.put(directedWithNegWeights.v2, -5);
                        // Vertex 1
                        m.put(directedWithNegWeights.v1, 0);
                    }
                    result.put(directedWithNegWeights.v1, m);
                }
            }

            // Compare results
            for (Vertex<Integer> vertex1 : pathWeights.keySet()) {
                final Map<Vertex<Integer>, Integer> m1 = pathWeights.get(vertex1);
                final Map<Vertex<Integer>, Integer> m2 = result.get(vertex1);
                for (Vertex<Integer> v : m1.keySet()) {
                    final int i1 = m1.get(v);
                    final int i2 = m2.get(v);
                    assertTrue("Floyd-Warshall's all-pairs shortest path weights error. i1="+i1+" i2="+i2, i1 == i2);
                }

            }
        }
    }

    @Test
    public void cycleCheckOnUndirected() {
        final List<Vertex<Integer>> cycledVerticies = new ArrayList<Vertex<Integer>>();
        {   // UNDIRECTED GRAPH
            final Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            cycledVerticies.add(cv1);
            final Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            cycledVerticies.add(cv2);
            final Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            cycledVerticies.add(cv3);
            final Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            cycledVerticies.add(cv4);
            final Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            cycledVerticies.add(cv5);
            final Graph.Vertex<Integer> cv6 = new Graph.Vertex<Integer>(6);
            cycledVerticies.add(cv6);

            final List<Edge<Integer>> cycledEdges = new ArrayList<Edge<Integer>>();
            final Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(7, cv1, cv2);
            cycledEdges.add(ce1_2);
            final Graph.Edge<Integer> ce2_4 = new Graph.Edge<Integer>(15, cv2, cv4);
            cycledEdges.add(ce2_4);
            final Graph.Edge<Integer> ce3_4 = new Graph.Edge<Integer>(11, cv3, cv4);
            cycledEdges.add(ce3_4);
            final Graph.Edge<Integer> ce3_6 = new Graph.Edge<Integer>(2, cv3, cv6);
            cycledEdges.add(ce3_6);
            final Graph.Edge<Integer> ce5_6 = new Graph.Edge<Integer>(9, cv5, cv6);
            cycledEdges.add(ce5_6);
            final Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(6, cv4, cv5);
            cycledEdges.add(ce4_5);

            final Graph<Integer> undirectedWithCycle = new Graph<Integer>(cycledVerticies, cycledEdges);

            boolean result = CycleDetection.detect(undirectedWithCycle);
            assertTrue("Cycle detection error.", result);

            final List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
            verticies.add(v1);
            final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
            verticies.add(v2);
            final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
            verticies.add(v3);
            final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
            verticies.add(v4);
            final Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
            verticies.add(v5);
            final Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
            verticies.add(v6);

            final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
            edges.add(e1_2);
            final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
            edges.add(e2_4);
            final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
            edges.add(e3_4);
            final Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
            edges.add(e3_6);
            final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
            edges.add(e4_5);

            final Graph<Integer> undirectedWithoutCycle = new Graph<Integer>(verticies, edges);

            result = CycleDetection.detect(undirectedWithoutCycle);
            assertFalse("Cycle detection error.", result);
        }
    }

    @Test
    public void topologicalSortOnDirectedGraph() {
        {   // DIRECTED GRAPH
            final List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            final Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            verticies.add(cv1);
            final Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            verticies.add(cv2);
            final Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            verticies.add(cv3);
            final Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            verticies.add(cv4);
            final Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            verticies.add(cv5);
            final Graph.Vertex<Integer> cv6 = new Graph.Vertex<Integer>(6);
            verticies.add(cv6);

            final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            final Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(1, cv1, cv2);
            edges.add(ce1_2);
            final Graph.Edge<Integer> ce2_4 = new Graph.Edge<Integer>(2, cv2, cv4);
            edges.add(ce2_4);
            final Graph.Edge<Integer> ce4_3 = new Graph.Edge<Integer>(3, cv4, cv3);
            edges.add(ce4_3);
            final Graph.Edge<Integer> ce3_6 = new Graph.Edge<Integer>(4, cv3, cv6);
            edges.add(ce3_6);
            final Graph.Edge<Integer> ce5_6 = new Graph.Edge<Integer>(5, cv5, cv6);
            edges.add(ce5_6);
            final Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(6, cv4, cv5);
            edges.add(ce4_5);

            final Graph<Integer> digraph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);

            final List<Graph.Vertex<Integer>> results1 = TopologicalSort.sort(digraph);
            assertTrue("Topological sort error. results="+results1, results1.size()!=0);

            final List<Graph.Vertex<Integer>> results2 = new ArrayList<Graph.Vertex<Integer>>(results1.size());
            {  // Ideal sort
                results2.add(cv6);
                results2.add(cv3);
                results2.add(cv5);
                results2.add(cv4);
                results2.add(cv2);
                results2.add(cv1);
            }

            // Compare results
            {
                final Iterator<Vertex<Integer>> iter1 = results1.iterator();
                final Iterator<Vertex<Integer>> iter2 = results2.iterator();
                assertTrue("Topological sort error. size1="+results1.size()+" size2="+results2.size(), results1.size()==results2.size());
                while (iter1.hasNext() && iter2.hasNext()) {
                    final Graph.Vertex<Integer> v1 = (Graph.Vertex<Integer>) iter1.next();
                    final Graph.Vertex<Integer> v2 = (Graph.Vertex<Integer>) iter2.next();
                    assertTrue("Topological sort error. v1="+v1+" v2="+v2, v1.equals(v2));
                }
            }
        }
    }

    @Test
    public void connectedComponents() {
        {
            final Graph<Integer> g = makeDirectedGraph(3, 2, new int[]{5, 4, 7});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==2);
        }

        {
            final Graph<Integer> g = makeDirectedGraph(5, 1, new int[]{5, 3, 4, 5, 6});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==1);
        }

        {
            final Graph<Integer> g = makeDirectedGraph(8, 51, new int[]{49, 57, 3, 95, 98, 100, 44, 40});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==3);
        }

        {
            final Graph<Integer> g = makeDirectedGraph(28, 79, new int[]{123, 60, 227, 766, 400, 405, 24, 968, 359, 533, 689, 409,
                                                                         188, 677, 231, 295, 240, 52, 373, 243, 493, 645, 307, 781,
                                                                         523, 494, 950, 899});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==3);
        }

        {
            final Graph<Integer> g = makeDirectedGraph(15, 564, new int[]{617, 400, 658, 30, 891, 517, 304, 156, 254, 610, 72, 371,
                                                                          411, 689, 381});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==10);
        }

        {
            final Graph<Integer> g = makeDirectedGraph(13, 422, new int[]{87, 950, 773, 928, 696, 131, 809, 781, 348, 144, 717, 555, 311});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==6);
        }

        {
            final Graph<Integer> g = makeDirectedGraph(17, 599, new int[]{903, 868, 67, 690, 841, 815, 469, 554, 647, 235, 787, 221, 669,
                                                                          87, 60, 28, 324});
            Assert.assertTrue(ConnectedComponents.getConnectedComponents(g).size()==10);
        }
    }

    @Test
    public void testAStarUndirected() {
        final UndirectedGraph undirected = new UndirectedGraph();
        final Graph.Vertex<Integer> start = undirected.v1;
        final Graph.Vertex<Integer> end = undirected.v8;
        {   // UNDIRECTED GRAPH
            final AStar<Integer> aStar = new AStar<Integer>();
            final List<Graph.Edge<Integer>> path = aStar.aStar(undirected.graph, start, end);
            final List<Graph.Edge<Integer>> ideal = getIdealUndirectedPath(undirected).get(end).getPath();
            assertTrue("A* path error. path="+path+" idealPathPair="+ideal, path.equals(ideal));
        }
    }

    @Test
    public void testAStarDirected() {
        final DirectedGraph directed = new DirectedGraph();
        final Graph.Vertex<Integer> start = directed.v1;
        final Graph.Vertex<Integer> end = directed.v8;
        {   // DIRECTED GRAPH
            final AStar<Integer> aStar = new AStar<Integer>();
            final List<Graph.Edge<Integer>> path = aStar.aStar(directed.graph, start, end);
            final List<Graph.Edge<Integer>> ideal = getIdealDirectedPath(directed).get(end).getPath();
            assertTrue("A* path error. path="+path+" idealPathPair="+ideal, path.equals(ideal));
        }
    }

    /*
     * Makes a zero weighted directed graph, so that there is an edge between two vertices if the difference between the 
     * vertices values is >= K
     */
    @SuppressWarnings("unused")
    private static final Graph<Integer> makeDirectedGraph(int N, int K, int[] values) {
        final List<Vertex<Integer>> vertices = new ArrayList<Vertex<Integer>>(values.length);
        for (int i=0; i<values.length; i++)
            vertices.add(new Vertex<Integer>(values[i], 0));

        final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>(values.length);
        for (int i=0; i<values.length; i++) {
            final Vertex<Integer> vi = vertices.get(i);
            for (int j=i+1; j<values.length; j++) {
                final Vertex<Integer> vj = vertices.get(j);
                final int diff = Math.abs(vi.getValue() - vj.getValue());
                if (diff >= K) {
                    final Edge<Integer> eij = new Edge<Integer>(diff, vi, vj);
                    edges.add(eij);
                }
            }
        }

        final Graph<Integer> g = new Graph<Integer>(TYPE.DIRECTED, vertices, edges);
        return g;
    }
}
