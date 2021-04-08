package com.jonas.jnj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubsetFinder {
	
	Map<String, Set<List<Integer>>> memo = new HashMap<String, Set<List<Integer>>>();

	public static int CACHE_HITS = 0;
	
	public Set<List<Integer>> nSumByRange(int smallest, int largest, int target,
			int n) throws AssertionError {
		
		assert ((smallest * n) >= target
				&& target <= (largest * n)) : "Cannot be formed";

		Set<List<Integer>> subsets = new HashSet<List<Integer>>();
		
		if (n == 1) {
			List<Integer> l = new ArrayList<Integer>();
			l.add(target);
			subsets.add(l);
		} else {
			int begin = target - (largest * (n - 1));
			for (int i = Math.max(begin, smallest); i <= largest; i++) {
				if ((i + ((n - 1) * largest) < target) 
						|| i + ((n - 1) * smallest) > target) {
					//not possible to get there
					break;
				} else {
					try {
						//probably should cache these, go with DP solution
						Set<List<Integer>> recursed = nSumByRange(smallest, largest,
								target - i, n - 1);
						for (List<Integer> l : recursed) {
							l.add(0, i);
							subsets.add(l);
						}
					} catch (AssertionError e) {
						//no-op
					}
				}
			}	
		}
		
		return subsets;
	}
	
	public Set<List<Integer>> nSumByRangeDP(int smallest, int largest, int target,
			int n) throws AssertionError {
		
		assert ((smallest * n) >= target
				&& target <= (largest * n)) : "Cannot be formed";

		Set<List<Integer>> subsets;
		
		if (n == 1) {
			subsets = new HashSet<List<Integer>>();
			List<Integer> l = new ArrayList<Integer>();
			l.add(target);
			subsets.add(l);
			return subsets;
		} else if ((subsets = memo.get(target + ":" + n)) != null) {
			CACHE_HITS++;
			return subsets;
		} else {
			subsets = new HashSet<List<Integer>>();
			int begin = target - (largest * (n - 1));
			for (int i = Math.max(begin, smallest); i <= largest; i++) {
				if ((i + ((n - 1) * largest) < target) 
						|| i + ((n - 1) * smallest) > target) {
					//not possible to get there
					break;
				} else {
					try {
						Set<List<Integer>> recursed = nSumByRangeDP(smallest, largest,
									target - i, n - 1);	
						
						for (List<Integer> l : recursed) {
							l.add(i);
							subsets.add(l);
						}
					} catch (AssertionError e) {
						//no-op
					}
				}
			}	
		}
		memo.put((target + ":" + n), subsets);
		return subsets;
	}
	
	public List<List<Integer>> nSumByRangeIterative(int smallest, int largest, int target,
			int n) throws AssertionError {
		
		assert ((smallest * n) >= target
				&& target <= (largest * n)) : "Cannot be formed";
		
		//Generate all possible subsets, and figure out which total target?
		//Permutations approach: lists of size one, combined with itself in all possible configurations, etc? 
		//Intelligently generate all possible subsets, thereby not using too much space? 
		//while(true) { if terminating condition: break; }?
		
		List<List<Integer>> subsets = new ArrayList<List<Integer>>();
		
		for (int i = smallest; i <= largest; i++) {
			List<Integer> list = new ArrayList<>();
			list.add(i);
			subsets.add(list);
		}
		
		for(int i = 1; i < n; i++) {
			while(true) {
				List<Integer> l = subsets.get(0);
				if (l.size() > i) {
					break;
				} else {
					subsets.remove(l);
					for (int j = smallest; j <= largest; j++) {
						for (int k = 0; k <= l.size(); k++) {
							List<Integer> m = new ArrayList<Integer>(l);
							m.add(k, j);
							subsets.add(m);
						}
					}	
				}
			}
		}
				
		return subsets;
	}
}
