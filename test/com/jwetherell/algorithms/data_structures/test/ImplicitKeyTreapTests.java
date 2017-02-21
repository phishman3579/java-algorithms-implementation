package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.ImplicitKeyTreap;
import com.jwetherell.algorithms.data_structures.ImplicitKeyTreap.Node;
import com.jwetherell.algorithms.data_structures.ImplicitKeyTreap.Pair;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.ListTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class ImplicitKeyTreapTests {

    @Test
    public void testCollection() {
        TestData data = Utils.generateTestData(100);

        String name = "ImplicitKeyTreap";
        ImplicitKeyTreap<Integer> list = new ImplicitKeyTreap<Integer>();
        assertTrue(ListTest.testList(list, name, data.unsorted, data.invalid));

        Collection<Integer> tCollectionMin = list.toCollection();
        assertTrue(JavaCollectionTest.testCollection(tCollectionMin, Integer.class, name,
                                                     data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void implicitKeyInsertTests() {
        final int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        final MyTreap<Integer> treap = new MyTreap<Integer>();
        for (int i=0; i<data.length; i++) {
            Integer d = data[i];
            treap.add(i, d);
        }
        String inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("1 2 3 4 5 6 7 8 "));
    }

    @Test
    public void implicitKeyRemoveTests() {
        final int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        final MyTreap<Integer> treap = new MyTreap<Integer>();
        for (int i=0; i<data.length; i++) {
            Integer d = data[i];
            treap.add(i, d);
        }

        // remove value @ index 3 (value==4)
        treap.removeAtIndex(3);
        String inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("1 2 3 5 6 7 8 "));

        // remove value @ index 4 (value==6)
        treap.removeAtIndex(4);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("1 2 3 5 7 8 "));

        // remove value @ index 0 (value==1)
        treap.removeAtIndex(0);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("2 3 5 7 8 "));

        // remove value @ index 1 (value==3)
        treap.removeAtIndex(1);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("2 5 7 8 "));

        // remove value @ index 2 (value==7)
        treap.removeAtIndex(2);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("2 5 8 "));

        // remove value @ index 2 (value==8)
        treap.removeAtIndex(2);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("2 5 "));

        // remove value @ index 0 (value==2)
        treap.removeAtIndex(0);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("5 "));

        // remove value @ index 0 (value==5)
        treap.removeAtIndex(0);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals(""));
    }

    @Test
    public void implicitKeySplitMergeTests() {
        final int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        final MyTreap<Integer> treap = new MyTreap<Integer>();
        for (int i=0; i<data.length; i++) {
            Integer d = data[i];
            treap.add(i, d);
        }
        String inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("1 2 3 4 5 6 7 8 "));

        moveToFront(treap, 1, 4);
        inOrder = toString(Integer.class, treap.inOrder());
        // move '2 3 4' to the front
        Assert.assertTrue(inOrder.equals("2 3 4 1 5 6 7 8 "));

        moveToBack(treap, 2, 5);
        inOrder = toString(Integer.class, treap.inOrder());
        // move '4 1 5' to the back
        Assert.assertTrue(inOrder.equals("2 3 6 7 8 4 1 5 "));

        moveToFront(treap, 3, 7);
        inOrder = toString(Integer.class, treap.inOrder());
        // move '7 8 4 1' to the front
        Assert.assertTrue(inOrder.equals("7 8 4 1 2 3 6 5 "));

        moveToBack(treap, 0, 4);
        inOrder = toString(Integer.class, treap.inOrder());
        // move '2 3 6 5' to back
        Assert.assertTrue(inOrder.equals("2 3 6 5 7 8 4 1 "));
    }

    @Test
    public void implicitKeyTests() {
        final int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        final MyTreap<Integer> treap = new MyTreap<Integer>();
        for (int i=0; i<data.length; i++) {
            Integer d = data[i];
            treap.add(i, d);
        }

        // remove value @ index 3 (value==4)
        treap.removeAtIndex(3);
        String inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("1 2 3 5 6 7 8 "));

        // insert value @ index 0 (head)
        treap.add(0, 9);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("9 1 2 3 5 6 7 8 "));

        // insert value @ index 8 (tail)
        treap.add(8, 10);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("9 1 2 3 5 6 7 8 10 "));

        // insert via value (always inserts at end)
        treap.add((Integer)11);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("9 1 2 3 5 6 7 8 10 11 "));

        // remove via value
        treap.remove((Integer)5);
        inOrder = toString(Integer.class, treap.inOrder());
        Assert.assertTrue(inOrder.equals("9 1 2 3 6 7 8 10 11 "));

        moveToFront(treap, 5, 9);
        inOrder = toString(Integer.class, treap.inOrder());
        // move '7 8 10 11' to the front
        Assert.assertTrue(inOrder.equals("7 8 10 11 9 1 2 3 6 "));

        moveToBack(treap, 2, 6);
        inOrder = toString(Integer.class, treap.inOrder());
        // move '10 11 9 1' to back
        Assert.assertTrue(inOrder.equals("7 8 2 3 6 10 11 9 1 "));
    }

    private static <T extends Comparable<T>> void moveToFront(MyTreap<T> t, int begin, int end) {
        Pair<T> p = t.split(end);
        Node<T> e = p.getGreater();
        Node<T> tmp = p.getLesser();

        p = ImplicitKeyTreap.split(tmp, begin);
        Node<T> m = p.getLesser();
        Node<T> b = p.getGreater();

        Node<T> n = ImplicitKeyTreap.merge(b,m);
        n = ImplicitKeyTreap.merge(n,e);

        // update treap
        t.setRoot(n);
    }

    private static <T extends Comparable<T>> void moveToBack(MyTreap<T> t, int begin, int end) {
        Pair<T> p = t.split(end);
        Node<T> m = p.getGreater();
        Node<T> tmp = p.getLesser();

        p = ImplicitKeyTreap.split(tmp, begin);
        Node<T> b = p.getLesser();
        Node<T> e = p.getGreater();

        Node<T> n = ImplicitKeyTreap.merge(b,m);
        n = ImplicitKeyTreap.merge(n,e);

        // update treap
        t.setRoot(n);
    }

    @SuppressWarnings("unused")
    private static <T> String toString(Class<T> type, Object[] array) {
        final StringBuilder builder = new StringBuilder();
        for (Object a : array)
            builder.append(a.toString()).append(" ");
        return builder.toString();
    }

    // Opening up the access to the root node
    private class MyTreap<T extends Comparable<T>> extends ImplicitKeyTreap<T> {

        public void setRoot(Node<T> root) {
            this.root = root;
        }
    }
}
