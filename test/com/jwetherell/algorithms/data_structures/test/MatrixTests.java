package com.jwetherell.algorithms.data_structures.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
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

        assertTrue(actual.get(0, 0).compareTo(expected.get(0, 0)) == 0);
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
}