package com.jwetherell.algorithms.sequence;

/**
 * In mathematics, the Fibonacci numbers are the numbers in the following integer sequence, called the Fibonacci sequence, and characterized by the fact that every number after the first two is the 
 * sum of the two preceding ones: 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ...
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci Sequence (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class FibonacciSequence {

    private static final double INVERSE_SQUARE_ROOT_OF_5 = 1 / Math.sqrt(5); // Inverse of the square root of 5
    private static final double PHI = (1 + Math.sqrt(5)) / 2; // Golden ratio

    private FibonacciSequence() {}

    // Refactoring Technique used: Renamed variable 'r' to more meaningful name 'sumOfPrevTwo'
    public static final long fibonacciSequenceUsingLoop(int n) {
        final long[] array = new long[n + 1];
        int counter = 0;
        while (counter <= n) {
            long sumOfPrevTwo  = 0;
            if (counter > 1) {
                sumOfPrevTwo  = array[counter - 1] + array[counter - 2];
            } else if (counter == 1) {
                sumOfPrevTwo  = 1;
            }
            // If sumOfPrevTwo goes below zero then we have run out of bits in the long
            if (sumOfPrevTwo  < 0)
                throw new IllegalArgumentException("Run out of bits in long, n="+n);
            array[counter] = sumOfPrevTwo;
            counter++;
        }

        return array[n];
    }

    /**
     * Recursion with memoization
     */
    public static final long fibonacciSequenceUsingRecursion(int n) {
        // Using the array to store values already computed
        final long[] array = new long[n + 1];
        return fibonacciSequenceUsingRecursion(array,n);
    }

    private static final long fibonacciSequenceUsingRecursion(long[] array, int n) {
        if (n == 0 || n == 1)
            return n;

        // If array already has a value then it has previously been computed
        if (array[n] != 0)
            return array[n];

        final String exception = "Run out of bits in long, n="+n;

        final long r1 = fibonacciSequenceUsingRecursion(array, (n - 1));
        array[n-1] = r1; // memoization
        // If r1 goes below zero then we have run out of bits in the long
        if (r1 < 0)
            throw new IllegalArgumentException(exception);

        final long r2 = fibonacciSequenceUsingRecursion(array, (n - 2));
        array[n-2] = r2; // memoization
        // If r2 goes below zero then we have run out of bits in the long
        if (r2 < 0)
            throw new IllegalArgumentException(exception);

        final long r = r1 + r2;
        // If r goes below zero then we have run out of bits in the long
        if (r < 0)
            throw new IllegalArgumentException("Run out of bits in long, n="+n);

        array[n] = r; // memoization

        return r;
    }

    // Refactoring Technique: Extract Method
    public static long[][] matrix_initialization(long[][] input_matrix){
        input_matrix[0][0] = 1;
        input_matrix[0][1] = 1;
        input_matrix[1][0] = 1;
        input_matrix[1][1] = 0;
        return input_matrix;
    }

    public static final long fibonacciSequenceUsingMatrixMultiplication(int n) {
        // m = [ 1 , 1 ]
        //     [ 1 , 0 ]

        // Refactoring Technique: Extract Method ; method definition on line 79-86
        final long[][] matrix = matrix_initialization(new long[2][2]);
        // final long[][] matrix = new long[2][2];
        // matrix[0][0] = 1;
        // matrix[0][1] = 1;
        // matrix[1][0] = 1;
        // matrix[1][1] = 0;

        // Refactoring Technique: Extract Method ; method definition on line 79-86
        long[][] temp = matrix_initialization(new long[2][2]);
        // long[][] temp = new long[2][2];
        // temp[0][0] = 1;
        // temp[0][1] = 1;
        // temp[1][0] = 1;
        // temp[1][1] = 0;

        int counter = n;
        while (counter > 0) {
            temp = multiplyMatrices(matrix, temp);
            // Subtract an additional 1 the first time in the loop because the
            // first multiplication is actually n -= 2 since it multiplying two matrices
            counter -= (counter == n) ? 2 : 1;
        }
        final long r = temp[0][1];
        // If r goes below zero then we have run out of bits in the long
        if (r < 0)
            throw new IllegalArgumentException("Run out of bits in long, n="+n);
        return r;
    }

    private static final long[][] multiplyMatrices(long[][] A, long[][] B) {
        final long a = A[0][0];
        final long b = A[0][1];
        final long c = A[1][0];
        final long d = A[1][1];

        final long e = B[0][0];
        final long f = B[0][1];
        final long g = B[1][0];
        final long h = B[1][1];

        B[0][0] = a * e + b * g;
        B[0][1] = a * f + b * h;
        B[1][0] = c * e + d * g;
        B[1][1] = c * f + d * h;

        return B;
    }

    public static final long fibonacciSequenceUsingBinetsFormula(int n) {
        final long r = (long) Math.floor(Math.pow(PHI, n) * INVERSE_SQUARE_ROOT_OF_5 + 0.5);
        // If r hits max value then we have run out of bits in the long
        if (r == Long.MAX_VALUE)
            throw new IllegalArgumentException("Run out of bits in long, n="+n);
        return r;
    }
}
