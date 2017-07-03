package com.jwetherell.algorithms.data_structures.interfaces;

/**
 * A tree can be defined recursively (locally) as a collection of nodes (starting at a root node), 
 * where each node is a data structure consisting of a value, together with a list of nodes (the "children"), 
 * with the constraints that no node is duplicated. A tree can be defined abstractly as a whole (globally) 
 * as an ordered tree, with a value assigned to each node.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Tree_(data_structure)">Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public interface ITree<T> {

    /**
     * Add value to the tree. Tree can contain multiple equal values.
     * 
     * @param value to add to the tree.
     * @return True if successfully added to tree.
     */
    public boolean add(T value);

    /**
     * Remove first occurrence of value in the tree.
     * 
     * @param value to remove from the tree.
     * @return T value removed from tree.
     */
    public T remove(T value);

    /**
     * Clear the entire stack.
     */
    public void clear();

    /**
     * Does the tree contain the value.
     * 
     * @param value to locate in the tree.
     * @return True if tree contains value.
     */
    public boolean contains(T value);

    /**
     * Get number of nodes in the tree.
     * 
     * @return Number of nodes in the tree.
     */
    public int size();

    /**
     * Validate the tree according to the invariants.
     * 
     * @return True if the tree is valid.
     */
    public boolean validate();

    /**
     * Get Tree as a Java compatible Collection
     * 
     * @return Java compatible Collection
     */
    public java.util.Collection<T> toCollection();

}
