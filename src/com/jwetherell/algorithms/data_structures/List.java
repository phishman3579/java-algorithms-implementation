package com.jwetherell.algorithms.data_structures;

import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A list or sequence is an abstract data type that implements an ordered
 * collection of values, where the same value may occur more than once.
 * 
 * http://en.wikipedia.org/wiki/List_(computing)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class List<T> {

    /**
     * A dynamic array, growable array, resizable array, dynamic table, or array
     * list is a random access, variable-size list data structure that allows
     * elements to be added or removed.
     * 
     * http://en.wikipedia.org/wiki/Dynamic_array
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayList<T> extends AbstractList<T> {

        private static final int MINIMUM_SIZE = 10;

        private int size = 0;

        @SuppressWarnings("unchecked")
        private T[] array = (T[]) new Object[MINIMUM_SIZE];

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            if (size >= array.length) {
                array = Arrays.copyOf(array, ((size * 3) / 2) + 1);
            }
            array[size++] = value;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            for (int i = 0; i < size; i++) {
                T obj = array[i];
                if (obj.equals(value)) {
                    if (i != --size) {
                        // Shift the array down one spot
                        System.arraycopy(array, i + 1, array, i, size - i);
                    }
                    array[size] = null;

                    if (size >= MINIMUM_SIZE && size < array.length / 2) {
                        array = Arrays.copyOf(array, size);
                    }

                    return true;
                }
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            for (int i = 0; i < size; i++) {
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
        public T get(int index) {
            if (index >= size)
                return null;
            return array[index];
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
            for (int i = 0; i < size; i++) {
                builder.append(array[i]).append(", ");
            }
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * This iterator is NOT thread safe and is invalid when the data structure is modified.
         */
        @Override
        public Iterator<T> iterator() {
            return (new ArrayListIterator<T>(this));
        }

        private static class ArrayListIterator<T> implements Iterator<T> {

            private ArrayList<T> list = null;
            private int index = 0;

            private ArrayListIterator(ArrayList<T> list) {
                this.list = list;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (index+1<=list.size);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                if (index>=list.size) return null;
                return list.array[index++];
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("OperationNotSupported");
            }
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            return (new ArrayListListIterator<T>(this));
        }

        private static class ArrayListListIterator<T> implements ListIterator<T> {

            private int index = -1;
            private ArrayList<T> list = null;

            private ArrayListListIterator(ArrayList<T> list) {
                this.list = list;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void add(T e) {
                throw new UnsupportedOperationException("OperationNotSupported");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("OperationNotSupported");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void set(T e) {
                throw new UnsupportedOperationException("OperationNotSupported");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (index+1<list.size);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasPrevious() {
                return (index>=0);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int nextIndex() {
                int next = index+1;
                return (next<list.size)?next:list.size;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int previousIndex() {
                return index;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                if (index+1>=list.size) return null;
                return list.array[++index];
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T previous() {
                if (index>=0) return list.array[index--];
                return null;
            }
        }
    }

    /**
     * Linked List (doubly link). A linked list is a data structure consisting
     * of a group of nodes which together represent a sequence.
     * 
     * http://en.wikipedia.org/wiki/Linked_list
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class LinkedList<T> extends AbstractSequentialList<T> {

        private int size = 0;
        private Node<T> head = null;
        private Node<T> tail = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return add(new Node<T>(value));
        }

        /**
         * Add node to list.
         * 
         * @param node
         *            to add to list.
         */
        private boolean add(Node<T> node) {
            if (head == null) {
                head = node;
                tail = node;
            } else {
                Node<T> prev = tail;
                prev.next = node;
                node.prev = prev;
                tail = node;
            }
            size++;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            // Find the node
            Node<T> node = head;
            while (node != null && (!node.value.equals(value))) {
                node = node.next;
            }
            if (node == null)
                return false;

            // Update the tail, if needed
            if (node.equals(tail))
                tail = node.prev;

            Node<T> prev = node.prev;
            Node<T> next = node.next;
            if (prev != null && next != null) {
                prev.next = next;
                next.prev = prev;
            } else if (prev != null && next == null) {
                prev.next = null;
            } else if (prev == null && next != null) {
                // Node is the head
                next.prev = null;
                head = next;
            } else {
                // prev==null && next==null
                head = null;
            }
            size--;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
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
        public T get(int index) {
            T result = null;
            Node<T> node = head;
            int i = 0;
            while (node != null && i < index) {
                node = node.next;
                i++;
            }
            if (node != null)
                result = node.value;
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
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
                return "value=" + value + " previous=" + ((prev != null) ? prev.value : "NULL")
                        + " next=" + ((next != null) ? next.value : "NULL");
            }
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

        /**
         * {@inheritDoc}
         * 
         * This iterator is NOT thread safe and is invalid when the data structure is modified.
         */
        @Override
        public Iterator<T> iterator() {
            return (new LinkedListIterator<T>(this.head));
        }

        private static class LinkedListIterator<T> implements Iterator<T> {

            private Node<T> nextNode = null;

            private LinkedListIterator(Node<T> head) {
                this.nextNode = head;
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
                    nextNode = current.next;
                    return current.value;
                } else {
                    nextNode = null;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("OperationNotSupported");
            }
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            return (new LinkedListListIterator<T>(this));
        }

        private static class LinkedListListIterator<T> implements ListIterator<T> {

            private int index = -1;

            private LinkedList<T> list = null;
            private Node<T> prevNode = null;
            private Node<T> current = null;
            private Node<T> nextNode = null;

            private LinkedListListIterator(LinkedList<T> list) {
                this.list = list;
                this.prevNode = null;
                this.current = null;
                this.nextNode = list.head;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void add(T e) {
                throw new UnsupportedOperationException("OperationNotSupported");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("OperationNotSupported");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void set(T e) {
                throw new UnsupportedOperationException("OperationNotSupported");
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
            public boolean hasPrevious() {
                return (prevNode!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int nextIndex() {
                int next = index+1;
                return (next<list.size)?next:list.size;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int previousIndex() {
                return index-1;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                prevNode = current;
                current = nextNode;
                if (index<list.size)index++;
                if (current!=null) {
                    nextNode = current.next;
                    return current.value;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T previous() {
                current = prevNode;
                if (index>0) index--;
                if (current!=null) {
                    prevNode = current.prev;
                    nextNode = current.next;
                    return current.value;
                }
                return null;
            }
        }
    }
}
