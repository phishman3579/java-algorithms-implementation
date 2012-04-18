package com.jwetherell.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.AVLTree;
import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
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

    private static final int NUMBER_OF_TESTS = 10;
    private static final Random RANDOM = new Random();
    private static final int ARRAY_SIZE = 1000;

    private static Integer[] unsorted = null;
    private static String string = null;

    private static int debug = 1; //debug level. 0=None, 1=Time and Memory (if enabled), 2=Time, Memory, data structure debug
    private static boolean debugTime = true; //How much time to: add all, remove all, add all items in reverse order, remove all
    private static boolean debugMemory = true; //How much memory is used by the data structure
    private static boolean validateStructure = true; //Is the data structure valid (passed invariants) and proper size
    private static boolean validateContents = true; //Was the item added/removed really added/removed from the structure

    private static final int TESTS = 17; //Number of dynamic data structures to test
    private static int test = 0;
    private static String[] testNames = new String[TESTS];
    private static long[][] testResults = new long[TESTS][];


    public static void main(String[] args) {
        System.out.println("Starting tests.");
        boolean passed = true;
        for (int i=0; i<NUMBER_OF_TESTS; i++) {
            passed = runTests();
            if (!passed) break;
        }
        if (passed) System.out.println("Tests finished. All passed.");
        else System.err.println("Tests finished. Detected a failure.");
    }
    
    private static boolean runTests() {

        test = 0;
        
        System.out.println("Generating data.");
        StringBuilder builder = new StringBuilder();
        builder.append("Array=");
        unsorted = new Integer[ARRAY_SIZE];
        for (int i=0; i<unsorted.length; i++) {
            Integer j = RANDOM.nextInt(unsorted.length*10);
            //Make sure there are no duplicates
            boolean found = true;
            while (found) {
                found = false;
                for (int k=0; k<i; k++) {
                    int l = unsorted[k];
                    if (j==l) {
                        found = true;
                        j = RANDOM.nextInt(unsorted.length*10);
                        break;
                    }
                }
            }
            unsorted[i] = j;
            builder.append(j).append(',');
        }
        builder.append('\n');
        string = builder.toString();
        if (debug>1) System.out.println(string);
        System.out.println("Generated data.");

        boolean passed = true;

        passed = testAVLTree();
        if (!passed) {
            System.err.println("AVL Tree failed.");
            return false;
        }

        passed = testBST();
        if (!passed) {
            System.err.println("BST failed.");
            return false;
        }

        passed = testHeap();
        if (!passed) {
            System.err.println("Heap failed.");
            return false;
        }

        passed = testHashMap();
        if (!passed) {
            System.err.println("Hash Map failed.");
            return false;
        }

        passed = testLinkedList();
        if (!passed) {
            System.err.println("Linked List failed.");
            return false;
        }

        passed = testPatriciaTrie();
        if (!passed) {
            System.err.println("Patricia Trie failed.");
            return false;
        }

        passed = testQueue();
        if (!passed) {
            System.err.println("Queue failed.");
            return false;
        }

        passed = testRadixTree();
        if (!passed) {
            System.err.println("Radix Tree failed.");
            return false;
        }

        passed = testSkipList();
        if (!passed) {
            System.err.println("Skip List failed.");
            return false;
        }

        passed = testSplayTree();
        if (!passed) {
            System.err.println("Splay Tree failed.");
            return false;
        }

        passed = testStack();
        if (!passed) {
            System.err.println("Stack failed.");
            return false;
        }

        passed = testTreap();
        if (!passed) {
            System.err.println("Treap failed.");
            return false;
        }

        passed = testTrie();
        if (!passed) {
            System.err.println("Trie failed.");
            return false;
        }

        passed = testTrieMap();
        if (!passed) {
            System.err.println("Trie Map failed.");
            return false;
        }

        if (debugTime && debugMemory) {
            String results = getTestResults(testNames,testResults);
            System.out.println(results);
        }
        
        //STATIC DATA STRUCTURES

        passed = testGraph();
        if (!passed) {
            System.err.println("Graph failed.");
            return false;
        }

        passed = testMatrix();
        if (!passed) {
            System.err.println("Matrix failed.");
            return false;
        }

        passed = testSegmentTree();
        if (!passed) {
            System.err.println("Segment Tree failed.");
            return false;
        }

        passed = testSuffixTree();
        if (!passed) {
            System.err.println("Suffix Tree failed.");
            return false;
        }

        passed = testSuffixTrie();
        if (!passed) {
            System.err.println("Suffix Trie failed.");
            return false;
        }

        return true;
    }

    private static void handleError(Object obj) {
        System.err.println(string);
        System.err.println(obj.toString());
    }
    
    private static boolean testAVLTree() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //AVL Tree
            if (debug>1) System.out.println("AVL Tree");
            testNames[test] = "AVL Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            AVLTree<Integer> tree = new AVLTree<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                tree.add(item);
                if (validateStructure && !(tree.getSize()==(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("AVL Tree add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("AVL Tree memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(tree.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
                if (validateStructure && !(tree.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("AVL Tree remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                tree.add(item);
                if (validateStructure && !(tree.getSize()==(unsorted.length-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("AVL Tree add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("AVL Tree memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(tree.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
                if (validateStructure && !(tree.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("AVL Tree remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }

    private static boolean testBST() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // BINARY SEARCH TREE (first)
            if (debug>1) System.out.println("Binary search tree with first node.");
            testNames[test] = "BST (first)";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(unsorted,BinarySearchTree.TYPE.FIRST);
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("BST (first) add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("BST (first) memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(bst.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("BST (first) remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                bst.add(item);
                if (validateStructure && !(bst.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("BST (first) add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("BST (first) memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(bst.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("BST (first) remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }

        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // BINARY SEARCH TREE (middle)
            if (debug>1) System.out.println("Binary search tree with middle node.");
            testNames[test] = "BST (middle)";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(unsorted,BinarySearchTree.TYPE.MIDDLE);
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("BST (middle) add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("BST (middle) memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(bst.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("BST (middle) remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                bst.add(item);
                if (validateStructure && !(bst.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("BST (middle) add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("BST (middle) memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(bst.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("BST (middle) remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }

        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // BINARY SEARCH TREE (random)
            if (debug>1) System.out.println("Binary search tree with random node.");
            testNames[test] = "BST (random)";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(unsorted,BinarySearchTree.TYPE.RANDOM);
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("BST (random) add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("BST (random) memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(bst.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("BST (random) remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                bst.add(item);
                if (validateStructure && !(bst.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("BST (random) add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("BST (random) memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(bst.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! "+i+" still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("BST (random) remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }

        return true;
    }

    private static boolean testGraph() {
        {
            // UNDIRECTED GRAPH
            if (debug>1) System.out.println("Undirected Graph.");
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
            if (debug>1) System.out.println(undirected.toString());
            
            Graph.Vertex<Integer> start = v1;
            if (debug>1) System.out.println("Dijstra's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(undirected, start);
            if (debug>1) System.out.println(getPathMapString(start,map1));

            Graph.Vertex<Integer> end = v5;
            if (debug>1) System.out.println("Dijstra's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(undirected, start, end);
            if (debug>1) {
                if (pair1!=null) System.out.println(pair1.toString());
                else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            }

            start = v1;
            if (debug>1) System.out.println("Bellman-Ford's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(undirected, start);
            if (debug>1) System.out.println(getPathMapString(start,map2));

            end = v5;
            if (debug>1) System.out.println("Bellman-Ford's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(undirected, start, end);
            if (debug>1) {
                if (pair2!=null) System.out.println(pair2.toString());
                else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            }

            if (debug>1) System.out.println("Prim's minimum spanning tree of the undirected graph from "+start.getValue());
            Graph.CostPathPair<Integer> pair = Prim.getMinimumSpanningTree(undirected, start);
            if (debug>1) System.out.println(pair.toString());

            if (debug>1) System.out.println();
        }

        {
            // DIRECTED GRAPH
            if (debug>1) System.out.println("Directed Graph.");
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
            if (debug>1) System.out.println(directed.toString());
            
            Graph.Vertex<Integer> start = v1;
            if (debug>1) System.out.println("Dijstra's shortest paths of the directed graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = Dijkstra.getShortestPaths(directed, start);
            if (debug>1) System.out.println(getPathMapString(start,map));

            Graph.Vertex<Integer> end = v5;
            if (debug>1) System.out.println("Dijstra's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair = Dijkstra.getShortestPath(directed, start, end);
            if (debug>1) {
                if (pair!=null) System.out.println(pair.toString());
                else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            }
            
            start = v1;
            if (debug>1) System.out.println("Bellman-Ford's shortest paths of the undirected graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed, start);
            if (debug>1) System.out.println(getPathMapString(start,map2));

            end = v5;
            if (debug>1) System.out.println("Bellman-Ford's shortest path of the undirected graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (debug>1) {
                if (pair2!=null) System.out.println(pair2.toString());
                else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            }

            if (debug>1) System.out.println();
        }

        {
            // DIRECTED GRAPH (WITH NEGATIVE WEIGHTS)
            if (debug>1) System.out.println("Undirected Graph with Negative Weights.");
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
            if (debug>1) System.out.println(directed.toString());

            Graph.Vertex<Integer> start = v1;
            if (debug>1) System.out.println("Bellman-Ford's shortest paths of the directed graph from "+start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed, start);
            if (debug>1) System.out.println(getPathMapString(start,map2));

            Graph.Vertex<Integer> end = v3;
            if (debug>1) System.out.println("Bellman-Ford's shortest path of the directed graph from "+start.getValue()+" to "+end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (debug>1) {
                if (pair2!=null) System.out.println(pair2.toString());
                else System.out.println("No path from "+start.getValue()+" to "+end.getValue());
            }

            if (debug>1) System.out.println("Johnson's all-pairs shortest path of the directed graph.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> paths = Johnson.getAllPairsShortestPaths(directed);
            if (debug>1) {
                if (paths==null) System.out.println("Directed graph contains a negative weight cycle.");
                else System.out.println(getPathMapString(paths));
            }

            if (debug>1) System.out.println("Floyd-Warshall's all-pairs shortest path weights of the directed graph.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> pathWeights = FloydWarshall.getAllPairsShortestPaths(directed);
            if (debug>1) System.out.println(getWeightMapString(pathWeights));

            if (debug>1) System.out.println();
        }

        {
            // UNDIRECTED GRAPH
            if (debug>1) System.out.println("Undirected Graph cycle check.");
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
            if (debug>1) System.out.println(undirectedWithCycle.toString());

            if (debug>1) {
                System.out.println("Cycle detection of the undirected graph.");
                boolean result = CycleDetection.detect(undirectedWithCycle);
                System.out.println("result="+result);
                System.out.println();
            }

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
            if (debug>1) System.out.println(undirectedWithoutCycle.toString());

            if (debug>1) {
                System.out.println("Cycle detection of the undirected graph.");
                boolean result = CycleDetection.detect(undirectedWithoutCycle);
                System.out.println("result="+result);
                System.out.println();
            }
        }

        {
            // DIRECTED GRAPH
            if (debug>1) System.out.println("Directed Graph topological sort.");
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
            if (debug>1) System.out.println(directed.toString());

            if (debug>1) System.out.println("Topological sort of the directed graph.");
            List<Graph.Vertex<Integer>> results = TopologicalSort.sort(directed);
            if (debug>1) {
                System.out.println("result="+results);
                System.out.println();
            }
        }
        
        return true;
    }

    private static boolean testHeap() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // MIN-HEAP
            if (debug>1) System.out.println("Min-Heap.");
            testNames[test] = "Min-heap";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            BinaryHeap<Integer> minHeap = new BinaryHeap<Integer>(BinaryHeap.TYPE.MIN);
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=0;  i<unsorted.length; i++) {
                int item = unsorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Min-Heap add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Min-Heap memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(minHeap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = minHeap.removeRoot();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (validateStructure && minHeap.getRootValue()!=null) {
                System.err.println("YIKES!! heap isn't empty.");
                handleError(minHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Min-Heap remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Min-Heap add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Min-Heap memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(minHeap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = minHeap.removeRoot();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (validateStructure && minHeap.getRootValue()!=null) {
                System.err.println("YIKES!! heap isn't empty.");
                handleError(minHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Min-Heap remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }

        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // MIN-HEAP
            if (debug>1) System.out.println("Max-Heap.");
            testNames[test] = "Max-heap";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinaryHeap<Integer> maxHeap = new BinaryHeap<Integer>(BinaryHeap.TYPE.MAX);
            for (int i=0;  i<unsorted.length; i++) {
                int item = unsorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Max-Heap add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Max-Heap memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(maxHeap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = maxHeap.removeRoot();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (validateStructure && maxHeap.getRootValue()!=null) {
                System.err.println("YIKES!! heap isn't empty.");
                handleError(maxHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Max-Heap remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Max-Heap add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Max-Heap memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(maxHeap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = maxHeap.removeRoot();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Heap isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (validateStructure && maxHeap.getRootValue()!=null) {
                System.err.println("YIKES!! heap isn't empty.");
                handleError(maxHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Max-Heap remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }

        return true;
    }

    private static boolean testHashMap() {
        int key = unsorted.length/2;
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // Hash Map            
            if (debug>1) System.out.println("Hash Map.");
            testNames[test] = "Hash Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>(key);
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                hash.put(item, item);
                if (validateStructure && !(hash.getSize()==(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && !hash.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Hash Map add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Hash Map memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(hash.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                hash.remove(item);
                if (validateStructure && !(hash.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && hash.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Hash Map remove time = "+removeTime/count+" ms");
            }

            count++;
            
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                hash.put(item,item);
                if (validateStructure && !(hash.getSize()==(unsorted.length-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && !hash.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Hash Map add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Hash Map memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(hash.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                hash.remove(item);
                if (validateStructure && !(hash.getSize()==i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && hash.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Hash Map remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testLinkedList() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // Linked List
            if (debug>1) System.out.println("Linked List.");
            testNames[test] = "Linked List";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            LinkedList<Integer> list = new LinkedList<Integer>();
            for (int i=0;  i<unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Linked List add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Linked List memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(list.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Linked List remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                list.add(item);
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Linked List add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Linked List memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(list.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Linked List remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }

        return true;
    }
    
    private static boolean testMatrix() {
        {
            // MATRIX
            if (debug>1) System.out.println("Matrix.");
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

            if (debug>1) System.out.println("Matrix multiplication.");
            Matrix<Integer> matrix3 = matrix1.multiply(matrix2);
            if (debug>1) System.out.println(matrix3);
            
            int rows = 2;
            int cols = 2;
            int counter = 0;
            Matrix<Integer> matrix4 = new Matrix<Integer>(rows,cols);
            for (int r=0; r<rows; r++) {
                for (int c=0; c<cols; c++) {
                    matrix4.set(r, c, counter++);
                }
            }

            if (debug>1) System.out.println("Matrix subtraction.");
            Matrix<Integer> matrix5 = matrix4.subtract(matrix4);
            if (debug>1) System.out.println(matrix5);

            if (debug>1) System.out.println("Matrix addition.");
            Matrix<Integer> matrix6 = matrix4.add(matrix4);
            if (debug>1) System.out.println(matrix6);
            
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
            
            if (debug>1) System.out.println("Matrix multiplication.");
            Matrix<Integer> matrix9 = matrix7.multiply(matrix8);
            if (debug>1) System.out.println(matrix9);
        }
        
        return true;
    }
    
    private static boolean testPatriciaTrie() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //Patricia Trie
            if (debug>1) System.out.println("Patricia Trie.");
            testNames[test] = "Patricia Trie";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            PatriciaTrie<String> trie = new PatriciaTrie<String>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.getSize()==(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Patricia Trie add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Patricia Trie memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(trie.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Patricia Trie remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.getSize()==(unsorted.length-i))) {
                    System.err.println("YIKES!! "+string+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Patricia Trie add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Patricia Trie memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(trie.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+string+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Patricia Trie remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testQueue() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // Queue
            if (debug>1) System.out.println("Queue.");
            testNames[test] = "Queue";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Queue<Integer> queue = new Queue<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Queue add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Queue memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(queue.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = queue.getSize();
            for (int i=0; i<size; i++) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Queue remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Queue add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Queue memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(queue.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Queue remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testRadixTree() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //Radix Tree (map)
            if (debug>1) System.out.println("Radix Tree (map).");
            testNames[test] = "Radix Tree (map)";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            RadixTree<String,Integer> tree = new RadixTree<String,Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.put(string, i);
                if (validateStructure && !(tree.getSize()==(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Radix Tree add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Radix Tree memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(tree.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.remove(string);
                if (validateStructure && !(tree.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Radix Tree remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.put(string, i);
                if (validateStructure && !(tree.getSize()==(unsorted.length-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Radix Tree add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Radix Tree memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(tree.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.remove(string);
                if (validateStructure && !(tree.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Radix Tree remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testSegmentTree() {
        {
            //Segment tree
            if (debug>1) System.out.println("Segment Tree.");
            SegmentTree.Segment[] segments = new SegmentTree.Segment[4];
            segments[0] = new SegmentTree.Segment(0,1,0,0,0); //first point in the 0th quadrant
            segments[1] = new SegmentTree.Segment(1,0,1,0,0); //second point in the 1st quadrant
            segments[2] = new SegmentTree.Segment(2,0,0,1,0); //third point in the 2nd quadrant
            segments[3] = new SegmentTree.Segment(3,0,0,0,1); //fourth point in the 3rd quadrant
            SegmentTree tree = new SegmentTree(segments);
            
            SegmentTree.Query query = tree.query(0, 3);
            if (debug>1) System.out.println(query.quad1+" "+query.quad2+" "+query.quad3+" "+query.quad4);

            tree.update(1, 0, -1, 1, 0); //Move the first point from quadrant one to quadrant two
            tree.update(2, 0, 1, -1, 0); //Move the second point from quadrant two to quadrant one
            tree.update(3, 1, 0, 0, -1); //Move the third point from quadrant third to quadrant zero
            
            query = tree.query(2, 3);
            if (debug>1) System.out.println(query.quad1+" "+query.quad2+" "+query.quad3+" "+query.quad4);

            tree.update(0, -1, 1, 0, 0); //Move the zeroth point from quadrant zero to quadrant one
            tree.update(1, 0, 0, -1, 1); //Move the first point from quadrant three to quadrant four
            
            query = tree.query(0, 2);
            if (debug>1) System.out.println(query.quad1+" "+query.quad2+" "+query.quad3+" "+query.quad4);

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testSkipList() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // SkipList
            if (debug>1) System.out.println("Skip List.");
            testNames[test] = "Skip List";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            SkipList<Integer> list = new SkipList<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
                if (validateStructure && !(list.getSize()==(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exist.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Skip List add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Skip List memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(list.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateStructure && !(list.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exist.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Skip List remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                list.add(item);
                if (validateStructure && !(list.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exist.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Skip List add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Skip List memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(list.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateStructure && !(list.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Skip List remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testSplayTree() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //Splay Tree
            if (debug>1) System.out.println("Splay Tree.");
            testNames[test] = "Splay Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            SplayTree<Integer> splay = new SplayTree<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                splay.add(item);
                if (validateStructure && !(splay.getSize()==(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && !splay.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Splay Tree add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Splay Tree memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(splay.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                splay.remove(item);
                if (validateStructure && !(splay.getSize()==((unsorted.length-1)-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && splay.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Splay Tree remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                splay.add(item);
                if (validateStructure && !(splay.getSize()==(unsorted.length-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && !splay.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Splay Tree add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Splay Tree memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(splay.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                splay.remove(item);
                if (validateStructure && !(splay.getSize()==((unsorted.length-1)-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && splay.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Splay Tree remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testStack() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            // Stack
            if (debug>1) System.out.println("Stack.");
            testNames[test] = "Stack";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Stack<Integer> stack = new Stack<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(stack);
                    return false;                
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Stack add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Stack memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(stack.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = stack.getSize();
            for (int i=0; i<size; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Stack remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Stack add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Stack memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(stack.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Stack remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testSuffixTree() {
        {
            //Suffix Tree
            if (debug>1) System.out.println("Suffix Tree.");
            String bookkeeper = "bookkeeper";
            SuffixTree<String> tree = new SuffixTree<String>(bookkeeper);
            if (debug>1) System.out.println(tree.toString());
            if (debug>1) System.out.println(tree.getSuffixes());

            boolean exists = tree.doesSubStringExist(bookkeeper);
            if (!exists) {
                System.err.println("YIKES!! "+bookkeeper+" doesn't exists.");
                handleError(tree);
                return false;                
            }
            
            String failed = "booker";
            exists = tree.doesSubStringExist(failed);
            if (exists) {
                System.err.println("YIKES!! "+failed+" exists.");
                handleError(tree);
                return false;                
            }

            String pass = "kkee";
            exists = tree.doesSubStringExist(pass);
            if (!exists) {
                System.err.println("YIKES!! "+pass+" doesn't exists.");
                handleError(tree);
                return false;                
            }

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testSuffixTrie() {
        {
            //Suffix Trie
            if (debug>1) System.out.println("Suffix Trie.");
            String bookkeeper = "bookkeeper";
            SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
            if (debug>1) System.out.println(trie.toString());
            if (debug>1) System.out.println(trie.getSuffixes());

            boolean exists = trie.doesSubStringExist(bookkeeper);
            if (!exists) {
                System.err.println("YIKES!! "+bookkeeper+" doesn't exists.");
                handleError(trie);
                return false;                
            }
            
            String failed = "booker";
            exists = trie.doesSubStringExist(failed);
            if (exists) {
                System.err.println("YIKES!! "+failed+" exists.");
                handleError(trie);
                return false;                
            }

            String pass = "kkee";
            exists = trie.doesSubStringExist(pass);
            if (!exists) {
                System.err.println("YIKES!! "+pass+" doesn't exists.");
                handleError(trie);
                return false;                
            }

            if (debug>1) System.out.println();
        }
        
        return true;
    }

    private static boolean testTreap() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //Treap
            if (debug>1) System.out.println("Treap.");
            testNames[test] = "Treap";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Treap<Integer> treap = new Treap<Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                treap.add(item);
                if (validateStructure && !(treap.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.1");
                    handleError(treap);
                    return false;
                }
                if (validateContents && !treap.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(treap);
                    return false;     
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Treap add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Treap memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(treap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];       
                treap.remove(item);
                if (validateStructure && !(treap.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.2");
                    handleError(treap);
                    return false;
                }
                if (validateContents && treap.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(treap);
                    return false;     
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Treap remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                treap.add(item);
                if (validateStructure && !(treap.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.3");
                    handleError(treap);
                    return false;
                }
                if (validateContents && !treap.contains(item)) {
                    System.err.println("YIKES!! "+item+" doesn't exists.");
                    handleError(treap);
                    return false;     
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Treap add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Treap memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(treap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];       
                treap.remove(item);
                if (validateStructure && !(treap.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.4");
                    handleError(treap);
                    return false;
                }
                if (validateContents && treap.contains(item)) {
                    System.err.println("YIKES!! "+item+" still exists.");
                    handleError(treap);
                    return false;     
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Treap remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testTrie() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //Trie.
            if (debug>1) System.out.println("Trie.");
            testNames[test] = "Trie";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Trie<String> trie = new Trie<String>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Trie add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Trie memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(trie.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Trie remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.getSize()==unsorted.length-i)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Trie add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Trie memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(trie.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.getSize()==unsorted.length-(i+1))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Trie remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }
    
    private static boolean testTrieMap() {
        {
            long count = 0;

            long addTime = 0L;
            long removeTime = 0L;
            long beforeAddTime = 0L;
            long afterAddTime = 0L;
            long beforeRemoveTime = 0L;
            long afterRemoveTime = 0L;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;

            //Trie Map
            if (debug>1) System.out.println("Trie Map.");
            testNames[test] = "Trie Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, i);
                if (validateStructure && !(trieMap.getSize()==i+1)) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && !trieMap.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Trie Map add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Trie Map memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(trieMap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
                if (validateStructure && !(trieMap.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && trieMap.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Trie Map remove time = "+removeTime/count+" ms");
            }

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i=unsorted.length-1; i>=0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, i);
                if (validateStructure && !(trieMap.getSize()==(unsorted.length-i))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && !trieMap.contains(string)) {
                    System.err.println("YIKES!! "+string+" doesn't exist.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime-beforeAddTime;
                if (debug>0) System.out.println("Trie Map add time = "+addTime/count+" ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory-beforeMemory;
                if (debug>0) System.out.println("Trie Map memory use = "+(memory/count)+" bytes");
            }

            if (debug>1) System.out.println(trieMap.toString());

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i=0; i<unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
                if (validateStructure && !(trieMap.getSize()==(unsorted.length-(i+1)))) {
                    System.err.println("YIKES!! "+item+" caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && trieMap.contains(string)) {
                    System.err.println("YIKES!! "+string+" still exists.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime-beforeRemoveTime;
                if (debug>0) System.out.println("Trie Map remove time = "+removeTime/count+" ms");
            }

            testResults[test++]= new long[]{addTime,removeTime,memory};

            if (debug>1) System.out.println();
        }
        
        return true;
    }

    private static final String getTestResults(String[] names, long[][] results) {
        StringBuilder resultsBuilder = new StringBuilder();

        int KB = 1000;
        int MB = 1000*KB;
        
        long SECOND = 1000;
        long MINUTES = 60*SECOND;

        resultsBuilder.append("Data Structure      ").append("\t");
        resultsBuilder.append("Add time").append("\t").append("Remove time").append("\t").append("Size");
        resultsBuilder.append("\n");
        for (int i=0; i<TESTS; i++) {
            String name = names[i];
            long[] result = results[i];
            if (name!=null && result!=null) {
                if (name.length()<20) {
                    StringBuilder nameBuilder = new StringBuilder(name);
                    for (int j=name.length(); j<20; j++) {
                        nameBuilder.append(" ");
                    }
                    name = nameBuilder.toString();
                }
                resultsBuilder.append(name).append("\t");

                long size = result[2];
                String sizeString = null;
                if (size>MB) {
                    size = size/MB;
                    sizeString = size+" MB";
                } else if (size>KB) {
                    size = size/KB;
                    sizeString = size+" KB";
                } else {
                    sizeString = size+" Bytes";
                }

                long addTime = result[0];
                String addTimeString = null;
                if (addTime>MINUTES) {
                    addTime = addTime/MINUTES;
                    addTimeString = addTime+" mins";
                } else if (addTime>SECOND) {
                    addTime = addTime/SECOND;
                    addTimeString = addTime+" secs";
                } else {
                    addTimeString = addTime+" ms";
                }

                long removeTime = result[1];
                String removeTimeString = null;
                if (removeTime>MINUTES) {
                    removeTime = removeTime/MINUTES;
                    removeTimeString = removeTime+" mins";
                } else if (removeTime>SECOND) {
                    removeTime = removeTime/SECOND;
                    removeTimeString = removeTime+" secs";
                } else {
                    removeTimeString = removeTime+" ms";
                }

                resultsBuilder.append(addTimeString).append("\t\t");
                resultsBuilder.append(removeTimeString).append("\t\t");
                resultsBuilder.append(sizeString);
                resultsBuilder.append("\n");
            }
        }
        
        return resultsBuilder.toString();
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

    private static final long getMemoryUse() {
        putOutTheGarbage();
        long totalMemory = Runtime.getRuntime().totalMemory();

        putOutTheGarbage();
        long freeMemory = Runtime.getRuntime().freeMemory();

        return (totalMemory - freeMemory);
    }

    private static final void putOutTheGarbage() {
        collectGarbage();
        collectGarbage();
    }
    
    private static final long fSLEEP_INTERVAL = 100;
    private static final void collectGarbage() {
        try {
            System.gc();
            Thread.sleep(fSLEEP_INTERVAL);
            System.runFinalization();
            Thread.sleep(fSLEEP_INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
