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
    public void testRangeMaxSegmentTree() {
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
        testRangeMaxSegmentTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMaxSegmentTree(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMaxSegmentTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMaxSegmentTree(segments);
    }

    private void testRangeMaxSegmentTree(java.util.List<SegmentTree.Data.RangeMaximumData<Integer>> segments) {   // Range Maximum Segment tree
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
    public void testRangeMinSegmentTree() {
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
        testRangeMinSegmentTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeMinSegmentTree(segments);

        // Try in order
        Collections.sort(segments);
        testRangeMinSegmentTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeMinSegmentTree(segments);
    }

    private void testRangeMinSegmentTree(java.util.List<SegmentTree.Data.RangeMinimumData<Integer>> segments) {   // Range Minimum Segment tree
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

    @Test
    public void testRangeSumSegmentTree() {
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
        testRangeSumSegmentTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumSegmentTree(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumSegmentTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumSegmentTree(segments);
    }

    private void testRangeSumSegmentTree(java.util.List<SegmentTree.Data.RangeSumData<Integer>> segments) {   // Range Sum Segment tree
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
