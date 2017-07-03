package com.jwetherell.algorithms.sequence;

import java.util.HashSet;
import java.util.Set;

/**
 * The longest common subsequence (LCS) problem is the problem of finding the longest subsequence common to all sequences in a set of sequences (often just two sequences). It differs from problems 
 * of finding common substrings: unlike substrings, subsequences are not required to occupy consecutive positions within the original sequences. 
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Longest_common_subsequence_problem">Longest Common Subsequence Problem (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class LongestCommonSubsequence {

    private static int[][] lengthMatrix = null;
    private static Set<String>[][] sequenceMatrix = null;

    private LongestCommonSubsequence() { }

    public static MatrixPair getLCS(char[] seq1, char[] seq2) {
        try {
            populateMatrix(seq1, seq2);
    
            for (int i = 0; i < seq1.length; i++) {
                for (int j = 0; j < seq2.length; j++) {
                    lengthMatrix[i + 1][j + 1] = longestCommonSubsequence(i, j, seq1, seq2);
                }
            }
    
            return (new MatrixPair(lengthMatrix, sequenceMatrix));
        } finally {
            lengthMatrix = null;
            sequenceMatrix = null;
        }
    }

    private static void populateMatrix(char[] seq1, char[] seq2) {
        lengthMatrix = new int[seq1.length + 1][seq2.length + 1];
        sequenceMatrix = new HashSet[seq1.length][seq2.length];
    }

    private static int longestCommonSubsequence(int i, int j, char[] seq1, char[] seq2) {
        char x = seq1[i];
        char y = seq2[j];
        int result = 0;
        Set<String> set = sequenceMatrix[i][j];
        if (set == null)
            set = new HashSet<String>();
        if (x == y) {
            if (i > 0 && j > 0)
                set = new HashSet<String>(sequenceMatrix[i - 1][j - 1]);
            distribute(x, set);

            result = (lengthMatrix[i][j]) + 1;
        } else {
            int a = lengthMatrix[i][j + 1];
            int b = lengthMatrix[i + 1][j];

            if (a > b) {
                set = new HashSet<String>(sequenceMatrix[i - 1][j]);

                result = a;
            } else if (b > a) {
                set = new HashSet<String>(sequenceMatrix[i][j - 1]);

                result = b;
            } else if (a == b) {
                if (i > 0 && j > 0) {
                    Set<String> list1 = sequenceMatrix[i - 1][j];
                    Set<String> list2 = sequenceMatrix[i][j - 1];

                    set.addAll(list1);
                    set.addAll(list2);
                }

                result = a; // a==b
            }
        }
        sequenceMatrix[i][j] = set;
        return result;
    }

    private static void distribute(char c, Set<String> set) {
        if (set.size() == 0) {
            set.add(String.valueOf(c));
        } else {
            Object[] strings = set.toArray();
            set.clear();
            for (Object object : strings) {
                String string = (String) object;
                String seq = String.valueOf(c);
                if (!string.contains(seq))
                    string = string.concat(seq);
                set.add(string);
            }
        }
    }

    public static class MatrixPair {

        private int[][] lenMatrix = null;
        private Set<String>[][] seqMatrix = null;

        public MatrixPair(int[][] lengthMatrix, Set<String>[][] sequenceMatrix) {
            this.lenMatrix = lengthMatrix;
            this.seqMatrix = sequenceMatrix;
        }

        public int getLongestSequenceLength() {
            if (lenMatrix == null)
                return 0;

            int length1 = lenMatrix.length;
            int length2 = lenMatrix[length1 - 1].length;
            return lenMatrix[length1 - 1][length2 - 1];
        }

        public Set<String> getLongestSequences() {
            if (seqMatrix == null)
                return (new HashSet<String>());

            int length1 = seqMatrix.length;
            int length2 = seqMatrix[length1 - 1].length;
            return seqMatrix[length1 - 1][length2 - 1];
        }

        public int[][] getLengthMatrix() {
            return lenMatrix;
        }

        public Set<String>[][] getSequenceMatrix() {
            return seqMatrix;
        }

        public String getLengthMatrixString() {
            StringBuilder builder = new StringBuilder();
            if (lenMatrix == null) {
                builder.append("Length matrix is NULL.\n");
            } else {
                for (int i = 0; i < lenMatrix.length; i++) {
                    int length = lenMatrix[i].length;
                    for (int j = 0; j < length; j++) {
                        int size = lenMatrix[i][j];
                        builder.append(size);
                        if (j < length - 1)
                            builder.append(",\t");
                    }
                    builder.append("\n");
                }
            }
            return builder.toString();
        }

        public String getSequenceMatrixString() {
            StringBuilder builder = new StringBuilder();
            if (seqMatrix == null) {
                builder.append("Sequence matrix is NULL.\n");
            } else {
                for (int i = 0; i < seqMatrix.length; i++) {
                    int length = seqMatrix[i].length;
                    for (int j = 0; j < length; j++) {
                        Set<String> set = seqMatrix[i][j];
                        builder.append(set.toString());
                        if (j < length - 1)
                            builder.append(", ");
                    }
                    builder.append("\n");
                }
            }
            return builder.toString();
        }
    }
}
