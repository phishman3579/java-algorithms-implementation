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
import com.jwetherell.algorithms.data_structures.PatriciaTrie;
import com.jwetherell.algorithms.data_structures.RadixTree;
import com.jwetherell.algorithms.data_structures.SuffixTree;
import com.jwetherell.algorithms.data_structures.TrieMap;
import com.jwetherell.algorithms.data_structures.LinkedList;
import com.jwetherell.algorithms.data_structures.Matrix;
import com.jwetherell.algorithms.data_structures.Queue;
import com.jwetherell.algorithms.data_structures.SegmentTree;
import com.jwetherell.algorithms.data_structures.SkipList;
import com.jwetherell.algorithms.data_structures.SplayTree;
import com.jwetherell.algorithms.data_structures.Stack;
import com.jwetherell.algorithms.data_structures.SuffixTrie;
import com.jwetherell.algorithms.data_structures.Treap;
import com.jwetherell.algorithms.data_structures.Trie;
import com.jwetherell.algorithms.graph.BellmanFord;
import com.jwetherell.algorithms.graph.CycleDetection;
import com.jwetherell.algorithms.graph.Dijkstra;
import com.jwetherell.algorithms.graph.FloydWarshall;
import com.jwetherell.algorithms.graph.Johnson;
import com.jwetherell.algorithms.graph.Prim;
import com.jwetherell.algorithms.graph.TopologicalSort;


public class DataStructures {
    private static final int SIZE = 100;
    
    private static Integer[] unsorted = null;
    
    public static void main(String[] args) {
        Random random = new Random();
        
        System.out.print("Array=");
        unsorted = new Integer[SIZE];
        for (int i=0; i<unsorted.length; i++) {
            Integer j = random.nextInt(unsorted.length*10);
            //Make sure there are no duplicates
            boolean found = true;
            while (found) {
                found = false;
                for (int k=0; k<i; k++) {
                    int l = unsorted[k];
                    if (j==l) {
                        found = true;
                        j = random.nextInt(unsorted.length*10);
                        break;
                    }
                }
            }
            unsorted[i] = j;
            System.out.print(j+",");
        }

        System.out.println();
        System.out.println();

        {
            // MIN-HEAP
            System.out.println("Min-Heap.");
            BinaryHeap<Integer> minHeap = new BinaryHeap<Integer>();
            for (int i=0;  i<unsorted.length; i++) {
                int item = unsorted[i];
                minHeap.add(item);
                boolean exists = (minHeap.getSize()==i+1);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(minHeap.toString());
                    return;
                }
            }
            System.out.println(minHeap.toString());

            for (int i=0; i<unsorted.length; i++) {
                minHeap.removeRoot();
                boolean exists = (minHeap.getSize()==unsorted.length-(i+1));
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(minHeap.toString());
                    return;
                }
            }
            if (minHeap.getRootValue()!=null) {
                System.err.println("YIKES!! heap isn't empty.");
                System.err.println(minHeap.toString());
                return;
            }

