package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.Iterator;
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

    private static Map<Graph.Vertex, CostVertexPair> costs = null;
    private static Map<Graph.Vertex, Set<Graph.Vertex>> paths = null;

    private BellmanFord() { }

    public static Map<Graph.Vertex, CostPathPair> getShortestPaths(Graph g, Graph.Vertex start) {
        getShortestPath(g,start,null);
        Map<Graph.Vertex, CostPathPair> map = new HashMap<Graph.Vertex, CostPathPair>();
        for (CostVertexPair pair : costs.values()) {
            int cost = pair.cost;
            Graph.Vertex vertex = pair.vertex;
            Set<Graph.Vertex> path = paths.get(vertex);
            map.put(vertex, new CostPathPair(cost,path));
        }
        return map;
    }
    
    public static CostPathPair getShortestPath(Graph g, Graph.Vertex start, Graph.Vertex end) {
        if (g==null) throw (new NullPointerException("Graph must be non-NULL."));

        paths = new TreeMap<Graph.Vertex, Set<Graph.Vertex>>();
        for (Graph.Vertex v : g.getVerticies()) {
            paths.put(v, new LinkedHashSet<Graph.Vertex>());
        }

        costs = new TreeMap<Graph.Vertex, CostVertexPair>();
        for (Graph.Vertex v : g.getVerticies()) {
            if (v.equals(start)) costs.put(v,new CostVertexPair(0,v));
            else costs.put(v,new CostVertexPair(Integer.MAX_VALUE,v));
        }

        boolean negativeCycleCheck = false;
        for (int i=0; i<(g.getVerticies().size()); i++) {
            
            // If it's the last vertices perform a negative weight cycle check. The graph should be 
            // finished by the size()-1 time through this loop.
            if (i==(g.getVerticies().size()-1)) negativeCycleCheck = true;
            
            // Compute costs to all vertices
            for (Graph.Edge e : g.getEdges()) {
                CostVertexPair pair = costs.get(e.getToVertex());
                CostVertexPair lowestCostToThisVertex = costs.get(e.getFromVertex());
                
                // If the cost of the from vertex is MAX_VALUE then treat as INIFINITY.
                if (lowestCostToThisVertex.cost==Integer.MAX_VALUE) continue;
                
                int cost = lowestCostToThisVertex.cost + e.getCost();
                if (cost<pair.cost) {
                    if (negativeCycleCheck) {
                        // Uhh ohh... negative weight cycle
                        System.out.println("Graph contains a negative weight cycle.");
                    } else {
                        // Found a shorter path to a reachable vertex
                        pair.cost = cost;
                        Set<Graph.Vertex> set = paths.get(e.getToVertex());
                        set.clear();
                        set.addAll(paths.get(e.getFromVertex()));
                        set.add(e.getFromVertex());
                    }
                }
            }
        }

        // Add the end vertex to the Set, just to make it more understandable.
        if (end!=null) {
            CostVertexPair pair = costs.get(end);
            Set<Graph.Vertex> set = paths.get(end);
            set.add(end);
    
            return (new CostPathPair(pair.cost,set));
        } else {
            for (Graph.Vertex v1 : paths.keySet()) {
                Set<Graph.Vertex> v2 = paths.get(v1);
                v2.add(v1);
            }
            return null;
        }
    }

    private static class CostVertexPair implements Comparable<CostVertexPair> {
        
        private int cost = Integer.MAX_VALUE;
        private Graph.Vertex vertex = null;
        
        private CostVertexPair(int cost, Graph.Vertex vertex) {
            this.cost = cost;
            this.vertex = vertex;
        }

        @Override
        public int compareTo(CostVertexPair p) {
            if (p==null) throw new NullPointerException("CostVertexPair 'p' must be non-NULL.");
            if (this.cost<p.cost) return -1;
            if (this.cost>p.cost) return 1;
            return 0;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Vertex=").append(vertex.getValue()).append(" cost=").append(cost).append("\n");
            return builder.toString();
        }
    }

    public static class CostPathPair {

        private int cost = 0;
        private Set<Graph.Vertex> path = null;

        public CostPathPair(int cost, Set<Graph.Vertex> path) {
            this.cost = cost;
            this.path = path;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Cost = ").append(cost).append("\n");
            Iterator<Graph.Vertex> iter = path.iterator();
            while (iter.hasNext()) {
                Graph.Vertex v =iter.next();
                builder.append(v.getValue());
                if (iter.hasNext()) builder.append("->");
            }
            builder.append("\n");
            return builder.toString();
        }
    }
}
