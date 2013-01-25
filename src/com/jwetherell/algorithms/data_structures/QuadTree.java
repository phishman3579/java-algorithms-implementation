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
public abstract class QuadTree<G extends QuadTree.GeometricObject> {

    /**
     * Get the root node.
     * 
     * @return Root QuadNode.
     */
    protected abstract QuadNode<G> getRoot();

    /**
     * Range query of the quadtree.
     */
    public abstract List<G> queryRange(float x, float y, float height, float width);

    /**
     * QuadTree which holds 2D objects.
     */
    public static class PointQuadTree<P extends QuadTree.XYPoint> extends QuadTree<P> {

        private PointQuadNode<P> root = null;

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width. This uses a default leafCapacity of 4 and a maxTreeHeight of 20.
         *
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param height Height of the bounding box containing all points
         * @param width Width of the bounding box containing all points
         */
        public PointQuadTree(float x, float y, float height, float width) {
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
        public PointQuadTree(float x, float y, float height, float width, int leafCapacity, int maxTreeHeight) {
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox(xyPoint,height,width);
            root = new PointQuadNode<P>(aabb);
            PointQuadNode.maxCapacity = leafCapacity;
            PointQuadNode.maxHeight = maxTreeHeight;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public QuadTree.QuadNode<P> getRoot() {
            return root;
        }

        /**
         * Insert point at X,Y into tree.
         * 
         * @param x X position of point.
         * @param y Y position of point.
         */
        @SuppressWarnings("unchecked")
        public void insert(float x, float y) {
            XYPoint xyPoint = new XYPoint(x,y);
            root.insert((P)xyPoint);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<P> queryRange(float x, float y, float height, float width) {
            List<P> pointsInRange = new LinkedList<P>();
            if (root==null) return (List<P>)pointsInRange; 
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox range = new AxisAlignedBoundingBox(xyPoint,height,width);
            root.queryRange(range,pointsInRange);
            return (List<P>)pointsInRange;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return super.toString();
        }

        protected static class PointQuadNode<XY extends QuadTree.XYPoint> extends QuadNode<XY> {

            // max number of children before sub-dividing
            protected static int maxCapacity = 0;
            // max height of the tree (will over-ride maxCapacity when height==maxHeight)
            protected static int maxHeight = 0;

            protected List<XY> points = new LinkedList<XY>();
            protected int height = 1;

            protected PointQuadNode(AxisAlignedBoundingBox aabb) {
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

            private void subdivide() {
                float h = aabb.height/2;
                float w = aabb.width/2;

                AxisAlignedBoundingBox aabbNW = new AxisAlignedBoundingBox(aabb.upperLeft,h,w);
                northWest = new PointQuadNode<XY>(aabbNW);
                ((PointQuadNode<XY>)northWest).height = height+1;

                XYPoint xyNE = new XYPoint(aabb.upperLeft.x+w,aabb.upperLeft.y);
                AxisAlignedBoundingBox aabbNE = new AxisAlignedBoundingBox(xyNE,h,w);
                northEast = new PointQuadNode<XY>(aabbNE);
                ((PointQuadNode<XY>)northEast).height = height+1;

                XYPoint xySW = new XYPoint(aabb.upperLeft.x,aabb.upperLeft.y+h);
                AxisAlignedBoundingBox aabbSW = new AxisAlignedBoundingBox(xySW,h,w);
                southWest = new PointQuadNode<XY>(aabbSW);
                ((PointQuadNode<XY>)southWest).height = height+1;

                XYPoint xySE = new XYPoint(aabb.upperLeft.x+w,aabb.upperLeft.y+h);
                AxisAlignedBoundingBox aabbSE = new AxisAlignedBoundingBox(xySE,h,w);
                southEast = new PointQuadNode<XY>(aabbSE);
                ((PointQuadNode<XY>)southEast).height = height+1;

                // points live in leaf nodes, so distribute
                for (XY p : points) {
                    insertIntoChildren(p);
                }
                points.clear();
            }

            private boolean insertIntoChildren(XY p) {
                // A point can only live in one child.
                if (northWest.insert(p)) return true;
                if (northEast.insert(p)) return true;
                if (southWest.insert(p)) return true;
                if (southEast.insert(p)) return true;
                return false; // should never happen
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected void queryRange(AxisAlignedBoundingBox range, List<XY> pointsInRange) {
                // Automatically abort if the range does not collide with this quad
                if (!aabb.intersectsBox(range)) return;

                // If leaf, check objects at this level
                if (isLeaf()) {
                    for (XY xyPoint : points) {
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
     * Quad-tree which holds rectangles as axis-aligned bounding boxes.
     */
    public static class RectangleQuadTree<B extends QuadTree.AxisAlignedBoundingBox> extends QuadTree<B> {

        private RectangleQuadNode<B> root = null;

        /**
         * Create a quadtree who's upper left coordinate is located at x,y and it's bounding box is described
         * by the height and width. This uses a default leafCapacity of 4 and a maxTreeHeight of 20.
         *
         * @param x Upper left X coordinate
         * @param y Upper left Y coordinate
         * @param height Height of the bounding box containing all points
         * @param width Width of the bounding box containing all points
         */
        public RectangleQuadTree(float x, float y, float height, float width) {
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox(xyPoint,height,width);
            root = new RectangleQuadNode<B>(aabb);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public QuadTree.QuadNode<B> getRoot() {
            return root;
        }

        /**
         * Insert rectangle whose upper-left point is located at X,Y and has a height and width into tree.
         * 
         * @param x X position of upper-left hand corner.
         * @param y Y position of upper-left hand corner.
         * @param height Height of the rectangle.
         * @param width Width of the rectangle.
         */
        @SuppressWarnings("unchecked")
        public void insert(float x, float y, float height, float width) {
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox range = new AxisAlignedBoundingBox(xyPoint,height,width);
            root.insert((B)range);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<B> queryRange(float x, float y, float height, float width) {
            List<B> geometricObjectsInRange = new LinkedList<B>();
            if (root==null) return (List<B>)geometricObjectsInRange; 
            XYPoint xyPoint = new XYPoint(x,y);
            AxisAlignedBoundingBox range = new AxisAlignedBoundingBox(xyPoint,height,width);
            root.queryRange(range,geometricObjectsInRange);
            return (List<B>)geometricObjectsInRange;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return TreePrinter.getString(this);
        }

        protected static class RectangleQuadNode<AABB extends QuadTree.AxisAlignedBoundingBox> extends QuadNode<AABB> {

            protected List<AABB> aabbs = new LinkedList<AABB>();

            protected RectangleQuadNode(AxisAlignedBoundingBox aabb) {
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
                if (!aabb.intersectsBox(b)) return false; // object cannot be added
                if (aabbs.contains(b)) return true; // already exists

                // If this is the biggest bounding box which completely contains the aabb.
                float nextLevelsHeight = aabb.height/2;
                float nextLevelsWidth = aabb.width/2;
                if (nextLevelsHeight<b.height && nextLevelsWidth<b.width) {
                    if (aabb.insideThis(b)) {
                        aabbs.add(b);
                        return true;
                    }
                    return false;
                }

                // Otherwise, we need to subdivide then add the objects to whichever node will accept it
                if (isLeaf()) subdivide();
                boolean inserted = insertIntoChildren(b);
                if (!inserted) {
                    // Couldn't insert into children (it could strattle the bounds of the box)
                    if (aabb.insideThis(b)) {
                        // If it fits completely into this box
                        aabbs.add(b);
                        return true;
                    }
                    return false;
                } else {
                    return true;
                }
            }

            private void subdivide() {
                float h = aabb.height/2;
                float w = aabb.width/2;

                AxisAlignedBoundingBox aabbNW = new AxisAlignedBoundingBox(aabb.upperLeft,h,w);
                northWest = new RectangleQuadNode<AABB>(aabbNW);

                XYPoint xyNE = new XYPoint(aabb.upperLeft.x+w,aabb.upperLeft.y);
                AxisAlignedBoundingBox aabbNE = new AxisAlignedBoundingBox(xyNE,h,w);
                northEast = new RectangleQuadNode<AABB>(aabbNE);

                XYPoint xySW = new XYPoint(aabb.upperLeft.x,aabb.upperLeft.y+h);
                AxisAlignedBoundingBox aabbSW = new AxisAlignedBoundingBox(xySW,h,w);
                southWest = new RectangleQuadNode<AABB>(aabbSW);

                XYPoint xySE = new XYPoint(aabb.upperLeft.x+w,aabb.upperLeft.y+h);
                AxisAlignedBoundingBox aabbSE = new AxisAlignedBoundingBox(xySE,h,w);
                southEast = new RectangleQuadNode<AABB>(aabbSE);
            }

            private boolean insertIntoChildren(AABB b) {
                //Try to insert into all children
                if (northWest.insert(b)) return true;
                if (northEast.insert(b)) return true;
                if (southWest.insert(b)) return true;
                if (southEast.insert(b)) return true;
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected void queryRange(AxisAlignedBoundingBox range, List<AABB> geometricObjectsInRange) {
                // Automatically abort if the range does not collide with this quad
                if (!aabb.intersectsBox(range)) return;

                // Check objects at this level
                for (AABB b : aabbs) {
                    if (range.intersectsBox(b)) geometricObjectsInRange.add(b);
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
                for (AABB p : aabbs) {
                    builder.append(p).append(", ");
                }
                builder.append("]");
                return builder.toString();
            }
        }
    }


    protected static abstract class QuadNode<G extends QuadTree.GeometricObject> implements Comparable<QuadNode<G>> {

        protected AxisAlignedBoundingBox aabb = null;
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
         * Find all objects which appear within a range.
         * 
         * @param range Upper-left and height,width of a axis-aligned bounding box.
         * @param geometricObjectsInRange Geometric objects inside the bounding box. 
         */
        protected abstract void queryRange(AxisAlignedBoundingBox range, List<G> geometricObjectsInRange);

        /**
         * Is current node a leaf node.
         * @return True if node is a leaf node.
         */
        protected boolean isLeaf() {
            return (northWest==null);
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
        @SuppressWarnings("unchecked")
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

    protected static class GeometricObject {
        // Nothing.. At this point
    }

    public static class XYPoint extends GeometricObject implements Comparable<XYPoint> {

        private float x = Float.MIN_VALUE;
        private float y = Float.MIN_VALUE;

        public XYPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }
        public float getY() {
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

    public static class AxisAlignedBoundingBox extends GeometricObject implements Comparable<AxisAlignedBoundingBox> {

        private XYPoint upperLeft = null;
        private float height = 0;
        private float width = 0;

        private float minX = 0;
        private float minY = 0;
        private float maxX = 0;
        private float maxY = 0;

        public AxisAlignedBoundingBox(XYPoint upperLeft, float height, float width) {
            this.upperLeft = upperLeft;
            this.height = height;
            this.width = width;

            minX = upperLeft.x;
            minY = upperLeft.y;
            maxX = upperLeft.x+width;
            maxY = upperLeft.y+height;
        }

        public XYPoint getUpperLeft() {
            return upperLeft;
        }
        public float getHeight() {
            return height;
        }
        public float getWidth() {
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

        public static <T extends GeometricObject> String getString(QuadTree<T> tree) {
            if (tree.getRoot() == null) return "Tree has no nodes.";
            return getString(tree.getRoot(), "", true);
        }

        private static <T extends GeometricObject> String getString(QuadNode<T> node, String prefix, boolean isTail) {
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
