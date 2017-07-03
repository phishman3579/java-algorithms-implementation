package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.IList;

public class ListTest {

    public static <T extends Comparable<T>> boolean testList(IList<T> list, String name,
                                                             T[] data, T _invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            boolean added = list.add(item);
            if ((!list.validate() || (list.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,list);
                return false;
            }
            if ((!added || !list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,list);
                return false;
            }
        }

        boolean contains = list.contains(_invalid);
        boolean removed = list.remove(_invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,list);
            return false;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            T item = data[i];
            removed = list.remove(item);
            if ((!list.validate() || (list.size() != data.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,list);
                return false;
            }
            if ((!removed || list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,list);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T item = data[i];
            boolean added = list.add(item);
            if ((!list.validate() || (list.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,list);
                return false;
            }
            if ((!added || !list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,list);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = data[i];
            removed = list.remove(item);
            if ((!list.validate() || (list.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,list);
                return false;
            }
            if ((!removed || list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,list);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            T item = data[i];
            boolean added = list.add(item);
            if ((!list.validate() || (list.size() != i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,list);
                return false;
            }
            if ((!added || !list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,list);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            T item = data[i];
            removed = list.remove(item);
            if ((!list.validate() || (list.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,list);
                return false;
            }
            if ((!removed || list.contains(item))) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,list);
                return false;
            }
        }

        if ((list.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(data,list);
            return false;
        }
 
        return true;
    }
}
