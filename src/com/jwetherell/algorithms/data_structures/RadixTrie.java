package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.interfaces.IMap;

/**
 * A radix trie or radix tree is a space-optimized trie data structure where
 * each node with only one child is merged with its child. The result is that
 * every internal node has at least two children. Unlike in regular tries, edges
 * can be labeled with sequences of characters as well as single characters.
 * This makes them much more efficient for small sets (especially if the strings
 * are long) and for sets of strings that share long prefixes. This particular
 * radix tree is used to represent an associative array.
 * <p>
 * This implementation is a composition of a PatriciaTrie as the backing
 * structure.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Radix_tree">Radix Tree / Patricia Trie (Wikipedia)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Associative_array">Associative Array (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class RadixTrie<K extends CharSequence, V> implements PatriciaTrie.INodeCreator, IMap<K,V> {

    private PatriciaTrie<K> trie = null;

    public RadixTrie() {
        trie = new PatriciaTrie<K>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V prev = null;
        PatriciaTrie.Node node = trie.addSequence(key);

        if (node!=null && node instanceof RadixNode) {
            RadixNode<K,V> radixNode = (RadixNode<K,V>) node;
            if (radixNode.value!=null) 
                prev = radixNode.value;
            radixNode.value = value;
        }

        return prev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        PatriciaTrie.Node node = trie.getNode(key);
        if (node!=null && node instanceof RadixNode) {
            RadixNode<K,V> radixNode = (RadixNode<K,V>) node;
            return radixNode.value;
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
        PatriciaTrie.Node node = trie.getNode(key);
        V value = null;
        if (node!=null) {
            if (node instanceof RadixNode) {
                RadixNode<K,V> rn = (RadixNode<K,V>)node;
                value = rn.value;
                rn.value = null;
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
        PatriciaTrie.Node node = trie.root;
        if (node!=null && !validate(node,"",keys)) 
            return false;
        return (keys.size()==size());
    }

    private boolean validate(PatriciaTrie.Node node, String string, java.util.Set<K> keys) {
        if (!(node instanceof RadixNode)) 
            return false;

        RadixNode<K,V> tmn = (RadixNode<K,V>)node;

        StringBuilder builder = new StringBuilder(string);
        if (tmn.string!=null) builder.append(tmn.string);
        String s = builder.toString();

        if (tmn.type == PatriciaTrie.WHITE) {
            K k = (K)s;
            V v = tmn.value;
            if (k==null || v==null) 
                return false;
            if (keys.contains(k)) 
                return false;
            keys.add(k);
        }
        for (int i=0; i<tmn.childrenSize; i++) {
            PatriciaTrie.Node n = tmn.getChild(i);
            if (n!=null && !validate(n,s,keys)) 
                return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Map<K,V> toMap() {
        return (new JavaCompatibleRadixTrieMap<K,V>(this));
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
        return (new RadixNode<K,V>(parent, seq, type));
    }

    protected static final class RadixNode<K extends CharSequence, V> extends PatriciaTrie.Node {

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

        public static <K extends CharSequence, V> String getString(RadixTrie<K, V> map) {
            return getString(map.trie.root, "", null, true);
        }

        protected static <K extends CharSequence, V> String getString(PatriciaTrie.Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.string != null) {
                String temp = String.valueOf(node.string);
                if (previousString != null)
                    string = previousString + temp;
                else
                    string = temp;
            }
            if (node instanceof RadixNode) {
                RadixNode<K,V> radix = (RadixNode<K,V>) node;
                builder.append(prefix
                                + (isTail ? "└── " : "├── ")
                                + ((radix.string != null) ? 
                                    "(" + String.valueOf(radix.string) + ") " 
                                    + "[" + ((node.type == PatriciaTrie.WHITE) ? "WHITE" : "BLACK") + "] " 
                                    + string
                                    + ((radix.value != null) ? " = " + radix.value : "") 
                                : 
                                    "[" + ((node.type == PatriciaTrie.WHITE) ? "WHITE" : "BLACK") + "]") + "\n");
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


    private static class JavaCompatibleIteratorWrapper<K extends CharSequence,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private RadixTrie<K,V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(RadixTrie<K,V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
            this.map = map;
            this.iter = iter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if (iter==null) 
                return false;
            return iter.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map.Entry<K, V> next() {
            if (iter==null) 
                return null;

            lastEntry = iter.next();
            return lastEntry;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (iter==null || lastEntry==null) 
                return;

            map.remove(lastEntry.getKey());
            iter.remove();
        }
    }

    private static class JavaCompatibleMapEntry<K extends CharSequence,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = -4427602384853830561L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleRadixTrieMap<K extends CharSequence,V> extends java.util.AbstractMap<K,V> {

        private RadixTrie<K,V> map = null;

        protected JavaCompatibleRadixTrieMap(RadixTrie<K,V> map) {
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
                PatriciaTrie.Node n = map.trie.root;
                levelOrder(n,"",set);
            }
            return set;
        }

        private void levelOrder(PatriciaTrie.Node node, String string, java.util.Set<java.util.Map.Entry<K, V>> set) {
            StringBuilder builder = new StringBuilder(string);
            RadixNode<K,V> tmn = null;
            if (node instanceof RadixNode) {
                tmn = (RadixNode<K,V>)node;
                if (tmn.string!=null) 
                    builder.append(tmn.string);
                if (tmn.type == PatriciaTrie.WHITE) {
                    K s = (K)builder.toString();
                    java.util.Map.Entry<K, V> entry = new JavaCompatibleMapEntry<K, V>(s, tmn.value);
                    set.add(entry);
                }
            }

            String b = builder.toString();
            for (int i=0; i<node.childrenSize; i++) {
                PatriciaTrie.Node n = node.getChild(i);
                levelOrder(n,b,set);
            }
        }
    }
}
