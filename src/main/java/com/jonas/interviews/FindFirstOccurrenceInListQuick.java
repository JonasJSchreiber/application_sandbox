package com.jonas.interviews;

import org.junit.Test;

/*
 * Given an array whose elements are sorted, 
 * return the index of the first occurrence of a specific integer in log(n)
 */
public class FindFirstOccurrenceInListQuick {
	
	public Integer findFirstOccurrence(int[] a, int key) {
		int lo = 0;
		int hi = a.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if (key < a[mid])
				hi = mid - 1;
			else if (key > a[mid])
				lo = mid + 1;
			else {
				while (mid > 0) {
					if (a[mid - 1] == key) {
						mid--;
					} else {
						break;
					}
				}
				return mid;
			}
		}
		return -1;
	}
	
	@Test
	public void findFirstOccurrenceTest() {
		int[] array = { 0, 2, 4, 6, 6, 7, 8, 10, 16, 18, 24, 33, 56, 77 };
		System.out.println(findFirstOccurrence(array, 15));
	}
}
