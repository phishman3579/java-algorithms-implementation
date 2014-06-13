package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.IMap;
import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class MapTest {

    @SuppressWarnings("unchecked")
    public static <K,V> boolean testMap(IMap<K,V> map, Type keyType, String name,
                                        Integer[] unsorted, Integer invalid) {
        for (int i = 0; i < unsorted.length; i++) {
            Integer item = unsorted[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
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
        if (keyType == Type.Integer) {
            invalidKey = (K)invalid;
        } else if (keyType == Type.String) {
            invalidKey = (K)String.valueOf(invalid);
        }
        boolean contains = map.contains(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(map);
            return false;
        }

        for (int i = 0; i < unsorted.length; i++) {
            Integer item = unsorted[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if ((!map.validate() || (map.size() != (unsorted.length-(i+1))))) {
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
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        for (int i = 0; i < half; i++) {
            Integer item = unsorted[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
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
            Integer item = unsorted[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
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
        for (int i = quarter; i < unsorted.length; i++) {
            Integer item = unsorted[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
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
        for (int i = unsorted.length-1; i >= 0; i--) {
            Integer item = unsorted[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
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
