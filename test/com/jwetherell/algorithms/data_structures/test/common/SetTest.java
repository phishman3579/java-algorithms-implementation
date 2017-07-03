package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.ISet;

public class SetTest {

    public static <T extends Comparable<T>> boolean testSet(ISet<T> set, String name,
                                                            T[] data, T _invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            boolean added = set.add(item);
            if (!set.validate() || (set.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,set);
                return false;
            }
            if (!added || !set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,set);
                return false;
            }
        }

        boolean contains = set.contains(_invalid);
        boolean removed = set.remove(_invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,set);
            return false;
        }

        int size = set.size();
        for (int i = 0; i < size; i++) {
            T item = data[i];
            removed = set.remove(item);
            if (!set.validate() || (set.size() != data.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,set);
                return false;
            }
            if (!removed || set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,set);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T item = data[i];
            boolean added = set.add(item);
            if (!set.validate() || (set.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,set);
                return false;
            }
            if (!added || !set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,set);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = data[i];
            removed = set.remove(item);
            if (!set.validate() || (set.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,set);
                return false;
            }
            if (!removed || set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,set);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            T item = data[i];
            boolean added = set.add(item);
            if (!set.validate() || (set.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,set);
                return false;
            }
            if (!added || !set.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,set);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            T item = data[i];
            removed = set.remove(item);
            if (!set.validate() || (set.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,set);
                return false;
            }
            if ((!removed || set.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,set);
                return false;
            }
        }

        if (set.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(data,set);
            return false;
        }
 
        return true;
    }
}
