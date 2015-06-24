package com.jwetherell.algorithms.data_structures.timing;

import java.lang.reflect.Array;
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

    private static final Random RANDOM = new Random();
    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");
    private static final int NUMBER_OF_TESTS = 3; // There will always be NUMBER_OF_TESTS+1 runs since the first round is thrown away (JITing)
    private static final int ARRAY_SIZE = 1024*20; // Number of items to add/remove/look-up from each data structure
    private static final int RANDOM_SIZE = 1000 * ARRAY_SIZE;
    private static final Integer INVALID = RANDOM_SIZE + 10;

    private static final int TESTS = 39; // Max number of dynamic data structures to test
    private static final String[] testNames = new String[TESTS]; // Array to hold the test names
    private static final long[][] testResults = new long[TESTS][]; // Array to hold the test results

    private static int debug = 1; // Debug level. 0=None, 1=Time and Memory (if enabled), 2=Time, Memory, data structure debug
    private static boolean debugTime = true; // How much time to: add all, remove all, add all items in reverse order, remove all
    private static boolean debugMemory = true; // How much memory is used by the data structure

    private static int testIndex = 0; // Index into the tests
    private static boolean firstTimeThru = true; // We throw away the first set of data to avoid JITing

    public static void main(String[] args) {
        System.out.println("Starting tests.");
        boolean passed = false;
        try {
            passed = runTests();
        } catch (NullPointerException e) {
            throw e;
        }
        if (passed) System.out.println("Tests finished. All passed.");
        else System.err.println("Tests finished. Detected a failure.");
    }

    private static void generateTestData(int idx, int size, Integer[][] unsorteds, Integer[][] sorteds, String[] strings) {
        System.out.println("Generating data.");
        StringBuilder builder = new StringBuilder();
        builder.append("Array=");
        java.util.Set<Integer> set = new java.util.HashSet<Integer>();
        unsorteds[idx] = new Integer[size];
        sorteds[idx] = new Integer[size];
        for (int i = 0; i < size; i++) {
            Integer j = RANDOM.nextInt(RANDOM_SIZE);
            // Make sure there are no duplicates
            boolean found = true;
            while (found) {
                if (set.contains(j)) {
                    j = RANDOM.nextInt(RANDOM_SIZE);
                } else {
                    unsorteds[idx][i] = j;
                    set.add(j);
                    found = false;
                }
            }
            unsorteds[idx][i] = j;
            if (i!=size-1) builder.append(j).append(',');
        }
        set.clear();
        set = null;
        builder.append('\n');
        strings[idx] = builder.toString();
        if (debug > 1) System.out.println(strings[idx]);

        sorteds[idx] = Arrays.copyOf(unsorteds[idx], unsorteds[idx].length);
        Arrays.sort(sorteds[idx]);

        System.out.println("Generated data.");
    }

    private static boolean runTests() {
        testIndex = 0;

        // requested number of tests plus the warm-up round
        int tests = NUMBER_OF_TESTS+1;
        Integer[][] unsorteds = new Integer[tests][];
        Integer[][] sorteds = new Integer[tests][];
        String[] strings = new String[tests];
        for (int i=0; i<tests; i++)
            generateTestData(i, ARRAY_SIZE, unsorteds, sorteds, strings);
        putOutTheGarbage();

        // Trees

        if (!runTests(new testJavaRedBlackIntegerTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testRedBlackTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testAVLTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testSplayTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testBTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testTreap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testBST(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testJavaRedBlackStringTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testTrie(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testPatriciaTrie(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Sets

        if (!runTests(new testJavaSkipList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testSkipList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Heaps

        if (!runTests(new testJavaMinHeap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testMinHeapArray(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testMinHeapTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testJavaMaxHeap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testMaxHeapArray(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testMaxHeapTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Lists

        if (!runTests(new testJavaArrayList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testArrayList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testJavaLinkedList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testLinkedList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Queues

        if (!runTests(new testJavaArrayQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testArrayQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testJavaLinkedQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testLinkedQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Stacks

        if (!runTests(new testJavaStack(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testArrayStack(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testLinkedStack(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Maps

        if (!runTests(new testJavaHashMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testHashMapProbing(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testHashMapChaining(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testJavaTreeMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testTreeMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testTrieMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testRadixTrie(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testJavaSkipListMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testSkipListMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new testHAMT(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        return true;
    }

    private static final boolean runTests(Testable testable, int tests, Integer[][] unsorteds, Integer sorteds[][], String[] strings) {
        boolean passed = false; 
        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                Integer[] unsorted = unsorteds[i];
                Integer[] sorted = sorteds[i];
                String string = strings[i];
                passed = testable.run(unsorted, sorted, string);
                if (!passed) {
                    System.err.println(testable.getInput());
                    System.err.println(testable.getName()+" failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(testable.getInput());
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;
        return true;
    }

    private static void handleError(String input, Object obj) {
        System.err.println(input);
        System.err.println(obj.toString());
        throw new RuntimeException("Error in test.");
    }

    public static abstract class Testable {
        String input = null;
        public String getInput() {
            return input;
        }
        public abstract String getName();
        public abstract boolean run(Integer[] unsorted, Integer[] sorted, String input);
    }

    private static class testAVLTree extends Testable {
        BinarySearchTree<Integer> avlTree = new AVLTree<Integer>();
        String name = "AVL Tree <Integer>";
        Collection<Integer> bstCollection = avlTree.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testBTree extends Testable {
        BTree<Integer> bTree = new BTree<Integer>(2);
        String name = "B-Tree <Integer>";
        Collection<Integer> bstCollection = bTree.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testBST extends Testable {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        String name = "BST <Integer>";
        Collection<Integer> bstCollection = bst.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testMinHeapArray extends Testable {
        BinaryHeap.BinaryHeapArray<Integer> aHeapMin = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
        String name = "Min-Heap <Integer> [array]";
        Collection<Integer> aCollectionMin = aHeapMin.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(aCollectionMin,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testMinHeapTree extends Testable {
        BinaryHeap.BinaryHeapTree<Integer> tHeapMin = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
        String name = "Min-Heap <Integer> [tree]";
        Collection<Integer> tCollectionMin = tHeapMin.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(tCollectionMin,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testMaxHeapArray extends Testable {
        BinaryHeap.BinaryHeapArray<Integer> aHeapMax = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
        String name = "Max-Heap <Integer> [array]";
        Collection<Integer> aCollectionMax = aHeapMax.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(aCollectionMax,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testMaxHeapTree extends Testable {
        BinaryHeap.BinaryHeapTree<Integer> tHeapMax = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
        String name = "Max-Heap <Integer> [tree]";
        Collection<Integer> tCollectionMax = tHeapMax.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(tCollectionMax,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testHashMapProbing extends Testable {
        HashMap<Integer,String> pHashMap = new HashMap<Integer,String>(HashMap.Type.PROBING, ARRAY_SIZE/2);
        String name = "Probing HashMap <Integer>";
        java.util.Map<Integer,String> jMap = pHashMap.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testHashMapChaining extends Testable {
        HashMap<Integer,String> cHashMap = new HashMap<Integer,String>(HashMap.Type.CHAINING, ARRAY_SIZE/2);
        String name = "Chaining HashMap <Integer>";
        java.util.Map<Integer,String> jMap = cHashMap.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testHAMT extends Testable {
        HashArrayMappedTrie<Integer,String> hamt = new HashArrayMappedTrie<Integer,String>();
        String name = "HAMT <Integer>";
        java.util.Map<Integer,String> jMap = hamt.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaHashMap extends Testable {
        java.util.Map<Integer,String> javaHashMap = new java.util.HashMap<Integer,String>(ARRAY_SIZE/2);
        String name = "Java's HashMap <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(javaHashMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaMinHeap extends Testable {
        java.util.PriorityQueue<Integer> javaMinArrayHeap = new java.util.PriorityQueue<Integer>(10,
            new Comparator<Integer>() {
                @Override
                public int compare(Integer arg0, Integer arg1) {
                    if (arg0.compareTo(arg1) > 0)
                        return 1;
                    else if (arg1.compareTo(arg0) > 0)
                        return -1;
                    return 0;
                }
            }
        );
        String name = "Java's Min-Heap <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaMinArrayHeap,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaMaxHeap extends Testable {
        java.util.PriorityQueue<Integer> javaMaxArrayHeap = new java.util.PriorityQueue<Integer>(10,
            new Comparator<Integer>() {
                @Override
                public int compare(Integer arg0, Integer arg1) {
                    if (arg0.compareTo(arg1) > 0)
                        return -1;
                    else if (arg1.compareTo(arg0) > 0)
                        return 1;
                    return 0;
                }
            }
        );
        String name = "Java's Max-Heap <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaMaxArrayHeap,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaArrayList extends Testable {
        java.util.List<Integer> javaArrayList = new java.util.ArrayList<Integer>();
        String name = "Java's List <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaArrayList,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaLinkedList extends Testable {
        java.util.List<Integer> javaLinkedList = new java.util.LinkedList<Integer>();
        String name = "Java's List <Integer> [linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaLinkedList,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaArrayQueue extends Testable {
        java.util.Deque<Integer> javaArrayQueue = new java.util.ArrayDeque<Integer>();
        String name = "Java's Queue <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaArrayQueue,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaLinkedQueue extends Testable {
        java.util.Deque<Integer> javaLinkedQueue = new java.util.LinkedList<Integer>();
        String name = "Java's Queue <Integer> [linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaLinkedQueue,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaRedBlackIntegerTree extends Testable {
        java.util.TreeSet<Integer> javaRedBlackTreeInteger = new java.util.TreeSet<Integer>();
        String name = "Java's Red-Black Tree <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaRedBlackTreeInteger,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaRedBlackStringTree extends Testable {
        java.util.TreeSet<String> javaRedBlackTreeString = new java.util.TreeSet<String>();
        String name = "Java's Red-Black Tree <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaRedBlackTreeString,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaStack extends Testable {
        java.util.Stack<Integer> javaStack = new java.util.Stack<Integer>();
        String name = "Java's Stack <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(javaStack,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaTreeMap extends Testable {
        java.util.Map<String,Integer> javaTreeMap = new java.util.TreeMap<String,Integer>();
        String name = "Java's TreeMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(javaTreeMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testArrayList extends Testable {
        List.ArrayList<Integer> arrayList = new List.ArrayList<Integer>();
        String name = "List <Integer> [array]";
        Collection<Integer> aCollection = arrayList.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(aCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testLinkedList extends Testable {
        List.LinkedList<Integer> linkedList = new List.LinkedList<Integer>();
        String name = "List <Integer> [linked]";
        Collection<Integer> lCollection = linkedList.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testPatriciaTrie extends Testable {
        PatriciaTrie<String> patriciaTrie = new PatriciaTrie<String>();
        String name = "PatriciaTrie <String>";
        Collection<String> bstCollection = patriciaTrie.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(bstCollection,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testArrayQueue extends Testable {
        Queue.ArrayQueue<Integer> arrayQueue = new Queue.ArrayQueue<Integer>();
        String name = "Queue <Integer> [array]";
        Collection<Integer> aCollection = arrayQueue.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(aCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testLinkedQueue extends Testable {
        Queue.LinkedQueue<Integer> linkedQueue = new Queue.LinkedQueue<Integer>();
        String name = "Queue <Integer> [linked]";
        Collection<Integer> lCollection = linkedQueue.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testRadixTrie extends Testable {
        RadixTrie<String,Integer> radixTrie = new RadixTrie<String,Integer>();
        String name = "RadixTrie <String>";
        java.util.Map<String,Integer> jMap = radixTrie.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testRedBlackTree extends Testable {
        BinarySearchTree<Integer> redBlackTree = new RedBlackTree<Integer>();
        String name = "Red-Black Tree <Integer>";
        Collection<Integer> bstCollection = redBlackTree.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaSkipList extends Testable {
        NavigableSet<Integer> javaSkipList = new ConcurrentSkipListSet<Integer>();
        String name = "Java's SkipListSet <Integer>";
        Collection<Integer> lCollection = javaSkipList;

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testSkipList extends Testable {
        SkipList<Integer> skipList = new SkipList<Integer>();
        String name = "SkipList <Integer>";
        Collection<Integer> lCollection = skipList.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testSplayTree extends Testable {
        BinarySearchTree<Integer> splayTree = new SplayTree<Integer>();
        String name = "Splay Tree <Integer>";
        Collection<Integer> bstCollection = splayTree.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testArrayStack extends Testable {
        Stack.ArrayStack<Integer> arrayStack = new Stack.ArrayStack<Integer>();
        String name = "Stack <Integer> [array]";
        Collection<Integer> aCollection = arrayStack.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(aCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testLinkedStack extends Testable {
        Stack.LinkedStack<Integer> linkedStack = new Stack.LinkedStack<Integer>();
        String name = "Stack <Integer> [linked]";
        Collection<Integer> lCollection = linkedStack.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testTreap extends Testable {
        BinarySearchTree<Integer> treap = new Treap<Integer>();
        String name = "Treap <Integer>";
        Collection<Integer> treapCollection = treap.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(treapCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testTreeMap extends Testable {
        TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>();
        String name = "TreeMap <String>";
        java.util.Map<String,Integer> jMap = treeMap.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testTrie extends Testable {
        Trie<String> trie = new Trie<String>();
        String name = "Trie <String>";
        Collection<String> trieCollection = trie.toCollection();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaCollection(trieCollection,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testTrieMap extends Testable {
        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        String name = "TrieMap <String>";
        java.util.Map<String,Integer> jMap = trieMap.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testJavaSkipListMap extends Testable {
        ConcurrentSkipListMap<String,Integer> javaSkipListMap = new ConcurrentSkipListMap<String,Integer>();
        String name = "Java's SkipListMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(javaSkipListMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class testSkipListMap extends Testable {
        SkipListMap<String,Integer> skipListMap = new SkipListMap<String,Integer>();
        String name = "SkipListMap <String>";
        java.util.Map<String,Integer> jMap = skipListMap.toMap();

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> boolean testJavaCollection(Collection<T> collection, Class<T> type, String name, Integer[] _unsorted, Integer[] _sorted, String input) {
        // Make sure the collection is empty
        if (!collection.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            handleError(input,collection);
            return false;
        }
        if (collection.size()!=0) {
            System.err.println(name+" initial size() failed.");
            handleError(input,collection);
            return false;
        }

        T[] unsorted = (T[]) Array.newInstance(type, _unsorted.length);
        T[] sorted = (T[]) Array.newInstance(type, _sorted.length);
        for (int i=0; i<unsorted.length; i++)
            unsorted[i] = Utils.parseT(_unsorted[i], type);
        for (int i=0; i<sorted.length; i++)
            sorted[i] = Utils.parseT(_sorted[i], type);

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

        // Throw away first test to remove JITing
        if (!firstTimeThru)
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
                T item = unsorted[i];
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" unsorted add failed.");
                    handleError(input,collection);
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
                T item = unsorted[i];
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" unsorted contains failed.");
                    handleError(input,collection);
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
                T item = unsorted[i];
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" unsorted remove failed.");
                    handleError(input,collection);
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
                handleError(input,collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(input,collection);
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
                T item = unsorted[i];
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" unsorted add failed.");
                    handleError(input,collection);
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
                T item = unsorted[i];
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" unsorted contains failed.");
                    handleError(input,collection);
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
                T item = unsorted[i];
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" unsorted remove failed.");
                    handleError(input,collection);
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
                handleError(input,collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(input,collection);
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
                T item = sorted[i];
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" sorted add failed.");
                    handleError(input,collection);
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
                T item = sorted[i];
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" sorted contains failed.");
                    handleError(input,collection);
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
                T item = sorted[i];
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" sorted remove failed.");
                    handleError(input,collection);
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
                handleError(input,collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(input,collection);
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
                T item = sorted[i];
                boolean added = collection.add(item);
                if (!added) {
                    System.err.println(name+" sorted add failed.");
                    handleError(input,collection);
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
                T item = sorted[i];
                boolean contains = collection.contains(item);
                if (!contains) {
                    System.err.println(name+" sorted contains failed.");
                    handleError(input,collection);
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
                T item = sorted[i];
                boolean removed = collection.remove(item);
                if (!removed) {
                    System.err.println(name+" sorted remove failed.");
                    handleError(input,collection);
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
                handleError(input,collection);
                return false;
            }
            if (collection.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(input,collection);
                return false;
            }
        }

        // Throw away first test to remove JITing
        if (!firstTimeThru) {
            if (testResults[testIndex] == null) 
                testResults[testIndex] = new long[6];
            testResults[testIndex][0] += (addTime / unsortedCount);
            testResults[testIndex][1] += (removeTime / unsortedCount);
            testResults[testIndex][2] += (addSortedTime / sortedCount);
            testResults[testIndex][3] += (removeSortedTime / sortedCount);
            testResults[testIndex][4] += (lookupTime / (unsortedCount + sortedCount));
            testResults[testIndex][5] += (memory / (unsortedCount + sortedCount));
        }

        if (debug > 1) System.out.println();

        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K extends Comparable<K>,V> boolean testJavaMap(java.util.Map<K,V> map, Class<K> keyType, Class<V> valueType, String name, Integer[] _unsorted, Integer[] _sorted, String input) {
        // Make sure the map is empty
        if (!map.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            handleError(input,map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" initial size() failed.");
            handleError(input,map);
            return false;
        }

        K[] kUnsorted = (K[]) Array.newInstance(keyType, _unsorted.length);
        K[] kSorted = (K[]) Array.newInstance(keyType, _sorted.length);
        V[] vUnsorted = (V[]) Array.newInstance(valueType, _unsorted.length);
        V[] vSorted = (V[]) Array.newInstance(valueType, _sorted.length);
        for (int i=0; i<kUnsorted.length; i++)
            kUnsorted[i] = Utils.parseT(_unsorted[i], keyType);
        for (int i=0; i<kSorted.length; i++)
            kSorted[i] = Utils.parseT(_sorted[i], keyType);
        for (int i=0; i<vUnsorted.length; i++)
            vUnsorted[i] = Utils.parseT(_unsorted[i], valueType);
        for (int i=0; i<kSorted.length; i++)
            vSorted[i] = Utils.parseT(_sorted[i], valueType);

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

        // Throw away first test to remove JITing
        if (!firstTimeThru)
            testNames[testIndex] = name;

        unsortedCount++;
        {
            beforeMemory = 0L;
            afterMemory = 0L;
            beforeAddTime = 0L;
            afterAddTime = 0L;
            if (debugMemory) beforeMemory = DataStructuresTiming.getMemoryUse();
            if (debugTime) beforeAddTime = System.nanoTime();
            for (int i = 0; i < kUnsorted.length; i++) {
                K k = kUnsorted[i];
                V v = vUnsorted[i];
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
            for (K k : kUnsorted) {
                map.containsKey(k);
            }
            if (debugTime) {
                afterLookupTime = System.nanoTime();
                lookupTime += afterLookupTime - beforeLookupTime;
                if (debug > 0) System.out.println(name+" unsorted lookup time = " + (lookupTime / (unsortedCount+sortedCount)) + " ns");
            }

            if (debugTime) beforeRemoveTime = System.nanoTime();
            for (int i = 0; i < kUnsorted.length; i++) {
                K k = kUnsorted[i];
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
                handleError(input,map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(input,map);
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
            for (int i = kUnsorted.length - 1; i >= 0; i--) {
                K k = kUnsorted[i];
                V v = vUnsorted[i];
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
            for (K k : kUnsorted) {
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
            for (int i = kUnsorted.length - 1; i >= 0; i--) {
                K k = kUnsorted[i];
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
                handleError(input,map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" unsorted size() failed.");
                handleError(input,map);
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
            for (int i = 0; i < kSorted.length; i++) {
                K k = kSorted[i];
                V v = vSorted[i];
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
            for (K k : kSorted) {
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
            for (int i = 0; i < kSorted.length; i++) {
                K k = kSorted[i];
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
                handleError(input,map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(input,map);
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
            for (int i = 0; i < kSorted.length; i++) {
                K k = kSorted[i];
                V v = vSorted[i];
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
            for (K k : kSorted) {
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
            for (int i = kSorted.length - 1; i >= 0; i--) {
                K k = kSorted[i];
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
                handleError(input,map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println(name+" sorted size() failed.");
                handleError(input,map);
                return false;
            }

        }

        // Throw away first test to remove JITing
        if (!firstTimeThru) {
            if (testResults[testIndex] == null) 
                testResults[testIndex] = new long[6];
            testResults[testIndex][0] += (addTime / unsortedCount);
            testResults[testIndex][1] += (removeTime / unsortedCount);
            testResults[testIndex][2] += (addSortedTime / sortedCount);
            testResults[testIndex][3] += (removeSortedTime / sortedCount);
            testResults[testIndex][4] += (lookupTime / (unsortedCount + sortedCount));
            testResults[testIndex][5] += (memory / (unsortedCount + sortedCount));
        }

        if (debug > 1) System.out.println();

        return true;
    }

    private static final String getTestResults(int number, String[] names, long[][] results) {
        StringBuilder resultsBuilder = new StringBuilder();
        String format = "%-35s %-10s %-15s %-15s %-25s %-15s %-15s\n";
        Formatter formatter = new Formatter(resultsBuilder, Locale.US);
        formatter.format(format, "Data Structure ("+ARRAY_SIZE+" items)", "Add time", "Remove time", "Sorted add time", "Sorted remove time", "Lookup time", "Size");

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
