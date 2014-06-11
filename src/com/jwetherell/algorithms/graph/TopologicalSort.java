package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
     * @param graph
     * @return Sorted List of Vertices or NULL if graph has a cycle
     */
    public static final List<Graph.Vertex<Integer>> sort(Graph<Integer> graph) {
        if (graph == null)
            throw new IllegalArgumentException("Graph is NULL.");

        if (graph.getType() != Graph.TYPE.DIRECTED)
            throw new IllegalArgumentException("Cannot perform a topological sort on a non-directed graph. graph type = "+graph.getType());

        List<Graph.Vertex<Integer>> sorted = new ArrayList<Graph.Vertex<Integer>>();
        List<Graph.Vertex<Integer>> noOutgoing = new ArrayList<Graph.Vertex<Integer>>();

        List<Graph.Edge<Integer>> edges = new CopyOnWriteArrayList<Graph.Edge<Integer>>();
        edges.addAll(graph.getEdges());

        for (Graph.Vertex<Integer> v : graph.getVerticies()) {
            if (v.getEdges().size() == 0)
                noOutgoing.add(v);
        }
        while (noOutgoing.size() > 0) {
            Graph.Vertex<Integer> v = noOutgoing.remove(0);
            sorted.add(v);
            for (Graph.Edge<Integer> e : edges) {
                Graph.Vertex<Integer> v2 = e.getFromVertex();
                Graph.Vertex<Integer> v3 = e.getToVertex();
                if (v3.equals(v)) {
                    edges.remove(e);
                    v2.getEdges().remove(e);
                }
                if (v2.getEdges().size() == 0)
                    noOutgoing.add(v2);
            }
        }
        if (edges.size() > 0)
            return null;
        return sorted;
    }
}
