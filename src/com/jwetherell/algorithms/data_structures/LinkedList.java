package com.jwetherell.algorithms.data_structures;

/**
 * Linked List (doubly link). A linked list is a data structure consisting of a
 * group of nodes which together represent a sequence.
 * 
 * http://en.wikipedia.org/wiki/Linked_list
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LinkedList<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public LinkedList() { }

    public LinkedList(T[] nodes) {
        for (T n : nodes) {
            add(new Node<T>(n));
        }
    }

    public void add(T value) {
        add(new Node<T>(value));
    }

    public void add(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> prev = tail;
            prev.nextNode = node;
            node.previousNode = prev;
            tail = node;
        }
        size++;
    }

    public boolean remove(T value) {
        //Find the node
        Node<T> node = head;
        while (node != null && (!node.value.equals(value))) {
            node = node.nextNode;
        }
        if (node == null) return false;

        //Update the tail, if needed
        if (node.equals(tail)) tail = node.previousNode;

        Node<T> prev = node.previousNode;
        Node<T> next = node.nextNode;
        if (prev != null && next != null) {
            prev.nextNode = next;
            next.previousNode = prev;
        } else if (prev != null && next == null) {
            prev.nextNode = null;
        } else if (prev == null && next != null) {
            // Node is the head
            next.previousNode = null;
            head = next;
        } else {
            // prev==null && next==null
            head = null;
        }
        size--;
        return true;
    }

    public boolean contains(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value.equals(value)) return true;
            node = node.nextNode;
        }
        return false;
    }

    public T get(int index) {
        T result = null;
        Node<T> node = head;
        int i = 0;
        while (node != null && i < index) {
            node = node.nextNode;
            i++;
        }
        if (node != null) result = node.value;
        return result;
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
        Node<T> node = head;
        while (node != null) {
            builder.append(node.value).append(", ");
            node = node.nextNode;
        }
        return builder.toString();
    }


    private static class Node<T> {

        private T value = null;
        private Node<T> previousNode = null;
        private Node<T> nextNode = null;

        private Node(T value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + " previous=" + ((previousNode != null) ? previousNode.value : "NULL") + " next="
                    + ((nextNode != null) ? nextNode.value : "NULL");
        }
    }
}
