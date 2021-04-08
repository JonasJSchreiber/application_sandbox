package com.jonas.toptal;

public class Toptal {

	// For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
	//
	// Given A = [1, 2, 3], the function should return 4.
	//
	// Given A = [−1, −3], the function should return 1.
	public static int minMissing(int[] a) {
		boolean[] b = new boolean[1000000];
		for (int i : a) {
			if (i > 0) {
				b[i] = true;
			}
		}
		for (int i = 1; i < 1000000; i++) {
			if (!b[i]) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}

	// Seats on a plane. Tell how many four contiguous seats there are
	public static int solution(int N, String S) {
		// write your code in Java SE 8
		boolean[][] seats = new boolean[N][10];
		if (S.trim().length() > 0) {
			String[] fields = S.split(" ");
			for (String s : fields) {
				seats[getRow(s)][getCol(s)] = true;
			}
		}
		int numAvailable = 0;
		for (int i = 0; i < N; i++) {
			numAvailable += spacesAvailable(seats, i);
		}
		return numAvailable;
	}

	public static int getRow(String s) {
		return Integer.valueOf(s.trim().toUpperCase().replaceAll("[^\\d]", "")) - 1;
	}

	public static int getCol(String s) {
		if (s.trim().length() < 1) {
			return Integer.MAX_VALUE;
		}
		char seat = s.trim().toUpperCase().replaceAll("[\\d]", "").toCharArray()[0];
		switch (seat) {
			case 'A' :
				return 0;
			case 'B' :
				return 1;
			case 'C' :
				return 2;
			case 'D' :
				return 3;
			case 'E' :
				return 4;
			case 'F' :
				return 5;
			case 'G' :
				return 6;
			case 'H' :
				return 7;
			case 'I' :
				return 8;
			case 'K' :
				return 9;
			default :
				return Integer.MAX_VALUE;
		}
	}

	public static int spacesAvailable(boolean[][] seats, int row) {
		// possible trues:
		// indices 1-4 are false - inverse if any of 1,2,3,4 is true
		// indices 3-6 are false - inverse if any of 3,4,5,6 is true
		// indices 5-8 are false - inverse if any of 5,6,7,8 is true
		int numPossible = 0;
		if (!seats[row][1] && !seats[row][2] && !seats[row][3] && !seats[row][4]) {
			numPossible++;
		}
		if (!seats[row][5] && !seats[row][6] && !seats[row][7] && !seats[row][8]) {
			numPossible++;
		}
		if (numPossible == 0 && !seats[row][3] && !seats[row][4] && !seats[row][5]
				&& !seats[row][6]) {
			numPossible++;
		}
		return numPossible;
	}

	// alice collects from k trees, bob collects from l trees
	public static int solution(int[] A, int K, int L) {
		// write your code in Java SE 8
		if (K + L > A.length) {
			return -1;
		}
		// choose one
		int endIndexK = maxBeginningSum(A, K, A.length);
		int endIndexL = maxBeginningSum(A, L, A.length - K);
		int total = 0;
		for (int i = A.length - endIndexK; i < endIndexK; i++) {
			total += A[i];
		}
		for (int i = A.length - endIndexL; i < endIndexL; i++) {
			total += A[i];
		}
		return total;
	}

	public static int maxBeginningSum(int arr[], int k, int n) {
		// k must be greater
		if (n < k) {
			return -1;
		}
		// Compute sum of first window of size k
		int res = 0;
		for (int i = 0; i < k; i++)
			res += arr[i];
		// Compute sums of remaining windows by
		// removing first element of previous
		// window and adding last element of
		// current window.
		int curr_sum = res;
		int start = 0;
		for (int i = k; i < n; i++) {
			curr_sum += arr[i] - arr[i - k];
			if (res < curr_sum) {
				res = curr_sum;
				start = i;
			}
		}
		return start;
	}

	public int solution(int[] A) {
		// write your code in Java SE 8
		if (A.length <= 1) {
			return 1;
		}
		int max = 0;
		for (int i = 0; i < A.length; i++) {
			int currMax = findMaxSwitch(A, i);
			max = Math.max(max, currMax);
		}
		return max;
	}

	public static int findMaxSwitch(int[] A, int from) {
		int max = 0;
		for (int i = from; i < A.length - 2; i++) {
			if (A[i] == A[i + 2]) {
				max++;
			}
		}
		return max;
	}
}
