package com.jwetherell.algorithms.strings.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.jwetherell.algorithms.strings.StringFunctions;

public class Strings {

    @Test
    public void testReverseCharsInString() {
        // REVERSE CHARS IN A STRING
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String check = "zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA";
        String result = StringFunctions.reverseWithStringConcat(string);
        assertTrue("Reverse With String Concat error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithStringBuilder(string);
        assertTrue("Reverse With String String Builder error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithStringBuilderBuiltinMethod(string);
        assertTrue("Reverse With Built-in error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithSwaps(string);
        assertTrue("Reverse With Swaps error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithXOR(string);
        assertTrue("Reverse With XOR error. expect="+check+" got="+result, check.equals(result));
    }

    @Test
    public void testReverseWordsInString() {
        // REVERSE WORDS IN A STRING
        String string = "Could you pretty please reverse this sentence";
        String check = "sentence this reverse please pretty you Could";
        String result = StringFunctions.reverseWordsByCharWithAdditionalStorage(string);
        assertTrue("Reverse Words By Char w/ Additional Storage error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage(string);
        assertTrue("Reverse Words Using String Tokenizer w/ Additional Storage error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWordsUsingSplitWithAdditionalStorage(string);
        assertTrue("Reverse Words Using Split w/ Additional Storage error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWordsInPlace(string);
        assertTrue("Reverse Words In-Place error. expect="+check+" got="+result, check.equals(result));
    }

    @Test
    public void testIsPalinDrone() {
        // PALINDROME
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        boolean result = StringFunctions.isPalindromeWithAdditionalStorage(string);
        assertFalse("Is Palindrome With Additional Storage error. expected=false got="+result, result);

        result = StringFunctions.isPalindromeInPlace(string);
        assertFalse("Is Palindrome In Place error. expected=false got="+result, result);

        string = "ABCDEFGHIJKKJIHGFEDCBA";
        result = StringFunctions.isPalindromeWithAdditionalStorage(string);
        assertTrue("Is Palindrome With Additional Storage error. expected=true got="+result, result);

        result = StringFunctions.isPalindromeInPlace(string);
        assertTrue("Is Palindrome In Place error. expected=true got="+result, result);
    }

    @Test
    public void testGenerateSubSets() {
        // COMBINATIONS
        String string = "abc";
        String[] check = new String[] {"", "c", "b", "cb", "a", "ca", "ba", "cba"};
        String[] result = StringFunctions.generateSubsets(string);
        assertTrue("Generate Subsets error. expected="+print(check)+" got="+print(result), Arrays.equals(check, result));
    }

    @Test
    public void testEditDistanceRecursive() {
        // Edit Distance
        String string1 = "kitten";
        String string2 = "sitting";
        int check = 3;
        int result = StringFunctions.levenshteinDistanceRecursive(string1, string2);
        assertTrue("Edit Distance error. expected="+check+" got="+result, (check==result));
    }

    @Test
    public void testEditDistanceDP() {
        // Edit Distance
        String string1 = "kitten";
        String string2 = "sitting";
        int check = 3;
        int result = StringFunctions.levenshteinDistanceIterative(string1, string2);
        assertTrue("Edit Distance error. expected="+check+" got="+result, (check==result));
    }

    private static final String print(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings)
            builder.append(s).append(' ');
        return builder.toString();
    }
}
