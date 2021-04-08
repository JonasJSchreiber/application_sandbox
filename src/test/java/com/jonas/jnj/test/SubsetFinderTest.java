package com.jonas.jnj.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.jonas.jnj.SubsetFinder;

public class SubsetFinderTest {
	
	SubsetFinder subsetFinder;
	
	@Before
	public void setup() {
		subsetFinder = new SubsetFinder();
	}
	
	@Test
	public void test() {
		Set<List<Integer>> subsets = subsetFinder.nSumByRange(1, 10, 13, 2);
		for(List<Integer> l : subsets) {
			System.out.println(l.toString());
		}
	}
	
	@Test
	public void test3() {
		Set<List<Integer>> subsets = subsetFinder.nSumByRange(1, 10, 13, 3);
		for(List<Integer> l : subsets) {
			System.out.println(l.toString());
		}
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void test5() {
		long start = System.nanoTime();
		Set<List<Integer>> subsets = subsetFinder.nSumByRange(1, 20, 32, 5);
		System.out.println("Without DP Took: " + (System.nanoTime() - start) + "ns");
		start = System.nanoTime();
		Set<List<Integer>> subsetsDP = subsetFinder.nSumByRangeDP(1, 20, 32, 5);
		System.out.println("With DP Took: " + (System.nanoTime() - start) + "ns");
		System.out.println("Cache Hits: " + subsetFinder.CACHE_HITS);
		System.out.println("Total Size of Result List: " + subsets.size());
		assertEquals(subsets.size(), subsetsDP.size());
	}
	
	@Test
	public void testIterative() {
		List<List<Integer>> subsets = subsetFinder.nSumByRangeIterative(1, 10, 32, 5);
		for(List<Integer> l : subsets) {
			System.out.println(l.toString());
		}
	}
}
