package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * B-tree is a tree data structure that keeps data sorted and allows searches, sequential access, insertions, 
 * and deletions in logarithmic time. The B-tree is a generalization of a binary search tree in that a node 
 * can have more than two children. Unlike self-balancing binary search trees, the B-tree is optimized for 
 * systems that read and write large blocks of data. It is commonly used in databases and file-systems.
 * 
 * http://en.wikipedia.org/wiki/B-tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BTree<T extends Comparable<T>> {

    //Default to 2-3 Tree
    private int childrenSize = 3;
    private int highSize = childrenSize-1;
    private int lowSize = highSize/2;
    
    private Node<T> root = null;
    private int size = 0;

    public BTree() { }
    
    public BTree(int way) {
        this.childrenSize = way;
        this.highSize = childrenSize-1;
        this.lowSize = highSize/2;
    }

    public void add(T value) {
        System.out.println("Adding "+value+"\n"+this.toString());
        if (root==null) {
            root = new Node<T>(null,highSize);
            root.addKey(value);
            size++;
            System.out.println(this.toString());
            return;
        }

        Node<T> node = root;
        while (node!=null) {
            if (node.children.size()==0) {
                node.addKey(value);
                if (node.keys.size()<=highSize) {
                    //A-OK
                    break;
                } else {
                    //Need to split up
                    split(node);
                    break;
                }
            } else {
                //navigate
                T lesser = node.keys.get(0);
                if (value.compareTo(lesser) < 0) {
                    node = node.children.get(0);
                    continue;
                }

                int size = node.keys.size();
                int last = size-1;
                T greater = node.keys.get(last);
                if (value.compareTo(greater) > 0) {
                    node = node.children.get(size);
                    continue;
                }

                for (int i=1; i<node.keys.size(); i++) {
                    T prev = node.keys.get(i-1);
                    T next = node.keys.get(i);
                    if (value.compareTo(prev)>0 && value.compareTo(next)<0) {
                        node = node.children.get(i);
                        break;
                    }
                }
            }
        }
        
        size++;
        System.out.println("Added "+value+"\n"+this.toString());
    }

    private void split(Node<T> node) {
        System.out.println("Splitting "+node+"\n"+this.toString());
        int size = node.keys.size();
        int medianIndex = size/2;
        T medianValue = node.keys.get(medianIndex);

        Node<T> left = new Node<T>(null,highSize);
        for (int i=0; i<medianIndex; i++) {
            left.addKey(node.keys.get(i));
        }

        Node<T> right = new Node<T>(null,highSize);
        for (int i=medianIndex+1; i<size; i++) {
            right.addKey(node.keys.get(i));
        }

        if (node.parent==null) {
            //root
            Node<T> newRoot = new Node<T>(null,highSize);
            newRoot.addKey(medianValue);
            node.parent = newRoot;
            root = newRoot;
            node = root;

            left.parent = node;
            node.addChild(left);

            right.parent = node;
            node.addChild(right);
        } else {
            if (node.keys.size()>=highSize && node.parent.keys.size()>=highSize) {
                //Split node into a new branch
                Node<T> parent = node.parent;
                parent.addKey(medianValue);
                parent.children.remove(node);

                left.parent = parent;
                parent.addChild(left);

                right.parent = parent;
                parent.addChild(right);

                //Split parent into a new branch
                size = parent.keys.size();
                medianIndex = size/2;
                medianValue = parent.keys.get(medianIndex);

                left = new Node<T>(null,highSize);
                for (int i=0; i<medianIndex; i++) {
                    left.addKey(parent.keys.get(i));
                }
                right = new Node<T>(null,highSize);
                for (int i=medianIndex+1; i<size; i++) {
                    right.addKey(parent.keys.get(i));
                }

                size = parent.children.size();
                medianIndex = size/2;
                for (int i=0; i<medianIndex; i++) {
                    Node<T> child = parent.children.get(i);
                    child.parent = left;
                    left.addChild(child);
                }
                for (int i=medianIndex; i<size; i++) {
                    Node<T> child = parent.children.get(i);
                    child.parent = right;
                    right.addChild(child);
                }

                parent.keys.clear();
                parent.children.clear();
                parent.addKey(medianValue);

                left.parent = parent;
                parent.addChild(left);

                right.parent = parent;
                parent.addChild(right);

                if (parent.keys.size()>highSize) split(parent);
            } else {
                //Move up the median value
                Node<T> parent = node.parent;
                parent.addKey(medianValue);
                parent.children.remove(node);

                left.parent = parent;
                parent.addChild(left);

                right.parent = parent;
                parent.addChild(right);

                if (parent.keys.size()>highSize) split(parent);
            }
        }
        System.out.println("Splitted "+node+"\n"+this.toString());
    }
    
    public boolean contains(T value) {
        Node<T> node = getNode(value);
        return (node != null);
    }

    private Node<T> getNode(T value) {
        Node<T> node = root;
        while (node!=null) {
            T lesser = node.keys.get(0);
            if (value.compareTo(lesser) < 0) {
                if (node.children.size() > 0)
                    node = node.children.get(0);
                else
                    node = null;
                continue;
            }

            int size = node.keys.size();
            int last = size-1;
            T greater = node.keys.get(last);
            if (value.compareTo(greater) > 0) {
                if (node.children.size() > size)
                    node = node.children.get(size);
                else
                    node = null;
                continue;
            }
            
            for (int i=0; i<size; i++) {
                T currentValue = node.keys.get(i);
                if (currentValue.compareTo(value) == 0) {
                    return node;
                }
                
                int next = i+1;
                if (next <= last) {
                    T nextValue = node.keys.get(next);
                    if (currentValue.compareTo(value)<0 && nextValue.compareTo(value)>0) {
                        node = node.children.get(next);
                        break;
                    }
                }
            }
        }
        return null;
    }

    public boolean remove(T value) {
        System.out.println("Removing "+value+"\n"+this.toString());
        Node<T> node = this.getNode(value);
        if (node==null) return false;

        if (node.keys.size()>1) {
            int index = node.keys.indexOf(value);
            node.keys.remove(value);
            if (node.parent!=null && node.keys.size()<lowSize) {
                //Not the root and lower than minimum, try to combined
                combined(node);
            } else if (index!=0 && index!=node.keys.size()) {
                //Middle element
                if (index<node.children.size()) {
                    Node<T> child = node.children.get(index);
                    T childValue = child.keys.remove(child.keys.size()-1);
                    node.addKey(childValue);
                    if (child.keys.size()==0) {
                        node.children.remove(index);
                    } else if (child.keys.size()==1) {
                        combined(child);
                    }
                }
            } else if (index==0 && node.children.size()>0) {
                //Removed first element
                Node<T> child = node.children.remove(0);
                for (T k : child.keys) {
                    this.add(k);
                }
                System.out.println();
            } else if (index==node.keys.size() && node.children.size()>0) {
                //Removed last element
                Node<T> child = node.children.remove(index+1);
                for (T k : child.keys) {
                    this.add(k);
                }
                System.out.println();
            }
        } else {
            Node<T> parent = node.parent;
            if (parent==null) {
                //root
                System.out.println();
            } else {
                parent.children.remove(node);
            }
        }

        size--;

        System.out.println("Removed "+value+"\n"+this.toString());
        return true;
    }

    private void combined(Node<T> node) {
        System.out.println("Combineding "+node+"\n"+this.toString());
        Node<T> parent = node.parent;
        int index = parent.children.indexOf(node);
        int indexOfLeftNeighbor = index-1;
        int indexOfRightNeighbor = index+1;

        //Try to borrow from neighbors
        if (indexOfRightNeighbor<parent.children.size()) {
            int mySize = node.keys.size();
            Node<T> leftNeighbor = parent.children.get(indexOfRightNeighbor);
            int leftNeighborSize = leftNeighbor.keys.size();
            
            Node<T> rightNeighbor = null;
            int rightNeighborSize = -1;
            if (indexOfLeftNeighbor>0) {
                rightNeighbor = parent.children.get(indexOfLeftNeighbor);
                rightNeighborSize = rightNeighbor.keys.size();
            }

            if (leftNeighborSize-mySize>=2) {
                T parentValue = parent.keys.remove(parent.keys.size()-1);
                T neighborValue = leftNeighbor.keys.remove(0);
                node.addKey(parentValue);
                parent.addKey(neighborValue);
            } else if (rightNeighborSize-mySize>=2) {
                T parentValue = parent.keys.remove(indexOfLeftNeighbor);
                T neighborValue = rightNeighbor.keys.remove(rightNeighbor.keys.size()-1);
                node.addKey(parentValue);
                parent.addKey(neighborValue);
            } else if (parent.keys.size()>0) {
                //Can't borrow from neighbor, try to combined
                T parentValue = parent.keys.remove(parent.keys.size()-1);
                node.addKey(parentValue);

                for (T v : leftNeighbor.keys) {
                    node.addKey(v);
                }
                
                parent.children.remove(leftNeighbor);
                if (parent.keys.size()==1) {
                    combined(parent);
                } else if (parent.keys.size()==0) {
                    //new root
                    for (Node<T> c : leftNeighbor.children) {
                        c.parent = node;
                        node.addChild(c);
                    }
                    node.parent = null;
                    root = node;
                }
            }
        }  

        System.out.println("Combineded "+node+"\n"+this.toString());
    }
    
    public int getSize() {
        return size;
    }

    public boolean validate() {
        if (root==null) return true;
        return validateNode(root);
    }

    private boolean validateNode(Node<T> node) {
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

        private Node(Node<T> parent, int keySize) { 
            this.parent = parent;
            this.keys = new ArrayList<T>(keySize);
            this.children = new ArrayList<Node<T>>(keySize+1);
        }

        private void addKey(T value) {
            keys.add(value);
            Collections.sort(keys);
        }

        private void addChild(Node<T> child) {
            children.add(child);
            Collections.sort(children,comparator);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("keys=[");
            for (int i=0; i<keys.size(); i++) {
                T value = keys.get(i);
                builder.append(value);
                if (i<keys.size()-1) builder.append(", ");
            }
            builder.append("]\n");

            if (parent!=null) {
                builder.append("parent=[");
                for (int i=0; i<parent.keys.size(); i++) {
                    T value = parent.keys.get(i);
                    builder.append(value);
                    if (i<parent.keys.size()-1) builder.append(", ");
                }
                builder.append("]\n");
            }

            if (children!=null) {
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
            for (int i=0; i<node.keys.size(); i++) {
                T value = node.keys.get(i);
                builder.append(value);
                if (i<node.keys.size()-1) builder.append(", ");
            }
            builder.append("\n");
            
            if (node.children!=null) {
                for (int i=0; i<node.children.size()-1; i++) {
                    Node<T> obj = node.children.get(i);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.children.size()>=1) {
                    Node<T> obj = node.children.get(node.children.size()-1);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
