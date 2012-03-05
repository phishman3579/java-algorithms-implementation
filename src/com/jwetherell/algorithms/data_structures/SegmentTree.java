package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

/*
 * Segment tree using objects and pointers
 */
public class SegmentTree {

	private Segment root = null;

	public SegmentTree(Segment[] segments) {
		root = new Segment(segments);
	}

	public void update(long index, long quad1, long quad2, long quad3, long quad4) {
		root.update(index, quad1, quad2, quad3, quad4);
	}

	public Query query(int startIndex, int endIndex) {
		return root.query(startIndex, endIndex);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(root.toString());
		return builder.toString();
	}

	public static final class Query {
		public long quad1 = 0;
		public long quad2 = 0;
		public long quad3 = 0;
		public long quad4 = 0;

		public Query(long quad1, long quad2, long quad3, long quad4) {
			this.quad1 = quad1;
			this.quad2 = quad2;
			this.quad3 = quad3;
			this.quad4 = quad4;
		}

		public Query add(Query q) {
			this.add(q.quad1,q.quad2,q.quad3,q.quad4);
			return this;
		}

		private void add(long quad1, long quad2, long quad3, long quad4) {
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

		protected long quad1 = 0;
		protected long quad2 = 0;
		protected long quad3 = 0;
		protected long quad4 = 0;

		protected Segment() { }

		public Segment(long index, long quad1, long quad2, long quad3, long quad4) {
			this.length = 1;
			this.startIndex = index;
			this.endIndex = index;
			this.quad1 = quad1;
			this.quad2 = quad2;
			this.quad3 = quad3;
			this.quad4 = quad4;
		}

		protected Segment(Segment[] segments) {
			this.length = segments.length;
			this.startIndex = segments[0].startIndex;
			this.endIndex = segments[length-1].endIndex;

			Arrays.sort(segments);
			initialize(segments);

			if (length>=2) {
				half = length/2;
				if (length>1 && length%2!=0) half++;
				Segment[] s1 = new Segment[half];
				for (int i=0; i<half; i++) {
					Segment s = segments[i];
					s1[i] = s;
				}
				Segment sub1 = new Segment(s1);
				Segment[] s2 = new Segment[length-half];
				for (int i=half; i<length; i++) {
					Segment s = segments[i];
					s2[i-half] = s;
				}
				Segment sub2 = new Segment(s2);
				this.segments = new Segment[]{sub1,sub2};
			} else {
				this.segments = new Segment[]{this};
			}
		}

		protected void initialize(Segment[] segments) {
			this.segments = segments;
			for (Segment s : segments) {
				quad1 += s.quad1;
				quad2 += s.quad2;
				quad3 += s.quad3;
				quad4 += s.quad4;
			}
		}

		public void update(long index, long quad1, long quad2, long quad3, long quad4) {
			this.quad1 += quad1;
			this.quad2 += quad2;
			this.quad3 += quad3;
			this.quad4 += quad4;
			if (length>=2) {
				if (index<startIndex+half) {
					segments[0].update(index, quad1, quad2, quad3, quad4);
				} else if (index>=startIndex+half) {
					segments[1].update(index, quad1, quad2, quad3, quad4);
				}
			}
		}

		public Query query(long startIndex, long endIndex) {
			if (this.startIndex==startIndex && this.endIndex==endIndex) {
				return new Query(quad1,quad2,quad3,quad4);
			} else if (startIndex <= segments[0].endIndex && endIndex > segments[0].endIndex) {
				Query q1 = segments[0].query(startIndex, segments[0].endIndex);
				Query q2 = segments[1].query(segments[1].startIndex, endIndex);
				return q1.add(q2);
			} else if (startIndex <= segments[0].endIndex && endIndex <= segments[0].endIndex) {
				return segments[0].query(startIndex, endIndex);
			}
			return segments[1].query(startIndex, endIndex);
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("startIndex=").append(startIndex).append("->");
			builder.append("endIndex=").append(endIndex).append(" ");
			builder.append("q1=").append(quad1).append(",");
			builder.append("q2=").append(quad2).append(",");
			builder.append("q3=").append(quad3).append(",");
			builder.append("q4=").append(quad4).append("\n");
			return builder.toString();
		}

		public int compareTo(Segment p) {
			if (this.endIndex<p.startIndex) return -1;
			if (this.startIndex>p.endIndex) return 1;
			return 0;
		}
	}
}
