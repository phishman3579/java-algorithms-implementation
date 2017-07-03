package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.interfaces.IStack;

/**
 * In computer science, a stack is an abstract data type that serves as a collection of elements, with two principal operations: push, which adds an element to the collection, and pop, which removes 
 * the most recently added element that was not yet removed. 
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Stack_(abstract_data_type)">Stack (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public interface Stack<T> extends IStack<T> {

    /**
     * This stack implementation is backed by an array.
     * <p>
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayStack<T> implements Stack<T> {

        private static final int MINIMUM_SIZE = 1024;

        private T[] array = (T[]) new Object[MINIMUM_SIZE];
        private int size = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean push(T value) {
            if (size >= array.length)
                grow();
            array[size++] = value;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T pop() {
            if (size <= 0) return null;

            T t = array[--size];
            array[size] = null;

            int shrinkSize = array.length>>1;
            if (shrinkSize >= MINIMUM_SIZE && size < shrinkSize)
                shrink();

            return t;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T peek() {
            if (size <= 0) return null;

            T t = array[--size];
            return t;
        }

        /**
         * Get item at index.
         * 
         * @param index of item.
         * @return T at index.
         */
        public T get(int index) {
            if (index>=0 && index<size) return array[index];
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(T value) {
            for (int i = 0; i < size; i++) {
                T obj = array[i];
                if (obj.equals(value)) {
                    return (remove(i));
                }
            }
            return false;
        }

        private boolean remove(int index) {
            if (index != --size) {
                // Shift the array down one spot
                System.arraycopy(array, index + 1, array, index, size - index);
            }
            array[size] = null;

            int shrinkSize = array.length>>1;
            if (shrinkSize >= MINIMUM_SIZE && size < shrinkSize)
                shrink();

            return true;
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
        public java.util.Queue<T> toLifoQueue() {
            return (new JavaCompatibleArrayStack<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleArrayStack<T>(this));
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
     * <p>
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class LinkedStack<T> implements Stack<T> {

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
        public boolean push(T value) {
            return push(new Node<T>(value));
        }

        /**
         * Push node onto the stack.
         * 
         * @param node
         *            to push on the stack.
         */
        private boolean push(Node<T> node) {
            if (top == null) {
                top = node;
            } else {
                Node<T> oldTop = top;
                top = node;
                top.below = oldTop;
                oldTop.above = top;
            }
            size++;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T pop() {
            if (top==null) return null;

            Node<T> nodeToRemove = top;
            top = nodeToRemove.below;
            if (top != null) top.above = null;

            T value = nodeToRemove.value;
            size--;
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T peek() {
            return (top!=null)?top.value:null;
        }

        /**
         * Get item at index.
         * 
         * @param index of item.
         * @return T at index.
         */
        public T get(int index) {
            Node<T> current = top;
            for (int i=0; i<index; i++) {
                if (current==null) break;
                current = current.below;
            }
            return (current!=null)?current.value:null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(T value) {
            // Find the node
            Node<T> node = top;
            while (node != null && (!node.value.equals(value))) {
                node = node.below;
            }
            if (node == null) return false;
            return remove(node);
        }

        private boolean remove(Node<T> node) {
            Node<T> above = node.above;
            Node<T> below = node.below;
            if (above != null && below != null) {
                above.below = below;
                below.above = above;
            } else if (above != null && below == null) {
                above.below = null;
            } else if (above == null && below != null) {
                // Node is the top
                below.above = null;
                top = below;
            } else {
                // prev==null && next==null
                top = null;
            }
            size--;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            top = null;
            size = 0;
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
        public boolean validate() {
            java.util.Set<T> keys = new java.util.HashSet<T>();
            Node<T> node = top;
            if (node!=null) {
                keys.add(node.value);
                if (node.above!=null) return false;
                Node<T> child = node.below;
                while (child!=null) {
                    if (!validate(child,keys)) return false;
                    child = child.below;
                }
            }
            return (keys.size()==size());
        }

        private boolean validate(Node<T> node, java.util.Set<T> keys) {
            if (node.value==null) return false;
            keys.add(node.value);

            Node<T> child = node.below;
            if (child!=null && !child.above.equals(node)) return false;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Queue<T> toLifoQueue() {
            return (new JavaCompatibleLinkedStack<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleLinkedStack<T>(this));
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

    public static class JavaCompatibleArrayStack<T> extends java.util.AbstractQueue<T> {

        private ArrayStack<T> stack = null;

        public JavaCompatibleArrayStack(ArrayStack<T> stack) {
            this.stack = stack;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return stack.push(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return stack.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return stack.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean offer(T value) {
            return stack.push(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T peek() {
            return stack.peek();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T poll() {
            return stack.pop();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return stack.size();
        }

        /**
         * {@inheritDoc}
         * 
         * This iterator is NOT thread safe and is invalid when the data structure is modified.
         */
        @Override
        public java.util.Iterator<T> iterator() {
            return (new ArrayStackIterator<T>(this.stack));
        }

        private static class ArrayStackIterator<T> implements java.util.Iterator<T> {

            private ArrayStack<T> stack = null;
            private int last = -1;
            private int index = 0;

            private ArrayStackIterator(ArrayStack<T> stack) {
                this.stack = stack;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (index+1<=stack.size);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                if (index>=stack.size) return null;
                last = index;
                return stack.array[index++];
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                stack.remove(last);
            }
        }
    }

    public static class JavaCompatibleLinkedStack<T> extends java.util.AbstractQueue<T> {

        private LinkedStack<T> stack = null;

        public JavaCompatibleLinkedStack(LinkedStack<T> stack) {
            this.stack = stack;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return stack.push(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return stack.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return stack.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean offer(T value) {
            return stack.push(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T peek() {
            return stack.peek();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T poll() {
            return stack.pop();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return stack.size();
        }

        /**
         * {@inheritDoc}
         * 
         * This iterator is NOT thread safe and is invalid when the data structure is modified.
         */
        @Override
        public java.util.Iterator<T> iterator() {
            return (new LinkedStackIterator<T>(stack));
        }

        private static class LinkedStackIterator<T> implements java.util.Iterator<T> {

            private LinkedStack<T> stack = null;
            private LinkedStack.Node<T> lastNode = null;
            private LinkedStack.Node<T> nextNode = null;

            private LinkedStackIterator(LinkedStack<T> stack) {
                this.stack = stack;
                LinkedStack.Node<T> current = stack.top;
                while (current!=null && current.below!=null) current = current.below;
                this.nextNode = current;
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
                LinkedStack.Node<T> current = nextNode;
                if (current!=null) {
                    lastNode = nextNode;
                    nextNode = current.above;
                    return current.value;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                stack.remove(lastNode);
            }
        }
    }
}
