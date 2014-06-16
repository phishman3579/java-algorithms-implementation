package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Iterator;

public class IteratorTest {

    /**
     * In object-oriented computer programming, an iterator is an object that enables a programmer to traverse a container.
     * 
     * http://en.wikipedia.org/wiki/Iterator
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     * 
     * @param iter Iterator to test.
     * @return True if iterator passes invariant tests.
     */
    public static <T extends Comparable<T>> boolean testIterator(Iterator<T> iter) {
        while (iter.hasNext()) {
            T item = iter.next();
            if (item==null) {
                System.err.println("Iterator failure.");
                return false;
            }
        }
        return true;
    }
}
