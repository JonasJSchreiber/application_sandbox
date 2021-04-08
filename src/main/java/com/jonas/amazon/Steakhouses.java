package com.jonas.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class Steakhouses {
	
	 // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    List<List<Integer>> nearestXsteakHouses(int totalSteakhouses, 
                                         List<List<Integer>> allLocations, 
                                         int numSteakhouses) {
        return allLocations.stream()
        .filter(l -> l.size() == 2)
        .sorted((l1, l2) -> compareLengths(l1, l2))
        .collect(Collectors.toList()).subList(0, numSteakhouses);
    }
    // METHOD SIGNATURE ENDS

    public int compareLengths(List<Integer> l1, List<Integer> l2) {
    	return ((l1.get(0)*l1.get(0)) + (l1.get(1) * l1.get(1))) - 
    			((l2.get(0)*l2.get(0)) + (l2.get(1) * l2.get(1)));
    }
    
    @Test
    public void test() {
    	List<List<Integer>> l = new ArrayList<>();
    	l.add(Arrays.asList(1, 2));
    	l.add(Arrays.asList(3,4));
    	l.add(Arrays.asList(1, -1));
    	List<List<Integer>> values = nearestXsteakHouses(3, l, 2);
    	values.forEach(System.out::println);
    }
}
