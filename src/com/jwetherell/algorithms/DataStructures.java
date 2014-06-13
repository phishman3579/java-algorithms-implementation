package com.jwetherell.algorithms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.jwetherell.algorithms.data_structures.AVLTree;
import com.jwetherell.algorithms.data_structures.BTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.CompactSuffixTrie;
import com.jwetherell.algorithms.data_structures.HashArrayMappedTrie;
import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.IHeap;
import com.jwetherell.algorithms.data_structures.IList;
import com.jwetherell.algorithms.data_structures.IMap;
import com.jwetherell.algorithms.data_structures.IQueue;
import com.jwetherell.algorithms.data_structures.ISet;
import com.jwetherell.algorithms.data_structures.IStack;
import com.jwetherell.algorithms.data_structures.ITree;
import com.jwetherell.algorithms.data_structures.IntervalTree;
import com.jwetherell.algorithms.data_structures.KdTree;
import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.Matrix;
import com.jwetherell.algorithms.data_structures.PatriciaTrie;
import com.jwetherell.algorithms.data_structures.QuadTree;
import com.jwetherell.algorithms.data_structures.Queue;
import com.jwetherell.algorithms.data_structures.RadixTrie;
import com.jwetherell.algorithms.data_structures.RedBlackTree;
import com.jwetherell.algorithms.data_structures.SegmentTree;
import com.jwetherell.algorithms.data_structures.SegmentTree.DynamicSegmentTree;
import com.jwetherell.algorithms.data_structures.SegmentTree.FlatSegmentTree;
import com.jwetherell.algorithms.data_structures.SkipList;
import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.SplayTree;
import com.jwetherell.algorithms.data_structures.Stack;
import com.jwetherell.algorithms.data_structures.SuffixTree;
import com.jwetherell.algorithms.data_structures.SuffixTrie;
import com.jwetherell.algorithms.data_structures.Treap;
import com.jwetherell.algorithms.data_structures.TreeMap;
import com.jwetherell.algorithms.data_structures.Trie;
import com.jwetherell.algorithms.data_structures.TrieMap;

