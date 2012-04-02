package com.jwetherell.algorithms.data_structures;


/**
 * A splay tree is a self-adjusting binary search tree (BST) with the additional property that recently accessed elements are quick to access again.
 * http://en.wikipedia.org/wiki/Splay_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SplayTree<T> extends BinarySearchTree<T> {

    public SplayTree() {
        super();
    }
    
    public SplayTree(Comparable<T>[] nodes) { 
        super(nodes);
    }

    @Override
    public void add(Comparable<T> value) {
        add(new Node<T>(null,value),true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Comparable<T> value) {
        Node<T> node = root;
        while (node!=null) {
            if (value.compareTo((T)node.value) == 0) {
                //Splay the Node which will move it up the tree at most two Nodes in height.
                splay(node);
                return true;
            } else if (value.compareTo((T)node.value) < 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }
        return false;
    }

    private void splay(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> grandParent = (parent!=null)?parent.parent:null;
        if (parent==root) {
            //Zig step
            root = node;
            node.parent = null;

            if (node==parent.lesser) {
                parent.lesser = node.greater;
                if (node.greater!=null) node.greater.parent = parent;
                
                node.greater = parent;
                parent.parent = node;
            } else {
                parent.greater = node.lesser;
                if (node.lesser!=null) node.lesser.parent = parent;
                
                node.lesser = parent;
                parent.parent = node;
            }
        } else if (parent!=null && grandParent!=null) {
            Node<T> greatGrandParent = grandParent.parent;
            if (greatGrandParent!=null && greatGrandParent.lesser==grandParent) {
                greatGrandParent.lesser = node;
                node.parent = greatGrandParent;
            } else if (greatGrandParent!=null && greatGrandParent.greater==grandParent) {
                greatGrandParent.greater = node;
                node.parent = greatGrandParent;
            } else {
                //I am root!
                root = node;
                node.parent = null;
            }
            
            if ((node==parent.lesser && parent==grandParent.lesser) || (node==parent.greater && parent==grandParent.greater)) {
              //Zig-zig step
              if (node==parent.lesser) {
                  Node<T> nodeGreater = node.greater;
                  node.greater = parent;
                  parent.parent = node;
                  
                  parent.lesser = nodeGreater;
                  if (nodeGreater!=null) nodeGreater.parent = parent;

                  Node<T> parentGreater = parent.greater;
                  parent.greater = grandParent;
                  grandParent.parent = parent;
                  
                  grandParent.lesser = parentGreater;
                  if (parentGreater!=null) parentGreater.parent = grandParent;
              } else {
                  Node<T> nodeLesser = node.lesser;
                  node.lesser = parent;
                  parent.parent = node;
                  
                  parent.greater = nodeLesser;
                  if (nodeLesser!=null) nodeLesser.parent = parent;

                  Node<T> parentLesser = parent.lesser;
                  parent.lesser = grandParent;
                  grandParent.parent = parent;
                  
                  grandParent.greater = parentLesser;
                  if (parentLesser!=null) parentLesser.parent = grandParent;
              }
            } else {
                //Zig-zag step
                if (node==parent.lesser) {
                    Node<T> nodeLesser = node.greater;
                    Node<T> nodeGreater = node.lesser;
    
                    node.greater = parent;
                    parent.parent = node;
                    
                    node.lesser = grandParent;
                    grandParent.parent = node;
                    
                    parent.lesser = nodeLesser;
                    if (nodeLesser!=null) nodeLesser.parent = parent;

                    grandParent.greater = nodeGreater;
                    if (nodeGreater!=null) nodeGreater.parent = grandParent;
                } else {
                    Node<T> nodeLesser = node.lesser;
                    Node<T> nodeGreater = node.greater;
    
                    node.lesser = parent;
                    parent.parent = node;
                    
                    node.greater = grandParent;
                    grandParent.parent = node;
                    
                    parent.greater = nodeLesser;
                    if (nodeLesser!=null) nodeLesser.parent = parent;

                    grandParent.lesser = nodeGreater;
                    if (nodeGreater!=null) nodeGreater.parent = grandParent;
                }
            }
        }
    }
}
