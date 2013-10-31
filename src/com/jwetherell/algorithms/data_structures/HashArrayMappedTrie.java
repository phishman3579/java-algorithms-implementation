package com.jwetherell.algorithms.data_structures;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A hash array mapped trie (HAMT) is an implementation of an associative 
 * array that combines the characteristics of a hash table and an array mapped 
 * trie. It is a refined version of the more general notion of a hash tree.
 * 
 * http://en.wikipedia.org/wiki/Hash_array_mapped_trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class HashArrayMappedTrie<K extends Number, V> implements IMap<K,V> {

    private static final byte MAX_BITS = 32;
    private static final byte BITS = 5;
    private static final byte MAX_DEPTH = MAX_BITS/BITS; // default 6

    private Node root = null;
    private int size = 0;

    private static final String toBinaryString(int value) {
        if (value<0) return "null";

        StringBuilder builder = new StringBuilder(Integer.toBinaryString(value));
        builder = builder.reverse();
        while (builder.length()<BITS) {
            builder.append('0');
        }
        return builder.toString();
    }

    private static final int getPosition(int level, int value) {
        return (value >>> level*BITS) & 0x01f;
    }

    private V put(ArrayNode parent, Node node, byte level, int key, V value) {
        if (node instanceof KeyValueNode) {
            KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
            if (key==kvNode.key) {
                // Key already exists as key-value pair, replace value
                kvNode.value = value;
                return value;
            }
            // Key already exists but doesn't match current key
            KeyValueNode<V> oldParent = kvNode;
            int newParentPosition = getPosition(level-1, key);
            int oldParentPosition = getPosition(level, oldParent.key);
            int childPosition = getPosition(level, key);
            ArrayNode newParent = new ArrayNode(parent, key, level);
            newParent.parent = parent;
            if (parent==null) {
                // Only the root doesn't have a parent, so new root
                root = newParent;
            } else {
                // Add the child to the parent in it's parent's position
                parent.addChild(newParentPosition, newParent);
            }
            if (oldParentPosition != childPosition) {
                // The easy case, the two children have different positions in parent
                newParent.addChild(oldParentPosition, oldParent);
                oldParent.parent = newParent;
                newParent.addChild(childPosition, new KeyValueNode<V>(newParent, key, value));
                return value;
            }
            while (oldParentPosition == childPosition) {
                // Handle the case when the new children map to same position.
                level++;
                if (level>MAX_DEPTH) {
                    // We have found two keys which match exactly.
                    System.err.println("Yikes! Found two keys which match exactly.");
                    return null;
                }
                newParentPosition = getPosition(level-1, key);
                ArrayNode newParent2 = new ArrayNode(newParent, key, level);
                newParent.addChild(newParentPosition, newParent2);

                oldParentPosition = getPosition(level, oldParent.key);
                childPosition = getPosition(level, key);
                if (oldParentPosition != childPosition) {
                    newParent2.addChild(oldParentPosition, oldParent);
                    oldParent.parent = newParent2;
                    newParent2.addChild(childPosition, new KeyValueNode<V>(newParent2, key, value));   
                } else {
                    newParent = newParent2;
                }
            }
            return value;
        } else if (node instanceof ArrayNode) {
            ArrayNode arrayRoot = (ArrayNode) node;
            int position = getPosition(arrayRoot.level, key);
            Node child = arrayRoot.getChild(position);
            if (child==null) {
                // Found an empty slot in parent
                arrayRoot.addChild(position, new KeyValueNode<V>(arrayRoot, key, value));
                return value;
            }
            return put(arrayRoot, child, (byte)(level+1), key, value);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        //System.out.println("put="+key);
        //System.out.println(this.toString());
        int intKey = key.intValue();
        V toReturn = null;
        if (root==null) {
            root = new KeyValueNode<V>(null, intKey, value);
            toReturn = value;
        } else {
            toReturn = put(null, root, (byte)0, intKey, value);
        }
        //System.out.println(this.toString());
        if (toReturn!=null) size++;
        return toReturn;
    }

    private Node find(Node node, K key) {
        if (node instanceof KeyValueNode) {
            KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
            if (key.equals(kvNode.key))
                return kvNode;
            return null;
        } else if (node instanceof ArrayNode) {
            ArrayNode arrayNode = (ArrayNode)node;
            int intKey = key.intValue();
            int position = getPosition(arrayNode.level, intKey);
            Node possibleNode = arrayNode.getChild(position);
            if (possibleNode==null) return null;
            return find(possibleNode,key);
        }
        return null;
    }

    private Node find(K key) {
        if (root==null) return null;
        return find(root, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        Node node = find(key);
        if (node==null) return null;
        if (node instanceof KeyValueNode) return ((KeyValueNode<V>)node).value;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        Node node = this.find(key);
        if (node==null) return null;
        if (node instanceof ArrayNode) return null;

        //System.out.println("remove="+key);
        //System.out.println(this.toString());
        KeyValueNode<V> kvNode = (KeyValueNode<V>)node;
        V value = kvNode.value;
        if (node.parent==null) {
            // If parent is null, removing the root
            root = null;
        } else {
            ArrayNode parent = node.parent;
            // Remove child from parent
            int position = getPosition(parent.level, node.key);
            parent.removeChild(position);
            // Go back up the tree, pruning any array nodes which no longer have children.
            int numOfChildren = parent.getNumberOfChildren();
            while (numOfChildren==0) {
                node = parent;
                parent = node.parent;
                if (parent==null) {
                    // Reached root of the tree, need to stop
                    root = null;
                    break;
                }
                position = getPosition(parent.level, node.key);
                parent.removeChild(position);
                numOfChildren = parent.getNumberOfChildren();
            }
        }
        //System.out.println(this.toString());
        size--;
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        Node node = find(key);
        return (node!=null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    private static <K extends Number, V> boolean validate(ArrayNode parent, KeyValueNode<V> child) {
        if (parent==null || parent.level==0) return true;

        int parentPosition = getPosition(parent.level-1,parent.key);
        int childPosition = getPosition(parent.level-1,child.key);
        return (childPosition==parentPosition);
    }

    private static <K extends Number, V> boolean validate(ArrayNode parent, ArrayNode node) {
        if (parent!=null) {
            if (parent.key != (node.parent.key)) return false;
            if (parent.level+1 != node.level) return false;
        }

        int children = 0;
        for (int i=0; i<node.children.length; i++) {
            Node child = node.children[i];
            if (child!=null) {
                children++;
                if (child instanceof KeyValueNode) {
                    KeyValueNode<V> kvChild = (KeyValueNode<V>) child;
                    if (!validate(node,kvChild)) return false;
                } else if (child instanceof ArrayNode) {
                    ArrayNode arrayNode = (ArrayNode) child;
                    if (!validate(node, arrayNode)) return false;
                } else {
                    return false;
                }
            }
        }
        boolean result = (children==node.getNumberOfChildren());
        if (!result) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root==null) return true;
        Node child = root;
        if (child instanceof KeyValueNode) {
            KeyValueNode<V> kvChild = (KeyValueNode<V>) child;
            if (!validate(null, kvChild)) return false;
        } else if (child instanceof ArrayNode) {
            ArrayNode arrayNode = (ArrayNode) child;
            if (!validate(null, arrayNode)) return false;
        } else {
            return false;
        }
        return true;
    }

    protected static class Node {
        protected ArrayNode parent = null;
        protected int key = 0;
    }

    private static final class ArrayNode extends Node {

        private byte level = 0;
        private int bitmap = 0;
        private Node[] children = new Node[2];

        private ArrayNode(ArrayNode parent, int key, byte level) {
            this.parent = parent;
            this.key = key;
            this.level = level;
        }

        private void set(int position) {
            bitmap |= (1 << position);
        }

        private void unset(int position) {
            bitmap &= ~(1 << position);
        }

        private boolean isSet(int position) {
            return ((bitmap &(1 << position)) >>> position)==1;
        }

        private int getIndex(int position){
            position = (1 << position)-1;
            return Integer.bitCount(bitmap & position);
        }

        private static int calcNewLength(int size) {
            if (size==MAX_BITS) return size;

            size = (size + (size>>1));
            if (size>MAX_BITS) size = MAX_BITS;
            return size;
        }

        private void addChild(int position, Node child) {
            boolean overwrite = false;
            if (isSet(position)) overwrite = true;

            set(position);
            int idx = getIndex(position);
            if (!overwrite) {
                int len = calcNewLength(getNumberOfChildren());
                // Array size changed, copy the array to the new array
                if (len>children.length) {
                    Node[] temp = new Node[len];
                    System.arraycopy(children, 0, temp, 0, children.length);
                    children = temp;
                }
                // Move existing elements up to make room
                if (children[idx]!=null)
                    System.arraycopy(children, idx, children, idx+1, (children.length-(idx+1)));
            }
            children[idx] = child;
        }

        private void removeChild(int position) {
            if (!isSet(position)) return;

            unset(position);
            int lastIdx = getNumberOfChildren();
            int idx = getIndex(position);
            // Shift the entire array down one spot starting at idx
            System.arraycopy(children, idx+1, children, idx, (children.length-(idx+1)));
            children[lastIdx] = null;
        }

        private Node getChild(int position) {
            if (!isSet(position)) return null;

            int idx = getIndex(position);
            return children[idx];
        }

        private int getNumberOfChildren() {
            return Integer.bitCount(bitmap);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("level=").append(level).append(" key=").append(toBinaryString(key)).append("\n");
            for (int i=0; i<MAX_BITS; i++) {
                Node c = getChild(i);
                if (c!=null) builder.append(c.toString()).append(", ");
            }
            return builder.toString();
        }
    }

    private static final class KeyValueNode<V> extends Node {
        private V value;

        private KeyValueNode(ArrayNode parent, int key, V value) {
            this.parent = parent;
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("key=").append(toBinaryString(key)).append(" value=").append(value);
            return builder.toString();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    protected static class TreePrinter {

        public static <K extends Number, V> String getString(HashArrayMappedTrie<K,V> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, -1, "", true);
        }

        private static <K, V> String getString(Node node, int level, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node instanceof KeyValueNode) {
                KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
                int position = getPosition(level,kvNode.key);
                builder.append(prefix + (isTail ? "└── " : "├── ") + toBinaryString(position) + "=" + toBinaryString(kvNode.key) + "=" + kvNode.value + "\n");
            } else {
                ArrayNode arrayNode = (ArrayNode) node;
                int position = getPosition(level,arrayNode.key);
                builder.append(prefix + (isTail ? "└── " : "├── ") + toBinaryString(position) + " level=" + arrayNode.level + " bitmap=" + toBinaryString(arrayNode.bitmap) + "\n");
                List<Node> children = new LinkedList<Node>();
                for (int i=0; i<MAX_BITS; i++) {
                    Node child = arrayNode.getChild(i);
                    if (child != null) children.add(child);
                }
                for (int i = 0; i<(children.size()-1); i++) {
                    builder.append(getString(children.get(i), level+1, prefix+(isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size()-1), level+1, prefix+(isTail ? "    " : "│   "), true));
                }
            }
            return builder.toString();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<K,V> toMap() {
        return (new JavaCompatibleHashMap<K,V>(this));
    }

    private static class JavaCompatibleIteratorWrapper<K extends Number,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private HashArrayMappedTrie<K,V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(HashArrayMappedTrie<K,V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
            this.map = map;
            this.iter = iter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if (iter==null) return false;
            return iter.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map.Entry<K, V> next() {
            if (iter==null) return null;

            lastEntry = iter.next();
            return lastEntry;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (iter==null || lastEntry==null) return;

            map.remove(lastEntry.getKey());
            iter.remove();
        }
    }

    private static class JavaCompatibleMapEntry<K extends Number,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = -2943023801121889519L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleHashMap<K extends Number, V> extends java.util.AbstractMap<K,V> {

        private HashArrayMappedTrie<K,V> map = null;

        protected JavaCompatibleHashMap(HashArrayMappedTrie<K,V> map) {
            this.map = map;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            return map.put(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            return map.remove((K)key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return map.contains((K)key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return map.size();
        }

        private void addToSet(java.util.Set<java.util.Map.Entry<K, V>> set, Node node) {
            if (node instanceof KeyValueNode) {
                KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
                set.add(new JavaCompatibleMapEntry<K,V>((K)new Integer(kvNode.key), kvNode.value));
            } else if (node instanceof ArrayNode) {
                ArrayNode arrayNode = (ArrayNode) node;
                for (int i=0; i<MAX_BITS; i++) {
                    Node child = arrayNode.getChild(i);
                    if (child!=null) addToSet(set, child);
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            java.util.Set<java.util.Map.Entry<K, V>> set = new java.util.HashSet<java.util.Map.Entry<K, V>>() {

                private static final long serialVersionUID = 1L;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                    return (new JavaCompatibleIteratorWrapper<K,V>(map, super.iterator()));
                }
            };
            if (map.root!=null) addToSet(set,map.root);
            return set;
        }
    }
}
