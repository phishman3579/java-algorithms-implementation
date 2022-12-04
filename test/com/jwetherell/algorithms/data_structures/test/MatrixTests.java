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
    public void testMatrixManipulations() throws Exception {
        Matrix<Integer> matrix1 = new Matrix<Integer>(1, 1);
        matrix1.set(0, 0, 2);

        Matrix<Integer> matrix2 = new Matrix<Integer>(1, 1);
        matrix2.set(0, 0, 1);

        Matrix<Integer> matrix3 = new Matrix<Integer>(2, 2);
        matrix3.set(0, 0, 0);
        matrix3.set(0, 1, 1);
        matrix3.set(1, 0, 2);
        matrix3.set(1, 1, 3);

        Matrix<Integer> matrix4 = new Matrix<Integer>(2, 3);
        matrix4.set(0, 0, 0);
        matrix4.set(0, 1, 1);
        matrix4.set(0, 2, 2);
        matrix4.set(1, 0, 3);
        matrix4.set(1, 1, 4);

        Matrix<Integer> invalidProduct = matrix2.multiply(matrix3);
        Matrix<Integer> invalidSum = matrix2.add(matrix3);
        Matrix<Integer> invalidSub = matrix2.subtract(matrix3);
        assertNull(invalidProduct.get(0, 0));
        assertNull(invalidSum.get(0, 0));
        assertNull(invalidSub.get(0, 0));
        assertFalse(matrix4.equals(matrix3));
        assertFalse(matrix1.equals(null));
        assertFalse(matrix1.equals(new Integer(2)));
        assertFalse(matrix2.equals(matrix1));

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

        assertEquals(expectedResult, matrix.identity());
        matrix.copy(matrix3);
        assertEquals(matrix3, matrix);
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

        // Result of multiplication
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
                matrix4.set(r, c, toBigDecimal(counter++));

        // Result of subtraction
        BigDecimal[][] array2 = new BigDecimal[][]{{BigDecimal.ZERO,BigDecimal.ZERO},
                {BigDecimal.ZERO,BigDecimal.ZERO}};
        Matrix<BigDecimal> result2 = new Matrix<BigDecimal>(2,2,array2);

        Matrix<BigDecimal> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
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

        // Result of multiplication
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

        // Result of multiplication
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

        // Result of subtraction
        BigInteger[][] array2 = new BigInteger[][]{{BigInteger.ZERO,BigInteger.ZERO},
                {BigInteger.ZERO,BigInteger.ZERO}};
        Matrix<BigInteger> result2 = new Matrix<BigInteger>(2,2,array2);

        Matrix<BigInteger> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
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

        // Result of multiplication
        BigInteger[][] array4 = new BigInteger[][]{{toBigInteger(7),toBigInteger(10)},
                {toBigInteger(15),toBigInteger(22)}};
        Matrix<BigInteger> result4 = new Matrix<BigInteger>(2,2,array4);

        Matrix<BigInteger> matrix9 = matrix7.multiply(matrix8);
        assertTrue("Matrix multiplication error. matrix9="+matrix9+" result4"+result4, matrix9.equals(result4));
    }

    @Test
    public void testLongMatrix() {
        Matrix<Long> matrix1 = new Matrix<Long>(4, 3);
        matrix1.set(0, 0, toLong(14));
        matrix1.set(0, 1, toLong(9));
        matrix1.set(0, 2, toLong(3));
        matrix1.set(1, 0, toLong(2));
        matrix1.set(1, 1, toLong(11));
        matrix1.set(1, 2, toLong(15));
        matrix1.set(2, 0, toLong(0));
        matrix1.set(2, 1, toLong(12));
        matrix1.set(2, 2, toLong(17));
        matrix1.set(3, 0, toLong(5));
        matrix1.set(3, 1, toLong(2));
        matrix1.set(3, 2, toLong(3));

        Matrix<Long> matrix2 = new Matrix<Long>(3, 2);
        matrix2.set(0, 0, toLong(12));
        matrix2.set(0, 1, toLong(25));
        matrix2.set(1, 0, toLong(9));
        matrix2.set(1, 1, toLong(10));
        matrix2.set(2, 0, toLong(8));
        matrix2.set(2, 1, toLong(5));

        // Result of multiplication
        Long[][] array1 = new Long[][]{{toLong(273),toLong(455)},
                {toLong(243),toLong(235)},
                {toLong(244),toLong(205)},
                {toLong(102),toLong(160)}};
        Matrix<Long> result1 = new Matrix<Long>(4,2,array1);

        Matrix<Long> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Long> matrix4 = new Matrix<Long>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, toLong(counter++));

        // Result of subtraction
        Long[][] array2 = new Long[][]{{0l,0l},
                {0l,0l}};
        Matrix<Long> result2 = new Matrix<Long>(2,2,array2);

        Matrix<Long> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Long[][] array3 = new Long[][]{{0l,toLong(2)},
                {toLong(4),toLong(6)}};
        Matrix<Long> result3 = new Matrix<Long>(2,2,array3);

        Matrix<Long> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Long> matrix7 = new Matrix<Long>(2, 2);
        matrix7.set(0, 0, toLong(1));
        matrix7.set(0, 1, toLong(2));
        matrix7.set(1, 0, toLong(3));
        matrix7.set(1, 1, toLong(4));

        Matrix<Long> matrix8 = new Matrix<Long>(2, 2);
        matrix8.set(0, 0, toLong(1));
        matrix8.set(0, 1, toLong(2));
        matrix8.set(1, 0, toLong(3));
        matrix8.set(1, 1, toLong(4));

        // Result of multiplication
        Long[][] array4 = new Long[][]{{toLong(7),toLong(10)},
                {toLong(15),toLong(22)}};
        Matrix<Long> result4 = new Matrix<Long>(2,2,array4);

        Matrix<Long> matrix9 = matrix7.multiply(matrix8);
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

        // Result of multiplication
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

    @Test
    public void testFloatMatrix() {
        Matrix<Float> matrix1 = new Matrix<Float>(4, 3);
        matrix1.set(0, 0, toFloat(14));
        matrix1.set(0, 1, toFloat(9));
        matrix1.set(0, 2, toFloat(3));
        matrix1.set(1, 0, toFloat(2));
        matrix1.set(1, 1, toFloat(11));
        matrix1.set(1, 2, toFloat(15));
        matrix1.set(2, 0, toFloat(0));
        matrix1.set(2, 1, toFloat(12));
        matrix1.set(2, 2, toFloat(17));
        matrix1.set(3, 0, toFloat(5));
        matrix1.set(3, 1, toFloat(2));
        matrix1.set(3, 2, toFloat(3));

        Matrix<Float> matrix2 = new Matrix<Float>(3, 2);
        matrix2.set(0, 0, toFloat(12));
        matrix2.set(0, 1, toFloat(25));
        matrix2.set(1, 0, toFloat(9));
        matrix2.set(1, 1, toFloat(10));
        matrix2.set(2, 0, toFloat(8));
        matrix2.set(2, 1, toFloat(5));

        // Result of multiplication
        Float[][] array1 = new Float[][]{{toFloat(273),toFloat(455)},
                {toFloat(243),toFloat(235)},
                {toFloat(244),toFloat(205)},
                {toFloat(102),toFloat(160)}};
        Matrix<Float> result1 = new Matrix<Float>(4,2,array1);

        Matrix<Float> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        int rows = 2;
        int cols = 2;
        int counter = 0;
        Matrix<Float> matrix4 = new Matrix<Float>(rows, cols);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                matrix4.set(r, c, toFloat(counter++));

        // Result of subtraction
        Float[][] array2 = new Float[][]{{0f,0f},
                {0f,0f}};
        Matrix<Float> result2 = new Matrix<Float>(2,2,array2);

        Matrix<Float> matrix5 = matrix4.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix5="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Float[][] array3 = new Float[][]{{0f,toFloat(2)},
                {toFloat(4),toFloat(6)}};
        Matrix<Float> result3 = new Matrix<Float>(2,2,array3);

        Matrix<Float> matrix6 = matrix4.add(matrix4);
        assertTrue("Matrix addition error. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));

        Matrix<Float> matrix7 = new Matrix<Float>(2, 2);
        matrix7.set(0, 0, toFloat(1));
        matrix7.set(0, 1, toFloat(2));
        matrix7.set(1, 0, toFloat(3));
        matrix7.set(1, 1, toFloat(4));

        Matrix<Float> matrix8 = new Matrix<Float>(2, 2);
        matrix8.set(0, 0, toFloat(1));
        matrix8.set(0, 1, toFloat(2));
        matrix8.set(1, 0, toFloat(3));
        matrix8.set(1, 1, toFloat(4));

        // Result of multiplication
        Float[][] array4 = new Float[][]{{toFloat(7),toFloat(10)},
                {toFloat(15),toFloat(22)}};
        Matrix<Float> result4 = new Matrix<Float>(2,2,array4);

        Matrix<Float> matrix9 = matrix7.multiply(matrix8);
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
