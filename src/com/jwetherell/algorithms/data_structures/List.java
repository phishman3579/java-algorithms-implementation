package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.interfaces.IList;

/**
 * In mathematics, a sequence is an enumerated collection of objects in which repetitions are allowed. Like a set, it contains members (also called elements, or terms). The number of elements 
 * (possibly infinite) is called the length of the sequence. Unlike a set, order matters, and exactly the same elements can appear multiple times at different positions in the sequence.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Sequence">Sequence (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public abstract class List<T> implements IList<T> {

    /**
     * A dynamic array, growable array, resizable array, dynamic table, or array
     * list is a random access, variable-size list data structure that allows
     * elements to be added or removed.
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Dynamic_array">Dynamic Array (Wikipedia)</a>
     * <br>
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayList<T> extends List<T> {

        private static final int MINIMUM_SIZE = 1024;

        private int size = 0;
        private T[] array = (T[]) new Object[MINIMUM_SIZE];

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return add(size,value);
        }

        /**
         * Add value to list at index.
         * 
         * @param index to add value.
         * @param value to add to list.
         */
        public boolean add(int index, T value) {
            if (size >= array.length)
                grow();
            if (index==size) {
                array[size] = value;
            } else {
                // Shift the array down one spot
                System.arraycopy(array, index, array, index+1, size - index);
                array[index] = value;
            }
            size++;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(T value) {
            for (int i = 0; i < size; i++) {
                T obj = array[i];
                if (obj.equals(value)) {
                    remove(i);
                    return true;
                }
            }
            return false;
        }

        /**
         * Remove value at index from list.
         * 
         * @param index of value to remove.
         * @return value at index.
         */
        public T remove(int index) {
            if (index<0 || index>=size) return null;

            T t = array[index];
            if (index != --size) {
                // Shift the array down one spot
                System.arraycopy(array, index + 1, array, index, size - index);
            }
            array[size] = null;

            int shrinkSize = array.length>>1;
            if (shrinkSize >= MINIMUM_SIZE && size < shrinkSize)
                shrink();

            return t;
        }

        // Grow the array by 50%
        private void grow() {
            int growSize = size + (size<<1);
            array = Arrays.copyOf(array, growSize);
        }

        // Shrink the array by 50%
        private void shrink() {
            int shrinkSize = array.length>>1;
            array = Arrays.copyOf(array, shrinkSize);
        }

        /**
         * Set value at index.
         * 
         * @param index of value to set.
         * @param value to set.
         * @return value previously at index.
         */
        public T set(int index, T value) {
            if (index<0 || index>=size) return null;
            T t = array[index];
            array[index] = value;
            return t;
        }

        /**
         * Get value at index.
         * 
         * @param index of value to get.
         * @return value at index.
         */
        public T get(int index) {
            if (index<0 || index>=size) return null;
            return array[index];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            size = 0;
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
        public boolean validate() {
            int localSize = 0;
            for (int i=0; i<array.length; i++) {
                T t = array[i];
                if (i<size) {
                    if (t==null) return false;
                    localSize++;
                } else {
                    if (t!=null) return false;
                }
            }
            return (localSize==size);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.List<T> toList() {
            return (new JavaCompatibleArrayList<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleArrayList<T>(this));
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
    }

    public static class JavaCompatibleArrayList<T> extends java.util.AbstractList<T> implements java.util.RandomAccess {

        private List.ArrayList<T> list = null;

        public JavaCompatibleArrayList(List.ArrayList<T> list) {
            this.list = list;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return list.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return list.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return list.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return list.size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, T value) {
            list.add(index, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T remove(int index) {
            return list.remove(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T get(int index) {
            T t = list.get(index);
            if (t!=null) 
                return t;
            throw new IndexOutOfBoundsException();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T set(int index, T value) {
            return list.set(index, value);
        }
    }

    /**
     * Linked List (Singly link). A linked list is a data structure consisting
     * of a group of nodes which together represent a sequence.
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Linked_list">Linked List (Wikipedia)</a>
     * <br>
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class SinglyLinkedList<T> extends List<T> {

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
                tail = node;
            }
            size++;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(T value) {
            // Find the node
            Node<T> prev = null;
            Node<T> node = head;
            while (node != null && (!node.value.equals(value))) {
                prev = node;
                node = node.next;
            }

            if (node == null)
                return false;

            // Update the tail, if needed
            if (node.equals(tail)) {
                tail = prev;
                if (prev != null)
                    prev.next = null;
            }

            Node<T> next = node.next;
            if (prev != null && next != null) {
                prev.next = next;
            } else if (prev != null && next == null) {
                prev.next = null;
            } else if (prev == null && next != null) {
                // Node is the head
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
        public void clear() {
            head = null;
            size = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
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
        public boolean validate() {
            java.util.Set<T> keys = new java.util.HashSet<T>();
            Node<T> node = head;
            if (node != null) {
                keys.add(node.value);

                Node<T> child = node.next;
                while (child != null) {
                    if (!validate(child,keys)) 
                        return false;
                    child = child.next;
                }
            }
            return (keys.size()==size);
        }

        private boolean validate(Node<T> node, java.util.Set<T> keys) {
            if (node.value==null) 
                return false;

            keys.add(node.value);

            Node<T> child = node.next;
            if (child==null) {
                if (!node.equals(tail)) 
                    return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.List<T> toList() {
            return (new JavaCompatibleSinglyLinkedList<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleSinglyLinkedList<T>(this));
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
            private Node<T> next = null;

            private Node() { }

            private Node(T value) {
                this.value = value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return "value=" + value + " next=" + ((next != null) ? next.value : "NULL");
            }
        }
    }

    /**
     * Linked List (singly link). A linked list is a data structure consisting
     * of a group of nodes which together represent a sequence.
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Linked_list">Linked List (Wikipedia)</a>
     * <br>
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class JavaCompatibleSinglyLinkedList<T> extends java.util.AbstractSequentialList<T> {

        private List.SinglyLinkedList<T> list = null;

        public JavaCompatibleSinglyLinkedList(List.SinglyLinkedList<T> list) {
            this.list = list;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return list.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return list.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return list.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return list.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.ListIterator<T> listIterator(int index) {
            return (new SinglyLinkedListListIterator<T>(list));
        }

        private static class SinglyLinkedListListIterator<T> implements java.util.ListIterator<T> {

            private int index = 0;

            private SinglyLinkedList<T> list = null;
            private SinglyLinkedList.Node<T> prev = null;
            private SinglyLinkedList.Node<T> next = null;
            private SinglyLinkedList.Node<T> last = null;

            private SinglyLinkedListListIterator(SinglyLinkedList<T> list) {
                this.list = list;
                this.next = list.head;
                this.prev = null;
                this.last = null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void add(T value) {
                SinglyLinkedList.Node<T> node = new SinglyLinkedList.Node<T>(value);

                if (list.head == null) {
                    list.head = node;
                    list.tail = node;
                } else {
                    SinglyLinkedList.Node<T> p = null;
                    SinglyLinkedList.Node<T> n = list.head;
                    while (n!= null && !(n.equals(next))) {
                        p = node;
                        n = node.next;
                    }
                    if (p != null) {
                        p.next = node;
                    } else {
                        // replacing head
                        list.head = node;
                    }
                    node.next = n;
                }
                this.next = node;

                list.size++;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (last == null) 
                    return;

                SinglyLinkedList.Node<T> p = null;
                SinglyLinkedList.Node<T> node = this.last;
                while (node!= null && !(node.equals(last))) {
                    p = node;
                    node = node.next;
                }

                SinglyLinkedList.Node<T> n = last.next;
                if (p != null) 
                    p.next = n;

                if (last.equals(list.head)) 
                    list.head = n;
                if (last.equals(list.tail)) 
                    list.tail = p;

                list.size--;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void set(T value) {
                if (last != null) 
                    last.value = value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (next!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasPrevious() {
                return (prev!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int nextIndex() {
                return index;
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
                if (next == null) 
                    throw new java.util.NoSuchElementException();

                index++;
                last = next;
                prev = next;
                next = next.next;

                return last.value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T previous() {
                if (prev == null) 
                    throw new java.util.NoSuchElementException();

                index--;
                last = prev;
                next = prev;

                SinglyLinkedList.Node<T> p = null;
                SinglyLinkedList.Node<T> node = this.list.head;
                while (node!= null && !(node.equals(prev))) {
                    p = node;
                    node = node.next;
                }
                prev = p;

                return last.value;
            }
        }
    }

    /**
     * Linked List (doubly link). A linked list is a data structure consisting
     * of a group of nodes which together represent a sequence.
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Linked_list">Linked List (Wikipedia)</a>
     * <br>
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class DoublyLinkedList<T> extends List<T> {

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
        public boolean remove(T value) {
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
        public void clear() {
            head = null;
            size = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
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
        public boolean validate() {
            java.util.Set<T> keys = new java.util.HashSet<T>();
            Node<T> node = head;
            if (node!=null) {
                keys.add(node.value);
                if (node.prev!=null) 
                    return false;
                Node<T> child = node.next;
                while (child!=null) {
                    if (!validate(child,keys)) 
                        return false;
                    child = child.next;
                }
            }
            return (keys.size()==size);
        }

        private boolean validate(Node<T> node, java.util.Set<T> keys) {
            if (node.value==null) 
                return false;

            keys.add(node.value);

            Node<T> child = node.next;
            if (child!=null) {
                if (!child.prev.equals(node)) 
                    return false;
            } else {
                if (!node.equals(tail)) 
                    return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.List<T> toList() {
            return (new JavaCompatibleDoublyLinkedList<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleDoublyLinkedList<T>(this));
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

            private Node() { }

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
    }

    public static class JavaCompatibleDoublyLinkedList<T> extends java.util.AbstractSequentialList<T> {

        private List.DoublyLinkedList<T> list = null;

        public JavaCompatibleDoublyLinkedList(List.DoublyLinkedList<T> list) {
            this.list = list;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return list.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return list.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return list.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return list.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.ListIterator<T> listIterator(int index) {
            return (new DoublyLinkedListListIterator<T>(list));
        }

        private static class DoublyLinkedListListIterator<T> implements java.util.ListIterator<T> {

            private int index = 0;

            private DoublyLinkedList<T> list = null;
            private DoublyLinkedList.Node<T> prev = null;
            private DoublyLinkedList.Node<T> next = null;
            private DoublyLinkedList.Node<T> last = null;

            private DoublyLinkedListListIterator(DoublyLinkedList<T> list) {
                this.list = list;
                this.next = list.head;
                this.prev = null;
                this.last = null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void add(T value) {
                DoublyLinkedList.Node<T> node = new DoublyLinkedList.Node<T>(value);
                DoublyLinkedList.Node<T> n = this.next;

                if (this.prev != null) 
                    this.prev.next = node;
                node.prev = this.prev;

                node.next = n;
                if (n != null) 
                    n.prev = node;

                this.next = node;
                if (this.prev == null) 
                    list.head = node; // new root
                list.size++;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (last == null) 
                    return;

                DoublyLinkedList.Node<T> p = last.prev;
                DoublyLinkedList.Node<T> n = last.next;
                if (p != null) 
                    p.next = n;
                if (n != null) 
                    n.prev = p;
                if (last.equals(list.head)) 
                    list.head = n;
                if (last.equals(list.tail)) 
                    list.tail = p;

                list.size--;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void set(T value) {
                if (last != null) 
                    last.value = value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (next!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasPrevious() {
                return (prev!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int nextIndex() {
                return index;
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
                if (next == null) 
                    throw new java.util.NoSuchElementException();

                index++;
                last = next;
                prev = next;
                next = next.next;

                return last.value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T previous() {
                if (prev == null) 
                    throw new java.util.NoSuchElementException();

                index--;
                last = prev;
                next = prev;
                prev = next.prev;

                return last.value;
            }
        }
    }
}
