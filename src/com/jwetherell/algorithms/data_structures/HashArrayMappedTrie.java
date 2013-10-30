package com.jwetherell.algorithms.data_structures;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class HashArrayMappedTrie<K extends Number, V> implements IMap<K,V> {

    private static final int FIVE = 0x1f; // 5 bits
    private static final int TEN = FIVE << 5; // 5 bits
    private static final int FIFTEEN = FIVE << 10; // 5 bits
    private static final int TWENTY = FIVE << 15; // 5 bits
    private static final int TWENTY_FIVE = FIVE << 20; // 5 bits
    private static final int THIRTY = FIVE << 25; // 5 bits
    private static final int LEFT_OVERS = 0x3 << 30; // 2 bits

    private Node<K> root = null;
    private int size = 0;

    private static final <K extends Number> String toBinaryString(K key) {
        int value = key.intValue();
        if (value<0) return "null";

        StringBuilder builder = new StringBuilder(Integer.toBinaryString(value));
        builder = builder.reverse();
        while (builder.length()<5) {
            builder.append('0');
        }
        return builder.toString();
    }

    private static final <K extends Number> int getPosition(int level, K key) {
        int value = key.intValue();
        long result = -1;
        if (level==0) {
            result = (value & FIVE);
        } else if (level==1) {
            result = (value & TEN) >> 5;
        } else if (level==2) {
            result = (value & FIFTEEN) >> 10;
        } else if (level==3) {
            result = (value & TWENTY) >> 15;
        } else if (level==4) {
            result = (value & TWENTY_FIVE) >> 20;
        } else if (level==5) {
            result = (value & THIRTY) >> 25;
        } else if (level==6) {
            result = (value & LEFT_OVERS) >> 30;
        }
        return (int)result;
    }

    private V put(ArrayNode<K> parent, Node<K> node, byte level, K key, V value) {
        if (node instanceof KeyValueNode) {
            KeyValueNode<K,V> kvNode = (KeyValueNode<K,V>) node;
            if (key.equals(kvNode.key)) {
                // Key already exists as key-value pair, replace value
                kvNode.value = value;
                return value;
            }
            // Key already exists but doesn't match current key
            KeyValueNode<K,V> oldParent = kvNode;
            int newParentPosition = getPosition(level-1, key);
            int oldParentPosition = getPosition(level, oldParent.key);
            int childPosition = getPosition(level, key);
            ArrayNode<K> newParent = new ArrayNode<K>(parent, key, level);
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
                newParent.addChild(childPosition, new KeyValueNode<K,V>(newParent, key, value));
                return value;
            }
            while (oldParentPosition == childPosition) {
                // Handle the case when the new children map to same position.
                level++;
                newParentPosition = getPosition(level-1, key);
                ArrayNode<K> newParent2 = new ArrayNode<K>(newParent, key, level);
                newParent.addChild(newParentPosition, newParent2);

                oldParentPosition = getPosition(level, oldParent.key);
                childPosition = getPosition(level, key);
                if (oldParentPosition != childPosition) {
                    newParent2.addChild(oldParentPosition, oldParent);
                    oldParent.parent = newParent2;
                    newParent2.addChild(childPosition, new KeyValueNode<K,V>(newParent2, key, value));   
                } else {
                    newParent = newParent2;
                }
            }
            return value;
        } else if (node instanceof ArrayNode) {
            ArrayNode<K> arrayRoot = (ArrayNode<K>) node;
            int position = getPosition(arrayRoot.level, key);
            Node<K> child = arrayRoot.getChild(position);
            if (child==null) {
                // Found an empty slot in parent
                arrayRoot.addChild(position, new KeyValueNode<K,V>(arrayRoot, key, value));
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
        V toReturn = null;
        if (root==null) {
            root = new KeyValueNode<K,V>(null, key, value);
            toReturn = value;
        } else {
            toReturn = put(null, root, (byte)0, key, value);
        }
        if (toReturn!=null) size++;
        return toReturn;
    }

    private Node<K> find(Node<K> node, K key) {
        if (node instanceof KeyValueNode) {
            KeyValueNode<K,V> kvNode = (KeyValueNode<K,V>) node;
            if (key.equals(kvNode.key))
                return kvNode;
            return null;
        } else if (node instanceof ArrayNode) {
            ArrayNode<K> arrayNode = (ArrayNode<K>)node;
            int position = getPosition(arrayNode.level,key);
            Node<K> possibleNode = arrayNode.getChild(position);
            if (possibleNode==null) return null;
            return find(possibleNode,key);
        }
        return null;
    }

    private Node<K> find(K key) {
        if (root==null) return null;
        return find(root, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        Node<K> node = find(key);
        if (node==null) return null;
        if (node instanceof KeyValueNode) return ((KeyValueNode<K,V>)node).value;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        Node<K> node = this.find(key);
        if (node==null) return null;
        if (node instanceof ArrayNode) return null;

        KeyValueNode<K,V> kvNode = (KeyValueNode<K,V>)node;
        V value = kvNode.value;
        if (node.parent==null) {
            // If parent is null, removing the root
            root = null;
        } else {
            ArrayNode<K> parent = node.parent;
            // Remove child from parent
            int position = getPosition(parent.level, node.key);
            parent.removeChild(position);
            // Go back up the tree, pruning any array nodes which no longer have children.
            int numOfChildren = parent.getNumberOfChildren();
            while (numOfChildren==0) {
                node = parent;
                parent = node.parent;
                if (parent==null) {
                    root = null;
                    break;
                }
                position = getPosition(parent.level, node.key);
                parent.removeChild(position);
                numOfChildren = parent.getNumberOfChildren();
            }
        }
        size--;
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        Node<K> node = find(key);
        return (node!=null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    private static <K extends Number, V> boolean validate(ArrayNode<K> parent, KeyValueNode<K,V> child) {
        if (parent==null || parent.level==0) return true;

        int parentPosition = getPosition(parent.level-1,parent.key);
        int childPosition = getPosition(parent.level-1,child.key);
        return (childPosition==parentPosition);
    }

    private static <K extends Number, V> boolean validate(ArrayNode<K> parent, ArrayNode<K> node) {
        if (parent!=null) {
            if (!parent.key.equals(node.parent.key)) return false;
            if (parent.level+1 != node.level) return false;
        }

        int children = 0;
        for (int i=0; i<node.children.length; i++) {
            Node<K> child = node.getChild(i);
            if (child!=null) {
                children++;
                if (child instanceof KeyValueNode) {
                    KeyValueNode<K,V> kvChild = (KeyValueNode<K,V>) child;
                    if (!validate(node,kvChild)) return false;
                } else if (child instanceof ArrayNode) {
                    ArrayNode<K> arrayNode = (ArrayNode<K>) child;
                    if (!validate(node, arrayNode)) return false;
                } else {
                    return false;
                }
            }
        }
        return (children==node.numberOfChildren);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root==null) return true;
        Node<K> child = root;
        if (child instanceof KeyValueNode) {
            KeyValueNode<K,V> kvChild = (KeyValueNode<K,V>) child;
            if (!validate(null, kvChild)) return false;
        } else if (child instanceof ArrayNode) {
            ArrayNode<K> arrayNode = (ArrayNode<K>) child;
            if (!validate(null, arrayNode)) return false;
        } else {
            return false;
        }
        return true;
    }

    protected static class Node<K extends Number> {
        protected ArrayNode<K> parent = null;
        protected K key = null;
    }

    private static final class ArrayNode<K extends Number> extends Node<K> {

        private byte level = -1;
        private byte numberOfChildren = 0;
        private Node<K>[] children = null;

        private ArrayNode(ArrayNode<K> parent, K key, byte level) {
            this.parent = parent;
            this.key = key;
            this.level = level;
            if (level<6) children = new Node[32];
            else children = new Node[2];
        }

        private void addChild(int position, Node<K> child) {
            if (children[position]==null) numberOfChildren++;
            children[position] = child;
        }

        private void removeChild(int position) {
            if (children[position]!=null) numberOfChildren--;
            children[position] = null;
        }

        private Node<K> getChild(int position) {
            return children[position];
        }

        private int getNumberOfChildren() {
            return this.numberOfChildren;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("level=").append(level).append(" key=").append(toBinaryString(key)).append("\n");
            for (int i=0; i<children.length; i++) {
                Node<K> c = children[i];
                if (c!=null) builder.append(c.toString()).append(", ");
            }
            return builder.toString();
        }
    }

    private static final class KeyValueNode<K extends Number, V> extends Node<K> {
        private V value;

        private KeyValueNode(ArrayNode<K> parent, K key, V value) {
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

        private static <K extends Number, V> String getString(Node<K> node, int level, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node instanceof KeyValueNode) {
                KeyValueNode<K,V> kvNode = (KeyValueNode<K,V>) node;
                int position = getPosition(level,kvNode.key);
                builder.append(prefix + (isTail ? "└── " : "├── ") + toBinaryString(position) + "=" + toBinaryString(kvNode.key) + "=" + kvNode.value + "\n");
            } else {
                ArrayNode<K> arrayNode = (ArrayNode<K>) node;
                int position = getPosition(level,arrayNode.key);
                builder.append(prefix + (isTail ? "└── " : "├── ") + toBinaryString(position) + " level=" + arrayNode.level + "\n");
                List<Node<K>> children = new LinkedList<Node<K>>();
                for (int i=0; i<arrayNode.getNumberOfChildren(); i++) {
                    Node<K> child = arrayNode.getChild(i);
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

        private void addToSet(java.util.Set<java.util.Map.Entry<K, V>> set, Node<K> node) {
            if (node instanceof KeyValueNode) {
                KeyValueNode<K,V> kvNode = (KeyValueNode<K,V>) node;
                set.add(new JavaCompatibleMapEntry<K,V>(kvNode.key, kvNode.value));
            } else if (node instanceof ArrayNode) {
                ArrayNode<K> arrayNode = (ArrayNode<K>) node;
                for (int i=0; i<arrayNode.children.length; i++) {
                    Node<K> child = arrayNode.getChild(i);
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
