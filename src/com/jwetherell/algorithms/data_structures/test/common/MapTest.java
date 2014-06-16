package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.IMap;
import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class MapTest {

    /**
     * In computer science, an associative array, map, symbol table, or dictionary is an abstract data 
     * type composed of a collection of (key, value) pairs, such that each possible key appears at most 
     * once in the collection.
     * 
     * http://en.wikipedia.org/wiki/Associative_array
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     * 
     * @param map Map to test.
     * @param type Type of data in the map (Either String or Integer).
     * @param name Name used in debug.
     * @param data test data.
     * @param invalid Invalid data which isn't in the data-structure.
     * @return True if the map passes it's invariants tests.
     */
    @SuppressWarnings("unchecked")
    public static <K,V, T extends Comparable<T>> boolean testMap(IMap<K,V> map, Type type, String name,
                                                                 T[] data, T invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            K k = null;
            V v = null;
            if (type == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (type == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            V added = map.put(k,v);
            if ((!map.validate() || (map.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(map);
                return false;
            }
            if ((added!=null || !map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists.");
                Utils.handleError(map);
                return false;
            }
        }

        K invalidKey = null;
        if (type == Type.Integer) {
            invalidKey = (K)invalid;
        } else if (type == Type.String) {
            invalidKey = (K)String.valueOf(invalid);
        }
        boolean contains = map.contains(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(map);
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            K k = null;
            if (type == Type.Integer) {
                k = (K)item;
            } else if (type == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if ((!map.validate() || (map.size() != (data.length-(i+1))))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(map);
                return false;
            }
            if (map.contains(k)) {
                System.err.println(name+" YIKES!! " + item + " still exists.");
                Utils.handleError(map);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = data.length/4;
        int half = data.length/2;
        for (int i = 0; i < half; i++) {
            T item = data[i];
            K k = null;
            V v = null;
            if (type == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (type == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            V added = map.put(k,v);
            if ((!map.validate() || (map.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(map);
                return false;
            }
            if ((added!=null || !map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists.");
                Utils.handleError(map);
                return false;
            }
        }
        for (int i = (half-1); i >= quarter; i--) {
            T item = data[i];
            K k = null;
            if (type == Type.Integer) {
                k = (K)item;
            } else if (type == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if ((!map.validate() || (map.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(map);
                return false;
            }
            if ((removed==null || map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " still exists.");
                Utils.handleError(map);
                return false;
            }
        }
        for (int i = quarter; i < data.length; i++) {
            T item = data[i];
            K k = null;
            V v = null;
            if (type == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (type == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            V added = map.put(k,v);
            if ((!map.validate() || (map.size() != (i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(map);
                return false;
            }
            if ((added!=null || !map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists.");
                Utils.handleError(map);
                return false;
            }
        }
        for (int i = data.length-1; i >= 0; i--) {
            T item = data[i];
            K k = null;
            if (type == Type.Integer) {
                k = (K)item;
            } else if (type == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if ((!map.validate() || (map.size() != i))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(map);
                return false;
            }
            if ((removed==null || map.contains(k))) {
                System.err.println(name+" YIKES!! " + item + " still exists.");
                Utils.handleError(map);
                return false;
            }
        }

        if ((map.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(map);
            return false;
        }

        return true;
    }
}
