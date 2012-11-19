package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * A red–black tree is a type of self-balancing binary search tree, a data
 * structure used in computer science, typically to implement associative
 * arrays. A red–black tree is a binary search tree that inserts and deletes in
 * such a way that the tree is always reasonably balanced. Red-black trees are
 * often compared with AVL trees. AVL trees are more rigidly balanced, they are
 * faster than red-black trees for lookup intensive applications. However,
 * red-black trees are faster for insertion and removal.
 * 
 * http://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> implements BinarySearchTree.INodeCreator<T> {

    protected static final boolean BLACK = false;
    protected static final boolean RED = true;

    /**
     * Default constructor.
     */
    public RedBlackTree() {
        this.creator = this;
    }

    /**
     * Constructor with external Node creator.
     */
    public RedBlackTree(INodeCreator<T> creator) {
        super(creator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> addValue(T id) {
        RedBlackNode<T> nodeAdded = null;
        boolean added = false;
        if (root == null) {
            // Case 1 - The current node is at the root of the tree.
            if (this.creator == null) {
                root = new RedBlackNode<T>(null, id, BLACK);
                root.lesser = new RedBlackNode<T>(root, null, BLACK);
                root.greater = new RedBlackNode<T>(root, null, BLACK);
            } else {
                root = this.creator.createNewNode(null, id);
                ((RedBlackNode<T>) root).color = BLACK;
                root.lesser = this.creator.createNewNode(root, null);
                ((RedBlackNode<T>) root.lesser).color = BLACK;
                root.greater = this.creator.createNewNode(root, null);
                ((RedBlackNode<T>) root.greater).color = BLACK;
            }
            nodeAdded = (RedBlackNode<T>) root;
            added = true;
        } else {
            // Insert node like a BST would
            Node<T> node = root;
            while (node != null) {
                if (node.id == null) {
                    node.id = id;
                    ((RedBlackNode<T>) node).color = RED;
                    if (this.creator == null) {
                        node.lesser = new RedBlackNode<T>(node, null, BLACK);
                        node.greater = new RedBlackNode<T>(node, null, BLACK);
                    } else {
                        node.lesser = this.creator.createNewNode(node, null);
                        ((RedBlackNode<T>) node.lesser).color = BLACK;
                        node.greater = this.creator.createNewNode(node, null);
                        ((RedBlackNode<T>) node.greater).color = BLACK;
                    }
                    nodeAdded = (RedBlackNode<T>) node;
                    added = true;
                    break;
                } else if (id.compareTo(node.id) <= 0) {
                    node = node.lesser;
                } else {
                    node = node.greater;
                }
            }
        }

        if (added == true) {
            balanceAfterInsert(nodeAdded);
            size++;
        }

        return nodeAdded;
    }

    /**
     * Post insertion balancing algorithm.
     * 
     * @param node
     *            to begin balancing at.
     * @return True if balanced.
     */
    private void balanceAfterInsert(RedBlackNode<T> node) {
        RedBlackNode<T> parent = (RedBlackNode<T>) node.parent;

        if (parent == null) {
            // Case 1 - The current node is at the root of the tree.
            node.color = BLACK;
            return;
        }

        if (parent.color == BLACK) {
            // Case 2 - The current node's parent is black, so property 4 (both
            // children of every red node are black) is not invalidated.
            return;
        }

        RedBlackNode<T> grandParent = node.getGrandParent();
        RedBlackNode<T> uncle = node.getUncle();
        if (parent.color == RED && uncle.color == RED) {
            // Case 3 - If both the parent and the uncle are red, then both of
            // them can be repainted black and the grandparent becomes
            // red (to maintain property 5 (all paths from any given node to its
            // leaf nodes contain the same number of black nodes)).
            parent.color = BLACK;
            uncle.color = BLACK;
            if (grandParent != null) {
                grandParent.color = RED;
                balanceAfterInsert(grandParent);
            }
        } else {
            if (parent.color == RED && uncle.color == BLACK) {
                // Case 4 - The parent is red but the uncle is black; also, the
                // current node is the right child of parent, and parent in turn
                // is the left child of its parent grandparent.
                if (node.equals(parent.greater) && parent.equals(grandParent.lesser)) {
                    // right-left
                    rotateLeft(parent);
                    node = (RedBlackNode<T>) node.lesser;

                    grandParent = node.getGrandParent();
                    parent = (RedBlackNode<T>) node.parent;
                    uncle = node.getUncle();
                } else if (node.equals(parent.lesser) && parent.equals(grandParent.greater)) {
                    // left-right
                    rotateRight(parent);
                    node = (RedBlackNode<T>) node.greater;

                    grandParent = node.getGrandParent();
                    parent = (RedBlackNode<T>) node.parent;
                    uncle = node.getUncle();
                }
            }

            if (parent.color == RED && uncle.color == BLACK) {
                // Case 5 - The parent is red but the uncle is black, the
                // current node is the left child of parent, and parent is the
                // left child of its parent G.
                parent.color = BLACK;
                grandParent.color = RED;
                if (node.equals(parent.lesser) && parent.equals(grandParent.lesser)) {
                    // left-left
                    rotateRight(grandParent);
                } else if (node.equals(parent.greater) && parent.equals(grandParent.greater)) {
                    // right-right
                    rotateLeft(grandParent);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> removeValue(T value) {
        RedBlackNode<T> nodeRemoved = (RedBlackNode<T>) super.getNode(value);
        if (nodeRemoved == null) return null;

        if (nodeRemoved.isLeaf()) {
            // No children
            nodeRemoved.id = null;
            if (nodeRemoved.parent == null) {
                root = null;
            } else {
                nodeRemoved.id = null;
                nodeRemoved.color = BLACK;
                nodeRemoved.lesser = null;
                nodeRemoved.greater = null;
            }
        } else {
            // At least one child
            RedBlackNode<T> lesser = (RedBlackNode<T>) nodeRemoved.lesser;
            RedBlackNode<T> greater = (RedBlackNode<T>) nodeRemoved.greater;
            if (lesser.id != null && greater.id != null) {
                // Two children
                RedBlackNode<T> greatestInLesser = (RedBlackNode<T>) this.getGreatest(lesser);
                if (greatestInLesser == null || greatestInLesser.id == null) greatestInLesser = lesser;
                // Replace node with greatest in his lesser tree, which leaves
                // us with only one child
                replaceValueOnly(nodeRemoved, greatestInLesser);
                nodeRemoved = greatestInLesser;
            }

            // Handle one child
            RedBlackNode<T> child = (RedBlackNode<T>) ((nodeRemoved.lesser.id != null) ? nodeRemoved.lesser : nodeRemoved.greater);
            if (nodeRemoved.color == BLACK) {
                if (child.color == BLACK) {
                    nodeRemoved.color = RED;
                }
                boolean result = balanceAfterDelete(nodeRemoved);
                if (!result) return null;
            }
            replaceWithChild(nodeRemoved, child);
            if (root.equals(nodeRemoved) && nodeRemoved.isLeaf()) {
                // If we replaced the root with a leaf, just null out root
                root = null;
            }
        }

        size--;

        return nodeRemoved;
    }

    /**
     * Replace value of nodeToReplaceWith with nodeToReplace.
     * 
     * @param nodeToReplace
     *            will get value of nodeToReplaceWith.
     * @param nodeToReplaceWith
     *            will get value NULLed.
     */
    private void replaceValueOnly(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        nodeToReplace.id = nodeToReplaceWith.id;
        nodeToReplaceWith.id = null;
    }

    /**
     * Replace entire contents of nodeToReplace with nodeToReplaceWith.
     * 
     * @param nodeToReplace
     *            will get it's contents replace with nodeToReplaceWith
     *            contents.
     * @param nodeToReplaceWith
     *            will not be changed.
     */
    private void replaceWithChild(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        nodeToReplace.id = nodeToReplaceWith.id;
        nodeToReplace.color = nodeToReplaceWith.color;

        // root should always be black
        if (nodeToReplace.parent == null) nodeToReplace.color = BLACK;

        nodeToReplace.lesser = nodeToReplaceWith.lesser;
        nodeToReplace.greater = nodeToReplaceWith.greater;
    }

    /**
     * Post delete balancing algorithm.
     * 
     * @param node
     *            to begin balancing at.
     * @return True if balanced or false if error.
     */
    private boolean balanceAfterDelete(RedBlackNode<T> node) {
        if (node.parent == null) {
            // Case 1 - node is the new root.
            return true;
        }

        RedBlackNode<T> parent = (RedBlackNode<T>) node.parent;
        RedBlackNode<T> sibling = node.getSibling();
        if (sibling.color == RED) {
            // Case 2 - sibling is red.
            parent.color = RED;
            sibling.color = BLACK;
            if (node.equals(parent.lesser)) {
                rotateLeft(parent);
                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else if (node.equals(parent.greater)) {
                rotateRight(parent);
                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else {
                System.err.println("Yikes! I'm not related to my parent.");
                return false;
            }
        }

        if (parent.color == BLACK && sibling.color == BLACK && ((RedBlackNode<T>) sibling.lesser).color == BLACK
                && ((RedBlackNode<T>) sibling.greater).color == BLACK) {
            // Case 3 - parent, sibling, and sibling's children are black.
            sibling.color = RED;
            boolean result = balanceAfterDelete(parent);
            if (!result) return false;
        } else if (parent.color == RED && sibling.color == BLACK && ((RedBlackNode<T>) sibling.lesser).color == BLACK
                && ((RedBlackNode<T>) sibling.greater).color == BLACK) {
            // Case 4 - sibling and sibling's children are black, but parent is
            // red.
            sibling.color = RED;
            parent.color = BLACK;
        } else {
            if (sibling.color == BLACK) {
                // Case 5 - sibling is black, sibling's left child is red,
                // sibling's right child is black, and node is the left child of
                // its parent.
                if (node.equals(parent.lesser) && ((RedBlackNode<T>) sibling.lesser).color == RED && ((RedBlackNode<T>) sibling.greater).color == BLACK) {
                    sibling.color = RED;
                    ((RedBlackNode<T>) sibling.lesser).color = RED;
                    rotateRight(sibling);
                    // Rotation, need to update parent/sibling
                    parent = (RedBlackNode<T>) node.parent;
                    sibling = node.getSibling();
                } else if (node.equals(parent.greater) && ((RedBlackNode<T>) sibling.lesser).color == BLACK && ((RedBlackNode<T>) sibling.greater).color == RED) {
                    sibling.color = RED;
                    ((RedBlackNode<T>) sibling.greater).color = RED;
                    rotateLeft(sibling);
                    // Rotation, need to update parent/sibling
                    parent = (RedBlackNode<T>) node.parent;
                    sibling = node.getSibling();
                }
            }

            // Case 6 - sibling is black, sibling's right child is red, and node
            // is the left child of its parent.
            sibling.color = parent.color;
            parent.color = BLACK;
            if (node.equals(parent.lesser)) {
                ((RedBlackNode<T>) sibling.greater).color = BLACK;
                rotateLeft(node.parent);
            } else if (node.equals(parent.greater)) {
                ((RedBlackNode<T>) sibling.lesser).color = BLACK;
                rotateRight(node.parent);
            } else {
                System.err.println("Yikes! I'm not related to my parent. " + node.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root == null) return true;

        if (((RedBlackNode<T>) root).color == RED) {
            // Root node should be black
            return false;
        }

        return this.validateNode(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validateNode(Node<T> node) {
        RedBlackNode<T> rbNode = (RedBlackNode<T>) node;
        RedBlackNode<T> lesser = (RedBlackNode<T>) rbNode.lesser;
        RedBlackNode<T> greater = (RedBlackNode<T>) rbNode.greater;

        if (rbNode.isLeaf() && rbNode.color == RED) {
            // Leafs should not be red
            return false;
        }

        if (rbNode.color == RED) {
            // You should not have two red nodes in a row
            if (lesser.color == RED) return false;
            if (greater.color == RED) return false;
        }

        if (!lesser.isLeaf()) {
            // Check BST property
            boolean lesserCheck = lesser.id.compareTo(rbNode.id) <= 0;
            if (!lesserCheck) return false;
            // Check red-black property
            lesserCheck = this.validateNode(lesser);
            if (!lesserCheck) return false;
        }

        if (!greater.isLeaf()) {
            // Check BST property
            boolean greaterCheck = greater.id.compareTo(rbNode.id) > 0;
            if (!greaterCheck) return false;
            // Check red-black property
            greaterCheck = this.validateNode(greater);
            if (!greaterCheck) return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return RedBlackTreePrinter.getString(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node<T> createNewNode(Node<T> parent, T id) {
        return (new RedBlackNode<T>(parent, id, BLACK));
    }

    protected static class RedBlackNode<T extends Comparable<T>> extends Node<T> {

        protected boolean color = BLACK;

        protected RedBlackNode(Node<T> parent, T id, boolean color) {
            super(parent, id);
            this.color = color;
        }

        protected RedBlackNode<T> getGrandParent() {
            if (parent == null || parent.parent == null) return null;
            return (RedBlackNode<T>) parent.parent;
        }

        protected RedBlackNode<T> getUncle() {
            RedBlackNode<T> grandParent = getGrandParent();
            if (grandParent == null) return null;
            if (grandParent.lesser != null && grandParent.lesser.equals(parent)) {
                return (RedBlackNode<T>) grandParent.greater;
            } else if (grandParent.greater != null && grandParent.greater.equals(parent)) {
                return (RedBlackNode<T>) grandParent.lesser;
            }
            return null;
        }

        protected RedBlackNode<T> getSibling() {
            if (parent == null) return null;
            if (parent.lesser.equals(this)) {
                return (RedBlackNode<T>) parent.greater;
            } else if (parent.greater.equals(this)) {
                return (RedBlackNode<T>) parent.lesser;
            } else {
                System.err.println("Yikes! I'm not my parents child.");
            }
            return null;
        }

        protected boolean isLeaf() {
            if (lesser != null) return false;
            if (greater != null) return false;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + id + " color=" + ((color == RED) ? "RED" : "BLACK") + " isLeaf=" + isLeaf() + " parent="
                    + ((parent != null) ? parent.id : "NULL") + " lesser=" + ((lesser != null) ? lesser.id : "NULL") + " greater="
                    + ((greater != null) ? greater.id : "NULL");
        }
    }

    protected static class RedBlackTreePrinter {

        public static <T extends Comparable<T>> String getString(RedBlackTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString((RedBlackNode<T>) tree.root, "", true);
        }

        public static <T extends Comparable<T>> String getString(RedBlackNode<T> node) {
            if (node == null) return "Sub-tree has no nodes.";
            return getString(node, "", true);
        }

        private static <T extends Comparable<T>> String getString(RedBlackNode<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + ((node.color == RED) ? "RED" : "BLACK") + ") " + node.id + "\n");
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.lesser != null) children.add(node.lesser);
                if (node.greater != null) children.add(node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString((RedBlackNode<T>) children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString((RedBlackNode<T>) children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
