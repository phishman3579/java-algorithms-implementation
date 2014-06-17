package com.jwetherell.algorithms.strings;

import java.util.BitSet;
import java.util.StringTokenizer;

public class StringFunctions {

    private static final char SPACE = ' ';

    public static final String reverseWithStringConcat(String string) {
        String output = new String();
        for (int i = (string.length() - 1); i >= 0; i--) {
            output += (string.charAt(i));
        }
        return output;
    }

    public static final String reverseWithStringBuilder(String string) {
        StringBuilder builder = new StringBuilder();
        for (int i = (string.length() - 1); i >= 0; i--) {
            builder.append(string.charAt(i));
        }
        return builder.toString();
    }

    public static final String reverseWithStringBuilderBuiltinMethod(String string) {
        StringBuilder builder = new StringBuilder(string);
        return builder.reverse().toString();
    }

    public static final String reverseWithSwaps(String string) {
        char[] array = string.toCharArray();
        int length = array.length - 1;
        int half = (int) Math.floor(array.length / 2);
        char c;
        for (int i = length; i >= half; i--) {
            c = array[length - i];
            array[length - i] = array[i];
            array[i] = c;
        }
        return String.valueOf(array);
    }

    public static final String reverseWithXOR(String string) {
        char[] array = string.toCharArray();
        int length = array.length;
        int half = (int) Math.floor(array.length / 2);
        for (int i = 0; i < half; i++) {
            array[i] ^= array[length - i - 1];
            array[length - i - 1] ^= array[i];
            array[i] ^= array[length - i - 1];
        }
        return String.valueOf(array);
    }

    public static final String reverseWordsByCharWithAdditionalStorage(String string) {
        StringBuilder builder = new StringBuilder();

        char c = 0;
        int index = 0;
        int last = string.length();
        int length = string.length() - 1;
        StringBuilder temp = new StringBuilder();
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
        String output = new String();

        StringTokenizer st = new StringTokenizer(string);
        while (st.hasMoreTokens()) {
            output = (st.nextToken()) + ' ' + output;
        }

        return output.trim();
    }

    public static final String reverseWordsUsingSplitWithAdditionalStorage(String string) {
        StringBuilder builder = new StringBuilder();

        String[] temp = string.split(" ");
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
        String reversed = new StringBuilder(string).reverse().toString();
        return string.equals(reversed);
    }

    public static final boolean isPalindromeInPlace(String string) {
        char[] array = string.toCharArray();
        int length = array.length - 1;
        int half = Math.round(array.length / 2);
        char a, b;
        for (int i = length; i >= half; i--) {
            a = array[length - i];
            b = array[i];
            if (a != b)
                return false;
        }
        return true;
    }

    public static final String[] generateSubsets(String input) {
        int length = input.length();
        int size = (int) Math.pow(2, length);
        BitSet[] sets = new BitSet[size];
        String[] output = new String[size];

        for (int i = 0; i < size; i++) {
            BitSet set = new BitSet(size);
            StringBuilder builder = new StringBuilder();
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
                        builder.append(input.charAt(j));
                }
            }
            sets[i] = set;
            output[i] = builder.toString();
        }

        return output;
    }

    public static final int levenshteinDistance(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();

        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        int cost = 0;
        if ((sLength > 0 && tLength > 0) && sChars[0] != tChars[0])
            cost = 1;

        if (sLength == 0)
            return tLength;
        else if (tLength == 0)
            return sLength;
        else {
            int min1 = levenshteinDistance(s.substring(1), t) + 1;
            int min2 = levenshteinDistance(s, t.substring(1)) + 1;
            int min3 = levenshteinDistance(s.substring(1), t.substring(1)) + cost;

            int minOfFirstSet = Math.min(min1, min2);
            return (Math.min(minOfFirstSet, min3));
        }
    }
}
