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
        QuadTree<QuadTree.XYPoint> tree = new QuadTree.PointRegionQuadTree<QuadTree.XYPoint>(0,0,SIZE,SIZE);

            // Insert all in set
            for (QuadTree.XYPoint p : SET) {
                boolean r = tree.insert(p.getX(), p.getY());
                assertTrue("Not added to tree. p="+p.toString(), tree, r);
            }

            // We should find all points here (tests structure)
            for (QuadTree.XYPoint p : SET) {
                java.util.Collection<QuadTree.XYPoint> r = tree.queryRange(p.getX(), p.getY(), 1, 1);
                assertTrue("Quad tree queryRange error. p="+p.toString()+" r="+r, tree, r.size()>0);
            }

            // We may not find all points here (tests query speed)
            for (QuadTree.XYPoint p : QUERY) {
                java.util.Collection<QuadTree.XYPoint> r = tree.queryRange(p.getX(), p.getY(), 1, 1);
                if (SET.contains(p))
                    assertTrue("Point should be in tree. p="+p.toString()+" r="+r, tree, r.size()>0);
            }

            // Result set should not contain duplicates
            java.util.List<QuadTree.XYPoint> result = new java.util.ArrayList<QuadTree.XYPoint>();
            result.addAll(tree.queryRange(0, 0, SIZE, SIZE));
            Collections.sort(result);
            QuadTree.XYPoint prev = null;
            for (QuadTree.XYPoint p : result) {
                assertFalse("Quad tree compare error. p="+p+" prev="+prev+" result="+result, tree, (prev!=null && prev.equals(p)));
                prev = p;
            }

            // Remove all
            for (QuadTree.XYPoint p : SET) {
                boolean removed = tree.remove(p.getX(), p.getY());
                assertTrue("Quad tree remove error. removed="+removed+" p="+p.toString(), tree, removed);
            }
        }

    @Test
    public void testRectangleBasedQuadTree() {
        QuadTree<QuadTree.AxisAlignedBoundingBox> tree = new QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox>(0,0,SIZE,SIZE,10,10);

        // Insert all in set
        for (QuadTree.XYPoint p : SET) {
            boolean r = tree.insert(p.getX(), p.getY());
            assertTrue("Not added to tree. p="+p.toString(), tree, r);
        }

        // We should find all points here (tests structure)
        for (QuadTree.XYPoint p : SET) {
            java.util.Collection<QuadTree.AxisAlignedBoundingBox> r = tree.queryRange(p.getX(), p.getY(), 1, 1);
            assertTrue("Quad tree queryRange error. p="+p.toString()+" r="+r, tree, r.size()>0);
        }

        // We may not find all points here (tests query speed)
        for (QuadTree.XYPoint p : QUERY) {
            java.util.Collection<QuadTree.AxisAlignedBoundingBox> r = tree.queryRange(p.getX(), p.getY(), 1, 1);
            if (SET.contains(p))
                assertTrue("Point should be in tree. p="+p.toString()+" r="+r, tree, r.size()>0);
        }

        // Result set should not contain duplicates
        java.util.ArrayList<QuadTree.AxisAlignedBoundingBox> result = new java.util.ArrayList<QuadTree.AxisAlignedBoundingBox>();
        result.addAll(tree.queryRange(0, 0, SIZE, SIZE));
        Collections.sort(result);
        QuadTree.XYPoint prev = null;
        for (QuadTree.XYPoint p : result) {
            assertFalse("Quad tree compare error. p="+p+" prev="+prev+" result="+result, tree, (prev!=null && prev.equals(p)));
            prev = p;
        }

        // Remove all
        for (QuadTree.XYPoint p : SET) {
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
