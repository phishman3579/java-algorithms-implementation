package com.jwetherell.algorithms.data_structures;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * An interval tree is an ordered tree data structure to hold intervals.
 * Specifically, it allows one to efficiently find all intervals that overlap
 * with any given interval or point.
 * 
 * http://en.wikipedia.org/wiki/Interval_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class IntervalTree<O extends Object> {

    private Interval<O> root = null;

    private static final Comparator<IntervalData<?>> startComparator = new Comparator<IntervalData<?>>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IntervalData<?> arg0, IntervalData<?> arg1) {
            if (arg0.start < arg1.start) return -1;
            if (arg1.start < arg0.start) return 1;
            return 0;
        }
    };

    private static final Comparator<IntervalData<?>> endComparator = new Comparator<IntervalData<?>>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IntervalData<?> arg0, IntervalData<?> arg1) {
            if (arg0.end < arg1.end) return -1;
            if (arg1.end < arg0.end) return 1;
            return 0;
        }
    };

    /**
     * Create interval tree from list of IntervalData objects;
     * 
     * @param intervals
     *            is a list of IntervalData objects
     */
    public IntervalTree(List<IntervalData<O>> intervals) {
        if (intervals.size() <= 0) return;

        root = createFromList(intervals);
    }

    protected static final <O extends Object> Interval<O> createFromList(List<IntervalData<O>> intervals) {
        Interval<O> newInterval = new Interval<O>();
        int half = intervals.size() / 2;
        IntervalData<O> middle = intervals.get(half);
        newInterval.center = ((middle.start + middle.end) / 2);
        List<IntervalData<O>> leftIntervals = new ArrayList<IntervalData<O>>();
        List<IntervalData<O>> rightIntervals = new ArrayList<IntervalData<O>>();
        for (IntervalData<O> interval : intervals) {
            if (interval.end < newInterval.center) {
                leftIntervals.add(interval);
            } else if (interval.start > newInterval.center) {
                rightIntervals.add(interval);
            } else {
                newInterval.overlap.add(interval);
            }
        }
        if (leftIntervals.size() > 0) newInterval.left = createFromList(leftIntervals);
        if (rightIntervals.size() > 0) newInterval.right = createFromList(rightIntervals);
        return newInterval;
    }

    /**
     * Stabbing query
     * 
     * @param index
     *            to query for.
     * @return data at index.
     */
    public IntervalData<O> query(long index) {
        return root.query(index);
    }

    /**
     * Range query
     * 
     * @param start
     *            of range to query for.
     * @param end
     *            of range to query for.
     * @return data for range.
     */
    public IntervalData<O> query(long start, long end) {
        return root.query(start, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(IntervalTreePrinter.getString(this));
        return builder.toString();
    }

    protected static class IntervalTreePrinter {

        public static <O extends Object> String getString(IntervalTree<O> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <O extends Object> String getString(Interval<O> interval, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + interval.toString() + "\n");
            List<Interval<O>> children = new ArrayList<Interval<O>>();
            if (interval.left != null) children.add(interval.left);
            if (interval.right != null) children.add(interval.right);
            if (children.size() > 0) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() > 0) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }

    public static final class Interval<O> {

        private long center = Long.MIN_VALUE;
        private Interval<O> left = null;
        private Interval<O> right = null;
        private Set<IntervalData<O>> overlap = new TreeSet<IntervalData<O>>(startComparator);

        /**
         * Stabbing query
         * 
         * @param index
         *            to query for.
         * @return data at index.
         */
        public IntervalData<O> query(long index) {
            IntervalData<O> results = null;
            if (index < center) {
                // overlap is sorted by start point
                for (IntervalData<O> data : overlap) {
                    if (data.start > index) break;

                    IntervalData<O> temp = data.query(index);
                    if (results == null && temp != null) results = temp;
                    else if (temp != null) results.combined(temp);
                }
            } else if (index >= center) {
                // overlapEnd is sorted by end point
                Set<IntervalData<O>> overlapEnd = new TreeSet<IntervalData<O>>(endComparator);
                overlapEnd.addAll(overlap);
                for (IntervalData<O> data : overlapEnd) {
                    if (data.end < index) break;

                    IntervalData<O> temp = data.query(index);
                    if (results == null && temp != null) results = temp;
                    else if (temp != null) results.combined(temp);
                }
            }
            if (index < center) {
                if (left != null) {
                    IntervalData<O> temp = left.query(index);
                    if (results == null && temp != null) results = temp;
                    else if (temp != null) results.combined(temp);
                }
            } else if (index >= center) {
                if (right != null) {
                    IntervalData<O> temp = right.query(index);
                    if (results == null && temp != null) results = temp;
                    else if (temp != null) results.combined(temp);
                }
            }
            return results;
        }

        /**
         * Range query
         * 
         * @param start
         *            of range to query for.
         * @param end
         *            of range to query for.
         * @return data for range.
         */
        public IntervalData<O> query(long start, long end) {
            IntervalData<O> results = null;
            for (IntervalData<O> data : overlap) {
                if (data.start > end) break;
                IntervalData<O> temp = data.query(start, end);
                if (results == null && temp != null) results = temp;
                else if (temp != null) results.combined(temp);
            }
            if (left != null && start < center) {
                IntervalData<O> temp = left.query(start, end);
                if (temp != null && results == null) results = temp;
                else if (temp != null) results.combined(temp);
            }
            if (right != null && end >= center) {
                IntervalData<O> temp = right.query(start, end);
                if (temp != null && results == null) results = temp;
                else if (temp != null) results.combined(temp);
            }
            return results;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Center=").append(center);
            builder.append(" Set=").append(overlap);
            return builder.toString();
        }
    }

    /**
     * Data structure representing an interval.
     */
    public static final class IntervalData<O> implements Comparable<IntervalData<O>> {

        private long start = Long.MIN_VALUE;
        private long end = Long.MAX_VALUE;
        private Set<O> set = new TreeSet<O>(); // Sorted

        /**
         * Interval data using O as it's unique identifier
         * 
         * @param object
         *            Object which defines the interval data
         */
        public IntervalData(long index, O object) {
            this.start = index;
            this.end = index;
            this.set.add(object);
        }

        /**
         * Interval data using O as it's unique identifier
         * 
         * @param object
         *            Object which defines the interval data
         */
        public IntervalData(long start, long end, O object) {
            this.start = start;
            this.end = end;
            this.set.add(object);
        }

        /**
         * Interval data list which should all be unique
         * 
         * @param list
         *            of interval data objects
         */
        public IntervalData(long start, long end, Set<O> set) {
            this.start = start;
            this.end = end;
            this.set = set;

            // Make sure they are unique
            Iterator<O> iter = set.iterator();
            while (iter.hasNext()) {
                O obj1 = iter.next();
                O obj2 = null;
                if (iter.hasNext()) obj2 = iter.next();
                if (obj1.equals(obj2)) throw new InvalidParameterException("Each interval data in the list must be unique.");
            }
        }

        /**
         * Clear the indices.
         */
        public void clear() {
            this.start = Long.MIN_VALUE;
            this.end = Long.MAX_VALUE;
            this.set.clear();
        }

        /**
         * Combined this IntervalData with data.
         * 
         * @param data
         *            to combined with.
         * @return Data which represents the combination.
         */
        public IntervalData<O> combined(IntervalData<O> data) {
            if (data.start < this.start) this.start = data.start;
            if (data.end > this.end) this.end = data.end;
            this.set.addAll(data.set);
            return this;
        }

        /**
         * Deep copy of data.
         * 
         * @return deep copy.
         */
        public IntervalData<O> copy() {
            Set<O> listCopy = new TreeSet<O>();
            listCopy.addAll(set);
            return new IntervalData<O>(start, end, listCopy);
        }

        /**
         * Query inside this data object.
         * 
         * @param start
         *            of range to query for.
         * @param end
         *            of range to query for.
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public IntervalData<O> query(long index) {
            if (index < this.start || index > this.end) {
                // Ignore
            } else {
                return copy();
            }
            return null;
        }

        /**
         * Query inside this data object.
         * 
         * @param start
         *            of range to query for.
         * @param end
         *            of range to query for.
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public IntervalData<O> query(long start, long end) {
            if (end < this.start || start > this.end) {
                // Ignore
            } else {
                return copy();
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof IntervalData)) return false;
            @SuppressWarnings("unchecked")
            IntervalData<O> data = (IntervalData<O>) obj;
            if (this.start == data.start && this.end == data.end) {
                if (this.set.size() != data.set.size()) return false;
                for (O o : set) {
                    if (!data.set.contains(o)) return false;
                }
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(IntervalData<O> d) {
            if (this.end < d.end) return -1;
            if (d.end < this.end) return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(start).append("->").append(end);
            builder.append(" set=").append(set);
            return builder.toString();
        }
    }
}
