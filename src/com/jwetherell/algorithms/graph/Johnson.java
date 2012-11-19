package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;


/**
 * Johnson's algorithm is a way to find the shortest paths between all pairs of
 * vertices in a sparse directed graph. It allows some of the edge weights to be
 * negative numbers, but no negative-weight cycles may exist.
 * 
 * Worst case: O(V^2 log V + VE)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Johnson {

    private Johnson() {
    }

    public static Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>>> getAllPairsShortestPaths(Graph<Integer> g) {
        Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>>> allShortestPaths = new HashMap<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>>>();

        // Add the connector Vertex to all edges.
        for (Graph.Vertex<Integer> v : g.getVerticies()) {
            Graph<Integer> graph = new Graph<Integer>(g); // Clone the original
                                                          // graph
            Graph.Vertex<Integer> connector = new Graph.Vertex<Integer>(Integer.MAX_VALUE); // Make
                                                                                            // new
                                                                                            // Vertex
                                                                                            // that
                                                                                            // connects
                                                                                            // to
                                                                                            // all
                                                                                            // Vertices
            graph.getVerticies().add(connector);

            int indexOfV = graph.getVerticies().indexOf(v);
            Graph.Edge<Integer> e = new Graph.Edge<Integer>(0, connector, graph.getVerticies().get(indexOfV));
            connector.addEdge(e);
            graph.getEdges().add(e);

            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> costs = BellmanFord.getShortestPaths(graph, connector);
            if (BellmanFord.containsNegativeWeightCycle()) {
                System.out.println("Graph contains a negative weight cycle. Cannot compute shortest path.");
                return null;
            }
            for (Graph.Vertex<Integer> v2 : costs.keySet()) {
                int index = graph.getVerticies().indexOf(v2);
                Graph.Vertex<Integer> vertexToAdjust = graph.getVerticies().get(index);
                Graph.CostPathPair<Integer> pair = costs.get(v2);
                vertexToAdjust.setWeight(pair.getCost());
            }

            for (Graph.Edge<Integer> e2 : graph.getEdges()) {
                int startCost = e2.getFromVertex().getWeight();
                int endCode = e2.getToVertex().getWeight();
                int adjCost = e2.getCost() + startCost - endCode;
                e2.setCost(adjCost);
            }

            int index = graph.getVerticies().indexOf(connector);
            graph.getVerticies().remove(index);
            index = graph.getEdges().indexOf(e);
            graph.getEdges().remove(index);

            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> costPaths = Dijkstra.getShortestPaths(graph, v);
            Map<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, Set<Graph.Edge<Integer>>>();
            for (Graph.Vertex<Integer> v2 : costPaths.keySet()) {
                Graph.CostPathPair<Integer> pair = costPaths.get(v2);
                paths.put(v2, pair.getPath());
            }
            allShortestPaths.put(v, paths);
        }
        return allShortestPaths;
    }
}
