package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Binary Search Tree.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BinarySearchTree {
    private static final Random RANDOM = new Random();

    private Node root = null;
    private int size = 0;

    public static enum TYPE { FIRST, MIDDLE, RANDOM };
    public TYPE type = TYPE.FIRST;
    
    public BinarySearchTree() { 
        //If you are not passing in an array of node, we have to use TYPE==FIRST
    }
    
    public BinarySearchTree(int[] nodes) { 
        //Defaulted to TYPE==FIRST
        populateTree(nodes);
    }
    
    public BinarySearchTree(int[] nodes, TYPE type) {
        this.type = type;
        populateTree(nodes);
    }
    
    public void add(int value) {
        add(new Node(null,value),true);
    }

    public boolean contains(Integer value) {
        Node node = root;
        while (node!=null) {
            if (value.compareTo(node.value) == 0) {
            	return true;
            } else if (value.compareTo(node.value) < 0) {
            	node = node.lesser;
            } else {
            	node = node.greater;
            }
        }
        return false;
    }

    private void add(Node newNode, boolean adjustSize) {
        //If we are adding a node or subtree back into the current tree then set 'adjustSize'
        // to false. This is done in the remove method.
        if (newNode==null) return;
        
        if (root==null) {
            root = newNode;
            if (adjustSize) size++;
            return;
        }
        
        Node node = root;
        while (true) {
            if (newNode.value.compareTo(node.value) <= 0) {
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
    
    public void remove(int value) {
        Node nodeToRemove = root;
        while (true) {
            if (nodeToRemove == null) {
                //Could not find the node to remove
                return;
            } else if (value < nodeToRemove.value) {
                //Node to remove is less than current node
                nodeToRemove = nodeToRemove.lesser;
            } else if (value > nodeToRemove.value) {
                //Node to remove is greater then current node
                nodeToRemove = nodeToRemove.greater;
            } else if (value == nodeToRemove.value) {
                //We found our node! Or the first occurrence of our node
                Node parent = nodeToRemove.parent;
                Node nodeToRefactor = null;
                if (parent == null) {
                    //Replacing the root node
                    if (nodeToRemove.lesser != null) {
                        //Replace root with lesser subtree
                        root = nodeToRemove.lesser;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = root.greater;
                    } else if (nodeToRemove.greater != null) {
                        //Replace root with greater subtree
                        root = nodeToRemove.greater;
                        
                        //Save lesser subtree for adding back into the tree below
                        nodeToRefactor = root.lesser;
                    }
                    //Root not should not have a parent
                    root.parent = null;
                } else if (parent.lesser != null && parent.lesser.value.equals(nodeToRemove.value)) {
                    //If the node to remove is the parent's lesser node, replace 
                    // the parent's lesser node with one of the node to remove's lesser/greater subtrees
                    Node nodeToMoveUp = null;
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
                } else if (parent.greater != null && parent.greater.value.equals(nodeToRemove.value)) {
                    //If the node to remove is the parent's greater node, replace 
                    // the parent's greater node with the node's greater node
                    Node nodeToMoveUp = null;
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
                return;
            }
        }
    }

    private final int getRandom(int length) {
        if (type==TYPE.RANDOM && length>0) return RANDOM.nextInt(length);
        if (type==TYPE.FIRST && length>0) return 0;
        else return length/2;
    }
    
    private void populateTree(int[] nodes) {
        int rootIndex = getRandom(nodes.length);
        int rootValue = nodes[rootIndex];
        Node newNode = new Node(null,rootValue);
        add(newNode,true);
        
        for (int node : nodes) {
            if (node!=rootValue) {
                newNode = new Node(null,node);
                add(newNode,true);
            }
        }
    }

    public int[] getSorted() {
        List<Node> added = new ArrayList<Node>();
        int[] nodes = new int[size];
        int index = 0;
        Node node = root;
        while (index<size) {
            Node parent = node.parent;
            Node lesser = (node.lesser!=null && !added.contains(node.lesser))?node.lesser:null;
            Node greater = (node.greater!=null && !added.contains(node.greater))?node.greater:null;

            if (parent==null && lesser==null && greater==null) {
                if (!added.contains(node)) nodes[index++] = node.value;
                break;
            }
            
            if (lesser!=null) {
                node = lesser;
            } else {
                if (!added.contains(node)) {
                    nodes[index++] = node.value;
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
        int[] sorted = getSorted();
        for (int node : sorted) {
            builder.append(node).append(", ");
        }
        return builder.toString();
    }
    
    private static class Node {
        private Integer value = null;
        private Node parent = null;
        private Node lesser = null;
        private Node greater = null;
        
        private Node(Node parent, int value) {
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
