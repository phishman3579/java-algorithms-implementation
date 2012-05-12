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
    private int minChildrenSize = minKeySize + 1;
    private int maxKeySize = 2 * minKeySize;
    private int maxChildrenSize = maxKeySize + 1;
    
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
        System.out.println("Adding " + value + "\n" + this.toString());
        if (root == null) {
            root = new Node<T>(null, maxKeySize, maxChildrenSize);
            root.addKey(value);
            size++;
            System.out.println(this.toString());
            return;
        }

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

        size++;
        System.out.println("Added " + value + "\n" + this.toString());
    }

    private void split(Node<T> node) {
        System.out.println("Splitting " + node + "\n" + this.toString());
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
            // root
            Node<T> newRoot = new Node<T>(null, maxKeySize, maxChildrenSize);
            newRoot.addKey(medianValue);
            node.parent = newRoot;
            root = newRoot;
            node = root;
            node.addChild(left);
            node.addChild(right);
        } else {
            // Move up the median value
            Node<T> parent = node.parent;
            parent.addKey(medianValue);
            parent.children.remove(node);
            parent.addChild(left);
            parent.addChild(right);

            if (parent.keys.size() > maxKeySize) split(parent);
        }
        System.out.println("Splitted " + node + "\n" + this.toString());
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
        System.out.println("Removing " + value + "\n" + this.toString());
        Node<T> node = this.getNode(value);
        if (node == null) {
            System.out.println("Could not find " + value + "\n" + this.toString());
            return false;
        }

        if (node.keys.size() > 1) {
            int index = node.keys.indexOf(value);
            node.keys.remove(value);
            if (index != 0 && index != node.keys.size()) {
                // Middle element
                if (index < node.children.size()) {
                    Node<T> child = node.children.get(index);
                    T childValue = child.keys.remove(child.keys.size() - 1);
                    node.addKey(childValue);
                    if (child.keys.size() == 0) {
                        node.children.remove(index);
                    } else if (child.keys.size() == 1) {
                        combined(child);
                    }
                } else if (node.children.size() > 2) {
                    System.err.println("I don't know how I got here.");
                } else {
                    // Node has two children, proceed.
                }
            } else if (index == 0 && node.children.size() > 0) {
                // Removed first element
                Node<T> child = node.children.get(0);
                T childValue = child.keys.remove(child.keys.size() - 1);
                node.addKey(childValue);
                if (child.parent != null && child.keys.size() > 0 && child.keys.size() < minKeySize) combined(child);
            } else if (index == node.keys.size() && node.children.size() > 0) {
                // Removed last element
                Node<T> child = node.children.get(node.children.size()-1);
                T childValue = child.keys.remove(0);
                node.addKey(childValue);
                if (child.parent != null && child.keys.size() > 0 && child.keys.size() < minKeySize) combined(child);
            } else if (node.children.size() > 2) {
                System.err.println("I don't know how I got here.");
            } else {
                // Node has two children, proceed.
            }
        } else {
            Node<T> parent = node.parent;
            if (parent == null) {
                // root
                if (node.children.size() > 0) {
                    Node<T> first = node.children.get(0);
                    int firstSize = first.keys.size();
                    Node<T> last = node.children.get(node.children.size()-1);
                    int lastSize = first.keys.size();
                    node.keys.remove(value);
                    if (first.keys.size() > this.minKeySize && firstSize > lastSize) {
                        T childValue = null;
                        if (first.children.size()>0) {
                            first = first.children.get(first.keys.size()-1);
                            childValue = first.keys.remove(first.keys.size()-1);
                        } else {
                            childValue = first.keys.remove(first.keys.size()-1);
                        }
                        node.addKey(childValue);
                        if (first.keys.size() < this.minKeySize) combined(first);
                    } else if (last.keys.size() > this.minKeySize) {
                        T childValue = null;
                        if (last.children.size()>0) {
                            last = last.children.get(0);
                            childValue = last.keys.remove(0);
                        } else {
                            childValue = last.keys.remove(0);
                        }                            
                        node.addKey(childValue);
                        if (last.keys.size() < this.minKeySize) combined(last);
                    } else {
                        for (T k : first.keys) {
                            node.addKey(k);
                        }
                        node.children.remove(first);
                        for (T k : last.keys) {
                            node.addKey(k);
                        }
                        node.children.remove(last);

                        if (first.children.size() > 0 && last.children.size() > 0) merge(node,first,last);
                    }
                } else {
                    // Removed root
                    node.keys.remove(value);
                    if (node.keys.size() == 0) root = null;
                }
            } else {
                parent.children.remove(node);
                node = parent;
            }
        }

        if (node.parent != null && node.keys.size() < minKeySize) {
            // Not the root and lower than minimum, try to combined
            combined(node);
        } else if (node.keys.size() > maxKeySize) {
            split(node);
        }

        size--;

        System.out.println("Removed " + value + "\n" + this.toString());
        return true;
    }

    private boolean merge(Node<T> node, Node<T> first, Node<T> last) {
        Node<T> leftMiddle = first.children.get(first.children.size() - 1);
        Node<T> rightMiddle = last.children.get(0);
        int leftMiddleKeySize = leftMiddle.keys.size();
        int rightMiffleKeySize = rightMiddle.keys.size();
        int combinedMiddleNodeSize = leftMiddleKeySize + rightMiffleKeySize;
        if (combinedMiddleNodeSize > this.minChildrenSize) {
            // Need to create a new parent key
            T newParentValue = null;
            if (leftMiddleKeySize > rightMiffleKeySize) {
                newParentValue = leftMiddle.keys.remove(leftMiddleKeySize - 1);
            } else {
                newParentValue = rightMiddle.keys.remove(0);
            }
            node.addKey(newParentValue);

            for (Node<T> c : first.children) {
                node.addChild(c);
            }

            for (Node<T> c : last.children) {
                node.addChild(c);
            }
        } else {
            // Combined the two middle nodes
            for (T t : leftMiddle.keys) {
                rightMiddle.addKey(t);
            }

            if (leftMiddle.children.size() > 0 && rightMiddle.children.size() > 0) {
                Node<T> c1 = rightMiddle.children.get(0);
                Node<T> c2 = leftMiddle.children.remove(leftMiddle.children.size()-1);
                for (T v : c2.keys) {
                    c1.addKey(v);
                }
    
                for (int i=0; i<leftMiddle.children.size(); i++) {
                    Node<T> c = leftMiddle.children.get(i);
                    rightMiddle.addChild(c);
                }
            }

            for (int i = 0; i < first.children.size()-1; i++) {
                Node<T> c = first.children.get(i);
                node.addChild(c);
            }

            for (Node<T> c : last.children) {
                node.addChild(c);
            }
        }

        return true;
    }
    
    private boolean combined(Node<T> node) {
        System.out.println("Combineding " + node + "\n" + this.toString());
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

        // Try to borrow from neighbors
        if (rightNeighbor != null && rightNeighborSize > minKeySize) {
            // Try right neighbor
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
            // Try left neighbor
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
            node.addKey(parentValue);
            for (T v : rightNeighbor.keys) {
                node.addKey(v);
            }

            parent.children.remove(rightNeighbor);
            if (parent.parent != null && parent.keys.size() == 1) {
                combined(parent);
            } else if (parent.keys.size() == 0) {
                // new root
                for (Node<T> c : rightNeighbor.children) {
                    node.addChild(c);
                }
                node.parent = null;
                root = node;
            }
        } else if (leftNeighbor != null && parent.keys.size() > 0) {
            // Can't borrow from neighbors, try to combined with left neighbor
            T removeValue = leftNeighbor.keys.get(leftNeighbor.keys.size() - 1);
            int prev = getIndexOfNextValue(parent, removeValue);
            T parentValue = parent.keys.remove(prev);
            node.addKey(parentValue);
            for (T v : leftNeighbor.keys) {
                node.addKey(v);
            }

            parent.children.remove(leftNeighbor);
            if (parent.parent != null && parent.keys.size() == 1) {
                combined(parent);
            } else if (parent.keys.size() == 0) {
                // new root
                for (Node<T> c : leftNeighbor.children) {
                    node.addChild(c);
                }
                node.parent = null;
                root = node;
            }
        }

        System.out.println("Combineded " + node + "\n" + this.toString());
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
        if (node.parent == null && childrenSize==0) {
            // if root, no children, and keys are valid
            return true;
        } else if (node.parent == null && childrenSize < 2) {
            // root should have zero or at least two children
            return false;
        } else if (node.parent == null && keySize > this.maxKeySize) {
            return false;
        } else if (node.parent == null && childrenSize > this.maxChildrenSize) {
            return false;
        } else if (node.parent != null && keySize < this.minKeySize) {
            return false;
        } else if (node.parent != null && keySize > this.maxKeySize) {
            return false;
        } else if (node.parent != null && childrenSize==0) {
            return true;
        }else if (node.parent != null && childrenSize < this.minChildrenSize) {
            return false;
        } else if (node.parent != null && childrenSize > this.maxChildrenSize) {
            return false;
        } else if (node.parent != null && childrenSize>0 && keySize != (childrenSize - 1)) {
            // There should be one more child then keys
            return false;
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
