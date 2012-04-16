package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A binary search tree (BST), which may sometimes also be called an ordered or
 * sorted binary tree, is a node-based binary tree data structure which has the
 * following properties: 1) The left subtree of a node contains only nodes with
 * keys less than the node's key. 2) The right subtree of a node contains only
 * nodes with keys greater than the node's key. 3) Both the left and right
 * subtrees must also be binary search trees.
 * 
 * http://en.wikipedia.org/wiki/Binary_search_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BinarySearchTree<T extends Comparable<T>> {

    protected static final Random RANDOM = new Random();

    protected Node<T> root = null;
    protected int size = 0;

    public static enum TYPE {
        FIRST, MIDDLE, RANDOM
    };

    public TYPE type = TYPE.FIRST;

    public BinarySearchTree() {
        // If you are not passing in an array of node, we have to use
        // TYPE==FIRST
    }

    public BinarySearchTree(TYPE type) {
        this.type = type;
    }

    public BinarySearchTree(T[] nodes) {
        // Defaulted to TYPE==FIRST
        populateTree(nodes);
    }

    public BinarySearchTree(T[] nodes, TYPE type) {
        this(type);
        populateTree(nodes);
    }

    protected void populateTree(T[] nodes) {
        int rootIndex = getRandom(nodes.length);
        T rootValue = nodes[rootIndex];
        Node<T> newNode = new Node<T>(null, rootValue);
        add(newNode, true);

        for (T node : nodes) {
            if (node != rootValue) {
                add(node);
            }
        }
    }

    public void add(T value) {
        add(new Node<T>(null, value), true);
    }

    protected void add(Node<T> newNode, boolean adjustSize) {
        // If we are adding a node or subtree back into the current tree then
        // set 'adjustSize' to false. This is done in the remove method.
        if (newNode == null) return;

        if (root == null) {
            root = newNode;
            if (adjustSize) size++;
            return;
        }

        addToSubtree(root, newNode, adjustSize);
    }

    protected void addToSubtree(Node<T> subtreeRoot, Node<T> newNode, boolean adjustSize) {
        Node<T> node = subtreeRoot;
        while (node != null) {
            if (newNode.value.compareTo(node.value) <= 0) {
                if (node.lesser == null) {
                    // New left node
                    node.lesser = newNode;
                    newNode.parent = node;
                    if (adjustSize) size++;
                    return;
                } else {
                    node = node.lesser;
                }
            } else if (node.greater == null) {
                // New right node
                node.greater = newNode;
                newNode.parent = node;
                if (adjustSize) size++;
                return;
            } else {
                node = node.greater;
            }
        }
    }

    public void addAll(T[] nodes) {
        populateTree(nodes);
    }

    public boolean contains(T value) {
        Node<T> node = getNode(value);
        return (node != null);
    }

    protected Node<T> getNode(T value) {
        Node<T> node = root;
        while (node != null) {
            if (value.compareTo(node.value) == 0) {
                return node;
            } else if (value.compareTo(node.value) < 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }
        return null;
    }

    public boolean remove(T value) {
        Node<T> nodeToRemove = root;
        while (nodeToRemove != null) {
            if (value.compareTo(nodeToRemove.value) < 0) {
                // Node to remove is less than current node
                nodeToRemove = nodeToRemove.lesser;
            } else if (value.compareTo(nodeToRemove.value) > 0) {
                // Node to remove is greater then current node
                nodeToRemove = nodeToRemove.greater;
            } else if (value.compareTo(nodeToRemove.value) == 0) {
                // We found our node or the first occurrence of our node
                Node<T> parent = nodeToRemove.parent;
                Node<T> nodeToRefactor = null;
                if (parent == null) {
                    // Replacing the root node
                    if (nodeToRemove.lesser != null) {
                        // Replace root with lesser subtree
                        root = nodeToRemove.lesser;

                        // Save greater subtree for adding back into the tree
                        // below
                        nodeToRefactor = nodeToRemove.greater;

                        // Root not should not have a parent
                        root.parent = null;
                    } else if (nodeToRemove.greater != null) {
                        // Replace root with greater subtree
                        root = nodeToRemove.greater;

                        // Root not should not have a parent
                        root.parent = null;
                    } else {
                        // No children...
                        root = null;
                    }
                } else if (parent.lesser != null && (parent.lesser.value.compareTo(nodeToRemove.value) == 0)) {
                    // If the node to remove is the parent's lesser node, replace
                    // the parent's lesser node with one of the node to remove's
                    // lesser/greater subtrees
                    Node<T> nodeToMoveUp = null;
                    if (nodeToRemove.lesser != null) {
                        // Using the less subtree
                        nodeToMoveUp = nodeToRemove.lesser;
                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;

                        // Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greater;
                    } else if (nodeToRemove.greater != null) {
                        // Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = nodeToRemove.greater;
                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else {
                        // No children...
                        parent.lesser = null;
                    }
                } else if (parent.greater != null && (parent.greater.value.compareTo(nodeToRemove.value) == 0)) {
                    // If the node to remove is the parent's greater node, replace
                    // the parent's greater node with the node's greater node
                    Node<T> nodeToMoveUp = null;
                    if (nodeToRemove.lesser != null) {
                        // Using the less subtree
                        nodeToMoveUp = nodeToRemove.lesser;
                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;

                        // Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greater;
                    } else if (nodeToRemove.greater != null) {
                        // Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = nodeToRemove.greater;
                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else {
                        // No children...
                        parent.greater = null;
                    }
                }
                if (nodeToRefactor != null) {
                    // If there is a node to refactor then add the subtree to the new root
                    nodeToRefactor.parent = null;
                    add(nodeToRefactor, false);
                }
                nodeToRemove = null;
                size--;
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean validate() {
        if (root==null) return true;
        return validateNode(root);
    }

    private boolean validateNode(Node<T> node) {
        Node<T> lesser = node.lesser;
        Node<T> greater = node.greater;

        if (lesser!=null) {
            if (node.value.compareTo(lesser.value) > 0) return validateNode(lesser);
            else return false;
        }
        if (greater!=null) {
            if (node.value.compareTo(greater.value) <= 0) return validateNode(greater);
            else return false;
        }
        
        return true;
    }

    private final int getRandom(int length) {
        if (type == TYPE.RANDOM && length > 0) return RANDOM.nextInt(length);
        if (type == TYPE.FIRST && length > 0) return 0;
        else return length / 2;
    }

    @SuppressWarnings("unchecked")
    public T[] getSorted() {
        // Depth first search to traverse the tree in order.
        List<Node<T>> added = new ArrayList<Node<T>>();
        T[] nodes = (T[]) new Comparable[size];
        int index = 0;
        Node<T> node = root;
        while (index < size && node != null) {
            Node<T> parent = node.parent;
            Node<T> lesser = (node.lesser != null && !added.contains(node.lesser)) ? node.lesser : null;
            Node<T> greater = (node.greater != null && !added.contains(node.greater)) ? node.greater : null;

            if (parent == null && lesser == null && greater == null) {
                if (!added.contains(node)) nodes[index++] = node.value;
                break;
            }

            if (lesser != null) {
                node = lesser;
            } else {
                if (!added.contains(node)) {
                    nodes[index++] = node.value;
                    added.add(node);
                }
                if (greater != null) {
                    node = greater;
                } else if (greater == null && added.contains(node)) {
                    node = parent;
                } else {
                    // We should not get here. Stop the loop!
                    node = null;
                }
            }
        }
        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return BinarySearchTree.TreePrinter.getString(this);
    }

    protected static class Node<T extends Comparable<T>> {

        protected T value = null;
        protected Node<T> parent = null;
        protected Node<T> lesser = null;
        protected Node<T> greater = null;

        protected Node(Node<T> parent, T value) {
            this.parent = parent;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + 
                   " parent=" + ((parent != null) ? parent.value : "NULL") + 
                   " lesser=" + ((lesser != null) ? lesser.value : "NULL") + 
                   " greater=" + ((greater != null) ? greater.value : "NULL");
        }
    }

    protected static class TreePrinter {

        public static <T extends Comparable<T>> String getString(BinarySearchTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>();
                if (node.lesser != null) children.add(node.lesser);
                if (node.greater != null) children.add(node.greater);
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
