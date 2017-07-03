package com.jwetherell.algorithms.data_structures.test.common;

@SuppressWarnings("unchecked")
public class JavaMapTest {

    public static <K,V, T extends Comparable<T>> boolean testJavaMap(java.util.Map<K,V> map, Class<T> type, String name,
                                                                     Integer[] unsorted, Integer[] sorted, Integer _invalid) {
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
                                   unsorted, _invalid);
        addInReverseOrderAndRemoveInReverseOrder(map, type, name, 
                                                 unsorted, _invalid);
        addInOrderAndRemoveInReverseOrder(map, type, name, 
                                          unsorted, _invalid);

        addInOrderAndRemoveInOrder(map, type, name, sorted, _invalid);
        addInReverseOrderAndRemoveInReverseOrder(map, type, name, 
                                                 sorted, _invalid);
        addInOrderAndRemoveInReverseOrder(map, type, name, 
                                          sorted, _invalid);

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

        return true;
    }

    private static <K,V, T extends Comparable<T>> boolean addInOrderAndRemoveInOrder(java.util.Map<K,V> map, Class<T> keyType, String name, 
                                                                                     Integer[] data, Integer _invalid)
    {
        for (int i = 0; i < data.length; i++) {
            Integer item = data[i];
            K k = null;
            V v = null;
            if (keyType.isAssignableFrom(Integer.class)) {
                k = (K)Utils.parseT(item, keyType);
                v = (V)Utils.parseT(item, String.class);
            } else if (keyType.isAssignableFrom(String.class)) {
                k = (K)Utils.parseT(item, keyType);
                v = (V)Utils.parseT(item, Integer.class);
            }
            map.put(k, v);
        }

        K invalidKey = (K) Utils.parseT(_invalid, keyType);
        boolean contains = map.containsKey(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,map);
            return false;
        }

        for (Integer item : data) {
            K k = (K)Utils.parseT(item, keyType);
            map.containsKey(k);
        }

        for (int i = 0; i < data.length; i++) {
            Integer item = data[i];
            K k = (K)Utils.parseT(item, keyType);
            removed = map.remove(k);
            if (removed==null) {
                System.err.println(name+" invalidity check. removed=" + removed);
                Utils.handleError(data,map);
                return false;
            }
            
        }

        if (!testMapEntrySet(map, keyType, data)) return false;

        if (!map.isEmpty()) {
            System.err.println(name+" isEmpty() failed.");
            Utils.handleError(data,map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" size() failed.");
            Utils.handleError(data,map);
            return false;
        }
        return true;
    }

    private static <K,V, T extends Comparable<T>> boolean addInReverseOrderAndRemoveInReverseOrder(java.util.Map<K,V> map, Class<T> keyType, String name, 
                                                                                                   Integer[] data, Integer _invalid)
    {
        for (int i = data.length - 1; i >= 0; i--) {
            Integer item = data[i];
            K k = null;
            V v = null;
            if (keyType.isAssignableFrom(Integer.class)) {
                k = (K)Utils.parseT(item, keyType);
                v = (V)Utils.parseT(item, String.class);
            } else if (keyType.isAssignableFrom(String.class)) {
                k = (K)Utils.parseT(item, keyType);
                v = (V)Utils.parseT(item, String.class);
            }
            map.put(k, v);
        }

        K invalidKey = (K)Utils.parseT(_invalid, keyType);
        boolean contains = map.containsKey(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,map);
            return false;
        }

        for (Integer item : data) {
            K k = (K)Utils.parseT(item, keyType);
            map.containsKey(k);
        }

        for (int i = data.length - 1; i >= 0; i--) {
            Integer item = data[i];
            K k = (K)Utils.parseT(item, keyType);
            removed = map.remove(k);
            if (removed==null) {
                System.err.println(name+" invalidity check. removed=" + removed);
                Utils.handleError(data,map);
                return false;
            }
        }

        if (!map.isEmpty()) {
            System.err.println(name+" isEmpty() failed.");
            Utils.handleError(data,map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" size() failed.");
            Utils.handleError(data,map);
            return false;
        }
        return true;
    }

    private static <K,V, T extends Comparable<T>> boolean addInOrderAndRemoveInReverseOrder(java.util.Map<K,V> map, Class<T> keyType, String name, 
                                                                                            Integer[] data, Integer _invalid) {
        for (int i = 0; i < data.length; i++) {
            Integer item = data[i];
            K k = null;
            V v = null;
            if (keyType.isAssignableFrom(Integer.class)) {
                k = (K)Utils.parseT(item, keyType);
                v = (V)Utils.parseT(item, String.class);
            } else if (keyType.isAssignableFrom(String.class)) {
                k = (K)Utils.parseT(item, keyType);
                v = (V)Utils.parseT(item, Integer.class);
            }
            map.put(k, v);
        }

        K invalidKey = (K)Utils.parseT(_invalid, keyType);
        boolean contains = map.containsKey(invalidKey);
        V removed = map.remove(invalidKey);
        if (contains || (removed!=null)) {
            System.err.println(name+" sorted invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,map);
            return false;
        }

        for (Integer item : data) {
            K k = (K)Utils.parseT(item, keyType);
            map.containsKey(k);
        }

        for (int i = data.length - 1; i >= 0; i--) {
            Integer item = data[i];
            K k = (K)Utils.parseT(item, keyType);
            removed = map.remove(k);
            if (removed==null) {
                System.err.println(name+" invalidity check. removed=" + removed);
                Utils.handleError(data,map);
                return false;
            }
        }

        if (!testMapEntrySet(map, keyType, data)) return false;

        if (!map.isEmpty()) {
            System.err.println(name+" sorted isEmpty() failed.");
            Utils.handleError(data,map);
            return false;
        }
        if (map.size()!=0) {
            System.err.println(name+" sorted size() failed.");
            Utils.handleError(data,map);
            return false;
        }
        return true;
    }

    private static <K,V, T extends Comparable<T>> boolean testMapEntrySet(java.util.Map<K, V> map, Class<T> keyType,
                                                                          Integer[] data) {
        {   // Test keys
            for (int i = 0; i < data.length; i++) {
                Integer item = data[i];
                K k = null;
                V v = null;
                if (keyType.isAssignableFrom(Integer.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, Integer.class);
                }
                map.put(k, v);
            }

            java.util.Set<K> set = map.keySet();
            for (int i = 0; i < data.length; i++) {
                Integer item = data[i];
                K k = (K)Utils.parseT(item, keyType);
                if (!set.contains(k)) {
                    System.err.println("MayEntry contains() failure.");
                    Utils.handleError(data,map);
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
                Utils.handleError(data,map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println("MayEntry size()!=0 failure.");
                Utils.handleError(data,map);
                return false;
            }
        }

        {   // Test values
            for (int i = 0; i < data.length; i++) {
                Integer item = data[i];
                K k = null;
                V v = null;
                if (keyType.isAssignableFrom(Integer.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    k = (K)Utils.parseT(item, keyType);
                    v = (V)Utils.parseT(item, Integer.class);
                }
                map.put(k, v);
            }

            java.util.Collection<V> collection = map.values();
            for (int i = 0; i < data.length; i++) {
                Integer value = data[i];
                V v = null;
                // These are reversed on purpose
                if (keyType.isAssignableFrom(Integer.class)) {
                    v = (V)Utils.parseT(value, String.class);
                } else if (keyType.isAssignableFrom(String.class)) {
                    v = (V)Utils.parseT(value, Integer.class);
                }
                if (!collection.contains(v)) {
                    System.err.println("MayEntry contains() failure.");
                    Utils.handleError(data,map);
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
                Utils.handleError(data,map);
                return false;
            }
            if (map.size()!=0) {
                System.err.println("MayEntry size()!=0 failure.");
                Utils.handleError(data,map);
                return false;
            }
        }
        return true;
    }
}
