package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.test.common.Utils.Type;

public class JavaMapTest {

    public static <K,V> boolean testJavaMap(java.util.Map<K,V> map, Type keyType, String name,
                                            Integer[] unsorted, Integer[] sorted, Integer INVALID) {
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

        addInOrderAndRemoveInOrder(map, keyType, name, 
                                   unsorted, INVALID);
        addInReverseOrderAndRemoveInReverseOrder(map, keyType, name, 
                                                 unsorted, INVALID);
        addInOrderAndRemoveInReverseOrder(map, keyType, name, 
                                          unsorted, INVALID);

        addInOrderAndRemoveInOrder(map, keyType, name, sorted, INVALID);
        addInReverseOrderAndRemoveInReverseOrder(map, keyType, name, 
                                                 sorted, INVALID);
        addInOrderAndRemoveInReverseOrder(map, keyType, name, 
                                          sorted, INVALID);

        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K,V> boolean addInOrderAndRemoveInOrder(java.util.Map<K,V> map, Type keyType, String name, 
                                                            Integer[] data, Integer invalid)
    {
        for (int i = 0; i < data.length; i++) {
            Integer item = data[i];
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

        for (Integer item : data) {
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            map.containsKey(k);
        }

        for (int i = 0; i < data.length; i++) {
            Integer item = data[i];
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
    private static <K,V> boolean addInReverseOrderAndRemoveInReverseOrder(java.util.Map<K,V> map, Type keyType, String name, 
                                                                          Integer[] data, Integer invalid)
    {
        for (int i = data.length - 1; i >= 0; i--) {
            Integer item = data[i];
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

        for (Integer item : data) {
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            map.containsKey(k);
        }

        for (int i = data.length - 1; i >= 0; i--) {
            Integer item = data[i];
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
    private static <K,V> boolean addInOrderAndRemoveInReverseOrder(java.util.Map<K,V> map, Type keyType, String name, 
                                                                   Integer[] data, Integer invalid) {
        for (int i = 0; i < data.length; i++) {
            Integer item = data[i];
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

        for (Integer item : data) {
            K k = null;
            if (keyType == Type.Integer) {
                k = (K)item;
            } else if (keyType == Type.String) {
                k = (K)String.valueOf(item);
            }
            map.containsKey(k);
        }

        for (int i = data.length - 1; i >= 0; i--) {
            Integer item = data[i];
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
    private static <K,V> boolean testMapEntrySet(java.util.Map<K, V> map, Type keyType,
                                                 Integer[] data) {
        {   // Test keys
            for (int i = 0; i < data.length; i++) {
                Integer item = data[i];
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
                Integer key = data[i];
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
                Integer item = data[i];
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
                Integer value = data[i];
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
