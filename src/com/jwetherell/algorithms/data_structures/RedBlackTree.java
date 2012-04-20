package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


/**
 * A red–black tree is a type of self-balancing binary search tree, a data structure 
 * used in computer science, typically to implement associative arrays. A red–black tree 
 * is a binary search tree that inserts and deletes in such a way that the tree is always 
 * reasonably balanced. Red-black trees are often compared with AVL trees. AVL trees are 
 * more rigidly balanced, they are faster than red-black trees for lookup intensive 
 * applications. However, red-black trees are faster for insertion and removal.
 * 
 * http://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    private enum Color { Red, Black }; 
    
    @Override
    public void add(T value) {
        RedBlackNode<T> newNode = null;
        boolean added = false;
        if (root == null) {
            //Case 1
            root = new RedBlackNode<T>(null, value, Color.Red);
            root.lesser = new RedBlackNode<T>(root,null,Color.Black);
            root.greater = new RedBlackNode<T>(root,null,Color.Black);
            newNode = (RedBlackNode<T>) root;
            added = true;
        } else {
            Node<T> node = root;
            while (node != null) {
                if (node.value==null) {
                    node.value = value;
                    ((RedBlackNode<T>)node).color = Color.Red;
                    node.lesser = new RedBlackNode<T>(node,null,Color.Black);
                    node.greater = new RedBlackNode<T>(node,null,Color.Black);
                    newNode = (RedBlackNode<T>) node;
                    added = true;
                    break;
                } else if (value.compareTo(node.value) <= 0) {
                    node = node.lesser;
                } else {
                    node = node.greater;
                }
            }
        }

        if (added==true) {
            insert((RedBlackNode<T>)newNode);
            size++;
        }
        System.out.println("Added "+value+"\n"+RedBlackTree.RedBlackTreePrinter.getString(this));
    }
    
    private void insert(RedBlackNode<T> child) {
        RedBlackNode<T> parent = (RedBlackNode<T>) child.parent;

        if (parent == null) {
            //Case 1
            child.color = Color.Black;
            return;
        }
        
        if (parent.color == Color.Black) {
            //Case 2
            return;
        }
        
        RedBlackNode<T> grandParent = child.getGrandParent();
        RedBlackNode<T> uncle = child.getUncle();
        if (grandParent==null || uncle==null) {
            System.err.println("Could not find either my uncle or my grandParent!!"+child.toString());
            return;
        }
        
        if (parent.color==Color.Red && uncle.color==Color.Red) {
            //Case 3
            parent.color=Color.Black;
            uncle.color=Color.Black;
            grandParent.color=Color.Red;
            insert(grandParent);
        } else {
            grandParent = child.getGrandParent();
            parent = (RedBlackNode<T>) child.parent;
            uncle = child.getUncle();
            if (parent.color==Color.Red && uncle.color==Color.Black) {
                //Case 4
                if (child.equals(parent.greater) && parent.equals(grandParent.lesser)) {
                    rotateLeft(parent);
                    child = (RedBlackNode<T>) child.lesser;
                } else if (child.equals(parent.lesser) && parent.equals(grandParent.greater)) {
                    rotateRight(parent);
                    child = (RedBlackNode<T>) child.greater;
                }
            }
    
            grandParent = child.getGrandParent();
            parent = (RedBlackNode<T>) child.parent;
            uncle = child.getUncle();
            if (parent.color==Color.Red && uncle.color==Color.Black) {
                //Case 5
                parent.color = Color.Black;
                grandParent.color = Color.Red;
                if (child.equals(parent.lesser) && parent.equals(grandParent.lesser)) {
                    rotateRight(grandParent);
                } else if (child.equals(parent.greater) && parent.equals(grandParent.greater)) {
                    rotateLeft(grandParent);
                }
            }
        }
    }

    private void rotateLeft(Node<T> node) {
        System.out.println("Rotating left "+node.value+"\n"+this.toString());
        Node<T> parent = node.parent;
        Node<T> greater = node.greater;

        if (parent==null) {
            //root
            root = greater;
            root.parent = null;
        } else {
            if (node.equals(parent.lesser)) {
                parent.lesser = greater;
                greater.parent = parent;
            } else {
                parent.greater = greater;
                greater.parent = parent;
            }
        }

        Node<T> lesser = greater.lesser;

        greater.lesser = node;
        node.parent = greater;
        
        node.greater = lesser;
        if (lesser!=null) lesser.parent = node;

        System.out.println("Rotating left "+node.value+"\n"+this.toString());
    }

    private void rotateRight(Node<T> node) {
        System.out.println("Rotating right "+node.value+"\n"+this.toString());
        Node<T> parent = node.parent;
        Node<T> lesser = node.lesser;

        if (parent==null) {
            //root
            root = lesser;
            root.parent = null;
        } else {
            if (node.equals(parent.lesser)) {
                parent.lesser = lesser;
                lesser.parent = parent;
            } else {
                parent.greater = lesser;
                lesser.parent = parent;
            }
        }

        Node<T> greater = lesser.greater;

        lesser.greater = node;
        node.parent = lesser;
        
        node.lesser = greater;
        if (greater!=null) greater.parent = node;
        
        System.out.println("Rotated right "+node.value+"\n"+this.toString());
    }

    @Override
    public boolean remove(T value) {
        RedBlackNode<T> node = (RedBlackNode<T>) super.getNode(value);
        if (node==null) return false;

        System.out.println("Removing "+value+"\n"+RedBlackTree.RedBlackTreePrinter.getString(this));
        
        if (node.isLeaf()) {
            node.value = null;
            if (node.parent==null) {
                root = null;
            } else {
                node.value = null;
                node.color = Color.Black;
                node.lesser = null;
                node.greater = null;
            }
        } else {
            //At least one child
            RedBlackNode<T> lesser = (RedBlackNode<T>) node.lesser;
            RedBlackNode<T> greater = (RedBlackNode<T>) node.greater;

            if (lesser.value!=null && greater.value!=null) {
                //Two children
                RedBlackNode<T> greatestInLesser = (RedBlackNode<T>) this.getGreatest(lesser);
                if (greatestInLesser==null || greatestInLesser.value==null) greatestInLesser = lesser;
                replaceValue(node,greatestInLesser);
                node = greatestInLesser;
            }

            System.out.println("node = "+node.toString());

            RedBlackNode<T> child = (RedBlackNode<T>)((node.lesser.value!=null)?node.lesser:node.greater);
            if (node.color==Color.Black) {
                if (child.color==Color.Black) {
                    node.color = Color.Red;
                }
                delete(node);
            }
            replaceChild(node,child);
        }

        size--;
        System.out.println("Removed "+value+"\n"+RedBlackTree.RedBlackTreePrinter.getString(this));
        
        return true;
    }

    private void replaceValue(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        System.out.println("Replacing value "+nodeToReplace.value+" with "+nodeToReplaceWith.value);
        nodeToReplace.value = nodeToReplaceWith.value;
        nodeToReplaceWith.value = null;
    }

    private void replaceChild(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        System.out.println("Replacing child "+nodeToReplace.value+" with "+nodeToReplaceWith.value);
        nodeToReplace.value = nodeToReplaceWith.value;
        nodeToReplace.color = nodeToReplaceWith.color;
        nodeToReplace.lesser = nodeToReplaceWith.lesser;
        nodeToReplace.greater = nodeToReplaceWith.greater;
    }

    private void delete(RedBlackNode<T> node) {
        System.out.println("Delete "+node.value);
        if (node.parent==null) {
            //Case 1
            return;
        }

        RedBlackNode<T> parent = (RedBlackNode<T>) node.parent;

        RedBlackNode<T> sibling = node.getSibling();
        if (sibling.color==Color.Red) {
            //Case 2
            parent.color = Color.Red;
            sibling.color = Color.Black;
            if (node.equals(parent.lesser)) {
                rotateLeft(parent);
            } else {
                rotateRight(parent);
            }
        }

        parent = (RedBlackNode<T>) node.parent;
        sibling = node.getSibling();
        if (parent.color==Color.Black && 
            sibling.color==Color.Black && 
            ((RedBlackNode<T>)sibling.lesser).color==Color.Black && 
            ((RedBlackNode<T>)sibling.greater).color==Color.Black
        ) {
            //Case 3
            sibling.color = Color.Red;
            delete(parent);
        } else {
            sibling = node.getSibling();
            if (parent.color==Color.Red && 
                sibling.color==Color.Black && 
                ((RedBlackNode<T>)sibling.lesser).color==Color.Black && 
                ((RedBlackNode<T>)sibling.greater).color==Color.Black
            ) {
                //Case 4
                sibling.color = Color.Red;
                parent.color = Color.Black;
            } else {
                sibling = node.getSibling();
                if (sibling.color==Color.Black) {
                    //Case 5
                    if (node.equals(parent.lesser) && 
                       ((RedBlackNode<T>)sibling.lesser).color==Color.Red && 
                       ((RedBlackNode<T>)sibling.greater).color==Color.Black
                    ) {
                        sibling.color = Color.Red;
                        ((RedBlackNode<T>)sibling.lesser).color = Color.Red;
                        rotateRight(sibling);
                    } else if (node.equals(parent.greater)  && 
                              ((RedBlackNode<T>)sibling.lesser).color==Color.Black && 
                              ((RedBlackNode<T>)sibling.greater).color==Color.Red
                    ) {
                        sibling.color = Color.Red;
                        ((RedBlackNode<T>)sibling.greater).color = Color.Red;
                        rotateLeft(sibling);
                    }
                }
                
                //Case 6
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
                sibling.color = parent.color;
                parent.color = Color.Black;
                
                if (node.equals(parent.lesser)) {
                    ((RedBlackNode<T>)sibling.greater).color = Color.Black;
                    rotateLeft(node.parent);
                } else {
                    ((RedBlackNode<T>)sibling.lesser).color = Color.Black;
                    rotateRight(node.parent);
                }
            }
        }
        
    }
    
    @Override
    public boolean validate() {
        if (root==null) return true;
        
        if (((RedBlackNode<T>)root).color == Color.Red) return false;
        
        return validateNode(root);
    }

    @Override
    protected boolean validateNode(Node<T> node) {
        RedBlackNode<T> rbNode = (RedBlackNode<T>) node;
        if (rbNode.isLeaf()) {
            if (rbNode.color==Color.Red) return false;
            return true;
        } else if (rbNode.color==Color.Red) {
            RedBlackNode<T> lesser = (RedBlackNode<T>) rbNode.lesser;
            if (lesser.color==Color.Red) return false;
            RedBlackNode<T> greater = (RedBlackNode<T>) rbNode.greater;
            if (greater.color==Color.Red) return false;
        }
        
        boolean lesserCheck = true;
        if (rbNode.lesser!=null) validateNode(rbNode.lesser);
        boolean greaterCheck = true;
        if (rbNode.greater!=null) validateNode(rbNode.greater);
        
        return (lesserCheck&&greaterCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return RedBlackTreePrinter.getString(this);
    }


    protected static class RedBlackNode<T extends Comparable<T>> extends Node<T> {

        protected Color color = Color.Black;

        protected RedBlackNode(Node<T> parent, T value, Color color) {
            super(parent,value);
            this.color = color;
        }

        protected RedBlackNode<T> getGrandParent() {
            if (parent==null || parent.parent==null) return null; 
            return (RedBlackNode<T>) parent.parent;
        }

        protected RedBlackNode<T> getUncle() {
            if (parent == null) return null;
            RedBlackNode<T> grandParent = getGrandParent();
            if (grandParent == null) return null;
            if (grandParent.lesser.equals(parent)) {
                return (RedBlackNode<T>) grandParent.greater;
            } else {
                return (RedBlackNode<T>) grandParent.lesser;
            }
        }

        protected RedBlackNode<T> getSibling() {
            if (parent==null) return null;
            if (parent.lesser.equals(this)) {
                return (RedBlackNode<T>) parent.greater;
            } else {
                return (RedBlackNode<T>) parent.lesser;
            }
        }

        protected boolean isLeaf() {
            if (lesser!=null && lesser.value!=null) return false;
            if (greater!=null && greater.value!=null) return false;
            return true;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "value=" + value + 
                   " color=" + color + 
                   " isLeaf=" + isLeaf() + 
                   " parent=" + ((parent != null) ? parent.value : "NULL") + 
                   " lesser=" + ((lesser != null) ? lesser.value : "NULL") + 
                   " greater=" + ((greater != null) ? greater.value : "NULL");
        }
    }

    protected static class RedBlackTreePrinter {

        public static <T extends Comparable<T>> String getString(RedBlackTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString((RedBlackNode<T>)tree.root, "", true);
        }

        public static <T extends Comparable<T>> String getString(RedBlackNode<T> node) {
            if (node == null) return "Sub-tree has no nodes.";
            return getString(node, "", true);
        }

        private static <T extends Comparable<T>> String getString(RedBlackNode<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + node.color + ") " + node.value + "\n");
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.lesser != null) children.add(node.lesser);
                if (node.greater != null) children.add(node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString((RedBlackNode<T>)children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString((RedBlackNode<T>)children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
