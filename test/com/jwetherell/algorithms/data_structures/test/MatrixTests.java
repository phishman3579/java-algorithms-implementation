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
    public void testMatrixBigDecimal() {
        Matrix<BigDecimal> matrix1 = new Matrix<BigDecimal>(3, 2);
        matrix1.set(0, 0, new BigDecimal("11.1"));
        matrix1.set(0, 1, new BigDecimal("22.2"));
        matrix1.set(1, 0, new BigDecimal("33.3"));
        matrix1.set(1, 1, new BigDecimal("44.4"));
        matrix1.set(2, 0, new BigDecimal("55.5"));
        matrix1.set(2, 1, new BigDecimal("66.6"));

        Matrix<BigDecimal> matrix2 = new Matrix<BigDecimal>(2, 3);
        matrix2.set(0, 0, new BigDecimal("99.9"));
        matrix2.set(0, 1, new BigDecimal("88.8"));
        matrix2.set(0, 2, new BigDecimal("77.7"));
        matrix2.set(1, 0, new BigDecimal("66.6"));
        matrix2.set(1, 1, new BigDecimal("55.5"));
        matrix2.set(1, 2, new BigDecimal("44.4"));

        // Result of multiplication
        BigDecimal[][] array1 = new BigDecimal[][]{{new BigDecimal("2587.41"),new BigDecimal("2217.78"),new BigDecimal("1848.15")},
        	{new BigDecimal("6283.71"),new BigDecimal("5421.24"),new BigDecimal("4558.77")},
        	{new BigDecimal("9980.01"),new BigDecimal("8624.70"),new BigDecimal("7269.39")}};
        Matrix<BigDecimal> result1 = new Matrix<BigDecimal>(3,3,array1);

        Matrix<BigDecimal> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Error in multiplication. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<BigDecimal> matrix4 = new Matrix<BigDecimal>(3, 2);
        matrix4.set(0, 0, new BigDecimal("11.1"));
        matrix4.set(0, 1, new BigDecimal("22.2"));
        matrix4.set(1, 0, new BigDecimal("33.3"));
        matrix4.set(1, 1, new BigDecimal("44.4"));
        matrix4.set(2, 0, new BigDecimal("55.5"));
        matrix4.set(2, 1, new BigDecimal("66.6"));

        // Result of subtraction
        BigDecimal[][] array2 = new BigDecimal[][]{{new BigDecimal("0.0"),new BigDecimal("0.0")},
            {new BigDecimal("0.0"),new BigDecimal("0.0")},
            {new BigDecimal("0.0"),new BigDecimal("0.0")}};
        Matrix<BigDecimal> result2 = new Matrix<BigDecimal>(3,2,array2);

        Matrix<BigDecimal> matrix5 = matrix4.subtract(matrix1);
        assertTrue("Error in subtraction. matrix6="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        BigDecimal[][] array3 = new BigDecimal[][]{{new BigDecimal("22.2"),new BigDecimal("44.4")},
            {new BigDecimal("66.6"),new BigDecimal("88.8")},
            {new BigDecimal("111.0"),new BigDecimal("133.2")}};
        Matrix<BigDecimal> result3 = new Matrix<BigDecimal>(3,2,array3);

        Matrix<BigDecimal> matrix6 = matrix4.add(matrix1);
        assertTrue("Error in addition. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));
    }
    
    @Test
    public void testMatrixBigInteger() {
        Matrix<BigInteger> matrix1 = new Matrix<BigInteger>(3, 2);
        matrix1.set(0, 0, new BigInteger("11"));
        matrix1.set(0, 1, new BigInteger("22"));
        matrix1.set(1, 0, new BigInteger("33"));
        matrix1.set(1, 1, new BigInteger("44"));
        matrix1.set(2, 0, new BigInteger("55"));
        matrix1.set(2, 1, new BigInteger("66"));

        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(2, 3);
        matrix2.set(0, 0, new BigInteger("99"));
        matrix2.set(0, 1, new BigInteger("88"));
        matrix2.set(0, 2, new BigInteger("77"));
        matrix2.set(1, 0, new BigInteger("66"));
        matrix2.set(1, 1, new BigInteger("55"));
        matrix2.set(1, 2, new BigInteger("44"));

        // Result of multiplication
        BigInteger[][] array1 = new BigInteger[][]{{new BigInteger("2541"),new BigInteger("2178"),new BigInteger("1815")}, 
        	{new BigInteger("6171"),new BigInteger("5324"),new BigInteger("4477")}, 
        	{new BigInteger("9801"),new BigInteger("8470"),new BigInteger("7139")}};
        Matrix<BigInteger> result1 = new Matrix<BigInteger>(3,3,array1);

        Matrix<BigInteger> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Error in multiplication. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<BigInteger> matrix4 = new Matrix<BigInteger>(3, 2);
        matrix4.set(0, 0, new BigInteger("11"));
        matrix4.set(0, 1, new BigInteger("22"));
        matrix4.set(1, 0, new BigInteger("33"));
        matrix4.set(1, 1, new BigInteger("44"));
        matrix4.set(2, 0, new BigInteger("55"));
        matrix4.set(2, 1, new BigInteger("66"));

        // Result of subtraction
        BigInteger[][] array2 = new BigInteger[][]{{new BigInteger("0"),new BigInteger("0")},
            {new BigInteger("0"),new BigInteger("0")},
            {new BigInteger("0"),new BigInteger("0")}};
        Matrix<BigInteger> result2 = new Matrix<BigInteger>(3,2,array2);

        Matrix<BigInteger> matrix5 = matrix4.subtract(matrix1);
        assertTrue("Error in subtraction. matrix6="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        BigInteger[][] array3 = new BigInteger[][]{{new BigInteger("22"),new BigInteger("44")},
            {new BigInteger("66"),new BigInteger("88")},
            {new BigInteger("110"),new BigInteger("132")}};
        Matrix<BigInteger> result3 = new Matrix<BigInteger>(3,2,array3);

        Matrix<BigInteger> matrix6 = matrix4.add(matrix1);
        assertTrue("Error in addition. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));
    }
    
    @Test
    public void testMatrixLong() {
    	Matrix<Long> matrix1 = new Matrix<Long>(3, 2);
        matrix1.set(0, 0, 11L);
        matrix1.set(0, 1, 22L);
        matrix1.set(1, 0, 33L);
        matrix1.set(1, 1, 44L);
        matrix1.set(2, 0, 55L);
        matrix1.set(2, 1, 66L);

        Matrix<Long> matrix2 = new Matrix<Long>(2, 3);
        matrix2.set(0, 0, 99L);
        matrix2.set(0, 1, 88L);
        matrix2.set(0, 2, 77L);
        matrix2.set(1, 0, 66L);
        matrix2.set(1, 1, 55L);
        matrix2.set(1, 2, 44L);
      
        // Result of multiplication
        Long[][] array1 = new Long[][]{{2541L,2178L,1815L}, {6171L,5324L,4477L}, {9801L,8470L,7139L}};
        Matrix<Long> result1 = new Matrix<Long>(3,3,array1);

        Matrix<Long> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Error in multiplication. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<Long> matrix4 = new Matrix<Long>(3, 2);
        matrix4.set(0, 0, 1L);
        matrix4.set(0, 1, 22L);
        matrix4.set(1, 0, 33L);
        matrix4.set(1, 1, 44L);
        matrix4.set(2, 0, 55L);
        matrix4.set(2, 1, 66L);

        // Result of subtraction
        Long[][] array2 = new Long[][]{{-10L,0L}, {0L,0L}, {0L,0L}};
        Matrix<Long> result2 = new Matrix<Long>(3,2,array2);

        Matrix<Long> matrix5 = matrix4.subtract(matrix1);
        assertTrue("Error in subtraction. matrix6="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Long[][] array3 = new Long[][]{{12L,44L}, {66L,88L}, {110L,132L}};
        Matrix<Long> result3 = new Matrix<Long>(3,2,array3);

        Matrix<Long> matrix6 = matrix1.add(matrix4);
        assertTrue("Error in addition. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));
    }
    
    @Test
    public void testMatrixDouble() {
        Matrix<Double> matrix1 = new Matrix<Double>(3, 2);
        matrix1.set(0, 0, 11d);
        matrix1.set(0, 1, 22d);
        matrix1.set(1, 0, 33d);
        matrix1.set(1, 1, 44d);
        matrix1.set(2, 0, 55d);
        matrix1.set(2, 1, 66d);

        Matrix<Double> matrix2 = new Matrix<Double>(2, 3);
        matrix2.set(0, 0, 99d);
        matrix2.set(0, 1, 88d);
        matrix2.set(0, 2, 77d);
        matrix2.set(1, 0, 66d);
        matrix2.set(1, 1, 55d);
        matrix2.set(1, 2, 44d);

        // Result of multiplication
        Double[][] array1 = new Double[][]{{2541.0d,2178.0d,1815.0d}, {6171.0d,5324.0d,4477.0d}, {9801.0d,8470.0d,7139.0d}};
        Matrix<Double> result1 = new Matrix<Double>(3,3,array1);

        Matrix<Double> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Error in multiplication. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<Double> matrix4 = new Matrix<Double>(3, 2);
        matrix4.set(0, 0, 11d);
        matrix4.set(0, 1, 22d);
        matrix4.set(1, 0, 33d);
        matrix4.set(1, 1, 44d);
        matrix4.set(2, 0, 55d);
        matrix4.set(2, 1, 66d);

        // Result of subtraction
        Double[][] array2 = new Double[][]{{0.0d,0.0d}, {0.0d,0.0d}, {0.0d,0.0d}};
        Matrix<Double> result2 = new Matrix<Double>(3,2,array2);

        Matrix<Double> matrix5 = matrix4.subtract(matrix1);
        assertTrue("Error in subtraction. matrix6="+matrix5+" result2"+result2, matrix5.equals(result2));

        // Result of addition
        Double[][] array3 = new Double[][]{{22.0d,44.0d}, {66.0d,88.0d}, {110.0d,132.0d}};
        Matrix<Double> result3 = new Matrix<Double>(3,2,array3);

        Matrix<Double> matrix6 = matrix4.add(matrix1);
        assertTrue("Error in addition. matrix6="+matrix6+" result3"+result3, matrix6.equals(result3));
    }
    
    @Test(expected=NullPointerException.class)
    public void testMatrixFloat() {
        Matrix<Float> matrix1 = new Matrix<Float>(3, 2);
        matrix1.set(0, 0, 11.1f);
        matrix1.set(0, 1, 22.2f);
        matrix1.set(1, 0, 33.3f);
        matrix1.set(1, 1, 44.4f);
        matrix1.set(2, 0, 55.5f);
        matrix1.set(2, 1, 66.6f);

        Matrix<Float> matrix2 = new Matrix<Float>(2, 3);
        matrix2.set(0, 0, 99.9f);
        matrix2.set(0, 1, 88.8f);
        matrix2.set(0, 2, 77.7f);
        matrix2.set(1, 0, 66.6f);
        matrix2.set(1, 1, 55.5f);
        matrix2.set(1, 2, 44.4f);

        // Result of multiplication
        Float[][] array1 = new Float[][]{{2587.4102f,2217.7803f,1848.15f}, {6283.71f,5421.24f,4558.77f}, {9980.01f,8624.7f,7269.3896f}};
        Matrix<Float> result1 = new Matrix<Float>(3,3,array1);

        Matrix<Float> matrix3 = matrix1.multiply(matrix2);
        assertTrue("Error in multiplication. matrix3="+matrix3+" result1"+result1, matrix3.equals(result1));

        Matrix<Float> matrix4 = new Matrix<Float>(3, 2);
        matrix4.set(0, 0, 11.1f);
        matrix4.set(0, 1, 22.2f);
        matrix4.set(1, 0, 33.3f);
        matrix4.set(1, 1, 44.4f);
        matrix4.set(2, 0, 55.5f);
        matrix4.set(2, 1, 66.6f);

        // Result of subtraction
        Float[][] array2 = new Float[][]{{0.0f,0.0f}, {0.0f,0.0f}, {0.0f,0.0f}};
        Matrix<Float> result2 = new Matrix<Float>(3,2,array2);

        Matrix<Float> matrix6 = matrix1.subtract(matrix4);
        assertTrue("Error in subtraction. matrix6="+matrix6+" result2"+result2, matrix6.equals(result2));

        // Result of addition
        Float[][] array3 = new Float[][]{{22.2f,44.4f}, {66.6f,88.8f}, {111.0f,133.2f}};
        Matrix<Float> result3 = new Matrix<Float>(3,2,array3);

        Matrix<Float> matrix7 = matrix4.add(matrix1);
        assertTrue("Error in addition. matrix7="+matrix7+" result3"+result3, matrix7.equals(result3));
        
        Matrix<Integer> matrix8 = new Matrix<Integer>(3, 2);
        matrix8.set(0, 0, 1);
        matrix8.set(0, 1, 2);
        matrix8.set(1, 0, 3);
        matrix8.set(1, 1, 4);
        matrix8.set(2, 0, 5);
        matrix8.set(2, 1, 6);

        Matrix<Integer> matrix9 = new Matrix<Integer>(3, 2);
        matrix9.set(0, 0, 9);
        matrix9.set(0, 1, 8);
        matrix9.set(1, 0, 7);
        matrix9.set(1, 1, 6);
        matrix9.set(2, 0, 5);
        matrix9.set(2, 1, 4);

        // Result of multiplication
        Integer[][] array4 = new Integer[3][2];
        Matrix<Integer> result4 = new Matrix<Integer>(3,2,array4);

        Matrix<Integer> matrix10 = matrix8.multiply(matrix9);
        assertTrue("Error in multiplication. matrix10="+matrix10+" result4"+result4, matrix10.equals(result4));
    }
    
    @Test(expected=NullPointerException.class)
    public void testMatrixEquals() {
    	Matrix<Integer> matrix1 = new Matrix<Integer>(2, 3);
    	Matrix<Integer> matrix2 = new Matrix<Integer>(3, 2);
    	Matrix<Integer> matrix3 = new Matrix<Integer>(3, 3);
       
    	Integer result1 = null;
    	Integer result2 = 99;
    	
    	Boolean result3 = matrix1.equals(result1);
    	assertTrue("Error in equals!", !result3);
    	
    	Boolean result4 = matrix1.equals(result2);
    	assertTrue("Error in equals!", !result4);
        
    	Boolean result5 = matrix1.equals(matrix2);
    	assertTrue("Error in equals!", !result5);
    	
    	Boolean result6 = matrix2.equals(matrix3);
    	assertTrue("Error in equals!", !result6);
    	
    	int row = matrix1.getRows();
        int col = matrix2.getCols();
    	
    	int counter = 0;
        Matrix<Integer> matrix4 = new Matrix<Integer>(3, 2);
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 2; c++)
                matrix4.set(r, c, counter++);
        
        Matrix<Integer> matrix5 = new Matrix<Integer>(2, 3);
        for (int r = 0; r < 2; r++)
            for (int c = 0; c < 3; c++)
                matrix5.set(r, c, counter++);

        Matrix<Integer> result = new Matrix<Integer>(2,3);
        Matrix<Integer> matrix6 = matrix4.subtract(matrix5);
        Matrix<Integer> matrix7 = matrix4.add(matrix5);
        
        matrix2.copy(matrix4);
        
        assertTrue("Error in equals!"+result, matrix6.equals(matrix7));
    }
    
    @Test
    public void testMatrixHashCode() {
        Matrix<Integer> matrix = new Matrix<Integer>(2, 2);
        matrix.set(0, 0, 11);
        matrix.set(0, 1, 22);
        matrix.set(1, 0, 33);
        matrix.set(1, 1, 44);
       
        int result = matrix.hashCode();
        assertTrue("Error in HashCode matrix="+matrix+" result"+result, result == 3534);
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
        matrix1.set(0, 0, new BigDecimal("11"));
        matrix1.set(0, 1, new BigDecimal("22"));
        matrix1.set(1, 0, new BigDecimal("33"));
        matrix1.set(1, 1, new BigDecimal("44"));
        
        Matrix<BigDecimal> expectedResult1 = new Matrix<BigDecimal>(2, 2);
        expectedResult1.set(0, 0, new BigDecimal("1"));
        expectedResult1.set(0, 1, new BigDecimal("0"));
        expectedResult1.set(1, 0, new BigDecimal("0"));
        expectedResult1.set(1, 1, new BigDecimal("1"));
        
        Matrix<BigInteger> matrix2 = new Matrix<BigInteger>(2, 2);
        matrix2.set(0, 0, new BigInteger("11"));
        matrix2.set(0, 1, new BigInteger("22"));
        matrix2.set(1, 0, new BigInteger("33"));
        matrix2.set(1, 1, new BigInteger("44"));
        
        Matrix<BigInteger> expectedResult2 = new Matrix<BigInteger>(2, 2);
        expectedResult2.set(0, 0, new BigInteger("1"));
        expectedResult2.set(0, 1, new BigInteger("0"));
        expectedResult2.set(1, 0, new BigInteger("0"));
        expectedResult2.set(1, 1, new BigInteger("1"));
        
        Matrix<Long> matrix3 = new Matrix<Long>(2, 2);
        matrix3.set(0, 0, 11L);
        matrix3.set(0, 1, 22L);
        matrix3.set(1, 0, 33L);
        matrix3.set(1, 1, 44L);
        
        Matrix<Long> expectedResult3 = new Matrix<Long>(2, 2);
        expectedResult3.set(0, 0, 1L);
        expectedResult3.set(0, 1, 0L);
        expectedResult3.set(1, 0, 0L);
        expectedResult3.set(1, 1, 1L);
        
        Matrix<Double> matrix4 = new Matrix<Double>(2, 2);
        matrix4.set(0, 0, 11d);
        matrix4.set(0, 1, 22d);
        matrix4.set(1, 0, 33d);
        matrix4.set(1, 1, 44d);
        
        Matrix<Double> expectedResult4 = new Matrix<Double>(2, 2);
        expectedResult4.set(0, 0, 1d);
        expectedResult4.set(0, 1, 0d);
        expectedResult4.set(1, 0, 0d);
        expectedResult4.set(1, 1, 1d);
        
        Matrix<Float> matrix5 = new Matrix<Float>(2, 2);
        matrix5.set(0, 0, 11.1f);
        matrix5.set(0, 1, 22.2f);
        matrix5.set(1, 0, 33.3f);
        matrix5.set(1, 1, 44.4f);
        
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
}