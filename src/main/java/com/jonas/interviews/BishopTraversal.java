package com.jonas.interviews;

import org.junit.Test;
/*
 * Given an 8x8 chessboard, how many moves and how many spaces would 
 * it take for a bishop to go from a start location to an end location
 */
public class BishopTraversal {
	
	public void findBishopTraversal(int rowA, int colA, int rowB, int colB) {
		if ((rowA + colA) % 2 != (rowB + colB) % 2) {
			System.out.println("Can't be done");
		} else {
			if (testBishopDiagonal(rowA, colA, rowB, colB)) {
				printMove(rowA, colA, rowB, colB);
			} else {
				//from the spaces it can move to, which does it share with the end point? 
				for (int i = 1; i < 8; i++) {
					int tempRow = rowA + i;
					int tempCol = colA + i;
					if (testBishopDiagonal(tempRow, tempCol, rowB, colB)) {
						printMove(rowA, colA, tempRow, tempCol);
						findBishopTraversal(tempRow, tempCol, rowB, colB);
						return;
					}
					tempRow = rowA + i;
					tempCol = colA - i;
					if (testBishopDiagonal(tempRow, tempCol, rowB, colB)) {
						printMove(rowA, colA, tempRow, tempCol);
						findBishopTraversal(tempRow, tempCol, rowB, colB);
						return;
					}
					tempRow = rowA - i;
					tempCol = colA + i;
					if (testBishopDiagonal(tempRow, tempCol, rowB, colB)) {
						printMove(rowA, colA, tempRow, tempCol);
						findBishopTraversal(tempRow, tempCol, rowB, colB);
						return;
					}
					tempRow = rowA - i;
					tempCol = colA - i;
					if (testBishopDiagonal(tempRow, tempCol, rowB, colB)) {
						printMove(rowA, colA, tempRow, tempCol);
						findBishopTraversal(tempRow, tempCol, rowB, colB);
						return;
					}
				}
			}
		}
	}
	
	public boolean testBishopDiagonal(int rowA, int colA, int rowB, int colB) {
		return ((rowA > 0 && rowA <=8 && colA > 0 && colA <= 8) && (Math.abs(rowA - rowB) == Math.abs(colA - colB)));
	}
	
	public void printMove(int rowA, int colA, int rowB, int colB) {
		System.out.println("Starting from: " + rowA + ", " + colA + " and moving " + (Math.abs(rowA - rowB)) + " space(s) to: " + rowB + ", " + colB);
	}
	
	@Test
	public void testMoveBishops() {
		findBishopTraversal(2, 1, 7, 8);
		System.out.println();
		findBishopTraversal(1, 1, 8, 8);
	}
	
}
