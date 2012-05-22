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
 * This particular segment tree represents quadrants of points in the X/Y space.
 * Where upper right is the zeroth quadrant and bottom right represents the
 * third quadrant. You can update and query the segment tree but cannot add or
 * delete segments. This isn't a generic implementation but can easily be adapted
 * to any structure by changing the Data class.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SegmentTree {

    private Segment root = null;


    public SegmentTree(Segment[] segments) {
        root = new Segment(segments);
    }

    public void update(long index, Data data) {
        root.update(index, data);
    }

    public Data query(int startIndex, int endIndex) {
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


    public static final class Data {

        public long quad1 = 0;
        public long quad2 = 0;
        public long quad3 = 0;
        public long quad4 = 0;


        public Data() { }

        public Data(Data data) {
            this.quad1 = data.quad1;
            this.quad2 = data.quad2;
            this.quad3 = data.quad3;
            this.quad4 = data.quad4;
        }

        public Data(long quad1, long quad2, long quad3, long quad4) {
            this.quad1 = quad1;
            this.quad2 = quad2;
            this.quad3 = quad3;
            this.quad4 = quad4;
        }

        public Data update(Data q) {
            this.update(q.quad1, q.quad2, q.quad3, q.quad4);
            return this;
        }

        private void update(long quad1, long quad2, long quad3, long quad4) {
            this.quad1 += quad1;
            this.quad2 += quad2;
            this.quad3 += quad3;
            this.quad4 += quad4;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("q1=").append(quad1).append(",");
            builder.append("q2=").append(quad2).append(",");
            builder.append("q3=").append(quad3).append(",");
            builder.append("q4=").append(quad4);
            return builder.toString();
        }
    }

    public static class Segment implements Comparable<Segment> {

        protected Segment[] segments = null;
        protected int length = 0;
        protected int half = 0;

        protected long startIndex = 0;
        protected long endIndex = 0;

        protected Data data = null;


        protected Segment() { 
            this.data = new Data();
        }

        public Segment(long index, Data data) {
            this.length = 1;
            this.startIndex = index;
            this.endIndex = index;
            this.data = new Data(data); //copy constructor
        }

        protected Segment(Segment[] segments) {
            this.length = segments.length;
            this.startIndex = segments[0].startIndex;
            this.endIndex = segments[length - 1].endIndex;
            this.data = new Data();

            Arrays.sort(segments); //Make sure they are sorted
            this.segments = segments; //Keep all the segments
            for (Segment s : segments) {
                this.data.update(s.data); //Update our data to reflect all children's data
            }

            //If segment is greater or equal to two, split data into children
            if (length >= 2) {
                half = length / 2;
                if (length > 1 && length % 2 != 0) half++;
                Segment[] s1 = new Segment[half];
                for (int i = 0; i < half; i++) {
                    Segment s = segments[i];
                    s1[i] = s;
                }
                Segment sub1 = new Segment(s1);
                Segment[] s2 = new Segment[length - half];
                for (int i = half; i < length; i++) {
                    Segment s = segments[i];
                    s2[i - half] = s;
                }
                Segment sub2 = new Segment(s2);
                this.segments = new Segment[] { sub1, sub2 };
            } else {
                this.segments = new Segment[] { this };
            }
        }

        public void update(long index, Data data) {
            this.data.update(data);
            if (length >= 2) {
                if (index < startIndex + half) {
                    segments[0].update(index, data);
                } else if (index >= startIndex + half) {
                    segments[1].update(index, data);
                }
            }
        }

        public Data query(long startIndex, long endIndex) {
            if (this.startIndex == startIndex && this.endIndex == endIndex) {
                return new Data(data);
            } else if (startIndex <= segments[0].endIndex && endIndex > segments[0].endIndex) {
                Data q1 = segments[0].query(startIndex, segments[0].endIndex);
                Data q2 = segments[1].query(segments[1].startIndex, endIndex);
                return q1.update(q2);
            } else if (startIndex <= segments[0].endIndex && endIndex <= segments[0].endIndex) {
                return segments[0].query(startIndex, endIndex);
            }
            return segments[1].query(startIndex, endIndex);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("startIndex=").append(startIndex).append("->");
            builder.append("endIndex=").append(endIndex).append(" ");
            builder.append("Data=").append(data).append("\n");
            return builder.toString();
        }

        public int compareTo(Segment p) {
            if (this.endIndex < p.startIndex) return -1;
            if (this.startIndex > p.endIndex) return 1;
            return 0;
        }
    }
}
