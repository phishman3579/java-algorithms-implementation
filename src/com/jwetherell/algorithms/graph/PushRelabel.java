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

    private static class LocalVertex {
        boolean visited = false;
        int h;
        int currentEdge;
        long excess;
        ArrayList<LocalEdge> localEdges = new ArrayList<>();

    }

    private static class LocalEdge {
        long c;
        long f;
        LocalVertex from;
        LocalVertex to;
        LocalEdge revertedEdge;

        LocalEdge(LocalVertex from, LocalVertex to, long c) {
            this.from = from;
            this.to = to;
            this.c = c;

        }

        LocalEdge(LocalVertex from, LocalVertex to) {
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

            LocalEdge localEdge = (LocalEdge) o;

            if (!from.equals(localEdge.from)) {
                return false;
            }
            return to.equals(localEdge.to);

        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            return result;
        }
    }

    private Queue<LocalVertex> q = new LinkedList<>();
    private int relabelCounter;
    private int n;
    private ArrayList<LocalVertex> localVertices = new ArrayList<>();

    private LocalVertex s;
    private LocalVertex t;

    private PushRelabel(Collection<LocalVertex> localVertices, LocalVertex s, LocalVertex t) {
        this.localVertices.addAll(localVertices);
        this.s = s;
        this.t = t;
        this.n = localVertices.size();
    }


    public static <T extends Comparable<T>> Long getMaximumFlow(Map<Graph.Edge<T>, Long> capacities, Graph.Vertex<T> source, Graph.Vertex<T> sink) {
        if (capacities == null) {
            throw new IllegalArgumentException("Graph is NULL.");
        }

        Map<Graph.Vertex<T>, LocalVertex> vertexMap = new TreeMap<>();
        LocalVertex s = new LocalVertex();
        LocalVertex t = new LocalVertex();
        vertexMap.put(source, s);
        vertexMap.put(sink, t);


        for (Graph.Edge<T> edge : capacities.keySet()) {
            vertexMap.putIfAbsent(edge.getFromVertex(), new LocalVertex());
            vertexMap.putIfAbsent(edge.getToVertex(), new LocalVertex());
        }
        PushRelabel pushRelabel = new PushRelabel(vertexMap.values(), s, t);

        capacities.forEach((edge, c) -> pushRelabel.addEdge(
                vertexMap.get(edge.getFromVertex()),
                vertexMap.get(edge.getToVertex()),
                c)
        );

        return pushRelabel.maxFlow();
    }

    private void addEdge(LocalVertex from, LocalVertex to, long c) {
        int placeOfEdge = from.localEdges.indexOf(new LocalEdge(from, to));
        if (placeOfEdge == -1) {
            LocalEdge edge = new LocalEdge(from, to, c);
            LocalEdge revertedEdge = new LocalEdge(to, from, 0);
            edge.revertedEdge = revertedEdge;
            revertedEdge.revertedEdge = edge;
            from.localEdges.add(edge);
            to.localEdges.add(revertedEdge);
        } else {
            from.localEdges.get(placeOfEdge).c += c;
        }
    }

    private void recomputeHeigh() {
        Queue<LocalVertex> que = new LinkedList<>();
        for (LocalVertex vertex : localVertices) {
            vertex.visited = false;
            vertex.h = 2 * n;
        }

        t.h = 0;
        s.h = n;
        s.visited = true;
        t.visited = true;
        que.add(t);
        while (!que.isEmpty()) {
            LocalVertex act = que.poll();
            act.localEdges.stream().filter(e -> !e.to.visited && e.revertedEdge.c > e.revertedEdge.f).forEach(e -> {
                e.to.h = act.h + 1;
                que.add(e.to);
                e.to.visited = true;
            });
        }
        que.add(s);
        while (!que.isEmpty()) {
            LocalVertex act = que.poll();
            act.localEdges.stream().filter(e -> !e.to.visited && e.revertedEdge.c > e.revertedEdge.f).forEach(e -> {
                e.to.h = act.h + 1;
                que.add(e.to);
                e.to.visited = true;
            });
        }
    }

    private void init() {

        for (LocalEdge e : s.localEdges) {
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

    private void relabel(LocalVertex v) {
        int minimum = 0;
        for (LocalEdge e : v.localEdges) {
            if (e.f < e.c) {
                minimum = Math.min(minimum, e.to.h);
            }
        }
        v.h = minimum + 1;
    }

    private void push(LocalVertex u, LocalEdge e) {
        long delta = (u.excess < e.c - e.f) ? u.excess : e.c - e.f;
        e.f += delta;
        e.revertedEdge.f -= delta;
        u.excess -= delta;
        if (e.to.excess == 0 && e.to != s && e.to != t) {
            q.add(e.to);
        }
        e.to.excess += delta;
    }


    private void discharge(LocalVertex u) {
        while (u.excess > 0) {
            if (u.currentEdge == u.localEdges.size()) {
                relabel(u);
                if ((++relabelCounter) == n) {
                    recomputeHeigh();
                    for (LocalVertex vertex : localVertices) {
                        vertex.currentEdge = 0;
                    }
                    relabelCounter = 0;
                }
                u.currentEdge = 0;
            } else {
                LocalEdge e = u.localEdges.get(u.currentEdge);
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