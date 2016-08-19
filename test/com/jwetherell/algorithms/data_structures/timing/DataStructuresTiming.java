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
    private static final int ARRAY_SIZE = 1024*10; // Number of items to add/remove/look-up from each data structure
    private static final int RANDOM_SIZE = 1000 * ARRAY_SIZE;
    private static final Integer INVALID = RANDOM_SIZE + 10;

    private static final int TESTS = 40; // Max number of dynamic data structures to test
    private static final String[] TEST_NAMES = new String[TESTS]; // Array to hold the test names
    private static final long[][] TEST_RESULTS = new long[TESTS][]; // Array to hold the test results

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

        if (!runTests(new TestJavaRedBlackIntegerTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestRedBlackTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestAVLTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestSplayTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestBTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestTreap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestHAMT(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestBST(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestJavaRedBlackStringTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestTrie(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestPatriciaTrie(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Sets

        if (!runTests(new TestJavaSkipList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestSkipList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Heaps

        if (!runTests(new TestJavaMinHeap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestMinHeapArray(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestMinHeapTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestJavaMaxHeap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestMaxHeapArray(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestMaxHeapTree(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Lists

        if (!runTests(new TestJavaArrayList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestArrayList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestJavaLinkedList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestSinglyLinkedList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestDoublyLinkedList(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Queues

        if (!runTests(new TestJavaArrayQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestArrayQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestJavaLinkedQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestLinkedQueue(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Stacks

        if (!runTests(new TestJavaStack(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestArrayStack(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestLinkedStack(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        // Maps

        if (!runTests(new TestJavaHashMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestHashMapProbing(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestHashMapChaining(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestJavaTreeMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestTreeMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestTrieMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestRadixTrie(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestJavaSkipListMap(), tests, unsorteds, sorteds, strings)) return false;
        putOutTheGarbage();

        if (!runTests(new TestSkipListMap(), tests, unsorteds, sorteds, strings)) return false;
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
            System.out.println(getTestResults(NUMBER_OF_TESTS, TEST_NAMES, TEST_RESULTS));   
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

    private static class TestAVLTree extends Testable {
        String name = "AVL Tree <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinarySearchTree<Integer> avlTree = new AVLTree<Integer>();
            Collection<Integer> bstCollection = avlTree.toCollection();
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestBTree extends Testable {
        String name = "B-Tree <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BTree<Integer> bTree = new BTree<Integer>(4);
            Collection<Integer> bstCollection = bTree.toCollection();
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestBST extends Testable {
        String name = "BST <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
            Collection<Integer> bstCollection = bst.toCollection();
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestMinHeapArray extends Testable {
        String name = "Min-Heap <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinaryHeap.BinaryHeapArray<Integer> aHeapMin = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
            Collection<Integer> aCollectionMin = aHeapMin.toCollection();
            if (!testJavaCollection(aCollectionMin,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestMinHeapTree extends Testable {
        String name = "Min-Heap <Integer> [tree]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinaryHeap.BinaryHeapTree<Integer> tHeapMin = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
            Collection<Integer> tCollectionMin = tHeapMin.toCollection();
            if (!testJavaCollection(tCollectionMin,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestMaxHeapArray extends Testable {
        String name = "Max-Heap <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinaryHeap.BinaryHeapArray<Integer> aHeapMax = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
            Collection<Integer> aCollectionMax = aHeapMax.toCollection();
            if (!testJavaCollection(aCollectionMax,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestMaxHeapTree extends Testable {
        String name = "Max-Heap <Integer> [tree]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinaryHeap.BinaryHeapTree<Integer> tHeapMax = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
            Collection<Integer> tCollectionMax = tHeapMax.toCollection();
            if (!testJavaCollection(tCollectionMax,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestHashMapProbing extends Testable {
        String name = "Probing HashMap <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            HashMap<Integer,String> pHashMap = new HashMap<Integer,String>(HashMap.Type.PROBING, ARRAY_SIZE/2);
            java.util.Map<Integer,String> jMap = pHashMap.toMap();
            if (!testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestHashMapChaining extends Testable {
        String name = "Chaining HashMap <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            HashMap<Integer,String> cHashMap = new HashMap<Integer,String>(HashMap.Type.CHAINING, ARRAY_SIZE/2);
            java.util.Map<Integer,String> jMap = cHashMap.toMap();
            if (!testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestHAMT extends Testable {
        String name = "HAMT <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            HashArrayMappedTrie<Integer,String> hamt = new HashArrayMappedTrie<Integer,String>();
            java.util.Map<Integer,String> jMap = hamt.toMap();
            if (!testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaHashMap extends Testable {
        String name = "Java's HashMap <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.Map<Integer,String> javaHashMap = new java.util.HashMap<Integer,String>(ARRAY_SIZE/2);
            if (!testJavaMap(javaHashMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaMinHeap extends Testable {
        Comparator<Integer> comparator =  new Comparator<Integer>() {
            @Override
            public int compare(Integer arg0, Integer arg1) {
                if (arg0.compareTo(arg1) > 0)
                    return 1;
                else if (arg1.compareTo(arg0) > 0)
                    return -1;
                return 0;
            }
        };
        String name = "Java's Min-Heap <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.PriorityQueue<Integer> javaMinArrayHeap = new java.util.PriorityQueue<Integer>(10, comparator);
            if (!testJavaCollection(javaMinArrayHeap,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaMaxHeap extends Testable {
        Comparator<Integer> comparator =  new Comparator<Integer>() {
            @Override
            public int compare(Integer arg0, Integer arg1) {
                if (arg0.compareTo(arg1) > 0)
                    return -1;
                else if (arg1.compareTo(arg0) > 0)
                    return 1;
                return 0;
            }
        };
        String name = "Java's Max-Heap <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.PriorityQueue<Integer> javaMaxArrayHeap = new java.util.PriorityQueue<Integer>(10, comparator);
            if (!testJavaCollection(javaMaxArrayHeap,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaArrayList extends Testable {
        String name = "Java's List <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.List<Integer> javaArrayList = new java.util.ArrayList<Integer>();
            if (!testJavaCollection(javaArrayList,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaLinkedList extends Testable {
        String name = "Java's List <Integer> [linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.List<Integer> javaLinkedList = new java.util.LinkedList<Integer>();
            if (!testJavaCollection(javaLinkedList,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaArrayQueue extends Testable {
        String name = "Java's Queue <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.Deque<Integer> javaArrayQueue = new java.util.ArrayDeque<Integer>();
            if (!testJavaCollection(javaArrayQueue,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaLinkedQueue extends Testable {
        String name = "Java's Queue <Integer> [linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.Deque<Integer> javaLinkedQueue = new java.util.LinkedList<Integer>();
            if (!testJavaCollection(javaLinkedQueue,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaRedBlackIntegerTree extends Testable {
        String name = "Java's Red-Black Tree <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.TreeSet<Integer> javaRedBlackTreeInteger = new java.util.TreeSet<Integer>();
            if (!testJavaCollection(javaRedBlackTreeInteger,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaRedBlackStringTree extends Testable {
        String name = "Java's Red-Black Tree <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.TreeSet<String> javaRedBlackTreeString = new java.util.TreeSet<String>();
            if (!testJavaCollection(javaRedBlackTreeString,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaStack extends Testable {
        String name = "Java's Stack <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.Stack<Integer> javaStack = new java.util.Stack<Integer>();
            if (!testJavaCollection(javaStack,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaTreeMap extends Testable {
        String name = "Java's TreeMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            java.util.Map<String,Integer> javaTreeMap = new java.util.TreeMap<String,Integer>();
            if (!testJavaMap(javaTreeMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestArrayList extends Testable {
        String name = "List <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            List.ArrayList<Integer> arrayList = new List.ArrayList<Integer>();
            Collection<Integer> aCollection = arrayList.toCollection();
            if (!testJavaCollection(aCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestSinglyLinkedList extends Testable {
        String name = "List <Integer> [singly linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            List.SinglyLinkedList<Integer> linkedList = new List.SinglyLinkedList<Integer>();
            Collection<Integer> lCollection = linkedList.toCollection();
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestDoublyLinkedList extends Testable {
        String name = "List <Integer> [doubly linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            List.DoublyLinkedList<Integer> linkedList = new List.DoublyLinkedList<Integer>();
            Collection<Integer> lCollection = linkedList.toCollection();
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestPatriciaTrie extends Testable {
        String name = "PatriciaTrie <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            PatriciaTrie<String> patriciaTrie = new PatriciaTrie<String>();
            Collection<String> bstCollection = patriciaTrie.toCollection();
            if (!testJavaCollection(bstCollection,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestArrayQueue extends Testable {
        String name = "Queue <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            Queue.ArrayQueue<Integer> arrayQueue = new Queue.ArrayQueue<Integer>();
            Collection<Integer> aCollection = arrayQueue.toCollection();
            if (!testJavaCollection(aCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestLinkedQueue extends Testable {
        String name = "Queue <Integer> [linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            Queue.LinkedQueue<Integer> linkedQueue = new Queue.LinkedQueue<Integer>();
            Collection<Integer> lCollection = linkedQueue.toCollection();
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestRadixTrie extends Testable {
        String name = "RadixTrie <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            RadixTrie<String,Integer> radixTrie = new RadixTrie<String,Integer>();
            java.util.Map<String,Integer> jMap = radixTrie.toMap();
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestRedBlackTree extends Testable {
        String name = "Red-Black Tree <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinarySearchTree<Integer> redBlackTree = new RedBlackTree<Integer>();
            Collection<Integer> bstCollection = redBlackTree.toCollection();
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaSkipList extends Testable {
        String name = "Java's SkipListSet <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            NavigableSet<Integer> javaSkipList = new ConcurrentSkipListSet<Integer>();
            Collection<Integer> lCollection = javaSkipList;
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestSkipList extends Testable {
        String name = "SkipList <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            SkipList<Integer> skipList = new SkipList<Integer>();
            Collection<Integer> lCollection = skipList.toCollection();
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestSplayTree extends Testable {
        String name = "Splay Tree <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinarySearchTree<Integer> splayTree = new SplayTree<Integer>();
            Collection<Integer> bstCollection = splayTree.toCollection();
            if (!testJavaCollection(bstCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestArrayStack extends Testable {
        String name = "Stack <Integer> [array]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            Stack.ArrayStack<Integer> arrayStack = new Stack.ArrayStack<Integer>();
            Collection<Integer> aCollection = arrayStack.toCollection();
            if (!testJavaCollection(aCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestLinkedStack extends Testable {
        String name = "Stack <Integer> [linked]";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            Stack.LinkedStack<Integer> linkedStack = new Stack.LinkedStack<Integer>();
            Collection<Integer> lCollection = linkedStack.toCollection();
            if (!testJavaCollection(lCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestTreap extends Testable {
        String name = "Treap <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            BinarySearchTree<Integer> treap = new Treap<Integer>();
            Collection<Integer> treapCollection = treap.toCollection();
            if (!testJavaCollection(treapCollection,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestTreeMap extends Testable {
        String name = "TreeMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>();
            java.util.Map<String,Integer> jMap = treeMap.toMap();
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestTrie extends Testable {
        String name = "Trie <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            Trie<String> trie = new Trie<String>();
            Collection<String> trieCollection = trie.toCollection();
            if (!testJavaCollection(trieCollection,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestTrieMap extends Testable {
        String name = "TrieMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
            java.util.Map<String,Integer> jMap = trieMap.toMap();
            if (!testJavaMap(jMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestJavaSkipListMap extends Testable {
        String name = "Java's SkipListMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            ConcurrentSkipListMap<String,Integer> javaSkipListMap = new ConcurrentSkipListMap<String,Integer>();
            if (!testJavaMap(javaSkipListMap,String.class,Integer.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }

    private static class TestSkipListMap extends Testable {
        String name = "SkipListMap <String>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            SkipListMap<String,Integer> skipListMap = new SkipListMap<String,Integer>();
            java.util.Map<String,Integer> jMap = skipListMap.toMap();
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
            TEST_NAMES[testIndex] = name;

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
            if (TEST_RESULTS[testIndex] == null) 
                TEST_RESULTS[testIndex] = new long[6];
            TEST_RESULTS[testIndex][0] += (addTime / unsortedCount);
            TEST_RESULTS[testIndex][1] += (removeTime / unsortedCount);
            TEST_RESULTS[testIndex][2] += (addSortedTime / sortedCount);
            TEST_RESULTS[testIndex][3] += (removeSortedTime / sortedCount);
            TEST_RESULTS[testIndex][4] += (lookupTime / (unsortedCount + sortedCount));
            TEST_RESULTS[testIndex][5] += (memory / (unsortedCount + sortedCount));
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
            TEST_NAMES[testIndex] = name;

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
            if (TEST_RESULTS[testIndex] == null) 
                TEST_RESULTS[testIndex] = new long[6];
            TEST_RESULTS[testIndex][0] += (addTime / unsortedCount);
            TEST_RESULTS[testIndex][1] += (removeTime / unsortedCount);
            TEST_RESULTS[testIndex][2] += (addSortedTime / sortedCount);
            TEST_RESULTS[testIndex][3] += (removeSortedTime / sortedCount);
            TEST_RESULTS[testIndex][4] += (lookupTime / (unsortedCount + sortedCount));
            TEST_RESULTS[testIndex][5] += (memory / (unsortedCount + sortedCount));
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

    static final long getMemoryUse() {
        putOutTheGarbage();
        long totalMemory = Runtime.getRuntime().totalMemory();

        putOutTheGarbage();
        long freeMemory = Runtime.getRuntime().freeMemory();

        return (totalMemory - freeMemory);
    }

    private static final long SLEEP_INTERVAL = 100;

    static final void putOutTheGarbage() {
        putOutTheGarbage(SLEEP_INTERVAL);
    }

    static final void putOutTheGarbage(long sleep) {
        collectGarbage(sleep);
        collectGarbage(sleep);
        collectGarbage(sleep);
    }

    private static final void collectGarbage(long sleep) {
        try {
            System.gc();
            System.gc();
            System.gc();
            Thread.sleep(sleep);
            System.runFinalization();
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
