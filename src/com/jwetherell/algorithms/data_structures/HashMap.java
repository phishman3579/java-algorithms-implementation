package com.jwetherell.algorithms.data_structures;

import java.util.LinkedList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.interfaces.IMap;

/**
 * Hash Map backed by an array of ArrayLists. hash map is a data structure that
 * uses a hash function to map identifying values, known as keys, to their
 * associated values.
 * 
 * http://en.wikipedia.org/wiki/Hash_table
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class HashMap<K extends Number, V> implements IMap<K,V> {

    private K hashingKey = (K) new Integer(10);
    private List<Pair<K, V>>[] array = null;
    private int size = 0;

    /**
     * Create a hash map with K as the hashing key.
     * 
     * @param key
     *            to use for the hashing key.
     */
    public HashMap(K key) {
        if (key.intValue()>0) hashingKey = key;
        initializeMap();
    }

    /**
     * Create a hash map with the default hashing key.
     */
    public HashMap() {
        initializeMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V prev = null;
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = array[hashedKey];
        boolean exist = false;
        // Do not add duplicates
        for (Pair<K, V> p : list) {
            if (p.key.equals(key)) {
                prev = p.value;
                p.value = value;
                exist = true;
                break;
            }
        }
        if (!exist) 
            list.add(new Pair<K, V>(key, value));
        size++;
        return prev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = array[hashedKey];
        for (Pair<K, V> p : list) {
            if (p.key.equals(key))
                return p.value;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        return (get(key)!=null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = array[hashedKey];
        for (Pair<K, V> pair : list) {
            if (pair.key.equals(key)) {
                list.remove(pair);
                size--;
                V value = pair.value;
                pair.key = null;
                pair.value = null;
                return value;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (int i=0; i<array.length; i++) {
            array[i].clear();
        }
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Initialize the hash array.
     */
    private void initializeMap() {
        array = new LinkedList[hashingKey.intValue()];
        for (int i = 0; i < array.length; i++) {
            array[i] = new LinkedList<Pair<K, V>>();
        }
        size = 0;
    }

    /**
     * The hashing function. Converts the key into an integer.
     * 
     * @param key
     *            to create a hash for.
     * @return Integer which represents the key.
     */
    private int hashingFunction(K key) {
        return key.intValue() % hashingKey.intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Map<K,V> toMap() {
        return (new JavaCompatibleHashMap<K,V>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        java.util.Set<K> keys = new java.util.HashSet<K>();
        for (List<Pair<K, V>> list : array) {
            for (Pair<K, V> pair : list) {
                K k = pair.key;
                V v = pair.value;
                if (k==null || v==null) return false;
                if (keys.contains(k)) return false;
                keys.add(k);
            }
        }
        return (keys.size()==size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int key = 0; key < array.length; key++) {
            List<Pair<K, V>> list = array[key];
            for (int item = 0; item < list.size(); item++) {
                Pair<K, V> p = list.get(item);
                V value = p.value;
                if (value != null) builder.append(key).append("=").append(value).append(", ");
            }
        }
        return builder.toString();
    }

    private static final class Pair<K extends Number, V> {

        private K key = null;
        private V value = null;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * (this.key.hashCode());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof Pair)) return false;

            Pair<K, V> pair = (Pair<K, V>) obj;
            return key.equals(pair.key);
        }
    }

    private static class JavaCompatibleIteratorWrapper<K extends Number,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private HashMap<K,V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(HashMap<K,V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
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

    private static class JavaCompatibleMapEntry<K extends Number,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = 3282082818647198608L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleHashMap<K extends Number,V> extends java.util.AbstractMap<K,V> {

        private HashMap<K,V> map = null;

        protected JavaCompatibleHashMap(HashMap<K,V> map) {
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
        public boolean containsKey(Object key) {
            return map.contains((K)key);
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
            for (List<Pair<K, V>> list : map.array) {
                for (Pair<K, V> p : list) {
                    java.util.Map.Entry<K, V> entry = new JavaCompatibleMapEntry<K, V>(p.key, p.value);
                    set.add(entry);
                }
            }
            return set;
        }
    }
}
