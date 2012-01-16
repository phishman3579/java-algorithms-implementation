package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;


/**
 * Prim's minimum spanning tree. Only works on undirected graphs. It finds a subset of the edges 
 * that forms a tree that includes every vertex, where the total weight of all the edges in the tree is minimized.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Prim {

    private static Set<Graph.Edge> path = null;
    private static List<Graph.Vertex> unvisited = null;
    private static Queue<Graph.Edge> edgesAvailable = null;

    private Prim() { }

    public static Set<Graph.Edge> getMinimumSpanningTree(Graph g, Graph.Vertex start) {
        if (g==null) throw (new NullPointerException("Graph must be non-NULL."));
        
        // Prim's algorithm only works on undirected graphs
        if (g.getType() == Graph.TYPE.DIRECTED) throw (new IllegalArgumentException("Undirected graphs only.")); 

        path = new LinkedHashSet<Graph.Edge>();
        
        unvisited = new ArrayList<Graph.Vertex>();
        unvisited.addAll(g.getVerticies());
        unvisited.remove(start);
        
        edgesAvailable = new PriorityQueue<Graph.Edge>();

        Graph.Vertex vertex = start;
        while (!unvisited.isEmpty()) {
            for (Graph.Edge e : vertex.getEdges()) {
                if (unvisited.contains(e.getToVertex())) edgesAvailable.add(e);
            }

            Graph.Edge e = edgesAvailable.remove();
            path.add(e);

            vertex = e.getToVertex();            
            unvisited.remove(vertex);
        }
        
        return path;
    }
}
