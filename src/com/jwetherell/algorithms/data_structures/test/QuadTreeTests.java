package com.jwetherell.algorithms.data_structures.test;

import java.util.Collections;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.QuadTree;

public class QuadTreeTests {

    private static final Random RANDOM = new Random();
    private static final int SIZE = 1000;
    private static final java.util.Set<QuadTree.XYPoint> SET = new java.util.HashSet<QuadTree.XYPoint>(SIZE);
    private static final java.util.List<QuadTree.XYPoint> QUERY = new java.util.ArrayList<QuadTree.XYPoint>(SIZE);

    static {
        while (SET.size() < SIZE) {
            float x = RANDOM.nextInt(SIZE);
            float y = RANDOM.nextInt(SIZE);
            QuadTree.XYPoint xyPoint = new QuadTree.XYPoint(x,y);
            SET.add(xyPoint);
        }

        while (QUERY.size() < SIZE) {
            float x = RANDOM.nextInt(SIZE);
            float y = RANDOM.nextInt(SIZE);
            QuadTree.XYPoint xyPoint = new QuadTree.XYPoint(x,y);
            QUERY.add(xyPoint);
        }
    }

    @Test
    public void testPointBasedQuadTree() {
        QuadTree<?> tree = new QuadTree.PointRegionQuadTree<QuadTree.XYPoint>(0,0,SIZE,SIZE);
        runTests(tree, SIZE, SET, QUERY);
    }

    @Test
    public void testRectangleBasedQuadTree() {
        QuadTree<?> tree = new QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox>(0,0,SIZE,SIZE,10,10);
        runTests(tree, SIZE, SET, QUERY);
    }

    private static final <P extends QuadTree.XYPoint> void runTests(QuadTree<P> tree, int size, java.util.Set<P> set, java.util.List<P> query) {
        // Insert all in set
        for (QuadTree.XYPoint p : set) {
            boolean r = tree.insert(p.getX(), p.getY());
            assertTrue("Not added to tree. p="+p.toString(), tree, r);
        }

        // We should find all points here (tests structure)
        for (QuadTree.XYPoint p : set) {
            java.util.List<P> r = tree.queryRange(p.getX(), p.getY(), 1, 1);
            assertTrue("Quad tree queryRange error. p="+p.toString()+" r="+r, tree, r.size()>0);
        }

        // We may not find all points here (tests query speed)
        for (QuadTree.XYPoint p : query) {
            java.util.List<P> r = tree.queryRange(p.getX(), p.getY(), 1, 1);
            if (set.contains(p))
                assertTrue("Point should be in tree. p="+p.toString()+" r="+r, tree, r.size()>0);
        }

        // Result set should not contain duplicates
        java.util.List<P> result = tree.queryRange(0, 0, size, size);
        Collections.sort(result);
        QuadTree.XYPoint prev = null;
        for (QuadTree.XYPoint p : result) {
            assertFalse("Quad tree compare error. p="+p+" prev="+prev+" result="+result, tree, (prev!=null && prev.equals(p)));
            prev = p;
        }

        // Remove all
        for (QuadTree.XYPoint p : set) {
            boolean removed = tree.remove(p.getX(), p.getY());
            assertTrue("Quad tree remove error. removed="+removed+" p="+p.toString(), tree, removed);
        }
    }

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final <P extends QuadTree.XYPoint> void assertFalse(String msg, QuadTree<P> obj, boolean isFalse) {
        String toString = "";
        if (isFalse==true)
            toString = "\n"+obj.toString();
        Assert.assertFalse(msg+toString, isFalse);
    }

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final <P extends QuadTree.XYPoint> void assertTrue(String msg, QuadTree<P> obj, boolean isTrue) {
        String toString = "";
        if (isTrue==false)
            toString = "\n"+obj.toString();
        Assert.assertTrue(msg+toString, isTrue);
    }
}
