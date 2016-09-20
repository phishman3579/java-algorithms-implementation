package com.jwetherell.algorithms.strings.timing;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.strings.StringFunctions;

public class StringsTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        // REVERSE CHARS IN A STRING
        {
            String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            System.out.println("Reversing a string using concatination.");
            long before = System.nanoTime();
            StringFunctions.reverseWithStringConcat(string);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string with a StringBuilder.");
            before = System.nanoTime();
            StringFunctions.reverseWithStringBuilder(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string with StringBuilder built-in reverse method.");
            before = System.nanoTime();
            StringFunctions.reverseWithStringBuilderBuiltinMethod(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string with swaps.");
            before = System.nanoTime();
            StringFunctions.reverseWithSwaps(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string with XOR.");
            before = System.nanoTime();
            StringFunctions.reverseWithXOR(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // REVERSE WORDS IN A STRING
        {
            String string = "Could you pretty please reverse this sentence";
            System.out.println("Reversing a string using additional array.");
            long before = System.nanoTime();
            StringFunctions.reverseWordsByCharWithAdditionalStorage(string);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string using StringTokenizer with additional storage.");
            before = System.nanoTime();
            StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string using split with additional storage.");
            before = System.nanoTime();
            StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Reversing a string in-place.");
            before = System.nanoTime();
            StringFunctions.reverseWordsInPlace(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // PALINDROME
        {
            String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            System.out.println("Is Palindrome with additional storage?");
            long before = System.nanoTime();
            StringFunctions.isPalindromeWithAdditionalStorage(string);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Is Palindrome in-place?");
            before = System.nanoTime();
            StringFunctions.isPalindromeInPlace(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            string = "ABCDEFGHIJKLMNOPQRSTUVWXYZZYXWVUTSRQPONMLKJIHGFEDCBA";
            System.out.println("Is Palindrome with additional storage?");
            before = System.nanoTime();
            StringFunctions.isPalindromeWithAdditionalStorage(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Is Palindrome in-place?");
            before = System.nanoTime();
            StringFunctions.isPalindromeInPlace(string);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // COMBINATIONS
        {
            String string = "abc";
            System.out.println("All possible subsets.");
            long before = System.nanoTime();
            StringFunctions.generateSubsets(string);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // Edit Distance
        {
            String string1 = "kitten";
            String string2 = "sitting";
            System.out.println("Edit Distance Recursive");
            long before = System.nanoTime();
            StringFunctions.levenshteinDistanceRecursive(string1, string2);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // Edit Distance
        {
            String string1 = "kitten";
            String string2 = "sitting";
            System.out.println("Edit Distance Iterative");
            long before = System.nanoTime();
            StringFunctions.levenshteinDistanceIterative(string1, string2);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }
}
