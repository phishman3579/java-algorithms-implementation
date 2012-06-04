package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * An AVL tree is a self-balancing binary search tree, and it was the first such data 
 * structure to be invented. In an AVL tree, the heights of the two child subtrees 
 * of any node differ by at most one. AVL trees are often compared with red-black trees 
 * because they support the same set of operations and because red-black trees also take 
 * O(log n) time for the basic operations. Because AVL trees are more rigidly balanced, 
 * they are faster than red-black trees for lookup intensive applications. However, 
 * red-black trees are faster for insertion and removal.
 * 
 * http://en.wikipedia.org/wiki/AVL_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class AVLTree2<T extends Comparable<T>> extends BinarySearchTree<T> {

    private enum Balance { LEFT_LEFT, LEFT_RIGHT, RIGHT_LEFT, RIGHT_RIGHT }; 


    @Override
    protected Node<T> addValue(T value) {
        AVLNode<T> nodeToAdd = new AVLNode<T>(null,value);
        super.add(nodeToAdd);
        nodeToAdd.updateHeight(true);

        while (nodeToAdd!=null) {
            balanceAtNode(nodeToAdd);
            nodeToAdd = (AVLNode<T>) nodeToAdd.parent;
        }

        return nodeToAdd;
    }
/*
    private void balanceAtNode(AVLNode<T> newNode) {
        AVLNode<T> parent = (AVLNode<T>) newNode.parent;
        if (parent!=null && parent.parent!=null) {
            AVLNode<T> grandParent = (AVLNode<T>) parent.parent;
            //Only balance if height > 2
            int balanceFactor = grandParent.getBalanceFactor();
            if (balanceFactor>1 || balanceFactor<-1) {
                //Unbalanced
                Balance balance = null;

                if (grandParent.lesser!=null && grandParent.lesser.equals(parent)) {
                    if (parent.lesser!=null && parent.lesser.equals(newNode)) {
                        balance = Balance.LEFT_LEFT;
                    } else {
                        balance = Balance.LEFT_RIGHT;
                    }    
                } else {
                    if (parent.lesser!=null && parent.lesser.equals(newNode)) {
                        balance = Balance.RIGHT_LEFT;
                    } else {
                        balance = Balance.RIGHT_RIGHT;
                    }    
                }

                if (balance == Balance.LEFT_RIGHT) {
                    //Switch parent's greater with parent
                    AVLNode<T> greaterNode = (AVLNode<T>) parent.greater;
                    grandParent.lesser = greaterNode;
                    greaterNode.parent = grandParent;
                    
                    //Save lesser
                    Node<T> lesser = greaterNode.lesser;

                    greaterNode.lesser = parent;
                    parent.parent = greaterNode;

                    parent.greater = lesser;
                    if (lesser!=null) lesser.parent = parent;

                    newNode = parent;
                    parent = (AVLNode<T>) newNode.parent;
                    balance = Balance.LEFT_LEFT;
                } else if (balance == Balance.RIGHT_LEFT) {
                    //Switch parent's lesser with parent
                    AVLNode<T> lesserNode = (AVLNode<T>) parent.lesser;
                    grandParent.greater = lesserNode;
                    lesserNode.parent = grandParent;
                    
                    //Save lesser
                    Node<T> greater = lesserNode.greater;

                    lesserNode.greater = parent;
                    parent.parent = lesserNode;

                    parent.lesser = greater;
                    if (greater!=null) greater.parent = parent;
                    
                    newNode = parent;
                    parent = (AVLNode<T>) newNode.parent;
                    balance = Balance.RIGHT_RIGHT;
                }

                AVLNode<T> greatGrandParent = (AVLNode<T>) grandParent.parent;
                if (greatGrandParent!=null) {
                    if (greatGrandParent.lesser!=null && greatGrandParent.lesser.equals(grandParent)) {
                        greatGrandParent.lesser = parent;
                        parent.parent = greatGrandParent;
                    } else {
                        greatGrandParent.greater = parent;
                        parent.parent = greatGrandParent;
                    }
                } else {
                    //grandParent is root, make parent new root
                    root = parent;
                    parent.parent = null;
                }

                if (balance == Balance.LEFT_LEFT) {
                    AVLNode<T> greaterNode = (AVLNode<T>) parent.greater;
                    parent.greater = grandParent;
                    grandParent.parent = parent;
                    
                    grandParent.lesser = greaterNode;
                    if (greaterNode!=null) greaterNode.parent = grandParent;
                } else {
                    //Right-Right
                    AVLNode<T> lesserNode = (AVLNode<T>) parent.lesser;
                    parent.lesser = grandParent;
                    grandParent.parent = parent;
                    
                    grandParent.greater = lesserNode;
                    if (lesserNode!=null) lesserNode.parent = grandParent;
                }
                grandParent.updateHeight(false); //New child node
                newNode.updateHeight(false); //New child node
                parent.updateHeight(true); //New Parent node
            }
        }
    }
*/

    @Override
    protected Node<T> removeValue(T value) {
        System.out.println("Removing "+value+"\n"+this.toString());
        //Remove node
        Node<T> nodeRemoved = root;
        AVLNode<T> nodeToMoveUp = null;
        AVLNode<T> parentOfReplacement = null;
        while (nodeRemoved != null) {
            if (value.compareTo(nodeRemoved.value) < 0) {
                // Node to remove is less than current node
                nodeRemoved = nodeRemoved.lesser;
            } else if (value.compareTo(nodeRemoved.value) > 0) {
                // Node to remove is greater then current node
                nodeRemoved = nodeRemoved.greater;
            } else if (value.compareTo(nodeRemoved.value) == 0) {
                // We found our node or the first occurrence of our node
                Node<T> parent = nodeRemoved.parent;
                if (parent == null) {
                    // Replacing the root node
                    if (nodeRemoved.lesser != null && nodeRemoved.greater == null) {
                        // Replace root with lesser subtree
                        root = nodeRemoved.lesser;
                        parentOfReplacement = (AVLNode<T>) root.parent;

                        // Root not should not have a parent
                        root.parent = null;
                    } else if (nodeRemoved.greater != null && nodeRemoved.lesser==null) {
                        // Replace root with greater subtree
                        root = nodeRemoved.greater;
                        parentOfReplacement = (AVLNode<T>) root.parent;

                        // Root not should not have a parent
                        root.parent = null;
                    } else if (nodeRemoved.greater != null && nodeRemoved.lesser!=null) {
                        //Two children
                        nodeToMoveUp = (AVLNode<T>) this.getLeast(nodeRemoved.greater);
                        Node<T> greater = null;
                        if (nodeToMoveUp==null) {
                            nodeToMoveUp = (AVLNode<T>) nodeRemoved.greater;

                            parentOfReplacement = (AVLNode<T>) nodeToMoveUp;
                        } else {
                            greater = nodeToMoveUp.greater;
                            nodeToMoveUp.parent.lesser = greater;
                            if (greater!=null) greater.parent = nodeToMoveUp.parent;

                            nodeToMoveUp.greater = nodeRemoved.greater;
                            nodeRemoved.greater.parent = nodeToMoveUp;

                            parentOfReplacement = (AVLNode<T>) nodeToMoveUp.parent;
                        }

                        nodeToMoveUp.lesser = nodeRemoved.lesser;
                        nodeRemoved.lesser.parent = nodeToMoveUp;
                        
                        root = nodeToMoveUp;
                        root.parent = null;
                    } else {
                        // No children...
                        root = null;
                    }
                } else if (parent.lesser != null && (parent.lesser.value.compareTo(nodeRemoved.value) == 0)) {
                    // If the node to remove is the parent's lesser node, replace
                    // the parent's lesser node with one of the node to remove's
                    // lesser/greater subtrees
                    if (nodeRemoved.lesser != null && nodeRemoved.greater == null) {
                        // Using the less subtree
                        nodeToMoveUp = (AVLNode<T>) nodeRemoved.lesser;
                        parentOfReplacement = (AVLNode<T>) nodeToMoveUp.parent;

                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else if (nodeRemoved.greater != null && nodeRemoved.lesser == null) {
                        // Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = (AVLNode<T>) nodeRemoved.greater;
                        parentOfReplacement = (AVLNode<T>) nodeToMoveUp.parent;

                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else if (nodeRemoved.greater != null && nodeRemoved.lesser!=null) {
                        //Two children
                        nodeToMoveUp = (AVLNode<T>) this.getLeast(nodeRemoved.greater);
                        Node<T> greater = null;
                        if (nodeToMoveUp==null) {
                            nodeToMoveUp = (AVLNode<T>) nodeRemoved.greater;
                            
                            parentOfReplacement = (AVLNode<T>) nodeToMoveUp;
                        } else {
                            greater = nodeToMoveUp.greater;
                            nodeToMoveUp.parent.lesser = greater;
                            if (greater!=null) greater.parent = nodeToMoveUp.parent;

                            nodeToMoveUp.greater = nodeRemoved.greater;
                            nodeRemoved.greater.parent = nodeToMoveUp;

                            parentOfReplacement = (AVLNode<T>) nodeToMoveUp.parent;
                        }

                        nodeToMoveUp.lesser = nodeRemoved.lesser;
                        nodeRemoved.lesser.parent = nodeToMoveUp;
                        
                        parent.lesser = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else {
                        // No children...
                        parent.lesser = null;
                        parentOfReplacement = (AVLNode<T>) parent;
                    }
                } else if (parent.greater != null && (parent.greater.value.compareTo(nodeRemoved.value) == 0)) {
                    // If the node to remove is the parent's greater node, replace
                    // the parent's greater node with the node's greater node
                    if (nodeRemoved.lesser != null && nodeRemoved.greater == null) {
                        // Using the less subtree
                        nodeToMoveUp = (AVLNode<T>) nodeRemoved.lesser;
                        parentOfReplacement = (AVLNode<T>) nodeToMoveUp.parent;

                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else if (nodeRemoved.greater != null && nodeRemoved.lesser == null) {
                        // Using the greater subtree (there is no lesser subtree, no refactoring)
                        nodeToMoveUp = (AVLNode<T>) nodeRemoved.greater;
                        
                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                        parentOfReplacement = (AVLNode<T>) parent;
                    } else if (nodeRemoved.greater != null && nodeRemoved.lesser!=null) {
                        //Two children
                        nodeToMoveUp = (AVLNode<T>) this.getGreatest(nodeRemoved.lesser);
                        Node<T> lesser = null;
                        if (nodeToMoveUp==null) {
                            nodeToMoveUp = (AVLNode<T>) nodeRemoved.lesser;
                            parentOfReplacement = (AVLNode<T>) nodeToMoveUp;
                        } else {
                            lesser = nodeToMoveUp.lesser;
                            nodeToMoveUp.parent.greater = lesser;
                            if (lesser!=null) lesser.parent = nodeToMoveUp.parent;

                            nodeToMoveUp.lesser = nodeRemoved.lesser;
                            nodeRemoved.lesser.parent = nodeToMoveUp;
                            parentOfReplacement = (AVLNode<T>) nodeToMoveUp.parent;
                        }

                        nodeToMoveUp.greater = nodeRemoved.greater;
                        nodeRemoved.greater.parent = nodeToMoveUp;
                        
                        parent.greater = nodeToMoveUp;
                        nodeToMoveUp.parent = parent;
                    } else {
                        // No children...
                        parent.greater = null;
                        parentOfReplacement = (AVLNode<T>) parent;
                    }
                }
                size--;
                break;
            }
        }
        if (parentOfReplacement!=null) {
            if (parentOfReplacement.lesser!=null) 
                ((AVLNode<T>)parentOfReplacement.lesser).updateHeight(false);
            if (parentOfReplacement.greater!=null) 
                ((AVLNode<T>)parentOfReplacement.greater).updateHeight(false);
            ((AVLNode<T>)parentOfReplacement).updateHeight(true);
        }

        nodeToMoveUp = parentOfReplacement;
        if (nodeToMoveUp!=null) {
            while (nodeToMoveUp!=null) {
                balanceAtNode(nodeToMoveUp);
                nodeToMoveUp = (AVLNode<T>) nodeToMoveUp.parent;
            }
        }

        return nodeRemoved;
    }

    private void balanceAtNode(AVLNode<T> node) {
        AVLNode<T> grandParent = (AVLNode<T>) node;
        //Only balance if height > 2
        int balanceFactor = grandParent.getBalanceFactor();
        if (balanceFactor>1 || balanceFactor<-1) {
            AVLNode<T> parent = null;
            if (balanceFactor<0) {
                parent = (AVLNode<T>) grandParent.lesser;
            } else {
                parent = (AVLNode<T>) grandParent.greater;
            }

            balanceFactor = parent.getBalanceFactor();
            AVLNode<T> newNode = null;
            if (balanceFactor<0) {
                newNode = (AVLNode<T>) parent.lesser;
            } else {
                newNode = (AVLNode<T>) parent.greater;
            }
            
            //Unbalanced?
            Balance balance = null;

            if (grandParent.lesser!=null && grandParent.lesser.equals(parent)) {
                if (parent.lesser!=null && parent.lesser.equals(newNode)) {
                    balance = Balance.LEFT_LEFT;
                } else {
                    balance = Balance.LEFT_RIGHT;
                }    
            } else {
                if (parent.lesser!=null && parent.lesser.equals(newNode)) {
                    balance = Balance.RIGHT_LEFT;
                } else {
                    balance = Balance.RIGHT_RIGHT;
                }    
            }

            if (balance == Balance.LEFT_RIGHT) {
                //Switch parent's greater with parent
                leftRotate(parent,parent.greater);

                /*
                AVLNode<T> greaterNode = (AVLNode<T>) parent.greater;
                grandParent.lesser = greaterNode;
                greaterNode.parent = grandParent;

                //Save lesser
                Node<T> lesser = greaterNode.lesser;

                greaterNode.lesser = parent;
                parent.parent = greaterNode;

                parent.greater = lesser;
                if (lesser!=null) lesser.parent = parent;
                */

                newNode = parent;
                parent = (AVLNode<T>) newNode.parent;
                balance = Balance.LEFT_LEFT;
            } else if (balance == Balance.RIGHT_LEFT) {
                //Switch parent's lesser with parent
                /*
                AVLNode<T> lesserNode = (AVLNode<T>) parent.lesser;
                grandParent.greater = lesserNode;
                lesserNode.parent = grandParent;

                //Save greater
                Node<T> greater = lesserNode.greater;

                lesserNode.greater = parent;
                parent.parent = lesserNode;

                parent.lesser = greater;
                if (greater!=null) greater.parent = parent;
                */
                rightRotate(parent,parent.lesser);

                newNode = parent;
                parent = (AVLNode<T>) newNode.parent;
                balance = Balance.RIGHT_RIGHT;
            }

            AVLNode<T> greatGrandParent = (AVLNode<T>) grandParent.parent;
            if (greatGrandParent!=null) {
                if (greatGrandParent.lesser!=null && greatGrandParent.lesser.equals(grandParent)) {
                    greatGrandParent.lesser = parent;
                    parent.parent = greatGrandParent;
                } else {
                    greatGrandParent.greater = parent;
                    parent.parent = greatGrandParent;
                }
            } else {
                //grandParent is root, make parent new root
                root = parent;
                parent.parent = null;
            }

            if (balance == Balance.LEFT_LEFT) {
                //Left-Left
                /*
                AVLNode<T> greater = (AVLNode<T>) parent.greater;
                
                parent.greater = grandParent;
                grandParent.parent = parent;

                grandParent.lesser = greater;
                if (greater!=null) greater.parent = grandParent;
                */
                rightRotate(grandParent,parent);
            } else {
                //Right-Right
                /*
                AVLNode<T> lesser = (AVLNode<T>) parent.lesser;

                parent.lesser = grandParent;
                grandParent.parent = parent;

                grandParent.greater = lesser;
                if (lesser!=null) lesser.parent = grandParent;
                */
                leftRotate(grandParent,parent);
            }
            grandParent.updateHeight(false); //New child node
            newNode.updateHeight(false); //New child node
            parent.updateHeight(true); //New Parent node
        }
    }

    private void leftRotate(Node<T> parent, Node<T> node) {
        //Save lesser
        Node<T> lesser = node.lesser;

        node.lesser = parent;
        parent.parent = node;

        parent.greater = lesser;
        if (lesser!=null) lesser.parent = parent;
    }

    private void rightRotate(Node<T> parent, Node<T> node) {
        //Save greater
        Node<T> greater = node.greater;

        node.greater = parent;
        parent.parent = node;

        parent.lesser = greater;
        if (greater!=null) greater.parent = parent;
    }

    @Override
    protected boolean validateNode(Node<T> node) {
        boolean bst = super.validateNode(node);
        if (!bst) return false;

        AVLNode<T> avlNode = (AVLNode<T>) node;
        int balanceFactor = avlNode.getBalanceFactor();
        if (balanceFactor>1 || balanceFactor<-1) {
            System.err.println("node="+node.value+" balanceFactor="+balanceFactor);
            return false;
        }
        if (avlNode.isLeaf() && avlNode.height!=1) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return AVLTreePrinter.getString(this);
    }


    protected static class AVLNode<T extends Comparable<T>> extends Node<T> {

        protected int height = 1;

        protected AVLNode(Node<T> parent, T value) {
            super(parent,value);
        }

        protected boolean isLeaf() {
            return ((lesser == null) && (greater == null));
        }
        
        protected void updateHeight(boolean recurseUpTheTree) {
            int lesserHeight = 0;
            int greaterHeight = 0;
            if (lesser != null) {
                AVLNode<T> lesserAVLNode = (AVLNode<T>) lesser;
                lesserHeight = lesserAVLNode.height;
            }
            if (greater != null) {
                AVLNode<T> greaterAVLNode = (AVLNode<T>) greater;
                greaterHeight = greaterAVLNode.height;
            }
            
            if (lesserHeight>greaterHeight) {
                height = lesserHeight+1;
            } else {
                height = greaterHeight+1;
            }
            
            if (recurseUpTheTree && parent!=null) {
                AVLNode<T> parentAVLNode = (AVLNode<T>) parent;
                parentAVLNode.updateHeight(true);
            }
        }

        protected int getBalanceFactor() {
            int lesserHeight = 0;
            int greaterHeight = 0;
            if (lesser != null) {
                AVLNode<T> lesserAVLNode = (AVLNode<T>) lesser;
                lesserHeight = lesserAVLNode.height;
            }
            if (greater != null) {
                AVLNode<T> greaterAVLNode = (AVLNode<T>) greater;
                greaterHeight = greaterAVLNode.height;
            }
            return greaterHeight-lesserHeight;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + 
                   " height=" + height + 
                   " parent=" + ((parent != null) ? parent.value : "NULL") + 
                   " lesser=" + ((lesser != null) ? lesser.value : "NULL") + 
                   " greater=" + ((greater != null) ? greater.value : "NULL");
        }
    }

    protected static class AVLTreePrinter {

        public static <T extends Comparable<T>> String getString(AVLTree2<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString((AVLNode<T>)tree.root, "", true);
        }

        public static <T extends Comparable<T>> String getString(AVLNode<T> node) {
            if (node == null) return "Sub-tree has no nodes.";
            return getString(node, "", true);
        }

        private static <T extends Comparable<T>> String getString(AVLNode<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + node.height + ") " + node.value + "\n");
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.lesser != null) children.add(node.lesser);
                if (node.greater != null) children.add(node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString((AVLNode<T>)children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString((AVLNode<T>)children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
