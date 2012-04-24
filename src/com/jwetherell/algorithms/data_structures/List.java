package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

/**
 * A list or sequence is an abstract data type that implements an ordered collection 
 * of values, where the same value may occur more than once.
 * 
 * http://en.wikipedia.org/wiki/List_(computing)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class List<T> {

    public enum ListType { LinkedList, ArrayList };

    public abstract void add(T value);
    public abstract boolean remove(T value);
    public abstract boolean contains(T value);
    public abstract T get(int index);
    public abstract int getSize();

    public static <T> List<T> createList(ListType type) {
        switch (type) {
            case ArrayList:
                return new ArrayList<T>();
            default:
                return new LinkedList<T>();
        }
    }


    /**
     * Linked List (doubly link). A linked list is a data structure consisting of a
     * group of nodes which together represent a sequence.
     * 
     * http://en.wikipedia.org/wiki/Linked_list
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class LinkedList<T> extends List<T> {
        private Node<T> head = null;
        private Node<T> tail = null;    
        private int size = 0;


        @Override
        public void add(T value) {
            add(new Node<T>(value));
        }
    
        private void add(Node<T> node) {
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

        @Override
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

        @Override
        public boolean contains(T value) {
            Node<T> node = head;
            while (node != null) {
                if (node.value.equals(value)) return true;
                node = node.nextNode;
            }
            return false;
        }

        @Override
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

        @Override
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

    /**
     * A dynamic array, growable array, resizable array, dynamic table, or array list 
     * is a random access, variable-size list data structure that allows elements to 
     * be added or removed.
     * 
     * http://en.wikipedia.org/wiki/Dynamic_array
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayList<T> extends List<T> {    

        private static final int GROW_IN_CHUNK_SIZE = 10;
        private static final int SHRINK_IN_CHUNK_SIZE = 10;
        
        @SuppressWarnings("unchecked")
        private T[] array = (T[]) new Object[GROW_IN_CHUNK_SIZE];
        private int size = 0;


        @Override
        public void add(T value) {
            if (size>=array.length) {
                T[] temp = Arrays.copyOf(array, size+GROW_IN_CHUNK_SIZE);
                temp[size++] = value;
                array = temp;
            } else {
                array[size++] = value;
            }
        }

        @Override
        public boolean remove(T value) {
            for (int i=0; i<size; i++) {
                T obj = array[i];
                if (obj.equals(value)) {
                    for (int j=i+1; j<size; j++) {
                        array[j-1] = array[j];
                    }
                    array[--size] = null;

                    if (array.length-size>=SHRINK_IN_CHUNK_SIZE) {
                        T[] temp = Arrays.copyOf(array, size);
                        array = temp;
                    }

                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean contains(T value) {
            for (int i=0; i<size; i++) {
                T obj = array[i];
                if (obj.equals(value)) return true;
            }
            return false;
        }

        @Override
        public T get(int index) {
            if (index>=size) return null;
            return array[index];
        }

        @Override
        public int getSize() {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<size; i++) {
                builder.append(array[i]).append(", ");
            }
            return builder.toString();
        }
    }
}
