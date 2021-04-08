package com.jonas.icims;

import java.util.HashSet;
import java.util.Set;

public class IcimsInterview {
	public int solution(int[] A) {
		if (A == null || A.length < 3) {
			return 0;
		}
		int stablePeriods = 0;
		int i = 2;
		while (i < A.length) {
			int j = i;
			while (j >= 2) {
				if (A[j - 1] - A[j - 2] == A[j] - A[j - 1]) {
					stablePeriods++;
				} else {
					break;
				}
				j--;
			}
			i++;
		}
		return stablePeriods;
	}

	public int solution(int[] A, int[] B, int M, int X, int Y) {
		if (A == null || B == null || A.length != B.length
				|| A.length == 0 || X <= 0 || Y <= 0) {
			return 0;
		}
		int ptr = 0;
		int stops = 0;
		while (ptr < A.length) {
			int remainingCapWeight = Y;
			int remainingCapPersons = X;
			Set<Integer> floors = new HashSet<>();
			while (ptr < A.length && remainingCapWeight - A[ptr] > 0
					&& remainingCapPersons > 0) {
				if (B[ptr] > M) {
					return 0;
				}
				floors.add(B[ptr]);
				remainingCapWeight -= A[ptr++];
				remainingCapPersons--;
			}
			if (floors.size() == 0) {
				return 0;
			}
			stops += floors.size() + 1;
		}
		return stops;
	}

	int debugSolution(int[] A) {
		int n = A.length;
		int result = 0;
		for (int i = 0; i < n - 1; i++) {
			if (A[i] == A[i + 1] && result < A.length - 2)
				result = result + 1;
		}
		int r = 0;
		for (int i = 0; i < n; i++) {
			int count = 0;
			if (i > 0) {
				if (A[i - 1] != A[i])
					count = count + 1;
				else
					count = count - 1;
			}
			if (i < n - 1) {
				if (A[i + 1] != A[i])
					count = count + 1;
				else
					count = count - 1;
			}
			r = Math.max(r, count);
		}
		return result + r;
	}
}
