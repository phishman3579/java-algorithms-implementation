package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

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
    	SuffixTrie<String> suffixTrieTest = new SuffixTrie<String>("book");
    	String part = "pen";
        assertTrue(suffixTrieTest.add(part));
        assertNotNull(suffixTrieTest.getSuffixes());
    }

    @Test
    public void testString() {
    	SuffixTrie<String> suffixTrieTest2 = new SuffixTrie<String>("paper");
        assertNotNull(suffixTrieTest2.toString());
    }
}
