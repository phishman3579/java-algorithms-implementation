package com.jwetherell.algorithms.data_structures.test;

import java.io.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;

import static org.junit.Assert.assertArrayEquals;
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
        Integer[][] array1 = new Integer[][] { { 273, 455 },
                { 243, 235 },
                { 244, 205 },
                { 102, 160 } };
        Matrix<Integer> result1 = new Matrix<Integer>(4, 2, array1);

        Matrix<Integer> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3=" + matrix3 + " result1" + result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Integer> matrix4 = new Matrix<Integer>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, counter++);

        // Result of subtraction
        Integer[][] array2 = new Integer[][] { { 0, 0 },
                { 0, 0 } };
        Matrix<Integer> result2 = new Matrix<Integer>(2, 2, array2);

        Matrix<Integer> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5=" + matrix5 + " result2" + result2, matrix5.equals(result2));

        // Result of addition
        Integer[][] array3 = new Integer[][] { { 0, 2 },
                { 4, 6 } };
        Matrix<Integer> result3 = new Matrix<Integer>(2, 2, array3);

        Matrix<Integer> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6=" + matrix6 + " result3" + result3, matrix6.equals(result3));

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
        Integer[][] array4 = new Integer[][] { { 7, 10 },
                { 15, 22 } };
        Matrix<Integer> result4 = new Matrix<Integer>(2, 2, array4);

        Matrix<Integer> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9=" + matrix9 + " result4" + result4, matrix9.equals(result4));
    }

    // test null and cols!=rows cases
    @Test
    public void testMatrixInvalidInputs() {
        Matrix<Integer> matrix1 = new Matrix<Integer>(1, 1);
        matrix1.set(0, 0, 99);

        Matrix<Integer> matrix11 = new Matrix<Integer>(1, 1);
        matrix1.set(0, 0, 100);

        Matrix<Integer> matrix2 = new Matrix<Integer>(2, 2);
        matrix2.set(0, 0, 1);
        matrix2.set(0, 1, 1);
        matrix2.set(1, 0, 1);
        matrix2.set(1, 1, 1);

        Matrix<Integer> matrix3 = new Matrix<Integer>(2, 3);
        matrix2.set(0, 0, 0);
        matrix2.set(0, 1, 1);
        matrix2.set(0, 2, 2);
        matrix2.set(1, 0, 3);
        matrix2.set(1, 1, 4);

        // if (this.cols != input.rows)
        Matrix<Integer> matrixProductInvalid = matrix1.multiply(matrix2);
        Matrix<Integer> matrixSumInvalid = matrix1.add(matrix2);
        Matrix<Integer> matrixSubInvalid = matrix1.subtract(matrix2);
        Assert.assertNull(matrixProductInvalid.get(0, 0));
        Assert.assertNull(matrixSumInvalid.get(0, 0));
        Assert.assertNull(matrixSubInvalid.get(0, 0));

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

        try {
            matrix = matrix.identity();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            matrix3.equals(matrix2);
            matrix2.equals(matrix1);
            matrix2.equals("invalid string");
            matrix.equals(null);
            Assert.assertFalse(matrix11.equals(matrix1));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expectedResult.equals(matrix));

        try {
            matrix.copy(matrix2);
        } catch (Exception ex) {
            fail();
        }
        // call hashCode
        try {
            matrix.hashCode();
        } catch (Exception ex) {
            fail();
        }

    }

    // test BigDeimal
    @Test
    public void testMatrixBigDecimal() throws Exception {

        Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(2, 2);
        matrix1.set(0, 0, BigDecimal.valueOf(0));
        matrix1.set(0, 1, BigDecimal.valueOf(0));

        matrix1.set(1, 0, BigDecimal.valueOf(1));
        matrix1.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> matrix2 = new Matrix<BigDecimal>(2, 2);
        matrix2.set(0, 0, BigDecimal.valueOf(1));
        matrix2.set(0, 1, BigDecimal.valueOf(1));
        matrix2.set(1, 0, BigDecimal.valueOf(0));
        matrix2.set(1, 1, BigDecimal.valueOf(0));

        Matrix<BigDecimal> matrixSum = new Matrix<BigDecimal>(2, 2);
        matrixSum.set(0, 0, BigDecimal.valueOf(1));
        matrixSum.set(0, 1, BigDecimal.valueOf(1));
        matrixSum.set(1, 0, BigDecimal.valueOf(1));
        matrixSum.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> matrixProduct = new Matrix<BigDecimal>(2, 2);
        matrixProduct.set(0, 0, BigDecimal.valueOf(0));
        matrixProduct.set(0, 1, BigDecimal.valueOf(0));
        matrixProduct.set(1, 0, BigDecimal.valueOf(1));
        matrixProduct.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> matrixSubtract = new Matrix<BigDecimal>(2, 2);
        matrixSubtract.set(0, 0, BigDecimal.valueOf(-1));
        matrixSubtract.set(0, 1, BigDecimal.valueOf(-1));
        matrixSubtract.set(1, 0, BigDecimal.valueOf(1));
        matrixSubtract.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> matrix = new Matrix<BigDecimal>(2, 2);
        matrix.set(0, 0, BigDecimal.valueOf(0));
        matrix.set(0, 1, BigDecimal.valueOf(0));
        matrix.set(1, 0, BigDecimal.valueOf(0));
        matrix.set(1, 1, BigDecimal.valueOf(0));

        Matrix<BigDecimal> expectedResult = new Matrix<BigDecimal>(2, 2);
        expectedResult.set(0, 0, BigDecimal.valueOf(1));
        expectedResult.set(0, 1, BigDecimal.valueOf(0));
        expectedResult.set(1, 0, BigDecimal.valueOf(0));
        expectedResult.set(1, 1, BigDecimal.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expectedResult.equals(matrix));

        Assert.assertTrue(matrixSum.equals(matrix1.add(matrix2)));
        Assert.assertTrue(matrixProduct.equals(matrix1.multiply(matrix2)));
        Assert.assertTrue(matrixSubtract.equals(matrix1.subtract(matrix2)));

        Assert.assertEquals(2, matrix1.getRows());
        Assert.assertEquals(2, matrix1.getCols());
    }

    // test BigInteger
    @Test
    public void testMatrixBigInteger() {
        Matrix<BigInteger> matrix1 = new Matrix<BigInteger>(2, 2);
        matrix1.set(0, 0, BigInteger.valueOf(0));
        matrix1.set(0, 1, BigInteger.valueOf(0));

        matrix1.set(1, 0, BigInteger.valueOf(1));
        matrix1.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(2, 2);
        matrix2.set(0, 0, BigInteger.valueOf(1));
        matrix2.set(0, 1, BigInteger.valueOf(1));
        matrix2.set(1, 0, BigInteger.valueOf(0));
        matrix2.set(1, 1, BigInteger.valueOf(0));

        Matrix<BigInteger> matrixSum = new Matrix<BigInteger>(2, 2);
        matrixSum.set(0, 0, BigInteger.valueOf(1));
        matrixSum.set(0, 1, BigInteger.valueOf(1));
        matrixSum.set(1, 0, BigInteger.valueOf(1));
        matrixSum.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigInteger> matrixProduct = new Matrix<BigInteger>(2, 2);
        matrixProduct.set(0, 0, BigInteger.valueOf(0));
        matrixProduct.set(0, 1, BigInteger.valueOf(0));
        matrixProduct.set(1, 0, BigInteger.valueOf(1));
        matrixProduct.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigInteger> matrixSubtract = new Matrix<BigInteger>(2, 2);
        matrixSubtract.set(0, 0, BigInteger.valueOf(-1));
        matrixSubtract.set(0, 1, BigInteger.valueOf(-1));
        matrixSubtract.set(1, 0, BigInteger.valueOf(1));
        matrixSubtract.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigDecimal> matrix = new Matrix<BigDecimal>(2, 2);
        matrix.set(0, 0, BigDecimal.valueOf(0));
        matrix.set(0, 1, BigDecimal.valueOf(0));
        matrix.set(1, 0, BigDecimal.valueOf(0));
        matrix.set(1, 1, BigDecimal.valueOf(0));

        Matrix<BigDecimal> expectedResult = new Matrix<BigDecimal>(2, 2);
        expectedResult.set(0, 0, BigDecimal.valueOf(1));
        expectedResult.set(0, 1, BigDecimal.valueOf(0));
        expectedResult.set(1, 0, BigDecimal.valueOf(0));
        expectedResult.set(1, 1, BigDecimal.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expectedResult.equals(matrix));

        Assert.assertTrue(matrixSum.equals(matrix1.add(matrix2)));
        Assert.assertTrue(matrixProduct.equals(matrix1.multiply(matrix2)));
        Assert.assertTrue(matrixSubtract.equals(matrix1.subtract(matrix2)));
    }

    // Test Long
    @Test
    public void testMatrixLong() {

        Matrix<Long> matrix1 = new Matrix<Long>(2, 2);
        matrix1.set(0, 0, Long.valueOf(0));
        matrix1.set(0, 1, Long.valueOf(0));

        matrix1.set(1, 0, Long.valueOf(1));
        matrix1.set(1, 1, Long.valueOf(1));

        Matrix<Long> matrix2 = new Matrix<Long>(2, 2);
        matrix2.set(0, 0, Long.valueOf(1));
        matrix2.set(0, 1, Long.valueOf(1));
        matrix2.set(1, 0, Long.valueOf(0));
        matrix2.set(1, 1, Long.valueOf(0));

        Matrix<Long> matrixSum = new Matrix<Long>(2, 2);
        matrixSum.set(0, 0, Long.valueOf(1));
        matrixSum.set(0, 1, Long.valueOf(1));
        matrixSum.set(1, 0, Long.valueOf(1));
        matrixSum.set(1, 1, Long.valueOf(1));

        Matrix<Long> matrixProduct = new Matrix<Long>(2, 2);
        matrixProduct.set(0, 0, Long.valueOf(0));
        matrixProduct.set(0, 1, Long.valueOf(0));
        matrixProduct.set(1, 0, Long.valueOf(1));
        matrixProduct.set(1, 1, Long.valueOf(1));

        Matrix<Long> matrixSubtract = new Matrix<Long>(2, 2);
        matrixSubtract.set(0, 0, Long.valueOf(-1));
        matrixSubtract.set(0, 1, Long.valueOf(-1));
        matrixSubtract.set(1, 0, Long.valueOf(1));
        matrixSubtract.set(1, 1, Long.valueOf(1));

        Matrix<Long> matrix = new Matrix<Long>(2, 2);
        matrix.set(0, 0, Long.valueOf(0));
        matrix.set(0, 1, Long.valueOf(0));
        matrix.set(1, 0, Long.valueOf(0));
        matrix.set(1, 1, Long.valueOf(0));

        Matrix<Long> expectedResult = new Matrix<Long>(2, 2);
        expectedResult.set(0, 0, Long.valueOf(1));
        expectedResult.set(0, 1, Long.valueOf(0));
        expectedResult.set(1, 0, Long.valueOf(0));
        expectedResult.set(1, 1, Long.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expectedResult.equals(matrix));

        Assert.assertTrue(matrixSum.equals(matrix1.add(matrix2)));
        Assert.assertTrue(matrixProduct.equals(matrix1.multiply(matrix2)));
        Assert.assertTrue(matrixSubtract.equals(matrix1.subtract(matrix2)));
    }

    // Test Double
    @Test
    public void testMatrixDouble() {
        Matrix<Double> matrix1 = new Matrix<Double>(2, 2);
        matrix1.set(0, 0, Double.valueOf(0));
        matrix1.set(0, 1, Double.valueOf(0));

        matrix1.set(1, 0, Double.valueOf(1));
        matrix1.set(1, 1, Double.valueOf(1));

        Matrix<Double> matrix2 = new Matrix<Double>(2, 2);
        matrix2.set(0, 0, Double.valueOf(1));
        matrix2.set(0, 1, Double.valueOf(1));
        matrix2.set(1, 0, Double.valueOf(0));
        matrix2.set(1, 1, Double.valueOf(0));

        Matrix<Double> matrixSum = new Matrix<Double>(2, 2);
        matrixSum.set(0, 0, Double.valueOf(1));
        matrixSum.set(0, 1, Double.valueOf(1));
        matrixSum.set(1, 0, Double.valueOf(1));
        matrixSum.set(1, 1, Double.valueOf(1));

        Matrix<Double> matrixProduct = new Matrix<Double>(2, 2);
        matrixProduct.set(0, 0, Double.valueOf(0));
        matrixProduct.set(0, 1, Double.valueOf(0));
        matrixProduct.set(1, 0, Double.valueOf(1));
        matrixProduct.set(1, 1, Double.valueOf(1));

        Matrix<Double> matrixSubtract = new Matrix<Double>(2, 2);
        matrixSubtract.set(0, 0, Double.valueOf(-1));
        matrixSubtract.set(0, 1, Double.valueOf(-1));
        matrixSubtract.set(1, 0, Double.valueOf(1));
        matrixSubtract.set(1, 1, Double.valueOf(1));

        Matrix<Double> matrix = new Matrix<Double>(2, 2);
        matrix.set(0, 0, Double.valueOf(0));
        matrix.set(0, 1, Double.valueOf(0));
        matrix.set(1, 0, Double.valueOf(0));
        matrix.set(1, 1, Double.valueOf(0));

        Matrix<Double> expectedResult = new Matrix<Double>(2, 2);
        expectedResult.set(0, 0, Double.valueOf(1));
        expectedResult.set(0, 1, Double.valueOf(0));
        expectedResult.set(1, 0, Double.valueOf(0));
        expectedResult.set(1, 1, Double.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expectedResult.equals(matrix));

        Assert.assertTrue(matrixSum.equals(matrix1.add(matrix2)));
        Assert.assertTrue(matrixProduct.equals(matrix1.multiply(matrix2)));
        Assert.assertTrue(matrixSubtract.equals(matrix1.subtract(matrix2)));
    }

    // test Float
    @Test
    public void testMatrixFloat() {

        Matrix<Float> matrix1 = new Matrix<Float>(2, 2);
        matrix1.set(0, 0, Float.valueOf(0));
        matrix1.set(0, 1, Float.valueOf(0));

        matrix1.set(1, 0, Float.valueOf(1));
        matrix1.set(1, 1, Float.valueOf(1));

        Matrix<Float> matrix2 = new Matrix<Float>(2, 2);
        matrix2.set(0, 0, Float.valueOf(1));
        matrix2.set(0, 1, Float.valueOf(1));
        matrix2.set(1, 0, Float.valueOf(0));
        matrix2.set(1, 1, Float.valueOf(0));

        Matrix<Float> matrixSum = new Matrix<Float>(2, 2);
        matrixSum.set(0, 0, Float.valueOf(1));
        matrixSum.set(0, 1, Float.valueOf(1));
        matrixSum.set(1, 0, Float.valueOf(1));
        matrixSum.set(1, 1, Float.valueOf(1));

        Matrix<Float> matrixProduct = new Matrix<Float>(2, 2);
        matrixProduct.set(0, 0, Float.valueOf(0));
        matrixProduct.set(0, 1, Float.valueOf(0));
        matrixProduct.set(1, 0, Float.valueOf(1));
        matrixProduct.set(1, 1, Float.valueOf(1));

        Matrix<Float> matrixSubtract = new Matrix<Float>(2, 2);
        matrixSubtract.set(0, 0, Float.valueOf(-1));
        matrixSubtract.set(0, 1, Float.valueOf(-1));
        matrixSubtract.set(1, 0, Float.valueOf(1));
        matrixSubtract.set(1, 1, Float.valueOf(1));

        Matrix<Float> matrix = new Matrix<Float>(2, 2);
        matrix.set(0, 0, Float.valueOf(0));
        matrix.set(0, 1, Float.valueOf(0));
        matrix.set(1, 0, Float.valueOf(0));
        matrix.set(1, 1, Float.valueOf(0));

        Matrix<Float> expectedResult = new Matrix<Float>(2, 2);
        expectedResult.set(0, 0, Float.valueOf(1));
        expectedResult.set(0, 1, Float.valueOf(0));
        expectedResult.set(1, 0, Float.valueOf(0));
        expectedResult.set(1, 1, Float.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expectedResult.equals(matrix));

        Assert.assertTrue(matrixSum.equals(matrix1.add(matrix2)));
        Assert.assertTrue(matrixProduct.equals(matrix1.multiply(matrix2)));
        Assert.assertTrue(matrixSubtract.equals(matrix1.subtract(matrix2)));
    }

    @Test
    public void testIdentityMethodBigInteger() {
        Matrix<BigInteger> matrix = new Matrix<BigInteger>(2, 2);
        matrix.set(0, 0, BigInteger.valueOf(0));
        matrix.set(0, 1, BigInteger.valueOf(0));
        matrix.set(1, 0, BigInteger.valueOf(0));
        matrix.set(1, 1, BigInteger.valueOf(0));

        Matrix<BigInteger> expectedResult = new Matrix<BigInteger>(2, 2);
        expectedResult.set(0, 0, BigInteger.valueOf(1));
        expectedResult.set(0, 1, BigInteger.valueOf(0));
        expectedResult.set(1, 0, BigInteger.valueOf(0));
        expectedResult.set(1, 1, BigInteger.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception ex) {
            fail();
        }

        assertArrayEquals(expectedResult.getRow(0), matrix.getRow(0));
        assertArrayEquals(expectedResult.getRow(1), matrix.getRow(1));
    }
    // Identity Long

    @Test
    public void testMatrixIdentityNotSquareException() throws Exception {

        Matrix<BigDecimal> matrix = new Matrix<BigDecimal>(2, 3);
        matrix.set(0, 0, BigDecimal.valueOf(0));
        matrix.set(0, 1, BigDecimal.valueOf(0));
        matrix.set(1, 0, BigDecimal.valueOf(0));
        matrix.set(1, 1, BigDecimal.valueOf(0));

        Matrix<BigDecimal> expectedResult = new Matrix<BigDecimal>(2, 2);
        expectedResult.set(0, 0, BigDecimal.valueOf(1));
        expectedResult.set(0, 1, BigDecimal.valueOf(0));
        expectedResult.set(1, 0, BigDecimal.valueOf(0));
        expectedResult.set(1, 1, BigDecimal.valueOf(1));

        try {
            matrix = matrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }
    }

}