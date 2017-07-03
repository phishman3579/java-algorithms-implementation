package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.Trie.Node;
import com.jwetherell.algorithms.data_structures.interfaces.IMap;

/**
 * A trie used to store key->values pairs, this is an implementation of an
 * associative array.
 * <p>
 * This implementation is a composition using a Trie as the backing structure.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Trie">Trie (Wikipedia)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Associative_array">Associative Array (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class TrieMap<K extends CharSequence, V> implements Trie.INodeCreator, IMap<K,V> {

    private Trie<K> trie = null;

    public TrieMap() {
        trie = new Trie<K>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V prev = null;
        Trie.Node node = trie.addSequence(key);

        if (node!=null && node instanceof TrieMapNode) {
            TrieMapNode<V> trieMapNode = (TrieMapNode<V>) node;
            if (trieMapNode.value!=null) prev = trieMapNode.value;
            trieMapNode.value = value;
        }

        return prev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        Trie.Node node = trie.getNode(key);
        if (node!=null && node instanceof TrieMapNode) {
            TrieMapNode<V> mapNode = (TrieMapNode<V>) node;
            return mapNode.value;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        return trie.contains(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        Trie.Node node = trie.getNode(key);
        V value = null;
        if (node!=null) {
            if (node instanceof TrieMapNode) {
                TrieMapNode<V> tmn = (TrieMapNode<V>)node;
                value = tmn.value;
                tmn.value = null;
            }
            trie.remove(node);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        trie.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return trie.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        java.util.Set<K> keys = new java.util.HashSet<K>();
        Trie.Node node = trie.root;
        if (node!=null && !validate(node,"",keys)) return false;
        return (keys.size()==size());
    }

    private boolean validate(Trie.Node node, String string, java.util.Set<K> keys) {
        if (!(node instanceof TrieMapNode)) return false;

        TrieMapNode<V> tmn = (TrieMapNode<V>)node;

        StringBuilder builder = new StringBuilder(string);
        builder.append(tmn.character);
        String s = builder.toString();

        if (tmn.isWord) {
            K k = (K)s;
            V v = tmn.value;
            if (k==null || v==null) return false;
            if (keys.contains(k)) return false;
            keys.add(k);
        }
        for (int i=0; i<tmn.childrenSize; i++) {
            Trie.Node n = tmn.getChild(i);
            if (n!=null && !validate(n,s,keys)) return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Map<K,V> toMap() {
        return (new JavaCompatibleTrieMap<K,V>(this));
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
    public Trie.Node createNewNode(Trie.Node parent, Character character, boolean type) {
        return (new TrieMapNode<V>(parent, character, type));
    }

    protected static class TrieMapNode<V> extends Trie.Node {

        protected V value = null;

        protected TrieMapNode(Trie.Node parent, Character character, boolean type) {
            super(parent, character, type);
        }

        protected TrieMapNode(Trie.Node parent, Character character, boolean type, V value) {
            super(parent, character, type);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value != null)
                builder.append("key=").append(character).append(" value=").append(value).append("\n");
            for (int i = 0; i < getChildrenSize(); i++) {
                Trie.Node c = getChild(i);
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }

    protected static class TrieMapPrinter {

        public static <K extends CharSequence, V> String getString(TrieMap<K, V> map) {
            return getString(map.trie.root, "", null, true);
        }

        protected static <V> String getString(Trie.Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.character != Node.SENTINAL) {
                String temp = String.valueOf(node.character);
                if (previousString != null) {
                    string = previousString + temp;
                } else {
                    string = temp;
                }
            }
            if (node instanceof TrieMapNode) {
                TrieMapNode<V> hashNode = (TrieMapNode<V>) node;
                builder.append(prefix
                        + (isTail ? "└── " : "├── ")
                        + ((node.isWord) ? 
                              ("(" + String.valueOf(node.character) + ") " + string + " = {" + hashNode.value + "}")
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

    private static class JavaCompatibleMapEntry<K extends CharSequence,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = -4427602384853830561L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleIteratorWrapper<K extends CharSequence,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private TrieMap<K,V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(TrieMap<K,V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
            this.map = map;
            this.iter = iter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if (iter==null) return false;
            return iter.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map.Entry<K, V> next() {
            if (iter==null) return null;

            lastEntry = iter.next();
            return lastEntry;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (iter==null || lastEntry==null) return;

            map.remove(lastEntry.getKey());
            iter.remove();
        }
    }

    private static class JavaCompatibleTrieMap<K extends CharSequence,V> extends java.util.AbstractMap<K,V> {

        private TrieMap<K,V> map = null;

        protected JavaCompatibleTrieMap(TrieMap<K,V> map) {
            this.map = map;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            return map.put(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            return map.remove((K)key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            map.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return map.contains((K)key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return map.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            java.util.Set<java.util.Map.Entry<K, V>> set = new java.util.HashSet<java.util.Map.Entry<K, V>>() {

                private static final long serialVersionUID = 1L;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                    return (new JavaCompatibleIteratorWrapper<K,V>(map,super.iterator()));
                }
            };
            if (map.trie!=null && map.trie.root!=null) {
                Trie.Node n = map.trie.root;
                levelOrder(n,"",set);
            }
            return set;
        }

        private void levelOrder(Trie.Node node, String string, java.util.Set<java.util.Map.Entry<K, V>> set) {
            StringBuilder builder = new StringBuilder(string);
            TrieMapNode<V> tmn = null;
            if (node instanceof TrieMapNode) {
                tmn = (TrieMapNode<V>)node;
                if (tmn.character!=Trie.Node.SENTINAL) builder.append(tmn.character);
                if (tmn.isWord) {
                    K s = (K)builder.toString();
                    java.util.Map.Entry<K, V> entry = new JavaCompatibleMapEntry<K, V>(s, tmn.value);
                    set.add(entry);
                }
            }

            String b = builder.toString();
            for (int i=0; i<node.childrenSize; i++) {
                Trie.Node n = node.getChild(i);
                levelOrder(n,b,set);
            }
        }
    }
}
