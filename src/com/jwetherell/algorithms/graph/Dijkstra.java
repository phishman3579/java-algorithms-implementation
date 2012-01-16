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
 * Dijkstra's shortest path. Only works on non-negative path weights. Returns a tuple of 
 * total cost of shortest path and the path.
 * 
 * Worst case: O(|E| + |V| log |V|)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Dijkstra {

    private static Map<Graph.Vertex, Graph.CostVertexPair> costs = null;
    private static Map<Graph.Vertex, Set<Graph.Vertex>> paths = null;
    private static Queue<Graph.CostVertexPair> unvisited = null;

    private Dijkstra() { }

    public static Map<Graph.Vertex, Graph.CostPathPair> getShortestPaths(Graph g, Graph.Vertex start) {
        getShortestPath(g,start,null);
        Map<Graph.Vertex, Graph.CostPathPair> map = new HashMap<Graph.Vertex, Graph.CostPathPair>();
        for (Graph.CostVertexPair pair : costs.values()) {
            int cost = pair.getCost();
            Graph.Vertex vertex = pair.getVertex();
            Set<Graph.Vertex> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair(cost,path));
        }
        return map;
    }
    
    public static Graph.CostPathPair getShortestPath(Graph g, Graph.Vertex start, Graph.Vertex end) {
        if (g==null) throw (new NullPointerException("Graph must be non-NULL."));
        
        // Dijkstra's algorithm only works on positive cost graphs
        boolean hasNegativeEdge = checkForNegativeEdges(g.getVerticies());
        if (hasNegativeEdge) throw (new IllegalArgumentException("Negative cost Edges are not allowed.")); 

        paths = new TreeMap<Graph.Vertex, Set<Graph.Vertex>>();
        for (Graph.Vertex v : g.getVerticies()) {
            paths.put(v, new LinkedHashSet<Graph.Vertex>());
        }

        costs = new TreeMap<Graph.Vertex, Graph.CostVertexPair>();
        for (Graph.Vertex v : g.getVerticies()) {
            if (v.equals(start)) costs.put(v,new Graph.CostVertexPair(0,v));
            else costs.put(v,new Graph.CostVertexPair(Integer.MAX_VALUE,v));
        }
        
        unvisited = new PriorityQueue<Graph.CostVertexPair>();
        unvisited.addAll(costs.values()); // Shallow copy

        Graph.Vertex vertex = start;
        while (true) {
            // Compute costs from current vertex to all reachable vertices which haven't been visited
            for (Graph.Edge e : vertex.getEdges()) {
                Graph.CostVertexPair pair = costs.get(e.getToVertex());
                Graph.CostVertexPair lowestCostToThisVertex = costs.get(vertex);
                int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (pair.getCost()==Integer.MAX_VALUE) {
                    // Haven't seen this vertex yet
                    pair.setCost(cost);
                    Set<Graph.Vertex> set = paths.get(e.getToVertex());
                    set.addAll(paths.get(e.getFromVertex()));
                    set.add(e.getFromVertex());
                } else if (cost<pair.getCost()) {
                    // Found a shorter path to a reachable vertex
                    pair.setCost(cost);
                    Set<Graph.Vertex> set = paths.get(e.getToVertex());
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex()));
                    set.add(e.getFromVertex());
                }
            }

            // Termination conditions
            if (end!=null && vertex.equals(end)) {
                // If we are looking for shortest path, we found it.
                break;
            }  else if (unvisited.size()>0) {
                // If there are other vertices to visit (which haven't been visited yet)
                Graph.CostVertexPair pair = unvisited.remove();
                vertex = pair.getVertex();
                if (pair.getCost() == Integer.MAX_VALUE) {
                    // If the only edge left to explore has MAX_VALUE then it cannot be reached from the starting vertex
                    break;
                }
            } else {
                // No more edges to explore, we are done.
                break;
            }
        }

        // Add the end vertex to the Set, just to make it more understandable.
        if (end!=null) {
            Graph.CostVertexPair pair = costs.get(end);
            Set<Graph.Vertex> set = paths.get(end);
            set.add(end);
    
            return (new Graph.CostPathPair(pair.getCost(),set));
        } else {
            for (Graph.Vertex v1 : paths.keySet()) {
                Set<Graph.Vertex> v2 = paths.get(v1);
                v2.add(v1);
            }
            return null;
        }
    }

    private static boolean checkForNegativeEdges(List<Graph.Vertex> vertitices) {
        for (Graph.Vertex v : vertitices) {
            for (Graph.Edge e : v.getEdges()) {
                if (e.getCost()<0) return true;
            }
        }
        return false;
    }
}
