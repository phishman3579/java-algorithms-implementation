package com.jwetherell.algorithms.data_structures;

/**
 * A radix tree or radix trie is a space-optimized trie data structure where
 * each node with only one child is merged with its child. The result is that
 * every internal node has at least two children. Unlike in regular tries, edges
 * can be labeled with sequences of characters as well as single characters.
 * This makes them much more efficient for small sets (especially if the strings
 * are long) and for sets of strings that share long prefixes. This particular
 * radix tree is used to represent an associative array.
 * 
 * http://en.wikipedia.org/wiki/Radix_tree
 * http://en.wikipedia.org/wiki/Associative_array
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RadixTree<K extends CharSequence, V> extends PatriciaTrie<K> {

    public RadixTree() {
        super();
    }

    @SuppressWarnings("unchecked")
    public boolean put(K string, V value) {
        Node<K> node = this.addNode(string);
        if (node == null) return false;

        if (node instanceof RadixNode) {
            RadixNode<K, V> radix = (RadixNode<K, V>) node;
            radix.value = value;
        } else {
            // Really shouldn't get here
        }

        return true;
    }

    protected Node<K> createNewNode(Node<K> parent, K string, Node.Type type) {
        return (new RadixNode<K, V>(parent, string, type));
    }

    @SuppressWarnings("unchecked")
    public RadixNode<K, V> get(K string) {
        Node<K> k = this.getNode(string);
        if (k instanceof RadixNode) {
            RadixNode<K, V> r = (RadixNode<K, V>) k;
            return r;
        }
        return null;
    }

    @Override
    public boolean add(K String) {
        // This should not be used
        throw new RuntimeException("This method is not supported");
    }

    @Override
    public String toString() {
        return RadixTreePrinter.getString(this);
    }

    protected static final class RadixNode<K extends CharSequence, V> extends Node<K> implements Comparable<Node<K>> {

        protected V value = null;

        protected RadixNode(Node<K> node, V value) {
            super(node.parent, node.string, node.type);
            this.value = value;
            for (int i=0; i<node.getChildrenSize(); i++) {
                Node<K> c = node.getChild(i);
                this.addChild(c);
            }
        }

        protected RadixNode(Node<K> parent, K string, Type type) {
            super(parent, string, type);
        }

        protected RadixNode(Node<K> parent, K string, Type type, V value) {
            super(parent, string, type);
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("string = ").append(string).append("\n");
            builder.append("type = ").append(type).append("\n");
            return builder.toString();
        }

        @Override
        public int compareTo(Node<K> node) {
            if (node == null) return -1;

            int length = string.length();
            if (node.string.length() < length) length = node.string.length();
            for (int i = 0; i < length; i++) {
                Character a = string.charAt(i);
                Character b = node.string.charAt(i);
                int c = a.compareTo(b);
                if (c != 0) return c;
            }

            if (this.type.ordinal() < node.type.ordinal()) return -1;
            else if (this.type.ordinal() > node.type.ordinal()) return 1;

            if (this.getChildrenSize() < node.getChildrenSize()) return -1;
            else if (this.getChildrenSize() > node.getChildrenSize()) return 1;

            return 0;
        }
    }

    protected static class RadixTreePrinter<K extends CharSequence, V> {

        public static <C extends CharSequence, V> String getString(RadixTree<C, V> tree) {
            return getString(tree.root, "", true);
        }

        @SuppressWarnings("unchecked")
        protected static <C extends CharSequence, V> String getString(Node<C> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            if (node instanceof RadixNode) {
                RadixNode<C, V> radix = (RadixNode<C, V>) node;
                builder.append(prefix + (isTail ? "└── " : "├── ")
                        + ((radix.string != null) ? radix.string + " (" + radix.type + ") = " + radix.value : "null" + " (" + node.type + ")") + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ")
                        + ((node.string != null) ? node.string + " (" + node.type + ") " : "null" + " (" + node.type + ")") + "\n");
            }
            if (node.getChildrenSize()>0) {
                for (int i = 0; i < node.getChildrenSize() - 1; i++) {
                    builder.append(getString(node.getChild(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.getChildrenSize() >= 1) {
                    builder.append(getString(node.getChild(node.getChildrenSize() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }
            return builder.toString();
        }
    }
}
