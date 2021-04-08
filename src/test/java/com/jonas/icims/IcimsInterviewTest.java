package com.jonas.icims;

import org.junit.Test;

public class IcimsInterviewTest {
	IcimsInterview icimsInterview = new IcimsInterview();
	@Test
	public void test() {
		int[] A = {-1, 1, 3, 3, 3, 2, 3, 2, 1, 0};
		int x = icimsInterview.solution(A);
		System.out.println(x);
		int[] B = {7, 7, 7, 7};
		x = icimsInterview.solution(B);
		System.out.println(x);

	}

	@Test
	public void test2() {
		int[] A = {60, 220, 40};
		int[] B = {2, 3, 5};
		int M = 5;
		int X = 2;
		int Y = 200;
		int x = icimsInterview.solution(A, B, M, X, Y);
		System.out.println(x);

	}

	@Test
	public void testDebug() {
		int[] A = {1, 1, 0, 1, 0, 0};
		int x = icimsInterview.debugSolution(A);
		System.out.println(x);
		int[] B = {1, 1, 1};
		x = icimsInterview.debugSolution(B);
		System.out.println(x);
		int[] C = {0, 0, 1, 1, 1};
		x = icimsInterview.debugSolution(C);
		System.out.println(x);
	}

}
