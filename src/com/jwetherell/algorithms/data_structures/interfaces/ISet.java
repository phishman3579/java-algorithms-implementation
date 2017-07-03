package com.jwetherell.algorithms.data_structures.interfaces;

/**
 * In computer science, a set is an abstract data structure that can store certain values, without 
 * any particular order, and no repeated values. It is a computer implementation of the mathematical 
 * concept of a finite set. Unlike most other collection types, rather than retrieving a specific 
 * element from a set, one typically tests a value for membership in a set.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Set_(abstract_data_type)">Set (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public interface ISet<T> {

    /**
     * Add value to set.
     * 
     * @param value to add.
     * @return True if added.
     */
    public boolean add(T value);

    /**
     * Remove value from set.
     * 
     * @param value to remove.
     * @return True if removed.
     */
    public boolean remove(T value);

    /**
     * Clear the entire set.
     */
    public void clear();

    /**
     * Does the set contain value.
     * 
     * @param value to search set for.
     * @return True if set contains value.
     */
    public boolean contains(T value);

    /**
     * Size of the set.
     * 
     * @return size of the set.
     */
    public int size();

    /**
     * Validate the set according to the invariants.
     * 
     * @return True if the set is valid.
     */
    public boolean validate();

    /**
     * Get this Set as a Java compatible Set
     * 
     * @return Java compatible Set
     */
    public java.util.Set<T> toSet();

    /**
     * Get this Set as a Java compatible Collection
     * 
     * @return Java compatible Collection
     */
    public java.util.Collection<T> toCollection();

}
