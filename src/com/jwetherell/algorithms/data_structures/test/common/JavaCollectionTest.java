package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Collection;

import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class JavaCollectionTest {

    /**
     * In computer science, a collection or container is a grouping of some variable number of data items 
     * (possibly zero) that have some shared significance to the problem being solved and need to be operated 
     * upon together in some controlled fashion.
     * 
     * http://en.wikipedia.org/wiki/Collection_(abstract_data_type)
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     * 
     * @param collection Collection to test.
     * @param type Type of data in the collection (Either String or Integer).
     * @param name name Name used in debug.
     * @param unsorted Unsorted test data.
     * @param sorted Sorted test data.
     * @return True if the collection passes it's invariants tests.
     */
    public static  <C extends Comparable<C>, T extends Comparable<T>> boolean testCollection(Collection<C> collection, Type type, String name,
                                                                                             T[] unsorted, T[] sorted, T invalid) {
        // Make sure the collection is empty
        if (!collection.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            Utils.handleError(collection);
            return false;
        }

        if (collection.size()!=0) {
            System.err.println(name+" initial size() failed.");
            Utils.handleError(collection);
            return false;
        }

        addAndRemoveInOrder(collection, type, name, unsorted, invalid);
        addInOrderRemoveInReverseOrder(collection, type, name, unsorted, invalid);
        addInReverseOrderAndRemoveInOrder(collection, type, name, unsorted, invalid);

        addAndRemoveInOrder(collection, type, name, sorted, invalid);
        addInOrderRemoveInReverseOrder(collection, type, name, sorted, invalid);
        addInReverseOrderAndRemoveInOrder(collection, type, name, sorted, invalid);

        // Make sure the collection is empty
        if (!collection.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            Utils.handleError(collection);
            return false;
        }

        if (collection.size()!=0) {
            System.err.println(name+" initial size() failed.");
            Utils.handleError(collection);
            return false;
        }

        return true;
    }

    @SuppressWarnings({ "unchecked", "cast" })
    private static <C extends Comparable<C>, T extends Comparable<T>> boolean addAndRemoveInOrder(Collection<C> collection, Type type, String name,
                                                                                                  T[] data, T _invalid) {
        T invalid = _invalid;
        if (type == Type.String)
            invalid = (T)String.valueOf(_invalid);

        // Add and remove in order (from index zero to length)
        for (int i = 0; i < data.length; i++) {
            C item = null;
            if (type==Type.Integer) {
                item = (C)data[i];
            } else if (type==Type.String) {
                item = (C)String.valueOf(data[i]);
            }
            boolean added = collection.add(item);
            if (!added) {
                System.err.println(name+" addAndRemoveInOrder add failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            boolean contains = collection.contains(item);
            if (!contains) {
                System.err.println(name+" addAndRemoveInOrder contains failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        boolean contains = collection.contains((T)invalid);
        boolean removed = collection.remove((T)invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(collection);
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            removed = collection.remove(item);
            if (!removed) {
                System.err.println(name+" addAndRemoveInOrder remove failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        if (!collection.isEmpty()) {
            System.err.println(name+" addAndRemoveInOrder isEmpty() failed.");
            Utils.handleError(collection);
            return false;
        }
        if (collection.size()!=0) {
            System.err.println(name+" addAndRemoveInOrder size() failed.");
            Utils.handleError(collection);
            return false;
        }

        if (collection instanceof java.util.List && 
            (!ListIteratorTest.testListIterator(((java.util.List<T>)collection).listIterator(), 
                                                data, 
                                                data.length))
        ) {
            System.err.println(name+" addAndRemoveInOrder list iterator failed.");
            Utils.handleError(collection);
            return false;
        }

        return true;
    }

    @SuppressWarnings({ "unchecked", "cast" })
    private static <C extends Comparable<C>, T extends Comparable<T>> boolean addInReverseOrderAndRemoveInOrder(Collection<C> collection, Type type, String name,
                                                                                                                T[] data, T _invalid) {
        T invalid = _invalid;
        if (type == Type.String)
            invalid = (T)String.valueOf(_invalid);

        // Add in reverse (from index length-1 to zero) order and then remove in order (from index zero to length)
        for (int i = data.length - 1; i >= 0; i--) {
            C item = null;
            if (type==Type.Integer) {
                item = (C)data[i];
            } else if (type==Type.String) {
                item = (C)String.valueOf(data[i]);
            }
            boolean added = collection.add(item);
            if (!added) {
                System.err.println(name+" addInReverseOrderAndRemoveInOrder add failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        boolean contains = collection.contains((T)invalid);
        boolean removed = collection.remove((T)invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(collection);
            return false;
        }

        if (!IteratorTest.testIterator(collection.iterator())) {
            System.err.println(name+" addInReverseOrderAndRemoveInOrder iterator failed.");
            Utils.handleError(collection);
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            contains = collection.contains(item);
            if (!contains) {
                System.err.println(name+" addInReverseOrderAndRemoveInOrder contains failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            removed = collection.remove(item);
            if (!removed) {
                System.err.println(name+" addInReverseOrderAndRemoveInOrder remove failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        if (!collection.isEmpty()) {
            System.err.println(name+" addInReverseOrderAndRemoveInOrder isEmpty() failed.");
            Utils.handleError(collection);
            return false;
        }

        if (collection.size()!=0) {
            System.err.println(name+" addInReverseOrderAndRemoveInOrder size() failed.");
            Utils.handleError(collection);
            return false;
        }

        return true;
    }

    @SuppressWarnings({ "unchecked", "cast" })
    public static <C extends Comparable<C>, T extends Comparable<T>> boolean addInOrderRemoveInReverseOrder(Collection<C> collection, Type type, String name,
                                                                                                            T[] data, T _invalid) {
        T invalid = _invalid;
        if (type == Type.String)
            invalid = (T)String.valueOf(_invalid);

        // Add in order (from index zero to length) and then remove in reverse (from index length-1 to zero) order 
        for (int i = 0; i < data.length; i++) {
            C item = null;
            if (type==Type.Integer) {
                item = (C)data[i];
            } else if (type==Type.String) {
                item = (C)String.valueOf(data[i]);
            }
            boolean added = collection.add(item);
            if (!added) {
                System.err.println(name+" addInOrderRemoveInReverseOrder add failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        boolean contains = collection.contains((T)invalid);
        boolean removed = collection.remove((T)invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(collection);
            return false;
        }

        if (!IteratorTest.testIterator(collection.iterator())) {
            System.err.println(name+" addInOrderRemoveInReverseOrder iterator failed.");
            Utils.handleError(collection);
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            contains = collection.contains(item);
            if (!contains) {
                System.err.println(name+" addInOrderRemoveInReverseOrder contains failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        for (int i = data.length - 1; i >= 0; i--) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            removed = collection.remove(item);
            if (!removed) {
                System.err.println(name+" addInOrderRemoveInReverseOrder remove failed.");
                Utils.handleError(collection);
                return false;
            }
        }

        if (!collection.isEmpty()) {
            System.err.println(name+" addInOrderRemoveInReverseOrder isEmpty() failed.");
            Utils.handleError(collection);
            return false;
        }
        if (collection.size()!=0) {
            System.err.println(name+" addInOrderRemoveInReverseOrder size() failed.");
            Utils.handleError(collection);
            return false;
        }

        if (collection instanceof java.util.List && 
           (!ListIteratorTest.testListIterator(((java.util.List<T>)collection).listIterator(),
                                               data,
                                               data.length))
        ) {
            System.err.println(name+" addInOrderRemoveInReverseOrder list iterator failed.");
            Utils.handleError(collection);
            return false;
        }

        return true;
    }
}
