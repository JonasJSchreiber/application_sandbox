package com.jonas.skyscrapers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Skyscrapers {
	
	public static int boxLW = 6;
	Integer[] top = {3, null, 5, null, 5, null};
	Integer[] right = {null, null, null, null, null, null};
	Integer[] bottom = {null, 3, null, null, null, null};
	Integer[] left = {3, null, 1, 5, null, 3};
	
	
	
	public static void main(String[] args) {
		Skyscrapers s = new Skyscrapers();
		s.go();
	}
	
	/**
	 * 
	 */
	public void go() {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= boxLW; i++) {
			list.add(i);
		}
		List<List<Integer>> perms = generatePerms(list);
		Map<Integer, List<List<Integer>>> possibilities= new HashMap<>();
		possibilities.put(null, perms);
		for (int i = 1; i <= boxLW; i++) {
			possibilities.put(i, getValidSolutions(i, perms));
		}
		List<List<List<Integer>>> rows = new CopyOnWriteArrayList<>();
		for (int i = 0; i < boxLW; i++) {
			if (right[i] != null) {
				rows.add(i, invertLists(possibilities.get(right[i])));
			} else {
				rows.add(i, possibilities.get(left[i]));
			} 
		}
		
		//now we have a List of possible perms for each row. 
		//lets run it through a sieve
		for (int i = 0; i < boxLW; i++) {
			if (bottom[i] != null || top[i] != null) {
				List<List<Integer>> sieve = new ArrayList<>();
				if (bottom[i] != null) {
					sieve = invertLists(possibilities.get(bottom[i]));
				} else if (top[i] != null) {
					sieve = possibilities.get(top[i]);
				}
				
				//what are the possibilities for each row's j column?
				List<Set<Integer>> possibilitiesByRow = new ArrayList<>();
				for (int j = 0; j < boxLW; j++) {
					possibilitiesByRow.add(new HashSet<Integer>());
				}
				
				for (List<Integer> l : sieve) {
					for (Integer j : l) {
						possibilitiesByRow.get(l.indexOf(j)).add(j);
					}
				}	
				
				for (Set<Integer> s : possibilitiesByRow) {		
					if (s.size() < boxLW) {	
						//apply filters.
						int row = possibilitiesByRow.indexOf(s);
						List<List<Integer>> toFilter = new CopyOnWriteArrayList<>(rows.get(row));
						List<Integer> filter = new ArrayList<>();
						for (int j = 1; j <= boxLW; j++) {
							if(!s.contains(j)) {
								filter.add(j);
							}
						}
						
						for (List<Integer> perm : toFilter) {
							if (filter.contains(perm.get(i))) {
								toFilter.remove(perm);
								//column is i, row is row. 
								//filter any entry in rows that has k in this column. 
							}
						}
						rows.set(row, toFilter);
					}
				}
			}
		}
	
		List<Integer[][]> boxes = generateBoxes(rows);
		for (Integer[][] box : boxes) {
			if (checkFinalBoxConstraints(box)) {
				printBox(box);
			}
		}
	}
	
	public List<List<Integer>> generatePerms(List<Integer> input) {
		List<List<Integer>> output = new CopyOnWriteArrayList<>();
		return generatePerms(input, output);
	}
	
	public List<List<Integer>> generatePerms(List<Integer> input, List<List<Integer>> list) {
		Set<List<Integer>> set = new HashSet<>();
		List<List<Integer>> output = new ArrayList<>();
		for (Integer i : input) {
			List<Integer> newList = new CopyOnWriteArrayList<Integer>();
			newList.add(i);
			list.add(newList);
		}
		for (Integer i : input) {
			for (List<Integer> l : list) {
				if (l.contains(i)) {
					continue;
				}
				list.remove(l);
				for (int j = 0; j <= l.size(); j++) {
					List<Integer> newList = new CopyOnWriteArrayList<Integer>(l);
					newList.add(j, i);
					if (newList.size() == boxLW) {
						set.add(newList);
					} else {
						list.add(newList);	
					}
				}
			}
		}
		output.addAll(set);
		return output;
	}
	
	public List<List<Integer>> getValidSolutions(int i, List<List<Integer>> perms) {
		List<List<Integer>> valids = new ArrayList<>();
		for (List<Integer> l : perms) {
			if (isValid(i, l)) {
				valids.add(l);
			}
		}
		return valids;
	}
	
	public List<List<Integer>> invertLists(List<List<Integer>> perms) {
		for (List<Integer> l : perms) {
			Collections.reverse(l);
		}
		return perms;
	}
	
	public boolean isValid(int numVisible, List<Integer> list) {
		for (Integer entry : list) {
			boolean visible = true;
			for (int i = 0; i < list.indexOf(entry); i++) {
				if (list.get(i) > entry) {
					visible = false;
					break;
				}
			}
			if (visible) {
				numVisible--;
			}
		}
		return (numVisible == 0);
	}
	
	public List<Integer[][]> generateBoxes(List<List<List<Integer>>> rows) {
		List<Integer[][]> boxes = new CopyOnWriteArrayList<>();
		for (int i = 0; i < boxLW; i++) {
			List<List<Integer>> row = rows.get(i);
			
			if (boxes.size() == 0) {
				for(List<Integer> l : row) {
					Integer[][] box = new Integer[boxLW][boxLW];
					int k = 0;
					for (Integer j : l) {
						box[i][k] = j;
						k++;
					}
					boxes.add(box);
				}
			} else {
				List<Integer[][]> boxesToAdd = new CopyOnWriteArrayList<>();
				for (Integer[][] box : boxes) {
					boxes.remove(box);
					for(List<Integer> l : row) {
						Integer[][] newBox = box.clone();
						int k = 0;
						for (Integer j : l) {
							newBox[i][k] = j;
							k++;
						}
						boxesToAdd.add(newBox);	
					}
				}
				for(Integer[][] candidate : boxesToAdd) {
					if (candidate != null && checkBoxConstraints(candidate)) {
						boxes.add(candidate);	
					}
				}
			}
			
		}
		return boxes;
	}
	
	public boolean checkBoxConstraints(Integer[][] box) {
		for (int col = 0; col < box.length; col++) {
			List<Integer> column = new ArrayList<>();
			for (int row = 0; row < box[0].length; row++) {
				if (box[row][col] != null) {
					column.add(box[row][col]);	
				}
			}
			if (!checkNoDuplicates(column)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkNoDuplicates(List<Integer> l) {
		Set<Integer> set = new HashSet<>();
		set.addAll(l);
		return set.size() == l.size();
	}
	
	public boolean checkFinalBoxConstraints(Integer[][] box) {
		for (int col = 0; col < box.length; col++) {
			for (int row = 0; row < box[0].length; row++) {
				if (box[row][col] == null) {
					return false;	
				}
			}
		}
		return true;
	}
	
	public void printBox(Integer[][] box) {
		for (int i = 0; i < box.length; i++) {
			for (int j = 0; j < box[0].length; j++) {
				System.out.print(box[i][j] + " ");
			}
			System.out.println();
		}
	}

}
