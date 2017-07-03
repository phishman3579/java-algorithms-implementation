package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Iterator;

public class IteratorTest {

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
