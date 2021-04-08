package com.jonas.symmetry;

import java.util.ArrayList;

public class BinaryClocks {
	
	public int[][] getBinaryClock(String time) {
		int[][] binaryClock = new int[4][4];
		for (int i = 0; i < time.length(); i++) {
			String s = time.substring(i, i+1);
			int j = Integer.parseInt(s);
			int row = 0;
			for (int k = 8; k >= 1; k /= 2) {
				if (j/k >= 1) {
					binaryClock[row][i] = 1;
					j -= k;
				}
				row++;
			}
		}
		return binaryClock; 
	}
	
	public boolean testNorthSouth(int[][] binaryClock) {
		boolean symmetrical = true;
		binaryClock = stripEmptyRowsAndColumns(binaryClock);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < binaryClock[0].length; j++) {
			int multiplier = 1;
			int total = 0;
			for (int i = 0; i < binaryClock.length; i++) {
				total += binaryClock[i][j] * multiplier;
				multiplier *= 2;
			}
			list.add(total);
		}
		if (list.size() > 1) {
			if (list.size() % 2 == 1) {
				symmetrical = list.get(0) == list.get(2);
			} else {
				if (list.size() == 2) {
					symmetrical = list.get(0) == list.get(1);
				} else {
					symmetrical = list.get(0) == list.get(3) && list.get(1) == list.get(2);
				}
			}
		}
		
