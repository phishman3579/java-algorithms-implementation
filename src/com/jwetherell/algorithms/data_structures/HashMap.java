package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * Hash Map backed by an array of ArrayLists.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HashMap {

    private int hashingKey = 10;
    private List<Integer>[] map = null;
    private int size = 0;
    
    public HashMap() {
        initializeMap();
    }
    
    public HashMap(int[] values) {
        hashingKey = values.length;
        if (hashingKey>100) hashingKey = 100;
        initializeMap();
        populate(values);
    }
    
    @SuppressWarnings("unchecked")
    private void initializeMap() {
        map = new ArrayList[hashingKey];
        for (int i=0; i<map.length; i++) {
            map[i] = new ArrayList<Integer>();
        }
    }
    
    private void populate(int[] values) {
        for (int v : values) {
            put(v,v);
        }
    }
    
    private int hashingFunction(int key) {
        return key % hashingKey;
    }
    
    public boolean put(int key, int value) {
        int hashedKey = hashingFunction(key);
        List<Integer> list = map[hashedKey];
        // Do not add duplicates
        for (int i=0; i<list.size(); i++) {
            int v = list.get(i);
            if (v == value) return false;
        }
        list.add(value);
        size++;
        return true;
    }
    
    public boolean remove(int key) {
        int hashedKey = hashingFunction(key);
        List<Integer> list = map[hashedKey];
        if (list.remove((Object)key)) {
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(int value) {
        for (int key=0; key<map.length; key++) {
            List<Integer> list = map[key];
            for (int item=0; item<list.size(); item++) {
                int v = list.get(item);
                if (v == value) return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int key=0; key<map.length; key++) {
            List<Integer> list = map[key];
            for (int item=0; item<list.size(); item++) {
                int value = list.get(item);
                if (value!=Integer.MIN_VALUE) builder.append(key).append("=").append(value).append(", ");
            }
        }
        return builder.toString();
    }
}
