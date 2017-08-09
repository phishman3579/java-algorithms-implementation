package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.interfaces.ITree;

/**
 * A trie, or prefix tree, is an ordered tree data structure that is used to
 * store an associative array where the keys are usually strings.
 * <p>
 * NOTE: This is NOT a compact Trie
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Trie">Trie (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class Trie<C extends CharSequence> implements ITree<C> {

    protected INodeCreator creator;
    protected Node root;

    private int size = 0;

    public Trie() {
        this.creator = new INodeCreator() {
            /**
             * {@inheritDoc}
             */
            @Override
            public Node createNewNode(Node parent, Character character, boolean isWord) {
                return (new Node(parent, character, isWord));
            }
        };
    }

    /**
     * Constructor with external Node creator.
     */
    public Trie(INodeCreator creator) {
        this.creator = creator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(C seq) {
        return (this.addSequence(seq) != null);
    }

    /**
     * Add sequence to trie.
     * 
     * @param seq
     *            to add to the trie.
     * @return Node which was added to trie or null if it already exists.
     */
    protected Node addSequence(C seq) {
        if (root == null)
            root = this.creator.createNewNode(null, Node.SENTINAL, false);

        int length = (seq.length() - 1);
        Node prev = root;
        // For each Character in the input, we'll either go to an already define
        // child or create a child if one does not exist
        for (int i = 0; i < length; i++) {
            Node n = null;
            Character c = seq.charAt(i);
            int index = prev.childIndex(c);
            // If 'prev' has a child which starts with Character c
            if (index >= 0) {
                // Go to the child
                n = prev.getChild(index);
            } else {
                // Create a new child for the character
                n = this.creator.createNewNode(prev, c, false);
                prev.addChild(n);
            }
            prev = n;
        }

        // Deal with the first character of the input string not found in the
        // trie
        Node n = null;
        Character c = seq.charAt(length);
        int index = prev.childIndex(c);
        // If 'prev' already contains a child with the last Character
        if (index >= 0) {
            n = prev.getChild(index);
            // If the node doesn't represent a string already
            if (n.isWord == false) {
                // Set the string to equal the full input string
                n.character = c;
                n.isWord = true;
                size++;
                return n;
            }
            // String already exists in Trie
            return null;
        }
        // Create a new node for the input string
        n = this.creator.createNewNode(prev, c, true);
        prev.addChild(n);
        size++;
        return n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(C seq) {
        Node n = this.getNode(seq);
        if (n == null || !n.isWord) return false;

        // If the node found in the trie does not have it's string
        // field defined then input string was not found
        return n.isWord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C remove(C sequence) {
        if (root == null) return null;

        // Find the key in the Trie
        Node node = getNode(sequence);
        if (node==null) return null;

        return remove(node);
    }

    protected C remove(Node node) {
        Node previous = node.parent;
        if (node.childrenSize > 0) {
            // The node which contains the input string and has children, just
            // NULL out the string
            node.isWord = false;
        } else {
            // The node which contains the input string does NOT have children
            int index = previous.childIndex(node.character);
            // Remove node from previous node
            previous.removeChild(index);
            // Go back up the trie removing nodes until you find a node which
            // represents a string
            while (previous != null && previous.isWord == false && previous.childrenSize == 0) {
                if (previous.parent != null) {
                    int idx = previous.parent.childIndex(previous.character);
                    if (idx >= 0)
                        previous.parent.removeChild(idx);
                }
                previous = previous.parent;
            }
        }

        size--;

        return (C)(String.valueOf(node.character));
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
     * Get node which represents the sequence in the trie.
     * 
     * @param seq
     *            to find a node for.
     * @return Node which represents the sequence or NULL if not found.
     */
    protected Node getNode(C seq) {
        if (root == null) return null;

        // Find the string in the trie
        Node n = root;
        int length = (seq.length() - 1);
        for (int i = 0; i <= length; i++) {
            char c = seq.charAt(i);
            int index = n.childIndex(c);
            if (index >= 0) {
                n = n.getChild(index);
            } else {
                // string does not exist in trie
                return null;
            }
        }

        return n;
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
        Node node = root;
        if (node!=null && !validate(node,"",keys)) return false;
        return (keys.size()==size());
    }

    private boolean validate(Node node, String string, java.util.Set<C> keys) {
        StringBuilder builder = new StringBuilder(string);
        builder.append(node.character);
        String s = builder.toString();

        if (node.isWord) {
            C c = (C)s;
            if (c==null) return false;
            if (keys.contains(c)) return false;
            keys.add(c);
        }
        for (int i=0; i<node.childrenSize; i++) {
            Node n = node.getChild(i);
            if (n==null) return false;
            if (n.parent!=node) return false;
            if (!validate(n,s,keys)) return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<C> toCollection() {
        return (new JavaCompatibleTrie<C>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TriePrinter.getString(this);
    }

    protected static class Node {

        private static final int MINIMUM_SIZE = 2;

        protected static final char SENTINAL = '\0';

        protected Node[] children = new Node[MINIMUM_SIZE];
        protected int childrenSize = 0;
        protected Node parent = null;
        protected boolean isWord = false;  // Signifies this node represents a word
        protected char character = SENTINAL;  // First character that is different the parent's string

        protected Node(Node parent, Character character, boolean isWord) {
            this.parent = parent;
            this.character = character;
            this.isWord = isWord;
        }

        protected void addChild(Node node) {
            int growSize = children.length;
            if (childrenSize >= children.length) {
                children = Arrays.copyOf(children, (growSize + (growSize>>1)));
            }
            children[childrenSize++] = node;
        }

        protected boolean removeChild(int index) {
            if (index >= childrenSize) return false;

            children[index] = null;
            childrenSize--;

            // Shift down the array
            System.arraycopy(children, index + 1, children, index, childrenSize - index);

            int shrinkSize = childrenSize;
            if (childrenSize >= MINIMUM_SIZE && childrenSize < (shrinkSize + (shrinkSize<<1))) {
                System.arraycopy(children, 0, children, 0, childrenSize);
            }

            return true;
        }

        protected int childIndex(Character parentChar) {
            for (int i = 0; i < childrenSize; i++) {
                Node childChar = children[i];
                if (parentChar.equals(childChar.character)) return i;
            }
            return Integer.MIN_VALUE;
        }

        protected Node getChild(int index) {
            if (index >= childrenSize) return null;
            return children[index];
        }

        protected int getChildrenSize() {
            return childrenSize;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (isWord == true) builder.append("Node=").append(isWord).append("\n");
            for (int i = 0; i < childrenSize; i++) {
                Node c = children[i];
                builder.append(c.toString());
            }
            return builder.toString();
        }
    }

    protected static interface INodeCreator {

        /**
         * Create a new node for sequence.
         * 
         * @param parent
         *            node of the new node.
         * @param character
         *            which represents this node.
         * @param isWord
         *            signifies if the node represents a word.
         * @return Node which was created.
         */
        public Node createNewNode(Node parent, Character character, boolean isWord);
    }

    protected static class TriePrinter {

        public static <C extends CharSequence> void print(Trie<C> trie) {
            System.out.println(getString(trie));
        }

        public static <C extends CharSequence> String getString(Trie<C> tree) {
            if (tree.root == null) 
                return "Tree has no nodes.";
            return getString(tree.root, "", null, true);
        }

        protected static String getString(Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.character != Node.SENTINAL) {
                String temp = String.valueOf(node.character);
                if (previousString != null)
                    string = previousString + temp;
                else
                    string = temp;
            }
            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.isWord == true) ? 
                              ("(" + node.character + ") " + string)
                          : 
                              node.character) + "\n"
            );
            if (node.children != null) {
                for (int i = 0; i < node.childrenSize - 1; i++) {
                    builder.append(getString(node.children[i], prefix + (isTail ? "    " : "│   "), string, false));
                }
                if (node.childrenSize >= 1) {
                    builder.append(getString(node.children[node.childrenSize - 1], prefix + (isTail ? "    " : "│   "), string, true));
                }
            }
            return builder.toString();
        }
    }

    public static class JavaCompatibleTrie<C extends CharSequence> extends java.util.AbstractCollection<C> {

        private Trie<C> trie = null;

        public JavaCompatibleTrie(Trie<C> trie) {
            this.trie = trie;
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
            return (new TrieIterator<C>(trie));
        }

        private static class TrieIterator<C extends CharSequence> implements java.util.Iterator<C> {

            private Trie<C> trie = null;
            private Trie.Node lastNode = null;
            private java.util.Iterator<java.util.Map.Entry<Node, String>> iterator = null;

            protected TrieIterator(Trie<C> trie) {
                this.trie = trie;
                java.util.Map<Trie.Node,String> map = new java.util.LinkedHashMap<Trie.Node,String>();
                if (this.trie.root!=null) {
                    getNodesWhichRepresentsWords(this.trie.root,"",map);
                }
                iterator = map.entrySet().iterator();
            }

            private void getNodesWhichRepresentsWords(Trie.Node node, String string, java.util.Map<Trie.Node,String> nodesMap) {
                StringBuilder builder = new StringBuilder(string);
                if (node.character!=Node.SENTINAL) builder.append(node.character);
                if (node.isWord) nodesMap.put(node,builder.toString());
                for (int i=0; i<node.childrenSize; i++) {
                    Node child = node.getChild(i);
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

                java.util.Map.Entry<Trie.Node,String> entry = iterator.next();
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
