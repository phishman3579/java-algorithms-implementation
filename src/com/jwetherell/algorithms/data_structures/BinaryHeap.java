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

    public BinaryHeap() { 
        root = null;
        size = 0;
    }
    
    public BinaryHeap(TYPE type) { 
        this();
        this.type = type;
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
    
    private int[] getDirections(int index) {
        int directionsSize = (int)(Math.log10(index+1)/Math.log10(2))-1;
        int[] directions = null;
        if (directionsSize>0) {
            directions = new int[directionsSize];
            int i = directionsSize-1;
            while (i>=0) {
                index = (index-1)/2;
                directions[i--] = (index>0 && index % 2==0)?1:0; // 0=left, 1=right
            }
        }
        return directions;
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
        int[] directions = getDirections(size); // size == index of new node
        if (directions!=null && directions.length>0) {
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
        heapUp(newNode);
    }
    
    public void remove(int value) {
        //Find the last node
        int[] directions = getDirections(size-1); // Directions to the last node
        Node lastNode = root;
        if (directions!=null && directions.length>0) {
            for (int d : directions) {
                if (d==0) {
                    //Go left
                    lastNode = lastNode.leftNode;
                } else {
                    //Go right
                    lastNode = lastNode.rightNode;
                }
            }
        }
        if (lastNode.rightNode!=null) {
            lastNode = lastNode.rightNode;
        } else {
            lastNode = lastNode.leftNode;
        }

        //Could not find the last node, strange
        if (lastNode==null) return;
        
        //Find the node to replace
        Node nodeToRemove = getNode(root, value);

        //Could not find the node to remove, strange
        if (nodeToRemove==null) return;

        //Replace the node to remove with the last node
        Node lastNodeParent = lastNode.parentNode;
        if (lastNodeParent!=null) {
            if (lastNodeParent.leftNode!=null && lastNodeParent.leftNode.equals(lastNode)) {
                lastNodeParent.leftNode = null;
            } else {
                lastNodeParent.rightNode = null;
            }
        }
        
        if (lastNode.equals(nodeToRemove)) {
            size--;
        } else {        
            Node nodeToRemoveParent = nodeToRemove.parentNode;
            if (nodeToRemoveParent!=null) {
                if (nodeToRemoveParent.leftNode!=null && nodeToRemoveParent.leftNode.equals(nodeToRemove)) {
                    nodeToRemoveParent.leftNode = lastNode;
                } else {
                    nodeToRemoveParent.rightNode = lastNode;
                }
            } else {
                root = lastNode;
            }
            lastNode.parentNode = nodeToRemoveParent;
            lastNode.leftNode = nodeToRemove.leftNode;
            if (lastNode.leftNode!=null) lastNode.leftNode.parentNode = lastNode;
            lastNode.rightNode = nodeToRemove.rightNode;
            if (lastNode.rightNode!=null) lastNode.rightNode.parentNode = lastNode;
            size--;
            heapDown(root);
        }
    }
    
    private Node getNode(Node startingNode, int value) {
        Node result = null;
        if (startingNode!=null && startingNode.value == value) {
            result = startingNode;
        } else if (startingNode!=null && startingNode.value != value) {
            Node left = startingNode.leftNode;
            if (left!=null) {
                result = getNode(left, value);
                if (result!=null) return result;
            }
            Node right = startingNode.rightNode;
            if (right!=null) {
                result = getNode(right, value);
                if (result!=null) return result;
            }
        }
        return result;
    }
    
    private void heapUp(Node node) {
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

    private void heapDown(Node node) {
        heapUp(node);
        Node left = node.leftNode;
        if (left!=null) heapDown(left);
        Node right = node.rightNode;
        if (right!=null) heapDown(right);
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
    
    public int getRootValue() {
        int result = Integer.MIN_VALUE;
        if (root!=null) result = root.value;
        return result;
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
