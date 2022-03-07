package com.jwetherell.algorithms.data_structures.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.SegmentTree;
import com.jwetherell.algorithms.data_structures.SegmentTree.Data;
import com.jwetherell.algorithms.data_structures.SegmentTree.DynamicSegmentTree;
import com.jwetherell.algorithms.data_structures.SegmentTree.FlatSegmentTree;

import java.math.BigDecimal;
import java.math.BigInteger;
public class SegmentTreeTests {

    @Test
    public void testQuadrantSegmentTree() {
        java.util.List<SegmentTree.Data.QuadrantData> segments = new ArrayList<SegmentTree.Data.QuadrantData>();
        segments.add(new SegmentTree.Data.QuadrantData(0, 1, 0, 0, 0)); // first point in the 0th quadrant
        segments.add(new SegmentTree.Data.QuadrantData(1, 0, 1, 0, 0)); // second point in the 1st quadrant
        segments.add(new SegmentTree.Data.QuadrantData(2, 0, 0, 1, 0)); // third point in the 2nd quadrant
        segments.add(new SegmentTree.Data.QuadrantData(3, 0, 0, 0, 1)); // fourth point in the 3rd quadrant

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testQuadrantSegmentTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testQuadrantSegmentTree(segments);

        // Try in order
        Collections.sort(segments);
        testQuadrantSegmentTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testQuadrantSegmentTree(segments);
    }

    private void testQuadrantSegmentTree(java.util.List<SegmentTree.Data.QuadrantData> segments) {   // Quadrant Segment tree
        FlatSegmentTree<SegmentTree.Data.QuadrantData> tree = new FlatSegmentTree<SegmentTree.Data.QuadrantData>(segments);

        SegmentTree.Data.QuadrantData query = tree.query(0, 3);
        assertTrue("Quad tree query error. query=0->3 result="+query, tree, (query.quad0==1 && query.quad1==1 && query.quad2==1 && query.quad3==1));

        query = tree.query(2, 3);
        assertTrue("Quad tree query error. query=2->3 result="+query, tree, (query.quad0==0 && query.quad1==0 && query.quad2==1 && query.quad3==1));

        query = tree.query(0, 2);
        assertTrue("Quad tree query error. query=0->2 result="+query, tree, (query.quad0==1 && query.quad1==1 && query.quad2==1 && query.quad3==0));
    }

