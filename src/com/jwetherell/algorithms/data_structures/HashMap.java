package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * Hash Map backed by an array of ArrayLists. hash map is a data structure that
 * uses a hash function to map identifying values, known as keys, to their
 * associated values.
 * 
 * http://en.wikipedia.org/wiki/Hash_table
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HashMap<K extends Number, V> {

    @SuppressWarnings("unchecked")
    private K hashingKey = (K) new Integer(10);
    private List<Pair<K, V>>[] map = null;
    private int size = 0;

    /**
     * Create a hash map with K as the hashing key.
     * 
     * @param key
     *            to use for the hashing key.
     */
    public HashMap(K key) {
        hashingKey = key;
        initializeMap();
    }

    /**
     * Create a hash map with the default hashing key.
     */
    public HashMap() {
        initializeMap();
    }

    /**
     * Put key->value pair in the hash.
     * 
     * @param key
     *            to be inserted.
     * @param value
     *            to be inserted.
     * @return True if inserted or False is key/value already exists.
     */
    public boolean put(K key, V value) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = map[hashedKey];
        // Do not add duplicates
        for (Pair<K, V> p : list) {
            if (p.value.equals(value)) return false;
        }
        list.add(new Pair<K, V>(key, value));
        size++;
        return true;
    }

    /**
     * Get value for key.
     * 
     * @param key
     *            to get value for.
     * @return value mapped to key.
     */
    public V get(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = map[hashedKey];
        for (Pair<K, V> p : list) {
            if (p.key.equals(key)) return p.value;
        }
        return null;
    }

    /**
     * Remove key and value from hash map.
     * 
     * @param key
     *            to remove from the hash map.
     * @return True if removed or False if not found.
     */
    public boolean remove(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = map[hashedKey];
        for (Pair<K, V> pair : list) {
            if (pair.key.equals(key)) {
                list.remove(pair);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Does the hash map contain the key.
     * 
     * @param key
     *            to locate in the hash map.
     * @return True if key is in the hash map.
     */
    public boolean contains(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = map[hashedKey];
        for (Pair<K, V> pair : list) {
            if (pair.key.equals(key)) return true;
        }
        return false;
    }

    /**
     * Number of key/value pairs in the hash map.
     * 
     * @return number of key/value pairs.
     */
    public int size() {
        return size;
    }

    /**
     * Initialize the hash array.
     */
    @SuppressWarnings("unchecked")
    private void initializeMap() {
        map = new ArrayList[hashingKey.intValue()];
        for (int i = 0; i < map.length; i++) {
            map[i] = new ArrayList<Pair<K, V>>(2);
        }
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int key = 0; key < map.length; key++) {
            List<Pair<K, V>> list = map[key];
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

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof Pair)) return false;

            @SuppressWarnings("unchecked")
            Pair<K, V> pair = (Pair<K, V>) obj;
            return key.equals(pair.key);
        }
    }
}
