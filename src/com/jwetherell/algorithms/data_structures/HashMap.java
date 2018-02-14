package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.interfaces.IMap;

/**
 * Hash Map using either chaining or probing. hash map is a data structure that
 * uses a hash function to map identifying values, known as keys, to their
 * associated values.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Hash_table">Hash Map/Table (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class HashMap<K, V> implements IMap<K,V> {

    public static enum Type { CHAINING, PROBING }

    private HashMap<K,V> delegateMap = null;

    private static class ChainingHashMap<K, V> extends HashMap<K, V> {

        private float loadFactor = 10.0f;
        private int minimumSize = 1024;
        private int initialListSize = 10;
        private List<Pair<K, V>>[] array = null;
        private int size = 0;

        /**
         * Create a hash map with K as the hashing key.
         * 
         * @param size
         *            initial size.
         */
        public ChainingHashMap(int size) {
            initializeMap(size);
        }

        /**
         * Create a hash map with the default hashing key.
         */
        public ChainingHashMap() {
            initializeMap(minimumSize);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            return put(new Pair<K, V>(key, value));
        }

        public V put(Pair<K,V> newPair) {
            int index = indexOf(newPair.key.hashCode());
            List<Pair<K, V>> list = array[index];
            V prev = null;
            boolean exist = false;
            // Do not add duplicates
            for (Pair<K, V> p : list) {
                if (p.key.equals(newPair.key)) {
                    prev = p.value;
                    p.value = newPair.value;
                    exist = true;
                    break;
                }
            }

            if (!exist) 
                list.add(newPair);

            size++;

            // If size is greater than threshold
            int maxSize = (int)(loadFactor*array.length);
            if (size >= maxSize)
                increase();

            return prev;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            int index = indexOf(key.hashCode());
            List<Pair<K, V>> list = array[index];
            for (Pair<K, V> p : list) {
                if (p.key.equals(key))
                    return p.value;
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(K key) {
            return (get(key)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(K key) {
            int index = indexOf(key.hashCode());
            List<Pair<K, V>> list = array[index];
            for (Pair<K, V> pair : list) {
                if (pair.key.equals(key)) {
                    list.remove(pair);
                    size--;

                    V value = pair.value;
                    pair.key = null;
                    pair.value = null;

                    int loadFactored = (int)(size/loadFactor);
                    int smallerSize = getSmallerSize(array.length);
                    if (loadFactored < smallerSize && smallerSize > minimumSize)
                        reduce();

                    return value;
                }
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            for (int i=0; i<array.length; i++)
                array[i].clear();
            size = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        private void increase() {
            // Save old data
            List<Pair<K, V>>[] temp = this.array;

            // Calculate new size and assign
            int length = getLargerSize(array.length);
            //System.out.println("increase from "+array.length+" to "+length);
            initializeMap(length);

            // Re-hash old data
            for (List<Pair<K, V>> list : temp) {
                for (Pair<K, V> p :list) {
                    this.put(p);
                }
            }
        }

        private void reduce() {
            // Save old data
            List<Pair<K, V>>[] temp = this.array;

            // Calculate new size and check minimum
            int length = getSmallerSize(array.length);
            //System.out.println("reduce from "+array.length+" to "+length);
            initializeMap(length);

            // Re-hash old data
            for (List<Pair<K, V>> list : temp) {
                for (Pair<K, V> p :list) {
                    this.put(p);
                }
            }
        }

        /**
         * Increases the input ten-fold. e.g. 16->160
         */
        private static final int getLargerSize(int input) {
            return input*10;
        }

        /**
         * Returns one fourth of the input. e.g. 16->4
         */
        private static final int getSmallerSize(int input) {
            return input/4;
        }

        /**
         * Initialize the hash array.
         */
        private void initializeMap(int length) {
            this.array = new ArrayList[length];
            for (int i = 0; i < array.length; i++)
                this.array[i] = new ArrayList<Pair<K, V>>(initialListSize);
            this.size = 0;
        }

        /**
         * Converts the key into an integer.
         * 
         * @param h
         *            hash to get index of.
         * @return Integer which represents the key.
         */
        private int indexOf(int h) {
            return h & (array.length-1);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map<K,V> toMap() {
            return (new JavaCompatibleHashMap<K,V>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean validate() {
            java.util.Set<K> keys = new java.util.HashSet<K>();
            for (List<Pair<K, V>> list : array) {
                for (Pair<K, V> pair : list) {
                    K k = pair.key;
                    V v = pair.value;
                    if (k==null || v==null) return false;
                    if (keys.contains(k)) return false;
                    keys.add(k);
                }
            }
            return (keys.size()==size());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int key = 0; key < array.length; key++) {
                List<Pair<K, V>> list = array[key];
                for (int item = 0; item < list.size(); item++) {
                    Pair<K, V> p = list.get(item);
                    V value = p.value;
                    if (value != null) builder.append(key).append("=").append(value).append(", ");
                }
            }
            return builder.toString();
        }

        private static class JavaCompatibleHashMap<K,V> extends java.util.AbstractMap<K,V> {

            private ChainingHashMap<K,V> map = null;

            protected JavaCompatibleHashMap(ChainingHashMap<K,V> map) {
                this.map = map;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public V put(K key, V value) {
                return map.put(key, value);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public V remove(Object key) {
                return map.remove((K)key);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean containsKey(Object key) {
                return map.contains((K)key);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                map.clear();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int size() {
                return map.size();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
                java.util.Set<java.util.Map.Entry<K, V>> set = new java.util.HashSet<java.util.Map.Entry<K, V>>() {

                    private static final long serialVersionUID = 1L;

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                        return (new JavaCompatibleIteratorWrapper<K,V>(map,super.iterator()));
                    }
                };
                for (List<Pair<K, V>> list : map.array) {
                    for (Pair<K, V> p : list) {
                        java.util.Map.Entry<K, V> entry = new JavaCompatibleMapEntry<K, V>(p.key, p.value);
                        set.add(entry);
                    }
                }
                return set;
            }
        }
    }

    private static class ProbingHashMap<K, V> extends HashMap<K, V> {

        private int hashingKey = -1;
        private float loadFactor = 0.6f;
        private int minimumSize = 1024;
        private Pair<K, V>[] array = null;
        private int size = 0;

        /**
         * Create a hash map with K as the hash.
         * 
         * @param size
         *            to use for the hash.
         */
        public ProbingHashMap(int size) {
            initializeMap(size);
        }

        /**
         * Create a hash map with the default hashing key.
         */
        public ProbingHashMap() {
            initializeMap(minimumSize);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            return put(new Pair<K,V>(key,value));
        }

        private V put(Pair<K,V> newPair) {
            V prev = null;
            int index = indexOf(newPair.key);

            // Check initial position
            Pair<K, V> pair = array[index];
            if (pair == null) {
                array[index] = newPair;
                size++;

                // If size is greater than threshold
                int maxSize = (int)(loadFactor*array.length);
                if (size >= maxSize)
                    increase();

                return prev;
            }

            if (pair.key.equals(newPair.key)) {
                prev = pair.value;
                pair.value = newPair.value;
                return prev;
            }

            // Probing until we get back to the starting index
            int start = getNextIndex(index);
            while (start != index) {
                pair = array[start];
                if (pair == null) {
                    array[start] = newPair;
                    size++;

                    // If size is greater than threshold
                    int maxSize = (int)(loadFactor*array.length);
                    if (size >= maxSize)
                        increase();

                    return prev;
                }

                if (pair.key.equals(newPair.key)) {
                    prev = pair.value;
                    pair.value = newPair.value;
                    return prev;
                }

                start = getNextIndex(start);
            }
            // We should never get here.
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            int index = indexOf(key);
            Pair<K, V> pair = array[index];

            // Check initial position
            if (pair == null)
                return null;
            if (pair.key.equals(key))
                return pair.value;

            // Probing until we get back to the starting index
            int start = getNextIndex(index);
            while (start != index) {
                pair = array[start];

                if (pair == null)
                    return null;
                if (pair.key.equals(key))
                    return pair.value;

                start = getNextIndex(start);
            }
            // If we get here, probing failed.
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(K key) {
            return (get(key)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(K key) {
            int index = indexOf(key);
            Pair<K, V> prev = null;

            // Check initial position
            Pair<K, V> pair = array[index];
            if (pair != null && pair.key.equals(key)) {
                prev = array[index];
                array[index] = null;
                size--;

                int loadFactored = (int)(size/loadFactor);
                int smallerSize = getSmallerSize(array.length);
                if (loadFactored < smallerSize && smallerSize > minimumSize)
                    reduce();

                return prev.value;
            }

            // Probing until we get back to the starting index
            int start = getNextIndex(index);
            while (start != index) {
                pair = array[start];
                if (pair != null && pair.key.equals(key)) {
                    prev = array[start];
                    array[start] = null;
                    size--;

                    int loadFactored = (int)(size/loadFactor);
                    int smallerSize = getSmallerSize(array.length);
                    if (loadFactored < smallerSize && smallerSize > minimumSize)
                        reduce();

                    return prev.value;
                }
                start = getNextIndex(start);
            }
            // If we get here, probing failed.
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            for (int i=0; i<array.length; i++)
                array[i] = null;
            size = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        private void initializeMap(int current) {
            int length = getLargerSize(current);
            array = new Pair[length];
            size = 0;
            hashingKey = length;
        }

        private void increase() {
            // Save old data
            Pair<K,V>[] temp = this.array;

            // Calculate new size and assign
            int length = getLargerSize(array.length);
            //System.out.println("increase from "+array.length+" to "+length);
            initializeMap(length);

            // Re-hash old data
            for (Pair<K,V> p : temp) {
                if (p != null)
                    this.put(p);
            }
        }

        private void reduce() {
            // Save old data
            Pair<K,V>[] temp = this.array;

            // Calculate new size and check minimum
            int length = getSmallerSize(array.length);
            //System.out.println("reduce from "+array.length+" to "+length);
            initializeMap(length);

            // Re-hash old data
            for (Pair<K,V> p : temp) {
                if (p != null)
                    this.put(p);
            }
        }

        /**
         * Doubles the input. e.g. 16->32
         */
        private static final int getLargerSize(int input) {
            return input<<1;
        }

        /**
         * Returns one fourth of the input. e.g. 16->8->4
         */
        private static final int getSmallerSize(int input) {
            return input>>1>>1;
        }

        /**
         * Returns the next index in the probing sequence, at this point it's linear
         */
        private int getNextIndex(int input) {
            int i = input+1;
            if (i >= array.length)
                i = 0;
            return i;
        }

        /**
         * The hashing function. Converts the key into an integer.
         * 
         * @param key
         *            to create a hash for.
         * @return Integer which represents the key.
         */
        private int indexOf(K key) {
            int k = Math.abs(key.hashCode()) % hashingKey;
            if (k >= array.length)
                k = k - ((k / array.length) * array.length);
            return k;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map<K,V> toMap() {
            return (new JavaCompatibleHashMap<K,V>(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean validate() {
            java.util.Set<K> keys = new java.util.HashSet<K>();
            for (Pair<K, V> pair : array) {
                if (pair == null)
                    continue;

                K k = pair.key;
                V v = pair.value;
                if (k==null || v==null) 
                    return false;
                if (keys.contains(k)) 
                    return false;
                keys.add(k);
            }
            return (keys.size()==size());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int key = 0; key < array.length; key++) {
                Pair<K, V> p = array[key];
                if (p == null)
                    continue;
                V value = p.value;
                if (value != null) 
                    builder.append(key).append("=").append(value).append(", ");
            }
            return builder.toString();
        }

        private static class JavaCompatibleHashMap<K,V> extends java.util.AbstractMap<K,V> {

            private ProbingHashMap<K,V> map = null;

            protected JavaCompatibleHashMap(ProbingHashMap<K,V> map) {
                this.map = map;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public V put(K key, V value) {
                return map.put(key, value);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public V remove(Object key) {
                return map.remove((K)key);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean containsKey(Object key) {
                return map.contains((K)key);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                map.clear();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int size() {
                return map.size();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
                java.util.Set<java.util.Map.Entry<K, V>> set = new java.util.HashSet<java.util.Map.Entry<K, V>>() {

                    private static final long serialVersionUID = 1L;

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                        return (new JavaCompatibleIteratorWrapper<K,V>(map,super.iterator()));
                    }
                };
                for (Pair<K, V> p : map.array) {
                    if (p==null)
                        continue;
                    java.util.Map.Entry<K, V> entry = new JavaCompatibleMapEntry<K, V>(p.key, p.value);
                    set.add(entry);
                }
                return set;
            }
        }
    }

    /**
     * Create a hash map with K as the hashing key.
     * 
     * @param type
     *            type of hashing to use.
     * @param size
     *            initialize size.
     */
    public HashMap(Type type, int size) {
        if (type == Type.CHAINING) {
            delegateMap = new ChainingHashMap<K,V>(size);
        } else if (type == Type.PROBING) {
            delegateMap = new ProbingHashMap<K,V>(size);
        }
    }

    /**
     * Create a hash map with the default hashing key.
     * 
     * @param type
     *            type of hashing to use.
     */
    public HashMap(Type type) {
        if (type == Type.CHAINING) {
            delegateMap = new ChainingHashMap<K,V>();
        } else if (type == Type.PROBING) {
            delegateMap = new ProbingHashMap<K,V>();
        }
    }

    private HashMap() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        return delegateMap.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        return delegateMap.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        return (get(key)!=null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        return delegateMap.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegateMap.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegateMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Map<K,V> toMap() {
        return delegateMap.toMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        return delegateMap.validate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return delegateMap.toString();
    }

    private static final class Pair<K, V> {

        private K key = null;
        private V value = null;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * (this.key.hashCode());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) 
                return false;
            if (!(obj instanceof Pair)) 
                return false;

            Pair<K, V> pair = (Pair<K, V>) obj;
            return key.equals(pair.key);
        }
    }

    private static class JavaCompatibleIteratorWrapper<K,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private HashMap<K,V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(HashMap<K,V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
            this.map = map;
            this.iter = iter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if (iter==null) 
                return false;
            return iter.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map.Entry<K, V> next() {
            if (iter==null) 
                return null;

            lastEntry = iter.next();
            return lastEntry;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (iter==null || lastEntry==null) 
                return;

            map.remove(lastEntry.getKey());
            iter.remove();
        }
    }

    private static class JavaCompatibleMapEntry<K,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = 3282082818647198608L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }
}
