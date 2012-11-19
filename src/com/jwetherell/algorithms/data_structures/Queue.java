package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;


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
public abstract class Queue<T> {

    public enum QueueType {
        LinkedQueue, ArrayQueue
    };

    /**
     * Enqueue the value in the queue.
     * 
     * @param value
     *            to enqueue.
     */
    public abstract void enqueue(T value);

    /**
     * Dequeue the head of the queue.
     * 
     * @return value that was dequeued.
     */
    public abstract T dequeue();

    /**
     * Does the queue contain the value. Warning this is an O(n) operation.
     * 
     * @param value
     *            to locate in the queue.
     * @return True if queue contains value.
     */
    public abstract boolean contains(T value);

    /**
     * Number of items in the queue.
     * 
     * @return number of items.
     */
    public abstract int size();

    /**
     * Create queue from QueueType.
     * 
     * @param type
     *            of queue to create.
     * @return Queue that was created.
     */
    public static <T> Queue<T> createQueue(QueueType type) {
        switch (type) {
            case ArrayQueue:
                return new ArrayQueue<T>();
            default:
                return new LinkedQueue<T>();
        }
    }

    /**
     * This queue implementation is backed by an array.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayQueue<T> extends Queue<T> {

        private static final int MINIMUM_SIZE = 10;

        @SuppressWarnings("unchecked")
        private T[] array = (T[]) new Object[MINIMUM_SIZE];
        private int nextIndex = 0;
        private int firstIndex = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public void enqueue(T value) {
            int length = nextIndex - firstIndex;
            if (length >= array.length) {
                array = Arrays.copyOfRange(array, firstIndex, ((nextIndex * 3) / 2) + 1);
                nextIndex = nextIndex - firstIndex;
                firstIndex = 0;
            }
            array[nextIndex++] = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T dequeue() {
            int length = nextIndex - firstIndex;
            if (length < 0) return null;

            T t = array[firstIndex];
            array[firstIndex++] = null;

            length = nextIndex - firstIndex;
            if (length == 0) {
                // Removed last element
                nextIndex = 0;
                firstIndex = 0;
            }

            if (length >= MINIMUM_SIZE && (array.length - length) >= length) {
                array = Arrays.copyOfRange(array, firstIndex, nextIndex);
                nextIndex = length;
                firstIndex = 0;
            }

            return t;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            for (int i = firstIndex; i < nextIndex; i++) {
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
            return nextIndex - firstIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = nextIndex - 1; i >= firstIndex; i--) {
                builder.append(array[i]).append(", ");
            }
            return builder.toString();
        }
    }

    /**
     * This queue implementation is backed by a linked list.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class LinkedQueue<T> extends Queue<T> {

        private Node<T> head = null;
        private Node<T> tail = null;
        private int size = 0;

        public LinkedQueue() {
            head = null;
            tail = null;
            size = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enqueue(T value) {
            enqueue(new Node<T>(value));
        }

        /**
         * Enqueue the node in the queue.
         * 
         * @param node
         *            to enqueue.
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
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

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            if (head == null) return false;

            Node<T> node = head;
            while (node != null) {
                if (node.value.equals(value)) return true;
                node = node.next;
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
                return "value=" + value + " previous=" + ((prev != null) ? prev.value : "NULL") + " next=" + ((next != null) ? next.value : "NULL");
            }
        }
    }
}
