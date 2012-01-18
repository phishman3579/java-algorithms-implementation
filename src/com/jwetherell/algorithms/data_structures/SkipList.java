package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * Skip List. Not the best implementation.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SkipList {
    
    private int size = 0;
    private List<List<ExpressNode>> lanes = null;
    private Node head = null;
    
    public SkipList() { }
    
    public SkipList(int[] nodes) {
        this();
        
        populateLinkedList(nodes);
        generateExpressLanes();
    }
    
    private void populateLinkedList(int[] nodes) {
        for (int n : nodes) {
            add(n);
        }
    }
    
    private boolean refactorExpressLanes(int expressLanes) {
        if (expressLanes!=lanes.size()) return true;

        int length = size;
        for (int i=0; i<expressLanes; i++) {
            List<ExpressNode> expressLane = lanes.get(i);
            if (expressLane.size() != length) return true;
            length = length/2;
        }
        
        return false;
    }
    
    private void generateExpressLanes() {
        int expressLanes = (int)Math.ceil(Math.log10(size)/Math.log10(2));
        if (lanes==null) lanes = new ArrayList<List<ExpressNode>>(expressLanes);
        if (!refactorExpressLanes(expressLanes)) return;
        lanes.clear();
        int length = size;
        int width = 0;
        int index = 0;
        for (int i=0; i<expressLanes; i++) {
            width = size/length;
            List<ExpressNode> expressLane = new ArrayList<ExpressNode>();
            for (int j=0; j<length; j++) {
                Node node = null;
                if (i==0) {
                    node = this.getNode(j);
                } else {
                    List<ExpressNode> previousLane = lanes.get(i-1);
                    int prevIndex = j*2;
                    node = previousLane.get(prevIndex);
                }
                index = j;
                ExpressNode expressNode = new ExpressNode(index,width,node);
                expressLane.add(expressNode);
            }
            lanes.add(expressLane);
            length = length/2;
        }
    }
    
    public void add(int value) {
        add(new Node(value));
        generateExpressLanes();
    }

    public boolean remove(int value) {
        Node prev = null;
        Node node = head;
        while (node!=null && (node.value != value)) {
            prev = node;
            node = node.nextNode;
        }
        if (node==null) return false;

        Node next = node.nextNode;
        if (prev!=null && next!=null) {
            prev.nextNode = next;
        } else if (prev!=null && next==null) {
            prev.nextNode = null;
        } else if (prev==null && next!=null) {
            // Node is the head
            head = next;
        } else {
            // prev==null && next==null
            head = null;
        }
        
        int prevIndex = prev.index;
        node = prev;
        while (node!=null) {
            node = node.nextNode;
            if (node!=null) node.index = ++prevIndex;
        }
        size--;
        generateExpressLanes();
        return true;
    }
    
    private void add(Node node) {
        if (head==null) {
            head = node;
        } else {
            Node prev = null;
            Node next = head;
            while (next!=null) {
                prev = next;
                next = next.nextNode;
            }
            if (prev!=null) prev.nextNode =  node;
        }
        node.index = size;
        size++;
    }

    private Node getNode(int index) {
        Node node = null;

        if (lanes.size()>0) {
            int currentLane = lanes.size()-1;
            int currentIndex = 0;
            List<ExpressNode> lane = lanes.get(currentLane);
            node = lane.get(currentIndex);
            while (true) {
                if (node instanceof ExpressNode) {
                    // If the node is an ExpressNode
                    ExpressNode expressNode = (ExpressNode)node;
                    if (index<(currentIndex+1)*expressNode.width) {
                        // If the index is less than the current ExpressNode's cumulative width, try to go down a level.
                        if (currentLane>0) lane = lanes.get(--currentLane); // This will be true when the nextNode is a ExpressNode.
                        node = expressNode.nextNode;
                        currentIndex = node.index;
                    } else if (lane.size()>(expressNode.index+1)) {
                        // If the index greater than the current ExpressNode's cumulative width, try the next ExpressNode.
                        currentIndex = expressNode.index+1;
                        node = lane.get(currentIndex);
                    } else if (currentLane>0) {
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

        while (node!=null && node.index<index) {
            node = node.nextNode;
        } 

        return node;
    }

    public int get(int index) {
        Node node = this.getNode(index);
        if (node!=null) return node.value;
        else return Integer.MIN_VALUE;
    }
    
    public int getSize() {
        return size;
    }
    
    private static class ExpressNode extends Node {
        private Integer width = null;

        private ExpressNode(int index, int width, Node pointer) {
            this.width = width;
            this.index = index;
            this.nextNode = pointer;
        }

        private static Node getNodeFromExpress(ExpressNode node) {
            Node nextNode = node.nextNode;
            if (nextNode!=null && (nextNode instanceof ExpressNode)) {
                ExpressNode eNode = (ExpressNode) nextNode;
                return getNodeFromExpress(eNode);
            } else {
                return nextNode;
            }
        }
        
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (nextNode!=null && (nextNode instanceof ExpressNode)) {
                ExpressNode eNode = (ExpressNode) nextNode;
                Node pointerRoot = getNodeFromExpress(eNode);
                builder.append("width=").append(width).append(" pointer=[").append(pointerRoot.value).append("]");
            } else {
                builder.append("width=").append(width);
                if (nextNode!=null) builder.append(" ").append(nextNode.toString());
            }
            return builder.toString();
        }
    }
    
    private static class Node {
        private Integer value = null;
        protected Integer index = null;
        protected Node nextNode = null;
        
        private Node() {
            this.index = Integer.MIN_VALUE;
            this.value = Integer.MIN_VALUE;
        }
        
        private Node(int value) {
            this();
            this.value = value;
        }
        
        private Node(int index, int value) {
            this(value);
            this.index = index;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (index!=Integer.MIN_VALUE) builder.append("index=").append(index).append(" ");
            if (value!=Integer.MIN_VALUE) builder.append("value=").append(value).append(" ");
            builder.append("next=").append((nextNode!=null)?nextNode.value:"NULL");
            return builder.toString();
        }
    }
}
