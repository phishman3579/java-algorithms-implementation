package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * Skip List. A skip list is a data structure for storing a sorted list of items
 * using a hierarchy of linked lists that connect increasingly sparse
 * subsequences of the items. These auxiliary lists allow item lookup with
 * efficiency comparable to balanced binary search trees.
 * 
 * Not the best implementation, still a work in progress. The main problem is,
 * it regenerates the all the express lanes when a node is added/removed if the
 * number of express lanes changes.
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
        return (int) Math.ceil(Math.log10(size) / Math.log10(2));
    }
    
    private void generateExpressLanes() {
        int expressLanes = determineNumberOfExpressLanes();
        if (lanes == null) lanes = new ArrayList<List<ExpressNode<T>>>(expressLanes);
        int length = size;
        int width = 0;
        int index = 0;
        for (int i = 0; i < expressLanes; i++) {
            width = size / length;
            List<ExpressNode<T>> expressLane = null;
            if (i<lanes.size()) expressLane = lanes.get(i);
            else expressLane = new ArrayList<ExpressNode<T>>();

            for (int j = 0; j < length; j++) {
                if (j<expressLane.size()) {
                    expressLane.get(j).width = width;
                } else {
                    Node<T> node = null;
                    if (i == 0) {
                        node = this.getNode(j);
                    } else {
                        List<ExpressNode<T>> previousLane = lanes.get(i - 1);
                        int prevIndex = j * 2;
                        node = previousLane.get(prevIndex);
                    }
                    index = j;
                    ExpressNode<T> expressNode = new ExpressNode<T>(index, width, node);
                    expressLane.add(expressNode);
                }
            }
            if (!lanes.contains(expressLane)) lanes.add(expressLane);
            length = length / 2;
        }
    }

    private void refactorExpressLanes(int index) {
        if (lanes.size()==0) return;

        List<ExpressNode<T>> expressLanes = lanes.get(0);
        for (int i=0; i<expressLanes.size();) {
            ExpressNode<T> expressNode = expressLanes.get(i);
            if (expressNode.index<index) {
                //Ignore
                i++;
            } else if (expressNode.index==index) {
                expressLanes.remove(expressNode);
                if (lanes.size()==1 && expressLanes.size()==1) {
                    lanes.clear();
                }
                expressNode.index--;
            } else {
                expressNode.index--;
                i++;
            }
        }

        int expressLanesSize = determineNumberOfExpressLanes();
        if (expressLanesSize<lanes.size()) {
            lanes.remove(lanes.size()-1);
        }

        if (lanes.size()==0) return;
        
        expressLanes = lanes.get(0);
        ExpressNode<T> startExpress = expressLanes.get(0);
        Node<T> originalStart = startExpress.getNodeFromExpress();
        int length = size/2;
        int width = 0;
        for (int i=1; i<lanes.size(); i++) {
            width = size / length;
            Node<T> startNode = originalStart;
            expressLanes = lanes.get(i);
            if (length<expressLanes.size()) {
                expressLanes.remove(expressLanes.size()-1);
            }
            for (int j=0; j<expressLanes.size(); j++) {
                ExpressNode<T> expressNode = expressLanes.get(j);
                if (j==0) {
                    expressNode.nextNode = startNode;
                } else {
                    for (int k=0; k<width; k++) {
                        startNode = startNode.nextNode;
                        if (startNode==null) break;
                    }
                    expressNode.nextNode = startNode;
                }
                expressNode.width = width;
            }
            length = length / 2;
        }
    }

    public void add(T value) {
        Node<T> node = new Node<T>(value);
        if (head == null) {
            head = node;
        } else {
            Node<T> prev = null;
            Node<T> next = head;
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

        int oldIndex = node.index;
        
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

        refactorExpressLanes(oldIndex);

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

        if (lanes.size() > 0) {
            int currentLane = lanes.size() - 1;
            int currentIndex = 0;
            List<ExpressNode<T>> lane = lanes.get(currentLane);
            node = lane.get(currentIndex);
            while (true) {
                if (node instanceof ExpressNode) {
                    // If the node is an ExpressNode
                    ExpressNode<T> expressNode = (ExpressNode<T>) node;
                    if (index < (currentIndex + 1) * expressNode.width) {
                        // If the index is less than the current ExpressNode's
                        // cumulative width, try to go down a level.
                        if (currentLane > 0) lane = lanes.get(--currentLane); // This will be true when the
                                                                              // nextNode is a ExpressNode.
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
            builder.append(aNode.index).append("=").append(aNode.value);
            aNode = aNode.nextNode;
            if (aNode!=null) builder.append(", ");
            else builder.append("\n");
        }

        for (int i = 0; i < lanes.size(); i++) {
            builder.append("Lane=").append(i).append("\n");
            List<ExpressNode<T>> lane = lanes.get(i);
            for (int j = 0; j < lane.size(); j++) {
                ExpressNode<T> node = lane.get(j);
                builder.append(node);
            }
            builder.append("\n");
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
                if (nextNode != null) builder.append(" node=[").append(nextNode.value).append("]\t");
            }
            return builder.toString();
        }
    }

    private static class Node<T extends Comparable<T>> {

        private T value = null;
        protected int index = Integer.MIN_VALUE;
        protected Node<T> nextNode = null;

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
            builder.append("next=").append((nextNode != null) ? nextNode.value : "NULL");
            return builder.toString();
        }
    }
}
