package com.jwetherell.algorithms.mathematics;

import com.jwetherell.algorithms.data_structures.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LU decomposition of matrix M produces 2 matrices L and U such that M = L*U
 * where L is lower triangular matrix and U is upper triangular matrix
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/LU_decomposition">LU Decomposition (Wikipedia)</a>
 * <br>
 * @author Mateusz Cianciara <e.cianciara@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LUDecomposition {

    private int n = 0;
    private Double[][] L = null;
    private Double[][] A = null;
    private Integer[] permutation = null;

    public Matrix<Double> getL() {
        return new Matrix<Double>(n, n, L);
    }

    public Matrix<Double> getU() {
        return new Matrix<Double>(n, n, A);
    }

    public List<Integer> getPermutation() {
        return new ArrayList<Integer>(Arrays.asList(permutation));
    }

    public LUDecomposition(Matrix<Double> input) {
        if (input.getCols() != input.getRows())
            throw new IllegalArgumentException("Matrix is not square");

        n = input.getCols();
        L = new Double[n][n];
        A = new Double[n][n];
        permutation = new Integer[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                L[i][j] = 0.0;
                A[i][j] = input.get(i, j);
            }
        }
        for (int i = 0; i < n; i++) {
            L[i][i] = 1.0;
            permutation[i] = i;
        }
        for (int row = 0; row < n; row++) {
            // find max in column
            int max_in_col = row;
            double curr_big = Math.abs(A[row][row]);
            for (int k = row + 1; k < n; k++) {
                if (curr_big < Math.abs(A[k][row])) {
                    max_in_col = k;
                    curr_big = Math.abs(A[k][row]);
                }
            }

            //swap rows
            if (row != max_in_col) {
                for (int i = 0; i < n; i++) {
                    double temp = A[row][i];
                    A[row][i] = A[max_in_col][i];
                    A[max_in_col][i] = temp;

                    if (i < row) {
                        temp = L[row][i];
                        L[row][i] = L[max_in_col][i];
                        L[max_in_col][i] = temp;
                    }
                }
                final int temp = permutation[row];
                permutation[row] = permutation[max_in_col];
                permutation[max_in_col] = temp;
            }

            //zero column number row
            final double p = A[row][row];
            if (p == 0)
                return;

            for (int i = row + 1; i < n; i++) {
                final double y = A[i][row];
                L[i][row] = y / p;

                for (int j = row; j < n; j++) {
                    A[i][j] -= A[row][j] * (y / p);
                }
            }
        }
    }
}
