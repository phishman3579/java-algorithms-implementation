package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.IStack;

public class StackTest {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> boolean testStack(IStack<T> stack, String name,
                                                              Integer[] data, Integer invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = (T)data[i];
            boolean added = stack.push(item);
            if (!stack.validate() || (stack.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(stack);
                return false;
            }
            if (!added || item==null || !stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(stack);
                return false;
            }
        }

        boolean contains = stack.contains((T)invalid);
        boolean removed = stack.remove((T)invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(stack);
            return false;
        }

        int size = stack.size();
        for (int i = 0; i < size; i++) {
            T item = stack.pop();
            T correct = (T)data[data.length-(i+1)];
            if ((item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                Utils.handleError(stack);
                return false;
            }
            if (!stack.validate() || (stack.size() != data.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(stack);
                return false;
            }
            if (stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(stack);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T item = (T)data[i];
            boolean added = stack.push(item);
            if (!stack.validate() || (stack.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(stack);
                return false;
            }
            if (!added || item==null || !stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(stack);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = stack.pop();
            T correct = (T)data[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                Utils.handleError(stack);
                return false;
            }
            if (!stack.validate() || (stack.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(stack);
                return false;
            }
            if (stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(stack);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            T item = (T)data[i];
            boolean added = stack.push(item);
            if (!stack.validate() || (stack.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(stack);
                return false;
            }
            if (!added || item==null || !stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(stack);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            T item = stack.pop();
            T correct = (T)data[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match LIFO item.");
                Utils.handleError(stack);
                return false;
            }
            if (!stack.validate() || (stack.size() != i)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(stack);
                return false;
            }
            if (stack.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(stack);
                return false;
            }
        }

        if (stack.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(stack);
            return false;
        }

        return true;
    }
}
