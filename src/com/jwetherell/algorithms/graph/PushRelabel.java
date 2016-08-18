package com.jwetherell.algorithms.graph;

import com.jwetherell.algorithms.data_structures.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * @author mrowka
 */
public class PushRelabel {

    private static class Vertex {
        boolean visited = false;
        int h;
        int currentEdge;
        long excess;
        ArrayList<Edge> edges = new ArrayList<Edge>();
    }

    private static class Edge {
        long c;
        long f;
        Vertex from;
        Vertex to;
        Edge revertedEdge;

        Edge(Vertex from, Vertex to, long c) {
            this.from = from;
            this.to = to;
            this.c = c;

        }

        Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Edge edge = (Edge) o;

            if (!from.equals(edge.from)) {
                return false;
            }
            return to.equals(edge.to);

        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            return result;
        }
    }

    private Queue<Vertex> q = new LinkedList<Vertex>();
    private int relabelCounter;
    private int n;
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

    private Vertex s;
    private Vertex t;

    private PushRelabel(Collection<Vertex> vertices, Vertex s, Vertex t) {
        this.vertices.addAll(vertices);
        this.s = s;
        this.t = t;
        this.n = vertices.size();
    }

    /**
     * Computes maximum flow in flow network, using push-relabel algorithm with O(V^3) complexity.
     *
     * @param edgesToCapacities represents edges of network with capacities
     * @param source            source of network
     * @param sink              sink of network
     * @param <T>               parameter of graph on which network is based
     * @return the maximum flow
     */
    public static <T extends Comparable<T>> Long getMaximumFlow(Map<Graph.Edge<T>, Long> edgesToCapacities, Graph.Vertex<T> source, Graph.Vertex<T> sink) {
        if (edgesToCapacities == null) {
            throw new IllegalArgumentException("Graph is NULL.");
        }

        Map<Graph.Vertex<T>, Vertex> vertexMap = new TreeMap<Graph.Vertex<T>, Vertex>();


        for (Graph.Edge<T> edge : edgesToCapacities.keySet()) {
            vertexMap.put(edge.getFromVertex(), new Vertex());
            vertexMap.put(edge.getToVertex(), new Vertex());
        }
        Vertex s = new Vertex();
        Vertex t = new Vertex();
        vertexMap.put(source, s);
        vertexMap.put(sink, t);


        PushRelabel pushRelabel = new PushRelabel(vertexMap.values(), s, t);

        for (Map.Entry<Graph.Edge<T>, Long> edgeWithCapacity : edgesToCapacities.entrySet()) {
            pushRelabel.addEdge(
                    vertexMap.get(edgeWithCapacity.getKey().getFromVertex()),
                    vertexMap.get(edgeWithCapacity.getKey().getToVertex()),
                    edgeWithCapacity.getValue()
            );
        }

        return pushRelabel.maxFlow();
    }

    private void addEdge(Vertex from, Vertex to, long c) {
        int placeOfEdge = from.edges.indexOf(new Edge(from, to));
        if (placeOfEdge == -1) {
            Edge edge = new Edge(from, to, c);
            Edge revertedEdge = new Edge(to, from, 0);
            edge.revertedEdge = revertedEdge;
            revertedEdge.revertedEdge = edge;
            from.edges.add(edge);
            to.edges.add(revertedEdge);
        } else {
            from.edges.get(placeOfEdge).c += c;
        }
    }

    private void recomputeHeigh() {
        Queue<Vertex> que = new LinkedList<Vertex>();
        for (Vertex vertex : vertices) {
            vertex.visited = false;
            vertex.h = 2 * n;
        }

        t.h = 0;
        s.h = n;
        s.visited = true;
        t.visited = true;
        que.add(t);
        while (!que.isEmpty()) {
            Vertex act = que.poll();
            for (Edge e : act.edges) {
                if (!e.to.visited && e.revertedEdge.c > e.revertedEdge.f) {
                    e.to.h = act.h + 1;
                    que.add(e.to);
                    e.to.visited = true;
                }
            }
        }
        que.add(s);
        while (!que.isEmpty()) {
            Vertex act = que.poll();
            for (Edge e : act.edges) {
                if (!e.to.visited && e.revertedEdge.c > e.revertedEdge.f) {
                    e.to.h = act.h + 1;
                    que.add(e.to);
                    e.to.visited = true;
                }
            }
        }
    }

    private void init() {

        for (Edge e : s.edges) {
            e.f = e.c;
            e.revertedEdge.f = -e.f;
            e.to.excess += e.f;
            if (e.to != s && e.to != t) {
                q.add(e.to);
            }
        }
        recomputeHeigh();
        relabelCounter = 0;
    }

    private void relabel(Vertex v) {
        int minimum = 0;
        for (Edge e : v.edges) {
            if (e.f < e.c) {
                minimum = Math.min(minimum, e.to.h);
            }
        }
        v.h = minimum + 1;
    }

    private void push(Vertex u, Edge e) {
        long delta = (u.excess < e.c - e.f) ? u.excess : e.c - e.f;
        e.f += delta;
        e.revertedEdge.f -= delta;
        u.excess -= delta;
        if (e.to.excess == 0 && e.to != s && e.to != t) {
            q.add(e.to);
        }
        e.to.excess += delta;
    }


    private void discharge(Vertex u) {
        while (u.excess > 0) {
            if (u.currentEdge == u.edges.size()) {
                relabel(u);
                if ((++relabelCounter) == n) {
                    recomputeHeigh();
                    for (Vertex vertex : vertices) {
                        vertex.currentEdge = 0;
                    }
                    relabelCounter = 0;
                }
                u.currentEdge = 0;
            } else {
                Edge e = u.edges.get(u.currentEdge);
                if (e.f < e.c && u.h == e.to.h + 1) {
                    push(u, e);
                } else u.currentEdge++;
            }
        }
    }

    private long maxFlow() {
        init();
        while (!q.isEmpty()) {
            discharge(q.poll());
        }
        return t.excess;
    }

}