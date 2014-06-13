package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Random;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.QuadTree;

public class QuadTreeTests {

    private static final Random RANDOM = new Random();

    @Test
    public void testQuadTree() {
        int size = 1000;

        java.util.Set<QuadTree.XYPoint> set = new java.util.HashSet<QuadTree.XYPoint>(size);
        while (set.size() < size) {
            float x = RANDOM.nextInt(size);
            float y = RANDOM.nextInt(size);
            QuadTree.XYPoint xyPoint = new QuadTree.XYPoint(x,y);
            set.add(xyPoint);
        }

        java.util.List<QuadTree.XYPoint> query = new java.util.ArrayList<QuadTree.XYPoint>(size);
        for (int j=0; j<size; j++) {
            float x = RANDOM.nextInt(size);
            float y = RANDOM.nextInt(size);
            QuadTree.XYPoint xyPoint = new QuadTree.XYPoint(x,y);
            query.add(xyPoint);   
        }

        {   // Point based quad tree
            QuadTree.PointRegionQuadTree<QuadTree.XYPoint> tree = new QuadTree.PointRegionQuadTree<QuadTree.XYPoint>(0,0,size,size);

            {
                for (QuadTree.XYPoint p : set)
                    tree.insert(p.getX(), p.getY());
            }

            // We should find all points here (tests structure)
            for (QuadTree.XYPoint p : set) {
                java.util.List<QuadTree.XYPoint> result = tree.queryRange(p.getX(), p.getY(), 1, 1);
                assertTrue("Quad tree queryRange error. result="+result, result.size()>0);
            }

            // We may not find all points here (tests query speed)
            {
                for (QuadTree.XYPoint p : query)
                    tree.queryRange(p.getX(), p.getY(), 1, 1);
            }

            // Result set should not contain duplicates
            java.util.List<QuadTree.XYPoint> result = tree.queryRange(0, 0, size, size);

            Collections.sort(result);
            QuadTree.XYPoint prev = null;
            for (QuadTree.XYPoint p : result) {
                assertFalse("Quad tree compare error. p="+p+" prev="+prev+" result="+result, (prev!=null && prev.equals(p)));
                prev = p;
            }

            {   // Remove all
                for (QuadTree.XYPoint p : set) {
                    boolean removed = tree.remove(p.getX(), p.getY());
                    assertTrue("Quad tree remove error. removed="+removed+" x="+p.getX()+" y="+p.getY(), removed);
                }
            }
        }

        {   // Rectangle base quad-tree
            QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox> tree = new QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox>(0,0,size,size,10,10);
            {
                for (QuadTree.XYPoint p : set)
                    tree.insert(p.getX(), p.getY(), 1, 1);
            }

            // We should find all points here
            for (QuadTree.XYPoint p : set) {
                java.util.List<QuadTree.AxisAlignedBoundingBox> result = tree.queryRange(p.getX(), p.getY(), 1, 1);
                assertTrue("Quad tree queryRange error. result="+result, result.size()!=0);
            }

            {   // We may not find all points here
                for (QuadTree.XYPoint p : query)
                    tree.queryRange(p.getX(), p.getY(), 1, 1);
            }

            // Result set should not contain duplicates
            java.util.List<QuadTree.AxisAlignedBoundingBox> result = tree.queryRange(0, 0, size, size);

            Collections.sort(result);
            QuadTree.AxisAlignedBoundingBox prev = null;
            for (QuadTree.AxisAlignedBoundingBox p : result) {
                assertFalse("Quad tree compare error. p="+p+" prev="+prev+" result="+result, (prev!=null && prev.equals(p)));
                prev = p;
            }

            {   // Remove all
                for (QuadTree.XYPoint p : set) {
                    boolean removed = tree.remove(p.getX(), p.getY(), 1, 1);
                    assertTrue("Quad tree remove error. removed="+removed+" x="+p.getX()+" y="+p.getY(), removed);
                }
            }
        }
    }
}
