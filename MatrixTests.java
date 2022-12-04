package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.Matrix;

public class MatrixTests {

    @Test
    public void testMatrix() {
        Matrix<Integer> matrix1 = new Matrix<Integer>(4, 3);
        matrix1.set(0, 0, 14);
        matrix1.set(0, 1, 9);
        matrix1.set(0, 2, 3);
        matrix1.set(1, 0, 2);
        matrix1.set(1, 1, 11);
        matrix1.set(1, 2, 15);
        matrix1.set(2, 0, 0);
        matrix1.set(2, 1, 12);
        matrix1.set(2, 2, 17);
        matrix1.set(3, 0, 5);
        matrix1.set(3, 1, 2);
        matrix1.set(3, 2, 3);

        Matrix<Integer> matrix2 = new Matrix<Integer>(3, 2);
        matrix2.set(0, 0, 12);
        matrix2.set(0, 1, 25);
        matrix2.set(1, 0, 9);
        matrix2.set(1, 1, 10);
        matrix2.set(2, 0, 8);
        matrix2.set(2, 1, 5);

        // Result of multiplication
        Integer[][] array1 = new Integer[][]{{273,455},
                {243,235},
                {244,205},
                {102,160}};
        Matrix<Integer> result1 = new Matrix<Integer>(4,2,array1);

        Matrix<Integer> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Integer> matrix4 = new Matrix<Integer>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, counter++);

        // Result of subtraction
        Integer[][] array2 = new Integer[][]{{0,0},
                {0,0}};
        Matrix<Integer> result2 = new Matrix<Integer>(2,2,array2);

        Matrix<Integer> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Integer[][] array3 = new Integer[][]{{0,2},
                {4,6}};
        Matrix<Integer> result3 = new Matrix<Integer>(2,2,array3);

        Matrix<Integer> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Integer> matrix7 = new Matrix<Integer>(2, 2);
        matrix7.set(0, 0, 1);
        matrix7.set(0, 1, 2);
        matrix7.set(1, 0, 3);
        matrix7.set(1, 1, 4);

        Matrix<Integer> matrix8 = new Matrix<Integer>(2, 2);
        matrix8.set(0, 0, 1);
        matrix8.set(0, 1, 2);
        matrix8.set(1, 0, 3);
        matrix8.set(1, 1, 4);

        // Result of multiplication
        Integer[][] array4 = new Integer[][]{{7,10},
                {15,22}};
        Matrix<Integer> result4 = new Matrix<Integer>(2,2,array4);

        Matrix<Integer> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    @Test
    public void testIdentityMethod1() {
        Matrix<Integer> matrix = new Matrix<Integer>(2, 2);
        matrix.set(0, 0, 0);
        matrix.set(0, 1, 0);
        matrix.set(1, 0, 0);
        matrix.set(1, 1, 0);

        Matrix<Integer> expectedResult = new Matrix<Integer>(2, 2);
        expectedResult.set(0, 0, 1);
        expectedResult.set(0, 1, 0);
        expectedResult.set(1, 0, 0);
        expectedResult.set(1, 1, 1);

        try{
            matrix = matrix.identity();
        } catch(Exception ex){
            fail();
        }

        assertArrayEquals(expectedResult.getRow(0), matrix.getRow(0));
        assertArrayEquals(expectedResult.getRow(1), matrix.getRow(1));
    }