    @Test
    public void testRangeMaxSegmentTreeInteger() {
        java.util.List<SegmentTree.Data.RangeMaximumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<Integer>>();
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(0,     (Integer) 4));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(1,     (Integer) 2));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(2,     (Integer) 6));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(3,     (Integer) 3));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(4,     (Integer) 1));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(5,     (Integer) 5));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(6,     (Integer) 0));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(7, 17, (Integer) 7));
        segments.add(new SegmentTree.Data.RangeMaximumData<Integer>(21,    (Integer) 10));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMaxSegmentTreeInteger(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTreeInteger(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTreeInteger(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTreeInteger(segments);
    }

    private void testRangeMaxSegmentTreeInteger(java.util.List<SegmentTree.Data.RangeMaximumData<Integer>> segments) {   // Range Maximum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Integer>>(segments, 3);

        SegmentTree.Data.RangeMaximumData<Integer> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.maximum==7);

        query = tree.query(0, 21);
        assertTrue("Segment tree query error. query=0->21 result="+query, tree, query.maximum==10);

        // bounds checking
        {
            // max is first
            query = tree.query(2, 4);
            assertTrue("Segment tree query error. query=2->4 result="+query, tree, query.maximum==6);

            // max is middle
            query = tree.query(4, 6);
            assertTrue("Segment tree query error. query=4->6 result="+query, tree, query.maximum==5);

            // max is last
            query = tree.query(5, 7);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.maximum==7);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.maximum==7);
    }

    @Test
    public void testRangeMaxSegmentTreeBigInteger() {
        java.util.List<SegmentTree.Data.RangeMaximumData<BigInteger>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<BigInteger>>();
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(0,     (BigInteger.valueOf(4))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(1,     (BigInteger.valueOf(2))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(2,     (BigInteger.valueOf(6))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(3,     (BigInteger.valueOf(3))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(4,     (BigInteger.valueOf(1))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(5,     (BigInteger.valueOf(5))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(6,     (BigInteger.valueOf(0))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(7, 17, (BigInteger.valueOf(7))));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigInteger>(21,    (BigInteger.valueOf(10))));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMaxSegmentTreeBigInteger(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTreeBigInteger(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTreeBigInteger(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTreeBigInteger(segments);
    }

    private void testRangeMaxSegmentTreeBigInteger(java.util.List<SegmentTree.Data.RangeMaximumData<BigInteger>> segments) {   // Range Maximum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMaximumData<BigInteger>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<BigInteger>>(segments, 3);

        SegmentTree.Data.RangeMaximumData<BigInteger> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.maximum==BigInteger.valueOf(7));

        query = tree.query(0, 21);
        assertTrue("Segment tree query error. query=0->21 result="+query, tree, query.maximum==BigInteger.valueOf(10));

        // bounds checking
        {
            // max is first
            query = tree.query(2, 4);
            assertTrue("Segment tree query error. query=2->4 result="+query, tree, query.maximum==BigInteger.valueOf(6));

            // max is middle
            query = tree.query(4, 6);
            assertTrue("Segment tree query error. query=4->6 result="+query, tree, query.maximum==BigInteger.valueOf(5));

            // max is last
            query = tree.query(5, 7);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.maximum==BigInteger.valueOf(7));
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.maximum==BigInteger.valueOf(7));
    }

    @Test
    public void testRangeMaxSegmentTreeBigDecimal() {
        java.util.List<SegmentTree.Data.RangeMaximumData<BigDecimal>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<BigDecimal>>();
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(0,     BigDecimal.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(1,     BigDecimal.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(2,     BigDecimal.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(3,     BigDecimal.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(4,     BigDecimal.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(5,     BigDecimal.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(6,     BigDecimal.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(7, 17, BigDecimal.valueOf(7)));
        segments.add(new SegmentTree.Data.RangeMaximumData<BigDecimal>(21,    BigDecimal.valueOf(10)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMaxSegmentTreeBigDecimal(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTreeBigDecimal(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTreeBigDecimal(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTreeBigDecimal(segments);
    }

    private void testRangeMaxSegmentTreeBigDecimal(java.util.List<SegmentTree.Data.RangeMaximumData<BigDecimal>> segments) {   // Range Maximum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMaximumData<BigDecimal>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<BigDecimal>>(segments, 3);

        SegmentTree.Data.RangeMaximumData<BigDecimal> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.maximum==BigDecimal.valueOf(7));

        query = tree.query(0, 21);
        assertTrue("Segment tree query error. query=0->21 result="+query, tree, query.maximum==BigDecimal.valueOf(10));

        // bounds checking
        {
            // max is first
            query = tree.query(2, 4);
            assertTrue("Segment tree query error. query=2->4 result="+query, tree, query.maximum==BigDecimal.valueOf(6));

            // max is middle
            query = tree.query(4, 6);
            assertTrue("Segment tree query error. query=4->6 result="+query, tree, query.maximum==BigDecimal.valueOf(5));

            // max is last
            query = tree.query(5, 7);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.maximum==BigDecimal.valueOf(7));
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.maximum==BigDecimal.valueOf(7));
    }

    @Test
    public void testRangeMaxSegmentTreeLong() {
        java.util.List<SegmentTree.Data.RangeMaximumData<Long>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<Long>>();
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(0,     Long.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(1,     Long.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(2,     Long.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(3,     Long.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(4,     Long.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(5,     Long.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(6,     Long.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(7, 17, Long.valueOf(7)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Long>(21,    Long.valueOf(10)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMaxSegmentTreeLong(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTreeLong(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTreeLong(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTreeLong(segments);
    }

    private void testRangeMaxSegmentTreeLong(java.util.List<SegmentTree.Data.RangeMaximumData<Long>> segments) {   // Range Maximum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Long>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Long>>(segments, 3);

        SegmentTree.Data.RangeMaximumData<Long> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.maximum==7);

        query = tree.query(0, 21);
        assertTrue("Segment tree query error. query=0->21 result="+query, tree, query.maximum==10);

        // bounds checking
        {
            // max is first
            query = tree.query(2, 4);
            assertTrue("Segment tree query error. query=2->4 result="+query, tree, query.maximum==6);

            // max is middle
            query = tree.query(4, 6);
            assertTrue("Segment tree query error. query=4->6 result="+query, tree, query.maximum==5);

            // max is last
            query = tree.query(5, 7);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.maximum==7);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.maximum==7);
    }

    @Test
    public void testRangeMaxSegmentTreeDouble() {
        java.util.List<SegmentTree.Data.RangeMaximumData<Double>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<Double>>();
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(0,     Double.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(1,     Double.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(2,     Double.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(3,     Double.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(4,     Double.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(5,     Double.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(6,     Double.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(7, 17, Double.valueOf(7)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Double>(21,    Double.valueOf(10)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMaxSegmentTreeDouble(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTreeDouble(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTreeDouble(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTreeDouble(segments);
    }

    private void testRangeMaxSegmentTreeDouble(java.util.List<SegmentTree.Data.RangeMaximumData<Double>> segments) {   // Range Maximum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Double>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Double>>(segments, 3);

        SegmentTree.Data.RangeMaximumData<Double> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.maximum==7);

        query = tree.query(0, 21);
        assertTrue("Segment tree query error. query=0->21 result="+query, tree, query.maximum==10);

        // bounds checking
        {
            // max is first
            query = tree.query(2, 4);
            assertTrue("Segment tree query error. query=2->4 result="+query, tree, query.maximum==6);

            // max is middle
            query = tree.query(4, 6);
            assertTrue("Segment tree query error. query=4->6 result="+query, tree, query.maximum==5);

            // max is last
            query = tree.query(5, 7);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.maximum==7);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.maximum==7);
    }


    @Test
    public void testRangeMaxSegmentTreeFloat() {
        java.util.List<SegmentTree.Data.RangeMaximumData<Float>> segments = new ArrayList<SegmentTree.Data.RangeMaximumData<Float>>();
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(0,     Float.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(1,     Float.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(2,     Float.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(3,     Float.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(4,     Float.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(5,     Float.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(6,     Float.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(7, 17, Float.valueOf(7)));
        segments.add(new SegmentTree.Data.RangeMaximumData<Float>(21,    Float.valueOf(10)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMaxSegmentTreeFloat(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTreeFloat(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTreeFloat(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTreeFloat(segments);
    }

    private void testRangeMaxSegmentTreeFloat(java.util.List<SegmentTree.Data.RangeMaximumData<Float>> segments) {   // Range Maximum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Float>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMaximumData<Float>>(segments, 3);

        SegmentTree.Data.RangeMaximumData<Float> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.maximum==7);

        query = tree.query(0, 21);
        assertTrue("Segment tree query error. query=0->21 result="+query, tree, query.maximum==10);

        // bounds checking
        {
            // max is first
            query = tree.query(2, 4);
            assertTrue("Segment tree query error. query=2->4 result="+query, tree, query.maximum==6);

            // max is middle
            query = tree.query(4, 6);
            assertTrue("Segment tree query error. query=4->6 result="+query, tree, query.maximum==5);

            // max is last
            query = tree.query(5, 7);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.maximum==7);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.maximum==7);
    }




    /*-------------------------*/

    @Test
    public void testRangeMinSegmentTreeInteger() {
        java.util.List<SegmentTree.Data.RangeMinimumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<Integer>>();
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(0,  (Integer) 4));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(1,  (Integer) 2));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(2,  (Integer) 6));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(3,  (Integer) 3));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(4,  (Integer) 1));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(5,  (Integer) 5));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(6,  (Integer) 0));
        segments.add(new SegmentTree.Data.RangeMinimumData<Integer>(17, (Integer) 7));    

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMinSegmentTreeInteger(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTreeInteger(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTreeInteger(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTreeInteger(segments);
    }

    private void testRangeMinSegmentTreeInteger(java.util.List<SegmentTree.Data.RangeMinimumData<Integer>> segments) {   // Range Minimum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Integer>>(segments, 5);

        SegmentTree.Data.RangeMinimumData<Integer> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.minimum==0);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.minimum==0);

        // bounds checking
        {
            // min is first
            query = tree.query(1, 3);
            assertTrue("Segment tree query error. query=1->3 result="+query, tree, query.minimum==2);

            // min is middle
            query = tree.query(3, 5);
            assertTrue("Segment tree query error. query=3->5 result="+query, tree, query.minimum==1);

            // min is last
            query = tree.query(1, 4);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.minimum==1);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.minimum==null);
    }

    /*
    Requirement:
    

    */
    @Test
    public void testRangeMinSegmentTreeBigInteger() {
        java.util.List<SegmentTree.Data.RangeMinimumData<BigInteger>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<BigInteger>>();
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(0,  BigInteger.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(1,  BigInteger.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(2,  BigInteger.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(3,  BigInteger.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(4,  BigInteger.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(5,  BigInteger.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(6,  BigInteger.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigInteger>(17, BigInteger.valueOf(7)));    

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMinSegmentTreeBigInteger(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTreeBigInteger(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTreeBigInteger(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTreeBigInteger(segments);
    }

    private void testRangeMinSegmentTreeBigInteger(java.util.List<SegmentTree.Data.RangeMinimumData<BigInteger>> segments) {   // Range Minimum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMinimumData<BigInteger>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<BigInteger>>(segments, 5);

        SegmentTree.Data.RangeMinimumData<BigInteger> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.minimum==BigInteger.valueOf(0));

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.minimum==BigInteger.valueOf(0));

        // bounds checking
        {
            // min is first
            query = tree.query(1, 3);
            assertTrue("Segment tree query error. query=1->3 result="+query, tree, query.minimum==BigInteger.valueOf(2));

            // min is middle
            query = tree.query(3, 5);
            assertTrue("Segment tree query error. query=3->5 result="+query, tree, query.minimum==BigInteger.valueOf(1));

            // min is last
            query = tree.query(1, 4);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.minimum==BigInteger.valueOf(1));
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.minimum==null);
    }

    /*
    Requirement:
    

    */
    @Test
    public void testRangeMinSegmentTreeBigDecimal() {
        java.util.List<SegmentTree.Data.RangeMinimumData<BigDecimal>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<BigDecimal>>();
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(0,  BigDecimal.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(1,  BigDecimal.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(2,  BigDecimal.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(3,  BigDecimal.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(4,  BigDecimal.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(5,  BigDecimal.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(6,  BigDecimal.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMinimumData<BigDecimal>(17, BigDecimal.valueOf(7)));    

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMinSegmentTreeBigDecimal(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTreeBigDecimal(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTreeBigDecimal(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTreeBigDecimal(segments);
    }

    private void testRangeMinSegmentTreeBigDecimal(java.util.List<SegmentTree.Data.RangeMinimumData<BigDecimal>> segments) {   // Range Minimum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMinimumData<BigDecimal>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<BigDecimal>>(segments, 5);

        SegmentTree.Data.RangeMinimumData<BigDecimal> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.minimum==BigDecimal.valueOf(0));

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.minimum==BigDecimal.valueOf(0));

        // bounds checking
        {
            // min is first
            query = tree.query(1, 3);
            assertTrue("Segment tree query error. query=1->3 result="+query, tree, query.minimum==BigDecimal.valueOf(2));

            // min is middle
            query = tree.query(3, 5);
            assertTrue("Segment tree query error. query=3->5 result="+query, tree, query.minimum==BigDecimal.valueOf(1));

            // min is last
            query = tree.query(1, 4);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.minimum==BigDecimal.valueOf(1));
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.minimum==null);
    }

    @Test
    public void testRangeMinSegmentTreeLong() {
        java.util.List<SegmentTree.Data.RangeMinimumData<Long>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<Long>>();
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(0,  Long.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(1,  Long.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(2,  Long.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(3,  Long.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(4,  Long.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(5,  Long.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(6,  Long.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Long>(17, Long.valueOf(7)));    

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMinSegmentTreeLong(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTreeLong(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTreeLong(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTreeLong(segments);
    }

    private void testRangeMinSegmentTreeLong(java.util.List<SegmentTree.Data.RangeMinimumData<Long>> segments) {   // Range Minimum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Long>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Long>>(segments, 5);

        SegmentTree.Data.RangeMinimumData<Long> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.minimum==Long.valueOf(0));

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.minimum==Long.valueOf(0));

        // bounds checking
        {
            // min is first
            query = tree.query(1, 3);
            assertTrue("Segment tree query error. query=1->3 result="+query, tree, query.minimum==Long.valueOf(2));

            // min is middle
            query = tree.query(3, 5);
            assertTrue("Segment tree query error. query=3->5 result="+query, tree, query.minimum==Long.valueOf(1));

            // min is last
            query = tree.query(1, 4);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.minimum==Long.valueOf(1));
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.minimum==null);
    }

    @Test
    public void testRangeMinSegmentTreeDouble() {
        java.util.List<SegmentTree.Data.RangeMinimumData<Double>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<Double>>();
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(0,  Double.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(1,  Double.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(2,  Double.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(3,  Double.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(4,  Double.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(5,  Double.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(6,  Double.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Double>(17, Double.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMinSegmentTreeDouble(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTreeDouble(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTreeDouble(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTreeDouble(segments);
    }

    private void testRangeMinSegmentTreeDouble(java.util.List<SegmentTree.Data.RangeMinimumData<Double>> segments) {   // Range Minimum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Double>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Double>>(segments, 5);

        SegmentTree.Data.RangeMinimumData<Double> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.minimum==0);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.minimum==0);

        // bounds checking
        {
            // min is first
            query = tree.query(1, 3);
            assertTrue("Segment tree query error. query=1->3 result="+query, tree, query.minimum==2);

            // min is middle
            query = tree.query(3, 5);
            assertTrue("Segment tree query error. query=3->5 result="+query, tree, query.minimum==1);

            // min is last
            query = tree.query(1, 4);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.minimum==1);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.minimum==null);
    }

    @Test
    public void testRangeMinSegmentTreeFloat() {
        java.util.List<SegmentTree.Data.RangeMinimumData<Float>> segments = new ArrayList<SegmentTree.Data.RangeMinimumData<Float>>();
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(0,  Float.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(1,  Float.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(2,  Float.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(3,  Float.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(4,  Float.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(5,  Float.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(6,  Float.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeMinimumData<Float>(17, Float.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeMinSegmentTreeFloat(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTreeFloat(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTreeFloat(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTreeFloat(segments);
    }

    private void testRangeMinSegmentTreeFloat(java.util.List<SegmentTree.Data.RangeMinimumData<Float>> segments) {   // Range Minimum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Float>> tree = new FlatSegmentTree<SegmentTree.Data.RangeMinimumData<Float>>(segments, 5);

        SegmentTree.Data.RangeMinimumData<Float> query = tree.query(0, 7);
        assertTrue("Segment tree query error. query=0->7 result="+query, tree, query.minimum==0);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.minimum==0);

        // bounds checking
        {
            // min is first
            query = tree.query(1, 3);
            assertTrue("Segment tree query error. query=1->3 result="+query, tree, query.minimum== 2);

            // min is middle
            query = tree.query(3, 5);
            assertTrue("Segment tree query error. query=3->5 result="+query, tree, query.minimum== 1);

            // min is last
            query = tree.query(1, 4);
            assertTrue("Segment tree query error. query=5->7 result="+query, tree, query.minimum== 1);
        }

        query = tree.query(7); // stabbing
        assertTrue("Segment tree query error. query=7 result="+query, tree, query.minimum==null);
    }

    /* ----------------------------------------- */

    @Test
    public void testRangeSumSegmentTreeInteger() {
        java.util.List<SegmentTree.Data.RangeSumData<Integer>> segments = new ArrayList<SegmentTree.Data.RangeSumData<Integer>>();
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(0,  (Integer) 4));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(1,  (Integer) 2));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(2,  (Integer) 6));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(3,  (Integer) 3));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(4,  (Integer) 1));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(5,  (Integer) 5));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(6,  (Integer) 0));
        segments.add(new SegmentTree.Data.RangeSumData<Integer>(17, (Integer) 7));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumSegmentTreeInteger(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTreeInteger(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTreeInteger(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTreeInteger(segments);
    }

    private void testRangeSumSegmentTreeInteger(java.util.List<SegmentTree.Data.RangeSumData<Integer>> segments) {   // Range Sum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeSumData<Integer>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<Integer>>(segments, 10);

        SegmentTree.Data.RangeSumData<Integer> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum==21);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum==28);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum==15);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum==7);

        query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==null);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum==7);
    }

    @Test
    public void testRangeSumSegmentTreeBigInteger() {
        java.util.List<SegmentTree.Data.RangeSumData<BigInteger>> segments = new ArrayList<SegmentTree.Data.RangeSumData<BigInteger>>();
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(0,  BigInteger.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(1,  BigInteger.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(2,  BigInteger.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(3,  BigInteger.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(4,  BigInteger.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(5,  BigInteger.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(6,  BigInteger.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeSumData<BigInteger>(17, BigInteger.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumSegmentTreeBigInteger(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTreeBigInteger(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTreeBigInteger(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTreeBigInteger(segments);
    }

    private void testRangeSumSegmentTreeBigInteger(java.util.List<SegmentTree.Data.RangeSumData<BigInteger>> segments) {   // Range Sum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeSumData<BigInteger>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<BigInteger>>(segments, 10);

        SegmentTree.Data.RangeSumData<BigInteger> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum.compareTo(BigInteger.valueOf(21)) ==0);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum.compareTo(BigInteger.valueOf(28))==0);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum.compareTo(BigInteger.valueOf(15))==0);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum.compareTo(BigInteger.valueOf(7))==0);

       query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==null);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum.compareTo(BigInteger.valueOf(7))==0);
    }

    @Test
    public void testRangeSumSegmentTreeBigDecimal() {
        java.util.List<SegmentTree.Data.RangeSumData<BigDecimal>> segments = new ArrayList<SegmentTree.Data.RangeSumData<BigDecimal>>();
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(0,  BigDecimal.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(1,  BigDecimal.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(2,  BigDecimal.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(3,  BigDecimal.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(4,  BigDecimal.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(5,  BigDecimal.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(6,  BigDecimal.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeSumData<BigDecimal>(17, BigDecimal.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumSegmentTreeBigDecimal(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTreeBigDecimal(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTreeBigDecimal(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTreeBigDecimal(segments);
    }

    private void testRangeSumSegmentTreeBigDecimal(java.util.List<SegmentTree.Data.RangeSumData<BigDecimal>> segments) {   // Range Sum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeSumData<BigDecimal>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<BigDecimal>>(segments, 10);

        SegmentTree.Data.RangeSumData<BigDecimal> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum.compareTo(BigDecimal.valueOf(21))==0);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum.compareTo(BigDecimal.valueOf(28))==0);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum.compareTo(BigDecimal.valueOf(15))==0);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum.compareTo(BigDecimal.valueOf(7))==0);

        query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==null);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum.compareTo(BigDecimal.valueOf(7))==0);
    }

    @Test
    public void testRangeSumSegmentTreeLong() {
        java.util.List<SegmentTree.Data.RangeSumData<Long>> segments = new ArrayList<SegmentTree.Data.RangeSumData<Long>>();
        segments.add(new SegmentTree.Data.RangeSumData<Long>(0,  Long.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(1,  Long.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(2,  Long.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(3,  Long.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(4,  Long.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(5,  Long.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(6,  Long.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeSumData<Long>(17, Long.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumSegmentTreeLong(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTreeLong(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTreeLong(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTreeLong(segments);
    }

    private void testRangeSumSegmentTreeLong(java.util.List<SegmentTree.Data.RangeSumData<Long>> segments) {   // Range Sum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeSumData<Long>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<Long>>(segments, 10);

        SegmentTree.Data.RangeSumData<Long> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum==Long.valueOf(21));

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum==Long.valueOf(28));

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum==Long.valueOf(15));

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum==Long.valueOf(7));

        query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==null);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum==Long.valueOf(7));
    }

    @Test
    public void testRangeSumSegmentTreeDouble() {
        java.util.List<SegmentTree.Data.RangeSumData<Double>> segments = new ArrayList<SegmentTree.Data.RangeSumData<Double>>();
        segments.add(new SegmentTree.Data.RangeSumData<Double>(0,  Double.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(1,  Double.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(2,  Double.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(3,  Double.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(4,  Double.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(5,  Double.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(6,  Double.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeSumData<Double>(17, Double.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumSegmentTreeDouble(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTreeDouble(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTreeDouble(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTreeDouble(segments);
    }

    private void testRangeSumSegmentTreeDouble(java.util.List<SegmentTree.Data.RangeSumData<Double>> segments) {   // Range Sum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeSumData<Double>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<Double>>(segments, 10);

        SegmentTree.Data.RangeSumData<Double> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum==21);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum==28);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum==15);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum==7);

        query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==null);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum==7);
    }

    @Test
    public void testRangeSumSegmentTreeBigFloat() {
        java.util.List<SegmentTree.Data.RangeSumData<Float>> segments = new ArrayList<SegmentTree.Data.RangeSumData<Float>>();
        segments.add(new SegmentTree.Data.RangeSumData<Float>(0,  Float.valueOf(4)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(1,  Float.valueOf(2)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(2,  Float.valueOf(6)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(3,  Float.valueOf(3)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(4,  Float.valueOf(1)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(5,  Float.valueOf(5)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(6,  Float.valueOf(0)));
        segments.add(new SegmentTree.Data.RangeSumData<Float>(17, Float.valueOf(7)));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumSegmentTreeFloat(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTreeFloat(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTreeFloat(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTreeFloat(segments);
    }

    private void testRangeSumSegmentTreeFloat(java.util.List<SegmentTree.Data.RangeSumData<Float>> segments) {   // Range Sum Segment tree
        FlatSegmentTree<SegmentTree.Data.RangeSumData<Float>> tree = new FlatSegmentTree<SegmentTree.Data.RangeSumData<Float>>(segments, 10);

        SegmentTree.Data.RangeSumData<Float> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum==21);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum==28);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum==15);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum==7);

        query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==null);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum==7);
    }


    /*-----------------------------*/

    final String stravinsky = "Stravinsky";
    final String schoenberg = "Schoenberg";
    final String grieg      = "Grieg";
    final String schubert   = "Schubert";
    final String mozart     = "Mozart";
    final String schuetz    = "Schuetz";

    @Test
    public void testLifespanSegmentTree() {
        java.util.List<SegmentTree.Data.IntervalData<String>> segments = new ArrayList<SegmentTree.Data.IntervalData<String>>();
        segments.add((new SegmentTree.Data.IntervalData<String>(1888, 1971, stravinsky)));
        segments.add((new SegmentTree.Data.IntervalData<String>(1874, 1951, schoenberg)));
        segments.add((new SegmentTree.Data.IntervalData<String>(1843, 1907, grieg)));
        segments.add((new SegmentTree.Data.IntervalData<String>(1779, 1828, schubert)));
        segments.add((new SegmentTree.Data.IntervalData<String>(1756, 1791, mozart)));
        segments.add((new SegmentTree.Data.IntervalData<String>(1585, 1672, schuetz)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testLifespanSegmentTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testLifespanSegmentTree(segments);

        // Try in order
        Collections.sort(segments);
        testLifespanSegmentTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testLifespanSegmentTree(segments);
    }

    private void testLifespanSegmentTree(java.util.List<SegmentTree.Data.IntervalData<String>> segments) {   // Lifespan Interval Segment tree
        DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(segments, 25);

        SegmentTree.Data.IntervalData<String> query = tree.query(1890); // Stabbing
        assertTrue("Segment tree query error. query=1890 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg, grieg)));

        query = tree.query(1909); // Stabbing query
        assertTrue("Segment tree query error. query=1909 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg)));

        query = tree.query(1585); // Stabbing query
        assertTrue("Segment tree query error. query=1585 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(schuetz)));

        query = tree.query(1792, 1903); // Range query
        assertTrue("Segment tree query error. query=1792->1903 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg, grieg, schubert)));

        query = tree.query(1776, 1799); // Range query
        assertTrue("Segment tree query error. query=1776->1799 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(mozart, schubert)));
    }

    final String RED        = "RED";
    final String ORANGE     = "ORANGE";
    final String GREEN      = "GREEN";
    final String DARK_GREEN = "DARK_GREEN";
    final String BLUE       = "BLUE";
    final String PURPLE     = "PURPLE";
    final String BLACK      = "BLACK";

    @Test
    public void testIntervalSegmentTree() { 
        java.util.List<SegmentTree.Data.IntervalData<String>> segments = new ArrayList<SegmentTree.Data.IntervalData<String>>();
        segments.add((new SegmentTree.Data.IntervalData<String>(2,  6,  RED)));
        segments.add((new SegmentTree.Data.IntervalData<String>(3,  5,  ORANGE)));
        segments.add((new SegmentTree.Data.IntervalData<String>(4,  11, GREEN)));
        segments.add((new SegmentTree.Data.IntervalData<String>(5,  10, DARK_GREEN)));
        segments.add((new SegmentTree.Data.IntervalData<String>(8,  12, BLUE)));
        segments.add((new SegmentTree.Data.IntervalData<String>(9,  14, PURPLE)));
        segments.add((new SegmentTree.Data.IntervalData<String>(13, 15, BLACK)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testIntervalSegmentTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testIntervalSegmentTree(segments);

        // Try in order
        Collections.sort(segments);
        testIntervalSegmentTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testIntervalSegmentTree(segments);
    }

    private void testIntervalSegmentTree(java.util.List<SegmentTree.Data.IntervalData<String>> segments) {   // Interval Segment tree
        DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(segments);

        SegmentTree.Data.IntervalData<String> query = tree.query(2); // Stabbing
        assertTrue("Segment tree query error. query=2 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(RED)));

        query = tree.query(4); // Stabbing query
        assertTrue("Segment tree query error. query=4 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(RED, ORANGE, GREEN)));

        query = tree.query(9); // Stabbing query
        assertTrue("Segment tree query error. query=9 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(GREEN, DARK_GREEN, BLUE, PURPLE)));

        query = tree.query(1, 16); // Range query
        assertTrue("Segment tree query error. query=1->16 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(RED, ORANGE, GREEN, DARK_GREEN, BLUE, PURPLE, BLACK)));

        query = tree.query(7, 14); // Range query
        assertTrue("Segment tree query error. query=7->14 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(GREEN, DARK_GREEN, BLUE, PURPLE, BLACK)));

        query = tree.query(14, 15); // Range query
        assertTrue("Segment tree query error. query=14->15 result="+query, tree, collectionsEqual(query.getData(), Arrays.asList(PURPLE, BLACK)));
    }

    @Test
    public void testIntervalSegmentTree2() {
        List<SegmentTree.Data.IntervalData<String>> intervals = new ArrayList<SegmentTree.Data.IntervalData<String>>();
        intervals.add((new SegmentTree.Data.IntervalData<String>(1, 5, "a")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(2, 6, "b")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(3, 7, "c")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(7, 7, "d")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(8, 8, "e")));

        DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(intervals);

        SegmentTree.Data.IntervalData<String> query = tree.query(5); // Stabbing query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("b","c","a")));

        query = tree.query(6); // Stabbing query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("b","c")));

        query = tree.query(7); // Stabbing query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("c","d")));

        query = tree.query(1,7); // Range query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("a","b","c","d")));

        query = tree.query(8); // Stabbing query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("e")));
    }

    @Test
    public void testIntervalSegmentTree3() {
        List<SegmentTree.Data.IntervalData<String>> intervals = new ArrayList<SegmentTree.Data.IntervalData<String>>();
        intervals.add((new SegmentTree.Data.IntervalData<String>(5,  20, "a")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(10, 30, "b")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(12, 15, "c")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(15, 20, "d")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(17, 19, "e")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(30, 40, "f")));

        DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(intervals);

        SegmentTree.Data.IntervalData<String> query = tree.query(6,7); // Range query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("a")));
    }

    @Test
    public void testIntervalSegmentTree4() {
        List<SegmentTree.Data.IntervalData<String>> intervals = new ArrayList<SegmentTree.Data.IntervalData<String>>();
        intervals.add((new SegmentTree.Data.IntervalData<String>(15, 20, "a")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(4,  25, "b")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(3,  30, "c")));

        DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(intervals);

        SegmentTree.Data.IntervalData<String> query = tree.query(26,27); // Range query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("c")));
    }

    @Test
    public void testIntervalSegmentTree5() {
        List<SegmentTree.Data.IntervalData<String>> intervals = new ArrayList<SegmentTree.Data.IntervalData<String>>();
        intervals.add((new SegmentTree.Data.IntervalData<String>(17, 19, "a")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(5,  11, "b")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(23, 23, "c")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(4,  8, "d")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(15, 18, "e")));
        intervals.add((new SegmentTree.Data.IntervalData<String>(7,  10, "f")));

        DynamicSegmentTree<SegmentTree.Data.IntervalData<String>> tree = new DynamicSegmentTree<SegmentTree.Data.IntervalData<String>>(intervals);

        SegmentTree.Data.IntervalData<String> query = tree.query(14,16); // Range query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("e")));

        query = tree.query(12,14); // Range query
        assertTrue("Segment Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList()));
    }
    
    private static boolean collectionsEqual(Collection<?> c1, Collection<?> c2) {
        if (c1.size()!=c2.size()) return false;
        return c1.containsAll(c2) && c2.containsAll(c1);
    }

    private static final Comparator<SegmentTree.Data> REVERSE = new Comparator<SegmentTree.Data>() {
        @Override
        public int compare(Data arg0, Data arg1) {
            int r = arg0.compareTo(arg1);
            return r*-1;
        }       
    };

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final <D extends SegmentTree.Data> void assertTrue(String msg, SegmentTree<D> obj, boolean isTrue) {
        String toString = "";
        if (isTrue==false)
            toString = "\n"+obj.toString();
        Assert.assertTrue(msg+toString, isTrue);
    }
}
