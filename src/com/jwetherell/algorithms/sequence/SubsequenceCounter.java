package com.jwetherell.algorithms.sequence;

/**
 * Finds the number of times a string occurs as a subsequence in a text.
 * <p>
 * @see <a href="https://www.geeksforgeeks.org/find-number-times-string-occurs-given-string">Substring occurs in String (GeeksForGeeks)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SubsequenceCounter {

    private static char[] seq = null;
    private static char[] subseq = null;
    private static int[][] tbl = null;

    private SubsequenceCounter() { }

    /**
     * Finds the number of times a string occurs as a subsequence in a text.
     * 
     * @param sequence Text to find subsequence in.
     * @param subSequence subsequence to find in the text.
     * @return Number of times a string occurs as a subsequence in a text
     */
    public static int getCount(char[] sequence, char[] subSequence) {
        try {
            seq = sequence;
            subseq = subSequence;
            tbl = new int[seq.length + 1][subseq.length + 1];
    
            for (int row = 0; row < tbl.length; row++)
                for (int col = 0; col < tbl[row].length; col++)
                    tbl[row][col] = countMatches(row, col);
    
            return tbl[seq.length][subseq.length];
        } finally {
            seq = null;
            subseq = null;
            tbl = null;
        }
    }

    private static int countMatches(int seqDigitsLeft, int subseqDigitsLeft) {
        if (subseqDigitsLeft == 0)
            return 1;

        if (seqDigitsLeft == 0)
            return 0;

        final char currSeqDigit = seq[seq.length - seqDigitsLeft];
        final char currSubseqDigit = subseq[subseq.length - subseqDigitsLeft];

        int result = 0;
        if (currSeqDigit == currSubseqDigit)
            result += tbl[seqDigitsLeft - 1][subseqDigitsLeft - 1];
        result += tbl[seqDigitsLeft - 1][subseqDigitsLeft];

        return result;
    }
}
