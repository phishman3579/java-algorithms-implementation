package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


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
public abstract class SegmentTree<D extends SegmentTree.Data> {

    protected Segment<D> root = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(SegmentTreePrinter.getString(this));
        return builder.toString();
    }

    public abstract static class Data {
        public abstract Data combined(Data q);
        public abstract Data copy();
        public abstract Data query();
        public abstract Data update(Data q);
    }

    public abstract static class Segment<D extends Data> implements Comparable<Segment<D>> {
        protected Segment<D>[] segments = null;
        protected int length = 0;
        protected int half = 0;
        protected long startIndex = 0;
        protected long endIndex = 0;
        protected D data = null;
    }

    protected static class SegmentTreePrinter {

        public static <D extends SegmentTree.Data> String getString(SegmentTree<D> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <D extends SegmentTree.Data> String getString(Segment<D> segment, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append( prefix + (isTail ? "└── " : "├── ") + 
                            "start=" + segment.startIndex + " end=" + segment.endIndex + 
                            " length=" + segment.length +
                            " data=" + segment.data + "\n"
                          );
            List<Segment<D>> children = new ArrayList<Segment<D>>();
            if (segment.segments!=null) {
                for (Segment<D> c : segment.segments) children.add(c);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() > 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }

    /**
     * Segment tree is a balanced-binary-tree based data structure efficient for detecting all intervals (or segments) 
     * that contain a given point. The segments may overlap with each other. The end points of stored segments are not 
     * inclusive, that is, when an interval spans from 2 to 6, an arbitrary point x within that interval can take a 
     * value of 2 <= x < 6, but not the exact value of 6 itself.
     */
    public static final class FlatSegmentTree<D extends FlatSegmentTree.NonOverlappingData> extends SegmentTree<D> {

        public FlatSegmentTree(List<NonOverlappingSegment<D>> segments) {
            Collections.sort(segments); //Make sure they are sorted
            
            if (segments.size()<=0) return;
            
            root = NonOverlappingSegment.createFromList(segments);
        }

        public void update(long index, D data) {
            ((NonOverlappingSegment<D>)root).update(index, data);
        }

        public D query(long index) {
            return this.query(index, index);
        }

        public D query(long startIndex, long endIndex) {
            if (root==null) return null;
            
            if (startIndex<root.startIndex) startIndex = root.startIndex;
            if (endIndex>root.endIndex) endIndex = root.endIndex;
            
            return (D)((NonOverlappingSegment<D>)root).query(startIndex, endIndex);
        }

        public abstract static class NonOverlappingData extends Data {

            public static final class QuadrantData extends NonOverlappingData {
        
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

            public static final class RangeMaximumData<N extends Number> extends NonOverlappingData {
        
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
        
            public static final class RangeMinimumData<N extends Number> extends NonOverlappingData {
        
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
        
            public static final class RangeSumData<N extends Number> extends NonOverlappingData {
        
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
                this.length = ((int)(end-start))+1;
                this.startIndex = start;
                this.endIndex = end;
                this.data = ((D)data.copy());
            }
    
            @SuppressWarnings("unchecked")
            protected static <D extends Data> Segment<D> createFromList(List<NonOverlappingSegment<D>> segments) {
                NonOverlappingSegment<D> segment = new NonOverlappingSegment<D>();
                segment.length = segments.size();
                segment.startIndex = segments.get(0).startIndex;
                segment.endIndex = segments.get(segment.length - 1).endIndex;

                for (Segment<D> s : segments) {
                    if (segment.data==null) segment.data = ((D)s.data.copy());
                    else segment.data.combined(s.data); //Update our data to reflect all children's data
                }
    
                //If segment is greater or equal to two, split data into children
                if (segment.length >= 2) {
                    segment.half = segment.length / 2;
                    List<NonOverlappingSegment<D>> s1 = new ArrayList<NonOverlappingSegment<D>>();
                    List<NonOverlappingSegment<D>> s2 = new ArrayList<NonOverlappingSegment<D>>();
                    for (int i = 0; i < segment.length; i++) {
                        NonOverlappingSegment<D> s = segments.get(i);
                        if (s.startIndex<segment.startIndex+segment.half) s1.add(s);
                        else s2.add(s);
                    }
                    Segment<D> sub1 = createFromList(s1);
                    Segment<D> sub2 = createFromList(s2);
                    segment.segments = new Segment[] { sub1, sub2 };
                } else {
                    segment.segments = new Segment[] { segment };
                }
                
                return segment;
            }
    
            public void update(long index, D data) {
                if (length >= 2) {
                    if (index < startIndex + half) {
                        ((NonOverlappingSegment<D>)segments[0]).update(index, data);
                    } else if (index >= startIndex + half) {
                        ((NonOverlappingSegment<D>)segments[1]).update(index, data);
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
                } else if (startIndex <= segments[0].endIndex && endIndex > segments[0].endIndex) {
                    Data q1 = ((NonOverlappingSegment<D>)segments[0]).query(startIndex, segments[0].endIndex);
                    Data q2 = ((NonOverlappingSegment<D>)segments[1]).query(segments[1].startIndex, endIndex);
                    return ((D)q1.combined(q2));
                } else if (startIndex <= segments[0].endIndex && endIndex <= segments[0].endIndex) {
                    return ((NonOverlappingSegment<D>)segments[0]).query(startIndex, endIndex);
                }
                return ((NonOverlappingSegment<D>)segments[1]).query(startIndex, endIndex);
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
                if (!(p instanceof NonOverlappingSegment)) return Integer.MIN_VALUE;
                
                NonOverlappingSegment<D> o = (NonOverlappingSegment<D>) p;
                if (this.startIndex < o.startIndex) return -1;
                if (o.startIndex < this.startIndex) return 1;
                
                if (this.endIndex < o.endIndex) return -1;
                if (o.endIndex < this.endIndex) return 1;
                return 0;
            }
        }
    }
    
    /**
     * Flat segment tree is a variant of segment tree that is designed to store a collection of non-overlapping 
     * segments. This structure is efficient when you need to store values associated with 1 dimensional segments 
     * that never overlap with each other. Like segment tree, stored segments' end points are non-inclusive.
     */
    public static final class DynamicSegmentTree<D extends DynamicSegmentTree.OverlappingData> extends SegmentTree<D> {

        public DynamicSegmentTree(List<OverlappingSegment<D>> segments) {
            Collections.sort(segments); //Make sure they are sorted
            
            if (segments.size()<=0) return;
            
            root = OverlappingSegment.createFromList(segments);
        }

        public void update(long start, long end, D data) {
            ((OverlappingSegment<D>)root).update(start, end, data);
        }

        /**
         * Stabbing query
         */
        public D query(long index) {
            if (root==null) return null;
            
            if (index<root.startIndex) index = root.startIndex;
            if (index>root.endIndex) index = root.endIndex;

            D result = ((OverlappingSegment<D>)root).query(index,null);
            return result;
        }

        public abstract static class OverlappingData extends Data {

            public static final class IntervalData<O extends Object> extends OverlappingData {
        
                private Set<O> list = new TreeSet<O>();
                
        
                public IntervalData(O object) {
                    this.list.add(object);
                }
                
                public IntervalData(Set<O> list) {
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
                    Set<O> listCopy = new TreeSet<O>();
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
        }

        public static final class OverlappingSegment<D extends Data> extends Segment<D> {

            public OverlappingSegment() { }
    
            @SuppressWarnings("unchecked")
            public OverlappingSegment(long start, long end, D data) {
                this.length = ((int)(end-start));
                this.startIndex = start;
                this.endIndex = end;
                this.data = ((D)data.copy());
            }

            protected static <D extends Data> Segment<D> createFromList(List<OverlappingSegment<D>> list) {
                OverlappingSegment<D> segment = new OverlappingSegment<D>();

                //Define the min and max points
                Segment<D> start = list.get(0);
                Segment<D> end = list.get(list.size()-1);
                segment.startIndex = start.startIndex-1;
                segment.endIndex = end.endIndex+1;
                segment.length = ((int)(segment.endIndex-segment.startIndex))+1;

                //Create the whole tree
                createTree(segment);
                addSegments(segment,list);

                return segment;
            }

            @SuppressWarnings("unchecked")
            private static <D extends Data> void createTree(Segment<D> segment) {
                if (segment.length >= 2) {
                    segment.half = segment.length / 2;
                    OverlappingSegment<D> sub1 = new OverlappingSegment<D>();
                    sub1.length = segment.half;
                    sub1.startIndex = segment.startIndex;
                    if (sub1.length>1) {
                        sub1.endIndex = segment.startIndex + (segment.half-1);
                    } else {
                        sub1.endIndex = segment.startIndex;
                    }
                    OverlappingSegment<D> sub2 = new OverlappingSegment<D>();
                    sub2.length = segment.length-sub1.length;
                    sub2.startIndex = segment.startIndex + segment.half;
                    if (sub2.length>1) {
                        sub2.startIndex = segment.startIndex + segment.half;
                        sub2.endIndex = segment.endIndex;
                    } else {
                        sub2.startIndex = segment.startIndex + 1;
                        sub2.endIndex = sub2.startIndex;
                    }
                    createTree(sub1);
                    createTree(sub2);
                    segment.segments = new Segment[] { sub1, sub2 };
                } else {
                    segment.segments = new Segment[] { segment };
                }
            }

            private static <D extends Data> void addSegments(OverlappingSegment<D> segment, List<OverlappingSegment<D>> list) {
                for (OverlappingSegment<D> s : list) {
                    segment.update(s.startIndex, s.endIndex, s.data);
                }
            }

            @SuppressWarnings("unchecked")
            public void update(long start, long end, D data) {
                if (start==this.startIndex && end==this.endIndex) {
                    if (this.data==null) this.data = ((D)data.copy());
                    this.data.combined(data);
                } else {
                    long middleIndex = this.startIndex+this.half;
                    if (start<middleIndex && end>=middleIndex) {
                        //Split the segment across the middle sector
                        OverlappingSegment<D> s1 = new OverlappingSegment<D>(start,middleIndex-1,data);
                        OverlappingSegment<D> s2 = new OverlappingSegment<D>(middleIndex,end,data);
                        List<OverlappingSegment<D>> newList = new ArrayList<OverlappingSegment<D>>();
                        newList.add(s1);
                        newList.add(s2);
                        addSegments(this,newList);
                    } else if (end<middleIndex) {
                        ((OverlappingSegment<D>)this.segments[0]).update(start, end, data);
                    } else {
                        ((OverlappingSegment<D>)this.segments[1]).update(start, end, data);
                    }
                }
            }

            @SuppressWarnings("unchecked")
            public D query(long index, D result) {
                if (this.data!=null && index>=this.startIndex && index<=this.endIndex) {
                    if (result==null) result = ((D)this.data.copy());
                    else result.combined(data);
                }
                
                if (this.segments.length==1) return result;

                long middleIndex = this.startIndex+this.half;
                if (index<middleIndex) {
                    result = ((OverlappingSegment<D>) this.segments[0]).query(index,result);
                } else {
                    result = ((OverlappingSegment<D>)this.segments[1]).query(index,result);
                }
                return result;
            }
    
            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("startIndex=").append(startIndex).append("->");
                builder.append("endIndex=").append(endIndex).append(" ");
                builder.append("Length=").append(length).append("\n");
                builder.append("Data=").append(data).append("\n");
                builder.append("\n");
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
