package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.ITree;

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
    public static <T extends Comparable<T>> boolean testTree(ITree<T> tree, Class<T> type, String name, 
                                                             Integer[] data, Integer _invalid) {
        for (int i = 0; i < data.length; i++) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            boolean added = tree.add(item);
            if (!tree.validate() || (tree.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,tree);
                return false;
            }
            if (!added || !tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,tree);
                return false;
            }
        }

        T invalidItem = Utils.parseT(_invalid, type);
        boolean contains = tree.contains(invalidItem);
        T removed = tree.remove(invalidItem);
        if (contains || removed!=null) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,tree);
            return false;
        }

        int size = tree.size();
        for (int i = 0; i < size; i++) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            removed = tree.remove(item);
            if (!tree.validate()  || (tree.size() != data.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,tree);
                return false;
            }
            if (removed==null || tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been removed.");
                Utils.handleError(data,tree);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            boolean added = tree.add(item);
            if (!tree.validate() || (tree.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,tree);
                return false;
            }
            if (!added || !tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,tree);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            removed = tree.remove(item);
            if (!tree.validate() || (tree.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,tree);
                return false;
            }
            if (removed==null || tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,tree);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            boolean added = tree.add(item);
            if (!tree.validate() || (tree.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,tree);
                return false;
            }
            if (!added || !tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,tree);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            Integer value = data[i];
            T item = Utils.parseT(value, type);
            removed = tree.remove(item);
            if (!tree.validate() || (tree.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,tree);
                return false;
            }
            if (removed==null || tree.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,tree);
                return false;
            }
        }

        if (tree.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(data,tree);
            return false;
        }

        return true;
    }
}
