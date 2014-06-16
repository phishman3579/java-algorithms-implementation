package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class JavaMapTest {

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
     * @param unsorted Unsorted test data.
     * @param sorted Sorted test data.
     * @param invalid Invalid data which isn't in the data-structure.
     * @return True if the map passes it's invariants tests.
     */
    public static <K,V, T extends Comparable<T>> boolean testJavaMap(java.util.Map<K,V> map, Type type, String name,
                                                                     T[] unsorted, T[] sorted, T invalid) {
        // Make sure the map is empty
        if (!map.isEmpty()) {
            System.err.println(name+" initial isEmpty() failed.");
            Utils.handleError(map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" initial size() failed.");
            Utils.handleError(map);
            return false;
        }

        addInOrderAndRemoveInOrder(map, type, name, 
                                   unsorted, invalid);
        addInReverseOrderAndRemoveInReverseOrder(map, type, name, 
                                                 unsorted, invalid);
        addInOrderAndRemoveInReverseOrder(map, type, name, 
                                          unsorted, invalid);

        addInOrderAndRemoveInOrder(map, type, name, sorted, invalid);
        addInReverseOrderAndRemoveInReverseOrder(map, type, name, 
                                                 sorted, invalid);
        addInOrderAndRemoveInReverseOrder(map, type, name, 
                                          sorted, invalid);

        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K,V, T extends Comparable<T>> boolean addInOrderAndRemoveInOrder(java.util.Map<K,V> map, Type keyType, String name, 
                                                                                     T[] data, T invalid)
    {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            map.put(k, v);
        }

        K invalidKey = null;
        if (keyType == Type.Integer) {
            invalidKey = (K)invalid;
        } else if (keyType == Type.String) {
            invalidKey = (K)String.valueOf(invalid);
        }
        boolean contains = map.containsKey(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" unsorted invalidity check. contains=" + contains + " removed=" + removed);
            return false;
        }

        for (T item : data) {
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            map.containsKey(k);
        }

        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if (removed==null) {
                System.err.println(name+" unsorted invalidity check. removed=" + removed);
                return false;
            }
            
        }

        if (!testMapEntrySet(map, keyType, data)) return false;

        if (!map.isEmpty()) {
            System.err.println(name+" unsorted isEmpty() failed.");
            Utils.handleError(map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" unsorted size() failed.");
            Utils.handleError(map);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K,V, T extends Comparable<T>> boolean addInReverseOrderAndRemoveInReverseOrder(java.util.Map<K,V> map, Type keyType, String name, 
                                                                                                   T[] data, T invalid)
    {
        for (int i = data.length - 1; i >= 0; i--) {
            T item = data[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            map.put(k, v);
        }

        K invalidKey = null;
        if (keyType == Type.Integer) {
            invalidKey = (K)invalid;
        } else if (keyType == Type.String) {
            invalidKey = (K)String.valueOf(invalid);
        }
        boolean contains = map.containsKey(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" unsorted invalidity check. contains=" + contains + " removed=" + removed);
            return false;
        }

        for (T item : data) {
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            map.containsKey(k);
        }

        for (int i = data.length - 1; i >= 0; i--) {
            T item = data[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if (removed==null) {
                System.err.println(name+" unsorted invalidity check. removed=" + removed);
                return false;
            }
        }

        if (!map.isEmpty()) {
            System.err.println(name+" unsorted isEmpty() failed.");
            Utils.handleError(map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" unsorted size() failed.");
            Utils.handleError(map);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K,V, T extends Comparable<T>> boolean addInOrderAndRemoveInReverseOrder(java.util.Map<K,V> map, Type keyType, String name, 
                                                                                            T[] data, T invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            K k = null;
            V v = null;
            if (keyType == Type.Integer) {
                k = (K)item;
                v = (V)String.valueOf(item);
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
                v = (V)item;
            }
            map.put(k, v);
        }

        K invalidKey = null;
        if (keyType == Type.Integer) {
            invalidKey = (K)invalid;
        } else if (keyType == Type.String) {
            invalidKey = (K)String.valueOf(invalid);
        }
        boolean contains = map.containsKey(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" sorted invalidity check. contains=" + contains + " removed=" + removed);
            return false;
        }

        for (T item : data) {
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            map.containsKey(k);
        }

        for (int i = data.length - 1; i >= 0; i--) {
            T item = data[i];
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            removed = map.remove(k);
            if (removed==null) {
                System.err.println(name+" unsorted invalidity check. removed=" + removed);
                return false;
            }
        }

        if (!testMapEntrySet(map, keyType, data)) return false;

        if (!map.isEmpty()) {
            System.err.println(name+" sorted isEmpty() failed.");
            Utils.handleError(map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" sorted size() failed.");
            Utils.handleError(map);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K,V, T extends Comparable<T>> boolean testMapEntrySet(java.util.Map<K, V> map, Type keyType,
                                                                          T[] data) {
        {   // Test keys
            for (int i = 0; i < data.length; i++) {
                T item = data[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }

            java.util.Set<K> set = map.keySet();
            for (int i = 0; i < data.length; i++) {
                T key = data[i];
                K k = null;
                if (keyType == Type.Integer) {
                    k = (K)key;
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(key);
                }
                if (!set.contains(k)) {
                    System.err.println("MayEntry contains() failure.");
                    Utils.handleError(map);
                    return false;
                }
            }

            java.util.Iterator<K> keyIter = set.iterator();
            while (keyIter.hasNext()) {
                keyIter.next();
                keyIter.remove();
            }

            if (!map.isEmpty()) {
                System.err.println("MayEntry isEmpty() failure.");
                Utils.handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println("MayEntry size()!=0 failure.");
                Utils.handleError(map);
                return false;
            }
        }

        {   // Test values
            for (int i = 0; i < data.length; i++) {
                T item = data[i];
                K k = null;
                V v = null;
                if (keyType == Type.Integer) {
                    k = (K)item;
                    v = (V)String.valueOf(item);
                } else if (keyType == Type.String) {
                    k = (K)String.valueOf(item);
                    v = (V)item;
                }
                map.put(k, v);
            }

            java.util.Collection<V> collection = map.values();
            for (int i = 0; i < data.length; i++) {
                T value = data[i];
                V v = null;
                // These are reversed on purpose
                if (keyType == Type.Integer) {
                    v = (V)String.valueOf(value);
                } else if (keyType == Type.String) {
                    v = (V)value;
                }
                if (!collection.contains(v)) {
                    System.err.println("MayEntry contains() failure.");
                    Utils.handleError(map);
                    return false;
                }
            }

            java.util.Iterator<V> valueIter = collection.iterator();
            while (valueIter.hasNext()) {
                valueIter.next();
                valueIter.remove();
            }

            if (!map.isEmpty()) {
                System.err.println("MayEntry isEmpty() failure.");
                Utils.handleError(map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println("MayEntry size()!=0 failure.");
                Utils.handleError(map);
                return false;
            }
        }
        return true;
    }
}
