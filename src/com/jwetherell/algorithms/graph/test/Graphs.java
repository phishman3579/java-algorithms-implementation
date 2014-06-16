package com.jwetherell.algorithms.graph.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import com.jwetherell.algorithms.graph.BellmanFord;
import com.jwetherell.algorithms.graph.CycleDetection;
import com.jwetherell.algorithms.graph.Dijkstra;
import com.jwetherell.algorithms.graph.FloydWarshall;
import com.jwetherell.algorithms.graph.Johnson;
import com.jwetherell.algorithms.graph.Prim;
import com.jwetherell.algorithms.graph.TopologicalSort;

public class Graphs {

    private static int debug = 0; // Debug level. 0=None, 1=Some

    // Undirected
    private static class UndirectedGraph {
        List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
        Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
        Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
        }

        List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        Graph.Edge<Integer> e5_6 = new Graph.Edge<Integer>(9, v5, v6);
        Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
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
        }

        Graph<Integer> graph = new Graph<Integer>(verticies, edges);
    }
    private UndirectedGraph undirected = new UndirectedGraph();

    // Ideal undirected path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealUndirectedPath = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
    {
        {
            int cost = 11;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(undirected.e1_3);
            set.add(undirected.e3_6);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealUndirectedPath.put(undirected.v6, path);
        }
        {
            int cost = 20;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(undirected.e1_3);
            set.add(undirected.e3_6);
            set.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealUndirectedPath.put(undirected.v5, path);
        }
        {
            int cost = 9;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(undirected.e1_3);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealUndirectedPath.put(undirected.v3, path);
        }
        {
            int cost = 20;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(undirected.e1_3);
            set.add(undirected.e3_4);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealUndirectedPath.put(undirected.v4, path);
        }
        {
            int cost = 7;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(undirected.e1_2);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealUndirectedPath.put(undirected.v2, path);
        }
        {
            int cost = 0;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealUndirectedPath.put(undirected.v1, path);
        }
    }

    // Ideal undirected PathPair
    private Graph.CostPathPair<Integer> idealUndirectedPathPair = null;
    {
        int cost = 20;
        Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
        set.add(undirected.e1_3);
        set.add(undirected.e3_6);
        set.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
        idealUndirectedPathPair = new Graph.CostPathPair<Integer>(cost, set);
    }

    // Directed
    private static class DirectedGraph {
        List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
        Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
        Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
        Graph.Vertex<Integer> v7 = new Graph.Vertex<Integer>(7);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
            verticies.add(v7);
        }

        List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        Graph.Edge<Integer> e6_5 = new Graph.Edge<Integer>(9, v6, v5);
        Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
        Graph.Edge<Integer> e4_7 = new Graph.Edge<Integer>(16, v4, v7);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e6_5);
            edges.add(e4_5);
            edges.add(e4_7);
        }

        Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
    }
    private DirectedGraph directed = new DirectedGraph();

    // Ideal directed path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealDirectedPath = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
    {
        {
            int cost = 11;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directed.e1_3);
            set.add(directed.e3_6);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v6, path);
        }
        {
            int cost = 20;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directed.e1_3);
            set.add(directed.e3_6);
            set.add(new Graph.Edge<Integer>(9, directed.v6, directed.v5));
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v5, path);
        }
        {
            int cost = 36;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directed.e1_3);
            set.add(directed.e3_4);
            set.add(directed.e4_7);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v7, path);
        }
        {
            int cost = 9;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directed.e1_3);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v3, path);
        }
        {
            int cost = 20;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directed.e1_3);
            set.add(directed.e3_4);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v4, path);
        }
        {
            int cost = 7;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directed.e1_2);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v2, path);
        }
        {
            int cost = 0;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedPath.put(directed.v1, path);
        }
    }

    // Ideal directed Path Pair
    private Graph.CostPathPair<Integer> idealPathPair = null;
    {
        int cost = 20;
        Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
        set.add(directed.e1_3);
        set.add(directed.e3_6);
        set.add(new Graph.Edge<Integer>(9, directed.v6, directed.v5));
        idealPathPair = new Graph.CostPathPair<Integer>(cost, set);
    }

    // Directed with negative weights
    private static class DirectedWithNegativeWeights {
        List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
        Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
        }

        List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        Graph.Edge<Integer> e1_4 = new Graph.Edge<Integer>(2, v1, v4);
        Graph.Edge<Integer> e2_1 = new Graph.Edge<Integer>(6, v2, v1);
        Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(3, v2, v3);
        Graph.Edge<Integer> e3_1 = new Graph.Edge<Integer>(4, v3, v1);
        Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(5, v3, v4);
        Graph.Edge<Integer> e4_2 = new Graph.Edge<Integer>(-7, v4, v2);
        Graph.Edge<Integer> e4_3 = new Graph.Edge<Integer>(-3, v4, v3);
        {
            edges.add(e1_4);
            edges.add(e2_1);
            edges.add(e2_3);
            edges.add(e3_1);
            edges.add(e3_4);
            edges.add(e4_2);
            edges.add(e4_3);
        }

        Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
    }
    private DirectedWithNegativeWeights directedWithNegWeights = new DirectedWithNegativeWeights(); 

    // Ideal directed with negative weight path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealDirectedNegWeight = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
    {
        {
            int cost = -2;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directedWithNegWeights.e1_4);
            set.add(directedWithNegWeights.e4_2);
            set.add(directedWithNegWeights.e2_3);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedNegWeight.put(directedWithNegWeights.v3, path);
        }
        {
            int cost = 2;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directedWithNegWeights.e1_4);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedNegWeight.put(directedWithNegWeights.v4, path);
        }
        {
            int cost = -5;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            set.add(directedWithNegWeights.e1_4);
            set.add(directedWithNegWeights.e4_2);
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedNegWeight.put(directedWithNegWeights.v2, path);
        }
        {
            int cost = 0;
            Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
            Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, set);
            idealDirectedNegWeight.put(directedWithNegWeights.v1, path);
        }
    }

    // Ideal pair
    Graph.CostPathPair<Integer> idealDirectedWithNegWeightsPathPair = null;
    {
        int cost = -2;
        Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
        set.add(directedWithNegWeights.e1_4);
        set.add(directedWithNegWeights.e4_2);
        set.add(directedWithNegWeights.e2_3);
        idealDirectedWithNegWeightsPathPair = new Graph.CostPathPair<Integer>(cost, set);
    }

    @Test
    public void testDijstraUndirected() {
        if (debug > 0) System.out.println("Undirected Graph.");

        Graph.Vertex<Integer> start = undirected.v1;
        Graph.Vertex<Integer> end = undirected.v5;

        {   // UNDIRECTED GRAPH
            if (debug > 0) System.out.println("Dijstra's shortest paths of the undirected graph from " + start.getValue()+" to all.");
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(undirected.graph, start);
            if (debug > 0) System.out.println(getPathMapString(start, map1));

            // Compare results
            for (Graph.Vertex<Integer> v : map1.keySet()) {
                Graph.CostPathPair<Integer> path1 = map1.get(v);
                Graph.CostPathPair<Integer> path2 = idealUndirectedPath.get(v);
                assertTrue("Dijstra's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }
            if (debug > 0) System.out.println("Dijstra's shortest path worked correctly.");

            if (debug > 0) System.out.println("Dijstra's shortest path of the undirected graph from " + start.getValue() + " to " + end.getValue()+".");
            Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(undirected.graph, start, end);
            assertTrue("No path from " + start.getValue() + " to " + end.getValue(), (pair1 != null));
            if (debug > 0) System.out.println(pair1.toString());

            assertTrue("Dijstra's shortest path error. pair="+pair1+" pair="+idealUndirectedPathPair, pair1.equals(idealUndirectedPathPair));
            if (debug > 0) System.out.println("Dijstra's shortest path worked correctly");
        }
    }

    @Test
    public void testBellmanFordUndirected() {
        if (debug > 0) System.out.println("Undirected Graph.");

        Graph.Vertex<Integer> start = undirected.v1;
        Graph.Vertex<Integer> end = undirected.v5;

        {
            if (debug > 0) System.out.println("Bellman-Ford's shortest paths of the undirected graph from " + start.getValue()+" to all.");
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(undirected.graph, start);
            if (debug > 0) System.out.println(getPathMapString(start, map2));

            // Compare results
            for (Graph.Vertex<Integer> v : map2.keySet()) {
                Graph.CostPathPair<Integer> path1 = map2.get(v);
                Graph.CostPathPair<Integer> path2 = idealUndirectedPath.get(v);
                assertTrue("Bellman-Ford's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }
            if (debug>0) System.out.println("Bellman-Ford's shortest path worked correctly.");

            if (debug>0) System.out.println("Bellman-Ford's shortest path of the undirected graph from " + start.getValue() + " to " + end.getValue()+".");
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(undirected.graph, start, end);
            assertTrue("Bellman-Ford's shortest path error. pair="+pair2+" result="+idealUndirectedPathPair, pair2.equals(idealUndirectedPathPair));
            if (debug>0) System.out.println("Bellman-Ford's shortest path worked correctly");
        }
    }

    @Test
    public void testPrimUndirected() {
        if (debug > 0) System.out.println(undirected.toString());

        {
            Graph.Vertex<Integer> start = undirected.v1;

            if (debug > 0) System.out.println("Prim's minimum spanning tree of the undirected graph starting from vertex " + start.getValue()+".");
            Graph.CostPathPair<Integer> resultMST = Prim.getMinimumSpanningTree(undirected.graph, start);
            if (debug > 0) System.out.println(resultMST.toString());
            {
                // Ideal MST
                int cost = 33;
                Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
                set.add(undirected.e1_2);
                set.add(undirected.e1_3);
                set.add(undirected.e3_6);
                set.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
                set.add(new Graph.Edge<Integer>(6, undirected.v5, undirected.v4));
                Graph.CostPathPair<Integer> idealMST = new Graph.CostPathPair<Integer>(cost, set);
    
                assertTrue("Prim's minimum spanning tree error. resultMST="+resultMST+" idealMST="+idealMST, resultMST.equals(idealMST));
                if (debug > 0) System.out.println("Prim's minimum spanning tree worked correctly");
            }

            // Prim on a graph with cycles
            List<Vertex<Integer>> cyclicVerticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            cyclicVerticies.add(cv1);
            Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            cyclicVerticies.add(cv2);
            Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            cyclicVerticies.add(cv3);
            Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            cyclicVerticies.add(cv4);
            Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            cyclicVerticies.add(cv5);

            List<Edge<Integer>> cyclicEdges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(3, cv1, cv2);
            cyclicEdges.add(ce1_2);
            Graph.Edge<Integer> ce2_3 = new Graph.Edge<Integer>(2, cv2, cv3);
            cyclicEdges.add(ce2_3);
            Graph.Edge<Integer> ce3_4 = new Graph.Edge<Integer>(4, cv3, cv4);
            cyclicEdges.add(ce3_4);
            Graph.Edge<Integer> ce4_1 = new Graph.Edge<Integer>(1, cv4, cv1);
            cyclicEdges.add(ce4_1);
            Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(1, cv4, cv5);
            cyclicEdges.add(ce4_5);

            Graph<Integer> cyclicUndirected = new Graph<Integer>(cyclicVerticies, cyclicEdges);
            if (debug > 0) System.out.println(cyclicUndirected.toString());

            start = cv1;

            if (debug > 0) System.out.println("Prim's minimum spanning tree of a cyclic undirected graph from " + start.getValue());
            Graph.CostPathPair<Integer> pair4 = Prim.getMinimumSpanningTree(cyclicUndirected, start);
            if (debug > 0) System.out.println(pair4.toString());
            {
                // Ideal MST
                int cost = 7;
                Set<Graph.Edge<Integer>> set = new LinkedHashSet<Graph.Edge<Integer>>();
                set.add(new Graph.Edge<Integer>(1, undirected.v1, undirected.v4));
                set.add(ce4_5);
                set.add(ce1_2);
                set.add(ce2_3);
                Graph.CostPathPair<Integer> result4 = new Graph.CostPathPair<Integer>(cost, set);

                assertTrue("Prim's minimum spanning tree error. pair4="+pair4+" result4="+result4, pair4.equals(result4));
                if (debug > 0) System.out.println("Prim's minimum spanning tree worked correctly");
            }
        }
    }

    @Test
    public void testDijstraDirected() {
        if (debug>0) System.out.println(directed.toString());

        Graph.Vertex<Integer> start = directed.v1;
        Graph.Vertex<Integer> end = directed.v5;

        if (debug>0) System.out.println("Dijstra's shortest paths of the directed graph from "+start.getValue()+" to all.");
        Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(directed.graph, start);
        if (debug>0) System.out.println(getPathMapString(start, map1));

        // Compare results
        for (Graph.Vertex<Integer> v : map1.keySet()) {
            Graph.CostPathPair<Integer> path1 = map1.get(v);
            Graph.CostPathPair<Integer> path2 = idealDirectedPath.get(v);
            assertTrue("Dijstra's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
        }
        if (debug>0) System.out.println("Dijstra's shortest path worked correctly.");

        if (debug>0) System.out.println("Dijstra's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue()+".");
        Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(directed.graph, start, end);
        assertTrue("No path from "+start.getValue()+" to "+end.getValue(), (pair1!=null));

        // Compare pair
        assertTrue("Dijstra's shortest path error. pair1="+pair1+" idealPathPair="+idealPathPair, pair1.equals(idealPathPair));
        if (debug>0) System.out.println("Dijstra's shortest path worked correctly");
    }

    @Test
    public void testBellmanFordDirected() {
        Graph.Vertex<Integer> start = directed.v1;
        Graph.Vertex<Integer> end = directed.v5;

        if (debug>0) System.out.println("Bellman-Ford's shortest paths of the directed graph from "+start.getValue()+" to all.");
        Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed.graph, start);
        if (debug>0) System.out.println(getPathMapString(start, map2));

        // Compare results
        for (Graph.Vertex<Integer> v : map2.keySet()) {
            Graph.CostPathPair<Integer> path1 = map2.get(v);
            Graph.CostPathPair<Integer> path2 = idealDirectedPath.get(v);
            assertTrue("Bellman-Ford's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
        }
        if (debug>0) System.out.println("Bellman-Ford's shortest path worked correctly.");

        if (debug>0) System.out.println("Bellman-Ford's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue()+".");
        Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed.graph, start, end);
        assertTrue("No path from "+start.getValue()+" to "+end.getValue(), pair2!=null);

        // Compare pair
        assertTrue("Bellman-Ford's shortest path error. pair2="+pair2+" idealPathPair="+idealPathPair, pair2.equals(idealPathPair));
        if (debug>0) System.out.println("Bellman-Ford's shortest path worked correctly");
    }

    @Test
    public void testDijstraDirectedWihtNegativeWeights() {
        {   // DIRECTED GRAPH (WITH NEGATIVE WEIGHTS)
            if (debug > 0) System.out.println("Directed Graph with Negative Weights.");

            if (debug > 0) System.out.println(directedWithNegWeights.toString());

            Graph.Vertex<Integer> start = directedWithNegWeights.v1;
            Graph.Vertex<Integer> end = directedWithNegWeights.v3;

            if (debug > 0) System.out.println("Bellman-Ford's shortest paths of the directed graph with negative weight from " + start.getValue()+" to all.");
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = BellmanFord.getShortestPaths(directedWithNegWeights.graph, start);
            if (debug > 0) System.out.println(getPathMapString(start, map1));

            // Compare results
            for (Graph.Vertex<Integer> v : map1.keySet()) {
                Graph.CostPathPair<Integer> path1 = map1.get(v);
                Graph.CostPathPair<Integer> path2 = idealDirectedNegWeight.get(v);
                assertTrue("Bellman-Ford's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }
            if (debug > 0) System.out.println("Bellman-Ford's shortest path worked correctly.");

            if (debug > 0) System.out.println("Bellman-Ford's shortest path of the directed graph from with negative weight " + start.getValue() + " to " + end.getValue()+ ".");
            Graph.CostPathPair<Integer> pair1 = BellmanFord.getShortestPath(directedWithNegWeights.graph, start, end);
            assertTrue("No path from " + start.getValue() + " to " + end.getValue(), pair1 != null);

            // Compare pair
            assertTrue("Bellman-Ford's shortest path error. pair1="+pair1+" result2="+idealDirectedWithNegWeightsPathPair, pair1.equals(idealDirectedWithNegWeightsPathPair));
            if (debug > 0) System.out.println("Bellman-Ford's shortest path worked correctly");
        }
    }
 
    @Test
    @SuppressWarnings("unchecked")
    public void testJohnonsonsAllPairsShortestPathOnDirecteWithNegWeights() {
        {
            if (debug > 0) System.out.println("Johnson's all-pairs shortest path of the directed graph with negative weight.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> path1 = Johnson.getAllPairsShortestPaths(directedWithNegWeights.graph);
            if (debug > 0) System.out.println(getPathMapString(path1));
            assertTrue("Directed graph contains a negative weight cycle.", (path1 != null));

            Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> result3 = new HashMap<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>>();
            {
                {   // vertex 3
                    Map<Vertex<Integer>, Set<Edge<Integer>>> m = new HashMap<Vertex<Integer>, Set<Edge<Integer>>>();
                    Set<Edge<Integer>> s3 = new LinkedHashSet<Edge<Integer>>();
                    m.put(directedWithNegWeights.v3, s3);
                    Set<Edge<Integer>> s4 = new LinkedHashSet<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e3_4);
                    m.put(directedWithNegWeights.v4, s4);
                    Set<Edge<Integer>> s2 = new LinkedHashSet<Edge<Integer>>();
                    s2.add(directedWithNegWeights.e3_4);
                    s2.add(directedWithNegWeights.e4_2);
                    m.put(directedWithNegWeights.v2, s2);
                    Set<Edge<Integer>> s1 = new LinkedHashSet<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e3_1);
                    m.put(directedWithNegWeights.v1, s1);
                    result3.put(directedWithNegWeights.v3, m);
                }
                {   // vertex 4
                    Map<Vertex<Integer>, Set<Edge<Integer>>> m = new HashMap<Vertex<Integer>, Set<Edge<Integer>>>();
                    Set<Edge<Integer>> s3 = new LinkedHashSet<Edge<Integer>>();
                    s3.add(directedWithNegWeights.e4_2);
                    s3.add(directedWithNegWeights.e2_3);
                    m.put(directedWithNegWeights.v3, s3);
                    Set<Edge<Integer>> s4 = new LinkedHashSet<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e4_2);
                    s4.add(directedWithNegWeights.e2_3);
                    s4.add(directedWithNegWeights.e3_4);
                    m.put(directedWithNegWeights.v4, s4);
                    Set<Edge<Integer>> s2 = new LinkedHashSet<Edge<Integer>>();
                    s2.add(directedWithNegWeights.e4_2);
                    m.put(directedWithNegWeights.v2, s2);
                    Set<Edge<Integer>> s1 = new LinkedHashSet<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e4_2);
                    s1.add(directedWithNegWeights.e2_1);
                    m.put(directedWithNegWeights.v1, s1);
                    result3.put(directedWithNegWeights.v4, m);
                }
                {   // vertex 2
                    Map<Vertex<Integer>, Set<Edge<Integer>>> m = new HashMap<Vertex<Integer>, Set<Edge<Integer>>>();
                    Set<Edge<Integer>> s3 = new LinkedHashSet<Edge<Integer>>();
                    s3.add(directedWithNegWeights.e2_3);
                    m.put(directedWithNegWeights.v3, s3);
                    Set<Edge<Integer>> s4 = new LinkedHashSet<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e2_1);
                    s4.add(directedWithNegWeights.e1_4);
                    m.put(directedWithNegWeights.v4, s4);
                    Set<Edge<Integer>> s2 = new LinkedHashSet<Edge<Integer>>();
                    m.put(directedWithNegWeights.v2, s2);
                    Set<Edge<Integer>> s1 = new LinkedHashSet<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e2_1);
                    m.put(directedWithNegWeights.v1, s1);
                    result3.put(directedWithNegWeights.v2, m);
                }
                {   // vertex 1
                    Map<Vertex<Integer>, Set<Edge<Integer>>> m = new HashMap<Vertex<Integer>, Set<Edge<Integer>>>();
                    Set<Edge<Integer>> s3 = new LinkedHashSet<Edge<Integer>>();
                    s3.add(directedWithNegWeights.e1_4);
                    s3.add(directedWithNegWeights.e4_2);
                    s3.add(directedWithNegWeights.e2_3);
                    m.put(directedWithNegWeights.v3, s3);
                    Set<Edge<Integer>> s4 = new LinkedHashSet<Edge<Integer>>();
                    s4.add(directedWithNegWeights.e1_4);
                    m.put(directedWithNegWeights.v4, s4);
                    Set<Edge<Integer>> s2 = new LinkedHashSet<Edge<Integer>>();
                    s2.add(directedWithNegWeights.e1_4);
                    s2.add(directedWithNegWeights.e4_2);
                    m.put(directedWithNegWeights.v2, s2);
                    Set<Edge<Integer>> s1 = new LinkedHashSet<Edge<Integer>>();
                    s1.add(directedWithNegWeights.e1_4);
                    m.put(directedWithNegWeights.v1, s1);
                    result3.put(directedWithNegWeights.v1, m);
                }
            }

            // Compare results
            for (Vertex<Integer> vertex1 : path1.keySet()) {
                Map<Vertex<Integer>, Set<Edge<Integer>>> m1 = path1.get(vertex1);
                Map<Vertex<Integer>, Set<Edge<Integer>>> m2 = result3.get(vertex1);
                for (Vertex<Integer> vertex2 : m1.keySet()) {
                    Set<Edge<Integer>> set3 = m1.get(vertex2);
                    Set<Edge<Integer>> set4 = m2.get(vertex2);
                    Object[] objs1 = set3.toArray();
                    Object[] objs2 = set4.toArray();
                    int size = objs1.length;
                    for (int i=0; i<size; i++) {
                        Edge<Integer> e1 = (Edge<Integer>)objs1[i];
                        Edge<Integer> e2 = (Edge<Integer>)objs2[i];
                        assertTrue("Johnson's all-pairs shortest path error. e1.from="+e1.getFromVertex()+" e2.from="+e2.getFromVertex(), 
                                   e1.getFromVertex().equals(e2.getFromVertex()));
                        assertTrue("Johnson's all-pairs shortest path error. e1.to="+e1.getToVertex()+" e2.to="+e2.getToVertex(), 
                                   e1.getToVertex().equals(e2.getToVertex()));
                    }
                }
            }
            if (debug > 0) System.out.println("Johnson's all-pairs shortest path worked correctly.");
        }
    }

    @Test
    public void testFloydWarshallonDirectedWithNegWeights() {
        {
            if (debug > 0) System.out.println("Floyd-Warshall's all-pairs shortest path weights of the directed graph with negative weight.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> pathWeights = FloydWarshall.getAllPairsShortestPaths(directedWithNegWeights.graph);
            if (debug > 0) System.out.println(getWeightMapString(pathWeights));

            Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> result4 = new HashMap<Vertex<Integer>, Map<Vertex<Integer>, Integer>>();
            {
                // Ideal weights
                {   // Vertex 3
                    Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
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
                    result4.put(directedWithNegWeights.v3, m);
                }
                {   // Vertex 4
                    Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
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
                    result4.put(directedWithNegWeights.v4, m);
                }
                {   // Vertex 2
                    Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
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
                    result4.put(directedWithNegWeights.v2, m);
                }
                {   // Vertex 1
                    Map<Vertex<Integer>, Integer> m = new HashMap<Vertex<Integer>, Integer>();
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
                    result4.put(directedWithNegWeights.v1, m);
                }
            }

            // Compare results
            for (Vertex<Integer> vertex1 : pathWeights.keySet()) {
                Map<Vertex<Integer>, Integer> m1 = pathWeights.get(vertex1);
                Map<Vertex<Integer>, Integer> m2 = result4.get(vertex1);
                for (Vertex<Integer> v : m1.keySet()) {
                    int i1 = m1.get(v);
                    int i2 = m2.get(v);
                    assertTrue("Floyd-Warshall's all-pairs shortest path weights error. i1="+i1+" i2="+i2, i1 == i2);
                }

            }
            if (debug > 0) System.out.println("Floyd-Warshall's all-pairs shortest path worked correctly.");
        }
    }

    @Test
    public void cycleCheckOnUndirected() {
        {   // UNDIRECTED GRAPH
            if (debug > 0) System.out.println("Undirected Graph cycle check.");
            List<Vertex<Integer>> cycledVerticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            cycledVerticies.add(cv1);
            Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            cycledVerticies.add(cv2);
            Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            cycledVerticies.add(cv3);
            Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            cycledVerticies.add(cv4);
            Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            cycledVerticies.add(cv5);
            Graph.Vertex<Integer> cv6 = new Graph.Vertex<Integer>(6);
            cycledVerticies.add(cv6);

            List<Edge<Integer>> cycledEdges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(7, cv1, cv2);
            cycledEdges.add(ce1_2);
            Graph.Edge<Integer> ce2_4 = new Graph.Edge<Integer>(15, cv2, cv4);
            cycledEdges.add(ce2_4);
            Graph.Edge<Integer> ce3_4 = new Graph.Edge<Integer>(11, cv3, cv4);
            cycledEdges.add(ce3_4);
            Graph.Edge<Integer> ce3_6 = new Graph.Edge<Integer>(2, cv3, cv6);
            cycledEdges.add(ce3_6);
            Graph.Edge<Integer> ce5_6 = new Graph.Edge<Integer>(9, cv5, cv6);
            cycledEdges.add(ce5_6);
            Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(6, cv4, cv5);
            cycledEdges.add(ce4_5);

            Graph<Integer> undirectedWithCycle = new Graph<Integer>(cycledVerticies, cycledEdges);
            if (debug > 0) System.out.println(undirectedWithCycle.toString());

            if (debug > 0) System.out.println("Cycle detection of the undirected graph with a cycle.");
            boolean result = CycleDetection.detect(undirectedWithCycle);
            assertTrue("Cycle detection error.", result);
            if (debug > 0) System.out.println("Cycle detection worked correctly.");

            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
            verticies.add(v1);
            Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
            verticies.add(v2);
            Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
            verticies.add(v3);
            Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
            verticies.add(v4);
            Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
            verticies.add(v5);
            Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
            verticies.add(v6);

            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
            edges.add(e1_2);
            Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
            edges.add(e4_5);

            Graph<Integer> undirectedWithoutCycle = new Graph<Integer>(verticies, edges);
            if (debug > 0) System.out.println(undirectedWithoutCycle.toString());

            if (debug > 0) System.out.println("Cycle detection of the undirected graph without a cycle.");
            result = CycleDetection.detect(undirectedWithoutCycle);
            assertFalse("Cycle detection error.", result);
            if (debug > 0) System.out.println("Cycle detection worked correctly.");
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void topologicalSortOnDirectedGraph() {
        {   // DIRECTED GRAPH
            if (debug > 0) System.out.println("Directed Graph topological sort.");
            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);
            verticies.add(cv1);
            Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);
            verticies.add(cv2);
            Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);
            verticies.add(cv3);
            Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);
            verticies.add(cv4);
            Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);
            verticies.add(cv5);
            Graph.Vertex<Integer> cv6 = new Graph.Vertex<Integer>(6);
            verticies.add(cv6);

            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(1, cv1, cv2);
            edges.add(ce1_2);
            Graph.Edge<Integer> ce2_4 = new Graph.Edge<Integer>(2, cv2, cv4);
            edges.add(ce2_4);
            Graph.Edge<Integer> ce4_3 = new Graph.Edge<Integer>(3, cv4, cv3);
            edges.add(ce4_3);
            Graph.Edge<Integer> ce3_6 = new Graph.Edge<Integer>(4, cv3, cv6);
            edges.add(ce3_6);
            Graph.Edge<Integer> ce5_6 = new Graph.Edge<Integer>(5, cv5, cv6);
            edges.add(ce5_6);
            Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(6, cv4, cv5);
            edges.add(ce4_5);

            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
            if (debug > 0) System.out.println(directed.toString());

            System.out.println("Topological sort of the directed graph.");
            List<Graph.Vertex<Integer>> results = TopologicalSort.sort(directed);
            if (debug > 0) System.out.println("result=" + results);
            assertTrue("Topological sort error. results="+results, results.size()!=0);

            List<Graph.Vertex<Integer>> results2 = new ArrayList<Graph.Vertex<Integer>>(results.size());
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
                Object[] objs1 = results.toArray();
                Object[] objs2 = results2.toArray();
                int size = objs1.length;
                for (int i=0; i<size; i++) {
                    Graph.Vertex<Integer> v1 = (Graph.Vertex<Integer>) objs1[i];
                    Graph.Vertex<Integer> v2 = (Graph.Vertex<Integer>) objs2[i];
                    assertTrue("Topological sort error. v1="+v1+" v2", v1.equals(v2));
                }
            }
            if (debug > 0) System.out.println("Topological sort worked correctly.");

            if (debug > 0) System.out.println();
        }
    }

    private static final String getPathMapString(Graph.Vertex<Integer> start, Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : map.keySet()) {
            Graph.CostPathPair<Integer> pair = map.get(v);
            builder.append("From ").append(start.getValue()).append(" to vertex=").append(v.getValue()).append("\n");
            if (pair != null)
                builder.append(pair.toString()).append("\n");

        }
        return builder.toString();
    }

    private static final String getPathMapString(Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> paths) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : paths.keySet()) {
            Map<Vertex<Integer>, Set<Edge<Integer>>> map = paths.get(v);
            for (Graph.Vertex<Integer> v2 : map.keySet()) {
                builder.append("From=").append(v.getValue()).append(" to=").append(v2.getValue()).append("\n");
                Set<Graph.Edge<Integer>> path = map.get(v2);
                builder.append(path).append("\n");
            }
        }
        return builder.toString();
    }

    private static final String getWeightMapString(Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> paths) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : paths.keySet()) {
            Map<Vertex<Integer>, Integer> map = paths.get(v);
            for (Graph.Vertex<Integer> v2 : map.keySet()) {
                builder.append("From=").append(v.getValue()).append(" to=").append(v2.getValue()).append("\n");
                Integer weight = map.get(v2);
                builder.append(weight).append("\n");
            }
        }
        return builder.toString();
    }
}
