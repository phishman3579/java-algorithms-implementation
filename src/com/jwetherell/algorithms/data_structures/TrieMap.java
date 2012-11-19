package com.jwetherell.algorithms.data_structures;

/**
 * A trie used to store key->values pairs, this is an implementation of an
 * associative array.
 * 
 * This implementation is a composition of a Trie as the backing structure.
 * 
 * http://en.wikipedia.org/wiki/Trie
 * http://en.wikipedia.org/wiki/Associative_array
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TrieMap<K extends CharSequence, V> implements Trie.INodeCreator {

    private Trie<K> trie = null;

    /**
     * Default constructor.
     */
    public TrieMap() {
        trie = new Trie<K>(this);
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
        Trie.Node node = trie.addSequence(key);
        if (node == null) return false;

        if (node instanceof TrieMapNode) {
            TrieMapNode<V> mNode = (TrieMapNode<V>) node;
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
    @SuppressWarnings("unchecked")
    public V get(K key) {
        Trie.Node node = trie.getNode(key);
        if (node instanceof TrieMapNode) {
            TrieMapNode<V> mapNode = (TrieMapNode<V>) node;
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
    public Trie.Node createNewNode(Trie.Node parent, Character character, boolean isWord) {
        return (new TrieMapNode<V>(parent, character, isWord));
    }

    protected static class TrieMapNode<V> extends Trie.Node {

        protected V value = null;

        protected TrieMapNode(Trie.Node parent, Character character) {
            super(parent, character, false);
        }

        protected TrieMapNode(Trie.Node parent, Character character, boolean isWord) {
            super(parent, character, isWord);
        }

        protected TrieMapNode(Trie.Node parent, Character character, boolean isWord, V value) {
            super(parent, character, isWord);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value != null) builder.append("key=").append(isWord).append(" value=").append(value).append("\n");
            for (int i = 0; i < getChildrenSize(); i++) {
                Trie.Node c = getChild(i);
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }

    protected static class TrieMapPrinter {

        public static <C extends CharSequence, V> String getString(TrieMap<C, V> map) {
            return getString(map.trie.root, "", null, true);
        }

        @SuppressWarnings("unchecked")
        protected static <C extends CharSequence, V> String getString(Trie.Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.character != null) {
                String temp = String.valueOf(node.character);
                if (previousString != null) string = previousString + temp;
                else string = temp;
            }
            if (node instanceof TrieMapNode) {
                TrieMapNode<V> hashNode = (TrieMapNode<V>) node;
                builder.append(prefix + (isTail ? "└── " : "├── ")
                        + ((node.isWord == true) ? ("(" + node.character + ") " + string + " = " + hashNode.value) : node.character) + "\n");
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
