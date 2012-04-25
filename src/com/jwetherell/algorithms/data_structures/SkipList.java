package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * Skip List. A skip list is a data structure for storing a sorted list of items
 * using a hierarchy of linked lists that connect increasingly sparse
 * subsequences of the items. These auxiliary lists allow item lookup with
 * efficiency comparable to balanced binary search trees.
 * 
 * Not the best implementation, still a work in progress. The main problem is the
 * generation and re-factoring of express lanes.
 * 
 * http://en.wikipedia.org/wiki/Skip_list
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SkipList<T extends Comparable<T>> {

    private int size = 0;
    private List<List<ExpressNode<T>>> lanes = null;
    private Node<T> head = null;

    public SkipList() { }

    private int determineNumberOfExpressLanes() {
        int number = (int) Math.ceil(Math.log10(size) / Math.log10(2));
        if (number>0) number--;
        return number;
    }
    
    private void generateExpressLanes() {
        int numberOfLanes = determineNumberOfExpressLanes();
        //No need to do anything if number of lanes is zero
        if (numberOfLanes==0) return;

        int numberOfNodes = size / 2;
        //No need to do anything if there are no nodes in the lane
        if (numberOfNodes==0) return;

        if (lanes == null) lanes = new ArrayList<List<ExpressNode<T>>>(numberOfLanes);

        //If the number of lanes and the number of nodes is the same, don't do anything
        if (numberOfLanes==lanes.size() && numberOfNodes==lanes.get(0).size()) return;

        int width = 0;
        int index = 0;
        List<ExpressNode<T>> expressLane = null;
        Node<T> node = null;
        List<ExpressNode<T>> previousLane = null;
        int prevIndex = 0;
        ExpressNode<T> expressNode = null;
        for (int i = 0; i < numberOfLanes; i++) {
            //Re-factor each express lane
            width = size / numberOfNodes;
            expressLane = null;
            if (i<lanes.size()) {
                //Previously added lane, will be re-added at the end
                expressLane = lanes.remove(i);
            } else {
                //New lane
                expressLane = new ArrayList<ExpressNode<T>>(numberOfNodes);
            }

            for (int j = 0; j < numberOfNodes; j++) {
                //Re-factor each node
                if (j<expressLane.size()) {
                    //Previously added node
                    expressLane.get(j).width = width;
                } else {
                    //New node
                    node = null;
                    if (i == 0) {
                        //First lane (direct connection to nodes)
                        node = this.getNode(j*width);
                        node.isAnOffRamp = true;
                    } else {
                        //Non-first lane (connection to express nodes)
                        previousLane = lanes.get(i - 1);
                        prevIndex = j * 2;
                        node = previousLane.get(prevIndex);
                    }
                    index = j;
                    expressNode = new ExpressNode<T>(index, width, node);
                    expressLane.add(expressNode);
                }
            }
            lanes.add(i,expressLane);
            numberOfNodes = numberOfNodes / 2;
        }
    }

    private void refactorExpressLanes(int index) {
        if (lanes.size()==0) return;

        int numberOfLanes = determineNumberOfExpressLanes();
        if (numberOfLanes<lanes.size()) {
            //If the number of express lanes have been reduced then remove the last lane
            lanes.remove(lanes.size()-1);
            if (lanes.size()==0) return;
        }

        Node<T> startNode = null;
        List<ExpressNode<T>> expressLanes = null;
        ExpressNode<T> expressNode = null;
        int numberOfNodes = size/2;
        int width = 0;
        for (int i=0; i<lanes.size(); i++) {
            width = size / numberOfNodes;
            if (i==0) startNode = head;   
            expressLanes = lanes.get(i);
            if (numberOfNodes<expressLanes.size()) {
                //If the number of nodes in this express lane has been reduced then remove the last node
                expressLanes.remove(expressLanes.size()-1);
                if (expressLanes.size()==0) break;
            }
            
            //We only need to re-factor the first lane, all the rest of the lanes are pointers
            if (i>0) break;

            for (int j=0; j<expressLanes.size(); j++) {
                //Re-factor the nodes in each express lane
                expressNode = expressLanes.get(j);
                expressNode.nextNode.isAnOffRamp = false;
                if (j>0) {
                    //Non-first node should be moved forward by the width amount
                    for (int k=0; k<width; k++) {
                        startNode = startNode.nextNode;
                    }
                }
                expressNode.nextNode = startNode;
                expressNode.nextNode.isAnOffRamp = true;
                expressNode.width = width;
            }
            numberOfNodes = numberOfNodes / 2;
        }
    }

    public void add(T value) {
        Node<T> node = new Node<T>(value);
        Node<T> prev = null;
        Node<T> next = null;
        if (head == null) {
            head = node;
        } else {
            prev = null;
            next = head;
            while (next != null) {
                prev = next;
                next = next.nextNode;
            }
            if (prev != null) prev.nextNode = node;
        }
        node.index = size;

        size++;

        generateExpressLanes();
    }

    public boolean remove(T value) {
        Node<T> prev = null;
        Node<T> node = head;
        while (node != null) {
            if (node.value.compareTo(value) == 0) break;
            prev = node;
            node = node.nextNode;
        }
        if (node == null) return false;

        Node<T> oldNode = node;
        int oldIndex = oldNode.index;
        
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
        }

        if (prev != null) {
            node = prev;
            int prevIndex = prev.index;
            while (node != null) {
                node = node.nextNode;
                if (node != null) node.index = ++prevIndex;
            }
        } else {
            while (next!=null) {
                next.index--;
                next = next.nextNode;
            }
        }

        size--;

        if (oldNode.isAnOffRamp) refactorExpressLanes(oldIndex);

        return true;
    }

    public boolean contains(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value.compareTo(value) == 0) return true;
            node = node.nextNode;
        }
        return false;
    }

    private Node<T> getNode(int index) {
        Node<T> node = null;

        List<ExpressNode<T>> lane = null;
        ExpressNode<T> expressNode = null;
        if (lanes.size() > 0) {
            int currentLane = lanes.size() - 1;
            int currentIndex = 0;
            lane = lanes.get(currentLane);
            node = lane.get(currentIndex);
            while (true) {
                if (node instanceof ExpressNode) {
                    // If the node is an ExpressNode
                    expressNode = (ExpressNode<T>) node;
                    if (index < (currentIndex + 1) * expressNode.width) {
                        // If the index is less than the current ExpressNode's
                        // cumulative width, try to go down a level.
                        if (currentLane > 0) {
                            // This will be true when the nextNode is a ExpressNode.
                            lane = lanes.get(--currentLane);
                        }
                        node = expressNode.nextNode;
                        currentIndex = node.index;
                    } else if (lane.size() > (expressNode.index + 1)) {
                        // If the index greater than the current ExpressNode's
                        // cumulative width, try the next ExpressNode.
                        currentIndex = expressNode.index + 1;
                        node = lane.get(currentIndex);
                    } else if (currentLane > 0) {
                        // We have run out of nextNodes, try going down a level.
                        lane = lanes.get(--currentLane);
                        node = expressNode.nextNode;
                        currentIndex = node.index;
                    } else {
                        // Yikes! I don't know how I got here. break, just in case.
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            node = head;
        }

        while (node != null && node.index < index) {
            node = node.nextNode;
        }

        return node;
    }

    public T get(int index) {
        Node<T> node = this.getNode(index);
        if (node != null) return node.value;
        else return null;
    }

    public int getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Node<T> aNode = this.head;
        if (aNode!=null) builder.append("Nodes=");
        while (aNode!=null) {
            builder.append(aNode.index).append("=[").append(aNode.value).append(",").append(aNode.isAnOffRamp).append("]");
            aNode = aNode.nextNode;
            if (aNode!=null) builder.append(", ");
            else builder.append("\n");
        }

        if (lanes!=null) {
            for (int i = 0; i < lanes.size(); i++) {
                builder.append("Lane=").append(i).append("\n");
                List<ExpressNode<T>> lane = lanes.get(i);
                for (int j = 0; j < lane.size(); j++) {
                    ExpressNode<T> node = lane.get(j);
                    builder.append(node);
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private static class ExpressNode<T extends Comparable<T>> extends Node<T> {

        private int width = Integer.MIN_VALUE;

        private ExpressNode(int index, int width, Node<T> pointer) {
            this.width = width;
            this.index = index;
            this.nextNode = pointer;
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
                builder.append("index=").append(index).append(" width=").append(width).append(" pointer=[").append(pointerRoot.value).append("]\t");
            } else {
                builder.append("index=").append(index).append(" width=").append(width);
                if (nextNode != null) builder.append(" node=[").append(nextNode.value).append("=").append(nextNode.isAnOffRamp).append("]\t");
            }
            return builder.toString();
        }
    }

    private static class Node<T extends Comparable<T>> {

        private T value = null;
        protected int index = Integer.MIN_VALUE;
        protected Node<T> nextNode = null;
        protected boolean isAnOffRamp = false;

        private Node() {
            this.value = null;
        }

        private Node(T value) {
            this.value = value;
        }

        private Node(int index, T value) {
            this(value);
            this.index = index;
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
