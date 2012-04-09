package com.jwetherell.algorithms.data_structures;


/**
 * An trie used to store key->values pairs, this is an implementation of an associative array. 
 * 
 * == This is NOT a compact Trie. ==
 * 
 * http://en.wikipedia.org/wiki/Trie
 * http://en.wikipedia.org/wiki/Associative_array
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TrieMap<C extends CharSequence> extends Trie<C> {

    public TrieMap() { 
        root = new MapNode<C>(null);
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
                n = new MapNode<C>(c);
                prev.children.add(n);
            }
            prev = n;
        }

        MapNode<C> n = null;
        Character c = key.charAt(length);
        int index = prev.childIndex(c);
        if (index>=0) {
            n = (MapNode<C>) prev.getChild(index);
            if (n.value==Integer.MIN_VALUE) {
                n.character = c;
                n.string = key;
                n.value = value;
                return true;
            } else {
                return false;
            }
        } else {
            n = new MapNode<C>(c,key,value);
            prev.children.add(n);
            return true;
        }
    }

    public int get(String key) {
        if (root==null) return Integer.MIN_VALUE;
        
        MapNode<C> n = (MapNode<C>) root;
        int length = (key.length()-1);
        for (int i=0; i<=length; i++) {
            char c = key.charAt(i);
            int index = n.childIndex(c);
            if (index>=0) {
                n = (MapNode<C>) n.getChild(index);
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
        return TrieMapPrinter.getString(this);
    }


    protected static class MapNode<C extends CharSequence> extends Node<C> {

        protected int value = Integer.MIN_VALUE;


        protected MapNode(Character character) {
            super(character);
        }
        
        protected MapNode(Character character, C string, int value) {
            super(character,string);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value!=Integer.MIN_VALUE) builder.append("key=").append(string).append(" value=").append(value).append("\n");
            for (Node<C> c : children) {
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }
    
    protected static class TrieMapPrinter {
        
        public static <C extends CharSequence> void print(TrieMap<C> map) {
            System.out.println(getString(map));
        }
        
        public static <C extends CharSequence> String getString(TrieMap<C> map) {
            return getString(map.root, "", true);
        }

        protected static <C extends CharSequence> String getString(Node<C> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node instanceof MapNode) {
                MapNode<C> hashNode = (MapNode<C>) node;
                builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.string!=null)?("("+node.character+") "+node.string+" = "+hashNode.value):node.character)+"\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.string!=null)?("("+node.character+") "+node.string):node.character)+"\n");
            }
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
