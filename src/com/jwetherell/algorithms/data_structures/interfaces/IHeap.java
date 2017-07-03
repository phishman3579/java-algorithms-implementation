package com.jwetherell.algorithms.data_structures.interfaces;

/**
 * In computer science, a heap is a specialized tree-based data structure that 
 * satisfies the heap property: If A is a parent node of B then key(A) is ordered 
 * with respect to key(B) with the same ordering applying across the heap. Either 
 * the keys of parent nodes are always greater than or equal to those of the children 
 * and the highest key is in the root node (this kind of heap is called max heap) or 
 * the keys of parent nodes are less than or equal to those of the children (min heap).
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Heap">Heap (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public interface IHeap<T> {

    /**
     * Add value to the heap.
     * 
     * @param value to add to the heap.
     * @return True if added to the heap.
     */
    public boolean add(T value);

    /**
     * Get the value of the head node from the heap.
     * 
     * @return value of the head node.
     */
    public T getHeadValue();

    /**
     * Remove the head node from the heap.
     * 
     * @return value of the head node.
     */
    public T removeHead();

    /**
     * Remove the value from the heap.
     * 
     * @param value to remove from heap.
     * @return True if value was removed form the heap;
     */
    public T remove(T value);

    /**
     * Clear the entire heap.
     */
    public void clear();

    /**
     * Does the value exist in the heap. Warning this is a O(n) operation.
     * 
     * @param value to locate in the heap.
     * @return True if the value is in heap.
     */
    public boolean contains(T value);

    /**
     * Get size of the heap.
     * 
     * @return size of the heap.
     */
    public int size();

    /**
     * Validate the heap according to the invariants.
     * 
     * @return True if the heap is valid.
     */
    public boolean validate();

    /**
     * Get this Heap as a Java compatible Collection
     * 
     * @return Java compatible Collection
     */
    public java.util.Collection<T> toCollection();

}
