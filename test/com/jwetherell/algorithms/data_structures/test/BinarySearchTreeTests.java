package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.TreeTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class BinarySearchTreeTests {

    @Test
    public void testBST() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "BST";
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName,
                                     data.unsorted, data.invalid));

        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                                                 data.unsorted, data.sorted, data.invalid));
    }
}
