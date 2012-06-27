package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * A k-d tree (short for k-dimensional tree) is a space-partitioning data structure for organizing 
 * points in a k-dimensional space. k-d trees are a useful data structure for several applications, 
 * such as searches involving a multidimensional search key (e.g. range searches and nearest neighbor 
 * searches). k-d trees are a special case of binary space partitioning trees.
 * 
 * http://en.wikipedia.org/wiki/K-d_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class KdTree<T extends KdTree.XYZPoint> {

    private int k = 3;
    private KdNode root = null;

    private static final Comparator<XYZPoint> X_COMPARATOR = new Comparator<XYZPoint>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYZPoint o1, XYZPoint o2) {
            if (o1.x<o2.x) return -1;
            if (o1.x>o2.x) return 1;
            return 0;
        }
    };

    private static final Comparator<XYZPoint> Y_COMPARATOR = new Comparator<XYZPoint>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYZPoint o1, XYZPoint o2) {
            if (o1.y<o2.y) return -1;
            if (o1.y>o2.y) return 1;
            return 0;
        }
    };

    private static final Comparator<XYZPoint> Z_COMPARATOR = new Comparator<XYZPoint>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYZPoint o1, XYZPoint o2) {
            if (o1.z<o2.z) return -1;
            if (o1.z>o2.z) return 1;
            return 0;
        }
    };

    protected static final int X_AXIS = 0;
    protected static final int Y_AXIS = 1;
    protected static final int Z_AXIS = 2;


    /**
     * Default constructor.
     */
    public KdTree() { }

    /**
     * More efficient constructor.
     * 
     * @param list of XYZPoints.
     */
    public KdTree(List<XYZPoint> list) { 
        root = createNode(list, k, 0);
    }

    /**
     * Create node from list of XYZPoints.
     * 
     * @param list of XYZPoints.
     * @param k of the tree.
     * @param depth depth of the node.
     * @return node created.
     */
    private static KdNode createNode(List<XYZPoint> list, int k, int depth) {
        if (list==null || list.size()==0) return null;

        int axis = depth % k;
        if (axis==X_AXIS) Collections.sort(list, X_COMPARATOR);
        else if (axis==Y_AXIS) Collections.sort(list, Y_COMPARATOR);
        else Collections.sort(list, Z_COMPARATOR);

        int mediaIndex = list.size()/2;
        KdNode node = new KdNode(k,depth,list.get(mediaIndex));
        if (list.size()>0) {
            if ((mediaIndex-1)>=0) {
                List<XYZPoint> less = list.subList(0, mediaIndex);
                if (less.size()>0) {
                    node.lesser = createNode(less,k,depth+1);
                    node.lesser.parent = node;
                }
            }
            if ((mediaIndex+1)<=(list.size()-1)) {
                List<XYZPoint> more = list.subList(mediaIndex+1, list.size());
                if (more.size()>0) {
                    node.greater = createNode(more,k,depth+1);
                    node.greater.parent = node;
                }
            }
        }

        return node;
    }

    /**
     * Add value to the tree. Tree can contain multiple equal values.
     * 
     * @param value T to add to the tree.
     * @return True if successfully added to tree.
     */
    public boolean add(T value) {
        if (value==null) return false;

        if (root==null) {
            root = new KdNode(value);
            return true;
        }

        KdNode node = root;
        while (true) {
            if (KdNode.compareTo(node.depth, node.k, node.id, value)<=0) {
                //Lesser
                if (node.lesser==null) {
                    KdNode newNode = new KdNode(k,node.depth+1,value);
                    newNode.parent = node;
                    node.lesser = newNode;
                    break;
                } else {
                    node = node.lesser;
                }
            } else {
                //Greater
                if (node.greater==null) {
                    KdNode newNode = new KdNode(k,node.depth+1,value);
                    newNode.parent = node;
                    node.greater = newNode;
                    break;
                } else {
                    node = node.greater;
                }
            }
        }

        return true;
    }

    /**
     * Does the tree contain the value.
     * 
     * @param value T to locate in the tree.
     * @return True if tree contains value.
     */
    public boolean contains(T value) {
        if (value==null) return false;

        KdNode node = getNode(this,value);
        return (node!=null);
    }

    /**
     * Locate T in the tree.
     * 
     * @param tree to search.
     * @param value to search for.
     * @return KdNode or NULL if not found
     */
    private static final <T extends KdTree.XYZPoint> KdNode getNode(KdTree<T> tree, T value) {
        if (tree==null || tree.root==null || value==null) return null;

        KdNode node = tree.root;
        while (true) {
            if (node.id.equals(value)) {
                return node;
            } else if (KdNode.compareTo(node.depth, node.k, node.id, value)<0) {
                //Greater
                if (node.greater==null) {
                    return null;
                } else {
                    node = node.greater;
                }
            } else {
                //Lesser
                if (node.lesser==null) {
                    return null;
                } else {
                    node = node.lesser;
                }
            }
        }
    }

    /**
     * Remove first occurrence of value in the tree.
     * 
     * @param value T to remove from the tree.
     * @return True if value was removed from the tree.
     */
    public boolean remove(T value) {
        if (value==null) return false;

        KdNode node = getNode(this,value);
        if (node==null) return false;

        KdNode parent = node.parent;
        if (parent!=null) {
            if (parent.lesser!=null && node.equals(parent.lesser)) {
                List<XYZPoint> nodes = getTree(node);
                if (nodes.size()>0) {
                    parent.lesser = createNode(nodes,node.k,node.depth);
                    if (parent.lesser!=null) {
                        parent.lesser.parent = parent;
                    }
                } else {
                    parent.lesser = null;
                }
            } else {
                List<XYZPoint> nodes = getTree(node);
                if (nodes.size()>0) {
                    parent.greater = createNode(nodes,node.k,node.depth);
                    if (parent.greater!=null) {
                        parent.greater.parent = parent;
                    }
                } else {
                    parent.greater = null;
                }
            }
        } else {
            //root
            List<XYZPoint> nodes = getTree(node);
            if (nodes.size()>0) root = createNode(nodes,node.k,node.depth);
            else root = null;
        }

        return true;
    }

    /**
     * Get the (sub) tree rooted at root.
     * 
     * @param root of tree to get nodes for.
     * @return points in (sub) tree, not including root.
     */
    private static final List<XYZPoint> getTree(KdNode root) {
        List<XYZPoint> list = new ArrayList<XYZPoint>();
        if (root==null) return list;

        if (root.lesser!=null) {
            list.add(root.lesser.id);
            list.addAll(getTree(root.lesser));
        }
        if (root.greater!=null) {
            list.add(root.greater.id);
            list.addAll(getTree(root.greater));
        }

        return list;
    }

    /**
     * K Nearest Neighbor search
     * 
     * @param K Number of neighbors to retrieve. Can return more than K, if last nodes are equal distances.
     * @param value to find neighbors of.
     * @return collection of T neighbors.
     */
    @SuppressWarnings("unchecked")
    public Collection<T> nearestNeighbourSearch(int K, T value) {
        if (value==null) return null;

        //Map used for results
        TreeSet<KdNode> results = new TreeSet<KdNode>(new EuclideanComparator(value));

        //Find the closest leaf node
        KdNode prev = null;
        KdNode node = root;
        while (node!=null) {
            if (KdNode.compareTo(node.depth, node.k, node.id, value)<0) {
                //Greater
                prev = node;
                node = node.greater;
            } else {
                //Lesser
                prev = node;
                node = node.lesser;
            }
        }
        KdNode leaf = prev;

        if (leaf!=null) {
            //Used to not re-examine nodes
            Set<KdNode> examined = new HashSet<KdNode>();

            //Go up the tree, looking for better solutions
            node = leaf;
            while (node!=null) {
                //Search node
                searchNode(value,node,K,results,examined);
                node = node.parent;
            }
        }

        //Load up the collection of the results
        Collection<T> collection = new ArrayList<T>(K);
        for (KdNode kdNode : results) {
            collection.add((T)kdNode.id);
        }
        return collection;
    }

    private static final <T extends KdTree.XYZPoint> void searchNode(T value, KdNode node, int K, TreeSet<KdNode> results, Set<KdNode> examined) {
        examined.add(node);

        //Search node
        KdNode lastNode = null;
        Double lastDistance = Double.MAX_VALUE;
        if (results.size()>0) {
            lastNode = results.last();
            lastDistance = lastNode.id.euclideanDistance(value);
        }
        Double nodeDistance = node.id.euclideanDistance(value);
        if (nodeDistance.compareTo(lastDistance)<0) {
            if (results.size()==K && lastNode!=null) results.remove(lastNode);
            results.add(node);
        } else if (nodeDistance.equals(lastDistance)) {
            results.add(node);
        } else if (results.size()<K) {
            results.add(node);
        }
        lastNode = results.last();
        lastDistance = lastNode.id.euclideanDistance(value);

        int axis = node.depth % node.k;
        KdNode lesser = node.lesser;
        KdNode greater = node.greater;

        //Search children branches, if axis aligned distance is less than current distance
        if (lesser!=null && !examined.contains(lesser)) {
            examined.add(lesser);

            boolean lineIntersectsRect = false;
            Line line = null;
            Cube cube = null;
            if (axis==X_AXIS) {
                line = new Line(new Point(value.x-lastDistance,value.y,value.z), new Point(value.x+lastDistance,value.y,value.z));
                Point tul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tur = new Point(node.id.x,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tlr = new Point(node.id.x,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Rectangle trect = new Rectangle(tul,tur,tlr,tll);
                Point bul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bur = new Point(node.id.x,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point blr = new Point(node.id.x,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Rectangle brect = new Rectangle(bul,bur,blr,bll);
                cube = new Cube(trect,brect);
                lineIntersectsRect = cube.inserects(line);
            } else if (axis==Y_AXIS) {
                line = new Line(new Point(value.x,value.y-lastDistance,value.z), new Point(value.x,value.y+lastDistance,value.z));
                Point tul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tlr = new Point(Double.POSITIVE_INFINITY,node.id.y,Double.NEGATIVE_INFINITY);
                Point tll = new Point(Double.NEGATIVE_INFINITY,node.id.y,Double.NEGATIVE_INFINITY);
                Rectangle trect = new Rectangle(tul,tur,tlr,tll);
                Point bul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point blr = new Point(Double.POSITIVE_INFINITY,node.id.y,Double.POSITIVE_INFINITY);
                Point bll = new Point(Double.NEGATIVE_INFINITY,node.id.y,Double.POSITIVE_INFINITY);
                Rectangle brect = new Rectangle(bul,bur,blr,bll);
                cube = new Cube(trect,brect);
                lineIntersectsRect = cube.inserects(line);
            } else {
                line = new Line(new Point(value.x,value.y,value.z-lastDistance), new Point(value.x,value.y,value.z+lastDistance));
                Point tul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tlr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Rectangle trect = new Rectangle(tul,tur,tlr,tll);
                Point bul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,node.id.z);
                Point bur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,node.id.z);
                Point blr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,node.id.z);
                Point bll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,node.id.z);
                Rectangle brect = new Rectangle(bul,bur,blr,bll);
                cube = new Cube(trect,brect);
                lineIntersectsRect = cube.inserects(line);
            }

            //Continue down lesser branch
            if (lineIntersectsRect) {
                searchNode(value,lesser,K,results,examined);
            }
        }
        if (greater!=null && !examined.contains(greater)) {
            examined.add(greater);

            boolean lineIntersectsRect = false;
            Line line = null;
            Cube cube = null;
            if (axis==X_AXIS) {
                line = new Line(new Point(value.x-lastDistance,value.y,value.z), new Point(value.x+lastDistance,value.y,value.z));
                Point tul = new Point(node.id.x,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tlr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tll = new Point(node.id.x,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Rectangle trect = new Rectangle(tul,tur,tlr,tll);
                Point bul = new Point(node.id.x,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point blr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bll = new Point(node.id.x,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Rectangle brect = new Rectangle(bul,bur,blr,bll);
                cube = new Cube(trect,brect);
                lineIntersectsRect = cube.inserects(line);
            } else if (axis==Y_AXIS) {
                line = new Line(new Point(value.x,value.y-lastDistance,value.z), new Point(value.x,value.y+lastDistance,value.z));
                Point tul = new Point(Double.NEGATIVE_INFINITY,node.id.y,Double.NEGATIVE_INFINITY);
                Point tur = new Point(Double.POSITIVE_INFINITY,node.id.y,Double.NEGATIVE_INFINITY);
                Point tlr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Point tll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
                Rectangle trect = new Rectangle(tul,tur,tlr,tll);
                Point bul = new Point(Double.NEGATIVE_INFINITY,node.id.y,Double.POSITIVE_INFINITY);
                Point bur = new Point(Double.POSITIVE_INFINITY,node.id.y,Double.POSITIVE_INFINITY);
                Point blr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Rectangle brect = new Rectangle(bul,bur,blr,bll);
                cube = new Cube(trect,brect);
                lineIntersectsRect = cube.inserects(line);
            } else {
                line = new Line(new Point(value.x,value.y,value.z-lastDistance), new Point(value.x,value.y,value.z+lastDistance));
                Point tul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,node.id.z);
                Point tur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,node.id.z);
                Point tlr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,node.id.z);
                Point tll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,node.id.z);
                Rectangle trect = new Rectangle(tul,tur,tlr,tll);
                Point bul = new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bur = new Point(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point blr = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Point bll = new Point(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
                Rectangle brect = new Rectangle(bul,bur,blr,bll);
                cube = new Cube(trect,brect);
                lineIntersectsRect = cube.inserects(line);
            }

            //Continue down greater branch
            if (lineIntersectsRect) {
                searchNode(value,greater,K,results,examined);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }


    private static class Point {

        private double x = Double.NEGATIVE_INFINITY;
        private double y = Double.NEGATIVE_INFINITY;
        private double z = Double.NEGATIVE_INFINITY;

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = 0;
        }

        private Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(").append(x).append(", ").append(y).append(", ").append(z).append(")");
            return builder.toString();
        }
    }

    private static class Line {

        private Point p1 = null;
        private Point p2 = null;


        private Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("begin=").append(p1).append(" end=").append(p2);
            return builder.toString();
        }
    }

    private static class Rectangle {

        private Point ul = null;
        private Point ur = null;
        private Point lr = null;
        private Point ll = null;


        private Rectangle(Point ul, Point ur, Point lr, Point ll) {
            this.ul = ul;
            this.ur = ur;
            this.lr = lr;
            this.ll = ll;
        }

        private boolean inserects(Line line) {
            if (line.p1.x > ur.x && line.p2.x > lr.x) return false; //line is to right of rectangle
            if (line.p1.x < ul.x && line.p2.x < ll.x) return false; //line is to left of rectangle
            
            if (line.p1.y > ll.y && line.p2.y > lr.y) return false; //line is above rectangle
            if (line.p1.y < ul.y && line.p2.y < ur.y) return false; //line is below rectangle

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("ul=").append(ul).append(" ur=").append(ur);
            builder.append(" ll=").append(ll).append(" lr=").append(lr);
            return builder.toString();
        }
    }

    private static class Cube {

        private Rectangle top = null;
        private Rectangle bottom = null;


        private Cube(Rectangle top, Rectangle bottom) {
            this.top = top;
            this.bottom = bottom;
        }

        private boolean inserects(Line line) {

            double maxX = top.ur.x;
            if (top.lr.x>maxX) maxX = top.lr.x;
            if (bottom.ur.x>maxX) maxX = bottom.ur.x;
            if (bottom.lr.x>maxX) maxX = bottom.lr.x;
            if (line.p1.x > maxX && line.p2.x > maxX) return false;

            double minX = top.ul.x;
            if (top.ll.x<minX) minX = top.ll.x;
            if (bottom.ul.x<minX) minX = bottom.ul.x;
            if (bottom.ll.x<minX) minX = bottom.ll.x;
            if (line.p1.x < minX && line.p2.x < minX) return false;

            double maxY = bottom.ll.y;
            if (bottom.lr.y>maxY) maxY = bottom.lr.y;
            if (bottom.ll.y>maxY) maxY = bottom.ll.y;
            if (bottom.lr.y>maxY) maxY = bottom.lr.y;
            if (line.p1.y > maxY && line.p2.y > maxY) return false;

            double minY = top.ul.y;
            if (top.ur.y<minY) minY = top.ur.y;
            if (bottom.ul.y<minY) minY = bottom.ul.y;
            if (bottom.ur.y<minY) minY = bottom.ur.y;
            if (line.p1.y < minY && line.p2.y < minY) return false;

            double maxZ = bottom.ul.z;
            if (bottom.ur.z>maxZ) maxZ = bottom.ur.z;
            if (bottom.lr.z>maxZ) maxZ = bottom.lr.z;
            if (bottom.ll.z>maxZ) maxZ = bottom.ll.z;
            if (line.p1.z > maxZ && line.p2.z > maxZ) return false;

            double minZ = top.ul.z;
            if (top.ur.z<minZ) minZ = top.ur.z;
            if (top.lr.z<minZ) minZ = top.lr.z;
            if (top.ll.z<minZ) minZ = top.ll.z;
            if (line.p1.z < minZ && line.p2.z < minZ) return false;

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("top=[").append(top).append("] ");
            builder.append("bottom=[").append(bottom).append("]");
            return builder.toString();
        }
    }

    protected static class EuclideanComparator implements Comparator<KdNode> {

        private XYZPoint point = null;


        public EuclideanComparator(XYZPoint point) {
            this.point = point;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(KdNode o1, KdNode o2) {
            Double d1 = point.euclideanDistance(o1.id);
            Double d2 = point.euclideanDistance(o2.id);
            if (d1.compareTo(d2)<0) return -1;
            else if (d2.compareTo(d1)<0) return 1;
            return o1.id.compareTo(o2.id);
        }
    };

    public static class KdNode implements Comparable<KdNode> {

        private int k = 3;
        private int depth = 0;
        private XYZPoint id = null;
        private KdNode parent = null;
        private KdNode lesser = null;
        private KdNode greater = null;


        public KdNode(XYZPoint id) {
            this.id = id;
        }

        public KdNode(int k, int depth, XYZPoint id) {
            this(id);
            this.k = k;
            this.depth = depth;
        }

        public static int compareTo(int depth, int k, XYZPoint o1, XYZPoint o2) {
            int axis = depth % k;
            if (axis==X_AXIS) return X_COMPARATOR.compare(o1, o2);
            return Y_COMPARATOR.compare(o1, o2);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj==null) return false;
            if (!(obj instanceof KdNode)) return false;
            
            KdNode kdNode = (KdNode) obj;
            if (this.compareTo(kdNode)==0) return true; 
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(KdNode o) {
            return compareTo(depth, k, this.id, o.id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("k=").append(k);
            builder.append(" depth=").append(depth);
            builder.append(" id=").append(id.toString());
            return builder.toString();
        }
    }

    public static class XYZPoint implements Comparable<XYZPoint> {

        private double x = Double.NEGATIVE_INFINITY;
        private double y = Double.NEGATIVE_INFINITY;
        private double z = Double.NEGATIVE_INFINITY;


        public XYZPoint(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = 0;
        }

        public XYZPoint(double x, int y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Computes the Euclidean distance from this point to the other.
         * 
         * @param o1 other point.
         * @return euclidean distance.
         */
        public double euclideanDistance(XYZPoint o1) {
            return euclideanDistance(o1,this);
        }

        /**
         * Computes the Euclidean distance from one point to the other.
         * 
         * @param o1 first point.
         * @param o2 second point.
         * @return euclidean distance.
         */
        private static final double euclideanDistance(XYZPoint o1, XYZPoint o2) {
            return Math.sqrt(Math.pow((o1.x-o2.x),2)+Math.pow((o1.y-o2.y),2)+Math.pow((o1.z-o2.z),2));
        };

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof XYZPoint)) return false;

            XYZPoint xyzPoint = (XYZPoint) obj;
            int xComp = X_COMPARATOR.compare(this, xyzPoint);
            if (xComp!=0) return false;
            int yComp = Y_COMPARATOR.compare(this, xyzPoint);
            return (yComp==0);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(XYZPoint o) {
            int xComp = X_COMPARATOR.compare(this, o);
            if (xComp!=0) return xComp;
            int yComp = Y_COMPARATOR.compare(this, o);
            return yComp;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(x).append(", ");
            builder.append(y).append(", ");
            builder.append(z);
            builder.append(")");
            return builder.toString();
        }
    }

    protected static class TreePrinter {

        public static <T extends XYZPoint> String getString(KdTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(KdNode node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent!=null) {
                String side = "left";
                if (node.parent.greater!=null && node.id.equals(node.parent.greater.id)) side = "right";
                builder.append(prefix + (isTail ? "└── " : "├── ") + "[" + side + "] " + "depth=" + node.depth + " id=" + node.id + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + "depth=" + node.depth + " id=" + node.id + "\n");
            }
            List<KdNode> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<KdNode>(2);
                if (node.lesser != null) children.add(node.lesser);
                if (node.greater != null) children.add(node.greater);
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
