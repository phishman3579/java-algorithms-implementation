package com.jwetherell.algorithms.data_structures;

import java.util.Map;
import java.util.HashMap;

/**
 * A trie, or prefix tree, is an ordered tree data structure that is used to store an associative array where the keys are usually strings. This particular
 * trie is being used as a Map in the test code.
 * http://en.wikipedia.org/wiki/Trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Trie {

    private Node root = null;
    
    public Trie() { 
        root = new Node(null);
    }
    
    public boolean add(String key, int value) {
        int length = (key.length()-1);
        Node prev = root;
        for (int i=0; i<length; i++) {
            Node n = null;
            char c = key.charAt(i);
            if (prev.children.containsKey(c)) {
                n = prev.children.get(c);
            } else {
                n = new Node(c);
                prev.children.put(c, n);
            }
            prev = n;
        }

        Node n = null;
        char c = key.charAt(length);
        if (prev.children.containsKey(c)) {
            n = prev.children.get(c);
            if (n.value==Integer.MIN_VALUE) {
                n.string = key;
                n.value = value;
                return true;
            } else {
                return false;
            }
        } else {
            n = new Node(key,value);
            prev.children.put(c, n);
            return true;
        }
    }
    
    public int get(String key) {
        if (root==null) return Integer.MIN_VALUE;
        
        Node n = root;
        int length = (key.length()-1);
        for (int i=0; i<=length; i++) {
            char c = key.charAt(i);
            if (n.children.containsKey(c)) {
                n = n.children.get(c);
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
        for (Node c : root.children.values()) {
            builder.append(c.toString());
        }
        return builder.toString();
    }

    private static class Node {
        
        private String string = null;
        private Map<Character,Node> children = new HashMap<Character,Node>();
        private int value = Integer.MIN_VALUE;

        private Node(Character character) {
            this.string = (character!=null)?character.toString():null;
        }
        
        private Node(String string, int value) {
            this.string = string;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value!=Integer.MIN_VALUE) builder.append("Node=").append(string).append(" value=").append(value).append("\n");
            for (Node c : children.values()) {
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }
}
