package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.IStack;

public class StackTest {

    public static <T extends Comparable<T>> boolean testStack(IStack<T> stack, String name,
                                                              T[] data, T _invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            boolean added = stack.push(item);
            if (!stack.validate() || (stack.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,stack);
                return false;
            }
            if (!added || item==null || !stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,stack);
                return false;
            }
        }

        boolean contains = stack.contains(_invalid);
        boolean removed = stack.remove(_invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,stack);
            return false;
        }

        int size = stack.size();
        for (int i = 0; i < size; i++) {
            T item = stack.pop();
            T correct = data[data.length-(i+1)];
            if ((item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                Utils.handleError(data,stack);
                return false;
            }
            if (!stack.validate() || (stack.size() != data.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,stack);
                return false;
            }
            if (stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,stack);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T item = data[i];
            boolean added = stack.push(item);
            if (!stack.validate() || (stack.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,stack);
                return false;
            }
            if (!added || item==null || !stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,stack);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = stack.pop();
            T correct = data[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                Utils.handleError(data,stack);
                return false;
            }
            if (!stack.validate() || (stack.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,stack);
                return false;
            }
            if (stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,stack);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            T item = data[i];
            boolean added = stack.push(item);
            if (!stack.validate() || (stack.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,stack);
                return false;
            }
            if (!added || item==null || !stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,stack);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            T item = stack.pop();
            T correct = data[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                Utils.handleError(data,stack);
                return false;
            }
            if (!stack.validate() || (stack.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,stack);
                return false;
            }
            if (stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,stack);
                return false;
            }
        }

        if (stack.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(data,stack);
            return false;
        }

        return true;
    }
}
