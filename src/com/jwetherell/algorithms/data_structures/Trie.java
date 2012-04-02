package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * A trie, or prefix tree, is an ordered tree data structure that is used to store an associative 
 * array where the keys are usually strings. 
 * 
 * == This is NOT a compact Trie. ==
 * 
 * http://en.wikipedia.org/wiki/Trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Trie<C extends CharSequence> {

    protected Node<C> root = null;


    public Trie() { 
        root = new Node<C>(null);
    }
    
    public boolean add(C key) {
        int length = (key.length()-1);
        Node<C> prev = root;
        for (int i=0; i<length; i++) {
            Node<C> n = null;
            Character c = key.charAt(i);
            int index = prev.childIndex(c);
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
        int index = prev.childIndex(c);
        if (index>=0) {
            n = prev.getChild(index);
            if (n.string==null) {
                n.character = c;
                n.string = key;
                return true;
            } else {
                return false;
            }
        } else {
            n = new Node<C>(c,key);
            prev.children.add(n);
            return true;
        }
    }

    public Node<C> getRoot() {
        return root;
    }
    
    public boolean contains(String key) {
        if (root==null) return false;
        
        Node<C> n = root;
        int length = (key.length()-1);
        for (int i=0; i<=length; i++) {
            char c = key.charAt(i);
            int index = n.childIndex(c);
            if (index>=0) {
                n = n.getChild(index);
            } else {
                return false;
            }
        }
        return true;
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


    protected static class Node<C extends CharSequence> {
        
        protected Character character = null;
        protected C string = null;
        protected List<Node<C>> children = new ArrayList<Node<C>>();

        protected Node(Character character) {
            this.character = character;
        }
        
        protected Node(Character character, C string) {
            this.character = character;
            this.string = string;
        }

        protected int childIndex(Character character) {
            for (int i=0; i<children.size(); i++) {
                Node<C> c = children.get(i);
                if (c.character.equals(character)) return i;
            }
            return Integer.MIN_VALUE;
        }

        protected Node<C> getChild(int index) {
            Node<C> c = children.get(index);
            return c;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (string!=null) builder.append("Node=").append(string).append("\n");
            for (Node<C> c : children) {
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }
    
    public static class TriePrinter {
        
        public static <C extends CharSequence> void printNode(Node<C> root) {
            print(root, "", true);
        }

        protected static <C extends CharSequence> void print(Node<C> node, String prefix, boolean isTail) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + ((node.string!=null)?("("+node.character+") "+node.string):node.character));
            if (node.children != null) {
                for (int i = 0; i < node.children.size() - 1; i++) {
                    print(node.children.get(i), prefix + (isTail ? "    " : "│   "), false);
                }
                if (node.children.size() >= 1) {
                    print(node.children.get(node.children.size() - 1), prefix + (isTail ?"    " : "│   "), true);
                }
            }
        }
    }
}
