package com.jwetherell.algorithms.data_structures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * A red–black tree is a type of self-balancing binary search tree, a data
 * structure used in computer science, typically to implement associative
 * arrays. A red–black tree is a binary search tree that inserts and deletes in
 * such a way that the tree is always reasonably balanced. Red-black trees are
 * often compared with AVL trees. AVL trees are more rigidly balanced, they are
 * faster than red-black trees for lookup intensive applications. However,
 * red-black trees are faster for insertion and removal.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">Red-Black Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected static final boolean BLACK = false;
    protected static final boolean RED = true;

    /**
     * Default constructor.
     */
    public RedBlackTree() {
        this.creator = new BinarySearchTree.INodeCreator<T>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public BinarySearchTree.Node<T> createNewNode(BinarySearchTree.Node<T> parent, T id) {
                return (new RedBlackNode<T>(parent, id, BLACK));
            }
        };
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
        if (root == null) {
            // Case 1 - The current node is at the root of the tree.

            // Defaulted to black in our creator
            root = this.creator.createNewNode(null, id);
            root.lesser = this.creator.createNewNode(root, null);
            root.greater = this.creator.createNewNode(root, null);

            size++;
            return root;
        }

        RedBlackNode<T> nodeAdded = null;
        // Insert node like a BST would
        Node<T> node = root;
        while (node != null) {
            if (node.id == null) {
                node.id = id;
                ((RedBlackNode<T>) node).color = RED;

                // Defaulted to black in our creator
                node.lesser = this.creator.createNewNode(node, null);
                node.greater = this.creator.createNewNode(node, null);

                nodeAdded = (RedBlackNode<T>) node;
                break;
            } else if (id.compareTo(node.id) <= 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }

        if (nodeAdded != null)
            balanceAfterInsert(nodeAdded);

        size++;
        return nodeAdded;
    }

    /**
     * Post insertion balancing algorithm.
     * 
     * @param begin
     *            to begin balancing at.
     * @return True if balanced.
     */
    private void balanceAfterInsert(RedBlackNode<T> begin) {
        RedBlackNode<T> node = begin;
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
        RedBlackNode<T> uncle = node.getUncle(grandParent);
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
            return;
        }

        if (parent.color == RED && uncle.color == BLACK) {
            // Case 4 - The parent is red but the uncle is black; also, the
            // current node is the right child of parent, and parent in turn
            // is the left child of its parent grandparent.
            if (node == parent.greater && parent == grandParent.lesser) {
                // right-left
                rotateLeft(parent);
 
                node = (RedBlackNode<T>) node.lesser;
                parent = (RedBlackNode<T>) node.parent;
                grandParent = node.getGrandParent();
                uncle = node.getUncle(grandParent);
            } else if (node == parent.lesser && parent == grandParent.greater) {
                // left-right
                rotateRight(parent);

                node = (RedBlackNode<T>) node.greater;
                parent = (RedBlackNode<T>) node.parent;
                grandParent = node.getGrandParent();
                uncle = node.getUncle(grandParent);
            }
        }

        if (parent.color == RED && uncle.color == BLACK) {
            // Case 5 - The parent is red but the uncle is black, the
            // current node is the left child of parent, and parent is the
            // left child of its parent G.
            parent.color = BLACK;
            grandParent.color = RED;
            if (node == parent.lesser && parent == grandParent.lesser) {
                // left-left
                rotateRight(grandParent);
            } else if (node == parent.greater && parent == grandParent.greater) {
                // right-right
                rotateLeft(grandParent);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> removeNode(Node<T> node) {
        if (node == null) return node;

        RedBlackNode<T> nodeToRemoved = (RedBlackNode<T>)node;

        if (nodeToRemoved.isLeaf()) {
            // No children
            nodeToRemoved.id = null;
            if (nodeToRemoved == root) {
                root = null;
            } else {
                nodeToRemoved.id = null;
                nodeToRemoved.color = BLACK;
                nodeToRemoved.lesser = null;
                nodeToRemoved.greater = null;
            }

            size--;
            return nodeToRemoved;
        }

        // At least one child

        // Keep the id and assign it to the replacement node
        T id = nodeToRemoved.id;
        RedBlackNode<T> lesser = (RedBlackNode<T>) nodeToRemoved.lesser;
        RedBlackNode<T> greater = (RedBlackNode<T>) nodeToRemoved.greater;
        if (lesser.id != null && greater.id != null) {
            // Two children
            RedBlackNode<T> greatestInLesser = (RedBlackNode<T>) this.getGreatest(lesser);
            if (greatestInLesser == null || greatestInLesser.id == null) 
                greatestInLesser = lesser;

            // Replace node with greatest in his lesser tree, which leaves us with only one child
            replaceValueOnly(nodeToRemoved, greatestInLesser);
            nodeToRemoved = greatestInLesser;
            lesser = (RedBlackNode<T>) nodeToRemoved.lesser;
            greater = (RedBlackNode<T>) nodeToRemoved.greater;
        }

        // Handle one child
        RedBlackNode<T> child = (RedBlackNode<T>) ((lesser.id != null) ? lesser : greater);
        if (nodeToRemoved.color == BLACK) {
            if (child.color == BLACK) 
                nodeToRemoved.color = RED;
            boolean result = balanceAfterDelete(nodeToRemoved);
            if (!result) 
                return nodeToRemoved;
        }

        // Replacing node with child
        replaceWithChild(nodeToRemoved, child);
        // Add the id to the child because it represents the node that was removed.
        child.id = id;
        if (root == nodeToRemoved) {
            root.parent = null;
            ((RedBlackNode<T>)root).color = BLACK;
            // If we replaced the root with a leaf, just null out root
            if (nodeToRemoved.isLeaf()) 
                root = null;
        }
        nodeToRemoved = child;

        size--;
        return nodeToRemoved;
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

        nodeToReplace.lesser = nodeToReplaceWith.lesser;
        if (nodeToReplace.lesser!=null) 
            nodeToReplace.lesser.parent = nodeToReplace;

        nodeToReplace.greater = nodeToReplaceWith.greater;
        if (nodeToReplace.greater!=null) 
            nodeToReplace.greater.parent = nodeToReplace;
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
            if (node == parent.lesser) {
                rotateLeft(parent);

                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else if (node == parent.greater) {
                rotateRight(parent);

                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else {
                throw new RuntimeException("Yikes! I'm not related to my parent. " + node.toString());
            }
        }

        if (parent.color == BLACK 
            && sibling.color == BLACK 
            && ((RedBlackNode<T>) sibling.lesser).color == BLACK
            && ((RedBlackNode<T>) sibling.greater).color == BLACK
        ) {
            // Case 3 - parent, sibling, and sibling's children are black.
            sibling.color = RED;
            return balanceAfterDelete(parent);
        } 

        if (parent.color == RED 
            && sibling.color == BLACK 
            && ((RedBlackNode<T>) sibling.lesser).color == BLACK
            && ((RedBlackNode<T>) sibling.greater).color == BLACK
        ) {
            // Case 4 - sibling and sibling's children are black, but parent is red.
            sibling.color = RED;
            parent.color = BLACK;
            return true;
        }

        if (sibling.color == BLACK) {
            // Case 5 - sibling is black, sibling's left child is red,
            // sibling's right child is black, and node is the left child of
            // its parent.
            if (node == parent.lesser 
                && ((RedBlackNode<T>) sibling.lesser).color == RED
                && ((RedBlackNode<T>) sibling.greater).color == BLACK
            ) {
                sibling.color = RED;
                ((RedBlackNode<T>) sibling.lesser).color = RED;

                rotateRight(sibling);

                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else if (node == parent.greater 
                       && ((RedBlackNode<T>) sibling.lesser).color == BLACK
                       && ((RedBlackNode<T>) sibling.greater).color == RED
            ) {
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
        if (node == parent.lesser) {
            ((RedBlackNode<T>) sibling.greater).color = BLACK;
            rotateLeft(node.parent);
        } else if (node == parent.greater) {
            ((RedBlackNode<T>) sibling.lesser).color = BLACK;
            rotateRight(node.parent);
        } else {
            throw new RuntimeException("Yikes! I'm not related to my parent. " + node.toString());
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root == null)
            return true;

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
            if (!lesserCheck) 
                return false;
            // Check red-black property
            lesserCheck = this.validateNode(lesser);
            if (!lesserCheck) 
                return false;
        }

        if (!greater.isLeaf()) {
            // Check BST property
            boolean greaterCheck = greater.id.compareTo(rbNode.id) > 0;
            if (!greaterCheck) 
                return false;
            // Check red-black property
            greaterCheck = this.validateNode(greater);
            if (!greaterCheck) 
                return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<T> toCollection() {
        return (new JavaCompatibleRedBlackTree<T>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return RedBlackTreePrinter.getString(this);
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

        protected RedBlackNode<T> getUncle(RedBlackNode<T> grandParent) {
            if (grandParent == null) return null;
            if (grandParent.lesser != null && grandParent.lesser == parent) {
                return (RedBlackNode<T>) grandParent.greater;
            } else if (grandParent.greater != null && grandParent.greater == parent) {
                return (RedBlackNode<T>) grandParent.lesser;
            }
            return null;
        }

        protected RedBlackNode<T> getUncle() {
            RedBlackNode<T> grandParent = getGrandParent();
            return getUncle(grandParent);
        }

        protected RedBlackNode<T> getSibling() {
            if (parent == null) 
                return null;
            if (parent.lesser == this) {
                return (RedBlackNode<T>) parent.greater;
            } else if (parent.greater == this) {
                return (RedBlackNode<T>) parent.lesser;
            } else {
                throw new RuntimeException("Yikes! I'm not related to my parent. " + this.toString());
            }
        }

        protected boolean isLeaf() {
            if (lesser != null) 
                return false;
            if (greater != null) 
                return false;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "id=" + id + " color=" + ((color == RED) ? "RED" : "BLACK") + " isLeaf=" + isLeaf() + " parent="
                    + ((parent != null) ? parent.id : "NULL") + " lesser=" + ((lesser != null) ? lesser.id : "NULL")
                    + " greater=" + ((greater != null) ? greater.id : "NULL");
        }
    }

    protected static class RedBlackTreePrinter {

        public static <T extends Comparable<T>> String getString(RedBlackTree<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString((RedBlackNode<T>) tree.root, "", true);
        }

        public static <T extends Comparable<T>> String getString(RedBlackNode<T> node) {
            if (node == null)
                return "Sub-tree has no nodes.";
            return getString(node, "", true);
        }

        private static <T extends Comparable<T>> String getString(RedBlackNode<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + ((node.color == RED) ? "RED" : "BLACK") + ") " + node.id
                           + " [parent=" + ((node.parent!=null)?node.parent.id:"NULL") 
                           + " grand-parent=" + ((node.parent!=null && node.parent.parent!=null)?node.parent.parent.id:"NULL")
                           + "]\n"
            );
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.lesser != null)
                    children.add(node.lesser);
                if (node.greater != null)
                    children.add(node.greater);
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

    public static class JavaCompatibleRedBlackTree<T extends Comparable<T>> extends java.util.AbstractCollection<T> {

        private RedBlackTree<T> tree = null;

        public JavaCompatibleRedBlackTree() {
            this.tree = new RedBlackTree<T> ();
        }

        public JavaCompatibleRedBlackTree(RedBlackTree<T> tree) {
            this.tree = tree;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return tree.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return (tree.remove((T)value)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return tree.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return tree.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<T> iterator() {
            return (new RedBlackTreeIterator<T>(this.tree));
        }

        private static class RedBlackTreeIterator<C extends Comparable<C>> implements Iterator<C> {

            private RedBlackTree<C> tree = null;
            private RedBlackTree.Node<C> last = null;
            private Deque<RedBlackTree.Node<C>> toVisit = new ArrayDeque<RedBlackTree.Node<C>>();

            protected RedBlackTreeIterator(RedBlackTree<C> tree) {
                this.tree = tree;
                if (tree.root!=null) {
                    toVisit.add(tree.root);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                if (toVisit.size()>0) return true; 
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public C next() {
                while (toVisit.size()>0) {
                    // Go thru the current nodes
                    RedBlackTree.Node<C> n = toVisit.pop();

                    // Add non-null children
                    if (n.lesser!=null && n.lesser.id!=null) {
                        toVisit.add(n.lesser);
                    }
                    if (n.greater!=null && n.greater.id!=null) {
                        toVisit.add(n.greater);
                    }

                    last = n;
                    return n.id;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                tree.removeNode(last);
            }
        }
    }
}
