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
            int index = prev.containsChild(c);
            if (index>=0) {
                n = prev.getChild(index);
            } else {
                n = new Node(c);
                prev.children.add(n);
            }
            prev = n;
        }

        Node n = null;
        char c = key.charAt(length);
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
            n = new Node(c,key,value);
            prev.children.add(n);
            return true;
        }
    }
    
    public int get(String key) {
        if (root==null) return Integer.MIN_VALUE;
        
        Node n = root;
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
        for (Node c : root.children) {
            builder.append(c.toString());
        }
        return builder.toString();
    }

    private static class Node {
        
        private Character character = null;
        private String string = null;
        private int value = Integer.MIN_VALUE;
        private List<Node> children = new ArrayList<Node>();

        private Node(Character character) {
            this.character = character;
        }
        
        private Node(Character character, String string, int value) {
            this.character = character;
            this.string = string;
            this.value = value;
        }

        private int containsChild(Character character) {
            for (int i=0; i<children.size(); i++) {
                Node c = children.get(i);
                if (c.character==character) return i;
            }
            return Integer.MIN_VALUE;
        }

        private Node getChild(int index) {
            Node c = children.get(index);
            if (c!=null) return c;
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value!=Integer.MIN_VALUE) builder.append("Node=").append(string).append(" value=").append(value).append("\n");
            for (Node c : children) {
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }
}
