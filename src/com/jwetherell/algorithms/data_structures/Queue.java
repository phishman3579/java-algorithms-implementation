package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.interfaces.IQueue;

/**
 * In computer science, a queue is a particular kind of abstract data type or collection in which the entities in the collection are kept in order and the principal (or only) operations 
 * on the collection are the addition of entities to the rear terminal position, known as enqueue, and removal of entities from the front terminal position, known as dequeue. 
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Queue_(abstract_data_type)">Queue (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public interface Queue<T> extends IQueue<T> {

    /**
     * This queue implementation is backed by an array.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class ArrayQueue<T> implements Queue<T> {

        private static final int MINIMUM_SIZE = 1024;

        private T[] array = (T[]) new Object[MINIMUM_SIZE];
        private int lastIndex = 0;
        private int firstIndex = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean offer(T value) {
            if (size() >= array.length)
                grow(size());

            array[lastIndex % array.length] = value;
            lastIndex++;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T poll() {
            int size = lastIndex - firstIndex;
            if (size < 0) return null;

            T t = array[firstIndex % array.length];
            array[firstIndex % array.length] = null;
            firstIndex++;

            size = lastIndex - firstIndex;
            if (size <= 0) {
                // Removed last element
                lastIndex = 0;
                firstIndex = 0;
            }

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
            return array[firstIndex % array.length];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(T value) {
            for (int i=0; i < array.length; i++) {
                T obj = array[i];
                // if obj is null, it should return false (not NPE)
                if (value.equals(obj)) return remove(i);
            }
            return false;
        }

        private boolean remove(int index) {
            if (index<0 || index >= array.length) return false;
            if (index==firstIndex) return (poll()!=null);

            int adjIndex = index % array.length;
            int adjLastIndex = (lastIndex-1) % array.length;
            if (adjIndex != adjLastIndex) {
                // Shift the array down one spot
                System.arraycopy(array, index+1, array, index, (array.length - (index+1)));
                if (adjLastIndex < firstIndex) {
                	//Wrapped around array
                	array[array.length-1] = array[0];
                	System.arraycopy(array, 1, array, 0, firstIndex-1);
                }
            }
            array[adjLastIndex] = null;

            int shrinkSize = array.length>>1;
            if (shrinkSize >= MINIMUM_SIZE && size() < shrinkSize)
                shrink();

            lastIndex--;
            return true;
        }

        // Triple the size of the underlying array and rearrange to make sequential
        private void grow(int size) {
            int growSize = (size + (size<<1));
            T[] temp = (T[]) new Object[growSize];
            // Since the array can wrap around, make sure you grab the first chunk 
            int adjLast = lastIndex % array.length;
            if (adjLast > 0 && adjLast <= firstIndex) {
                System.arraycopy(array, 0, temp, array.length-adjLast, adjLast);
            }
            // Copy the remaining
            System.arraycopy(array, firstIndex, temp, 0, array.length - firstIndex);
            array = null;
            array = temp;
            lastIndex = (lastIndex - firstIndex);
            firstIndex = 0;
        }

        // Shrink the array by 50% and rearrange to make sequential
        private void shrink() {
            int shrinkSize = array.length>>1;
            T[] temp = (T[]) new Object[shrinkSize];
            // Since the array can wrap around, make sure you grab the first chunk 
            int adjLast = lastIndex % array.length;
            int endIndex = (lastIndex>array.length) ? array.length : lastIndex;
            if (adjLast <= firstIndex) {
                System.arraycopy(array, 0, temp, array.length - firstIndex, adjLast);
            }
            // Copy the remaining
            System.arraycopy(array, firstIndex, temp, 0, endIndex-firstIndex);
            array = null;
            array = temp;
            lastIndex = (lastIndex - firstIndex);
            firstIndex = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            firstIndex = 0;
            lastIndex = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            for (int i=0; i < array.length; i++) {
                T obj = array[i];
                // if obj is null, it should return false (not NPE)
                if (value.equals(obj)) return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean validate() {
            if (size()==0) return true;

            int localSize = 0;
            int realFirst = firstIndex;
            if (firstIndex>array.length) 
                realFirst = firstIndex%array.length;
            int realLast = lastIndex;
            if (lastIndex>array.length) 
                realLast = lastIndex%array.length;
            for (int i=0; i<array.length; i++) {
                T t = array[i];
                if ((realFirst==realLast) || 
                    (realFirst<realLast && (i>=realFirst && i<realLast)) || 
                    (realLast<realFirst && (i<realLast || i>=realFirst))
                ) {
                    if (t==null)
                        return false;
                    localSize++;
                } else {
                    if (t!=null)
                        return false;
                }
            }
            return (localSize==size());
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
        public java.util.Queue<T> toQueue() {
            return (new JavaCompatibleArrayQueue<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleArrayQueue<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = lastIndex - 1; i >= firstIndex; i--) {
                builder.append(array[i%array.length]).append(", ");
            }
            return builder.toString();
        }
    }

    /**
     * This queue implementation is backed by a linked list.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class LinkedQueue<T> implements Queue<T> {

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
        public boolean offer(T value) {
            return add(new Node<T>(value));
        }

        /**
         * Enqueue the node in the queue.
         * 
         * @param node
         *            to enqueue.
         */
        private boolean add(Node<T> node) {
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
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T poll() {
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
        public T peek() {
            return (tail!=null)?tail.value:null;
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
            if (node == null) return false;
            return remove(node);
        }

        private boolean remove(Node<T> node) {
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
        public boolean validate() {
            java.util.Set<T> keys = new java.util.HashSet<T>();
            Node<T> node = head;
            if (node!=null) {
                keys.add(node.value);
                if (node.prev!=null) return false;
                Node<T> child = node.next;
                while (child!=null) {
                    if (!validate(child,keys)) return false;
                    child = child.next;
                }
            }
            return (keys.size()==size());
        }

        private boolean validate(Node<T> node, java.util.Set<T> keys) {
            if (node.value==null) return false;
            keys.add(node.value);

            Node<T> child = node.next;
            if (child!=null) {
                if (!child.prev.equals(node)) return false;
                if (!validate(child,keys)) return false;
            } else {
                if (!node.equals(tail)) return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Queue<T> toQueue() {
            return (new JavaCompatibleLinkedQueue<T>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Collection<T> toCollection() {
            return (new JavaCompatibleLinkedQueue<T>(this));
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

    public static class JavaCompatibleArrayQueue<T> extends java.util.AbstractQueue<T> {

        private ArrayQueue<T> queue = null;

        public JavaCompatibleArrayQueue(ArrayQueue<T> queue) {
            this.queue = queue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return queue.offer(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return queue.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return queue.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean offer(T value) {
            return queue.offer(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T peek() {
            return queue.peek();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T poll() {
            return queue.poll();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return queue.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Iterator<T> iterator() {
            return (new ArrayQueueIterator<T>(queue));
        }

        private static class ArrayQueueIterator<T> implements java.util.Iterator<T> {

            private ArrayQueue<T> queue = null;
            private int last = -1;
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
                    last = queue.firstIndex+index;
                    return queue.array[(queue.firstIndex + index++) % queue.array.length];
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                queue.remove(last);
            }
        }
    }

    public static class JavaCompatibleLinkedQueue<T> extends java.util.AbstractQueue<T> {

        private LinkedQueue<T> queue = null;

        public JavaCompatibleLinkedQueue(LinkedQueue<T> queue) {
            this.queue = queue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return queue.offer(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return queue.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return queue.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean offer(T value) {
            return queue.offer(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T peek() {
            return queue.peek();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T poll() {
            return queue.poll();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return queue.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Iterator<T> iterator() {
            return (new LinkedQueueIterator<T>(queue));
        }

        private static class LinkedQueueIterator<T> implements java.util.Iterator<T> {

            private LinkedQueue<T> queue = null;
            private LinkedQueue.Node<T> lastNode = null;
            private LinkedQueue.Node<T> nextNode = null;

            private LinkedQueueIterator(LinkedQueue<T> queue) {
                this.queue = queue;
                this.nextNode = queue.tail;
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
                LinkedQueue.Node<T> current = nextNode;
                lastNode = current;
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
                queue.remove(lastNode);
            }
        }
    }
}
