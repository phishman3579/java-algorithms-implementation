package com.jwetherell.algorithms.data_structures;

/**
 * A splay tree is a self-adjusting binary search tree (BST) with the additional
 * property that recently accessed elements are quick to access again.
 * <p>
 *  @see <a href="https://en.wikipedia.org/wiki/Splay_tree">Splay Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> addValue(T id) {
        Node<T> nodeToReturn = super.addValue(id);
        Node<T> nodeAdded = nodeToReturn;
        if (nodeAdded != null) {
            // Splay the new node to the root position
            while (nodeAdded.parent != null) {
                this.splay(nodeAdded);
            }
        }
        return nodeToReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> removeValue(T value) {
        Node<T> nodeToRemove = super.removeValue(value);
        if (nodeToRemove != null && nodeToRemove.parent != null) {
            Node<T> nodeParent = nodeToRemove.parent;
            // Splay the parent node to the root position
            while (nodeParent.parent != null) {
                this.splay(nodeParent);
            }
        }
        return nodeToRemove;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        Node<T> node = getNode(value);
        if (node != null) {
            // Splay the new node to the root position
            while (node.parent != null) {
                this.splay(node);
            }
            return true;
        }
        return false;
    }

    /**
     * Splay the tree at the node.
     * 
     * @param node
     *            to splay at.
     */
    private void splay(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> grandParent = (parent != null) ? parent.parent : null;
        if (parent != null && parent == root) {
            grandParent = parent.parent;
            // Zig step
            root = node;
            node.parent = null;

            if (parent!=null) {
                if (node == parent.lesser) {
                    parent.lesser = node.greater;
                    if (node.greater != null)
                        node.greater.parent = parent;

                    node.greater = parent;
                    parent.parent = node;
                } else {
                    parent.greater = node.lesser;
                    if (node.lesser != null)
                        node.lesser.parent = parent;

                    node.lesser = parent;
                    parent.parent = node;
                }
            }
            return;
        }

        if (parent != null && grandParent != null) {
            Node<T> greatGrandParent = grandParent.parent;
            if (greatGrandParent != null && greatGrandParent.lesser == grandParent) {
                greatGrandParent.lesser = node;
                node.parent = greatGrandParent;
            } else if (greatGrandParent != null && greatGrandParent.greater == grandParent) {
                greatGrandParent.greater = node;
                node.parent = greatGrandParent;
            } else {
                // I am now root!
                root = node;
                node.parent = null;
            }

            if ((node == parent.lesser && parent == grandParent.lesser)
                || (node == parent.greater && parent == grandParent.greater)) {
                // Zig-zig step
                if (node == parent.lesser) {
                    Node<T> nodeGreater = node.greater;
                    node.greater = parent;
                    parent.parent = node;

                    parent.lesser = nodeGreater;
                    if (nodeGreater != null)
                        nodeGreater.parent = parent;

                    Node<T> parentGreater = parent.greater;
                    parent.greater = grandParent;
                    grandParent.parent = parent;

                    grandParent.lesser = parentGreater;
                    if (parentGreater != null)
                        parentGreater.parent = grandParent;
                } else {
                    Node<T> nodeLesser = node.lesser;
                    node.lesser = parent;
                    parent.parent = node;

                    parent.greater = nodeLesser;
                    if (nodeLesser != null)
                        nodeLesser.parent = parent;

                    Node<T> parentLesser = parent.lesser;
                    parent.lesser = grandParent;
                    grandParent.parent = parent;

                    grandParent.greater = parentLesser;
                    if (parentLesser != null)
                        parentLesser.parent = grandParent;
                }
                return;
            }

            // Zig-zag step
            if (node == parent.lesser) {
                Node<T> nodeLesser = node.greater;
                Node<T> nodeGreater = node.lesser;

                node.greater = parent;
                parent.parent = node;

                node.lesser = grandParent;
                grandParent.parent = node;

                parent.lesser = nodeLesser;
                if (nodeLesser != null)
                    nodeLesser.parent = parent;

                grandParent.greater = nodeGreater;
                if (nodeGreater != null)
                    nodeGreater.parent = grandParent;
                return;
            }

            Node<T> nodeLesser = node.lesser;
            Node<T> nodeGreater = node.greater;

            node.lesser = parent;
            parent.parent = node;

            node.greater = grandParent;
            grandParent.parent = node;

            parent.greater = nodeLesser;
            if (nodeLesser != null)
                nodeLesser.parent = parent;

            grandParent.lesser = nodeGreater;
            if (nodeGreater != null)
                nodeGreater.parent = grandParent;
        }
    }
}
