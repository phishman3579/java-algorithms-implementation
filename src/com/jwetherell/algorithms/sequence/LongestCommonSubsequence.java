package com.jwetherell.algorithms.sequence;

import java.util.HashSet;
import java.util.Set;


public class LongestCommonSubsequence {

    private static int[][] lengthMatrix = null;
    private static Set<String>[][] sequenceMatrix = null;

    private LongestCommonSubsequence() {
    }

    @SuppressWarnings("unchecked")
    private static void populateMatrix(char[] seq1, char[] seq2) {
        lengthMatrix = new int[seq1.length + 1][seq2.length + 1];
        sequenceMatrix = new HashSet[seq1.length][seq2.length];
    }

    private static int longestCommonSubsequence(int i, int j, char[] seq1, char[] seq2) {
        char x = seq1[i];
        char y = seq2[j];
        int result = 0;
        Set<String> set = sequenceMatrix[i][j];
        if (set == null) set = new HashSet<String>();
        if (x == y) {
            if (i > 0 && j > 0) set = new HashSet<String>(sequenceMatrix[i - 1][j - 1]);
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
                if (!string.contains(seq)) string = string.concat(seq);
                set.add(string);
            }
        }
    }

    public static MatrixPair getLCS(char[] seq1, char[] seq2) {
        populateMatrix(seq1, seq2);

        for (int i = 0; i < seq1.length; i++) {
            for (int j = 0; j < seq2.length; j++) {
                lengthMatrix[i + 1][j + 1] = longestCommonSubsequence(i, j, seq1, seq2);
            }
        }

        return (new MatrixPair(lengthMatrix, sequenceMatrix));
    }

    public static class MatrixPair {

        private int[][] lengthMatrix = null;
        private Set<String>[][] sequenceMatrix = null;

        public MatrixPair(int[][] lengthMatrix, Set<String>[][] sequenceMatrix) {
            this.lengthMatrix = lengthMatrix;
            this.sequenceMatrix = sequenceMatrix;
        }

        public int getLongestSequenceLength() {
            if (lengthMatrix == null) return 0;

            int length1 = lengthMatrix.length;
            int length2 = lengthMatrix[length1 - 1].length;
            return lengthMatrix[length1 - 1][length2 - 1];
        }

        public Set<String> getLongestSequences() {
            if (sequenceMatrix == null) return (new HashSet<String>());

            int length1 = sequenceMatrix.length;
            int length2 = sequenceMatrix[length1 - 1].length;
            return sequenceMatrix[length1 - 1][length2 - 1];
        }

        public int[][] getLengthMatrix() {
            return lengthMatrix;
        }

        public Set<String>[][] getSequenceMatrix() {
            return sequenceMatrix;
        }

        public String getLengthMatrixString() {
            StringBuilder builder = new StringBuilder();
            if (lengthMatrix == null) {
                builder.append("Length matrix is NULL.\n");
            } else {
                for (int i = 0; i < lengthMatrix.length; i++) {
                    int length = lengthMatrix[i].length;
                    for (int j = 0; j < length; j++) {
                        int size = lengthMatrix[i][j];
                        builder.append(size);
                        if (j < length - 1) builder.append(",\t");
                    }
                    builder.append("\n");
                }
            }
            return builder.toString();
        }

        public String getSequenceMatrixString() {
            StringBuilder builder = new StringBuilder();
            if (sequenceMatrix == null) {
                builder.append("Sequence matrix is NULL.\n");
            } else {
                for (int i = 0; i < sequenceMatrix.length; i++) {
                    int length = sequenceMatrix[i].length;
                    for (int j = 0; j < length; j++) {
                        Set<String> set = sequenceMatrix[i][j];
                        builder.append(set.toString());
                        if (j < length - 1) builder.append(", ");
                    }
                    builder.append("\n");
                }
            }
            return builder.toString();
        }
    }
}
