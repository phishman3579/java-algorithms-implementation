package com.jwetherell.algorithms.sorts;

import java.util.Random;


public class BinarySearchTreeSort {
    private static final Random RANDOM = new Random();
    
    private static int[] unsorted = null;
    private static Node root = null;

    public static enum ROOT_TYPE { FIRST, MIDDLE, RANDOM };
    public static ROOT_TYPE type = ROOT_TYPE.RANDOM;
    
    private BinarySearchTreeSort() { }
    
    public static int[] sort(ROOT_TYPE type, int[] unsorted) {
        BinarySearchTreeSort.unsorted = unsorted;
        BinarySearchTreeSort.type = type;
        BinarySearchTreeSort.root = null;
        
        generateTree();
        sort();

        return BinarySearchTreeSort.unsorted;
    }
    
    private static void sort() {
        int i = 0;
        Node node = root;
        while (true) {
            if (node.lesserNode==null) {
                unsorted[i++] = node.value;
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
            }
            if (node.lesserNode != null) {
                node = node.lesserNode;
            }
        }
    }
    
    private static void generateTree() {
        int rootIndex = getRandom(unsorted.length);
        int rootValue = unsorted[rootIndex];
        root = new Node(null,rootValue);

        for (int i=0; i<unsorted.length; i++) {
            if (i==rootIndex) continue;

            if (root==null) {
                root = new Node(null,rootValue);
            }
            
            int e = unsorted[i];
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

    private static final int getRandom(int length) {
        if (type==ROOT_TYPE.RANDOM && length>0) return RANDOM.nextInt(length);
        if (type==ROOT_TYPE.FIRST && length>0) return 0;
        else return length/2;
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
