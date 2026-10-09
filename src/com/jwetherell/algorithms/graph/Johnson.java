package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * Johnson's algorithm is a way to find the shortest paths between all pairs of
 * vertices in a sparse directed graph. It allows some of the edge weights to be
 * negative numbers, but no negative-weight cycles may exist.
 * <p>
 * Worst case: O(V^2 log V + VE)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Johnson%27s_algorithm">Johnson's Algorithm (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Johnson {

    private Johnson() { }

    public static Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>> getAllPairsShortestPaths(Graph<Integer> g) {
        if (g == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        // First, a new node 'connector' is added to the graph, connected by zero-weight edges to each of the other nodes.
        final Graph<Integer> graph = new Graph<Integer>(g);
        final Map<Graph.Vertex<Integer>, Graph.Vertex<Integer>> originalVertexByCopy =
                new IdentityHashMap<Graph.Vertex<Integer>, Graph.Vertex<Integer>>();
        final Map<Graph.Edge<Integer>, Graph.Edge<Integer>> originalEdgeByCopy =
                new IdentityHashMap<Graph.Edge<Integer>, Graph.Edge<Integer>>();
        for (int i = 0; i < graph.getVertices().size(); i++)
            originalVertexByCopy.put(graph.getVertices().get(i), g.getVertices().get(i));
        for (int i = 0; i < graph.getEdges().size(); i++)
            originalEdgeByCopy.put(graph.getEdges().get(i), g.getEdges().get(i));

        final Graph.Vertex<Integer> connector = new Graph.Vertex<Integer>(Integer.MAX_VALUE);

        // Add a zero-cost edge from the connector to each copied vertex.
        for (Graph.Vertex<Integer> v : graph.getVertices()) {
            final Graph.Edge<Integer> edge = new Graph.Edge<Integer>(0, connector, v);
            connector.addEdge(edge);
            graph.getEdges().add(edge);
        }

        graph.getVertices().add(connector);

        // Second, the Bellman–Ford algorithm is used, starting from the new vertex 'connector', to find for each vertex 'v'
        // the minimum weight h(v) of a path from 'connector' to 'v'. If this step detects a negative cycle, the algorithm is terminated.
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> costs = BellmanFord.getShortestPaths(graph, connector);

        // Next the edges of the original graph are re-weighted using the values computed by the Bellman–Ford algorithm: an edge 
        // from u to v, having length w(u,v), is given the new length w(u,v) + h(u) − h(v).
        for (Graph.Edge<Integer> e : graph.getEdges()) {
            final int weight = e.getCost();
            final Graph.Vertex<Integer> u = e.getFromVertex();
            final Graph.Vertex<Integer> v = e.getToVertex();

            // Don't worry about the connector
            if (u == connector || v == connector) 
                continue;

            // Adjust the costs
            final int uCost = costs.get(u).getCost();
            final int vCost = costs.get(v).getCost();
            final int newWeight = weight + uCost - vCost;
            e.setCost(newWeight);
        }

        // Finally, 'connector' is removed, and Dijkstra's algorithm is used to find the shortest paths from each node (s) to every 
        // other vertex in the re-weighted graph.
        final int indexOfConnector = graph.getVertices().indexOf(connector);
        graph.getVertices().remove(indexOfConnector);
        for (Graph.Edge<Integer> e : connector.getEdges()) {
            final int indexOfConnectorEdge = graph.getEdges().indexOf(e);
            graph.getEdges().remove(indexOfConnectorEdge);
        }

        final Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>> copiedPaths =
                new HashMap<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>>();
        for (Graph.Vertex<Integer> v : graph.getVertices()) {
            final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> costPaths = Dijkstra.getShortestPaths(graph, v);
            final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths =
                    new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
            for (Graph.Vertex<Integer> v2 : costPaths.keySet()) {
                final Graph.CostPathPair<Integer> pair = costPaths.get(v2);
                paths.put(v2, pair.getPath());
            }
            copiedPaths.put(v, paths);
        }

        // Expose paths using the caller's original vertices and edges, not the internal copies.
        final Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>> allShortestPaths =
                new HashMap<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>>();
        for (Map.Entry<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>> entry
                : copiedPaths.entrySet()) {
            final Graph.Vertex<Integer> originalSource = originalVertexByCopy.get(entry.getKey());
            final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> originalPaths =
                    new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
            for (Map.Entry<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> pathEntry : entry.getValue().entrySet()) {
                final Graph.Vertex<Integer> originalDestination = originalVertexByCopy.get(pathEntry.getKey());
                final List<Graph.Edge<Integer>> originalPath = new ArrayList<Graph.Edge<Integer>>();
                for (Graph.Edge<Integer> edge : pathEntry.getValue()) {
                    final Graph.Edge<Integer> originalEdge = originalEdgeByCopy.get(edge);
                    if (originalEdge != null)
                        originalPath.add(originalEdge);
                }
                originalPaths.put(originalDestination, originalPath);
            }
            allShortestPaths.put(originalSource, originalPaths);
        }
        return allShortestPaths;
    }
}
