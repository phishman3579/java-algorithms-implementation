package com.jwetherell.algorithms.data_structures;


/**
 * Linked List (double link).
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LinkedList {

    private Node head = null;
    private int size = 0;
    
    public LinkedList() {
        head = null;
        size = 0;
    }
    
    public LinkedList(int[] nodes) {
        this();
        populate(nodes);
    }
    
    private void populate(int[] nodes) {
        for (int n : nodes) {
            add(new Node(n));
        }
    }
    
    public void add(int value) {
        add(new Node(value));
    }
    
    public void add(Node node) {
        if (head==null) {
            head = node;
        } else {
            Node prev = null;
            Node next = head;
            while (next!=null) {
                prev = next;
                next = next.nextNode;
            }
            if (prev!=null) {
                prev.nextNode =  node;
                node.previousNode = prev;
            }
        }
        size++;
    }

    public boolean remove(int value) {
        Node node = head;
        while (node!=null && (node.value != value)) {
            node = node.nextNode;
        }
        if (node==null) return false;

        Node prev = node.previousNode;
        Node next = node.nextNode;
        if (prev!=null && next!=null) {
            prev.nextNode = next;
            next.previousNode = prev;
        } else if (prev!=null && next==null) {
            prev.nextNode = null;
        } else if (prev==null && next!=null) {
            // Node is the head
            next.previousNode = null;
            head = next;
        } else {
            // prev==null && next==null
            head = null;
        }
        size--;
        return true;
    }

    public int get(int index) {
        int result = Integer.MIN_VALUE;
        Node node = head;
        int i = 0;
        while (node!=null && i<index) {
            node = node.nextNode;
            i++;
        }
        if (node!=null) result = node.value;
        return result;
    }

    public int getHeadValue() {
        int result = Integer.MIN_VALUE;;
        if (head!=null) result = head.value;
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
