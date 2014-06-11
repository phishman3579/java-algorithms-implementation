package com.jwetherell.algorithms.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * Cycle detection in an undirected graph using depth first search.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class CycleDetection {

    private static Set<Graph.Vertex<Integer>> visitedVerticies = new HashSet<Graph.Vertex<Integer>>();
    private static Set<Graph.Edge<Integer>> visitedEdges = new HashSet<Graph.Edge<Integer>>();

    private CycleDetection() { }

    /**
     * Cycle detection on a unidrected graph.
     * 
     * @param graph Graph
     * @return true if a cycle exists
     */
    public static boolean detect(Graph<Integer> graph) {
        if (graph == null)
            throw new IllegalArgumentException("Graph is NULL.");

        if (graph.getType() != Graph.TYPE.UNDIRECTED)
            throw new IllegalArgumentException("Graph is needs to be Undirected.");

        visitedVerticies.clear();
        visitedEdges.clear();
        List<Graph.Vertex<Integer>> verticies = graph.getVerticies();
        if (verticies == null || verticies.size() == 0)
            return false;

        // Select the zero-ith element as the root
        Graph.Vertex<Integer> root = verticies.get(0);
        return depthFirstSearch(root);
    }

    private static final boolean depthFirstSearch(Graph.Vertex<Integer> vertex) {
        if (!visitedVerticies.contains(vertex)) {
            // Not visited
            visitedVerticies.add(vertex);

            List<Graph.Edge<Integer>> edges = vertex.getEdges();
            if (edges != null) {
                for (Graph.Edge<Integer> edge : edges) {
                    Graph.Vertex<Integer> to = edge.getToVertex();
                    boolean result = false;
                    if (to != null && !visitedEdges.contains(edge)) {
                        visitedEdges.add(edge);
                        Graph.Edge<Integer> recip = new Graph.Edge<Integer>(edge.getCost(), edge.getToVertex(), edge.getFromVertex());
                        visitedEdges.add(recip);
                        result = depthFirstSearch(to);
                    }
                    if (result == true)
                        return result;
                }
            }
        } else {
            // visited
            return true;
        }
        return false;
    }
}
