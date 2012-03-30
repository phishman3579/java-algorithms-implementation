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
 * @param <T>
 */
public class Treap<T> {

    private static final int RANDOM_SIZE = 100; // This should be larger than the number of Nodes
    private static final Random RANDOM = new Random();

    private static enum DIRECTION {
        left, right
    };

    private Node<T> root = null;
    private int size = 0;

    public Treap() { }

    public void add(Comparable<T> key) {
        add(new Node<T>(null, key));
    }

    public boolean remove(Comparable<T> key) {
        if (!contains(key)) return false;
        
        Node<T> node = getNode(key);
        if (node==null) return false;
        
        //Which side am I?
        Node<T> parent = node.parent;
        
        if (parent==null) {
            //removing the root!!!
            if (node.lesser!=null && node.greater!=null) {
                //Use left node
                root = node.greater;
                node.greater.parent = null;
                
                //Add the right node to the left node's subtree
                Node<T> lost = node.lesser;
                lost.parent = null;
                addToSubtree(node.greater,lost);
            } else {
                if (node.lesser!=null) {
                    root = node.lesser;
                    node.lesser.parent = parent;
                } else if (node.greater!=null) {
                    root = node.greater;
                    node.greater.parent = parent;
                } else {
                    // No children, just get rid of it
                    root = null;
                }
            }
        } else {
            DIRECTION direction = 
                (parent.lesser!=null&&parent.lesser==node)?
                    DIRECTION.left
                :
                    DIRECTION.right;
            
            if (node.lesser!=null && node.greater!=null) {
                //Use greater node
                if (direction==DIRECTION.left) parent.lesser = node.greater;
                else parent.greater = node.greater;
                node.greater.parent = parent;
                
                //Add the lesser node to the greaters node's subtree
                Node<T> lost = node.lesser;
                lost.parent = null;
                addToSubtree(node.greater,lost);
            } else {
                if (node.lesser!=null) {
                    if (direction==DIRECTION.left) parent.lesser = node.lesser;
                    else parent.greater = node.lesser;
                    node.lesser.parent = parent;
                } else if (node.greater!=null) {
                    if (direction==DIRECTION.left) parent.lesser = node.greater;
                    else parent.greater = node.greater;
                    node.greater.parent = parent;
                } else {
                    // No children, just get rid of it
                    if (direction==DIRECTION.left) parent.lesser = null;
                    else parent.greater = null;
                }
            }
        }
        size--;
        
        return true;
    }

    public boolean contains(Comparable<T> key) {
        Node<T> node = getNode(key);
        return (node!=null);
    }
    
    @SuppressWarnings("unchecked")
    private Node<T> getNode(Comparable<T> key) {
        Node<T> node = root;
        while (node != null) {
            if (key.compareTo((T)node.key) == 0) {
                return node;
            } else if (key.compareTo((T)node.key) < 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }
        return null;
    }

    private void add(Node<T> node) {
        if (root == null) {
            root = node;
        } else {
            addToSubtree(root, node);
        }
        size++;
    }

    private void addToSubtree(Node<T> subtreeRoot, Node<T> node) {
        addAsBinarySearchTree(subtreeRoot, node);
        heapify(node);
    }

    @SuppressWarnings("unchecked")
    private void addAsBinarySearchTree(Node<T> subtree, Node<T> node) {
        // Add like a binary search tree
        DIRECTION direction = null;
        Node<T> previous = null;
        Node<T> current = subtree;
        while (current != null) {
            if (node.key.compareTo((T)current.key) <= 0) {
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

    private void heapify(Node<T> current) {
        // Bubble up the heap, if needed
        Node<T> parent = current.parent;
        while (parent != null && current.priority > parent.priority) {
            Node<T> parentLeft = parent.lesser;
            Node<T> parentRight = parent.greater;
            Node<T> grandParent = parent.parent;

            current.parent = grandParent;
            if (parentLeft != null && parentLeft == current) {
                // LEFT
                if (grandParent != null) grandParent.lesser = current;
                parent.lesser = null;

                if (current.greater == null) {
                    current.greater = parent;
                    parent.parent = current;
                } else {
                    Node<T> lost = current.greater;
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
                    Node<T> lost = current.lesser;
                    lost.parent = null;
                    current.lesser = parent;
                    parent.parent = current;
                    addToSubtree(parent, lost);
                }
            } else {
                // We really shouldn't get here
                System.err.println("YIKES!");
            }
            if (parent == root) root = current;
            parent = current.parent;
        }
    }

    @SuppressWarnings("unchecked")
    public T[] getSorted() {
        // Depth first search
        T[] nodes = (T[]) new Object[size];
        if (size <= 0) return nodes;

        List<Node<T>> added = new ArrayList<Node<T>>();
        int index = 0;
        Node<T> node = root;
        while (index < size) {
            Node<T> parent = node.parent;
            Node<T> lesser = (node.lesser != null && !added.contains(node.lesser)) ? node.lesser : null;
            Node<T> greater = (node.greater != null && !added.contains(node.greater)) ? node.greater : null;

            if (parent == null && lesser == null && greater == null) {
                if (!added.contains(node)) nodes[index++] = (T)node.key;
                break;
            }

            if (lesser != null) {
                node = lesser;
            } else {
                if (!added.contains(node)) {
                    nodes[index++] = (T)node.key;
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
        T[] sorted = getSorted();
        for (T node : sorted) {
            builder.append(node).append(", ");
        }
        return builder.toString();
    }

    private static class Node<T> {

        private Node<T> parent = null;
        private Integer priority = Integer.MIN_VALUE;
        private Comparable<T> key = null;

        private Node<T> lesser = null;
        private Node<T> greater = null;

        private Node(Node<T> parent, int priority, Comparable<T> key) {
            this.parent = parent;
            this.priority = priority;
            this.key = key;
        }

        private Node(Node<T> parent, Comparable<T> key) {
            this(parent, RANDOM.nextInt(RANDOM_SIZE), key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("priorty=").append(priority).append(" key=").append(key);
            if (parent != null)  builder.append(" parent=").append(parent.key);
            builder.append("\n");
            if (lesser != null) builder.append("left=").append(lesser.toString()).append("\n");
            if (greater != null) builder.append("right=").append(greater.toString()).append("\n");
            return builder.toString();
        }
    }
}
