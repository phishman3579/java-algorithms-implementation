package com.jwetherell.algorithms.data_structures;


/**
 * Hash Map backed by an array of LinkedLists.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HashMap {

    private int hashingKey = 10;
    private LinkedList[] map = null;
    private int size = 0;
    
    public HashMap() {
        initializeMap();
    }
    
    public HashMap(int[] values) {
        hashingKey = values.length;
        initializeMap();
        populate(values);
    }
    
    private void initializeMap() {
        map = new LinkedList[hashingKey];
        for (int i=0; i<map.length; i++) {
            map[i] = new LinkedList();
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
        LinkedList list = map[hashedKey];
        // Do not add duplicates
        for (int i=0; i<list.getSize(); i++) {
            int v = list.get(i);
            if (v == value) return false;
        }
        list.add(value);
        size++;
        return true;
    }
    
    public boolean remove(int key) {
        int hashedKey = hashingFunction(key);
        LinkedList list = map[hashedKey];
        if (list.remove(key)) {
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(int value) {
        for (int key=0; key<map.length; key++) {
            LinkedList list = map[key];
            for (int item=0; item<list.getSize(); item++) {
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
            LinkedList list = map[key];
            for (int item=0; item<list.getSize(); item++) {
                int value = list.get(item);
                if (value!=Integer.MIN_VALUE) builder.append(key).append("=").append(value).append(", ");
            }
        }
        return builder.toString();
    }
}
