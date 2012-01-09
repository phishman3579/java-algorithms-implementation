package com.jwetherell.algorithms.data_structures;

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
        size = nodes.length;
        generateTree(nodes);
    }
    
    public void add(int value) {
        System.out.println("Adding "+value);
        
        if (root==null) {
            root = new Node(null, value);
            return;
        }
        
        Node node = root;
        while (true) {
            if (value <= node.value) {
                if (node.lesserNode==null) {
                    // New left node
                    Node newNode = new Node(node, value);
                    node.lesserNode = newNode;
                    size++;
                    break;
                } else {
                    node = node.lesserNode;
                }
            } else {
                if (node.greaterNode == null) {
                    // New right node
                    Node newNode = new Node(node, value);
                    node.greaterNode = newNode;
                    size++;
                    break;
                } else {
                    node = node.greaterNode;
                }
            }
        }
    }

    public void remove(int value) {
        Node node = root;
        while (true) {
            if (node == null) {
                return;
            } else if (value == node.value) {
                Node parent = node.parentNode;
                if (parent == null) {
                    break;
                } else if (parent.lesserNode != null && parent.lesserNode == node) {
                    parent.lesserNode = null;
                    node = null;
                    size--;
                    break;
                } else {
                    parent.greaterNode = null;
                    node = null; 
                    size--;
                    break;
                }
            } else if (value < node.value) {
                node = node.lesserNode;
            } else {
                node = node.greaterNode;
            }
        }
    }
    
    public int[] sort(SEARCH_TYPE type) {
        this.type = type;
        return sort();
    }

    private int[] sort() {
        int[] nodes = new int[size];
        int index = 0;
        Node node = root;
        while (true) {
            if (node.lesserNode == null) {
                nodes[index++] = node.value;
                if (node.greaterNode != null) {
                    node.greaterNode.parentNode = node.parentNode;
                    node = node.greaterNode;
                } else if (node.parentNode == null) {
                    if (node.greaterNode != null) {
                        node = node.greaterNode;
                        node.parentNode = null;
                    } else if (node.greaterNode == null) {
                        break;
                    }
                } else {
                    node.parentNode.lesserNode = null;
                    node = node.parentNode;
                }
            } else {
                node = node.lesserNode;
            }
        }
        return nodes;
    }
    
    private void generateTree(int[] nodes) {
        int rootIndex = getRandom(nodes.length);
        int rootValue = nodes[rootIndex];
        root = new Node(null,rootValue);

        for (int i=0; i<nodes.length; i++) {
            if (i==rootIndex) continue;

            if (root==null) {
                root = new Node(null,rootValue);
            }
            
            int e = nodes[i];
            Node node = root;
            while (true) {
                if (e > node.value) {
                    if (node.greaterNode==null) {
                        node.greaterNode = new Node(node,e);
                        break;
                    }
                    node = node.greaterNode;
                } else {
                    if (node.lesserNode==null) {
                        node.lesserNode = new Node(node,e);
                        break;
                    }
                    node = node.lesserNode;
                }
            }
        }
    }

    private final int getRandom(int length) {
        if (type==SEARCH_TYPE.RANDOM && length>0) return RANDOM.nextInt(length);
        if (type==SEARCH_TYPE.FIRST && length>0) return 0;
        else return length/2;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int[] sorted = sort();
        for (int i : sorted) {
            builder.append(i).append(' ');
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
