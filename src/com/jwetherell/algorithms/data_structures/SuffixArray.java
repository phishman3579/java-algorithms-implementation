package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * In computer science, a suffix array is a sorted array of all suffixes of a string.
 * It is a data structure used, among others, in full text indices, data compression
 * algorithms and within the field of bibliometrics.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Suffix_array">Suffix Array (Wikipedia)</a>
 * <p>
 * NOTE: This implementation returns starting indexes instead of full suffixes
 * <br>
 * @author Jakub Szarawarski <kubaszarawarski@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SuffixArray {

    private static final StringBuilder STRING_BUILDER = new StringBuilder();
    private static final char DEFAULT_END_SEQ_CHAR = '$';

    private final char endSeqChar;

    private String string;
    private ArrayList<Integer> suffixArray;
    private ArrayList<Integer> KMRarray;

    public SuffixArray(CharSequence sequence) {
        this(sequence, DEFAULT_END_SEQ_CHAR);
    }

    public SuffixArray(CharSequence sequence, char endChar) {
        endSeqChar = endChar;
        string = buildStringWithEndChar(sequence);
    }

    public ArrayList<Integer> getSuffixArray() {
        if (suffixArray == null)
            KMRalgorithm();
        return suffixArray;
    }

    /**
     * @return inverted suffix array
     */
    public ArrayList<Integer> getKMRarray() {
        if (KMRarray == null)
            KMRalgorithm();
        return KMRarray;
    }

    public String getString(){
        return string;
    }

    /**
     * Creates suffix array using KMR algorithm with O(n log^2 n) complexity.
     *
     * For radius r:
     *      KMR[i] == k,
     *      when string[i..i+r-1] is kth r-letter substring of string sorted lexicographically
     * KMR is counted for radius = 1,2,4,8 ...
     * KMR for radius bigger than string length is the inverted suffix array
     */
    private void KMRalgorithm() {
        final int length = string.length();

        ArrayList<KMRsWithIndex> KMRinvertedList = new ArrayList<KMRsWithIndex>();
        ArrayList<Integer> KMR = getBasicKMR(length);

        int radius = 1;
        while (radius < length) {
            KMRinvertedList = getKMRinvertedList(KMR, radius, length);
            KMR = getKMR(KMRinvertedList, length);
            radius *= 2;
        }

        KMRarray = new ArrayList<Integer>(KMR.subList(0, length));
        suffixArray = new ArrayList<Integer>();
        for (KMRsWithIndex kmr : KMRinvertedList) {
            suffixArray.add(new Integer(kmr.index));
        }
    }

    /**
     * Creates KMR array for new radius from nearly inverted array.
     * Elements from inverted array need to be grouped by substring tey represent.
     *
     * @param KMRinvertedList       indexes are nearly inverted KMR array
     * @param length                string length
     * @return KMR array for new radius
     */
    private ArrayList<Integer> getKMR(ArrayList<KMRsWithIndex> KMRinvertedList, int length) {
        final ArrayList<Integer> KMR = new ArrayList<Integer>(length*2);
        for (int i=0; i<2*length; i++) 
            KMR.add(new Integer(-1));

        int counter = 0;
        for (int i=0; i<length; i++){
            if(i>0 && substringsAreEqual(KMRinvertedList, i))
                counter++;
            KMR.set(KMRinvertedList.get(i).index, new Integer(counter));
        }

        return KMR;
    }

    private boolean substringsAreEqual(ArrayList<KMRsWithIndex> KMRinvertedList, int i) {
        return (KMRinvertedList.get(i-1).beginKMR.equals(KMRinvertedList.get(i).beginKMR) == false) || 
               (KMRinvertedList.get(i-1).endKMR.equals(KMRinvertedList.get(i).endKMR) == false);
    }

    /**
     * helper method to create KMR array for radius = radius from KMR array for radius = radius/2
     *
     * @param KMR       KMR array for radius = radius/2
     * @param radius    new radius
     * @param length    string length
     * @return list of KMRsWithIndex which indexes are nearly inverted KMR array
     */
    private ArrayList<KMRsWithIndex> getKMRinvertedList(ArrayList<Integer> KMR, int radius, int length) {
        final ArrayList<KMRsWithIndex> KMRinvertedList = new ArrayList<KMRsWithIndex>();
        for (int i=0; i<length; i++)
            KMRinvertedList.add(new KMRsWithIndex(KMR.get(i), KMR.get(i+radius), i));

        Collections.sort(KMRinvertedList,
            new Comparator<KMRsWithIndex>() {
                @Override
                public int compare(KMRsWithIndex A, KMRsWithIndex B) {
                    if (A.beginKMR.equals(B.beginKMR) == false)
                        return A.beginKMR.compareTo(B.beginKMR);
                    if (A.endKMR.equals(B.endKMR) == false)
                        return A.endKMR.compareTo(B.endKMR);
                    return A.index.compareTo(B.index);
                }
            }
        );
        return KMRinvertedList;
    }

    /**
     * KMR array for radius=1, instead of initial natural numbers ascii codes are used
     *
     * @param length        length of string
     * @return pseudo KMR array for radius=1
     */
    private ArrayList<Integer> getBasicKMR(int length) {
        final ArrayList<Integer> result = new ArrayList<Integer>(length*2);
        final char[] characters = string.toCharArray();
        for (int i=0; i<length; i++)
            result.add(new Integer(characters[i]));
        for (int i=0; i<length; i++)
            result.add(new Integer(-1));
        return result;
    }

    private String buildStringWithEndChar(CharSequence sequence) {
        STRING_BUILDER.setLength(0);
        STRING_BUILDER.append(sequence);
        if (STRING_BUILDER.indexOf(String.valueOf(endSeqChar)) < 0)
            STRING_BUILDER.append(endSeqChar);
        return STRING_BUILDER.toString();
    }

    private class KMRsWithIndex{

        Integer beginKMR;
        Integer endKMR;
        Integer index;

        KMRsWithIndex(Integer begin, Integer end, Integer index){
            this.beginKMR = begin;
            this.endKMR = end;
            this.index = index;
        }
    }
}
