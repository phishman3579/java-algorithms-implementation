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
        if (root==null) {
            root = newNode;
            if (adjustSize) size++;
            return;
        }
        
        Node node = root;
        while (true) {
            if (newNode.value <= node.value) {
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
        remove(new Node(null,value),true);
    }
    
    private void remove(Node newNode, boolean adjustSize) {
        Node node = root;
        while (true) {
            if (node == null) {
                return;
            } else if (newNode.value < node.value) {
                node = node.lesserNode;
            } else if (newNode.value > node.value) {
                node = node.greaterNode;
            } else if (newNode.value == node.value) {
                Node parent = node.parentNode;
                if (parent == null) {
                    //Replace the root
                    Node lesser = node.lesserNode;
                    Node greater = node.greaterNode;
                    Node oldRoot = root;
                    Node nodeToMove = null;
                    if (lesser != null) {
                        //Replace root with lesser subtree
                        nodeToMove = oldRoot.greaterNode;
                        root = lesser;
                        root.parentNode = null;
                        if (nodeToMove!=null) {
                            //If the greater subtree isn't NULL then add the subtree to the new root
                            nodeToMove.parentNode = null;
                            add(nodeToMove,false);
                        }
                        node = null;
                        if (adjustSize) size--;
                        return;
                    } else if (greater != null) {
                        //Replace root with greater subtree
                        nodeToMove = oldRoot.lesserNode;
                        root = greater;
                        root.parentNode = null;
                        if (nodeToMove!=null) {
                            //If the lesser subtree isn't NULL then add the subtree to the new root
                            nodeToMove.parentNode = null;
                            add(nodeToMove,false);
                        }
                        node = null;
                        if (adjustSize) size--;
                        return;
                    }
                    return;
                } else if (parent.lesserNode != null && parent.lesserNode == node) {
                    //If the node to remove is the parent's lesser node, replace 
                    // the parent's lesser node with the node's lesser node
                    parent.lesserNode = node.lesserNode;
                    if (node.lesserNode!=null) {
                        node.lesserNode.parentNode = parent;
                        Node oldGreater = node.greaterNode;
                        node = node.lesserNode;
                        if (oldGreater!=null) {
                            //If the node to remove has a greater node add the node
                            // and it's subtree onto root
                            oldGreater.parentNode = null;
                            add(oldGreater,false);
                        }
                    }
                    node = null;
                    if (adjustSize) size--;
                    return;
                } else if (parent.greaterNode != null && parent.greaterNode == node) {
                    //If the node to remove is the parent's greater node, replace 
                    // the parent's greater node with the node's greater node
                    parent.greaterNode = node.greaterNode;
                    if (node.greaterNode!=null) {
                        node.greaterNode.parentNode = parent;
                        Node oldLesser = node.lesserNode;
                        node = node.greaterNode;
                        if (oldLesser!=null) {
                            //If the node to remove has a lesser node add the node
                            // and it's subtree onto root
                            oldLesser.parentNode = null;
                            add(oldLesser,false);
                        }
                    }
                    node = null;
                    if (adjustSize) size--;
                    return;
                }
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

            if (parent==null && lesser==null && greater==null) break;
            
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
