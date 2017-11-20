package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.CompactSuffixTrie;

public class CompactSuffixTrieTests {

    @Test
    public void testCompactSuffixTrie() {
        String bookkeeper = "bookkeeper";
        CompactSuffixTrie<String> trie = new CompactSuffixTrie<String>(bookkeeper);

        boolean exists = trie.doesSubStringExist(bookkeeper);
        assertTrue("YIKES!! " + bookkeeper + " doesn't exists.", exists);

        String failed = "booker";
        exists = trie.doesSubStringExist(failed);
        assertFalse("YIKES!! " + failed + " exists.", exists);

        String pass = "kkee";
        exists = trie.doesSubStringExist(pass);
        assertTrue("YIKES!! " + pass + " doesn't exists.", exists);
    }

    @Test
    public void testCompactSuffixTrie_equals() {
        String bookkeeper = "bookkeeper";
        CompactSuffixTrie<String> trie = new CompactSuffixTrie<String>(bookkeeper);

        String bookkeeper_1 = "bookkeeper";
        CompactSuffixTrie<String> trie_1 = new CompactSuffixTrie<String>(bookkeeper_1);

        boolean equal = trie.equals(trie_1);
        assertTrue("YIKES!! " + bookkeeper + " and " + bookkeeper_1 + " are not equal.", equal);


        String failed = "failed";
        trie = new CompactSuffixTrie<String>(failed);

        String failed_1 = "failet";
        trie_1 = new CompactSuffixTrie<String>(failed_1);

        equal = trie.equals(trie_1);
        assertFalse("YIKES!! " + failed + " and " + failed_1 + " are equal.", equal);
    }
}
