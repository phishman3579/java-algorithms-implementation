package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class ConnectedComponents {

    private static final <T extends Comparable<T>> void visit(Map<Vertex<T>,Integer> map, List<List<Vertex<T>>> list, Vertex<T> v, int c) {
        map.put(v, c);

        List<Vertex<T>> r = null;
        if (c == list.size()) {
            r = new ArrayList<Vertex<T>>();
            list.add(r);
        } else {
            r = list.get(c);
        }
        r.add(v);

        if (v.getEdges().size() > 0) {
            boolean found = false;
            for (Edge<T> e : v.getEdges()) {
                final Vertex<T> to = e.getToVertex();
                final Integer i = map.get(to);
                if (i == null) {
                    visit(map, list, to, c);
                    found = true;
                }
                if (found)
                    break;
            }
        }
    }

    public static final <T extends Comparable<T>> List<List<Vertex<T>>> getConnectedComponents(Graph<T> g) {
        final Map<Vertex<T>,Integer> map = new HashMap<Vertex<T>,Integer>();
        final List<List<Vertex<T>>> list = new ArrayList<List<Vertex<T>>>();

        int c = 0;
        for (Vertex<T> v : g.getVerticies()) 
            if (map.get(v) == null)
                visit(map, list, v, c++);
        return list;
    }
}
