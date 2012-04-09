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

    public boolean remove(C key) {
        if (root==null) return false;
        
        Node<C> previous = null;
        Node<C> node = root;
        int length = (key.length()-1);
        for (int i=0; i<=length; i++) {
            char c = key.charAt(i);
            int index = node.childIndex(c);
            if (index>=0) {
                previous = node;
                node = node.getChild(index);
            } else {
                return false;
            }
        }
        if (node.children.size()>0) {
            node.string = null;
        } else {
            int index = previous.childIndex(node.character);
            previous.children.remove(index);
        }
        return true;
    }

    public boolean contains(C key) {
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
        
        public static <C extends CharSequence> void print(Trie<C> trie) {
            print(trie, "", true);
        }

        protected static <C extends CharSequence> void print(Trie<C> trie, String prefix, boolean isTail) {
            System.out.println(getString(trie));
        }

        public static <C extends CharSequence> String getString(Trie<C> tree) {
            return getString(tree.root, "", true);
        }

        protected static <C extends CharSequence> String getString(Node<C> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.string!=null)?("("+node.character+") "+node.string):node.character)+"\n");
            if (node.children != null) {
                for (int i = 0; i < node.children.size() - 1; i++) {
                    builder.append(getString(node.children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.children.size() >= 1) {
                    builder.append(getString(node.children.get(node.children.size() - 1), prefix + (isTail ?"    " : "│   "), true));
                }
            }
            return builder.toString();
        }
    }
}
