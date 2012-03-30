package com.jwetherell.algorithms.data_structures;


/**
 * Stack.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Stack<T> {

    private Node<T> top = null;
    private int size = 0;
    
    public Stack() {
        top = null;
        size = 0;
    }
    
    public Stack(Comparable<T>[] nodes) {
        this();
        populate(nodes);
    }
    
    private void populate(Comparable<T>[] nodes) {
        for (Comparable<T> n : nodes) {
            push(new Node<T>(n));
        }
    }
    
    public void push(Comparable<T> value) {
        push(new Node<T>(value));
    }
    
    private void push(Node<T> node) {
        if (top==null) {
            top = node;
        } else {
            Node<T> oldTop = top;
            top = node;
            top.belowNode = oldTop;
            oldTop.aboveNode = top;
        }
        size++;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        Node<T> nodeToRemove = top;
        top = nodeToRemove.belowNode;
        if (top!=null) top.aboveNode = null;
        
        T value = null;
        if (nodeToRemove!=null) {
            value = (T)nodeToRemove.value;
            size--;
        }
        return value;
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
        Node<T> node = top;
        while (node!=null) {
            builder.append(node.value).append(", ");
            node = node.belowNode;
        }
        return builder.toString();
    }
    
    private static class Node<T> {
        private Comparable<T> value = null;
        private Node<T> aboveNode = null;
        private Node<T> belowNode = null;
        
        private Node(Comparable<T> value) {
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value="+value+
                   " above="+((aboveNode!=null)?aboveNode.value:"NULL")+
                   " below="+((belowNode!=null)?belowNode.value:"NULL");
        }
    }
}
