package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * Bellman-Ford's shortest path. Works on both negative and positive weighted
 * edges. Also detects negative weight cycles. Returns a tuple of total cost of
 * shortest path and the path.
 * <p>
 * Worst case: O(|V| |E|)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm">Bellman Ford (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BellmanFord {

    private BellmanFord() { }

    /**
     * Get shortest path for all vertices
     */
    public static Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getShortestPaths(Graph<Integer> graph, Graph.Vertex<Integer> start) {
        final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
        final Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = new HashMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();

        getShortestPath(graph, start, paths, costs);

        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        for (Graph.CostVertexPair<Integer> pair : costs.values()) {
            final int cost = pair.getCost();
            final Graph.Vertex<Integer> vertex = pair.getVertex();
            final List<Graph.Edge<Integer>> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair<Integer>(cost, path));
        }
        return map;
    }

    /**
     * Get shortest path to from 'start' to 'end' vertices
     */
    public static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> graph, Graph.Vertex<Integer> start, Graph.Vertex<Integer> end) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
        final Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = new HashMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();
        return getShortestPath(graph, start, end, paths, costs);
    }

    private static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> graph, 
                                                               Graph.Vertex<Integer> start, Graph.Vertex<Integer> end,
                                                               Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths,
                                                               Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs) {
        if (end == null)
            throw (new NullPointerException("end must be non-NULL."));

        getShortestPath(graph, start, paths, costs);

        final Graph.CostVertexPair<Integer> pair = costs.get(end);
        final List<Graph.Edge<Integer>> list = paths.get(end);
        return (new Graph.CostPathPair<Integer>(pair.getCost(), list));
    }

    private static void getShortestPath(Graph<Integer> graph, 
                                        Graph.Vertex<Integer> start,
                                        Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths,
                                        Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));
        if (start == null)
            throw (new NullPointerException("start must be non-NULL."));

        for (Graph.Vertex<Integer> v : graph.getVertices())
            paths.put(v, new ArrayList<Graph.Edge<Integer>>());

        // All vertices are INFINITY unless it's the start vertices
        for (Graph.Vertex<Integer> v : graph.getVertices())
            if (v.equals(start))
                costs.put(v, new Graph.CostVertexPair<Integer>(0, v));
            else
                costs.put(v, new Graph.CostVertexPair<Integer>(Integer.MAX_VALUE, v));

        boolean negativeCycleCheck = false;
        for (int i = 0; i < graph.getVertices().size(); i++) {
            // If it's the last vertices, perform a negative weight cycle check.
            // The graph should be finished by the size()-1 time through this loop.
            if (i == (graph.getVertices().size() - 1))
                negativeCycleCheck = true;

            // Compute costs to all vertices
            for (Graph.Edge<Integer> e : graph.getEdges()) {
                final Graph.CostVertexPair<Integer> pair = costs.get(e.getToVertex());
                final Graph.CostVertexPair<Integer> lowestCostToThisVertex = costs.get(e.getFromVertex());

                // If the cost of the from vertex is MAX_VALUE then treat as INIFINITY.
                if (lowestCostToThisVertex.getCost() == Integer.MAX_VALUE)
                    continue;

                final int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (cost < pair.getCost()) {
                    // Found a shorter path to a reachable vertex
                    pair.setCost(cost);

                    if (negativeCycleCheck) {
                        // Uhh ohh... negative weight cycle
                        throw new IllegalArgumentException("Graph contains a negative weight cycle.");
                    }

                    final List<Graph.Edge<Integer>> list = paths.get(e.getToVertex());
                    list.clear();
                    list.addAll(paths.get(e.getFromVertex()));
                    list.add(e);
                }
            }
        }
    }
}
