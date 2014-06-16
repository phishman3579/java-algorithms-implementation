package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.ITree;
import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class TreeTest {

    /**
     * In computer science, a tree is a widely used abstract data type (ADT) or data structure implementing this ADT 
     * that simulates a hierarchical tree structure, with a root value and subtrees of children, represented as a set 
     * of linked nodes.
     * 
     * http://en.wikipedia.org/wiki/Tree_(data_structure)
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     * 
     * @param tree Tree to test.
     * @param type Type of data in the heap (Either String or Integer).
     * @param name Name used in debug.
     * @param data Test data.
     * @param invalid Invalid data which isn't in the data-structure.
     * @return True if the tree passes it's invariants tests.
     */
    @SuppressWarnings("unchecked")
    public static <C extends Comparable<C>, T extends Comparable<T>> boolean testTree(ITree<C> tree, Type type, String name, 
                                                                                      T[] data, T invalid) {
        for (int i = 0; i < data.length; i++) {
            T value = data[i];
            C item = null;
            if (type == Type.Integer) {
                item = (C)value;
            } else if (type == Type.String) {
                item = (C)String.valueOf(value);
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

        C invalidItem = null;
        if (type == Type.Integer) {
            invalidItem = (C)invalid;
        } else if (type == Type.String) {
            invalidItem = (C)String.valueOf(invalid);
        }
        boolean contains = tree.contains(invalidItem);
        C removed = tree.remove(invalidItem);
        if (contains || removed!=null) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(tree);
            return false;
        }

        int size = tree.size();
        for (int i = 0; i < size; i++) {
            T value = data[i];
            C item = null;
            if (type == Type.Integer) {
                item = (C)value;
            } else if (type == Type.String) {
                item = (C)String.valueOf(value);
            }
            removed = tree.remove(item);
            if (!tree.validate()  || (tree.size() != data.length-(i+1))) {
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
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T value = data[i];
            C item = null;
            if (type == Type.Integer) {
                item = (C)value;
            } else if (type == Type.String) {
                item = (C)String.valueOf(value);
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
            T value = data[i];
            C item = null;
            if (type == Type.Integer) {
                item = (C)value;
            } else if (type == Type.String) {
                item = (C)String.valueOf(value);
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
        for (int i = quarter; i < data.length; i++) {
            T value = data[i];
            C item = null;
            if (type == Type.Integer) {
                item = (C)value;
            } else if (type == Type.String) {
                item = (C)String.valueOf(value);
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
        for (int i = data.length-1; i >= 0; i--) {
            T value = data[i];
            C item = null;
            if (type == Type.Integer) {
                item = (C)value;
            } else if (type == Type.String) {
                item = (C)String.valueOf(value);
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
