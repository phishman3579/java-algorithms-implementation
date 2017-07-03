package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

/**
 * In computer science, A* is a computer algorithm that is widely used in path finding and graph traversal, the process 
 * of plotting an efficiently traversable path between multiple points, called nodes.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/A*_search_algorithm">A* Algorithm (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class AStar<T extends Comparable<T>> {

    public AStar() { }

    /**
     * Find the path using the A* algorithm from start vertex to end vertex or NULL if no path exists.
     * 
     * @param graph
     *          Graph to search.
     * @param start
     *          Start vertex.
     * @param goal
     *          Goal vertex.
     * 
     * @return 
     *          List of Edges to get from start to end or NULL if no path exists.
     */
    public List<Graph.Edge<T>> aStar(Graph<T> graph, Graph.Vertex<T> start, Graph.Vertex<T> goal) {
        final int size = graph.getVertices().size(); // used to size data structures appropriately
        final Set<Graph.Vertex<T>> closedSet = new HashSet<Graph.Vertex<T>>(size); // The set of nodes already evaluated.
        final List<Graph.Vertex<T>> openSet = new ArrayList<Graph.Vertex<T>>(size); // The set of tentative nodes to be evaluated, initially containing the start node
        openSet.add(start);
        final Map<Graph.Vertex<T>,Graph.Vertex<T>> cameFrom = new HashMap<Graph.Vertex<T>,Graph.Vertex<T>>(size); // The map of navigated nodes.

        final Map<Graph.Vertex<T>,Integer> gScore = new HashMap<Graph.Vertex<T>,Integer>(); // Cost from start along best known path.
        gScore.put(start, 0);

        // Estimated total cost from start to goal through y.
        final Map<Graph.Vertex<T>,Integer> fScore = new HashMap<Graph.Vertex<T>,Integer>();
        for (Graph.Vertex<T> v : graph.getVertices())
            fScore.put(v, Integer.MAX_VALUE);
        fScore.put(start, heuristicCostEstimate(start,goal));

        final Comparator<Graph.Vertex<T>> comparator = new Comparator<Graph.Vertex<T>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(Vertex<T> o1, Vertex<T> o2) {
                if (fScore.get(o1) < fScore.get(o2))
                    return -1;
                if (fScore.get(o2) < fScore.get(o1))
                    return 1;
                return 0;
            }
        };

        while (!openSet.isEmpty()) {
            final Graph.Vertex<T> current = openSet.get(0);
            if (current.equals(goal))
                return reconstructPath(cameFrom, goal);

            openSet.remove(0);
            closedSet.add(current);
            for (Graph.Edge<T> edge : current.getEdges()) {
                final Graph.Vertex<T> neighbor = edge.getToVertex();
                if (closedSet.contains(neighbor))
                    continue; // Ignore the neighbor which is already evaluated.

                final int tenativeGScore = gScore.get(current) + distanceBetween(current,neighbor); // length of this path.
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor); // Discover a new node
                else if (tenativeGScore >= gScore.get(neighbor))
                    continue;

                // This path is the best until now. Record it!
                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tenativeGScore);
                final int estimatedFScore = gScore.get(neighbor) + heuristicCostEstimate(neighbor, goal);
                fScore.put(neighbor, estimatedFScore);

                // fScore has changed, re-sort the list
                Collections.sort(openSet,comparator);
            }
        }

        return null;
    }

    /**
     * Default distance is the edge cost. If there is no edge between the start and next then
     * it returns Integer.MAX_VALUE;
     */
    protected int distanceBetween(Graph.Vertex<T> start, Graph.Vertex<T> next) {
        for (Edge<T> e : start.getEdges()) {
            if (e.getToVertex().equals(next))
                return e.getCost();
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Default heuristic: cost to each vertex is 1.
     */
    @SuppressWarnings("unused") 
    protected int heuristicCostEstimate(Graph.Vertex<T> start, Graph.Vertex<T> goal) {
        return 1;
    }

    private List<Graph.Edge<T>> reconstructPath(Map<Graph.Vertex<T>,Graph.Vertex<T>> cameFrom, Graph.Vertex<T> current) {
        final List<Graph.Edge<T>> totalPath = new ArrayList<Graph.Edge<T>>();

        while (current != null) {
            final Graph.Vertex<T> previous = current;
            current = cameFrom.get(current);
            if (current != null) {
                final Graph.Edge<T> edge = current.getEdge(previous);
                totalPath.add(edge);
            }
        }
        Collections.reverse(totalPath);
        return totalPath;
    }
}
