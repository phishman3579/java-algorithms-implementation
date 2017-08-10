package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.interfaces.ITree;

/**
 * In computer science, a ternary search tree is a type of trie (sometimes called a prefix tree) where nodes are arranged in a manner similar to a binary search tree, but with up to three 
 * children rather than the binary tree's limit of two.
 * <br>
 * <a href="https://www.cs.upc.edu/~ps/downloads/tst/tst.html">This implementation is based on Jon Bentley and Bob Sedgewick's paper.</a>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Ternary_search_tree">Ternary Search Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class TernarySearchTree<C extends CharSequence> implements ITree<C> {

    protected INodeCreator creator;
    protected Node root;

    private int size = 0;

    public TernarySearchTree() {
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
    public TernarySearchTree(INodeCreator creator) {
        this.creator = creator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(C value) {
        if (value == null)
            return false;

        final int before = size;
        if (root == null)
            this.root = insert(null, null, value, 0);
        else
            insert(null, root, value, 0);
        final int after = size;

        // Successful if the size has increased by one
        return (before==(after-1));
    }

    private Node insert(Node parent, Node node, C value, int idx) {
        if (idx >= value.length())
            return null;

        final char c = value.charAt(idx);
        final boolean isWord = (idx==(value.length()-1));

        if (node == null) {
            // Create new node
            node = this.creator.createNewNode(parent, c, isWord);

            // If new node represents a "word", increase the size
            if (isWord)
                size++;
        } else if (c==node.character && isWord && !node.isWord) {
            // Changing an existing node into a "word" node
            node.isWord = true;
            // Increase the size
            size++;
        }

        if (c < node.character) {
            node.loKid = insert(node, node.loKid, value, idx);
        } else if (c > node.character) {
            node.hiKid = insert(node, node.hiKid, value, idx);
        } else if (idx < (value.length()-1)) {
            // Not done with whole string yet
            node.kid = insert(node, node.kid, value, ++idx);
        }
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C remove(C value) {
        if (value == null || root == null)
            return null;

        // Find the node
        final Node node = search(root, value, 0);

        // If node was not found or the node is not a "word"
        if (node == null || !node.isWord)
            return null;

        // Node was found, remove from tree if possible
        node.isWord = false;
        remove(node);
        size--;
        return value;
    }

    private void remove(Node node) {
        // If node is a "word", stop the recursive pruning
        if (node.isWord)
            return;

        // If node has at least one child, we cannot prune it.
        if (node.loKid!=null || node.kid!=null || node.hiKid!=null) 
            return;

        // Node has no children, prune the node
        final Node parent = node.parent;
        if (parent != null) {
            // Remove node from parent
            if (parent.loKid==node) {
                parent.loKid = null;
            } else if (parent.hiKid==node) {
                parent.hiKid = null;
            } else if (parent.kid==node) {
                parent.kid = null;
            }

            // If parent is not a "word" node, go up the tree and prune if possible
            if (!node.isWord)
                remove(parent);
        } else {
            // If node doesn't have a parent, it's root.
            this.root = null;
        }
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
    public boolean contains(C value) {
        if (value == null)
            return false;

        // Find the node
        final Node node = search(root, value, 0);

        // If node isn't null and it represents a "word" then the tree contains the value
        return (node!=null && node.isWord);
    }

    private Node search(Node node, C value, int idx) {
        if (node == null || idx>=value.length())
            return null;

        final char c = value.charAt(idx);

        if (c < node.character)
            return search(node.loKid, value, idx);
        if (c > node.character)
            return search(node.hiKid, value, idx);
        if (idx < (value.length()-1)) {
            // c == node.char and there is still some characters left in the search string
            return search(node.kid, value, ++idx);
        }
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean validate() {
        if (this.root == null)
            return true;
        return validate(root);
    }

    private boolean validate(Node node) {
        boolean result = false;
        if (node.loKid != null) {
            if (node.loKid.character >= node.character)
                return false;
            result = validate(node.loKid);
            if (!result)
                return false;
        }

        if (node.kid != null) {
            result = validate(node.kid);
            if (!result)
                return false;
        }

        if (node.hiKid != null) {
            if (node.hiKid.character <= node.character)
                return false;
            result = validate(node.hiKid);
            if (!result)
                return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<C> toCollection() {
        return (new JavaCompatibleTree<C>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
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

    protected static class TreePrinter {

        public static <C extends CharSequence> void print(TernarySearchTree<C> tree) {
            System.out.println(getString(tree));
        }

        public static <C extends CharSequence> String getString(TernarySearchTree<C> tree) {
            if (tree.root == null) 
                return "Tree has no nodes.";
            return getString(tree.root, "", null, true);
        }

        protected static String getString(Node node, String prefix, String previousString, boolean isTail) {
            final StringBuilder builder = new StringBuilder();
            String string = "";
            if (previousString != null)
                string = previousString;
            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.isWord == true) ? 
                              ("(" + node.character + ") " + string+String.valueOf(node.character))
                          : 
                              node.character) + "\n"
            );
            if (node.loKid != null)
                builder.append(getString(node.loKid, prefix + (isTail ? "    " : "│   "), string, false));
            if (node.kid != null)
                builder.append(getString(node.kid, prefix + (isTail ? "    " : "│   "), string+String.valueOf(node.character), false));
            if (node.hiKid != null)
                builder.append(getString(node.hiKid, prefix + (isTail ? "    " : "│   "), string, true));
            return builder.toString();
        }
    }

    protected static class Node {

        private final Node parent;
        private final char character;

        private boolean isWord;

        protected Node loKid;
        protected Node kid;
        protected Node hiKid;

        protected Node(Node parent, char character, boolean isWord) {
            this.parent = parent;
            this.character = character;
            this.isWord = isWord;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("char=").append(this.character).append('\n');
            if (this.loKid != null)
                builder.append('\t').append("lo=").append(this.loKid.character).append('\n');
            if (this.kid != null)
                builder.append('\t').append("eq=").append(this.kid.character).append('\n');
            if (this.hiKid != null)
                builder.append('\t').append("hi=").append(this.hiKid.character).append('\n');
            return builder.toString();
        }
    }

    @SuppressWarnings("unchecked")
    public static class JavaCompatibleTree<C extends CharSequence> extends java.util.AbstractCollection<C> {

        private TernarySearchTree<C> tree = null;

        public JavaCompatibleTree(TernarySearchTree<C> tree) {
            this.tree = tree;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(C value) {
            return tree.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return (tree.remove((C)value)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return tree.contains((C)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return tree.size;
        }

        /**
         * {@inheritDoc}
         * 
         * WARNING: This iterator makes a copy of the tree's contents during it's construction!
         */
        @Override
        public java.util.Iterator<C> iterator() {
            return (new TreeIterator<C>(tree));
        }

        private static class TreeIterator<C extends CharSequence> implements java.util.Iterator<C> {

            private TernarySearchTree<C> tree = null;
            private TernarySearchTree.Node lastNode = null;
            private java.util.Iterator<java.util.Map.Entry<Node, String>> iterator = null;

            protected TreeIterator(TernarySearchTree<C> tree) {
                this.tree = tree;
                java.util.Map<TernarySearchTree.Node,String> map = new java.util.LinkedHashMap<TernarySearchTree.Node,String>();
                if (this.tree.root!=null) {
                    getNodesWhichRepresentsWords(this.tree.root,"",map);
                }
                iterator = map.entrySet().iterator();
            }

            private void getNodesWhichRepresentsWords(TernarySearchTree.Node node, String string, java.util.Map<TernarySearchTree.Node,String> nodesMap) {
                final StringBuilder builder = new StringBuilder(string);
                if (node.isWord) 
                    nodesMap.put(node,builder.toString());
                if (node.loKid != null) {
                    Node child = node.loKid;
                    getNodesWhichRepresentsWords(child, builder.toString(), nodesMap);
                }
                if (node.kid != null) {
                    Node child = node.kid;
                    getNodesWhichRepresentsWords(child, builder.append(node.character).toString(), nodesMap);
                }
                if (node.hiKid != null) {
                    Node child = node.hiKid;
                    getNodesWhichRepresentsWords(child, builder.toString(), nodesMap);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                if (iterator!=null && iterator.hasNext()) 
                    return true;
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public C next() {
                if (iterator==null) 
                    return null;

                java.util.Map.Entry<TernarySearchTree.Node,String> entry = iterator.next();
                lastNode = entry.getKey();
                return (C)entry.getValue();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (iterator==null || tree==null) 
                    return;

                iterator.remove();
                this.tree.remove(lastNode);
            }
        }
    }
}
