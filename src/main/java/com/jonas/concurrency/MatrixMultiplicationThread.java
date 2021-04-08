package com.jonas.concurrency;

import java.util.List;
import java.util.concurrent.Callable;

public class MatrixMultiplicationThread implements Callable<int[]> {

	int[][] m1;
	int[][] m2;
	int row;
	List<Integer> indices;
	
	public MatrixMultiplicationThread(int[][] m1, int[][] m2, List<Integer> indices, int row) {
		this.m1 = m1;
		this.m2 = m2;
		this.row = row;
		this.indices = indices;
	}
	
	@Override
	public int[] call() throws Exception {
		int[] results = new int[m1.length];
		for (int i : indices) {
			for (int j : indices) {
				results[i] += m1[row][j] * m2[j][i];
				
//				System.out.println("Row Worker: " + row + " "
//						+ "adding m1[" + row + "][" + j + "] * m2 [" + j + "][" + i + "]: "
//						+ "(" +  m1[row][j] + " * " + m2[j][i] + " = " + m1[row][j] * m2[j][i] + ") "
//								+ "Currently at: " + result[i]);
			}
//			System.out.println("Row Worker: " + row + " calculated index " + i + " to be " + result[i]);
		}
		return results;
	}

}
