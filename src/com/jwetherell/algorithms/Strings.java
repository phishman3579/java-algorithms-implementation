package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.strings.StringFunctions;

public class Strings {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");
    
    public static void main(String[] args) {
        //REVERSE CHARS IN A STRING
        {
            String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            System.out.println("Reversing a string using concatination.");
            long before = System.nanoTime();
            String result = StringFunctions.reverseWithStringConcat(string);
            long after = System.nanoTime();
            System.out.println("before="+string+" after="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Reversing a string with a StringBuilder.");
            before = System.nanoTime();
            result = StringFunctions.reverseWithStringBuilder(string);
            after = System.nanoTime();
            System.out.println("before="+string+" after="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Reversing a string with StringBuilder built-in reverse method.");
            before = System.nanoTime();
            result = StringFunctions.reverseWithStringBuilderBuiltinMethod(string);
            after = System.nanoTime();
            System.out.println("before="+string+" after="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Reversing a string with swaps.");
            before = System.nanoTime();
            result = StringFunctions.reverseWithSwaps(string);
            after = System.nanoTime();
            System.out.println("before="+string+" after="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.out.println();
            System.gc();
        }
        
        //REVERSE WORDS IN A STRING
        {
            String string = "Could you pretty please reverse this sentence";
            System.out.println("Reversing a string using additional array.");
            long before = System.nanoTime();
            String result = StringFunctions.reverseWordsWithAdditionalStorage(string);
            long after = System.nanoTime();
            System.out.println("before="+string+" after="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Reversing a string in-place.");
            before = System.nanoTime();
            result = StringFunctions.reverseWordsInPlace(string);
            after = System.nanoTime();
            System.out.println("before="+string+" after="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.out.println();
            System.gc();
        }
        
        //PALINDROME
        {
            String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            System.out.println("Is Palindrome?");
            long before = System.nanoTime();
            boolean result = StringFunctions.isPalindrome(string);
            long after = System.nanoTime();
            System.out.println("string="+string+" isPalindrome="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            string = "ABCDEFGHIJKLMNOPQRSTUVWXYZZYXWVUTSRQPONMLKJIHGFEDCBA";
            System.out.println("Is Palindrome?");
            before = System.nanoTime();
            result = StringFunctions.isPalindrome(string);
            after = System.nanoTime();
            System.out.println("string="+string+" isPalindrome="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.out.println();
            System.gc();
        }
    }
}
