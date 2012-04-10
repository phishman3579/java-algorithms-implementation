package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * Hash Map backed by an array of ArrayLists. hash map is a data structure that uses a hash function 
 * to map identifying values, known as keys, to their associated values. 
 * 
 * http://en.wikipedia.org/wiki/Hash_table
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HashMap<K extends Number, V extends Number> {

    @SuppressWarnings("unchecked")
    private K hashingKey = (K) new Integer(10);
    private List<V>[] map = null;
    private int size = 0;

    public HashMap() {
        initializeMap();
    }

    @SuppressWarnings("unchecked")
    public HashMap(V[] values) {
        hashingKey = (K) new Integer(values.length);
        if (hashingKey.intValue()>100) hashingKey = (K) new Integer(100);
        initializeMap();
        populate(values);
    }

    public boolean put(K key, V value) {
        int hashedKey = hashingFunction(key);
        List<V> list = map[hashedKey];
        // Do not add duplicates
        for (int i=0; i<list.size(); i++) {
            V v = list.get(i);
            if (v == value) return false;
        }
        list.add(value);
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        int hashedKey = hashingFunction(key);
        List<V> list = map[hashedKey];
        for (int i=0; i<list.size(); i++) {
            V v = list.get(i);
            if (v.equals((V)key)) return v;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public boolean remove(K key) {
        int hashedKey = hashingFunction(key);
        List<V> list = map[hashedKey];
        if (list.remove((V)key)) {
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(V value) {
        for (int key=0; key<map.length; key++) {
            List<V> list = map[key];
            for (int item=0; item<list.size(); item++) {
                V v = list.get(item);
                if (v == value) return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void initializeMap() {
        map = new ArrayList[hashingKey.intValue()];
        for (int i=0; i<map.length; i++) {
            map[i] = new ArrayList<V>();
        }
    }

    @SuppressWarnings("unchecked")
    private void populate(V[] values) {
        for (V v : values) {
            put((K)v,v);
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
        for (int key=0; key<map.length; key++) {
            List<V> list = map[key];
            for (int item=0; item<list.size(); item++) {
                V value = list.get(item);
                if (value!=null) builder.append(key).append("=").append(value).append(", ");
            }
        }
        return builder.toString();
    }
}
