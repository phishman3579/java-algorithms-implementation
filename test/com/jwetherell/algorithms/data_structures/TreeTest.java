package com.jwetherell.algorithms.data_structures;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void largeTreeTest() throws Tree.NodesNotInSameTreeException {
        Tree<Object> root = new Tree<>();
        Tree<Object> left = root.addChild();
        Tree<Object> middle = root.addChild();
        Tree<Object> right = root.addChild();

        //long path
        Tree<Object> v = left;
        for(int i = 0; i<1000; i++)
            v = v.addChild();
        Tree<Object> leftRight = left.addChild();

        assertEquals(Tree.lowestCommonAncestor(v, leftRight), left);

        for(int i = 0; i<2000; i++) {
            leftRight = leftRight.addChild();

            assertEquals(Tree.lowestCommonAncestor(v, leftRight), left);
        }

        assertEquals(Tree.lowestCommonAncestor(middle, right), root);
        assertEquals(Tree.lowestCommonAncestor(root, right), root);
        assertEquals(Tree.lowestCommonAncestor(root, root), root);

        Tree<Object> root2 = new Tree<>();
        boolean thrownException = false;
        try {
            Tree.lowestCommonAncestor(v, root2);
        } catch (Tree.NodesNotInSameTreeException e) {
            thrownException = true;
        }
        assertTrue(thrownException);
    }
}