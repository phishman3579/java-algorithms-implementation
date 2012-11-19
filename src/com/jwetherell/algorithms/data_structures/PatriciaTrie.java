package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;


/**
 * A Patricia trie is a space-optimized trie data structure where each
 * non-terminating (black) node with only one child is merged with its child.
 * The result is that every internal non-terminating (black) node has at least
 * two children. Each terminating node (white) represents the end of a string.
 * 
 * http://en.wikipedia.org/wiki/Radix_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class PatriciaTrie<C extends CharSequence> {

    private int size = 0;

    protected INodeCreator creator = null;
    protected Node root = null;
    protected static final boolean BLACK = false;
    protected static final boolean WHITE = true;

    /**
     * Default constructor for trie.
     */
    public PatriciaTrie() {
    }

    /**
     * Constructor with external Node creator.
     */
    public PatriciaTrie(INodeCreator creator) {
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
    protected Node createNewNode(Node parent, char[] seq, boolean type) {
        return (new Node(parent, seq, type));
    }

    /**
     * Add sequence to trie.
     * 
     * @param seq
     *            to add to the trie.
     * @return True if added to the trie, false if it already exists.
     */
    public boolean add(C seq) {
        Node node = this.addSequence(seq);
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
    protected Node addSequence(C seq) {
        if (root == null) {
            if (this.creator == null) root = createNewNode(null, null, BLACK);
            else root = this.creator.createNewNode(null, null, BLACK);
        }

        int indexIntoParent = -1;
        int indexIntoString = -1;
        Node node = root;
        for (int i = 0; i <= seq.length();) {
            indexIntoString = i;
            indexIntoParent++;
            if (i == seq.length()) break;

            char c = seq.charAt(i);
            if (node.partOfThis(c, indexIntoParent)) {
                // Node has a char which is equal to char c at that index
                i++;
                continue;
            } else if (node.string != null && indexIntoParent < node.string.length) {
                // string is equal to part of this Node's string
                break;
            }

            Node child = node.getChildBeginningWithChar(c);
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

        Node addedNode = null;
        if (node.string != null && indexIntoParent < node.string.length) {
            char[] parentString = Arrays.copyOfRange(node.string, 0, indexIntoParent);
            char[] refactorString = Arrays.copyOfRange(node.string, indexIntoParent, node.string.length);

            Node parent = node.parent;
            if (indexIntoString < seq.length()) {
                // Creating a new parent by splitting a previous node and adding
                // a new node

                // Create new parent
                if (parent != null) parent.removeChild(node);
                Node newParent = null;
                if (this.creator == null) newParent = createNewNode(parent, parentString, BLACK);
                else newParent = this.creator.createNewNode(parent, parentString, BLACK);
                if (parent != null) parent.addChild(newParent);

                // Convert the previous node into a child of the new parent
                Node newNode1 = node;
                newNode1.parent = newParent;
                newNode1.string = refactorString;
                newParent.addChild(newNode1);

                // Create a new node from the rest of the string
                CharSequence newString = seq.subSequence(indexIntoString, seq.length());
                Node newNode2 = null;
                if (this.creator == null) newNode2 = createNewNode(newParent, newString.toString().toCharArray(), WHITE);
                else newNode2 = this.creator.createNewNode(newParent, newString.toString().toCharArray(), WHITE);
                newParent.addChild(newNode2);

                // New node which was added
                addedNode = newNode2;
            } else {
                // Creating a new parent by splitting a previous node and
                // converting the previous node
                if (parent != null) parent.removeChild(node);
                Node newParent = null;
                if (this.creator == null) newParent = createNewNode(parent, parentString, WHITE);
                else newParent = this.creator.createNewNode(parent, parentString, WHITE);
                if (parent != null) parent.addChild(newParent);

                // Parent node was created
                addedNode = newParent;

                // Convert the previous node into a child of the new parent
                Node newNode1 = node;
                newNode1.parent = newParent;
                newNode1.string = refactorString;
                newParent.addChild(newNode1);
            }
        } else if (node.string != null && seq.length() == indexIntoString) {
            // Found a node who exactly matches a previous node

            // Already exists as a white node (leaf node)
            if (node.type == WHITE) return null;

            // Was black (branching), now white (leaf)
            node.type = WHITE;
            addedNode = node;
        } else if (node.string != null) {
            // Adding a child
            CharSequence newString = seq.subSequence(indexIntoString, seq.length());
            Node newNode = null;
            if (this.creator == null) newNode = createNewNode(node, newString.toString().toCharArray(), WHITE);
            else newNode = this.creator.createNewNode(node, newString.toString().toCharArray(), WHITE);
            node.addChild(newNode);
            addedNode = newNode;
        } else {
            // Add to root node
            Node newNode = null;
            if (this.creator == null) newNode = createNewNode(node, seq.toString().toCharArray(), WHITE);
            else newNode = this.creator.createNewNode(node, seq.toString().toCharArray(), WHITE);
            node.addChild(newNode);
            addedNode = newNode;
        }

        size++;

        return addedNode;
    }

    /**
     * Remove the sequence from the trie.
     * 
     * @param seq
     *            to remove from the trie.
     * @return True if the sequence was removed or false if it doesn't exist in
     *         the trie.
     */
    public boolean remove(C seq) {
        Node node = getNode(seq);
        if (node == null) return false;

        // No longer a white node (leaf)
        node.type = BLACK;

        Node parent = node.parent;
        if (node.getChildrenSize() == 0) {
            // Remove the node if it has no children
            if (parent != null) parent.removeChild(node);
        } else if (node.getChildrenSize() == 1) {
            // Merge the node with it's child and add to node's parent

            Node child = node.getChild(0);
            StringBuilder builder = new StringBuilder();
            builder.append(node.string);
            builder.append(child.string);
            child.string = builder.toString().toCharArray();
            child.parent = parent;

            if (parent != null) {
                parent.removeChild(node);
                parent.addChild(child);
            }
        }

        // Walk up the tree and see if we can compact it
        while (parent != null && parent.type == BLACK && parent.getChildrenSize() == 1) {
            Node child = parent.getChild(0);
            // Merge with parent
            StringBuilder builder = new StringBuilder();
            if (parent.string != null) builder.append(parent.string);
            builder.append(child.string);
            child.string = builder.toString().toCharArray();
            if (parent.parent != null) {
                child.parent = parent.parent;
                parent.parent.removeChild(parent);
                parent.parent.addChild(child);
            }
            parent = parent.parent;
        }

        size--;

        return true;
    }

    /**
     * Does the sequence exist in the trie.
     * 
     * @param seq
     *            to locate in the trie.
     * @return True if the sequence exists in the trie.
     */
    public boolean contains(C seq) {
        Node node = getNode(seq);
        return (node != null && node.type == WHITE);
    }

    /**
     * Get node which represents the sequence in the trie.
     * 
     * @param seq
     *            to find a node for.
     * @return Node which represents the sequence or NULL if not found.
     */
    protected Node getNode(C seq) {
        Node node = root;
        int indexIntoParent = -1;
        for (int i = 0; i < seq.length();) {
            indexIntoParent++;

            char c = seq.charAt(i);
            if (node.partOfThis(c, indexIntoParent)) {
                // Node has a char which is equal to char c at that index
                i++;
                continue;
            } else if (node.string != null && indexIntoParent < node.string.length) {
                // string is equal to part of this Node's string
                return null;
            }

            Node child = node.getChildBeginningWithChar(c);
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

        if (node.string != null && indexIntoParent == (node.string.length - 1)) {
            // Get the partial string to compare against the node's string
            int length = node.string.length;
            CharSequence sub = seq.subSequence(seq.length() - length, seq.length());
            for (int i = 0; i < length; i++) {
                if (node.string[i] != sub.charAt(i)) return null;
            }
            return node;
        }
        return null;
    }

    /**
     * Number of nodes in the trie.
     * 
     * @return number of nodes in the trie.
     */
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return PatriciaTriePrinter.getString(this);
    }

    protected static class Node implements Comparable<Node> {

        private static final int MINIMUM_SIZE = 2;

        private Node[] children = new Node[MINIMUM_SIZE];
        private int childrenSize = 0;

        protected Node parent = null;
        protected boolean type = BLACK;
        protected char[] string = null;

        protected Node(Node parent) {
            this.parent = parent;
        }

        protected Node(Node parent, char[] string) {
            this(parent);
            this.string = string;
        }

        protected Node(Node parent, char[] string, boolean type) {
            this(parent, string);
            this.type = type;
        }

        protected void addChild(Node node) {
            if (childrenSize >= children.length) {
                children = Arrays.copyOf(children, ((children.length * 3) / 2) + 1);
            }
            children[childrenSize++] = node;
            Arrays.sort(children, 0, childrenSize);
        }

        private boolean removeChild(Node child) {
            boolean found = false;
            if (childrenSize == 0) return found;
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
                Node c = children[i];
                if (c.string != null && c.string.length > 0 && c.string[0] == character) return i;
            }
            return Integer.MIN_VALUE;
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

        protected Node getChild(int index) {
            if (index >= childrenSize) return null;
            return children[index];
        }

        protected int getChildrenSize() {
            return childrenSize;
        }

        protected boolean partOfThis(char c, int idx) {
            // Search myself
            if (string != null && idx < string.length && string[idx] == c) return true;
            return false;
        }

        protected Node getChildBeginningWithChar(char c) {
            // Search children
            Node node = null;
            for (int i = 0; i < this.childrenSize; i++) {
                Node child = this.children[i];
                if (child.string[0] == c) return child;
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
            if (string != null) output = String.valueOf(string);
            builder.append("string = ").append((output != null) ? output : "NULL").append("\n");
            builder.append("type = ").append(type).append("\n");
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Node node) {
            if (node == null) return -1;

            int length = string.length;
            if (node.string.length < length) length = node.string.length;
            for (int i = 0; i < length; i++) {
                Character a = string[i];
                Character b = node.string[i];
                int c = a.compareTo(b);
                if (c != 0) return c;
            }

            if (this.type == BLACK && node.type == WHITE) return -1;
            else if (node.type == BLACK && this.type == WHITE) return 1;

            if (this.getChildrenSize() < node.getChildrenSize()) return -1;
            else if (this.getChildrenSize() > node.getChildrenSize()) return 1;

            return 0;
        }
    }

    protected static interface INodeCreator {

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
        public Node createNewNode(Node parent, char[] seq, boolean type);
    }

    protected static class PatriciaTriePrinter<C extends CharSequence> {

        protected static <C extends CharSequence> String getString(PatriciaTrie<C> tree) {
            return getString(tree.root, "", null, true);
        }

        protected static <C extends CharSequence> String getString(Node node, String prefix, String previousString, boolean isTail) {
            StringBuilder builder = new StringBuilder();
            String thisString = null;
            if (node.string != null) thisString = String.valueOf(node.string);
            String fullString = ((previousString != null) ? previousString : "") + thisString;
            builder.append(prefix
                    + (isTail ? "└── " : "├── ")
                    + ((thisString != null) ? "[" + ((node.type == WHITE) ? "white" : "black") + "] "
                            + ((node.type == WHITE) ? "(" + thisString + ") " + fullString : thisString) : "[" + ((node.type == WHITE) ? "white" : "black")
                            + "]") + "\n");
            if (node.children != null) {
                for (int i = 0; i < node.getChildrenSize() - 1; i++) {
                    builder.append(getString(node.getChild(i), prefix + (isTail ? "    " : "│   "), thisString, false));
                }
                if (node.getChildrenSize() >= 1) {
                    builder.append(getString(node.getChild(node.getChildrenSize() - 1), prefix + (isTail ? "    " : "│   "), thisString, true));
                }
            }
            return builder.toString();
        }
    }
}
