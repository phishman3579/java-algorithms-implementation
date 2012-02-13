package com.jwetherell.algorithms.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;


public class CycleDetection {

    private static Set<Graph.Vertex> visitedVerticies = new HashSet<Graph.Vertex>();
    private static Set<Graph.Edge> visitedEdges = new HashSet<Graph.Edge>();
    
    private CycleDetection() {};
    
    public static boolean detect(Graph g) {
        if (g==null) return false;
        visitedVerticies.clear();
        visitedEdges.clear();
        List<Graph.Vertex> verticies = g.getVerticies();
        if (verticies==null || verticies.size()==0) return false;

        //Select the zero-ith element as the root
        Graph.Vertex root = verticies.get(0);
        return depthFirstSearch(root);
    }
    
    private static final boolean depthFirstSearch(Graph.Vertex vertex) {
        if (!visitedVerticies.contains(vertex)) {
            //Not visited
            visitedVerticies.add(vertex);

            List<Graph.Edge> edges = vertex.getEdges();
            if (edges!=null) {
                for (Graph.Edge edge : edges) {
                    Graph.Vertex to = edge.getToVertex();
                    boolean result = false;
                    if (to!=null && !visitedEdges.contains(edge)) {
                        visitedEdges.add(edge);
                        Graph.Edge recip = new Graph.Edge(edge.getCost(),edge.getToVertex(),edge.getFromVertex());
                        visitedEdges.add(recip);
                        result = depthFirstSearch(to);
                    }
                    if (result==true) return result;
                }
            }
        } else {
            //visited
            return true;
        }
        return false;
    }
}
