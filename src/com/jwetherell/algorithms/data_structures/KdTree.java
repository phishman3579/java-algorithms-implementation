package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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

    private int k = 3;
    private KdNode root = null;


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
        int axis = depth % k;
        if (axis==0) Collections.sort(list, X_COMPARATOR);
        else if (axis==1) Collections.sort(list, Y_COMPARATOR);
        else if (axis==2) Collections.sort(list, Z_COMPARATOR);
        
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
        if (tree==null || tree.root==null) return null;

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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }


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
            int axis = depth%k;
            if (axis==0) return X_COMPARATOR.compare(o1, o2);
            if (axis==1) return Y_COMPARATOR.compare(o1, o2);
            return Z_COMPARATOR.compare(o1, o2);
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

        private int x = Integer.MIN_VALUE;
        private int y = Integer.MIN_VALUE;
        private int z = Integer.MIN_VALUE;


        public XYZPoint(int x, int y) {
            this.x = x;
            this.y = y;
            this.z = 0;
        }

        public XYZPoint(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

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
            if (yComp!=0) return false;
            int zComp = Z_COMPARATOR.compare(this, xyzPoint);
            return (zComp==0);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(XYZPoint o) {
            int xComp = X_COMPARATOR.compare(this, o);
            if (xComp!=0) return xComp;
            int yComp = Y_COMPARATOR.compare(this, o);
            if (yComp!=0) return xComp;
            int zComp = Z_COMPARATOR.compare(this, o);
            return zComp;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(x).append(",");
            builder.append(y).append(",");
            builder.append(z);
            builder.append(") ");
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
