package com.jwetherell.algorithms.data_structures.interfaces;

/**
 * In computer science, an associative array, map, or dictionary is an abstract 
 * data type composed of a collection of (key, value) pairs, such that each possible 
 * key appears at most once in the collection.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Associative_array">Associative Array (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public interface IMap<K,V> {

    /**
     * Put key->value pair in the map.
     * 
     * @param key to be inserted.
     * @param value to be inserted.
     * @return V previous value or null if none.
     */
    public V put(K key, V value);

    /**
     * Get value for key.
     * 
     * @param key to get value for.
     * @return value mapped to key.
     */
    public V get(K key);

    /**
     * Remove key and value from map.
     * 
     * @param key to remove from the map.
     * @return True if removed or False if not found.
     */
    public V remove(K key);

    /**
     * Clear the entire map.
     */
    public void clear();

    /**
     * Does the map contain the key.
     * 
     * @param key to locate in the map.
     * @return True if key is in the map.
     */
    public boolean contains(K key);

    /**
     * Number of key/value pairs in the hash map.
     * 
     * @return number of key/value pairs.
     */
    public int size();

    /**
     * Validate the map according to the invariants.
     * 
     * @return True if the map is valid.
     */
    public boolean validate();

    /**
     *  Wraps this map in a Java compatible Map
     * 
     * @return Java compatible Map
     */
    public java.util.Map<K,V> toMap();

}
