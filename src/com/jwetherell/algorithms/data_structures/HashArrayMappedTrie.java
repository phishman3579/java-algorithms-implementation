package com.jwetherell.algorithms.data_structures;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.interfaces.IMap;

/**
 * A hash array mapped trie (HAMT) is an implementation of an associative 
 * array that combines the characteristics of a hash table and an array mapped 
 * trie. It is a refined version of the more general notion of a hash tree.
 * <p>
 * This implementation is 32-bit and steps in 5-bit intervals, maximum tree
 * height of 7.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Hash_array_mapped_trie">Hash Array Mapped Trie (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class HashArrayMappedTrie<K, V> implements IMap<K,V> {

    private static final byte MAX_BITS = 32;
    private static final byte BITS = 5;
    private static final byte MAX_DEPTH = MAX_BITS/BITS; // 6
    private static final int  MASK = (int)Math.pow(2, BITS)-1;

    private Node root = null;
    private int size = 0;

    /**
     * Get the "BITS" length integer starting at height*BITS position.
     * 
     * e.g. BITS=5 height=1 value==266 big-endian=100001010 (shifts height*BITS off the right) return=1000 (8 in decimal)
     */
    private static final int getPosition(int height, int value) {
        return (value >>> height*BITS) & MASK;
    }

    private V put(ArrayNode parent, Node node, byte height, int key, V value) {
        byte newHeight = height;
        if (node instanceof KeyValueNode) {
            KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
            if (key==kvNode.key) {
                // Key already exists as key-value pair, replace value
                kvNode.value = value;
                return value;
            }

            // Key already exists but doesn't match current key
            KeyValueNode<V> oldParent = kvNode;
            int newParentPosition = getPosition(newHeight-1, key);
            int oldParentPosition = getPosition(newHeight, oldParent.key);
            int childPosition = getPosition(newHeight, key);
            ArrayNode newParent = new ArrayNode(parent, key, newHeight);
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
                return null;
            }

            while (oldParentPosition == childPosition) {
                // Handle the case when the new children map to same position.
                newHeight++;
                if (newHeight>MAX_DEPTH) {
                    // We have found two keys which match exactly. I really don't know what to do.
                    throw new RuntimeException("Yikes! Found two keys which match exactly.");
                }
                newParentPosition = getPosition(newHeight-1, key);
                ArrayNode newParent2 = new ArrayNode(newParent, key, newHeight);
                newParent.addChild(newParentPosition, newParent2);

                oldParentPosition = getPosition(newHeight, oldParent.key);
                childPosition = getPosition(newHeight, key);
                if (oldParentPosition != childPosition) {
                    newParent2.addChild(oldParentPosition, oldParent);
                    oldParent.parent = newParent2;
                    newParent2.addChild(childPosition, new KeyValueNode<V>(newParent2, key, value));   
                } else {
                    newParent = newParent2;
                }
            }
            return null;
        } else if (node instanceof ArrayNode) {
            ArrayNode arrayRoot = (ArrayNode) node;
            int position = getPosition(arrayRoot.height, key);
            Node child = arrayRoot.getChild(position);

            if (child==null) {
                // Found an empty slot in parent
                arrayRoot.addChild(position, new KeyValueNode<V>(arrayRoot, key, value));
                return null;
            }

            return put(arrayRoot, child, (byte)(newHeight+1), key, value);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        int intKey = key.hashCode();
        V toReturn = null;
        if (root==null)
            root = new KeyValueNode<V>(null, intKey, value);
        else
            toReturn = put(null, root, (byte)0, intKey, value);
        if (toReturn==null) size++;
        return toReturn;
    }

    private Node find(Node node, int key) {
        if (node instanceof KeyValueNode) {
            KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
            if (kvNode.key==key)
                return kvNode;
            return null;
        } else if (node instanceof ArrayNode) {
            ArrayNode arrayNode = (ArrayNode)node;
            int position = getPosition(arrayNode.height, key);
            Node possibleNode = arrayNode.getChild(position);
            if (possibleNode==null) 
                return null;
            return find(possibleNode,key);
        }
        return null;
    }

    private Node find(int key) {
        if (root==null) return null;
        return find(root, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        Node node = find(key.hashCode());
        if (node==null) 
            return null;
        if (node instanceof KeyValueNode) 
            return ((KeyValueNode<V>)node).value;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        Node node = find(key.hashCode());
        return (node!=null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        Node node = find(key.hashCode());
        if (node==null) 
            return null;
        if (node instanceof ArrayNode) 
            return null;

        KeyValueNode<V> kvNode = (KeyValueNode<V>)node;
        V value = kvNode.value;
        if (node.parent==null) {
            // If parent is null, removing the root
            root = null;
        } else {
            ArrayNode parent = node.parent;
            // Remove child from parent
            int position = getPosition(parent.height, node.key);
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
                position = getPosition(parent.height, node.key);
                parent.removeChild(position);
                numOfChildren = parent.getNumberOfChildren();
            }
        }
        kvNode.key = 0;
        kvNode.value = null;
        size--;
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    private static <V> boolean validate(ArrayNode parent, KeyValueNode<V> child) {
        if (parent==null || parent.height==0) return true;

        int parentPosition = getPosition(parent.height-1,parent.key);
        int childPosition = getPosition(parent.height-1,child.key);
        return (childPosition==parentPosition);
    }

    private static <V> boolean validate(ArrayNode parent, ArrayNode node) {
        if (parent!=null) {
            if (parent.key != (node.parent.key)) return false;
            if (parent.height+1 != node.height) return false;
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

    /**
     * Convert a big-endian binary string to a little-endian binary string
     * and also pads with zeros to make it "BITS" length.
     * 
     * e.g. BITS=5 value==6 big-endian=110 little-endian=011 (pad) return=01100
     */
    private static final String toBinaryString(int value) {
        StringBuilder builder = new StringBuilder(Integer.toBinaryString(value));
        builder = builder.reverse();
        while (builder.length()<BITS) {
            builder.append('0');
        }
        return builder.toString();
    }

    protected static class Node {
        protected ArrayNode parent = null;
        protected int key = 0;
    }

    private static final class ArrayNode extends Node {

        private byte height = 0;
        private int bitmap = 0;
        private Node[] children = new Node[2];

        private ArrayNode(ArrayNode parent, int key, byte height) {
            this.parent = parent;
            this.key = key;
            this.height = height;
        }

        /**
         * Set the bit at position (zero based)
         * 
         * e.g. bitmap=0000 position==3 result=1000
         */
        private void set(int position) {
            bitmap |= (1 << position);
        }

        /**
         * Unset the bit at position (zero based)
         * 
         * e.g. bitmap==110 position=1 result=100
         */
        private void unset(int position) {
            bitmap &= ~(1 << position);
        }

        /**
         * Is the bit at position set (zero based)
         * 
         * e.g. bitmap=01110 position=3 result=true
         */
        private boolean isSet(int position) {
            return ((bitmap &(1 << position)) >>> position)==1;
        }

        /**
         * Count the number of 1s in the bitmap between 0 and position (zero based)
         * 
         * Because each 1 in the bitmap means a child exists, you can use the 1s count
         * to calculate the index of the child at the given position.
         * 
         * e.g. bitmap=01110010 position=5 result=3
         */
        private int getIndex(int pos){
            int position = pos;
            position = (1 << position)-1;
            return Integer.bitCount(bitmap & position);
        }

        /**
         * Calculates the height by doubling the size with a max size of MAX_BITS
         */
        private static int calcNewLength(int size) {
            int newSize = size;
            if (newSize==MAX_BITS) return newSize;

            newSize = (newSize + (newSize>>1));
            if (newSize>MAX_BITS) newSize = MAX_BITS;
            return newSize;
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
            builder.append("height=").append(height).append(" key=").append(toBinaryString(key)).append("\n");
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

        public static <K, V> String getString(HashArrayMappedTrie<K,V> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(null, tree.root, -1, "", true);
        }

        private static <V> String getString(Node parent, Node node, int height, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node instanceof KeyValueNode) {
                KeyValueNode<V> kvNode = (KeyValueNode<V>) node;
                int position = getPosition(height,kvNode.key);
                builder.append(prefix + (isTail ? "└── " : "├── ") + ((parent==null)?null:toBinaryString(position)) + "=" + toBinaryString(kvNode.key) + "=" + kvNode.value + "\n");
            } else {
                ArrayNode arrayNode = (ArrayNode) node;
                int position = (arrayNode.parent==null)?-1:getPosition(height,arrayNode.key);
                builder.append(prefix + (isTail ? "└── " : "├── ") + ((parent==null)?null:toBinaryString(position)) + " height=" + ((height<0)?null:height) + " bitmap=" + toBinaryString(arrayNode.bitmap) + "\n");
                List<Node> children = new LinkedList<Node>();
                for (int i=0; i<MAX_BITS; i++) {
                    Node child = arrayNode.getChild(i);
                    if (child != null) children.add(child);
                }
                for (int i = 0; i<(children.size()-1); i++) {
                    builder.append(getString(arrayNode, children.get(i), height+1, prefix+(isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(arrayNode, children.get(children.size()-1), height+1, prefix+(isTail ? "    " : "│   "), true));
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
        return (new JavaCompatibleMap<K,V>(this));
    }

    private static class JavaCompatibleIteratorWrapper<K,V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

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

    private static class JavaCompatibleMapEntry<K,V> extends java.util.AbstractMap.SimpleEntry<K,V> {

        private static final long serialVersionUID = -2943023801121889519L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleMap<K, V> extends java.util.AbstractMap<K,V> {

        private HashArrayMappedTrie<K,V> map = null;

        protected JavaCompatibleMap(HashArrayMappedTrie<K,V> map) {
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
        public void clear() {
            map.clear();
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
