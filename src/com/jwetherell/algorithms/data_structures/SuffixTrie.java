package com.jwetherell.algorithms.data_structures;

import java.util.Set;
import java.util.TreeSet;


/**
 * A suffix trie is a data structure that presents the suffixes of a given
 * string in a way that allows for a particularly fast implementation of many
 * important string operations.
 * 
 * http://en.wikipedia.org/wiki/Suffix_trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SuffixTrie<C extends CharSequence> extends Trie<C> {

    @SuppressWarnings("unchecked")
    public SuffixTrie(String string) {
        root = new Node<C>(null, null, false);
        int length = string.length();
        for (int i = 0; i < length; i++) {
            CharSequence seq = string.substring(i, length);
            super.add((C) seq);
        }
    }

    public boolean doesSubStringExist(String string) {
        char[] chars = string.toCharArray();
        int length = chars.length;
        Node<C> current = root;
        for (int i = 0; i < length; i++) {
            int idx = current.childIndex(chars[i]);
            if (idx < 0) return false;
            current = current.getChild(idx);
        }
        return true;
    }

    public Set<String> getSuffixes() {
        return this.getSuffixes(root);
    }

    private Set<String> getSuffixes(Node<C> p) {
        StringBuilder builder = new StringBuilder();
        if (p.character!=null) builder.append(p.character);
        Set<String> set = new TreeSet<String>();
        if (p.getChildrenSize() == 0) {
            set.add(builder.toString());
        } else {
            for (int i=0; i<p.getChildrenSize(); i++) {
                Node<C> c = p.getChild(i);
                set.addAll(getSuffixes(c,builder.toString()));
            }
        }
        return set;
    }

    private Set<String> getSuffixes(Node<C> p, String prefix) {
        StringBuilder builder = new StringBuilder(prefix);
        if (p.character!=null) builder.append(p.character);
        Set<String> set = new TreeSet<String>();
        if (p.getChildrenSize() == 0) {
            set.add(builder.toString());
        } else {
            for (int i=0; i<p.getChildrenSize(); i++) {
                Node<C> c = p.getChild(i);
                set.addAll(getSuffixes(c,builder.toString()));
            }
        }
        return set;
    }

    @Override
    public boolean add(C key) {
        // Ignore public calls to add. The class should be immutable.
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TriePrinter.getString(this);
    }
}
