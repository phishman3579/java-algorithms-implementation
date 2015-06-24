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

    private static final Random RANDOM = new Random();
    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");
    private static final int NUMBER_OF_TESTS = 3; // There will always be NUMBER_OF_TESTS+1 tests run, since the first round is thrown away (JITing)
    private static final int ARRAY_SIZE = 1024*20; // Number of items to add/remove/look-up from each data structure
    private static final int RANDOM_SIZE = 1000 * ARRAY_SIZE;
    private static final Integer INVALID = RANDOM_SIZE + 10;

    private static Integer[] unsorted = null;
    private static Integer[] sorted = null;
    private static String string = null;

    private static boolean firstTimeThru = true; // We throw away the first set of data to avoid JITing
    private static int debug = 1; // Debug level. 0=None, 1=Time and Memory (if enabled), 2=Time, Memory, data structure debug
    private static boolean debugTime = true; // How much time to: add all, remove all, add all items in reverse order, remove all
    private static boolean debugMemory = true; // How much memory is used by the data structure

    private static final int TESTS = 39; // Max number of dynamic data structures to test
    private static final String[] testNames = new String[TESTS]; // Array to hold the test names
    private static final long[][] testResults = new long[TESTS][]; // Array to hold the test results
    private static int testIndex = 0; // Index into the tests

    public static void main(String[] args) {
        System.out.println("Starting tests.");
        boolean passed = false;
        try {
            passed = runTests();
        } catch (NullPointerException e) {
            System.err.println(string);
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
        if (debug > 1) System.out.println(string);

        sorteds[idx] = Arrays.copyOf(unsorteds[idx], unsorteds[idx].length);
        Arrays.sort(sorteds[idx]);

        System.out.println("Generated data.");
    }

    private static boolean runTests() {
        testIndex = 0;

        boolean passed = true;

        // requested number of tests plus the warm-up round
        int tests = NUMBER_OF_TESTS+1;
        Integer[][] unsorteds = new Integer[tests][];
        Integer[][] sorteds = new Integer[tests][];
        String[] strings = new String[tests];
        for (int i=0; i<tests; i++)
            generateTestData(i, ARRAY_SIZE, unsorteds, sorteds, strings);

        // Trees

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaRedBlackIntegerTree();
                if (!passed) {
                    System.err.println("Java Red-Black [Integer] failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testRedBlackTree();
                if (!passed) {
                    System.err.println("Red-Black Tree failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testAVLTree();
                if (!passed) {
                    System.err.println("AVL Tree failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testSplayTree();
                if (!passed) {
                    System.err.println("Splay Tree failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testBTree();
                if (!passed) {
                    System.err.println("B-Tree failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testTreap();
                if (!passed) {
                    System.err.println("Treap failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testBST();
                if (!passed) {
                    System.err.println("BST failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaRedBlackStringTree();
                if (!passed) {
                    System.err.println("Java Red-Black [String] failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testTrie();
                if (!passed) {
                    System.err.println("Trie failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testPatriciaTrie();
                if (!passed) {
                    System.err.println("Patricia Trie failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        // Sets

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaSkipList();
                if (!passed) {
                    System.err.println("Java's Skip List failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testSkipList();
                if (!passed) {
                    System.err.println("Skip List failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        // Heaps

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaMinHeap();
                if (!passed) {
                    System.err.println("Java Min-Heap failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testMinHeap();
                if (!passed) {
                    System.err.println("Min-Heap failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaMaxHeap();
                if (!passed) {
                    System.err.println("Java Max-Heap failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testMaxHeap();
                if (!passed) {
                    System.err.println("Max-Heap failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        // Lists

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaArrayList();
                if (!passed) {
                    System.err.println("Java List failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testArrayList();
                if (!passed) {
                    System.err.println("List failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaLinkedList();
                if (!passed) {
                    System.err.println("Java List failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testLinkedList();
                if (!passed) {
                    System.err.println("List failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        // Queues

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaArrayQueue();
                if (!passed) {
                    System.err.println("Java Queue failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testArrayQueue();
                if (!passed) {
                    System.err.println("Queue failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaLinkedQueue();
                if (!passed) {
                    System.err.println("Java Queue failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testLinkedQueue();
                if (!passed) {
                    System.err.println("Queue failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        // Stacks

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaStack();
                if (!passed) {
                    System.err.println("Java Stack failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testArrayStack();
                if (!passed) {
                    System.err.println("Stack failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testLinkedStack();
                if (!passed) {
                    System.err.println("Stack failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        // Maps

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaHashMap();
                if (!passed) {
                    System.err.println("Java Hash Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testHashMap();
                if (!passed) {
                    System.err.println("Hash Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaTreeMap();
                if (!passed) {
                    System.err.println("Java Tree Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testTreeMap();
                if (!passed) {
                    System.err.println("Tree Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testTrieMap();
                if (!passed) {
                    System.err.println("Trie Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testRadixTrie();
                if (!passed) {
                    System.err.println("Radix Trie failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testJavaSkipListMap();
                if (!passed) {
                    System.err.println("Java's Skip List Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testSkipListMap();
                if (!passed) {
                    System.err.println("Skip List Map failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        firstTimeThru = true;
        for (int i=0; i<tests; i++) {
            try {
                unsorted = unsorteds[i];
                sorted = sorteds[i];
                string = strings[i];
                passed = testHAMT();
                if (!passed) {
                    System.err.println("HAMT failed.");
                    return false;
                }
            } catch (NullPointerException e) {
                System.err.println(string);
                throw e;
            }
            firstTimeThru = false;
        }
        if (debugTime && debugMemory)
            System.out.println(getTestResults(NUMBER_OF_TESTS, testNames, testResults));   
        testIndex++;

        putOutTheGarbage();

        return true;
    }

    private static void handleError(Object obj) {
        System.err.println(string);
        System.err.println(obj.toString());
        throw new RuntimeException("Error in test.");
    }

    private static final BinarySearchTree<Integer> avlTree = new AVLTree<Integer>();
    private static boolean testAVLTree() {
        String avlName = "AVL Tree <Integer>";
        Collection<Integer> bstCollection = avlTree.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,avlName)) return false;
        return true;
    }

    private static final BTree<Integer> bTree = new BTree<Integer>(2);
    private static boolean testBTree() {
        String bTreeName = "B-Tree <Integer>";
        Collection<Integer> bstCollection = bTree.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,bTreeName)) return false;
        return true;
    }

    private static final BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
    private static boolean testBST() {
        String name = "BST <Integer>";
        Collection<Integer> bstCollection = bst.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,name)) return false;
        return true;
    }

    private static final BinaryHeap.BinaryHeapArray<Integer> aHeapMin = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
    private static final BinaryHeap.BinaryHeapTree<Integer> tHeapMin = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
    private static boolean testMinHeap() {
        String aNameMin = "Min-Heap <Integer> [array]";
        Collection<Integer> aCollectionMin = aHeapMin.toCollection();
        if (!testJavaCollection(aCollectionMin,Integer.class,aNameMin)) return false;

        String tNameMin = "Min-Heap <Integer> [tree]";
        Collection<Integer> tCollectionMin = tHeapMin.toCollection();
        if (!testJavaCollection(tCollectionMin,Integer.class,tNameMin)) return false;

        return true;
    }

    private static final BinaryHeap.BinaryHeapArray<Integer> aHeapMax = new BinaryHeap.BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
    private static final BinaryHeap.BinaryHeapTree<Integer> tHeapMax = new BinaryHeap.BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
    private static boolean testMaxHeap() {
        String aNameMax = "Max-Heap <Integer> [array]";
        Collection<Integer> aCollectionMax = aHeapMax.toCollection();
        if (!testJavaCollection(aCollectionMax,Integer.class,aNameMax)) return false;

        String lNameMax = "Max-Heap <Integer> [tree]";
        Collection<Integer> tCollectionMax = tHeapMax.toCollection();
        if (!testJavaCollection(tCollectionMax,Integer.class,lNameMax)) return false;

        return true;
    }

    private static final HashMap<Integer,String> pHashMap = new HashMap<Integer,String>(HashMap.Type.PROBING, ARRAY_SIZE/2);
    private static final HashMap<Integer,String> cHashMap = new HashMap<Integer,String>(HashMap.Type.CHAINING, ARRAY_SIZE/2);
    private static boolean testHashMap() {
        String name = "Probing HashMap <Integer>";
        java.util.Map<Integer,String> jMap = pHashMap.toMap();

        if (!testJavaMap(jMap,Integer.class,name)) return false;

        name = "Chaining HashMap <Integer>";
        jMap = cHashMap.toMap();

        if (!testJavaMap(jMap,Integer.class,name)) return false;

        return true;
    }

    private static final HashArrayMappedTrie<Integer,String> hamt = new HashArrayMappedTrie<Integer,String>();
    private static boolean testHAMT() {
        String hamtName = "HAMT <Integer>";
        java.util.Map<Integer,String> jMap = hamt.toMap();

        if (!testJavaMap(jMap,Integer.class,hamtName)) return false;
        return true;
    }

    private static final java.util.Map<Integer,String> javaHashMap = new java.util.HashMap<Integer,String>(ARRAY_SIZE/2);
    private static boolean testJavaHashMap() {
        String name = "Java's HashMap <Integer>";

        if (!testJavaMap(javaHashMap,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.PriorityQueue<Integer> javaMinArrayHeap = new java.util.PriorityQueue<Integer>(10,
        new Comparator<Integer>() {
            @Override
            public int compare(Integer arg0, Integer arg1) {
                if (arg0.compareTo(arg1) < 0)
                    return -1;
                else if (arg1.compareTo(arg0) < 0)
                    return 1;
                return 0;
            }
        }
    );
    private static boolean testJavaMinHeap() {
        String name = "Java's Min-Heap <Integer> [array]";

        if (!testJavaCollection(javaMinArrayHeap,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.PriorityQueue<Integer> javaMaxArrayHeap = new java.util.PriorityQueue<Integer>(10,
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
    private static boolean testJavaMaxHeap() {
        String name = "Java's Max-Heap <Integer> [array]";

        if (!testJavaCollection(javaMaxArrayHeap,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.List<Integer> javaArrayList = new java.util.ArrayList<Integer>();
    private static boolean testJavaArrayList() {
        String name = "Java's List <Integer> [array]";

        if (!testJavaCollection(javaArrayList,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.List<Integer> javaLinkedList = new java.util.LinkedList<Integer>();
    private static boolean testJavaLinkedList() {
        String name = "Java's List <Integer> [linked]";

        if (!testJavaCollection(javaLinkedList,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.Deque<Integer> javaArrayQueue = new java.util.ArrayDeque<Integer>();
    private static boolean testJavaArrayQueue() {
        String name = "Java's Queue <Integer> [array]";

        if (!testJavaCollection(javaArrayQueue,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.Deque<Integer> javaLinkedQueue = new java.util.LinkedList<Integer>();
    private static boolean testJavaLinkedQueue() {
        String name = "Java's Queue <Integer> [linked]";

        if (!testJavaCollection(javaLinkedQueue,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.TreeSet<Integer> javaRedBlackTreeInteger = new java.util.TreeSet<Integer>();
    private static boolean testJavaRedBlackIntegerTree() {
        String name = "Java's Red-Black Tree <Integer>";

        if (!testJavaCollection(javaRedBlackTreeInteger,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.TreeSet<String> javaRedBlackTreeString = new java.util.TreeSet<String>();
    private static boolean testJavaRedBlackStringTree() {
        String name = "Java's Red-Black Tree <String>";

        if (!testJavaCollection(javaRedBlackTreeString,String.class,name)) return false;
        return true;
    }

    private static final java.util.Stack<Integer> javaStack = new java.util.Stack<Integer>();
    private static boolean testJavaStack() {
        String name = "Java's Stack <Integer> [array]";

        if (!testJavaCollection(javaStack,Integer.class,name)) return false;
        return true;
    }

    private static final java.util.Map<String,Integer> javaTreeMap = new java.util.TreeMap<String,Integer>();
    private static boolean testJavaTreeMap() {
        String name = "Java's TreeMap <String>";

        if (!testJavaMap(javaTreeMap,String.class,name)) return false;
        return true;
    }

    private static final List.ArrayList<Integer> arrayList = new List.ArrayList<Integer>();
    private static boolean testArrayList() {
        String name = "List <Integer> [array]";
        Collection<Integer> aCollection = arrayList.toCollection();

        if (!testJavaCollection(aCollection,Integer.class,name)) return false;
        return true;
    }

    private static final List.LinkedList<Integer> linkedList = new List.LinkedList<Integer>();
    private static boolean testLinkedList() {
        String name = "List <Integer> [linked]";
        Collection<Integer> lCollection = linkedList.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,name)) return false;
        return true;
    }

    private static final PatriciaTrie<String> patriciaTrie = new PatriciaTrie<String>();
    private static boolean testPatriciaTrie() {
        String name = "PatriciaTrie <String>";
        Collection<String> bstCollection = patriciaTrie.toCollection();

        
        if (!testJavaCollection(bstCollection,String.class,name)) return false;
        return true;
    }

    private static final Queue.ArrayQueue<Integer> arrayQueue = new Queue.ArrayQueue<Integer>();
    private static boolean testArrayQueue() {
        String name = "Queue <Integer> [array]";
        Collection<Integer> aCollection = arrayQueue.toCollection();

        if (!testJavaCollection(aCollection,Integer.class,name)) return false;
        return true;
    }

    private static final Queue.LinkedQueue<Integer> linkedQueue = new Queue.LinkedQueue<Integer>();
    private static boolean testLinkedQueue() {
        String name = "Queue <Integer> [linked]";
        Collection<Integer> lCollection = linkedQueue.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,name)) return false;
        return true;
    }

    private static final RadixTrie<String,Integer> radixTrie = new RadixTrie<String,Integer>();
    private static boolean testRadixTrie() {
        String name = "RadixTrie <String>";
        java.util.Map<String,Integer> jMap = radixTrie.toMap();

        if (!testJavaMap(jMap,String.class,name)) return false;
        return true;
    }

    private static final BinarySearchTree<Integer> redBlackTree = new RedBlackTree<Integer>();
    private static boolean testRedBlackTree() {
        String name = "Red-Black Tree <Integer>";
        Collection<Integer> bstCollection = redBlackTree.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,name)) return false;
        return true;
    }

    private static final NavigableSet<Integer> javaSkipList = new ConcurrentSkipListSet<Integer>();
    private static boolean testJavaSkipList() {
        String name = "Java's SkipListSet <Integer>";
        Collection<Integer> lCollection = javaSkipList;

        if (!testJavaCollection(lCollection,Integer.class,name)) return false;
        return true;
    }

    private static final SkipList<Integer> skipList = new SkipList<Integer>();
    private static boolean testSkipList() {
        String name = "SkipList <Integer>";
        Collection<Integer> lCollection = skipList.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,name)) return false;
        return true;
    }

    private static final BinarySearchTree<Integer> splayTree = new SplayTree<Integer>();
    private static boolean testSplayTree() {
        String name = "Splay Tree <Integer>";
        Collection<Integer> bstCollection = splayTree.toCollection();

        if (!testJavaCollection(bstCollection,Integer.class,name)) return false;
        return true;
    }

    private static final Stack.ArrayStack<Integer> arrayStack = new Stack.ArrayStack<Integer>();
    private static boolean testArrayStack() {
        String name = "Stack <Integer> [array]";
        Collection<Integer> aCollection = arrayStack.toCollection();

        if (!testJavaCollection(aCollection,Integer.class,name)) return false;
        return true;
    }

    private static final Stack.LinkedStack<Integer> linkedStack = new Stack.LinkedStack<Integer>();
    private static boolean testLinkedStack() {
        String name = "Stack <Integer> [linked]";
        Collection<Integer> lCollection = linkedStack.toCollection();

        if (!testJavaCollection(lCollection,Integer.class,name)) return false;
        return true;
    }

    private static final BinarySearchTree<Integer> treap = new Treap<Integer>();
    private static boolean testTreap() {
        String name = "Treap <Integer>";
        Collection<Integer> treapCollection = treap.toCollection();

        if (!testJavaCollection(treapCollection,Integer.class,name)) return false;
        return true;
    }

    private static final TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>();
    private static boolean testTreeMap() {
        String name = "TreeMap <String>";
        java.util.Map<String,Integer> jMap = treeMap.toMap();

        if (!testJavaMap(jMap,String.class,name)) return false;
        return true;
    }

    private static final Trie<String> trie = new Trie<String>();
    private static boolean testTrie() {
        String name = "Trie <String>";
        Collection<String> trieCollection = trie.toCollection();

        if (!testJavaCollection(trieCollection,String.class,name)) return false;
        return true;
    }

    private static final TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
    private static boolean testTrieMap() {
        String name = "TrieMap <String>";
        java.util.Map<String,Integer> jMap = trieMap.toMap();

        if (!testJavaMap(jMap,String.class,name)) return false;
        return true;
    }

    private static final ConcurrentSkipListMap<String,Integer> javaSkipListMap = new ConcurrentSkipListMap<String,Integer>();
    private static boolean testJavaSkipListMap() {
        String name = "Java's SkipListMap <String>";

        if (!testJavaMap(javaSkipListMap,String.class,name)) return false;
        return true;
    }

    private static final SkipListMap<String,Integer> skipListMap = new SkipListMap<String,Integer>();
    private static boolean testSkipListMap() {
        String name = "SkipListMap <String>";
        java.util.Map<String,Integer> jMap = skipListMap.toMap();

        if (!testJavaMap(jMap,String.class,name)) return false;
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
