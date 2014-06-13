package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.ITree;
import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class TreeTest {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> boolean testTree(ITree<T> tree, Type type, String name, 
                                                             Integer[] unsorted, Integer invalid) {
        for (int i = 0; i < unsorted.length; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            boolean added = tree.add(item);
            if (!tree.validate() || (tree.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(tree);
                return false;
            }
            if (!added || !tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(tree);
                return false;
            }
        }

        T invalidItem = null;
        if (type == Type.Integer) {
            invalidItem = (T)invalid;
        } else if (type == Type.String) {
            invalidItem = (T)String.valueOf(invalid);
        }
        boolean contains = tree.contains(invalidItem);
        T removed = tree.remove(invalidItem);
        if (contains || removed!=null) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(tree);
            return false;
        }

        int size = tree.size();
        for (int i = 0; i < size; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (!tree.validate()  || (tree.size() != unsorted.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(tree);
                return false;
            }
            if (removed==null || tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been removed.");
                Utils.handleError(tree);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            boolean added = tree.add(item);
            if (!tree.validate() || (tree.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(tree);
                return false;
            }
            if (!added || !tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(tree);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (!tree.validate() || (tree.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(tree);
                return false;
            }
            if (removed==null || tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(tree);
                return false;
            }
        }
        for (int i = quarter; i < unsorted.length; i++) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            boolean added = tree.add(item);
            if (!tree.validate() || (tree.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(tree);
                return false;
            }
            if (!added || !tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(tree);
                return false;
            }
        }
        for (int i = unsorted.length-1; i >= 0; i--) {
            Integer value = unsorted[i];
            T item = null;
            if (type == Type.Integer) {
                item = (T)value;
            } else if (type == Type.String) {
                item = (T)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (!tree.validate() || (tree.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(tree);
                return false;
            }
            if (removed==null || tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(tree);
                return false;
            }
        }

        if (tree.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(tree);
            return false;
        }

        return true;
    }
}
