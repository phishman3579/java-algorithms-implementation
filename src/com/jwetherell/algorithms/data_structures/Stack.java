package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;


/**
 * Stack. a stack is a last in, first out (LIFO) abstract data type and linear
 * data structure. A stack can have any abstract data type as an element, but is
 * characterized by two fundamental operations, called push and pop. The push
 * operation adds a new item to the top of the stack, or initializes the stack
 * if it is empty. If the stack is full and does not contain enough space to
 * accept the given item, the stack is then considered to be in an overflow
 * state. The pop operation removes an item from the top of the stack.
 * 
 * http://en.wikipedia.org/wiki/Stack_(abstract_data_type)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class Stack<T> {

    public enum StackType {
        LinkedStack, ArrayStack
    };

    /**
     * Push value on top of stack
     * 
     * @param value
     *            to push on the stack.
     */
    public abstract void push(T value);

    /**
     * Pop the value from the top of stack.
     * 
     * @return value popped off the top of the stack.
     */
    public abstract T pop();

    /**
     * Does the stack contains the value. Warning, this is a O(n) operation.
     * 
     * @param value
     *            to locate in the stack.
     * @return True if value is in the stack.
     */
    public abstract boolean contains(T value);

    /**
     * Number of values in the stack.
     * 
     * @return number of values in the stack.
     */
    public abstract int size();

    public static <T> Stack<T> createStack(StackType type) {
        switch (type) {
            case ArrayStack:
                return new ArrayStack<T>();
            default:
                return new LinkedStack<T>();
        }
    }

    /**
     * This stack implementation is backed by an array.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayStack<T> extends Stack<T> {

        private static final int MINIMUM_SIZE = 10;

        @SuppressWarnings("unchecked")
        private T[] array = (T[]) new Object[MINIMUM_SIZE];
        private int size = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public void push(T value) {
            if (size >= array.length) {
                array = Arrays.copyOf(array, ((size * 3) / 2) + 1);
            }
            array[size++] = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T pop() {
            if (size <= 0) return null;

            T t = array[--size];
            array[size] = null;

            if (size >= MINIMUM_SIZE && size < array.length / 2) {
                array = Arrays.copyOf(array, size);
            }

            return t;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            for (int i = 0; i < size; i++) {
                T obj = array[i];
                if (obj.equals(value)) return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = size - 1; i >= 0; i--) {
                builder.append(array[i]).append(", ");
            }
            return builder.toString();
        }
    }

    /**
     * This stack implementation is backed by a linked list.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class LinkedStack<T> extends Stack<T> {

        private Node<T> top = null;
        private int size = 0;

        public LinkedStack() {
            top = null;
            size = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void push(T value) {
            push(new Node<T>(value));
        }

        /**
         * Push node onto the stack.
         * 
         * @param node
         *            to push on the stack.
         */
        private void push(Node<T> node) {
            if (top == null) {
                top = node;
            } else {
                Node<T> oldTop = top;
                top = node;
                top.below = oldTop;
                oldTop.above = top;
            }
            size++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T pop() {
            Node<T> nodeToRemove = top;
            top = nodeToRemove.below;
            if (top != null) top.above = null;

            T value = null;
            if (nodeToRemove != null) {
                value = nodeToRemove.value;
                size--;
            }
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            if (top == null) return false;
            Node<T> node = top;
            while (node != null) {
                if (node.value.equals(value)) return true;
                node = node.below;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Node<T> node = top;
            while (node != null) {
                builder.append(node.value).append(", ");
                node = node.below;
            }
            return builder.toString();
        }

        private static class Node<T> {

            private T value = null;
            private Node<T> above = null;
            private Node<T> below = null;

            private Node(T value) {
                this.value = value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return "value=" + value + " above=" + ((above != null) ? above.value : "NULL") + " below=" + ((below != null) ? below.value : "NULL");
            }
        }
    }
}
