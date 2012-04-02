package com.jwetherell.algorithms.data_structures;


/**
 * The treap and the randomized binary search tree are two closely related forms
 * of binary search tree data structures that maintain a dynamic set of ordered
 * values and allow binary searches among the values. After any sequence of
 * insertions and deletions of values, the shape of the tree is a random variable
 * with the same probability distribution as a random binary tree; in
 * particular, with high probability its height is proportional to the logarithm
 * of the number of values, so that each search, insertion, or deletion operation
 * takes logarithmic time to perform. 
 * 
 * http://en.wikipedia.org/wiki/Treap
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Treap<T extends Comparable<T>> extends BinarySearchTree<T> {

    private static final int RANDOM_SIZE = 100; // This should be at least twice the number of Nodes


    public void add(T value, int priority) {
        add(new TreapNode<T>(null,priority,value),true);
    }
    
    @Override
    public void add(T value) {
        add(new TreapNode<T>(null,value),true);
    }

    @Override
    protected void populateTree(T[] nodes) {
        for (T node : nodes) {
            TreapNode<T> newNode = new TreapNode<T>(null,node);
            add(newNode,true);
        }
    }
    
    @Override
    protected void addToSubtree(Node<T> subtreeRoot, Node<T> newNode, boolean adjustSize) {
        super.addToSubtree(subtreeRoot, newNode, adjustSize);
        TreapNode<T> current = (TreapNode<T>) newNode;
        heapify(current);
    }

    private void heapify(TreapNode<T> current) {
        // Bubble up the heap, if needed
        TreapNode<T> parent = (TreapNode<T>)current.parent;
        while (parent != null && current.priority > parent.priority) {
            Node<T> grandParent = parent.parent;
            if (grandParent != null) {
                if (grandParent.greater!=null && grandParent.greater==parent) {
                    //My parent is my grandparents greater branch
                    grandParent.greater = current;
                    current.parent = grandParent;
                } else if (grandParent.lesser!=null && grandParent.lesser==parent) {
                    //My parent is my grandparents lesser branch
                    grandParent.lesser = current;
                    current.parent = grandParent;
                } else {
                    System.err.println("YIKES! Grandparent should have at least one non-NULL child which should be my parent.");
                }
                current.parent = grandParent;
            } else {
                current.parent = null;
            }

            if (parent.lesser != null && parent.lesser == current) {
                // LEFT
                parent.lesser = null;

                if (current.greater == null) {
                    current.greater = parent;
                    parent.parent = current;
                } else {
                    Node<T> lost = current.greater;
                    lost.parent = null;
                    current.greater = parent;
                    parent.parent = current;
                    this.addToSubtree(parent, lost, false);
                }
            } else if (parent.greater != null && parent.greater == current) {
                // RIGHT
                parent.greater = null;

                if (current.lesser == null) {
                    current.lesser = parent;
                    parent.parent = current;
                } else {
                    Node<T> lost = current.lesser;
                    lost.parent = null;
                    current.lesser = parent;
                    parent.parent = current;
                    this.addToSubtree(parent, lost, false);
                }
            } else {
                // We really shouldn't get here
                System.err.println("YIKES! Parent should have at least one non-NULL child which should be me.");
            }
            if (parent == root) {
                root = current;
                root.parent = null;
            }
            parent = (TreapNode<T>)current.parent;
        }
    }

    private static class TreapNode<T extends Comparable<T>> extends Node<T> {

        private Integer priority = Integer.MIN_VALUE;

        private TreapNode(TreapNode<T> parent, int priority, T value) {
            super(parent,value);
            this.priority = priority;
        }

        private TreapNode(TreapNode<T> parent, T value) {
            this(parent, RANDOM.nextInt(RANDOM_SIZE), value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("priorty=").append(priority).append(" value=").append(value);
            if (parent != null)  builder.append(" parent=").append(parent.value);
            builder.append("\n");
            if (lesser != null) builder.append("left=").append(lesser.toString()).append("\n");
            if (greater != null) builder.append("right=").append(greater.toString()).append("\n");
            return builder.toString();
        }
    }
}
