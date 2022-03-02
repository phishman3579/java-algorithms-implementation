package com.jwetherell.algorithms.data_structures.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

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

    // Requirement: 
    // When FenwickTree consists of data with BigDecimal numbers it should be able to sum them up correctly
   @Test
    public void testBigDecimalFenwickTree() {
        List<FenwickTree.Data.RangeSumData<BigDecimal>> segments = new ArrayList<FenwickTree.Data.RangeSumData<BigDecimal>>();
        segments.add(new FenwickTree.Data.RangeSumData<BigDecimal>(0,  (BigDecimal) new BigDecimal(1.1)));
        segments.add(new FenwickTree.Data.RangeSumData<BigDecimal>(1,  (BigDecimal) new BigDecimal(2.1)));
        segments.add(new FenwickTree.Data.RangeSumData<BigDecimal>(2,  (BigDecimal) new BigDecimal(6.1)));
        segments.add(new FenwickTree.Data.RangeSumData<BigDecimal>(3,  (BigDecimal) new BigDecimal(7.1)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testBigDecimalFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testBigDecimalFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testBigDecimalFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testBigDecimalFenwickTree(segments);
    }

     private void testBigDecimalFenwickTree(List<FenwickTree.Data.RangeSumData<BigDecimal>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<BigDecimal>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<BigDecimal>>(segments);

        FenwickTree.Data.RangeSumData<BigDecimal> query = tree.query(0, 3);
 
        query = tree.query(2);
        assertTrue("Segment tree query error. query=2 result="+query, tree, query.sum.compareTo(new BigDecimal(6.1)) == 0);

        query = tree.query(0, 1); // stabbing
        assertTrue("Segment tree query error. query=0->1 result="+ query, tree, query.sum.compareTo(new BigDecimal(1.1).add(new BigDecimal(2.1))) == 0);
    }

    // Requirement: 
    // When FenwickTree consists of data with BigInteger numbers it should be able to sum them up correctly
    @Test
    public void testBigIntegerFenwickTree() {
        List<FenwickTree.Data.RangeSumData<BigInteger>> segments = new ArrayList<FenwickTree.Data.RangeSumData<BigInteger>>();
        segments.add(new FenwickTree.Data.RangeSumData<BigInteger>(0,  (BigInteger) BigInteger.valueOf(1)));
        segments.add(new FenwickTree.Data.RangeSumData<BigInteger>(1,  (BigInteger) BigInteger.valueOf(2)));
        segments.add(new FenwickTree.Data.RangeSumData<BigInteger>(2,  (BigInteger) BigInteger.valueOf(6)));
        segments.add(new FenwickTree.Data.RangeSumData<BigInteger>(3,  (BigInteger) BigInteger.valueOf(7)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testBigIntegerFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testBigIntegerFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testBigIntegerFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testBigIntegerFenwickTree(segments);
    }

     private void testBigIntegerFenwickTree(List<FenwickTree.Data.RangeSumData<BigInteger>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<BigInteger>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<BigInteger>>(segments);

        FenwickTree.Data.RangeSumData<BigInteger> query = tree.query(0, 3);
 
        query = tree.query(2);
        assertTrue("Segment tree query error. query=2 result="+query, tree, query.sum.compareTo(BigInteger.valueOf(6)) == 0);

        query = tree.query(0, 1); // stabbing
        assertTrue("Segment tree query error. query=0->1 result="+ query, tree, query.sum.compareTo(BigInteger.valueOf(1).add(BigInteger.valueOf(2))) == 0);
    }

    // Requirement: 
    // When FenwickTree consists of data with Long numbers it should be able to sum them up correctly
    @Test
    public void testLongFenwickTree() {
        List<FenwickTree.Data.RangeSumData<Long>> segments = new ArrayList<FenwickTree.Data.RangeSumData<Long>>();
        segments.add(new FenwickTree.Data.RangeSumData<Long>(0,  (Long) Long.valueOf(1)));
        segments.add(new FenwickTree.Data.RangeSumData<Long>(1,  (Long) Long.valueOf(2)));
        segments.add(new FenwickTree.Data.RangeSumData<Long>(2,  (Long) Long.valueOf(6)));
        segments.add(new FenwickTree.Data.RangeSumData<Long>(3,  (Long) Long.valueOf(7)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testLongFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testLongFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testLongFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testLongFenwickTree(segments);
    }

     private void testLongFenwickTree(List<FenwickTree.Data.RangeSumData<Long>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<Long>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<Long>>(segments);

        FenwickTree.Data.RangeSumData<Long> query = tree.query(0, 3);
 
        query = tree.query(2);
        assertTrue("Segment tree query error. query=2 result="+query, tree, query.sum.compareTo(Long.valueOf(6)) == 0);

        query = tree.query(0, 1); // stabbing
        assertTrue("Segment tree query error. query=0->1 result="+ query, tree, query.sum.compareTo(Long.sum(Long.valueOf(1), (Long.valueOf(2)))) == 0);
    }

    // Requirement: 
    // When FenwickTree consists of data with Double numbers it should be able to sum them up correctly
    @Test
    public void testDoubleFenwickTree() {
        List<FenwickTree.Data.RangeSumData<Double>> segments = new ArrayList<FenwickTree.Data.RangeSumData<Double>>();
        segments.add(new FenwickTree.Data.RangeSumData<Double>(0,  (Double) new Double(1.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Double>(1,  (Double) new Double(2.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Double>(2,  (Double) new Double(6.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Double>(3,  (Double) new Double(7.11)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testDoubleFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testDoubleFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testDoubleFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testDoubleFenwickTree(segments);
    }

     private void testDoubleFenwickTree(List<FenwickTree.Data.RangeSumData<Double>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<Double>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<Double>>(segments);

        FenwickTree.Data.RangeSumData<Double> query = tree.query(0, 3);
 
        query = tree.query(2);
        assertTrue("Segment tree query error. query=2 result="+query, tree, query.sum.compareTo(new Double(6.11)) == 0);

        query = tree.query(0, 1); // stabbing
        assertTrue("Segment tree query error. query=0->1 result="+ query, tree, query.sum.compareTo(Double.sum(new Double(1.11),(new Double(2.11)))) == 0);
    }

    // Requirement: 
    // When FenwickTree consists of data with Float numbers it should be able to sum them up correctly
    @Test
    public void testFloatFenwickTree() {
        List<FenwickTree.Data.RangeSumData<Float>> segments = new ArrayList<FenwickTree.Data.RangeSumData<Float>>();
        segments.add(new FenwickTree.Data.RangeSumData<Float>(0,  (Float) new Float(1.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Float>(1,  (Float) new Float(2.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Float>(2,  (Float) new Float(6.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Float>(3,  (Float) new Float(7.11)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testFloatFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testFloatFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testFloatFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testFloatFenwickTree(segments);
    }

     private void testFloatFenwickTree(List<FenwickTree.Data.RangeSumData<Float>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<Float>> tree = new FenwickTree<FenwickTree.Data.RangeSumData<Float>>(segments);

        FenwickTree.Data.RangeSumData<Float> query = tree.query(0, 3);
 
        query = tree.query(2);
        assertTrue("Segment tree query error. query=2 result="+query, tree, query.sum.compareTo(new Float(6.11)) == 0);

        query = tree.query(0, 1); // stabbing
        assertTrue("Segment tree query error. query=0->1 result="+ query, tree, query.sum.compareTo(Float.sum(new Float(1.11),(new Float(2.11)))) == 0);
    }


    // Requirement: 
    // Added branch coverage for when an element is null when using the functions combined and separate for every case 
    // except the case in separate where "this.sum == null && data.sum != null" since it will not be possible to test as 
    // a separated number supposed to be null (this.sum) from a sum which is not null (data.sum) is equal to 0.0 and not null 
    // when separated

    @Test
    public void testNullFenwickTree() {
        List<FenwickTree.Data.RangeSumData<Float>> segments = new ArrayList<FenwickTree.Data.RangeSumData<Float>>();
        segments.add(new FenwickTree.Data.RangeSumData<Float>(0,  null));
        segments.add(new FenwickTree.Data.RangeSumData<Float>(1,  null));
        segments.add(new FenwickTree.Data.RangeSumData<Float>(2,  new Float(6.11)));
        segments.add(new FenwickTree.Data.RangeSumData<Float>(3,  null));
        //segments.add(new FenwickTree.Data.RangeSumData<Float>(2,  (Float) new Float(6.11)));
        //segments.add(new FenwickTree.Data.RangeSumData<Float>(3,  (Float) new Float(7.11)));

        // No matter which order the data is given, all tests should pass

        // Initial order.
        testNullFenwickTree(segments);

        // Randomize it
        Collections.shuffle(segments);
        testNullFenwickTree(segments);

        // Try in order
        Collections.sort(segments);
        testNullFenwickTree(segments);

        // Try reverse order
        Collections.sort(segments,REVERSE);
        testNullFenwickTree(segments);
    }

     private void testNullFenwickTree(List<FenwickTree.Data.RangeSumData<Float>> segments) {   // Range Sum Segment tree
        FenwickTree<FenwickTree.Data.RangeSumData<Float>> tree1 = new FenwickTree<FenwickTree.Data.RangeSumData<Float>>(segments);

        FenwickTree.Data.RangeSumData<Float> query = tree1.query(0, 3);

        query = tree1.query(1);
        assertTrue("Segment tree query error. query=1 result="+query, tree1, query.sum == null);

        query = tree1.query(2);
        assertTrue("Segment tree query error. query=2 result="+query, tree1, query.sum.compareTo(new Float(6.11)) == 0);

        query = tree1.query(3);
        assertTrue("Segment tree query error. query=3 result="+query, tree1, query.sum == 0.0);

        query = tree1.query(0, 1); // stabbing
        assertTrue("Segment tree query error. query=0->1 result="+ query, tree1, query.sum == null);

        query = tree1.query(0, 2); // stabbing
        assertTrue("Segment tree query error. query=0->2 result="+ query, tree1, query.sum.compareTo(new Float(6.11)) == 0);

        query = tree1.query(0, 3); // stabbing
        assertTrue("Segment tree query error. query=0->3 result="+ query, tree1, query.sum.compareTo(new Float(6.11)) == 0);
        
        query = tree1.query(2, 3); // stabbing
        assertTrue("Segment tree query error. query=2->3 result="+ query, tree1, query.sum.compareTo(new Float(6.11)) == 0);


    }




}
