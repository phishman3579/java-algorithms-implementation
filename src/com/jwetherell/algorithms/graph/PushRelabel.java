package com.jwetherell.algorithms.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.jwetherell.algorithms.data_structures.Graph;

/**
 * In mathematical optimization, the push–relabel algorithm (alternatively, preflow–push 
 * algorithm) is an algorithm for computing maximum flows. The name "push–relabel" comes 
 * from the two basic operations used in the algorithm. Throughout its execution, the 
 * algorithm maintains a "preflow" and gradually converts it into a maximum flow by moving 
 * flow locally between neighboring nodes using push operations under the guidance of an 
 * admissible network maintained by relabel operations.
 * 
 * https://en.wikipedia.org/wiki/Push%E2%80%93relabel_maximum_flow_algorithm
 * 
 * @author Miron Ficak <miron.ficak@gmail.com>
 */
public class PushRelabel {

    private final Queue<Vertex> queue = new ArrayDeque<Vertex>();
    private final List<Vertex> vertices = new ArrayList<Vertex>();

    private int relabelCounter;
    private int n;
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
        if (edgesToCapacities == null)
            throw new IllegalArgumentException("Graph is NULL.");

        final Map<Graph.Vertex<T>, Vertex> vertexMap = new TreeMap<Graph.Vertex<T>, Vertex>();
        for (Graph.Edge<T> edge : edgesToCapacities.keySet()) {
            vertexMap.put(edge.getFromVertex(), new Vertex());
            vertexMap.put(edge.getToVertex(), new Vertex());
        }

        final Vertex s = new Vertex();
        vertexMap.put(source, s);

        final Vertex t = new Vertex();
        vertexMap.put(sink, t);

        final PushRelabel pushRelabel = new PushRelabel(vertexMap.values(), s, t);
        for (Map.Entry<Graph.Edge<T>, Long> edgeWithCapacity : edgesToCapacities.entrySet()) {
            addEdge(
                vertexMap.get(edgeWithCapacity.getKey().getFromVertex()),
                vertexMap.get(edgeWithCapacity.getKey().getToVertex()),
                edgeWithCapacity.getValue()
            );
        }

        return pushRelabel.maxFlow();
    }

    private static final void addEdge(Vertex from, Vertex to, long c) {
        final int placeOfEdge = from.edges.indexOf(new Edge(from, to));
        if (placeOfEdge == -1) {
            final Edge edge = new Edge(from, to, c);
            final Edge revertedEdge = new Edge(to, from, 0);
            edge.revertedEdge = revertedEdge;
            revertedEdge.revertedEdge = edge;
            from.edges.add(edge);
            to.edges.add(revertedEdge);
        } else {
            from.edges.get(placeOfEdge).c += c;
        }
    }

    private final void recomputeHeigh() {
        final Queue<Vertex> que = new ArrayDeque<Vertex>();
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
            final Vertex act = que.poll();
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
            final Vertex act = que.poll();
            for (Edge e : act.edges) {
                if (!e.to.visited && e.revertedEdge.c > e.revertedEdge.f) {
                    e.to.h = act.h + 1;
                    que.add(e.to);
                    e.to.visited = true;
                }
            }
        }
    }

    private final void init() {
        for (Edge e : s.edges) {
            e.f = e.c;
            e.revertedEdge.f = -e.f;
            e.to.excess += e.f;
            if (e.to != s && e.to != t)
                queue.add(e.to);
        }
        recomputeHeigh();
        relabelCounter = 0;
    }

    private static final void relabel(Vertex v) {
        int minimum = 0;
        for (Edge e : v.edges) {
            if (e.f < e.c)
                minimum = Math.min(minimum, e.to.h);
        }
        v.h = minimum + 1;
    }

    private final void push(Vertex u, Edge e) {
        final long delta = (u.excess < e.c - e.f) ? u.excess : e.c - e.f;
        e.f += delta;
        e.revertedEdge.f -= delta;
        u.excess -= delta;

        if (e.to.excess == 0 && e.to != s && e.to != t)
            queue.add(e.to);

        e.to.excess += delta;
    }


    private final void discharge(Vertex u) {
        while (u.excess > 0) {
            if (u.currentEdge == u.edges.size()) {
                relabel(u);
                if ((++relabelCounter) == n) {
                    recomputeHeigh();
                    for (Vertex vertex : vertices)
                        vertex.currentEdge = 0;
                    relabelCounter = 0;
                }
                u.currentEdge = 0;
            } else {
                Edge e = u.edges.get(u.currentEdge);
                if (e.f < e.c && u.h == e.to.h + 1)
                    push(u, e);
                else 
                    u.currentEdge++;
            }
        }
    }

    private final long maxFlow() {
        init();
        while (!queue.isEmpty()) {
            discharge(queue.poll());
        }
        return t.excess;
    }


    private static final class Vertex {

        private boolean visited = false;
        private int h;
        private int currentEdge;
        private long excess;
        private List<Edge> edges = new ArrayList<Edge>();

    }

    private final static class Edge {

        private final Vertex from;
        private final Vertex to;

        private long c;
        private long f;
        private Edge revertedEdge;

        private Edge(Vertex from, Vertex to, long c) {
            this.from = from;
            this.to = to;
            this.c = c;
        }

        private Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o == null || getClass() != o.getClass())
                return false;

            final Edge edge = (Edge) o;
            if (!from.equals(edge.from))
                return false;
            return to.equals(edge.to);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            return result;
        }
    }
}
