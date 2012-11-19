package com.jwetherell.algorithms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.jwetherell.algorithms.data_structures.AVLTree;
import com.jwetherell.algorithms.data_structures.BTree;
import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.CompactSuffixTrie;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.IntervalTree;
import com.jwetherell.algorithms.data_structures.KdTree;
import com.jwetherell.algorithms.data_structures.PatriciaTrie;
import com.jwetherell.algorithms.data_structures.RadixTrie;
import com.jwetherell.algorithms.data_structures.RedBlackTree;
import com.jwetherell.algorithms.data_structures.SegmentTree;
import com.jwetherell.algorithms.data_structures.SegmentTree.DynamicSegmentTree;
import com.jwetherell.algorithms.data_structures.SegmentTree.FlatSegmentTree;
import com.jwetherell.algorithms.data_structures.SuffixTree;
import com.jwetherell.algorithms.data_structures.TreeMap;
import com.jwetherell.algorithms.data_structures.TrieMap;
import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.Matrix;
import com.jwetherell.algorithms.data_structures.Queue;
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

    private static final int NUMBER_OF_TESTS = 3;
    private static final Random RANDOM = new Random();
    private static final int ARRAY_SIZE = 1000;
    private static final int RANDOM_SIZE = 1000 * ARRAY_SIZE;
    private static final Integer INVALID = RANDOM_SIZE + 10;
    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");

    private static Integer[] unsorted = null;
    private static Integer[] sorted = null;
    private static String string = null;

    private static int debug = 1; // Debug level. 0=None, 1=Time and Memory (if
                                  // enabled), 2=Time, Memory, data structure
                                  // debug
    private static boolean debugTime = true; // How much time to: add all,
                                             // remove all, add all items in
                                             // reverse order, remove all
    private static boolean debugMemory = true; // How much memory is used by the
                                               // data structure
    private static boolean validateStructure = true; // Is the data structure
                                                     // valid (passed
                                                     // invariants) and proper
                                                     // size
    private static boolean validateContents = true; // Was the item
                                                    // added/removed really
                                                    // added/removed from the
                                                    // structure

    private static final int TESTS = 33; // Max number of dynamic data
                                         // structures to test
    private static final String[] testNames = new String[TESTS]; // Array to
                                                                 // hold the
                                                                 // test names
    private static final long[][] testResults = new long[TESTS][]; // Array to
                                                                   // hold the
                                                                   // test
                                                                   // results
    private static int testIndex = 0; // Index into the tests
    private static int testNumber = 0; // Number of aggregate tests which have
                                       // been run

    public static void main(String[] args) {
        System.out.println("Starting tests.");
        boolean passed = true;
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            passed = runTests();
            if (!passed) break;
        }
        if (passed) System.out.println("Tests finished. All passed.");
        else System.err.println("Tests finished. Detected a failure.");
    }

    private static boolean runTests() {

        testIndex = 0;
        testNumber++;

        System.out.println("Generating data.");
        StringBuilder builder = new StringBuilder();
        builder.append("Array=");
        unsorted = new Integer[ARRAY_SIZE];
        java.util.Set<Integer> set = new java.util.HashSet<Integer>();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            Integer j = RANDOM.nextInt(RANDOM_SIZE);

            // Make sure there are no duplicates
            boolean found = true;
            while (found) {
                if (set.contains(j)) {
                    j = RANDOM.nextInt(RANDOM_SIZE);
                } else {
                    unsorted[i] = j;
                    set.add(j);
                    found = false;
                }
            }

            unsorted[i] = j;
            builder.append(j).append(',');
        }
        builder.append('\n');
        string = builder.toString();
        if (debug > 1) System.out.println(string);

        sorted = Arrays.copyOf(unsorted, unsorted.length);
        Arrays.sort(sorted);

        System.out.println("Generated data.");

        boolean passed = true;

        // MY DYNAMIC DATA STRUCTURES

        passed = testAVLTree();
        if (!passed) {
            System.err.println("AVL Tree failed.");
            return false;
        }

        passed = testBTree();
        if (!passed) {
            System.err.println("B-Tree failed.");
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

        passed = testList();
        if (!passed) {
            System.err.println("List failed.");
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

        passed = testRadixTrie();
        if (!passed) {
            System.err.println("Radix Trie failed.");
            return false;
        }

        passed = testRedBlackTree();
        if (!passed) {
            System.err.println("Red-Black Tree failed.");
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

        passed = testTreeMap();
        if (!passed) {
            System.err.println("Tree Map failed.");
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

        // JAVA DATA STRUCTURES

        passed = testJavaHeap();
        if (!passed) {
            System.err.println("Java Heap failed.");
            return false;
        }

        passed = testJavaHashMap();
        if (!passed) {
            System.err.println("Java Hash Map failed.");
            return false;
        }

        passed = testJavaList();
        if (!passed) {
            System.err.println("Java List failed.");
            return false;
        }

        passed = testJavaQueue();
        if (!passed) {
            System.err.println("Java Queue failed.");
            return false;
        }

        passed = testJavaRedBlackTree();
        if (!passed) {
            System.err.println("Java Red-Black failed.");
            return false;
        }

        passed = testJavaStack();
        if (!passed) {
            System.err.println("Java Stack failed.");
            return false;
        }

        passed = testJavaTreeMap();
        if (!passed) {
            System.err.println("Java Tree Map failed.");
            return false;
        }

        if (debugTime && debugMemory) {
            String results = getTestResults(testNumber, testNames, testResults);
            System.out.println(results);
        }

        // MY STATIC DATA STRUCTURES

        passed = testCompactSuffixTrie();
        if (!passed) {
            System.err.println("Compact Suffix Trie failed.");
            return false;
        }

        passed = testGraph();
        if (!passed) {
            System.err.println("Graph failed.");
            return false;
        }

        passed = testIntervalTree();
        if (!passed) {
            System.err.println("Interval Tree failed.");
            return false;
        }

        passed = testKdTree();
        if (!passed) {
            System.err.println("k-d Tree Tree failed.");
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

            // AVL Tree
            if (debug > 1) System.out.println("AVL Tree");
            testNames[testIndex] = "AVL Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            AVLTree<Integer> tree = new AVLTree<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.add(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! AVL Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("AVL Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("AVL Tree memory use = " + (memory / count) + " bytes");
            }

            boolean contains = tree.contains(INVALID);
            boolean removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("AVL Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! AVL Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("AVL Tree remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                tree.add(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! AVL Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("AVL Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("AVL Tree memory use = " + (memory / count) + " bytes");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("AVL Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! AVL Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("AVL Tree remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                tree.add(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! AVL Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("AVL Tree add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("AVL Tree memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("AVL Tree lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                tree.remove(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! AVL Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("AVL Tree remove time = " + removeSortedTime + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("AVL Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testBTree() {
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

            // B-Tree
            if (debug > 1) System.out.println("B-Tree with node.");
            testNames[testIndex] = "B-Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BTree<Integer> bTree = new BTree<Integer>(4); // 2-3-4 Tree
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                bTree.add(item);
                if (validateStructure && !bTree.validate()) {
                    System.err.println("YIKES!! B-Tree isn't valid.");
                    handleError(bTree);
                    return false;
                }
                if (validateStructure && !(bTree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bTree);
                    return false;
                }
                if (validateContents && !bTree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(bTree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("B-Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("B-Tree memory use = " + (memory / count) + " bytes");
            }

            boolean contains = bTree.contains(INVALID);
            boolean removed = bTree.remove(INVALID);
            if (contains || removed) {
                System.err.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(bTree.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                boolean found = bTree.contains(item);
                if (!found) return false;
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("B-Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                bTree.remove(item);
                if (validateStructure && !bTree.validate()) {
                    System.err.println("YIKES!! B-Tree isn't valid.");
                    handleError(bTree);
                    return false;
                }
                if (validateStructure && !(bTree.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bTree);
                    return false;
                }
                if (validateContents && bTree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(bTree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("B-Tree remove time = " + removeTime / count + " ms");
            }

            contains = bTree.contains(INVALID);
            removed = bTree.remove(INVALID);
            if (contains || removed) {
                System.err.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                bTree.add(item);
                if (validateStructure && !bTree.validate()) {
                    System.err.println("YIKES!! B-Tree isn't valid.");
                    handleError(bTree);
                    return false;
                }
                if (validateStructure && !(bTree.size() == sorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bTree);
                    return false;
                }
                if (validateContents && !bTree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(bTree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("B-Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("B-Tree memory use = " + (memory / count) + " bytes");
            }

            contains = bTree.contains(INVALID);
            removed = bTree.remove(INVALID);
            if (contains || removed) {
                System.err.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(bTree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                bTree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("B-Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                bTree.remove(item);
                if (validateStructure && !bTree.validate()) {
                    System.err.println("YIKES!! B-Tree isn't valid.");
                    handleError(bTree);
                    return false;
                }
                if (validateStructure && !(bTree.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bTree);
                    return false;
                }
                if (validateContents && bTree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(bTree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("B-Tree remove time = " + removeTime / count + " ms");
            }

            contains = bTree.contains(INVALID);
            removed = bTree.remove(INVALID);
            if (contains || removed) {
                System.err.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                bTree.add(item);
                if (validateStructure && !bTree.validate()) {
                    System.err.println("YIKES!! B-Tree isn't valid.");
                    handleError(bTree);
                    return false;
                }
                if (validateStructure && !(bTree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bTree);
                    return false;
                }
                if (validateContents && !bTree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(bTree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("B-Tree add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("B-Tree memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = bTree.contains(INVALID);
            removed = bTree.remove(INVALID);
            if (contains || removed) {
                System.err.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(bTree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                bTree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("B-Tree lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                bTree.remove(item);
                if (validateStructure && !bTree.validate()) {
                    System.err.println("YIKES!! B-Tree isn't valid.");
                    handleError(bTree);
                    return false;
                }
                if (validateStructure && !(bTree.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bTree);
                    return false;
                }
                if (validateContents && bTree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(bTree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("B-Tree remove time = " + removeSortedTime + " ms");
            }

            contains = bTree.contains(INVALID);
            removed = bTree.remove(INVALID);
            if (contains || removed) {
                System.err.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("B-Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();

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

            // BINARY SEARCH TREE
            if (debug > 1) System.out.println("Binary search tree with node.");
            testNames[testIndex] = "Binary Search Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                bst.add(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! BST isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("BST add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("BST memory use = " + (memory / count) + " bytes");
            }

            boolean contains = bst.contains(INVALID);
            boolean removed = bst.remove(INVALID);
            if (contains || removed) {
                System.err.println("BST invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("BST invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(bst.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                bst.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("BST lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! BST isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("BST remove time = " + removeTime / count + " ms");
            }

            contains = bst.contains(INVALID);
            removed = bst.remove(INVALID);
            if (contains || removed) {
                System.err.println("BST invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("BST invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                bst.add(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! BST isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.size() == sorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("BST add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("BST memory use = " + (memory / count) + " bytes");
            }

            contains = bst.contains(INVALID);
            removed = bst.remove(INVALID);
            if (contains || removed) {
                System.err.println("BST invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("BST invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(bst.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                bst.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("BST lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! BST isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("BST remove time = " + removeTime / count + " ms");
            }

            contains = bst.contains(INVALID);
            removed = bst.remove(INVALID);
            if (contains || removed) {
                System.err.println("BST invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("BST invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                bst.add(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! BST isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && !bst.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("BST add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("BST memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = bst.contains(INVALID);
            removed = bst.remove(INVALID);
            if (contains || removed) {
                System.err.println("BST invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("BST invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(bst.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                bst.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("BST lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                bst.remove(item);
                if (validateStructure && !bst.validate()) {
                    System.err.println("YIKES!! BST isn't valid.");
                    handleError(bst);
                    return false;
                }
                if (validateStructure && !(bst.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(bst);
                    return false;
                }
                if (validateContents && bst.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(bst);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("BST remove time = " + removeSortedTime + " ms");
            }

            contains = bst.contains(INVALID);
            removed = bst.remove(INVALID);
            if (contains || removed) {
                System.err.println("BST invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("BST invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testCompactSuffixTrie() {
        {
            // Compact Suffix Trie
            if (debug > 1) System.out.println("Compact Suffix Trie.");
            String bookkeeper = "bookkeeper";
            CompactSuffixTrie<String> trie = new CompactSuffixTrie<String>(bookkeeper);
            if (debug > 1) System.out.println(trie.toString());
            if (debug > 1) System.out.println(trie.getSuffixes());

            boolean exists = trie.doesSubStringExist(bookkeeper);
            if (!exists) {
                System.err.println("YIKES!! " + bookkeeper + " doesn't exists.");
                handleError(trie);
                return false;
            }

            String failed = "booker";
            exists = trie.doesSubStringExist(failed);
            if (exists) {
                System.err.println("YIKES!! " + failed + " exists.");
                handleError(trie);
                return false;
            }

            String pass = "kkee";
            exists = trie.doesSubStringExist(pass);
            if (!exists) {
                System.err.println("YIKES!! " + pass + " doesn't exists.");
                handleError(trie);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testGraph() {
        {
            // UNDIRECTED GRAPH
            if (debug > 1) System.out.println("Undirected Graph.");
            java.util.List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
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

            java.util.List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
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

            Graph<Integer> undirected = new Graph<Integer>(verticies, edges);
            if (debug > 1) System.out.println(undirected.toString());

            Graph.Vertex<Integer> start = v1;
            if (debug > 1) System.out.println("Dijstra's shortest paths of the undirected graph from " + start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(undirected, start);
            if (debug > 1) System.out.println(getPathMapString(start, map1));

            Graph.Vertex<Integer> end = v5;
            if (debug > 1) System.out.println("Dijstra's shortest path of the undirected graph from " + start.getValue() + " to " + end.getValue());
            Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(undirected, start, end);
            if (debug > 1) {
                if (pair1 != null) System.out.println(pair1.toString());
                else System.out.println("No path from " + start.getValue() + " to " + end.getValue());
            }

            start = v1;
            if (debug > 1) System.out.println("Bellman-Ford's shortest paths of the undirected graph from " + start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(undirected, start);
            if (debug > 1) System.out.println(getPathMapString(start, map2));

            end = v5;
            if (debug > 1) System.out.println("Bellman-Ford's shortest path of the undirected graph from " + start.getValue() + " to " + end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(undirected, start, end);
            if (debug > 1) {
                if (pair2 != null) System.out.println(pair2.toString());
                else System.out.println("No path from " + start.getValue() + " to " + end.getValue());
            }

            if (debug > 1) System.out.println("Prim's minimum spanning tree of the undirected graph from " + start.getValue());
            Graph.CostPathPair<Integer> pair = Prim.getMinimumSpanningTree(undirected, start);
            if (debug > 1) System.out.println(pair.toString());

            if (debug > 1) System.out.println();
        }

        {
            // DIRECTED GRAPH
            if (debug > 1) System.out.println("Directed Graph.");
            java.util.List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
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

            java.util.List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
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

            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
            if (debug > 1) System.out.println(directed.toString());

            Graph.Vertex<Integer> start = v1;
            if (debug > 1) System.out.println("Dijstra's shortest paths of the directed graph from " + start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = Dijkstra.getShortestPaths(directed, start);
            if (debug > 1) System.out.println(getPathMapString(start, map));

            Graph.Vertex<Integer> end = v5;
            if (debug > 1) System.out.println("Dijstra's shortest path of the directed graph from " + start.getValue() + " to " + end.getValue());
            Graph.CostPathPair<Integer> pair = Dijkstra.getShortestPath(directed, start, end);
            if (debug > 1) {
                if (pair != null) System.out.println(pair.toString());
                else System.out.println("No path from " + start.getValue() + " to " + end.getValue());
            }

            start = v1;
            if (debug > 1) System.out.println("Bellman-Ford's shortest paths of the undirected graph from " + start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed, start);
            if (debug > 1) System.out.println(getPathMapString(start, map2));

            end = v5;
            if (debug > 1) System.out.println("Bellman-Ford's shortest path of the undirected graph from " + start.getValue() + " to " + end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (debug > 1) {
                if (pair2 != null) System.out.println(pair2.toString());
                else System.out.println("No path from " + start.getValue() + " to " + end.getValue());
            }

            if (debug > 1) System.out.println();
        }

        {
            // DIRECTED GRAPH (WITH NEGATIVE WEIGHTS)
            if (debug > 1) System.out.println("Undirected Graph with Negative Weights.");
            java.util.List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
            Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
            verticies.add(v1);
            Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
            verticies.add(v2);
            Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
            verticies.add(v3);
            Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
            verticies.add(v4);

            java.util.List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
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

            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
            if (debug > 1) System.out.println(directed.toString());

            Graph.Vertex<Integer> start = v1;
            if (debug > 1) System.out.println("Bellman-Ford's shortest paths of the directed graph from " + start.getValue());
            Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map2 = BellmanFord.getShortestPaths(directed, start);
            if (debug > 1) System.out.println(getPathMapString(start, map2));

            Graph.Vertex<Integer> end = v3;
            if (debug > 1) System.out.println("Bellman-Ford's shortest path of the directed graph from " + start.getValue() + " to " + end.getValue());
            Graph.CostPathPair<Integer> pair2 = BellmanFord.getShortestPath(directed, start, end);
            if (debug > 1) {
                if (pair2 != null) System.out.println(pair2.toString());
                else System.out.println("No path from " + start.getValue() + " to " + end.getValue());
            }

            if (debug > 1) System.out.println("Johnson's all-pairs shortest path of the directed graph.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Set<Edge<Integer>>>> paths = Johnson.getAllPairsShortestPaths(directed);
            if (debug > 1) {
                if (paths == null) System.out.println("Directed graph contains a negative weight cycle.");
                else System.out.println(getPathMapString(paths));
            }

            if (debug > 1) System.out.println("Floyd-Warshall's all-pairs shortest path weights of the directed graph.");
            Map<Vertex<Integer>, Map<Vertex<Integer>, Integer>> pathWeights = FloydWarshall.getAllPairsShortestPaths(directed);
            if (debug > 1) System.out.println(getWeightMapString(pathWeights));

            if (debug > 1) System.out.println();
        }

        {
            // UNDIRECTED GRAPH
            if (debug > 1) System.out.println("Undirected Graph cycle check.");
            java.util.List<Vertex<Integer>> cycledVerticies = new ArrayList<Vertex<Integer>>();
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

            java.util.List<Edge<Integer>> cycledEdges = new ArrayList<Edge<Integer>>();
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

            Graph<Integer> undirectedWithCycle = new Graph<Integer>(cycledVerticies, cycledEdges);
            if (debug > 1) System.out.println(undirectedWithCycle.toString());

            if (debug > 1) {
                System.out.println("Cycle detection of the undirected graph.");
                boolean result = CycleDetection.detect(undirectedWithCycle);
                System.out.println("result=" + result);
                System.out.println();
            }

            java.util.List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
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

            java.util.List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
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

            Graph<Integer> undirectedWithoutCycle = new Graph<Integer>(verticies, edges);
            if (debug > 1) System.out.println(undirectedWithoutCycle.toString());

            if (debug > 1) {
                System.out.println("Cycle detection of the undirected graph.");
                boolean result = CycleDetection.detect(undirectedWithoutCycle);
                System.out.println("result=" + result);
                System.out.println();
            }
        }

        {
            // DIRECTED GRAPH
            if (debug > 1) System.out.println("Directed Graph topological sort.");
            java.util.List<Vertex<Integer>> verticies = new ArrayList<Vertex<Integer>>();
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

            java.util.List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
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

            Graph<Integer> directed = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
            if (debug > 1) System.out.println(directed.toString());

            if (debug > 1) System.out.println("Topological sort of the directed graph.");
            java.util.List<Graph.Vertex<Integer>> results = TopologicalSort.sort(directed);
            if (debug > 1) {
                System.out.println("result=" + results);
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

            // Min-Heap [array]
            if (debug > 1) System.out.println("Min-Heap [array].");
            testNames[testIndex] = "Min-Heap [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            BinaryHeap<Integer> minHeap = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [array] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Min-Heap [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Min-Heap [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Min-Heap [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = minHeap.removeHead();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [array] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (validateStructure && minHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Min-Heap [array] isn't empty.");
                handleError(minHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Min-Heap [array] remove time = " + removeTime / count + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [array] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [array] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Min-Heap [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Min-Heap [array] memory use = " + (memory / count) + " bytes");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Min-Heap [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = minHeap.removeHead();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [array] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (validateStructure && minHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Min-Heap [array] isn't empty.");
                handleError(minHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Min-Heap [array] remove time = " + removeTime / count + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [array] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [array] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Min-Heap [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Min-Heap [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Min-Heap [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = minHeap.removeHead();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [array] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Min-Heap [array] remove time = " + removeSortedTime + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [array] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Min-Heap [tree]
            if (debug > 1) System.out.println("Min-Heap [tree].");
            testNames[testIndex] = "Min-Heap [tree]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            BinaryHeap<Integer> minHeap = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [tree] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Min-Heap [tree] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Min-Heap [tree] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [tree] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Min-Heap [tree] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = minHeap.removeHead();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [tree] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (validateStructure && minHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Min-Heap [tree] isn't empty.");
                handleError(minHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Min-Heap [tree] remove time = " + removeTime / count + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [tree] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [tree] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Min-Heap [tree] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Min-Heap [tree] memory use = " + (memory / count) + " bytes");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [tree] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Min-Heap [tree] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = minHeap.removeHead();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [tree] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (validateStructure && minHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Min-Heap [tree] isn't empty.");
                handleError(minHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Min-Heap [tree] remove time = " + removeTime / count + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [tree] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                minHeap.add(item);
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [tree] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && !minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Min-Heap [tree] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Min-Heap [tree] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [tree] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Min-Heap [tree] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = minHeap.removeHead();
                if (validateStructure && !minHeap.validate()) {
                    System.err.println("YIKES!! Min-Heap [tree] isn't valid.");
                    handleError(minHeap);
                    return false;
                }
                if (validateStructure && !(minHeap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(minHeap);
                    return false;
                }
                if (validateContents && minHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(minHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Min-Heap [tree] remove time = " + removeSortedTime + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Min-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Min-Heap [tree] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Max-Heap [array]
            if (debug > 1) System.out.println("Max-Heap [array].");
            testNames[testIndex] = "Max-Heap [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinaryHeap<Integer> maxHeap = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [array] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Max-Heap [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Max-Heap [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Max-Heap [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = maxHeap.removeHead();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [array] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (validateStructure && maxHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Max-Heap [array] isn't empty.");
                handleError(maxHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Max-Heap [array] remove time = " + removeTime / count + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [array] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [array] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Max-Heap [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Max-Heap [array] memory use = " + (memory / count) + " bytes");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Max-Heap [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = maxHeap.removeHead();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [array] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (validateStructure && maxHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Max-Heap [array] isn't empty.");
                handleError(maxHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Max-Heap [array] remove time = " + removeTime / count + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [array] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [array] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Max-Heap [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Max-Heap [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Max-Heap [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = maxHeap.removeHead();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [array] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Max-Heap [array] remove time = " + removeSortedTime + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [array] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Max-Heap [tree]
            if (debug > 1) System.out.println("Max-Heap [tree].");
            testNames[testIndex] = "Max-Heap [tree]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            BinaryHeap<Integer> maxHeap = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [tree] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Max-Heap [tree] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Max-Heap [tree] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [tree] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Max-Heap [tree] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = maxHeap.removeHead();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [tree] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (validateStructure && maxHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Max-Heap [tree] isn't empty.");
                handleError(maxHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Max-Heap [tree] remove time = " + removeTime / count + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [tree] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [tree] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Max-Heap [tree] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Max-Heap [tree] memory use = " + (memory / count) + " bytes");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [tree] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Max-Heap [tree] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = maxHeap.removeHead();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [tree] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (validateStructure && maxHeap.getHeadValue() != null) {
                System.err.println("YIKES!! Max-Heap [tree] isn't empty.");
                handleError(maxHeap);
                return false;
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Max-Heap [tree] remove time = " + removeTime / count + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [tree] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                maxHeap.add(item);
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [tree] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && !maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Max-Heap [tree] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Max-Heap [tree] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [tree] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Max-Heap [tree] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = maxHeap.removeHead();
                if (validateStructure && !maxHeap.validate()) {
                    System.err.println("YIKES!! Max-Heap [tree] isn't valid.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateStructure && !(maxHeap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(maxHeap);
                    return false;
                }
                if (validateContents && maxHeap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(maxHeap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Max-Heap [tree] remove time = " + removeSortedTime + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Max-Heap [tree] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Max-Heap [tree] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testHashMap() {
        int key = unsorted.length / 2;
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
            if (debug > 1) System.out.println("Hash Map.");
            testNames[testIndex] = "Hash Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            HashMap<Integer, String> hash = new HashMap<Integer, String>(key);
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                hash.put(item, string);
                if (validateStructure && !(hash.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && !hash.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Hash Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Hash Map memory use = " + (memory / count) + " bytes");
            }

            boolean contains = hash.contains(INVALID);
            boolean removed = hash.remove(INVALID);
            if (contains || removed) {
                System.err.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(hash.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                hash.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Hash Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                hash.remove(item);
                if (validateStructure && !(hash.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && hash.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Hash Map remove time = " + removeTime / count + " ms");
            }

            contains = hash.contains(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed) {
                System.err.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                hash.put(item, string);
                if (validateStructure && !(hash.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && !hash.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Hash Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Hash Map memory use = " + (memory / count) + " bytes");
            }

            contains = hash.contains(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed) {
                System.err.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(hash.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                hash.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Hash Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                hash.remove(item);
                if (validateStructure && !(hash.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && hash.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Hash Map remove time = " + removeTime / count + " ms");
            }

            contains = hash.contains(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed) {
                System.err.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                hash.put(item, string);
                if (validateStructure && !(hash.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && !hash.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Hash Map add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Hash Map memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = hash.contains(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed) {
                System.err.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(hash.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                hash.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Hash Map lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                hash.remove(item);
                if (validateStructure && !(hash.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(hash);
                    return false;
                }
                if (validateContents && hash.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(hash);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Hash Map remove time = " + removeSortedTime + " ms");
            }

            contains = hash.contains(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed) {
                System.err.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Hash Map invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testIntervalTree() {
        {
            // Interval tree
            if (debug > 1) System.out.println("Interval Tree.");
            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>(2, 6, "RED")));
            intervals.add((new IntervalTree.IntervalData<String>(3, 5, "ORANGE")));
            intervals.add((new IntervalTree.IntervalData<String>(4, 11, "GREEN")));
            intervals.add((new IntervalTree.IntervalData<String>(5, 10, "DARK_GREEN")));
            intervals.add((new IntervalTree.IntervalData<String>(8, 12, "BLUE")));
            intervals.add((new IntervalTree.IntervalData<String>(9, 14, "PURPLE")));
            intervals.add((new IntervalTree.IntervalData<String>(13, 15, "BLACK")));
            IntervalTree<String> tree = new IntervalTree<String>(intervals);
            if (debug > 1) System.out.println(tree);

            IntervalTree.IntervalData<String> query = tree.query(2);
            if (debug > 1) System.out.println("2: " + query);

            query = tree.query(4); // Stabbing query
            if (debug > 1) System.out.println("4: " + query);

            query = tree.query(9); // Stabbing query
            if (debug > 1) System.out.println("9: " + query);

            query = tree.query(1, 16); // Range query
            if (debug > 1) System.out.println("1->16: " + query);

            query = tree.query(7, 14); // Range query
            if (debug > 1) System.out.println("7->14: " + query);

            query = tree.query(14, 15); // Range query
            if (debug > 1) System.out.println("14->15: " + query);

            if (debug > 1) System.out.println();
        }

        {
            // Lifespan Interval tree
            if (debug > 1) System.out.println("Lifespan Interval Tree.");
            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>(1888, 1971, "Stravinsky")));
            intervals.add((new IntervalTree.IntervalData<String>(1874, 1951, "Schoenberg")));
            intervals.add((new IntervalTree.IntervalData<String>(1843, 1907, "Grieg")));
            intervals.add((new IntervalTree.IntervalData<String>(1779, 1828, "Schubert")));
            intervals.add((new IntervalTree.IntervalData<String>(1756, 1791, "Mozart")));
            intervals.add((new IntervalTree.IntervalData<String>(1585, 1672, "Schuetz")));
            IntervalTree<String> tree = new IntervalTree<String>(intervals);
            if (debug > 1) System.out.println(tree);

            IntervalTree.IntervalData<String> query = tree.query(1890);
            if (debug > 1) System.out.println("1890: " + query);

            query = tree.query(1909); // Stabbing query
            if (debug > 1) System.out.println("1909: " + query);

            query = tree.query(1792, 1903); // Range query
            if (debug > 1) System.out.println("1792->1903: " + query);

            query = tree.query(1776, 1799); // Range query
            if (debug > 1) System.out.println("1776->1799: " + query);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaHashMap() {
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

            // Java's Hash Map
            if (debug > 1) System.out.println("Java's Hash Map.");
            testNames[testIndex] = "Java's Hash Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.HashMap<Integer, String> hash = new java.util.HashMap<Integer, String>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                hash.put(item, string);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Hash Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Hash Map memory use = " + (memory / count) + " bytes");
            }

            boolean contains = hash.containsKey(INVALID);
            String removed = hash.remove(INVALID);
            if (contains || removed != null) {
                System.err.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (debug > 1) System.out.println(hash.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                hash.containsKey(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Hash Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                hash.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Hash Map remove time = " + removeTime / count + " ms");
            }

            contains = hash.containsKey(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed != null) {
                System.err.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                hash.put(item, string);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Hash Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Hash Map memory use = " + (memory / count) + " bytes");
            }

            contains = hash.containsKey(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed != null) {
                System.err.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (debug > 1) System.out.println(hash.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                hash.containsKey(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Hash Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                hash.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Hash Map remove time = " + removeTime / count + " ms");
            }

            contains = hash.containsKey(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed != null) {
                System.err.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                hash.put(item, string);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Hash Map add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Hash Map memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = hash.containsKey(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed != null) {
                System.err.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (debug > 1) System.out.println(hash.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                hash.containsKey(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Hash Map lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                hash.remove(item);
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Hash Map remove time = " + removeSortedTime + " ms");
            }

            contains = hash.containsKey(INVALID);
            removed = hash.remove(INVALID);
            if (contains || removed != null) {
                System.err.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Hash Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaHeap() {
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
            if (debug > 1) System.out.println("Java's Min-Heap.");
            testNames[testIndex] = "Java's Min-Heap";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            java.util.PriorityQueue<Integer> minHeap = new java.util.PriorityQueue<Integer>(10, new Comparator<Integer>() {

                @Override
                public int compare(Integer arg0, Integer arg1) {
                    if (arg0.compareTo(arg1) == -1) return -1;
                    else if (arg1.compareTo(arg0) == -1) return 1;
                    return 0;
                }
            });
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                minHeap.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Min-Heap add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Min-Heap memory use = " + (memory / count) + " bytes");
            }

            boolean contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Min-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Min-Heap invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Min-Heap lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                minHeap.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Min-Heap remove time = " + removeTime / count + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Min-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Min-Heap invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                minHeap.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Min-Heap add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Min-Heap memory use = " + (memory / count) + " bytes");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Min-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Min-Heap invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Min-Heap lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                minHeap.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Min-Heap remove time = " + removeTime / count + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Min-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Min-Heap invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                minHeap.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Min-Heap add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Min-Heap memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Min-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Min-Heap invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(minHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                minHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Min-Heap lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                minHeap.remove();
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Min-Heap remove time = " + removeSortedTime + " ms");
            }

            contains = minHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Min-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Min-Heap invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // MAX-HEAP
            if (debug > 1) System.out.println("Java's Max-Heap.");
            testNames[testIndex] = "Java's Max-Heap";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.PriorityQueue<Integer> maxHeap = new java.util.PriorityQueue<Integer>(10, new Comparator<Integer>() {

                @Override
                public int compare(Integer arg0, Integer arg1) {
                    if (arg0.compareTo(arg1) == 1) return -1;
                    else if (arg1.compareTo(arg0) == 1) return 1;
                    return 0;
                }
            });
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                maxHeap.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Max-Heap add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Max-Heap memory use = " + (memory / count) + " bytes");
            }

            boolean contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Max-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Max-Heap invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Max-Heap lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                maxHeap.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Max-Heap remove time = " + removeTime / count + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Max-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Max-Heap invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                maxHeap.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Max-Heap add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Max-Heap memory use = " + (memory / count) + " bytes");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Max-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Max-Heap invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Max-Heap lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                maxHeap.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Max-Heap remove time = " + removeTime / count + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Max-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Max-Heap invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                maxHeap.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Max-Heap add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Max-Heap memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Max-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Max-Heap invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(maxHeap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                maxHeap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Max-Heap lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                maxHeap.remove();
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Max-Heap remove time = " + removeSortedTime + " ms");
            }

            contains = maxHeap.contains(INVALID);
            if (contains) {
                System.err.println("Java's Max-Heap invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Max-Heap invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaList() {
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

            // Java's List [array]
            if (debug > 1) System.out.println("Java's List [array].");
            testNames[testIndex] = "Java's List [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.List<Integer> list = new java.util.ArrayList<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's List [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's List [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = list.contains(INVALID);
            boolean removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's List [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                list.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's List [array] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                list.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's List [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's List [array] memory use = " + (memory / count) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's List [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                list.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's List [array] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's List [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's List [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's List [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                Integer item = sorted[i];
                list.remove(item);
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's List [array] remove time = " + removeSortedTime + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Java's List [linked]
            if (debug > 1) System.out.println("Java's List [linked].");
            testNames[testIndex] = "Java's List [linked]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.List<Integer> list = new java.util.LinkedList<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's List [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's List [linked] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = list.contains(INVALID);
            boolean removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's List [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                list.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's List [linked] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                list.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's List [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's List [linked] memory use = " + (memory / count) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's List [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                list.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's List [linked] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's List [linked] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's List [linked] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's List [linked] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                Integer item = sorted[i];
                list.remove(item);
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's List [linked] remove time = " + removeSortedTime + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaQueue() {
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

            // Java's Queue [array]
            if (debug > 1) System.out.println("Java's Queue [array].");
            testNames[testIndex] = "Java's Queue [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.Queue<Integer> queue = new java.util.ArrayDeque<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                queue.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Queue [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Queue [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Queue [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                queue.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Queue [array] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [array] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                queue.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Queue [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Queue [array] memory use = " + (memory / count) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Queue [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                queue.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Queue [array] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [array] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                queue.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Queue [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Queue [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Queue [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                queue.remove();
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Queue [array] remove time = " + removeSortedTime + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [array] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // LinkedQueue
            if (debug > 1) System.out.println("Java's Queue [linked].");
            testNames[testIndex] = "Java's Queue [linked]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.Queue<Integer> queue = new java.util.LinkedList<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                queue.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Queue [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Queue [linked] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Queue [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                queue.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Queue [linked] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [linked] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                queue.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Queue [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Queue [linked] memory use = " + (memory / count) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Queue [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                queue.remove();
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Queue [linked] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [linked] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                queue.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Queue [linked] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Queue [linked] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Queue [linked] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                queue.remove();
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Queue [linked] remove time = " + removeSortedTime + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Java's Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Queue [linked] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaRedBlackTree() {
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

            // Java's Red-Black Tree
            if (debug > 1) System.out.println("Java's Red-Black Tree");
            testNames[testIndex] = "Java's RedBlack Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.TreeSet<Integer> tree = new java.util.TreeSet<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Red-Black Tree memory use = " + (memory / count) + " bytes");
            }

            boolean contains = tree.contains(INVALID);
            boolean removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Red-Black lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                tree.add(item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Red-Black Tree memory use = " + (memory / count) + " bytes");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                tree.add(item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Red-Black Tree memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                tree.remove(item);
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Red-Black Tree remove time = " + removeSortedTime + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaStack() {
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

            // Java's Stack [vector]
            if (debug > 1) System.out.println("Java's Stack [vector].");
            testNames[testIndex] = "Java's Stack [vector]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.Stack<Integer> stack = new java.util.Stack<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Stack [vector] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Stack [vector] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Java's Stack [vector] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Stack [vector] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Stack [vector] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Stack [vector] remove time = " + removeTime / count + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Java's Stack [vector] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Stack [vector] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Stack [vector] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Stack [vector] memory use = " + (memory / count) + " bytes");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Java's Stack [vector] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Stack [vector] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Stack [vector] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Stack [vector] remove time = " + removeTime / count + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Java's Stack [vector] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Stack [vector] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Stack [vector] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Stack [vector] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Java's Stack [vector] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Stack [vector] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Stack [vector] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Stack [vector] remove time = " + removeSortedTime + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Java's Stack [vector] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Java's Stack [vector] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testJavaTreeMap() {
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

            // Java's Tree Map
            if (debug > 1) System.out.println("Java's Tree Map.");
            testNames[testIndex] = "Java's Tree Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            java.util.TreeMap<String, Integer> trieMap = new java.util.TreeMap<String, Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Tree Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Tree Map memory use = " + (memory / count) + " bytes");
            }

            String invalid = INVALID.toString();
            boolean contains = trieMap.containsKey(invalid);
            Integer removed = trieMap.remove(invalid);
            if (contains || removed != null) {
                System.err.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (debug > 1) System.out.println(trieMap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trieMap.containsKey(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Tree Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Tree Map remove time = " + removeTime / count + " ms");
            }

            contains = trieMap.containsKey(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed != null) {
                System.err.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, item);
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Java's Tree Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Tree Map memory use = " + (memory / count) + " bytes");
            }

            contains = trieMap.containsKey(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed != null) {
                System.err.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (debug > 1) System.out.println(trieMap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trieMap.containsKey(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Tree Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Java's Tree Map remove time = " + removeTime / count + " ms");
            }

            contains = trieMap.containsKey(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed != null) {
                System.err.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, item);
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Java's Tree Map add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Java's Tree Map memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = trieMap.containsKey(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed != null) {
                System.err.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (debug > 1) System.out.println(trieMap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                String string = String.valueOf(item);
                trieMap.containsKey(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Java's Tree Map lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Java's Tree Map remove time = " + removeSortedTime + " ms");
            }

            contains = trieMap.containsKey(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed != null) {
                System.err.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Java's Tree Map invalidity check. contains=" + contains + " removed=" + (removed != null));

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testKdTree() {
        {
            // K-D TREE
            if (debug > 1) System.out.println("k-d tree with node.");

            java.util.List<KdTree.XYZPoint> points = new ArrayList<KdTree.XYZPoint>();
            KdTree.XYZPoint p1 = new KdTree.XYZPoint(2, 3);
            points.add(p1);
            KdTree.XYZPoint p2 = new KdTree.XYZPoint(5, 4);
            points.add(p2);
            KdTree.XYZPoint p3 = new KdTree.XYZPoint(9, 6);
            points.add(p3);
            KdTree.XYZPoint p4 = new KdTree.XYZPoint(4, 7);
            points.add(p4);
            KdTree.XYZPoint p5 = new KdTree.XYZPoint(8, 1);
            points.add(p5);
            KdTree.XYZPoint p6 = new KdTree.XYZPoint(7, 2);
            points.add(p6);
            KdTree<KdTree.XYZPoint> kdTree = new KdTree<KdTree.XYZPoint>(points);
            if (debug > 1) System.out.println(kdTree.toString());

            Collection<KdTree.XYZPoint> result = kdTree.nearestNeighbourSearch(1, p3);
            if (debug > 1) System.out.println("NNS for " + p3 + " result=" + result + "\n");

            KdTree.XYZPoint search = new KdTree.XYZPoint(1, 4);
            result = kdTree.nearestNeighbourSearch(4, search);
            if (debug > 1) System.out.println("NNS for " + search + " result=" + result + "\n");

            kdTree.remove(p6);
            if (debug > 1) System.out.println("Removed " + p6 + "\n" + kdTree.toString());
            kdTree.remove(p4);
            if (debug > 1) System.out.println("Removed " + p4 + "\n" + kdTree.toString());
            kdTree.remove(p3);
            if (debug > 1) System.out.println("Removed " + p3 + "\n" + kdTree.toString());
            kdTree.remove(p5);
            if (debug > 1) System.out.println("Removed " + p5 + "\n" + kdTree.toString());
            kdTree.remove(p1);
            if (debug > 1) System.out.println("Removed " + p1 + "\n" + kdTree.toString());
            kdTree.remove(p2);
            if (debug > 1) System.out.println("Removed " + p2 + "\n" + kdTree.toString());

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testList() {
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

            // List [array]
            if (debug > 1) System.out.println("List [array].");
            testNames[testIndex] = "List [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            List<Integer> list = new List.ArrayList<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("List [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("List [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = list.contains(INVALID);
            boolean removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("List [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("List [array] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                list.add(item);
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("List [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("List [array] memory use = " + (memory / count) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("List [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("List [array] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.add(item);
                if (validateStructure && !(list.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("List [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("List [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("List [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                list.remove(item);
                if (validateStructure && !(list.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("List [array] remove time = " + removeSortedTime + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [array] invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // List [linked]
            if (debug > 1) System.out.println("List [linked].");
            testNames[testIndex] = "List [linked]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            List<Integer> list = new List.LinkedList<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.add(item);
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("List [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("List [linked] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = list.contains(INVALID);
            boolean removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("List [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("List [linked] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                list.add(item);
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("List [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("List [linked] memory use = " + (memory / count) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("List [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                list.remove(item);
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
                if (validateStructure && !(list.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("List [linked] remove time = " + removeTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.add(item);
                if (validateStructure && !(list.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("List [linked] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("List [linked] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("List [linked] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                list.remove(item);
                if (validateStructure && !(list.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("List [linked] remove time = " + removeSortedTime + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("List [linked] invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testMatrix() {
        {
            // MATRIX
            if (debug > 1) System.out.println("Matrix.");
            Matrix<Integer> matrix1 = new Matrix<Integer>(4, 3);
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

            Matrix<Integer> matrix2 = new Matrix<Integer>(3, 2);
            matrix2.set(0, 0, 12);
            matrix2.set(0, 1, 25);
            matrix2.set(1, 0, 9);
            matrix2.set(1, 1, 10);
            matrix2.set(2, 0, 8);
            matrix2.set(2, 1, 5);

            if (debug > 1) System.out.println("Matrix multiplication.");
            Matrix<Integer> matrix3 = matrix1.multiply(matrix2);
            if (debug > 1) System.out.println(matrix3);

            int rows = 2;
            int cols = 2;
            int counter = 0;
            Matrix<Integer> matrix4 = new Matrix<Integer>(rows, cols);
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    matrix4.set(r, c, counter++);
                }
            }

            if (debug > 1) System.out.println("Matrix subtraction.");
            Matrix<Integer> matrix5 = matrix4.subtract(matrix4);
            if (debug > 1) System.out.println(matrix5);

            if (debug > 1) System.out.println("Matrix addition.");
            Matrix<Integer> matrix6 = matrix4.add(matrix4);
            if (debug > 1) System.out.println(matrix6);

            Matrix<Integer> matrix7 = new Matrix<Integer>(2, 2);
            matrix7.set(0, 0, 1);
            matrix7.set(0, 1, 2);
            matrix7.set(1, 0, 3);
            matrix7.set(1, 1, 4);

            Matrix<Integer> matrix8 = new Matrix<Integer>(2, 2);
            matrix8.set(0, 0, 1);
            matrix8.set(0, 1, 2);
            matrix8.set(1, 0, 3);
            matrix8.set(1, 1, 4);

            if (debug > 1) System.out.println("Matrix multiplication.");
            Matrix<Integer> matrix9 = matrix7.multiply(matrix8);
            if (debug > 1) System.out.println(matrix9);
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

            // Patricia Trie
            if (debug > 1) System.out.println("Patricia Trie.");
            testNames[testIndex] = "Patricia Trie";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            PatriciaTrie<String> trie = new PatriciaTrie<String>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Patricia Trie add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Patricia Trie memory use = " + (memory / count) + " bytes");
            }

            String invalid = INVALID.toString();
            boolean contains = trie.contains(invalid);
            boolean removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trie.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trie.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Patricia Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Patricia Trie remove time = " + removeTime / count + " ms");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + string + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Patricia Trie add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Patricia Trie memory use = " + (memory / count) + " bytes");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trie.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trie.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Patricia Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + string + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Patricia Trie remove time = " + removeTime / count + " ms");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Patricia Tree add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Patricia Tree memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trie.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                String string = String.valueOf(item);
                trie.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Patricia Tree lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Patricia Tree remove time = " + removeSortedTime + " ms");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Patricia Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Queue [array]
            if (debug > 1) System.out.println("Queue [array].");
            testNames[testIndex] = "Queue [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Queue<Integer> queue = new Queue.ArrayQueue<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Queue [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Queue [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Queue [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Queue [array] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [array] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Queue [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Queue [array] memory use = " + (memory / count) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Queue [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Queue [array] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [array] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Queue [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Queue [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Queue [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Queue [array] remove time = " + removeSortedTime + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [array] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // LinkedQueue
            if (debug > 1) System.out.println("Queue [linked].");
            testNames[testIndex] = "Queue [linked]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Queue<Integer> queue = new Queue.LinkedQueue<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Queue [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Queue [linked] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Queue [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Queue [linked] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [linked] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Queue [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Queue [linked] memory use = " + (memory / count) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Queue [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Queue [linked] remove time = " + removeTime / count + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [linked] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                queue.enqueue(item);
                if (validateStructure && !(queue.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && !queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Queue [linked] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Queue [linked] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(queue.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                queue.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Queue [linked] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = queue.dequeue();
                if (validateStructure && !(queue.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(queue);
                    return false;
                }
                if (validateContents && queue.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(queue);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Queue [linked] remove time = " + removeSortedTime + " ms");
            }

            contains = queue.contains(INVALID);
            if (contains) {
                System.err.println("Queue [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Queue [linked] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testRadixTrie() {
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

            // Radix Trie (map)
            if (debug > 1) System.out.println("Radix Trie (map).");
            testNames[testIndex] = "Radix Trie (map)";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            RadixTrie<String, Integer> tree = new RadixTrie<String, Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.put(string, item);
                if (validateStructure && !(tree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Radix Trie add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Radix Trie memory use = " + (memory / count) + " bytes");
            }

            String invalid = INVALID.toString();
            boolean contains = tree.contains(invalid);
            boolean removed = tree.remove(invalid);
            if (contains || removed) {
                System.err.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                tree.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Radix Trie lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.remove(string);
                if (validateStructure && !(tree.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Radix Trie remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(invalid);
            removed = tree.remove(invalid);
            if (contains || removed) {
                System.err.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.put(string, item);
                if (validateStructure && !(tree.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Radix Trie add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Radix Trie memory use = " + (memory / count) + " bytes");
            }

            contains = tree.contains(invalid);
            removed = tree.remove(invalid);
            if (contains || removed) {
                System.err.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                tree.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Radix Trie lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                tree.remove(string);
                if (validateStructure && !(tree.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Radix Trie remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(invalid);
            removed = tree.remove(invalid);
            if (contains || removed) {
                System.err.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                tree.put(string, item);
                if (validateStructure && !(tree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(string)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Radix Trie add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Radix Trie memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = tree.contains(invalid);
            removed = tree.remove(invalid);
            if (contains || removed) {
                System.err.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                String string = String.valueOf(item);
                tree.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Radix Trie lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                String string = String.valueOf(item);
                tree.remove(string);
                if (validateStructure && !(tree.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(string)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Radix Trie remove time = " + removeSortedTime + " ms");
            }

            contains = tree.contains(invalid);
            removed = tree.remove(invalid);
            if (contains || removed) {
                System.err.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Radix Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testRedBlackTree() {
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

            // Red-Black Tree
            if (debug > 1) System.out.println("Red-Black Tree");
            testNames[testIndex] = "RedBlack Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.add(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! Red-Black Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Red-Black Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Red-Black Tree memory use = " + (memory / count) + " bytes");
            }

            boolean contains = tree.contains(INVALID);
            boolean removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Red-Black lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! Red-Black Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Red-Black Tree remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                tree.add(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! Red-Black Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Red-Black Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Red-Black Tree memory use = " + (memory / count) + " bytes");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Red-Black Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                tree.remove(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! Red-Black Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Red-Black Tree remove time = " + removeTime / count + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                tree.add(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! Red-Black Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && !tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Red-Black Tree add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Red-Black Tree memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(tree.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                tree.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Red-Black Tree lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                tree.remove(item);
                if (validateStructure && !tree.validate()) {
                    System.err.println("YIKES!! Red-Black Tree isn't valid.");
                    handleError(tree);
                    return false;
                }
                if (validateStructure && !(tree.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(tree);
                    return false;
                }
                if (validateContents && tree.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(tree);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Red-Black Tree remove time = " + removeSortedTime + " ms");
            }

            contains = tree.contains(INVALID);
            removed = tree.remove(INVALID);
            if (contains || removed) {
                System.err.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Red-Black Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testSegmentTree() {

        {
            // Quadrant Segment tree
            if (debug > 1) System.out.println("Quadrant Segment Tree.");
            java.util.List<SegmentTree.Data.QuadrantData> segments = new ArrayList<SegmentTree.Data.QuadrantData>();
            segments.add(new SegmentTree.Data.QuadrantData(0, 1, 0, 0, 0)); // first
                                                                            // point
                                                                            // in
                                                                            // the
                                                                            // 0th
                                                                            // quadrant
            segments.add(new SegmentTree.Data.QuadrantData(1, 0, 1, 0, 0)); // second
                                                                            // point
                                                                            // in
                                                                            // the
                                                                            // 1st
                                                                            // quadrant
            segments.add(new SegmentTree.Data.QuadrantData(2, 0, 0, 1, 0)); // third
                                                                            // point
                                                                            // in
                                                                            // the
                                                                            // 2nd
                                                                            // quadrant
            segments.add(new SegmentTree.Data.QuadrantData(3, 0, 0, 0, 1)); // fourth
                                                                            // point
                                                                            // in
                                                                            // the
                                                                            // 3rd
                                                                            // quadrant
            FlatSegmentTree<SegmentTree.Data.QuadrantData> tree = new FlatSegmentTree<SegmentTree.Data.QuadrantData>(segments);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.QuadrantData query = tree.query(0, 3);
            if (debug > 1) System.out.println("0->3: " + query + "\n");

            query = tree.query(2, 3);
            if (debug > 1) System.out.println("2->3: " + query + "\n");

            query = tree.query(0, 2);
            if (debug > 1) System.out.println("0->2: " + query + "\n");

            if (debug > 1) System.out.println();
        }

        {
            // Range Maximum Segment tree
            if (debug > 1) System.out.println("Range Maximum Segment Tree.");
            java.util.List<SegmentTree.Data.RangeMaximumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<Integer>>();
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(0, (Integer) 4));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(1, (Integer) 2));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(2, (Integer) 6));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(3, (Integer) 3));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(4, (Integer) 1));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(5, (Integer) 5));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(6, (Integer) 0));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(7, 17, (Integer) 7));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(21, (Integer) 10));
            FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Integer>>(segments, 3);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.RangeMaximumData<Integer> query = tree.query(0, 7);
            if (debug > 1) System.out.println("0->7: " + query + "\n");

            query = tree.query(0, 21);
            if (debug > 1) System.out.println("0->21: " + query + "\n");

            query = tree.query(2, 5);
            if (debug > 1) System.out.println("2->5: " + query + "\n");

            query = tree.query(7);
            if (debug > 1) System.out.println("7: " + query + "\n");

            if (debug > 1) System.out.println();
        }

        {
            // Range Minimum Segment tree
            if (debug > 1) System.out.println("Range Minimum Segment Tree.");
            java.util.List<SegmentTree.Data.RangeMinimumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<Integer>>();
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(0, (Integer) 4));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(1, (Integer) 2));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(2, (Integer) 6));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(3, (Integer) 3));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(4, (Integer) 1));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(5, (Integer) 5));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(6, (Integer) 0));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(17, (Integer) 7));
            FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Integer>>(segments, 5);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.RangeMinimumData<Integer> query = tree.query(0, 7);
            if (debug > 1) System.out.println("0->7: " + query + "\n");

            query = tree.query(0, 17);
            if (debug > 1) System.out.println("0->17: " + query + "\n");

            query = tree.query(1, 3);
            if (debug > 1) System.out.println("1->3: " + query + "\n");

            query = tree.query(7);
            if (debug > 1) System.out.println("7: " + query + "\n");

            if (debug > 1) System.out.println();
        }

        {
            // Range Sum Segment tree
            if (debug > 1) System.out.println("Range Sum Segment Tree.");
            java.util.List<SegmentTree.Data.RangeSumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeSumData<Integer>>();
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(0, (Integer) 4));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(1, (Integer) 2));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(2, (Integer) 6));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(3, (Integer) 3));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(4, (Integer) 1));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(5, (Integer) 5));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(6, (Integer) 0));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(17, (Integer) 7));
            FlatSegmentTree<SegmentTree.Data.RangeSumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<Integer>>(segments, 10);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.RangeSumData<Integer> query = tree.query(0, 8);
            if (debug > 1) System.out.println("0->8: " + query + "\n");

            query = tree.query(0, 17);
            if (debug > 1) System.out.println("0->17: " + query + "\n");

            query = tree.query(2, 5);
            if (debug > 1) System.out.println("2->5: " + query + "\n");

            query = tree.query(10, 17);
            if (debug > 1) System.out.println("10->17: " + query + "\n");

            query = tree.query(16);
            if (debug > 1) System.out.println("16: " + query + "\n");

            query = tree.query(17);
            if (debug > 1) System.out.println("17: " + query + "\n");

            if (debug > 1) System.out.println();
        }

        {
            // Interval Segment tree
            if (debug > 1) System.out.println("Interval Segment Tree.");
            java.util.List<SegmentTree.Data.IntervalData<String>> segments = new ArrayList<SegmentTree.Data.IntervalData<String>>();
            segments.add((new SegmentTree.Data.IntervalData<String>(2, 6, "RED")));
            segments.add((new SegmentTree.Data.IntervalData<String>(3, 5, "ORANGE")));
            segments.add((new SegmentTree.Data.IntervalData<String>(4, 11, "GREEN")));
            segments.add((new SegmentTree.Data.IntervalData<String>(5, 10, "DARK_GREEN")));
            segments.add((new SegmentTree.Data.IntervalData<String>(8, 12, "BLUE")));
            segments.add((new SegmentTree.Data.IntervalData<String>(9, 14, "PURPLE")));
            segments.add((new SegmentTree.Data.IntervalData<String>(13, 15, "BLACK")));
            DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(segments);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.IntervalData<String> query = tree.query(2);
            if (debug > 1) System.out.println("2: " + query);

            query = tree.query(4); // Stabbing query
            if (debug > 1) System.out.println("4: " + query);

            query = tree.query(9); // Stabbing query
            if (debug > 1) System.out.println("9: " + query);

            query = tree.query(1, 16); // Range query
            if (debug > 1) System.out.println("1->16: " + query);

            query = tree.query(7, 14); // Range query
            if (debug > 1) System.out.println("7->14: " + query);

            query = tree.query(14, 15); // Range query
            if (debug > 1) System.out.println("14->15: " + query);

            if (debug > 1) System.out.println();
        }

        {
            // Lifespan Interval Segment tree
            if (debug > 1) System.out.println("Lifespan Interval Segment Tree.");
            java.util.List<SegmentTree.Data.IntervalData<String>> segments = new ArrayList<SegmentTree.Data.IntervalData<String>>();
            segments.add((new SegmentTree.Data.IntervalData<String>(1888, 1971, "Stravinsky")));
            segments.add((new SegmentTree.Data.IntervalData<String>(1874, 1951, "Schoenberg")));
            segments.add((new SegmentTree.Data.IntervalData<String>(1843, 1907, "Grieg")));
            segments.add((new SegmentTree.Data.IntervalData<String>(1779, 1828, "Schubert")));
            segments.add((new SegmentTree.Data.IntervalData<String>(1756, 1791, "Mozart")));
            segments.add((new SegmentTree.Data.IntervalData<String>(1585, 1672, "Schuetz")));
            DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(segments, 25);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.IntervalData<String> query = tree.query(1890);
            if (debug > 1) System.out.println("1890: " + query);

            query = tree.query(1909); // Stabbing query
            if (debug > 1) System.out.println("1909: " + query);

            query = tree.query(1792, 1903); // Range query
            if (debug > 1) System.out.println("1792->1903: " + query);

            query = tree.query(1776, 1799); // Range query
            if (debug > 1) System.out.println("1776->1799: " + query);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testSkipList() {
        {
            long count = 0;

            long memory = 0L;
            long beforeMemory = 0L;
            long afterMemory = 0L;
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            // SkipList only works with sorted items
            if (debug > 1) System.out.println("Skip List.");
            testNames[testIndex] = "Skip List";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            SkipList<Integer> list = new SkipList<Integer>();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.add(item);
                if (validateStructure && !(list.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Skip List add time = " + (addSortedTime / count) + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Skip List memory use = " + (memory / count) + " bytes");
            }

            boolean contains = list.contains(INVALID);
            boolean removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Skip List lookup time = " + (lookupTime / count) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                list.remove(item);
                if (validateStructure && !(list.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Skip List remove time = " + (removeSortedTime / count) + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.add(item);
                if (validateStructure && !(list.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && !list.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Skip List add time = " + (addSortedTime / count) + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Skip List memory use = " + (memory / count) + " bytes");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(list.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;

            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                list.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Skip List lookup time = " + (lookupTime / count) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                list.remove(item);
                if (validateStructure && !(list.size() == (sorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(list);
                    return false;
                }
                if (validateContents && list.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(list);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Skip List remove time = " + removeSortedTime / count + " ms");
            }

            contains = list.contains(INVALID);
            removed = list.remove(INVALID);
            if (contains || removed) {
                System.err.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Skip List invalidity check. contains=" + contains + " removed=" + removed);

            testResults[testIndex] = new long[] { 0, 0, addSortedTime / count, removeSortedTime / count, lookupTime / count, memory / count };

            if (debug > 1) System.out.println();
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

            // Splay Tree
            if (debug > 1) System.out.println("Splay Tree.");
            testNames[testIndex] = "Splay Tree";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            SplayTree<Integer> splay = new SplayTree<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                splay.add(item);
                if (validateStructure && !(splay.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && !splay.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Splay Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Splay Tree memory use = " + (memory / count) + " bytes");
            }

            boolean contains = splay.contains(INVALID);
            boolean removed = splay.remove(INVALID);
            if (contains || removed) {
                System.err.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(splay.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                splay.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Splay Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                splay.remove(item);
                if (validateStructure && !(splay.size() == ((unsorted.length - 1) - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && splay.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Splay Tree remove time = " + removeTime / count + " ms");
            }

            contains = splay.contains(INVALID);
            removed = splay.remove(INVALID);
            if (contains || removed) {
                System.err.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                splay.add(item);
                if (validateStructure && !(splay.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && !splay.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Splay Tree add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Splay Tree memory use = " + (memory / count) + " bytes");
            }

            contains = splay.contains(INVALID);
            removed = splay.remove(INVALID);
            if (contains || removed) {
                System.err.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(splay.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                splay.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Splay Tree lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                splay.remove(item);
                if (validateStructure && !(splay.size() == ((unsorted.length - 1) - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && splay.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Splay Tree remove time = " + removeTime / count + " ms");
            }

            contains = splay.contains(INVALID);
            removed = splay.remove(INVALID);
            if (contains || removed) {
                System.err.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                splay.add(item);
                if (validateStructure && !(splay.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && !splay.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Splay Tree add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Splay Tree memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = splay.contains(INVALID);
            removed = splay.remove(INVALID);
            if (contains || removed) {
                System.err.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(splay.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                splay.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Splay Tree lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                splay.remove(item);
                if (validateStructure && !(splay.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(splay);
                    return false;
                }
                if (validateContents && splay.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(splay);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Splay Tree remove time = " + removeSortedTime + " ms");
            }

            contains = splay.contains(INVALID);
            removed = splay.remove(INVALID);
            if (contains || removed) {
                System.err.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Splay Tree invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Stack [array]
            if (debug > 1) System.out.println("Stack [array].");
            testNames[testIndex] = "Stack [array]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Stack<Integer> stack = new Stack.ArrayStack<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Stack [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Stack [array] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Stack [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Stack [array] remove time = " + removeTime / count + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [array] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Stack [array] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Stack [array] memory use = " + (memory / count) + " bytes");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Stack [array] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Stack [array] remove time = " + removeTime / count + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [array] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Stack [array] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Stack [array] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [array] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Stack [array] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Stack [array] remove time = " + removeSortedTime + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [array] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [array] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Stack [linked]
            if (debug > 1) System.out.println("Stack [linked].");
            testNames[testIndex] = "Stack [linked]";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Stack<Integer> stack = new Stack.LinkedStack<Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Stack [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Stack [linked] memory use = " + (memory / count) + " bytes");
            }

            boolean contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Stack [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Stack [linked] remove time = " + removeTime / count + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [linked] invalidity check. contains=" + contains);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Stack [linked] add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Stack [linked] memory use = " + (memory / count) + " bytes");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Stack [linked] lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Stack [linked] remove time = " + removeTime / count + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [linked] invalidity check. contains=" + contains);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                stack.push(item);
                if (validateStructure && !(stack.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && !stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Stack [linked] add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Stack [linked] memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [linked] invalidity check. contains=" + contains);

            if (debug > 1) System.out.println(stack.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                stack.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Stack [linked] lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = stack.pop();
                if (validateStructure && !(stack.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(stack);
                    return false;
                }
                if (validateContents && stack.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(stack);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Stack [linked] remove time = " + removeSortedTime + " ms");
            }

            contains = stack.contains(INVALID);
            if (contains) {
                System.err.println("Stack [linked] invalidity check. contains=" + contains);
                return false;
            } else System.out.println("Stack [linked] invalidity check. contains=" + contains);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testSuffixTree() {
        {
            // Suffix Tree
            if (debug > 1) System.out.println("Suffix Tree.");
            String bookkeeper = "bookkeeper";
            SuffixTree<String> tree = new SuffixTree<String>(bookkeeper);
            if (debug > 1) System.out.println(tree.toString());
            if (debug > 1) System.out.println(tree.getSuffixes());

            boolean exists = tree.doesSubStringExist(bookkeeper);
            if (!exists) {
                System.err.println("YIKES!! " + bookkeeper + " doesn't exists.");
                handleError(tree);
                return false;
            }

            String failed = "booker";
            exists = tree.doesSubStringExist(failed);
            if (exists) {
                System.err.println("YIKES!! " + failed + " exists.");
                handleError(tree);
                return false;
            }

            String pass = "kkee";
            exists = tree.doesSubStringExist(pass);
            if (!exists) {
                System.err.println("YIKES!! " + pass + " doesn't exists.");
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testSuffixTrie() {
        {
            // Suffix Trie
            if (debug > 1) System.out.println("Suffix Trie.");
            String bookkeeper = "bookkeeper";
            SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
            if (debug > 1) System.out.println(trie.toString());
            if (debug > 1) System.out.println(trie.getSuffixes());

            boolean exists = trie.doesSubStringExist(bookkeeper);
            if (!exists) {
                System.err.println("YIKES!! " + bookkeeper + " doesn't exists.");
                handleError(trie);
                return false;
            }

            String failed = "booker";
            exists = trie.doesSubStringExist(failed);
            if (exists) {
                System.err.println("YIKES!! " + failed + " exists.");
                handleError(trie);
                return false;
            }

            String pass = "kkee";
            exists = trie.doesSubStringExist(pass);
            if (!exists) {
                System.err.println("YIKES!! " + pass + " doesn't exists.");
                handleError(trie);
                return false;
            }

            if (debug > 1) System.out.println();
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

            // Treap
            if (debug > 1) System.out.println("Treap.");
            testNames[testIndex] = "Treap";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Treap<Integer> treap = new Treap<Integer>(unsorted.length * 2);
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                treap.add(item);
                if (validateStructure && !(treap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.1");
                    handleError(treap);
                    return false;
                }
                if (validateContents && !treap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(treap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Treap add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Treap memory use = " + (memory / count) + " bytes");
            }

            boolean contains = treap.contains(INVALID);
            boolean removed = treap.remove(INVALID);
            if (contains || removed) {
                System.err.println("Treap invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Treap invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(treap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                treap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Treap lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                treap.remove(item);
                if (validateStructure && !(treap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.2");
                    handleError(treap);
                    return false;
                }
                if (validateContents && treap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(treap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Treap remove time = " + removeTime / count + " ms");
            }

            contains = treap.contains(INVALID);
            removed = treap.remove(INVALID);
            if (contains || removed) {
                System.err.println("Treap invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Treap invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                treap.add(item);
                if (validateStructure && !(treap.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.3");
                    handleError(treap);
                    return false;
                }
                if (validateContents && !treap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exists.");
                    handleError(treap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Treap add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Treap memory use = " + (memory / count) + " bytes");
            }

            contains = treap.contains(INVALID);
            removed = treap.remove(INVALID);
            if (contains || removed) {
                System.err.println("Treap invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Treap invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(treap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                treap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Treap lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                treap.remove(item);
                if (validateStructure && !(treap.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.4");
                    handleError(treap);
                    return false;
                }
                if (validateContents && treap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(treap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Treap remove time = " + removeTime / count + " ms");
            }

            contains = treap.contains(INVALID);
            removed = treap.remove(INVALID);
            if (contains || removed) {
                System.err.println("Treap invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Treap invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                treap.add(item);
                if (validateStructure && !(treap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(treap);
                    return false;
                }
                if (validateContents && !treap.contains(item)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(treap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Treap add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Treap memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = treap.contains(INVALID);
            removed = treap.remove(INVALID);
            if (contains || removed) {
                System.err.println("Treap invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Treap invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(treap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                treap.contains(item);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Treap lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                treap.remove(item);
                if (validateStructure && !(treap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(treap);
                    return false;
                }
                if (validateContents && treap.contains(item)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(treap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Treap remove time = " + removeSortedTime + " ms");
            }

            contains = treap.contains(INVALID);
            removed = treap.remove(INVALID);
            if (contains || removed) {
                System.err.println("Treap invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Treap invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static boolean testTreeMap() {
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

            // Tree Map
            if (debug > 1) System.out.println("Tree Map.");
            testNames[testIndex] = "Tree Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            TreeMap<String, Integer> TreeMap = new TreeMap<String, Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                TreeMap.put(string, item);
                if (validateStructure && !(TreeMap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(TreeMap);
                    return false;
                }
                if (validateContents && !TreeMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(TreeMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Tree Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Tree Map memory use = " + (memory / count) + " bytes");
            }

            String invalid = INVALID.toString();
            boolean contains = TreeMap.contains(invalid);
            boolean removed = TreeMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(TreeMap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                TreeMap.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Tree Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                TreeMap.remove(string);
                if (validateStructure && !(TreeMap.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(TreeMap);
                    return false;
                }
                if (validateContents && TreeMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(TreeMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Tree Map remove time = " + removeTime / count + " ms");
            }

            contains = TreeMap.contains(invalid);
            removed = TreeMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                TreeMap.put(string, item);
                if (validateStructure && !(TreeMap.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(TreeMap);
                    return false;
                }
                if (validateContents && !TreeMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(TreeMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Tree Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Tree Map memory use = " + (memory / count) + " bytes");
            }

            contains = TreeMap.contains(invalid);
            removed = TreeMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(TreeMap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                TreeMap.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Tree Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                TreeMap.remove(string);
                if (validateStructure && !(TreeMap.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(TreeMap);
                    return false;
                }
                if (validateContents && TreeMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(TreeMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Tree Map remove time = " + removeTime / count + " ms");
            }

            contains = TreeMap.contains(invalid);
            removed = TreeMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                TreeMap.put(string, item);
                if (validateStructure && !(TreeMap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(TreeMap);
                    return false;
                }
                if (validateContents && !TreeMap.contains(string)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(TreeMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Tree Map add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Tree Map memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = TreeMap.contains(invalid);
            removed = TreeMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(TreeMap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                String string = String.valueOf(item);
                TreeMap.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Tree Map lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                String string = String.valueOf(item);
                TreeMap.remove(string);
                if (validateStructure && !(TreeMap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(TreeMap);
                    return false;
                }
                if (validateContents && TreeMap.contains(string)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(TreeMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Tree Map remove time = " + removeSortedTime + " ms");
            }

            contains = TreeMap.contains(invalid);
            removed = TreeMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Tree Map invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Trie.
            if (debug > 1) System.out.println("Trie.");
            testNames[testIndex] = "Trie";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            Trie<String> trie = new Trie<String>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Trie add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Trie memory use = " + (memory / count) + " bytes");
            }

            String invalid = INVALID.toString();
            boolean contains = trie.contains(invalid);
            boolean removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trie.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trie.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Trie lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Trie remove time = " + removeTime / count + " ms");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.size() == unsorted.length - i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Trie add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Trie memory use = " + (memory / count) + " bytes");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trie.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trie.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Trie lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.size() == unsorted.length - (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Trie remove time = " + removeTime / count + " ms");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trie.add(string);
                if (validateStructure && !(trie.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && !trie.contains(string)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Trie add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Trie memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trie.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                String string = String.valueOf(item);
                trie.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Trie lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trie.remove(string);
                if (validateStructure && !(trie.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trie);
                    return false;
                }
                if (validateContents && trie.contains(string)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(trie);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Trie remove time = " + removeSortedTime + " ms");
            }

            contains = trie.contains(invalid);
            removed = trie.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
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

            // Trie Map
            if (debug > 1) System.out.println("Trie Map.");
            testNames[testIndex] = "Trie Map";

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            TrieMap<String, Integer> trieMap = new TrieMap<String, Integer>();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, item);
                if (validateStructure && !(trieMap.size() == i + 1)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && !trieMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Trie Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Trie Map memory use = " + (memory / count) + " bytes");
            }

            String invalid = INVALID.toString();
            boolean contains = trieMap.contains(invalid);
            boolean removed = trieMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trieMap.toString());

            long lookupTime = 0L;
            long beforeLookupTime = 0L;
            long afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trieMap.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Trie Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
                if (validateStructure && !(trieMap.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && trieMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Trie Map remove time = " + removeTime / count + " ms");
            }

            contains = trieMap.contains(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);

            count++;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.currentTimeMillis();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, item);
                if (validateStructure && !(trieMap.size() == (unsorted.length - i))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && !trieMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " doesn't exist.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.currentTimeMillis();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println("Trie Map add time = " + addTime / count + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Trie Map memory use = " + (memory / count) + " bytes");
            }

            contains = trieMap.contains(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trieMap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : unsorted) {
                String string = String.valueOf(item);
                trieMap.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Trie Map lookup time = " + lookupTime / count + " ms");
            }

            if (debugTime) beforeRemoveTime = System.currentTimeMillis();
            for (int i = 0; i < unsorted.length; i++) {
                int item = unsorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
                if (validateStructure && !(trieMap.size() == (unsorted.length - (i + 1)))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && trieMap.contains(string)) {
                    System.err.println("YIKES!! " + string + " still exists.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.currentTimeMillis();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println("Trie Map remove time = " + removeTime / count + " ms");
            }

            contains = trieMap.contains(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);

            // sorted
            long addSortedTime = 0L;
            long removeSortedTime = 0L;
            long beforeAddSortedTime = 0L;
            long afterAddSortedTime = 0L;
            long beforeRemoveSortedTime = 0L;
            long afterRemoveSortedTime = 0L;

            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.currentTimeMillis();
            for (int i = 0; i < sorted.length; i++) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trieMap.put(string, item);
                if (validateStructure && !(trieMap.size() == (i + 1))) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && !trieMap.contains(string)) {
                    System.err.println("YIKES!! " + item + " doesn't exist.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.currentTimeMillis();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println("Trie Map add time = " + addSortedTime + " ms");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println("Trie Map memory use = " + (memory / (count + 1)) + " bytes");
            }

            contains = trieMap.contains(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);

            if (debug > 1) System.out.println(trieMap.toString());

            lookupTime = 0L;
            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.currentTimeMillis();
            for (int item : sorted) {
                String string = String.valueOf(item);
                trieMap.contains(string);
            }
            if (debugTime) {
                afterLookupTime = System.currentTimeMillis();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println("Trie Map lookup time = " + lookupTime / (count + 1) + " ms");
            }

            if (debugTime) beforeRemoveSortedTime = System.currentTimeMillis();
            for (int i = sorted.length - 1; i >= 0; i--) {
                int item = sorted[i];
                String string = String.valueOf(item);
                trieMap.remove(string);
                if (validateStructure && !(trieMap.size() == i)) {
                    System.err.println("YIKES!! " + item + " caused a size mismatch.");
                    handleError(trieMap);
                    return false;
                }
                if (validateContents && trieMap.contains(string)) {
                    System.err.println("YIKES!! " + item + " still exists.");
                    handleError(trieMap);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.currentTimeMillis();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println("Trie Map remove time = " + removeSortedTime + " ms");
            }

            contains = trieMap.contains(invalid);
            removed = trieMap.remove(invalid);
            if (contains || removed) {
                System.err.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            } else System.out.println("Trie Map invalidity check. contains=" + contains + " removed=" + removed);

            if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
            testResults[testIndex][0] += addTime / count;
            testResults[testIndex][1] += removeTime / count;
            testResults[testIndex][2] += addSortedTime;
            testResults[testIndex][3] += removeSortedTime;
            testResults[testIndex][4] += lookupTime / (count + 1);
            testResults[testIndex++][5] += memory / (count + 1);

            if (debug > 1) System.out.println();
        }

        return true;
    }

    private static final String getTestResults(int testNumber, String[] names, long[][] results) {
        StringBuilder resultsBuilder = new StringBuilder();
        String format = "%-25s %-10s %-15s %-15s %-20s %-15s %-15s\n";
        Formatter formatter = new Formatter(resultsBuilder, Locale.US);
        formatter = formatter.format(format, "Data Structure", "Add time", "Remove time", "Sorted add time", "Sorted remove time", "Lookup time", "Size");

        double KB = 1000;
        double MB = 1000 * KB;

        double SECOND = 1000;
        double MINUTES = 60 * SECOND;

        for (int i = 0; i < TESTS; i++) {
            String name = names[i];
            long[] result = results[i];
            if (name != null && result != null) {
                double addTime = result[0];
                addTime /= testNumber;
                String addTimeString = null;
                if (addTime > MINUTES) {
                    addTime /= MINUTES;
                    addTimeString = FORMAT.format(addTime) + " m";
                } else if (addTime > SECOND) {
                    addTime /= SECOND;
                    addTimeString = FORMAT.format(addTime) + " s";
                } else {
                    addTimeString = FORMAT.format(addTime) + " ms";
                }

                double removeTime = result[1];
                removeTime /= testNumber;
                String removeTimeString = null;
                if (removeTime > MINUTES) {
                    removeTime /= MINUTES;
                    removeTimeString = FORMAT.format(removeTime) + " m";
                } else if (removeTime > SECOND) {
                    removeTime /= SECOND;
                    removeTimeString = FORMAT.format(removeTime) + " s";
                } else {
                    removeTimeString = FORMAT.format(removeTime) + " ms";
                }

                // sorted
                double addSortedTime = result[2];
                addSortedTime /= testNumber;
                String sortedAddTimeString = null;
                if (addSortedTime > MINUTES) {
                    addSortedTime /= MINUTES;
                    sortedAddTimeString = FORMAT.format(addSortedTime) + " m";
                } else if (addSortedTime > SECOND) {
                    addSortedTime /= SECOND;
                    sortedAddTimeString = FORMAT.format(addSortedTime) + " s";
                } else {
                    sortedAddTimeString = FORMAT.format(addSortedTime) + " ms";
                }

                double removeSortedTime = result[3];
                removeSortedTime /= testNumber;
                String sortedRemoveTimeString = null;
                if (removeSortedTime > MINUTES) {
                    removeSortedTime /= MINUTES;
                    sortedRemoveTimeString = FORMAT.format(removeSortedTime) + " m";
                } else if (removeSortedTime > SECOND) {
                    removeSortedTime /= SECOND;
                    sortedRemoveTimeString = FORMAT.format(removeSortedTime) + " s";
                } else {
                    sortedRemoveTimeString = FORMAT.format(removeSortedTime) + " ms";
                }

                double lookupTime = result[4];
                lookupTime /= testNumber;
                String lookupTimeString = null;
                if (lookupTime > MINUTES) {
                    lookupTime /= MINUTES;
                    lookupTimeString = FORMAT.format(lookupTime) + " m";
                } else if (lookupTime > SECOND) {
                    lookupTime /= SECOND;
                    lookupTimeString = FORMAT.format(lookupTime) + " s";
                } else {
                    lookupTimeString = FORMAT.format(lookupTime) + " ms";
                }

                double size = result[5];
                size /= testNumber;
                String sizeString = null;
                if (size > MB) {
                    size = size / MB;
                    sizeString = FORMAT.format(size) + " MB";
                } else if (size > KB) {
                    size = size / KB;
                    sizeString = FORMAT.format(size) + " KB";
                } else {
                    sizeString = FORMAT.format(size) + " Bytes";
                }

                formatter = formatter.format(format, name, addTimeString, removeTimeString, sortedAddTimeString, sortedRemoveTimeString, lookupTimeString,
                        sizeString);
            }
        }

        return resultsBuilder.toString();
    }

    private static final String getPathMapString(Graph.Vertex<Integer> start, Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map) {
        StringBuilder builder = new StringBuilder();
        for (Graph.Vertex<Integer> v : map.keySet()) {
            Graph.CostPathPair<Integer> pair = map.get(v);
            builder.append("From ").append(start.getValue()).append(" to vertex=").append(v.getValue()).append("\n");
            if (pair != null) builder.append(pair.toString()).append("\n");

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

    private static final long fSLEEP_INTERVAL = 75;

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
