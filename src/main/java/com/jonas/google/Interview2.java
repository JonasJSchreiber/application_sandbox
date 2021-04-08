package com.jonas.google;

public class Interview2 {
	public static class Point {
	    int x;
	    int y;
	}
	
	public static Point findHighestPointBeta(int[][] continent) {
	    int altitude = continent[0][0];
	    boolean ascending = true;
	    Point p = new Point();
		int rowToStart = 0;
		int j = 0;
		int i = 0;
		//answer originally given, debugged
		while (j < continent[0].length-1) {
			if (ascending) {
				if (continent[i][j+1] > altitude) {
					//move east
					j++;
					altitude = continent[i][j];
				} else {
					if (i + 1 < continent.length && continent[i+1][j] > altitude) {
						//move south
						i++;
						altitude = continent[i][j];
					} else if (i - 1 >= 0 && continent[i-1][j] > altitude) {
						//move north
						i--;
						altitude = continent[i][j];
					} else {
						//north, south, and east all lower
						p.x = j;
						p.y = i;
						ascending = false;
					}
				}
			} 
			if (!ascending) {
				if (continent[i][j+1] < altitude) {
					//move east
					altitude = continent[i][j+1];
					j++;
				} else {
					if (i + 1 < continent.length && continent[i+1][j] < altitude) {
						//move south
						i++;
						altitude = continent[i][j];
					} else if (i - 1 >= 0 && continent[i-1][j] < altitude) {
						//move north 
						i--;
						altitude = continent[i][j];
					} else {
						//north, south, and east from all higher - restart from next row
						
						//FIXME: Need to backtrack here!
						// - Add current point to a vector of points and pop?
						// - How to keep track of neighbors visited? 
						
						if (rowToStart+1 < continent.length) {
							rowToStart++;
							i = rowToStart;
							j = 0;
							ascending = true;
						} else {
							return null;
						}
					}
				}
			}
		}
		return p;
	}
	
	public static Point findHighestPoint(int[][] continent) {
	    boolean ascending = true;
		return highestPointHelper(continent, 0, 0, 0, ascending, null);
	}
	
	public static Point highestPointHelper(int[][] continent, int i, int j, int rowToStart, boolean ascending, Point p) {
		
		Point returnPoint = null;
		while (j < continent[0].length-1) {
			int altitude = continent[i][j];
			
			if (testPoint(continent, i, j+1, altitude, ascending)) { //try east
				if (j+1 == continent[0].length-1) { //satisfies condition 
					return p; 
				} else {
					// start new instance, while not losing scope of current one
					returnPoint = highestPointHelper(continent, i, j+1, rowToStart, ascending, p); 
					if (returnPoint != null) {
						return returnPoint;
					}
				}
			} 
			
			if (i + 1 < continent.length && testPoint(continent, i+1, j, altitude, ascending)) { //try south
				returnPoint = highestPointHelper(continent, i+1, j, rowToStart, ascending, p);
				if (returnPoint != null) {
					return returnPoint;
				}
			} 
			
			if (i - 1 >= 0 && testPoint(continent, i-1, j, altitude, ascending)) { //try north
				returnPoint = highestPointHelper(continent, i-1, j, rowToStart, ascending, p);
				if (returnPoint != null) {
					return returnPoint;
				}
			} 
			
			if (returnPoint == null) { //cannot find a satisfactory neighbor
				if (ascending) { // highest point along current route, time to descend. 
					p = new Point();
					p.x = j;
					p.y = i;
					ascending = false;
				} else {
					if (rowToStart+1 < continent.length) {
						rowToStart++;
						i = rowToStart;
						return highestPointHelper(continent, i, 0, rowToStart, true, p);
					} else {
						return null;
					}
				}
			}
			
		}		
		return p;
	}

	public static boolean testPoint(int[][] continent, int i, int j, int altitude, boolean ascending) {
		int difference = continent[i][j] - altitude;
		if (ascending && difference > 0) {
			return true;
		} else if (!ascending && difference < 0) {
			return true;
		} else {
			return false;
		}
	}

}
