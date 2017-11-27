package com.jwetherell.algorithms.data_structures;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Segment tree using objects and pointers. A segment tree is a tree data
 * structure for storing intervals, or segments. It allows querying which of the
 * stored segments contain a given point. It is, in principle, a static
 * structure; that is, its content cannot be modified once the structure is
 * built.
 * <p>
 * This class is meant to be somewhat generic, all you'd have to do is extend
 * the Data abstract class to store your custom data. I've also included a range
 * minimum, range maximum, range sum, and interval stabbing implementations.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Segment_tree">Segment Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public abstract class SegmentTree<D extends SegmentTree.Data> {

    protected Segment<D> root = null;

    /**
     * Stabbing query
     * 
     * @param index
     *            index to query
     * @return data at index.
     */
    public abstract D query(long index);

    /**
     * Range query
     * 
     * @param start
     *            start of range (inclusive)
     * @param end
     *            end of range to (inclusive)
     * @return data for range.
     */
    public abstract D query(long start, long end);

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(SegmentTreePrinter.getString(this));
        return builder.toString();
    }

    public abstract static class Data implements Comparable<Data> {

        protected long start = Long.MIN_VALUE;
        protected long end = Long.MAX_VALUE;

        /**
         * Constructor for data at index
         * 
         * @param index
         *            of data.
         */
        public Data(long index) {
            this.start = index;
            this.end = index;
        }

        /**
         * Constructor for data at range (inclusive)
         * 
         * @param start
         *            start of range for data.
         * @param end
         *            end of range for data.
         */
        public Data(long start, long end) {
            this.start = start;
            this.end = end;
        }

        /**
         * Clear the indices
         */
        public void clear() {
            start = Long.MIN_VALUE;
            end = Long.MAX_VALUE;
        }

        /**
         * Combined this data with the Data parameter
         * 
         * @param data
         *            Data to combined
         * @return Data which represents the combination.
         */
        public abstract Data combined(Data data);

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
         *            start of range (inclusive)
         * @param endOfRange
         *            end of range (inclusive)
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public abstract Data query(long startOfRange, long endOfRange);

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(start).append("->").append(end);
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Data d) {
            if (this.end < d.end)
                return -1;
            if (d.end < this.end)
                return 1;
            return 0;
        }

        /**
         * Data structure representing points in the x,y space and their
         * location in the quadrants.
         */
        public static final class QuadrantData extends Data {

            public long quad0 = 0;
            public long quad1 = 0;
            public long quad2 = 0;
            public long quad3 = 0;

            public QuadrantData(long start, long end) {
                super(start, end);
            }

            public QuadrantData(long index, long quad1, long quad2, long quad3, long quad4) {
                super(index);

                this.quad0 = quad1;
                this.quad1 = quad2;
                this.quad2 = quad3;
                this.quad3 = quad4;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                super.clear();

                quad0 = 0;
                quad1 = 0;
                quad2 = 0;
                quad3 = 0;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public QuadrantData copy() {
                final QuadrantData copy = new QuadrantData(start, end);
                copy.quad0 = this.quad0;
                copy.quad1 = this.quad1;
                copy.quad2 = this.quad2;
                copy.quad3 = this.quad3;
                return copy;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data query(long startOfQuery, long endOfQuery) {
                if (endOfQuery < this.start || startOfQuery > this.end)
                    return null;
                return copy();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data combined(Data data) {
                QuadrantData q = null;
                if (data instanceof QuadrantData) {
                    q = (QuadrantData) data;
                    this.combined(q);
                }
                return this;
            }

            private void combined(QuadrantData data) {
                this.quad0 += data.quad0;
                this.quad1 += data.quad1;
                this.quad2 += data.quad2;
                this.quad3 += data.quad3;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return 31 * (int)(this.start + this.end + this.quad0 + this.quad1 + this.quad2 + this.quad3);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof QuadrantData))
                    return false;
                QuadrantData data = (QuadrantData) obj;
                if (this.start == data.start && this.end == data.end && this.quad0 == data.quad0
                    && this.quad1 == data.quad1 && this.quad2 == data.quad2 && this.quad3 == data.quad3) 
                {
                    return true;
                }
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                final StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(" ");
                builder.append(quad0).append(",");
                builder.append(quad1).append(",");
                builder.append(quad2).append(",");
                builder.append(quad3);
                return builder.toString();
            }
        }

        /**
         * Data structure representing maximum in the range.
         */
        public static final class RangeMaximumData<N extends Number> extends Data {

            public N maximum = null;

            public RangeMaximumData(long start, long end) {
                super(start, end);
            }

            public RangeMaximumData(long index, N number) {
                super(index);

                this.maximum = number;
            }

            public RangeMaximumData(long start, long end, N number) {
                super(start, end);

                this.maximum = number;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                super.clear();

                maximum = null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data copy() {
                return new RangeMaximumData<N>(start, end, maximum);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data query(long startOfQuery, long endOfQuery) {
                if (endOfQuery < this.start || startOfQuery > this.end)
                    return null;

                return copy();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data combined(Data data) {
                RangeMaximumData<N> q = null;
                if (data instanceof RangeMaximumData) {
                    q = (RangeMaximumData<N>) data;
                    this.combined(q);
                }
                return this;
            }

            private void combined(RangeMaximumData<N> data) {
                if (this.maximum == null && data.maximum == null)
                    return;
                else if (this.maximum != null && data.maximum == null)
                    return;
                else if (this.maximum == null && data.maximum != null)
                    this.maximum = data.maximum;
                else {
                    /* TODO: This is ugly */
                    if (this.maximum instanceof BigDecimal || data.maximum instanceof BigDecimal) {
                        if (((BigDecimal)data.maximum).compareTo(((BigDecimal)this.maximum))==1)
                            this.maximum = data.maximum;
                    } else if (this.maximum instanceof BigInteger || data.maximum instanceof BigInteger) {
                        if (((BigInteger)data.maximum).compareTo(((BigInteger)this.maximum))==1)
                            this.maximum = data.maximum;
                    } else if (this.maximum instanceof Long || data.maximum instanceof Long) {
                        if (((Long)data.maximum).compareTo(((Long)this.maximum))==1)
                            this.maximum = data.maximum;
                    } else if (this.maximum instanceof Double || data.maximum instanceof Double) {
                        if (((Double)data.maximum).compareTo(((Double)this.maximum))==1)
                            this.maximum = data.maximum;
                    } else if (this.maximum instanceof Float || data.maximum instanceof Float) {
                        if (((Float)data.maximum).compareTo(((Float)this.maximum))==1)
                            this.maximum = data.maximum;
                    } else {
                        // Integer
                        if (((Integer)data.maximum).compareTo(((Integer)this.maximum))==1)
                            this.maximum = data.maximum;
                    }
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return 31 * (int)(this.start + this.end + this.maximum.hashCode());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof RangeMaximumData))
                    return false;

                final RangeMaximumData<N> data = (RangeMaximumData<N>) obj;
                if (this.start == data.start && this.end == data.end && this.maximum.equals(data.maximum))
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
                builder.append("maximum=").append(maximum);
                return builder.toString();
            }
        }

        /**
         * Data structure representing minimum in the range.
         */
        public static final class RangeMinimumData<N extends Number> extends Data {

            public N minimum = null;

            public RangeMinimumData(long start, long end) {
                super(start, end);
            }

            public RangeMinimumData(long index, N number) {
                super(index);

                this.minimum = number;
            }

            public RangeMinimumData(long start, long end, N number) {
                super(start, end);

                this.minimum = number;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                super.clear();

                minimum = null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data copy() {
                return new RangeMinimumData<N>(start, end, minimum);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data query(long startOfQuery, long endOfQuery) {
                if (endOfQuery < this.start || startOfQuery > this.end)
                    return null;

                return copy();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data combined(Data data) {
                RangeMinimumData<N> q = null;
                if (data instanceof RangeMinimumData) {
                    q = (RangeMinimumData<N>) data;
                    this.combined(q);
                }
                return this;
            }

            private void combined(RangeMinimumData<N> data) {
                if (this.minimum == null && data.minimum == null)
                    return;
                else if (this.minimum != null && data.minimum == null)
                    return;
                else if (this.minimum == null && data.minimum != null)
                    this.minimum = data.minimum;
                else {
                    /* TODO: This is ugly */
                    if (this.minimum instanceof BigDecimal || data.minimum instanceof BigDecimal) {
                        if (((BigDecimal)data.minimum).compareTo(((BigDecimal)this.minimum))==-1)
                            this.minimum = data.minimum;
                    } else if (this.minimum instanceof BigInteger || data.minimum instanceof BigInteger) {
                        if (((BigInteger)data.minimum).compareTo(((BigInteger)this.minimum))==-1)
                            this.minimum = data.minimum;
                    } else if (this.minimum instanceof Long || data.minimum instanceof Long) {
                        if (((Long)data.minimum).compareTo(((Long)this.minimum))==-1)
                            this.minimum = data.minimum;
                    } else if (this.minimum instanceof Double || data.minimum instanceof Double) {
                        if (((Double)data.minimum).compareTo(((Double)this.minimum))==-1)
                            this.minimum = data.minimum;
                    } else if (this.minimum instanceof Float || data.minimum instanceof Float) {
                        if (((Float)data.minimum).compareTo(((Float)this.minimum))==-1)
                            this.minimum = data.minimum;
                    } else {
                        // Integer
                        if (((Integer)data.minimum).compareTo(((Integer)this.minimum))==-1)
                            this.minimum = data.minimum;
                    }
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return 31 * (int)(this.start + this.end + this.minimum.hashCode());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof RangeMinimumData))
                    return false;
 
                final RangeMinimumData<N> data = (RangeMinimumData<N>) obj;
                if (this.start == data.start && this.end == data.end && this.minimum.equals(data.minimum))
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
                builder.append("minimum=").append(minimum);
                return builder.toString();
            }
        }

        /**
         * Data structure representing sum of the range.
         */
        public static final class RangeSumData<N extends Number> extends Data {

            public N sum = null;

            public RangeSumData(long start, long end) {
                super(start, end);
            }

            public RangeSumData(long index, N number) {
                super(index);

                this.sum = number;
            }

            public RangeSumData(long start, long end, N number) {
                super(start, end);

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
            public Data copy() {
                return new RangeSumData<N>(start, end, sum);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data query(long startOfQuery, long endOfQuery) {
                if (endOfQuery < this.start || startOfQuery > this.end)
                    return null;

                return copy();
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
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return 31 * (int)(this.start + this.end + this.sum.hashCode());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof RangeSumData))
                    return false;

                final RangeSumData<N> data = (RangeSumData<N>) obj;
                if (this.start == data.start && this.end == data.end && this.sum.equals(data.sum))
                    return true;

                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                final StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(" ");
                builder.append("sum=").append(sum);
                return builder.toString();
            }
        }

        /**
         * Data structure representing an interval.
         */
        public static final class IntervalData<O extends Object> extends Data {

            private Set<O> set = new TreeSet<O>(); // Sorted

            /**
             * Interval data using O as it's unique identifier
             * 
             * @param object
             *            Object which defines the interval data
             */
            public IntervalData(long index, O object) {
                super(index);

                this.set.add(object);
            }

            /**
             * Interval data using O as it's unique identifier
             * 
             * @param object
             *            Object which defines the interval data
             */
            public IntervalData(long start, long end, O object) {
                super(start, end);

                this.set.add(object);
            }

            /**
             * Interval data list which should all be unique
             * 
             * @param set
             *            of interval data objects
             */
            public IntervalData(long start, long end, Set<O> set) {
                super(start, end);

                this.set = set;
            }

            /**
             * Get the data set in this interval
             * 
             * @return Unmodifiable collection of data objects
             */
            public Collection<O> getData() {
                return Collections.unmodifiableCollection(this.set);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void clear() {
                super.clear();

                this.set.clear();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data copy() {
                final Set<O> listCopy = new TreeSet<O>();
                listCopy.addAll(set);
                return new IntervalData<O>(start, end, listCopy);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data query(long startOfQuery, long endOfQuery) {
                if (endOfQuery < this.start || startOfQuery > this.end)
                    return null;

                return copy();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Data combined(Data data) {
                IntervalData<O> q = null;
                if (data instanceof IntervalData) {
                    q = (IntervalData<O>) data;
                    this.combined(q);
                }
                return this;
            }

            /**
             * Combined for interval specific data.
             * 
             * @param data
             *            resulted from combination.
             */
            private void combined(IntervalData<O> data) {
                if (data.start < this.start)
                    this.start = data.start;
                if (data.end > this.end)
                    this.end = data.end;
                this.set.addAll(data.set);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return 31 * (int)(this.start + this.end + this.set.size());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof IntervalData))
                    return false;

                final IntervalData<O> data = (IntervalData<O>) obj;
                if (this.start == data.start && this.end == data.end) {
                    if (this.set.size() != data.set.size())
                        return false;
                    for (O o : set) {
                        if (!data.set.contains(o))
                            return false;
                    }
                    return true;
                }
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                final StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(" ");
                builder.append("set=").append(set);
                return builder.toString();
            }
        }
    }

    /**
     * Data structure representing a segment.
     */
    protected abstract static class Segment<D extends Data> implements Comparable<Segment<D>> {

        protected Segment<D>[] segments = null;
        protected int length = 0;
        protected int half = 0;
        protected long start = 0;
        protected long end = 0;
        protected D data = null;
        protected int minLength = 0;

        public Segment(int minLength) {
            this.minLength = minLength;
        }

        /**
         * Query for data in range.
         * 
         * @param startOfQuery
         *            of the range to query for.
         * @param endOfQuery
         *            of range to query for.
         * @return Data in the range.
         */
        public abstract D query(long startOfQuery, long endOfQuery);

        protected boolean hasChildren() {
            return (segments != null);
        }

        protected Segment<D> getLeftChild() {
            return segments[0];
        }

        protected Segment<D> getRightChild() {
            return segments[1];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(start).append("->");
            builder.append(end).append(" ");
            builder.append("Length=").append(length).append(" ");
            builder.append("Data={").append(data).append("}");
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Segment<D> p) {
            if (this.end < p.end)
                return -1;
            if (p.end < this.end)
                return 1;
            return 0;
        }
    }

    protected static class SegmentTreePrinter {

        public static <D extends SegmentTree.Data> String getString(SegmentTree<D> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <D extends SegmentTree.Data> String getString(Segment<D> segment, String prefix, boolean isTail) {
            final StringBuilder builder = new StringBuilder();
            builder.append(prefix + (isTail ? "└── " : "├── ") + segment.toString() + "\n");

            final List<Segment<D>> children = new ArrayList<Segment<D>>();
            if (segment.segments != null) {
                for (Segment<D> c : segment.segments)
                    children.add(c);
            }

            for (int i = 0; i < children.size() - 1; i++)
                builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));

            if (children.size() > 1)
                builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));

            return builder.toString();
        }
    }

    /**
     * Flat segment tree is a variant of segment tree that is designed to store
     * a collection of non-overlapping segments. This structure is efficient
     * when you need to store values associated with 1 dimensional segments that
     * never overlap with each other. The end points of stored segments are
     * inclusive, that is, when a segment spans from 2 to 6, an arbitrary point
     * x within that segment can take a value of 2 <= x <= 6.
     */
    public static final class FlatSegmentTree<D extends Data> extends SegmentTree<D> {

        public FlatSegmentTree(List<D> data) {
            this(data, 1);
        }

        public FlatSegmentTree(List<D> data, int minLength) {
            if (data.size() <= 0)
                throw new InvalidParameterException("Segments list is empty.");

            Collections.sort(data); // Make sure they are sorted

            // Make sure they don't overlap
            if (data.size() >= 2) {
                for (int i = 0; i < (data.size() - 2); i++) {
                    Data s1 = data.get(i);
                    Data s2 = data.get(i + 1);
                    if (s1.end > s2.start)
                        throw new InvalidParameterException("Segments are overlapping.");
                }
            }

            // Check for gaps
            final List<NonOverlappingSegment<D>> segments = new ArrayList<NonOverlappingSegment<D>>();
            for (int i = 0; i < data.size(); i++) {
                if (i < data.size() - 1) {
                    final Data d1 = data.get(i);
                    final NonOverlappingSegment<D> s1 = new NonOverlappingSegment<D>(minLength, d1.start, d1.end, (D) d1);
                    segments.add(s1);
                    final Data d2 = data.get(i + 1);
                    if (d2.start - d1.end > 1) {
                        final Data d3 = d1.copy();
                        d3.clear();
                        d3.start = d1.end + 1;
                        d3.end = d2.start - 1;
                        final NonOverlappingSegment<D> s3 = new NonOverlappingSegment<D>(minLength, d3.start, d3.end, (D) d3);
                        segments.add(s3);
                    }
                } else {
                    final Data d1 = data.get(i);
                    final NonOverlappingSegment<D> s1 = new NonOverlappingSegment<D>(minLength, d1.start, d1.end, (D) d1);
                    segments.add(s1);
                }
            }

            final long start = segments.get(0).start;
            final long end = segments.get(segments.size() - 1).end;
            final int length = (int) (end - start) + 1;
            root = NonOverlappingSegment.createFromList(minLength, segments, start, length);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public D query(long index) {
            return this.query(index, index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public D query(long startOfQuery, long endOfQuery) {
            if (root == null)
                return null;

            long s = startOfQuery;
            long e = endOfQuery;
            if (s < root.start)
                s = root.start;
            if (e > root.end)
                e = root.end;

            return root.query(s, e);
        }

        /**
         * Data structure representing a non-overlapping segment.
         */
        protected static final class NonOverlappingSegment<D extends Data> extends Segment<D> {

            private Set<Segment<D>> set = new TreeSet<Segment<D>>();

            public NonOverlappingSegment(int minLength) {
                super(minLength);
            }

            public NonOverlappingSegment(int minLength, D data) {
                this(minLength, data.start, data.end, data);
            }

            public NonOverlappingSegment(int minLength, long start, long end, D data) {
                super(minLength);

                this.start = start;
                this.end = end;
                this.length = ((int) (end - start)) + 1;
                if (data == null)
                    return;
                this.data = ((D) data.copy());
            }

            protected static <D extends Data> Segment<D> createFromList(int minLength, List<NonOverlappingSegment<D>> segments, long start, int length) {
                final NonOverlappingSegment<D> segment = new NonOverlappingSegment<D>(minLength);
                segment.start = start;
                segment.end = start + (length - 1);
                segment.length = length;

                for (Segment<D> s : segments) {
                    if (segment.data == null)
                        segment.data = ((D) s.data.copy());
                    else
                        segment.data.combined(s.data); // Update our data to reflect all children's data
                }

                // If segment is greater or equal to two, split data into children
                if (segment.length >= 2 && segment.length >= minLength) {
                    segment.half = segment.length / 2;

                    final List<NonOverlappingSegment<D>> s1 = new ArrayList<NonOverlappingSegment<D>>();
                    final List<NonOverlappingSegment<D>> s2 = new ArrayList<NonOverlappingSegment<D>>();
                    for (int i = 0; i < segments.size(); i++) {
                        final NonOverlappingSegment<D> s = segments.get(i);
                        final long middle = segment.start + segment.half;
                        if (s.end < middle) {
                            s1.add(s);
                        } else if (s.start >= middle) {
                            s2.add(s);
                        } else {
                            // Need to split across middle
                            final NonOverlappingSegment<D> ss1 = new NonOverlappingSegment<D>(minLength, s.start, middle - 1, s.data);
                            s1.add(ss1);
 
                            final NonOverlappingSegment<D> ss2 = new NonOverlappingSegment<D>(minLength, middle, s.end, s.data);
                            s2.add(ss2);
                        }
                    }

                    final Segment<D> sub1 = createFromList(minLength, s1, segment.start, segment.half);
                    final Segment<D> sub2 = createFromList(minLength, s2, segment.start + segment.half, segment.length - segment.half);
                    segment.segments = new Segment[] { sub1, sub2 };
                } else if (segment.length <= minLength) {
                    for (Segment<D> s : segments) {
                        segment.set.add(s);
                    }
                }

                return segment;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public D query(long startOfQuery, long endOfQuery) {
                if (startOfQuery == this.start && endOfQuery == this.end) {
                    if (this.data == null)
                        return null;
                    final D dataToReturn = ((D) this.data.query(startOfQuery, endOfQuery));
                    return dataToReturn;
                }

                if (!this.hasChildren()) {
                    if (endOfQuery < this.start || startOfQuery > this.end) {
                        // Ignore
                    } else {
                        D dataToReturn = null;
                        if (this.set.size() == 0)
                            return dataToReturn;
                        for (Segment<D> s : this.set) {
                            if (s.start >= startOfQuery && s.end <= endOfQuery) {
                                if (dataToReturn == null)
                                    dataToReturn = (D) s.data.query(startOfQuery, endOfQuery);
                                else
                                    dataToReturn.combined(s.data);
                            } else if (s.start <= startOfQuery && s.end >= endOfQuery) {
                                if (dataToReturn == null)
                                    dataToReturn = (D) s.data.query(startOfQuery, endOfQuery);
                                else
                                    dataToReturn.combined(s.data);
                            }
                        }
                        return dataToReturn;
                    }
                } 

                if (this.hasChildren()) {
                    if (startOfQuery <= this.getLeftChild().end && endOfQuery > this.getLeftChild().end) {
                        final Data q1 = this.getLeftChild().query(startOfQuery, getLeftChild().end);
                        final Data q2 = this.getRightChild().query(getRightChild().start, endOfQuery);
                        if (q1 == null && q2 == null)
                            return null;
                        if (q1 != null && q2 == null)
                            return (D) q1;
                        if (q1 == null && q2 != null)
                            return (D) q2;
                        if (q1 != null && q2 != null) 
                            return ((D) q1.combined(q2));
                    } else if (startOfQuery <= this.getLeftChild().end && endOfQuery <= this.getLeftChild().end) {
                        return this.getLeftChild().query(startOfQuery, endOfQuery);
                    }
                    return this.getRightChild().query(startOfQuery, endOfQuery);
                }

                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                final StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(" ");
                builder.append("Set=").append(set);
                return builder.toString();
            }
        }
    }

    /**
     * Segment tree is a balanced-binary-tree based data structure efficient for
     * detecting all intervals (or segments) that contain a given point. The
     * segments may overlap with each other. The end points of stored segments
     * are inclusive, that is, when an interval spans from 2 to 6, an arbitrary
     * point x within that interval can take a value of 2 <= x <=6.
     */
    public static final class DynamicSegmentTree<D extends Data> extends SegmentTree<D> {

        private static final Comparator<OverlappingSegment<?>> START_COMPARATOR = new Comparator<OverlappingSegment<?>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(OverlappingSegment<?> arg0, OverlappingSegment<?> arg1) {
                if (arg0.start < arg1.start)
                    return -1;
                if (arg1.start < arg0.start)
                    return 1;
                return 0;
            }
        };

        private static final Comparator<OverlappingSegment<?>> END_COMPARATOR = new Comparator<OverlappingSegment<?>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(OverlappingSegment<?> arg0, OverlappingSegment<?> arg1) {
                if (arg0.end < arg1.end)
                    return -1;
                if (arg1.end < arg0.end)
                    return 1;
                return 0;
            }
        };

        public DynamicSegmentTree(List<D> data) {
            this(data, 1);
        }

        public DynamicSegmentTree(List<D> data, int minLength) {
            if (data.size() <= 0)
                return;

            // Check for gaps
            final List<OverlappingSegment<D>> segments = new ArrayList<OverlappingSegment<D>>();
            for (int i = 0; i < data.size(); i++) {
                if (i < data.size() - 1) {
                    final Data d1 = data.get(i);
                    final OverlappingSegment<D> s1 = new OverlappingSegment<D>(minLength, d1.start, d1.end, (D) d1);
                    segments.add(s1);

                    final Data d2 = data.get(i + 1);
                    if (d2.start - d1.end > 1) {
                        final Data d3 = d1.copy();
                        d3.clear();
                        d3.start = d1.end + 1;
                        d3.end = d2.start - 1;

                        final OverlappingSegment<D> s3 = new OverlappingSegment<D>(minLength, d3.start, d3.end, (D) d3);
                        segments.add(s3);
                    }
                } else {
                    final Data d1 = data.get(i);
                    final OverlappingSegment<D> s1 = new OverlappingSegment<D>(minLength, d1.start, d1.end, (D) d1);
                    segments.add(s1);
                }
            }

            // First start first
            Collections.sort(segments, START_COMPARATOR);
            final OverlappingSegment<D> startNode = segments.get(0);
            final long start = startNode.start - 1;
            final OverlappingSegment<D> s1 = new OverlappingSegment<D>(minLength, start, startNode.start, null);
            segments.add(0, s1);

            // Last end last
            Collections.sort(segments, END_COMPARATOR);
            final OverlappingSegment<D> endNode = segments.get(segments.size() - 1);
            final long end = endNode.end + 1;
            final OverlappingSegment<D> s2 = new OverlappingSegment<D>(minLength, endNode.end, end, null);
            segments.add(s2);

            final int length = (int) (end - start) + 1;
            root = OverlappingSegment.createFromList(minLength, segments, start, length);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public D query(long index) {
            return this.query(index, index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public D query(long startOfQuery, long endOfQuery) {
            if (root == null)
                return null;

            long s = startOfQuery;
            long e = endOfQuery;
            if (s < root.start)
                s = root.start;
            if (e > root.end)
                e = root.end;

            final D result = root.query(s, e);
            // reset the start and end, it can change during the query
            result.start = startOfQuery;
            result.end = endOfQuery;
            return result;
        }

        /**
         * Data structure representing a possibly overlapping segment.
         */
        protected static final class OverlappingSegment<D extends Data> extends Segment<D> {

            // Separate range set for fast range queries
            protected Set<Segment<D>> range = new HashSet<Segment<D>>();

            public OverlappingSegment(int minLength) {
                super(minLength);
            }

            public OverlappingSegment(int minLength, long start, long end, D data) {
                super(minLength);

                this.start = start;
                this.end = end;
                this.length = ((int) (end - start)) + 1;
                if (data == null)
                    return;
                this.data = ((D) data.copy());
            }

            protected static <D extends Data> Segment<D> createFromList(int minLength, List<OverlappingSegment<D>> segments, long start, int length) {
                final OverlappingSegment<D> segment = new OverlappingSegment<D>(minLength);
                segment.start = start;
                segment.end = start + (length - 1);
                segment.length = length;

                for (Segment<D> s : segments) {
                    if (s.data == null)
                        continue;
                    if (s.end < segment.start || s.start > segment.end) {
                        // Ignore
                    } else {
                        segment.range.add(s);
                    }
                    if (s.start == segment.start && s.end == segment.end) {
                        if (segment.data == null)
                            segment.data = ((D) s.data.copy());
                        else
                            segment.data.combined(s.data); // Update our data to reflect all children's data
                    } else if (!segment.hasChildren() && s.start >= segment.start && s.end <= segment.end) {
                        if (segment.data == null)
                            segment.data = ((D) s.data.copy());
                        else
                            segment.data.combined(s.data); // Update our data to reflect all children's data
                    }
                }

                // If segment is greater or equal to two, split data into children
                if (segment.length >= 2 && segment.length >= minLength) {
                    segment.half = segment.length / 2;

                    final List<OverlappingSegment<D>> s1 = new ArrayList<OverlappingSegment<D>>();
                    final List<OverlappingSegment<D>> s2 = new ArrayList<OverlappingSegment<D>>();
                    for (int i = 0; i < segments.size(); i++) {
                        final OverlappingSegment<D> s = segments.get(i);
                        final long middle = segment.start + segment.half;
                        if (s.end < middle) {
                            s1.add(s);
                        } else if (s.start >= middle) {
                            s2.add(s);
                        } else {
                            // Need to split across middle
                            final OverlappingSegment<D> ss1 = new OverlappingSegment<D>(minLength, s.start, middle - 1, s.data);
                            s1.add(ss1);

                            final OverlappingSegment<D> ss2 = new OverlappingSegment<D>(minLength, middle, s.end, s.data);
                            s2.add(ss2);
                        }
                    }

                    final Segment<D> sub1 = createFromList(minLength, s1, segment.start, segment.half);
                    final Segment<D> sub2 = createFromList(minLength, s2, segment.start + segment.half, segment.length - segment.half);
                    segment.segments = new Segment[] { sub1, sub2 };
                }

                return segment;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public D query(long startOfQuery, long endOfQuery) {
                D result = null;

                // Use the range data to make range queries faster
                if (startOfQuery == this.start && endOfQuery == this.end) {
                    for (Segment<D> s : this.range) {
                        final D temp = (D) s.data.query(startOfQuery, endOfQuery);
                        if (temp != null) {
                            if (result == null)
                                result = (D) temp.copy();
                            else
                                result.combined(temp);
                        }
                    }
                } else if (!this.hasChildren()) {
                    if (endOfQuery < this.start || startOfQuery > this.end) {
                        // Ignore
                    } else {
                        for (Segment<D> s : this.range) {
                            if (endOfQuery < s.start || startOfQuery > s.end) {
                                // Ignore
                            } else {
                                final D temp = (D) s.data.query(startOfQuery, endOfQuery);
                                if (temp != null) {
                                    if (result == null)
                                        result = (D) temp.copy();
                                    else
                                        result.combined(temp);
                                }
                            }
                        }
                    }
                } else {
                    final long middle = this.start + this.half;
                    D temp = null;
                    if (startOfQuery < middle && endOfQuery >= middle) {
                        temp = this.getLeftChild().query(startOfQuery, middle - 1);
                        D temp2 = this.getRightChild().query(middle, endOfQuery);
                        if (temp2 != null) {
                            if (temp == null)
                                temp = (D) temp2.copy();
                            else
                                temp.combined(temp2);
                        }
                    } else if (endOfQuery < middle) {
                        temp = this.getLeftChild().query(startOfQuery, endOfQuery);
                    } else if (startOfQuery >= middle) {
                        temp = this.getRightChild().query(startOfQuery, endOfQuery);
                    }
                    if (temp != null)
                        result = (D) temp.copy();
                }

                return result;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(" ");
                builder.append("Range=").append(range);
                return builder.toString();
            }
        }
    }
}
