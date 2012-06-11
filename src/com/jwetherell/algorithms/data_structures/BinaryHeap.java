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

    public enum HeapType { Tree, Array };
    public enum Type { MIN, MAX };

    /**
     * Number of nodes in the heap.
     * 
     * @return Number of nodes in the heap.
     */
    public abstract int size();

    /**
     * Add value to the heap.
     * 
     * @param value to add to the heap.
     */
    public abstract void add(T value);

    /**
     * Does the value exist in the heap. Warning
     * this is a O(n) operation.
     * 
     * @param value to locate in the heap.
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

    /**
     * Heap up the heap from this node.
     * 
     * @param node to heap up.
     */
    protected abstract void heapUp(Node<T> node);

    /**
     * Heap down the heap from this node.
     * 
     * @param node to heap down.
     */
    protected abstract void heapDown(Node<T> node);

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

        private static final int GROW_IN_CHUNK_SIZE = 50;
        private static final int SHRINK_IN_CHUNK_SIZE = 50;

        private Type type = Type.MIN;      
        private int size = 0;
        private HeapNode<T>[] array = null;


        /**
         * Get the parent index of this index, will return Integer.MIN_VALUE if no parent 
         * is possible.
         * 
         * @param index of the node to find a parent for.
         * @return index of parent node or Integer.MIN_VALUE if no parent.
         */
        private static final int getParentIndex(int index) {
            if (index>0) return (int) Math.floor((index-1)/2);
            else return Integer.MIN_VALUE;
        }

        /**
         * Get the left child index of this index.
         * 
         * @param index of the node to find a left child for.
         * @return index of left child node.
         */
        private static final int getLeftIndex(int index) {
            return 2*index+1;
        }

        /**
         * Get the right child index of this index.
         * 
         * @param index of the node to find a right child for.
         * @return index of right child node.
         */
        private static final int getRightIndex(int index) {
            return 2*index+2;
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
         * @param type Heap type.
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
        @SuppressWarnings("unchecked")
        public void add(T value) {
            if (array == null) array = new HeapNode[GROW_IN_CHUNK_SIZE];
            HeapNode<T> newNode = new HeapNode<T>(size,value);
            add(newNode);
        }

        /**
         * Add newNode to the heap.
         * 
         * @param newNode HeapNode<T> to add to the heap.
         */
        private void add(HeapNode<T> newNode) {
            if (size>=array.length) {
                HeapNode<T>[] temp = Arrays.copyOf(array, size+GROW_IN_CHUNK_SIZE);
                array = temp;
            }
            array[size++] = newNode;

            heapUp(newNode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(T value) {
            if (array==null || array.length==0) return false;
            for (int i=0; i<size; i++) {
                HeapNode<T> node = array[i];
                if (node.value.equals(value)) return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void heapUp(Node<T> node) {
            HeapNode<T> heapNode = (HeapNode<T>) node;
            while (heapNode != null) {
                int parentIndex = getParentIndex(heapNode.index);
                if (parentIndex<0) break;
                HeapNode<T> parent = this.array[parentIndex];

                if ( (type == Type.MIN && parent != null && node.value.compareTo(parent.value) < 0) || 
                     (type == Type.MAX && parent != null && node.value.compareTo(parent.value) > 0)
                ){
                    // Node is less than parent, switch node with parent
                    int nodeIndex = heapNode.index;
                    heapNode.index = parentIndex;
                    this.array[parentIndex] = heapNode;
                    parent.index = nodeIndex;
                    this.array[nodeIndex] = parent;
                } else {
                    heapNode = parent;
                }
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        protected void heapDown(Node<T> node) {
            HeapNode<T> heapNode = (HeapNode<T>) node;
            int leftIndex = getLeftIndex(heapNode.index);
            int rightIndex = getRightIndex(heapNode.index);
            HeapNode<T> left = (leftIndex!=Integer.MIN_VALUE && leftIndex<this.size)?this.array[leftIndex]:null;
            HeapNode<T> right = (rightIndex!=Integer.MIN_VALUE && rightIndex<this.size)?this.array[rightIndex]:null;

            if (left == null && right == null) {
                // Nothing to do here
                return;
            }

            HeapNode<T> nodeToMove = null;
            if ( (type == Type.MIN && left != null && right != null && node.value.compareTo(left.value) > 0 && node.value.compareTo(right.value) > 0) || 
                 (type == Type.MAX && left != null && right != null && node.value.compareTo(left.value) < 0 && node.value.compareTo(right.value) < 0)
            ) {
                // Both children are greater/lesser than node
                if ((type == Type.MIN && right.value.compareTo(left.value) < 0) || 
                    (type == Type.MAX && right.value.compareTo(left.value) > 0)
                ) {
                    // Right is greater/lesser than left
                    nodeToMove = right;
                } else if ( (type == Type.MIN && left.value.compareTo(right.value) < 0) || 
                            (type == Type.MAX && left.value.compareTo(right.value) > 0)
                ){
                    // Left is greater/lesser than right
                    nodeToMove = left;
                } else {
                    // Both children are equal, use right
                    nodeToMove = right;
                }
            } else if ( (type == Type.MIN && right != null && node.value.compareTo(right.value) > 0) || 
                        (type == Type.MAX && right != null && node.value.compareTo(right.value) < 0)
            ) {
                // Right is greater/lesser than node
                nodeToMove = right;
            } else if ( (type == Type.MIN && left != null && node.value.compareTo(left.value) > 0) || 
                        (type == Type.MAX && left != null && node.value.compareTo(left.value) < 0)
            ) {
                // Left is greater/lesser than node
                nodeToMove = left;
            }
            // No node to move, stop recursion
            if (nodeToMove == null) return;

            // Re-factor heap sub-tree
            int nodeIndex = heapNode.index;
            int nodeToMoveIndex = nodeToMove.index;
            heapNode.index = nodeToMoveIndex;
            this.array[nodeToMoveIndex] = heapNode;
            nodeToMove.index = nodeIndex;
            this.array[nodeIndex] = nodeToMove;

            heapDown(heapNode);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean validate() {
            if (array.length==0) return true;

            Node<T> root = array[0];
            if (root==null) return true;
            return validateNode(root);
        }

        /**
         * Validate the node for the heap invariants.
         * 
         * @param node to validate for.
         * @return True if node is valid.
         */
        private boolean validateNode(Node<T> node) {
            HeapNode<T> heapNode = (HeapNode<T>) node;
            int leftIndex = getLeftIndex(heapNode.index);
            int rightIndex = getRightIndex(heapNode.index);

            //We shouldn't ever have a right node without a left in a heap
            if (rightIndex!=Integer.MIN_VALUE && leftIndex==Integer.MIN_VALUE) return false;

            if (leftIndex!=Integer.MIN_VALUE && leftIndex<size) {
                Node<T> left = this.array[leftIndex];
                if ((type == Type.MIN && node.value.compareTo(left.value) < 0) || (type == Type.MAX && node.value.compareTo(left.value) > 0)) {
                    return validateNode(left);
                } else {
                    return false;
                }
            }
            if (rightIndex!=Integer.MIN_VALUE && rightIndex<size) {
                Node<T> right = this.array[rightIndex];
                if ((type == Type.MIN && node.value.compareTo(right.value) < 0) || (type == Type.MAX && node.value.compareTo(right.value) > 0)) {
                    return validateNode(right);
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
            if (array==null || array.length==0) return nodes;

            for (int i=0; i<size; i++) {
                HeapNode<T> node = this.array[i];
                nodes[i] = node.value;
            }
            return nodes;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public T getHeadValue() {
            T result = null;
            if (array==null || array.length==0) return result;

            Node<T> root = array[0];
            if (root != null) result = root.value;
            return result;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public T removeHead() {
            T result = null;
            if (array==null || array.length==0) return result;

            result = array[0].value;
            HeapNode<T> lastNode = array[--size];
            array[size] = null;

            if (size<0) {
                array = null;
                return result;
            }

            lastNode.index = 0;
            array[0] = lastNode;

            if (array.length-size>=SHRINK_IN_CHUNK_SIZE) {
                HeapNode<T>[] temp = Arrays.copyOf(array, size);
                array = temp;
            }

            heapDown(lastNode);
            
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

            public static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree) {
                if (tree.array.length==0) return "Tree has no nodes.";

                HeapNode<T> root = tree.array[0];
                if (root == null) return "Tree has no nodes.";
                return getString(tree, root, "", true);
            }

            private static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree, HeapNode<T> node, String prefix, boolean isTail) {
                StringBuilder builder = new StringBuilder();

                builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
                List<HeapNode<T>> children = null;
                int leftIndex = getLeftIndex(node.index);
                int rightIndex = getRightIndex(node.index);
                if (leftIndex != Integer.MIN_VALUE || rightIndex != Integer.MIN_VALUE) {
                    children = new ArrayList<HeapNode<T>>(2);
                    if (leftIndex != Integer.MIN_VALUE && leftIndex<tree.size) {
                        HeapNode<T> left = tree.array[leftIndex];
                        children.add(left);
                    }
                    if (rightIndex != Integer.MIN_VALUE && rightIndex<tree.size) {
                        HeapNode<T> right = tree.array[rightIndex];
                        children.add(right);
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

        private static class HeapNode<T extends Comparable<T>> extends Node<T> {

            private int index = 0;
            
            private HeapNode(int index, T value) {
                super(value);
                this.index = index;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                int parentIndex = getParentIndex(index);
                int leftIndex = getLeftIndex(index);
                int rightIndex = getRightIndex(index);
                return "value=" + value + 
                       " parent=" + ((parentIndex != Integer.MIN_VALUE) ? parentIndex : "NULL") + 
                       " left=" + ((leftIndex != Integer.MIN_VALUE) ? leftIndex : "NULL") + 
                       " right=" + ((rightIndex != Integer.MIN_VALUE) ? rightIndex : "NULL");
            }
        }
    }

    public static class BinaryHeapTree<T extends Comparable<T>> extends BinaryHeap<T> {

        private Type type = Type.MIN;      
        private int size = 0;
        private HeapNode<T> root = null;        


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
         * @param type Heap type.
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
         * @param index of the Node to get directions for.
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
            add(new HeapNode<T>(null, value));
        }

        private void add(HeapNode<T> newNode) {
            if (root == null) {
                root = newNode;
                size++;
                return;
            }

            HeapNode<T> node = root;
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
            int[] directions = getDirections(size - 1); // Directions to the last node
            HeapNode<T> lastNode = root;
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
            HeapNode<T> lastNodeParent = null;
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
         * @param startingNode node rooted sub-tree to search in.
         * @param value to search for.
         * @return Node<T> which equals value in sub-tree or NULL if not found.
         */
        private Node<T> getNode(HeapNode<T> startingNode, T value) {
            Node<T> result = null;
            if (startingNode != null && startingNode.value.equals(value)) {
                result = startingNode;
            } else if (startingNode != null && !startingNode.value.equals(value)) {
                HeapNode<T> left = startingNode.left;
                if (left != null) {
                    result = getNode(left, value);
                    if (result != null) return result;
                }
                HeapNode<T> right = startingNode.right;
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
            if (root==null) return false;
            Node<T> node = getNode(root, value);
            return (node!=null);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        protected void heapUp(Node<T> node) {
            while (node != null) {
                HeapNode<T> heapNode = (HeapNode<T>) node;
                HeapNode<T> parent = heapNode.parent;

                if ( (type == Type.MIN && parent != null && node.value.compareTo(parent.value) < 0) || 
                     (type == Type.MAX && parent != null && node.value.compareTo(parent.value) > 0)
                ){
                    // Node is less than parent, switch node with parent
                    HeapNode<T> grandParent = parent.parent;
                    HeapNode<T> parentLeft = parent.left;
                    HeapNode<T> parentRight = parent.right;

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
         * {@inheritDoc}
         */
        @Override
        protected void heapDown(Node<T> node) {
            HeapNode<T> heapNode = (HeapNode<T>) node;
            HeapNode<T> left = heapNode.left;
            HeapNode<T> right = heapNode.right;

            if (left == null && right == null) {
                // Nothing to do here
                return;
            }

            HeapNode<T> nodeToMove = null;

            if ( (type == Type.MIN && left != null && right != null && node.value.compareTo(left.value) > 0 && node.value.compareTo(right.value) > 0) || 
                 (type == Type.MAX && left != null && right != null && node.value.compareTo(left.value) < 0 && node.value.compareTo(right.value) < 0)
            ) {
                // Both children are greater/lesser than node
                if ((type == Type.MIN && right.value.compareTo(left.value) < 0) || 
                    (type == Type.MAX && right.value.compareTo(left.value) > 0)
                ) {
                    // Right is greater/lesser than left
                    nodeToMove = right;
                } else if ( (type == Type.MIN && left.value.compareTo(right.value) < 0) || 
                            (type == Type.MAX && left.value.compareTo(right.value) > 0)
                ){
                    // Left is greater/lesser than right
                    nodeToMove = left;
                } else {
                    // Both children are equal, use right
                    nodeToMove = right;
                }
            } else if ( (type == Type.MIN && right != null && node.value.compareTo(right.value) > 0) || 
                        (type == Type.MAX && right != null && node.value.compareTo(right.value) < 0)
            ) {
                // Right is greater than node
                nodeToMove = right;
            } else if ( (type == Type.MIN && left != null && node.value.compareTo(left.value) > 0) || 
                        (type == Type.MAX && left != null && node.value.compareTo(left.value) < 0)
            ) {
                // Left is greater than node
                nodeToMove = left;
            }
            // No node to move, stop recursion
            if (nodeToMove == null) return;

            // Re-factor heap sub-tree
            HeapNode<T> nodeParent = heapNode.parent;
            if (nodeParent == null) {
                // heap down the root
                root = nodeToMove;
                root.parent = null;

                HeapNode<T> nodeToMoveLeft = nodeToMove.left;
                HeapNode<T> nodeToMoveRight = nodeToMove.right;
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

                HeapNode<T> nodeLeft = heapNode.left;
                HeapNode<T> nodeRight = heapNode.right;
                HeapNode<T> nodeToMoveLeft = nodeToMove.left;
                HeapNode<T> nodeToMoveRight = nodeToMove.right;
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
            if (root==null) return true;
            return validateNode(root);
        }

        /**
         * Validate node for heap invariants.
         * 
         * @param node to validate for.
         * @return True if node is valid.
         */
        private boolean validateNode(Node<T> node) {
            HeapNode<T> left = ((HeapNode<T>)node).left;
            HeapNode<T> right = ((HeapNode<T>)node).right;
            
            //We shouldn't ever have a right node without a left in a heap
            if (right!=null && left==null) return false;

            if (left!=null) {
                if ((type == Type.MIN && node.value.compareTo(left.value) < 0) || (type == Type.MAX && node.value.compareTo(left.value) > 0)) {
                    return validateNode(left);
                } else {
                    return false;
                }
            }
            if (right!=null) {
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
         * @param node to populate.
         * @param index of node in array.
         * @param array where the node lives.
         */
        private void getNodeValue(Node<T> node, int index, T[] array) {
            array[index] = node.value;
            index = (index * 2) + 1;

            Node<T> left = ((HeapNode<T>)node).left;
            if (left != null) getNodeValue(left, index, array);
            Node<T> right = ((HeapNode<T>)node).right;
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

            private static <T extends Comparable<T>> String getString(HeapNode<T> node, String prefix, boolean isTail) {
                StringBuilder builder = new StringBuilder();

                builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
                List<HeapNode<T>> children = null;
                if (node.left != null || node.right != null) {
                    children = new ArrayList<HeapNode<T>>(2);
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

        private static class HeapNode<T extends Comparable<T>> extends Node<T> {

            private HeapNode<T> parent = null;
            private HeapNode<T> left = null;
            private HeapNode<T> right = null;


            private HeapNode(HeapNode<T> parent, T value) {
                super(value);
                this.parent = parent;
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
    }

    private abstract static class Node<T extends Comparable<T>> {

        protected T value = null;


        protected Node(T value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value;
        }
    }
}
