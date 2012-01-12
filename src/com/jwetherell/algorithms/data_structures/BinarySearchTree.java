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

    public static enum SEARCH_TYPE { FIRST, MIDDLE, RANDOM };
    public SEARCH_TYPE type = SEARCH_TYPE.FIRST;
    
    public BinarySearchTree() { }
    
    public BinarySearchTree(int[] nodes) { 
        populateTree(nodes);
    }
    
    public void add(int value) {
        add(new Node(null,value),true);
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
                if (node.lesserNode==null) {
                    // New left node
                    newNode.parentNode = node;
                    node.lesserNode = newNode;
                    if (adjustSize) size++;
                    return;
                } else {
                    node = node.lesserNode;
                }
            } else {
                if (node.greaterNode == null) {
                    // New right node
                    newNode.parentNode = node;
                    node.greaterNode = newNode;
                    if (adjustSize) size++;
                    return;
                } else {
                    node = node.greaterNode;
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
                nodeToRemove = nodeToRemove.lesserNode;
            } else if (value > nodeToRemove.value) {
                //Node to remove is greater then current node
                nodeToRemove = nodeToRemove.greaterNode;
            } else if (value == nodeToRemove.value) {
                //We found our node! Or the first occurrence of our node
                Node parent = nodeToRemove.parentNode;
                Node nodeToRefactor = null;
                if (parent == null) {
                    //Replacing the root node
                    if (nodeToRemove.lesserNode != null) {
                        //Replace root with lesser subtree
                        root = nodeToRemove.lesserNode;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = root.greaterNode;
                    } else if (nodeToRemove.greaterNode != null) {
                        //Replace root with greater subtree
                        root = nodeToRemove.greaterNode;
                        
                        //Save lesser subtree for adding back into the tree below
                        nodeToRefactor = root.lesserNode;
                    }
                    //Root not should not have a parent
                    root.parentNode = null;
                } else if (parent.lesserNode != null && parent.lesserNode.value.equals(nodeToRemove.value)) {
                    //If the node to remove is the parent's lesser node, replace 
                    // the parent's lesser node with one of the node to remove's lesser/greater subtrees
                    Node nodeToMoveUp = null;
                    if (nodeToRemove.lesserNode==null && nodeToRemove.greaterNode==null) {
                        //Node to remove doesn't have a lesser or greater node. Nothing to refactor.
                        parent.lesserNode = null;
                    } else if (nodeToRemove.lesserNode!=null) {
                        //Using the less subtree
                        nodeToMoveUp = nodeToRemove.lesserNode;
                        parent.lesserNode = nodeToMoveUp;
                        nodeToMoveUp.parentNode = parent;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greaterNode;
                    } else if (nodeToRemove.greaterNode!=null) {
                        //Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = nodeToRemove.greaterNode;
                        parent.lesserNode = nodeToMoveUp;
                        nodeToMoveUp.parentNode = parent;
                    }
                } else if (parent.greaterNode != null && parent.greaterNode.value.equals(nodeToRemove.value)) {
                    //If the node to remove is the parent's greater node, replace 
                    // the parent's greater node with the node's greater node
                    Node nodeToMoveUp = null;
                    if (nodeToRemove.lesserNode==null && nodeToRemove.greaterNode==null) {
                        //Node to remove doesn't have a lesser or greater node. Nothing to refactor.
                        parent.greaterNode = null;
                    } else if (nodeToRemove.lesserNode!=null) {
                        //Using the less subtree
                        nodeToMoveUp = nodeToRemove.lesserNode;
                        parent.greaterNode = nodeToMoveUp;
                        nodeToMoveUp.parentNode = parent;
                        
                        //Save greater subtree for adding back into the tree below
                        nodeToRefactor = nodeToRemove.greaterNode;
                    } else if (nodeToRemove.greaterNode!=null) {
                        //Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = nodeToRemove.greaterNode;
                        parent.greaterNode = nodeToMoveUp;
                        nodeToMoveUp.parentNode = parent;
                    }
                }
                if (nodeToRefactor!=null) {
                    //If there is a node to refactor then add the subtree to the new root
                    nodeToRefactor.parentNode = null;
                    add(nodeToRefactor,false);
                }
                nodeToRemove = null;
                size--;
                return;
            }
        }
    }

    private final int getRandom(int length) {
        if (type==SEARCH_TYPE.RANDOM && length>0) return RANDOM.nextInt(length);
        if (type==SEARCH_TYPE.FIRST && length>0) return 0;
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
        while (true) {
            Node parent = node.parentNode;
            Node lesser = (node.lesserNode!=null && !added.contains(node.lesserNode))?node.lesserNode:null;
            Node greater = (node.greaterNode!=null && !added.contains(node.greaterNode))?node.greaterNode:null;

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
        private Node parentNode = null;
        private Node lesserNode = null;
        private Node greaterNode = null;
        
        private Node(Node parent, int value) {
            this.parentNode = parent;
            this.value = value;
        }

        public String toString() {
            return "value="+value+
                   " parent="+((parentNode!=null)?parentNode.value:"NULL")+
                   " lesser="+((lesserNode!=null)?lesserNode.value:"NULL")+
                   " greater="+((greaterNode!=null)?greaterNode.value:"NULL");
        }
    }
}
