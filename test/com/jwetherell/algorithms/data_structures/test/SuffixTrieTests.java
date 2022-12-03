package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.SuffixTrie;
import com.jwetherell.algorithms.data_structures.test.common.SuffixTreeTest;

public class SuffixTrieTests {
SuffixTrie<String> tries = new SuffixTrie<String>("test");

    @Test
    public void testSuffixTrie() {
        String bookkeeper = "bookkeeper";
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
        
    }
    
    @Test
    public void testAdd() {
    	String course = "Advanced Software Testing";
    	assertTrue(tries.add(course));
    	
    }
    
    @Test
    public void testGetSuffix() {
    	Set<String> getTrie = tries.getSuffixes();
    	assertNotNull(getTrie);
    	
    }
    
    @Test 
    public void stringTest() {
    	assertNotNull(tries.toString());
    }
    
    @Test
    public void getSuffix() {
    	StringBuilder builder = new StringBuilder();
    	Set<String> set = new TreeSet<String>();
    	
    	assertTrue(set.add(builder.toString()));
    	
    }
    

    @Test
    public void testSuffixTrieAdd() {
        String software = "software";
        SuffixTrie<String> trie = new SuffixTrie<String>(software);
        trie.add(software);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, software));
    }

    @Test
    public void testSuffixTrieGetSuffixes() {
        String testing = "testing";
        SuffixTrie<String> trie = new SuffixTrie<String>(testing);
        Set<String> trieSet = trie.getSuffixes();
        String trieString = trie.toString();
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, testing));
    }
    
}
