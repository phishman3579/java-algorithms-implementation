package com.jwetherell.algorithms.sequence;

/**
 * A longest palin­dromic sub­se­quence is a sequence that appears in the same
 * rel­a­tive order, but not nec­es­sar­ily contiguous(not sub­string) and
 * palin­drome in nature.
 * <p>
 * Given a string, find the length of the longest palin­dromic sub­se­quence in it.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Longest_palindromic_substring">Longest Palin­dromic Sub­se­quence (Wikipedia)</a>
 * <br>
 * @author Miguel Stephane KAKANAKOU <Skakanakou@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LongestPalindromicSubsequence {

    private LongestPalindromicSubsequence() { }

    /**
     * Find the length of the longest palin­dromic sub­se­quence in the given
     * string s using the dynamic programming approach.
     */
    public static int getLongestPalindromeSubsequence(String s) {
        if (s == null)
            throw new NullPointerException("The given String is null");

        final int len = s.length();
        final int[][] M = new int[len][len];
        final char[] ch = s.toCharArray();

        initializeMatrix(M);
        fillMatrix(M, ch);
        return M[0][len-1];
    }
    
    private static void initializeMatrix(int[][] M) {
        int len = M.length;
        for (int i=0; i<len; i++) {
            for (int j=0; j<=i; j++) {
                if (j == i)
                    M[i][j] = 1;
                else
                    M[i][j] = 0;
            }
        }
    }
    
    private static void fillMatrix(int[][] M, char[] ch) {
        final int len = M.length;
        int i, j;
        for (int k=1; k<len; k++) {
            i = 0;
            j = k;
            while (j<len) {
                if (ch[i] == ch[j])
                    M[i][j] = 2 + M[i+1][j-1];
                else
                    M[i][j] = Math.max(M[i][j-1], M[i+1][j]);
                i++;
                j++;
            }
        }
    }
}
