package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import com.jwetherell.algorithms.data_structures.Graph;


/**
 * Dijkstra's shortest path. Returns a tuple of total cost of shortest path and the path.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Dijkstra {

    private static List<Graph.Vertex> unvisited = null;
    private static List<CostVertexPair> costs = null;
    private static Map<Graph.Vertex, Set<Graph.Vertex>> paths = null;

    private Dijkstra() { }

    public static Map<Graph.Vertex, CostPathPair> getShortestPaths(Graph g, Graph.Vertex start) {
        getShortestPath(g,start,null);
        Map<Graph.Vertex, CostPathPair> map = new HashMap<Graph.Vertex, CostPathPair>();
        for (CostVertexPair pair : costs) {
            int cost = pair.cost;
            Graph.Vertex vertex = pair.vertex;
            Set<Graph.Vertex> path = paths.get(vertex);
            map.put(vertex, new CostPathPair(cost,path));
        }
        return map;
    }
    
    public static CostPathPair getShortestPath(Graph g, Graph.Vertex start, Graph.Vertex end) {
        if (g==null) throw (new NullPointerException("Graph must be non-NULL."));

        unvisited = new ArrayList<Graph.Vertex>(g.getVerticies());
        unvisited.remove(start);

        paths = new TreeMap<Graph.Vertex, Set<Graph.Vertex>>();
        for (Graph.Vertex v : g.getVerticies()) {
            paths.put(v, new LinkedHashSet<Graph.Vertex>());
        }
        paths.get(start).add(start);

        costs = new ArrayList<CostVertexPair>();
        costs.add(new CostVertexPair(0,start));
        for (Graph.Vertex v : unvisited) {
            costs.add(new CostVertexPair(Integer.MAX_VALUE,v));
        }

        // Dijkstra's algorithm only works on positive cost graphs
        boolean hasNegativeEdge = checkForNegativeEdges(unvisited);
        if (hasNegativeEdge) throw (new IllegalArgumentException("Negative cost Edges are not allowed.")); 

        Stack<Graph.Vertex> previous = new Stack<Graph.Vertex>(); 
        Graph.Vertex vertex = start;
        while (vertex!=null) {
            if  (end!=null && vertex.equals(end)) break; //If we are looking for shortest path
            else if (unvisited.isEmpty()) break; // or looking for all shortest paths
            
            Queue<Graph.Edge> queue = new PriorityQueue<Graph.Edge>();
            for (Graph.Edge e : vertex.getEdges()) {
                // Only add vertices which haven't been visited
                if (unvisited.contains(e.getToVertex())) queue.add(e);
            }

            // Compute costs from current vertex to all reachable vertices which haven't been visited
            for (Graph.Edge e : queue) {
                CostVertexPair pair = getPairForVertex(e.getToVertex());
                CostVertexPair lowestCostToThisVertex = getPairForVertex(vertex);
                int cost = lowestCostToThisVertex.cost + e.getCost();
                if (pair.cost==Integer.MAX_VALUE) {
                    // Haven't seen this vertex yet
                    pair.cost = cost;
                    Set<Graph.Vertex> set = paths.get(e.getToVertex());
                    set.addAll(paths.get(e.getFromVertex()));
                    set.add(e.getFromVertex());
                } else if (cost<pair.cost) {
                    // Found a shorter path to a reachable vertex
                    pair.cost = cost;
                    Set<Graph.Vertex> set = paths.get(e.getToVertex());
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex()));
                    set.add(e.getFromVertex());
                }
            }
            // We have visited this vertex, remove it from the list
            unvisited.remove(vertex);

            if (queue.size()>0) {
                // If there are other vertices from this vertex to visit (which haven't been visited yet)
                Graph.Edge e = queue.remove();
                previous.push(vertex);
                vertex = e.getToVertex();
            } else if (previous.size()>0) {
                // No vertices available from this vertex or all vertices from here have been visited. Go back.
                vertex = previous.pop();
            } else if (vertex.equals(start)) {
                // No other vertices to try and we are back at the start. No path from start to end;
                return null;
            }
        }

        // Add the end vertex to the Set, just to make it more understandable.
        if (end!=null) {
            CostVertexPair pair = getPairForVertex(end);
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

    private static boolean checkForNegativeEdges(List<Graph.Vertex> vertitices) {
        for (Graph.Vertex v : vertitices) {
            for (Graph.Edge e : v.getEdges()) {
                if (e.getCost()<0) return true;
            }
        }
        return false;
    }

    private static CostVertexPair getPairForVertex(Graph.Vertex v) {
        for (CostVertexPair p : costs) {
            if (p.vertex.equals(v)) return p;
        }
        return null;
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
