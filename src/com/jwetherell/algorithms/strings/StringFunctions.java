package com.jwetherell.algorithms.strings;

import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * This class contains methods for modifying text.
 * <p>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class StringFunctions {

    private static final char SPACE = ' ';

    public static final String reverseWithStringConcat(String string) {
        if (string == null || string.isEmpty()){
            return "";
        }
            
            
        String output = new String();
        for (int i = (string.length() - 1); i >= 0; i--) {
            output += (string.charAt(i));
        }
        return output;
    }

    public static final String reverseWithStringBuilder(String string) {
        if (string == null || string.isEmpty()){
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = (string.length() - 1); i >= 0; i--) {
            builder.append(string.charAt(i));
        }
        return builder.toString();
    }

    public static final String reverseWithStringBuilderBuiltinMethod(String string) {
        final StringBuilder builder = new StringBuilder(string);
        return builder.reverse().toString();
    }

    public static final String reverseWithSwaps(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length - 1;
        final int half = (int) Math.floor(array.length / 2);

        char c;
        for (int i = length; i >= half; i--) {
            c = array[length - i];
            array[length - i] = array[i];
            array[i] = c;
        }
        return String.valueOf(array);
    }

    public static final String reverseWithXOR(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length;
        final int half = (int) Math.floor(array.length / 2);

        for (int i = 0; i < half; i++) {
            array[i] ^= array[length - i - 1];
            array[length - i - 1] ^= array[i];
            array[i] ^= array[length - i - 1];
        }
        return String.valueOf(array);
    }

    public static final String reverseWordsByCharWithAdditionalStorage(String string) {
        final StringBuilder builder = new StringBuilder();
        final int length = string.length() - 1;
        final StringBuilder temp = new StringBuilder();

        char c = 0;
        int index = 0;
        int last = string.length();
        for (int i = length; i >= 0; i--) {
            c = string.charAt(i);
            if (c == SPACE || i == 0) {
                index = (i == 0) ? 0 : i + 1;
                temp.append(string.substring(index, last));
                if (index != 0)
                    temp.append(c);
                builder.append(temp);
                temp.delete(0, temp.length());
                last = i;
            }
        }

        return builder.toString();
    }

    public static final String reverseWordsUsingStringTokenizerWithAdditionalStorage(String string) {
        final StringTokenizer st = new StringTokenizer(string);
        String output = new String();
        while (st.hasMoreTokens()) {
            output = (st.nextToken()) + ' ' + output;
        }

        return output.trim();
    }

    public static final String reverseWordsUsingSplitWithAdditionalStorage(String string) {
        final StringBuilder builder = new StringBuilder();
        final String[] temp = string.split(" ");

        for (int i = (temp.length - 1); i >= 0; i--) {
            builder.append(temp[i]).append(' ');
        }
        return builder.toString().trim();
    }

    public static final String reverseWordsInPlace(String string) {
        char[] chars = string.toCharArray();

        int lengthI = 0;
        int lastI = 0;
        int lengthJ = 0;
        int lastJ = chars.length - 1;

        int i = 0;
        char iChar = 0;
        char jChar = 0;
        while (i < chars.length && i <= lastJ) {
            iChar = chars[i];
            if (iChar == SPACE) {
                lengthI = i - lastI;
                for (int j = lastJ; j >= i; j--) {
                    jChar = chars[j];
                    if (jChar == SPACE) {
                        lengthJ = lastJ - j;
                        swapWords(lastI, i - 1, j + 1, lastJ, chars);
                        lastJ = lastJ - lengthI - 1;
                        break;
                    }
                }
                lastI = lastI + lengthJ + 1;
                i = lastI;
            } else {
                i++;
            }
        }

        return String.valueOf(chars);
    }

    private static final void swapWords(int startA, int endA, int startB, int endB, char[] array) {
        int lengthA = endA - startA + 1;
        int lengthB = endB - startB + 1;

        int length = lengthA;
        if (lengthA > lengthB)
            length = lengthB;

        int indexA = 0;
        int indexB = 0;
        char c = 0;
        for (int i = 0; i < length; i++) {
            indexA = startA + i;
            indexB = startB + i;

            c = array[indexB];
            array[indexB] = array[indexA];
            array[indexA] = c;
        }

        if (lengthB > lengthA) {
            length = lengthB - lengthA;
            int end = 0;
            for (int i = 0; i < length; i++) {
                end = endB - ((length - 1) - i);
                c = array[end];
                shiftRight(endA + i, end, array);
                array[endA + 1 + i] = c;
            }
        } else if (lengthA > lengthB) {
            length = lengthA - lengthB;
            for (int i = 0; i < length; i++) {
                c = array[endA];
                shiftLeft(endA, endB, array);
                array[endB + i] = c;
            }
        }
    }

    private static final void shiftRight(int start, int end, char[] array) {
        for (int i = end; i > start; i--) {
            array[i] = array[i - 1];
        }
    }

    private static final void shiftLeft(int start, int end, char[] array) {
        for (int i = start; i < end; i++) {
            array[i] = array[i + 1];
        }
    }

    public static final boolean isPalindromeWithAdditionalStorage(String string) {
        final String reversed = new StringBuilder(string).reverse().toString();
        return string.equals(reversed);
    }

    public static final boolean isPalindromeInPlace(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length - 1;
        final int half = Math.round(array.length / 2f);
        char a, b;
        for (int i = length; i >= half; i--) {
            a = array[length - i];
            b = array[i];
            if (a != b)
                return false;
        }
        return true;
    }

    public static final String[] generateSubsets(String inputString) {
        final int length = inputString.length();
        final int size = (int) Math.pow(2, length);
        final BitSet[] sets = new BitSet[size];
        final String[] output = new String[size];

        for (int i = 0; i < size; i++) {
            final BitSet set = new BitSet(size);
            final StringBuilder builder = new StringBuilder();
            if (i > 0) {
                for (int j = length - 1; j >= 0; j--) {
                    if (j == length - 1) {
                        if (i % 2 != 0)
                            set.set(j, true);
                    } else {
                        boolean prev = sets[i - 1].get(j);
                        boolean next = true;
                        for (int k = j + 1; k < length; k++) {
                            next = next && sets[i - 1].get(k);
                        }
                        if (next)
                            prev = !prev;
                        set.set(j, prev);
                    }
                    if (set.get(j))
                        builder.append(inputString.charAt(j));
                }
            }
            sets[i] = set;
            output[i] = builder.toString();
        }
        return output;
    }

    /** recursive **/
    public static final int levenshteinDistanceRecursive(String s, String t) {
        final int sLength = s.length();
        final int tLength = t.length();
        final char[] sChars = s.toCharArray();
        final char[] tChars = t.toCharArray();

        int cost = 0;
        if ((sLength > 0 && tLength > 0) && sChars[0] != tChars[0])
            cost = 1;

        if (sLength == 0)
            return tLength;
        else if (tLength == 0)
            return sLength;
        else {
            final int min1 = levenshteinDistanceRecursive(s.substring(1), t) + 1;
            final int min2 = levenshteinDistanceRecursive(s, t.substring(1)) + 1;
            final int min3 = levenshteinDistanceRecursive(s.substring(1), t.substring(1)) + cost;

            int minOfFirstSet = Math.min(min1, min2);
            return (Math.min(minOfFirstSet, min3));
        }
    }

    /** iterative - dynamic programming **/
    public static final int levenshteinDistanceIterative(String string1, String string2) {
        final char[] s = string1.toCharArray();
        final char[] t = string2.toCharArray();
        final int m = s.length;
        final int n = t.length;

        // for all i and j, d[i,j] will hold the Levenshtein distance between
        // the first i characters of s and the first j characters of t
        // note that d has (m+1)*(n+1) values
        final int[][] d = new int[m+1][n+1];

        // source prefixes can be transformed into empty string by
        // dropping all characters
        for (int i=1; i<=m; i++)
            d[i][0] = i;

        // target prefixes can be reached from empty source prefix
        // by inserting every character
        for (int j=1; j<=n; j++)
            d[0][j] = j;

        int substitutionCost;
        for (int j=1; j<=n; j++) {
            for (int i=1; i<=m; i++) {
                if (s[i-1] == t[j-1])
                    substitutionCost = 0;
                else
                    substitutionCost = 1;

                int minOfInsAndDel = Math.min(d[i-1][j] + 1,          // deletion
                                              d[i][j-1] + 1);         // insertion
                d[i][j] =  Math.min(minOfInsAndDel,                   // minimum of insert and delete
                                    d[i-1][j-1] + substitutionCost);  // substitution
            }
        }
        return d[m][n];
    }
}
