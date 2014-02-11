package dbscan;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class KdTree<T extends KdTree.XYZPoint> {

	private int k = 2;
	public KdNode root = null;

	private static final Comparator<XYZPoint> X_COMPARATOR = new Comparator<XYZPoint>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(XYZPoint o1, XYZPoint o2) {
			if (o1.getX()<o2.getX()) return -1;
			if (o1.getX()>o2.getX()) return 1;
			return 0;
		}
	};

	private static final Comparator<XYZPoint> Y_COMPARATOR = new Comparator<XYZPoint>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(XYZPoint o1, XYZPoint o2) {
			if (o1.getY()<o2.getY()) return -1;
			if (o1.getY()>o2.getY()) return 1;
			return 0;
		}
	};

	private static final Comparator<XYZPoint> Z_COMPARATOR = new Comparator<XYZPoint>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(XYZPoint o1, XYZPoint o2) {
			if (o1.getZ()<o2.getZ()) return -1;
			if (o1.getZ()>o2.getZ()) return 1;
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

		if (mediaIndex < 500) {
			List<XYZPoint> less = list.subList(0, mediaIndex);
			node.leftPoints = less;
			List<XYZPoint> more = list.subList(mediaIndex+1, list.size());
			node.rightPoints = more;
		} else {
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
	public static final List<XYZPoint> getTree(KdNode root) {
		List<XYZPoint> list = new ArrayList<XYZPoint>();
		if (root==null) {
			return list;
		} else {
			list.add(root.id);
		}

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

	@SuppressWarnings("unchecked")
	public Collection<Integer> rangeRecursive(T value, double eps) {
		if (value==null) return null;

		Collection<KdNode> results = new ArrayList<KdNode>();

		searchNodeRangeRecursive(value, eps, this.root, results);

		Collection<Integer> collection = new ArrayList<Integer>();
		for (KdNode kdNode : results) {
			collection.add(((T)kdNode.id).getIndex());
		}

		return collection;
	}

	public static final <T extends KdTree.XYZPoint> void searchNodeRangeRecursive(T value, double eps, KdNode root, Collection<KdNode> results) {

		Double axisDistance = null;
		Double rootDistance = value.euclideanDistance(root.id);

		int axis = root.depth % root.k;
		KdNode lesser = root.lesser;
		KdNode greater = root.greater;

		if (axis==X_AXIS) {
			axisDistance = DBScan.getDistance(value.getX(), root.id.getX(), value.getY(), value.getY());
		} else if (axis==Y_AXIS) {
			axisDistance = DBScan.getDistance(value.getX(), value.getX(), value.getY(), root.id.getY());			
		} 

		if (lesser == null && greater == null) {

			if (rootDistance <= eps) {
				results.add(root);
			}

			for (KdTree.XYZPoint p : root.leftPoints) {
				if (value.euclideanDistance(p) <= eps) {
					results.add(new KdNode(p));
				}
			}

			for (KdTree.XYZPoint p : root.rightPoints) {
				if (value.euclideanDistance(p) <= eps) {
					results.add(new KdNode(p));
				}
			}

		} else {

			if (axisDistance <= eps) {
				
				if (rootDistance <= eps) {
					results.add(root);
				}
				
				searchNodeRangeRecursive(value, eps, lesser, results);
				searchNodeRangeRecursive(value, eps, greater, results);

			} else {
				if (axis==X_AXIS) {
					if(value.getX() > root.id.getX()) {
						searchNodeRangeRecursive(value, eps, greater, results);
					} else if (value.getX() < root.id.getX()) {
						searchNodeRangeRecursive(value, eps, lesser, results);
					} else if (value.getX() == root.id.getX()) {
						searchNodeRangeRecursive(value, eps, lesser, results);
						searchNodeRangeRecursive(value, eps, greater, results);					
					}

				} else if (axis==Y_AXIS) {
					if(value.getY() > root.id.getY()) {
						searchNodeRangeRecursive(value, eps, greater, results);
					} else if (value.getY() < root.id.getY()){
						searchNodeRangeRecursive(value, eps, lesser, results);
					} else if (value.getY() == root.id.getY()) {
						searchNodeRangeRecursive(value, eps, lesser, results);
						searchNodeRangeRecursive(value, eps, greater, results);
					}
				} 
			}
		}
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

		System.out.println("---" + node.id.lon + " ," + node.id.lat + "\n");

		//Search children branches, if axis aligned distance is less than current distance
		if (lesser!=null && !examined.contains(lesser)) {
			examined.add(lesser);

			double p1 = Double.MIN_VALUE;
			double p2 = Double.MIN_VALUE;
			if (axis==X_AXIS) {
				p1 = node.id.getX();
				p2 = value.getX()-lastDistance;
			} else if (axis==Y_AXIS) {
				p1 = node.id.getY();
				p2 = value.getY()-lastDistance;
			} else {
				p1 = node.id.getZ();
				p2 = value.getZ()-lastDistance;
			}
			boolean lineIntersectsCube = ((p2<=p1)?true:false);

			//Continue down lesser branch
			if (lineIntersectsCube) {
				searchNode(value,lesser,K,results,examined);
			}
		}
		if (greater!=null && !examined.contains(greater)) {
			examined.add(greater);

			double p1 = Double.MIN_VALUE;
			double p2 = Double.MIN_VALUE;
			if (axis==X_AXIS) {
				p1 = node.id.getX();
				p2 = value.getX()+lastDistance;
			} else if (axis==Y_AXIS) {
				p1 = node.id.getY();
				p2 = value.getY()+lastDistance;
			} else {
				p1 = node.id.getZ();
				p2 = value.getZ()+lastDistance;
			}
			boolean lineIntersectsCube = ((p2>=p1)?true:false);

			//Continue down greater branch
			if (lineIntersectsCube) {
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

		public int k = 2;
		public int depth = 0;
		public XYZPoint id = null;
		public KdNode parent = null;
		public KdNode lesser = null;
		public KdNode greater = null;

		private List<XYZPoint> leftPoints = null;
		private List<XYZPoint> rightPoints = null;

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

		private double lon = Double.NEGATIVE_INFINITY;
		private double lat = Double.NEGATIVE_INFINITY;
		private double z = Double.NEGATIVE_INFINITY;
		private boolean iscore = Boolean.FALSE;
		private int neigh;
		private boolean isvisited;
		private String cluster;
		private boolean isNoise;
		private Integer index;


		public XYZPoint(double lat, double lon) {
			this.setLat(lat);
			this.setLon(lon);
			this.setZ(0);
			this.setIscore(false);
			this.setNeigh(0);
			this.setIsvisited(false);
			this.setCluster("");
			this.setNoise(false);
			this.setIndex(-1);
		}

		public XYZPoint(double lat, double lon, boolean iscore, int neigh, String cluster, boolean isNoise) {
			this.setLat(lat);
			this.setLon(lon);
			this.setZ(0);
			this.setIscore(iscore);
			this.setNeigh(neigh);
			this.setIsvisited(false);
			this.setCluster(cluster);
			this.setNoise(isNoise);
			this.setIndex(-1);
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
		public static final double euclideanDistance(XYZPoint o1, XYZPoint o2) {
			return Math.sqrt(Math.pow((o1.getX()-o2.getX()),2)+Math.pow((o1.getY()-o2.getY()),2)+Math.pow((o1.getZ()-o2.getZ()),2));
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
			builder.append(getX()).append(", ");
			builder.append(getY()).append(", ");
			builder.append(getZ());
			builder.append(")");
			return builder.toString();
		}

		public double getX() {
			return lon;
		}

		public void setX(double x) {
			this.lon = x;
		}

		public double getY() {
			return lat;
		}

		public void setY(double y) {
			this.lat = y;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
			this.z = z;
		}

		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}

		public boolean isCore() {
			return iscore;
		}
		public void setIscore(boolean iscore) {
			this.iscore = iscore;
		}
		public int getNeigh() {
			return neigh;
		}
		public void setNeigh(int neigh) {
			this.neigh = neigh;
		}

		public boolean isVisited() {
			return isvisited;
		}

		public void setIsvisited(boolean isvisited) {
			this.isvisited = isvisited;
		}

		public String getCluster() {
			return cluster;
		}

		public void setCluster(String cluster) {
			this.cluster = cluster;
		}

		public boolean isNoise() {
			return isNoise;
		}

		public void setNoise(boolean isNoise) {
			this.isNoise = isNoise;
		}

		public void increaseNeigh(){
			this.neigh++;
		}

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}
	}

	public static class TreePrinter {

		public static <T extends XYZPoint> String getString(KdTree<T> tree) {
			if (tree.root == null) return "Tree has no nodes.";
			return getString(tree.root, "", true);
		}

		//T extends Comparable<T>
		private static <Type> String getString(KdNode node, String prefix, boolean isTail) {
			StringBuilder builder = new StringBuilder();

			if (node.parent!=null) {
				String side = "left";
				if (node.parent.greater!=null && node.id.equals(node.parent.greater.id)) side = "right";
				builder.append(prefix + (isTail ? "â””â”€â”€ " : "â”œâ”€â”€ ") + "[" + side + "] " + "depth=" + node.depth + " id=" + node.id + "\n");
			} else {
				builder.append(prefix + (isTail ? "â””â”€â”€ " : "â”œâ”€â”€ ") + "depth=" + node.depth + " id=" + node.id + "\n");
			}
			List<KdNode> children = null;
			if (node.lesser != null || node.greater != null) {
				children = new ArrayList<KdNode>(2);
				if (node.lesser != null) children.add(node.lesser);
				if (node.greater != null) children.add(node.greater);
			}
			if (children != null) {
				for (int i = 0; i < children.size() - 1; i++) {
					builder.append(getString(children.get(i), prefix + (isTail ? "    " : "â”‚   "), false));
				}
				if (children.size() >= 1) {
					builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "â”‚   "), true));
				}
			}

			return builder.toString();
		}
	}
}