    @Test
    public void testMatrixNotSquare() throws Exception {
        boolean failed=false;
        Matrix<Long> matrix = new Matrix<Long>(2, 3);
        matrix.set(0, 0, 0l);
        matrix.set(0, 1, 1l);
        matrix.set(0, 2, 2l);
        matrix.set(1, 0, 3l);
        matrix.set(1, 1, 4l);
        matrix.set(0, 2, 5l);

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            failed = true;
        }
        assertTrue(failed);
    }




    @Test
    public void testBigDecimalMatrix() {
        Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(4, 3);
        matrix1.set(0, 0, toBigDecimal(14));
        matrix1.set(0, 1, toBigDecimal(9));
        matrix1.set(0, 2, toBigDecimal(3));
        matrix1.set(1, 0, toBigDecimal(2));
        matrix1.set(1, 1, toBigDecimal(11));
        matrix1.set(1, 2, toBigDecimal(15));
        matrix1.set(2, 0, toBigDecimal(0));
        matrix1.set(2, 1, toBigDecimal(12));
        matrix1.set(2, 2, toBigDecimal(17));
        matrix1.set(3, 0, toBigDecimal(5));
        matrix1.set(3, 1, toBigDecimal(2));
        matrix1.set(3, 2, toBigDecimal(3));

        Matrix<BigDecimal> matrix2 = new Matrix<BigDecimal>(3, 2);
        matrix2.set(0, 0, toBigDecimal(12));
        matrix2.set(0, 1, toBigDecimal(25));
        matrix2.set(1, 0, toBigDecimal(9));
        matrix2.set(1, 1, toBigDecimal(10));
        matrix2.set(2, 0, toBigDecimal(8));
        matrix2.set(2, 1, toBigDecimal(5));

        BigDecimal[][] array1 = new BigDecimal[][]{{toBigDecimal(273),toBigDecimal(455)},
                {toBigDecimal(243),toBigDecimal(235)},
                {toBigDecimal(244),toBigDecimal(205)},
                {toBigDecimal(102),toBigDecimal(160)}};
        Matrix<BigDecimal> result1 = new Matrix<BigDecimal>(4,2,array1);

        Matrix<BigDecimal> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<BigDecimal> matrix4 = new Matrix<BigDecimal>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, toBigDecimal(counter++))
        BigDecimal[][] array2 = new BigDecimal[][]{{BigDecimal.ZERO,BigDecimal.ZERO},
                {BigDecimal.ZERO,BigDecimal.ZERO}};
        Matrix<BigDecimal> result2 = new Matrix<BigDecimal>(2,2,array2);

        Matrix<BigDecimal> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));
        BigDecimal[][] array3 = new BigDecimal[][]{{BigDecimal.ZERO,toBigDecimal(2)},
                {toBigDecimal(4),toBigDecimal(6)}};
        Matrix<BigDecimal> result3 = new Matrix<BigDecimal>(2,2,array3);

        Matrix<BigDecimal> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<BigDecimal> matrix7 = new Matrix<BigDecimal>(2, 2);
        matrix7.set(0, 0, toBigDecimal(1));
        matrix7.set(0, 1, toBigDecimal(2));
        matrix7.set(1, 0, toBigDecimal(3));
        matrix7.set(1, 1, toBigDecimal(4));

        Matrix<BigDecimal> matrix8 = new Matrix<BigDecimal>(2, 2);
        matrix8.set(0, 0, toBigDecimal(1));
        matrix8.set(0, 1, toBigDecimal(2));
        matrix8.set(1, 0, toBigDecimal(3));
        matrix8.set(1, 1, toBigDecimal(4));
        BigDecimal[][] array4 = new BigDecimal[][]{{toBigDecimal(7),toBigDecimal(10)},
                {toBigDecimal(15),toBigDecimal(22)}};
        Matrix<BigDecimal> result4 = new Matrix<BigDecimal>(2,2,array4);

        Matrix<BigDecimal> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    @Test
    public void testBigIntegerMatrix() {
        Matrix<BigInteger> matrix1 = new Matrix<BigInteger>(4, 3);
        matrix1.set(0, 0, toBigInteger(14));
        matrix1.set(0, 1, toBigInteger(9));
        matrix1.set(0, 2, toBigInteger(3));
        matrix1.set(1, 0, toBigInteger(2));
        matrix1.set(1, 1, toBigInteger(11));
        matrix1.set(1, 2, toBigInteger(15));
        matrix1.set(2, 0, toBigInteger(0));
        matrix1.set(2, 1, toBigInteger(12));
        matrix1.set(2, 2, toBigInteger(17));
        matrix1.set(3, 0, toBigInteger(5));
        matrix1.set(3, 1, toBigInteger(2));
        matrix1.set(3, 2, toBigInteger(3));

        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(3, 2);
        matrix2.set(0, 0, toBigInteger(12));
        matrix2.set(0, 1, toBigInteger(25));
        matrix2.set(1, 0, toBigInteger(9));
        matrix2.set(1, 1, toBigInteger(10));
        matrix2.set(2, 0, toBigInteger(8));
        matrix2.set(2, 1, toBigInteger(5));
        BigInteger[][] array1 = new BigInteger[][]{{toBigInteger(273),toBigInteger(455)},
                {toBigInteger(243),toBigInteger(235)},
                {toBigInteger(244),toBigInteger(205)},
                {toBigInteger(102),toBigInteger(160)}};
        Matrix<BigInteger> result1 = new Matrix<BigInteger>(4,2,array1);

        Matrix<BigInteger> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<BigInteger> matrix4 = new Matrix<BigInteger>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, toBigInteger(counter++));
        BigInteger[][] array2 = new BigInteger[][]{{BigInteger.ZERO,BigInteger.ZERO},
                {BigInteger.ZERO,BigInteger.ZERO}};
        Matrix<BigInteger> result2 = new Matrix<BigInteger>(2,2,array2);

        Matrix<BigInteger> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));
        BigInteger[][] array3 = new BigInteger[][]{{BigInteger.ZERO,toBigInteger(2)},
                {toBigInteger(4),toBigInteger(6)}};
        Matrix<BigInteger> result3 = new Matrix<BigInteger>(2,2,array3);

        Matrix<BigInteger> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<BigInteger> matrix7 = new Matrix<BigInteger>(2, 2);
        matrix7.set(0, 0, toBigInteger(1));
        matrix7.set(0, 1, toBigInteger(2));
        matrix7.set(1, 0, toBigInteger(3));
        matrix7.set(1, 1, toBigInteger(4));

        Matrix<BigInteger> matrix8 = new Matrix<BigInteger>(2, 2);
        matrix8.set(0, 0, toBigInteger(1));
        matrix8.set(0, 1, toBigInteger(2));
        matrix8.set(1, 0, toBigInteger(3));
        matrix8.set(1, 1, toBigInteger(4));
        BigInteger[][] array4 = new BigInteger[][]{{toBigInteger(7),toBigInteger(10)},
                {toBigInteger(15),toBigInteger(22)}};
        Matrix<BigInteger> result4 = new Matrix<BigInteger>(2,2,array4);

        Matrix<BigInteger> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }


    @Test
    public void testDoubleMatrix() {
        Matrix<Double> matrix1 = new Matrix<Double>(4, 3);
        matrix1.set(0, 0, toDouble(14));
        matrix1.set(0, 1, toDouble(9));
        matrix1.set(0, 2, toDouble(3));
        matrix1.set(1, 0, toDouble(2));
        matrix1.set(1, 1, toDouble(11));
        matrix1.set(1, 2, toDouble(15));
        matrix1.set(2, 0, toDouble(0));
        matrix1.set(2, 1, toDouble(12));
        matrix1.set(2, 2, toDouble(17));
        matrix1.set(3, 0, toDouble(5));
        matrix1.set(3, 1, toDouble(2));
        matrix1.set(3, 2, toDouble(3));

        Matrix<Double> matrix2 = new Matrix<Double>(3, 2);
        matrix2.set(0, 0, toDouble(12));
        matrix2.set(0, 1, toDouble(25));
        matrix2.set(1, 0, toDouble(9));
        matrix2.set(1, 1, toDouble(10));
        matrix2.set(2, 0, toDouble(8));
        matrix2.set(2, 1, toDouble(5));

        Double[][] array1 = new Double[][]{{toDouble(273),toDouble(455)},
                {toDouble(243),toDouble(235)},
                {toDouble(244),toDouble(205)},
                {toDouble(102),toDouble(160)}};
        Matrix<Double> result1 = new Matrix<Double>(4,2,array1);

        Matrix<Double> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Double> matrix4 = new Matrix<Double>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, toDouble(counter++));

        // Result of subtraction
        Double[][] array2 = new Double[][]{{0.0,0.0},
                {0.0,0.0}};
        Matrix<Double> result2 = new Matrix<Double>(2,2,array2);

        Matrix<Double> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Double[][] array3 = new Double[][]{{0.0,toDouble(2)},
                {toDouble(4),toDouble(6)}};
        Matrix<Double> result3 = new Matrix<Double>(2,2,array3);

        Matrix<Double> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Double> matrix7 = new Matrix<Double>(2, 2);
        matrix7.set(0, 0, toDouble(1));
        matrix7.set(0, 1, toDouble(2));
        matrix7.set(1, 0, toDouble(3));
        matrix7.set(1, 1, toDouble(4));

        Matrix<Double> matrix8 = new Matrix<Double>(2, 2);
        matrix8.set(0, 0, toDouble(1));
        matrix8.set(0, 1, toDouble(2));
        matrix8.set(1, 0, toDouble(3));
        matrix8.set(1, 1, toDouble(4));

        // Result of multiplication
        Double[][] array4 = new Double[][]{{toDouble(7),toDouble(10)},
                {toDouble(15),toDouble(22)}};
        Matrix<Double> result4 = new Matrix<Double>(2,2,array4);

        Matrix<Double> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }



    private BigDecimal toBigDecimal(int val) {
        return BigDecimal.valueOf(val);
    }

    private BigInteger toBigInteger(int val) {
        return BigInteger.valueOf(val);
    }

    private Long toLong(int val) {
        return Long.valueOf(val);
    }

    private Double toDouble(int val) {
        return Double.valueOf(val);
    }

    private Float toFloat(int val) {
        return Float.valueOf(val);
    }

}