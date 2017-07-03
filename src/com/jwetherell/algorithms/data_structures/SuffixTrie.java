package com.jwetherell.algorithms.data_structures;

import java.util.Set;
import java.util.TreeSet;

import com.jwetherell.algorithms.data_structures.Trie.Node;
import com.jwetherell.algorithms.data_structures.interfaces.ISuffixTree;

/**
 * A suffix trie is a data structure that presents the suffixes of a given
 * string in a way that allows for a particularly fast implementation of many
 * important string operations.
 * <p>
 * This implementation is based upon a Trie which is NOT a compact trie.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Suffix_trie">Suffix Trie (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class SuffixTrie<C extends CharSequence> implements ISuffixTree<C> {

    private Trie<C> tree;

    /**
     * Create a suffix trie from sequence
     * 
     * @param sequence
     *            to create a suffix trie from.
     */
    public SuffixTrie(C sequence) {
        tree = new Trie<C>();
        int length = sequence.length();
        for (int i = 0; i < length; i++) {
            CharSequence seq = sequence.subSequence(i, length);
            tree.add((C) seq);
        }
    }

    /**
     * Add character sequence to the suffix trie.
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
    @Override
    public boolean doesSubStringExist(C sequence) {
        char[] chars = sequence.toString().toCharArray();
        int length = chars.length;
        Trie.Node current = tree.root;
        for (int i = 0; i < length; i++) {
            int idx = current.childIndex(chars[i]);
            if (idx < 0)
                return false;
            current = current.getChild(idx);
        }
        return true;
    }

    /**
     * Get all suffixes in the trie.
     * 
     * @return set of suffixes in trie.
     */
    @Override
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
    private Set<String> getSuffixes(Trie.Node node) {
        StringBuilder builder = new StringBuilder();
        if (node.character != Node.SENTINAL) builder.append(node.character);
        Set<String> set = new TreeSet<String>();
        if (node.isWord) {
            set.add(builder.toString());
        }
        for (int i = 0; i < node.getChildrenSize(); i++) {
            Trie.Node c = node.getChild(i);
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
    private Set<String> getSuffixes(Trie.Node node, String prefix) {
        StringBuilder builder = new StringBuilder(prefix);
        if (node.character != Node.SENTINAL) builder.append(node.character);
        Set<String> set = new TreeSet<String>();
        if (node.isWord) {
            set.add(builder.toString());
        }
        for (int i = 0; i < node.getChildrenSize(); i++) {
            Trie.Node c = node.getChild(i);
            set.addAll(getSuffixes(c, builder.toString()));
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Trie.TriePrinter.getString(tree);
    }
}
