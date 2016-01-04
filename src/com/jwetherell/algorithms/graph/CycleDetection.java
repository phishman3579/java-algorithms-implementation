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

        final Set<Graph.Vertex<Integer>> visitedVerticies = new HashSet<Graph.Vertex<Integer>>();
        final Set<Graph.Edge<Integer>> visitedEdges = new HashSet<Graph.Edge<Integer>>();

        final List<Graph.Vertex<Integer>> verticies = graph.getVertices();
        if (verticies == null || verticies.size() == 0)
            return false;

        // Select the zero-ith element as the root
        final Graph.Vertex<Integer> root = verticies.get(0);
        return depthFirstSearch(root, visitedVerticies, visitedEdges);
    }

    private static final boolean depthFirstSearch(Graph.Vertex<Integer> vertex, Set<Graph.Vertex<Integer>> visitedVerticies, Set<Graph.Edge<Integer>> visitedEdges) {
        if (!visitedVerticies.contains(vertex)) {
            // Found an unvisited, add to the set
            visitedVerticies.add(vertex);

            final List<Graph.Edge<Integer>> edges = vertex.getEdges();
            if (edges != null) {
                // Follow each unvisited edge, visit the vertex the edge connects to.
                for (Graph.Edge<Integer> edge : edges) {
                    final Graph.Vertex<Integer> to = edge.getToVertex();
                    boolean result = false;
                    if (to != null && !visitedEdges.contains(edge)) {
                        visitedEdges.add(edge);

                        final Graph.Edge<Integer> recip = new Graph.Edge<Integer>(edge.getCost(), edge.getToVertex(), edge.getFromVertex());
                        visitedEdges.add(recip);

                        result = depthFirstSearch(to, visitedVerticies, visitedEdges);
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
