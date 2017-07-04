package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.data_structures.Matrix;
import com.jwetherell.algorithms.mathematics.LUDecomposition;
import org.junit.Test;

import static org.junit.Assert.*;

public class LUDecompositionTest {

    private boolean epsiMatrixCompare(Matrix<Double> a, Matrix<Double> b, double epsi) {
        if (a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            throw new IllegalArgumentException("Matrices are not the same shape");
        }
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                if (Math.abs(a.get(i, j) - b.get(i, j)) > epsi) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void decompositionTest1() throws Exception {
        Double[][] m = new Double[][]{{4.0, 3.0}, {6.0, 3.0}};
        Double[][] resultL = new Double[][]{{1.0, 0.0}, {2.0 / 3.0, 1.0}};
        Double[][] resultU = new Double[][]{{6.0, 3.0}, {0.0, 1.0}};

        LUDecomposition luDecomposition = new LUDecomposition(new Matrix<Double>(2, 2, m));
        assertTrue(epsiMatrixCompare(luDecomposition.getL(), new Matrix<Double>(2, 2, resultL), 10e-4));
        assertTrue(epsiMatrixCompare(luDecomposition.getU(), new Matrix<Double>(2, 2, resultU), 10e-4));
    }

    @Test
    public void decompositionTest2() throws Exception {
        Double[][] m = new Double[][]{{5.0, 3.0, 2.0}, {1.0, 2.0, 0.0}, {3.0, 0.0, 4.0}};
        Double[][] resultL = new Double[][]{{1.0, 0.0, 0.0}, {0.6, 1.0, 0.0}, {0.2, -0.7778, 1.0}};
        Double[][] resultU = new Double[][]{{5.0, 3.0, 2.0}, {0.0, -1.8, 2.8}, {0.0, 0.0, 1.778}};

        LUDecomposition luDecomposition = new LUDecomposition(new Matrix<Double>(3, 3, m));
        assertTrue(epsiMatrixCompare(luDecomposition.getL(), new Matrix<Double>(3, 3, resultL), 10e-4));
        assertTrue(epsiMatrixCompare(luDecomposition.getU(), new Matrix<Double>(3, 3, resultU), 10e-4));
    }
}