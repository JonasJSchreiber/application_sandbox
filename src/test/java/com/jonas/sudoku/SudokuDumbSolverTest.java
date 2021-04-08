package com.jonas.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SudokuDumbSolverTest {

	Sudoku sudoku;
	SudokuDumbSolver solver;
	
	@Before
	public void setup() {
		this.sudoku = new Sudoku();
		this.solver = new SudokuDumbSolver();
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void testDumbSolver() {
		sudoku.grid = gridA();
		sudoku = solver.solve(sudoku);
	}
	
	public int[][] gridA() {
		int[][] grid = {
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,3,0,8,5},
			{0,0,1,0,2,0,0,0,0},
			{0,0,0,5,0,7,0,0,0},
			{0,0,4,0,0,0,1,0,0},
			{0,9,0,0,0,0,0,0,0},
			{5,0,0,0,0,0,0,7,3},
			{0,0,2,0,1,0,0,0,0},
			{0,0,0,0,4,0,0,0,9}
		};
		return grid;
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
