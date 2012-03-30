package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Graph. Could be directed or undirected depending on the TYPE enum.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Graph {

    private List<Vertex> verticies = new CopyOnWriteArrayList<Vertex>();
    private List<Edge> edges = new CopyOnWriteArrayList<Edge>();
    
    public enum TYPE {DIRECTED, UNDIRECTED};
    private TYPE type = TYPE.UNDIRECTED;
    
    public Graph() { }
    
    public Graph(Graph g) {
        //Deep copies
        
        //Copy the vertices (which copies the edges)
        for (Vertex v : g.getVerticies()) {
            this.verticies.add(new Vertex(v));
        }

        //Update the object references
        for (Vertex v : this.verticies) {
            for (Edge e : v.getEdges()) {
                Vertex fromVertex = e.getFromVertex();
                Vertex toVertex = e.getToVertex();
                int indexOfFrom = this.verticies.indexOf(fromVertex);
                e.from = this.verticies.get(indexOfFrom);
                int indexOfTo = this.verticies.indexOf(toVertex);
                e.to = this.verticies.get(indexOfTo);
                this.edges.add(e);
            }
        }

        type = g.getType();
    }
    
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

    /**
     * {@inheritDoc}
     */
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
        private int weight = 0;
        private List<Edge> edges = new ArrayList<Edge>();

        public Vertex(int value) {
            this.value = value;
        }
        
        public Vertex(int value, int weight) {
            this(value);
            this.weight = weight;
        }
        
        public Vertex(Vertex vertex) {
            this(vertex.value,vertex.weight);
            this.edges = new ArrayList<Edge>();
            for (Edge e : vertex.edges) {
                this.edges.add(new Edge(e));
            }
        }
        
        public int getValue() {
            return value;
        }
        
        public int getWeight() {
            return weight;
        }        
        public void setWeight(int weight) {
            this.weight = weight;
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

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Vertex v) {
            if (this.value<v.value) return -1;
            if (this.value>v.value) return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object v1) {
            if (!(v1 instanceof Vertex)) return false;
            
            Vertex v = (Vertex)v1;

            boolean values = this.value==v.value;
            if (!values) return false;

            boolean weight = this.weight==v.weight;
            if (!weight) return false;

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("vertex:").append(" value=").append(value).append(" weight=").append(weight).append("\n");
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

        public Edge(Edge e) {
            this(e.cost, e.from, e.to);
        }

        public int getCost() {
            return cost;
        }
        public void setCost(int cost) {
            this.cost = cost;;
        }

        public Vertex getFromVertex() {
            return from;
        }

        public Vertex getToVertex() {
            return to;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Edge e) {
            if (this.cost<e.cost) return -1;
            if (this.cost>e.cost) return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return this.cost*(this.getFromVertex().value*this.getToVertex().value);
        }

        /**
         * {@inheritDoc}
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("edge:").append(" [").append(from.value).append("]")
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

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(CostVertexPair p) {
            if (p==null) throw new NullPointerException("CostVertexPair 'p' must be non-NULL.");
            if (this.cost<p.cost) return -1;
            if (this.cost>p.cost) return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
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

        /**
         * {@inheritDoc}
         */
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
