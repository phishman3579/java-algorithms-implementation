package com.jwetherell.algorithms.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * In computer science, cycle detection or cycle finding is the algorithmic problem of finding a cycle in a sequence of iterated function values.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Cycle_detection">Cycle Detection (Wikipedia)</a>
 * <br>
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
    public static <T extends Comparable<T>> boolean detect(Graph<T> graph) {
        if (graph == null)
            throw new IllegalArgumentException("Graph is NULL.");

        if (graph.getType() != Graph.TYPE.UNDIRECTED)
            throw new IllegalArgumentException("Graph is needs to be Undirected.");

        final Set<Graph.Vertex<T>> visitedVerticies = new HashSet<Graph.Vertex<T>>();
        final Set<Graph.Edge<T>> visitedEdges = new HashSet<Graph.Edge<T>>();

        final List<Graph.Vertex<T>> verticies = graph.getVertices();
        if (verticies == null || verticies.size() == 0)
            return false;

        // Select the zero-ith element as the root
        final Graph.Vertex<T> root = verticies.get(0);
        return depthFirstSearch(root, visitedVerticies, visitedEdges);
    }

    private static final <T extends Comparable<T>> boolean depthFirstSearch(Graph.Vertex<T> vertex, Set<Graph.Vertex<T>> visitedVerticies, Set<Graph.Edge<T>> visitedEdges) {
        if (!visitedVerticies.contains(vertex)) {
            // Found an unvisited, add to the set
            visitedVerticies.add(vertex);

            final List<Graph.Edge<T>> edges = vertex.getEdges();
            if (edges != null) {
                // Follow each unvisited edge, visit the vertex the edge connects to.
                for (Graph.Edge<T> edge : edges) {
                    final Graph.Vertex<T> to = edge.getToVertex();
                    boolean result = false;
                    if (to != null && !visitedEdges.contains(edge)) {
                        visitedEdges.add(edge);

                        final Graph.Edge<T> recip = new Graph.Edge<T>(edge.getCost(), edge.getToVertex(), edge.getFromVertex());
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
