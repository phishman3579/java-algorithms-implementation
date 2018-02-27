package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.BTree;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.TreeTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class BTreeTests {
    @Test
    public void testBTree() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "B-Tree";
        BTree<Integer> bst = new BTree<Integer>(2);
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName, data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                                                     data.unsorted, data.sorted, data.invalid));
    }

/*
 * By creating a BTree with order of 0,
 * and only one key, testing branch 
 * "keySize > maxKeySize"
 * keySize = amount of elements, 
 * maxKeySize = keySize*2
 *
*/  
    @Test
    public void validateNodeBtreeTest1(){
        BTree<Integer> bt = new BTree<>(0);
        bt.add(1);
        assertTrue(!(bt.validate()));
    }


}

