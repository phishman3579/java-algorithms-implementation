package com.jwetherell.algorithms.data_structures;


/**
 * An ordinary trie used to store hash values, for example, in an implementation of a hash tree. 
 * 
 * == This is NOT a compact Trie. ==
 * 
 * http://en.wikipedia.org/wiki/Hash_trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HashTrie<C extends CharSequence> extends Trie<C> {

    public HashTrie() { 
        root = new HashNode<C>(null);
    }

    public boolean add(C key, int value) {
        int length = (key.length()-1);
        Node<C> prev = root;
        for (int i=0; i<length; i++) {
            Node<C> n = null;
            Character c = key.charAt(i);
            int index = prev.childIndex(c);
            if (index>=0) {
                n = prev.getChild(index);
            } else {
                n = new HashNode<C>(c);
                prev.children.add(n);
            }
            prev = n;
        }

        HashNode<C> n = null;
        Character c = key.charAt(length);
        int index = prev.childIndex(c);
        if (index>=0) {
            n = (HashNode<C>) prev.getChild(index);
            if (n.value==Integer.MIN_VALUE) {
                n.character = c;
                n.string = key;
                n.value = value;
                return true;
            } else {
                return false;
            }
        } else {
            n = new HashNode<C>(c,key,value);
            prev.children.add(n);
            return true;
        }
    }

    public int get(String key) {
        if (root==null) return Integer.MIN_VALUE;
        
        HashNode<C> n = (HashNode<C>) root;
        int length = (key.length()-1);
        for (int i=0; i<=length; i++) {
            char c = key.charAt(i);
            int index = n.childIndex(c);
            if (index>=0) {
                n = (HashNode<C>) n.getChild(index);
            } else {
                return Integer.MIN_VALUE;
            }
        }
        if (n!=null) return n.value;
        return Integer.MIN_VALUE;
    }

    protected static class HashNode<C extends CharSequence> extends Node<C> {

        protected int value = Integer.MIN_VALUE;


        protected HashNode(Character character) {
            super(character);
        }
        
        protected HashNode(Character character, C string, int value) {
            super(character,string);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value!=Integer.MIN_VALUE) builder.append("HashNode=").append(string).append(" value=").append(value).append("\n");
            for (Node<C> c : children) {
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }
    
    public static class HashTriePrinter {
        
        public static <C extends CharSequence> void printNode(Node<C> root) {
            print(root, "", true);
        }

        protected static <C extends CharSequence> void print(Node<C> node, String prefix, boolean isTail) {
            HashNode<C> hashNode = (HashNode<C>) node;
            System.out.println(prefix + (isTail ? "└── " : "├── ") + ((node.string!=null)?("("+node.character+") "+node.string+" = "+hashNode.value):node.character));
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
