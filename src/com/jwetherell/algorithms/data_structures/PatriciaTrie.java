package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

/**
 * A Patricia trie (radix tree) is a space-optimized trie data structure where each
 * non-terminating (black) node with only one child is merged with its child.
 * The result is that every internal non-terminating (black) node has at least
 * two children. Each terminating node (white) represents the end of a string.
 * 
 * http://en.wikipedia.org/wiki/Radix_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class PatriciaTrie<C extends CharSequence> implements ITree<C> {

    private int size = 0;

    protected INodeCreator<C> creator = null;
    protected Node<C> root = null;
    protected static final boolean BLACK = false; // non-terminating
    protected static final boolean WHITE = true; // terminating

    public PatriciaTrie() { }

    /**
     * Constructor with external Node creator.
     */
    public PatriciaTrie(INodeCreator<C> creator) {
        this.creator = creator;
    }

    /**
     * Create a new node for sequence.
     * 
     * @param parent
     *            node of the new node.
     * @param seq
     *            of characters which represents this node.
     * @param type
     *            of the node, can be either BLACK or WHITE.
     * @return Node which was created.
     */
    protected Node<C> createNewNode(Node<C> parent, C seq, boolean type) {
        return (new Node<C>(parent, seq, type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(C seq) {
        Node<C> node = this.addSequence(seq);
        return (node != null);
    }

    /**
     * Add CharSequence to trie and return the Node which represents the
     * sequence.
     * 
     * @param seq
     *            to add to the trie.
     * @return Node which represents the sequence in the trie or NULL if the
     *         sequence already exists.
     */
    protected Node<C> addSequence(C seq) {
        if (root == null) {
            if (this.creator == null) {
                root = createNewNode(null, null, BLACK);
            } else {
                root = this.creator.createNewNode(null, null, BLACK);
            }
        }

        int indexIntoParent = -1;
        int indexIntoString = -1;
        Node<C> node = root;
        for (int i = 0; i <= seq.length();) {
            indexIntoString = i;
            indexIntoParent++;
            if (i == seq.length())
                break;

            char c = seq.charAt(i);
            if (node.partOfThis(c, indexIntoParent)) {
                // Node has a char which is equal to char c at that index
                i++;
                continue;
            } else if (node.string != null && indexIntoParent < node.string.length()) {
                // string is equal to part of this Node's string
                break;
            }

            Node<C> child = node.getChildBeginningWithChar(c);
            if (child != null) {
                // Found a child node starting with char c
                indexIntoParent = 0;
                node = child;
                i++;
            } else {
                // Node doesn't have a child starting with char c
                break;
            }
        }

        Node<C> addedNode = null;
        if (node.string != null && indexIntoParent < node.string.length()) {
            C parentString = (C)node.string.subSequence(0, indexIntoParent);
            C refactorString = (C)node.string.subSequence(indexIntoParent, node.string.length());

            Node<C> parent = node.parent;
            if (indexIntoString < seq.length()) {
                // Creating a new parent by splitting a previous node and adding
                // a new node

                // Create new parent
                if (parent != null)
                    parent.removeChild(node);
                Node<C> newParent = null;
                if (this.creator == null)
                    newParent = createNewNode(parent, parentString, BLACK);
                else
                    newParent = this.creator.createNewNode(parent, parentString, BLACK);
                if (parent != null)
                    parent.addChild(newParent);

                // Convert the previous node into a child of the new parent
                Node<C> newNode1 = node;
                newNode1.parent = newParent;
                newNode1.string = refactorString;
                newParent.addChild(newNode1);

                // Create a new node from the rest of the string
                CharSequence newString = seq.subSequence(indexIntoString, seq.length());
                Node<C> newNode2 = null;
                if (this.creator == null)
                    newNode2 = createNewNode(newParent, (C)newString, WHITE);
                else
                    newNode2 = this.creator.createNewNode(newParent, (C)newString, WHITE);
                newParent.addChild(newNode2);

                // New node which was added
                addedNode = newNode2;
            } else {
                // Creating a new parent by splitting a previous node and
                // converting the previous node
                if (parent != null)
                    parent.removeChild(node);
                Node<C> newParent = null;
                if (this.creator == null)
                    newParent = createNewNode(parent, parentString, WHITE);
                else
                    newParent = this.creator.createNewNode(parent, parentString, WHITE);
                if (parent != null)
                    parent.addChild(newParent);

                // Parent node was created
                addedNode = newParent;

                // Convert the previous node into a child of the new parent
                Node<C> newNode1 = node;
                newNode1.parent = newParent;
                newNode1.string = refactorString;
                newParent.addChild(newNode1);
            }
        } else if (node.string != null && seq.length() == indexIntoString) {
            // Found a node who exactly matches a previous node

            // Already exists as a white node (leaf node)
            if (node.type == WHITE)
                return null;

            // Was black (branching), now white (leaf)
            node.type = WHITE;
            addedNode = node;
        } else if (node.string != null) {
            // Adding a child
            CharSequence newString = seq.subSequence(indexIntoString, seq.length());
            Node<C> newNode = null;
            if (this.creator == null)
                newNode = createNewNode(node, (C)newString, WHITE);
            else
                newNode = this.creator.createNewNode(node, (C)newString, WHITE);
            node.addChild(newNode);
            addedNode = newNode;
        } else {
            // Add to root node
            Node<C> newNode = null;
            if (this.creator == null)
                newNode = createNewNode(node, (C)seq, WHITE);
            else
                newNode = this.creator.createNewNode(node, (C)seq, WHITE);
            node.addChild(newNode);
            addedNode = newNode;
        }

        size++;

        return addedNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(C seq) {
        Node<C> node = getNode(seq);
        return (node != null && node.type == WHITE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C remove(C seq) {
        C removed = null;
        Node<C> node = getNode(seq);
        if (node!=null) removed = node.string;
        remove(node);
        return removed;
    }

    protected void remove(Node<C> node) {
        if (node == null) return;

        // No longer a white node (leaf)
        node.type = BLACK;

        Node<C> parent = node.parent;
        if (node.getChildrenSize() == 0) {
            // Remove the node if it has no children
            if (parent != null)
                parent.removeChild(node);
        } else if (node.getChildrenSize() == 1) {
            // Merge the node with it's child and add to node's parent

            Node<C> child = node.getChild(0);
            StringBuilder builder = new StringBuilder();
            builder.append(node.string);
            builder.append(child.string);
            child.string = (C)builder;
            child.parent = parent;

            if (parent != null) {
                parent.removeChild(node);
                parent.addChild(child);
            }
        }

        // Walk up the tree and see if we can compact it
        while (parent != null && parent.type == BLACK && parent.getChildrenSize() == 1) {
            Node<C> child = parent.getChild(0);
            // Merge with parent
            StringBuilder builder = new StringBuilder();
            if (parent.string != null)
                builder.append(parent.string);
            builder.append(child.string);
            child.string = (C)builder;
            if (parent.parent != null) {
                child.parent = parent.parent;
                parent.parent.removeChild(parent);
                parent.parent.addChild(child);
            }
            parent = parent.parent;
        }

        size--;
    }

    /**
     * Get node which represents the sequence in the trie.
     * 
     * @param seq
     *            to find a node for.
     * @return Node which represents the sequence or NULL if not found.
     */
    protected Node<C> getNode(C seq) {
        Node<C> node = root;
        int indexIntoParent = -1;
        for (int i = 0; i < seq.length();) {
            indexIntoParent++;

            char c = seq.charAt(i);
            if (node.partOfThis(c, indexIntoParent)) {
                // Node has a char which is equal to char c at that index
                i++;
                continue;
            } else if (node.string != null && indexIntoParent < node.string.length()) {
                // string is equal to part of this Node's string
                return null;
            }

            Node<C> child = node.getChildBeginningWithChar(c);
            if (child != null) {
                // Found a child node starting with char c
                indexIntoParent = 0;
                node = child;
                i++;
            } else {
                // Node doesn't have a child starting with char c
                return null;
            }
        }

        if (node.string != null && indexIntoParent == (node.string.length() - 1)) {
            // Get the partial string to compare against the node's string
            int length = node.string.length();
            CharSequence sub = seq.subSequence(seq.length() - length, seq.length());
            for (int i = 0; i < length; i++) {
                if (node.string.charAt(i) != sub.charAt(i))
                    return null;
            }
            return node;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        java.util.Set<C> keys = new java.util.HashSet<C>();
        Node<C> node = root;
        if (node!=null && !validate(node,"",keys)) return false;
        return (keys.size()==size());
    }

    private boolean validate(Node<C> node, String string, java.util.Set<C> keys) {
        StringBuilder builder = new StringBuilder(string);
        builder.append(node.string);
        String s = builder.toString();

        if (node.type == WHITE) {
            C c = (C)s;
            if (c==null) return false;
            if (keys.contains(c)) return false;
            keys.add(c);
        }
        for (int i=0; i<node.childrenSize; i++) {
            Node<C> n = node.getChild(i);
            if (n==null) return false;
            if (n.parent!=node) return false;
            if (n!=null && !validate(n,s,keys)) return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<C> toCollection() {
        return (new JavaCompatiblePatriciaTrie<C>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return PatriciaTriePrinter.getString(this);
    }

    protected static class Node<C extends CharSequence> implements Comparable<Node<C>> {

        private static final int MINIMUM_SIZE = 2;

        private Node<C>[] children = new Node[MINIMUM_SIZE];

        protected int childrenSize = 0;
        protected Node<C> parent = null;
        protected boolean type = BLACK;

        protected C string = (C)new StringBuilder();

        protected Node(Node<C> parent) {
            this.parent = parent;
        }

        protected Node(Node<C> parent, C string) {
            this(parent);
            this.string = string;
        }

        protected Node(Node<C> parent, C string, boolean type) {
            this(parent, string);
            this.type = type;
        }

        protected void addChild(Node<C> node) {
            if (childrenSize >= children.length) {
                children = Arrays.copyOf(children, ((children.length * 3) / 2) + 1);
            }
            children[childrenSize++] = node;
            Arrays.sort(children, 0, childrenSize);
        }

        private boolean removeChild(Node<C> child) {
            boolean found = false;
            if (childrenSize == 0)
                return found;
            for (int i = 0; i < childrenSize; i++) {
                if (children[i].equals(child)) {
                    found = true;
                } else if (found) {
                    // shift the rest of the keys down
                    System.arraycopy(children, i, children, i - 1, childrenSize - i);
                    break;
                }
            }
            if (found) {
                childrenSize--;
                if (childrenSize >= MINIMUM_SIZE && childrenSize < children.length / 2) {
                    children = Arrays.copyOf(children, childrenSize);
                }
            }
            return found;
        }

        protected int childIndex(Character character) {
            for (int i = 0; i < childrenSize; i++) {
                Node<C> c = children[i];
                if (c.string != null && c.string.length() > 0 && c.string.charAt(0) == character)
                    return i;
            }
            return Integer.MIN_VALUE;
        }

        protected boolean removeChild(int index) {
            if (index >= childrenSize)
                return false;
            children[index] = null;
            childrenSize--;
            System.arraycopy(children, index + 1, children, index, childrenSize - index);
            if (childrenSize >= MINIMUM_SIZE && childrenSize < children.length / 2) {
                children = Arrays.copyOf(children, childrenSize);
            }
            return true;
        }

        protected Node<C> getChild(int index) {
            if (index >= childrenSize)
                return null;
            return children[index];
        }

        protected int getChildrenSize() {
            return childrenSize;
        }

        protected boolean partOfThis(char c, int idx) {
            // Search myself
            if (string != null && idx < string.length() && string.charAt(idx) == c)
                return true;
            return false;
        }

        protected Node<C> getChildBeginningWithChar(char c) {
            // Search children
            Node<C> node = null;
            for (int i = 0; i < this.childrenSize; i++) {
                Node<C> child = this.children[i];
                if (child.string.charAt(0) == c)
                    return child;
            }
            return node;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            String output = null;
            if (string != null)
                output = String.valueOf(string);
            builder.append("string = ").append((output != null) ? output : "NULL").append("\n");
            builder.append("type = ").append(type).append("\n");
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Node<C> node) {
            if (node == null)
                return -1;

            int length = string.length();
            if (node.string.length() < length)
                length = node.string.length();
            for (int i = 0; i < length; i++) {
                Character a = string.charAt(i);
                Character b = node.string.charAt(i);
                int c = a.compareTo(b);
                if (c != 0)
                    return c;
            }

            if (this.type == BLACK && node.type == WHITE)
                return -1;
            else if (node.type == BLACK && this.type == WHITE)
                return 1;

            if (this.getChildrenSize() < node.getChildrenSize())
                return -1;
            else if (this.getChildrenSize() > node.getChildrenSize())
                return 1;

            return 0;
        }
    }

    protected static interface INodeCreator<C extends CharSequence> {

        /**
         * Create a new node for sequence.
         * 
         * @param parent
         *            node of the new node.
         * @param seq
         *            of characters which represents this node.
         * @param type
         *            of the node, can be either BLACK or WHITE.
         * @return Node which was created.
         */
        public Node<C> createNewNode(Node<C> parent, C seq, boolean type);
    }

    protected static class PatriciaTriePrinter<C extends CharSequence> {

        protected static <C extends CharSequence> String getString(PatriciaTrie<C> tree) {
            return getString(tree.root, "", null, true);
        }

        protected static <C extends CharSequence> String getString(Node<C> node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String thisString = null;
            if (node.string != null)
                thisString = String.valueOf(node.string);
            String fullString = ((previousString != null) ? previousString : "") + thisString;
            builder.append(prefix
                    + (isTail ? "└── " : "├── ")
                    + ((thisString != null) ? "[" + ((node.type == WHITE) ? "white" : "black") + "] "
                            + ((node.type == WHITE) ? "(" + thisString + ") " + fullString : thisString) : "["
                            + ((node.type == WHITE) ? "white" : "black") + "]") + "\n");
            if (node.children != null) {
                for (int i = 0; i < node.getChildrenSize() - 1; i++) {
                    builder.append(getString(node.getChild(i), prefix + (isTail ? "    " : "│   "), thisString, false));
                }
                if (node.getChildrenSize() >= 1) {
                    builder.append(getString(node.getChild(node.getChildrenSize() - 1), prefix
                            + (isTail ? "    " : "│   "), thisString, true));
                }
            }
            return builder.toString();
        }
    }

    public static class JavaCompatiblePatriciaTrie<C extends CharSequence> extends java.util.AbstractCollection<C> {

        private PatriciaTrie<C> trie = null;

        public JavaCompatiblePatriciaTrie(PatriciaTrie<C> list) {
            this.trie = list;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(C value) {
            return trie.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return (trie.remove((C)value)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return trie.contains((C)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return trie.size;
        }

        /**
         * {@inheritDoc}
         * 
         * WARNING: This iterator makes a copy of the trie's contents during it's construction!
         */
        @Override
        public java.util.Iterator<C> iterator() {
            return (new PatriciaTrieIterator<C>(trie));
        }

        private static class PatriciaTrieIterator<C extends CharSequence> implements java.util.Iterator<C> {

            private PatriciaTrie<C> trie = null;
            private PatriciaTrie.Node<C> lastNode = null;
            private java.util.Iterator<java.util.Map.Entry<Node<C>, String>> iterator = null;

            protected PatriciaTrieIterator(PatriciaTrie<C> trie) {
                this.trie = trie;
                java.util.Map<PatriciaTrie.Node<C>,String> map = new java.util.LinkedHashMap<PatriciaTrie.Node<C>,String>();
                if (this.trie.root!=null) {
                    getNodesWhichRepresentsWords(this.trie.root,"",map);
                }
                iterator = map.entrySet().iterator();
            }

            private void getNodesWhichRepresentsWords(PatriciaTrie.Node<C> node, String string, java.util.Map<PatriciaTrie.Node<C>,String> nodesMap) {
                StringBuilder builder = new StringBuilder(string);
                if (node.string!=null) builder.append(node.string);
                if (node.type == PatriciaTrie.WHITE) nodesMap.put(node,builder.toString()); //Terminating
                for (int i=0; i<node.childrenSize; i++) {
                    PatriciaTrie.Node<C> child = node.getChild(i);
                    getNodesWhichRepresentsWords(child,builder.toString(),nodesMap);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                if (iterator!=null && iterator.hasNext()) return true;
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public C next() {
                if (iterator==null) return null;

                java.util.Map.Entry<PatriciaTrie.Node<C>,String> entry = iterator.next();
                lastNode = entry.getKey();
                return (C)entry.getValue();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (iterator==null || trie==null) return;

                iterator.remove();
                this.trie.remove(lastNode);
            }
        }
    }
}
