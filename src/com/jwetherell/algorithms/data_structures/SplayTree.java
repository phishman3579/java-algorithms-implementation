package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * A splay tree is a self-adjusting binary search tree with the additional property that recently accessed elements are quick to access again.
 * http://en.wikipedia.org/wiki/Splay_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SplayTree<T> {

    private Node<T> root = null;
    private int size = 0;

    public SplayTree() { }
    
    public SplayTree(Comparable<T>[] nodes) { 
        populateTree(nodes);
    }

    public void add(Comparable<T> value) {
        add(new Node<T>(null,value),true);
    }

    @SuppressWarnings("unchecked")
    public boolean contains(Comparable<T> value) {
        Node<T> node = root;
        while (node!=null) {
            if (value.compareTo((T)node.value) == 0) {
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
    
    @SuppressWarnings("unchecked")
    private void add(Node<T> newNode, boolean adjustSize) {
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

    private void populateTree(Comparable<T>[] nodes) {
        for (Comparable<T> node : nodes) {
            Node<T> newNode = new Node<T>(null,node);
            add(newNode,true);
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
    
    private static class Node<T> {
        private Comparable<T> value = null;
        private Node<T> parent = null;
        private Node<T> lesser = null;
        private Node<T> greater = null;
        
        private Node(Node<T> parent, Comparable<T> value) {
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
