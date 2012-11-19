package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Arrays;
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
public abstract class BinaryHeap<T extends Comparable<T>> {

    public enum HeapType {
        Tree, Array
    };

    public enum Type {
        MIN, MAX
    };

    /**
     * Number of nodes in the heap.
     * 
     * @return Number of nodes in the heap.
     */
    public abstract int size();

    /**
     * Add value to the heap.
     * 
     * @param value
     *            to add to the heap.
     */
    public abstract void add(T value);

    /**
     * Does the value exist in the heap. Warning this is a O(n) operation.
     * 
     * @param value
     *            to locate in the heap.
     * @return True if the value is in heap.
     */
    public abstract boolean contains(T value);

    /**
     * Get the heap in array form.
     * 
     * @return array representing the heap.
     */
    public abstract T[] getHeap();

    /**
     * Get the value of the head node from the heap.
     * 
     * @return value of the head node.
     */
    public abstract T getHeadValue();

    /**
     * Remove the head node from the heap.
     * 
     * @return value of the head node.
     */
    public abstract T removeHead();

    /**
     * Validate the heap according to the invariants.
     * 
     * @return True if the heap is valid.
     */
    public abstract boolean validate();

    public static <T extends Comparable<T>> BinaryHeap<T> createHeap(HeapType type) {
        switch (type) {
            case Array:
                return new BinaryHeapArray<T>();
            default:
                return new BinaryHeapTree<T>();
        }
    }

    /**
     * A binary heap using an array to hold the nodes.
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     */
    public static class BinaryHeapArray<T extends Comparable<T>> extends BinaryHeap<T> {

        private static final int MINIMUM_SIZE = 10;

        private Type type = Type.MIN;
        private int size = 0;
        @SuppressWarnings("unchecked")
        private T[] array = (T[]) new Comparable[MINIMUM_SIZE];

        /**
         * Get the parent index of this index, will return Integer.MIN_VALUE if
         * no parent is possible.
         * 
         * @param index
         *            of the node to find a parent for.
         * @return index of parent node or Integer.MIN_VALUE if no parent.
         */
        private static final int getParentIndex(int index) {
            if (index > 0) return (int) Math.floor((index - 1) / 2);
            else return Integer.MIN_VALUE;
        }

        /**
         * Get the left child index of this index.
         * 
         * @param index
         *            of the node to find a left child for.
         * @return index of left child node.
         */
        private static final int getLeftIndex(int index) {
            return 2 * index + 1;
        }

        /**
         * Get the right child index of this index.
         * 
         * @param index
         *            of the node to find a right child for.
         * @return index of right child node.
         */
        private static final int getRightIndex(int index) {
            return 2 * index + 2;
        }

        /**
         * Constructor for heap, defaults to a min-heap.
         */
        public BinaryHeapArray() {
            size = 0;
        }

