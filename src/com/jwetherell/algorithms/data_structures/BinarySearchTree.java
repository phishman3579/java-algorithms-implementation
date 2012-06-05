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


    public BinarySearchTree() { }

    public boolean add(T value) {
        Node<T> nodeAdded = this.addValue(value);
        return (nodeAdded!=null);
    }

    protected Node<T> addValue(T value) {
        Node<T> nodeToAdd = new Node<T>(null, value);
        add(nodeToAdd);
        return nodeToAdd;
    }

    protected void add(Node<T> newNode) {
        if (newNode == null) return;

        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        Node<T> node = root;
        while (node != null) {
            if (newNode.value.compareTo(node.value) <= 0) {
                if (node.lesser == null) {
                    // New left node
                    node.lesser = newNode;
                    newNode.parent = node;
                    size++;
                    return;
                } else {
                    node = node.lesser;
                }
            } else {
                if (node.greater == null) {
                    // New right node
                    node.greater = newNode;
                    newNode.parent = node;
                    size++;
                    return;
                } else {
                    node = node.greater;
                }
            }
        }
    }

    public boolean contains(T value) {
        Node<T> node = getNode(value);
        return (node != null);
    }

    protected Node<T> getNode(T value) {
        Node<T> node = root;
        while (node != null && node.value!=null) {
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

    protected Node<T> getGreatest(Node<T> startingNode) {
        if (startingNode==null) return null;

        Node<T> greater = startingNode.greater;
        while (greater!=null && greater.value!=null) {
            Node<T> node = greater.greater;
            if (node!=null && node.value!=null) greater = node;
            else break;
        }
        return greater;
    }

    protected Node<T> getLeast(Node<T> startingNode) {
        if (startingNode==null) return null;

        Node<T> lesser = startingNode.lesser;
        while (lesser!=null && lesser.value!=null) {
            Node<T> node = lesser.lesser;
            if (node!=null && node.value!=null) lesser = node;
            else break;
        }
        return lesser;
    }

    public boolean remove(T value) {
        Node<T> nodeToRemove = this.removeValue(value);
        return (nodeToRemove!=null);
    }

    protected Node<T> removeValue(T value) {
        Node<T> nodeToRemoved = this.getNode(value);
        if (nodeToRemoved != null) {
            Node<T> replacementNode = this.getReplacementNode(nodeToRemoved);
            replaceNodeWithNode(nodeToRemoved,replacementNode);
        }
        return nodeToRemoved;
    }

    /*
     * nodeToRemoved cannot be NULL
     */
    protected Node<T> getReplacementNode(Node<T> nodeToRemoved) {
        Node<T> replacement = null;
        Node<T> parent = nodeToRemoved.parent;
        if (parent == null) {
            // Replacing the root node
            if (nodeToRemoved.lesser != null && nodeToRemoved.greater == null) {
                // Replace with lesser subtree
                replacement = nodeToRemoved.lesser;
            } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser==null) {
                // Replace with greater subtree
                replacement = nodeToRemoved.greater;
            } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser!=null) {
                //Two children
                replacement = this.getLeast(nodeToRemoved.greater);
                if (replacement==null) replacement = nodeToRemoved.greater;
            }
        } else if (parent.lesser != null && (parent.lesser.value.compareTo(nodeToRemoved.value) == 0)) {
            // If the node to remove is the parent's lesser node, replace
            // the parent's lesser node with one of the node to remove's
            // lesser/greater subtrees
            if (nodeToRemoved.lesser != null && nodeToRemoved.greater == null) {
                // Using the less subtree
                replacement = nodeToRemoved.lesser;
            } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser == null) {
                // Using the greater subtree (there is no lesser subtree, no refactoring)
                replacement = nodeToRemoved.greater;
            } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser!=null) {
                //Two children
                replacement = this.getLeast(nodeToRemoved.greater);
                if (replacement==null) replacement = nodeToRemoved.greater;
            }
        } else if (parent.greater != null && (parent.greater.value.compareTo(nodeToRemoved.value) == 0)) {
            // If the node to remove is the parent's greater node, replace
            // the parent's greater node with the node's greater node
            if (nodeToRemoved.lesser != null && nodeToRemoved.greater == null) {
                // Using the less subtree
                replacement = nodeToRemoved.lesser;
            } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser == null) {
                // Using the greater subtree (there is no lesser subtree, no refactoring)
                replacement = nodeToRemoved.greater;
            } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser!=null) {
                //Two children
                replacement = this.getGreatest(nodeToRemoved.lesser);
                if (replacement==null) replacement = nodeToRemoved.lesser;
            }
        }
        return replacement;
    }

    /*
     * nodeToRemoved cannot be NULL but replacementNode can.
     */
    protected void replaceNodeWithNode(Node<T> nodeToRemoved, Node<T> replacementNode) {
        if (replacementNode!=null) {
            //Save for later
            Node<T> replacementNodeLesser = replacementNode.lesser;
            Node<T> replacementNodeGreater = replacementNode.greater;

            //Replace replacementNode's branches with nodeToRemove's branches
            Node<T> nodeToRemoveLesser = nodeToRemoved.lesser;
            if (nodeToRemoveLesser!=null && !nodeToRemoveLesser.equals(replacementNode)) {
                replacementNode.lesser = nodeToRemoveLesser;
                nodeToRemoveLesser.parent = replacementNode;
            }
            Node<T> nodeToRemoveGreater = nodeToRemoved.greater;
            if (nodeToRemoveGreater!=null && !nodeToRemoveGreater.equals(replacementNode)) {
                replacementNode.greater = nodeToRemoveGreater;
                nodeToRemoveGreater.parent = replacementNode;
            }

            //Remove link from replacementNode's parent to replacement
            Node<T> replacementParent = replacementNode.parent;
            if (replacementParent!=null && !replacementParent.equals(nodeToRemoved)) {
                Node<T> replacementParentLesser = replacementParent.lesser;
                Node<T> replacementParentGreater = replacementParent.greater;
                if (replacementParentLesser!=null && replacementParentLesser.equals(replacementNode)) {
                    replacementParent.lesser = replacementNodeGreater;
                    if (replacementNodeGreater!=null) replacementNodeGreater.parent = replacementParent;
                } else if (replacementParentGreater!=null && replacementParentGreater.equals(replacementNode)) {
                    replacementParent.greater = replacementNodeLesser;
                    if (replacementNodeLesser!=null) replacementNodeLesser.parent = replacementParent;
                }
            }
        }

        //Update the link in the tree from the nodeToRemoved to the replacementNode
        Node<T> parent = nodeToRemoved.parent;
        if (parent == null) {
            // Replacing the root node
            if (replacementNode!=null) {
                root = replacementNode;
                root.parent = null;
            } else {
                // No children...
                root = null;
            }
        } else if (parent.lesser != null && (parent.lesser.value.compareTo(nodeToRemoved.value) == 0)) {
            if (replacementNode!=null) {
                parent.lesser = replacementNode;
                replacementNode.parent = parent;
            } else {
                // No children...
                parent.lesser = null;
            }
        } else if (parent.greater != null && (parent.greater.value.compareTo(nodeToRemoved.value) == 0)) {
            if (replacementNode!=null) {
                parent.greater = replacementNode;
                replacementNode.parent = parent;
            } else {
                // No children...
                parent.greater = null;
            }
        }
        size--;
    }

    public int size() {
        return size;
    }

    public boolean validate() {
        if (root==null) return true;
        return validateNode(root);
    }

    protected boolean validateNode(Node<T> node) {
        Node<T> lesser = node.lesser;
        Node<T> greater = node.greater;

        boolean lesserCheck = true;
        if (lesser!=null && lesser.value!=null) {
            lesserCheck = (lesser.value.compareTo(node.value) <= 0);
            if (lesserCheck) lesserCheck = validateNode(lesser);
        }
        if (!lesserCheck) return false;

        boolean greaterCheck = true;
        if (greater!=null && greater.value!=null) {
            greaterCheck = (greater.value.compareTo(node.value) > 0);
            if (greaterCheck) greaterCheck = validateNode(greater);
        }
        return greaterCheck;
    }

    @SuppressWarnings("unchecked")
    public T[] getSorted() {
        // Depth first search to traverse the tree in order.
        List<Node<T>> added = new ArrayList<Node<T>>(2);
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
        return TreePrinter.getString(this);
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

            if (node.parent!=null) {
                String side = "left";
                if (node.equals(node.parent.greater)) side = "right";
                builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + side + ") " + node.value + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
            }
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>(2);
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
