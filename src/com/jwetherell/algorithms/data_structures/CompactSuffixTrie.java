package com.jwetherell.algorithms.data_structures;

import java.util.Set;
import java.util.TreeSet;

/**
 * A suffix trie is a data structure that presents the suffixes of a given
 * string in a way that allows for a particularly fast implementation of many
 * important string operations. This implementation is based upon a patricia
 * trie which IS a compact trie.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Suffix_trie">Suffix Trie (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class CompactSuffixTrie<C extends CharSequence> {

    private PatriciaTrie<C> tree = null;

    /**
     * Create a compact suffix trie from sequence
     * 
     * @param sequence
     *            to create a suffix trie from.
     */
    public CompactSuffixTrie(C sequence) {
        tree = new PatriciaTrie<C>();
        int length = sequence.length();
        for (int i = 0; i < length; i++) {
            CharSequence seq = sequence.subSequence(i, length);
            tree.add((C) seq);
        }
    }

    /**
     * Add character sequence to the trie.
     * 
     * @param sequence
     *            to add to trie.
     * @return True if added successfully.
     */
    public boolean add(C sequence) {
        int length = sequence.length();
        for (int i = 0; i < length; i++) {
            CharSequence seq = sequence.subSequence(i, length);
            tree.add((C) seq);
        }
        return true;
    }

    /**
     * Does the sequence exists in the trie.
     * 
     * @param sequence
     *            to locate in the trie.
     * @return True if sequence exists in trie.
     */
    public boolean doesSubStringExist(C sequence) {
        char[] chars = sequence.toString().toCharArray();
        int length = chars.length;
        PatriciaTrie.Node current = tree.root;
        int index = 0;
        for (int i = 0; i < length; i++) {
            int innerStringLength = (current.string != null) ? current.string.length : 0;
            char c = chars[i];
            if (innerStringLength > index) {
                boolean inThis = current.partOfThis(c, index++);
                if (!inThis)
                    return false;
            } else {
                int idx = current.childIndex(c);
                if (idx < 0)
                    return false;
                current = current.getChild(idx);
                index = 1;
            }
        }
        return true;
    }

    /**
     * Get all suffixes in the trie.
     * 
     * @return set of suffixes in trie.
     */
    public Set<String> getSuffixes() {
        return this.getSuffixes(tree.root);
    }

    /**
     * Get all suffixes at node.
     * 
     * @param node
     *            to get all suffixes at.
     * @return set of suffixes in trie at node.
     */
    private Set<String> getSuffixes(PatriciaTrie.Node node) {
        StringBuilder builder = new StringBuilder();
        if (node.string != null)
            builder.append(node.string);
        Set<String> set = new TreeSet<String>();
        if (node.type == PatriciaTrie.WHITE) {
            set.add(builder.toString());
        }
        for (int i = 0; i < node.getChildrenSize(); i++) {
            PatriciaTrie.Node c = node.getChild(i);
            set.addAll(getSuffixes(c, builder.toString()));
        }
        return set;
    }

    /**
     * Get all suffixes at node and prepend the prefix.
     * 
     * @param node
     *            to get all suffixes from.
     * @param prefix
     *            to prepend to suffixes.
     * @return set of suffixes in trie at node.
     */
    private Set<String> getSuffixes(PatriciaTrie.Node node, String prefix) {
        StringBuilder builder = new StringBuilder(prefix);
        if (node.string != null)
            builder.append(node.string);
        Set<String> set = new TreeSet<String>();
        if (node.type == PatriciaTrie.WHITE) {
            set.add(builder.toString());
        }
        for (int i = 0; i < node.getChildrenSize(); i++) {
            PatriciaTrie.Node c = node.getChild(i);
            set.addAll(getSuffixes(c, builder.toString()));
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return PatriciaTrie.PatriciaTriePrinter.getString(tree);
    }

    public boolean equals(CompactSuffixTrie<C> trie){
        if(this.getSuffixes().equals(trie.getSuffixes())) return true;
        return false;
    }
}
