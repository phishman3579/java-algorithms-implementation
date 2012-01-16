package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Graph. Could be directed or undirected depending on the TYPE enum.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Graph {

    private List<Vertex> verticies = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();
    
    public enum TYPE {DIRECTED, UNDIRECTED};
    private TYPE type = TYPE.UNDIRECTED;
    
    public Graph() { }
    
    public Graph(TYPE type) {
        this();
        this.type = type;
    }
    
    public Graph(List<Vertex> verticies, List<Edge> edges) {
        this(TYPE.UNDIRECTED,verticies,edges);
    }
    
    public Graph(TYPE type, List<Vertex> verticies, List<Edge> edges) {
        this(type);
        this.verticies.addAll(verticies);
        this.edges.addAll(edges);
        
        for (Edge e : edges) {
            Vertex from = e.from;
            Vertex to = e.to;
            
            if(!this.verticies.contains(from) || !this.verticies.contains(to)) continue;
            
            int index = this.verticies.indexOf(from);
            Vertex fromVertex = this.verticies.get(index);
            index = this.verticies.indexOf(to);
            Vertex toVertex = this.verticies.get(index);
            fromVertex.addEdge(e);
            if (this.type == TYPE.UNDIRECTED) {
                Edge reciprical = new Edge(e.cost, toVertex, fromVertex);
                toVertex.addEdge(reciprical);
                this.edges.add(reciprical);
            }
        }
    }
    
    public TYPE getType() {
        return type;
    }
    
    public List<Vertex> getVerticies() {
        return verticies;
    }
    
    public List<Edge> getEdges() {
        return edges;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex v : verticies) {
            builder.append(v.toString());
        }
        return builder.toString();
    }
    
    public static class Vertex implements Comparable<Vertex> {
        
        private int value = Integer.MIN_VALUE;
        private List<Edge> edges = new ArrayList<Edge>();
        
        public Vertex(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
        
        public void addEdge(Edge e) {
            edges.add(e);
        }
        
        public List<Edge> getEdges() {
            return edges;
        }

        public boolean pathTo(Vertex v) {
            for (Edge e : edges) {
                if (e.to.equals(v)) return true;
            }
            return false;
        }
        
        @Override
        public int compareTo(Vertex v) {
            if (this.value<v.value) return -1;
            if (this.value>v.value) return 1;
            return 0;
        }

        @Override
        public boolean equals(Object v1) {
            if (!(v1 instanceof Vertex)) return false;
            
            Vertex v = (Vertex)v1;

            boolean values = this.value==v.value;
            if (!values) return false;
            
            boolean edges = v.getEdges().equals(this.getEdges());
            if (!edges) return false;
            
            return true;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(value).append("\n");
            for (Edge e : edges) {
                builder.append("\t").append(e.toString());
            }
            return builder.toString();
        }
    }
    
    public static class Edge implements Comparable<Edge> {

        private Vertex from = null;
        private Vertex to = null;
        private int cost = 0;

        public Edge(int cost, Vertex from, Vertex to) {
            if (from==null || to==null) throw (new NullPointerException("Both 'to' and 'from' Verticies need to be non-NULL."));
            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        public int getCost() {
            return cost;
        }

        public Vertex getFromVertex() {
            return from;
        }

        public Vertex getToVertex() {
            return to;
        }

        @Override
        public int compareTo(Edge e) {
            if (this.cost<e.cost) return -1;
            if (this.cost>e.cost) return 1;
            return 0;
        }

        @Override
        public boolean equals(Object e1) {
            if (!(e1 instanceof Edge)) return false;
            
            Edge e = (Edge)e1;
            
            boolean costs = this.cost==e.cost;
            if (!costs) return false;
            
            boolean froms = this.from.equals(e.from);
            if (!froms) return false;
            
            boolean tos = this.to.equals(e.to);
            if (!tos) return false;
            
            return true;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[").append(from.value).append("]")
                   .append(" -> ")
                   .append("[").append(to.value).append("]")
                   .append(" = ")
                   .append(cost)
                   .append("\n");
            return builder.toString();
        }
    }

    public static class CostVertexPair implements Comparable<CostVertexPair> {
        
        private int cost = Integer.MAX_VALUE;
        private Vertex vertex = null;
        
        public CostVertexPair(int cost, Vertex vertex) {
            if (vertex==null) throw (new NullPointerException("vertex cannot be NULL."));

            this.cost = cost;
            this.vertex = vertex;
        }

        public int getCost() {
            return cost;
        }
        public void setCost(int cost) {
            this.cost = cost;
        }

        public Vertex getVertex() {
            return vertex;
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
        private Set<Edge> path = null;

        public CostPathPair(int cost, Set<Edge> path) {
            if (path==null) throw (new NullPointerException("path cannot be NULL."));

            this.cost = cost;
            this.path = path;
        }

        public int getCost() {
            return cost;
        }
        public void setCost(int cost) {
            this.cost = cost;
        }

        public Set<Edge> getPath() {
            return path;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Cost = ").append(cost).append("\n");
            for (Edge e : path) {
                builder.append("\t").append(e);
            }
            return builder.toString();
        }
    }
}
