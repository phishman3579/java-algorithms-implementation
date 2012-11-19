package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;


/**
 * A trie, or prefix tree, is an ordered tree data structure that is used to
 * store an associative array where the keys are usually strings.
 * 
 * == This is NOT a compact Trie. ==
 * 
 * http://en.wikipedia.org/wiki/Trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Trie<C extends CharSequence> {

    private int size = 0;

    protected INodeCreator creator = null;
    protected Node root = null;

    /**
     * Default constructor.
     */
    public Trie() {
    }

    /**
     * Constructor with external Node creator.
     */
    public Trie(INodeCreator creator) {
        this.creator = creator;
    }

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
    protected Node createNewNode(Node parent, Character character, boolean isWord) {
        return (new Node(parent, character, isWord));
    }

    /**
     * Add sequence to trie.
     * 
     * @param seq
     *            to add to the trie.
     * @return True if sequence is added to trie or false if it already exists.
     */
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
        if (root == null) {
            if (this.creator == null) root = createNewNode(null, null, false);
            else root = this.creator.createNewNode(null, null, false);
        }

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
                if (this.creator == null) n = createNewNode(prev, c, false);
                else n = this.creator.createNewNode(prev, c, false);
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
            } else {
                // String already exists in Trie
                return null;
            }
        } else {
            // Create a new node for the input string
            if (this.creator == null) n = createNewNode(prev, c, true);
            else n = this.creator.createNewNode(prev, c, true);
            prev.addChild(n);
            size++;
            return n;
        }
    }

    /**
     * Remove sequence from the trie.
     * 
     * @param sequence
     *            to remove from the trie.
     * @return True if sequence was remove or false if sequence is not found.
     */
    public boolean remove(C sequence) {
        if (root == null) return false;

        // Find the key in the Trie
        Node previous = null;
        Node node = root;
        int length = (sequence.length() - 1);
        for (int i = 0; i <= length; i++) {
            char c = sequence.charAt(i);
            int index = node.childIndex(c);
            if (index >= 0) {
                previous = node;
                node = node.getChild(index);
            } else {
                return false;
            }
        }

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
                    if (idx >= 0) previous.parent.removeChild(idx);
                }
                previous = previous.parent;
            }
        }
        size--;
        return true;
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
     * Does the trie contain the sequence.
     * 
     * @param seq
     *            to locate in the trie.
     * @return True if sequence is in the trie.
     */
    public boolean contains(C seq) {
        Node n = this.getNode(seq);
        if (n == null || !n.isWord) return false;

        // If the node found in the trie does not have it's string
        // field defined then input string was not found
        return n.isWord;
    }

    /**
     * Number of sequences in the trie.
     * 
     * @return number of sequences in the trie.
     */
    public int size() {
        return size;
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

        protected Node[] children = new Node[MINIMUM_SIZE];
        protected int childrenSize = 0;
        protected Node parent = null;
        protected boolean isWord = false; // Signifies this node represents a
                                          // word
        protected Character character = null; // First character that is
                                              // different the parent's string

        protected Node(Node parent, Character character, boolean isWord) {
            this.parent = parent;
            this.character = character;
            this.isWord = isWord;
        }

        protected void addChild(Node node) {
            if (childrenSize >= children.length) {
                children = Arrays.copyOf(children, ((children.length * 3) / 2) + 1);
            }
            children[childrenSize++] = node;
        }

        protected boolean removeChild(int index) {
            if (index >= childrenSize) return false;
            children[index] = null;
            childrenSize--;
            System.arraycopy(children, index + 1, children, index, childrenSize - index);
            if (childrenSize >= MINIMUM_SIZE && childrenSize < children.length / 2) {
                children = Arrays.copyOf(children, childrenSize);
            }
            return true;
        }

        protected int childIndex(Character character) {
            for (int i = 0; i < childrenSize; i++) {
                Node c = children[i];
                if (c.character.equals(character)) return i;
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
        public Node createNewNode(Node parent, Character character, boolean type);
    }

    protected static class TriePrinter {

        public static <C extends CharSequence> void print(Trie<C> trie) {
            System.out.println(getString(trie));
        }

        public static <C extends CharSequence> String getString(Trie<C> tree) {
            return getString(tree.root, "", null, true);
        }

        protected static <C extends CharSequence> String getString(Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            if (node.character != null) {
                String temp = String.valueOf(node.character);
                if (previousString != null) string = previousString + temp;
                else string = temp;
            }
            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.isWord == true) ? ("(" + node.character + ") " + string) : node.character) + "\n");
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
}
