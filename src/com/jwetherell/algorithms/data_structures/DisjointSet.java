package com.jwetherell.algorithms.data_structures;

/**
 * In computer science, a disjoint-set data structure, also called a union–find data structure or merge–find set, is a data structure that keeps track of a set of 
 * elements partitioned into a number of disjoint (non-overlapping) subsets. 
 * <p>
 * It supports two useful operations:<br>
 *     Find: Determine which subset a particular element is in. Find typically returns an item from this set that serves as its "representative"; by comparing the 
 *           result of two Find operations, one can determine whether two elements are in the same subset.<br>
 *     Union: Join two subsets into a single subset.<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure">Disjoint Set (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class DisjointSet<T extends Object> {

    private DisjointSet() { }

    /**
     * Creates a set of one element.
     * 
     * @param v Value to use when creating the set
     * @return Item representing the value
     */
    public static final <T extends Object> Item<T> makeSet(T v) {
        final Item<T> item = new Item<T>(null,v);
        item.parent = item;
        return item;
    }

    /**
     * Determine which subset a particular element is in. Find returns an item from this set that serves as its "representative"; by comparing the result 
     * of two Find operations, one can determine whether two elements are in the same subset. This method uses path compression which is a way of flattening 
     * the structure of the tree whenever Find is used on it.
     * 
     * @param x Find the "representative" of this Item
     * @return "Representative" of this Item
     */
    public static final <T extends Object> Item<T> find(Item<T> x) {
        if (x == null)
            return null;

        if ((x.parent!=null) && !(x.parent.equals(x)))
            return x.parent = find(x.parent);
        return x.parent;
    }

    /**
     * Join two subsets into a single subset. This method uses 'union by rank' which always attaches the smaller tree to the root of the larger tree. 
     * 
     * @param x Subset 1 to join
     * @param y Subset 2 to join
     * @return Resulting Set of joining Subset 1 and Subset 2 
     */
    public static final <T extends Object> Item<T> union(Item<T> x, Item<T> y) {
        final Item<T> xRoot = find(x);
        final Item<T> yRoot = find(y);
        if (xRoot==null && yRoot==null)
            return null;
        if (xRoot==null && yRoot!=null)
            return yRoot;
        if (yRoot==null && xRoot!=null)
            return xRoot;

        // x and y are in the same set
        if (xRoot.equals(yRoot))
            return xRoot;

        if (xRoot.rank < yRoot.rank) {
            xRoot.parent = yRoot;
            return yRoot;
        } else if (xRoot.rank > yRoot.rank) {
            yRoot.parent = xRoot;
            return xRoot;
        }
        // else
        yRoot.parent = xRoot;
        xRoot.rank = xRoot.rank + 1;
        return xRoot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Nothing here to see, yet.";
    }

    public static final class Item<T> {

        private Item<T> parent;
        private T value;
        /** Rank is not the actual depth of the tree rather it is an upper bound. As such, on a find operation, the rank is allowed to get out of sync with the depth. **/
        private long rank;

        public Item(Item<T> parent, T value) {
            this.parent = parent;
            this.value = value;
            this.rank = 0;
        }

        public T getValue() {
            return value;
        }
        public long getRank() {
            return rank;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Item))
                return false;

            final Item<T> i = (Item<T>) o;
            if ((i.parent!=null && parent!=null) && !(i.parent.value.equals(parent.value)))
                return false;
            if ((i.value!=null && value!=null) && !(i.value.equals(value)))
                return false;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "parent="+(parent!=null?parent.value:null)+" "+
                   (rank>0?"rank="+rank +" " : "") +
                   "value="+(value!=null?value:null);
        }
    }
}
