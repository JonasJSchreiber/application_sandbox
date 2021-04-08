package com.jonas.lifion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Solution {
	public static void main(String args[] ) throws Exception {
		
		int result = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] nks = br.readLine().split("\\s+");
		String[] integers = br.readLine().split("\\s+");
		br.close();
		Set<Integer> values = new HashSet<Integer>();
		if (nks.length >= 2) {
			try {
				int k = Integer.parseInt(nks[1]);
				for(String s : integers) {
					values.add(Integer.parseInt(s));
				}
				
				for (int i : values) {
					if (values.contains(i + k)) {
						result++;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Only enter integers");
				return;
			}
			
		}
		System.out.println(result);
	}
}