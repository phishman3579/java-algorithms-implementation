package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A quadtree is a tree data structure in which each internal node has exactly four children. Quadtrees 
 * are most often used to partition a two dimensional space by recursively subdividing it into four 
 * quadrants or regions. The regions may be square or rectangular, or may have arbitrary shapes.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Quadtree">QuadTree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public abstract class QuadTree<G extends QuadTree.XYPoint> {

    /**
     * Get the root node.
     * 
     * @return Root QuadNode.
     */
    protected abstract QuadNode<G> getRoot();

    /**
     * Range query of the quadtree.
     */
    public abstract Collection<G> queryRange(double x, double y, double width, double height);

    /**
     * Insert point at X,Y into tree.
     * 
     * @param x X position of point.
     * @param y Y position of point.
     */
    public abstract boolean insert(double x, double y);

    /**
     * Remove point at X,Y from tree.
     * 
     * @param x X position of point.
     * @param y Y position of point.
     */
    public abstract boolean remove(double x, double y);

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    /**
     * A PR (Point Region) Quadtree is a four-way search trie. This means that each node has either 
     * four (internal guide node) or zero (leaf node) children. Keys are only stored in the leaf nodes, 
     * all internal nodes act as guides towards the keys.
     * 
     * This implementation is a PR QuadTree which uses "Buckets" to prevent stalky trees.
     */
    public static class PointRegionQuadTree<P extends QuadTree.XYPoint> extends QuadTree<P> {

        private static final XYPoint XY_POINT = new XYPoint();
        private static final AxisAlignedBoundingBox RANGE = new AxisAlignedBoundingBox();

        private PointRegionQuadNode<P> root = null;

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width. This uses a default leafCapacity of 4 and a maxTreeHeight of 20.
         *
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param width Width of the bounding box containing all points
         * @param height Height of the bounding box containing all points
         */
        public PointRegionQuadTree(double x, double y, double width, double height) {
            this(x,y,width,height,4,20);
        }

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width.
         * 
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param width Width of the bounding box containing all points
         * @param height Height of the bounding box containing all points
         * @param leafCapacity Max capacity of leaf nodes. (Note: All data is stored in leaf nodes)
         */
        public PointRegionQuadTree(double x, double y, double width, double height, int leafCapacity) {
            this(x,y,width,height,leafCapacity,20);
        }

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width.
         * 
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param width Width of the bounding box containing all points
         * @param height Height of the bounding box containing all points
         * @param leafCapacity Max capacity of leaf nodes. (Note: All data is stored in leaf nodes)
         * @param maxTreeHeight Max height of the quadtree. (Note: If this is defined, the tree will ignore the 
         *                                                   max capacity defined by leafCapacity)
         */
        public PointRegionQuadTree(double x, double y, double width, double height, int leafCapacity, int maxTreeHeight) {
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox(xyPoint,width,height);
            PointRegionQuadNode.maxCapacity = leafCapacity;
            PointRegionQuadNode.maxHeight = maxTreeHeight;
            root = new PointRegionQuadNode<P>(aabb);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public QuadTree.QuadNode<P> getRoot() {
            return root;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean insert(double x, double y) {
            XYPoint xyPoint = new XYPoint(x,y);

            return root.insert((P)xyPoint);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(double x, double y) {
            XY_POINT.set(x,y);

            return root.remove((P)XY_POINT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<P> queryRange(double x, double y, double width, double height) {
            if (root == null) 
                return Collections.EMPTY_LIST;

            XY_POINT.set(x,y);
            RANGE.set(XY_POINT,width,height);

            List<P> pointsInRange = new LinkedList<P>();
            root.queryRange(RANGE,pointsInRange);
            return pointsInRange;
        }

        protected static class PointRegionQuadNode<XY extends QuadTree.XYPoint> extends QuadNode<XY> {

            // max number of children before sub-dividing
            protected static int maxCapacity = 0;
            // max height of the tree (will over-ride maxCapacity when height==maxHeight)
            protected static int maxHeight = 0;

            protected List<XY> points = new LinkedList<XY>();
            protected int height = 1;

            protected PointRegionQuadNode(AxisAlignedBoundingBox aabb) {
                super(aabb);
            }

            /**
             * {@inheritDoc}
             * 
             * returns True if inserted.
             * returns False if not in bounds of tree OR tree already contains point.
             */
            @Override
            protected boolean insert(XY p) {
                // Ignore objects which do not belong in this quad tree
                if (!aabb.containsPoint(p) || (isLeaf() && points.contains(p))) 
                    return false; // object cannot be added

                // If there is space in this quad tree, add the object here
                if ((height==maxHeight) || (isLeaf() && points.size() < maxCapacity)) {
                    points.add(p);
                    return true;
                }

                // Otherwise, we need to subdivide then add the point to whichever node will accept it
                if (isLeaf() && height<maxHeight) 
                    subdivide();
                return insertIntoChildren(p);
            }

            /**
             * {@inheritDoc}
             * 
             * This method will merge children into self if it can without overflowing the maxCapacity param.
             */
            @Override
            protected boolean remove(XY p) {
                // If not in this AABB, don't do anything
                if (!aabb.containsPoint(p)) 
                    return false;

                // If in this AABB and in this node
                if (points.remove(p)) 
                    return true;

                // If this node has children
                if (!isLeaf()) {
                    // If in this AABB but in a child branch
                    boolean removed = removeFromChildren(p);
                    if (!removed) 
                        return false;
    
                    // Try to merge children
                    merge();
    
                    return true;
                }

                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected int size() {
                return points.size();
            }

            private void subdivide() {
                double h = aabb.height/2d;
                double w = aabb.width/2d;

                AxisAlignedBoundingBox aabbNW = new AxisAlignedBoundingBox(aabb,w,h);
                northWest = new PointRegionQuadNode<XY>(aabbNW);
                ((PointRegionQuadNode<XY>)northWest).height = height+1;

                XYPoint xyNE = new XYPoint(aabb.x+w,aabb.y);
                AxisAlignedBoundingBox aabbNE = new AxisAlignedBoundingBox(xyNE,w,h);
                northEast = new PointRegionQuadNode<XY>(aabbNE);
                ((PointRegionQuadNode<XY>)northEast).height = height+1;

                XYPoint xySW = new XYPoint(aabb.x,aabb.y+h);
                AxisAlignedBoundingBox aabbSW = new AxisAlignedBoundingBox(xySW,w,h);
                southWest = new PointRegionQuadNode<XY>(aabbSW);
                ((PointRegionQuadNode<XY>)southWest).height = height+1;

                XYPoint xySE = new XYPoint(aabb.x+w,aabb.y+h);
                AxisAlignedBoundingBox aabbSE = new AxisAlignedBoundingBox(xySE,w,h);
                southEast = new PointRegionQuadNode<XY>(aabbSE);
                ((PointRegionQuadNode<XY>)southEast).height = height+1;

                // points live in leaf nodes, so distribute
                for (XY p : points)
                    insertIntoChildren(p);
                points.clear();
            }

            private void merge() {
                // If the children aren't leafs, you cannot merge
                if (!northWest.isLeaf() || !northEast.isLeaf() || !southWest.isLeaf() || !southEast.isLeaf()) 
                    return;

                // Children and leafs, see if you can remove point and merge into this node
                int nw = northWest.size();
                int ne = northEast.size();
                int sw = southWest.size();
                int se = southEast.size();
                int total = nw+ne+sw+se;

                // If all the children's point can be merged into this node
                if ((size()+total) < maxCapacity) {
                    this.points.addAll(((PointRegionQuadNode<XY>)northWest).points);
                    this.points.addAll(((PointRegionQuadNode<XY>)northEast).points);
                    this.points.addAll(((PointRegionQuadNode<XY>)southWest).points);
                    this.points.addAll(((PointRegionQuadNode<XY>)southEast).points);

                    this.northWest = null;
                    this.northEast = null;
                    this.southWest = null;
                    this.southEast = null;
                }
            }

            private boolean insertIntoChildren(XY p) {
                // A point can only live in one child.
                if (northWest.insert(p)) return true;
                if (northEast.insert(p)) return true;
                if (southWest.insert(p)) return true;
                if (southEast.insert(p)) return true;
                return false; // should never happen
            }

            private boolean removeFromChildren(XY p) {
                // A point can only live in one child.
                if (northWest.remove(p)) return true;
                if (northEast.remove(p)) return true;
                if (southWest.remove(p)) return true;
                if (southEast.remove(p)) return true;
                return false; // should never happen
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected void queryRange(AxisAlignedBoundingBox range, List<XY> pointsInRange) {
                // Automatically abort if the range does not collide with this quad
                if (!aabb.intersectsBox(range)) 
                    return;

                // If leaf, check objects at this level
                if (isLeaf()) {
                    for (XY xyPoint : points) {
                        if (range.containsPoint(xyPoint)) 
                            pointsInRange.add(xyPoint);
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
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(", ");
                builder.append("[");
                for (XYPoint p : points) {
                    builder.append(p).append(", ");
                }
                builder.append("]");
                return builder.toString();
            }
        }
    }

    /**
     * MX-CIF quadtree is a variant of quadtree data structure which supports area-based query. It is designed for storing a
     * set of rectangles (axis-aligned bounded box) in a dynamic environment.
     */
    public static class MxCifQuadTree<B extends QuadTree.AxisAlignedBoundingBox> extends QuadTree<B> {

        private static final XYPoint XY_POINT = new XYPoint();
        private static final AxisAlignedBoundingBox RANGE = new AxisAlignedBoundingBox();

        private MxCifQuadNode<B> root = null;

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width. This uses a default leafCapacity of 4 and a maxTreeHeight of 20.
         *
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param width Width of the bounding box containing all points
         * @param height Height of the bounding box containing all points
         */
        public MxCifQuadTree(double x, double y, double width, double height) {
            this(x,y,width,height,0,0);
        }

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width. This uses a default leafCapacity of 4 and a maxTreeHeight of 20.
         *
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param width Width of the bounding box containing all points
         * @param height Height of the bounding box containing all points
         * @param minWidth The tree will stop splitting when leaf node's width <= minWidth
         * @param minHeight The tree will stop splitting when leaf node's height <= minHeight
         */
        public MxCifQuadTree(double x, double y, double width, double height, double minWidth, double minHeight) {
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox(xyPoint,width,height);
            MxCifQuadNode.minWidth = minWidth;
            MxCifQuadNode.minHeight = minHeight;
            root = new MxCifQuadNode<B>(aabb);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public QuadTree.QuadNode<B> getRoot() {
            return root;
        }

        /**
         * {@inheritDoc}
         * 
         * Assumes height and width of 1
         */
        @Override
        public boolean insert(double x, double y) {
            return insert(x,y,1,1);
        }

        /**
         * Insert rectangle whose upper-left point is located at X,Y and has a height and width into tree.
         * 
         * @param x X position of upper-left hand corner.
         * @param y Y position of upper-left hand corner.
         * @param width Width of the rectangle.
         * @param height Height of the rectangle.
         */
        public boolean insert(double x, double y, double width, double height) {
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox range = new AxisAlignedBoundingBox(xyPoint,width,height);

            return root.insert((B)range);
        }

        /**
         * {@inheritDoc}
         * 
         * Assumes height and width of 1
         */
        @Override
        public boolean remove(double x, double y) {
            return remove(x,y,1,1);
        }

        /**
         * Remove rectangle whose upper-left point is located at X,Y and has a height and width into tree.
         * 
         * @param x X position of upper-left hand corner.
         * @param y Y position of upper-left hand corner.
         * @param width Width of the rectangle.
         * @param height Height of the rectangle.
         */
        public boolean remove(double x, double y, double width, double height) {
            XY_POINT.set(x,y);
            RANGE.set(XY_POINT,width,height);

            return root.remove((B)RANGE);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<B> queryRange(double x, double y, double width, double height) {
            if (root == null) 
                return Collections.EMPTY_LIST;

            XY_POINT.set(x,y);
            RANGE.set(XY_POINT,width,height);

            List<B> geometricObjectsInRange = new LinkedList<B>();
            root.queryRange(RANGE,geometricObjectsInRange);
            return geometricObjectsInRange;
        }

        protected static class MxCifQuadNode<AABB extends QuadTree.AxisAlignedBoundingBox> extends QuadNode<AABB> {

            protected static double minWidth = 1;
            protected static double minHeight = 1;

            protected List<AABB> aabbs = new LinkedList<AABB>();

            protected MxCifQuadNode(AxisAlignedBoundingBox aabb) {
                super(aabb);
            }

            /**
             * {@inheritDoc}
             * 
             * returns True if inserted or already contains.
             */
            @Override
            protected boolean insert(AABB b) {
                // Ignore objects which do not belong in this quad tree
                if (!aabb.intersectsBox(b)) 
                    return false; // object cannot be added
                if (aabbs.contains(b)) 
                    return true; // already exists

                // Subdivide then add the objects to whichever node will accept it
                if (isLeaf()) 
                    subdivide(b);

                boolean inserted = false;
                if (isLeaf()) {
                    aabbs.add(b);
                    inserted = true;
                } else {                
                    inserted = insertIntoChildren(b);
                }

                if (!inserted) {
                    // Couldn't insert into children (it could strattle the bounds of the box)
                    aabbs.add(b);
                    return true;
                }
                return true;
            }

            /**
             * {@inheritDoc}
             * 
             * This method does not merge children.
             */
            @Override
            protected boolean remove(AABB b) {
                // If not in this AABB, don't do anything
                if (!aabb.intersectsBox(b)) 
                    return false;

                // If in this AABB and in this node
                if (aabbs.remove(b)) 
                    return true;

                // If this node has children
                if (!isLeaf()) {
                    // If in this AABB but in a child branch
                    return removeFromChildren(b);
                }

                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected int size() {
                return aabbs.size();
            }

            private boolean subdivide(AABB b) {
                double w = aabb.width/2d;
                double h = aabb.height/2d;
                if (w<minWidth || h<minHeight) return false;

                AxisAlignedBoundingBox aabbNW = new AxisAlignedBoundingBox(aabb,w,h);
                northWest = new MxCifQuadNode<AABB>(aabbNW);

                XYPoint xyNE = new XYPoint(aabb.x+w,aabb.y);
                AxisAlignedBoundingBox aabbNE = new AxisAlignedBoundingBox(xyNE,w,h);
                northEast = new MxCifQuadNode<AABB>(aabbNE);

                XYPoint xySW = new XYPoint(aabb.x,aabb.y+h);
                AxisAlignedBoundingBox aabbSW = new AxisAlignedBoundingBox(xySW,w,h);
                southWest = new MxCifQuadNode<AABB>(aabbSW);

                XYPoint xySE = new XYPoint(aabb.x+w,aabb.y+h);
                AxisAlignedBoundingBox aabbSE = new AxisAlignedBoundingBox(xySE,w,h);
                southEast = new MxCifQuadNode<AABB>(aabbSE);

                return insertIntoChildren(b);
            }

            private boolean insertIntoChildren(AABB b) {
                //Try to insert into all children
                if (northWest.aabb.insideThis(b) && northWest.insert(b)) return true;
                if (northEast.aabb.insideThis(b) && northEast.insert(b)) return true;
                if (southWest.aabb.insideThis(b) && southWest.insert(b)) return true;
                if (southEast.aabb.insideThis(b) && southEast.insert(b)) return true;
                return false;
            }

            private boolean removeFromChildren(AABB b) {
                // A AABB can only live in one child.
                if (northWest.remove(b)) return true;
                if (northEast.remove(b)) return true;
                if (southWest.remove(b)) return true;
                if (southEast.remove(b)) return true;
                return false; // should never happen
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected void queryRange(AxisAlignedBoundingBox range, List<AABB> geometricObjectsInRange) {
                // Automatically abort if the range does not collide with this quad
                if (!aabb.intersectsBox(range)) 
                    return;

                // Check objects at this level
                for (AABB b : aabbs) {
                    if (range.intersectsBox(b)) 
                        geometricObjectsInRange.add(b);
                }

                // Otherwise, add the objects from the children
                if (!isLeaf()) {
                    northWest.queryRange(range,geometricObjectsInRange);
                    northEast.queryRange(range,geometricObjectsInRange);
                    southWest.queryRange(range,geometricObjectsInRange);
                    southEast.queryRange(range,geometricObjectsInRange);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append(super.toString()).append(", ");
                builder.append("[");
                for (AABB p : aabbs)
                    builder.append(p).append(", ");
                builder.append("]");
                return builder.toString();
            }
        }
    }

    protected static abstract class QuadNode<G extends QuadTree.XYPoint> implements Comparable<QuadNode<G>> {

        protected final AxisAlignedBoundingBox aabb;

        protected QuadNode<G> northWest = null;
        protected QuadNode<G> northEast = null;
        protected QuadNode<G> southWest = null;
        protected QuadNode<G> southEast = null;

        protected QuadNode(AxisAlignedBoundingBox aabb) {
            this.aabb = aabb;
        }

        /**
         * Insert object into tree.
         * 
         * @param g Geometric object to insert into tree.
         * @return True if successfully inserted.
         */
        protected abstract boolean insert(G g);

        /**
         * Remove object from tree.
         * 
         * @param g Geometric object to remove from tree.
         * @return True if successfully removed.
         */
        protected abstract boolean remove(G g);

        /**
         * How many GeometricObjects this node contains.
         * 
         * @return Number of GeometricObjects this node contains.
         */
        protected abstract int size();

        /**
         * Find all objects which appear within a range.
         * 
         * @param range Upper-left and width,height of a axis-aligned bounding box.
         * @param geometricObjectsInRange Geometric objects inside the bounding box. 
         */
        protected abstract void queryRange(AxisAlignedBoundingBox range, List<G> geometricObjectsInRange);

        /**
         * Is current node a leaf node.
         * @return True if node is a leaf node.
         */
        protected boolean isLeaf() {
            return (northWest==null && northEast==null && southWest==null && southEast==null);
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

            QuadNode<G> qNode = (QuadNode<G>) obj;
            if (this.compareTo(qNode) == 0)
                return true;

            return false;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("rawtypes")
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
            builder.append(aabb.toString());
            return builder.toString();
        }
    }

    public static class XYPoint implements Comparable<Object> {

        protected double x = Float.MIN_VALUE;
        protected double y = Float.MIN_VALUE;

        public XYPoint() { }

        public XYPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void set(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
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
        public int compareTo(Object o) {
            if ((o instanceof XYPoint)==false)
                throw new RuntimeException("Cannot compare object.");

            XYPoint p = (XYPoint) o;
            int xComp = X_COMPARATOR.compare(this, p);
            if (xComp != 0) 
                return xComp;
            return Y_COMPARATOR.compare(this, p);
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

    public static class AxisAlignedBoundingBox extends XYPoint {

        private double height = 0;
        private double width = 0;

        private double minX = 0;
        private double minY = 0;
        private double maxX = 0;
        private double maxY = 0;

        public AxisAlignedBoundingBox() { }

        public AxisAlignedBoundingBox(XYPoint upperLeft, double width, double height) {
            super(upperLeft.x, upperLeft.y);
            this.width = width;
            this.height = height;

            minX = upperLeft.x;
            minY = upperLeft.y;
            maxX = upperLeft.x+width;
            maxY = upperLeft.y+height;
        }

        public void set(XYPoint upperLeft, double width, double height) {
            set(upperLeft.x, upperLeft.y);
            this.width = width;
            this.height = height;

            minX = upperLeft.x;
            minY = upperLeft.y;
            maxX = upperLeft.x+width;
            maxY = upperLeft.y+height;
        }

        public double getHeight() {
            return height;
        }
        public double getWidth() {
            return width;
        }

        public boolean containsPoint(XYPoint p) {
            if (p.x>=maxX) return false;
            if (p.x<minX) return false;
            if (p.y>=maxY) return false;
            if (p.y<minY) return false;
            return true;
        }

        /**
         * Is the inputted AxisAlignedBoundingBox completely inside this AxisAlignedBoundingBox.
         * 
         * @param b AxisAlignedBoundingBox to test.
         * @return True if the AxisAlignedBoundingBox is completely inside this AxisAlignedBoundingBox.
         */
        public boolean insideThis(AxisAlignedBoundingBox b) {
            if (b.minX >= minX && b.maxX <= maxX && b.minY >= minY && b.maxY <= maxY) {
                // INSIDE
                return true;
            }
            return false;
        }

        /**
         * Is the inputted AxisAlignedBoundingBox intersecting this AxisAlignedBoundingBox.
         * 
         * @param b AxisAlignedBoundingBox to test.
         * @return True if the AxisAlignedBoundingBox is intersecting this AxisAlignedBoundingBox.
         */
        public boolean intersectsBox(AxisAlignedBoundingBox b) {
            if (insideThis(b) || b.insideThis(this)) {
                // INSIDE
                return true;
            }

            // OUTSIDE
            if (maxX < b.minX || minX > b.maxX) return false;
            if (maxY < b.minY || minY > b.maxY) return false;

            // INTERSECTS
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = super.hashCode();
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
        public int compareTo(Object o) {
            if ((o instanceof AxisAlignedBoundingBox)==false)
                throw new RuntimeException("Cannot compare object.");

            AxisAlignedBoundingBox a = (AxisAlignedBoundingBox) o;
            int p = super.compareTo(a);
            if (p!=0) return p;

            if (height>a.height) return 1;
            if (height<a.height) return -1;

            if (width>a.width) return 1;
            if (width<a.width) return -1;

            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(super.toString()).append(", ");
            builder.append("height").append("=").append(height).append(", ");
            builder.append("width").append("=").append(width);
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
            if (tree.getRoot() == null) return "Tree has no nodes.";
            return getString(tree.getRoot(), "", true);
        }

        private static <T extends XYPoint> String getString(QuadNode<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + " node={" + node.toString() + "}\n");
            List<QuadNode<T>> children = null;
            if (node.northWest != null || node.northEast != null || node.southWest != null || node.southEast != null) {
                children = new ArrayList<QuadNode<T>>(4);
                if (node.northWest != null) children.add(node.northWest);
                if (node.northEast != null) children.add(node.northEast);
                if (node.southWest != null) children.add(node.southWest);
                if (node.southEast != null) children.add(node.southEast);
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
