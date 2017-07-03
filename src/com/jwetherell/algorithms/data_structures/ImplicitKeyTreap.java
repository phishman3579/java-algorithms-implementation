package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jwetherell.algorithms.data_structures.interfaces.IList;

/**
 * A Treap is a self-balancing binary search tree that uses randomization to maintain 
 * a low height. In this version, it is used emulate the operations of an array and linked list.
 * <p>
 * Time Complexity: Assuming the join/merge functions have constant complexity.
 * add(value), add(index,value), remove(index), set(index,value), get(index) all have O(log N).
 * remove(value), get(value), contains(value) all have O(N).
 * <p>
 * Space Complexity: O(N)
 * <p>
 * Note: This implementation is 0-based, meaning that all
 * indices from 0 to size() - 1, inclusive, are accessible.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Treap">Treap (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class ImplicitKeyTreap<T> implements IList<T> {

    private static final Random RANDOM = new Random();

    private static int randomSeed = Integer.MAX_VALUE; // This should be at least twice the number of Nodes

    protected Node<T> root = null;
    protected int size = 0;

    /**
     * Default constructor.
     */
    public ImplicitKeyTreap() { }

    /**
     * Constructor with a random seed.
     * 
     * @param randomSeed to use.
     */
    public ImplicitKeyTreap(int randomSeed) {
        this();
        ImplicitKeyTreap.randomSeed = randomSeed;
    }

    /**
     * {@inheritDoc}
     * 
     * Note: adds to the end of the treap (rightmost node)
     */
    @Override
    public boolean add(T value) {
        final T t = add(size, value);
        return (t!=null);
    }

    /**
     * Insert value at index
     * 
     * @param index to insert value
     * @param value to insert
     */
    public T add(int index, T value) {
        addAtIndexAndUpdate(index, value);

        // Check to make sure value was added
        final Node<T> n = getNodeByIndex(index);
        if (n == null)
            return null;
        return n.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        // See if value already exists
        final int idx = getIndexByValue(value);
        if (idx < 0)
            return false;

        final T t = removeAtIndex(idx);
        return (t!=null);
    }

    /**
     * Remove value at index
     * 
     * @param index to remove value
     * @return value or null if not found
     */
    public T removeAtIndex(int index) {
        // See if index already exists
        Node<T> n = getNodeByIndex(index);
        if (n == null)
            return null;

        removeAtIndexAndUpdate(index);
        return n.value;
    }

    /**
     * Set value at index
     * 
     * @param index to remove value
     * @return value or null if not found
     */
    public T set(int index, T value) {
        // See if index already exists
        final Node<T> n = getNodeByIndex(index);
        if (n == null)
            return null;

        n.value = value;
        return n.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        final Node<T> n = getNodeByValue(value);
        return (n!=null);
    }

    /**
     * Get value at index
     * 
     * @param index to remove value
     */
    public T getAtIndex(int index) {
        final Node<T> n = getNodeByIndex(index);
        if (n == null)
            return null;
        return n.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
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
        if (root == null) 
            return true;
        return validateNode(root);
    }

    private boolean validateNode(Node<T> node) {
        final Node<T> left = node.left;
        final Node<T> right = node.right;

        if (left != null) {
            if (node.priority < left.priority)
                return validateNode(left);
            return false;
        }
        if (right != null) {
            if (node.priority < right.priority)
                return validateNode(right);
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> toList() {
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
     * Split the treap at index
     * 
     * @param index to split at
     * 
     * @return Pair which contains root of both trees
     */
    public Pair<T> split(int index) {
        final Pair<T> p = split((Node<T>)root, index);
        if (p.left != null)
            p.left.parent = null;
        if (p.right != null)
            p.right.parent = null;
        return p;
    }

    public void addAtIndexAndUpdate(int index, T value) {
        root = insert(((Node<T>)root), index, value);
        if (root == null)
            size = 0;
        else
            size = (((Node<T>)root).size);
    }

    private void removeAtIndexAndUpdate(int index) {
        root = remove(((Node<T>)root), index);
        if (root == null)
            size = 0;
        else
            size = (((Node<T>)root).size);
    }

    private Node<T> getNodeByValue(T value) {
        return getNodeByValue(root,value);
    }

    private Node<T> getNodeByIndex(int index) {
        if (root == null)
            return null;

        final Node<T> l = (Node<T>)root.left;
        final Node<T> r = (Node<T>)root.right;
        final int leftSize = ((l!=null)?l.size:0);
        final int idx = leftSize;

        if (idx == index) {
            return root;
        } else if (index < leftSize) {
            return getNodeByIndex(l, idx, index);
        } else {
            return getNodeByIndex(r, idx, index);
        }
    }

    private int getIndexByValue(T value) {
        final Node<T> node = (Node<T>)root;
        if (value == null || node == null)
            return Integer.MIN_VALUE;

        final Node<T> l = (Node<T>)node.left;
        final Node<T> r = (Node<T>)node.right;
        final int leftSize = ((l!=null)?l.size:0);
        final int idx = leftSize;

        if (value.equals(node.value)) 
            return idx;

        int i = getIndexByValue(l, idx, value);
        if (i >= 0)
            return i;

        i = getIndexByValue(r, idx, value);
        return i;
    }

    /**
     * Split the treap rooted at node at given index
     * 
     * @param node which represents root
     * @param index in treap to split
     * 
     * @return Pair which contains root of both trees
     */
    public static <T> Pair<T> split(Node<T> node, int index) {
        if (node == null)
            return new Pair<T>(null, null);

        final int leftSize = (node.left!=null?
                                  ((Node<T>)node.left).size
                              :
                                  0);
        if (index <= leftSize) {
            final Pair<T> sub = split((Node<T>)node.left, index);
            node.left = sub.right;
            if (node.left != null)
                node.left.parent = node;
            sub.right = node;
            node.update();
            return sub;
        }
        // else
        final Pair<T> sub = split((Node<T>)node.right, (index - leftSize - 1));
        node.right = sub.left;
        if (node.right != null)
            node.right.parent = node;
        sub.left = node;
        node.update();
        return sub;
    }

    /**
     * Merge treaps from given left and right nodes
     * 
     * @param left node which represents root of left treap
     * @param right node which represents root of great treap
     * 
     * @return treap from merged treaps
     */
    public static <T> Node<T> merge(Node<T> left, Node<T> right) {
        if (left == null)
            return right;

        if (right == null)
            return left;

        if (left.priority < right.priority) {
            left.right = merge((Node<T>)left.right, right);
            if (left.right != null) 
                left.right.parent = left;
            left.update();
            return left;
        }
        // else
        right.left = merge(left, (Node<T>)right.left);
        if (right.left != null) 
            right.left.parent = right;
        right.update();
        return right;
    }

    private static <T> Node<T> insert(Node<T> root, int index, T value) {
        final Pair<T> p = split(root, index);
        return merge(merge((Node<T>)p.left, new Node<T>(value)), (Node<T>)p.right);
    }

    private static <T> Node<T> remove(Node<T> root, int index) {
        final Pair<T> p = split(root, index);
        final int leftSize = (p.left!=null?
                                      ((Node<T>)p.left).size
                                  :
                                      0);
        return merge(p.left, (split(p.right, (index + 1 - leftSize))).right);
    }

    private static <T> Node<T> getNodeByValue(Node<T> node, T value) {
        if (node == null)
            return null;

        if (node.value.equals(value))
            return node;

        Node<T> n = getNodeByValue(node.left, value);
        if (n == null)
            n = getNodeByValue(node.right, value);
        return n;
    }

    private static <T> Node<T> getNodeByIndex(Node<T> node, int parentIndex, int index) {
        if (node == null)
            return null;

        final Node<T> p = (Node<T>)node.parent;
        final Node<T> l = (Node<T>)node.left;
        final Node<T> r = (Node<T>)node.right;
        final int leftSize = ((l!=null)?l.size:0);
        final int rightSize = ((r!=null)?r.size:0);

        int idx = Integer.MIN_VALUE;
        if (p!=null && node.equals(p.left)) {
            // left
            idx = parentIndex - rightSize - 1;
        } else if (p!=null && node.equals(p.right)) {
            // right
            idx = leftSize + parentIndex + 1;
        } else {
            throw new RuntimeException("I do not have a parent :-(");
        }

        if (idx == index)
            return node;

        if (index <= idx) {
            return getNodeByIndex(l, idx, index);
        } else {
            return getNodeByIndex(r, idx, index);
        }
    }

    private static <T> int getIndexByValue(Node<T> node, int parentIndex, T value) {
        if (node == null)
            return Integer.MIN_VALUE;

        final Node<T> p = (Node<T>)node.parent;
        final Node<T> l = (Node<T>)node.left;
        final Node<T> r = (Node<T>)node.right;
        final int leftSize = ((l!=null)?l.size:0);
        final int rightSize = ((r!=null)?r.size:0);

        int idx = Integer.MIN_VALUE;
        if (p!=null && node.equals(p.left)) {
            // left
            idx = parentIndex - rightSize - 1;
        } else if (p!=null && node.equals(p.right)) {
            // right
            idx = leftSize + parentIndex + 1;
        } else {
            throw new RuntimeException("I do not have a parent :-(");
        }

        if (value.equals(node.value)) 
            return idx;

        int i = getIndexByValue(l, idx, value);
        if (i >= 0)
            return i;

        i = getIndexByValue(r, idx, value);
        return i;
    }

    public T[] inOrder() {
        return inOrder(root,size);
    }

    public static <T> T[] inOrder(Node<T> node, int size) {
        T[] data = (T[]) new Object[size];
        if (node == null)
            return data;

        inOrder(node, data, 0);
        return data;
    }

    private static <T> int inOrder(Node<T> node, T[] data, int idx) {
        if (node == null)
            return idx;

        idx = inOrder(node.left, data, idx);
        data[idx++] = node.value;
        idx = inOrder(node.right, data, idx);
        return idx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    public static class Node<T> {

        private T value = null;
        private int priority;
        private int size;
        private Node<T> parent = null;
        private Node<T> left = null;
        private Node<T> right = null;

        private Node(T id) {
            this(null, id);
        }

        private Node(Node<T> parent, T id) {
            this.parent = parent;
            this.value = id;
            this.size = 1;
            this.priority = RANDOM.nextInt(randomSeed);
        }

        public int getSize() {
            return size;
        }

        private void update() {
            size = 1 + (left!=null?
                                    ((Node<T>)left).size
                                :   
                                    0) 
                       + (right!=null?
                                   ((Node<T>)right).size
                               :
                                   0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "id=" + value + " parent=" + ((parent != null) ? parent.value : "NULL") + " left="
                    + ((left != null) ? left.value : "NULL") + " right=" + ((right != null) ? right.value : "NULL");
        }
    }

    public static class Pair<T>  {

        private Node<T> left;
        private Node<T> right;

        private Pair(Node<T> left, Node<T> right) {
            this.left = left;
            this.right = right;
        }

        public Node<T> getLesser() {
            return left;
        }

        public Node<T> getGreater() {
            return right;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "left={"+left.toString()+"} right={"+right.toString()+"}";
        }
    }

    public static class JavaCompatibleArrayList<T> extends java.util.AbstractList<T> implements java.util.RandomAccess {

        private ImplicitKeyTreap<T> list = null;

        public JavaCompatibleArrayList(ImplicitKeyTreap<T> list) {
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
            return list.removeAtIndex(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T get(int index) {
            T t = list.getAtIndex(index);
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

    protected static class TreePrinter {

        public static <T> String getString(ImplicitKeyTreap<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent != null) {
                String side = "left";
                if (node.equals(node.parent.right))
                    side = "right";
                builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + side + ") " + node.value + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
            }
            List<Node<T>> children = null;
            if (node.left != null || node.right != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.left != null)
                    children.add(node.left);
                if (node.right != null)
                    children.add(node.right);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}

