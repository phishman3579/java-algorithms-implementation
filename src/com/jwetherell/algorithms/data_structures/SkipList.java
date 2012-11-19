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
public class SkipList<T extends Comparable<T>> {

    private int size = 0;
    private List<List<ExpressNode<T>>> lanes = null;
    private Node<T> head = null;
    private Node<T> tail = null;

    /**
     * Default constructor.
     */
    public SkipList() {
    }

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
        if (numberOfLanes == 0) return;

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
                expressNode.nextNode.isAnOffRamp = false;
                if (j > 0) {
                    // Non-first node should be moved forward by the width
                    // amount
                    for (int k = 0; k < width; k++) {
                        startNode = startNode.nextNode;
                    }
                }
                expressNode.nextNode = startNode;
                expressNode.nextNode.isAnOffRamp = true;
                expressNode.width = width;
            }
        }
    }

    /**
     * Add value to list.
     * 
     * @param value
     *            to add to list.
     */
    public void add(T value) {
        Node<T> node = new Node<T>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<T> prev = tail;
            prev.nextNode = node;
            tail = node;
        }
        node.index = size;

        size++;

        generateExpressLanes();
    }

    /**
     * Remove value from list.
     * 
     * @param value
     *            to remove from list.
     * @return True if value was removed from list or false if not found.
     */
    public boolean remove(T value) {
        // Find the node
        Node<T> prev = null;
        Node<T> node = head;
        while (node != null && (!node.value.equals(value))) {
            prev = node;
            node = node.nextNode;
        }
        if (node == null) return false;

        Node<T> oldNode = node;

        // Update the tail, if needed
        if (node.equals(tail)) tail = prev;

        Node<T> next = node.nextNode;
        if (prev != null && next != null) {
            prev.nextNode = next;
        } else if (prev != null && next == null) {
            prev.nextNode = null;
        } else if (prev == null && next != null) {
            // Node is the head
            head = next;
        } else {
            // prev==null && next==null
            head = null;
            lanes = null;
        }

        while (next != null) {
            next.index--;
            next = next.nextNode;
        }

        size--;

        if (oldNode.isAnOffRamp) refactorExpressLanes();

        return true;
    }

    /**
     * Does the list contain value.
     * 
     * @param value
     *            to locate in the list.
     * @return True if value is in list.
     */
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
                                node = expressNode.nextNode;
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
            node = node.nextNode;
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
                                node = expressNode.nextNode;
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
            node = node.nextNode;
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
     * Number of values in the list.
     * 
     * @return number of values in list.
     */
    public int size() {
        return size;
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
                aNode = aNode.nextNode;
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
            this.nextNode = pointer;
        }

        protected int getIndex() {
            return nextNode.getIndex();
        }

        protected T getValue() {
            return nextNode.getValue();
        }

        private Node<T> getNodeFromExpress() {
            Node<T> nextNode = this.nextNode;
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
            if (nextNode != null && (nextNode instanceof ExpressNode)) {
                ExpressNode<T> eNode = (ExpressNode<T>) nextNode;
                Node<T> pointerRoot = eNode.getNodeFromExpress();
                builder.append("index=").append(getIndex());
                builder.append(" value=").append(getValue());
                builder.append(" width=").append(width);
                builder.append(" pointer=[").append(pointerRoot.value).append("]");
            } else {
                builder.append("index=").append(getIndex());
                builder.append(" value=").append(getValue());
                builder.append(" width=").append(width);
                if (nextNode != null) builder.append(" node=[").append(nextNode.value).append("=").append(nextNode.isAnOffRamp).append("]");
            }
            return builder.toString();
        }
    }

    private static class Node<T extends Comparable<T>> {

        protected T value = null;
        protected int index = Integer.MIN_VALUE;
        protected Node<T> nextNode = null;
        protected boolean isAnOffRamp = false;

        private Node() {
        }

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
}
