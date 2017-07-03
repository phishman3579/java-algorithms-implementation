package com.jwetherell.algorithms.data_structures;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * A Fenwick tree or binary indexed tree is a data structure providing efficient methods 
 * for calculation and manipulation of the prefix sums of a table of values. Fenwick trees 
 * primarily solve the problem of balancing prefix sum calculation efficiency with element 
 * modification efficiency. 
 * <p>
 * This class is meant to be somewhat generic, all you'd have to do is extend
 * the Data abstract class to store your custom data. I've included a range sum
 * implementations.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Fenwick_tree">Fenwick Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class FenwickTree<D extends FenwickTree.Data> {

    private Object[] array;

    public FenwickTree(List<D> data) {
        // Find the largest index
        int n = 0;
        for (Data d : data)
            if (d.index > n)
                n = d.index;
        n = next(n+1);
        array = new Object[n];

        // Add the data
        for (D d : data)
            update(d.index, d);
    }

    /**
     * Stabbing query
     * 
     * @param index
     *            index for query
     * @return data at index.
     */
    public D query(int index) {
        return query(index, index);
    }

    /**
     * Range query
     * 
     * @param start
     *            start of range (inclusive)
     * @param end
     *            end of range (inclusive)
     * @return data for range.
     */
    public D query(int start, int end) {
        final D e = lookup(end);
        final D s = lookup(start-1);
        final D c = (D) e.copy();
        if (s != null)
            c.separate(s);
        return c;
    }

    private D lookup(int index) {
        index++; // tree index is 1 based
        index = Math.min(array.length - 1, index);
        if (index <= 0)
            return null;

        D res = null;
        while (index > 0) {
            if (res == null) {
                final D data = (D) array[index];
                if (data != null)
                    res = (D) data.copy();
            } else{ 
                res.combined((D) array[index]);
            }
            index = prev(index);
        }
        return res;
    }

    private void update(int index, D value) {
        index++; // tree index is 1 based
        while (index < array.length) {
            D data = (D) array[index];
            if (data == null) {
                data = (D) value.copy();
                data.index = index;
                array[index] = data;
            } else {
                data.combined(value);
            }
            index = next(index);
        }
    }

    private static final int prev(int x) {
        return x & (x - 1);
    }

    private static final int next(int x) {
        return 2 * x - prev(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(FenwickTreePrinter.getString(this));
        return builder.toString();
    }

    protected static class FenwickTreePrinter {

        public static <D extends FenwickTree.Data> String getString(FenwickTree<D> tree) {
            if (tree.array.length == 0)
                return "Tree has no nodes.";

            final D first = (D) tree.array[1];
            if (first == null)
                return "Tree has no nodes.";

            final StringBuilder builder = new StringBuilder();
            builder.append("└── dummy \n");
            builder.append(getString(tree, 0, tree.array.length, "", true));
            return builder.toString();
        }

        private static <D extends FenwickTree.Data> String getString(FenwickTree<D> tree, int start, int end, String prefix, boolean isTail) {
            if (end > tree.array.length || (end - start == 0))
                return "";

            final StringBuilder builder = new StringBuilder();

            final D value = (D) tree.array[start];
            if (value != null)
                builder.append(prefix + (isTail ? "└── " : "├── ") + value + "\n");

            int next = start + 1;
            final List<Integer> children = new ArrayList<Integer>(2);
            while (next < end) {
                children.add(next);
                next = next(next);
            }
            for (int i = 0; i < children.size() - 1; i++)
                builder.append(getString(tree, children.get(i),                 children.get(i+1), prefix + (isTail ? "    " : "│   "), false));
            if (children.size() >= 1)
                builder.append(getString(tree, children.get(children.size()-1), end,               prefix + (isTail ? "    " : "│   "), true));

            return builder.toString();
        }
    }

    public abstract static class Data implements Comparable<Data> {

        protected int index = Integer.MIN_VALUE;

        /**
         * Constructor for data at index.
         * 
         * @param index
         *            of data.
         */
        protected Data(int index) {
            this.index = index;
        }

        /**
         * Clear the indices.
         */
        public void clear() {
            index = Integer.MIN_VALUE;
        }

        /**
         * Combined this data with the Data parameter.
         * 
         * @param data
         *            to combined with.
         * @return Data which represents the combination.
         */
        public abstract Data combined(Data data);

        /**
         * Separate this data with the Data parameter.
         * 
         * @param data
         *            to separate with.
         * @return Data which represents the combination.
         */
        public abstract Data separate(Data data);

        /**
         * Deep copy of data.
         * 
         * @return deep copy.
         */
        public abstract Data copy();

        /**
         * Query inside this data object.
         * 
         * @param startOfRange
         *            of range to query for.
         * @param endOfRange
         *            of range to query for.
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public abstract Data query(long startOfRange, long endOfRange);

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("[").append(index).append("]");
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Data d) {
            if (this.index < d.index)
                return -1;
            if (d.index < this.index)
                return 1;
            return 0;
        }

        /**
         * Data structure representing sum of the range.
         */
        public static final class RangeSumData<N extends Number> extends Data {

            public N sum = null;

            public RangeSumData(int index, N number) {
                super(index);

                this.sum = number;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                super.clear();

                sum = null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data combined(Data data) {
                RangeSumData<N> q = null;
                if (data instanceof RangeSumData) {
                    q = (RangeSumData<N>) data;
                    this.combined(q);
                }
                return this;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data separate(Data data) {
                RangeSumData<N> q = null;
                if (data instanceof RangeSumData) {
                    q = (RangeSumData<N>) data;
                    this.separate(q);
                }
                return this;
            }

            /**
             * Combined range sum data.
             * 
             * @param data
             *            resulted from combination.
             */
            private void combined(RangeSumData<N> data) {
                if (this.sum == null && data.sum == null)
                    return;
                else if (this.sum != null && data.sum == null)
                    return;
                else if (this.sum == null && data.sum != null)
                    this.sum = data.sum;
                else {
                    /* TODO: This is ugly and how to handle number overflow? */
                    if (this.sum instanceof BigDecimal || data.sum instanceof BigDecimal) {
                        BigDecimal result = ((BigDecimal)this.sum).add((BigDecimal)data.sum);
                        this.sum = (N)result;
                    } else if (this.sum instanceof BigInteger || data.sum instanceof BigInteger) {
                        BigInteger result = ((BigInteger)this.sum).add((BigInteger)data.sum);
                        this.sum = (N)result;
                    } else if (this.sum instanceof Long || data.sum instanceof Long) {
                        Long result = (this.sum.longValue() + data.sum.longValue());
                        this.sum = (N)result;
                    } else if (this.sum instanceof Double || data.sum instanceof Double) {
                        Double result = (this.sum.doubleValue() + data.sum.doubleValue());
                        this.sum = (N)result;
                    } else if (this.sum instanceof Float || data.sum instanceof Float) {
                        Float result = (this.sum.floatValue() + data.sum.floatValue());
                        this.sum = (N)result;
                    } else {
                        // Integer
                        Integer result = (this.sum.intValue() + data.sum.intValue());
                        this.sum = (N)result;
                    }
                }
            }

            /**
             * Separate range sum data.
             * 
             * @param data
             *            resulted from combination.
             */
            private void separate(RangeSumData<N> data) {
                if (this.sum == null && data.sum == null)
                    return;
                else if (this.sum != null && data.sum == null)
                    return;
                else if (this.sum == null && data.sum != null)
                    this.sum = data.sum;
                else {
                    /* TODO: This is ugly and how to handle number overflow? */
                    if (this.sum instanceof BigDecimal || data.sum instanceof BigDecimal) {
                        BigDecimal result = ((BigDecimal)this.sum).subtract((BigDecimal)data.sum);
                        this.sum = (N)result;
                    } else if (this.sum instanceof BigInteger || data.sum instanceof BigInteger) {
                        BigInteger result = ((BigInteger)this.sum).subtract((BigInteger)data.sum);
                        this.sum = (N)result;
                    } else if (this.sum instanceof Long || data.sum instanceof Long) {
                        Long result = (this.sum.longValue() - data.sum.longValue());
                        this.sum = (N)result;
                    } else if (this.sum instanceof Double || data.sum instanceof Double) {
                        Double result = (this.sum.doubleValue() - data.sum.doubleValue());
                        this.sum = (N)result;
                    } else if (this.sum instanceof Float || data.sum instanceof Float) {
                        Float result = (this.sum.floatValue() - data.sum.floatValue());
                        this.sum = (N)result;
                    } else {
                        // Integer
                        Integer result = (this.sum.intValue() - data.sum.intValue());
                        this.sum = (N)result;
                    }
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data copy() {
                return new RangeSumData<N>(index, sum);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data query(long startOfQuery, long endOfQuery) {
                if (endOfQuery < this.index || startOfQuery > this.index)
                    return null;

                return copy();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return 31 * (int)(this.index + this.sum.hashCode());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof RangeSumData))
                    return false;

                RangeSumData<N> data = (RangeSumData<N>) obj;
                if (this.index == data.index && this.sum.equals(data.sum))
                    return true;

                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(" ");
                builder.append("sum=").append(sum);
                return builder.toString();
            }
        }
    }
}
