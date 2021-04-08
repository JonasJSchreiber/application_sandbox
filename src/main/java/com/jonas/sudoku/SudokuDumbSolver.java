package com.jonas.sudoku;

public class SudokuDumbSolver {
	
	int iterations;
	Sudoku model;
	
	public SudokuDumbSolver() {
		iterations = 0;
	}

	public Sudoku solve(Sudoku sudoku) {
		this.model = sudoku;
		model.printSudoku();
		
		Long start =  System.nanoTime();;
		
		int[] firstSquare = model.findFirstEmptySquare();
		if (firstSquare != null) {
			sudoku.grid = privateSolver(sudoku.grid, firstSquare[0], firstSquare[1]);			
		}

		Long end =  System.nanoTime();;
		Long runtime = end-start;
		
		if (sudoku.grid != null) {
			System.out.println("Sudoku solved in: " + iterations + " iterations.");
			System.out.println("Time elapsed was: " + runtime + " nanoseconds");
			model.printSudoku();
		} else {
			System.out.println("Sudoku determined unsolvable in: " + iterations + " iterations.");
			System.out.println("Time elapsed was: " + runtime + " nanoseconds");
		}
		
		
		return sudoku;
	}
	
	private int[][] privateSolver(int[][] grid, int i, int j) {
		int[] nextEmpty;		
		for (int k = 1; k <= 9; k++) {
			iterations++;
			if (model.testSquare(i, j, k)) {
				int[][] tempGrid = grid;
				tempGrid[i][j] = k;
				if ((nextEmpty = model.findSecondEmptySquare()) == null) {
					return grid;
				} else if ((tempGrid = privateSolver(tempGrid, nextEmpty[0], nextEmpty[1])) != null) {
					return tempGrid;
				} else {
					tempGrid = grid; 
					tempGrid[i][j] = 0;
				}
			}
		}
		return null;
	}
}
