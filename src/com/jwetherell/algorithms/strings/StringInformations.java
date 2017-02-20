package com.jwetherell.algorithms.strings;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for getting informations about text.
 *
 * @author Szymon Stankiewicz <mail@stankiewicz.me>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class StringInformations {

    /**
     * This function implements KMP algorithm for finding length of maximal prefix-suffix for each prefix of the string.
     * Prefix-suffix of string S is a substring which occurs at the beginning and at the end of S.
     *
     * Time complexity: O(n)
     *
     * https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
     *
     * @param text Text
     * @return maximal length of prefix-suffix for each prefix of the string text
     */
    public static List<Integer> getPrefSufTable(String text) {
        final List<Integer> prefSufTable = new ArrayList<Integer>();

        if(text.length() == 0) 
            return prefSufTable;

        prefSufTable.add(0);

        for(int i = 1; i<text.length(); i++) {
            int sizeOfPrefSuf = prefSufTable.get(i-1);
            while(sizeOfPrefSuf > 0 && text.charAt(i) != text.charAt(sizeOfPrefSuf)) {
                sizeOfPrefSuf = prefSufTable.get(sizeOfPrefSuf-1); // because string is 0-indexed
            }

            // if characters at this positions are different then sizeOfPrefSuf is equal to zero,
            // so there is no proper prefix-suffix
            if(text.charAt(i) == text.charAt(sizeOfPrefSuf)) {
                prefSufTable.add(sizeOfPrefSuf+1);
            } else {
                prefSufTable.add(0);
            }
        }
        return prefSufTable;
    }
}
