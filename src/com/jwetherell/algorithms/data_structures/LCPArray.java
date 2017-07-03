package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;

/**
 * In computer science, the longest common prefix array (LCP array) is an auxiliary
 * data structure to the suffix array. It stores the lengths of the longest common
 * prefixes (LCPs) between all pairs of consecutive suffixes in a sorted suffix array.
 * <p>
 * https://en.wikipedia.org/wiki/LCP_array
 * <br>
 * @author Jakub Szarawarski <kubaszarawarski@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LCPArray {

    private static final char DEFAULT_END_SEQ_CHAR = '$';

    private char END_SEQ_CHAR;
    private SuffixArray suffixArrayBuilder;
    private ArrayList<Integer> LCP;

    public LCPArray(CharSequence sequence){
        this(sequence, DEFAULT_END_SEQ_CHAR);
    }

    public LCPArray(CharSequence sequence, char endChar) {
        END_SEQ_CHAR = endChar;
        suffixArrayBuilder = new SuffixArray(sequence, END_SEQ_CHAR);
    }

    public ArrayList<Integer> getLCPArray() {
        if (LCP == null)
            LCPAlgorithm();
        return LCP;
    }

    private void LCPAlgorithm() {
        final ArrayList<Integer> LCPR = getLCPR();
        getLCPfromLCPR(LCPR);
    }

    private ArrayList<Integer> getLCPR() {
        final ArrayList<Integer> KMRArray = suffixArrayBuilder.getKMRarray();
        final ArrayList<Integer> suffixArray = suffixArrayBuilder.getSuffixArray();
        final String string = suffixArrayBuilder.getString();
        final int length = KMRArray.size();
        final ArrayList<Integer> LCPR = new ArrayList<Integer>();             // helper array, LCP[i] = LCPR[suffixArray[i]]

        int startingValue = 0;
        for (int i=0; i<length; i++) {
            if(KMRArray.get(i).equals(0)) {
                LCPR.add(0);
                startingValue = 0;
            } else {
                int LCPRValue = startingValue;
                final int predecessor = suffixArray.get(KMRArray.get(i)-1);
                while (string.charAt(i+LCPRValue) == string.charAt(predecessor+LCPRValue))
                    LCPRValue++;
                LCPR.add(LCPRValue);
                startingValue = LCPRValue-1 > 0 ? LCPRValue-1 : 0;
            }
        }

        return LCPR;
    }

    private void getLCPfromLCPR(ArrayList<Integer> LCPR) {
        final ArrayList<Integer> suffixArray = suffixArrayBuilder.getSuffixArray();
        final int length = suffixArray.size();

        LCP = new ArrayList<Integer>();
        LCP.add(null);                                                  //no value for LCP[0]
        for (int i=1; i<length; i++)
            LCP.add(LCPR.get(suffixArray.get(i)));
    }
}
