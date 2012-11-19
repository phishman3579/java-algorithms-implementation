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

    private TopologicalSort() {
    };

    public static final List<Graph.Vertex<Integer>> sort(Graph<Integer> graph) {
        List<Graph.Vertex<Integer>> sorted = new ArrayList<Graph.Vertex<Integer>>();
        List<Graph.Vertex<Integer>> noOutgoing = new ArrayList<Graph.Vertex<Integer>>();
        for (Graph.Vertex<Integer> v : graph.getVerticies()) {
            if (v.getEdges().size() == 0) {
                noOutgoing.add(v);
            }
        }
        while (noOutgoing.size() > 0) {
            Graph.Vertex<Integer> v = noOutgoing.remove(0);
            sorted.add(v);
            for (Graph.Edge<Integer> e : graph.getEdges()) {
                Graph.Vertex<Integer> v2 = e.getFromVertex();
                Graph.Vertex<Integer> v3 = e.getToVertex();
                if (v3.equals(v)) {
                    graph.getEdges().remove(e);
                    v2.getEdges().remove(e);
                }
                if (v2.getEdges().size() == 0) noOutgoing.add(v2);
            }
        }
        if (graph.getEdges().size() > 0) System.out.println("cycle detected");
        return sorted;
    }
}
