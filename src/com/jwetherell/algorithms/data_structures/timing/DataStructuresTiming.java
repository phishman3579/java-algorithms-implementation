package com.jwetherell.algorithms.data_structures.timing;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.jwetherell.algorithms.data_structures.AVLTree;
import com.jwetherell.algorithms.data_structures.BTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.HashArrayMappedTrie;
import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.PatriciaTrie;
import com.jwetherell.algorithms.data_structures.Queue;
import com.jwetherell.algorithms.data_structures.RadixTrie;
import com.jwetherell.algorithms.data_structures.RedBlackTree;
import com.jwetherell.algorithms.data_structures.SkipList;
import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.SplayTree;
import com.jwetherell.algorithms.data_structures.Stack;
import com.jwetherell.algorithms.data_structures.Treap;
import com.jwetherell.algorithms.data_structures.TreeMap;
import com.jwetherell.algorithms.data_structures.Trie;
import com.jwetherell.algorithms.data_structures.TrieMap;
import com.jwetherell.algorithms.data_structures.test.common.Utils;

public class DataStructuresTiming {

    private static final int NUMBER_OF_TESTS = 3;
    private static final Random RANDOM = new Random();
    private static final int ARRAY_SIZE = 10000;
    private static final int RANDOM_SIZE = 1000 * ARRAY_SIZE;
    private static final Integer INVALID = RANDOM_SIZE + 10;
    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");

    private static Integer[] unsorted = null;
    private static Integer[] sorted = null;
    private static String string = null;

    private static int debug = 1; // Debug level. 0=None, 1=Time and Memory (if enabled), 2=Time, Memory, data structure debug
    private static boolean debugTime = true; // How much time to: add all, remove all, add all items in reverse order, remove all
    private static boolean debugMemory = true; // How much memory is used by the data structure

    private static final int TESTS = 39; // Max number of dynamic data structures to test
    private static final String[] testNames = new String[TESTS]; // Array to hold the test names
    private static final long[][] testResults = new long[TESTS][]; // Array to hold the test results
    private static int testIndex = 0; // Index into the tests
    private static int testNumber = 0; // Number of aggregate tests which have been run

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

    private static void generateTestData() {
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

        if (!testJavaCollection(bstCollection,Integer.class,bstName)) return false;
        return true;
    }

