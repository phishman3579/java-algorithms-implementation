package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * B-tree is a tree data structure that keeps data sorted and allows searches,
 * sequential access, insertions, and deletions in logarithmic time. The B-tree
 * is a generalization of a binary search tree in that a node can have more than
 * two children. Unlike self-balancing binary search trees, the B-tree is
 * optimized for systems that read and write large blocks of data. It is
 * commonly used in databases and file-systems.
 * 
 * http://en.wikipedia.org/wiki/B-tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BTree<T extends Comparable<T>> {

    // Default to 2-3 Tree
    private int minKeySize = 3;
    private int minChildrenSize = minKeySize + 1; //4
    private int maxKeySize = 2 * minKeySize; //6
    private int maxChildrenSize = maxKeySize + 1; //7
    
    private Node<T> root = null;
    private int size = 0;


    public BTree() { }

    public BTree(int order) {
        this.minKeySize = order;
        this.minChildrenSize = minKeySize + 1;
        this.maxKeySize = 2*order;
        this.maxChildrenSize = maxKeySize + 1;
    }

    public void add(T value) {
        if (root == null) {
            root = new Node<T>(null, maxKeySize, maxChildrenSize);
            root.addKey(value);
        } else {
	        Node<T> node = root;
	        while (node != null) {
	            if (node.children.size() == 0) {
	                node.addKey(value);
	                if (node.keys.size() <= maxKeySize) {
	                    // A-OK
	                    break;
	                } else {
	                    // Need to split up
	                    split(node);
	                    break;
	                }
	            } else {
	                // navigate
	                T lesser = node.keys.get(0);
	                if (value.compareTo(lesser) < 0) {
	                    node = node.children.get(0);
	                    continue;
	                }
	
	                int size = node.keys.size();
	                int last = size - 1;
	                T greater = node.keys.get(last);
	                if (value.compareTo(greater) > 0) {
	                    node = node.children.get(size);
	                    continue;
	                }
	
	                for (int i = 1; i < node.keys.size(); i++) {
	                    T prev = node.keys.get(i - 1);
	                    T next = node.keys.get(i);
	                    if (value.compareTo(prev) > 0 && value.compareTo(next) < 0) {
	                        node = node.children.get(i);
	                        break;
	                    }
	                }
	            }
	        }
        }

        size++;
    }

    private void split(Node<T> node) {
        int size = node.keys.size();
        int medianIndex = size / 2;
        T medianValue = node.keys.get(medianIndex);

        Node<T> left = new Node<T>(null, maxKeySize, maxChildrenSize);
        for (int i=0; i<medianIndex; i++) {
            left.addKey(node.keys.get(i));
        }
        if (node.children.size()>0) {
            for (int j=0; j<=medianIndex; j++) {
                Node<T> c = node.children.get(j);
                left.addChild(c);
            }
        }

        Node<T> right = new Node<T>(null, maxKeySize, maxChildrenSize);
        for (int i = medianIndex+1; i < size; i++) {
            right.addKey(node.keys.get(i));
        }
        if (node.children.size()>0) {
            for (int j=medianIndex+1; j<node.children.size(); j++) {
                Node<T> c = node.children.get(j);
                right.addChild(c);
            }
        }

        if (node.parent == null) {
            // new root, height of tree is increased
            Node<T> newRoot = new Node<T>(null, maxKeySize, maxChildrenSize);
            newRoot.addKey(medianValue);
            node.parent = newRoot;
            root = newRoot;
            node = root;
            node.addChild(left);
            node.addChild(right);
        } else {
            // Move the median value up to the parent
            Node<T> parent = node.parent;
            parent.addKey(medianValue);
            parent.children.remove(node);
            parent.addChild(left);
            parent.addChild(right);

            if (parent.keys.size() > maxKeySize) split(parent);
        }
    }

    public boolean contains(T value) {
        Node<T> node = getNode(value);
        return (node != null);
    }

    private Node<T> getNode(T value) {
        Node<T> node = root;
        while (node != null) {
            T lesser = node.keys.get(0);
            if (value.compareTo(lesser) < 0) {
                if (node.children.size() > 0) node = node.children.get(0);
                else node = null;
                continue;
            }

            int size = node.keys.size();
            int last = size - 1;
            T greater = node.keys.get(last);
            if (value.compareTo(greater) > 0) {
                if (node.children.size() > size) node = node.children.get(size);
                else node = null;
                continue;
            }

            for (int i = 0; i < size; i++) {
                T currentValue = node.keys.get(i);
                if (currentValue.compareTo(value) == 0) {
                    return node;
                }

                int next = i + 1;
                if (next <= last) {
                    T nextValue = node.keys.get(next);
                    if (currentValue.compareTo(value) < 0 && nextValue.compareTo(value) > 0) {
                        if (next < node.children.size()) {
                            node = node.children.get(next);
                            break;
                        } else {
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean remove(T value) {
        Node<T> node = this.getNode(value);
        if (node == null) {
            System.out.println("Could not find " + value + "\n" + this.toString());
            return false;
        }

        int index = node.keys.indexOf(value);
        node.keys.remove(value);
        if (node.children.size()==0) {
        	//leaf node
        	if (node.parent!=null && node.keys.size()<this.minKeySize) {
        		this.combined(node);
        	} else if (node.parent==null && node.keys.size()==0) {
        		//Removing root node with no keys or children
        		root = null;
        	}
        } else {
        	//internal node
        	Node<T> lesser = node.children.get(index);
        	Node<T> greatest = this.getGreatestNode(lesser);
        	T replaceValue = this.removeGreatestValue(greatest);
        	node.addKey(replaceValue);
        	if (greatest.parent!=null && greatest.keys.size()<this.minKeySize) {
        		this.combined(greatest);
        	}
        	if (greatest.children.size()>this.maxChildrenSize) {
        		this.split(greatest);
        	}
        }
        
        size--;

        return true;
    }

    private T removeGreatestValue(Node<T> node) {
    	T value = null;
    	if (node.keys.size()>0) {
    		value = node.keys.remove(node.keys.size()-1);
    	}
    	return value;
    }
    
    private Node<T> getGreatestNode(Node<T> branch) {
    	while (branch.children.size()>0) {
    		branch = branch.children.get(branch.children.size()-1);
    	}
    	return branch;
    }

    private boolean combined(Node<T> node) {
        Node<T> parent = node.parent;
        int index = parent.children.indexOf(node);
        int indexOfLeftNeighbor = index - 1;
        int indexOfRightNeighbor = index + 1;

        Node<T> rightNeighbor = null;
        int rightNeighborSize = -minChildrenSize;
        if (indexOfRightNeighbor < parent.children.size()) {
            rightNeighbor = parent.children.get(indexOfRightNeighbor);
            rightNeighborSize = rightNeighbor.keys.size();
        }

        Node<T> leftNeighbor = null;
        int leftNeighborSize = -minChildrenSize;
        if (indexOfLeftNeighbor >= 0) {
            leftNeighbor = parent.children.get(indexOfLeftNeighbor);
            leftNeighborSize = leftNeighbor.keys.size();
        }

        // Try to borrow neighbor
        if (rightNeighbor != null && rightNeighborSize > minKeySize) {
            // Try to borrow from right neighbor
            T removeValue = rightNeighbor.keys.get(0);
            int prev = getIndexOfPreviousValue(parent, removeValue);
            T parentValue = parent.keys.remove(prev);
            T neighborValue = rightNeighbor.keys.remove(0);
            node.addKey(parentValue);
            parent.addKey(neighborValue);
            if (rightNeighbor.children.size()>0) {
                node.addChild(rightNeighbor.children.remove(0));
            }
        } else if (leftNeighbor != null && leftNeighborSize > minKeySize) {
            // Try to borrow from left neighbor
            T removeValue = leftNeighbor.keys.get(leftNeighbor.keys.size() - 1);
            int prev = getIndexOfNextValue(parent, removeValue);
            T parentValue = parent.keys.remove(prev);
            T neighborValue = leftNeighbor.keys.remove(leftNeighbor.keys.size() - 1);
            node.addKey(parentValue);
            parent.addKey(neighborValue);
            if (leftNeighbor.children.size()>0) {
                node.addChild(leftNeighbor.children.remove(leftNeighbor.children.size()-1));
            }
        } else if (rightNeighbor != null && parent.keys.size() > 0) {
            // Can't borrow from neighbors, try to combined with right neighbor
            T removeValue = rightNeighbor.keys.get(0);
            int prev = getIndexOfPreviousValue(parent, removeValue);
            T parentValue = parent.keys.remove(prev);
            parent.children.remove(rightNeighbor);
            node.addKey(parentValue);
            for (T v : rightNeighbor.keys) {
                node.addKey(v);
            }
            for (Node<T> c : rightNeighbor.children) {
            	node.addChild(c);
            }

            if (parent.parent != null && parent.keys.size() < this.minKeySize) {
            	// removing key made parent too small, combined up tree
                this.combined(parent);
            } else if (parent.keys.size() == 0) {
                // parent no longer has keys, make this node the new root
            	// which decreases the height of the tree
                node.parent = null;
                root = node;
            }
        } else if (leftNeighbor != null && parent.keys.size() > 0) {
            // Can't borrow from neighbors, try to combined with left neighbor
            T removeValue = leftNeighbor.keys.get(leftNeighbor.keys.size() - 1);
            int prev = getIndexOfNextValue(parent, removeValue);
            T parentValue = parent.keys.remove(prev);
            parent.children.remove(leftNeighbor);
            node.addKey(parentValue);
            for (T v : leftNeighbor.keys) {
                node.addKey(v);
            }
            for (Node<T> c : leftNeighbor.children) {
            	node.addChild(c);
            }

            if (parent.parent != null && parent.keys.size() < this.minKeySize) {
            	// removing key made parent too small, combined up tree
            	this.combined(parent);
            } else if (parent.keys.size() == 0) {
                // parent no longer has keys, make this node the new root
            	// which decreases the height of the tree
                node.parent = null;
                root = node;
            }
        }

        return true;
    }

    private int getIndexOfPreviousValue(Node<T> node, T value) {
        for (int i = 1; i < node.keys.size(); i++) {
            T t = node.keys.get(i);
            if (t.compareTo(value) >= 0) return i - 1;
        }
        return node.keys.size() - 1;
    }

    private int getIndexOfNextValue(Node<T> node, T value) {
        for (int i = 0; i < node.keys.size(); i++) {
            T t = node.keys.get(i);
            if (t.compareTo(value) >= 0) return i;
        }
        return node.keys.size() - 1;
    }

    public int getSize() {
        return size;
    }

    public boolean validate() {
        if (root == null) return true;
        return validateNode(root);
    }

    private boolean validateNode(Node<T> node) {
        int keySize = node.keys.size();
        if (keySize > 1) {
            // Make sure the keys are sorted
            for (int i = 1; i < keySize; i++) {
                T p = node.keys.get(i - 1);
                T n = node.keys.get(i);
                if (p.compareTo(n) > 0) return false;
            }
        }
        int childrenSize = node.children.size();
        if (node.parent == null) {
        	//root
        	if (keySize > this.maxKeySize) {
        		// check max key size. root does not have a min key size
                return false;
            } else if (childrenSize==0) {
                // if root, no children, and keys are valid
                return true;
            } else if (childrenSize < 2) {
                // root should have zero or at least two children
                return false;
            } else if (childrenSize > this.maxChildrenSize) {
                return false;
            }
        } else {
        	//non-root
            if (keySize < this.minKeySize) {
                return false;
            } else if (keySize > this.maxKeySize) {
                return false;
            } else if (childrenSize==0) {
                return true;
            } else if (keySize != (childrenSize - 1)) {
                // If there are chilren, there should be one more child then keys
                return false;
            } else if (childrenSize < this.minChildrenSize) {
                return false;
            } else if (childrenSize > this.maxChildrenSize) {
                return false;
            }
        }

        Node<T> first = node.children.get(0);
        // The first child's last key should be less than the node's first key
        if (first.keys.get(first.keys.size() - 1).compareTo(node.keys.get(0)) > 0) return false;

        Node<T> last = node.children.get(node.children.size() - 1);
        // The last child's first key should be greater than the node's last key
        if (last.keys.get(0).compareTo(node.keys.get(node.keys.size() - 1)) < 0) return false;

        // Check that each node's first and last key holds it's invariance
        for (int i = 1; i < node.keys.size(); i++) {
            T p = node.keys.get(i - 1);
            T n = node.keys.get(i);
            Node<T> c = node.children.get(i);
            if (p.compareTo(c.keys.get(0)) > 0) return false;
            if (n.compareTo(c.keys.get(c.keys.size() - 1)) < 0) return false;
        }

        for (Node<T> c : node.children) {
            boolean valid = this.validateNode(c);
            if (!valid) return false;
        }
        
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    private static class Node<T extends Comparable<T>> {

        private Node<T> parent = null;
        private List<T> keys = null;
        private List<Node<T>> children = null;
        private Comparator<Node<T>> comparator = new Comparator<Node<T>>() {
            @Override
            public int compare(Node<T> arg0, Node<T> arg1) {
                return arg0.keys.get(0).compareTo(arg1.keys.get(0));
            }
        };

        private Node(Node<T> parent, int maxKeySize, int maxChildrenSize) {
            this.parent = parent;
            this.keys = new ArrayList<T>(maxKeySize);
            this.children = new ArrayList<Node<T>>(maxChildrenSize);
        }

        private void addKey(T value) {
            this.keys.add(value);
            Collections.sort(keys);
        }

        private void addChild(Node<T> child) {
            child.parent = this;
            this.children.add(child);
            Collections.sort(children, comparator);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("keys=[");
            for (int i = 0; i < keys.size(); i++) {
                T value = keys.get(i);
                builder.append(value);
                if (i < keys.size() - 1) builder.append(", ");
            }
            builder.append("]\n");

            if (parent != null) {
                builder.append("parent=[");
                for (int i = 0; i < parent.keys.size(); i++) {
                    T value = parent.keys.get(i);
                    builder.append(value);
                    if (i < parent.keys.size() - 1) builder.append(", ");
                }
                builder.append("]\n");
            }

            if (children != null) {
                builder.append("children=").append(children.size()).append("\n");
            }

            return builder.toString();
        }
    }

    private static class TreePrinter {

        public static <T extends Comparable<T>> String getString(BTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix).append((isTail ? "└── " : "├── "));
            for (int i = 0; i < node.keys.size(); i++) {
                T value = node.keys.get(i);
                builder.append(value);
                if (i < node.keys.size() - 1) builder.append(", ");
            }
            builder.append("\n");

            if (node.children != null) {
                for (int i = 0; i < node.children.size() - 1; i++) {
                    Node<T> obj = node.children.get(i);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.children.size() >= 1) {
                    Node<T> obj = node.children.get(node.children.size() - 1);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
