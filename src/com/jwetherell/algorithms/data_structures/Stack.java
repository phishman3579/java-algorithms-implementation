package com.jwetherell.algorithms.data_structures;


/**
 * Stack.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Stack {

    private Node top = null;
    private int size = 0;
    
    public Stack() {
        top = null;
        size = 0;
    }
    
    public Stack(int[] nodes) {
        this();
        populate(nodes);
    }
    
    private void populate(int[] nodes) {
        for (int n : nodes) {
            push(new Node(n));
        }
    }
    
    public void push(int value) {
        push(new Node(value));
    }
    
    private void push(Node node) {
        if (top==null) {
            top = node;
        } else {
            Node oldTop = top;
            top = node;
            top.belowNode = oldTop;
            oldTop.aboveNode = top;
        }
        size++;
    }
    
    public int pop() {
        Node nodeToRemove = top;
        top = nodeToRemove.belowNode;
        if (top!=null) top.aboveNode = null;
        
        int value = -1;
        if (nodeToRemove!=null) {
            value = nodeToRemove.value;
            size--;
        }
        return value;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node node = top;
        while (node!=null) {
            builder.append(node.value).append(", ");
            node = node.belowNode;
        }
        return builder.toString();
    }
    
    private static class Node {
        private Integer value = null;
        private Node aboveNode = null;
        private Node belowNode = null;
        
        private Node(int value) {
            this.value = value;
        }

        public String toString() {
            return "value="+value+
                   " above="+((aboveNode!=null)?aboveNode.value:"NULL")+
                   " below="+((belowNode!=null)?belowNode.value:"NULL");
        }
    }
}
