package com.jwetherell.algorithms.data_structures;

/**
 * A radix trie or radix tree is a space-optimized trie data structure where
 * each node with only one child is merged with its child. The result is that
 * every internal node has at least two children. Unlike in regular tries, edges
 * can be labeled with sequences of characters as well as single characters.
 * This makes them much more efficient for small sets (especially if the strings
 * are long) and for sets of strings that share long prefixes. This particular
 * radix tree is used to represent an associative array.
 * 
 * This implementation is a composition of a PatriciaTrie as the backing
 * structure.
 * 
 * http://en.wikipedia.org/wiki/Radix_tree
 * http://en.wikipedia.org/wiki/Associative_array
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RadixTrie<K extends CharSequence, V> implements PatriciaTrie.INodeCreator {

    private PatriciaTrie<K> trie = null;

    /**
     * Default constructor.
     */
    public RadixTrie() {
        trie = new PatriciaTrie<K>(this);
    }

    /**
     * Put the key/value pair in the trie.
     * 
     * @param key
     *            to represent the value.
     * @param value
     *            to store in the key.
     * @return True if added to the trie or false if it already exists.
     */
    @SuppressWarnings("unchecked")
    public boolean put(K key, V value) {
        PatriciaTrie.Node node = trie.addSequence(key);
        if (node == null) return false;

        if (node instanceof RadixNode) {
            RadixNode<V> radixNode = (RadixNode<V>) node;
            radixNode.value = value;
        }

        return true;
    }

    /**
     * Remove the key/value pair from the map.
     * 
     * @param key
     *            to remove from the map.
     * @return True if the key was removed or false if it doesn't exist.
     */
    public boolean remove(K key) {
        return trie.remove(key);
    }

    /**
     * Get the value stored with the key.
     * 
     * @param key
     *            to get value for.
     * @return value stored at key.
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        PatriciaTrie.Node node = trie.getNode(key);
        if (node instanceof RadixNode) {
            RadixNode<V> radixNode = (RadixNode<V>) node;
            return radixNode.value;
        }
        return null;
    }

    /**
     * Does the key exist in the map.
     * 
     * @param key
     *            to locate in the map.
     * @return True if the key exists.
     */
    public boolean contains(K key) {
        return trie.contains(key);
    }

    /**
     * Number of key/value pairs in the map.
     * 
     * @return number of key/value pairs.
     */
    public int size() {
        return trie.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return RadixTreePrinter.getString(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatriciaTrie.Node createNewNode(PatriciaTrie.Node parent, char[] seq, boolean type) {
        return (new RadixNode<V>(parent, seq, type));
    }

    protected static final class RadixNode<V> extends PatriciaTrie.Node implements Comparable<PatriciaTrie.Node> {

        protected V value = null;

        protected RadixNode(PatriciaTrie.Node node, V value) {
            super(node.parent, node.string, node.type);
            this.value = value;
            for (int i = 0; i < node.getChildrenSize(); i++) {
                PatriciaTrie.Node c = node.getChild(i);
                this.addChild(c);
            }
        }

        protected RadixNode(PatriciaTrie.Node parent, char[] string, boolean type) {
            super(parent, string, type);
        }

        protected RadixNode(PatriciaTrie.Node parent, char[] string, boolean type, V value) {
            super(parent, string, type);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(super.toString());
            builder.append("value = ").append(value).append("\n");
            return builder.toString();
        }
    }

    protected static class RadixTreePrinter<K extends CharSequence, V> {

        public static <C extends CharSequence, V> String getString(RadixTrie<C, V> map) {
            return getString(map.trie.root, "", null, true);
        }

        @SuppressWarnings("unchecked")
        protected static <V> String getString(PatriciaTrie.Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.string != null) {
                String temp = String.valueOf(node.string);
                if (previousString != null) string = previousString + temp;
                else string = temp;
            }
            if (node instanceof RadixNode) {
                RadixNode<V> radix = (RadixNode<V>) node;
                builder.append(prefix
                        + (isTail ? "└── " : "├── ")
                        + ((radix.string != null) ? "(" + String.valueOf(radix.string) + ") " + "[" + ((node.type == PatriciaTrie.WHITE) ? "WHITE" : "BLACK")
                                + "] " + string + ((radix.value != null) ? " = " + radix.value : "") : "[" + node.type + "]") + "\n");
            }
            if (node.getChildrenSize() > 0) {
                for (int i = 0; i < node.getChildrenSize() - 1; i++) {
                    builder.append(getString(node.getChild(i), prefix + (isTail ? "    " : "│   "), string, false));
                }
                if (node.getChildrenSize() >= 1) {
                    builder.append(getString(node.getChild(node.getChildrenSize() - 1), prefix + (isTail ? "    " : "│   "), string, true));
                }
            }
            return builder.toString();
        }
    }
}
