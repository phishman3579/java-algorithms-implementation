package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.ISet;

public class SetTest {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> boolean testSet(ISet<T> set, String name,
                                                            Integer[] data, Integer invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = (T)data[i];
            boolean added = set.add(item);
            if (!set.validate() || (set.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(set);
                return false;
            }
            if (!added || !set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(set);
                return false;
            }
        }

        boolean contains = set.contains((T)invalid);
        boolean removed = set.remove((T)invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(set);
            return false;
        }

        int size = set.size();
        for (int i = 0; i < size; i++) {
            T item = (T)data[i];
            removed = set.remove(item);
            if (!set.validate() || (set.size() != data.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(set);
                return false;
            }
            if (!removed || set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(set);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T item = (T)data[i];
            boolean added = set.add(item);
            if (!set.validate() || (set.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(set);
                return false;
            }
            if (!added || !set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(set);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = (T)data[i];
            removed = set.remove(item);
            if (!set.validate() || (set.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(set);
                return false;
            }
            if (!removed || set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(set);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            T item = (T)data[i];
            boolean added = set.add(item);
            if (!set.validate() || (set.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(set);
                return false;
            }
            if (!added || !set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(set);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            T item = (T)data[i];
            removed = set.remove(item);
            if (!set.validate() || (set.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(set);
                return false;
            }
            if ((!removed || set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(set);
                return false;
            }
        }

        if (set.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(set);
            return false;
        }
 
        return true;
    }
}
