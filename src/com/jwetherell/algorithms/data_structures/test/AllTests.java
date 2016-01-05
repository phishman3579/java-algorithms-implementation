package com.jwetherell.algorithms.data_structures.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
                AVLTreeTests.class,
                BinaryHeapTests.class,
                BinarySearchTreeTests.class,
                BTreeTests.class,
                CompactSuffixTrieTests.class,
                DisjointSetTests.class,
                FenwickTreeTests.class,
                GraphTests.class,
                HashArrayMappedTreeTests.class,
                HashMapTests.class,
                ImplicitKeyTreapTests.class,
                IntervalTreeTests.class,
                KdTreeTests.class,
                ListTests.class,
                MatrixTests.class,
                PatriciaTreeTests.class,
                QuadTreeTests.class,
                QueueTests.class,
                RadixTrieTests.class,
                RedBlackTreeTests.class,
                SegmentTreeTests.class,
                SkipListMapTests.class,
                SkipListTests.class,
                SplayTreeTests.class,
                StackTests.class,
                SuffixTreeTests.class,
                SuffixTrieTests.class,
                TreapTests.class,
                TreeMapTests.class,
                TrieTests.class,
                TrieMapTests.class
               }
             )

public class AllTests {

    /**
     * @param args
     */
    public static void main(String[] args) {   
        JUnitCore core = new JUnitCore(); 
        core.run(AllTests.class); 
    }
}
