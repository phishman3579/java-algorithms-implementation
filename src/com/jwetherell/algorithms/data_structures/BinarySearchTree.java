package com.jwetherell.algorithms.data_structures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Queue;
import java.util.Set;


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

    private int modifications = 0;

    protected static final Random RANDOM = new Random();

    protected enum Position {
        LEFT, RIGHT
    };

    protected Node<T> root = null;
    protected int size = 0;
    protected INodeCreator<T> creator = null;

    public enum DepthFirstSearchOrder {
        inOrder, preOrder, postOrder
    };

    /**
     * Default constructor.
     */
    public BinarySearchTree() {
    }

    /**
     * Constructor with external Node creator.
     */
    public BinarySearchTree(INodeCreator<T> creator) {
        this.creator = creator;
    }

    /**
     * Add value to the tree. Tree can contain multiple equal values.
     * 
     * @param value
     *            T to add to the tree.
     * @return True if successfully added to tree.
     */
    public boolean add(T value) {
        Node<T> nodeAdded = this.addValue(value);
        return (nodeAdded != null);
    }

    /**
     * Add value to the tree and return the Node that was added. Tree can
     * contain multiple equal values.
     * 
     * @param value
     *            T to add to the tree.
     * @return Node<T> which was added to the tree.
     */
    protected Node<T> addValue(T value) {
        Node<T> newNode = null;
        if (this.creator == null) newNode = new Node<T>(null, value);
        else newNode = this.creator.createNewNode(null, value);

        // If root is null, assign
        if (root == null) {
            root = newNode;
            size++;
            return newNode;
        }

        Node<T> node = root;
        while (node != null) {
            if (newNode.id.compareTo(node.id) <= 0) {
                // Less than or equal to goes left
                if (node.lesser == null) {
                    // New left node
                    node.lesser = newNode;
                    newNode.parent = node;
                    size++;
                    return newNode;
                } else {
                    node = node.lesser;
                }
            } else {
                // Greater than goes right
                if (node.greater == null) {
                    // New right node
                    node.greater = newNode;
                    newNode.parent = node;
                    size++;
                    return newNode;
                } else {
                    node = node.greater;
                }
            }
        }

        return newNode;
    }

    /**
     * Does the tree contain the value.
     * 
     * @param value
     *            T to locate in the tree.
     * @return True if tree contains value.
     */
    public boolean contains(T value) {
        Node<T> node = getNode(value);
        return (node != null);
    }

    /**
     * Locate T in the tree.
     * 
     * @param value
     *            T to locate in the tree.
     * @return Node<T> representing first reference of value in tree or NULL if
     *         not found.
     */
    protected Node<T> getNode(T value) {
        Node<T> node = root;
        while (node != null && node.id != null) {
            if (value.compareTo(node.id) == 0) {
                return node;
            } else if (value.compareTo(node.id) < 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }
        return null;
    }

    /**
     * Rotate tree left at sub-tree rooted at node.
     * 
     * @param node
     *            Root of tree to rotate left.
     */
    protected void rotateLeft(Node<T> node) {
        Position parentPosition = null;
        Node<T> parent = node.parent;
        if (parent != null) {
            if (node.equals(parent.lesser)) {
                // Lesser
                parentPosition = Position.LEFT;
            } else {
                // Greater
                parentPosition = Position.RIGHT;
            }
        }

        Node<T> greater = node.greater;
        node.greater = null;
        Node<T> lesser = greater.lesser;

        greater.lesser = node;
        node.parent = greater;

        node.greater = lesser;
        if (lesser != null) lesser.parent = node;

        if (parentPosition != null) {
            if (parentPosition == Position.LEFT) {
                parent.lesser = greater;
            } else {
                parent.greater = greater;
            }
            greater.parent = parent;
        } else {
            root = greater;
            greater.parent = null;
        }
    }

    /**
     * Rotate tree right at sub-tree rooted at node.
     * 
     * @param node
     *            Root of tree to rotate left.
     */
    protected void rotateRight(Node<T> node) {
        Position parentPosition = null;
        Node<T> parent = node.parent;
        if (parent != null) {
            if (node.equals(parent.lesser)) {
                // Lesser
                parentPosition = Position.LEFT;
            } else {
                // Greater
                parentPosition = Position.RIGHT;
            }
        }

        Node<T> lesser = node.lesser;
        node.lesser = null;
        Node<T> greater = lesser.greater;

        lesser.greater = node;
        node.parent = lesser;

        node.lesser = greater;
        if (greater != null) greater.parent = node;

        if (parentPosition != null) {
            if (parentPosition == Position.LEFT) {
                parent.lesser = lesser;
            } else {
                parent.greater = lesser;
            }
            lesser.parent = parent;
        } else {
            root = lesser;
            lesser.parent = null;
        }
    }

    /**
     * Get greatest node in sub-tree rooted at startingNode. The search does not
     * include startingNode in it's results.
     * 
     * @param startingNode
     *            Root of tree to search.
     * @return Node<T> which represents the greatest node in the startingNode
     *         sub-tree or NULL if startingNode has no greater children.
     */
    protected Node<T> getGreatest(Node<T> startingNode) {
        if (startingNode == null) return null;

        Node<T> greater = startingNode.greater;
        while (greater != null && greater.id != null) {
            Node<T> node = greater.greater;
            if (node != null && node.id != null) greater = node;
            else break;
        }
        return greater;
    }

    /**
     * Get least node in sub-tree rooted at startingNode. The search does not
     * include startingNode in it's results.
     * 
     * @param startingNode
     *            Root of tree to search.
     * @return Node<T> which represents the least node in the startingNode
     *         sub-tree or NULL if startingNode has no lesser children.
     */
    protected Node<T> getLeast(Node<T> startingNode) {
        if (startingNode == null) return null;

        Node<T> lesser = startingNode.lesser;
        while (lesser != null && lesser.id != null) {
            Node<T> node = lesser.lesser;
            if (node != null && node.id != null) lesser = node;
            else break;
        }
        return lesser;
    }

    /**
     * Remove first occurrence of value in the tree.
     * 
     * @param value
     *            T to remove from the tree.
     * @return True if value was removed from the tree.
     */
    public boolean remove(T value) {
        Node<T> nodeToRemove = this.removeValue(value);
        return (nodeToRemove != null);
    }

    /**
     * Remove first occurrence of value in the tree.
     * 
     * @param value
     *            T to remove from the tree.
     * @return Node<T> which was removed from the tree.
     */
    protected Node<T> removeValue(T value) {
        Node<T> nodeToRemoved = this.getNode(value);
        if (nodeToRemoved != null) {
            Node<T> replacementNode = this.getReplacementNode(nodeToRemoved);
            replaceNodeWithNode(nodeToRemoved, replacementNode);
        }
        return nodeToRemoved;
    }

    /**
     * Get the proper replacement node according to the binary search tree
     * algorithm from the tree.
     * 
     * @param nodeToRemoved
     *            Node<T> to find a replacement for.
     * @return Node<T> which can be used to replace nodeToRemoved. nodeToRemoved
     *         should NOT be NULL.
     */
    protected Node<T> getReplacementNode(Node<T> nodeToRemoved) {
        Node<T> replacement = null;
        if (nodeToRemoved.lesser != null && nodeToRemoved.greater == null) {
            // Using the less subtree
            replacement = nodeToRemoved.lesser;
        } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser == null) {
            // Using the greater subtree (there is no lesser subtree, no
            // refactoring)
            replacement = nodeToRemoved.greater;
        } else if (nodeToRemoved.greater != null && nodeToRemoved.lesser != null) {
            // Two children
            // Add some randomness to deletions, so we don't always use the
            // greatest/least on deletion
            if (modifications % 2 != 0) {
                replacement = this.getGreatest(nodeToRemoved.lesser);
                if (replacement == null) replacement = nodeToRemoved.lesser;
            } else {
                replacement = this.getLeast(nodeToRemoved.greater);
                if (replacement == null) replacement = nodeToRemoved.greater;
            }
            modifications++;
        }
        return replacement;
    }

    /**
     * Replace nodeToRemoved with replacementNode in the tree.
     * 
     * @param nodeToRemoved
     *            Node<T> to remove replace in the tree. nodeToRemoved should
     *            NOT be NULL.
     * @param replacementNode
     *            Node<T> to replace nodeToRemoved in the tree. replacementNode
     *            can be NULL.
     */
    protected void replaceNodeWithNode(Node<T> nodeToRemoved, Node<T> replacementNode) {
        if (replacementNode != null) {
            // Save for later
            Node<T> replacementNodeLesser = replacementNode.lesser;
            Node<T> replacementNodeGreater = replacementNode.greater;

            // Replace replacementNode's branches with nodeToRemove's branches
            Node<T> nodeToRemoveLesser = nodeToRemoved.lesser;
            if (nodeToRemoveLesser != null && !nodeToRemoveLesser.equals(replacementNode)) {
                replacementNode.lesser = nodeToRemoveLesser;
                nodeToRemoveLesser.parent = replacementNode;
            }
            Node<T> nodeToRemoveGreater = nodeToRemoved.greater;
            if (nodeToRemoveGreater != null && !nodeToRemoveGreater.equals(replacementNode)) {
                replacementNode.greater = nodeToRemoveGreater;
                nodeToRemoveGreater.parent = replacementNode;
            }

            // Remove link from replacementNode's parent to replacement
            Node<T> replacementParent = replacementNode.parent;
            if (replacementParent != null && !replacementParent.equals(nodeToRemoved)) {
                Node<T> replacementParentLesser = replacementParent.lesser;
                Node<T> replacementParentGreater = replacementParent.greater;
                if (replacementParentLesser != null && replacementParentLesser.equals(replacementNode)) {
                    replacementParent.lesser = replacementNodeGreater;
                    if (replacementNodeGreater != null) replacementNodeGreater.parent = replacementParent;
                } else if (replacementParentGreater != null && replacementParentGreater.equals(replacementNode)) {
                    replacementParent.greater = replacementNodeLesser;
                    if (replacementNodeLesser != null) replacementNodeLesser.parent = replacementParent;
                }
            }
        }

        // Update the link in the tree from the nodeToRemoved to the
        // replacementNode
        Node<T> parent = nodeToRemoved.parent;
        if (parent == null) {
            // Replacing the root node
            root = replacementNode;
            if (root != null) root.parent = null;
        } else if (parent.lesser != null && (parent.lesser.id.compareTo(nodeToRemoved.id) == 0)) {
            parent.lesser = replacementNode;
            if (replacementNode != null) replacementNode.parent = parent;
        } else if (parent.greater != null && (parent.greater.id.compareTo(nodeToRemoved.id) == 0)) {
            parent.greater = replacementNode;
            if (replacementNode != null) replacementNode.parent = parent;
        }
        size--;
    }

    /**
     * Get number of nodes in the tree.
     * 
     * @return Number of nodes in the tree.
     */
    public int size() {
        return size;
    }

    /**
     * Validate the tree for all Binary Search Tree invariants.
     * 
     * @return True if tree is valid.
     */
    public boolean validate() {
        if (root == null) return true;
        return validateNode(root);
    }

    /**
     * Validate the node for all Binary Search Tree invariants.
     * 
     * @param node
     *            Node<T> to validate in the tree. node should NOT be NULL.
     * @return True if the node is valid.
     */
    protected boolean validateNode(Node<T> node) {
        Node<T> lesser = node.lesser;
        Node<T> greater = node.greater;

        boolean lesserCheck = true;
        if (lesser != null && lesser.id != null) {
            lesserCheck = (lesser.id.compareTo(node.id) <= 0);
            if (lesserCheck) lesserCheck = validateNode(lesser);
        }
        if (!lesserCheck) return false;

        boolean greaterCheck = true;
        if (greater != null && greater.id != null) {
            greaterCheck = (greater.id.compareTo(node.id) > 0);
            if (greaterCheck) greaterCheck = validateNode(greater);
        }
        return greaterCheck;
    }

    /**
     * Get an array representation of the tree in breath first search order.
     * 
     * @return breath first search sorted array representing the tree.
     */
    @SuppressWarnings("unchecked")
    public T[] getBFS() {
        Queue<Node<T>> queue = new ArrayDeque<Node<T>>();
        T[] values = (T[]) new Comparable[size];
        int count = 0;
        Node<T> node = root;
        while (node != null) {
            values[count++] = node.id;
            if (node.lesser != null) queue.add(node.lesser);
            if (node.greater != null) queue.add(node.greater);
            if (!queue.isEmpty()) node = queue.remove();
            else node = null;
        }
        return values;
    }

    /**
     * Get an array representation of the tree in level order.
     * 
     * @return level order sorted array representing the tree.
     */
    public T[] getLevelOrder() {
        return getBFS();
    }

    /**
     * Get an array representation of the tree in-order.
     * 
     * @return in-order sorted array representing the tree.
     */
    @SuppressWarnings("unchecked")
    public T[] getDFS(DepthFirstSearchOrder order) {
        Set<Node<T>> added = new HashSet<Node<T>>(2);
        T[] nodes = (T[]) new Comparable[size];
        int index = 0;
        Node<T> node = root;
        while (index < size && node != null) {
            Node<T> parent = node.parent;
            Node<T> lesser = (node.lesser != null && !added.contains(node.lesser)) ? node.lesser : null;
            Node<T> greater = (node.greater != null && !added.contains(node.greater)) ? node.greater : null;

            if (parent == null && lesser == null && greater == null) {
                if (!added.contains(node)) nodes[index++] = node.id;
                break;
            }

            if (order == DepthFirstSearchOrder.inOrder) {
                if (lesser != null) {
                    node = lesser;
                } else {
                    if (!added.contains(node)) {
                        nodes[index++] = node.id;
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
            } else if (order == DepthFirstSearchOrder.preOrder) {
                if (!added.contains(node)) {
                    nodes[index++] = node.id;
                    added.add(node);
                }
                if (lesser != null) {
                    node = lesser;
                } else if (greater != null) {
                    node = greater;
                } else if (greater == null && added.contains(node)) {
                    node = parent;
                } else {
                    // We should not get here. Stop the loop!
                    node = null;
                }
            } else {
                // post-Order
                if (lesser != null) {
                    node = lesser;
                } else {
                    if (!added.contains(node)) {
                        nodes[index++] = node.id;
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
        }
        return nodes;
    }

    /**
     * Get an array representation of the tree in sorted order.
     * 
     * @return sorted array representing the tree.
     */
    public T[] getSorted() {
        // Depth first search to traverse the tree in-order sorted.
        return getDFS(DepthFirstSearchOrder.inOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    protected static class Node<T extends Comparable<T>> {

        protected T id = null;
        protected Node<T> parent = null;
        protected Node<T> lesser = null;
        protected Node<T> greater = null;

        /**
         * Node constructor.
         * 
         * @param parent
         *            Parent link in tree. parent can be NULL.
         * @param id
         *            T representing the node in the tree.
         */
        protected Node(Node<T> parent, T id) {
            this.parent = parent;
            this.id = id;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "id =" + id + " parent=" + ((parent != null) ? parent.id : "NULL") + " lesser=" + ((lesser != null) ? lesser.id : "NULL") + " greater="
                    + ((greater != null) ? greater.id : "NULL");
        }
    }

    protected static interface INodeCreator<T extends Comparable<T>> {

        /**
         * Create a new Node with the following parameters.
         * 
         * @param parent
         *            of this node.
         * @param id
         *            of this node.
         * @return new Node
         */
        public Node<T> createNewNode(Node<T> parent, T id);
    }

    protected static class TreePrinter {

        public static <T extends Comparable<T>> String getString(BinarySearchTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent != null) {
                String side = "left";
                if (node.equals(node.parent.greater)) side = "right";
                builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + side + ") " + node.id + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + node.id + "\n");
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
