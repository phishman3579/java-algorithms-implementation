package com.jwetherell.algorithms.data_structures.test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

    // Requirement: Matrix addition, subtraction, and multiplication should work correctly with Long values.
    @Test
    public void testMatrixLongOperations() {
        Matrix<Long> matrix1 = new Matrix<Long>(4, 3);
        matrix1.set(0, 0, Long.valueOf(14));
        matrix1.set(0, 1, Long.valueOf(9));
        matrix1.set(0, 2, Long.valueOf(3));
        matrix1.set(1, 0, Long.valueOf(2));
        matrix1.set(1, 1, Long.valueOf(11));
        matrix1.set(1, 2, Long.valueOf(15));
        matrix1.set(2, 0, Long.valueOf(0));
        matrix1.set(2, 1, Long.valueOf(12));
        matrix1.set(2, 2, Long.valueOf(17));
        matrix1.set(3, 0, Long.valueOf(5));
        matrix1.set(3, 1, Long.valueOf(2));
        matrix1.set(3, 2, Long.valueOf(3));

        Matrix<Long> matrix2 = new Matrix<Long>(3, 2);
        matrix2.set(0, 0, Long.valueOf(12));
        matrix2.set(0, 1, Long.valueOf(25));
        matrix2.set(1, 0, Long.valueOf(9));
        matrix2.set(1, 1, Long.valueOf(10));
        matrix2.set(2, 0, Long.valueOf(8));
        matrix2.set(2, 1, Long.valueOf(5));

        // Result of multiplication
        Long[][] array1 = new Long[][]{{Long.valueOf(273),Long.valueOf(455)},
                                             {Long.valueOf(243),Long.valueOf(235)},
                                             {Long.valueOf(244),Long.valueOf(205)},
                                             {Long.valueOf(102),Long.valueOf(160)}};
        Matrix<Long> result1 = new Matrix<Long>(4,2,array1);

        Matrix<Long> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Long> matrix4 = new Matrix<Long>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, Long.valueOf(counter++));

        // Result of subtraction
        Long[][] array2 = new Long[][]{{Long.valueOf(0),Long.valueOf(0)},
                                             {Long.valueOf(0),Long.valueOf(0)}};
        Matrix<Long> result2 = new Matrix<Long>(2,2,array2);

        Matrix<Long> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Long[][] array3 = new Long[][]{{Long.valueOf(0),Long.valueOf(2)},
                                             {Long.valueOf(4),Long.valueOf(6)}};
        Matrix<Long> result3 = new Matrix<Long>(2,2,array3);

        Matrix<Long> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Long> matrix7 = new Matrix<Long>(2, 2);
        matrix7.set(0, 0, Long.valueOf(1));
        matrix7.set(0, 1, Long.valueOf(2));
        matrix7.set(1, 0, Long.valueOf(3));
        matrix7.set(1, 1, Long.valueOf(4));

        Matrix<Long> matrix8 = new Matrix<Long>(2, 2);
        matrix8.set(0, 0, Long.valueOf(1));
        matrix8.set(0, 1, Long.valueOf(2));
        matrix8.set(1, 0, Long.valueOf(3));
        matrix8.set(1, 1, Long.valueOf(4));

        // Result of multiplication
        Long[][] array4 = new Long[][]{{Long.valueOf(7),Long.valueOf(10)},
                                             {Long.valueOf(15),Long.valueOf(22)}};
        Matrix<Long> result4 = new Matrix<Long>(2,2,array4);

        Matrix<Long> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    // Requirement: Matrix addition, subtraction, and multiplication should work correctly with Float values.
    @Test
    public void testMatrixFloatOperations() {
        Matrix<Float> matrix1 = new Matrix<Float>(4, 3);
        matrix1.set(0, 0, Float.valueOf(14));
        matrix1.set(0, 1, Float.valueOf(9));
        matrix1.set(0, 2, Float.valueOf(3));
        matrix1.set(1, 0, Float.valueOf(2));
        matrix1.set(1, 1, Float.valueOf(11));
        matrix1.set(1, 2, Float.valueOf(15));
        matrix1.set(2, 0, Float.valueOf(0));
        matrix1.set(2, 1, Float.valueOf(12));
        matrix1.set(2, 2, Float.valueOf(17));
        matrix1.set(3, 0, Float.valueOf(5));
        matrix1.set(3, 1, Float.valueOf(2));
        matrix1.set(3, 2, Float.valueOf(3));

        Matrix<Float> matrix2 = new Matrix<Float>(3, 2);
        matrix2.set(0, 0, Float.valueOf(12));
        matrix2.set(0, 1, Float.valueOf(25));
        matrix2.set(1, 0, Float.valueOf(9));
        matrix2.set(1, 1, Float.valueOf(10));
        matrix2.set(2, 0, Float.valueOf(8));
        matrix2.set(2, 1, Float.valueOf(5));

        // Result of multiplication
        Float[][] array1 = new Float[][]{{Float.valueOf(273),Float.valueOf(455)},
                                            {Float.valueOf(243),Float.valueOf(235)},
                                            {Float.valueOf(244),Float.valueOf(205)},
                                            {Float.valueOf(102),Float.valueOf(160)}};
        Matrix<Float> result1 = new Matrix<Float>(4,2,array1);

        Matrix<Float> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Float> matrix4 = new Matrix<Float>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, Float.valueOf(counter++));

        // Result of subtraction
        Float[][] array2 = new Float[][]{{Float.valueOf(0),Float.valueOf(0)},
                                            {Float.valueOf(0),Float.valueOf(0)}};
        Matrix<Float> result2 = new Matrix<Float>(2,2,array2);

        Matrix<Float> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Float[][] array3 = new Float[][]{{Float.valueOf(0),Float.valueOf(2)},
                                            {Float.valueOf(4),Float.valueOf(6)}};
        Matrix<Float> result3 = new Matrix<Float>(2,2,array3);

        Matrix<Float> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Float> matrix7 = new Matrix<Float>(2, 2);
        matrix7.set(0, 0, Float.valueOf(1));
        matrix7.set(0, 1, Float.valueOf(2));
        matrix7.set(1, 0, Float.valueOf(3));
        matrix7.set(1, 1, Float.valueOf(4));

        Matrix<Float> matrix8 = new Matrix<Float>(2, 2);
        matrix8.set(0, 0, Float.valueOf(1));
        matrix8.set(0, 1, Float.valueOf(2));
        matrix8.set(1, 0, Float.valueOf(3));
        matrix8.set(1, 1, Float.valueOf(4));

        // Result of multiplication
        Float[][] array4 = new Float[][]{{Float.valueOf(7),Float.valueOf(10)},
                                            {Float.valueOf(15),Float.valueOf(22)}};
        Matrix<Float> result4 = new Matrix<Float>(2,2,array4);

        Matrix<Float> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    // Requirement: Matrix addition, subtraction, and multiplication should work correctly with Double values.
    @Test
    public void testMatrixDoubleOperations() {
        Matrix<Double> matrix1 = new Matrix<Double>(4, 3);
        matrix1.set(0, 0, Double.valueOf(14));
        matrix1.set(0, 1, Double.valueOf(9));
        matrix1.set(0, 2, Double.valueOf(3));
        matrix1.set(1, 0, Double.valueOf(2));
        matrix1.set(1, 1, Double.valueOf(11));
        matrix1.set(1, 2, Double.valueOf(15));
        matrix1.set(2, 0, Double.valueOf(0));
        matrix1.set(2, 1, Double.valueOf(12));
        matrix1.set(2, 2, Double.valueOf(17));
        matrix1.set(3, 0, Double.valueOf(5));
        matrix1.set(3, 1, Double.valueOf(2));
        matrix1.set(3, 2, Double.valueOf(3));

        Matrix<Double> matrix2 = new Matrix<Double>(3, 2);
        matrix2.set(0, 0, Double.valueOf(12));
        matrix2.set(0, 1, Double.valueOf(25));
        matrix2.set(1, 0, Double.valueOf(9));
        matrix2.set(1, 1, Double.valueOf(10));
        matrix2.set(2, 0, Double.valueOf(8));
        matrix2.set(2, 1, Double.valueOf(5));

        // Result of multiplication
        Double[][] array1 = new Double[][]{{Double.valueOf(273),Double.valueOf(455)},
                                            {Double.valueOf(243),Double.valueOf(235)},
                                            {Double.valueOf(244),Double.valueOf(205)},
                                            {Double.valueOf(102),Double.valueOf(160)}};
        Matrix<Double> result1 = new Matrix<Double>(4,2,array1);

        Matrix<Double> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Double> matrix4 = new Matrix<Double>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, Double.valueOf(counter++));

        // Result of subtraction
        Double[][] array2 = new Double[][]{{Double.valueOf(0),Double.valueOf(0)},
                                            {Double.valueOf(0),Double.valueOf(0)}};
        Matrix<Double> result2 = new Matrix<Double>(2,2,array2);

        Matrix<Double> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Double[][] array3 = new Double[][]{{Double.valueOf(0),Double.valueOf(2)},
                                            {Double.valueOf(4),Double.valueOf(6)}};
        Matrix<Double> result3 = new Matrix<Double>(2,2,array3);

        Matrix<Double> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Double> matrix7 = new Matrix<Double>(2, 2);
        matrix7.set(0, 0, Double.valueOf(1));
        matrix7.set(0, 1, Double.valueOf(2));
        matrix7.set(1, 0, Double.valueOf(3));
        matrix7.set(1, 1, Double.valueOf(4));

        Matrix<Double> matrix8 = new Matrix<Double>(2, 2);
        matrix8.set(0, 0, Double.valueOf(1));
        matrix8.set(0, 1, Double.valueOf(2));
        matrix8.set(1, 0, Double.valueOf(3));
        matrix8.set(1, 1, Double.valueOf(4));

        // Result of multiplication
        Double[][] array4 = new Double[][]{{Double.valueOf(7),Double.valueOf(10)},
                                            {Double.valueOf(15),Double.valueOf(22)}};
        Matrix<Double> result4 = new Matrix<Double>(2,2,array4);

        Matrix<Double> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    // Requirement: Matrix addition, subtraction, and multiplication should work correctly with BigDecimal values.
    @Test
    public void testMatrixBigDecimalOperations() {
        Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(4, 3);
        matrix1.set(0, 0, BigDecimal.valueOf(14));
        matrix1.set(0, 1, BigDecimal.valueOf(9));
        matrix1.set(0, 2, BigDecimal.valueOf(3));
        matrix1.set(1, 0, BigDecimal.valueOf(2));
        matrix1.set(1, 1, BigDecimal.valueOf(11));
        matrix1.set(1, 2, BigDecimal.valueOf(15));
        matrix1.set(2, 0, BigDecimal.valueOf(0));
        matrix1.set(2, 1, BigDecimal.valueOf(12));
        matrix1.set(2, 2, BigDecimal.valueOf(17));
        matrix1.set(3, 0, BigDecimal.valueOf(5));
        matrix1.set(3, 1, BigDecimal.valueOf(2));
        matrix1.set(3, 2, BigDecimal.valueOf(3));

        Matrix<BigDecimal> matrix2 = new Matrix<BigDecimal>(3, 2);
        matrix2.set(0, 0, BigDecimal.valueOf(12));
        matrix2.set(0, 1, BigDecimal.valueOf(25));
        matrix2.set(1, 0, BigDecimal.valueOf(9));
        matrix2.set(1, 1, BigDecimal.valueOf(10));
        matrix2.set(2, 0, BigDecimal.valueOf(8));
        matrix2.set(2, 1, BigDecimal.valueOf(5));

        // Result of multiplication
        BigDecimal[][] array1 = new BigDecimal[][]{{BigDecimal.valueOf(273),BigDecimal.valueOf(455)},
                                            {BigDecimal.valueOf(243),BigDecimal.valueOf(235)},
                                            {BigDecimal.valueOf(244),BigDecimal.valueOf(205)},
                                            {BigDecimal.valueOf(102),BigDecimal.valueOf(160)}};
        Matrix<BigDecimal> result1 = new Matrix<BigDecimal>(4,2,array1);

        Matrix<BigDecimal> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<BigDecimal> matrix4 = new Matrix<BigDecimal>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, BigDecimal.valueOf(counter++));

        // Result of subtraction
        BigDecimal[][] array2 = new BigDecimal[][]{{BigDecimal.valueOf(0),BigDecimal.valueOf(0)},
                                            {BigDecimal.valueOf(0),BigDecimal.valueOf(0)}};
        Matrix<BigDecimal> result2 = new Matrix<BigDecimal>(2,2,array2);

        Matrix<BigDecimal> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        BigDecimal[][] array3 = new BigDecimal[][]{{BigDecimal.valueOf(0),BigDecimal.valueOf(2)},
                                            {BigDecimal.valueOf(4),BigDecimal.valueOf(6)}};
        Matrix<BigDecimal> result3 = new Matrix<BigDecimal>(2,2,array3);

        Matrix<BigDecimal> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<BigDecimal> matrix7 = new Matrix<BigDecimal>(2, 2);
        matrix7.set(0, 0, BigDecimal.valueOf(1));
        matrix7.set(0, 1, BigDecimal.valueOf(2));
        matrix7.set(1, 0, BigDecimal.valueOf(3));
        matrix7.set(1, 1, BigDecimal.valueOf(4));

        Matrix<BigDecimal> matrix8 = new Matrix<BigDecimal>(2, 2);
        matrix8.set(0, 0, BigDecimal.valueOf(1));
        matrix8.set(0, 1, BigDecimal.valueOf(2));
        matrix8.set(1, 0, BigDecimal.valueOf(3));
        matrix8.set(1, 1, BigDecimal.valueOf(4));

        // Result of multiplication
        BigDecimal[][] array4 = new BigDecimal[][]{{BigDecimal.valueOf(7),BigDecimal.valueOf(10)},
                                            {BigDecimal.valueOf(15),BigDecimal.valueOf(22)}};
        Matrix<BigDecimal> result4 = new Matrix<BigDecimal>(2,2,array4);

        Matrix<BigDecimal> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    // Requirement: Matrix addition, subtraction, and multiplication should work correctly with BigInteger values.
    @Test
    public void testMatrixBigIntegerOperations() {
        Matrix<BigInteger> matrix1 = new Matrix<BigInteger>(4, 3);
        matrix1.set(0, 0, BigInteger.valueOf(14));
        matrix1.set(0, 1, BigInteger.valueOf(9));
        matrix1.set(0, 2, BigInteger.valueOf(3));
        matrix1.set(1, 0, BigInteger.valueOf(2));
        matrix1.set(1, 1, BigInteger.valueOf(11));
        matrix1.set(1, 2, BigInteger.valueOf(15));
        matrix1.set(2, 0, BigInteger.valueOf(0));
        matrix1.set(2, 1, BigInteger.valueOf(12));
        matrix1.set(2, 2, BigInteger.valueOf(17));
        matrix1.set(3, 0, BigInteger.valueOf(5));
        matrix1.set(3, 1, BigInteger.valueOf(2));
        matrix1.set(3, 2, BigInteger.valueOf(3));

        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(3, 2);
        matrix2.set(0, 0, BigInteger.valueOf(12));
        matrix2.set(0, 1, BigInteger.valueOf(25));
        matrix2.set(1, 0, BigInteger.valueOf(9));
        matrix2.set(1, 1, BigInteger.valueOf(10));
        matrix2.set(2, 0, BigInteger.valueOf(8));
        matrix2.set(2, 1, BigInteger.valueOf(5));

        // Result of multiplication
        BigInteger[][] array1 = new BigInteger[][]{{BigInteger.valueOf(273),BigInteger.valueOf(455)},
                                            {BigInteger.valueOf(243),BigInteger.valueOf(235)},
                                            {BigInteger.valueOf(244),BigInteger.valueOf(205)},
                                            {BigInteger.valueOf(102),BigInteger.valueOf(160)}};
        Matrix<BigInteger> result1 = new Matrix<BigInteger>(4,2,array1);

        Matrix<BigInteger> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<BigInteger> matrix4 = new Matrix<BigInteger>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, BigInteger.valueOf(counter++));

        // Result of subtraction
        BigInteger[][] array2 = new BigInteger[][]{{BigInteger.valueOf(0),BigInteger.valueOf(0)},
                                            {BigInteger.valueOf(0),BigInteger.valueOf(0)}};
        Matrix<BigInteger> result2 = new Matrix<BigInteger>(2,2,array2);

        Matrix<BigInteger> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        BigInteger[][] array3 = new BigInteger[][]{{BigInteger.valueOf(0),BigInteger.valueOf(2)},
                                            {BigInteger.valueOf(4),BigInteger.valueOf(6)}};
        Matrix<BigInteger> result3 = new Matrix<BigInteger>(2,2,array3);

        Matrix<BigInteger> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<BigInteger> matrix7 = new Matrix<BigInteger>(2, 2);
        matrix7.set(0, 0, BigInteger.valueOf(1));
        matrix7.set(0, 1, BigInteger.valueOf(2));
        matrix7.set(1, 0, BigInteger.valueOf(3));
        matrix7.set(1, 1, BigInteger.valueOf(4));

        Matrix<BigInteger> matrix8 = new Matrix<BigInteger>(2, 2);
        matrix8.set(0, 0, BigInteger.valueOf(1));
        matrix8.set(0, 1, BigInteger.valueOf(2));
        matrix8.set(1, 0, BigInteger.valueOf(3));
        matrix8.set(1, 1, BigInteger.valueOf(4));

        // Result of multiplication
        BigInteger[][] array4 = new BigInteger[][]{{BigInteger.valueOf(7),BigInteger.valueOf(10)},
                                            {BigInteger.valueOf(15),BigInteger.valueOf(22)}};
        Matrix<BigInteger> result4 = new Matrix<BigInteger>(2,2,array4);

        Matrix<BigInteger> matrix9 = matrix7.multiply(matrix8);
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

    // Requirement: Matrix addition should work correctly with Long values.
    @Test
    public void testAddLong() {
        Matrix<Long> matrix = new Matrix<Long>(2, 2);
        matrix.set(0, 0, Long.valueOf(1));
        matrix.set(0, 1, Long.valueOf(2));
        matrix.set(1, 0, Long.valueOf(3));
        matrix.set(1, 1, Long.valueOf(4));

        Matrix<Long> actualResult = matrix.add(matrix);

        Matrix<Long> expectedResult = new Matrix<Long>(2, 2);
        expectedResult.set(0, 0, Long.valueOf(2));
        expectedResult.set(0, 1, Long.valueOf(4));
        expectedResult.set(1, 0, Long.valueOf(6));
        expectedResult.set(1, 1, Long.valueOf(8));

        assertArrayEquals(expectedResult.getRow(0), actualResult.getRow(0));
        assertArrayEquals(expectedResult.getRow(1), actualResult.getRow(1));
    }

    // Requirement: Matrix addition should work correctly with BigInteger values.
    @Test
    public void testAddBigInteger() {
        Matrix<BigInteger> matrix = new Matrix<BigInteger>(2, 2);
        matrix.set(0, 0, BigInteger.valueOf(1));
        matrix.set(0, 1, BigInteger.valueOf(2));
        matrix.set(1, 0, BigInteger.valueOf(3));
        matrix.set(1, 1, BigInteger.valueOf(4));

        Matrix<BigInteger> actualResult = matrix.add(matrix);

        Matrix<BigInteger> expectedResult = new Matrix<BigInteger>(2, 2);
        expectedResult.set(0, 0, BigInteger.valueOf(2));
        expectedResult.set(0, 1, BigInteger.valueOf(4));
        expectedResult.set(1, 0, BigInteger.valueOf(6));
        expectedResult.set(1, 1, BigInteger.valueOf(8));

        assertArrayEquals(expectedResult.getRow(0), actualResult.getRow(0));
        assertArrayEquals(expectedResult.getRow(1), actualResult.getRow(1));
    }

    // Requirement: Matrix addition should work correctly with Float values.
    @Test
    public void testAddFloat() {
        Matrix<Float> matrix = new Matrix<Float>(1, 1);
        matrix.set(0, 0, new Float(1.1));

        Matrix<Float> actual = matrix.add(matrix);

        Matrix<Float> expected = new Matrix<Float>(1, 1);
        expected.set(0, 0, new Float(2.2));

        Matrix<Float> wrong1 = new Matrix<Float>(1, 1);
        wrong1.set(0, 0, new Float(2.1));

        Matrix<Float> wrong2 = new Matrix<Float>(1, 1);
        wrong2.set(0, 0, new Float(2.3));

        assertTrue(Float.compare(actual.get(0, 0), expected.get(0, 0)) == 0);
        assertTrue(Float.compare(actual.get(0, 0), wrong1.get(0, 0)) != 0);
        assertTrue(Float.compare(actual.get(0, 0), wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix addition should work correctly with Double values.
    @Test
    public void testAddDouble() {
        Matrix<Double> matrix = new Matrix<Double>(1, 1);
        matrix.set(0, 0, new Double(1.1));

        Matrix<Double> actual = matrix.add(matrix);

        Matrix<Double> expected = new Matrix<Double>(1, 1);
        expected.set(0, 0, new Double(2.2));

        Matrix<Double> wrong1 = new Matrix<Double>(1, 1);
        wrong1.set(0, 0, new Double(2.1));

        Matrix<Double> wrong2 = new Matrix<Double>(1, 1);
        wrong2.set(0, 0, new Double(2.3));

        assertTrue(Double.compare(actual.get(0, 0), expected.get(0, 0)) == 0);
        assertTrue(Double.compare(actual.get(0, 0), wrong1.get(0, 0)) != 0);
        assertTrue(Double.compare(actual.get(0, 0), wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix addition should work correctly with BigDecimal values.
    @Test
    public void testAddBigDecimal() {
        Matrix<BigDecimal> matrix = new Matrix<BigDecimal>(1, 1);
        matrix.set(0, 0, new BigDecimal(1.1));

        Matrix<BigDecimal> actual = matrix.add(matrix);

        Matrix<BigDecimal> expected = new Matrix<BigDecimal>(1, 1);
        expected.set(0, 0, new BigDecimal(2.2));

        Matrix<BigDecimal> wrong1 = new Matrix<BigDecimal>(1, 1);
        wrong1.set(0, 0, new BigDecimal(2.1));

        Matrix<BigDecimal> wrong2 = new Matrix<BigDecimal>(1, 1);
        wrong2.set(0, 0, new BigDecimal(2.3));

        BigDecimal diff = actual.get(0, 0).subtract(expected.get(0, 0)).abs();
        assertTrue((diff.compareTo(new BigDecimal(0.00001)) < 0));
        assertTrue(actual.get(0, 0).compareTo(wrong1.get(0, 0)) != 0);
        assertTrue(actual.get(0, 0).compareTo(wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix subtraction should work correctly with Long values.
    @Test
    public void testSubtractLong() {
        Matrix<Long> matrix1 = new Matrix<Long>(2, 2);
        matrix1.set(0, 0, Long.valueOf(1));
        matrix1.set(0, 1, Long.valueOf(2));
        matrix1.set(1, 0, Long.valueOf(3));
        matrix1.set(1, 1, Long.valueOf(4));

        Matrix<Long> matrix2 = new Matrix<Long>(2, 2);
        matrix2.set(0, 0, Long.valueOf(1*2));
        matrix2.set(0, 1, Long.valueOf(2*2));
        matrix2.set(1, 0, Long.valueOf(3*2));
        matrix2.set(1, 1, Long.valueOf(4*2));

        Matrix<Long> actualResult = matrix1.subtract(matrix2);

        Matrix<Long> expectedResult = new Matrix<Long>(2, 2);
        expectedResult.set(0, 0, Long.valueOf(-1));
        expectedResult.set(0, 1, Long.valueOf(-2));
        expectedResult.set(1, 0, Long.valueOf(-3));
        expectedResult.set(1, 1, Long.valueOf(-4));

        assertArrayEquals(expectedResult.getRow(0), actualResult.getRow(0));
        assertArrayEquals(expectedResult.getRow(1), actualResult.getRow(1));
    }

    // Requirement: Matrix subtraction should work correctly with BigInteger values.
    @Test
    public void testSubtractBigInteger() {
        Matrix<BigInteger> matrix1 = new Matrix<BigInteger>(2, 2);
        matrix1.set(0, 0, BigInteger.valueOf(1));
        matrix1.set(0, 1, BigInteger.valueOf(2));
        matrix1.set(1, 0, BigInteger.valueOf(3));
        matrix1.set(1, 1, BigInteger.valueOf(4));

        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(2, 2);
        matrix2.set(0, 0, BigInteger.valueOf(1*2));
        matrix2.set(0, 1, BigInteger.valueOf(2*2));
        matrix2.set(1, 0, BigInteger.valueOf(3*2));
        matrix2.set(1, 1, BigInteger.valueOf(4*2));

        Matrix<BigInteger> actualResult = matrix1.subtract(matrix2);

        Matrix<BigInteger> expectedResult = new Matrix<BigInteger>(2, 2);
        expectedResult.set(0, 0, BigInteger.valueOf(-1));
        expectedResult.set(0, 1, BigInteger.valueOf(-2));
        expectedResult.set(1, 0, BigInteger.valueOf(-3));
        expectedResult.set(1, 1, BigInteger.valueOf(-4));

        assertArrayEquals(expectedResult.getRow(0), actualResult.getRow(0));
        assertArrayEquals(expectedResult.getRow(1), actualResult.getRow(1));
    }

    // Requirement: Matrix subtraction should work correctly with Float values.
    @Test
    public void testSubtractFloat() {
        Matrix<Float> matrix1 = new Matrix<Float>(1, 1);
        matrix1.set(0, 0, new Float(1.1));

        Matrix<Float> matrix2 = new Matrix<Float>(1, 1);
        matrix2.set(0, 0, new Float(1.1*2));

        Matrix<Float> actual = matrix1.subtract(matrix2);

        Matrix<Float> expected = new Matrix<Float>(1, 1);
        expected.set(0, 0, new Float(-1.1));

        Matrix<Float> wrong1 = new Matrix<Float>(1, 1);
        wrong1.set(0, 0, new Float(-1.05));

        Matrix<Float> wrong2 = new Matrix<Float>(1, 1);
        wrong2.set(0, 0, new Float(1.15));

        assertTrue(Math.abs(actual.get(0, 0) - expected.get(0, 0)) < 0.00001);
        assertTrue(Float.compare(actual.get(0, 0), wrong1.get(0, 0)) != 0);
        assertTrue(Float.compare(actual.get(0, 0), wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix subtraction should work correctly with Double values.
    @Test
    public void testSubtractDouble() {
        Matrix<Double> matrix1 = new Matrix<Double>(1, 1);
        matrix1.set(0, 0, new Double(1.1));

        Matrix<Double> matrix2 = new Matrix<Double>(1, 1);
        matrix2.set(0, 0, new Double(1.1*2));

        Matrix<Double> actual = matrix1.subtract(matrix2);

        Matrix<Double> expected = new Matrix<Double>(1, 1);
        expected.set(0, 0, new Double(-1.1));

        Matrix<Double> wrong1 = new Matrix<Double>(1, 1);
        wrong1.set(0, 0, new Double(-1.05));

        Matrix<Double> wrong2 = new Matrix<Double>(1, 1);
        wrong2.set(0, 0, new Double(1.15));

        assertTrue(Math.abs(actual.get(0, 0) - expected.get(0, 0)) < 0.00001);
        assertTrue(Double.compare(actual.get(0, 0), wrong1.get(0, 0)) != 0);
        assertTrue(Double.compare(actual.get(0, 0), wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix subtraction should work correctly with BigDecimal values.
    @Test
    public void testSubtractBigDecimal() {
        Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(1, 1);
        matrix1.set(0, 0, new BigDecimal(1.1));

        Matrix<BigDecimal> matrix2 = new Matrix<BigDecimal>(1, 1);
        matrix2.set(0, 0, new BigDecimal(1.1*2));

        Matrix<BigDecimal> actual = matrix1.subtract(matrix2);

        Matrix<BigDecimal> expected = new Matrix<BigDecimal>(1, 1);
        expected.set(0, 0, new BigDecimal(-1.1));

        Matrix<BigDecimal> wrong1 = new Matrix<BigDecimal>(1, 1);
        wrong1.set(0, 0, new BigDecimal(-1.05));

        Matrix<BigDecimal> wrong2 = new Matrix<BigDecimal>(1, 1);
        wrong2.set(0, 0, new BigDecimal(1.15));

        BigDecimal diff = actual.get(0, 0).subtract(expected.get(0, 0)).abs();
        assertTrue((diff.compareTo(new BigDecimal(0.00001)) < 0));
        assertTrue(actual.get(0, 0).compareTo(wrong1.get(0, 0)) != 0);
        assertTrue(actual.get(0, 0).compareTo(wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix multiplication should work correctly with Long values.
    @Test
    public void testMultiplyLong() {
        Matrix<Long> matrix = new Matrix<Long>(1, 1);
        matrix.set(0, 0, new Long(2));

        Matrix<Long> actual = matrix.multiply(matrix);

        Matrix<Long> expected = new Matrix<Long>(1, 1);
        expected.set(0, 0, new Long(4));

        Matrix<Long> wrong = new Matrix<Long>(1, 1);
        wrong.set(0, 0, new Long(3));

        assertTrue(Long.compare(actual.get(0, 0), expected.get(0, 0)) == 0);
        assertTrue(Long.compare(actual.get(0, 0), wrong.get(0, 0)) != 0);
    }

    // Requirement: Matrix multiplication should work correctly with BigInteger values.
    @Test
    public void testMultiplyBigInteger() {
        Matrix<BigInteger> matrix = new Matrix<BigInteger>(1, 1);
        matrix.set(0, 0, BigInteger.valueOf(2));

        Matrix<BigInteger> actual = matrix.multiply(matrix);

        Matrix<BigInteger> expected = new Matrix<BigInteger>(1, 1);
        expected.set(0, 0, BigInteger.valueOf(4));

        Matrix<BigInteger> wrong = new Matrix<BigInteger>(1, 1);
        wrong.set(0, 0, BigInteger.valueOf(3));

        assertTrue(actual.get(0, 0).compareTo(expected.get(0, 0)) == 0);
        assertTrue(actual.get(0, 0).compareTo(wrong.get(0, 0)) != 0);
    }

    // Requirement: Matrix multiplication should work correctly with Float values
    @Test
    public void testMultiplyFloat() {
        Matrix<Float> matrix = new Matrix<Float>(1, 1);
        matrix.set(0, 0, new Float(1.1));

        Matrix<Float> actual = matrix.multiply(matrix);

        Matrix<Float> expected = new Matrix<Float>(1, 1);
        expected.set(0, 0, new Float(1.21));

        Matrix<Float> wrong1 = new Matrix<Float>(1, 1);
        wrong1.set(0, 0, new Float(1.2));

        Matrix<Float> wrong2 = new Matrix<Float>(1, 1);
        wrong2.set(0, 0, new Float(1.22));

        assertTrue(Math.abs(actual.get(0, 0) - expected.get(0, 0)) < 0.00001);
        assertTrue(Float.compare(actual.get(0, 0), wrong1.get(0, 0)) != 0);
        assertTrue(Float.compare(actual.get(0, 0), wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix multiplication should work correctly with Double values
    @Test
    public void testMultiplyDouble() {
        Matrix<Double> matrix = new Matrix<Double>(1, 1);
        matrix.set(0, 0, new Double(1.1));

        Matrix<Double> actual = matrix.multiply(matrix);

        Matrix<Double> expected = new Matrix<Double>(1, 1);
        expected.set(0, 0, new Double(1.21));

        Matrix<Double> wrong1 = new Matrix<Double>(1, 1);
        wrong1.set(0, 0, new Double(1.2));

        Matrix<Double> wrong2 = new Matrix<Double>(1, 1);
        wrong2.set(0, 0, new Double(1.22));

        assertTrue(Math.abs(actual.get(0, 0) - expected.get(0, 0)) < 0.00001);
        assertTrue(Double.compare(actual.get(0, 0), wrong1.get(0, 0)) != 0);
        assertTrue(Double.compare(actual.get(0, 0), wrong2.get(0, 0)) != 0);
    }

    // Requirement: Matrix multiplication should work correctly with BigDecimal values
    @Test
    public void testMultiplyBigDecimal() {
        Matrix<BigDecimal> matrix = new Matrix<BigDecimal>(1, 1);
        matrix.set(0, 0, new BigDecimal(1.1));

        Matrix<BigDecimal> actual = matrix.multiply(matrix);

        Matrix<BigDecimal> expected = new Matrix<BigDecimal>(1, 1);
        expected.set(0, 0, new BigDecimal(1.21));

        Matrix<BigDecimal> wrong1 = new Matrix<BigDecimal>(1, 1);
        wrong1.set(0, 0, new BigDecimal(1.2));

        Matrix<BigDecimal> wrong2 = new Matrix<BigDecimal>(1, 1);
        wrong2.set(0, 0, new BigDecimal(1.22));

        BigDecimal diff = actual.get(0, 0).subtract(expected.get(0, 0)).abs();
        assertTrue((diff.compareTo(new BigDecimal(0.00001)) < 0));
        assertTrue(actual.get(0, 0).compareTo(wrong1.get(0, 0)) != 0);
        assertTrue(actual.get(0, 0).compareTo(wrong2.get(0, 0)) != 0);
    }
}
