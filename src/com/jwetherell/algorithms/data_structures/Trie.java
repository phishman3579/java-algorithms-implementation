package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * A trie, or prefix tree, is an ordered tree data structure that is used to store an associative array where the keys are usually strings. This particular
 * trie is being used as a Map in the test code.
 * http://en.wikipedia.org/wiki/Trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Trie<C extends CharSequence> {

    private Node<C> root = null;
    
    public Trie() { 
        root = new Node<C>(null);
    }
    
    public boolean add(C key, int value) {
        int length = (key.length()-1);
        Node<C> prev = root;
        for (int i=0; i<length; i++) {
            Node<C> n = null;
            Character c = key.charAt(i);
            int index = prev.containsChild(c);
            if (index>=0) {
                n = prev.getChild(index);
            } else {
                n = new Node<C>(c);
                prev.children.add(n);
            }
            prev = n;
        }

        Node<C> n = null;
        Character c = key.charAt(length);
        int index = prev.containsChild(c);
        if (index>=0) {
            n = prev.getChild(index);
            if (n.value==Integer.MIN_VALUE) {
                n.character = c;
                n.string = key;
                n.value = value;
                return true;
            } else {
                return false;
            }
        } else {
            n = new Node<C>(c,key,value);
            prev.children.add(n);
            return true;
        }
    }
    
    public int get(String key) {
        if (root==null) return Integer.MIN_VALUE;
        
        Node<C> n = root;
        int length = (key.length()-1);
        for (int i=0; i<=length; i++) {
            char c = key.charAt(i);
            int index = n.containsChild(c);
            if (index>=0) {
                n = n.getChild(index);
            } else {
                return Integer.MIN_VALUE;
            }
        }
        if (n!=null) return n.value;
        return Integer.MIN_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node<C> c : root.children) {
            builder.append(c.toString());
        }
        return builder.toString();
    }

    private static class Node<C extends CharSequence> {
        
        private Character character = null;
        private C string = null;
        private int value = Integer.MIN_VALUE;
        private List<Node<C>> children = new ArrayList<Node<C>>();

        private Node(Character character) {
            this.character = character;
        }
        
        private Node(Character character, C string, int value) {
            this.character = character;
            this.string = string;
            this.value = value;
        }

        private int containsChild(Character character) {
            for (int i=0; i<children.size(); i++) {
                Node<C> c = children.get(i);
                if (c.character.equals(character)) return i;
            }
            return Integer.MIN_VALUE;
        }

        private Node<C> getChild(int index) {
            Node<C> c = children.get(index);
            return c;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value!=Integer.MIN_VALUE) builder.append("Node=").append(string).append(" value=").append(value).append("\n");
            for (Node<C> c : children) {
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }
}
