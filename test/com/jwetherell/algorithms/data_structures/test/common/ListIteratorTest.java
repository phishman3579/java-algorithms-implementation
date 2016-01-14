package com.jwetherell.algorithms.data_structures.test.common;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListIteratorTest {

    /**
     * An iterator for lists that allows the programmer to traverse the list in either direction, 
     * modify the list during iteration, and obtain the iterator's current position in the list.
     * 
     * http://en.wikipedia.org/wiki/Iterator
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     * 
     * @param iter List iterator to test.
     * @param data Test data to test against.
     * @param size Size of the test data.
     * @return True if list iterator passes it's invariant tests.
     */
    public static <T extends Comparable<T>> boolean testListIterator(ListIterator<T> iter, Class<T> type, 
                                                                     Integer[] data, int size) {
        // Make sure you catch going prev at the start
        boolean exceptionThrown = false;
        try {
            iter.previous();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.err.println("ListIterator exception failure.");
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            iter.add(item);
        }
        while (iter.hasPrevious()) 
            iter.previous();

        int i = 0;
        while (iter.hasNext()) {
            T item = iter.next();
            int idx = iter.nextIndex();
            if (idx!=++i) {
                System.err.println("ListIterator index failure.");
                return false;
            }
            if (item==null) {
                System.err.println("ListIterator item is null.");
                return false;
            }
        }

        // We should be at the end of the collection, this should fail
        exceptionThrown = false;
        try {
            iter.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.err.println("ListIterator exception failure.");
            return false;
        }

        //This should be list.size
        iter.nextIndex();
        int listSize = iter.nextIndex();
        if (listSize!=size) {
            System.err.println("ListIterator ARRAY_SIZE failure.");
            return false;
        }

        i--;
        while (iter.hasPrevious()) {
            T item = iter.previous();
            int idx = iter.previousIndex();
            if (idx!=--i) {
                System.err.println("ListIterator index failure.");
                return false;
            }
            if (item==null) {
                System.err.println("ListIterator item is null.");
                return false;
            }
        }

        // We should be at the beginning of the collection, this should fail
        exceptionThrown = false;
        try {
            iter.previous();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.err.println("ListIterator exception failure.");
            return false;
        }

        // This should be negative one
        iter.previousIndex();
        int negOne = iter.previousIndex();
        if (negOne!=-1) {
            System.err.println("ListIterator negative_one failure.");
            return false;
        }

        // Remove all using iterator
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }

        return true;
    }
}
