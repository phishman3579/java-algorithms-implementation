package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.IntervalTree;

public class IntervalTreeTests {
	
	private static boolean collectionsEqual(Collection<?> c1, Collection<?> c2) {
	    if (c1.size()!=c2.size()) return false;
		return c1.containsAll(c2) && c2.containsAll(c1);
	}

    @Test
    public void testIntervalTree() {
        {   // Interval tree
            final String RED        = "RED";
            final String ORANGE     = "ORANGE";
            final String GREEN      = "GREEN";
            final String DARK_GREEN = "DARK_GREEN";
            final String BLUE       = "BLUE";
            final String PURPLE     = "PURPLE";
            final String BLACK      = "BLACK";
            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>(2,  6,   RED)));
            intervals.add((new IntervalTree.IntervalData<String>(3,  5,   ORANGE)));
            intervals.add((new IntervalTree.IntervalData<String>(4,  11,  GREEN)));
            intervals.add((new IntervalTree.IntervalData<String>(5,  10,  DARK_GREEN)));
            intervals.add((new IntervalTree.IntervalData<String>(8,  12,  BLUE)));
            intervals.add((new IntervalTree.IntervalData<String>(9,  14,  PURPLE)));
            intervals.add((new IntervalTree.IntervalData<String>(13, 15,  BLACK)));
            IntervalTree<String> tree = new IntervalTree<String>(intervals);

            IntervalTree.IntervalData<String> query = tree.query(2);
            assertTrue("Interval Tree query error. query=2 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(RED)));

            query = tree.query(4); // Stabbing query
            assertTrue("Interval Tree query error. query=4 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(RED, ORANGE, GREEN)));

            query = tree.query(9); // Stabbing query
            assertTrue("Interval Tree query error. query=9 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(GREEN, DARK_GREEN, BLUE, PURPLE)));

            query = tree.query(1, 16); // Range query
            assertTrue("Interval Tree query error. query=1->16 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(RED, ORANGE, GREEN, DARK_GREEN, BLUE, PURPLE, BLACK)));

            query = tree.query(7, 14); // Range query
            assertTrue("Interval Tree query error. query=7->14 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(GREEN, DARK_GREEN, BLUE, PURPLE, BLACK)));

            query = tree.query(14, 15); // Range query
            assertTrue("Interval Tree query error. query=14->15 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(PURPLE, BLACK)));
        }

        {   // Lifespan Interval tree
            final String stravinsky = "Stravinsky";
            final String schoenberg = "Schoenberg";
            final String grieg      = "Grieg";
            final String schubert   = "Schubert";
            final String mozart     = "Mozart";
            final String schuetz    = "Schuetz";
            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>(1888, 1971, stravinsky)));
            intervals.add((new IntervalTree.IntervalData<String>(1874, 1951, schoenberg)));
            intervals.add((new IntervalTree.IntervalData<String>(1843, 1907, grieg)));
            intervals.add((new IntervalTree.IntervalData<String>(1779, 1828, schubert)));
            intervals.add((new IntervalTree.IntervalData<String>(1756, 1791, mozart)));
            intervals.add((new IntervalTree.IntervalData<String>(1585, 1672, schuetz)));
            IntervalTree<String> tree = new IntervalTree<String>(intervals);

            IntervalTree.IntervalData<String> query = tree.query(1890); // Stabbing query
            assertTrue("Interval Tree query error. query=1890 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg, grieg)));

            query = tree.query(1909); // Stabbing query
            assertTrue("Interval Tree query error. query=1909 returned=" + query , collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg)));

            query = tree.query(1792, 1903); // Range query
            assertTrue("Interval Tree query error. query=1792->1903 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(stravinsky, schoenberg, grieg, schubert)));

            query = tree.query(1776, 1799); // Range query
            assertTrue("Interval Tree query error. query=1776->1799 returned=" + query, collectionsEqual(query.getData(), Arrays.asList(schubert, mozart)));
        }
    }
}
