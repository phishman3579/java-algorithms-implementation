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
public class HashMap<T extends Number> {

    private int hashingKey = 10;
    private List<T>[] map = null;
    private int size = 0;

    public HashMap() {
        initializeMap();
    }

    public HashMap(T[] values) {
        hashingKey = values.length;
        if (hashingKey>100) hashingKey = 100;
        initializeMap();
        populate(values);
    }

    @SuppressWarnings("unchecked")
    private void initializeMap() {
        map = new ArrayList[hashingKey];
        for (int i=0; i<map.length; i++) {
            map[i] = new ArrayList<T>();
        }
    }

    private void populate(T[] values) {
        for (T v : values) {
            put(v,v);
        }
    }

    private int hashingFunction(T key) {
        return key.intValue() % hashingKey;
    }

    public boolean put(T key, T value) {
        int hashedKey = hashingFunction(key);
        List<T> list = map[hashedKey];
        // Do not add duplicates
        for (int i=0; i<list.size(); i++) {
            T v = list.get(i);
            if (v == value) return false;
        }
        list.add(value);
        size++;
        return true;
    }

    public boolean remove(T key) {
        int hashedKey = hashingFunction(key);
        List<T> list = map[hashedKey];
        if (list.remove(key)) {
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(T value) {
        for (int key=0; key<map.length; key++) {
            List<T> list = map[key];
            for (int item=0; item<list.size(); item++) {
                T v = list.get(item);
                if (v == value) return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int key=0; key<map.length; key++) {
            List<T> list = map[key];
            for (int item=0; item<list.size(); item++) {
                T value = list.get(item);
                if (value!=null) builder.append(key).append("=").append(value).append(", ");
            }
        }
        return builder.toString();
    }
}
