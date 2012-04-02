package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A binary search tree (BST), which may sometimes also be called an ordered or sorted binary tree, is a node-based binary 
 * tree data structure which has the following properties:
 *   1) The left subtree of a node contains only nodes with keys less than the node's key.
 *   2) The right subtree of a node contains only nodes with keys greater than the node's key.
 *   3) Both the left and right subtrees must also be binary search trees.
 * 
 * http://en.wikipedia.org/wiki/Binary_search_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BinarySearchTree<T> {
    private static final Random RANDOM = new Random();

    protected Node<T> root = null;
    protected int size = 0;

    public static enum TYPE { FIRST, MIDDLE, RANDOM };
    public TYPE type = TYPE.FIRST;
    
    public BinarySearchTree() { 
        //If you are not passing in an array of node, we have to use TYPE==FIRST
    }
    
    public BinarySearchTree(Comparable<T>[] nodes) { 
        //Defaulted to TYPE==FIRST
        populateTree(nodes);
    }
    
    public BinarySearchTree(Comparable<T>[] nodes, TYPE type) {
        this.type = type;
        populateTree(nodes);
    }
    
    public void add(Comparable<T> value) {
        add(new Node<T>(null,value),true);
    }

    public boolean contains(Comparable<T> key) {
        Node<T> node = getNode(key);
        return (node!=null);
    }
    
    @SuppressWarnings("unchecked")
    protected Node<T> getNode(Comparable<T> value) {
        Node<T> node = root;
        while (node != null) {
            if (value.compareTo((T)node.value) == 0) {
                return node;
            } else if (value.compareTo((T)node.value) < 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void add(Node<T> newNode, boolean adjustSize) {
        //If we are adding a node or subtree back into the current tree then set 'adjustSize'
        // to false. This is done in the remove method.
        if (newNode==null) return;
        
        if (root==null) {
            root = newNode;
            if (adjustSize) size++;
            return;
        }
        
        Node<T> node = root;
        while (true) {
            if (newNode.value.compareTo((T)node.value) <= 0) {
                if (node.lesser==null) {
                    // New left node
                    newNode.parent = node;
                    node.lesser = newNode;
                    if (adjustSize) size++;
                    return;
                } else {
                    node = node.lesser;
                }
            } else {
                if (node.greater == null) {
                    // New right node
                    newNode.parent = node;
                    node.greater = newNode;
                    if (adjustSize) size++;
                    return;
                } else {
                    node = node.greater;
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public boolean remove(Comparable<T> value) {
        Node<T> nodeToRemove = root;
        while (true) {
            if (nodeToRemove == null) {
                //Could not find the node to remove
                return false;
            } else if (value.compareTo((T)nodeToRemove.value)<0) {
                //Node to remove is less than current node
                nodeToRemove = nodeToRemove.lesser;
            } else if (value.compareTo((T)nodeToRemove.value)>0) {
                //Node to remove is greater then current node
                nodeToRemove = nodeToRemove.greater;
            } else if (value.compareTo((T)nodeToRemove.value)==0) {
                //We found our node! Or the first occurrence of our node
                Node<T> parent = nodeToRemove.parent;
                Node<T> nodeToRefactor = null;
                if (parent == null) {
                    //Replacing the root node
                    if (nodeToRemove.lesser != null) {
                        //Replace root with lesser subtree
                        root = nodeToRemove.lesser;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greater;
                    } else if (nodeToRemove.greater != null) {
                        //Replace root with greater subtree
                        root = nodeToRemove.greater;
                    }
                    //Root not should not have a parent
                    root.parent = null;
                } else if (parent.lesser != null && (parent.lesser.value.compareTo((T)nodeToRemove.value)==0)) {
                    //If the node to remove is the parent's lesser node, replace 
                    // the parent's lesser node with one of the node to remove's lesser/greater subtrees
                    Node<T> nodeToMoveUp = null;
                    if (nodeToRemove.lesser==null && nodeToRemove.greater==null) {
                        //Node to remove doesn't have a lesser or greater node. Nothing to refactor.
                        parent.lesser = null;
                    } else if (nodeToRemove.lesser!=null) {
                        //Using the less subtree
                        nodeToMoveUp = nodeToRemove.lesser;
                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greater;
                    } else if (nodeToRemove.greater!=null) {
                        //Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = nodeToRemove.greater;
                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    }
                } else if (parent.greater != null && (parent.greater.value.compareTo((T)nodeToRemove.value)==0)) {
                    //If the node to remove is the parent's greater node, replace 
                    // the parent's greater node with the node's greater node
                    Node<T> nodeToMoveUp = null;
                    if (nodeToRemove.lesser==null && nodeToRemove.greater==null) {
                        //Node to remove doesn't have a lesser or greater node. Nothing to refactor.
                        parent.greater = null;
                    } else if (nodeToRemove.lesser!=null) {
                        //Using the less subtree
                        nodeToMoveUp = nodeToRemove.lesser;
                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greater;
                    } else if (nodeToRemove.greater!=null) {
                        //Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = nodeToRemove.greater;
                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    }
                }
                if (nodeToRefactor!=null) {
                    //If there is a node to refactor then add the subtree to the new root
                    nodeToRefactor.parent = null;
                    add(nodeToRefactor,false);
                }
                nodeToRemove = null;
                size--;
                return true;
            }
        }
    }

    private final int getRandom(int length) {
        if (type==TYPE.RANDOM && length>0) return RANDOM.nextInt(length);
        if (type==TYPE.FIRST && length>0) return 0;
        else return length/2;
    }
    
    protected void populateTree(Comparable<T>[] nodes) {
        int rootIndex = getRandom(nodes.length);
        Comparable<T> rootValue = nodes[rootIndex];
        Node<T> newNode = new Node<T>(null,rootValue);
        add(newNode,true);
        
        for (Comparable<T> node : nodes) {
            if (node!=rootValue) {
                newNode = new Node<T>(null,node);
                add(newNode,true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T[] getSorted() {
        List<Node<T>> added = new ArrayList<Node<T>>();
        T[] nodes = (T[]) new Object[size];
        int index = 0;
        Node<T> node = root;
        while (index<size) {
            Node<T> parent = node.parent;
            Node<T> lesser = (node.lesser!=null && !added.contains(node.lesser))?node.lesser:null;
            Node<T> greater = (node.greater!=null && !added.contains(node.greater))?node.greater:null;

            if (parent==null && lesser==null && greater==null) {
                if (!added.contains(node)) nodes[index++] = (T) node.value;
                break;
            }
            
            if (lesser!=null) {
                node = lesser;
            } else {
                if (!added.contains(node)) {
                    nodes[index++] = (T) node.value;
                    added.add(node);
                }
                if (greater!=null) {
                    node = greater;
                } else if (greater==null && added.contains(node)) {
                    node = parent;
                }
            }
        }
        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        T[] sorted = getSorted();
        for (T node : sorted) {
            builder.append(node).append(", ");
        }
        return builder.toString();
    }
    
    protected static class Node<T> {
        protected Comparable<T> value = null;
        protected Node<T> parent = null;
        protected Node<T> lesser = null;
        protected Node<T> greater = null;
        
        protected Node(Node<T> parent, Comparable<T> value) {
            this.parent = parent;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value="+value+
                   " parent="+((parent!=null)?parent.value:"NULL")+
                   " lesser="+((lesser!=null)?lesser.value:"NULL")+
                   " greater="+((greater!=null)?greater.value:"NULL");
        }
    }
}
