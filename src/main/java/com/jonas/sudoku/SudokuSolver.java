package com.jonas.sudoku;

public class SudokuSolver {
	
	int iterations;
	Sudoku model;
	
	public SudokuSolver() {
		this.iterations = 0;
	}

	public Sudoku solve(Sudoku sudoku) {
		this.model = sudoku;
		model.printSudoku();
		
		Long start = System.currentTimeMillis();
		
		if (model.gridContainsZeroes()) {
			sudoku.grid = privateSolver(sudoku.grid);			
		}

		Long end = System.currentTimeMillis();
		Long runtime = end-start;
		
		if (sudoku.grid != null) {
			System.out.println("Sudoku solved in: " + iterations + " iterations.");
			System.out.println("Time elapsed was: " + runtime + " milliseconds");
			model.printSudoku();
		} else {
			System.out.println("Sudoku determined unsolvable in: " + iterations + " iterations.");
			System.out.println("Time elapsed was: " + runtime + " milliseconds");
		}
		
		
		return sudoku;
	}
	
	private int[][] privateSolver(int[][] grid) {
		while (model.gridContainsZeroes()) {
			boolean changed = false;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (model.grid[i][j] == 0 && model.possibilities.get(i * 9 + j).size() == 1) {
						if (model.testSquare(i, j, model.possibilities.get(i * 9 + j).get(0))) {
							model.setSquare(i, j, model.possibilities.get(i * 9 + j).get(0));
							changed = true;
						} else {
							return null;
						}
					}
					iterations++;
				}
			}
			if (!changed) {
				//here is where you implement the guessing. 
				//must have to return the whole Sudoku object. Setting the temp grid doesn't seem to take.
				//also needs more logic, as the upper right obviously gets a 1
				
				/**
				
				0 0 0 0 0 0 0 0 0 
				0 0 0 0 0 3 0 8 5 
				0 0 1 0 2 0 0 0 0 
				0 0 0 5 0 7 0 0 0 
				0 0 4 0 0 0 1 0 0 
				7 9 5 4 6 1 8 3 2 
				5 0 0 0 0 0 0 7 3 
				0 0 2 0 1 0 0 0 0 
				8 6 3 7 4 5 2 1 9
				 
				 */
				
				//we know each row and column needs a 1-9, why not use that info to place these numbers within regions.  
				
				
				int[] firstSquare = model.findFirstEmptySquare();
				for (int k = 1; k <= 9; k++) {
					iterations++;
					if (model.testSquare(firstSquare[0], firstSquare[1], k)) {
						Sudoku tempModel = model;
						tempModel.setSquare(firstSquare[0], firstSquare[1], k);
						if ((tempModel.grid = privateSolver(tempModel.grid)) != null) {
							return tempModel.grid;
						} else {
							tempModel.grid = grid; 
							tempModel.grid[firstSquare[0]][firstSquare[1]] = 0;
						}
					}
				}
			}
		}
		return model.grid;
	}
}
