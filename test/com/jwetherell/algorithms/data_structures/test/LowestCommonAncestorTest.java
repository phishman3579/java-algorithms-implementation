package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.LowestCommonAncestor;
import com.jwetherell.algorithms.data_structures.LowestCommonAncestor.NodesNotInSameTreeException;
import com.jwetherell.algorithms.data_structures.LowestCommonAncestor.TreeNode;

public class LowestCommonAncestorTest {

    @Test
    public void largeTreeTest() throws NodesNotInSameTreeException {

        final TreeNode<Integer> root = new TreeNode<Integer>();
        final TreeNode<Integer> left = root.addChild();
        final TreeNode<Integer> middle = root.addChild();
        final TreeNode<Integer> right = root.addChild();

        //long path
        TreeNode<Integer> v = left;
        for (int i = 0; i<1000; i++)
            v = v.addChild();
        TreeNode<Integer> leftRight = left.addChild();
        assertEquals(LowestCommonAncestor.lowestCommonAncestor(v, leftRight), left);

        for (int i = 0; i<2000; i++) {
            leftRight = leftRight.addChild();
            assertEquals(LowestCommonAncestor.lowestCommonAncestor(v, leftRight), left);
        }

        assertEquals(LowestCommonAncestor.lowestCommonAncestor(middle, right), root);
        assertEquals(LowestCommonAncestor.lowestCommonAncestor(root, right), root);
        assertEquals(LowestCommonAncestor.lowestCommonAncestor(root, root), root);

        final TreeNode<Integer> root2 = new TreeNode<Integer>();
        boolean thrownException = false;
        try {
            LowestCommonAncestor.lowestCommonAncestor(v, root2);
        } catch (NodesNotInSameTreeException e) {
            thrownException = true;
        }
        assertTrue(thrownException);

        final TreeNode<Integer> deepChild = v.addChild(101);
        assertEquals(deepChild, root.find(101));
        assertTrue(root.contains(101));

        assertNull(root.find(102));
        assertFalse(root.contains(102));
    }
}
