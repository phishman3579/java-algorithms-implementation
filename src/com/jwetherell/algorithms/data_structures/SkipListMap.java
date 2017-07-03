package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.SkipList.Node;
import com.jwetherell.algorithms.data_structures.interfaces.IMap;

/**
 * A set used to store key->values pairs, this is an implementation of an
 * associative array.
 * <p>
 * This implementation is a composition of a Skip List as the backing structure.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Skip_list">Skip List (Wikipedia)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Associative_array">Associative Array (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class SkipListMap<K extends Comparable<K>, V> implements SkipList.INodeCreator<K>, IMap<K,V> {

    private SkipList<K> list = null;

    public SkipListMap() {
        list = new SkipList<K>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V prev = null;
        SkipList.Node<K> node = list.addValue(key);
        if (node instanceof SkipListMapNode) {
            SkipListMapNode<K, V> treeMapNode = (SkipListMapNode<K, V>) node;
            if (treeMapNode.value!=null) prev = treeMapNode.value;
            treeMapNode.value = value;
        }

        return prev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        SkipList.Node<K> node = list.getNode(key);
        if (node instanceof SkipListMapNode) {
            SkipListMapNode<K, V> mapNode = (SkipListMapNode<K, V>) node;
            return mapNode.value;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        return list.contains(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        Node<K> node = list.removeValue(key);
        V value = null;
        if (node instanceof SkipListMapNode) {
            SkipListMapNode<K, V> treeMapNode = (SkipListMapNode<K, V>) node;
            value = treeMapNode.value;
            treeMapNode.data = null;
            treeMapNode.value = null;
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (list==null) return true;

        java.util.Set<K> keys = new java.util.HashSet<K>();
        Node<K> node = list.head;
        if (node==null) return true;
        if (!validate(node,keys)) return false;

        Node<K> next = node.getNext(0);
        while (next!=null) {
            if (!validate(next, keys)) return false;
            next = next.getNext(0);
        }

        return (keys.size()==size());
    }

    private boolean validate(Node<K> node, java.util.Set<K> keys) {
        if (!(node instanceof SkipListMapNode)) return false;

        SkipListMapNode<K,V> tmn = (SkipListMapNode<K,V>)node;
        K k = tmn.data;
        V v = tmn.value;
        if (k==null || v==null) return false;
        if (keys.contains(k)) return false;
        keys.add(k);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Map<K,V> toMap() {
        return (new JavaCompatibleTreeMap<K,V>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (list!=null && list.head!=null) {
            Node<K> node = list.head;
            while (node!=null) {
                if (!(node instanceof SkipListMapNode)) continue;

                SkipListMapNode<K,V> sln = (SkipListMapNode<K, V>) node;
                builder.append(sln.data).append("=").append(sln.value);

                node = node.getNext(0);
                if (node!=null) builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SkipList.Node<K> createNewNode(int level, K key) {
        return (new SkipListMapNode<K, V>(level, key));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapNode(Node<K> node, Node<K> next) {
        K key = node.data;
        node.data = next.data;
        next.data = key;
        if (node instanceof SkipListMapNode && next instanceof SkipListMapNode) {
            SkipListMapNode<K,V> node2 = (SkipListMapNode<K,V>) node;
            SkipListMapNode<K,V> next2 = (SkipListMapNode<K,V>) next;
            V value = node2.value;
            node2.value = next2.value;
            next2.value = value;
        }
    }

    protected static class SkipListMapNode<K extends Comparable<K>, V> extends SkipList.Node<K> {

        protected V value = null;

        protected SkipListMapNode(int level, K key) {
            super(level, key);
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

    private static class JavaCompatibleIteratorWrapper<K extends Comparable<K>,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private SkipListMap<K,V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(SkipListMap<K,V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
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

    private static class JavaCompatibleMapEntry<K extends Comparable<K>,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = 3282082818647198608L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleTreeMap<K extends Comparable<K>,V> extends java.util.AbstractMap<K,V> {

        private SkipListMap<K,V> map = null;

        protected JavaCompatibleTreeMap(SkipListMap<K,V> map) {
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
            if (map.list!=null && map.list.head!=null) {
                Node<K> n = map.list.head;
                while (n!=null) {
                    if (!(n instanceof SkipListMapNode)) continue;

                    SkipListMapNode<K,V> node = (SkipListMapNode<K,V>)n;
                    set.add(new JavaCompatibleMapEntry<K,V>(node.data,node.value));

                    n = node.getNext(0);    
                }
            }
            return set;
        }
    }
}
