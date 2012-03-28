package com.jwetherell.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinaryHeap.TYPE;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.LinkedList;
import com.jwetherell.algorithms.data_structures.Matrix;
import com.jwetherell.algorithms.data_structures.Queue;
import com.jwetherell.algorithms.data_structures.SegmentTree;
import com.jwetherell.algorithms.data_structures.SkipList;
import com.jwetherell.algorithms.data_structures.Stack;
import com.jwetherell.algorithms.graph.BellmanFord;
import com.jwetherell.algorithms.graph.CycleDetection;
import com.jwetherell.algorithms.graph.Dijkstra;
import com.jwetherell.algorithms.graph.FloydWarshall;
import com.jwetherell.algorithms.graph.Johnson;
import com.jwetherell.algorithms.graph.Prim;
import com.jwetherell.algorithms.graph.TopologicalSort;


public class DataStructures {
    private static final int SIZE = 100;
    
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
            // SkipList
            System.out.println("Skip List.");
            SkipList list = new SkipList(unsorted);
            list.add(99);
            System.out.println(list.toString());
            list.remove(99);
            System.out.println(list.toString());

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
            Map<Graph.Vertex, Graph.CostPathPair> map1 = Dijkstra.getShortestPaths(undirected, start);
            System.out.println(getPathMapString(start,map1));

