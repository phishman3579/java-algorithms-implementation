package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * In computer science, a topological sort (sometimes abbreviated topsort or
 * toposort) or topological ordering of a directed graph is a linear ordering of
 * its vertices such that, for every edge uv, u comes before v in the ordering.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Topological_sorting">Topological Sort (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TopologicalSort {

    private TopologicalSort() { }

    /**
     * Performs a topological sort on a directed graph. Returns NULL if a cycle is detected.
     * 
     * Note: This should NOT change the state of the graph parameter.
     * 
     * @param graph
     * @return Sorted List of Vertices or NULL if graph has a cycle
     */
    public static final List<Graph.Vertex<Integer>> sort(Graph<Integer> graph) {
        if (graph == null)
            throw new IllegalArgumentException("Graph is NULL.");

        if (graph.getType() != Graph.TYPE.DIRECTED)
            throw new IllegalArgumentException("Cannot perform a topological sort on a non-directed graph. graph type = "+graph.getType());

        // clone to prevent changes the graph parameter's state
        final Graph<Integer> clone = new Graph<Integer>(graph);
        final List<Graph.Vertex<Integer>> sorted = new ArrayList<Graph.Vertex<Integer>>();
        final List<Graph.Vertex<Integer>> noOutgoing = new ArrayList<Graph.Vertex<Integer>>();

        // Work on a separate edge list and counts; leave each vertex's adjacency list intact.
        final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>(clone.getEdges());
        final Map<Graph.Vertex<Integer>, Integer> outgoingCounts =
                new IdentityHashMap<Graph.Vertex<Integer>, Integer>();

        // Find all the vertices which have no outgoing edges.
        for (Graph.Vertex<Integer> vertex : clone.getVertices()) {
            final int count = vertex.getEdges().size();
            outgoingCounts.put(vertex, count);
            if (count == 0)
                noOutgoing.add(vertex);
        }

        // Repeatedly remove incoming edges to vertices with no remaining outgoing edges.
        while (!noOutgoing.isEmpty()) {
            final Graph.Vertex<Integer> current = noOutgoing.remove(0);
            sorted.add(current);

            int i = 0;
            while (i < edges.size()) {
                final Graph.Edge<Integer> edge = edges.get(i);
                if (edge.getToVertex() == current) {
                    edges.remove(i);
                    final Graph.Vertex<Integer> from = edge.getFromVertex();
                    final int remaining = outgoingCounts.get(from) - 1;
                    outgoingCounts.put(from, remaining);
                    if (remaining == 0)
                        noOutgoing.add(from);
                } else {
                    i++;
                }
            }
        }
        // If we have processed all connected vertices and there are edges remaining, graph has multiple connected components.
        if (edges.size() > 0)
            return null;
        return sorted;
    }
}
