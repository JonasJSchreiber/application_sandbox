package com.jonas.sudoku;

import java.util.ArrayList;

public class Sudoku {
	
	int[][] grid;
	ArrayList<ArrayList<Integer>> possibilities;
	
	public Sudoku() {
		this.grid = new int[9][9];
	}

	public void instantiatePossibilities() {
		possibilities = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				possibilities.add(new ArrayList<Integer>());
				for (int k = 1; k <= 9; k++) {
					possibilities.get(i * 9 + j).add(k);
				}
			}
		}
	}
	
	public void setSquare(int row, int column, Integer value) {
		grid[row][column] = value;
		
		for (int i = 0; i < 9; i++) {
			possibilities.get((row * 9) + i).remove(value);
			possibilities.get((i * 9) + column).remove(value); 
		}
		
		int rowRegion = row/3;
		int colRegion = column/3;
		for (int i = rowRegion*3; i < rowRegion*3 + 3; i++) {
			for (int j = colRegion*3; j < colRegion*3 + 3; j++) {
				possibilities.get(i * 9 + j).remove(value);
			}
		}
		
	}
	
	public void printSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean testSquare(int row, int column, int value) {
		//test rows and columns
		for (int i = 0; i < 9; i++) {
			if (grid[row][i] == value || grid[i][column] == value) {
				return false;
			}
		}
		
		//test region 
		int rowRegion = row/3;
		int colRegion = column/3;
		for (int i = rowRegion*3; i < rowRegion*3 + 3; i++) {
			for (int j = colRegion*3; j < colRegion*3 + 3; j++) {
				if (i != row && j != column && grid[i][j] == value) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean gridContainsZeroes() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int[] findFirstEmptySquare() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					int[] square = {i,j};
					return square;
				}
			}
		}
		return null;
	}
	
	public int[] findSecondEmptySquare() {
		boolean foundFirst = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					if (foundFirst) {
						int[] square = {i,j};
						return square;
					} else {
						foundFirst = true;
					}
				}
			}
		}
		return null;
	}
}
