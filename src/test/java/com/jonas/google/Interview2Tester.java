package com.jonas.google;

import org.junit.Test;

import com.jonas.google.Interview2.Point;
import com.jonas.google.Interview2;

public class Interview2Tester {
	@Test
	public void testInterview2Question() {
		int[][] continent = {{3,2,9},{2,2,2},{3,4,3}};
		Point p = Interview2.findHighestPoint(continent);
		if (p != null) {
			int euclidianX = p.x;
			int euclidianY = continent.length - 1 - p.y;
			System.out.println("Highest point along path is: {" + euclidianX + "," + euclidianY + "}, "
					+ "which has an altitude of: " + continent[p.y][p.x]);
		} else {
			System.out.println("Could not find a path which adhered to the constraints of the program");
		}
		p = Interview2.findHighestPointBeta(continent);
		if (p != null) {
			int euclidianX = p.x;
			int euclidianY = continent.length - 1 - p.y;
			System.out.println("Highest point along path is: {" + euclidianX + "," + euclidianY + "}, "
					+ "which has an altitude of: " + continent[p.y][p.x]);
		} else {
			System.out.println("Could not find a path which adhered to the constraints of the program");
		}
	}
	
	@Test 
	public void testInterview2QuestionWithBackTracking() {
		int[][] continent = {{0,3,4,4,-1,-2},{0,-1,2,1,0,2},{0,-1,3,-1,-1,2},{0,-1,-1,-1,-2,2}};
		Point p = Interview2.findHighestPoint(continent);
		if (p != null) {
			int euclidianX = p.x;
			int euclidianY = continent.length - 1 - p.y;
			System.out.println("Highest point along path is: {" + euclidianX + "," + euclidianY + "}, "
					+ "which has an altitude of: " + continent[p.y][p.x]);
		} else {
			System.out.println("Could not find a path which adhered to the constraints of the program");
		}
		p = Interview2.findHighestPointBeta(continent);
		if (p != null) {
			int euclidianX = p.x;
			int euclidianY = continent.length - 1 - p.y;
			System.out.println("Highest point along path is: {" + euclidianX + "," + euclidianY + "}, "
					+ "which has an altitude of: " + continent[p.y][p.x]);
		} else {
			System.out.println("Could not find a path which adhered to the constraints of the program");
		}		
	}
}
