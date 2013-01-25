package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A quadtree is a tree data structure in which each internal node has exactly four children. Quadtrees 
 * are most often used to partition a two dimensional space by recursively subdividing it into four 
 * quadrants or regions. The regions may be square or rectangular, or may have arbitrary shapes.
 * 
 * http://en.wikipedia.org/wiki/Quadtree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class QuadTree<T extends QuadTree.XYPoint> {

    private QuadNode root = null;

    /**
     * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
     * by the height and width. This uses a default leafCapacity of 4 and a maxTreeHeight of 20.
     *
     * @param x Upper left X coordinate
     * @param y Upper left Y coordinate
     * @param height Height of the bounding box containing all points
     * @param width Width of the bounding box containing all points
     */
    public QuadTree(double x, double y, double height, double width) {
        this(x,y,height,width,4,20);
    }

    /**
     * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
     * by the height and width.
     * 
     * @param x Upper left X coordinate
     * @param y Upper left Y coordinate
     * @param height Height of the bounding box containing all points
     * @param width Width of the bounding box containing all points
     * @param leafCapacity Max capacity of leaf nodes. (Note: All data is stored in leaf nodes)
     * @param maxTreeHeight Max height of the quadtree. (Note: If this is defined, the tree will ignore the 
     *                                                   max capacity defined by leafCapacity)
     */
    public QuadTree(double x, double y, double height, double width, int leafCapacity, int maxTreeHeight) {
        XYPoint xyPoint = new XYPoint(x,y);
        AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox(xyPoint,height,width);
        this.root = new QuadNode(aabb);
        QuadNode.maxCapacity = leafCapacity;
        QuadNode.maxHeight = maxTreeHeight;
    }

    // insert point into tree
    public void insert(double x, double y) {
        XYPoint xyPoint = new XYPoint(x,y);
        root.insert(xyPoint);
    }

    // Find all points which appear within a range
    public List<XYPoint> queryRange(double x, double y, double height, double width) {
        List<XYPoint> pointsInRange = new LinkedList<XYPoint>();
        if (root==null) return pointsInRange; 
        XYPoint xyPoint = new XYPoint(x,y);
        AxisAlignedBoundingBox range = new AxisAlignedBoundingBox(xyPoint,height,width);
        root.queryRange(range,pointsInRange);
        return pointsInRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    private static class QuadNode implements Comparable<QuadNode> {

        private static int maxCapacity = 0;
        private static int maxHeight = 0;
        
        private List<XYPoint> points = new LinkedList<XYPoint>();
        private int height = 1;
        private AxisAlignedBoundingBox aabb = null;
        private QuadNode northWest = null;
        private QuadNode northEast = null;
        private QuadNode southWest = null;
        private QuadNode southEast = null;

        private QuadNode(AxisAlignedBoundingBox aabb) {
            this.aabb = aabb;
        }

        private boolean insert(XYPoint p) {
            // Ignore objects which do not belong in this quad tree
            if (!aabb.containsPoint(p) || (isLeaf() && points.contains(p))) return false; // object cannot be added

            // If there is space in this quad tree, add the object here
            if ((height==maxHeight) || (isLeaf() && points.size() < maxCapacity)) {
                points.add(p);
                return true;
            }

            // Otherwise, we need to subdivide then add the point to whichever node will accept it
            if (isLeaf() && height<maxHeight) subdivide();
            insertIntoChildren(p);

            // Otherwise, the point cannot be inserted for some unknown reason (which should never happen)
            return false;
        }

        private boolean isLeaf() {
            return (northWest==null);
        }

        private void subdivide() {
            double h = aabb.height/2;
            double w = aabb.width/2;

            AxisAlignedBoundingBox aabbNW = new AxisAlignedBoundingBox(aabb.upperLeft,h,w);
            northWest = new QuadNode(aabbNW);
            northWest.height = height+1;

            XYPoint xyNE = new XYPoint(aabb.upperLeft.x+w,aabb.upperLeft.y);
            AxisAlignedBoundingBox aabbNE = new AxisAlignedBoundingBox(xyNE,h,w);
            northEast = new QuadNode(aabbNE);
            northEast.height = height+1;

            XYPoint xySW = new XYPoint(aabb.upperLeft.x,aabb.upperLeft.y+h);
            AxisAlignedBoundingBox aabbSW = new AxisAlignedBoundingBox(xySW,h,w);
            southWest = new QuadNode(aabbSW);
            southWest.height = height+1;

            XYPoint xySE = new XYPoint(aabb.upperLeft.x+w,aabb.upperLeft.y+h);
            AxisAlignedBoundingBox aabbSE = new AxisAlignedBoundingBox(xySE,h,w);
            southEast = new QuadNode(aabbSE);
            southEast.height = height+1;

            // points live in leaf nodes, so distribute
            for (XYPoint p : points) {
                insertIntoChildren(p);
            }
            points.clear();
        }

        private boolean insertIntoChildren(XYPoint p) {
            if (northWest.insert(p)) return true;
            if (northEast.insert(p)) return true;
            if (southWest.insert(p)) return true;
            if (southEast.insert(p)) return true;
            return false; // should never happen
        }

        // Find all points which appear within a range
        private void queryRange(AxisAlignedBoundingBox range, List<XYPoint> pointsInRange) {
          // Automatically abort if the range does not collide with this quad
          if (!aabb.intersectsBox(range)) return;

          // If leaf, check objects at this level
          if (isLeaf()) {
              for (XYPoint xyPoint : points) {
                  if (range.containsPoint(xyPoint)) pointsInRange.add(xyPoint);
              }
              return;
          }

          // Otherwise, add the points from the children
          northWest.queryRange(range,pointsInRange);
          northEast.queryRange(range,pointsInRange);
          southWest.queryRange(range,pointsInRange);
          southEast.queryRange(range,pointsInRange);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = aabb.hashCode();
            hash = hash * 13 + ((northWest!=null)?northWest.hashCode():1);
            hash = hash * 17 + ((northEast!=null)?northEast.hashCode():1);
            hash = hash * 19 + ((southWest!=null)?southWest.hashCode():1);
            hash = hash * 23 + ((southEast!=null)?southEast.hashCode():1);
            return hash; 
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof QuadNode))
                return false;

            QuadNode qNode = (QuadNode) obj;
            if (this.compareTo(qNode) == 0)
                return true;

            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(QuadNode o) {
            return this.aabb.compareTo(o.aabb);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(aabb.toString()).append(", ");
            builder.append("[");
            for (XYPoint p : points) {
                builder.append(p).append(", ");
            }
            builder.append("]");
            return builder.toString();
        }
    }

    public static class AxisAlignedBoundingBox implements Comparable<AxisAlignedBoundingBox> {

        private XYPoint upperLeft = null;
        private double height = 0;
        private double width = 0;
        private double minX = 0;
        private double minY = 0;
        private double maxX = 0;
        private double maxY = 0;

        public AxisAlignedBoundingBox(XYPoint upperLeft, double height, double width) {
            this.upperLeft = upperLeft;
            this.height = height;
            this.width = width;

            minX = upperLeft.x;
            minY = upperLeft.y;
            maxX = upperLeft.x+width;
            maxY = upperLeft.y+height;
        }

        public boolean containsPoint(XYPoint p) {
            if (p.x>=maxX) return false;
            if (p.x<minX) return false;
            if (p.y>=maxY) return false;
            if (p.y<minY) return false;
            return true;
        }

        public boolean intersectsBox(AxisAlignedBoundingBox aabb) {
            if (aabb.minX >= minX && aabb.maxX <= maxX && aabb.minY >= minY && aabb.maxY <= maxY) {
                // INSIDE
                return true;
            }

            // OUTSIDE
            if (maxX < aabb.minX || minX > aabb.maxX) return false;
            if (maxY < aabb.minY || minY > aabb.maxY) return false;

            // INTERSECTS
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = upperLeft.hashCode();
            hash = hash * 13 + (int)height;
            hash = hash * 19 + (int)width;
            return hash; 
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof AxisAlignedBoundingBox))
                return false;

            AxisAlignedBoundingBox aabb = (AxisAlignedBoundingBox) obj;
            return compareTo(aabb) == 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(AxisAlignedBoundingBox o) {
            int p = upperLeft.compareTo(o.upperLeft);
            if (p!=0) return p;

            if (height>o.height) return 1;
            if (height<o.height) return -1;

            if (width>o.width) return 1;
            if (width<o.width) return -1;

            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(upperLeft.toString()).append(", ");
            builder.append("height").append("=").append(height).append(", ");
            builder.append("width").append("=").append(width);
            builder.append(")");
            return builder.toString();
        }
    }

    public static class XYPoint implements Comparable<XYPoint> {

        public double x = Double.NEGATIVE_INFINITY;
        public double y = Double.NEGATIVE_INFINITY;

        public XYPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = 1;
            hash = hash * 13 + (int)x;
            hash = hash * 19 + (int)y;
            return hash; 
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof XYPoint))
                return false;

            XYPoint xyzPoint = (XYPoint) obj;
            return compareTo(xyzPoint) == 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(XYPoint o) {
            int xComp = X_COMPARATOR.compare(this, o);
            if (xComp != 0) return xComp;

            return Y_COMPARATOR.compare(this, o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(x).append(", ");
            builder.append(y);
            builder.append(")");
            return builder.toString();
        }
    }

    private static final Comparator<XYPoint> X_COMPARATOR = new Comparator<XYPoint>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYPoint o1, XYPoint o2) {
            if (o1.x < o2.x)
                return -1;
            if (o1.x > o2.x)
                return 1;
            return 0;
        }
    };

    private static final Comparator<XYPoint> Y_COMPARATOR = new Comparator<XYPoint>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYPoint o1, XYPoint o2) {
            if (o1.y < o2.y)
                return -1;
            if (o1.y > o2.y)
                return 1;
            return 0;
        }
    };

    protected static class TreePrinter {

        public static <T extends XYPoint> String getString(QuadTree<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(QuadNode node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + " node={" + node.toString() + "}\n");
            List<QuadNode> children = null;
            if (node.northWest != null || node.northEast != null || node.southWest != null || node.southEast != null) {
                children = new ArrayList<QuadNode>(4);
                if (node.northWest != null)
                    children.add(node.northWest);
                if (node.northEast != null)
                    children.add(node.northEast);
                if (node.southWest != null)
                    children.add(node.southWest);
                if (node.southEast != null)
                    children.add(node.southEast);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
