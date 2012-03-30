package com.jwetherell.algorithms.data_structures;


/**
 * Linked List (doubly link). A linked list is a data structure consisting of a group of nodes which together represent a sequence. 
 * 
 * http://en.wikipedia.org/wiki/Linked_list
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LinkedList<T> {

    private Node<T> head = null;
    private int size = 0;
    
    public LinkedList() {
        head = null;
        size = 0;
    }
    
    public LinkedList(Comparable<T>[] nodes) {
        this();
        populate(nodes);
    }
    
    private void populate(Comparable<T>[] nodes) {
        for (Comparable<T> n : nodes) {
            add(new Node<T>(n));
        }
    }
    
    public void add(Comparable<T> value) {
        add(new Node<T>(value));
    }
    
    public void add(Node<T> node) {
        if (head==null) {
            head = node;
        } else {
            Node<T> prev = null;
            Node<T> next = head;
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

    @SuppressWarnings("unchecked")
    public boolean remove(Comparable<T> value) {
        Node<T> node = head;
        while (node!=null && (node.value.compareTo((T)value))!=0) {
            node = node.nextNode;
        }
        if (node==null) return false;

        Node<T> prev = node.previousNode;
        Node<T> next = node.nextNode;
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

    public Comparable<T> get(int index) {
        Comparable<T> result = null;
        Node<T> node = head;
        int i = 0;
        while (node!=null && i<index) {
            node = node.nextNode;
            i++;
        }
        if (node!=null) result = node.value;
        return result;
    }

    @SuppressWarnings("unchecked")
    public T getHeadValue() {
        T result = null;
        if (head!=null) result = (T)head.value;
        return result;
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
        Node<T> node = head;
        while (node!=null) {
            builder.append(node.value).append(", ");
            node = node.nextNode;
        }
        return builder.toString();
    }
    
    private static class Node<T> {
        private Comparable<T> value = null;
        private Node<T> previousNode = null;
        private Node<T> nextNode = null;
        
        private Node(Comparable<T> value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value="+value+
                   " previous="+((previousNode!=null)?previousNode.value:"NULL")+
                   " next="+((nextNode!=null)?nextNode.value:"NULL");
        }
    }
}
