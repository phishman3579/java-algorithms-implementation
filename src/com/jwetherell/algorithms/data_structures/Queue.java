package com.jwetherell.algorithms.data_structures;


/**
 * Queue.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Queue<T> {
    
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;
    
    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public Queue(Comparable<T>[] nodes) {
        this();
        populate(nodes);
    }
    
    private void populate(Comparable<T>[] nodes) {
        for (Comparable<T> n : nodes) {
            enqueue(new Node<T>(n));
        }
    }
    
    public void enqueue(Comparable<T> value) {
        enqueue(new Node<T>(value));
    }
    
    private void enqueue(Node<T> node) {
        if (head==null) {
            head = node;
            tail = node;
        } else {
            Node<T> oldHead = head;
            head = node;
            node.nextNode = oldHead;
            oldHead.previousNode = node;
        }
        size++;
    }
    
    @SuppressWarnings("unchecked")
    public T dequeue() {
        T result = null;
        if (tail!=null) {
            result = (T)tail.value;
            
            Node<T> prev = tail.previousNode;
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
