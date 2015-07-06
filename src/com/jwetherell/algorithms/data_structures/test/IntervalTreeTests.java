package com.jwetherell.algorithms.data_structures.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.IntervalTree;

public class IntervalTreeTests {
	
	private static boolean collectionsEqual(Collection<?> c1, Collection<?> c2) {
	    if (c1.size()!=c2.size()) return false;
		return c1.containsAll(c2) && c2.containsAll(c1);
	}

    private static final Comparator<IntervalTree.IntervalData<String>> REVERSE = new Comparator<IntervalTree.IntervalData<String>>() {
        @Override
        public int compare(IntervalTree.IntervalData<String> arg0, IntervalTree.IntervalData<String> arg1) {
            int r = arg0.compareTo(arg1);
            return r*-1;
        }       
    };

    final String stravinsky = "Stravinsky";
    final String schoenberg = "Schoenberg";
    final String grieg      = "Grieg";
    final String schubert   = "Schubert";
    final String mozart     = "Mozart";
    final String schuetz    = "Schuetz";

    @Test
    public void testLifespanIntervalTree() {
        java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
        intervals.add((new IntervalTree.IntervalData<String>(1888, 1971, stravinsky)));
        intervals.add((new IntervalTree.IntervalData<String>(1874, 1951, schoenberg)));
        intervals.add((new IntervalTree.IntervalData<String>(1843, 1907, grieg)));
        intervals.add((new IntervalTree.IntervalData<String>(1779, 1828, schubert)));
        intervals.add((new IntervalTree.IntervalData<String>(1756, 1791, mozart)));
        intervals.add((new IntervalTree.IntervalData<String>(1585, 1672, schuetz)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testLifespanIntervalTree(intervals);

        // Randomize it
        Collections.shuffle(intervals);
        testLifespanIntervalTree(intervals);

        // Try in order
        Collections.sort(intervals);
        testLifespanIntervalTree(intervals);

        // Try reverse order
        Collections.sort(intervals,REVERSE);
        testLifespanIntervalTree(intervals);
    }

    public void testLifespanIntervalTree(java.util.List<IntervalTree.IntervalData<String>> intervals) {   // Lifespan Interval tree
        IntervalTree<String> tree = new IntervalTree<String>(intervals);

        IntervalTree.IntervalData<String> query = tree.query(1890); // Stabbing query
        assertTrue("Interval Tree query error. query=1890 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg, grieg)));

        query = tree.query(1909); // Stabbing query
        assertTrue("Interval Tree query error. query=1909 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg)));

        query = tree.query(1792, 1903); // Range query
        assertTrue("Interval Tree query error. query=1792->1903 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg, grieg, schubert)));

        query = tree.query(1776, 1799); // Range query
        assertTrue("Interval Tree query error. query=1776->1799 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(schubert, mozart)));
    }

    final String RED        = "RED";
    final String ORANGE     = "ORANGE";
    final String GREEN      = "GREEN";
    final String DARK_GREEN = "DARK_GREEN";
    final String BLUE       = "BLUE";
    final String PURPLE     = "PURPLE";
    final String BLACK      = "BLACK";

    @Test
    public void testIntervalTree() {
        java.util.List<IntervalTree.IntervalData<String>> intervals = new java.util.ArrayList<IntervalTree.IntervalData<String>>();
        intervals.add((new IntervalTree.IntervalData<String>(2,  6,   RED)));
        intervals.add((new IntervalTree.IntervalData<String>(3,  5,   ORANGE)));
        intervals.add((new IntervalTree.IntervalData<String>(4,  11,  GREEN)));
        intervals.add((new IntervalTree.IntervalData<String>(5,  10,  DARK_GREEN)));
        intervals.add((new IntervalTree.IntervalData<String>(8,  12,  BLUE)));
        intervals.add((new IntervalTree.IntervalData<String>(9,  14,  PURPLE)));
        intervals.add((new IntervalTree.IntervalData<String>(13, 15,  BLACK)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testIntervalTree(intervals);

        // Randomize it
        Collections.shuffle(intervals);
        testIntervalTree(intervals);

        // Try in order
        Collections.sort(intervals);
        testIntervalTree(intervals);

        // Try reverse order
        Collections.sort(intervals,REVERSE);
        testIntervalTree(intervals);
    }

    private void testIntervalTree(java.util.List<IntervalTree.IntervalData<String>> intervals) {
        IntervalTree<String> tree = new IntervalTree<String>(intervals);

        IntervalTree.IntervalData<String> query = tree.query(2); // Stabbing query
        assertTrue("Interval Tree query error. query=2 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(RED)));

        query = tree.query(4); // Stabbing query
        assertTrue("Interval Tree query error. query=4 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(RED, ORANGE, GREEN)));

        query = tree.query(9); // Stabbing query
        assertTrue("Interval Tree query error. query=9 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(GREEN, DARK_GREEN, BLUE, PURPLE)));

        query = tree.query(1, 16); // Range query
        assertTrue("Interval Tree query error. query=1->16 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(RED, ORANGE, GREEN, DARK_GREEN, BLUE, PURPLE, BLACK)));

        query = tree.query(7, 14); // Range query
        assertTrue("Interval Tree query error. query=7->14 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(GREEN, DARK_GREEN, BLUE, PURPLE, BLACK)));

        query = tree.query(14, 15); // Range query
        assertTrue("Interval Tree query error. query=14->15 returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList(PURPLE, BLACK)));
    }

    @Test
    public void testIntervalTree2() {
        List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
        intervals.add((new IntervalTree.IntervalData<String>(1, 5, "a")));
        intervals.add((new IntervalTree.IntervalData<String>(2, 6, "b")));
        intervals.add((new IntervalTree.IntervalData<String>(3, 7, "c")));
        intervals.add((new IntervalTree.IntervalData<String>(7, 7, "d")));
        intervals.add((new IntervalTree.IntervalData<String>(8, 8, "e")));

        IntervalTree<String> tree = new IntervalTree<String>(intervals);

        IntervalTree.IntervalData<String> query = tree.query(5); // Stabbing query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("b","c","a")));

        query = tree.query(6); // Stabbing query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("b","c")));

        query = tree.query(7); // Stabbing query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("c","d")));

        query = tree.query(1,7); // Range query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("a","b","c","d")));

        query = tree.query(8); // Stabbing query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("e")));
    }

    @Test
    public void testIntervalTree3() {
        List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
        intervals.add((new IntervalTree.IntervalData<String>(5,  20, "a")));
        intervals.add((new IntervalTree.IntervalData<String>(10, 30, "b")));
        intervals.add((new IntervalTree.IntervalData<String>(12, 15, "c")));
        intervals.add((new IntervalTree.IntervalData<String>(15, 20, "d")));
        intervals.add((new IntervalTree.IntervalData<String>(17, 19, "e")));
        intervals.add((new IntervalTree.IntervalData<String>(30, 40, "f")));

        IntervalTree<String> tree = new IntervalTree<String>(intervals);

        IntervalTree.IntervalData<String> query = tree.query(6,7); // Range query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("a")));
    }

    @Test
    public void testIntervalTree4() {
        List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
        intervals.add((new IntervalTree.IntervalData<String>(15, 20, "a")));
        intervals.add((new IntervalTree.IntervalData<String>(4,  25, "b")));
        intervals.add((new IntervalTree.IntervalData<String>(3,  30, "c")));

        IntervalTree<String> tree = new IntervalTree<String>(intervals);

        IntervalTree.IntervalData<String> query = tree.query(26,27); // Range query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("c")));
    }

    @Test
    public void testIntervalTree5() {
        List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
        intervals.add((new IntervalTree.IntervalData<String>(17, 19, "a")));
        intervals.add((new IntervalTree.IntervalData<String>(5,  11, "b")));
        intervals.add((new IntervalTree.IntervalData<String>(23, 23, "c")));
        intervals.add((new IntervalTree.IntervalData<String>(4,  8, "d")));
        intervals.add((new IntervalTree.IntervalData<String>(15, 18, "e")));
        intervals.add((new IntervalTree.IntervalData<String>(7,  10, "f")));

        IntervalTree<String> tree = new IntervalTree<String>(intervals);

        IntervalTree.IntervalData<String> query = tree.query(14,16); // Range query
        assertTrue("Interval Tree query error. returned=" + query, tree, collectionsEqual(query.getData(), Arrays.asList("e")));

        query = tree.query(12,14); // Range query
        assertTrue("Interval Tree query error. returned=" + query, tree, query==null);
    }

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final void assertTrue(String msg, IntervalTree<String> obj, boolean isTrue) {
        String toString = "";
        if (isTrue==false)
            toString = "\n"+obj.toString();
        Assert.assertTrue(msg+toString, isTrue);
    }
}
