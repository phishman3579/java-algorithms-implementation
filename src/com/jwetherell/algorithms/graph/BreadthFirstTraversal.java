package com.jwetherell.algorithms.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

/**
 * Breadth-first search (BFS) is an algorithm for traversing or searching tree or graph data structures. It starts at the tree root (or some arbitrary node of a graph, sometimes referred to as a 
 * 'search key') and explores the neighbor nodes first, before moving to the next level neighbors.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-First Search (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BreadthFirstTraversal {

    @SuppressWarnings("unchecked")
    public static final <T extends Comparable<T>> Graph.Vertex<T>[] breadthFirstTraversal(Graph<T> graph, Graph.Vertex<T> source) {
        // use for look-up via index
        final ArrayList<Vertex<T>> vertices = new ArrayList<Vertex<T>>();
        vertices.addAll(graph.getVertices());

        // used for look-up via vertex
        final int n = vertices.size();
        final Map<Vertex<T>,Integer> vertexToIndex = new HashMap<Vertex<T>,Integer>();
        for (int i=0; i<n; i++) {
            final Vertex<T> v = vertices.get(i);
            vertexToIndex.put(v,i);
        }

        // adjacency matrix
        final byte[][] adj = new byte[n][n];
        for (int i=0; i<n; i++) {
            final Vertex<T> v = vertices.get(i);
            final int idx = vertexToIndex.get(v);
            final byte[] array = new byte[n];
            adj[idx] = array;
            final List<Edge<T>> edges = v.getEdges();
            for (Edge<T> e : edges)
                array[vertexToIndex.get(e.getToVertex())] = 1;
        }

        // visited array
        final byte[] visited = new byte[n];
        for (int i = 0; i < visited.length; i++)
            visited[i] = -1;

        // for holding results
        final Graph.Vertex<T>[] arr = new Graph.Vertex[n];

        // start at the source
        Vertex<T> element = source;
        int c = 0;
        int i = vertexToIndex.get(element); 
        int k = 0;

        arr[k] = element;
        visited[i] = 1;
        k++;

        final Queue<Vertex<T>> queue = new ArrayDeque<Vertex<T>>();
        queue.add(source);
        while (!queue.isEmpty()) {    
            element = queue.peek();
            c = vertexToIndex.get(element);
            i = 0;
            while (i < n) {
                if (adj[c][i] == 1 && visited[i] == -1) {
                    final Vertex<T> v = vertices.get(i);
                    queue.add(v);
                    visited[i] = 1;

                    arr[k] = v;
                    k++;
                }
                i++;
            }
            queue.poll();
        }
        return arr;
    }

    public static int[] breadthFirstTraversal(int n, byte[][] adjacencyMatrix, int source) {
        final int[] visited = new int[n];
        for (int i = 0; i < visited.length; i++)
            visited[i] = -1;

        int element = source;
        int i = source;
        int arr[] = new int[n];
        int k = 0;

        arr[k] = element;
        visited[i] = 1;
        k++;

        final Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(source);
        while (!queue.isEmpty()) {    
            element = queue.peek();
            i = 0;
            while (i < n) {
                if (adjacencyMatrix[element][i] == 1 && visited[i] == -1) {
                    queue.add(i);
                    visited[i] = 1;

                    arr[k] = i;
                    k++;
                }
                i++;
            }
            queue.poll();
        }
        return arr;
    }
}

