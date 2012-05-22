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


    public HashMap(K key) {
        hashingKey = key;
        initializeMap();
    }

    public HashMap() {
        initializeMap();
    }

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

    public V get(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = map[hashedKey];
        for (Pair<K, V> p : list) {
            if (p.key.equals(key)) return p.value;
        }
        return null;
    }

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

    public boolean contains(K key) {
        int hashedKey = hashingFunction(key);
        List<Pair<K, V>> list = map[hashedKey];
        for (Pair<K, V> pair : list) {
            if (pair.key.equals(key)) return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void initializeMap() {
        map = new ArrayList[hashingKey.intValue()];
        for (int i = 0; i < map.length; i++) {
            map[i] = new ArrayList<Pair<K, V>>(2);
        }
    }

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
