package com.jwetherell.algorithms.strings;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements KMP algorithm for finding length of maximal prefix-suffix for each prefix of the string.
 * Prefix-suffix of string S is a substring which occurs at the beginning and at the end of S.
 * <p>
 * Time complexity: O(n) <br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm">Knuth Morris Pratt (Wikipedia)</a>
 * <br>
 * @author Szymon Stankiewicz <mail@stankiewicz.me>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class KnuthMorrisPratt {

    private KnuthMorrisPratt() {}

    /**
     * This function implements KMP algorithm for finding length of maximal prefix-suffix for each prefix of the string.
     * Prefix-suffix of string S is a substring which occurs at the beginning and at the end of S.
     * <p>
     * @param text Text
     * @return maximal length of prefix-suffix for each prefix of the string text
     */
    public static List<Integer> getPrefSufTable(String text) {

        final List<Integer> prefSufTable = new ArrayList<Integer>();
        final char[] chars = text.toCharArray();

        if (text.length() == 0) 
            return prefSufTable;

        prefSufTable.add(0);

        for (int i = 1; i<chars.length; i++) {
            int sizeOfPrefSuf = prefSufTable.get(i-1);
            while (sizeOfPrefSuf > 0 && (chars[i] != chars[sizeOfPrefSuf]))
                sizeOfPrefSuf = prefSufTable.get(sizeOfPrefSuf-1); // because string is 0-indexed

            // if characters at this positions are different then sizeOfPrefSuf is equal to zero,
            // so there is no proper prefix-suffix
            if (chars[i] == chars[sizeOfPrefSuf]) {
                prefSufTable.add(sizeOfPrefSuf+1);
            } else {
                prefSufTable.add(0);
            }
        }
        return prefSufTable;
    }
}
