package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.jwetherell.algorithms.data_structures.Graph;


/**
 * Bellman-Ford's shortest path. Works on both negative and positive weighted edges. Also detects
 * negative weight cycles. Returns a tuple of total cost of shortest path and the path.
 * 
 * Worst case: O(|V| |E|)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BellmanFord {

    private static Map<Graph.Vertex, Graph.CostVertexPair> costs = null;
    private static Map<Graph.Vertex, Set<Graph.Edge>> paths = null;

    private BellmanFord() { }

    public static Map<Graph.Vertex, Graph.CostPathPair> getShortestPaths(Graph g, Graph.Vertex start) {
        getShortestPath(g,start,null);
        Map<Graph.Vertex, Graph.CostPathPair> map = new HashMap<Graph.Vertex, Graph.CostPathPair>();
        for (Graph.CostVertexPair pair : costs.values()) {
            int cost = pair.getCost();
            Graph.Vertex vertex = pair.getVertex();
            Set<Graph.Edge> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair(cost,path));
        }
        return map;
    }
    
    public static Graph.CostPathPair getShortestPath(Graph g, Graph.Vertex start, Graph.Vertex end) {
        if (g==null) throw (new NullPointerException("Graph must be non-NULL."));

        paths = new TreeMap<Graph.Vertex, Set<Graph.Edge>>();
        for (Graph.Vertex v : g.getVerticies()) {
            paths.put(v, new LinkedHashSet<Graph.Edge>());
        }

        costs = new TreeMap<Graph.Vertex, Graph.CostVertexPair>();
        for (Graph.Vertex v : g.getVerticies()) {
            if (v.equals(start)) costs.put(v,new Graph.CostVertexPair(0,v));
            else costs.put(v,new Graph.CostVertexPair(Integer.MAX_VALUE,v));
        }

        boolean negativeCycleCheck = false;
        for (int i=0; i<(g.getVerticies().size()); i++) {
            
            // If it's the last vertices perform a negative weight cycle check. The graph should be 
            // finished by the size()-1 time through this loop.
            if (i==(g.getVerticies().size()-1)) negativeCycleCheck = true;
            
            // Compute costs to all vertices
            for (Graph.Edge e : g.getEdges()) {
                Graph.CostVertexPair pair = costs.get(e.getToVertex());
                Graph.CostVertexPair lowestCostToThisVertex = costs.get(e.getFromVertex());
                
                // If the cost of the from vertex is MAX_VALUE then treat as INIFINITY.
                if (lowestCostToThisVertex.getCost()==Integer.MAX_VALUE) continue;
                
                int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (cost<pair.getCost()) {
                    if (negativeCycleCheck) {
                        // Uhh ohh... negative weight cycle
                        System.out.println("Graph contains a negative weight cycle.");
                    } else {
                        // Found a shorter path to a reachable vertex
                        pair.setCost(cost);
                        Set<Graph.Edge> set = paths.get(e.getToVertex());
                        set.clear();
                        set.addAll(paths.get(e.getFromVertex()));
                        set.add(e);
                    }
                }
            }
        }

        if (end!=null) {
            Graph.CostVertexPair pair = costs.get(end);
            Set<Graph.Edge> set = paths.get(end);
            return (new Graph.CostPathPair(pair.getCost(),set));
        }
        return null;
    }
}