		return symmetrical;
	} 
	
	public boolean testEastWest(int[][] binaryClock) {
		binaryClock = stripEmptyRowsAndColumns(binaryClock);
		int[][] rotated = new int[binaryClock[0].length][binaryClock.length];
		for (int i = 0; i < binaryClock.length; i++) {
			for (int j = 0; j < binaryClock[0].length; j++) {
				rotated[j][i] = binaryClock[i][j];
			}
		}		
		return testNorthSouth(rotated); 
	} 
	
	public boolean testNorthEast(int[][] binaryClock) {
		boolean symmetrical = true;
		
		ArrayList<Integer> columnsNotEmpty = new ArrayList<Integer>();
		ArrayList<Integer> rowsNotEmpty = new ArrayList<Integer>();
		ArrayList<Integer> emptyColumns = getEmptyColumns(binaryClock);
		ArrayList<Integer> emptyRows = getEmptyRows(binaryClock);
		for (int i = 0; i < binaryClock.length; i++) {
			if (!emptyColumns.contains(i)) {
				columnsNotEmpty.add(i);
			}
			if (!emptyRows.contains(i)) {
				rowsNotEmpty.add(i);
			}
		}
		
		//find first and last non-empty row
		int firstNonEmptyColumn = -1;
		int lastNonEmptyColumn = -1;
		int firstNonEmptyRow = -1;
		int lastNonEmptyRow = -1;
		
		for (int i = 0; i < binaryClock.length; i++) {
			if (columnsNotEmpty.contains(i)) {
				firstNonEmptyColumn = i;
				break;
			}
		}
		
		for (int i = 0; i < binaryClock.length; i++) {
			if (rowsNotEmpty.contains(i)) {
				firstNonEmptyRow = i;
				break;
			}
		}
		
		for (int i = binaryClock.length -1; i >= 0; i--) {
			if (columnsNotEmpty.contains(i)) {
				lastNonEmptyColumn = i;
				break;
			}
		}
		
		for (int i = binaryClock.length -1; i >= 0; i--) {
			if (rowsNotEmpty.contains(i)) {
				lastNonEmptyRow = i;
				break;
			}
		}
		
		int[][] newClock = new int[lastNonEmptyRow-firstNonEmptyRow + 1][lastNonEmptyColumn-firstNonEmptyColumn + 1];
		
		if (newClock.length != newClock[0].length) {
			return false;
		}
		
		for (int i = 0; i < newClock.length; i++) {
			for (int j = 0; j < newClock[0].length; j++) {
				newClock[i][j] = binaryClock[firstNonEmptyRow+i][firstNonEmptyColumn+j];
			}
		}
		
		int[] diagonals = new int[7];
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < newClock.length; i++) {
			for (int j = 0; j < newClock[0].length; j++) {
				if (i + j >= ((newClock.length + newClock[0].length)/2)) {
					diagonals[i + j] += (newClock[i][j] * Math.pow(2, (newClock.length - i - 1)));
				} else {
					diagonals[i + j] += (newClock[i][j] * (Math.pow(2, j)));
				}
			}
		}
		
		
		for (int i : diagonals) {
			list.add(i);
		}
		
		//trim list
		list = removeEmptyLeadingValues(list);
		list = removeEmptyTrailingValues(list);
		
		if (list.size() > 1) {
			if (list.size() % 2 == 1) {
				if (list.size() == 3) {
					symmetrical = list.get(0) == list.get(2);	
				} else if (list.size() == 5) {
					symmetrical = list.get(0) == list.get(4) && list.get(1) == list.get(3);
				} else {
					symmetrical = list.get(0) == list.get(6) && list.get(1) == list.get(5) && list.get(2) == list.get(4);
				}
			} else {
				//impossible
				return false;
			}
		}
		
		return symmetrical; 
	} 
	
	public boolean testNorthWest(int[][] binaryClock) { 
		int[][] mirrored = mirrorClock(binaryClock);
		return testNorthEast(mirrored); 
	} 
	
	public ArrayList<Integer> removeEmptyLeadingValues(ArrayList<Integer> list) {
		boolean found = false;
		ArrayList<Integer> retList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != 0 || found) {
				retList.add(list.get(i));
				found = true;
			}
		}
		return retList;
	}
	
	public ArrayList<Integer> removeEmptyTrailingValues(ArrayList<Integer> list) {
		boolean found = false;
		ArrayList<Integer> retList = new ArrayList<Integer>();
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) != 0 || found) {
				retList.add(list.get(i));
				found = true;
			}
		}
		return reverseList(retList);
	}
	
	public ArrayList<Integer> reverseList(ArrayList<Integer> list) {
		ArrayList<Integer> retList = new ArrayList<Integer>();
		for (int i = list.size() - 1; i >= 0; i--) {
			retList.add(list.get(i));
		}
		return retList;
	}
	
	public int[][] mirrorClock(int[][] binaryClock) {
		int[][] mirrored = new int[binaryClock.length][binaryClock[0].length];
		for (int j = binaryClock[0].length - 1; j >= 0; j--) {
			for (int i = 0; i < binaryClock.length; i++) {
				mirrored[i][binaryClock[0].length - 1 - j] = binaryClock[i][j]; 
			}
		}
		return mirrored;
	}

	public int[][] stripEmptyRowsAndColumns(int[][] binaryClock) { 
		ArrayList<Integer> emptyRows = getEmptyRows(binaryClock);
		ArrayList<Integer> emptyColumns = getEmptyColumns(binaryClock);
		int[][] stripped = new int[binaryClock.length-emptyRows.size()][binaryClock[0].length - emptyColumns.size()];
		int row = 0;
		for (int i = 0; i < binaryClock.length; i++) {
			if (!emptyRows.contains(i)) {
				int column = 0;
				for (int j = 0; j < binaryClock[0].length; j++) {
					if (!emptyColumns.contains(j)) {
						stripped[row][column] = binaryClock[i][j];
						column++;
					}
					
				}
				row++;
			}
		}
		return stripped;
	}
	
	public ArrayList<Integer> getEmptyColumns(int[][] binaryClock) {
		ArrayList<Integer> emptyColumns = new ArrayList<Integer>();
		for (int j = 0; j < binaryClock[0].length; j++) {
			boolean empty = true;
			for (int i = 0; i < binaryClock.length; i++) {
				if (binaryClock[i][j] != 0) {
					empty = false;
					break;
				}
			}
			if (empty) {
				emptyColumns.add(j);
			}
		}
		return emptyColumns;
	}
	
	public ArrayList<Integer> getEmptyRows(int[][] binaryClock) {
		ArrayList<Integer> emptyRows = new ArrayList<Integer>();
		for (int i = 0; i < binaryClock.length; i++) {
			boolean empty = true;
			for (int j = 0; j < binaryClock[0].length; j++) {
				if (binaryClock[i][j] != 0) {
					empty = false;
					break;
				}
			}
			if (empty) {
				emptyRows.add(i);
			}
		}
		return emptyRows;
	}
	
	public void printBinaryClock(int[][] binaryClock) { 
		for (int i = 0; i < binaryClock.length; i++) {
			String s = "";
			for (int j = 0; j < binaryClock[0].length; j++) {
				s += binaryClock[i][j] + " ";
			}	
			System.out.println(s.trim());
		}
	} 

	public boolean[] testSymmetry(String time) {
		boolean[] symmetrical = new boolean[4];  
		int[][] binaryClock = getBinaryClock(time);
		symmetrical[0] = testNorthSouth(binaryClock);
		symmetrical[1] = testEastWest(binaryClock);
		symmetrical[2] = testNorthWest(binaryClock);
		symmetrical[3] = testNorthEast(binaryClock);
		return symmetrical;
	}

}
