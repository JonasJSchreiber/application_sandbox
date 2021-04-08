package com.jonas.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class BusRoute {
    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
	
	Set<List<Integer>> visited = new HashSet<>();
    int minimumDistance(int numRows, int numColumns, List<List<Integer>> area)
    {
    	return minimumDistanceFromPoint(numRows, numColumns, area, Arrays.asList(0, 0));
        // WRITE YOUR CODE HERE
    }
    
    int minimumDistanceFromPoint(int numRows, int numColumns, List<List<Integer>> area, List<Integer> point) {
    	visited.add(point);
    	if (point.get(0) == numColumns - 1 && point.get(1) == numRows -1) {
    		return 0;
    	}
    	if (visited.size() == numRows * numColumns) {
    		return -1;
    	}
    	List<List<Integer>> queue = getValidNeighbors(area, numColumns, numRows, point.get(0), point.get(1));
    	for(List<Integer> l : queue) {
    		int steps = minimumDistanceFromPoint(numRows, numColumns, area, l);
    		if (steps != -1) {
    			return 1 + steps;
    		}
    	}
    	return - 1;
    }
    
    List<List<Integer>> getValidNeighbors(List<List<Integer>> area, int numColumns, int numRows, int col, int row) {
    	List<List<Integer>> neighbors = new ArrayList<>();
    	if (boundaryCheck(numColumns, numRows, col - 1, row) && !visited.contains(Arrays.asList(col - 1, row)) && area.get(col - 1).get(row) > 0) {
    		neighbors.add(Arrays.asList(col - 1, row));
    	}
    	if (boundaryCheck(numColumns, numRows, col + 1, row) && !visited.contains(Arrays.asList(col + 1, row)) && area.get(col + 1).get(row) > 0) {
    		neighbors.add(Arrays.asList(col + 1, row));
    	}
    	if (boundaryCheck(numColumns, numRows, col, row - 1) && !visited.contains(Arrays.asList(col, row - 1)) && area.get(col).get(row - 1) > 0) {
    		neighbors.add(Arrays.asList(col, row - 1));
    	}
    	if (boundaryCheck(numColumns, numRows, col, row + 1) && !visited.contains(Arrays.asList(col, row + 1)) && area.get(col).get(row + 1) > 0) {
    		neighbors.add(Arrays.asList(col, row + 1));
    	}
    	return neighbors;
    }
    
    boolean boundaryCheck(int numColumns, int numRows, int col, int row) {
    	return col >= 0 && col < numColumns && row >= 0 && row < numRows;
    }
    
    @Test
    public void test() {
    	List<List<Integer>> area = new ArrayList<>();
    	area.add(Arrays.asList(1, 0, 0));
    	area.add(Arrays.asList(1, 0, 0));
    	area.add(Arrays.asList(1, 9, 1));
    	System.out.println(minimumDistance(3, 3, area));
    	
    	area = new ArrayList<>();
    	area.add(Arrays.asList(1, 0, 0));
    	area.add(Arrays.asList(0, 0, 0));
    	area.add(Arrays.asList(1, 9, 1));
    	System.out.println(minimumDistance(3, 3, area));
    	

    	area.add(Arrays.asList(1, 0, 1, 1, 1));
    	area.add(Arrays.asList(1, 0, 1, 0, 1));
    	area.add(Arrays.asList(1, 9, 1, 0, 3));
    	System.out.println(minimumDistance(5, 3, area));
    }
    
    
}
