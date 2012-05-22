package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;


/**
 * Segment tree using objects and pointers. A segment tree is a tree data
 * structure for storing intervals, or segments. It allows querying which of the
 * stored segments contain a given point. It is, in principle, a static
 * structure; that is, its content cannot be modified once the structure is
 * built.
 * 
 * http://en.wikipedia.org/wiki/Segment_tree
 * 
 * This class is meant to be somewhat generic, all you'd have to do is extend the 
 * Data abstract class to store your custom data. I've also included a range minimum, 
 * range maximum, and range sum implementations.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SegmentTree<D extends SegmentTree.Data> {

    private Segment<D> root = null;


    public SegmentTree(Segment<D>[] segments) {
        root = new Segment<D>(segments);
    }

    public void update(long index, D data) {
        root.update(index, data);
    }

    public D query(int startIndex, int endIndex) {
        return root.query(startIndex, endIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(root.toString());
        return builder.toString();
    }


    public abstract static class Data {
        public Data() { }
        public abstract Data combined(Data q);
        public abstract Data copy();
        public abstract Data query();
        public abstract Data update(Data q);
    }

    public static final class RangeMinimumData<N extends Number> extends Data {

        public N number = null;


        public RangeMinimumData(N number) {
            this.number = number;
        }

        @SuppressWarnings("unchecked")
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
            if (data.number.doubleValue() < this.number.doubleValue()) {
                this.number = data.number;
            }
        }

        @Override
        public Data copy() {
            return new RangeMinimumData<N>(number);
        }

        @Override
        public Data query() {
            return copy();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Data update(Data data) {
            RangeMinimumData<N> q = null;
            if (data instanceof RangeMinimumData) {
                q = (RangeMinimumData<N>) data;
                this.update(q);
            }
            return this;
        }

        private void update(RangeMinimumData<N> data) {
            this.number = data.number;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("number=").append(number).append("\n");
            return builder.toString();
        }
    }

    public static final class RangeMaximumData<N extends Number> extends Data {

        public N number = null;


        public RangeMaximumData(N number) {
            this.number = number;
        }

        @SuppressWarnings("unchecked")
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
            if (data.number.doubleValue() > this.number.doubleValue()) {
                this.number = data.number;
            }
        }

        @Override
        public Data copy() {
            return new RangeMaximumData<N>(number);
        }

        @Override
        public Data query() {
            return copy();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Data update(Data data) {
            RangeMaximumData<N> q = null;
            if (data instanceof RangeMaximumData) {
                q = (RangeMaximumData<N>) data;
                this.update(q);
            }
            return this;
        }

        private void update(RangeMaximumData<N> data) {
            this.number = data.number;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("number=").append(number).append("\n");
            return builder.toString();
        }
    }

    public static final class RangeSumData<N extends Number> extends Data {

        public N number = null;


        public RangeSumData(N number) {
            this.number = number;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Data combined(Data data) {
            RangeSumData<N> q = null;
            if (data instanceof RangeSumData) {
                q = (RangeSumData<N>) data;
                this.combined(q);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        private void combined(RangeSumData<N> data) {
            Double d1 = this.number.doubleValue();
            Double d2 = data.number.doubleValue();
            Double r = d1+d2;
            this.number = (N)r;
        }

        @Override
        public Data copy() {
            return new RangeSumData<N>(number);
        }

        @Override
        public Data query() {
            return copy();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Data update(Data data) {
            RangeSumData<N> q = null;
            if (data instanceof RangeSumData) {
                q = (RangeSumData<N>) data;
                this.update(q);
            }
            return this;
        }

        private void update(RangeSumData<N> data) {
            this.number = data.number;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("number=").append(number).append("\n");
            return builder.toString();
        }
    }

    public static final class QuadrantData extends Data {

        public long quad1 = 0;
        public long quad2 = 0;
        public long quad3 = 0;
        public long quad4 = 0;


        public QuadrantData() { }

        public QuadrantData(long quad1, long quad2, long quad3, long quad4) {
            this.quad1 = quad1;
            this.quad2 = quad2;
            this.quad3 = quad3;
            this.quad4 = quad4;
        }

        @Override
        public Data combined(Data data) {
            QuadrantData q = null;
            if (data instanceof QuadrantData) {
                q = (QuadrantData) data;
                this.combined(q);
            }
            return this;
        }

        private void combined(QuadrantData q) {
            this.quad1 += q.quad1;
            this.quad2 += q.quad2;
            this.quad3 += q.quad3;
            this.quad4 += q.quad4;
        }

        @Override
        public QuadrantData copy() {
            QuadrantData copy = new QuadrantData();
            copy.quad1 = this.quad1;
            copy.quad2 = this.quad2;
            copy.quad3 = this.quad3;
            copy.quad4 = this.quad4;
            return copy;
        }

        @Override
        public Data query() {
            return copy();
        }

        @Override
        public Data update(Data data) {
            QuadrantData q = null;
            if (data instanceof QuadrantData) {
                q = (QuadrantData) data;
                this.update(q);
            }
            return this;
        }

        private void update(QuadrantData q) {
            this.quad1 = q.quad1;
            this.quad2 = q.quad2;
            this.quad3 = q.quad3;
            this.quad4 = q.quad4;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("q1=").append(quad1).append(",");
            builder.append("q2=").append(quad2).append(",");
            builder.append("q3=").append(quad3).append(",");
            builder.append("q4=").append(quad4);
            return builder.toString();
        }
    }

    public static final class Segment<D extends Data> implements Comparable<Segment<D>> {

        protected Segment<D>[] segments = null;
        protected int length = 0;
        protected int half = 0;

        protected long start = 0;
        protected long end = 0;

        protected D data = null;


        @SuppressWarnings("unchecked")
        public Segment(int index, D data) {
            this.length = 1;
            this.start = index;
            this.end = index;
            this.data = ((D)data.copy());
        }

        @SuppressWarnings("unchecked")
        public Segment(int start, int end, D data) {
            this.length = 1;
            this.start = start;
            this.end = end;
            this.data = ((D)data.copy());
        }

        @SuppressWarnings("unchecked")
        protected Segment(Segment<D>[] segments) {
            this.length = segments.length;
            this.start = segments[0].start;
            this.end = segments[length - 1].end;
            this.data = null;

            Arrays.sort(segments); //Make sure they are sorted
            this.segments = segments; //Keep all the segments
            for (Segment<D> s : segments) {
                if (this.data==null) this.data = ((D)s.data.copy());
                else this.data.combined(s.data); //Update our data to reflect all children's data
            }

            //If segment is greater or equal to two, split data into children
            if (length >= 2) {
                half = length / 2;
                if (length > 1 && length % 2 != 0) half++;
                Segment<D>[] s1 = new Segment[half];
                for (int i = 0; i < half; i++) {
                    Segment<D> s = segments[i];
                    s1[i] = s;
                }
                Segment<D> sub1 = new Segment<D>(s1);
                Segment<D>[] s2 = new Segment[length - half];
                for (int i = half; i < length; i++) {
                    Segment<D> s = segments[i];
                    s2[i - half] = s;
                }
                Segment<D> sub2 = new Segment<D>(s2);
                this.segments = new Segment[] { sub1, sub2 };
            } else {
                this.segments = new Segment[] { this };
            }
        }

        public void update(long index, D data) {
            if (length >= 2) {
                if (index < start + half) {
                    segments[0].update(index, data);
                } else if (index >= start + half) {
                    segments[1].update(index, data);
                }
            }
            if (index>=this.start && index<=this.end && this.segments.length==1) {
                this.data.update(data); //update leaf which replaces value
            } else {
                this.update(); //update self from children
            }
        }

        @SuppressWarnings("unchecked")
        private void update() {
            this.data = null;
            for (Segment<D> d : segments) {
                if (this.data==null) this.data = ((D)d.data.copy());
                else this.data.combined(d.data);
            }
        }

        @SuppressWarnings("unchecked")
        public D query(long start, long end) {
            if (this.start == start && this.end == end) {
                D dataToReturn = ((D)this.data.copy());
                return dataToReturn;
            } else if (start <= segments[0].end && end > segments[0].end) {
                Data q1 = segments[0].query(start, segments[0].end);
                Data q2 = segments[1].query(segments[1].start, end);
                return ((D)q1.combined(q2));
            } else if (start <= segments[0].end && end <= segments[0].end) {
                return segments[0].query(start, end);
            }
            return segments[1].query(start, end);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("start=").append(start).append("->");
            builder.append("end=").append(end).append(" ");
            builder.append("Data=").append(data).append("\n");
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Segment<D> p) {
            if (this.end < p.start) return -1;
            if (this.start > p.end) return 1;
            return 0; //Equal or overlapping
        }
    }
}
