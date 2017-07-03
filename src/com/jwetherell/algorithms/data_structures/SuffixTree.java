package com.jwetherell.algorithms.data_structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.jwetherell.algorithms.data_structures.interfaces.ISuffixTree;

/**
 * A suffix tree is a data structure that presents the suffixes of a given
 * string in a way that allows for a particularly fast implementation of many
 * important string operations. This implementation is based on the Ukkonen's
 * algorithm.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Suffix_tree">Suffix Tree (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SuffixTree<C extends CharSequence> implements ISuffixTree<C> {

    private static final char DEFAULT_END_SEQ_CHAR = '$';

    private final char endSeqChar;

    private Map<Integer, Link> linksMap = new HashMap<Integer, Link>();
    private Map<Integer, Edge<C>> edgeMap = new TreeMap<Integer, Edge<C>>();

    private int currentNode = 0;
    private int firstCharIndex = 0;
    private int lastCharIndex = -1;

    private String string;
    private char[] characters;

    /**
     * Create suffix tree with sequence and default end sequence.
     * 
     * @param seq
     *            to create a suffix tree with.
     */
    public SuffixTree(C seq) {
        this(seq, DEFAULT_END_SEQ_CHAR);
    }

    /**
     * Create suffix tree with sequence and end sequence parameter.
     * 
     * @param seq
     *            to create a suffix tree with.
     * @param endSeq
     *            which defines the end of a sequence.
     */
    public SuffixTree(C seq, char endSeq) {
        endSeqChar = endSeq;
        StringBuilder builder = new StringBuilder(seq);
        if (builder.indexOf(String.valueOf(endSeqChar)) < 0)
            builder.append(endSeqChar);
        string = builder.toString();
        int length = string.length();
        characters = new char[length];
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            characters[i] = c;
        }

        for (int j = 0; j < length; j++) {
            addPrefix(j);
        }
    }

    /**
     * Does the sub-sequence exist in the suffix tree.
     * 
     * @param sub
     *            sub-sequence to locate in the tree.
     * @return True if the sub-sequence exist in the tree.
     */
    @Override
    public boolean doesSubStringExist(C sub) {
        char[] chars = new char[sub.length()];
        for (int i = 0; i < sub.length(); i++) {
            chars[i] = sub.charAt(i);
        }
        int[] indices = searchEdges(chars);
        int start = indices[0];
        int end = indices[1];
        int length = end - start;
        if (length == (chars.length - 1))
            return true;
        return false;
    }

    /**
     * Get all the suffixes in the tree.
     * 
     * @return set of suffixes in the tree.
     */
    @Override
    public Set<String> getSuffixes() {
        Set<String> set = getSuffixes(0);
        return set;
    }

    /**
     * Get all suffixes at starting node.
     * 
     * @param start
     *            node.
     * @return set of suffixes in the tree at start node.
     */
    private Set<String> getSuffixes(int start) {
        Set<String> set = new TreeSet<String>();
        for (int key : edgeMap.keySet()) {
            Edge<C> e = edgeMap.get(key);
            if (e == null)
                continue;
            if (e.startNode != start)
                continue;

            String s = (string.substring(e.firstCharIndex, e.lastCharIndex + 1));
            Link n = linksMap.get(e.endNode);
            if (n == null) {
                int index = s.indexOf(endSeqChar);
                if (index >= 0)
                    s = s.substring(0, index);
                set.add(s);
            } else {
                Set<String> set2 = getSuffixes(e.endNode);
                for (String s2 : set2) {
                    int index = s2.indexOf(endSeqChar);
                    if (index >= 0)
                        s2 = s2.substring(0, index);
                    set.add(s + s2);
                }
            }
        }
        return set;
    }

    /**
     * Get all edges in the table
     * 
     * @return debug string.
     */
    public String getEdgesTable() {
        StringBuilder builder = new StringBuilder();
        if (edgeMap.size() > 0) {
            int charsLength = characters.length;
            builder.append("Edge\tStart\tEnd\tSuf\tfirst\tlast\tString\n");
            for (int key : edgeMap.keySet()) {
                Edge<C> e = edgeMap.get(key);
                Link link = linksMap.get(e.endNode);
                int suffix = (link != null) ? link.suffixNode : -1;
                builder.append("\t" + e.startNode + "\t" + e.endNode + "\t" + suffix + "\t" + e.firstCharIndex + "\t"
                        + e.lastCharIndex + "\t");
                int begin = e.firstCharIndex;
                int end = (charsLength < e.lastCharIndex) ? charsLength : e.lastCharIndex;
                builder.append(string.substring(begin, end + 1));
                builder.append("\n");
            }
            builder.append("Link\tStart\tEnd\n");
            for (int key : linksMap.keySet()) {
                Link link = linksMap.get(key);
                builder.append("\t" + link.node + "\t" + link.suffixNode + "\n");
            }
        }
        return builder.toString();
    }

    /**
     * Add prefix at index.
     * 
     * @param index
     *            to add prefix at.
     */
    private void addPrefix(int index) {
        int parentNodeIndex = 0;
        int lastParentIndex = -1;

        while (true) {
            Edge<C> edge = null;
            parentNodeIndex = currentNode;
            if (isExplicit()) {
                edge = Edge.find(this, currentNode, characters[index]);
                if (edge != null) {
                    // Edge already exists
                    break;
                }
            } else {
                // Implicit node, a little more complicated
                edge = Edge.find(this, currentNode, characters[firstCharIndex]);
                int span = lastCharIndex - firstCharIndex;
                if (characters[edge.firstCharIndex + span + 1] == characters[index]) {
                    // If the edge is the last char, don't split
                    break;
                }
                parentNodeIndex = edge.split(currentNode, firstCharIndex, lastCharIndex);
            }
            edge = new Edge<C>(this, index, characters.length - 1, parentNodeIndex);
            if (lastParentIndex > 0) {
                // Last parent is not root, create a link.
                linksMap.get(lastParentIndex).suffixNode = parentNodeIndex;
            }
            lastParentIndex = parentNodeIndex;
            if (currentNode == 0) {
                firstCharIndex++;
            } else {
                // Current node is not root, follow link
                currentNode = linksMap.get(currentNode).suffixNode;
            }
            if (!isExplicit())
                canonize();
        }
        if (lastParentIndex > 0) {
            // Last parent is not root, create a link.
            linksMap.get(lastParentIndex).suffixNode = parentNodeIndex;
        }
        lastParentIndex = parentNodeIndex;
        lastCharIndex++; // Now the endpoint is the next active point
        if (!isExplicit())
            canonize();
    }

    /**
     * Is the tree explicit
     * 
     * @return True if explicit.
     */
    private boolean isExplicit() {
        return firstCharIndex > lastCharIndex;
    }

    /**
     * Canonize the tree.
     */
    private void canonize() {
        Edge<C> edge = Edge.find(this, currentNode, characters[firstCharIndex]);
        int edgeSpan = edge.lastCharIndex - edge.firstCharIndex;
        while (edgeSpan <= (lastCharIndex - firstCharIndex)) {
            firstCharIndex = firstCharIndex + edgeSpan + 1;
            currentNode = edge.endNode;
            if (firstCharIndex <= lastCharIndex) {
                edge = Edge.find(this, edge.endNode, characters[firstCharIndex]);
                edgeSpan = edge.lastCharIndex - edge.firstCharIndex;
            }
        }
    }

    /**
     * Returns a two element int array who's 0th index is the start index and
     * 1th is the end index.
     */
    private int[] searchEdges(char[] query) {
        int startNode = 0;
        int queryPosition = 0;
        int startIndex = -1;
        int endIndex = -1;
        boolean stop = false;

        while (!stop && queryPosition < query.length) {
            Edge<C> edge = Edge.find(this, startNode, query[queryPosition]);
            if (edge == null) {
                stop = true;
                break;
            }
            if (startNode == 0)
                startIndex = edge.firstCharIndex;
            for (int i = edge.firstCharIndex; i <= edge.lastCharIndex; i++) {
                if (queryPosition >= query.length) {
                    stop = true;
                    break;
                } else if (query[queryPosition] == characters[i]) {
                    queryPosition++;
                    endIndex = i;
                } else {
                    stop = true;
                    break;
                }
            }
            if (!stop) { // proceed with next node
                startNode = edge.endNode;
                if (startNode == -1)
                    stop = true;
            }
        }
        return (new int[] { startIndex, endIndex });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("String = ").append(this.string).append("\n");
        builder.append("End of word character = ").append(endSeqChar).append("\n");
        builder.append(TreePrinter.getString(this));
        return builder.toString();
    }

    private static class Link implements Comparable<Link> {

        private int node = 0;
        private int suffixNode = -1;

        public Link(int node) {
            this.node = node;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("node=").append(node).append("\n");
            builder.append("suffixNode=").append(suffixNode).append("\n");
            return builder.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Link link) {
            if (link == null)
                return -1;

            if (node < link.node)
                return -1;
            if (node > link.node)
                return 1;

            if (suffixNode < link.suffixNode)
                return -1;
            if (suffixNode > link.suffixNode)
                return 1;

            return 0;
        }
    }

    private static class Edge<C extends CharSequence> implements Comparable<Edge<C>> {

        private static final int KEY_MOD = 2179; // Should be a prime that is
                                                 // roughly 10% larger than the
                                                 // String
        private static int count = 1;

        private SuffixTree<C> tree = null;

        private int startNode = -1;
        private int endNode = 0;
        private int firstCharIndex = 0;
        private int lastCharIndex = 0;

        private Edge(SuffixTree<C> tree, int first, int last, int parent) {
            this.tree = tree;
            firstCharIndex = first;
            lastCharIndex = last;
            startNode = parent;
            endNode = count++;
            insert(this);
        }

        private int getKey() {
            return key(startNode, tree.characters[firstCharIndex]);
        }

        private static int key(int node, char c) {
            return ((node << 8) + c) % KEY_MOD;
        }

        private void insert(Edge<C> edge) {
            tree.edgeMap.put(edge.getKey(), edge);
        }

        private void remove(Edge<C> edge) {
            int i = edge.getKey();
            Edge<C> e = tree.edgeMap.remove(i);
            while (true) {
                e.startNode = -1;
                int j = i;
                while (true) {
                    i = ++i % KEY_MOD;
                    e = tree.edgeMap.get(i);
                    if (e == null)
                        return;
                    int r = key(e.startNode, tree.characters[e.firstCharIndex]);
                    if (i >= r && r > j)
                        continue;
                    if (r > j && j > i)
                        continue;
                    if (j > i && i >= r)
                        continue;
                    break;
                }
                tree.edgeMap.put(j, e);
            }
        }

        private static <C extends CharSequence> Edge<C> find(SuffixTree<C> tree, int node, char c) {
            int key = key(node, c);
            return tree.edgeMap.get(key);
        }

        private int split(int originNode, int firstIndex, int lastIndex) {
            remove(this);
            Edge<C> newEdge = new Edge<C>(tree, this.firstCharIndex, this.firstCharIndex + lastIndex - firstIndex, originNode);
            Link link = tree.linksMap.get(newEdge.endNode);
            if (link == null) {
                link = new Link(newEdge.endNode);
                tree.linksMap.put(newEdge.endNode, link);
            }
            tree.linksMap.get(newEdge.endNode).suffixNode = originNode;
            this.firstCharIndex += lastIndex - firstIndex + 1;
            this.startNode = newEdge.endNode;
            insert(this);
            return newEdge.endNode;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return getKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (obj instanceof Edge)
                return false;

            @SuppressWarnings("unchecked")
            Edge<C> e = (Edge<C>) obj;
            if (startNode == e.startNode && tree.characters[firstCharIndex] == tree.characters[e.firstCharIndex]) {
                return true;
            }

            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Edge<C> edge) {
            if (edge == null)
                return -1;

            if (startNode < edge.startNode)
                return -1;
            if (startNode > edge.startNode)
                return 1;

            if (endNode < edge.endNode)
                return -1;
            if (endNode > edge.endNode)
                return 1;

            if (firstCharIndex < edge.firstCharIndex)
                return -1;
            if (firstCharIndex > edge.firstCharIndex)
                return 1;

            if (lastCharIndex < edge.lastCharIndex)
                return -1;
            if (lastCharIndex > edge.lastCharIndex)
                return 1;

            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("startNode=").append(startNode).append("\n");
            builder.append("endNode=").append(endNode).append("\n");
            builder.append("firstCharIndex=").append(firstCharIndex).append("\n");
            builder.append("lastCharIndex=").append(lastCharIndex).append("\n");
            String s = tree.string.substring(firstCharIndex, lastCharIndex + 1);
            builder.append("string=").append(s).append("\n");
            return builder.toString();
        }
    }

    protected static class TreePrinter {

        public static <C extends CharSequence> void printNode(SuffixTree<C> tree) {
            System.out.println(getString(tree, null, "", true));
        }

        public static <C extends CharSequence> String getString(SuffixTree<C> tree) {
            return getString(tree, null, "", true);
        }

        private static <C extends CharSequence> String getString(SuffixTree<C> tree, Edge<C> e, String prefix,
                boolean isTail) {
            StringBuilder builder = new StringBuilder();
            int value = 0;
            if (e != null) {
                value = e.endNode;
                String string = tree.string.substring(e.firstCharIndex, e.lastCharIndex + 1);
                int index = string.indexOf(tree.endSeqChar);
                if (index >= 0)
                    string = string.substring(0, index + 1);
                builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + value + ") " + string + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + 0 + ")" + "\n");
            }

            if (tree.edgeMap.size() > 0) {
                List<Edge<C>> children = new LinkedList<Edge<C>>();
                for (Edge<C> edge : tree.edgeMap.values()) {
                    if (edge != null && (edge.startNode == value)) {
                        children.add(edge);
                    }
                }
                if (children.size() > 0) {
                    for (int i = 0; i < children.size() - 1; i++) {
                        Edge<C> edge = children.get(i);
                        builder.append(getString(tree, edge, prefix + (isTail ? "    " : "│   "), false));
                    }
                    if (children.size() >= 1) {
                        Edge<C> edge = children.get(children.size() - 1);
                        builder.append(getString(tree, edge, prefix + (isTail ? "    " : "│   "), true));
                    }
                }
            }
            return builder.toString();
        }
    }
}
