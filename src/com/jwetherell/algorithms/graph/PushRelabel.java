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
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Push%E2%80%93relabel_maximum_flow_algorithm">Push-Relabel Algorithm (Wikipedia)</a>
 * <br>
 * @author Miron Ficak <miron.ficak@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class PushRelabel {

    private final Queue<Vertex> queue = new ArrayDeque<Vertex>();
    private final List<Vertex> vertices = new ArrayList<Vertex>();

    private int relabelCounter;
    private int n;
    private Vertex source;
    private Vertex sink;

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

        final Vertex s = new Vertex(); // source
        vertexMap.put(source, s);

        final Vertex t = new Vertex(); // sink
        vertexMap.put(sink, t);

        final PushRelabel pushRelabel = new PushRelabel(vertexMap.values(), s, t);
        for (Map.Entry<Graph.Edge<T>, Long> edgeWithCapacity : edgesToCapacities.entrySet()) {
            final Graph.Edge<T> e = edgeWithCapacity.getKey();
            addEdge(
                vertexMap.get(e.getFromVertex()),
                vertexMap.get(e.getToVertex()),
                edgeWithCapacity.getValue()
            );
        }

        return pushRelabel.maxFlow();
    }

    private PushRelabel(Collection<Vertex> vertices, Vertex source, Vertex sink) {
        this.vertices.addAll(vertices);
        this.source = source;
        this.sink = sink;
        this.n = vertices.size();
    }

    private static final void addEdge(Vertex from, Vertex to, long cost) {
        final int placeOfEdge = from.edges.indexOf(new Edge(from, to));
        if (placeOfEdge == -1) {
            final Edge edge = new Edge(from, to, cost);
            final Edge revertedEdge = new Edge(to, from, 0);
            edge.revertedEdge = revertedEdge;
            revertedEdge.revertedEdge = edge;
            from.edges.add(edge);
            to.edges.add(revertedEdge);
        } else {
            from.edges.get(placeOfEdge).cost += cost;
        }
    }

    private final void recomputeHeight() {
        final Queue<Vertex> que = new ArrayDeque<Vertex>();
        for (Vertex vertex : vertices) {
            vertex.visited = false;
            vertex.height = 2 * n;
        }

        sink.height = 0;
        source.height = n;
        source.visited = true;
        sink.visited = true;
        que.add(sink);
        while (!que.isEmpty()) {
            final Vertex act = que.poll();
            for (Edge e : act.edges) {
                if (!e.to.visited && e.revertedEdge.cost > e.revertedEdge.flow) {
                    e.to.height = act.height + 1;
                    que.add(e.to);
                    e.to.visited = true;
                }
            }
        }
        que.add(source);
        while (!que.isEmpty()) {
            final Vertex act = que.poll();
            for (Edge e : act.edges) {
                if (!e.to.visited && e.revertedEdge.cost > e.revertedEdge.flow) {
                    e.to.height = act.height + 1;
                    que.add(e.to);
                    e.to.visited = true;
                }
            }
        }
    }

    private final void init() {
        for (Edge e : source.edges) {
            e.flow = e.cost;
            e.revertedEdge.flow = -e.flow;
            e.to.excess += e.flow;
            if (e.to != source && e.to != sink)
                queue.add(e.to);
        }
        recomputeHeight();
        relabelCounter = 0;
    }

    private static final void relabel(Vertex v) {
        int minimum = 0;
        for (Edge e : v.edges) {
            if (e.flow < e.cost)
                minimum = Math.min(minimum, e.to.height);
        }
        v.height = minimum + 1;
    }

    private final void push(Vertex u, Edge e) {
        final long delta = (u.excess < e.cost - e.flow) ? u.excess : e.cost - e.flow;
        e.flow += delta;
        e.revertedEdge.flow -= delta;
        u.excess -= delta;

        if (e.to.excess == 0 && e.to != source && e.to != sink)
            queue.add(e.to);

        e.to.excess += delta;
    }

    private final void discharge(Vertex u) {
        while (u.excess > 0) {
            if (u.currentEdge == u.edges.size()) {
                relabel(u);
                if ((++relabelCounter) == n) {
                    recomputeHeight();
                    for (Vertex vertex : vertices)
                        vertex.currentEdge = 0;
                    relabelCounter = 0;
                }
                u.currentEdge = 0;
            } else {
                Edge e = u.edges.get(u.currentEdge);
                if (e.flow < e.cost && u.height == e.to.height + 1)
                    push(u, e);
                else 
                    u.currentEdge++;
            }
        }
    }

    private final long maxFlow() {
        init();
        while (!queue.isEmpty())
            discharge(queue.poll());
        return sink.excess;
    }

    private static final class Vertex {

        private final List<Edge> edges = new ArrayList<Edge>();

        private boolean visited = false;
        private int height;
        private int currentEdge;
        private long excess;

    }

    private final static class Edge {

        private final Vertex from;
        private final Vertex to;
        private long cost;

        private long flow;
        private Edge revertedEdge;

        private Edge(Vertex from, Vertex to, long cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
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
