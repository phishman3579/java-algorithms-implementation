package com.jwetherell.algorithms.data_structures.test;

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
    public void testBigInteger() {
        Matrix<BigInteger> matrix_1 = new Matrix<BigInteger>(2, 2);
        matrix_1.set(0, 0, BigInteger.valueOf(1));
        matrix_1.set(0, 1, BigInteger.valueOf(1));

        matrix_1.set(1, 0, BigInteger.valueOf(2));
        matrix_1.set(1, 1, BigInteger.valueOf(2));

        Matrix<BigInteger> matrix_2 = new Matrix<BigInteger>(2, 2);
        matrix_2.set(0, 0, BigInteger.valueOf(2));
        matrix_2.set(0, 1, BigInteger.valueOf(2));
        matrix_2.set(1, 0, BigInteger.valueOf(1));
        matrix_2.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigInteger> sumOfmatrix = new Matrix<BigInteger>(2, 2);
        sumOfmatrix.set(0, 0, BigInteger.valueOf(3));
        sumOfmatrix.set(0, 1, BigInteger.valueOf(3));
        sumOfmatrix.set(1, 0, BigInteger.valueOf(3));
        sumOfmatrix.set(1, 1, BigInteger.valueOf(3));

        Matrix<BigInteger> productOfMatrix = new Matrix<BigInteger>(2, 2);
        productOfMatrix.set(0, 0, BigInteger.valueOf(3));
        productOfMatrix.set(0, 1, BigInteger.valueOf(3));
        productOfMatrix.set(1, 0, BigInteger.valueOf(6));
        productOfMatrix.set(1, 1, BigInteger.valueOf(6));

        Matrix<BigInteger> subOfMatrix = new Matrix<BigInteger>(2, 2);
        subOfMatrix.set(0, 0, BigInteger.valueOf(-1));
        subOfMatrix.set(0, 1, BigInteger.valueOf(-1));
        subOfMatrix.set(1, 0, BigInteger.valueOf(1));
        subOfMatrix.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigDecimal> inputMatrix = new Matrix<BigDecimal>(2, 2);
        inputMatrix.set(0, 0, BigDecimal.valueOf(1));
        inputMatrix.set(0, 1, BigDecimal.valueOf(1));
        inputMatrix.set(1, 0, BigDecimal.valueOf(1));
        inputMatrix.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> identityMatrix = new Matrix<BigDecimal>(2, 2);
        identityMatrix.set(0, 0, BigDecimal.valueOf(1));
        identityMatrix.set(0, 1, BigDecimal.valueOf(0));
        identityMatrix.set(1, 0, BigDecimal.valueOf(0));
        identityMatrix.set(1, 1, BigDecimal.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(identityMatrix.equals(inputMatrix));

        Assert.assertTrue(sumOfmatrix.equals(matrix_1.add(matrix_2)));
        Assert.assertTrue(productOfMatrix.equals(matrix_1.multiply(matrix_2)));
        Assert.assertTrue(subOfMatrix.equals(matrix_1.subtract(matrix_2)));
    }

    @Test
    public void testBigDecimal() throws Exception {

        Matrix<BigDecimal> matrix_1 = new Matrix<BigDecimal>(2, 2);
        matrix_1.set(0, 0, BigDecimal.valueOf(1));
        matrix_1.set(0, 1, BigDecimal.valueOf(1));

        matrix_1.set(1, 0, BigDecimal.valueOf(2));
        matrix_1.set(1, 1, BigDecimal.valueOf(2));

        Matrix<BigDecimal> matrix_2 = new Matrix<BigDecimal>(2, 2);
        matrix_2.set(0, 0, BigDecimal.valueOf(2));
        matrix_2.set(0, 1, BigDecimal.valueOf(2));
        matrix_2.set(1, 0, BigDecimal.valueOf(1));
        matrix_2.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> sumOfmatrix = new Matrix<BigDecimal>(2, 2);
        sumOfmatrix.set(0, 0, BigDecimal.valueOf(3));
        sumOfmatrix.set(0, 1, BigDecimal.valueOf(3));
        sumOfmatrix.set(1, 0, BigDecimal.valueOf(3));
        sumOfmatrix.set(1, 1, BigDecimal.valueOf(3));

        Matrix<BigDecimal> productOfMatrix = new Matrix<BigDecimal>(2, 2);
        productOfMatrix.set(0, 0, BigDecimal.valueOf(3));
        productOfMatrix.set(0, 1, BigDecimal.valueOf(3));
        productOfMatrix.set(1, 0, BigDecimal.valueOf(6));
        productOfMatrix.set(1, 1, BigDecimal.valueOf(6));

        Matrix<BigDecimal> subOfMatrix = new Matrix<BigDecimal>(2, 2);
        subOfMatrix.set(0, 0, BigDecimal.valueOf(-1));
        subOfMatrix.set(0, 1, BigDecimal.valueOf(-1));
        subOfMatrix.set(1, 0, BigDecimal.valueOf(1));
        subOfMatrix.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> inputMatrix = new Matrix<BigDecimal>(2, 2);
        inputMatrix.set(0, 0, BigDecimal.valueOf(1));
        inputMatrix.set(0, 1, BigDecimal.valueOf(1));
        inputMatrix.set(1, 0, BigDecimal.valueOf(1));
        inputMatrix.set(1, 1, BigDecimal.valueOf(1));

        Matrix<BigDecimal> identityMatrix = new Matrix<BigDecimal>(2, 2);
        identityMatrix.set(0, 0, BigDecimal.valueOf(1));
        identityMatrix.set(0, 1, BigDecimal.valueOf(0));
        identityMatrix.set(1, 0, BigDecimal.valueOf(0));
        identityMatrix.set(1, 1, BigDecimal.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(identityMatrix.equals(inputMatrix));

        Assert.assertTrue(sumOfmatrix.equals(matrix_1.add(matrix_2)));
        Assert.assertTrue(productOfMatrix.equals(matrix_1.multiply(matrix_2)));
        Assert.assertTrue(subOfMatrix.equals(matrix_1.subtract(matrix_2)));

        Assert.assertEquals(2, matrix_1.getRows());
        Assert.assertEquals(2, matrix_1.getCols());
    }

    @Test
    public void identityBigInteger() {
        Matrix<BigInteger> inputMatrix = new Matrix<BigInteger>(2, 2);
        inputMatrix.set(0, 0, BigInteger.valueOf(1));
        inputMatrix.set(0, 1, BigInteger.valueOf(1));
        inputMatrix.set(1, 0, BigInteger.valueOf(1));
        inputMatrix.set(1, 1, BigInteger.valueOf(1));

        Matrix<BigInteger> identityMatrix = new Matrix<BigInteger>(2, 2);
        identityMatrix.set(0, 0, BigInteger.valueOf(1));
        identityMatrix.set(0, 1, BigInteger.valueOf(0));
        identityMatrix.set(1, 0, BigInteger.valueOf(0));
        identityMatrix.set(1, 1, BigInteger.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception ex) {
            fail();
        }

        assertArrayEquals(identityMatrix.getRow(0), inputMatrix.getRow(0));
        assertArrayEquals(identityMatrix.getRow(1), inputMatrix.getRow(1));
    }


    @Test
    public void testDouble() {
        Matrix<Double> matrix_1 = new Matrix<Double>(2, 2);
        matrix_1.set(0, 0, Double.valueOf(1));
        matrix_1.set(0, 1, Double.valueOf(1));
        matrix_1.set(1, 0, Double.valueOf(2));
        matrix_1.set(1, 1, Double.valueOf(2));

        Matrix<Double> matrix_2 = new Matrix<Double>(2, 2);
        matrix_2.set(0, 0, Double.valueOf(2));
        matrix_2.set(0, 1, Double.valueOf(2));
        matrix_2.set(1, 0, Double.valueOf(1));
        matrix_2.set(1, 1, Double.valueOf(1));

        Matrix<Double> sumOfmatrix = new Matrix<Double>(2, 2);
        sumOfmatrix.set(0, 0, Double.valueOf(3));
        sumOfmatrix.set(0, 1, Double.valueOf(3));
        sumOfmatrix.set(1, 0, Double.valueOf(3));
        sumOfmatrix.set(1, 1, Double.valueOf(3));

        Matrix<Double> productOfMatrix = new Matrix<Double>(2, 2);
        productOfMatrix.set(0, 0, Double.valueOf(3));
        productOfMatrix.set(0, 1, Double.valueOf(3));
        productOfMatrix.set(1, 0, Double.valueOf(6));
        productOfMatrix.set(1, 1, Double.valueOf(6));

        Matrix<Double> subOfMatrix = new Matrix<Double>(2, 2);
        subOfMatrix.set(0, 0, Double.valueOf(-1));
        subOfMatrix.set(0, 1, Double.valueOf(-1));
        subOfMatrix.set(1, 0, Double.valueOf(1));
        subOfMatrix.set(1, 1, Double.valueOf(1));

        Matrix<Double> inputMatrix = new Matrix<Double>(2, 2);
        inputMatrix.set(0, 0, Double.valueOf(1));
        inputMatrix.set(0, 1, Double.valueOf(1));
        inputMatrix.set(1, 0, Double.valueOf(1));
        inputMatrix.set(1, 1, Double.valueOf(1));

        Matrix<Double> identityMatrix = new Matrix<Double>(2, 2);
        identityMatrix.set(0, 0, Double.valueOf(1));
        identityMatrix.set(0, 1, Double.valueOf(0));
        identityMatrix.set(1, 0, Double.valueOf(0));
        identityMatrix.set(1, 1, Double.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(identityMatrix.equals(inputMatrix));

        Assert.assertTrue(sumOfmatrix.equals(matrix_1.add(matrix_2)));
        Assert.assertTrue(productOfMatrix.equals(matrix_1.multiply(matrix_2)));
        Assert.assertTrue(subOfMatrix.equals(matrix_1.subtract(matrix_2)));
    }

    @Test
    public void testLong() {

        Matrix<Long> matrix_1 = new Matrix<Long>(2, 2);
        matrix_1.set(0, 0, Long.valueOf(1));
        matrix_1.set(0, 1, Long.valueOf(1));
        matrix_1.set(1, 0, Long.valueOf(2));
        matrix_1.set(1, 1, Long.valueOf(2));

        Matrix<Long> matrix_2 = new Matrix<Long>(2, 2);
        matrix_2.set(0, 0, Long.valueOf(2));
        matrix_2.set(0, 1, Long.valueOf(2));
        matrix_2.set(1, 0, Long.valueOf(1));
        matrix_2.set(1, 1, Long.valueOf(1));

        Matrix<Long> sumOfmatrix = new Matrix<Long>(2, 2);
        sumOfmatrix.set(0, 0, Long.valueOf(3));
        sumOfmatrix.set(0, 1, Long.valueOf(3));
        sumOfmatrix.set(1, 0, Long.valueOf(3));
        sumOfmatrix.set(1, 1, Long.valueOf(3));

        Matrix<Long> productOfMatrix = new Matrix<Long>(2, 2);
        productOfMatrix.set(0, 0, Long.valueOf(3));
        productOfMatrix.set(0, 1, Long.valueOf(3));
        productOfMatrix.set(1, 0, Long.valueOf(6));
        productOfMatrix.set(1, 1, Long.valueOf(6));

        Matrix<Long> subOfMatrix = new Matrix<Long>(2, 2);
        subOfMatrix.set(0, 0, Long.valueOf(-1));
        subOfMatrix.set(0, 1, Long.valueOf(-1));
        subOfMatrix.set(1, 0, Long.valueOf(1));
        subOfMatrix.set(1, 1, Long.valueOf(1));

        Matrix<Long> inputMatrix = new Matrix<Long>(2, 2);
        inputMatrix.set(0, 0, Long.valueOf(1));
        inputMatrix.set(0, 1, Long.valueOf(1));
        inputMatrix.set(1, 0, Long.valueOf(1));
        inputMatrix.set(1, 1, Long.valueOf(1));

        Matrix<Long> identityMatrix = new Matrix<Long>(2, 2);
        identityMatrix.set(0, 0, Long.valueOf(1));
        identityMatrix.set(0, 1, Long.valueOf(0));
        identityMatrix.set(1, 0, Long.valueOf(0));
        identityMatrix.set(1, 1, Long.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(identityMatrix.equals(inputMatrix));

        Assert.assertTrue(sumOfmatrix.equals(matrix_1.add(matrix_2)));
        Assert.assertTrue(productOfMatrix.equals(matrix_1.multiply(matrix_2)));
        Assert.assertTrue(subOfMatrix.equals(matrix_1.subtract(matrix_2)));
    }

    @Test
    public void identityNotSquareException() throws Exception {

        Matrix<BigDecimal> inputMatrix = new Matrix<BigDecimal>(2, 3);
        inputMatrix.set(0, 0, BigDecimal.valueOf(0));
        inputMatrix.set(0, 1, BigDecimal.valueOf(0));
        inputMatrix.set(1, 0, BigDecimal.valueOf(0));
        inputMatrix.set(1, 1, BigDecimal.valueOf(0));

        Matrix<BigDecimal> identityMatrix = new Matrix<BigDecimal>(2, 2);
        identityMatrix.set(0, 0, BigDecimal.valueOf(1));
        identityMatrix.set(0, 1, BigDecimal.valueOf(0));
        identityMatrix.set(1, 0, BigDecimal.valueOf(0));
        identityMatrix.set(1, 1, BigDecimal.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }
    }

    @Test
    public void testFloat() {

        Matrix<Float> matrix_1 = new Matrix<Float>(2, 2);
        matrix_1.set(0, 0, Float.valueOf(1));
        matrix_1.set(0, 1, Float.valueOf(1));
        matrix_1.set(1, 0, Float.valueOf(2));
        matrix_1.set(1, 1, Float.valueOf(2));

        Matrix<Float> matrix_2 = new Matrix<Float>(2, 2);
        matrix_2.set(0, 0, Float.valueOf(2));
        matrix_2.set(0, 1, Float.valueOf(2));
        matrix_2.set(1, 0, Float.valueOf(1));
        matrix_2.set(1, 1, Float.valueOf(1));

        Matrix<Float> sumOfmatrix = new Matrix<Float>(2, 2);
        sumOfmatrix.set(0, 0, Float.valueOf(3));
        sumOfmatrix.set(0, 1, Float.valueOf(3));
        sumOfmatrix.set(1, 0, Float.valueOf(3));
        sumOfmatrix.set(1, 1, Float.valueOf(3));

        Matrix<Float> productOfMatrix = new Matrix<Float>(2, 2);
        productOfMatrix.set(0, 0, Float.valueOf(3));
        productOfMatrix.set(0, 1, Float.valueOf(3));
        productOfMatrix.set(1, 0, Float.valueOf(6));
        productOfMatrix.set(1, 1, Float.valueOf(6));

        Matrix<Float> subOfMatrix = new Matrix<Float>(2, 2);
        subOfMatrix.set(0, 0, Float.valueOf(-1));
        subOfMatrix.set(0, 1, Float.valueOf(-1));
        subOfMatrix.set(1, 0, Float.valueOf(1));
        subOfMatrix.set(1, 1, Float.valueOf(1));

        Matrix<Float> inputMatrix = new Matrix<Float>(2, 2);
        inputMatrix.set(0, 0, Float.valueOf(1));
        inputMatrix.set(0, 1, Float.valueOf(1));
        inputMatrix.set(1, 0, Float.valueOf(1));
        inputMatrix.set(1, 1, Float.valueOf(1));

        Matrix<Float> identityMatrix = new Matrix<Float>(2, 2);
        identityMatrix.set(0, 0, Float.valueOf(1));
        identityMatrix.set(0, 1, Float.valueOf(0));
        identityMatrix.set(1, 0, Float.valueOf(0));
        identityMatrix.set(1, 1, Float.valueOf(1));

        try {
            inputMatrix = inputMatrix.identity();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(identityMatrix.equals(inputMatrix));

        Assert.assertTrue(sumOfmatrix.equals(matrix_1.add(matrix_2)));
        Assert.assertTrue(productOfMatrix.equals(matrix_1.multiply(matrix_2)));
        Assert.assertTrue(subOfMatrix.equals(matrix_1.subtract(matrix_2)));
    }

    @Test
    public void testInvalidInputs() {
        Matrix<Integer> matrix_1 = new Matrix<Integer>(1, 1);
        matrix_1.set(0, 0, 99);

        Matrix<Integer> matrix11 = new Matrix<Integer>(1, 1);
        matrix_1.set(0, 0, 100);

        Matrix<Integer> matrix_2 = new Matrix<Integer>(2, 2);
        matrix_2.set(0, 0, 1);
        matrix_2.set(0, 1, 1);
        matrix_2.set(1, 0, 1);
        matrix_2.set(1, 1, 1);

        Matrix<Integer> matrix3 = new Matrix<Integer>(2, 3);
        matrix_2.set(0, 0, 0);
        matrix_2.set(0, 1, 1);
        matrix_2.set(0, 2, 2);
        matrix_2.set(1, 0, 3);
        matrix_2.set(1, 1, 4);

        // if (this.cols != input.rows)
        Matrix<Integer> matrixProductInvalid = matrix_1.multiply(matrix_2);
        Matrix<Integer> matrixSumInvalid = matrix_1.add(matrix_2);
        Matrix<Integer> matrixSubInvalid = matrix_1.subtract(matrix_2);
        Assert.assertNull(matrixProductInvalid.get(0, 0));
        Assert.assertNull(matrixSumInvalid.get(0, 0));
        Assert.assertNull(matrixSubInvalid.get(0, 0));

        Matrix<Integer> inputMatrix = new Matrix<Integer>(2, 2);
        inputMatrix.set(0, 0, 0);
        inputMatrix.set(0, 1, 0);
        inputMatrix.set(1, 0, 0);
        inputMatrix.set(1, 1, 0);

        Matrix<Integer> identityMatrix = new Matrix<Integer>(2, 2);
        identityMatrix.set(0, 0, 1);
        identityMatrix.set(0, 1, 0);
        identityMatrix.set(1, 0, 0);
        identityMatrix.set(1, 1, 1);

        try {
            inputMatrix = inputMatrix.identity();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            matrix3.equals(matrix_2);
            matrix_2.equals(matrix_1);
            matrix_2.equals("invalid string");
            inputMatrix.equals(null);
            Assert.assertFalse(matrix11.equals(matrix_1));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(identityMatrix.equals(inputMatrix));

        try {
            inputMatrix.copy(matrix_2);
        } catch (Exception ex) {
            fail();
        }
        try {
            inputMatrix.hashCode();
        } catch (Exception ex) {
            fail();
        }

    }

}
