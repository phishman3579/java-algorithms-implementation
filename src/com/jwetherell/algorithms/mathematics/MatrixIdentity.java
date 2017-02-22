package com.jwetherell.algorithms.mathematics;

public class MatrixIdentity {

	public static int[][] getMatrixIdentity(int size){
		int[][] matrix = new int[size][size];
		for(int i = 0; i < size; ++i){
			matrix[i][i] = 1;
		}
		return matrix;
	}
}
