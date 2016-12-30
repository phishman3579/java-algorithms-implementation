package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinarySearchTree.DepthFirstSearchOrder;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.TreeTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class BinarySearchTreeTests {

    /**
        .....4.... <br>
        ..2.....5. <br>
        1...3..... <br>
     */
    private static final BinarySearchTree<Integer> testBST = new BinarySearchTree<Integer>();
    static {
        testBST.add(4);
        testBST.add(2);
        testBST.add(5);
        testBST.add(1);
        testBST.add(3);
    }

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

    /** 4 2 5 1 3 */
    @Test
    public void testBSF() {
        final Integer[] inOrder = testBST.getBFS();
        final Integer[] expectation = new Integer[]{4, 2, 5, 1, 3};
        for (int i=0; i<inOrder.length; i++) {
            Assert.assertTrue(inOrder[i] == expectation[i]);
        }
    }

    /** 4 2 5 1 3 */
    @Test
    public void testLevelOrder() {
        final Integer[] inOrder = testBST.getBFS();
        final Integer[] expectation = new Integer[]{4, 2, 5, 1, 3};
        for (int i=0; i<inOrder.length; i++) {
            Assert.assertTrue(inOrder[i] == expectation[i]);
        }
    }

    /** BFS and level order are the same */
    @Test
    public void testLargeBFS() {
        TestData data = Utils.generateTestData(1000);

        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i : data.unsorted) {
            bst.add(i);
        }
        bst.getBFS();
    }

    /** 4 2 1 3 5 */
    @Test
    public void testPreOrderDFS() {
        final Integer[] inOrder = testBST.getDFS(DepthFirstSearchOrder.preOrder);
        final Integer[] expectation = new Integer[]{4, 2, 1, 3, 5};
        for (int i=0; i<inOrder.length; i++) {
            Assert.assertTrue(inOrder[i] == expectation[i]);
        }
    }

    @Test
    public void testLargePreOrderDFS() {
        TestData data = Utils.generateTestData(1000);

        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i : data.unsorted) {
            bst.add(i);
        }
        bst.getDFS(DepthFirstSearchOrder.preOrder);
    }

    /** 1 2 3 4 5 */
    @Test
    public void testInOrderDFS() {
        final Integer[] inOrder = testBST.getDFS(DepthFirstSearchOrder.inOrder);
        final Integer[] expectation = new Integer[]{1, 2, 3, 4, 5};
        for (int i=0; i<inOrder.length; i++) {
            Assert.assertTrue(inOrder[i] == expectation[i]);
        }
    }

    @Test
    public void testLargeInOrderDFS() {
        TestData data = Utils.generateTestData(1000);

        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i : data.unsorted) {
            bst.add(i);
        }
        bst.getDFS(DepthFirstSearchOrder.inOrder);
    }

    /** 1 3 2 5 4 */
    @Test
    public void testPostOrderDFS() {
        final Integer[] inOrder = testBST.getDFS(DepthFirstSearchOrder.postOrder);
        final Integer[] expectation = new Integer[]{1, 3, 2, 5, 4};
        for (int i=0; i<inOrder.length; i++) {
            Assert.assertTrue(inOrder[i] == expectation[i]);
        }
    }

    @Test
    public void testLargePostOrderDFS() {
        TestData data = Utils.generateTestData(1000);

        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i : data.unsorted) {
            bst.add(i);
        }
        bst.getDFS(DepthFirstSearchOrder.postOrder);
    }

}
