package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import com.jwetherell.algorithms.data_structures.Graph;


/**
 * Dijkstra's shortest path. Only works on non-negative path weights. Returns a
 * tuple of total cost of shortest path and the path.
 * 
 * Worst case: O(|E| + |V| log |V|)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Dijkstra {

    private static Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = null;
    private static Map<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>> paths = null;
    private static Queue<Graph.CostVertexPair<Integer>> unvisited = null;

    private Dijkstra() {
    }

    public static Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getShortestPaths(Graph<Integer> g, Graph.Vertex<Integer> start) {
        getShortestPath(g, start, null);
        Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        for (Graph.CostVertexPair<Integer> pair : costs.values()) {
            int cost = pair.getCost();
            Graph.Vertex<Integer> vertex = pair.getVertex();
            Set<Graph.Edge<Integer>> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair<Integer>(cost, path));
        }
        return map;
    }

    public static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> g, Graph.Vertex<Integer> start, Graph.Vertex<Integer> end) {
        if (g == null) throw (new NullPointerException("Graph must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        boolean hasNegativeEdge = checkForNegativeEdges(g.getVerticies());
        if (hasNegativeEdge) throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        paths = new TreeMap<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>>();
        for (Graph.Vertex<Integer> v : g.getVerticies()) {
            paths.put(v, new LinkedHashSet<Graph.Edge<Integer>>());
        }

        costs = new TreeMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();
        for (Graph.Vertex<Integer> v : g.getVerticies()) {
            if (v.equals(start)) costs.put(v, new Graph.CostVertexPair<Integer>(0, v));
            else costs.put(v, new Graph.CostVertexPair<Integer>(Integer.MAX_VALUE, v));
        }

        unvisited = new PriorityQueue<Graph.CostVertexPair<Integer>>();
        unvisited.addAll(costs.values()); // Shallow copy

        Graph.Vertex<Integer> vertex = start;
        while (true) {
            // Compute costs from current vertex to all reachable vertices which
            // haven't been visited
            for (Graph.Edge<Integer> e : vertex.getEdges()) {
                Graph.CostVertexPair<Integer> pair = costs.get(e.getToVertex());
                Graph.CostVertexPair<Integer> lowestCostToThisVertex = costs.get(vertex);
                int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (pair.getCost() == Integer.MAX_VALUE) {
                    // Haven't seen this vertex yet
                    pair.setCost(cost);
                    Set<Graph.Edge<Integer>> set = paths.get(e.getToVertex());
                    set.addAll(paths.get(e.getFromVertex()));
                    set.add(e);
                } else if (cost < pair.getCost()) {
                    // Found a shorter path to a reachable vertex
                    pair.setCost(cost);
                    Set<Graph.Edge<Integer>> set = paths.get(e.getToVertex());
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex()));
                    set.add(e);
                }
            }

            // Termination conditions
            if (end != null && vertex.equals(end)) {
                // If we are looking for shortest path, we found it.
                break;
            } else if (unvisited.size() > 0) {
                // If there are other vertices to visit (which haven't been
                // visited yet)
                Graph.CostVertexPair<Integer> pair = unvisited.remove();
                vertex = pair.getVertex();
                if (pair.getCost() == Integer.MAX_VALUE) {
                    // If the only edge left to explore has MAX_VALUE then it
                    // cannot be reached from the starting vertex
                    break;
                }
            } else {
                // No more edges to explore, we are done.
                break;
            }
        }

        if (end != null) {
            Graph.CostVertexPair<Integer> pair = costs.get(end);
            Set<Graph.Edge<Integer>> set = paths.get(end);
            return (new Graph.CostPathPair<Integer>(pair.getCost(), set));
        }
        return null;
    }

    private static boolean checkForNegativeEdges(List<Graph.Vertex<Integer>> vertitices) {
        for (Graph.Vertex<Integer> v : vertitices) {
            for (Graph.Edge<Integer> e : v.getEdges()) {
                if (e.getCost() < 0) return true;
            }
        }
        return false;
    }
}
