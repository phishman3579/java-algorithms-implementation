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
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected enum Balance { LEFT_LEFT, LEFT_RIGHT, RIGHT_LEFT, RIGHT_RIGHT }; 
    
    @Override
    public void add(T value) {
        AVLNode<T> newNode = new AVLNode<T>(null,value);
        
        if (root == null) {
            root = newNode;
            size++;
            return;
        }
        
        //Add node
        boolean added = false;
        AVLNode<T> node = (AVLNode<T>) root;
        while (node != null) {
            if (newNode.value.compareTo(node.value) <= 0) {
                if (node.lesser == null) {
                    // New left node
                    node.lesser = newNode;
                    newNode.parent = node;
                    node.updateHeight(true);
                    added = true;
                    break;
                } else {
                    node = (AVLNode<T>) node.lesser;
                }
            } else if (node.greater == null) {
                // New right node
                node.greater = newNode;
                newNode.parent = node;
                node.updateHeight(true);
                added = true;
                break;
            } else {
                node = (AVLNode<T>) node.greater;
            }
        }

        //Balance tree
        if (added) {
            size++;
            while (newNode!=null) {
                balanceAtNode(newNode);
                newNode = (AVLNode<T>) newNode.parent;
            }
        }
    }
    
    private void balanceAtNode(AVLNode<T> newNode) {
        AVLNode<T> parent = (AVLNode<T>) newNode.parent;
        if (parent!=null && parent.parent!=null) {
            AVLNode<T> grandParent = (AVLNode<T>) parent.parent;
            //Only balance if height > 2
            int balanceFactor = grandParent.getBalanceFactor();
            if ((balanceFactor>1) || (balanceFactor<-1)) {
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
                grandParent.updateHeight(false);
                newNode.updateHeight(false);
                parent.updateHeight(true);
            }
        }
    }

    @Override
    public boolean remove(T value) {
        //Remove node
        boolean removed = false;
        AVLNode<T> node = (AVLNode<T>) root;
        while (node != null) {
            if (value.compareTo(node.value) < 0) {
                // Node to remove is less than current node
                node = (AVLNode<T>) node.lesser;
            } else if (value.compareTo(node.value) > 0) {
                // Node to remove is greater then current node
                node = (AVLNode<T>) node.greater;
            } else if (value.compareTo(node.value) == 0) {
                // We found our node or the first occurrence of our node
                AVLNode<T> parent = (AVLNode<T>) node.parent;
                if (parent==null) {
                    //Removing root node
                    if (node.isLeaf()) {
                        //Node children, no worries
                        root = null;
                    } else if (node.lesser!=null && node.greater==null) {
                        //One child which is the lesser
                        root = node.lesser;
                        root.parent = null;
                        ((AVLNode<T>)root).updateHeight(true);
                        node = (AVLNode<T>) root;
                    } else if (node.lesser==null && node.greater!=null) {
                        //One child which is the greater
                        root = node.greater;
                        root.parent = null;
                        ((AVLNode<T>)root).updateHeight(true);
                        node = (AVLNode<T>) root;
                    } else {
                        AVLNode<T> greater = (AVLNode<T>) node.greater;
                        AVLNode<T> lesser = (AVLNode<T>) node.lesser;

                        Node<T> leastInGreater = this.getLeast(greater);
                        if (leastInGreater!=null) {
                            leastInGreater.parent.lesser = null;
                            if (leastInGreater.greater!=null) {
                                leastInGreater.parent.lesser = leastInGreater.greater;
                                leastInGreater.greater.parent = leastInGreater.parent;
                            }

                            root = leastInGreater;
                            root.parent = null;
                            
                            root.lesser = lesser;
                            lesser.parent = root;

                            root.greater = greater;
                            greater.parent = root;

                            ((AVLNode<T>)lesser).updateHeight(false);
                            ((AVLNode<T>)greater).updateHeight(false);
                            ((AVLNode<T>)root).updateHeight(true);
                            
                            node = (AVLNode<T>) lesser;
                        } else {
                            root = greater;
                            root.parent = null;
                            
                            root.lesser = lesser;
                            lesser.parent = root;
                            ((AVLNode<T>)greater).updateHeight(true);
                            
                            node = (AVLNode<T>) lesser;
                        }
                    }
                } else {
                    if (parent.lesser!=null && parent.lesser.equals(node)) {
                        if (node.isLeaf()) {
                            //Node children, no worries
                            parent.lesser = null;
                            parent.updateHeight(true);
                            node = (AVLNode<T>) parent;
                        } else if (node.lesser!=null && node.greater==null) {
                            //One child which is the lesser
                            parent.lesser = node.lesser;
                            node.lesser.parent = parent;
                            ((AVLNode<T>)node.lesser).updateHeight(true);
                            
                            node = (AVLNode<T>) node.lesser;
                        } else if (node.lesser==null && node.greater!=null) {
                            //One child which is the greater
                            parent.lesser = node.greater;
                            node.greater.parent = parent;
                            ((AVLNode<T>)node.greater).updateHeight(true);
                            
                            node = (AVLNode<T>) node.greater;
                        } else {
                            AVLNode<T> greater = (AVLNode<T>) node.greater;
                            AVLNode<T> lesser = (AVLNode<T>) node.lesser;

                            Node<T> greatestInLesser = this.getGreatest(lesser);
                            if (greatestInLesser!=null) {
                                greatestInLesser.parent.greater = null;
                                if (greatestInLesser.lesser!=null) {
                                    greatestInLesser.parent.greater = greatestInLesser.lesser;
                                    greatestInLesser.lesser.parent = greatestInLesser.parent;
                                }

                                parent.lesser = greatestInLesser;
                                greatestInLesser.parent = parent;

                                greatestInLesser.lesser = lesser;
                                lesser.parent = greatestInLesser;

                                greatestInLesser.greater = greater;
                                greater.parent = greatestInLesser;

                                ((AVLNode<T>)lesser).updateHeight(false);
                                ((AVLNode<T>)greater).updateHeight(false);
                                ((AVLNode<T>)greatestInLesser).updateHeight(true);
                                
                                node = (AVLNode<T>) lesser;
                            } else {
                                parent.lesser = lesser;
                                lesser.parent = parent;
                                
                                lesser.greater = greater;
                                greater.parent = lesser;
                                
                                ((AVLNode<T>)greater).updateHeight(false);
                                ((AVLNode<T>)lesser).updateHeight(true);
                                
                                node = (AVLNode<T>) greater;
                            }
                        }
                    } else {
                        //Greater
                        if (node.isLeaf()) {
                            //Node children, no worries
                            parent.greater = null;
                            parent.updateHeight(true);
                            node = (AVLNode<T>) parent;
                        } else if (node.lesser!=null && node.greater==null) {
                            //One child which is the lesser
                            parent.greater = node.lesser;
                            node.lesser.parent = parent;
                            ((AVLNode<T>)node.lesser).updateHeight(true);
                            node = (AVLNode<T>) node.lesser;
                        } else if (node.lesser==null && node.greater!=null) {
                            //One child which is the greater
                            parent.greater = node.greater;
                            node.greater.parent = parent;
                            ((AVLNode<T>)node.greater).updateHeight(true);
                            node = (AVLNode<T>) node.greater;
                        } else {
                            AVLNode<T> lesser = (AVLNode<T>) node.lesser;
                            AVLNode<T> greater = (AVLNode<T>) node.greater;

                            Node<T> leastInGreater = this.getLeast(greater);
                            if (leastInGreater!=null) {
                                leastInGreater.parent.lesser = null;
                                if (leastInGreater.greater!=null) {
                                    leastInGreater.parent.lesser = leastInGreater.greater;
                                    leastInGreater.greater.parent = leastInGreater.parent;
                                }
                                
                                parent.greater = leastInGreater;
                                leastInGreater.parent = parent;

                                leastInGreater.lesser = lesser;
                                lesser.parent = leastInGreater;

                                leastInGreater.greater = greater;
                                greater.parent = leastInGreater;

                                ((AVLNode<T>)greater).updateHeight(false);
                                ((AVLNode<T>)lesser).updateHeight(false);
                                ((AVLNode<T>)leastInGreater).updateHeight(true);

                                node = (AVLNode<T>) greater;
                            } else {
                                parent.greater = greater;
                                greater.parent = parent;
                                
                                greater.lesser = lesser;
                                lesser.parent = greater;
                                ((AVLNode<T>)lesser).updateHeight(false);
                                
                                ((AVLNode<T>)greater).updateHeight(true);
                                node = (AVLNode<T>) lesser;
                            }
                        }
                    }
                }
                removed = true;
                break;
            }
        }

        //Balance tree
        if (removed) {
            size--;
            while (node!=null) {
                balanceAtNode(node);
                node = (AVLNode<T>) node.parent;
            }
        }

        return removed;
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

        public static <T extends Comparable<T>> String getString(AVLTree<T> tree) {
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