    private static boolean testBTree() {
        String bstName = "B-Tree";
        BTree<Integer> bst = new BTree<Integer>(2);
        Collection<Integer> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,bstName)) return false;
        return true;
    }

    private static boolean testBST() {
        String bstName = "BST";
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,bstName)) return false;
        return true;
    }

    private static boolean testMinHeap() {
        String aNameMin = "Min-Heap [array]";
        BinaryHeap.BinaryHeapArray<Integer> aHeapMin = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
        Collection<Integer> aCollectionMin = aHeapMin.toCollection();
        if (!testJavaCollection(aCollectionMin,Integer.class,aNameMin)) return false;

        String tNameMin = "Min-Heap [tree]";
        BinaryHeap.BinaryHeapTree<Integer> tHeapMin = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
        Collection<Integer> tCollectionMin = tHeapMin.toCollection();
        if (!testJavaCollection(tCollectionMin,Integer.class,tNameMin)) return false;

        return true;
    }

    private static boolean testMaxHeap() {
        String aNameMax = "Max-Heap [array]";
        BinaryHeap.BinaryHeapArray<Integer> aHeapMax = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
        Collection<Integer> aCollectionMax = aHeapMax.toCollection();
        if (!testJavaCollection(aCollectionMax,Integer.class,aNameMax)) return false;

        String lNameMax = "Max-Heap [tree]";
        BinaryHeap.BinaryHeapTree<Integer> tHeapMax = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
        Collection<Integer> tCollectionMax = tHeapMax.toCollection();
        if (!testJavaCollection(tCollectionMax,Integer.class,lNameMax)) return false;

        return true;
    }

    private static boolean testHashMap() {
        String mapName = "Probing HashMap";
        HashMap<Integer,String> map = new HashMap<Integer,String>(HashMap.Type.PROBING, unsorted.length/2);
        java.util.Map<Integer,String> jMap = map.toMap();

        if (!testJavaMap(jMap,Integer.class,mapName)) return false;

        mapName = "Chaining HashMap";
        map = new HashMap<Integer,String>(HashMap.Type.CHAINING, unsorted.length/2);
        jMap = map.toMap();

        if (!testJavaMap(jMap,Integer.class,mapName)) return false;

        return true;
    }

    private static boolean testHAMT() {
        String mapName = "HAMT";
        HashArrayMappedTrie<Integer,String> map = new HashArrayMappedTrie<Integer,String>();
        java.util.Map<Integer,String> jMap = map.toMap();

        if (!testJavaMap(jMap,Integer.class,mapName)) return false;
        return true;
    }

    private static boolean testJavaHashMap() {
        String mapName = "Java's HashMap";
        java.util.Map<Integer,String> map = new java.util.HashMap<Integer,String>(unsorted.length/2);

        if (!testJavaMap(map,Integer.class,mapName)) return false;
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
        if (!testJavaCollection(minArrayHeap,Integer.class,"Java's Min-Heap [array]")) return false;

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
        if (!testJavaCollection(maxArrayHeap,Integer.class,"Java's Max-Heap [array]")) return false;
        return true;
    }

    private static boolean testJavaArrayList() {
        if (!testJavaCollection(new java.util.ArrayList<Integer>(),Integer.class,"Java's List [array]")) return false;
        return true;
    }

    private static boolean testJavaLinkedList() {
        if (!testJavaCollection(new java.util.LinkedList<Integer>(),Integer.class,"Java's List [linked]")) return false;
        return true;
    }

    private static boolean testJavaArrayQueue() {
        String aName = "Java's Queue [array]";
        java.util.Deque<Integer> aCollection = new java.util.ArrayDeque<Integer>();

        if (!testJavaCollection(aCollection,Integer.class,aName)) return false;
        return true;
    }

    private static boolean testJavaLinkedQueue() {
        String lName = "Java's Queue [linked]";
        java.util.Deque<Integer> lCollection = new java.util.LinkedList<Integer>();

        if (!testJavaCollection(lCollection,Integer.class,lName)) return false;
        return true;
    }

    private static boolean testJavaRedBlackIntegerTree() {
        String aName = "Java's Red-Black Tree [Integer]";
        java.util.TreeSet<Integer> aCollection = new java.util.TreeSet<Integer>();
        if (!testJavaCollection(aCollection,Integer.class,aName)) return false;
        return true;
    }

    private static boolean testJavaRedBlackStringTree() {
        String aName = "Java's Red-Black Tree [String]";
        java.util.TreeSet<String> aCollection = new java.util.TreeSet<String>();
        if (!testJavaCollection(aCollection,String.class,aName)) return false;
        return true;
    }

    private static boolean testJavaStack() {
        String aName = "Java's Stack [array]";
        java.util.Stack<Integer> aCollection = new java.util.Stack<Integer>();
        if (!testJavaCollection(aCollection,Integer.class,aName)) return false;
        return true;
    }

    private static boolean testJavaTreeMap() {
        String mapName = "Java's TreeMap";
        java.util.Map<String,Integer> map = new java.util.TreeMap<String,Integer>();

        if (!testJavaMap(map,String.class,mapName)) return false;
        return true;
    }

    private static boolean testArrayList() {
        String aName = "List [array]";
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();
        Collection<Integer> aCollection = aList.toCollection();

        if (!testJavaCollection(aCollection,Integer.class,aName)) return false;
        return true;
    }

    private static boolean testLinkedList() {
        String lName = "List [linked]";
        List.LinkedList<Integer> lList = new List.LinkedList<Integer>();
        Collection<Integer> lCollection = lList.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,lName)) return false;
        return true;
    }

    private static boolean testPatriciaTrie() {
        String bstName = "PatriciaTrie";
        PatriciaTrie<String> bst = new PatriciaTrie<String>();
        Collection<String> bstCollection = bst.toCollection();

        
        if (!testJavaCollection(bstCollection,String.class,bstName)) return false;
        return true;
    }

    private static boolean testArrayQueue() {
        String aName = "Queue [array]";
        Queue.ArrayQueue<Integer> aQueue = new Queue.ArrayQueue<Integer>();
        Collection<Integer> aCollection = aQueue.toCollection();

        if (!testJavaCollection(aCollection,Integer.class,aName)) return false;
        return true;
    }

    private static boolean testLinkedQueue() {
        String lName = "Queue [linked]";
        Queue.LinkedQueue<Integer> lQueue = new Queue.LinkedQueue<Integer>();
        Collection<Integer> lCollection = lQueue.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,lName)) return false;
        return true;
    }

    private static boolean testRadixTrie() {
        String mapName = "RadixTrie";
        RadixTrie<String,Integer> map = new RadixTrie<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if (!testJavaMap(jMap,String.class,mapName)) return false;
        return true;
    }

    private static boolean testRedBlackTree() {
        String bstName = "Red-Black Tree";
        RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,bstName)) return false;
        return true;
    }

    private static boolean testJavaSkipList() {
        String sName = "Java's SkipList";
        NavigableSet<Integer> sList = new ConcurrentSkipListSet<Integer>();
        Collection<Integer> lCollection = sList;

        if (!testJavaCollection(lCollection,Integer.class,sName)) return false;
        return true;
    }

    private static boolean testSkipList() {
        String sName = "SkipList";
        SkipList<Integer> sList = new SkipList<Integer>();
        Collection<Integer> lCollection = sList.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,sName)) return false;
        return true;
    }

    private static boolean testSplayTree() {
        String bstName = "Splay Tree";
        BinarySearchTree<Integer> bst = new SplayTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,bstName)) return false;
        return true;
    }

    private static boolean testArrayStack() {
        String aName = "Stack [array]";
        Stack.ArrayStack<Integer> aStack = new Stack.ArrayStack<Integer>();
        Collection<Integer> aCollection = aStack.toCollection();

        if (!testJavaCollection(aCollection,Integer.class,aName)) return false;
        return true;
    }

    private static boolean testLinkedStack() {
        String lName = "Stack [linked]";
        Stack.LinkedStack<Integer> lStack = new Stack.LinkedStack<Integer>();
        Collection<Integer> lCollection = lStack.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,lName)) return false;
        return true;
    }

    private static boolean testTreap() {
        String bstName = "Treap";
        BinarySearchTree<Integer> bst = new Treap<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,bstName)) return false;
        return true;
    }

    private static boolean testTreeMap() {
        String mapName = "TreeMap";
        TreeMap<String,Integer> map = new TreeMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if (!testJavaMap(jMap,String.class,mapName)) return false;
        return true;
    }

    private static boolean testTrie() {
        String bstName = "Trie";
        Trie<String> bst = new Trie<String>();
        Collection<String> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,String.class,bstName)) return false;
        return true;
    }

    private static boolean testTrieMap() {
        String mapName = "TrieMap";
        TrieMap<String,Integer> map = new TrieMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if (!testJavaMap(jMap,String.class,mapName)) return false;
        return true;
    }

    private static boolean testJavaSkipListMap() {
        String mapName = "Java's SkipListMap";
        ConcurrentSkipListMap<String,Integer> map = new ConcurrentSkipListMap<String,Integer>();

        if (!testJavaMap(map,String.class,mapName)) return false;
        return true;
    }

    private static boolean testSkipListMap() {
        String mapName = "SkipListMap";
        SkipListMap<String,Integer> map = new SkipListMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        if (!testJavaMap(jMap,String.class,mapName)) return false;
        return true;
    }

    private static <T extends Comparable<T>> boolean testJavaCollection(Collection<T> collection, Class<T> type, String name) {
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
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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

        unsortedCount++;
        {   // UNSORTED: Add in reverse (from index length-1 to zero) order and then remove in order (from index zero to length)
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer value = unsorted[i];
                T item = Utils.parseT(value, type);
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
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer value = sorted[i];
                T item = Utils.parseT(value, type);
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
                Integer value = sorted[i];
                T item = Utils.parseT(value, type);
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
        }

        sortedCount++;
        {   // SORTED: Add in order (from index zero to length) and then remove in reverse (from index length-1 to zero) order 
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddSortedTime = 0L;
            afterAddSortedTime = 0L;
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer value = sorted[i];
                T item = Utils.parseT(value, type);
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
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            if (debug > 1) System.out.println(collection.toString());

            beforeLookupTime = 0L;
            afterLookupTime = 0L;
            if (debugTime) beforeLookupTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer value = sorted[i];
                T item = Utils.parseT(value, type);
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
                Integer value = sorted[i];
                T item = Utils.parseT(value, type);
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

    @SuppressWarnings("unchecked")
    private static <K extends Comparable<K>,V> boolean testJavaMap(java.util.Map<K,V> map, Class<K> keyType, String name) {
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
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = 0; i < unsorted.length; i++) {
                Integer item = unsorted[i];
                K k = null;
                V v = null;
                if (keyType.isAssignableFrom(Integer.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, Integer.class);
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddTime = System.nanoTime();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println(name+" unsorted add time = " + (addTime / unsortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = (K) Utils.parseT(INVALID, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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

        unsortedCount++;
        {
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = unsorted.length - 1; i >= 0; i--) {
                Integer item = unsorted[i];
                K k = null;
                V v = null;
                if (keyType.isAssignableFrom(Integer.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, Integer.class);
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddTime = System.nanoTime();
                addTime += afterAddTime - beforeAddTime;
                if (debug > 0) System.out.println(name+" unsorted add time = " + (addTime / unsortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" unsorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = (K) Utils.parseT(INVALID, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                V v = null;
                if (keyType.isAssignableFrom(Integer.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, Integer.class);
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddSortedTime = System.nanoTime();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println(name+" sorted add time = " + (addSortedTime / sortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = (K) Utils.parseT(INVALID, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddSortedTime = System.nanoTime();
            for (int i = 0; i < sorted.length; i++) {
                Integer item = sorted[i];
                K k = null;
                V v = null;
                if (keyType.isAssignableFrom(Integer.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, Integer.class);
                }
                map.put(k, v);
            }
            if (debugTime) {
                afterAddSortedTime = System.nanoTime();
                addSortedTime += afterAddSortedTime - beforeAddSortedTime;
                if (debug > 0) System.out.println(name+" sorted add time = " + (addSortedTime / sortedCount) + " ns");
            }
            if (debugMemory) {
                afterMemory = DataStructuresTiming.getMemoryUse();
                memory += afterMemory - beforeMemory;
                if (debug > 0) System.out.println(name+" sorted memory use = " + (memory / (unsortedCount+sortedCount)) + " bytes");
            }

            K invalidKey = (K) Utils.parseT(INVALID, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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
                K k = (K) Utils.parseT(item, keyType);
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

    private static final String getTestResults(int number, String[] names, long[][] results) {
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
                addTime /= number;
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
                removeTime /= number;
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
                addSortedTime /= number;
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
                removeSortedTime /= number;
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
                lookupTime /= number;
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
                size /= number;
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
            System.gc();
            System.gc();
            Thread.sleep(fSLEEP_INTERVAL);
            System.runFinalization();
            Thread.sleep(fSLEEP_INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
