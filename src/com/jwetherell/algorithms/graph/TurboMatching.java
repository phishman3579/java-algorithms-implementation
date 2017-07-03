package com.jwetherell.algorithms.graph;

import com.jwetherell.algorithms.data_structures.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * In the mathematical discipline of graph theory, a matching or independent edge set
 * in a graph is a set of edges without common vertices. In some matchings, all the vertices
 * may incident with some edge of the matching, but this is not required and can only occur
 * if the number of vertices is even.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Matching_(graph_theory)">Matching (Wikipedia)</a>
 * <br>
 * @author Jakub Szarawarski <kubaszarwarski@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TurboMatching {

    /**
     * Computes maximum matching, using turbomatching algorithm based on augmenting paths with O(EV) complexity.
     *
     * @param graph             bipartite graph
     * @param <T>               parameter of graph on which network is based
     * @return a MatchingResult class instance containg a map of mates for each paired vertex and number of pairs
     */
    public static <T extends Comparable<T>> MatchingResult<T> getMaximumMatching(Graph<T> graph){
        final Map<Graph.Vertex<T>, Graph.Vertex<T>> mate = new HashMap<Graph.Vertex<T>, Graph.Vertex<T>>();

        while (pathset(graph, mate));

        return new MatchingResult<T>(mate);
    }

    /**
     * Searches for an augmenting path for each unmatched vertex.
     *
     * @param graph         bipartite graph
     * @param mate          map containing a mate for each matched vertex
     * @return              information if any augmenting path was found
     */
    private static <T extends Comparable<T>> boolean pathset(Graph<T> graph, Map<Graph.Vertex<T>, Graph.Vertex<T>> mate){
        final Set<Graph.Vertex<T>> visited = new HashSet<Graph.Vertex<T>>();

        boolean result = false;
        for (Graph.Vertex<T> vertex : graph.getVertices()) {
            if (mate.containsKey(vertex) == false) {
                if (path(graph, mate, visited, vertex))
                    result = true;
            }
        }
        return result;
    }

    /**
     * Searches for an augmenting path for a vertex.
     * Refreshes mates map appropriately.
     *
     * @param graph         bipartite graph
     * @param mate          map containing a mate for each matched vertex
     * @param visited       set containing vertices visited in current pathset
     * @param vertex        regarded vertex
     * @param <T>           parameter of graph on which network is based
     * @return              information if an augmenting path was found
     */
    private static <T extends Comparable<T>> boolean path(Graph<T> graph, Map<Graph.Vertex<T>, Graph.Vertex<T>> mate, Set<Graph.Vertex<T>> visited, Graph.Vertex<T> vertex){
        if (visited.contains(vertex)) 
            return false;

        visited.add(vertex);
        for (Graph.Edge<T> edge : vertex.getEdges()) {
            final Graph.Vertex<T> neighbour = edge.getFromVertex().equals(vertex) ? edge.getToVertex() : edge.getFromVertex();
            if (mate.containsKey(neighbour) == false || path(graph, mate, visited, mate.get(neighbour))) {
                mate.put(vertex, neighbour);
                mate.put(neighbour, vertex);
                return true;
            }
        }
        return false;
    }


    public static class MatchingResult<T extends Comparable<T>>{

        private final Map<Graph.Vertex<T>, Graph.Vertex<T>> mate;
        private final int size;

        private MatchingResult(Map<Graph.Vertex<T>, Graph.Vertex<T>> mate){
            this.mate = mate;
            this.size = mate.size()/2;
        }

        /**
         * @return the number of edges in independent edge set
         */
        public int getSize(){
            return this.size;
        }

        /**
         * @return a symetric map that contains a mate for each matched vertex
         */
        public Map<Graph.Vertex<T>, Graph.Vertex<T>> getMate(){
            return this.mate;
        }
    }
}