            Graph.Vertex end = v5;
            System.out.println("Dijstra's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair pair1 = Dijkstra.getShortestPath(undirected, start, end);
            if (pair1!=null) System.out.println(pair1.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());

            start = v1;
            System.out.println("Bellman-Ford's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex, Graph.CostPathPair> map2 = BellmanFord.getShortestPaths(undirected, start);
            System.out.println(getPathMapString(start,map2));

            end = v5;
            System.out.println("Bellman-Ford's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair pair2 = BellmanFord.getShortestPath(undirected, start, end);
            if (pair2!=null) System.out.println(pair2.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());

            System.out.println("Prim's minimum spanning tree of the undirected graph from "+start.getValue());
            Graph.CostPathPair pair = Prim.getMinimumSpanningTree(undirected, start);
            System.out.println(pair.toString());
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
            Map<Graph.Vertex, Graph.CostPathPair> map = Dijkstra.getShortestPaths(directed, start);
            System.out.println(getPathMapString(start,map));

            Graph.Vertex end = v5;
            System.out.println("Dijstra's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair pair = Dijkstra.getShortestPath(directed, start, end);
            if (pair!=null) System.out.println(pair.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            
            start = v1;
            System.out.println("Bellman-Ford's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex, Graph.CostPathPair> map2 = BellmanFord.getShortestPaths(directed, start);
            System.out.println(getPathMapString(start,map2));

            end = v5;
            System.out.println("Bellman-Ford's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (pair2!=null) System.out.println(pair2.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            System.out.println();
        }

        {
            // DIRECTED GRAPH (WITH NEGATIVE WEIGHTS)
            System.out.println("Undirected Graph with Negative Weights.");
            List<Vertex> verticies = new ArrayList<Vertex>();
            Graph.Vertex v1 = new Graph.Vertex(1);            
            verticies.add(v1);
            Graph.Vertex v2 = new Graph.Vertex(2);            
            verticies.add(v2);
            Graph.Vertex v3 = new Graph.Vertex(3);            
            verticies.add(v3);
            Graph.Vertex v4 = new Graph.Vertex(4);            
            verticies.add(v4);

            List<Edge> edges = new ArrayList<Edge>();
            Graph.Edge e1_4 = new Graph.Edge(2, v1, v4);
            edges.add(e1_4);
            Graph.Edge e2_1 = new Graph.Edge(6, v2, v1);
            edges.add(e2_1);
            Graph.Edge e2_3 = new Graph.Edge(3, v2, v3);
            edges.add(e2_3);
            Graph.Edge e3_1 = new Graph.Edge(4, v3, v1);
            edges.add(e3_1);
            Graph.Edge e3_4 = new Graph.Edge(5, v3, v4);
            edges.add(e3_4);
            Graph.Edge e4_2 = new Graph.Edge(-7, v4, v2);
            edges.add(e4_2);
            Graph.Edge e4_3 = new Graph.Edge(-3, v4, v3);
            edges.add(e4_3);
            
            Graph directed = new Graph(Graph.TYPE.DIRECTED,verticies,edges);
            System.out.println(directed.toString());

            Graph.Vertex start = v1;
            System.out.println("Bellman-Ford's shortest paths of the directed graph from "+start.getValue());
            Map<Graph.Vertex, Graph.CostPathPair> map2 = BellmanFord.getShortestPaths(directed, start);
            System.out.println(getPathMapString(start,map2));

            Graph.Vertex end = v3;
            System.out.println("Bellman-Ford's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (pair2!=null) System.out.println(pair2.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());

            System.out.println("Johnson's all-pairs shortest path of the directed graph.");
            Map<Vertex, Map<Vertex, Set<Edge>>> paths = Johnson.getAllPairsShortestPaths(directed);
            if (paths==null) System.out.println("Directed graph contains a negative weight cycle.");
            else System.out.println(getPathMapString(paths));

            System.out.println("Floyd-Warshall's all-pairs shortest path weights of the directed graph.");
            Map<Vertex, Map<Vertex, Integer>> pathWeights = FloydWarshall.getAllPairsShortestPaths(directed);
            System.out.println(getWeightMapString(pathWeights));
            System.out.println();
        }
        
        {
            // MATRIX
            Matrix matrix1 = new Matrix(4,3);
            matrix1.set(0, 0, 14);
            matrix1.set(0, 1, 9);
            matrix1.set(0, 2, 3);
            matrix1.set(1, 0, 2);
            matrix1.set(1, 1, 11);
            matrix1.set(1, 2, 15);
            matrix1.set(2, 0, 0);
            matrix1.set(2, 1, 12);
            matrix1.set(2, 2, 17);
            matrix1.set(3, 0, 5);
            matrix1.set(3, 1, 2);
            matrix1.set(3, 2, 3);
            
            Matrix matrix2 = new Matrix(3,2);
            matrix2.set(0, 0, 12);
            matrix2.set(0, 1, 25);
            matrix2.set(1, 0, 9);
            matrix2.set(1, 1, 10);
            matrix2.set(2, 0, 8);
            matrix2.set(2, 1, 5);

            System.out.println("Matrix multiplication.");
            Matrix matrix3 = matrix1.multiply(matrix2);
            System.out.println(matrix3);
            
            int rows = 2;
            int cols = 2;
            int counter = 0;
            Matrix matrix4 = new Matrix(rows,cols);
            for (int r=0; r<rows; r++) {
                for (int c=0; c<cols; c++) {
                    matrix4.set(r, c, counter++);
                }
            }

            System.out.println("Matrix subtraction.");
            Matrix matrix5 = matrix4.subtract(matrix4);
            System.out.println(matrix5);

            System.out.println("Matrix addition.");
            Matrix matrix6 = matrix4.add(matrix4);
            System.out.println(matrix6);
            
            Matrix matrix7 = new Matrix(2,2);
            matrix7.set(0, 0, 1);
            matrix7.set(0, 1, 2);
            matrix7.set(1, 0, 3);
            matrix7.set(1, 1, 4);
            
            Matrix matrix8 = new Matrix(2,2);
            matrix8.set(0, 0, 1);
            matrix8.set(0, 1, 2);
            matrix8.set(1, 0, 3);
            matrix8.set(1, 1, 4);
            
            System.out.println("Matrix multiplication.");
            Matrix matrix9 = matrix7.multiply(matrix8);
            System.out.println(matrix9);
        }

        {
            // UNDIRECTED GRAPH
            System.out.println("Undirected Graph cycle check.");
            List<Vertex> cycledVerticies = new ArrayList<Vertex>();
            Graph.Vertex cv1 = new Graph.Vertex(1);            
            cycledVerticies.add(cv1);
            Graph.Vertex cv2 = new Graph.Vertex(2);            
            cycledVerticies.add(cv2);
            Graph.Vertex cv3 = new Graph.Vertex(3);            
            cycledVerticies.add(cv3);
            Graph.Vertex cv4 = new Graph.Vertex(4);            
            cycledVerticies.add(cv4);
            Graph.Vertex cv5 = new Graph.Vertex(5);            
            cycledVerticies.add(cv5);
            Graph.Vertex cv6 = new Graph.Vertex(6);            
            cycledVerticies.add(cv6);

            List<Edge> cycledEdges = new ArrayList<Edge>();
            Graph.Edge ce1_2 = new Graph.Edge(7, cv1, cv2);
            cycledEdges.add(ce1_2);
            Graph.Edge ce2_4 = new Graph.Edge(15, cv2, cv4);
            cycledEdges.add(ce2_4);
            Graph.Edge ce3_4 = new Graph.Edge(11, cv3, cv4);
            cycledEdges.add(ce3_4);
            Graph.Edge ce3_6 = new Graph.Edge(2, cv3, cv6);
            cycledEdges.add(ce3_6);
            Graph.Edge ce5_6 = new Graph.Edge(9, cv5, cv6);
            cycledEdges.add(ce5_6);
            Graph.Edge ce4_5 = new Graph.Edge(6, cv4, cv5);
            cycledEdges.add(ce4_5);

            Graph undirectedWithCycle = new Graph(cycledVerticies,cycledEdges);
            System.out.println(undirectedWithCycle.toString());

            System.out.println("Cycle detection of the undirected graph.");
            boolean result = CycleDetection.detect(undirectedWithCycle);
            System.out.println("result="+result);
            System.out.println();

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
            Graph.Edge e2_4 = new Graph.Edge(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge e3_4 = new Graph.Edge(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge e3_6 = new Graph.Edge(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge e4_5 = new Graph.Edge(6, v4, v5);
            edges.add(e4_5);

            Graph undirectedWithoutCycle = new Graph(verticies,edges);
            System.out.println(undirectedWithoutCycle.toString());

            System.out.println("Cycle detection of the undirected graph.");
            result = CycleDetection.detect(undirectedWithoutCycle);
            System.out.println("result="+result);
            System.out.println();

            System.out.println("Topological sort of the undirected graph.");
            List<Graph.Vertex> results = TopologicalSort.sort(undirectedWithoutCycle);
            System.out.println("result="+results);
            System.out.println();
        }

        {
            // DIRECTED GRAPH
            System.out.println("Directed Graph topological sort.");
            List<Vertex> verticies = new ArrayList<Vertex>();
            Graph.Vertex cv1 = new Graph.Vertex(1);            
            verticies.add(cv1);
            Graph.Vertex cv2 = new Graph.Vertex(2);            
            verticies.add(cv2);
            Graph.Vertex cv3 = new Graph.Vertex(3);            
            verticies.add(cv3);
            Graph.Vertex cv4 = new Graph.Vertex(4);            
            verticies.add(cv4);
            Graph.Vertex cv5 = new Graph.Vertex(5);            
            verticies.add(cv5);
            Graph.Vertex cv6 = new Graph.Vertex(6);            
            verticies.add(cv6);

            List<Edge> edges = new ArrayList<Edge>();
            Graph.Edge ce1_2 = new Graph.Edge(1, cv1, cv2);
            edges.add(ce1_2);
            Graph.Edge ce2_4 = new Graph.Edge(2, cv2, cv4);
            edges.add(ce2_4);
            Graph.Edge ce4_3 = new Graph.Edge(3, cv4, cv3);
            edges.add(ce4_3);
            Graph.Edge ce3_6 = new Graph.Edge(4, cv3, cv6);
            edges.add(ce3_6);
            Graph.Edge ce5_6 = new Graph.Edge(5, cv5, cv6);
            edges.add(ce5_6);
            Graph.Edge ce4_5 = new Graph.Edge(6, cv4, cv5);
            edges.add(ce4_5);

            Graph directed = new Graph(Graph.TYPE.DIRECTED,verticies,edges);
            System.out.println(directed.toString());

            System.out.println("Topological sort of the undirected graph.");
            List<Graph.Vertex> results = TopologicalSort.sort(directed);
            System.out.println("result="+results);
            System.out.println();
        }

        {
            //Segment tree
            SegmentTree.Segment[] segments = new SegmentTree.Segment[4];
            segments[0] = new SegmentTree.Segment(0,1,0,0,0); //first point in the 0th quadrant
            segments[1] = new SegmentTree.Segment(1,0,1,0,0); //second point in the 1st quadrant
            segments[2] = new SegmentTree.Segment(2,0,0,1,0); //third point in the 2nd quadrant
            segments[3] = new SegmentTree.Segment(3,0,0,0,1); //fourth point in the 3rd quadrant
            SegmentTree tree = new SegmentTree(segments);
            
            SegmentTree.Query query = tree.query(0, 3);
            System.out.println(query.quad1+" "+query.quad2+" "+query.quad3+" "+query.quad4);

            tree.update(1, 0, -1, 1, 0); //Move the first point from quadrant one to quadrant two
            tree.update(2, 0, 1, -1, 0); //Move the second point from quadrant two to quadrant one
            tree.update(3, 1, 0, 0, -1); //Move the third point from quadrant third to quadrant zero
            
            query = tree.query(2, 3);
            System.out.println(query.quad1+" "+query.quad2+" "+query.quad3+" "+query.quad4);

            tree.update(0, -1, 1, 0, 0); //Move the zeroth point from quadrant zero to quadrant one
            tree.update(1, 0, 0, -1, 1); //Move the first point from quadrant three to quadrant four
            
            query = tree.query(0, 2);
            System.out.println(query.quad1+" "+query.quad2+" "+query.quad3+" "+query.quad4);
        }
    }
    
    private static final String getPathMapString(Graph.Vertex start, Map<Graph.Vertex, Graph.CostPathPair> map) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex v : map.keySet()) {
            Graph.CostPathPair pair = map.get(v);
            builder.append("From ").append(start.getValue()).append(" to vertex=").append(v.getValue()).append("\n");
            if (pair!=null) builder.append(pair.toString()).append("\n");

        }
        return builder.toString();
    }

    private static final String getPathMapString(Map<Vertex, Map<Vertex, Set<Edge>>> paths) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex v : paths.keySet()) {
            Map<Vertex, Set<Edge>> map = paths.get(v);
            for (Graph.Vertex v2 : map.keySet()) {
                builder.append("From=").append(v.getValue()).append(" to=").append(v2.getValue()).append("\n");
                Set<Graph.Edge> path = map.get(v2);
                builder.append(path).append("\n");
            }
        }
        return builder.toString();
    }

    private static final String getWeightMapString(Map<Vertex, Map<Vertex, Integer>> paths) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex v : paths.keySet()) {
            Map<Vertex, Integer> map = paths.get(v);
            for (Graph.Vertex v2 : map.keySet()) {
                builder.append("From=").append(v.getValue()).append(" to=").append(v2.getValue()).append("\n");
                Integer weight = map.get(v2);
                builder.append(weight).append("\n");
            }
        }
        return builder.toString();
    }
}
