package com.jwetherell.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinaryHeap.TYPE;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.LinkedList;
import com.jwetherell.algorithms.data_structures.Queue;
import com.jwetherell.algorithms.data_structures.Stack;
import com.jwetherell.algorithms.graph.Dijkstra;
import com.jwetherell.algorithms.graph.Dijkstra.CostPathPair;


public class DataStructures {
    private static final int SIZE = 1000;
    
    private static int[] unsorted = null;
    
    public static void main(String[] args) {
        Random random = new Random();
        
        System.out.print("Array=");
        unsorted = new int[SIZE];
        int i=0;
        System.out.print("array=");
        while (i<unsorted.length) {
            int j = random.nextInt(unsorted.length*10);
            unsorted[i++] = j;
            System.out.print(j+",");
        }
        System.out.println();
        System.out.println();

        {
            // Linked List
            System.out.println("Linked List.");
            LinkedList list = new LinkedList(unsorted);
            System.out.println(list.toString());
            
            int index = 0;
            int next = unsorted[index];
            System.out.println("Removing the head of the List "+next);
            list.remove(next);
            System.out.println(list.toString());
            
            index = unsorted.length-1;
            next = unsorted[index];
            System.out.println("Removing the tail of the List "+next);
            list.remove(next);
            System.out.println(list.toString());

            next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            list.add(next);
            System.out.println(list.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            list.remove(next);
            System.out.println(list.toString());

            /*
            while (list.getSize()>0) {
                int headValue = list.getHeadValue();
                list.remove(headValue);
                System.out.println("Removed the head "+headValue+" from the list.");
                System.out.println(list.toString());
            }
            */
            System.out.println();
        }
        
        {
            // Stack
            System.out.println("Stack.");
            Stack stack = new Stack(unsorted);
            System.out.println(stack.toString());

            int next = random.nextInt(unsorted.length*100);
            System.out.println("Pushing a new node onto the Stack "+next);
            stack.push(next);
            System.out.println(stack.toString());

            int node = stack.pop();
            System.out.println("Popped "+node+" from the Stack.");
            System.out.println(stack.toString());
            
            /*
            int size = stack.getSize();
            for (int j=0; j<size; j++) {
                int node = stack.pop();
                System.out.println("Popped "+node+" from the Stack.");
                System.out.println(stack.toString());
            }
            */
            System.out.println();
        }
        
        {
            // Queue
            System.out.println("Queue.");
            Queue queue = new Queue(unsorted);
            System.out.println(queue.toString());

            int next = random.nextInt(unsorted.length*100);
            System.out.println("Pushing a new node onto the Queue "+next);
            queue.enqueue(next);
            System.out.println(queue.toString());
            
            /*
            int node = queue.dequeue();
            System.out.println("Dequeued "+node+" from the Queue.");
            System.out.println(queue.toString());

            /*
            int size = queue.getSize();
            for (int j=0; j<size; j++) {
                int node = queue.dequeue();
                System.out.println("Dequeued "+node+" from the Queue.");
                System.out.println(queue.toString());
            }
            */
            System.out.println();
        }

        {
            // HashMap
            System.out.println("Hash Map.");
            HashMap hash = new HashMap(unsorted);
            System.out.println(hash.toString());

            int next = random.nextInt(unsorted.length*100);
            System.out.println("Putting a new node into the HashMap "+next);
            hash.put(next,next);
            System.out.println(hash.toString());

            hash.remove(next);
            System.out.println("Removed key="+next+" from the HashMap.");
            System.out.println(hash.toString());

            /*
            /*
            for (int j=0; j<unsorted.length; j++) {
                int key = unsorted[j];
                hash.remove(key);
                System.out.println("Removed key="+key+" from the HashMap.");
                System.out.println(hash.toString());
            }
            */
            System.out.println();
        }

        {
            // BINARY SEARCH TREE
            System.out.println("Binary search tree.");
            BinarySearchTree bst = new BinarySearchTree(unsorted);
            System.out.println(bst.toString());

            // Add random node
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            bst.add(next);
            System.out.println(bst.toString());

            // Remove a previously added node
            next = random.nextInt(unsorted.length);
            System.out.println("Removing a previously added node "+unsorted[next]);
            bst.remove(unsorted[next]);
            System.out.println(bst.toString());
            System.out.println();
        }
        
        {
            // MIN-HEAP
            System.out.println("Min-Heap.");
            BinaryHeap minHeap = new BinaryHeap(unsorted);
            System.out.println(minHeap.toString());
            
            int next = minHeap.getRootValue();
            System.out.println("Removing the root "+next);
            minHeap.remove(next);
            System.out.println(minHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            minHeap.add(next);
            System.out.println(minHeap.toString());
            
            int index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Adding a previously added node "+next);
            minHeap.add(next);
            System.out.println(minHeap.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            minHeap.remove(next);
            System.out.println(minHeap.toString());
            System.out.println();
        }
        
        {
            // MAX-HEAP
            System.out.println("Max-Heap.");
            BinaryHeap maxHeap = new BinaryHeap(unsorted,TYPE.MAX);
            System.out.println(maxHeap.toString());
            
            int next = maxHeap.getRootValue();
            System.out.println("Removing the root "+next);
            maxHeap.remove(next);
            System.out.println(maxHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            maxHeap.add(next);
            System.out.println(maxHeap.toString());
            
            int index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Adding a previously added node "+next);
            maxHeap.add(next);
            System.out.println(maxHeap.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            maxHeap.remove(next);
            System.out.println(maxHeap.toString());
            System.out.println();
        }

        {
            // UNDIRECTED GRAPH
            System.out.println("Undirected Graph.");
            List<Vertex> verticies = new ArrayList<Vertex>();
            Graph.Vertex v1 = new Graph.Vertex(1);            
            verticies.add(v1);
            Graph.Vertex v2 = new Graph.Vertex(2);            
            verticies.add(v2);
            Graph.Vertex v3 = new Graph.Vertex(3);            
            verticies.add(v3);
            Graph.Vertex v4 = new Graph.Vertex(4);            
            verticies.add(v4);
            Graph.Vertex v5 = new Graph.Vertex(5);            
            verticies.add(v5);
            Graph.Vertex v6 = new Graph.Vertex(6);            
            verticies.add(v6);

            List<Edge> edges = new ArrayList<Edge>();
            Graph.Edge e1_2 = new Graph.Edge(7, v1, v2);
            edges.add(e1_2);
            Graph.Edge e1_3 = new Graph.Edge(9, v1, v3);
            edges.add(e1_3);
            Graph.Edge e1_6 = new Graph.Edge(14, v1, v6);
            edges.add(e1_6);
            Graph.Edge e2_3 = new Graph.Edge(10, v2, v3);
            edges.add(e2_3);
            Graph.Edge e2_4 = new Graph.Edge(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge e3_4 = new Graph.Edge(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge e3_6 = new Graph.Edge(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge e5_6 = new Graph.Edge(9, v5, v6);
            edges.add(e5_6);
            Graph.Edge e4_5 = new Graph.Edge(6, v4, v5);
            edges.add(e4_5);

            Graph undirected = new Graph(verticies,edges);
            System.out.println(undirected.toString());
            
            Graph.Vertex start = v1;
            System.out.println("Dijstra's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex, CostPathPair> map = Dijkstra.getShortestPaths(undirected, start);
            System.out.println(getPathMapString(map));

            Graph.Vertex end = v5;
            System.out.println("Dijstra's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Dijkstra.CostPathPair pair = Dijkstra.getShortestPath(undirected, start, end);
            if (pair!=null) System.out.println(pair.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            System.out.println();
        }

        {
            // DIRECTED GRAPH
            System.out.println("Directed Graph.");
            List<Vertex> verticies = new ArrayList<Vertex>();
            Graph.Vertex v1 = new Graph.Vertex(1);            
            verticies.add(v1);
            Graph.Vertex v2 = new Graph.Vertex(2);            
            verticies.add(v2);
            Graph.Vertex v3 = new Graph.Vertex(3);            
            verticies.add(v3);
            Graph.Vertex v4 = new Graph.Vertex(4);            
            verticies.add(v4);
            Graph.Vertex v5 = new Graph.Vertex(5);            
            verticies.add(v5);
            Graph.Vertex v6 = new Graph.Vertex(6);            
            verticies.add(v6);
            Graph.Vertex v7 = new Graph.Vertex(7);            
            verticies.add(v7);

            List<Edge> edges = new ArrayList<Edge>();
            Graph.Edge e1_2 = new Graph.Edge(7, v1, v2);
            edges.add(e1_2);
            Graph.Edge e1_3 = new Graph.Edge(9, v1, v3);
            edges.add(e1_3);
            Graph.Edge e1_6 = new Graph.Edge(14, v1, v6);
            edges.add(e1_6);
            Graph.Edge e2_3 = new Graph.Edge(10, v2, v3);
            edges.add(e2_3);
            Graph.Edge e2_4 = new Graph.Edge(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge e3_4 = new Graph.Edge(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge e3_6 = new Graph.Edge(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge e6_5 = new Graph.Edge(9, v6, v5);
            edges.add(e6_5);
            Graph.Edge e4_5 = new Graph.Edge(6, v4, v5);
            edges.add(e4_5);
            Graph.Edge e4_7 = new Graph.Edge(16, v4, v7);
            edges.add(e4_7);
            
            Graph directed = new Graph(Graph.TYPE.DIRECTED,verticies,edges);
            System.out.println(directed.toString());
            
            Graph.Vertex start = v1;
            System.out.println("Dijstra's shortest paths of the directed graph from "+start.getValue());
            Map<Graph.Vertex, CostPathPair> map = Dijkstra.getShortestPaths(directed, start);
            System.out.println(getPathMapString(map));

            Graph.Vertex end = v5;
            System.out.println("Dijstra's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Dijkstra.CostPathPair pair = Dijkstra.getShortestPath(directed, start, end);
            if (pair!=null) System.out.println(pair.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            System.out.println();
        }
    }
    
    private static final String getPathMapString(Map<Graph.Vertex, CostPathPair> map) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex v : map.keySet()) {
            CostPathPair pair = map.get(v);
            builder.append("to vertex=").append(v.getValue()).append("\n");
            builder.append(pair.toString()).append("\n");
        }
        return builder.toString();
    }
}
