package com.jwetherell.algorithms.data_structures;

/**
 * A trie used to store key->values pairs, this is an implementation of an
 * associative array.
 * 
 * This implementation is a composition using (patricia/radix) Trie as the backing structure.
 *
 * http://en.wikipedia.org/wiki/Radix_tree
 * http://en.wikipedia.org/wiki/Associative_array
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class TrieMap<K extends CharSequence, V> implements PatriciaTrie.INodeCreator<K> {

    private PatriciaTrie<K> trie = null;

    /**
     * Default constructor.
     */
    public TrieMap() {
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
    public boolean put(K key, V value) {
        PatriciaTrie.Node<K> node = trie.addSequence(key);
        if (node == null)
            return false;

        if (node instanceof TrieMapNode) {
            TrieMapNode<K,V> mNode = (TrieMapNode<K,V>) node;
            mNode.value = value;
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
    public V get(K key) {
        PatriciaTrie.Node<K> node = trie.getNode(key);
        if (node instanceof TrieMapNode) {
            TrieMapNode<K,V> mapNode = (TrieMapNode<K,V>) node;
            return mapNode.value;
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
        return TrieMapPrinter.getString(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatriciaTrie.Node<K> createNewNode(PatriciaTrie.Node<K> parent, K seq, boolean type) {
        return (new TrieMapNode<K,V>(parent, seq, type));
    }

    protected static class TrieMapNode<C extends CharSequence, U> extends PatriciaTrie.Node<C> {

        protected U value = null;

        protected TrieMapNode(PatriciaTrie.Node<C> parent, C seq) {
            super(parent, seq);
        }

        protected TrieMapNode(PatriciaTrie.Node<C> parent, C seq, boolean type) {
            super(parent, seq, type);
        }

        protected TrieMapNode(PatriciaTrie.Node<C> parent, C seq, boolean type, U value) {
            super(parent, seq, type);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value != null)
                builder.append("key=").append(type).append(" value=").append(value).append("\n");
            for (int i = 0; i < getChildrenSize(); i++) {
                PatriciaTrie.Node<C> c = getChild(i);
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }

    protected static class TrieMapPrinter {

        public static <C extends CharSequence, U> String getString(TrieMap<C, U> map) {
            return getString(map.trie.root, "", null, true);
        }

        protected static <C extends CharSequence, U> String getString(PatriciaTrie.Node<C> node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.string != null) {
                String temp = String.valueOf(node.string);
                if (previousString != null)
                    string = previousString + temp;
                else
                    string = temp;
            }
            if (node instanceof TrieMapNode) {
                TrieMapNode<C,U> hashNode = (TrieMapNode<C,U>) node;
                builder.append(prefix
                        + (isTail ? "└── " : "├── ")
                        + ((node.type == PatriciaTrie.WHITE) ? 
                              ("(" + String.valueOf(node.string) + ") " + string + " = {" + hashNode.value + "}")
                          : 
                              string)
                        + "\n");
            }
            if (node.getChildrenSize() > 0) {
                for (int i = 0; i < node.getChildrenSize() - 1; i++) {
                    builder.append(getString(node.getChild(i), prefix + (isTail ? "    " : "│   "), string, false));
                }
                if (node.getChildrenSize() >= 1) {
                    builder.append(getString(node.getChild(node.getChildrenSize() - 1), prefix
                            + (isTail ? "    " : "│   "), string, true));
                }
            }

            return builder.toString();
        }
    }
}
