package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * In computer science, a topological sort (sometimes abbreviated topsort or
 * toposort) or topological ordering of a directed graph is a linear ordering of
 * its vertices such that, for every edge uv, u comes before v in the ordering.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TopologicalSort {

    private TopologicalSort() { }

    /**
     * Performs a topological sort on a directed graph. Returns NULL if a cycle is detected.
     * 
     * Note: This should not change the state of the graph parameter.
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

        final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        edges.addAll(clone.getEdges());

        for (Graph.Vertex<Integer> v : clone.getVerticies()) {
            if (v.getEdges().size() == 0)
                noOutgoing.add(v);
        }
        while (noOutgoing.size() > 0) {
            final Graph.Vertex<Integer> v = noOutgoing.remove(0);
            sorted.add(v);

            int i = 0;
            while (i < edges.size()) {
                final Graph.Edge<Integer> e = edges.get(i);
                final Graph.Vertex<Integer> from = e.getFromVertex();
                final Graph.Vertex<Integer> to = e.getToVertex();
                if (to.equals(v)) {
                    edges.remove(e);
                    from.getEdges().remove(e);
                } else {
                    i++;
                }
                if (from.getEdges().size() == 0)
                    noOutgoing.add(from);
            }
        }
        if (edges.size() > 0)
            return null;
        return sorted;
    }
}
