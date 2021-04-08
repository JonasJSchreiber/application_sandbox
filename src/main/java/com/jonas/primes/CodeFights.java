package com.jonas.primes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

public class CodeFights {
	
	@Test
	public void runTests() {
		System.out.println(primeSum(11, 2));
	}
	
	Set<Integer> getPrimesBelow250() {
		List<Integer> primeList = new CopyOnWriteArrayList<>();
		primeList.add(2);
	    for (int i = 3; i < 250; i++) {
	        boolean isPrime = true;
	        for (Integer j : primeList) {
	        	if ((i % j) == 0) {
	                isPrime = false;
	                break;
	            }
	        }
            if (isPrime) {
            	primeList.add(i);
            }
	    }
	    Set<Integer> primes = new HashSet<>();	
	    for (Integer i : primeList) {
	    	primes.add(i);
	    }
	    return primes;
	}

	boolean primeSum(int n, int k) {
	    Set<Integer> primes = getPrimesBelow250();
	    return primeSums(n, k, primes);
	}

	boolean primeSums(int n, int k, Set<Integer> primes) {
	    if (k == 1) {
	            return primes.contains(n);
	    }
	    for (Integer i : primes) {
	        if (i < n && primeSums(n - i, k -1, primes)) {
	               return true;
	        } else if (i > n) {
	                return false;
	        }
	    }
	    return false;
	}



}
