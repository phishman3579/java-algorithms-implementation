package com.jwetherell.algorithms.data_structures;

import java.util.Random;

/**
 * Skip List. A skip list is a data structure for storing a sorted list of items
 * using a hierarchy of linked lists that connect increasingly sparse
 * subsequences of the items. These auxiliary lists allow item lookup with
 * efficiency comparable to balanced binary search trees.
 * 
 * http://en.wikipedia.org/wiki/Skip_list
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<T>> implements ISet<T> {

    private static final Random seedGenerator = new Random();

    // Defaults
    private static final int MAX = 31;

    private int randomSeed = -1;
    private Node<T> head = null;
    private int size = 0;

    public SkipList() {
        randomSeed = seedGenerator.nextInt() | 0x0100;
    }

    /**
     * Returns a random level for inserting a new node.
     * Hardwired to k=1, p=0.5, max 31
     */
    private int getRandom() {
        int x = randomSeed;
        x ^= x << 13;
        x ^= x >>> 17;
        randomSeed = x ^= x << 5;
        if ((x & 0x8001) != 0) // test highest and lowest bits
            return 0;
        int level = 1;
        while (((x >>>= 1) & 1) != 0) ++level;
        return level;
    }

    private Node<T> insertValue(T value) {
        Node<T> node = null;
        if (head==null) {
            // new list
            node = new Node<T>(MAX,value);
            head = node;
        } else {
            int level = getRandom();
            node = new Node<T>(level,value);

            // Insert
            Node<T> prev = head;
            if (head.value.compareTo(value)==1) {
                // handle case where head is greater than new node, just swap values
                T oldHeadValue = head.value;
                head.value = value;
                node.value = oldHeadValue;
            }
            for (int i=MAX; i>=0; i--) {
                Node<T> next = prev.getNext(i);
                while (next!=null) {
                    if (next.value.compareTo(value)==1) break;
                    prev = next;
                    next = prev.getNext(i);
                }
                if (i <= level) {
                    node.setNext(i, next);
                    if (next!=null) next.setPrev(i, node);

                    prev.setNext(i, node);
                    node.setPrev(i, prev);
                }
            }
        }
        size++;
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        Node<T> node = insertValue(value);
        return (node!=null);
    }

    private Node<T> findValue(T value) {
        Node<T> node = head;
        if (node==null) return null; 
        else if (node.value.equals(value)) return node;

        // Current node is not the node we are looking for. Keep moving down
        // until you find a node with a "next" node.
        int level = node.getLevel();
        Node<T> next = node.getNext(level);
        while (next==null) {
            // If next is null, move down
            if (level>0) next = node.getNext(--level);
            else break;
        }

        // Found a node with a next node OR I reached the bottom level
        while (next!=null) {
            int comp = next.value.compareTo(value);
            if (comp==0) {
                // Found the node!
                return next;
            } else if (comp==1) {
                // Found a node that's greater, move down a level
                if (level>0) level--;
                else return null;

                // Update the next pointer
                next = node.getNext(level);
            } else {
                // Next is less then the value we are looking for, keep moving right.
                node = next;
                next = node.getNext(level);
                while (next==null && level>0) {
                    next = node.getNext(--level);
                }
            }
        }
        return null;
    }

    private Node<T> removeValue(T value) {
        Node<T> node = findValue(value);
        if (node==null) return null;
        
        Node<T> prev = node.getPrev(0);
        Node<T> next = node.getNext(0);
        if (prev == null) {
            // Removing head
            if (next != null) {
                node.value = next.value;
                next.value = value;
                node = next;
            } else {
                head = null;
            }
        }
        int level = node.getLevel();
        for (int i=level; i>=0; i--) {
            prev = node.getPrev(i);
            next = node.getNext(i);
            if (prev != null)
                prev.setNext(i, next);
            if (next != null)
                next.setPrev(i, prev);
        }
        size--;
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        Node<T> node = removeValue(value);
        return (node!=null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        return (findValue(value)!=null);
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
        Node<T> node = head;
        if (node==null) return true;

        int level = node.getLevel();
        for (int i=level; i>=0; i--) {
            Node<T> prev = null;
            while (node != null) {
                // The list should be ordered
                if (prev != null && (node.value.compareTo(prev.value) == -1)) {
                    System.err.println("List is not in order.");
                    return false;
                }
                prev = node;
                node = node.getNext(i);
                if (node != null && !node.getPrev(i).value.equals(prev.value)) {
                    System.err.println("prev!=next");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Set<T> toSet() {
        return (new JavaCompatibleSkipList<T>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<T> toCollection() {
        return (new JavaCompatibleSkipList<T>(this));
    }

    // Output a String version of the skip list. If a value and level is passed
    // then output with that node highlighted.
    public String getString(T value, int level) {
        StringBuilder builder = new StringBuilder();
        builder.append("size=").append(size).append("\n");
        Node<T> node = head;
        if (node!=null) {
            int iLevel = node.getLevel();
            for (int i=iLevel; i>=0; i--) {
                builder.append("[").append(i).append("] ");
                node = head;
                while (node != null) {
                    if (level==i && value!=null && node.value.equals(value)) 
                        builder.append("(").append(node.value).append(")");
                    else 
                        builder.append(node.value);
                    Node<T> next = node.getNext(i);
                    if (next != null)
                        builder.append("->");
                    node = next;
                }
                if (i>0) builder.append("\n");
            }
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getString(null,-1);
    }

    private static final class Node<T extends Comparable<T>> {

        private Node<T>[] prev = null;
        private Node<T>[] next = null;
        private T value = null;

        private Node(int level, T data) {
            this.prev = new Node[level+1];
            this.next = new Node[level+1];
            this.value = data;
        }

        private int getLevel() {
            return prev.length-1;
        }

        private void setNext(int idx, Node<T> node) {
            this.next[idx] = node;
        }
        private Node<T> getNext(int idx) {
            if (idx>=this.next.length) return null;
            return this.next[idx];
        }

        private void setPrev(int idx, Node<T> node) {
            this.prev[idx] = node;
        }
        private Node<T> getPrev(int idx) {
            if (idx>=this.prev.length) return null; 
            return this.prev[idx];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("data=").append(value);
            int size = prev.length;
            if (prev!=null) {
                builder.append("\n").append("prev=[");
                for (int i=0; i<size; i++) {
                    Node<T> n = prev[i];
                    if (n!=null) builder.append(n.value);
                    else builder.append("none");
                    if (i!=size-1) builder.append("<-");
                }
                builder.append("]");
            }
            if (next!=null) {
                builder.append("\n").append("next=[");
                for (int i=0; i<size; i++) {
                    Node<T> n = next[i];
                    if (n!=null) builder.append(n.value);
                    else builder.append("none");
                    if (i!=size-1) builder.append(", ");
                }
                builder.append("]");
            }
            return builder.toString();
        }
    }

    public static class JavaCompatibleSkipList<T extends Comparable<T>> extends java.util.AbstractSet<T> {

        private SkipList<T> list = null;

        public JavaCompatibleSkipList(SkipList<T> list) {
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
        public java.util.Iterator<T> iterator() {
            return (new SkipListListIterator<T>(list));
        }

        private static class SkipListListIterator<T extends Comparable<T>> implements java.util.Iterator<T> {

            private SkipList<T> list = null;
            private SkipList.Node<T> next = null;
            private SkipList.Node<T> last = null;

            private SkipListListIterator(SkipList<T> list) {
                this.list = list;
                this.next = list.head;
            }
 
            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (last==null) return;
                list.remove(last.value);
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
            public T next() {
                if (next == null) throw new java.util.NoSuchElementException();

                last = next;
                next = next.getNext(0);
                return last.value;
            }
        }
    }
}
