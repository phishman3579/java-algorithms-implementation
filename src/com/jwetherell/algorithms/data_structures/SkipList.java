package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Skip List. A skip list is a data structure for storing a sorted list of items
 * using a hierarchy of linked lists that connect increasingly sparse
 * subsequences of the items. These auxiliary lists allow item lookup with
 * efficiency comparable to balanced binary search trees.
 * 
 * Not the best implementation, still a work in progress. The main problem is
 * the generation and re-factoring of express lanes.
 * 
 * http://en.wikipedia.org/wiki/Skip_list
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<T>> implements IList<T> {

    private int size = 0;
    private List<List<ExpressNode<T>>> lanes = null;
    private Node<T> head = null;
    private Node<T> tail = null;

    public SkipList() { }

    /**
     * Determine the number of express lanes based on the size of the list.
     * 
     * @return number of express lanes.
     */
    private int determineNumberOfExpressLanes() {
        int number = (int) Math.ceil(Math.log10(size) / Math.log10(2));
        if (number > 0) number--;
        return number;
    }

    /**
     * Generate the express lanes.
     */
    private void generateExpressLanes() {
        int numberOfLanes = determineNumberOfExpressLanes();
        // No need to do anything if number of lanes is zero
        if (numberOfLanes == 0)
            return;

        int numberOfNodes = size / 2;
        // No need to do anything if there are no nodes in the lane
        if (numberOfNodes == 0) return;

        if (lanes == null) lanes = new ArrayList<List<ExpressNode<T>>>(numberOfLanes);

        // If the number of lanes and the number of nodes is the same, don't do
        // anything
        if (numberOfLanes == lanes.size() && numberOfNodes == lanes.get(0).size()) return;

        int width = 0;
        List<ExpressNode<T>> expressLane = null;
        Node<T> node = null;
        List<ExpressNode<T>> previousLane = null;
        int prevIndex = 0;
        ExpressNode<T> expressNode = null;
        boolean reuse = false;
        for (int i = 0; i < numberOfLanes; i++) {
            // Re-factor each express lane
            width = size / numberOfNodes;
            expressLane = null;
            if (i < lanes.size()) {
                // Previously added lane, will be re-added at the end
                expressLane = lanes.get(i);
                reuse = true;
            } else {
                // New lane
                expressLane = new ArrayList<ExpressNode<T>>(numberOfNodes);
                reuse = false;
            }

            for (int j = 0; j < numberOfNodes; j++) {
                // Re-factor each node
                if (j < expressLane.size()) {
                    // Previously added node
                    expressLane.get(j).width = width;
                } else {
                    // New node
                    node = null;
                    if (i == 0) {
                        // First lane (direct connection to nodes)
                        node = this.getNode(j * width);
                        node.isAnOffRamp = true;
                    } else {
                        // Non-first lane (connection to express nodes)
                        previousLane = lanes.get(i - 1);
                        prevIndex = j * 2;
                        node = previousLane.get(prevIndex);
                    }
                    expressNode = new ExpressNode<T>(width, node);
                    expressLane.add(expressNode);
                }
            }
            if (!reuse) lanes.add(expressLane);
            numberOfNodes = numberOfNodes / 2;
        }
    }

    /**
     * Re-factor the express lanes at the index.
     */
    private void refactorExpressLanes() {
        if (lanes == null || lanes.size() == 0) return;

        int numberOfLanes = determineNumberOfExpressLanes();
        if (numberOfLanes < lanes.size()) {
            // If the number of express lanes have been reduced then remove the
            // last lane
            lanes.remove(lanes.size() - 1);
            if (lanes.size() == 0) return;
        }

        Node<T> startNode = null;
        List<ExpressNode<T>> expressLanes = null;
        ExpressNode<T> expressNode = null;
        int numberOfNodes = size / 2;
        int width = 0;
        for (int i = 0; i < lanes.size(); i++) {
            width = size / numberOfNodes;
            if (i == 0) startNode = head;
            expressLanes = lanes.get(i);
            if (numberOfNodes < expressLanes.size()) {
                // If the number of nodes in this express lane has been reduced
                // then remove the last node
                expressLanes.remove(expressLanes.size() - 1);
            }

            numberOfNodes = numberOfNodes / 2;

            // We only need to re-factor the first lane, all the rest of the
            // lanes are pointers
            if (i > 0) continue;

            for (int j = 0; j < expressLanes.size(); j++) {
                // Re-factor the nodes in each express lane
                expressNode = expressLanes.get(j);
                expressNode.next.isAnOffRamp = false;
                if (j > 0) {
                    // Non-first node should be moved forward by the width
                    // amount
                    for (int k = 0; k < width; k++) {
                        startNode = startNode.next;
                    }
                }
                expressNode.next = startNode;
                expressNode.next.isAnOffRamp = true;
                expressNode.width = width;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        Node<T> node = new Node<T>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> prev = tail;
            prev.next = node;
            node.prev = prev;
            tail = node;
        }
        node.index = size;

        size++;

        generateExpressLanes();

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T value) {
        // Find the node
        Node<T> prev = null;
        Node<T> node = head;
        while (node != null && (!node.value.equals(value))) {
            prev = node;
            node = node.next;
        }
        if (node == null) return false;

        Node<T> oldNode = node;

        // Update the tail, if needed
        if (node.equals(tail)) tail = prev;

        Node<T> next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
            next.prev = prev;
        } else if (prev != null && next == null) {
            prev.next = null;
        } else if (prev == null && next != null) {
            // Node is the head
            head = next;
            next.prev = null;
        } else {
            // prev==null && next==null
            head = null;
            lanes = null;
        }

        while (next != null) {
            next.index--;
            next = next.next;
        }

        size--;

        if (oldNode.isAnOffRamp) refactorExpressLanes();

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        Node<T> node = null;
        Node<T> prev = null;
        List<ExpressNode<T>> lane = null;
        ExpressNode<T> expressNode = null;
        if (lanes != null && lanes.size() > 0) {
            int currentLane = lanes.size() - 1;
            lane = lanes.get(currentLane);
            int currentIndex = 0;
            node = lane.get(currentIndex);
            while (true) {
                if (node instanceof ExpressNode) {
                    // If the node is an ExpressNode
                    expressNode = (ExpressNode<T>) node;
                    if (expressNode.getValue().compareTo(value) < 0) {
                        prev = node;
                        currentIndex++;
                        if (currentIndex >= lane.size()) {
                            // No more express nodes in express lane
                            currentLane--;
                            if (currentLane < 0) {
                                // No more express lanes, go to regular nodes
                                node = expressNode.next;
                            } else {
                                // Get next express lane
                                lane = lanes.get(currentLane);
                                node = lane.get(--currentIndex);
                            }
                        } else {
                            // next node in lane
                            node = lane.get(currentIndex);
                        }
                    } else if (expressNode.getValue().compareTo(value) == 0) {
                        return true;
                    } else {
                        // node's index is greater than the index we are looking
                        // for, go back
                        currentLane--;
                        if (currentLane < 0) {
                            // No more express lanes, go to regular nodes
                            node = prev;
                            break;
                        } else {
                            // Get next express lane
                            lane = lanes.get(currentLane);
                            node = lane.get(currentIndex);
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            node = head;
        }

        while (node != null && node.getValue().compareTo(value) < 0) {
            node = node.next;
        }
        if (node != null && node.getValue().compareTo(value) == 0) {
            return true;
        }

        return false;
    }

    /**
     * Get node for index.
     * 
     * @param index
     *            to get node for.
     * @return node for index.
     */
    private Node<T> getNode(int index) {
        Node<T> node = null;
        Node<T> prev = null;
        List<ExpressNode<T>> lane = null;
        ExpressNode<T> expressNode = null;
        if (lanes.size() > 0) {
            int currentLane = lanes.size() - 1;
            lane = lanes.get(currentLane);
            int currentIndex = 0;
            node = lane.get(currentIndex);
            while (true) {
                if (node instanceof ExpressNode) {
                    // If the node is an ExpressNode
                    expressNode = (ExpressNode<T>) node;
                    if (expressNode.getIndex() < index) {
                        prev = node;
                        currentIndex++;
                        if (currentIndex >= lane.size()) {
                            // No more express nodes in express lane
                            currentLane--;
                            if (currentLane < 0) {
                                // No more express lanes, go to regular nodes
                                node = expressNode.next;
                            } else {
                                // Get next express lane
                                lane = lanes.get(currentLane);
                                node = lane.get(--currentIndex);
                            }
                        } else {
                            // next node in lane
                            node = lane.get(currentIndex);
                        }
                    } else if (expressNode.getIndex() == index) {
                        node = expressNode;
                        break;
                    } else {
                        // node's index is greater than the index we are looking
                        // for, go back
                        currentLane--;
                        if (currentLane < 0) {
                            // No more express lanes, go to regular nodes
                            node = prev;
                            break;
                        } else {
                            // Get next express lane
                            lane = lanes.get(currentLane);
                            node = lane.get(currentIndex);
                        }
                    }
                } else {
                    break;
                }
            }
        } else {
            node = head;
        }

        while (node != null && node.getIndex() < index) {
            node = node.next;
        }

        return node;
    }

    /**
     * Get value at index.
     * 
     * @param index
     *            to get value for.
     * @return value of index or NULL if the index is not populated.
     */
    public T get(int index) {
        Node<T> node = this.getNode(index);
        if (node != null) return node.getValue();
        else return null;
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
    public boolean validate() {
        java.util.Set<T> keys = new java.util.HashSet<T>();
        Node<T> node = head;
        if (node!=null) {
            if (node.prev!=null) return false;
            if (node!=null && !validate(node,keys)) return false;
        }
        return (keys.size()==size());
    }

    private boolean validate(Node<T> node, java.util.Set<T> keys) {
        if (node.value==null) return false;
        keys.add(node.value);

        Node<T> child = node.next;
        if (child!=null) {
            if (!child.prev.equals(node)) return false;
            if (!validate(child,keys)) return false;
        } else {
            if (!node.equals(tail)) return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.List<T> toList() {
        return (new JavaCompatibleSkipList<T>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<T> toCollection() {
        return (new JavaCompatibleSkipList<T>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Node<T> aNode = this.head;
        if (aNode != null) {
            builder.append("Nodes={ ");
            while (aNode != null) {
                builder.append(aNode.getIndex()).append("=[").append(aNode.getValue()).append(",").append(aNode.isAnOffRamp).append("]");
                aNode = aNode.next;
                if (aNode != null) builder.append(", ");
            }
            builder.append(" }\n");
        }

        if (lanes != null) {
            for (int i = 0; i < lanes.size(); i++) {
                builder.append("Lane=").append(i).append("\n");
                List<ExpressNode<T>> lane = lanes.get(i);
                for (int j = 0; j < lane.size(); j++) {
                    ExpressNode<T> node = lane.get(j);
                    builder.append(node);
                    if (j < (lane.size() - 1)) builder.append(", ");
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private static class ExpressNode<T extends Comparable<T>> extends Node<T> {

        private int width = Integer.MIN_VALUE;

        private ExpressNode(int width, Node<T> pointer) {
            this.width = width;
            this.next = pointer;
        }

        protected int getIndex() {
            return next.getIndex();
        }

        protected T getValue() {
            return next.getValue();
        }

        private Node<T> getNodeFromExpress() {
            Node<T> nextNode = this.next;
            if (nextNode != null && (nextNode instanceof ExpressNode)) {
                ExpressNode<T> eNode = (ExpressNode<T>) nextNode;
                return eNode.getNodeFromExpress();
            } else {
                return nextNode;
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (next != null && (next instanceof ExpressNode)) {
                ExpressNode<T> eNode = (ExpressNode<T>) next;
                Node<T> pointerRoot = eNode.getNodeFromExpress();
                builder.append("index=").append(getIndex());
                builder.append(" value=").append(getValue());
                builder.append(" width=").append(width);
                builder.append(" pointer=[").append(pointerRoot.value).append("]");
            } else {
                builder.append("index=").append(getIndex());
                builder.append(" value=").append(getValue());
                builder.append(" width=").append(width);
                if (next != null) builder.append(" node=[").append(next.value).append("=").append(next.isAnOffRamp).append("]");
            }
            return builder.toString();
        }
    }

    private static class Node<T extends Comparable<T>> {

        protected T value = null;
        protected int index = Integer.MIN_VALUE;
        protected Node<T> next = null;
        protected Node<T> prev = null;
        protected boolean isAnOffRamp = false;

        private Node() { }

        private Node(T value) {
            this.value = value;
        }

        protected int getIndex() {
            return index;
        }

        protected T getValue() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (index != Integer.MIN_VALUE) builder.append("index=").append(index).append(" ");
            if (value != null) builder.append("value=").append(value).append(" ");
            builder.append("isAnOffRamp=").append(isAnOffRamp);
            return builder.toString();
        }
    }

    public static class JavaCompatibleSkipList<T extends Comparable<T>> extends java.util.AbstractSequentialList<T> {

        private SkipList<T> list = null;

        public JavaCompatibleSkipList(SkipList<T> list) {
            this.list = list;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return list.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return list.remove((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return list.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return list.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.ListIterator<T> listIterator(int index) {
            return (new SkipListListIterator<T>(list));
        }

        private static class SkipListListIterator<T extends Comparable<T>> implements java.util.ListIterator<T> {

            private int index = 0;

            private SkipList<T> list = null;
            private SkipList.Node<T> prev = null;
            private SkipList.Node<T> next = null;
            private SkipList.Node<T> last = null;

            private SkipListListIterator(SkipList<T> list) {
                this.list = list;
                this.next = list.head;
                if (this.next!=null) this.prev = next.prev;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void add(T value) {
                SkipList.Node<T> node = new SkipList.Node<T>(value);
 
                SkipList.Node<T> n = this.next;

                if (this.prev!=null) this.prev.next = node;
                node.prev = this.prev;

                node.next = n;
                if (n!=null) n.prev = node;

                this.next = node;
                if (this.prev==null) list.head = node; // new root
                list.size++;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (last==null) return;

                SkipList.Node<T> p = last.prev;
                SkipList.Node<T> n = last.next;
                if (p!=null) p.next = n;
                if (n!=null) n.prev = p;
                if (last.equals(list.head)) list.head = n;
                if (last.equals(list.tail)) list.tail = p;
                list.size--;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void set(T value) {
                if (last!=null) last.value = value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return (next!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasPrevious() {
                return (prev!=null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int nextIndex() {
                return index;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int previousIndex() {
                return index-1;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                if (next == null) throw new java.util.NoSuchElementException();
                index++;
                last = next;
                prev = next;
                next = next.next;

                return last.value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T previous() {
                if (prev == null) throw new java.util.NoSuchElementException();
                index--;
                last = prev;
                next = prev;
                prev = next.prev;

                return last.value;
            }
        }
    }
}
