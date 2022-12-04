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
    public void testEmptyTree() {
        //Given
        String emptyStr = "";
        //When
        SuffixTrie<String> actualTrie = new SuffixTrie<String>(emptyStr);
        //Then
        assertEquals("Tree has no nodes.",actualTrie.toString());
    }


    @Test
    public void testSubSequence() {
        //When
        SuffixTrie<String> actualTrie = new SuffixTrie<String>("Potterhead");
        //Then
        assertTrue(actualTrie.doesSubStringExist("head"));
        assertFalse(actualTrie.doesSubStringExist("muggle"));
    }

    @Test
    public void testCountNodes() {
        //Given
        int leftLimit = 97;
        int rightLimit = 122;
        int length = 12;
        Random random = new Random();
        String str = random.ints(leftLimit, rightLimit+1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        //When
        SuffixTrie<String> actualTrie = new SuffixTrie<String>(str);
        //Then
        System.out.println(actualTrie);
        assertEquals(str.length(), actualTrie.getSuffixes().size());
    }
}
