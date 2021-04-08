package com.jonas.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class ThirdMax {
	public int thirdMax(int[] nums) {
		Set<Integer> ints = new TreeSet<>();
		for (int i : nums) {
			ints.add(i);
		}
		if (ints.size() >= 3) {
			return (int) ints.toArray()[ints.size() - 3];
		} else {
			return (int) ints.toArray()[0];
		}
	}

	@Test
	public void test() {
		int[] nums = {2, 1};
		int expected = 1;
		int actual = thirdMax(nums);
		assertEquals(expected, actual);
	}
}
