package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * Dijkstra's shortest path. Only works on non-negative path weights. Returns a
 * tuple of total cost of shortest path and the path.
 * <p>
 * Worst case: O(|E| + |V| log |V|)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">Dijkstra's Algorithm (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Dijkstra {

    private Dijkstra() { }

    public static Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getShortestPaths(Graph<Integer> graph, Graph.Vertex<Integer> start) {
        final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
        final Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = new HashMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();

        getShortestPath(graph, start, null, paths, costs);

        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        for (Graph.CostVertexPair<Integer> pair : costs.values()) {
            int cost = pair.getCost();
            Graph.Vertex<Integer> vertex = pair.getVertex();
            List<Graph.Edge<Integer>> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair<Integer>(cost, path));
        }
        return map;
    }

    public static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> graph, Graph.Vertex<Integer> start, Graph.Vertex<Integer> end) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        final boolean hasNegativeEdge = checkForNegativeEdges(graph.getVertices());
        if (hasNegativeEdge)
            throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
        final Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = new HashMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();
        return getShortestPath(graph, start, end, paths, costs);
    }

    private static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> graph, 
                                                              Graph.Vertex<Integer> start, Graph.Vertex<Integer> end,
                                                              Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths,
                                                              Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));
        if (start == null)
            throw (new NullPointerException("start must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        boolean hasNegativeEdge = checkForNegativeEdges(graph.getVertices());
        if (hasNegativeEdge)
            throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        for (Graph.Vertex<Integer> v : graph.getVertices())
            paths.put(v, new ArrayList<Graph.Edge<Integer>>());

        for (Graph.Vertex<Integer> v : graph.getVertices()) {
            if (v.equals(start))
                costs.put(v, new Graph.CostVertexPair<Integer>(0, v));
            else
                costs.put(v, new Graph.CostVertexPair<Integer>(Integer.MAX_VALUE, v));
        }

        final Queue<Graph.CostVertexPair<Integer>> unvisited = new PriorityQueue<Graph.CostVertexPair<Integer>>();
        unvisited.add(costs.get(start));

        while (!unvisited.isEmpty()) {
            final Graph.CostVertexPair<Integer> pair = unvisited.remove();
            final Graph.Vertex<Integer> vertex = pair.getVertex();

            // Compute costs from current vertex to all reachable vertices which haven't been visited
            for (Graph.Edge<Integer> e : vertex.getEdges()) {
                final Graph.CostVertexPair<Integer> toPair = costs.get(e.getToVertex()); // O(1)
                final Graph.CostVertexPair<Integer> lowestCostToThisVertex = costs.get(vertex); // O(1)
                final int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (toPair.getCost() == Integer.MAX_VALUE) {
                    // Haven't seen this vertex yet

                    // Need to remove the pair and re-insert, so the priority queue keeps it's invariants
                    unvisited.remove(toPair); // O(n)
                    toPair.setCost(cost);
                    unvisited.add(toPair); // O(log n)

                    // Update the paths
                    List<Graph.Edge<Integer>> set = paths.get(e.getToVertex()); // O(log n)
                    set.addAll(paths.get(e.getFromVertex())); // O(log n)
                    set.add(e);
                } else if (cost < toPair.getCost()) {
                    // Found a shorter path to a reachable vertex

                    // Need to remove the pair and re-insert, so the priority queue keeps it's invariants
                    unvisited.remove(toPair); // O(n)
                    toPair.setCost(cost);
                    unvisited.add(toPair); // O(log n)

                    // Update the paths
                    List<Graph.Edge<Integer>> set = paths.get(e.getToVertex()); // O(log n)
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex())); // O(log n)
                    set.add(e);
                }
            }

            // Termination conditions
            if (end != null && vertex.equals(end)) {
                // We are looking for shortest path to a specific vertex, we found it.
                break;
            }
        }

        if (end != null) {
            final Graph.CostVertexPair<Integer> pair = costs.get(end);
            final List<Graph.Edge<Integer>> set = paths.get(end);
            return (new Graph.CostPathPair<Integer>(pair.getCost(), set));
        }
        return null;
    }

    private static boolean checkForNegativeEdges(Collection<Graph.Vertex<Integer>> vertitices) {
        for (Graph.Vertex<Integer> v : vertitices) {
            for (Graph.Edge<Integer> e : v.getEdges()) {
                if (e.getCost() < 0)
                    return true;
            }
        }
        return false;
    }
}
