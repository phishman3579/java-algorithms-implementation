package com.jwetherell.algorithms.data_structures;

import org.junit.Test;

import static org.junit.Assert.*;

public class RootedTreeTest {

    @Test
    public void largeTreeTest() throws RootedTree.NodesNotInSameTreeException {
        RootedTree<Integer> root = new RootedTree<>();
        RootedTree<Integer> left = root.addChild();
        RootedTree<Integer> middle = root.addChild();
        RootedTree<Integer> right = root.addChild();

        //long path
        RootedTree<Integer> v = left;
        for(int i = 0; i<1000; i++)
            v = v.addChild();
        RootedTree<Integer> leftRight = left.addChild();

        assertEquals(RootedTree.lowestCommonAncestor(v, leftRight), left);

        for(int i = 0; i<2000; i++) {
            leftRight = leftRight.addChild();

            assertEquals(RootedTree.lowestCommonAncestor(v, leftRight), left);
        }

        assertEquals(RootedTree.lowestCommonAncestor(middle, right), root);
        assertEquals(RootedTree.lowestCommonAncestor(root, right), root);
        assertEquals(RootedTree.lowestCommonAncestor(root, root), root);

        RootedTree<Integer> root2 = new RootedTree<>();
        boolean thrownException = false;
        try {
            RootedTree.lowestCommonAncestor(v, root2);
        } catch (RootedTree.NodesNotInSameTreeException e) {
            thrownException = true;
        }
        assertTrue(thrownException);

        RootedTree<Integer> deepChild = v.addChild(101);
        assertEquals(deepChild, root.find(101));
        assertTrue(root.contains(101));

        assertNull(root.find(102));
        assertFalse(root.contains(102));
    }
}