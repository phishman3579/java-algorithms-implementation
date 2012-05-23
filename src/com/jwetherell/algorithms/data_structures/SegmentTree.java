package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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


    public SegmentTree(List<Segment<D>> segments) {
        Collections.sort(segments); //Make sure they are sorted
        
        if (segments.size()<=0) return;
        
        Segment<D> first = segments.get(0);
        if (first instanceof Segment.NonOverlappingSegment) {
            root = Segment.NonOverlappingSegment.createFromList(segments);
        } else if (first instanceof Segment.OverlappingSegment) {
            root = Segment.OverlappingSegment.createFromList(segments);
        }
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

        public abstract Data combined(Data q);
        public abstract Data copy();
        public abstract Data query();
        public abstract Data update(Data q);


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
                builder.append(quad1).append(",");
                builder.append(quad2).append(",");
                builder.append(quad3).append(",");
                builder.append(quad4);
                return builder.toString();
            }
        }
    
        public static final class IntervalData<O extends Object> extends Data {
    
            private List<O> list = new ArrayList<O>();
            
    
            public IntervalData(O object) {
                this.list.add(object);
            }
            
            public IntervalData(List<O> list) {
                this.list = list;
            }
    
            public void add(O object) {
                this.list.add(object);
            }
            public void remove(O object) {
                this.list.remove(object);
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public Data combined(Data data) {
                IntervalData<O> q = null;
                if (data instanceof IntervalData) {
                    q = (IntervalData<O>) data;
                    this.combined(q);
                }
                return this;
            }
    
            private void combined(IntervalData<O> data) {
                this.list.addAll(data.list);
            }
    
            @Override
            public Data copy() {
                List<O> listCopy = new ArrayList<O>();
                listCopy.addAll(list);
                return new IntervalData<O>(listCopy);
            }
    
            @Override
            public Data query() {
                return copy();
            }
    
            @SuppressWarnings("unchecked")
            @Override
            public Data update(Data data) {
                IntervalData<O> q = null;
                if (data instanceof IntervalData) {
                    q = (IntervalData<O>) data;
                    this.update(q);
                }
                return this;
            }
    
            private void update(IntervalData<O> data) {
                this.list.clear();
                this.list.addAll(data.list);
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("list=").append(list);
                return builder.toString();
            }
        }
    
        public static final class RangeMaximumData<N extends Number> extends Data {
    
            public N maximum = null;
    
    
            public RangeMaximumData(N number) {
                this.maximum = number;
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
                if (data.maximum.doubleValue() > this.maximum.doubleValue()) {
                    this.maximum = data.maximum;
                }
            }
    
            @Override
            public Data copy() {
                return new RangeMaximumData<N>(maximum);
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
                this.maximum = data.maximum;
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("maximum=").append(maximum);
                return builder.toString();
            }
        }
    
        public static final class RangeMinimumData<N extends Number> extends Data {
    
            public N minimum = null;
    
    
            public RangeMinimumData(N number) {
                this.minimum = number;
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
                if (data.minimum.doubleValue() < this.minimum.doubleValue()) {
                    this.minimum = data.minimum;
                }
            }
    
            @Override
            public Data copy() {
                return new RangeMinimumData<N>(minimum);
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
                this.minimum = data.minimum;
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("minimum=").append(minimum);
                return builder.toString();
            }
        }
    
        public static final class RangeSumData<N extends Number> extends Data {
    
            public N sum = null;
    
    
            public RangeSumData(N number) {
                this.sum = number;
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
                Double d1 = this.sum.doubleValue();
                Double d2 = data.sum.doubleValue();
                Double r = d1+d2;
                this.sum = (N)r;
            }
    
            @Override
            public Data copy() {
                return new RangeSumData<N>(sum);
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
                this.sum = data.sum;
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("sum=").append(sum);
                return builder.toString();
            }
        }
    }

    public abstract static class Segment<D extends Data> implements Comparable<Segment<D>> {

        protected Segment<D>[] segments = null;
        protected int length = 0;
        protected int half = 0;

        protected long startIndex = 0;
        protected long endIndex = 0;

        protected D data = null;


        public abstract void update(long index, D data);
        public abstract D query(long startIndex, long endIndex);


        public static final class NonOverlappingSegment<D extends Data> extends Segment<D> {
    
    
            public NonOverlappingSegment() { }
    
            @SuppressWarnings("unchecked")
            public NonOverlappingSegment(long index, D data) {
                this.length = 1;
                this.startIndex = index;
                this.endIndex = index;
                this.data = ((D)data.copy());
            }
    
            @SuppressWarnings("unchecked")
            public NonOverlappingSegment(long start, long end, D data) {
                this.length = 1;
                this.startIndex = start;
                this.endIndex = end;
                this.data = ((D)data.copy());
            }
    
            @SuppressWarnings("unchecked")
            protected static <D extends Data> NonOverlappingSegment<D> createFromList(List<Segment<D>> segments) {
                NonOverlappingSegment<D> segment = new NonOverlappingSegment<D>();
                segment.length = segments.size();
                segment.startIndex = segments.get(0).startIndex;
                segment.endIndex = segments.get(segment.length - 1).endIndex;
                segment.data = null;
    
                for (Segment<D> s : segments) {
                    if (segment.data==null) segment.data = ((D)s.data.copy());
                    else segment.data.combined(s.data); //Update our data to reflect all children's data
                }
    
                //If segment is greater or equal to two, split data into children
                if (segment.length >= 2) {
                    segment.half = segment.length / 2;
                    if (segment.length > 1 && segment.length % 2 != 0) segment.half++;
                    List<Segment<D>> s1 = new ArrayList<Segment<D>>();
                    List<Segment<D>> s2 = new ArrayList<Segment<D>>();
                    for (int i = 0; i < segment.length; i++) {
                        Segment<D> s = segments.get(i);
                        if (s.startIndex<segment.startIndex+segment.half) s1.add(s);
                        else s2.add(s);
                    }
                    NonOverlappingSegment<D> sub1 = createFromList(s1);
                    NonOverlappingSegment<D> sub2 = createFromList(s2);
                    segment.segments = new NonOverlappingSegment[] { sub1, sub2 };
                } else {
                    segment.segments = new NonOverlappingSegment[] { segment };
                }
                
                return segment;
            }
    
            public void update(long index, D data) {
                if (length >= 2) {
                    if (index < startIndex + half) {
                        segments[0].update(index, data);
                    } else if (index >= startIndex + half) {
                        segments[1].update(index, data);
                    }
                }
                if (index>=this.startIndex && index<=this.endIndex && this.segments.length==1) {
                    this.data.update(data); //update leaf
                } else {
                    this.update(); //update from children
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
            public D query(long startIndex, long endIndex) {
                if (startIndex == this.startIndex && endIndex == this.endIndex) {
                    D dataToReturn = ((D)this.data.query());
                    return dataToReturn;
                } else if (startIndex >= this.startIndex && endIndex <= this.endIndex && this.segments.length == 1) {
                    D dataToReturn = ((D)this.data.query());
                    return dataToReturn;
                } else if (startIndex <= segments[0].endIndex && endIndex > segments[0].endIndex) {
                    Data q1 = segments[0].query(startIndex, segments[0].endIndex);
                    Data q2 = segments[1].query(segments[1].startIndex, endIndex);
                    return ((D)q1.combined(q2));
                } else if (startIndex <= segments[0].endIndex && endIndex <= segments[0].endIndex) {
                    return segments[0].query(startIndex, endIndex);
                }
                return segments[1].query(startIndex, endIndex);
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("startIndex=").append(startIndex).append("->");
                builder.append("endIndex=").append(endIndex).append(" ");
                builder.append("Data=").append(data).append("\n");
                return builder.toString();
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public int compareTo(Segment<D> p) {
                if (!(p instanceof OverlappingSegment)) return Integer.MIN_VALUE;
                
                OverlappingSegment<D> o = (OverlappingSegment<D>) p;
                if (this.startIndex < o.startIndex) return -1;
                if (o.startIndex < this.startIndex) return 1;
                
                if (this.endIndex < o.endIndex) return -1;
                if (o.endIndex < this.endIndex) return 1;
                return 0;
            }
        }
        
        public static final class OverlappingSegment<D extends Data> extends Segment<D> {
    
    
            public OverlappingSegment() { }
    
            @SuppressWarnings("unchecked")
            public OverlappingSegment(long start, long end, D data) {
                this.length = 1;
                this.startIndex = start;
                this.endIndex = end;
                this.data = ((D)data.copy());
            }
    
            @SuppressWarnings("unchecked")
            protected static <D extends Data> OverlappingSegment<D> createFromList(List<Segment<D>> segments) {
                OverlappingSegment<D> segment = new OverlappingSegment<D>();
                segment.length = segments.size();
                segment.startIndex = segments.get(0).startIndex;
                segment.endIndex = segments.get(segment.length - 1).endIndex;
                segment.data = null;
    
                for (Segment<D> s : segments) {
                    if (segment.data==null) segment.data = ((D)s.data.copy());
                    else segment.data.combined(s.data); //Update our data to reflect all children's data
                }
    
                //If segment is greater or equal to two, split data into children
                if (segment.length >= 2) {
                    segment.half = segment.length / 2;
                    if (segment.length > 1 && segment.length % 2 != 0) segment.half++;
                    List<Segment<D>> s1 = new ArrayList<Segment<D>>();
                    List<Segment<D>> s2 = new ArrayList<Segment<D>>();
                    for (int i = 0; i < segment.length; i++) {
                        Segment<D> s = segments.get(i);
                        if (s.startIndex<segment.startIndex+segment.half) s1.add(s);
                        else s2.add(s);
                    }
                    OverlappingSegment<D> sub1 = createFromList(s1);
                    OverlappingSegment<D> sub2 = createFromList(s2);
                    segment.segments = new OverlappingSegment[] { sub1, sub2 };
                } else {
                    segment.segments = new OverlappingSegment[] { segment };
                }
                
                return segment;
            }
    
            public void update(long index, D data) {
                if (length >= 2) {
                    if (index < startIndex + half) {
                        segments[0].update(index, data);
                    } else if (index >= startIndex + half) {
                        segments[1].update(index, data);
                    }
                }
                if (index>=this.startIndex && index<=this.endIndex && this.segments.length==1) {
                    this.data.update(data); //update leaf
                } else {
                    this.update(); //update from children
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
            public D query(long startIndex, long endIndex) {
                if (startIndex == this.startIndex && endIndex == this.endIndex) {
                    D dataToReturn = ((D)this.data.query());
                    return dataToReturn;
                } else if (startIndex >= this.startIndex && endIndex <= this.endIndex && this.segments.length == 1) {
                    D dataToReturn = ((D)this.data.query());
                    return dataToReturn;
                } else if (startIndex <= segments[0].endIndex && endIndex > segments[0].endIndex) {
                    Data q1 = segments[0].query(startIndex, segments[0].endIndex);
                    Data q2 = segments[1].query(segments[1].startIndex, endIndex);
                    return ((D)q1.combined(q2));
                } else if (startIndex <= segments[0].endIndex && endIndex <= segments[0].endIndex) {
                    return segments[0].query(startIndex, endIndex);
                }
                return segments[1].query(startIndex, endIndex);
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("startIndex=").append(startIndex).append("->");
                builder.append("endIndex=").append(endIndex).append(" ");
                builder.append("Data=").append(data).append("\n");
                return builder.toString();
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public int compareTo(Segment<D> p) {
                if (!(p instanceof OverlappingSegment)) return Integer.MIN_VALUE;
                
                OverlappingSegment<D> o = (OverlappingSegment<D>) p;
                if (this.startIndex < o.startIndex) return -1;
                if (o.startIndex < this.startIndex) return 1;
                
                if (this.endIndex < o.endIndex) return -1;
                if (o.endIndex < this.endIndex) return 1;
                return 0;
            }
        }
    }
}
