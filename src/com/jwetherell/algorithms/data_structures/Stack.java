package com.jwetherell.algorithms.data_structures;

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
public class Stack<T> {

    private Node<T> top = null;
    private int size = 0;

    public Stack() {
        top = null;
        size = 0;
    }

    public Stack(T[] nodes) {
        this();
        populate(nodes);
    }

    private void populate(T[] nodes) {
        for (T n : nodes) {
            push(new Node<T>(n));
        }
    }

    public void push(T value) {
        push(new Node<T>(value));
    }

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

    public boolean contains(T value) {
        if (top==null) return false;
        Node<T> node = top;
        while (node!=null) {
            if (node.value.equals(value)) return true;
            node = node.below;
        }
        return false;
    }
    
    public int getSize() {
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
            return "value=" + value + " above=" + ((above != null) ? above.value : "NULL") + " below="
                    + ((below != null) ? below.value : "NULL");
        }
    }
}
