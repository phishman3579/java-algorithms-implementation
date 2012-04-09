package com.jwetherell.algorithms.data_structures;

import java.util.List;
import java.util.ArrayList;


/**
 * A radix tree (also patricia trie or radix trie) is a space-optimized trie data structure where each node with 
 * only one child is merged with its child. The result is that every internal node has at least two children.
 * 
 * http://en.wikipedia.org/wiki/Radix_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RadixTree {

    private Node root = null;
    
    public RadixTree() {
        root = new Node(null);
    }
    
    public boolean add(String string) {
        int indexIntoParent = -1;
        int indexIntoString = -1;
        Node node = root;
        for (int i=0; i<=string.length(); i++) {
            indexIntoString = i;
            indexIntoParent++;
            if (i==string.length()) break;

            char c = string.charAt(i);
            if (node.partOfThis(c, indexIntoParent)) {
                //Node has a char which is equal to char c at that index
                continue;
            } else if (node.string!=null && indexIntoParent < node.string.length()) {
                //string is equal to part of this Node's string
                break;
            }
            
            Node child = node.getChildBeginningWithChar(c);
            if (child!=null) {
                //Found a child node starting with char c
                indexIntoParent = 0;
                node = child;
            } else {
                //Node doesn't have a child starting with char c
                break;
            }
        }

        if (node.string!=null && indexIntoParent<node.string.length()) {
            String parent = node.string.substring(0, indexIntoParent);
            String refactor = node.string.substring(indexIntoParent);
            node.string = parent;
            Node newNode1 = new Node(node, refactor, node.type);
            if (node.children.size()>0) {
                for (Node c : node.children) {
                    c.parent = newNode1;
                    newNode1.children.add(c);
                }
            }
            node.children.clear();
            node.children.add(newNode1);
            if (indexIntoString==string.length()) node.type = Node.Type.white;
            else node.type = Node.Type.black;

            if (indexIntoString!=string.length()) {
                String newString = string.substring(indexIntoString);
                Node newNode2 = new Node(node, newString, Node.Type.white);
                node.children.add(newNode2);
            }
        } else if (node.string!=null && string.length()==indexIntoString) {
            //Found a node who exactly matches a previous node
            
            //Already exists as a white node (leaf node)
            if (node.type == Node.Type.white) return false;
            
            //Was black (branching), now white
            node.type = Node.Type.white;
        } else if (node.string!=null) {
            //Adding a child
            String newString = string.substring(indexIntoString);
            Node newNode = new Node(node, newString, Node.Type.white);
            node.children.add(newNode);
        } else {
            //Add to root node
            Node newNode = new Node(node, string, Node.Type.white);
            node.children.add(newNode);
        }

        return true;
    }
    
    public boolean remove(String string) {
        Node node = getNode(string);
        if (node==null) return false;

        //No longer a white node (leaf)
        node.type = Node.Type.black;
        
        Node parent = node.parent;
        if (node.children.size()==0) {
            //Remove the node if it has no children
            parent.children.remove(node);
        } else if (node.children.size()==1) {
            //Merge the node with it's child and add to node's parent
            parent.children.remove(node);
            
            Node child = node.children.get(0);
            child.string = node.string+child.string;
            child.parent = parent;
            
            parent.children.add(child);
        }
        
        return true;
    }
    
    public boolean contains(String string) {
        Node node = getNode(string);
        return (node!=null && node.type==Node.Type.white);
    }

    private Node getNode(String string) { 
        int indexIntoParent = -1;
        Node node = root;
        for (int i=0; i<string.length(); i++) {
            indexIntoParent++;

            char c = string.charAt(i);
            if (node.partOfThis(c, indexIntoParent)) {
                //Node has a char which is equal to char c at that index
                continue;
            } else if (node.string!=null && indexIntoParent < node.string.length()) {
                //string is equal to part of this Node's string
                continue;
            }
            
            Node child = node.getChildBeginningWithChar(c);
            if (child!=null) {
                //Found a child node starting with char c
                indexIntoParent = 0;
                node = child;
            } else {
                //Node doesn't have a child starting with char c
                break;
            }
        }
        
        if (node.string!=null && (indexIntoParent==(node.string.length()-1))) {
            //We ended our search at the last char in the node's string
            return node;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }


    private static final class Node implements Comparable<Node> {
        
        private enum Type { black, white };
        
        private Node parent = null;
        private String string = null;
        private Type type = Type.black;
        private List<Node> children = new ArrayList<Node>();
        
        
        private Node(Node parent) { 
            this.parent = parent;
        }

        private Node(Node parent, String string) {
            this(parent);
            this.string = string;
        }

        private Node(Node parent, String string, Type type) {
            this(parent,string);
            this.type = type;
        }

        private boolean partOfThis(char c, int idx) {
            //Search myself
            if (string!=null && idx<string.length() && string.charAt(idx)==c) return true;
            return false;
        }
        
        private Node getChildBeginningWithChar(char c) {
            //Search children
            Node node = null;
            for (Node child : this.children) {
                if (child.string.charAt(0) == c) return child;
            }
            return node;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("string = ").append(string).append("\n");
            builder.append("type = ").append(type).append("\n");
            return builder.toString();
        }
        
        @Override
        public int compareTo(Node node) {
            if (node==null) return -1;
            
            int result = string.compareTo(node.string);
            if (result!=0) return result;
            
            if (this.type.ordinal() < node.type.ordinal()) return -1;
            else if (this.type.ordinal() > node.type.ordinal()) return 1;
            
            if (this.children.size() < node.children.size()) return -1;
            else if (this.children.size() > node.children.size()) return 1;
            
            return 0;
        }
    }

    protected static class TreePrinter {

        public static String getString(RadixTree tree) {
            return getString(tree.root, "", true);
        }

        protected static String getString(Node node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.string!=null)?node.string+" = "+node.type:"null")+"\n");
            if (node.children != null) {
                for (int i = 0; i < node.children.size() - 1; i++) {
                    builder.append(getString(node.children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.children.size() >= 1) {
                    builder.append(getString(node.children.get(node.children.size() - 1), prefix + (isTail ?"    " : "│   "), true));
                }
            }
            return builder.toString();
        }
    }
}