        /**
         * Constructor for heap.
         * 
         * @param type
         *            Heap type.
         */
        public BinaryHeapArray(Type type) {
            this();
            this.type = type;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(T value) {
            if (size >= array.length) {
                array = Arrays.copyOf(array, ((size * 3) / 2) + 1);
            }
            array[size] = value;

            heapUp(size++);
        }

        protected void heapUp(int nodeIndex) {
            T value = this.array[nodeIndex];
            while (nodeIndex >= 0) {
                int parentIndex = getParentIndex(nodeIndex);
                if (parentIndex < 0) break;
                T parent = this.array[parentIndex];

                if ((type == Type.MIN && parent != null && value.compareTo(parent) < 0) || (type == Type.MAX && parent != null && value.compareTo(parent) > 0)) {
                    // Node is less than parent, switch node with parent
                    this.array[parentIndex] = value;
                    this.array[nodeIndex] = parent;
                } else {
                    nodeIndex = parentIndex;
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            if (array.length == 0) return false;
            for (int i = 0; i < size; i++) {
                T node = array[i];
                if (node.equals(value)) return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean validate() {
            if (array.length == 0) return true;
            return validateNode(0);
        }

        /**
         * Validate the node for the heap invariants.
         * 
         * @param node
         *            to validate for.
         * @return True if node is valid.
         */
        private boolean validateNode(int index) {
            T value = this.array[index];
            int leftIndex = getLeftIndex(index);
            int rightIndex = getRightIndex(index);

            // We shouldn't ever have a right node without a left in a heap
            if (rightIndex != Integer.MIN_VALUE && leftIndex == Integer.MIN_VALUE) return false;

            if (leftIndex != Integer.MIN_VALUE && leftIndex < size) {
                T left = this.array[leftIndex];
                if ((type == Type.MIN && value.compareTo(left) < 0) || (type == Type.MAX && value.compareTo(left) > 0)) {
                    return validateNode(leftIndex);
                } else {
                    return false;
                }
            }
            if (rightIndex != Integer.MIN_VALUE && rightIndex < size) {
                T right = this.array[rightIndex];
                if ((type == Type.MIN && value.compareTo(right) < 0) || (type == Type.MAX && value.compareTo(right) > 0)) {
                    return validateNode(rightIndex);
                } else {
                    return false;
                }
            }

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public T[] getHeap() {
            T[] nodes = (T[]) new Comparable[size];
            if (array.length == 0) return nodes;

            for (int i = 0; i < size; i++) {
                T node = this.array[i];
                nodes[i] = node;
            }
            return nodes;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T getHeadValue() {
            if (array.length == 0) return null;
            return array[0];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T removeHead() {
            T result = null;
            if (array.length == 0) return result;

            // Get the root element in the array
            result = array[0];

            // Save the last element of the array and then null out the last
            // element's index
            int lastIndex = --size;
            T lastNode = array[lastIndex];
            array[size] = null;

            // No more elements in the heap
            if (size <= 0) {
                return result;
            }

            // Put the last element in the root's spot
            array[0] = lastNode;

            if (size >= MINIMUM_SIZE && size < array.length / 2) {
                array = Arrays.copyOf(array, size);
            }

            // Heap down from the root
            heapDown(0);

            return result;
        }

        protected void heapDown(int index) {
            T value = this.array[index];
            int leftIndex = getLeftIndex(index);
            int rightIndex = getRightIndex(index);
            T left = (leftIndex != Integer.MIN_VALUE && leftIndex < this.size) ? this.array[leftIndex] : null;
            T right = (rightIndex != Integer.MIN_VALUE && rightIndex < this.size) ? this.array[rightIndex] : null;

            if (left == null && right == null) {
                // Nothing to do here
                return;
            }

            T nodeToMove = null;
            int nodeToMoveIndex = -1;
            if ((type == Type.MIN && left != null && right != null && value.compareTo(left) > 0 && value.compareTo(right) > 0)
                    || (type == Type.MAX && left != null && right != null && value.compareTo(left) < 0 && value.compareTo(right) < 0)) {
                // Both children are greater/lesser than node
                if ((type == Type.MIN && right.compareTo(left) < 0) || (type == Type.MAX && right.compareTo(left) > 0)) {
                    // Right is greater/lesser than left
                    nodeToMove = right;
                    nodeToMoveIndex = rightIndex;
                } else if ((type == Type.MIN && left.compareTo(right) < 0) || (type == Type.MAX && left.compareTo(right) > 0)) {
                    // Left is greater/lesser than right
                    nodeToMove = left;
                    nodeToMoveIndex = leftIndex;
                } else {
                    // Both children are equal, use right
                    nodeToMove = right;
                    nodeToMoveIndex = rightIndex;
                }
            } else if ((type == Type.MIN && right != null && value.compareTo(right) > 0) || (type == Type.MAX && right != null && value.compareTo(right) < 0)) {
                // Right is greater/lesser than node
                nodeToMove = right;
                nodeToMoveIndex = rightIndex;
            } else if ((type == Type.MIN && left != null && value.compareTo(left) > 0) || (type == Type.MAX && left != null && value.compareTo(left) < 0)) {
                // Left is greater/lesser than node
                nodeToMove = left;
                nodeToMoveIndex = leftIndex;
            }
            // No node to move, stop recursion
            if (nodeToMove == null) return;

            // Re-factor heap sub-tree
            this.array[nodeToMoveIndex] = value;
            this.array[index] = nodeToMove;

            heapDown(nodeToMoveIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return HeapPrinter.getString(this);
        }

        protected static class HeapPrinter {

            public static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree) {
                if (tree.array.length == 0) return "Tree has no nodes.";

                T root = tree.array[0];
                if (root == null) return "Tree has no nodes.";
                return getString(tree, 0, "", true);
            }

            private static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree, int index, String prefix, boolean isTail) {
                StringBuilder builder = new StringBuilder();

                T value = tree.array[index];
                builder.append(prefix + (isTail ? "└── " : "├── ") + value + "\n");
                List<Integer> children = null;
                int leftIndex = getLeftIndex(index);
                int rightIndex = getRightIndex(index);
                if (leftIndex != Integer.MIN_VALUE || rightIndex != Integer.MIN_VALUE) {
                    children = new ArrayList<Integer>(2);
                    if (leftIndex != Integer.MIN_VALUE && leftIndex < tree.size) {
                        children.add(leftIndex);
                    }
                    if (rightIndex != Integer.MIN_VALUE && rightIndex < tree.size) {
                        children.add(rightIndex);
                    }
                }
                if (children != null) {
                    for (int i = 0; i < children.size() - 1; i++) {
                        builder.append(getString(tree, children.get(i), prefix + (isTail ? "    " : "│   "), false));
                    }
                    if (children.size() >= 1) {
                        builder.append(getString(tree, children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                    }
                }

                return builder.toString();
            }
        }
    }

    public static class BinaryHeapTree<T extends Comparable<T>> extends BinaryHeap<T> {

        private Type type = Type.MIN;
        private int size = 0;
        private Node<T> root = null;

        /**
         * Constructor for heap, defaults to a min-heap.
         */
        public BinaryHeapTree() {
            root = null;
            size = 0;
        }

        /**
         * Constructor for heap.
         * 
         * @param type
         *            Heap type.
         */
        public BinaryHeapTree(Type type) {
            this();
            this.type = type;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * Get the navigation directions through the tree to the index.
         * 
         * @param index
         *            of the Node to get directions for.
         * @return Integer array representing the directions to the index.
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
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

        /**
         * Remove the root node.
         */
        private void removeRoot() {
            // Find the last node
            int[] directions = getDirections(size - 1); // Directions to the
                                                        // last node
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

        /**
         * Get the node in the startingNode sub-tree which has the value.
         * 
         * @param startingNode
         *            node rooted sub-tree to search in.
         * @param value
         *            to search for.
         * @return Node<T> which equals value in sub-tree or NULL if not found.
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            if (root == null) return false;
            Node<T> node = getNode(root, value);
            return (node != null);
        }

        /**
         * Heap up the heap from this node.
         * 
         * @param node
         *            to heap up.
         */
        protected void heapUp(Node<T> node) {
            while (node != null) {
                Node<T> heapNode = (Node<T>) node;
                Node<T> parent = heapNode.parent;

                if ((type == Type.MIN && parent != null && node.value.compareTo(parent.value) < 0)
                        || (type == Type.MAX && parent != null && node.value.compareTo(parent.value) > 0)) {
                    // Node is less than parent, switch node with parent
                    Node<T> grandParent = parent.parent;
                    Node<T> parentLeft = parent.left;
                    Node<T> parentRight = parent.right;

                    parent.left = heapNode.left;
                    if (parent.left != null) parent.left.parent = parent;
                    parent.right = heapNode.right;
                    if (parent.right != null) parent.right.parent = parent;

                    if (parentLeft != null && parentLeft.equals(node)) {
                        heapNode.left = parent;
                        heapNode.right = parentRight;
                        if (parentRight != null) parentRight.parent = heapNode;
                    } else {
                        heapNode.right = parent;
                        heapNode.left = parentLeft;
                        if (parentLeft != null) parentLeft.parent = heapNode;
                    }
                    parent.parent = heapNode;

                    if (grandParent == null) {
                        // New root.
                        heapNode.parent = null;
                        root = heapNode;
                    } else {
                        Node<T> grandLeft = grandParent.left;
                        if (grandLeft != null && grandLeft.equals(parent)) {
                            grandParent.left = heapNode;
                        } else {
                            grandParent.right = heapNode;
                        }
                        heapNode.parent = grandParent;
                    }
                } else {
                    node = heapNode.parent;
                }
            }
        }

        /**
         * Heap down the heap from this node.
         * 
         * @param node
         *            to heap down.
         */
        protected void heapDown(Node<T> node) {
            Node<T> heapNode = (Node<T>) node;
            Node<T> left = heapNode.left;
            Node<T> right = heapNode.right;

            if (left == null && right == null) {
                // Nothing to do here
                return;
            }

            Node<T> nodeToMove = null;

            if ((type == Type.MIN && left != null && right != null && node.value.compareTo(left.value) > 0 && node.value.compareTo(right.value) > 0)
                    || (type == Type.MAX && left != null && right != null && node.value.compareTo(left.value) < 0 && node.value.compareTo(right.value) < 0)) {
                // Both children are greater/lesser than node
                if ((type == Type.MIN && right.value.compareTo(left.value) < 0) || (type == Type.MAX && right.value.compareTo(left.value) > 0)) {
                    // Right is greater/lesser than left
                    nodeToMove = right;
                } else if ((type == Type.MIN && left.value.compareTo(right.value) < 0) || (type == Type.MAX && left.value.compareTo(right.value) > 0)) {
                    // Left is greater/lesser than right
                    nodeToMove = left;
                } else {
                    // Both children are equal, use right
                    nodeToMove = right;
                }
            } else if ((type == Type.MIN && right != null && node.value.compareTo(right.value) > 0)
                    || (type == Type.MAX && right != null && node.value.compareTo(right.value) < 0)) {
                // Right is greater than node
                nodeToMove = right;
            } else if ((type == Type.MIN && left != null && node.value.compareTo(left.value) > 0)
                    || (type == Type.MAX && left != null && node.value.compareTo(left.value) < 0)) {
                // Left is greater than node
                nodeToMove = left;
            }
            // No node to move, stop recursion
            if (nodeToMove == null) return;

            // Re-factor heap sub-tree
            Node<T> nodeParent = heapNode.parent;
            if (nodeParent == null) {
                // heap down the root
                root = nodeToMove;
                root.parent = null;

                Node<T> nodeToMoveLeft = nodeToMove.left;
                Node<T> nodeToMoveRight = nodeToMove.right;
                if (heapNode.left.equals(nodeToMove)) {
                    nodeToMove.left = heapNode;
                    nodeToMove.right = heapNode.right;
                } else {
                    nodeToMove.left = heapNode.left;
                    nodeToMove.right = heapNode;
                }
                heapNode.parent = nodeToMove;
                heapNode.left = nodeToMoveLeft;
                heapNode.right = nodeToMoveRight;
            } else {
                // heap down a left
                if (nodeParent.left.equals(node)) {
                    nodeParent.left = nodeToMove;
                    nodeToMove.parent = nodeParent;
                } else {
                    nodeParent.right = nodeToMove;
                    nodeToMove.parent = nodeParent;
                }

                Node<T> nodeLeft = heapNode.left;
                Node<T> nodeRight = heapNode.right;
                Node<T> nodeToMoveLeft = nodeToMove.left;
                Node<T> nodeToMoveRight = nodeToMove.right;
                if (heapNode.left.equals(nodeToMove)) {
                    nodeToMove.right = nodeRight;
                    if (nodeRight != null) nodeRight.parent = nodeToMove;

                    nodeToMove.left = heapNode;
                    heapNode.parent = nodeToMove;
                } else {
                    nodeToMove.left = nodeLeft;
                    if (nodeLeft != null) nodeLeft.parent = nodeToMove;

                    nodeToMove.right = heapNode;
                    heapNode.parent = nodeToMove;
                }

                heapNode.left = nodeToMoveLeft;
                if (nodeToMoveLeft != null) nodeToMoveLeft.parent = heapNode;
                heapNode.right = nodeToMoveRight;
                if (nodeToMoveRight != null) nodeToMoveRight.parent = heapNode;
            }

            heapDown(node);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean validate() {
            if (root == null) return true;
            return validateNode(root);
        }

        /**
         * Validate node for heap invariants.
         * 
         * @param node
         *            to validate for.
         * @return True if node is valid.
         */
        private boolean validateNode(Node<T> node) {
            Node<T> left = ((Node<T>) node).left;
            Node<T> right = ((Node<T>) node).right;

            // We shouldn't ever have a right node without a left in a heap
            if (right != null && left == null) return false;

            if (left != null) {
                if ((type == Type.MIN && node.value.compareTo(left.value) < 0) || (type == Type.MAX && node.value.compareTo(left.value) > 0)) {
                    return validateNode(left);
                } else {
                    return false;
                }
            }
            if (right != null) {
                if ((type == Type.MIN && node.value.compareTo(right.value) < 0) || (type == Type.MAX && node.value.compareTo(right.value) > 0)) {
                    return validateNode(right);
                } else {
                    return false;
                }
            }

            return true;
        }

        /**
         * Populate the node in the array at the index.
         * 
         * @param node
         *            to populate.
         * @param index
         *            of node in array.
         * @param array
         *            where the node lives.
         */
        private void getNodeValue(Node<T> node, int index, T[] array) {
            array[index] = node.value;
            index = (index * 2) + 1;

            Node<T> left = ((Node<T>) node).left;
            if (left != null) getNodeValue(left, index, array);
            Node<T> right = ((Node<T>) node).right;
            if (right != null) getNodeValue(right, index + 1, array);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public T[] getHeap() {
            T[] nodes = (T[]) new Comparable[size];
            if (root != null) getNodeValue(root, 0, nodes);
            return nodes;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T getHeadValue() {
            T result = null;
            if (root != null) result = root.value;
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T removeHead() {
            T result = null;
            if (root != null) {
                result = root.value;
                removeRoot();
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

        protected static class HeapPrinter {

            public static <T extends Comparable<T>> void print(BinaryHeapTree<T> tree) {
                System.out.println(getString(tree.root, "", true));
            }

            public static <T extends Comparable<T>> String getString(BinaryHeapTree<T> tree) {
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

        private static class Node<T extends Comparable<T>> {

            private T value = null;
            private Node<T> parent = null;
            private Node<T> left = null;
            private Node<T> right = null;

            private Node(Node<T> parent, T value) {
                this.value = value;
                this.parent = parent;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return "value=" + value + " parent=" + ((parent != null) ? parent.value : "NULL") + " left=" + ((left != null) ? left.value : "NULL")
                        + " right=" + ((right != null) ? right.value : "NULL");
            }
        }
    }
}
