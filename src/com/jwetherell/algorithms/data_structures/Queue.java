package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;
import java.util.Iterator;

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
public abstract class Queue<T> implements Iterable<T> {

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
        private int lastIndex = 0;
        private int firstIndex = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public void enqueue(T value) {
            int length = lastIndex - firstIndex;
            if (length >= array.length) {
                array = Arrays.copyOfRange(array, firstIndex, ((lastIndex * 3) / 2) + 1);
                lastIndex = lastIndex - firstIndex;
                firstIndex = 0;
            }
            array[lastIndex++] = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T dequeue() {
            int length = lastIndex - firstIndex;
            if (length < 0)
                return null;

            T t = array[firstIndex];
            array[firstIndex++] = null;

            length = lastIndex - firstIndex;
            if (length == 0) {
                // Removed last element
                lastIndex = 0;
                firstIndex = 0;
            }

            if (length >= MINIMUM_SIZE && (array.length - length) >= length) {
                array = Arrays.copyOfRange(array, firstIndex, lastIndex);
                lastIndex = length;
                firstIndex = 0;
            }

            return t;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            for (int i = firstIndex; i < lastIndex; i++) {
                T obj = array[i];
                if (obj.equals(value))
                    return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return lastIndex - firstIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = lastIndex - 1; i >= firstIndex; i--) {
                builder.append(array[i]).append(", ");
            }
            return builder.toString();
        }

        @Override
        public Iterator<T> iterator() {
            return (new ArrayQueueIterator<T>(this));
        }

        private static class ArrayQueueIterator<T> implements Iterator<T> {

            private ArrayQueue<T> queue = null;
            private int index = 0; //offset from first

            private ArrayQueueIterator(ArrayQueue<T> queue) {
                this.queue = queue;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return ((queue.firstIndex+index) < queue.lastIndex);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                if (queue.firstIndex+index < queue.lastIndex) {
                    return queue.array[queue.firstIndex+index++];
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                System.err.println("OperationNotSupported");
            }
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
            if (head == null)
                return false;

            Node<T> node = head;
            while (node != null) {
                if (node.value.equals(value))
                    return true;
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
                return "value=" + value + " previous=" + ((prev != null) ? prev.value : "NULL") + " next="
                        + ((next != null) ? next.value : "NULL");
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<T> iterator() {
            return (new LinkedQueueIterator<T>(this.tail));
        }

        private static class LinkedQueueIterator<T> implements Iterator<T> {

            private Node<T> nextNode = null;

            private LinkedQueueIterator(Node<T> tail) {
                this.nextNode = tail;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (nextNode!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                Node<T> current = nextNode;
                if (current!=null) {
                    nextNode = current.prev;
                    return current.value;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                System.err.println("OperationNotSupported");
            }
        }
    }
}
