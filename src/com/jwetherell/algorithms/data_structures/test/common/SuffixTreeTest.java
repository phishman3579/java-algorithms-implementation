package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.ISuffixTree;

public class SuffixTreeTest {

    /**
     * In computer science, a suffix tree (also called PAT tree or, in an earlier 
     * form, position tree) is a compressed trie containing all the suffixes of 
     * the given text as their keys and positions in the text as their values. 
     * Suffix trees allow particularly fast implementations of many important 
     * string operations.
     * 
     * @param tree Suffix tree to test.
     * @param test String to use in testing the suffix tree.
     * @return True if the suffix tree passes it's invariants tests.
     */
    public static boolean suffixTreeTest(ISuffixTree<String> tree, String test) {
        boolean exists = tree.doesSubStringExist(test);
        if (!exists) {
            System.err.println("YIKES!! " + test + " doesn't exists.");
            Utils.handleError(test,tree);
            return false;
        }

        String failed = test+"Z";
        exists = tree.doesSubStringExist(failed);
        if (exists) {
            System.err.println("YIKES!! " + failed + " exists.");
            Utils.handleError(failed,tree);
            return false;
        }

        String pass = test.substring(0, 6);
        exists = tree.doesSubStringExist(pass);
        if (!exists) {
            System.err.println("YIKES!! " + pass + " doesn't exists.");
            Utils.handleError(pass,tree);
            return false;
        }

        return true;
    }
}
