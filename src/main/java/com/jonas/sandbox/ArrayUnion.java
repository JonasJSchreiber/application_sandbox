package com.jonas.sandbox;

import java.util.Arrays;

public class ArrayUnion {

	static int findKthSmallest(int A[], int m, int B[], int n, int k) {
	  assert(m >= 0); assert(n >= 0); assert(k > 0); assert(k <= m+n);
	  
	  int i = (int)((double)m / (m+n) * (k-1));
	  int j = (k-1) - i;
	 
	  assert(i >= 0); assert(j >= 0); assert(i <= m); assert(j <= n);
	  // invariant: i + j = k-1
	  // Note: A[-1] = -INF and A[m] = +INF to maintain invariant
	  int Ai_1 = ((i == 0) ? Integer.MIN_VALUE : A[i-1]);
	  int Bj_1 = ((j == 0) ? Integer.MIN_VALUE : B[j-1]);
	  int Ai   = ((i == m) ? Integer.MAX_VALUE : A[i]);
	  int Bj   = ((j == n) ? Integer.MAX_VALUE : B[j]);
	 
	  if (Bj_1 < Ai && Ai < Bj)
	    return Ai;
	  else if (Ai_1 < Bj && Bj < Ai)
	    return Bj;
	 
	  assert((Ai > Bj && Ai_1 > Bj) || (Ai < Bj && Ai < Bj_1));
	  // if none of the cases above, then it is either:
	  if (Ai < Bj) {
	    // exclude Ai and below portion
	    // exclude Bj and above portion
		  return findKthSmallest(Arrays.copyOfRange(A, i+1, A.length), m-i-1, B, j, k-i-1);
	  } else {/* Bj < Ai */ 
	    // exclude Ai and above portion
	    // exclude Bj and below portion
	    return findKthSmallest(A, i, Arrays.copyOfRange(B, j+1, B.length), n-j-1, k-j-1);
	  }
	}

	public static void main(String[] args) {
		int[] a = { 0, 1, 4, 5, 7, 9 };
		int[] b = { 3, 4, 5, 9, 11 };
		int result = findKthSmallest(a, 0, b, 0, 5);
		System.out.println("result: " + result);
	}
}
