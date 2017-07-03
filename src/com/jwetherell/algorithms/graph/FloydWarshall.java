package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * Floyd–Warshall algorithm is a graph analysis algorithm for finding shortest
 * paths in a weighted graph (with positive or negative edge weights).
 * <p>
 * Worst case: O(V^3)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm">Floyd-Warshall Algorithm (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class FloydWarshall {

    private FloydWarshall() { }

    public static Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Integer>> getAllPairsShortestPaths(Graph<Integer> graph) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        final List<Graph.Vertex<Integer>> vertices = graph.getVertices();

        final int[][] sums = new int[vertices.size()][vertices.size()];
        for (int i = 0; i < sums.length; i++) {
            for (int j = 0; j < sums[i].length; j++) {
                sums[i][j] = Integer.MAX_VALUE;
            }
        }

        final List<Graph.Edge<Integer>> edges = graph.getEdges();
        for (Graph.Edge<Integer> e : edges) {
            final int indexOfFrom = vertices.indexOf(e.getFromVertex());
            final int indexOfTo = vertices.indexOf(e.getToVertex());
            sums[indexOfFrom][indexOfTo] = e.getCost();
        }

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (i == j) {
                        sums[i][j] = 0;
                    } else {
                        final int ijCost = sums[i][j];
                        final int ikCost = sums[i][k];
                        final int kjCost = sums[k][j];
                        final int summed = (ikCost != Integer.MAX_VALUE && 
                                            kjCost != Integer.MAX_VALUE) ? 
                                                   (ikCost + kjCost)
                                               : 
                                                   Integer.MAX_VALUE;
                        if (ijCost > summed)
                            sums[i][j] = summed;
                    }
                }
            }
        }

        final Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Integer>> allShortestPaths = new HashMap<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Integer>>();
        for (int i = 0; i < sums.length; i++) {
            for (int j = 0; j < sums[i].length; j++) {
                final Graph.Vertex<Integer> from = vertices.get(i);
                final Graph.Vertex<Integer> to = vertices.get(j);

                Map<Graph.Vertex<Integer>, Integer> map = allShortestPaths.get(from);
                if (map == null)
                    map = new HashMap<Graph.Vertex<Integer>, Integer>();

                final int cost = sums[i][j];
                if (cost != Integer.MAX_VALUE)
                    map.put(to, cost);
                allShortestPaths.put(from, map);
            }
        }
        return allShortestPaths;
    }
}
