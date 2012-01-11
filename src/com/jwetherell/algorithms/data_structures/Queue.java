package com.jwetherell.algorithms.data_structures;


/**
 * Queue.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Queue {
    
    private Node head = null;
    private Node tail = null;
    private int size = 0;
    
    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public Queue(int[] nodes) {
        this();
        populate(nodes);
    }
    
    private void populate(int[] nodes) {
        for (int n : nodes) {
            enqueue(new Node(n));
        }
    }
    
    public void enqueue(int value) {
        enqueue(new Node(value));
    }
    
    private void enqueue(Node node) {
        if (head==null) {
            head = node;
            tail = node;
        } else {
            Node oldHead = head;
            head = node;
            node.nextNode = oldHead;
            oldHead.previousNode = node;
        }
        size++;
    }
    
    public int dequeue() {
        int result = Integer.MIN_VALUE;
        if (tail!=null) {
            result = tail.value;
            
            Node prev = tail.previousNode;
            if (prev!=null) {
                prev.nextNode = null;
                tail = prev;
            } else {
                head = null;
                tail = null;
            }
            size--;
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node node = head;
        while (node!=null) {
            builder.append(node.value).append(", ");
            node = node.nextNode;
        }
        return builder.toString();
    }

    private static class Node {
        private Integer value = null;
        private Node previousNode = null;
        private Node nextNode = null;
        
        private Node(int value) {
            this.value = value;
        }

        public String toString() {
            return "value="+value+
                   " previous="+((previousNode!=null)?previousNode.value:"NULL")+
                   " next="+((nextNode!=null)?nextNode.value:"NULL");
        }
    }
}
