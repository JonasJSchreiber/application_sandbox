package com.jonas.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SudokuSolverTest {

	Sudoku sudoku;
	SudokuSolver solver;
	
	@Before
	public void setup() {
		this.sudoku = new Sudoku();
		this.solver = new SudokuSolver();
		sudoku.instantiatePossibilities();
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void testSolver() {
		setupGridA();
		sudoku = solver.solve(sudoku);
	}
	
	public void setupGridA() {
		sudoku.setSquare(1, 5, 3);
		sudoku.setSquare(1, 7, 8);
		sudoku.setSquare(1, 8, 5);
		sudoku.setSquare(2, 2, 1);
		sudoku.setSquare(2, 4, 2);
		sudoku.setSquare(3, 3, 5);
		sudoku.setSquare(3, 5, 7);
		sudoku.setSquare(4, 2, 4);
		sudoku.setSquare(4, 6, 1);
		sudoku.setSquare(5, 0, 7);
		sudoku.setSquare(5, 1, 9);
		sudoku.setSquare(5, 2, 5);
		sudoku.setSquare(5, 3, 4);
		sudoku.setSquare(5, 4, 6);
		sudoku.setSquare(5, 5, 1);
		sudoku.setSquare(5, 6, 8);
		sudoku.setSquare(5, 7, 3);
		sudoku.setSquare(5, 8, 2);
		sudoku.setSquare(6, 0, 5);
		sudoku.setSquare(6, 7, 7);
		sudoku.setSquare(6, 8, 3);
		sudoku.setSquare(7, 2, 2);
		sudoku.setSquare(7, 4, 1);
		sudoku.setSquare(8, 0, 8);
		sudoku.setSquare(8, 1, 6);
		sudoku.setSquare(8, 2, 3);
		sudoku.setSquare(8, 3, 7);
		sudoku.setSquare(8, 4, 4);
		sudoku.setSquare(8, 5, 5);
		sudoku.setSquare(8, 6, 2);
		sudoku.setSquare(8, 7, 1);
		sudoku.setSquare(8, 8, 9);
	}
	
	public int[][] gridB() {
		int[][] grid = {
			{0,0,3,0,9,2,0,0,0},
			{4,0,0,0,3,0,0,1,0},
			{2,7,0,0,0,0,0,0,0},
			{0,1,0,3,0,0,0,0,8},
			{0,5,0,1,6,7,0,3,0},
			{3,0,0,0,0,8,0,6,0},
			{0,0,0,0,0,0,0,5,3},
			{0,3,0,0,8,0,0,0,9},
			{0,0,0,6,2,0,1,0,0}
		};
		return grid;
	}
}
