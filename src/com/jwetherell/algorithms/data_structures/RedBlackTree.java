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
            boolean result = insert((RedBlackNode<T>)newNode);
            if (result) size++;
        }
    }
    
    private boolean insert(RedBlackNode<T> child) {
        RedBlackNode<T> parent = (RedBlackNode<T>) child.parent;

        if (parent == null) {
            //Case 1
            child.color = Color.Black;
            return true;
        }
        
        if (parent.color == Color.Black) {
            //Case 2
            return true;
        }
        
        RedBlackNode<T> grandParent = child.getGrandParent();
        RedBlackNode<T> uncle = child.getUncle();
        if (parent.color==Color.Red && uncle!=null && uncle.color==Color.Red) {
            //Case 3
            parent.color=Color.Black;
            uncle.color=Color.Black;
            if (grandParent!=null) {
                grandParent.color=Color.Red;
                insert(grandParent);
            }
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

        return true;
    }

    private void rotateLeft(Node<T> node) {
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
    }

    private void rotateRight(Node<T> node) {
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
    }

    @Override
    public boolean remove(T value) {
        RedBlackNode<T> node = (RedBlackNode<T>) super.getNode(value);
        if (node==null) return false;

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

            if (lesser!=null && greater!=null && lesser.value!=null && greater.value!=null) {
                //Two children
                RedBlackNode<T> greatestInLesser = (RedBlackNode<T>) this.getGreatest(lesser);
                if (greatestInLesser==null || greatestInLesser.value==null) greatestInLesser = lesser;
                replaceValue(node,greatestInLesser);
                node = greatestInLesser;
            }

            RedBlackNode<T> child = (RedBlackNode<T>)((node.lesser!=null && node.lesser.value!=null)?node.lesser:node.greater);
            if (node.color==Color.Black) {
                if (child.color==Color.Black) {
                    node.color = Color.Red;
                }
                boolean result = delete(node);
                if (!result) return false;
            }
            replaceChild(node,child);
            if (root.equals(node) && node.isLeaf()) {
                root = null;
            }
        }

        size--;

        return true;
    }

    private void replaceValue(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        nodeToReplace.value = nodeToReplaceWith.value;
        nodeToReplaceWith.value = null;
    }

    private void replaceChild(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        nodeToReplace.value = nodeToReplaceWith.value;
        nodeToReplace.color = nodeToReplaceWith.color;
        if (nodeToReplace.parent==null) nodeToReplace.color = Color.Black; 
        nodeToReplace.lesser = nodeToReplaceWith.lesser;
        nodeToReplace.greater = nodeToReplaceWith.greater;
    }

    private boolean delete(RedBlackNode<T> node) {
        if (node.parent==null) {
            //Case 1
            return true;
        }

        RedBlackNode<T> parent = (RedBlackNode<T>) node.parent;
        RedBlackNode<T> sibling = node.getSibling();
        if (sibling.color==Color.Red) {
            //Case 2
            parent.color = Color.Red;
            sibling.color = Color.Black;
            if (node.equals(parent.lesser)) {
                rotateLeft(parent);
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else if (node.equals(parent.greater)) {
                rotateRight(parent);
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else {
                System.err.println("Yikes! I'm not related to my parent. node="+node.toString()+" parent="+parent.toString());
                return false;
            }
        }

        if (parent.color==Color.Black && 
            sibling.color==Color.Black && 
            sibling.lesser!=null && ((RedBlackNode<T>)sibling.lesser).color==Color.Black && 
            sibling.greater!=null && ((RedBlackNode<T>)sibling.greater).color==Color.Black
        ) {
            //Case 3
            sibling.color = Color.Red;
            boolean result = delete(parent);
            if (!result) return false;
        } else if (parent.color==Color.Red && 
                   sibling.color==Color.Black && 
                   sibling.lesser!=null && ((RedBlackNode<T>)sibling.lesser).color==Color.Black && 
                   sibling.greater!=null && ((RedBlackNode<T>)sibling.greater).color==Color.Black
        ) {
            //Case 4
            sibling.color = Color.Red;
            parent.color = Color.Black;
        } else {
            if (sibling.color==Color.Black) {
                //Case 5
                if (node.equals(parent.lesser) && 
                    sibling.lesser!=null && ((RedBlackNode<T>)sibling.lesser).color==Color.Red && 
                    sibling.greater!=null && ((RedBlackNode<T>)sibling.greater).color==Color.Black
                ) {
                    sibling.color = Color.Red;
                    if (sibling.lesser!=null) ((RedBlackNode<T>)sibling.lesser).color = Color.Red;
                    rotateRight(sibling);
                } else if (node.equals(parent.greater)  && 
                           sibling.lesser!=null && ((RedBlackNode<T>)sibling.lesser).color==Color.Black && 
                           sibling.greater!=null && ((RedBlackNode<T>)sibling.greater).color==Color.Red
                ) {
                    sibling.color = Color.Red;
                    if (sibling.greater!=null) ((RedBlackNode<T>)sibling.greater).color = Color.Red;
                    rotateLeft(sibling);
                }
            }
            
            //Case 6
            parent = (RedBlackNode<T>) node.parent;
            sibling = node.getSibling();
            sibling.color = parent.color;
            parent.color = Color.Black;
            
            if (node.equals(parent.lesser)) {
                if (sibling.greater!=null) ((RedBlackNode<T>)sibling.greater).color = Color.Black;
                rotateLeft(node.parent);
            } else if (node.equals(parent.greater)) {
                if (sibling.lesser!=null) ((RedBlackNode<T>)sibling.lesser).color = Color.Black;
                rotateRight(node.parent);
            } else {
                System.err.println("Yikes! I'm not related to my parent. "+node.toString());
                return false;
            }
        }
        return true;
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
        if (rbNode.lesser!=null) {
            if (rbNode.value!=null && rbNode.lesser.value!=null) {
                lesserCheck = rbNode.lesser.value.compareTo(rbNode.value)<=0;
            }
            if (lesserCheck) lesserCheck = validateNode(rbNode.lesser);
        }
        if (!lesserCheck) return false;
        
        boolean greaterCheck = true;
        if (rbNode.greater!=null) {
            if (rbNode.value!=null && rbNode.greater.value!=null) {
                greaterCheck = rbNode.greater.value.compareTo(rbNode.value)>0;
            }
            if (greaterCheck) greaterCheck = validateNode(rbNode.greater);
        }
        return greaterCheck;
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
            RedBlackNode<T> grandParent = getGrandParent();
            if (grandParent == null) return null;
            if (grandParent.lesser!=null && grandParent.lesser.equals(parent)) {
                return (RedBlackNode<T>) grandParent.greater;
            } else if (grandParent.greater!=null && grandParent.greater.equals(parent)) {
                return (RedBlackNode<T>) grandParent.lesser;
            }
            return null;
        }

        protected RedBlackNode<T> getSibling() {
            if (parent==null) return null;
            if (parent.lesser.equals(this)) {
                return (RedBlackNode<T>) parent.greater;
            } else if (parent.greater.equals(this)) {
                return (RedBlackNode<T>) parent.lesser;
            } else {
                System.err.println("Yikes! I'm not my parents child.");
            }
            return null;
        }

        protected boolean isLeaf() {
            if (lesser!=null) return false;
            if (greater!=null) return false;
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
