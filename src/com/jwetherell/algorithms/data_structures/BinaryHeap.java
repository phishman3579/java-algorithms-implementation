package com.jwetherell.algorithms.data_structures;


/**
 * Binary Heap.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BinaryHeap {

    private Node root = null;
    private int size = 0;
    
    public enum TYPE {MIN,MAX};
    private TYPE type = TYPE.MIN;
    
    public BinaryHeap(TYPE type) { 
        this();
        this.type = type;
    }

    public BinaryHeap() { 
        root = null;
        size = 0;
    }
    
    public BinaryHeap(int[] nodes) { 
        this();
        populate(nodes);
    }
    
    public BinaryHeap(int[] nodes, TYPE type) { 
        this(type);
        populate(nodes);
    }

    private void populate(int[] nodes) {
        for (int node : nodes) {
            add(new Node(null,node));
        }
    }
    
    public void add(int value) {
        add(new Node(null,value));
    }
    
    private void add(Node newNode) {
        if (root==null) {
            root = newNode;
            size++;
            return;
        }

        Node node = root;
        int directionsSize = (int)(Math.log10(size+1)/Math.log10(2))-1;
        int[] directions = null;
        if (directionsSize>0) {
            directions = new int[directionsSize];
            int i = directionsSize-1;
            int index = size;
            while (i>=0) {
                index = (index-1)/2;
                directions[i--] = (index>0 && index % 2==0)?1:0; // 0=left, 1=right
            }
    
            for (int d : directions) {
                if (d==0) {
                    //Go left
                    node = node.leftNode;
                } else {
                    //Go right
                    node = node.rightNode;
                }
            }
        }
        if (node.leftNode==null) {
            node.leftNode = newNode;
        } else {
            node.rightNode = newNode;
        }
        newNode.parentNode = node;
        size++;
        heapify(newNode);
    }

    private void heapify(Node node) {
        while (node != null) {
            Node parent = node.parentNode;
            
            int compare = (type == TYPE.MIN)?-1:1;
            if (parent!=null && node.value.compareTo(parent.value) == compare) {
                //Node is less than parent, switch node with parent
                Node grandParent = parent.parentNode;
                Node parentLeft = parent.leftNode;
                Node parentRight = parent.rightNode;
                
                parent.leftNode = node.leftNode;
                if (parent.leftNode!=null) parent.leftNode.parentNode = parent;
                parent.rightNode = node.rightNode;
                if (parent.rightNode!=null) parent.rightNode.parentNode = parent;
                
                if (parentLeft!=null && parentLeft.equals(node)) {
                    node.leftNode = parent;
                    node.rightNode = parentRight;
                    if (parentRight!=null) parentRight.parentNode = node;
                } else {
                    node.rightNode = parent;
                    node.leftNode = parentLeft;
                    if (parentLeft!=null) parentLeft.parentNode = node;
                }
                parent.parentNode = node;

                if (grandParent==null) {
                    //New root.
                    node.parentNode = null;
                    root = node;
                } else {
                    Node grandLeft = grandParent.leftNode;
                    if (grandLeft!=null && grandLeft.equals(parent)) {
                        grandParent.leftNode = node;
                    } else {
                        grandParent.rightNode = node;
                    }
                    node.parentNode = grandParent;
                }
            } else {
                node = node.parentNode;
            }
        }
    }
    
    private void getNodeValue(Node node, int index, int[] array) {
        array[index] = node.value;
        index = (index*2)+1;

        Node left = node.leftNode;
        if (left!=null) getNodeValue(left,index,array);
        Node right = node.rightNode;
        if (right!=null) getNodeValue(right,index+1,array);
    }

    public int[] getHeap() {
        int[] nodes = new int[size];
        getNodeValue(root,0,nodes);
        return nodes;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int[] heap = getHeap();
        for (int node : heap) {
            builder.append(node).append(", ");
        }
        return builder.toString();
    }

    private static class Node {
        private Integer value = null;
        private Node parentNode = null;
        private Node leftNode = null;
        private Node rightNode = null;
        
        private Node(Node parent, int value) {
            this.parentNode = parent;
            this.value = value;
        }

        public String toString() {
            return "value="+value+
                   " parent="+((parentNode!=null)?parentNode.value:"NULL")+
                   " left="+((leftNode!=null)?leftNode.value:"NULL")+
                   " right="+((rightNode!=null)?rightNode.value:"NULL");
        }
    }
}
