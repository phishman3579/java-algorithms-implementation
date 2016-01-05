package com.jwetherell.algorithms.data_structures.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.FenwickTree;
import com.jwetherell.algorithms.data_structures.FenwickTree.Data;

public class FenwickTreeTests {

    @Test
    public void testRangeSumFenwickTree() {
        List<FenwickTree.Data.RangeSumData<Integer>> segments = new ArrayList<FenwickTree.Data.RangeSumData<Integer>>();
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(0,  (Integer) 4));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(1,  (Integer) 2));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(2,  (Integer) 6));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(3,  (Integer) 3));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(4,  (Integer) 1));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(5,  (Integer) 5));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(6,  (Integer) 0));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(17, (Integer) 7));   

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumFenwickTree(segments);
    }

    private void testRangeSumFenwickTree(List<FenwickTree.Data.RangeSumData<Integer>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<Integer>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<Integer>>(segments);

        FenwickTree.Data.RangeSumData<Integer> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum==21);

        query = tree.query(0, 17);
        assertTrue("Segment tree query error. query=0->17 result="+query, tree, query.sum==28);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum==15);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum==7);

        query = tree.query(16); // stabbing
        assertTrue("Segment tree query error. query=16 result="+query, tree, query.sum==0);

        query = tree.query(17); // stabbing
        assertTrue("Segment tree query error. query=17 result="+query, tree, query.sum==7);
    }

    @Test
    public void testRangeSumFenwickTree2() {
        List<FenwickTree.Data.RangeSumData<Integer>> segments = new ArrayList<FenwickTree.Data.RangeSumData<Integer>>();
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(0,  (Integer) 2));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(1,  (Integer) 1));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(2,  (Integer) 1));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(3,  (Integer) 3));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(4,  (Integer) 2));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(5,  (Integer) 3));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(6,  (Integer) 4));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(7,  (Integer) 5));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(8,  (Integer) 6));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(9,  (Integer) 7));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(10, (Integer) 8));
        segments.add(new FenwickTree.Data.RangeSumData<Integer>(11, (Integer) 9));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testRangeSumFenwickTree2(segments);

        // Randomize it
        Collections.shuffle(segments);
        testRangeSumFenwickTree2(segments);

        // Try in order
        Collections.sort(segments);
        testRangeSumFenwickTree2(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testRangeSumFenwickTree2(segments);
    }

    private void testRangeSumFenwickTree2(List<FenwickTree.Data.RangeSumData<Integer>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<Integer>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<Integer>>(segments);

        FenwickTree.Data.RangeSumData<Integer> query = tree.query(0, 8);
        assertTrue("Segment tree query error. query=0->8 result="+query, tree, query.sum==27);

        query = tree.query(0, 11);
        assertTrue("Segment tree query error. query=0->11 result="+query, tree, query.sum==51);

        query = tree.query(2, 5);
        assertTrue("Segment tree query error. query=2->5 result="+query, tree, query.sum==9);

        query = tree.query(10, 17);
        assertTrue("Segment tree query error. query=10->17 result="+query, tree, query.sum==17);

        query = tree.query(2); // stabbing
        assertTrue("Segment tree query error. query=2 result="+query, tree, query.sum==1);

        query = tree.query(10); // stabbing
        assertTrue("Segment tree query error. query=10 result="+query, tree, query.sum==8);
    }

    private static final Comparator<FenwickTree.Data> REVERSE = new Comparator<FenwickTree.Data>() {
        @Override
        public int compare(Data arg0, Data arg1) {
            int r = arg0.compareTo(arg1);
            return r*-1;
        }       
    };

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final <D extends FenwickTree.Data> void assertTrue(String msg, FenwickTree<D> obj, boolean isTrue) {
        String toString = "";
        if (isTrue==false)
            toString = "\n"+obj.toString();
        Assert.assertTrue(msg+toString, isTrue);
    }
}
