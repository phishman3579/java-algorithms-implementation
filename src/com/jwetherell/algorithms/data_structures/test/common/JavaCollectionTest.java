package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Collection;

import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class JavaCollectionTest {

    public static <T extends Comparable<T>> boolean testCollection(Collection<T> collection, Type type, String name,
                                                                   Integer[] unsorted, Integer[] sorted) {
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

        addAndRemoveInOrder(collection, type, name, unsorted);
        addInOrderRemoveInReverseOrder(collection, type, name, unsorted);
        addInReverseOrderAndRemoveInOrder(collection, type, name, unsorted);

        addAndRemoveInOrder(collection, type, name, sorted);
        addInOrderRemoveInReverseOrder(collection, type, name, sorted);
        addInReverseOrderAndRemoveInOrder(collection, type, name, sorted);

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

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> boolean addAndRemoveInOrder(Collection<T> collection, Type type, String name,
                                                                          Integer[] data) {
        // Add and remove in order (from index zero to length)
        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
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

        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            boolean removed = collection.remove(item);
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

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> boolean addInReverseOrderAndRemoveInOrder(Collection<T> collection, Type type, String name,
                                                                                       Integer[] data) {
        // Add in reverse (from index length-1 to zero) order and then remove in order (from index zero to length)
        for (int i = data.length - 1; i >= 0; i--) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            boolean added = collection.add(item);
            if (!added) {
                System.err.println(name+" addInReverseOrderAndRemoveInOrder add failed.");
                Utils.handleError(collection);
                return false;
            }
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
            boolean contains = collection.contains(item);
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
            boolean removed = collection.remove(item);
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

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> boolean addInOrderRemoveInReverseOrder(Collection<T> collection, Type type, String name,
                                                                                   Integer[] data) {
        // Add in order (from index zero to length) and then remove in reverse (from index length-1 to zero) order 
        for (int i = 0; i < data.length; i++) {
            T item = null;
            if (type==Type.Integer) {
                item = (T)data[i];
            } else if (type==Type.String) {
                item = (T)String.valueOf(data[i]);
            }
            boolean added = collection.add(item);
            if (!added) {
                System.err.println(name+" addInOrderRemoveInReverseOrder add failed.");
                Utils.handleError(collection);
                return false;
            }
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
            boolean contains = collection.contains(item);
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
            boolean removed = collection.remove(item);
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
