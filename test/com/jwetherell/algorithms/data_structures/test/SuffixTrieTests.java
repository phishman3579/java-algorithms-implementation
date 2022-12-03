package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

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
    public void testAdd() {
        SuffixTrie<String> trie = new SuffixTrie<String>("bookkeeper");
        String str = "abc";

        assertTrue(trie.add(str));
    }

    @Test
    public void testGetSuffix() {
        SuffixTrie<String> trie = new SuffixTrie<String>("bookkeeper");
        Set<String> getSuffix = trie.getSuffixes();

        assertNotNull(getSuffix);
    }

    @Test
    public void testToString() {
        SuffixTrie<String> trie = new SuffixTrie<String>("bookkeeper");

        assertNotNull(trie.toString());
    }
}
