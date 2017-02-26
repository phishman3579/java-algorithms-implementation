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
}
