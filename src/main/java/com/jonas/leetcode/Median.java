package com.jonas.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Median {
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int m1 = (nums1.length + nums2.length + 1) / 2;
		int m2 = (nums1.length + nums2.length + 2) / 2;
		return (getkth(nums1, 0, nums2, 0, m1) + getkth(nums1, 0, nums2, 0, m2)) / 2.0;
	}

	public double getkth(int[] nums1, int aStart, int[] nums2, int bStart, int median) {
		if (aStart > nums1.length - 1) {
			return nums2[bStart + median - 1];
		}
		if (bStart > nums2.length - 1) {
			return nums1[aStart + median - 1];
		}
		if (median == 1) {
			return Math.min(nums1[aStart], nums2[bStart]);
		}

		int m1 = Integer.MAX_VALUE;
		int m2 = Integer.MAX_VALUE;
		if (aStart + median / 2 - 1 < nums1.length) {
			m1 = nums1[aStart + median / 2 - 1];
		}
		if (bStart + median / 2 - 1 < nums2.length) {
			m2 = nums2[bStart + median / 2 - 1];
		}

		if (m1 < m2) {
			return getkth(nums1, aStart + median / 2, nums2, bStart, median - median / 2);
		} else {
			return getkth(nums1, aStart, nums2, bStart + median / 2, median - median / 2);
		}
	}

	@Test
	public void test() {
		int[] nums1 = {1, 2};
		int[] nums2 = {1, 2, 3};
		Double result = findMedianSortedArrays(nums1, nums2);
		Double expected = new Double(2.0);
		assertEquals(expected, result);
	}

	@Test
	public void testLarger() {
		int nums1[] = {1, 12, 15, 26, 38};
		int nums2[] = {2, 13, 17, 30, 45};
		Double result = findMedianSortedArrays(nums1, nums2);
		Double expected = 16.0;
		assertEquals(expected, result);
	}
}