            System.out.println();
        }
        
        {
            // MAX-HEAP
            System.out.println("Max-Heap.");
            BinaryHeap<Integer> maxHeap = new BinaryHeap<Integer>(TYPE.MAX);
            for (int i=0;  i<unsorted.length; i++) {
                int item = unsorted[i];
                maxHeap.add(item);
                boolean exists = (maxHeap.getSize()==i+1);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(maxHeap.toString());
                    return;
                }
            }
            System.out.println(maxHeap.toString());

            for (int i=0; i<unsorted.length; i++) {
                maxHeap.removeRoot();
                boolean exists = (maxHeap.getSize()==unsorted.length-(i+1));
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(maxHeap.toString());
                    return;
                }
            }
            if (maxHeap.getRootValue()!=null) {
                System.err.println("YIKES!! heap isn't empty.");
                System.err.println(maxHeap.toString());
                return;
            }

            System.out.println();
        }

        {
            // BINARY SEARCH TREE (first)
            System.out.println("Binary search tree with first HashNode.");
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(BinarySearchTree.TYPE.FIRST);
            for (int i : unsorted) {
                bst.add(i);
                boolean exists = bst.contains(i);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(bst.toString());
                    return;
                }
            }
            System.out.println(bst.toString());

            // Add random HashNode
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new HashNode "+next);
            bst.add(next);
            System.out.println(bst.toString());
            System.out.println("Removing a HashNode "+next);
            bst.remove(next);
            System.out.println(bst.toString());

            // Remove a previously added HashNode
            next = random.nextInt(unsorted.length);
            boolean contains = bst.contains(unsorted[next]);
            System.out.println("Does "+unsorted[next]+" exist in the BST? "+contains);
            System.out.println("Removing a previously added HashNode "+unsorted[next]);
            bst.remove(unsorted[next]);
            System.out.println(bst.toString());

            for (int i : unsorted) {
                bst.remove(i);
                boolean exists = bst.contains(i);
                if (exists) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    System.err.println(bst.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            // BINARY SEARCH TREE (middle)
            System.out.println("Binary search tree with middle HashNode.");
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(BinarySearchTree.TYPE.MIDDLE);
            for (int i : unsorted) {
                bst.add(i);
                boolean exists = bst.contains(i);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(bst.toString());
                    return;
                }
            }
            System.out.println(bst.toString());

            // Add random HashNode
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new HashNode "+next);
            bst.add(next);
            System.out.println(bst.toString());
            System.out.println("Removing a HashNode "+next);
            bst.remove(next);
            System.out.println(bst.toString());

            // Remove a previously added HashNode
            next = random.nextInt(unsorted.length);
            boolean contains = bst.contains(unsorted[next]);
            System.out.println("Does "+unsorted[next]+" exist in the BST? "+contains);
            System.out.println("Removing a previously added HashNode "+unsorted[next]);
            bst.remove(unsorted[next]);
            System.out.println(bst.toString());

            for (int i : unsorted) {
                bst.remove(i);
                boolean exists = bst.contains(i);
                if (exists) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    System.err.println(bst.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            // BINARY SEARCH TREE (random)
            System.out.println("Binary search tree using random HashNode.");
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(BinarySearchTree.TYPE.RANDOM);
            for (int i : unsorted) {
                bst.add(i);
                boolean exists = bst.contains(i);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(bst.toString());
                    return;
                }
            }
            System.out.println(bst.toString());

            // Add random HashNode
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new HashNode "+next);
            bst.add(next);
            System.out.println(bst.toString());
            System.out.println("Removing a HashNode "+next);
            bst.remove(next);
            System.out.println(bst.toString());

            // Remove a previously added HashNode
            next = random.nextInt(unsorted.length);
            boolean contains = bst.contains(unsorted[next]);
            System.out.println("Does "+unsorted[next]+" exist in the BST? "+contains);
            System.out.println("Removing a previously added HashNode "+unsorted[next]);
            bst.remove(unsorted[next]);
            System.out.println(bst.toString());

            for (int i : unsorted) {
                bst.remove(i);
                boolean exists = bst.contains(i);
                if (exists) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    System.err.println(bst.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            // UNDIRECTED GRAPH
            System.out.println("Undirected Graph.");
            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);            
            verticies.add(v1);
            Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);            
            verticies.add(v2);
            Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);            
            verticies.add(v3);
            Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);            
            verticies.add(v4);
            Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);            
            verticies.add(v5);
            Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);            
            verticies.add(v6);

            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
            edges.add(e1_2);
            Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
            edges.add(e1_3);
            Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
            edges.add(e1_6);
            Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
            edges.add(e2_3);
            Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge<Integer> e5_6 = new Graph.Edge<Integer>(9, v5, v6);
            edges.add(e5_6);
            Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
            edges.add(e4_5);

            Graph<Integer> undirected = new Graph<Integer>(verticies,edges);
            System.out.println(undirected.toString());
            
            Graph.Vertex<Integer> start = v1;
            System.out.println("Dijstra's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(undirected, start);
            System.out.println(getPathMapString(start,map1));

            Graph.Vertex<Integer> end = v5;
            System.out.println("Dijstra's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(undirected, start, end);
            if (pair1!=null) System.out.println(pair1.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());

            start = v1;
            System.out.println("Bellman-Ford's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(undirected, start);
            System.out.println(getPathMapString(start,map2));

            end = v5;
            System.out.println("Bellman-Ford's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(undirected, start, end);
            if (pair2!=null) System.out.println(pair2.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());

            System.out.println("Prim's minimum spanning tree of the undirected graph from "+start.getValue());
            Graph.CostPathPair<Integer> pair = Prim.getMinimumSpanningTree(undirected, start);
            System.out.println(pair.toString());
            System.out.println();
        }

        {
            // DIRECTED GRAPH
            System.out.println("Directed Graph.");
            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);            
            verticies.add(v1);
            Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);            
            verticies.add(v2);
            Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);            
            verticies.add(v3);
            Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);            
            verticies.add(v4);
            Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);            
            verticies.add(v5);
            Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);            
            verticies.add(v6);
            Graph.Vertex<Integer> v7 = new Graph.Vertex<Integer>(7);            
            verticies.add(v7);

            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
            edges.add(e1_2);
            Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
            edges.add(e1_3);
            Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
            edges.add(e1_6);
            Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
            edges.add(e2_3);
            Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge<Integer> e6_5 = new Graph.Edge<Integer>(9, v6, v5);
            edges.add(e6_5);
            Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
            edges.add(e4_5);
            Graph.Edge<Integer> e4_7 = new Graph.Edge<Integer>(16, v4, v7);
            edges.add(e4_7);
            
            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED,verticies,edges);
            System.out.println(directed.toString());
            
            Graph.Vertex<Integer> start = v1;
            System.out.println("Dijstra's shortest paths of the directed graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = Dijkstra.getShortestPaths(directed, start);
            System.out.println(getPathMapString(start,map));

            Graph.Vertex<Integer> end = v5;
            System.out.println("Dijstra's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair = Dijkstra.getShortestPath(directed, start, end);
            if (pair!=null) System.out.println(pair.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            
            start = v1;
            System.out.println("Bellman-Ford's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed, start);
            System.out.println(getPathMapString(start,map2));

            end = v5;
            System.out.println("Bellman-Ford's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (pair2!=null) System.out.println(pair2.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            System.out.println();
        }

        {
            // DIRECTED GRAPH (WITH NEGATIVE WEIGHTS)
            System.out.println("Undirected Graph with Negative Weights.");
            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);            
            verticies.add(v1);
            Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);            
            verticies.add(v2);
            Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);            
            verticies.add(v3);
            Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);            
            verticies.add(v4);

            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> e1_4 = new Graph.Edge<Integer>(2, v1, v4);
            edges.add(e1_4);
            Graph.Edge<Integer> e2_1 = new Graph.Edge<Integer>(6, v2, v1);
            edges.add(e2_1);
            Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(3, v2, v3);
            edges.add(e2_3);
            Graph.Edge<Integer> e3_1 = new Graph.Edge<Integer>(4, v3, v1);
            edges.add(e3_1);
            Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(5, v3, v4);
            edges.add(e3_4);
            Graph.Edge<Integer> e4_2 = new Graph.Edge<Integer>(-7, v4, v2);
            edges.add(e4_2);
            Graph.Edge<Integer> e4_3 = new Graph.Edge<Integer>(-3, v4, v3);
            edges.add(e4_3);
            
            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED,verticies,edges);
            System.out.println(directed.toString());

            Graph.Vertex<Integer> start = v1;
            System.out.println("Bellman-Ford's shortest paths of the directed graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed, start);
            System.out.println(getPathMapString(start,map2));

            Graph.Vertex<Integer> end = v3;
            System.out.println("Bellman-Ford's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (pair2!=null) System.out.println(pair2.toString());
            else System.out.println("No path from "+start.getValue()+" to "+end.getValue());

            System.out.println("Johnson's all-pairs shortest path of the directed graph.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> paths = Johnson.getAllPairsShortestPaths(directed);
            if (paths==null) System.out.println("Directed graph contains a negative weight cycle.");
            else System.out.println(getPathMapString(paths));

            System.out.println("Floyd-Warshall's all-pairs shortest path weights of the directed graph.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> pathWeights = FloydWarshall.getAllPairsShortestPaths(directed);
            System.out.println(getWeightMapString(pathWeights));
            System.out.println();
        }

        {
            // UNDIRECTED GRAPH
            System.out.println("Undirected Graph cycle check.");
            List<Vertex<Integer>> cycledVerticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);            
            cycledVerticies.add(cv1);
            Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);            
            cycledVerticies.add(cv2);
            Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);            
            cycledVerticies.add(cv3);
            Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);            
            cycledVerticies.add(cv4);
            Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);            
            cycledVerticies.add(cv5);
            Graph.Vertex<Integer> cv6 = new Graph.Vertex<Integer>(6);            
            cycledVerticies.add(cv6);

            List<Edge<Integer>> cycledEdges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(7, cv1, cv2);
            cycledEdges.add(ce1_2);
            Graph.Edge<Integer> ce2_4 = new Graph.Edge<Integer>(15, cv2, cv4);
            cycledEdges.add(ce2_4);
            Graph.Edge<Integer> ce3_4 = new Graph.Edge<Integer>(11, cv3, cv4);
            cycledEdges.add(ce3_4);
            Graph.Edge<Integer> ce3_6 = new Graph.Edge<Integer>(2, cv3, cv6);
            cycledEdges.add(ce3_6);
            Graph.Edge<Integer> ce5_6 = new Graph.Edge<Integer>(9, cv5, cv6);
            cycledEdges.add(ce5_6);
            Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(6, cv4, cv5);
            cycledEdges.add(ce4_5);

            Graph<Integer> undirectedWithCycle = new Graph<Integer>(cycledVerticies,cycledEdges);
            System.out.println(undirectedWithCycle.toString());

            System.out.println("Cycle detection of the undirected graph.");
            boolean result = CycleDetection.detect(undirectedWithCycle);
            System.out.println("result="+result);
            System.out.println();

            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
            verticies.add(v1);
            Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
            verticies.add(v2);
            Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
            verticies.add(v3);
            Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
            verticies.add(v4);
            Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
            verticies.add(v5);
            Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
            verticies.add(v6);
            
            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
            edges.add(e1_2);
            Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
            edges.add(e2_4);
            Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
            edges.add(e3_4);
            Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
            edges.add(e3_6);
            Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
            edges.add(e4_5);

            Graph<Integer> undirectedWithoutCycle = new Graph<Integer>(verticies,edges);
            System.out.println(undirectedWithoutCycle.toString());

            System.out.println("Cycle detection of the undirected graph.");
            result = CycleDetection.detect(undirectedWithoutCycle);
            System.out.println("result="+result);
            System.out.println();
        }

        {
            // DIRECTED GRAPH
            System.out.println("Directed Graph topological sort.");
            List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> cv1 = new Graph.Vertex<Integer>(1);            
            verticies.add(cv1);
            Graph.Vertex<Integer> cv2 = new Graph.Vertex<Integer>(2);            
            verticies.add(cv2);
            Graph.Vertex<Integer> cv3 = new Graph.Vertex<Integer>(3);            
            verticies.add(cv3);
            Graph.Vertex<Integer> cv4 = new Graph.Vertex<Integer>(4);            
            verticies.add(cv4);
            Graph.Vertex<Integer> cv5 = new Graph.Vertex<Integer>(5);            
            verticies.add(cv5);
            Graph.Vertex<Integer> cv6 = new Graph.Vertex<Integer>(6);            
            verticies.add(cv6);

            List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
            Graph.Edge<Integer> ce1_2 = new Graph.Edge<Integer>(1, cv1, cv2);
            edges.add(ce1_2);
            Graph.Edge<Integer> ce2_4 = new Graph.Edge<Integer>(2, cv2, cv4);
            edges.add(ce2_4);
            Graph.Edge<Integer> ce4_3 = new Graph.Edge<Integer>(3, cv4, cv3);
            edges.add(ce4_3);
            Graph.Edge<Integer> ce3_6 = new Graph.Edge<Integer>(4, cv3, cv6);
            edges.add(ce3_6);
            Graph.Edge<Integer> ce5_6 = new Graph.Edge<Integer>(5, cv5, cv6);
            edges.add(ce5_6);
            Graph.Edge<Integer> ce4_5 = new Graph.Edge<Integer>(6, cv4, cv5);
            edges.add(ce4_5);

            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED,verticies,edges);
            System.out.println(directed.toString());

            System.out.println("Topological sort of the directed graph.");
            List<Graph.Vertex<Integer>> results = TopologicalSort.sort(directed);
            System.out.println("result="+results);
            System.out.println();
        }

        {
            // Hash Map
            System.out.println("Hash Map.");
            HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
            for (int i : unsorted) {
                hash.put(i, i);
                boolean exists = hash.contains(i);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(hash.toString());
                    return;
                }
            }
            System.out.println(hash.toString());

            for (int i : unsorted) {
                hash.remove(i);
                boolean exists = hash.contains(i);
                if (exists) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    System.err.println(hash.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            // Linked List
            System.out.println("Linked List.");
            LinkedList<Integer> list = new LinkedList<Integer>();
            for (int i=0;  i<unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
                boolean exists = (list.getSize()==i+1);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(list.toString());
                    return;
                }
            }
            System.out.println(list.toString());

            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                boolean exists = (list.getSize()==unsorted.length-(i+1));
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(list.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            // MATRIX
            System.out.println("Matrix.");
            Matrix<Integer> matrix1 = new Matrix<Integer>(4,3);
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
            
            Matrix<Integer> matrix2 = new Matrix<Integer>(3,2);
            matrix2.set(0, 0, 12);
            matrix2.set(0, 1, 25);
            matrix2.set(1, 0, 9);
            matrix2.set(1, 1, 10);
            matrix2.set(2, 0, 8);
            matrix2.set(2, 1, 5);

            System.out.println("Matrix multiplication.");
            Matrix<Integer> matrix3 = matrix1.multiply(matrix2);
            System.out.println(matrix3);
            
            int rows = 2;
            int cols = 2;
            int counter = 0;
            Matrix<Integer> matrix4 = new Matrix<Integer>(rows,cols);
            for (int r=0; r<rows; r++) {
                for (int c=0; c<cols; c++) {
                    matrix4.set(r, c, counter++);
                }
            }

            System.out.println("Matrix subtraction.");
            Matrix<Integer> matrix5 = matrix4.subtract(matrix4);
            System.out.println(matrix5);

            System.out.println("Matrix addition.");
            Matrix<Integer> matrix6 = matrix4.add(matrix4);
            System.out.println(matrix6);
            
            Matrix<Integer> matrix7 = new Matrix<Integer>(2,2);
            matrix7.set(0, 0, 1);
            matrix7.set(0, 1, 2);
            matrix7.set(1, 0, 3);
            matrix7.set(1, 1, 4);
            
            Matrix<Integer> matrix8 = new Matrix<Integer>(2,2);
            matrix8.set(0, 0, 1);
            matrix8.set(0, 1, 2);
            matrix8.set(1, 0, 3);
            matrix8.set(1, 1, 4);
            
            System.out.println("Matrix multiplication.");
            Matrix<Integer> matrix9 = matrix7.multiply(matrix8);
            System.out.println(matrix9);
        }

        {
            //Patricia Trie
            System.out.println("Patricia Trie.");
            
            PatriciaTrie<String> patriciaTrie = new PatriciaTrie<String>();
            for (int i : unsorted) {
                String string = String.valueOf(i);
                patriciaTrie.add(string);
                boolean exists = patriciaTrie.contains(string);
                if (!exists) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    System.err.println(patriciaTrie.toString());
                    return;
                }
            }
            System.out.println(patriciaTrie.toString());

            for (int i : unsorted) {
                String string = String.valueOf(i);
                patriciaTrie.remove(string);
                boolean exists = patriciaTrie.contains(string);
                if (exists) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    System.err.println(patriciaTrie.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            // Queue
            System.out.println("Queue.");
            Queue<Integer> queue = new Queue<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                queue.enqueue(item);
                boolean exists = (queue.getSize()==i+1);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(queue.toString());
                    return;
                }
            }
            System.out.println(queue.toString());

            int size = queue.getSize();
            for (int i=0; i<size; i++) {
                queue.dequeue();
                boolean exists = (queue.getSize()==unsorted.length-(i+1));
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(queue.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            //Radix Tree (map)
            System.out.println("Radix Tree (map).");
            
            RadixTree<String,Integer> radixTree = new RadixTree<String,Integer>();
            for (int i : unsorted) {
                String string = String.valueOf(i);
                radixTree.put(string, i);
                boolean exists = radixTree.contains(string);
                if (!exists) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    System.err.println(radixTree.toString());
                    return;
                }
            }
            System.out.println(radixTree.toString());

            for (int i : unsorted) {
                String string = String.valueOf(i);
                radixTree.remove(string);
                boolean exists = radixTree.contains(string);
                if (exists) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    System.err.println(radixTree.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            //Segment tree
            System.out.println("Segment Tree.");
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
            System.out.println();
        }

        {
            // SkipList
            System.out.println("Skip List.");
            SkipList<Integer> list = new SkipList<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
                boolean exists = (list.getSize()==i+1);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(list.toString());
                    return;
                }
            }
            System.out.println(list.toString());

            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                boolean exists = (list.getSize()==unsorted.length-(i+1));
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(list.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            //Splay Tree
            System.out.println("Splay Tree.");
            SplayTree<Character> splay = new SplayTree<Character>();

            String alphabet = new String("KLMUFGNRSTABHIJVWXYZCDEOPQ");
            for (int i=0; i<alphabet.length(); i++) {
                char c = alphabet.charAt(i);
                splay.add(c);
                boolean exists = splay.contains(c);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                }
            }
            System.out.println(splay.toString());

            int length = alphabet.length()-1;
            for (int i=0; i<=length; i++) {
                char letter = alphabet.charAt(length-i);
                //Moves up the tree
                splay.contains(letter);
                splay.contains(letter);
                splay.contains(letter);
                //Remove
                splay.remove(letter);
                boolean exists = splay.contains(letter);
                if (exists) {
                    System.err.println("YIKES!! "+i+" still exists.");
                }
            }

            System.out.println();
        }

        {
            // Stack
            System.out.println("Stack.");
            Stack<Integer> stack = new Stack<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                stack.push(item);
                boolean exists = (stack.getSize()==i+1);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(stack.toString());
                    return;
                }
            }
            System.out.println(stack.toString());

            int size = stack.getSize();
            for (int i=0; i<size; i++) {
                stack.pop();
                boolean exists = (stack.getSize()==unsorted.length-(i+1));
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(stack.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            //Suffix Tree
            System.out.println("Suffix Tree.");
            String bookkeeper = "bookkeeper";
            SuffixTree<String> suffixTree = new SuffixTree<String>(bookkeeper);

            System.out.println(suffixTree.toString());
            System.out.println(suffixTree.getSuffixes());

            boolean exist = suffixTree.doesSubStringExist(bookkeeper);
            System.out.println("Does "+bookkeeper+" exist in the Suffix Trie? "+exist);
            
            String failed = "booker";
            exist = suffixTree.doesSubStringExist(failed);
            System.out.println("Does "+failed+" exist in the Suffix Trie? "+exist);
            
            String pass = "kkee";
            exist = suffixTree.doesSubStringExist(pass);
            System.out.println("Does "+pass+" exist in the Suffix Trie? "+exist);

            System.out.println();
        }

        {
            //Suffix Trie
            System.out.println("Suffix Trie.");
            String bookkeeper = "bookkeeper";
            SuffixTrie<String> suffixTrie = new SuffixTrie<String>(bookkeeper);

            System.out.println(suffixTrie.toString());
            System.out.println(suffixTrie.getSuffixes());
            
            boolean exist = suffixTrie.doesSubStringExist(bookkeeper);
            System.out.println("Does "+bookkeeper+" exist in the Suffix Trie? "+exist);
            
            String failed = "booker";
            exist = suffixTrie.doesSubStringExist(failed);
            System.out.println("Does "+failed+" exist in the Suffix Trie? "+exist);
            
            String pass = "kkee";
            exist = suffixTrie.doesSubStringExist(pass);
            System.out.println("Does "+pass+" exist in the Suffix Trie? "+exist);

            System.out.println();
        }

        {
            //Treap
            System.out.println("Treap.");
        	Treap<Character> treap = new Treap<Character>();

        	String alphabet = new String("TVWXYABHIJKLMUFGNRSZCDEOPQ");
        	for (int i=0; i<alphabet.length(); i++) {
        	    char c = alphabet.charAt(i);
        	    treap.add(c);
                boolean exists = treap.contains(c);
                if (!exists) {
                    System.err.println("YIKES!! "+i+" doesn't exists.");
                    System.err.println(treap.toString());
                    return;
                }
        	}
            System.out.println(treap.toString());

            for (int i=0; i<alphabet.length(); i++) {
                char letter = alphabet.charAt(i);       
                treap.remove(letter);
                boolean exists = treap.contains(letter);
                if (exists) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    System.err.println(treap.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            //Trie.
            System.out.println("Trie.");
            
            Trie<String> trie = new Trie<String>();
            for (int i : unsorted) {
                String string = String.valueOf(i);
                trie.add(string);
                boolean exists = trie.contains(string);
                if (!exists) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    System.err.println(trie.toString());
                    return;
                }
            }
            System.out.println(trie.toString());

            for (int i : unsorted) {
                String string = String.valueOf(i);
                trie.remove(string);
                boolean exists = trie.contains(string);
                if (exists) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    System.err.println(trie.toString());
                    return;
                }
            }

            System.out.println();
        }

        {
            //Trie Map
            System.out.println("Trie Map.");
            TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
            for (int i : unsorted) {
                String string = String.valueOf(i);
                trieMap.put(string, i);
                boolean exists = trieMap.contains(string);
                if (!exists) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    System.err.println(trieMap.toString());
                    return;
                }
            }
            System.out.println(trieMap.toString());            

            for (int i : unsorted) {
                String string = String.valueOf(i);
                trieMap.remove(string);
                boolean exists = trieMap.contains(string);
                if (exists) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    System.err.println(trieMap.toString());
                    return;
                }
            }

            System.out.println();
        }
    }

    private static final String getPathMapString(Graph.Vertex<Integer> start, Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : map.keySet()) {
            Graph.CostPathPair<Integer> pair = map.get(v);
            builder.append("From ").append(start.getValue()).append(" to vertex=").append(v.getValue()).append("\n");
            if (pair!=null) builder.append(pair.toString()).append("\n");

        }
        return builder.toString();
    }

    private static final String getPathMapString(Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> paths) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : paths.keySet()) {
            Map<Vertex<Integer>, Set<Edge<Integer>>> map = paths.get(v);
            for (Graph.Vertex<Integer> v2 : map.keySet()) {
                builder.append("From=").append(v.getValue()).append(" to=").append(v2.getValue()).append("\n");
                Set<Graph.Edge<Integer>> path = map.get(v2);
                builder.append(path).append("\n");
            }
        }
        return builder.toString();
    }

    private static final String getWeightMapString(Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> paths) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : paths.keySet()) {
            Map<Vertex<Integer>, Integer> map = paths.get(v);
            for (Graph.Vertex<Integer> v2 : map.keySet()) {
                builder.append("From=").append(v.getValue()).append(" to=").append(v2.getValue()).append("\n");
                Integer weight = map.get(v2);
                builder.append(weight).append("\n");
            }
        }
        return builder.toString();
    }
}
