package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.*;

//importing two additional required utilities
//Author: Abhishek Ahuja
import java.util.Set;
import java.util.TreeSet;

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
    
    //New Tests Added
    //Author: Abhishek Ahuja
    
    @Test
    public void testAddGet() {
    	SuffixTrie<String> testTrie = new SuffixTrie<String>("batman");
    	String partner = "robin";
        assertTrue(testTrie.add(partner));
        assertNotNull(testTrie.getSuffixes());
    }
    
    @Test
    public void testString() {
    	SuffixTrie<String> testTrie2 = new SuffixTrie<String>("superman");
        assertNotNull(testTrie2.toString());
    }
    
}
