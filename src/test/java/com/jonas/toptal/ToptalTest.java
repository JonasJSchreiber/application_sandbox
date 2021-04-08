package com.jonas.toptal;

import org.junit.Test;

public class ToptalTest {

	@Test
	public void test() {
		int[] a = {0, 1, 5, 3, 6};
		System.out.println(Toptal.minMissing(a));
	}

	@Test
	public void testSolution() {
		String s = "1A 1F 2F 1C 2B";
		int n = 2;
		System.out.println(Toptal.solution(n, ""));
	}

	@Test
	public void testSolution2() {
		int[] A = {6, 1, 4, 6, 3, 2, 7, 4};
		int K = 2;
		int L = 3;
		System.out.println(Toptal.solution(A, K, L));
	}
}
