package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.MatrixIdentity;

public class MatrixIdentityTest {

	@Test
	public void test1() {
		int[][] matrix = MatrixIdentity.getMatrixIdentity(3);
		
		assertEquals(1, matrix[0][0]);
		assertEquals(1, matrix[1][1]);
		assertEquals(1, matrix[2][2]);
	}

}
