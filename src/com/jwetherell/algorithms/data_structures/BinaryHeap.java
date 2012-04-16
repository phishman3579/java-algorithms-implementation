package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * A binary heap is a heap data structure created using a binary tree. It can be
 * seen as a binary tree with two additional constraints: 1) The shape property:
 * the tree is a complete binary tree; that is, all levels of the tree, except
 * possibly the last one (deepest) are fully filled, and, if the last level of
 * the tree is not complete, the nodes of that level are filled from left to
 * right. 2) The heap property: each node is right than or equal to each of its
 * children according to a comparison predicate defined for the data structure.
 * 
 * http://en.wikipedia.org/wiki/Binary_heap
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BinaryHeap<T extends Comparable<T>> {

    private Node<T> root = null;
    private int size = 0;

    public enum TYPE {
        MIN, MAX
    };

    private TYPE type = TYPE.MIN;

    public BinaryHeap() {
        root = null;
        size = 0;
    }

    public BinaryHeap(TYPE type) {
        this();
        this.type = type;
    }

    public BinaryHeap(T[] nodes) {
        this();
        populate(nodes);
    }

    public BinaryHeap(T[] nodes, TYPE type) {
        this(type);
        populate(nodes);
    }

    private void populate(T[] nodes) {
        for (T node : nodes) {
            add(new Node<T>(null, node));
        }
    }

    public int getSize() {
        return size;
    }

    private int[] getDirections(int index) {
        int directionsSize = (int) (Math.log10(index + 1) / Math.log10(2)) - 1;
        int[] directions = null;
        if (directionsSize > 0) {
            directions = new int[directionsSize];
            int i = directionsSize - 1;
            while (i >= 0) {
                index = (index - 1) / 2;
                directions[i--] = (index > 0 && index % 2 == 0) ? 1 : 0; // 0=left,
                                                                         // 1=right
            }
        }
        return directions;
    }

    public void add(T value) {
        add(new Node<T>(null, value));
    }

    private void add(Node<T> newNode) {
        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        Node<T> node = root;
        int[] directions = getDirections(size); // size == index of new node
        if (directions != null && directions.length > 0) {
            for (int d : directions) {
                if (d == 0) {
                    // Go left
                    node = node.left;
                } else {
                    // Go right
                    node = node.right;
                }
            }
        }
        if (node.left == null) {
            node.left = newNode;
        } else {
            node.right = newNode;
        }
        newNode.parent = node;

        size++;

        heapUp(newNode);
    }

    private void removeRoot(Node<T> node) {
        // Find the last node
        int[] directions = getDirections(size - 1); // Directions to the last node
        Node<T> lastNode = root;
        if (directions != null && directions.length > 0) {
            for (int d : directions) {
                if (d == 0) {
                    // Go left
                    lastNode = lastNode.left;
                } else {
                    // Go right
                    lastNode = lastNode.right;
                }
            }
        }
        Node<T> lastNodeParent = null;
        if (lastNode.right != null) {
            lastNodeParent = lastNode;
            lastNode = lastNode.right;
            lastNodeParent.right = null;
        } else if (lastNode.left != null) {
            lastNodeParent = lastNode;
            lastNode = lastNode.left;
            lastNodeParent.left = null;
        }

        lastNode.left = root.left;
        if (lastNode.left != null) lastNode.left.parent = lastNode;
        lastNode.right = root.right;
        if (lastNode.right != null) lastNode.right.parent = lastNode;
        lastNode.parent = null;

        if (!lastNode.equals(root)) root = lastNode;
        else root = null;

        size--;

        heapDown(lastNode);
    }

    private Node<T> getNode(Node<T> startingNode, T value) {
        Node<T> result = null;
        if (startingNode != null && startingNode.value.equals(value)) {
            result = startingNode;
        } else if (startingNode != null && !startingNode.value.equals(value)) {
            Node<T> left = startingNode.left;
            if (left != null) {
                result = getNode(left, value);
                if (result != null) return result;
            }
            Node<T> right = startingNode.right;
            if (right != null) {
                result = getNode(right, value);
                if (result != null) return result;
            }
        }
        return result;
    }

    public boolean contains(T value) {
        if (root==null) return false;
        Node<T> node = getNode(root, value);
        return (node!=null);
    }

    private void heapUp(Node<T> node) {
        while (node != null) {
            Node<T> parent = node.parent;

            int compare = (type == TYPE.MIN) ? -1 : 1;
            if (parent != null && node.value.compareTo(parent.value) == compare) {
                // Node is less than parent, switch node with parent
                Node<T> grandParent = parent.parent;
                Node<T> parentLeft = parent.left;
                Node<T> parentRight = parent.right;

                parent.left = node.left;
                if (parent.left != null) parent.left.parent = parent;
                parent.right = node.right;
                if (parent.right != null) parent.right.parent = parent;

                if (parentLeft != null && parentLeft.equals(node)) {
                    node.left = parent;
                    node.right = parentRight;
                    if (parentRight != null) parentRight.parent = node;
                } else {
                    node.right = parent;
                    node.left = parentLeft;
                    if (parentLeft != null) parentLeft.parent = node;
                }
                parent.parent = node;

                if (grandParent == null) {
                    // New root.
                    node.parent = null;
                    root = node;
                } else {
                    Node<T> grandLeft = grandParent.left;
                    if (grandLeft != null && grandLeft.equals(parent)) {
                        grandParent.left = node;
                    } else {
                        grandParent.right = node;
                    }
                    node.parent = grandParent;
                }
            } else {
                node = node.parent;
            }
        }
    }

    private void heapDown(Node<T> node) {
        Node<T> left = node.left;
        Node<T> right = node.right;

        if (left == null && right == null) {
            // Nothing to do here
            return;
        }

        Node<T> nodeToMove = null;
        int compare = (type == TYPE.MIN) ? 1 : -1; // reversed
        if (left != null && right != null && node.value.compareTo(left.value) == compare && node.value.compareTo(right.value) == compare) {
            // Both children are greater/lesser than node
            compare = (type == TYPE.MIN) ? -1 : 1;
            if (right.value.compareTo(left.value) == compare) {
                // Right is greater/lesser than left
                nodeToMove = right;
            } else if (left.value.compareTo(right.value) == compare) {
                // Left is greater/lesser than right
                nodeToMove = left;
            } else {
                // Both children are equal, use right
                nodeToMove = right;
            }
        } else if (right != null && node.value.compareTo(right.value) == compare) {
            // Right is greater than node
            nodeToMove = right;
        } else if (left != null && node.value.compareTo(left.value) == compare) {
            // Left is greater than node
            nodeToMove = left;
        }
        // No node to move, stop recursion
        if (nodeToMove == null) return;

        // Refactor heap sub-tree
        Node<T> nodeParent = node.parent;
        if (nodeParent == null) {
            // heap down the root
            root = nodeToMove;
            root.parent = null;

            Node<T> nodeToMoveLeft = nodeToMove.left;
            Node<T> nodeToMoveRight = nodeToMove.right;
            if (node.left.equals(nodeToMove)) {
                nodeToMove.left = node;
                nodeToMove.right = node.right;
            } else {
                nodeToMove.left = node.left;
                nodeToMove.right = node;
            }
            node.parent = nodeToMove;
            node.left = nodeToMoveLeft;
            node.right = nodeToMoveRight;
        } else {
            // heap down a left
            if (nodeParent.left.equals(node)) {
                nodeParent.left = nodeToMove;
                nodeToMove.parent = nodeParent;
            } else {
                nodeParent.right = nodeToMove;
                nodeToMove.parent = nodeParent;
            }

            Node<T> nodeLeft = node.left;
            Node<T> nodeRight = node.right;
            Node<T> nodeToMoveLeft = nodeToMove.left;
            Node<T> nodeToMoveRight = nodeToMove.right;
            if (node.left.equals(nodeToMove)) {
                nodeToMove.right = nodeRight;
                if (nodeRight != null) nodeRight.parent = nodeToMove;

                nodeToMove.left = node;
                node.parent = nodeToMove;
            } else {
                nodeToMove.left = nodeLeft;
                if (nodeLeft != null) nodeLeft.parent = nodeToMove;

                nodeToMove.right = node;
                node.parent = nodeToMove;
            }

            node.left = nodeToMoveLeft;
            if (nodeToMoveLeft != null) nodeToMoveLeft.parent = node;
            node.right = nodeToMoveRight;
            if (nodeToMoveRight != null) nodeToMoveRight.parent = node;
        }

        heapDown(node);
    }

    public boolean validate() {
        if (root==null) return true;
        return validateNode(root);
    }

    private boolean validateNode(Node<T> node) {
        Node<T> left = node.left;
        Node<T> right = node.right;
        
        //We shouldn't ever have a right node without a left in a heap
        if (right!=null && left==null) return false;
        
        int compare = (type == TYPE.MIN) ? -1 : 1;
        if (left!=null) {
            if (node.value.compareTo(left.value) == compare) return validateNode(left);
            else return false;
        }
        if (right!=null) {
            if (node.value.compareTo(right.value) == compare) return validateNode(right);
            else return false;
        }
        
        return true;
    }

    private void getNodeValue(Node<T> node, int index, T[] array) {
        array[index] = node.value;
        index = (index * 2) + 1;

        Node<T> left = node.left;
        if (left != null) getNodeValue(left, index, array);
        Node<T> right = node.right;
        if (right != null) getNodeValue(right, index + 1, array);
    }

    @SuppressWarnings("unchecked")
    public T[] getHeap() {
        T[] nodes = (T[]) new Comparable[size];
        if (root != null) getNodeValue(root, 0, nodes);
        return nodes;
    }

    public T getRootValue() {
        T result = null;
        if (root != null) result = root.value;
        return result;
    }

    public T removeRoot() {
        T result = null;
        if (root != null) {
            result = root.value;
            removeRoot(root);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return HeapPrinter.getString(this);
    }

    private static class Node<T extends Comparable<T>> {

        private T value = null;
        private Node<T> parent = null;
        private Node<T> left = null;
        private Node<T> right = null;

        private Node(Node<T> parent, T value) {
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
                   " left=" + ((left != null) ? left.value : "NULL") + 
                   " right=" + ((right != null) ? right.value : "NULL");
        }
    }

    protected static class HeapPrinter {

        public static <T extends Comparable<T>> void print(BinaryHeap<T> tree) {
            System.out.println(getString(tree.root, "", true));
        }

        public static <T extends Comparable<T>> String getString(BinaryHeap<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
            List<Node<T>> children = null;
            if (node.left != null || node.right != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.left != null) children.add(node.left);
                if (node.right != null) children.add(node.right);
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
