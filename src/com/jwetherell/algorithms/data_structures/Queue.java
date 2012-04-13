package com.jwetherell.algorithms.data_structures;

/**
 * Queue. A queue is a particular kind of abstract data type or collection in
 * which the entities in the collection are kept in order and the principal (or
 * only) operations on the collection are the addition of entities to the rear
 * terminal position and removal of entities from the front terminal position.
 * This makes the queue a First-In-First-Out (FIFO) data structure. In a FIFO
 * data structure, the first element added to the queue will be the first one to
 * be removed.
 * 
 * http://en.wikipedia.org/wiki/Queue_(abstract_data_type)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Queue<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    public Queue(T[] nodes) {
        this();
        populate(nodes);
    }

    private void populate(T[] nodes) {
        for (T n : nodes) {
            enqueue(new Node<T>(n));
        }
    }

    public void enqueue(T value) {
        enqueue(new Node<T>(value));
    }

    private void enqueue(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> oldHead = head;
            head = node;
            node.next = oldHead;
            oldHead.prev = node;
        }
        size++;
    }

    public T dequeue() {
        T result = null;
        if (tail != null) {
            result = tail.value;

            Node<T> prev = tail.prev;
            if (prev != null) {
                prev.next = null;
                tail = prev;
            } else {
                head = null;
                tail = null;
            }
            size--;
        }
        return result;
    }

    public boolean contains(T value) {
        if (head == null) return false;
        
        Node<T> node = head;
        while (node!=null) {
            if (node.value.equals(value)) return true;
            node = node.next;
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
        Node<T> node = head;
        while (node != null) {
            builder.append(node.value).append(", ");
            node = node.next;
        }
        return builder.toString();
    }

    private static class Node<T> {

        private T value = null;
        private Node<T> prev = null;
        private Node<T> next = null;

        private Node(T value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + " previous=" + ((prev != null) ? prev.value : "NULL") + " next="
                    + ((next != null) ? next.value : "NULL");
        }
    }
}
