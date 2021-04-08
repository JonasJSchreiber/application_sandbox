package com.jonas.interviews;

import java.util.Arrays;

import org.junit.Test;

/*
 * Find the kth element in the union of two sorted arrays in log(n)
 */
public class FindKthSmallestInUnion {
	
	public int findK(int a[],int aTailIndex,int b[], int bTailIndex,int k) {
	    assert (k <= aTailIndex + bTailIndex);
	    
	    if(aTailIndex == 0)
	    	return b[k-1];
	    if(bTailIndex == 0) 
	    	return a[k-1];
	    if(k == 1)
	    	return Math.min(a[0],b[0]);
	    
	    int subK;
	    
	    if (k % 2 == 0) {
	    	subK = k/2;
	    } else {
	    	subK = (k-1) / 2;
	    }
	    
	    int aSub = Math.min(subK,aTailIndex);
	    int bSub = Math.min(subK,bTailIndex);
	    
	    if(a[aSub-1] < b[bSub-1])
	        return findK(Arrays.copyOfRange(a, aSub, a.length), aTailIndex-aSub, b, bTailIndex, k-aSub);
	    else 
	    	return findK(a,aTailIndex,Arrays.copyOfRange(b,  bSub, b.length),bTailIndex-bSub,k-bSub);
	}
	
	@Test
	public void findKthSmallestInUnion() {
		int[] a = { 0, 2, 4, 6, 7, 8, 10, 16, 18, 24, 33, 56 };
		int[] b = { 1, 2, 8, 11, 14, 16, 36};
		int k = 2;
		System.out.println(k + "th smallest in union: " + findK(a, a.length, b, b.length, k));
		k = 12;
		System.out.println(k + "th smallest in union: " + findK(a, a.length, b, b.length, k));
		k = 19;
		System.out.println(k + "th smallest in union: " + findK(a, a.length, b, b.length, k));
	}
}
