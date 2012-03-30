package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The treap and the randomized binary search tree are two closely related forms
 * of binary search tree data structures that maintain a dynamic set of ordered
 * keys and allow binary searches among the keys. After any sequence of
 * insertions and deletions of keys, the shape of the tree is a random variable
 * with the same probability distribution as a random binary tree; in
 * particular, with high probability its height is proportional to the logarithm
 * of the number of keys, so that each search, insertion, or deletion operation
 * takes logarithmic time to perform. http://en.wikipedia.org/wiki/Treap
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Treap {

    private static final int RANDOM_SIZE = 100; // This should be larger than the number of Nodes
    private static final Random RANDOM = new Random();

    private static enum DIRECTION {
        left, right
    };

    private Node root = null;
    private int size = 0;

    public Treap() {
    }

    public void add(char character) {
        add(new Node(null, character));
    }

    public boolean contains(Character key) {
        Node node = root;
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                return true;
            } else if (key.compareTo(node.key) < 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }
        return false;
    }

    private void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            addToSubtree(root, node);
        }
        size++;
    }

    private void addToSubtree(Node subtreeRoot, Node node) {
        addAsBinarySearchTree(subtreeRoot, node);
        heapify(node);
    }

    private void addAsBinarySearchTree(Node subtree, Node node) {
        // Add like a binary search tree
        DIRECTION direction = null;
        Node previous = null;
        Node current = subtree;
        while (current != null) {
            if (node.key.compareTo(current.key) <= 0) {
                // Less than or equal to -- go left
                direction = DIRECTION.left;
                previous = current;
                current = current.lesser;
            } else {
                // Greater than -- go right
                direction = DIRECTION.right;
                previous = current;
                current = current.greater;
            }
        }
        node.parent = previous;
        if (direction == DIRECTION.left) {
            previous.lesser = node;
        } else {
            previous.greater = node;
        }
    }

    private void heapify(Node current) {
        // Bubble up the heap, if needed
        Node parent = current.parent;
        while (parent != null && current.priority > parent.priority) {
            Node parentLeft = parent.lesser;
            Node parentRight = parent.greater;
            Node grandParent = parent.parent;

            current.parent = grandParent;
            if (parentLeft != null && parentLeft == current) {
                // LEFT
                if (grandParent != null) grandParent.lesser = current;
                parent.lesser = null;

                if (current.greater == null) {
                    current.greater = parent;
                    parent.parent = current;
                } else {
                    Node lost = current.greater;
                    lost.parent = null;
                    current.greater = parent;
                    parent.parent = current;
                    addToSubtree(parent, lost);
                }
            } else if (parentRight != null && parentRight == current) {
                // RIGHT
                if (grandParent != null) grandParent.greater = current;
                parent.greater = null;

                if (current.lesser == null) {
                    current.lesser = parent;
                    parent.parent = current;
                } else {
                    Node lost = current.lesser;
                    lost.parent = null;
                    current.lesser = parent;
                    parent.parent = current;
                    addToSubtree(parent, lost);
                }
            } else {
                // We really shouldn't get here
                System.err.println("YIKES!");
            }
            if (parent == root)
                root = current;
            parent = current.parent;
        }
    }

    public char[] getSorted() {
        // Depth first search
        char[] nodes = new char[size];
        if (size <= 0)
            return nodes;

        List<Node> added = new ArrayList<Node>();
        int index = 0;
        Node node = root;
        while (index < size) {
            Node parent = node.parent;
            Node lesser = (node.lesser != null && !added.contains(node.lesser)) ? node.lesser : null;
            Node greater = (node.greater != null && !added.contains(node.greater)) ? node.greater : null;

            if (parent == null && lesser == null && greater == null) {
                if (!added.contains(node))
                    nodes[index++] = node.key;
                break;
            }

            if (lesser != null) {
                node = lesser;
            } else {
                if (!added.contains(node)) {
                    nodes[index++] = node.key;
                    added.add(node);
                }
                if (greater != null) {
                    node = greater;
                } else if (greater == null && added.contains(node)) {
                    node = parent;
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
        StringBuilder builder = new StringBuilder();
        char[] sorted = getSorted();
        for (char node : sorted) {
            builder.append(node).append(", ");
        }
        return builder.toString();
    }

    private static class Node {

        private Node parent = null;
        private Integer priority = Integer.MIN_VALUE;
        private Character key = ' ';

        private Node lesser = null;
        private Node greater = null;

        private Node(Node parent, int priority, char character) {
            this.parent = parent;
            this.priority = priority;
            this.key = character;
        }

        private Node(Node parent, char character) {
            this(parent, RANDOM.nextInt(RANDOM_SIZE), character);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("priorty=").append(priority).append(" key=").append(key);
            if (parent != null)
                builder.append(" parent=").append(parent.key);
            builder.append("\n");
            if (lesser != null)
                builder.append("left=").append(lesser.toString()).append("\n");
            if (greater != null)
                builder.append("right=").append(greater.toString()).append("\n");
            return builder.toString();
        }
    }
}
