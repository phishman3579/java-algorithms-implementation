package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.BinarySearchTree.Node;


/**
 * An tree used to store key->values pairs, this is an implementation of an
 * associative array.
 * 
 * This implementation is a composition of a AVLTree as the backing structure.
 * 
 * http://en.wikipedia.org/wiki/AVL_tree
 * http://en.wikipedia.org/wiki/Associative_array
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TreeMap<K extends Comparable<K>, V> implements BinarySearchTree.INodeCreator<K> {

    private AVLTree<K> tree = null;

    /**
     * Default constructor.
     */
    public TreeMap() {
        tree = new AVLTree<K>(this);
    }

    /**
     * Put the key/value pair in the trie.
     * 
     * @param key
     *            to represent the value.
     * @param value
     *            to store in the key.
     * @return True if added to the trie or false if it already exists.
     */
    @SuppressWarnings("unchecked")
    public boolean put(K key, V value) {
        BinarySearchTree.Node<K> node = tree.addValue(key);
        if (node == null) return false;

        if (node instanceof TreeMapNode) {
            TreeMapNode<K, V> mNode = (TreeMapNode<K, V>) node;
            mNode.value = value;
        }

        return true;
    }

    /**
     * Remove the key/value pair from the map.
     * 
     * @param key
     *            to remove from the map.
     * @return True if the key was removed or false if it doesn't exist.
     */
    public boolean remove(K key) {
        return tree.remove(key);
    }

    /**
     * Get the value stored with the key.
     * 
     * @param key
     *            to get value for.
     * @return value stored at key.
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        BinarySearchTree.Node<K> node = tree.getNode(key);
        if (node instanceof TreeMapNode) {
            TreeMapNode<K, V> mapNode = (TreeMapNode<K, V>) node;
            return mapNode.value;
        }
        return null;
    }

    /**
     * Does the key exist in the map.
     * 
     * @param key
     *            to locate in the map.
     * @return True if the key exists.
     */
    public boolean contains(K key) {
        return tree.contains(key);
    }

    /**
     * Number of key/value pairs in the map.
     * 
     * @return number of key/value pairs.
     */
    public int size() {
        return tree.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreeMapPrinter.getString(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node<K> createNewNode(Node<K> parent, K id) {
        return (new TreeMapNode<K, V>(parent, id, null));
    }

    protected static class TreeMapNode<K extends Comparable<K>, V> extends AVLTree.AVLNode<K> {

        protected V value = null;

        protected TreeMapNode(RedBlackTree.Node<K> parent, K key, V value) {
            super(parent, key);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(super.toString());
            builder.append("value = ").append(value).append("\n");
            return builder.toString();
        }
    }

    @SuppressWarnings("unchecked")
    protected static class TreeMapPrinter {

        public static <K extends Comparable<K>, V> String getString(TreeMap<K, V> map) {
            if (map.tree.root == null) return "Tree has no nodes.";
            return getString((TreeMapNode<K, V>) map.tree.root, "", true);
        }

        private static <K extends Comparable<K>, V> String getString(TreeMapNode<K, V> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.id != null) ? (node.id + " = " + node.value) : node.id) + "\n");
            List<TreeMapNode<K, V>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<TreeMapNode<K, V>>(2);
                if (node.lesser != null) children.add((TreeMapNode<K, V>) node.lesser);
                if (node.greater != null) children.add((TreeMapNode<K, V>) node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString((TreeMapNode<K, V>) children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString((TreeMapNode<K, V>) children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}
