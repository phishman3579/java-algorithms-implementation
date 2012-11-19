package com.jwetherell.algorithms.sequence;

public class FibonacciSequence {

    private static final double INVERSE_SQUARE_ROOT_OF_5 = 1 / Math.sqrt(5); // Inverse
                                                                             // of
                                                                             // the
                                                                             // square
                                                                             // root
                                                                             // of
                                                                             // 5
    private static final double PHI = (1 + Math.sqrt(5)) / 2; // Golden ratio

    public static final long fibonacciSequenceUsingLoop(int n) {
        long[] array = new long[n + 1];
        int counter = 0;
        while (counter <= n) {
            long r = 0;
            if (counter > 1) {
                r = array[counter - 1] + array[counter - 2];
            } else if (counter == 1) {
                r = 1;
            }
            array[counter] = r;
            counter++;
        }

        return array[n];
    }

    public static final long fibonacciSequenceUsingRecursion(int n) {
        if (n == 0 || n == 1) return n;
        else return fibonacciSequenceUsingRecursion(n - 1) + fibonacciSequenceUsingRecursion(n - 2);
    }

    public static final long fibonacciSequenceUsingMatrixMultiplication(int n) {
        // m = [ 1 , 1 ]
        // [ 1 , 0 ]
        long[][] matrix = new long[2][2];
        matrix[0][0] = 1;
        matrix[0][1] = 1;
        matrix[1][0] = 1;
        matrix[1][1] = 0;

        long[][] temp = new long[2][2];
        temp[0][0] = 1;
        temp[0][1] = 1;
        temp[1][0] = 1;
        temp[1][1] = 0;

        int counter = n;
        while (counter > 0) {
            temp = multiplyMatrices(matrix, temp);
            // Subtract an additional 1 the first time in the loop because the
            // first multiplication is
            // actually n -= 2 since it multiplying two matrices
            counter -= (counter == n) ? 2 : 1;
        }
        return temp[0][1];
    }

    private static final long[][] multiplyMatrices(long[][] A, long[][] B) {
        long a = A[0][0];
        long b = A[0][1];
        long c = A[1][0];
        long d = A[1][1];

        long e = B[0][0];
        long f = B[0][1];
        long g = B[1][0];
        long h = B[1][1];

        B[0][0] = a * e + b * g;
        B[0][1] = a * f + b * h;
        B[1][0] = c * e + d * g;
        B[1][1] = c * f + d * h;

        return B;
    }

    public static final long fibonacciSequenceUsingBinetsFormula(int n) {
        return (long) Math.floor(Math.pow(PHI, n) * INVERSE_SQUARE_ROOT_OF_5 + 0.5);
    }
}
