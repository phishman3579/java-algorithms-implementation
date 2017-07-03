package com.jwetherell.algorithms.data_structures.interfaces;

/**
 * A list or sequence is an abstract data type that implements an ordered
 * collection of values, where the same value may occur more than once.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/List_(computing)">List (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public interface IList<T> {

    /**
     * Add value to list.
     * 
     * @param value to add.
     * @return True if added.
     */
    public boolean add(T value);

    /**
     * Remove value from list.
     * 
     * @param value to remove.
     * @return True if removed.
     */
    public boolean remove(T value);

    /**
     * Clear the entire list.
     */
    public void clear();

    /**
     * Does the list contain value.
     * 
     * @param value to search list for.
     * @return True if list contains value.
     */
    public boolean contains(T value);

    /**
     * Size of the list.
     * 
     * @return size of the list.
     */
    public int size();

    /**
     * Validate the list according to the invariants.
     * 
     * @return True if the list is valid.
     */
    public boolean validate();

    /**
     * Get this List as a Java compatible List
     * 
     * @return Java compatible List
     */
    public java.util.List<T> toList();

    /**
     * Get this List as a Java compatible Collection
     * 
     * @return Java compatible Collection
     */
    public java.util.Collection<T> toCollection();

}
