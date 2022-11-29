package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.SuffixTrie;
import com.jwetherell.algorithms.data_structures.test.common.SuffixTreeTest;

public class SuffixTrieTests {

    @Test
    public void testSuffixTrie() {
        String bookkeeper = "bookkeeper";
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
    }
    
    @Test
    public void testSuffixTrietoString() {
        String bookkeeper = "bookkeeper";
        
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        Set<String> trieSet = trie.getSuffixes();
        String trieString = trie.toString();
        
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
    }
    
    @Test
    public void testSuffixTrieAdd() {
        String bookkeeper = "bookkeeper";
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        trie.add(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
    }
}