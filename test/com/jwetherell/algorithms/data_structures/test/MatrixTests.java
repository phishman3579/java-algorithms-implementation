package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    
    @Test(expected=NullPointerException.class)
    public void testMatrixMethod2() {
        int counter = 0;
        Matrix<Integer> matrix1 = new Matrix<Integer>(3, 2);
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 2; c++)
                matrix1.set(r, c, counter++);
        
        Matrix<Integer> matrix2 = new Matrix<Integer>(2, 3);
        for (int r = 0; r < 2; r++)
            for (int c = 0; c < 3; c++)
                matrix1.set(r, c, counter++);

        // Result of subtraction
        Matrix<Integer> result = new Matrix<Integer>(2,3);
        Matrix<Integer> matrix3 = matrix2.subtract(matrix1);
        
        // Result of addition
        Matrix<Integer> matrix4 = matrix2.add(matrix1);
        
        assertTrue("Matrix addition/subtraction error. result"+result,matrix3.equals(matrix4));        
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
    
    @Test()
    public void testIdentityMethod2() {
        Matrix<Integer> matrix = new Matrix<Integer>(1, 2);
        
    	try {
			matrix.identity();
		} catch (Exception e) {
			assertEquals("Matrix should be a square", e.getMessage());
		}
    	
    	Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(2, 2);
        matrix1.set(0, 0, new BigDecimal("0.1"));
        matrix1.set(0, 1, new BigDecimal("0.1"));
        matrix1.set(1, 0, new BigDecimal("0.1"));
        matrix1.set(1, 1, new BigDecimal("0.1"));
        
        Matrix<BigDecimal> expectedResult1 = new Matrix<BigDecimal>(2, 2);
        expectedResult1.set(0, 0, new BigDecimal("1"));
        expectedResult1.set(0, 1, new BigDecimal("0"));
        expectedResult1.set(1, 0, new BigDecimal("0"));
        expectedResult1.set(1, 1, new BigDecimal("1"));
        
        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(2, 2);
        matrix2.set(0, 0, new BigInteger("1"));
        matrix2.set(0, 1, new BigInteger("2"));
        matrix2.set(1, 0, new BigInteger("3"));
        matrix2.set(1, 1, new BigInteger("4"));
        
        Matrix<BigInteger> expectedResult2 = new Matrix<BigInteger>(2, 2);
        expectedResult2.set(0, 0, new BigInteger("1"));
        expectedResult2.set(0, 1, new BigInteger("0"));
        expectedResult2.set(1, 0, new BigInteger("0"));
        expectedResult2.set(1, 1, new BigInteger("1"));
        
        Matrix<Long> matrix3 = new Matrix<Long>(2, 2);
        matrix3.set(0, 0, 99L);
        matrix3.set(0, 1, 99L);
        matrix3.set(1, 0, 99L);
        matrix3.set(1, 1, 99L);
        
        Matrix<Long> expectedResult3 = new Matrix<Long>(2, 2);
        expectedResult3.set(0, 0, 1L);
        expectedResult3.set(0, 1, 0L);
        expectedResult3.set(1, 0, 0L);
        expectedResult3.set(1, 1, 1L);
        
        Matrix<Double> matrix4 = new Matrix<Double>(2, 2);
        matrix4.set(0, 0, 99d);
        matrix4.set(0, 1, 99d);
        matrix4.set(1, 0, 99d);
        matrix4.set(1, 1, 99d);
        
        Matrix<Double> expectedResult4 = new Matrix<Double>(2, 2);
        expectedResult4.set(0, 0, 1d);
        expectedResult4.set(0, 1, 0d);
        expectedResult4.set(1, 0, 0d);
        expectedResult4.set(1, 1, 1d);
        
        Matrix<Float> matrix5 = new Matrix<Float>(2, 2);
        matrix5.set(0, 0, 99.9f);
        matrix5.set(0, 1, 99.9f);
        matrix5.set(1, 0, 99.9f);
        matrix5.set(1, 1, 99.9f);
        
        Matrix<Float> expectedResult5 = new Matrix<Float>(2, 2);
        expectedResult5.set(0, 0, 1f);
        expectedResult5.set(0, 1, 0f);
        expectedResult5.set(1, 0, 0f);
        expectedResult5.set(1, 1, 1f);
    	
        try {
			Matrix<BigDecimal> result1 = matrix1.identity();
			assertArrayEquals(expectedResult1.getRow(0), result1.getRow(0));
	        assertArrayEquals(expectedResult1.getRow(1), result1.getRow(1));
	        
	        Matrix<BigInteger> result2 = matrix2.identity();
			assertArrayEquals(expectedResult2.getRow(0), result2.getRow(0));
	        assertArrayEquals(expectedResult2.getRow(1), result2.getRow(1));
	        
	        Matrix<Long> result3 = matrix3.identity();
			assertArrayEquals(expectedResult3.getRow(0), result3.getRow(0));
	        assertArrayEquals(expectedResult3.getRow(1), result3.getRow(1));
	        
	        Matrix<Double> result4 = matrix4.identity();
			assertArrayEquals(expectedResult4.getRow(0), result4.getRow(0));
	        assertArrayEquals(expectedResult4.getRow(1), result4.getRow(1));
	        
	        Matrix<Float> result5 = matrix5.identity();
			assertArrayEquals(expectedResult5.getRow(0), result5.getRow(0));
	        assertArrayEquals(expectedResult5.getRow(1), result5.getRow(1));
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    
    @Test(expected=NullPointerException.class)
    public void testMatrixBigDecimal() {
        Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(3, 2);
        matrix1.set(0, 0, new BigDecimal("0.1"));
        matrix1.set(0, 1, new BigDecimal("0.1"));
        matrix1.set(1, 0, new BigDecimal("0.1"));
        matrix1.set(1, 1, new BigDecimal("0.1"));
        matrix1.set(2, 0, new BigDecimal("0.1"));
        matrix1.set(2, 1, new BigDecimal("0.1"));

        Matrix<BigDecimal> matrix2 = new Matrix<BigDecimal>(2, 3);
        matrix2.set(0, 0, new BigDecimal("0.2"));
        matrix2.set(0, 1, new BigDecimal("0.2"));
        matrix2.set(0, 2, new BigDecimal("0.2"));
        matrix2.set(1, 0, new BigDecimal("0.2"));
        matrix2.set(1, 1, new BigDecimal("0.2"));
        matrix2.set(1, 2, new BigDecimal("0.2"));

        // Result of multiplication
        BigDecimal[][] array1 = new BigDecimal[][]{{new BigDecimal("0.04"),new BigDecimal("0.04"),new BigDecimal("0.04")},
                                             {new BigDecimal("0.04"),new BigDecimal("0.04"),new BigDecimal("0.04")},
                                             {new BigDecimal("0.04"),new BigDecimal("0.04"),new BigDecimal("0.04")}};
        Matrix<BigDecimal> result1 = new Matrix<BigDecimal>(3,3,array1);

        Matrix<BigDecimal> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<BigDecimal> matrix4 = new Matrix<BigDecimal>(3, 2);
        matrix4.set(0, 0, new BigDecimal("0.2"));
        matrix4.set(0, 1, new BigDecimal("0.2"));
        matrix4.set(1, 0, new BigDecimal("0.2"));
        matrix4.set(1, 1, new BigDecimal("0.2"));
        matrix4.set(2, 0, new BigDecimal("0.2"));
        matrix4.set(2, 1, new BigDecimal("0.2"));

        Matrix<BigDecimal> matrix5 = new Matrix<BigDecimal>(3, 2);
        matrix5.set(0, 0, new BigDecimal("0.1"));
        matrix5.set(0, 1, new BigDecimal("0.1"));
        matrix5.set(1, 0, new BigDecimal("0.1"));
        matrix5.set(1, 1, new BigDecimal("0.1"));
        matrix5.set(2, 0, new BigDecimal("0.1"));
        matrix5.set(2, 1, new BigDecimal("0.1"));

        // Result of subtraction
        BigDecimal[][] array2 = new BigDecimal[][]{{new BigDecimal("0.1"),new BigDecimal("0.1")},
            {new BigDecimal("0.1"),new BigDecimal("0.1")},
            {new BigDecimal("0.1"),new BigDecimal("0.1")}};
        Matrix<BigDecimal> result2 = new Matrix<BigDecimal>(3,2,array2);

        Matrix<BigDecimal> matrix6 = matrix4.subtract(matrix5);
        assertTrue("Matrix subtraction error. matrix6="+matrix6+" result2"+result2, matrix6.equals(result2));

        // Result of addition
        BigDecimal[][] array3 = new BigDecimal[][]{{new BigDecimal("0.3"),new BigDecimal("0.3")},
            {new BigDecimal("0.3"),new BigDecimal("0.3")},
            {new BigDecimal("0.3"),new BigDecimal("0.3")}};
        Matrix<BigDecimal> result3 = new Matrix<BigDecimal>(3,2,array3);

        Matrix<BigDecimal> matrix7 = matrix4.add(matrix5);
        assertTrue("Matrix addition error. matrix7="+matrix7+" result3"+result3, matrix7.equals(result3));

        Matrix<Integer> matrix8 = new Matrix<Integer>(3, 2);
        matrix8.set(0, 0, 1);
        matrix8.set(0, 1, 2);
        matrix8.set(1, 0, 3);
        matrix8.set(1, 1, 4);
        matrix8.set(2, 0, 5);
        matrix8.set(2, 1, 6);

        Matrix<Integer> matrix9 = new Matrix<Integer>(3, 2);
        matrix9.set(0, 0, 1);
        matrix9.set(0, 1, 2);
        matrix9.set(1, 0, 3);
        matrix9.set(1, 1, 4);
        matrix9.set(2, 0, 5);
        matrix9.set(2, 1, 6);

        // Result of multiplication
        Integer[][] array4 = new Integer[3][2];
        Matrix<Integer> result4 = new Matrix<Integer>(3,2,array4);

        Matrix<Integer> matrix10 = matrix8.multiply(matrix9);
        assertTrue("Matrix multiplication error. matrix10="+matrix10+" result4"+result4, matrix10.equals(result4));
    }
    
    @Test
    public void testMatrixBigInteger() {
        Matrix<BigInteger> matrix1 = new Matrix<BigInteger>(3, 2);
        matrix1.set(0, 0, new BigInteger("1"));
        matrix1.set(0, 1, new BigInteger("1"));
        matrix1.set(1, 0, new BigInteger("1"));
        matrix1.set(1, 1, new BigInteger("1"));
        matrix1.set(2, 0, new BigInteger("1"));
        matrix1.set(2, 1, new BigInteger("1"));

        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(2, 3);
        matrix2.set(0, 0, new BigInteger("2"));
        matrix2.set(0, 1, new BigInteger("2"));
        matrix2.set(0, 2, new BigInteger("2"));
        matrix2.set(1, 0, new BigInteger("2"));
        matrix2.set(1, 1, new BigInteger("2"));
        matrix2.set(1, 2, new BigInteger("2"));

        // Result of multiplication
        BigInteger[][] array1 = new BigInteger[][]{{new BigInteger("4"),new BigInteger("4"),new BigInteger("4")},
                                             {new BigInteger("4"),new BigInteger("4"),new BigInteger("4")},
                                             {new BigInteger("4"),new BigInteger("4"),new BigInteger("4")}};
        Matrix<BigInteger> result1 = new Matrix<BigInteger>(3,3,array1);

        Matrix<BigInteger> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<BigInteger> matrix4 = new Matrix<BigInteger>(3, 2);
        matrix4.set(0, 0, new BigInteger("2"));
        matrix4.set(0, 1, new BigInteger("2"));
        matrix4.set(1, 0, new BigInteger("2"));
        matrix4.set(1, 1, new BigInteger("2"));
        matrix4.set(2, 0, new BigInteger("2"));
        matrix4.set(2, 1, new BigInteger("2"));

        Matrix<BigInteger> matrix5 = new Matrix<BigInteger>(3, 2);
        matrix5.set(0, 0, new BigInteger("1"));
        matrix5.set(0, 1, new BigInteger("1"));
        matrix5.set(1, 0, new BigInteger("1"));
        matrix5.set(1, 1, new BigInteger("1"));
        matrix5.set(2, 0, new BigInteger("1"));
        matrix5.set(2, 1, new BigInteger("1"));

        // Result of subtraction
        BigInteger[][] array2 = new BigInteger[][]{{new BigInteger("1"),new BigInteger("1")},
            {new BigInteger("1"),new BigInteger("1")},
            {new BigInteger("1"),new BigInteger("1")}};
        Matrix<BigInteger> result2 = new Matrix<BigInteger>(3,2,array2);

        Matrix<BigInteger> matrix6 = matrix4.subtract(matrix5);
        assertTrue("Matrix subtraction error. matrix6="+matrix6+" result2"+result2, matrix6.equals(result2));

        // Result of addition
        BigInteger[][] array3 = new BigInteger[][]{{new BigInteger("3"),new BigInteger("3")},
            {new BigInteger("3"),new BigInteger("3")},
            {new BigInteger("3"),new BigInteger("3")}};
        Matrix<BigInteger> result3 = new Matrix<BigInteger>(3,2,array3);

        Matrix<BigInteger> matrix7 = matrix4.add(matrix5);
        assertTrue("Matrix addition error. matrix7="+matrix7+" result3"+result3, matrix7.equals(result3));
    }
    
    @Test
    public void testMatrixLong() {
        Matrix<Long> matrix1 = new Matrix<Long>(3, 2);
        matrix1.set(0, 0, 999L);
        matrix1.set(0, 1, 999L);
        matrix1.set(1, 0, 999L);
        matrix1.set(1, 1, 999L);
        matrix1.set(2, 0, 999L);
        matrix1.set(2, 1, 999L);

        Matrix<Long> matrix2 = new Matrix<Long>(2, 3);
        matrix2.set(0, 0, 888L);
        matrix2.set(0, 1, 888L);
        matrix2.set(0, 2, 888L);
        matrix2.set(1, 0, 888L);
        matrix2.set(1, 1, 888L);
        matrix2.set(1, 2, 888L);

        // Result of multiplication
        Long[][] array1 = new Long[][]{{1774224L,1774224L,1774224L},
                                             {1774224L,1774224L,1774224L},
                                             {1774224L,1774224L,1774224L}};
        Matrix<Long> result1 = new Matrix<Long>(3,3,array1);

        Matrix<Long> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<Long> matrix4 = new Matrix<Long>(3, 2);
        matrix4.set(0, 0, 888L);
        matrix4.set(0, 1, 888L);
        matrix4.set(1, 0, 888L);
        matrix4.set(1, 1, 888L);
        matrix4.set(2, 0, 888L);
        matrix4.set(2, 1, 888L);

        Matrix<Long> matrix5 = new Matrix<Long>(3, 2);
        matrix5.set(0, 0, 999L);
        matrix5.set(0, 1, 999L);
        matrix5.set(1, 0, 999L);
        matrix5.set(1, 1, 999L);
        matrix5.set(2, 0, 999L);
        matrix5.set(2, 1, 999L);

        // Result of subtraction
        Long[][] array2 = new Long[][]{{111L,111L},
            {111L,111L},
            {111L,111L}};
        Matrix<Long> result2 = new Matrix<Long>(3,2,array2);

        Matrix<Long> matrix6 = matrix5.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix6="+matrix6+" result2"+result2, matrix6.equals(result2));

        // Result of addition
        Long[][] array3 = new Long[][]{{1887L,1887L},
            {1887L,1887L},
            {1887L,1887L}};
        Matrix<Long> result3 = new Matrix<Long>(3,2,array3);

        Matrix<Long> matrix7 = matrix4.add(matrix5);
        assertTrue("Matrix addition error. matrix7="+matrix7+" result3"+result3, matrix7.equals(result3));
    }
    
    @Test
    public void testMatrixDouble() {
        Matrix<Double> matrix1 = new Matrix<Double>(3, 2);
        matrix1.set(0, 0, 999d);
        matrix1.set(0, 1, 999d);
        matrix1.set(1, 0, 999d);
        matrix1.set(1, 1, 999d);
        matrix1.set(2, 0, 999d);
        matrix1.set(2, 1, 999d);

        Matrix<Double> matrix2 = new Matrix<Double>(2, 3);
        matrix2.set(0, 0, 888d);
        matrix2.set(0, 1, 888d);
        matrix2.set(0, 2, 888d);
        matrix2.set(1, 0, 888d);
        matrix2.set(1, 1, 888d);
        matrix2.set(1, 2, 888d);

        // Result of multiplication
        Double[][] array1 = new Double[][]{{1774224d,1774224d,1774224d},
                                             {1774224d,1774224d,1774224d},
                                             {1774224d,1774224d,1774224d}};
        Matrix<Double> result1 = new Matrix<Double>(3,3,array1);

        Matrix<Double> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<Double> matrix4 = new Matrix<Double>(3, 2);
        matrix4.set(0, 0, 888d);
        matrix4.set(0, 1, 888d);
        matrix4.set(1, 0, 888d);
        matrix4.set(1, 1, 888d);
        matrix4.set(2, 0, 888d);
        matrix4.set(2, 1, 888d);

        Matrix<Double> matrix5 = new Matrix<Double>(3, 2);
        matrix5.set(0, 0, 999d);
        matrix5.set(0, 1, 999d);
        matrix5.set(1, 0, 999d);
        matrix5.set(1, 1, 999d);
        matrix5.set(2, 0, 999d);
        matrix5.set(2, 1, 999d);

        // Result of subtraction
        Double[][] array2 = new Double[][]{{111d,111d},
            {111d,111d},
            {111d,111d}};
        Matrix<Double> result2 = new Matrix<Double>(3,2,array2);

        Matrix<Double> matrix6 = matrix5.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix6="+matrix6+" result2"+result2, matrix6.equals(result2));

        // Result of addition
        Double[][] array3 = new Double[][]{{1887d,1887d},
            {1887d,1887d},
            {1887d,1887d}};
        Matrix<Double> result3 = new Matrix<Double>(3,2,array3);

        Matrix<Double> matrix7 = matrix4.add(matrix5);
        assertTrue("Matrix addition error. matrix7="+matrix7+" result3"+result3, matrix7.equals(result3));
    }
    
    @Test
    public void testMatrixFloat() {
        Matrix<Float> matrix1 = new Matrix<Float>(3, 2);
        matrix1.set(0, 0, 99.9f);
        matrix1.set(0, 1, 99.9f);
        matrix1.set(1, 0, 99.9f);
        matrix1.set(1, 1, 99.9f);
        matrix1.set(2, 0, 99.9f);
        matrix1.set(2, 1, 99.9f);

        Matrix<Float> matrix2 = new Matrix<Float>(2, 3);
        matrix2.set(0, 0, 88.8f);
        matrix2.set(0, 1, 88.8f);
        matrix2.set(0, 2, 88.8f);
        matrix2.set(1, 0, 88.8f);
        matrix2.set(1, 1, 88.8f);
        matrix2.set(1, 2, 88.8f);

        // Result of multiplication
        Float[][] array1 = new Float[][]{{17742.24f,17742.24f,17742.24f},
                                             {17742.24f,17742.24f,17742.24f},
                                             {17742.24f,17742.24f,17742.24f}};
        Matrix<Float> result1 = new Matrix<Float>(3,3,array1);

        Matrix<Float> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Matrix multiplication error. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<Float> matrix4 = new Matrix<Float>(3, 2);
        matrix4.set(0, 0, 88.8f);
        matrix4.set(0, 1, 88.8f);
        matrix4.set(1, 0, 88.8f);
        matrix4.set(1, 1, 88.8f);
        matrix4.set(2, 0, 88.8f);
        matrix4.set(2, 1, 88.8f);

        Matrix<Float> matrix5 = new Matrix<Float>(3, 2);
        matrix5.set(0, 0, 99.9f);
        matrix5.set(0, 1, 99.9f);
        matrix5.set(1, 0, 99.9f);
        matrix5.set(1, 1, 99.9f);
        matrix5.set(2, 0, 99.9f);
        matrix5.set(2, 1, 99.9f);

        // Result of subtraction
        Float[][] array2 = new Float[][]{{11.099998f,11.099998f},
            {11.099998f,11.099998f},
            {11.099998f,11.099998f}};
        Matrix<Float> result2 = new Matrix<Float>(3,2,array2);

        Matrix<Float> matrix6 = matrix5.subtract(matrix4);
        assertTrue("Matrix subtraction error. matrix6="+matrix6+" result2"+result2, matrix6.equals(result2));

        // Result of addition
        Float[][] array3 = new Float[][]{{188.70001f,188.70001f},
            {188.70001f,188.70001f},
            {188.70001f,188.70001f}};
        Matrix<Float> result3 = new Matrix<Float>(3,2,array3);

        Matrix<Float> matrix7 = matrix4.add(matrix5);
        assertTrue("Matrix addition error. matrix7="+matrix7+" result3"+result3, matrix7.equals(result3));
    }
    
    @Test
    public void testMatrixCopy() {
        Matrix<Integer> matrix1 = new Matrix<Integer>(3, 2);
        matrix1.set(0, 0, 99);
        matrix1.set(0, 1, 99);
        matrix1.set(1, 0, 99);
        matrix1.set(1, 1, 99);
        matrix1.set(2, 0, 99);
        matrix1.set(2, 1, 99);
        
        Matrix<Integer> result1 = new Matrix<Integer>(3,2);
        
        result1.copy(matrix1);
    }
    
    @Test
    public void testMatrixHashCode() {
        Matrix<Integer> matrix1 = new Matrix<Integer>(3, 2);
        matrix1.set(0, 0, 99);
        matrix1.set(0, 1, 99);
        matrix1.set(1, 0, 99);
        matrix1.set(1, 1, 99);
        matrix1.set(2, 0, 99);
        matrix1.set(2, 1, 99);
        
        int rows = matrix1.getRows();
        int cols = matrix1.getCols();
       
        int result1 = matrix1.hashCode();
        assertTrue("Matrix HashCode error. matrix1="+matrix1+" result1"+result1, result1 == 18569);
    }
    
    @Test
    public void testMatrixEquals() {
    	Matrix<Integer> matrix1 = new Matrix<Integer>(3, 2);
    	Matrix<Integer> matrix2 = new Matrix<Integer>(2, 3);
    	Matrix<Integer> matrix3 = new Matrix<Integer>(2, 2);
       
    	Integer result1 = 1234;
    	Integer result2 = null;
    	
    	Boolean result3 = matrix1.equals(result1);
    	assertTrue("Matrix equals error", !result3);
    	
    	Boolean result4 = matrix1.equals(result2);
    	assertTrue("Matrix equals error", !result4);
        
    	Boolean result5 = matrix1.equals(matrix2);
    	assertTrue("Matrix equals error", !result5);
    	
    	Boolean result6 = matrix2.equals(matrix3);
    	assertTrue("Matrix equals error", !result6);
    }
}