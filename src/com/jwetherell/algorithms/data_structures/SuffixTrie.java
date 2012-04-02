package com.jwetherell.algorithms.data_structures;


/**
 * A suffix tree is a data structure that presents the suffixes of a given string in a way that allows 
 * for a particularly fast implementation of many important string operations. This is NOT a compact tree.
 * 
 * http://en.wikipedia.org/wiki/Suffix_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SuffixTrie<C extends CharSequence> extends Trie<C> {

    @SuppressWarnings("unchecked")
    public SuffixTrie(String string) {
        root = new Node<C>(null);
        int length = string.length();
        for (int i=0; i<length; i++) {
            CharSequence seq = string.substring(i, length);
            super.add((C)seq);
        }
    }
    
    @Override
    public boolean add(C key) {
        return false;
    }
}
