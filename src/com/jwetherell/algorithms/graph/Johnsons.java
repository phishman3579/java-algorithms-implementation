package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;


public class Johnsons {

    
    public static Map<Graph.Vertex, Map<Graph.Vertex, Set<Graph.Edge>>> getAllPairsShortestPaths(Graph g) {
        Map<Graph.Vertex, Map<Graph.Vertex, Set<Graph.Edge>>> allShortestPaths = new HashMap<Graph.Vertex, Map<Graph.Vertex, Set<Graph.Edge>>>();

        // Add the connector Vertex to all edges.
        for (Graph.Vertex v : g.getVerticies()) {
            Graph graph = new Graph(g); // Clone the original graph
            Graph.Vertex connector = new Graph.Vertex(Integer.MAX_VALUE); //Make new Vertex that connects to all Vertices
            graph.getVerticies().add(connector);

            int indexOfV = graph.getVerticies().indexOf(v);
            Graph.Edge e = new Graph.Edge(0, connector, graph.getVerticies().get(indexOfV));
            connector.addEdge(e);
            graph.getEdges().add(e);
            
            Map<Graph.Vertex, Graph.CostPathPair> costs = BellmanFord.getShortestPaths(graph, connector);
            if (BellmanFord.containsNegativeWeightCycle()) {
                System.out.println("Graph contains a negative weight cycle. Cannot compute shortest path.");
                return null;
            }
            for (Graph.Vertex v2 : costs.keySet()) {
                int index = graph.getVerticies().indexOf(v2);
                Graph.Vertex vertexToAdjust = graph.getVerticies().get(index);
                Graph.CostPathPair pair = costs.get(v2);
                vertexToAdjust.setWeight(pair.getCost());
            }
            
            for (Graph.Edge e2 : graph.getEdges()) {
                int startCost = e2.getFromVertex().getWeight();
                int endCode = e2.getToVertex().getWeight();
                int adjCost = e2.getCost() + startCost - endCode;
                e2.setCost(adjCost);
            }
            
            int index = graph.getVerticies().indexOf(connector);
            graph.getVerticies().remove(index);
            index = graph.getEdges().indexOf(e);
            graph.getEdges().remove(index);
            
            Map<Graph.Vertex, Graph.CostPathPair> costPaths = Dijkstra.getShortestPaths(graph, v);
            Map<Graph.Vertex, Set<Graph.Edge>> paths = new HashMap<Graph.Vertex, Set<Graph.Edge>>();
            for (Graph.Vertex v2 : costPaths.keySet()) {
                Graph.CostPathPair  pair = costPaths.get(v2);
                paths.put(v2, pair.getPath());
            }
            allShortestPaths.put(v, paths);
        }
        return allShortestPaths;
    }
}