@SuppressWarnings("unchecked")
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

    private static int debug = 1; // Debug level. 0=None, 1=Time and Memory (if enabled), 2=Time, Memory, data structure debug
    private static boolean debugTime = true; // How much time to: add all, remove all, add all items in reverse order, remove all
    private static boolean debugMemory = true; // How much memory is used by the data structure
    private static boolean validateStructure = true; // Is the data structure valid (passed invariants) and proper size
    private static boolean validateContents = true; // Was the item added/removed really added/removed from the structure
    private static boolean validateIterator = true; // Does the iterator(s) work

    private static final int TESTS = 38; // Max number of dynamic data structures to test
    private static final String[] testNames = new String[TESTS]; // Array to hold the test names
    private static final long[][] testResults = new long[TESTS][]; // Array to hold the test results
    private static int testIndex = 0; // Index into the tests
    private static int testNumber = 0; // Number of aggregate tests which have been run

    public static enum Type {Integer, String};

    public static void main(String[] args) {
        System.out.println("Starting tests.");
        boolean passed = false;
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            try {
                passed = runTests();
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            if (!passed) break;
        }
        if (passed) System.out.println("Tests finished. All passed.");
        else System.err.println("Tests finished. Detected a failure.");
    }

    public static void generateTestData() {
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
            if (i!=ARRAY_SIZE-1) builder.append(j).append(',');
        }
        set.clear();
        set = null;
        builder.append('\n');
        string = builder.toString();
        if (debug > 1) System.out.println(string);

        sorted = Arrays.copyOf(unsorted, unsorted.length);
        Arrays.sort(sorted);

        System.out.println("Generated data.");
    }

    public static void setTestData(Integer[] iUnsorted, Integer[] iSorted) {
        unsorted = iUnsorted;
        sorted = iSorted;
    }

    private static boolean runTests() {
        testIndex = 0;
        testNumber++;

        generateTestData();

        boolean passed = true;

        // Trees

        passed = testJavaRedBlackIntegerTree();
        if (!passed) {
            System.err.println("Java Red-Black [Integer] failed.");
            return false;
        }

        passed = testRedBlackTree();
        if (!passed) {
            System.err.println("Red-Black Tree failed.");
            return false;
        }

        passed = testAVLTree();
        if (!passed) {
            System.err.println("AVL Tree failed.");
            return false;
        }

        passed = testSplayTree();
        if (!passed) {
            System.err.println("Splay Tree failed.");
            return false;
        }

        passed = testBTree();
        if (!passed) {
            System.err.println("B-Tree failed.");
            return false;
        }

        passed = testTreap();
        if (!passed) {
            System.err.println("Treap failed.");
            return false;
        }

        passed = testBST();
        if (!passed) {
            System.err.println("BST failed.");
            return false;
        }

        passed = testJavaRedBlackStringTree();
        if (!passed) {
            System.err.println("Java Red-Black [String] failed.");
            return false;
        }

        passed = testTrie();
        if (!passed) {
            System.err.println("Trie failed.");
            return false;
        }

        passed = testPatriciaTrie();
        if (!passed) {
            System.err.println("Patricia Trie failed.");
            return false;
        }

        // Sets

        passed = testJavaSkipList();
        if (!passed) {
            System.err.println("Java's Skip List failed.");
            return false;
        }

        passed = testSkipList();
        if (!passed) {
            System.err.println("Skip List failed.");
            return false;
        }

        // Heaps

        passed = testJavaMinHeap();
        if (!passed) {
            System.err.println("Java Min-Heap failed.");
            return false;
        }

        passed = testMinHeap();
        if (!passed) {
            System.err.println("Min-Heap failed.");
            return false;
        }

        passed = testJavaMaxHeap();
        if (!passed) {
            System.err.println("Java Max-Heap failed.");
            return false;
        }

        passed = testMaxHeap();
        if (!passed) {
            System.err.println("Max-Heap failed.");
            return false;
        }

        // Lists

        passed = testJavaArrayList();
        if (!passed) {
            System.err.println("Java List failed.");
            return false;
        }

        passed = testArrayList();
        if (!passed) {
            System.err.println("List failed.");
            return false;
        }

        passed = testJavaLinkedList();
        if (!passed) {
            System.err.println("Java List failed.");
            return false;
        }

        passed = testLinkedList();
        if (!passed) {
            System.err.println("List failed.");
            return false;
        }

        // Queues

        passed = testJavaArrayQueue();
        if (!passed) {
            System.err.println("Java Queue failed.");
            return false;
        }

        passed = testArrayQueue();
        if (!passed) {
            System.err.println("Queue failed.");
            return false;
        }

        passed = testJavaLinkedQueue();
        if (!passed) {
            System.err.println("Java Queue failed.");
            return false;
        }

        passed = testLinkedQueue();
        if (!passed) {
            System.err.println("Queue failed.");
            return false;
        }

        // Stacks

        passed = testJavaStack();
        if (!passed) {
            System.err.println("Java Stack failed.");
            return false;
        }

        passed = testArrayStack();
        if (!passed) {
            System.err.println("Stack failed.");
            return false;
        }

        passed = testLinkedStack();
        if (!passed) {
            System.err.println("Stack failed.");
            return false;
        }

        // Maps

        passed = testJavaHashMap();
        if (!passed) {
            System.err.println("Java Hash Map failed.");
            return false;
        }

        passed = testHashMap();
        if (!passed) {
            System.err.println("Hash Map failed.");
            return false;
        }

        passed = testJavaTreeMap();
        if (!passed) {
            System.err.println("Java Tree Map failed.");
            return false;
        }

        passed = testTreeMap();
        if (!passed) {
            System.err.println("Tree Map failed.");
            return false;
        }

        passed = testTrieMap();
        if (!passed) {
            System.err.println("Trie Map failed.");
            return false;
        }

        passed = testRadixTrie();
        if (!passed) {
            System.err.println("Radix Trie failed.");
            return false;
        }

        passed = testJavaSkipListMap();
        if (!passed) {
            System.err.println("Java's Skip List Map failed.");
            return false;
        }

        passed = testSkipListMap();
        if (!passed) {
            System.err.println("Skip List Map failed.");
            return false;
        }

        passed = testHAMT();
        if (!passed) {
            System.err.println("HAMT failed.");
            return false;
        }

        if (debugTime && debugMemory) {
            String results = getTestResults(testNumber, testNames, testResults);
            System.out.println(results);
        }

        // MY STATIC DATA STRUCTURES

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

        passed = testCompactSuffixTrie();
        if (!passed) {
            System.err.println("Compact Suffix Trie failed.");
            return false;
        }

        passed = testSuffixTrie();
        if (!passed) {
            System.err.println("Suffix Trie failed.");
            return false;
        }

        passed = testSuffixTree();
        if (!passed) {
            System.err.println("Suffix Tree failed.");
            return false;
        }

        passed = testQuadTree();
        if (!passed) {
            System.err.println("QuadTree failed.");
            return false;
        }

        return true;
    }

    private static void handleError(Object obj) {
        System.err.println(string);
        System.err.println(obj.toString());
        throw new RuntimeException("Error in test.");
    }

    private static boolean testAVLTree() {
        String bstName = "AVL Tree";
        BinarySearchTree<Integer> bst = new AVLTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.Integer,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.Integer,bstName)) return false;
        return true;
    }

    private static boolean testBTree() {
        String bstName = "B-Tree";
        BTree<Integer> bst = new BTree<Integer>(2);
        Collection<Integer> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.Integer,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.Integer,bstName)) return false;
        return true;
    }

    private static boolean testBST() {
        String bstName = "BST";
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.Integer,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.Integer,bstName)) return false;
        return true;
    }

    private static boolean testCompactSuffixTrie() {
        System.out.println("Compact Suffix Trie.");
 
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

        System.out.println("Compact Suffix Trie worked correctly.");

        if (debug > 1) System.out.println();

        return true;
    }


    private static boolean testMinHeap() {
        String aNameMin = "Min-Heap [array]";
        BinaryHeap.BinaryHeapArray<Integer> aHeapMin = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
        Collection<Integer> aCollectionMin = aHeapMin.toCollection();
        if((validateStructure||validateContents) && !testHeap(aHeapMin,aNameMin,BinaryHeap.Type.MIN)) return false;
        if(!testJavaCollection(aCollectionMin,Type.Integer,aNameMin)) return false;

        String tNameMin = "Min-Heap [tree]";
        BinaryHeap.BinaryHeapTree<Integer> tHeapMin = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
        Collection<Integer> tCollectionMin = tHeapMin.toCollection();
        if((validateStructure||validateContents) && !testHeap(tHeapMin,tNameMin,BinaryHeap.Type.MIN)) return false;
        if(!testJavaCollection(tCollectionMin,Type.Integer,tNameMin)) return false;

        return true;
    }

    private static boolean testMaxHeap() {
        String aNameMax = "Max-Heap [array]";
        BinaryHeap.BinaryHeapArray<Integer> aHeapMax = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
        Collection<Integer> aCollectionMax = aHeapMax.toCollection();
        if((validateStructure||validateContents) && !testHeap(aHeapMax,aNameMax,BinaryHeap.Type.MAX)) return false;
        if(!testJavaCollection(aCollectionMax,Type.Integer,aNameMax)) return false;

        String lNameMax = "Max-Heap [tree]";
        BinaryHeap.BinaryHeapTree<Integer> tHeapMax = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
        Collection<Integer> tCollectionMax = tHeapMax.toCollection();
        if((validateStructure||validateContents) && !testHeap(tHeapMax,lNameMax,BinaryHeap.Type.MAX)) return false;
        if(!testJavaCollection(tCollectionMax,Type.Integer,lNameMax)) return false;

        return true;
    }

    private static boolean testHashMap() {
        String mapName = "HashMap";
        HashMap<Integer,String> map = new HashMap<Integer,String>(unsorted.length/2);
        java.util.Map<Integer,String> jMap = map.toMap();

        if((validateStructure||validateContents) && !testMap(map,Type.Integer,mapName)) return false;
        if(!testJavaMap(jMap,Type.Integer,mapName)) return false;
        return true;
    }

    private static boolean testHAMT() {
        String mapName = "HAMT";
        HashArrayMappedTrie<Integer,String> map = new HashArrayMappedTrie<Integer,String>();
        java.util.Map<Integer,String> jMap = map.toMap();

        if((validateStructure||validateContents) && !testMap(map,Type.Integer,mapName)) return false;
        if(!testJavaMap(jMap,Type.Integer,mapName)) return false;
        return true;
    }

    private static boolean testIntervalTree() {
        System.out.println("Interval Tree.");

        {   // Interval tree
            final String RED        = "RED";
            final String ORANGE     = "ORANGE";
            final String GREEN      = "GREEN";
            final String DARK_GREEN = "DARK_GREEN";
            final String BLUE       = "BLUE";
            final String PURPLE     = "PURPLE";
            final String BLACK      = "BLACK";
            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>(2,  6,   RED)));
            intervals.add((new IntervalTree.IntervalData<String>(3,  5,   ORANGE)));
            intervals.add((new IntervalTree.IntervalData<String>(4,  11,  GREEN)));
            intervals.add((new IntervalTree.IntervalData<String>(5,  10,  DARK_GREEN)));
            intervals.add((new IntervalTree.IntervalData<String>(8,  12,  BLUE)));
            intervals.add((new IntervalTree.IntervalData<String>(9,  14,  PURPLE)));
            intervals.add((new IntervalTree.IntervalData<String>(13, 15,  BLACK)));
            IntervalTree<String> tree = new IntervalTree<String>(intervals);
            if (debug > 1) System.out.println(tree);

            IntervalTree.IntervalData<String> query = tree.query(2);
            if (debug > 1) System.out.println("2: " + query);
            if (!query.getData().contains(RED)) {
                System.err.println("Interval Tree query error. query=2 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(4); // Stabbing query
            if (debug > 1) System.out.println("4: " + query);
            if (!query.getData().contains(GREEN)) {
                System.err.println("Interval Tree query error. query=4 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(9); // Stabbing query
            if (debug > 1) System.out.println("9: " + query);
            if (!query.getData().contains(PURPLE)) {
                System.err.println("Interval Tree query error. query=9 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1, 16); // Range query
            if (debug > 1) System.out.println("1->16: " + query);
            if (!query.getData().contains(RED) ||
                !query.getData().contains(ORANGE) ||
                !query.getData().contains(GREEN) ||
                !query.getData().contains(DARK_GREEN) ||
                !query.getData().contains(BLUE) ||
                !query.getData().contains(PURPLE) ||
                !query.getData().contains(BLACK)
            ) {
                System.err.println("Interval Tree query error. query=1->16 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(7, 14); // Range query
            if (debug > 1) System.out.println("7->14: " + query);
            if (!query.getData().contains(GREEN) ||
                !query.getData().contains(DARK_GREEN) ||
                !query.getData().contains(BLUE) ||
                !query.getData().contains(PURPLE) ||
                !query.getData().contains(BLACK)
            ) {
                System.err.println("Interval Tree query error. query=7->14 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(14, 15); // Range query
            if (debug > 1) System.out.println("14->15: " + query);
            if (!query.getData().contains(PURPLE) ||
                !query.getData().contains(BLACK)
            ) {
                System.err.println("Interval Tree query error. query=14->15 returned="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        {   // Lifespan Interval tree
            if (debug > 1) System.out.println("Lifespan Interval Tree.");
            final String stravinsky = "Stravinsky";
            final String schoenberg = "Schoenberg";
            final String grieg      = "Grieg";
            final String schubert   = "Schubert";
            final String mozart     = "Mozart";
            final String schuetz    = "Schuetz";
            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>(1888, 1971, stravinsky)));
            intervals.add((new IntervalTree.IntervalData<String>(1874, 1951, schoenberg)));
            intervals.add((new IntervalTree.IntervalData<String>(1843, 1907, grieg)));
            intervals.add((new IntervalTree.IntervalData<String>(1779, 1828, schubert)));
            intervals.add((new IntervalTree.IntervalData<String>(1756, 1791, mozart)));
            intervals.add((new IntervalTree.IntervalData<String>(1585, 1672, schuetz)));
            IntervalTree<String> tree = new IntervalTree<String>(intervals);
            if (debug > 1) System.out.println(tree);

            IntervalTree.IntervalData<String> query = tree.query(1890); // Stabbing query
            if (debug > 1) System.out.println("1890: " + query);
            if (!query.getData().contains(stravinsky) ||
                !query.getData().contains(schoenberg) ||
                !query.getData().contains(grieg)
            ) {
                System.err.println("Interval Tree query error. query=1890 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1909); // Stabbing query
            if (debug > 1) System.out.println("1909: " + query);
            if (!query.getData().contains(stravinsky) ||
                !query.getData().contains(schoenberg)
            ) {
                System.err.println("Interval Tree query error. query=1909 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1792, 1903); // Range query
            if (debug > 1) System.out.println("1792->1903: " + query);
            if (!query.getData().contains(stravinsky) ||
                !query.getData().contains(schoenberg) ||
                !query.getData().contains(grieg) ||
                !query.getData().contains(schubert)
            ) {
                System.err.println("Interval Tree query error. query=1792->1903 returned="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1776, 1799); // Range query
            if (debug > 1) System.out.println("1776->1799: " + query);
            if (!query.getData().contains(schubert) ||
                !query.getData().contains(mozart)
            ) {
                System.err.println("Interval Tree query error. query=1776->1799 returned="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        System.out.println("Interval Tree worked correctly.");

        return true;
    }

    private static boolean testJavaHashMap() {
        String mapName = "Java's HashMap";
        java.util.Map<Integer,String> map = new java.util.HashMap<Integer,String>(unsorted.length/2);

        if(!testJavaMap(map,Type.Integer,mapName)) return false;
        return true;
    }

    private static boolean testJavaMinHeap() {
        java.util.PriorityQueue<Integer> minArrayHeap = new java.util.PriorityQueue<Integer>(10,
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer arg0, Integer arg1) {
                        if (arg0.compareTo(arg1) < 0)
                            return -1;
                        else if (arg1.compareTo(arg0) < 0)
                            return 1;
                        return 0;
                    }
                });
        if(!testJavaCollection(minArrayHeap,Type.Integer,"Java's Min-Heap [array]")) return false;

        return true;
    }

    private static boolean testJavaMaxHeap() {
        java.util.PriorityQueue<Integer> maxArrayHeap = new java.util.PriorityQueue<Integer>(10,
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer arg0, Integer arg1) {
                        if (arg0.compareTo(arg1) > 0)
                            return -1;
                        else if (arg1.compareTo(arg0) > 0)
                            return 1;
                        return 0;
                    }
                });
        if(!testJavaCollection(maxArrayHeap,Type.Integer,"Java's Max-Heap [array]")) return false;
        return true;
    }

    private static boolean testJavaArrayList() {
        if(!testJavaCollection(new java.util.ArrayList<Integer>(),Type.Integer,"Java's List [array]")) return false;
        return true;
    }

    private static boolean testJavaLinkedList() {
        if(!testJavaCollection(new java.util.LinkedList<Integer>(),Type.Integer,"Java's List [linked]")) return false;
        return true;
    }

    private static boolean testJavaArrayQueue() {
        String aName = "Java's Queue [array]";
        java.util.Deque<Integer> aCollection = new java.util.ArrayDeque<Integer>();

        if(!testJavaCollection(aCollection,Type.Integer,aName)) return false;
        return true;
    }

    private static boolean testJavaLinkedQueue() {
        String lName = "Java's Queue [linked]";
        java.util.Deque<Integer> lCollection = new java.util.LinkedList<Integer>();

        if(!testJavaCollection(lCollection,Type.Integer,lName)) return false;
        return true;
    }

    private static boolean testJavaRedBlackIntegerTree() {
        String aName = "Java's Red-Black Tree [Integer]";
        java.util.TreeSet<Integer> aCollection = new java.util.TreeSet<Integer>();
        if(!testJavaCollection(aCollection,Type.Integer,aName)) return false;
        return true;
    }

    private static boolean testJavaRedBlackStringTree() {
        String aName = "Java's Red-Black Tree [String]";
        java.util.TreeSet<String> aCollection = new java.util.TreeSet<String>();
        if(!testJavaCollection(aCollection,Type.String,aName)) return false;
        return true;
    }

    private static boolean testJavaStack() {
        String aName = "Java's Stack [array]";
        java.util.Stack<Integer> aCollection = new java.util.Stack<Integer>();
        if(!testJavaCollection(aCollection,Type.Integer,aName)) return false;
        return true;
    }

    private static boolean testJavaTreeMap() {
        String mapName = "Java's TreeMap";
        java.util.Map<String,Integer> map = new java.util.TreeMap<String,Integer>();

        if(!testJavaMap(map,Type.String,mapName)) return false;
        return true;
    }

    private static boolean testKdTree() {
        System.out.println("K-D tree.");

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
        if (!result.contains(p3)) {
            System.err.println("K-D Tree query error. query=(k=1, p=(9, 6)) returned="+result);
            handleError(kdTree);
            return false;
        }

        KdTree.XYZPoint search = new KdTree.XYZPoint(1, 4);
        result = kdTree.nearestNeighbourSearch(4, search);
        if (debug > 1) System.out.println("NNS for " + search + " result=" + result + "\n");
        if (!result.contains(p1) ||
            !result.contains(p2) ||
            !result.contains(p4) ||
            !result.contains(p6)
        ) {
            System.err.println("K-D Tree query error. query=(k=4, p=(1, 4)) returned="+result);
            handleError(kdTree);
            return false;
        }

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

        if (debug > 1) System.out.println(kdTree.toString());

        if (debug > 1) System.out.println();

        System.out.println("K-D tree worked correctly.");

        return true;
    }

    private static boolean testArrayList() {
        String aName = "List [array]";
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();
        Collection<Integer> aCollection = aList.toCollection();

        if((validateStructure||validateContents) && !testList(aList,aName)) return false;
        if(!testJavaCollection(aCollection,Type.Integer,aName)) return false;
        return true;
    }

    private static boolean testLinkedList() {
        String lName = "List [linked]";
        List.LinkedList<Integer> lList = new List.LinkedList<Integer>();
        Collection<Integer> lCollection = lList.toCollection();

        if((validateStructure||validateContents) && !testList(lList,lName)) return false;
        if(!testJavaCollection(lCollection,Type.Integer,lName)) return false;
        return true;
    }

    private static boolean testMatrix() {
        System.out.println("Matrix.");

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

        // Result of multiplication
        Integer[][] array1 = new Integer[][]{{273,455},
                                             {243,235},
                                             {244,205},
                                             {102,160}};
        Matrix<Integer> result1 = new Matrix<Integer>(4,2,array1);

        if (debug > 1) System.out.println("Matrix multiplication.");
        Matrix<Integer> matrix3 = matrix1.multiply(matrix2);
        if (debug > 1) System.out.println(matrix3);
        if (!matrix3.equals(result1)) {
            System.err.println("Matrix multiplication error. matrix3="+matrix3+" result1"+result1);
            return false;
        }

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Integer> matrix4 = new Matrix<Integer>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, counter++);

        // Result of subtraction
        Integer[][] array2 = new Integer[][]{{0,0},
                                             {0,0}};
        Matrix<Integer> result2 = new Matrix<Integer>(2,2,array2);

        if (debug > 1) System.out.println("Matrix subtraction.");
        Matrix<Integer> matrix5 = matrix4.subtract(matrix4);
        if (debug > 1) System.out.println(matrix5);
        if (!matrix5.equals(result2)) {
            System.err.println("Matrix multiplication error. matrix5="+matrix5+" result2"+result2);
            return false;
        }

        // Result of addition
        Integer[][] array3 = new Integer[][]{{0,2},
                                             {4,6}};
        Matrix<Integer> result3 = new Matrix<Integer>(2,2,array3);

        if (debug > 1) System.out.println("Matrix addition.");
        Matrix<Integer> matrix6 = matrix4.add(matrix4);
        if (debug > 1) System.out.println(matrix6);
        if (!matrix6.equals(result3)) {
            System.err.println("Matrix multiplication error. matrix6="+matrix6+" result3"+result3);
            return false;
        }

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

        // Result of multiplication
        Integer[][] array4 = new Integer[][]{{7,10},
                                             {15,22}};
        Matrix<Integer> result4 = new Matrix<Integer>(2,2,array4);

        if (debug > 1) System.out.println("Matrix multiplication.");
        Matrix<Integer> matrix9 = matrix7.multiply(matrix8);
        if (debug > 1) System.out.println(matrix9);
        if (!matrix9.equals(result4)) {
            System.err.println("Matrix multiplication error. matrix9="+matrix9+" result4"+result4);
            return false;
        }

        System.out.println("Matrix worked correctly.");

        return true;
    }

    private static boolean testPatriciaTrie() {
        String bstName = "PatriciaTrie";
        PatriciaTrie<String> bst = new PatriciaTrie<String>();
        Collection<String> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.String,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.String,bstName)) return false;
        return true;
    }

    private static boolean testQuadTree() {

        System.out.println("QuadTree.");

        int size = 16000;

        java.util.Set<QuadTree.XYPoint> set = new java.util.HashSet<QuadTree.XYPoint>(size);
        while (set.size() < size) {
            float x = RANDOM.nextInt(size);
            float y = RANDOM.nextInt(size);
            QuadTree.XYPoint xyPoint = new QuadTree.XYPoint(x,y);
            set.add(xyPoint);
        }

        java.util.List<QuadTree.XYPoint> query = new java.util.ArrayList<QuadTree.XYPoint>(size);
        for (int j=0; j<size; j++) {
            float x = RANDOM.nextInt(size);
            float y = RANDOM.nextInt(size);
            QuadTree.XYPoint xyPoint = new QuadTree.XYPoint(x,y);
            query.add(xyPoint);   
        }

        long beforeInsert;
        long beforeQuery;
        long beforeMemory;
        long beforeTreeQuery;
        long beforeRemove;
        long afterInsert;
        long afterQuery;
        long afterMemory;
        long afterTreeQuery;
        long afterRemove;
        long insertTime;
        long queryTime;
        long removeTime;
        long treeMemory;
        long treeQuery;

        {   // Point based quad tree
            QuadTree.PointRegionQuadTree<QuadTree.XYPoint> tree = new QuadTree.PointRegionQuadTree<QuadTree.XYPoint>(0,0,size,size);

            beforeMemory = DataStructures.getMemoryUse();
            {
                beforeInsert = System.nanoTime();
                for (QuadTree.XYPoint p : set)
                    tree.insert(p.getX(), p.getY());
                afterInsert = System.nanoTime();
                insertTime = afterInsert - beforeInsert;
                if (debug > 1) System.out.println("PointRegionQuadTree insertTime="+insertTime/100000d+" ms");
            }
            afterMemory = DataStructures.getMemoryUse();
            treeMemory = afterMemory - beforeMemory;
            if (debug > 1) System.out.println("PointRegionQuadTree treeMemory="+treeMemory);

            if (debug > 1) System.out.println(tree);

            // We should find all points here (tests structure)
            for (QuadTree.XYPoint p : set) {
                java.util.List<QuadTree.XYPoint> result = tree.queryRange(p.getX(), p.getY(), 1, 1);
                if (result.size()<=0) {
                    System.err.println("Quad tree queryRange error. result="+result);
                    return false;
                }
            }

            // We may not find all points here (tests query speed)
            {
                beforeQuery = System.nanoTime();
                for (QuadTree.XYPoint p : query)
                    tree.queryRange(p.getX(), p.getY(), 1, 1);
                afterQuery = System.nanoTime();
                queryTime = afterQuery - beforeQuery;
                if (debug > 1) System.out.println("PointRegionQuadTree queryTime="+queryTime/100000d+" ms");
            }

            // Result set should not contain duplicates
            beforeTreeQuery = System.nanoTime();
            java.util.List<QuadTree.XYPoint> result = tree.queryRange(0, 0, size, size);
            afterTreeQuery = System.nanoTime();
            treeQuery = afterTreeQuery - beforeTreeQuery;
            if (debug > 1) System.out.println("PointRegionQuadTree wholeTreeQuery="+treeQuery/100000d+" ms");

            Collections.sort(result);
            QuadTree.XYPoint prev = null;
            for (QuadTree.XYPoint p : result) {
                if (prev!=null && prev.equals(p)) {
                    System.err.println("Quad tree compare error. p="+p+" prev="+prev+" result="+result);
                    return false;
                }
                prev = p;
            }

            {   // Remove all
                beforeRemove = System.nanoTime();
                for (QuadTree.XYPoint p : set) {
                    boolean removed = tree.remove(p.getX(), p.getY());
                    if (!removed) {
                        System.err.println("Quad tree remove error. removed="+removed+" x="+p.getX()+" y="+p.getY());
                        return false;
                    }
                }
                afterRemove = System.nanoTime();
                removeTime = afterRemove - beforeRemove;
                if (debug > 1) System.out.println("PointRegionQuadTree removeTime="+removeTime/100000d+" ms");
            }
        }

        {   // Rectangle base quad-tree
            QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox> tree = new QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox>(0,0,size,size,10,10);
            beforeMemory = DataStructures.getMemoryUse();
            {
                beforeInsert = System.nanoTime();
                for (QuadTree.XYPoint p : set)
                    tree.insert(p.getX(), p.getY(), 1, 1);
                afterInsert = System.nanoTime();
                insertTime = afterInsert - beforeInsert;
                if (debug > 1) System.out.println("MxCifQuadTree insertTime="+insertTime/100000d+" ms");
            }
            afterMemory = DataStructures.getMemoryUse();
            treeMemory = afterMemory - beforeMemory;
            if (debug > 1) System.out.println("MxCifQuadTree treeMemory="+treeMemory);

            if (debug > 1) System.out.println(tree);

            // We should find all points here
            for (QuadTree.XYPoint p : set) {
                java.util.List<QuadTree.AxisAlignedBoundingBox> result = tree.queryRange(p.getX(), p.getY(), 1, 1);
                if (result.size()<=0) {
                    System.err.println("Quad tree queryRange error. result="+result);
                    return false;
                }
            }

            {   // We may not find all points here
                beforeQuery = System.nanoTime();
                for (QuadTree.XYPoint p : query)
                    tree.queryRange(p.getX(), p.getY(), 1, 1);
                afterQuery = System.nanoTime();
                queryTime = afterQuery - beforeQuery;
                if (debug > 1) System.out.println("MxCifQuadTree queryTime="+queryTime/100000d+" ms");
            }

            // Result set should not contain duplicates
            beforeTreeQuery = System.nanoTime();
            java.util.List<QuadTree.AxisAlignedBoundingBox> result = tree.queryRange(0, 0, size, size);
            afterTreeQuery = System.nanoTime();
            treeQuery = afterTreeQuery - beforeTreeQuery;
            if (debug > 1) System.out.println("MxCifQuadTree wholeTreeQuery="+treeQuery/100000d+" ms");

            Collections.sort(result);
            QuadTree.AxisAlignedBoundingBox prev = null;
            for (QuadTree.AxisAlignedBoundingBox p : result) {
                if (prev!=null && prev.equals(p)) {
                    System.err.println("Quad tree compare error. p="+p+" prev="+prev+" result="+result);
                    return false; 
                }
                prev = p;
            }

            {   // Remove all
                beforeRemove = System.nanoTime();
                for (QuadTree.XYPoint p : set) {
                    boolean removed = tree.remove(p.getX(), p.getY(), 1, 1);
                    if (!removed) {
                        System.err.println("Quad tree remove error. removed="+removed+" x="+p.getX()+" y="+p.getY());
                        return false;
                    }
                }
                afterRemove = System.nanoTime();
                removeTime = afterRemove - beforeRemove;
                if (debug > 1) System.out.println("MxCifQuadTree removeTime="+removeTime/100000d+" ms");
            }
        }

        System.out.println("QuadTree worked correctly.");

        return true;
    }

    private static boolean testArrayQueue() {
        String aName = "Queue [array]";
        Queue.ArrayQueue<Integer> aQueue = new Queue.ArrayQueue<Integer>();
        Collection<Integer> aCollection = aQueue.toCollection();

        if((validateStructure||validateContents) && !testQueue(aQueue,aName)) return false;
        if(!testJavaCollection(aCollection,Type.Integer,aName)) return false;
        return true;
    }

    private static boolean testLinkedQueue() {
        String lName = "Queue [linked]";
        Queue.LinkedQueue<Integer> lQueue = new Queue.LinkedQueue<Integer>();
        Collection<Integer> lCollection = lQueue.toCollection();

        if((validateStructure||validateContents) && !testQueue(lQueue,lName)) return false;
        if(!testJavaCollection(lCollection,Type.Integer,lName)) return false;
        return true;
    }

    private static boolean testRadixTrie() {
        String mapName = "RadixTrie";
        RadixTrie<String,Integer> map = new RadixTrie<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if((validateStructure||validateContents) && !testMap(map,Type.String,mapName)) return false;
        if(!testJavaMap(jMap,Type.String,mapName)) return false;
        return true;
    }

    private static boolean testRedBlackTree() {
        String bstName = "Red-Black Tree";
        RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.Integer,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.Integer,bstName)) return false;

        return true;
    }

    private static boolean testSegmentTree() {
        System.out.println("Segment Tree.");

        {   // Quadrant Segment tree
            if (debug>1) System.out.println("Quadrant Segment Tree.");
            java.util.List<SegmentTree.Data.QuadrantData> segments = new ArrayList<SegmentTree.Data.QuadrantData>();
            segments.add(new SegmentTree.Data.QuadrantData(0, 1, 0, 0, 0)); // first point in the 0th quadrant
            segments.add(new SegmentTree.Data.QuadrantData(1, 0, 1, 0, 0)); // second point in the 1st quadrant
            segments.add(new SegmentTree.Data.QuadrantData(2, 0, 0, 1, 0)); // third point in the 2nd quadrant
            segments.add(new SegmentTree.Data.QuadrantData(3, 0, 0, 0, 1)); // fourth point in the 3rd quadrant
            FlatSegmentTree<SegmentTree.Data.QuadrantData> tree = new FlatSegmentTree<SegmentTree.Data.QuadrantData>(segments);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.QuadrantData query = tree.query(0, 3);
            if (debug > 1) System.out.println("0->3: " + query + "\n");
            if (query.quad0!=1 || query.quad1!=1 || query.quad2!=1 || query.quad3!=1) {
                System.err.println("Quad tree query error. query=0->3 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(2, 3);
            if (debug > 1) System.out.println("2->3: " + query + "\n");
            if (query.quad0!=0 || query.quad1!=0 || query.quad2!=1 || query.quad3!=1) {
                System.err.println("Quad tree query error. query=2->3 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(0, 2);
            if (debug > 1) System.out.println("0->2: " + query + "\n");
            if (query.quad0!=1 || query.quad1!=1 || query.quad2!=1 || query.quad3!=0) {
                System.err.println("Quad tree query error. query=0->2 result="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        {   // Range Maximum Segment tree
            if (debug > 1) System.out.println("Range Maximum Segment Tree.");
            java.util.List<SegmentTree.Data.RangeMaximumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<Integer>>();
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(0,     (Integer) 4));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(1,     (Integer) 2));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(2,     (Integer) 6));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(3,     (Integer) 3));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(4,     (Integer) 1));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(5,     (Integer) 5));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(6,     (Integer) 0));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(7, 17, 7));
            segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(21,    (Integer) 10));
            FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Integer>>(segments, 3);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.RangeMaximumData<Integer> query = tree.query(0, 7);
            if (debug > 1) System.out.println("0->7: " + query + "\n");
            if (query.maximum!=7) {
                System.err.println("Segment tree query error. query=0->7 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(0, 21);
            if (debug > 1) System.out.println("0->21: " + query + "\n");
            if (query.maximum!=10) {
                System.err.println("Segment tree query error. query=0->21 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(2, 5);
            if (debug > 1) System.out.println("2->5: " + query + "\n");
            if (query.maximum!=6) {
                System.err.println("Segment tree query error. query=2->5 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(7); // stabing
            if (debug > 1) System.out.println("7: " + query + "\n");
            if (query.maximum!=7) {
                System.err.println("Segment tree query error. query=7 result="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        {   // Range Minimum Segment tree
            if (debug > 1) System.out.println("Range Minimum Segment Tree.");
            java.util.List<SegmentTree.Data.RangeMinimumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<Integer>>();
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(0,  (Integer) 4));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(1,  (Integer) 2));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(2,  (Integer) 6));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(3,  (Integer) 3));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(4,  (Integer) 1));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(5,  (Integer) 5));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(6,  (Integer) 0));
            segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(17, (Integer) 7));
            FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Integer>>(segments, 5);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.RangeMinimumData<Integer> query = tree.query(0, 7);
            if (debug > 1) System.out.println("0->7: " + query + "\n");
            if (query.minimum!=0) {
                System.err.println("Segment tree query error. query=0->7 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(0, 17);
            if (debug > 1) System.out.println("0->17: " + query + "\n");
            if (query.minimum!=0) {
                System.err.println("Segment tree query error. query=0->17 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1, 3);
            if (debug > 1) System.out.println("1->3: " + query + "\n");
            if (query.minimum!=2) {
                System.err.println("Segment tree query error. query=1->3 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(7); // stabbing
            if (debug > 1) System.out.println("7: " + query + "\n");
            if (query.minimum!=null) {
                System.err.println("Segment tree query error. query=7 result="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        {   // Range Sum Segment tree
            if (debug > 1) System.out.println("Range Sum Segment Tree.");
            java.util.List<SegmentTree.Data.RangeSumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeSumData<Integer>>();
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(0,  (Integer) 4));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(1,  (Integer) 2));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(2,  (Integer) 6));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(3,  (Integer) 3));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(4,  (Integer) 1));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(5,  (Integer) 5));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(6,  (Integer) 0));
            segments.add(new SegmentTree.Data.RangeSumData<Integer>(17, (Integer) 7));
            FlatSegmentTree<SegmentTree.Data.RangeSumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<Integer>>(segments, 10);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.RangeSumData<Integer> query = tree.query(0, 8);
            if (debug > 1) System.out.println("0->8: " + query + "\n");
            if (query.sum != 21) {
                System.err.println("Segment tree query error. query=0->8 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(0, 17);
            if (debug > 1) System.out.println("0->17: " + query + "\n");
            if (query.sum != 28) {
                System.err.println("Segment tree query error. query=0->17 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(2, 5);
            if (debug > 1) System.out.println("2->5: " + query + "\n");
            if (query.sum != 15) {
                System.err.println("Segment tree query error. query=2->5 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(10, 17);
            if (debug > 1) System.out.println("10->17: " + query + "\n");
            if (query.sum != 7) {
                System.err.println("Segment tree query error. query=10->17 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(16); // stabbing
            if (debug > 1) System.out.println("16: " + query + "\n");
            if (query.sum != null) {
                System.err.println("Segment tree query error. query=16 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(17); // stabbing
            if (debug > 1) System.out.println("17: " + query + "\n");
            if (query.sum != 7) {
                System.err.println("Segment tree query error. query=17 result="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        {   // Interval Segment tree
            if (debug > 1) System.out.println("Interval Segment Tree.");
            final String RED        = "RED";
            final String ORANGE     = "ORANGE";
            final String GREEN      = "GREEN";
            final String DARK_GREEN = "DARK_GREEN";
            final String BLUE       = "BLUE";
            final String PURPLE     = "PURPLE";
            final String BLACK      = "BLACK";
            java.util.List<SegmentTree.Data.IntervalData<String>> segments = new ArrayList<SegmentTree.Data.IntervalData<String>>();
            segments.add((new SegmentTree.Data.IntervalData<String>(2,  6,  RED)));
            segments.add((new SegmentTree.Data.IntervalData<String>(3,  5,  ORANGE)));
            segments.add((new SegmentTree.Data.IntervalData<String>(4,  11, GREEN)));
            segments.add((new SegmentTree.Data.IntervalData<String>(5,  10, DARK_GREEN)));
            segments.add((new SegmentTree.Data.IntervalData<String>(8,  12, BLUE)));
            segments.add((new SegmentTree.Data.IntervalData<String>(9,  14, PURPLE)));
            segments.add((new SegmentTree.Data.IntervalData<String>(13, 15, BLACK)));
            DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(segments);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.IntervalData<String> query = tree.query(2); // Stabbing
            if (debug > 1) System.out.println("2: " + query);
            if (!query.getData().contains(RED)) {
                System.err.println("Segment tree query error. query=2 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(4); // Stabbing query
            if (debug > 1) System.out.println("4: " + query);
            if (!query.getData().contains(GREEN)) {
                System.err.println("Segment tree query error. query=4 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(9); // Stabbing query
            if (debug > 1) System.out.println("9: " + query);
            if (!query.getData().contains(PURPLE)) {
                System.err.println("Segment tree query error. query=9 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1, 16); // Range query
            if (debug > 1) System.out.println("1->16: " + query);
            if (!query.getData().contains(RED) ||
                !query.getData().contains(ORANGE) ||
                !query.getData().contains(GREEN) ||
                !query.getData().contains(DARK_GREEN) ||
                !query.getData().contains(BLUE) ||
                !query.getData().contains(PURPLE) ||
                !query.getData().contains(BLACK)
            ) {
                System.err.println("Segment tree query error. query=1->16 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(7, 14); // Range query
            if (debug > 1) System.out.println("7->14: " + query);
            if (!query.getData().contains(GREEN) ||
                !query.getData().contains(DARK_GREEN) ||
                !query.getData().contains(BLUE) ||
                !query.getData().contains(PURPLE) ||
                !query.getData().contains(BLACK)
            ) {
                System.err.println("Segment tree query error. query=7->14 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(14, 15); // Range query
            if (debug > 1) System.out.println("14->15: " + query);
            if (!query.getData().contains(PURPLE) ||
                !query.getData().contains(BLACK)
            ) {
                System.err.println("Segment tree query error. query=14->15 result="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        {   // Lifespan Interval Segment tree
            if (debug > 1) System.out.println("Lifespan Interval Segment Tree.");
            final String stravinsky = "Stravinsky";
            final String schoenberg = "Schoenberg";
            final String grieg      = "Grieg";
            final String schubert   = "Schubert";
            final String mozart     = "Mozart";
            final String schuetz    = "Schuetz";
            java.util.List<SegmentTree.Data.IntervalData<String>> segments = new ArrayList<SegmentTree.Data.IntervalData<String>>();
            segments.add((new SegmentTree.Data.IntervalData<String>(1888, 1971, stravinsky)));
            segments.add((new SegmentTree.Data.IntervalData<String>(1874, 1951, schoenberg)));
            segments.add((new SegmentTree.Data.IntervalData<String>(1843, 1907, grieg)));
            segments.add((new SegmentTree.Data.IntervalData<String>(1779, 1828, schubert)));
            segments.add((new SegmentTree.Data.IntervalData<String>(1756, 1791, mozart)));
            segments.add((new SegmentTree.Data.IntervalData<String>(1585, 1672, schuetz)));
            DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(segments, 25);
            if (debug > 1) System.out.println(tree);

            SegmentTree.Data.IntervalData<String> query = tree.query(1890); // Stabbing
            if (debug > 1) System.out.println("1890: " + query);
            if (!query.getData().contains(stravinsky) ||
                !query.getData().contains(schoenberg) ||
                !query.getData().contains(grieg)
            ) {
                System.err.println("Segment tree query error. query=1890 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1909); // Stabbing query
            if (debug > 1) System.out.println("1909: " + query);
            if (!query.getData().contains(stravinsky) ||
                !query.getData().contains(schoenberg)
            ) {
                System.err.println("Segment tree query error. query=1909 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1585); // Stabbing query
            if (debug > 1) System.out.println("1585: " + query);
            if (!query.getData().contains(schuetz)) {
                System.err.println("Segment tree query error. query=1585 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1792, 1903); // Range query
            if (debug > 1) System.out.println("1792->1903: " + query);
            if (!query.getData().contains(stravinsky) ||
                !query.getData().contains(schoenberg) ||
                !query.getData().contains(grieg) || 
                !query.getData().contains(schubert)
            ) {
                System.err.println("Segment tree query error. query=1792->1903 result="+query);
                handleError(tree);
                return false;
            }

            query = tree.query(1776, 1799); // Range query
            if (debug > 1) System.out.println("1776->1799: " + query);
            if (!query.getData().contains(schubert) ||
                !query.getData().contains(mozart)
            ) {
                System.err.println("Segment tree query error. query=1776->1799 result="+query);
                handleError(tree);
                return false;
            }

            if (debug > 1) System.out.println();
        }

        System.out.println("Segment Tree worked correctly.");

        return true;
    }

    private static boolean testJavaSkipList() {
        String sName = "Java's SkipList";
        NavigableSet<Integer> sList = new ConcurrentSkipListSet<Integer>();
        Collection<Integer> lCollection = sList;
        if(!testJavaCollection(lCollection,Type.Integer,sName)) return false;
        return true;
    }

    private static boolean testSkipList() {
        String sName = "SkipList";
        SkipList<Integer> sList = new SkipList<Integer>();
        Collection<Integer> lCollection = sList.toCollection();

        if((validateStructure||validateContents) && !testSet(sList,sName)) return false;
        if(!testJavaCollection(lCollection,Type.Integer,sName)) return false;
        return true;
    }

    private static boolean testSplayTree() {
        String bstName = "Splay Tree";
        BinarySearchTree<Integer> bst = new SplayTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.Integer,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.Integer,bstName)) return false;

        return true;
    }

    private static boolean testArrayStack() {
        String aName = "Stack [array]";
        Stack.ArrayStack<Integer> aStack = new Stack.ArrayStack<Integer>();
        Collection<Integer> aCollection = aStack.toCollection();

        if((validateStructure||validateContents) && !testStack(aStack,aName)) return false;
        if(!testJavaCollection(aCollection,Type.Integer,aName)) return false;
        return true;
    }

    private static boolean testLinkedStack() {
        String lName = "Stack [linked]";
        Stack.LinkedStack<Integer> lStack = new Stack.LinkedStack<Integer>();
        Collection<Integer> lCollection = lStack.toCollection();

        if((validateStructure||validateContents) && !testStack(lStack,lName)) return false;
        if(!testJavaCollection(lCollection,Type.Integer,lName)) return false;
        return true;
    }

    private static boolean testSuffixTree() {
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

        return true;
    }

    private static boolean testSuffixTrie() {
        System.out.println("Suffix Trie.");

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

        System.out.println("Suffix Trie worked correctly.");

        if (debug > 1) System.out.println();

        return true;
    }

    private static boolean testTreap() {
        String bstName = "Treap";
        BinarySearchTree<Integer> bst = new Treap<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.Integer,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.Integer,bstName)) return false;
        return true;
    }

    private static boolean testTreeMap() {
        String mapName = "TreeMap";
        TreeMap<String,Integer> map = new TreeMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if((validateStructure||validateContents) && !testMap(map,Type.String,mapName)) return false;
        if(!testJavaMap(jMap,Type.Integer,mapName)) return false;
        return true;
    }

    private static boolean testTrie() {
        String bstName = "Trie";
        Trie<String> bst = new Trie<String>();
        Collection<String> bstCollection = bst.toCollection();

        if((validateStructure||validateContents) && !testTree(bst,Type.String,bstName)) return false;
        if(!testJavaCollection(bstCollection,Type.String,bstName)) return false;
        return true;
    }

    private static boolean testTrieMap() {
        String mapName = "TrieMap";
        TrieMap<String,Integer> map = new TrieMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if((validateStructure||validateContents) && !testMap(map,Type.String,mapName)) return false;
        if(!testJavaMap(jMap,Type.String,mapName)) return false;
        return true;
    }

    private static boolean testJavaSkipListMap() {
        String mapName = "Java's SkipListMap";
        ConcurrentSkipListMap<String,Integer> map = new ConcurrentSkipListMap<String,Integer>();
        if(!testJavaMap(map,Type.Integer,mapName)) return false;
        return true;
    }

    private static boolean testSkipListMap() {
        String mapName = "SkipListMap";
        SkipListMap<String,Integer> map = new SkipListMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if((validateStructure||validateContents) && !testMap(map,Type.String,mapName)) return false;
        if(!testJavaMap(jMap,Type.Integer,mapName)) return false;
        return true;
    }

    public static <K,V> boolean testMap(IMap<K,V> map, Type keyType, String name) {
        for (int i = 0; i < unsorted.length; i++) {
            Integer item = unsorted[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            V added = map.put(k,v);
            if (validateStructure && (!map.validate() || (map.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(map);
                return false;
            }
            if (validateContents && (added!=null || !map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists.");
                handleError(map);
                return false;
            }
        }

        K invalidKey = null;
        if (keyType == Type.Integer) {
            invalidKey = (K)INVALID;
        } else if (keyType == Type.String) {
            invalidKey = (K)String.valueOf(INVALID);
        }
        boolean contains = map.contains(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(map);
            return false;
        }

        for (int i = 0; i < unsorted.length; i++) {
            Integer item = unsorted[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if (validateStructure && (!map.validate() || (map.size() != (unsorted.length-(i+1))))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(map);
                return false;
            }
            if (validateContents && map.contains(k)) {
                System.err.println(name+" YIKES!! " + item + " still exists.");
                handleError(map);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            Integer item = unsorted[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            V added = map.put(k,v);
            if (validateStructure && (!map.validate() || (map.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(map);
                return false;
            }
            if (validateContents && (added!=null || !map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists.");
                handleError(map);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            Integer item = unsorted[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if (validateStructure && (!map.validate() || (map.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(map);
                return false;
            }
            if (validateContents && (removed==null || map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " still exists.");
                handleError(map);
                return false;
            }
        }
        for (int i = quarter; i < unsorted.length; i++) {
            Integer item = unsorted[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            V added = map.put(k,v);
            if (validateStructure && (!map.validate() || (map.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(map);
                return false;
            }
            if (validateContents && (added!=null || !map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists.");
                handleError(map);
                return false;
            }
        }
        for (int i = unsorted.length-1; i >= 0; i--) {
            Integer item = unsorted[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if (validateStructure && (!map.validate() || (map.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(map);
                return false;
            }
            if (validateContents && (removed==null || map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " still exists.");
                handleError(map);
                return false;
            }
        }

        if (validateStructure && (map.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(map);
            return false;
        }

        return true;
    }

    /**
     * Tests the tree operations
     * 
     * @param tree to test.
     * @return True is works as a tree structure.
     */
    public static <T extends Comparable<T>> boolean testTree(ITree<T> tree, Type type, String name) {
        for (int i = 0; i < unsorted.length; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            boolean added = tree.add(item);
            if (validateStructure && (!tree.validate() || (tree.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(tree);
                return false;
            }
            if (validateContents && (!added || !tree.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(tree);
                return false;
            }
        }

        T invalidItem = null;
        if (type == Type.Integer) {
            invalidItem = (T)INVALID;
        } else if (type == Type.String) {
            invalidItem = (T)String.valueOf(INVALID);
        }
        boolean contains = tree.contains(invalidItem);
        T removed = tree.remove(invalidItem);
        if (contains || removed!=null) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(tree);
            return false;
        }

        int size = tree.size();
        for (int i = 0; i < size; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (validateStructure && (!tree.validate()  || (tree.size() != unsorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(tree);
                return false;
            }
            if (validateContents && (removed==null || tree.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been removed.");
                handleError(tree);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            boolean added = tree.add(item);
            if (validateStructure && (!tree.validate() || (tree.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(tree);
                return false;
            }
            if (validateContents && (!added || !tree.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(tree);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (validateStructure && (!tree.validate() || (tree.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(tree);
                return false;
            }
            if (validateContents && (removed==null || tree.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(tree);
                return false;
            }
        }
        for (int i = quarter; i < unsorted.length; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            boolean added = tree.add(item);
            if (validateStructure && (!tree.validate() || (tree.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(tree);
                return false;
            }
            if (validateContents && (!added || !tree.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(tree);
                return false;
            }
        }
        for (int i = unsorted.length-1; i >= 0; i--) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (validateStructure && (!tree.validate() || (tree.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(tree);
                return false;
            }
            if (validateContents && (removed==null || tree.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(tree);
                return false;
            }
        }

        if (validateStructure && (tree.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(tree);
            return false;
        }

        return true;
    }

    /**
     * Tests the actual heap like operations
     * 
     * @param heap to test.
     * @return True is works as a heap structure.
     */
    public static <T extends Comparable<T>> boolean testHeap(IHeap<T> heap, String name, BinaryHeap.Type type) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = heap.add(item);
            if (validateStructure && (!heap.validate() || (heap.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(heap);
                return false;
            }
            if (validateContents && (!added || !heap.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(heap);
                return false;
            }
        }

        boolean contains = heap.contains((T)INVALID);
        T removed = heap.remove((T)INVALID);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(heap);
            return false;
        }

        int size = heap.size();
        for (int i = 0; i < size; i++) {
            T item = heap.removeHead();
            T correct = (T)((type == BinaryHeap.Type.MIN)?sorted[i]:sorted[sorted.length-(i+1)]);
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                handleError(heap);
                return false;
            }
            if (validateStructure && (!heap.validate() || (heap.size() != unsorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(heap);
                return false;
            }
            if (validateContents && (item==null || heap.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(heap);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        Integer[] halfArray = Arrays.copyOf(unsorted, half);
        Arrays.sort(halfArray);
        Integer[] quarterArray = new Integer[quarter];
        Integer[] sortedQuarterArray = new Integer[quarter]; //Needed for binary search
        for (int i=0; i<quarter; i++) {
            quarterArray[i] = (type == BinaryHeap.Type.MIN)?halfArray[i]:halfArray[halfArray.length-(i+1)];
            sortedQuarterArray[i] = quarterArray[i];
        }
        Arrays.sort(sortedQuarterArray);
        int idx = 0;
        Integer[] threeQuartersArray = new Integer[unsorted.length-(half-quarter)];
        for (Integer i : unsorted) {
        	int index = Arrays.binarySearch(sortedQuarterArray, i);
        	if (type == BinaryHeap.Type.MIN) {
            	if (index>=0) {
            	    threeQuartersArray[idx++] = i;	
            	} else {
            	    index = Arrays.binarySearch(halfArray, i);
            	    if (index<0) threeQuartersArray[idx++] = i;
            	}
        	} else {
            	if (index>=0) {
            	    threeQuartersArray[idx++] = i;
            	} else {
            	    index = Arrays.binarySearch(halfArray, i);
            	    if (index<0) threeQuartersArray[idx++] = i;
            	}
        	}
        }
        for (int i = 0; i < half; i++) {
            T item = (T)unsorted[i];
            boolean added = heap.add(item);
            if (validateStructure && (!heap.validate() || (heap.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(heap);
                return false;
            }
            if (validateContents && (!added || !heap.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(heap);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = heap.removeHead();
            T correct = (T)quarterArray[i];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                handleError(heap);
                return false;
            }
            if (validateStructure && (!heap.validate() || (heap.size() != half-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(heap);
                return false;
            }
            if (validateContents && (item==null || heap.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(heap);
                return false;
            }
        }
        for (int i = 0; i < threeQuartersArray.length; i++) {
            T item = (T)threeQuartersArray[i];
            boolean added = heap.add(item);
            if (validateStructure && (!heap.validate() || (heap.size() != (half-quarter)+(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(heap);
                return false;
            }
            if (validateContents && (!added || !heap.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(heap);
                return false;
            }
        }
        for (int i = 0; i < sorted.length; i++) {
            T item = heap.removeHead();
            T correct = (T)((type == BinaryHeap.Type.MIN)?sorted[i]:sorted[sorted.length-(i+1)]);
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                handleError(heap);
                return false;
            }
            if (validateStructure && (!heap.validate() || (heap.size() != sorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(heap);
                return false;
            }
            if (validateContents && (item==null || heap.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(heap);
                return false;
            }
        }

        if (validateStructure && (heap.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(heap);
            return false;
        }

        return true;
    }

    /**
     * Tests the actual queue like (FIFO) operations
     * 
     * @param queue to test.
     * @return True is works as a FIFO structure.
     */
    public static <T extends Comparable<T>> boolean testQueue(IQueue<T> queue, String name) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = queue.offer(item);
            if (validateStructure && (!queue.validate() || (queue.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (!added || !queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(queue);
                return false;
            }
        }

        boolean contains = queue.contains((T)INVALID);
        boolean removed = queue.remove((T)INVALID);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(queue);
            return false;
        }

        int size = queue.size();
        for (int i = 0; i < size; i++) {
            T item = queue.poll();
            T correct = (T)unsorted[i];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match FIFO item.");
                handleError(queue);
                return false;
            }
            if (validateStructure && (!queue.validate() || (queue.size() != unsorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (item==null || queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(queue);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        int changeOver = half-quarter;
        for (int i = 0; i < half; i++) {
            T item = (T)unsorted[i];
            boolean added = queue.offer(item);
            if (validateStructure && (!queue.validate() || (queue.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (!added || !queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(queue);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = queue.poll();
            T correct = (T)unsorted[i];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match FIFO item.");
                handleError(queue);
                return false;
            }
            if (validateStructure && (!queue.validate() || (queue.size() != (half-(i+1))))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (item==null || queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(queue);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = (T)unsorted[i];
            boolean added = queue.offer(item);
            if (validateStructure && (!queue.validate() || (queue.size() != ((half-quarter)+(i+1))))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (!added || !queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(queue);
                return false;
            }
        }
        for (int i = half; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = queue.offer(item);
            if (validateStructure && (!queue.validate() || (queue.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (!added || !queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(queue);
                return false;
            }
        }
        for (int i = 0; i < unsorted.length; i++) {
            T item = queue.poll();
            int idx = i;
            if (idx < changeOver) {
            	idx = quarter+i;
            } else if (idx>=changeOver && idx<half) {
            	idx = i-changeOver;
            }
            T correct = (T)unsorted[idx];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match FIFO item.");
                handleError(queue);
                return false;
            }
            if (validateStructure && (!queue.validate() || (queue.size() != (unsorted.length-(i+1))))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(queue);
                return false;
            }
            if (validateContents && (item==null || queue.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(queue);
                return false;
            }
        }

        if (validateStructure && (queue.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(queue);
            return false;
        }

        return true;
    }

    /**
     * Tests the actual stack like (LIFO) operations
     * 
     * @param stack to test.
     * @return True is works as a LIFO structure.
     */
    public static <T extends Comparable<T>> boolean testStack(IStack<T> stack, String name) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = stack.push(item);
            if (validateStructure && (!stack.validate() || (stack.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(stack);
                return false;
            }
            if (validateContents && (!added || item==null || !stack.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(stack);
                return false;
            }
        }

        boolean contains = stack.contains((T)INVALID);
        boolean removed = stack.remove((T)INVALID);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(stack);
            return false;
        }

        int size = stack.size();
        for (int i = 0; i < size; i++) {
            T item = stack.pop();
            T correct = (T)unsorted[unsorted.length-(i+1)];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                handleError(stack);
                return false;
            }
            if (validateStructure && (!stack.validate() || (stack.size() != unsorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(stack);
                return false;
            }
            if (validateContents && (item==null || stack.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(stack);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            T item = (T)unsorted[i];
            boolean added = stack.push(item);
            if (validateStructure && (!stack.validate() || (stack.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(stack);
                return false;
            }
            if (validateContents && (!added || item==null || !stack.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(stack);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = stack.pop();
            T correct = (T)unsorted[i];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                handleError(stack);
                return false;
            }
            if (validateStructure && (!stack.validate() || (stack.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(stack);
                return false;
            }
            if (validateContents && (item==null || stack.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(stack);
                return false;
            }
        }
        for (int i = quarter; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = stack.push(item);
            if (validateStructure && (!stack.validate() || (stack.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(stack);
                return false;
            }
            if (validateContents && (!added || item==null || !stack.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(stack);
                return false;
            }
        }
        for (int i = unsorted.length-1; i >= 0; i--) {
            T item = stack.pop();
            T correct = (T)unsorted[i];
            if (validateStructure && (item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                handleError(stack);
                return false;
            }
            if (validateStructure && (!stack.validate() || (stack.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(stack);
                return false;
            }
            if (validateContents && (item==null || stack.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(stack);
                return false;
            }
        }

        if (validateStructure && (stack.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(stack);
            return false;
        }

        return true;
    }

    /**
     * Tests the actual list operations
     * 
     * @param list to test.
     * @return True is works as a list structure.
     */
    public static <T extends Comparable<T>> boolean testList(IList<T> list, String name) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = list.add(item);
            if (validateStructure && (!list.validate() || (list.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(list);
                return false;
            }
            if (validateContents && (!added || !list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(list);
                return false;
            }
        }

        boolean contains = list.contains((T)INVALID);
        boolean removed = list.remove((T)INVALID);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(list);
            return false;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            T item = (T)unsorted[i];
            removed = list.remove(item);
            if (validateStructure && (!list.validate() || (list.size() != unsorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(list);
                return false;
            }
            if (validateContents && (!removed || list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(list);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            T item = (T)unsorted[i];
            boolean added = list.add(item);
            if (validateStructure && (!list.validate() || (list.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(list);
                return false;
            }
            if (validateContents && (!added || !list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(list);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = (T)unsorted[i];
            removed = list.remove(item);
            if (validateStructure && (!list.validate() || (list.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(list);
                return false;
            }
            if (validateContents && (!removed || list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(list);
                return false;
            }
        }
        for (int i = quarter; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = list.add(item);
            if (validateStructure && (!list.validate() || (list.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(list);
                return false;
            }
            if (validateContents && (!added || !list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(list);
                return false;
            }
        }
        for (int i = unsorted.length-1; i >= 0; i--) {
            T item = (T)unsorted[i];
            removed = list.remove(item);
            if (validateStructure && (!list.validate() || (list.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(list);
                return false;
            }
            if (validateContents && (!removed || list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(list);
                return false;
            }
        }

        if (validateStructure && (list.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(list);
            return false;
        }
 
        return true;
    }

    /**
     * Tests the actual set operations
     * 
     * @param set to test.
     * @return True is works as a set structure.
     */
    public static <T extends Comparable<T>> boolean testSet(ISet<T> set, String name) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = set.add(item);
            if (validateStructure && (!set.validate() || (set.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(set);
                return false;
            }
            if (validateContents && (!added || !set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(set);
                return false;
            }
        }

        boolean contains = set.contains((T)INVALID);
        boolean removed = set.remove((T)INVALID);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            handleError(set);
            return false;
        }

        int size = set.size();
        for (int i = 0; i < size; i++) {
            T item = (T)unsorted[i];
            removed = set.remove(item);
            if (validateStructure && (!set.validate() || (set.size() != unsorted.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(set);
                return false;
            }
            if (validateContents && (!removed || set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(set);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            T item = (T)unsorted[i];
            boolean added = set.add(item);
            if (validateStructure && (!set.validate() || (set.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(set);
                return false;
            }
            if (validateContents && (!added || !set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(set);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = (T)unsorted[i];
            removed = set.remove(item);
            if (validateStructure && (!set.validate() || (set.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(set);
                return false;
            }
            if (validateContents && (!removed || set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(set);
                return false;
            }
        }
        for (int i = quarter; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = set.add(item);
            if (validateStructure && (!set.validate() || (set.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(set);
                return false;
            }
            if (validateContents && (!added || !set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                handleError(set);
                return false;
            }
        }
        for (int i = unsorted.length-1; i >= 0; i--) {
            T item = (T)unsorted[i];
            removed = set.remove(item);
            if (validateStructure && (!set.validate() || (set.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                handleError(set);
                return false;
            }
            if (validateContents && (!removed || set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                handleError(set);
                return false;
            }
        }

        if (validateStructure && (set.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            handleError(set);
            return false;
        }
 
        return true;
    }

    public static <T extends Comparable<T>> boolean testJavaCollection(Collection<T> collection, Type type, String name) {
        // Make sure the collection is empty
        if (!collection.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            handleError(collection);
            return false;
        }
        if (collection.size()!=0) {
            System.err.println(name+" initial size() failed.");
            handleError(collection);
            return false;
        }

        long sortedCount = 0;
        long unsortedCount = 0;

        long addTime = 0L;
        long removeTime = 0L;

        long beforeAddTime = 0L;
        long afterAddTime = 0L;
        long beforeRemoveTime = 0L;
        long afterRemoveTime = 0L;

        long memory = 0L;

        long beforeMemory = 0L;
        long afterMemory = 0L;

        long lookupTime = 0L;

        long beforeLookupTime = 0L;
        long afterLookupTime = 0L;

        if (debug > 1) System.out.println(name);
        testNames[testIndex] = name;

        unsortedCount++;
        {   // UNSORTED: Add and remove in order (from index zero to length)
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)unsorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(unsorted[i]);
                }
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" unsorted add failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.nanoTime();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println(name+" unsorted add time = " + (addTime / unsortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            if (validateIterator && !testIterator(collection.iterator())) {
                System.err.println(name+" unsorted iterator failed.");
                handleError(collection);
                return false;
            }

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)unsorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(unsorted[i]);
                }
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" unsorted contains failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" unsorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveTime = 0L;
            afterRemoveTime = 0L;
            if (debugTime) beforeRemoveTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)unsorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(unsorted[i]);
                }
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" unsorted remove failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.nanoTime();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println(name+" unsorted remove time = " + (removeTime / unsortedCount) + " ns");
            }

            if (!collection.isEmpty()) {
                System.err.println(name+" unsorted isEmpty() failed.");
                handleError(collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(collection);
                return false;
            }

            if (validateIterator && collection instanceof java.util.List && !testListIterator(((java.util.List<T>)collection).listIterator())) {
                System.err.println(name+" unsorted list iterator failed.");
                handleError(collection);
                return false;
            }
        }

        unsortedCount++;
        {   // UNSORTED: Add in reverse (from index length-1 to zero) order and then remove in order (from index zero to length)
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)unsorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(unsorted[i]);
                }
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" unsorted add failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterAddTime = System.nanoTime();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println(name+" unsorted add time = " + (addTime / unsortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            if (validateIterator && !testIterator(collection.iterator())) {
                System.err.println(name+" unsorted iterator failed.");
                handleError(collection);
                return false;
            }

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)unsorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(unsorted[i]);
                }
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" unsorted contains failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" unsorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveTime = 0L;
            afterRemoveTime = 0L;
            if (debugTime) beforeRemoveTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)unsorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(unsorted[i]);
                }
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" unsorted remove failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.nanoTime();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println(name+" unsorted remove time = " + (removeTime / unsortedCount) + " ns");
            }

            if (!collection.isEmpty()) {
                System.err.println(name+" unsorted isEmpty() failed.");
                handleError(collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(collection);
                return false;
            }
        }

        long addSortedTime = 0L;
        long removeSortedTime = 0L;

        long beforeAddSortedTime = 0L;
        long afterAddSortedTime = 0L;

        long beforeRemoveSortedTime = 0L;
        long afterRemoveSortedTime = 0L;

        sortedCount++;
        {   // SORTED: Add and remove in order (from index zero to length)
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddSortedTime = 0L;
            afterAddSortedTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)sorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(sorted[i]);
                }
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" sorted add failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.nanoTime();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println(name+" sorted add time = " + (addSortedTime / sortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            if (validateIterator && !testIterator(collection.iterator())) {
                System.err.println(name+" sorted iterator failed.");
                handleError(collection);
                return false;
            }

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)sorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(sorted[i]);
                }
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" sorted contains failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" sorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveSortedTime = 0L;
            afterRemoveSortedTime = 0L;
            if (debugTime) beforeRemoveSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)sorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(sorted[i]);
                }
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" sorted remove failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.nanoTime();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println(name+" sorted remove time = " + (removeSortedTime / sortedCount) + " ns");
            }

            if (!collection.isEmpty()) {
                System.err.println(name+" sorted isEmpty() failed.");
                handleError(collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(collection);
                return false;
            }

            if (validateIterator && collection instanceof java.util.List && !testListIterator(((java.util.List<T>)collection).listIterator())) {
                System.err.println(name+" sorted list iterator failed.");
                handleError(collection);
                return false;
            }
        }

        sortedCount++;
        {   // SORTED: Add in order (from index zero to length) and then remove in reverse (from index length-1 to zero) order 
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddSortedTime = 0L;
            afterAddSortedTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)sorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(sorted[i]);
                }
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" sorted add failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterAddSortedTime = System.nanoTime();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println(name+" sorted add time = " + (addSortedTime / sortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            if (validateIterator && !testIterator(collection.iterator())) {
                System.err.println(name+" sorted iterator failed.");
                handleError(collection);
                return false;
            }

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)sorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(sorted[i]);
                }
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" sorted contains failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" sorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveSortedTime = 0L;
            afterRemoveSortedTime = 0L;
            if (debugTime) beforeRemoveSortedTime = System.nanoTime();
            for (int i = sorted.length - 1; i >= 0; i--) {
                T item = null;
                if (type==Type.Integer) {
                    item = (T)sorted[i];
                } else if (type==Type.String) {
                    item = (T)String.valueOf(sorted[i]);
                }
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" sorted remove failed.");
                    handleError(collection);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.nanoTime();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println(name+" sorted remove time = " + (removeSortedTime / sortedCount) + " ns");
            }

            if (!collection.isEmpty()) {
                System.err.println(name+" sorted isEmpty() failed.");
                handleError(collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(collection);
                return false;
            }

            if (validateIterator && collection instanceof java.util.List && !testListIterator(((java.util.List<T>)collection).listIterator())) {
                System.err.println(name+" sorted list iterator failed.");
                handleError(collection);
                return false;
            }
        }

        if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
        testResults[testIndex][0] += addTime / unsortedCount;
        testResults[testIndex][1] += removeTime / unsortedCount;
        testResults[testIndex][2] += addSortedTime / sortedCount;
        testResults[testIndex][3] += removeSortedTime / sortedCount;
        testResults[testIndex][4] += lookupTime / (unsortedCount + sortedCount);
        testResults[testIndex++][5] += memory / (unsortedCount + sortedCount);

        if (debug > 1) System.out.println();

        return true;
    }

    public static <K,V> boolean testJavaMap(java.util.Map<K,V> map, Type keyType, String name) {
        // Make sure the map is empty
        if (!map.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            handleError(map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" initial size() failed.");
            handleError(map);
            return false;
        }

        long sortedCount = 0;
        long unsortedCount = 0;

        long addTime = 0L;
        long removeTime = 0L;

        long beforeAddTime = 0L;
        long afterAddTime = 0L;
        long beforeRemoveTime = 0L;
        long afterRemoveTime = 0L;

        long memory = 0L;

        long beforeMemory = 0L;
        long afterMemory = 0L;

        long lookupTime = 0L;

        long beforeLookupTime = 0L;
        long afterLookupTime = 0L;

        if (debug > 1) System.out.println(name);
        testNames[testIndex] = name;

        unsortedCount++;
        {
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddTime = System.nanoTime();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println(name+" unsorted add time = " + (addTime / unsortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = null;
            if (keyType == Type.Integer) {
                invalidKey = (K)INVALID;
            } else if (keyType == Type.String) {
                invalidKey = (K)String.valueOf(INVALID);
            }
            boolean contains = map.containsKey(invalidKey);
            V removed = map.remove(invalidKey);
            if (contains || (removed!=null)) {
                System.err.println(name+" unsorted invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            }

            if (debug > 1) System.out.println(map.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (Integer item : unsorted) {
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                map.containsKey(k);
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" unsorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            if (debugTime) beforeRemoveTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                removed = map.remove(k);
                if (removed==null) {
                    System.err.println(name+" unsorted invalidity check. removed=" + removed);
                    return false;
                }
                
            }
            if (debugTime) {
                afterRemoveTime = System.nanoTime();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println(name+" unsorted remove time = " + (removeTime / unsortedCount) + " ns");
            }

            if (validateIterator && !testMapEntrySet(map,keyType)) return false;

            if (!map.isEmpty()) {
                System.err.println(name+" unsorted isEmpty() failed.");
                handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(map);
                return false;
            }
        }

        unsortedCount++;
        {
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                Integer item = unsorted[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddTime = System.nanoTime();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println(name+" unsorted add time = " + (addTime / unsortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = null;
            if (keyType == Type.Integer) {
                invalidKey = (K)INVALID;
            } else if (keyType == Type.String) {
                invalidKey = (K)String.valueOf(INVALID);
            }
            boolean contains = map.containsKey(invalidKey);
            V removed = map.remove(invalidKey);
            if (contains || (removed!=null)) {
                System.err.println(name+" unsorted invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            }

            if (debug > 1) System.out.println(map.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (Integer item : unsorted) {
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                map.containsKey(k);
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" unsorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveTime = 0L;
            afterRemoveTime = 0L;
            if (debugTime) beforeRemoveTime = System.nanoTime();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                Integer item = unsorted[i];
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                removed = map.remove(k);
                if (removed==null) {
                    System.err.println(name+" unsorted invalidity check. removed=" + removed);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveTime = System.nanoTime();
                removeTime += afterRemoveTime - beforeRemoveTime;
                if (debug > 0) System.out.println(name+" unsorted remove time = " + (removeTime / unsortedCount) + " ns");
            }

            if (!map.isEmpty()) {
                System.err.println(name+" unsorted isEmpty() failed.");
                handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(map);
                return false;
            }
        }

        long addSortedTime = 0L;
        long removeSortedTime = 0L;

        long beforeAddSortedTime = 0L;
        long afterAddSortedTime = 0L;

        long beforeRemoveSortedTime = 0L;
        long afterRemoveSortedTime = 0L;

        sortedCount++;
        { // sorted
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddSortedTime = 0L;
            afterAddSortedTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddSortedTime = System.nanoTime();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println(name+" sorted add time = " + (addSortedTime / sortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = null;
            if (keyType == Type.Integer) {
                invalidKey = (K)INVALID;
            } else if (keyType == Type.String) {
                invalidKey = (K)String.valueOf(INVALID);
            }
            boolean contains = map.containsKey(invalidKey);
            V removed = map.remove(invalidKey);
            if (contains || (removed!=null)) {
                System.err.println(name+" sorted invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            }

            if (debug > 1) System.out.println(map.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (Integer item : sorted) {
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                map.containsKey(k);
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" sorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveSortedTime = 0L;
            afterRemoveSortedTime = 0L;
            if (debugTime) beforeRemoveSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                removed = map.remove(k);
                if (removed==null) {
                    System.err.println(name+" unsorted invalidity check. removed=" + removed);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.nanoTime();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println(name+" sorted remove time = " + (removeSortedTime / sortedCount) + " ns");
            }

            if (validateIterator && !testMapEntrySet(map,keyType)) return false;

            if (!map.isEmpty()) {
                System.err.println(name+" sorted isEmpty() failed.");
                handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(map);
                return false;
            }
        }

        sortedCount++;
        { // sorted
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddSortedTime = 0L;
            afterAddSortedTime = 0L;
            if (debugMemory) beforeMemory = DataStructures.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddSortedTime = System.nanoTime();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println(name+" sorted add time = " + (addSortedTime / sortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructures.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = null;
            if (keyType == Type.Integer) {
                invalidKey = (K)INVALID;
            } else if (keyType == Type.String) {
                invalidKey = (K)String.valueOf(INVALID);
            }
            boolean contains = map.containsKey(invalidKey);
            V removed = map.remove(invalidKey);
            if (contains || (removed!=null)) {
                System.err.println(name+" sorted invalidity check. contains=" + contains + " removed=" + removed);
                return false;
            }

            if (debug > 1) System.out.println(map.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (Integer item : sorted) {
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                map.containsKey(k);
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" sorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            beforeRemoveSortedTime = 0L;
            afterRemoveSortedTime = 0L;
            if (debugTime) beforeRemoveSortedTime = System.nanoTime();
            for (int i = sorted.length - 1; i >= 0; i--) {
                Integer item = sorted[i];
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                }
                removed = map.remove(k);
                if (removed==null) {
                    System.err.println(name+" unsorted invalidity check. removed=" + removed);
                    return false;
                }
            }
            if (debugTime) {
                afterRemoveSortedTime = System.nanoTime();
                removeSortedTime += afterRemoveSortedTime - beforeRemoveSortedTime;
                if (debug > 0) System.out.println(name+" sorted remove time = " + (removeSortedTime / sortedCount) + " ns");
            }

            if (validateIterator && !testMapEntrySet(map,keyType)) return false;

            if (!map.isEmpty()) {
                System.err.println(name+" sorted isEmpty() failed.");
                handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(map);
                return false;
            }

        }

        if (testResults[testIndex] == null) testResults[testIndex] = new long[6];
        testResults[testIndex][0] += addTime / unsortedCount;
        testResults[testIndex][1] += removeTime / unsortedCount;
        testResults[testIndex][2] += addSortedTime / sortedCount;
        testResults[testIndex][3] += removeSortedTime / sortedCount;
        testResults[testIndex][4] += lookupTime / (unsortedCount + sortedCount);
        testResults[testIndex++][5] += memory / (unsortedCount + sortedCount);

        if (debug > 1) System.out.println();

        return true;
    }

    public static <K,V> boolean testMapEntrySet(java.util.Map<K, V> map, Type keyType) {
        {   // Test keys
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }

            java.util.Set<K> set = map.keySet();
            for (int i = 0; i < sorted.length; i++) {
                Integer key = sorted[i];
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)key;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(key);
                }
                if (!set.contains(k)) {
                    System.err.println("MayEntry contains() failure.");
                    handleError(map);
                    return false;
                }
            }

            java.util.Iterator<K> keyIter = set.iterator();
            while (keyIter.hasNext()) {
                keyIter.next();
                keyIter.remove();
            }

            if (!map.isEmpty()) {
                System.err.println("MayEntry isEmpty() failure.");
                handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println("MayEntry size()!=0 failure.");
                handleError(map);
                return false;
            }
        }

        {   // Test values
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }

            java.util.Collection<V> collection = map.values();
            for (int i = 0; i < sorted.length; i++) {
                Integer value = sorted[i];
                V v = null;
                // These are reversed on purpose
                if (keyType == Type.Integer) {
                    v = (V)String.valueOf(value);
                } else if (keyType == Type.String) {
                    v = (V)value;
                }
                if (!collection.contains(v)) {
                    System.err.println("MayEntry contains() failure.");
                    handleError(map);
                    return false;
                }
            }

            java.util.Iterator<V> valueIter = collection.iterator();
            while (valueIter.hasNext()) {
                valueIter.next();
                valueIter.remove();
            }

            if (!map.isEmpty()) {
                System.err.println("MayEntry isEmpty() failure.");
                handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println("MayEntry size()!=0 failure.");
                handleError(map);
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean testIterator(Iterator<T> iter) {
        while (iter.hasNext()) {
            T item = iter.next();
            if (item==null) {
                System.err.println("Iterator failure.");
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean testListIterator(ListIterator<T> iter) {
        // Make sure you catch going prev at the start
        boolean exceptionThrown = false;
        try {
            iter.previous();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.err.println("ListIterator exception failure.");
            return false;
        }

        for (int i = 0; i < unsorted.length; i++) {
            T t = (T)unsorted[i];
            iter.add(t);
        }
        while (iter.hasPrevious()) iter.previous();

        int i = 0;
        while (iter.hasNext()) {
            T item = iter.next();
            int idx = iter.nextIndex();
            if (idx!=++i) {
                System.err.println("ListIterator index failure.");
                return false;
            }
            if (item==null) {
                System.err.println("ListIterator item is null.");
                return false;
            }
        }

        // We should be at the end of the collection, this should fail
        exceptionThrown = false;
        try {
            iter.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.err.println("ListIterator exception failure.");
            return false;
        }

        //This should be list.size
        iter.nextIndex();
        int listSize = iter.nextIndex();
        if (listSize!=ARRAY_SIZE) {
            System.err.println("ListIterator ARRAY_SIZE failure.");
            return false;
        }

        i--;
        while (iter.hasPrevious()) {
            T item = iter.previous();
            int idx = iter.previousIndex();
            if (idx!=--i) {
                System.err.println("ListIterator index failure.");
                return false;
            }
            if (item==null) {
                System.err.println("ListIterator item is null.");
                return false;
            }
        }

        // We should be at the beginning of the collection, this should fail
        exceptionThrown = false;
        try {
            iter.previous();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.err.println("ListIterator exception failure.");
            return false;
        }

        // This should be negative one
        iter.previousIndex();
        int negOne = iter.previousIndex();
        if (negOne!=-1) {
            System.err.println("ListIterator negative_one failure.");
            return false;
        }

        // Remove all using iterator
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }

        return true;
    }

    private static final String getTestResults(int testNumber, String[] names, long[][] results) {
        StringBuilder resultsBuilder = new StringBuilder();
        String format = "%-32s %-10s %-15s %-15s %-20s %-15s %-15s\n";
        Formatter formatter = new Formatter(resultsBuilder, Locale.US);
        formatter.format(format, "Data Structure", "Add time", "Remove time", "Sorted add time", "Sorted remove time", "Lookup time", "Size");

        double KB = 1000;
        double MB = 1000 * KB;

        double MILLIS = 1000000;
        double SECOND = 1000;
        double MINUTES = 60 * SECOND;

        for (int i=0; i<TESTS; i++) {
            String name = names[i];
            long[] result = results[i];
            if (name != null && result != null) {
                double addTime = result[0] / MILLIS;
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

                double removeTime = result[1] / MILLIS;
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
                double addSortedTime = result[2] / MILLIS;
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

                double removeSortedTime = result[3] / MILLIS;
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

                double lookupTime = result[4] / MILLIS;
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

                formatter.format(format, name, addTimeString, removeTimeString, sortedAddTimeString, sortedRemoveTimeString, lookupTimeString, sizeString);
            }
        }
        formatter.close();

        return resultsBuilder.toString();
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
