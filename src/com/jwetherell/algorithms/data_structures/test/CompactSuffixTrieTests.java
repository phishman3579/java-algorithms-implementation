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
}
