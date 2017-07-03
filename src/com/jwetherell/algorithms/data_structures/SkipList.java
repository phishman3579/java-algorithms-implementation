package com.jwetherell.algorithms.data_structures;

import java.util.Random;

import com.jwetherell.algorithms.data_structures.interfaces.ISet;

/**
 * Skip List. A skip list is a data structure for storing a sorted list of items
 * using a hierarchy of linked lists that connect increasingly sparse
 * subsequences of the items. These auxiliary lists allow item lookup with
 * efficiency comparable to balanced binary search trees.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Skip_list">Skip List (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<T>> implements ISet<T> {

    private static final Random seedGenerator = new Random();

    // If you change this number, you also need to change the random function.
    private static final int MAX = 31;

    private INodeCreator<T> creator = null;
    private int randomSeed = -1;
    private int size = 0;

    protected Node<T> head = null;

    public SkipList() {
        randomSeed = seedGenerator.nextInt() | 0x0100;
    }

    public SkipList(INodeCreator<T> creator) {
        this();
        this.creator = creator;
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

    protected Node<T> addValue(T value) {
        Node<T> toReturn = null;      
        if (head==null) {
            // new list
            Node<T> node = null; 
            if (creator==null) node = new Node<T>(MAX,value);
            else node = creator.createNewNode(MAX, value);
            head = node;
            toReturn = node;
        } else {
            int level = getRandom();
            Node<T> node = null; 
            if (creator==null) node = new Node<T>(level,value);
            else node = creator.createNewNode(level, value);

            Node<T> prev = head;
            if (head.data.compareTo(value)>0) {
                // handle case where head is greater than new node, just swap values
                //T oldHeadValue = head.data;
                //head.data = value;
                // Swap the old head value into the new node
                //node.data = oldHeadValue;
                if (creator==null) swapNode(node,head);
                else creator.swapNode(node, head);
                toReturn = head;
            } else {
                toReturn = node;
            }

            // Start from the top and work down to update the pointers
            for (int i=MAX; i>=0; i--) {
                Node<T> next = prev.getNext(i);
                while (next!=null) {
                    if (next.data.compareTo(value)>0) break;
                    prev = next;
                    // It's important to set next since the node we are looking for
                    // on the next level cannot be behind this "prev" node.
                    next = prev.getNext(i);
                }
                if (i <= level) {
                    // If we are on a level where the new node exists, update the linked list
                    node.setNext(i, next);
                    prev.setNext(i, node);
                }
            }
        }
        size++;
        return toReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        Node<T> node = addValue(value);
        return (node!=null);
    }

    private NodeLevelPair<T> getPredecessor(T value) {
        Node<T> node = head;
        if (node==null) return null; 
        if (node.data.compareTo(value)==0) return null;

        // Current node is not the node we are looking for; Keep moving down
        // until you find a node with a non-null "next" pointer.
        int level = node.getLevel();
        Node<T> next = node.getNext(level);
        while (next==null) {
            // If next is null, move down
            if (level>0) next = node.getNext(--level);
            else break;
        }

        // Found a node with a next node OR I reached the bottom level
        while (next!=null) {
            int comp = next.data.compareTo(value);
            if (comp==0) {
                // Found the node who's next node is the node we are looking for!
                NodeLevelPair<T> pair = new NodeLevelPair<T>(level,node);
                return pair;
            } else if (comp>=1) {
                // Found a node that's greater, move down a level
                if (level>0) level--;
                else return null;

                // Update the next pointer
                next = node.getNext(level);
            } else {
                // Next is less then the value we are looking for, keep moving to next.
                node = next;
                next = node.getNext(level);
                while (next==null && level>0) {
                    next = node.getNext(--level);
                }
            }
        }
        return null;
    }

    protected Node<T> getNode(T value) {
        if (head==null) return null; 
        if (head.data.compareTo(value)==0) return head;

        NodeLevelPair<T> pair = getPredecessor(value);
        if (pair==null) return null;
        return pair.node.getNext(pair.level);
    }

    protected void swapNode(Node<T> node, Node<T> next) {
        T value = node.data;
        node.data = next.data;
        next.data = value;
    }

    protected Node<T> removeValue(T value) {
        if (head==null) return null;

        Node<T> node = null;
        Node<T> prev = null;
        int lvl = 0;
        if (head.data.compareTo(value)==0) {
            node = head;
        } else {
            // Find the predecessor of the node we are looking for and
            // which level it is found on.
            NodeLevelPair<T> pair = getPredecessor(value);
            if (pair!=null) {
                prev = pair.node;
                lvl = pair.level;
            }
            // Cannot find predecessor of value
            if (prev == null)
                return null;
            // Use predecessor to get actual node
            node = prev.getNext(lvl);
            // Node doesn't exist
            if (node == null || node.data.compareTo(value)!=0) 
                return null;
        }

        Node<T> next = null;
        // Head node is the only node without a prev node
        if (prev == null) {
            next = node.getNext(0);
            // Removing head
            if (next != null) {
                // Switch the value of the next into the head node
                if (creator==null) swapNode(node,next);
                else creator.swapNode(node, next);
                // Update the prev and node pointer
                prev = node;
                node = next;
            } else {
                // If head doesn't have a new node then list is empty
                head = null;
            }
        } else {
            // Set the next node pointer
            next = node.getNext(lvl);
        }

        // Start from the top level and move down removing the node
        int level = node.getLevel();
        for (int i=level; i>=0; i--) {
            next = node.getNext(i);
            if (prev!=null) {
                prev.setNext(i, next);
                if (i > 0) {
                    // Move down a level and look for the 'next' previous node
                    Node<T> temp = prev.getNext(i - 1);
                    while (temp != null && temp.data.compareTo(value) != 0) {
                        prev = temp;
                        temp = temp.getNext(i - 1);
                    }
                } 
            }
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
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        return (getNode(value)!=null);
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
        if (head==null) return true;

        int level = MAX;
        for (int i=level; i>=0; i--) {
            Node<T> prev = head;
            Node<T> node = prev.getNext(i);
            while (node != null) {
                // The list should be ordered
                if (node.data.compareTo(prev.data) < 1)
                    return false;
                prev = node;
                node = prev.getNext(i);
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
                    if (level==i && value!=null && node.data.compareTo(value)==0) 
                        builder.append("(").append(node.data).append(")");
                    else 
                        builder.append(node.data);
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

    protected static class Node<T extends Comparable<T>> {

        private Node<T>[] next = null;

        protected T data = null;

        protected Node(int level, T data) {
            this.next = new Node[level+1];
            this.data = data;
        }

        protected int getLevel() {
            return next.length-1;
        }

        protected void setNext(int idx, Node<T> node) {
            this.next[idx] = node;
        }
        protected Node<T> getNext(int idx) {
            if (idx>=this.next.length) return null;
            return this.next[idx];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("data=").append(data);
            if (next!=null) {
                builder.append("\n").append("next=[");
                int size = next.length;
                for (int i=0; i<size; i++) {
                    Node<T> n = next[i];
                    if (n!=null) builder.append(n.data);
                    else builder.append("none");
                    if (i!=size-1) builder.append(", ");
                }
                builder.append("]");
            }
            return builder.toString();
        }
    }

    protected static interface INodeCreator<T extends Comparable<T>> {

        /**
         * Create a new Node with the following parameters.
         * 
         * @param level of this node.
         * @param data of this node.
         * @return new Node
         */
        public Node<T> createNewNode(int level, T data);

        /**
         * Swap the two nodes internals
         * 
         * @param node to swap.
         * @param next to swap.
         */
        public void swapNode(Node<T> node, Node<T> next);
    }

    private static final class NodeLevelPair<T extends Comparable<T>> {
        private int level = -1;
        private Node<T> node = null;
        private NodeLevelPair(int level, Node<T> node) {
            this.level = level;
            this.node = node;
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
                list.remove(last.data);
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
                return last.data;
            }
        }
    }
}
