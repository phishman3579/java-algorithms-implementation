package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;


/**
 * A trie, or prefix tree, is an ordered tree data structure that is used to
 * store an associative array where the keys are usually strings.
 * 
 * == This is NOT a compact Trie. ==
 * 
 * http://en.wikipedia.org/wiki/Trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Trie<C extends CharSequence> {

    protected int size = 0;
    protected Node<C> root = null;

    public Trie() { }

    public boolean add(C key) {
        if (root==null) root = new Node<C>(null, null);

        int length = (key.length() - 1);
        Node<C> prev = root;
        for (int i = 0; i < length; i++) {
            Node<C> n = null;
            Character c = key.charAt(i);
            int index = prev.childIndex(c);
            if (index >= 0) {
                n = prev.getChild(index);
            } else {
                n = new Node<C>(prev, c);
                prev.addChild(n);
            }
            prev = n;
        }

        Node<C> n = null;
        Character c = key.charAt(length);
        int index = prev.childIndex(c);
        if (index >= 0) {
            n = prev.getChild(index);
            if (n.string == null) {
                n.character = c;
                n.string = key;
                size++;
                return true;
            } else {
                return false;
            }
        } else {
            n = new Node<C>(prev, c, key);
            prev.addChild(n);
            size++;
            return true;
        }
    }

    public boolean remove(C key) {
        if (root == null) return false;

        Node<C> previous = null;
        Node<C> node = root;
        int length = (key.length() - 1);
        for (int i = 0; i <= length; i++) {
            char c = key.charAt(i);
            int index = node.childIndex(c);
            if (index >= 0) {
                previous = node;
                node = node.getChild(index);
            } else {
                return false;
            }
        }
        if (node.childrenSize > 0) {
            node.string = null;
        } else {
            int index = previous.childIndex(node.character);
            previous.removeChild(index);
            while (previous != null && previous.string==null && previous.childrenSize == 0) {
                if (previous.parent != null) {
                    int idx = previous.parent.childIndex(previous.character);
                    if (idx >= 0) previous.parent.removeChild(idx);
                }
                previous = previous.parent;
            }
        }
        size--;
        return true;
    }

    public boolean contains(C key) {
        if (root == null) return false;

        Node<C> n = root;
        int length = (key.length() - 1);
        for (int i = 0; i <= length; i++) {
            char c = key.charAt(i);
            int index = n.childIndex(c);
            if (index >= 0) {
                n = n.getChild(index);
            } else {
                return false;
            }
        }
        return (n.string != null);
    }

    public int getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TriePrinter.getString(this);
    }

    protected static class Node<C extends CharSequence> {
        
        private static final int GROW_IN_CHUNKS = 10;
        @SuppressWarnings("unchecked")
        private Node<C>[] children = new Node[2];
        private int childrenSize = 0;

        protected Node<C> parent = null;
        protected Character character = null;
        protected C string = null;


        protected Node(Node<C> parent, Character character) {
            this.parent = parent;
            this.character = character;
        }

        protected Node(Node<C> parent, Character character, C string) {
            this(parent, character);
            this.string = string;
        }

        protected void addChild(Node<C> node) {
            if (childrenSize==children.length) {
                children = Arrays.copyOf(children, children.length+GROW_IN_CHUNKS);
            }
            children[childrenSize++] = node;
        }
        protected boolean removeChild(int index) {
            if (index>=childrenSize) return false;
            children[index] = null;
            for (int i=index+1; i<childrenSize; i++) {
                //shift the rest of the keys down
                children[i-1] = children[i];
            }
            childrenSize--;
            children[childrenSize] = null;
            return true;
        }
        protected int childIndex(Character character) {
            for (int i = 0; i < childrenSize; i++) {
                Node<C> c = children[i];
                if (c.character.equals(character)) return i;
            }
            return Integer.MIN_VALUE;
        }
        protected Node<C> getChild(int index) {
            if (index>=childrenSize) return null;
            return children[index];
        }
        protected int getChildrenSize() {
            return childrenSize;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (string != null) builder.append("Node=").append(string).append("\n");
            for (int i=0; i<childrenSize; i++) {
                Node<C> c = children[i];
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }

    protected static class TriePrinter {

        public static <C extends CharSequence> void print(Trie<C> trie) {
            System.out.println(getString(trie));
        }

        public static <C extends CharSequence> String getString(Trie<C> tree) {
            return getString(tree.root, "", true);
        }

        protected static <C extends CharSequence> String getString(Node<C> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.string != null) ? ("(" + node.character + ") " + node.string) : node.character) + "\n");
            if (node.children != null) {
                for (int i = 0; i < node.childrenSize - 1; i++) {
                    builder.append(getString(node.children[i], prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.childrenSize >= 1) {
                    builder.append(getString(node.children[node.childrenSize - 1], prefix + (isTail ? "    " : "│   "), true));
                }
            }
            return builder.toString();
        }
    }
}
