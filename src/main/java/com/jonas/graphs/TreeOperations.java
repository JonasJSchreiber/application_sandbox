package com.jonas.graphs;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("unused")
public class TreeOperations {
	List<String> currentSiblings;
	List<String> nextSiblings;
	private Queue<String> queue = new LinkedList<String>();
	private Graph g;
	public String bfs(Graph g, String startingVertex, String targetVertex) {
		this.g = g;
		
		currentSiblings = new ArrayList<String>();
		nextSiblings = new ArrayList<String>();
		currentSiblings.add(startingVertex);
		queue.add(startingVertex);
		String node = null;
		int visited = 0;
		while (!queue.isEmpty()) {
			visited++;
			
			String vertex = queue.iterator().next();
			queue.remove(vertex);
				
			for (String s : g.getNeighbors(vertex)) {
				nextSiblings.add(s);
				if (s.equals(targetVertex)) {
					node = s;
					break;
				} else {
					queue.add(s);
				}
			}
			if (currentSiblings.size() == visited) {
				visited = 0;
				printSiblings(currentSiblings);
				currentSiblings = nextSiblings;
				nextSiblings = new ArrayList<String>();
			}
			if (node != null) {
				break;
			}
			
		}
		printSiblings(currentSiblings);
		return node;
	}
	
	public void printSixLetterStrings(Graph g) {
		ArrayList<String> verticesInGraph = g.getAllVertices();		
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> scrabbleResults = new ArrayList<String>();
		ArrayList<String> validResults = new ArrayList<String>();
		for (String s : verticesInGraph) {
			results.addAll(getStringsFromStartingVertex(g, s));
		}
	    
	    File file = new File("C:/ScrabbleWords.txt");
	    BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String str;
			while ((str = br.readLine()) != null) {
				if (str.length() == 6) {
					scrabbleResults.add(str);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String s : results) {
			 if (scrabbleResults.contains(s) && containsDoubleLetter(s)) {
				 validResults.add(s);
			 }
		}
		Collections.sort(validResults);
		
	    //You've got scrabble words containing all 6 letter scrabble words
	    //You've got lengthSixResults containing all the graph's length six results. 
	    try {
			File lengthSixStrings=new File("C:/validSixLetterStringsWithDoubleLetter.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(lengthSixStrings));
			for (String s : validResults) {
				writer.write(s + "\n");
			}
			
		    //Close writer
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public ArrayList<String> getStringsFromStartingVertex(Graph g, String startingVertex) {
		ArrayList<String> strings = new ArrayList<String>();
		
		ArrayList<String> depthTwoStrings = new ArrayList<String>();
		ArrayList<String> depthThreeStrings = new ArrayList<String>();
		ArrayList<String> depthFourStrings = new ArrayList<String>();
		ArrayList<String> depthFiveStrings = new ArrayList<String>();
		String toAdd = startingVertex;
		for (String s : g.getNeighbors(startingVertex)) {
			depthTwoStrings.add(startingVertex + s);
		}
		
		for (String s : depthTwoStrings) {
			for (String s1 : g.getNeighbors(s.substring(s.length() - 1))) {
				depthThreeStrings.add(s + s1);
			}
		}
		
		for (String s : depthThreeStrings) {
			for (String s1 : g.getNeighbors(s.substring(s.length() - 1))) {
				depthFourStrings.add(s + s1);
			}
		}
		
		for (String s : depthFourStrings) {
			for (String s1 : g.getNeighbors(s.substring(s.length() - 1))) {
				depthFiveStrings.add(s + s1);
			}
		}
		
		for (String s : depthFiveStrings) {
			for (String s1 : g.getNeighbors(s.substring(s.length() - 1))) {
				strings.add(s + s1);
			}
		}
		
		return strings;
	}
	
	public boolean containsDoubleLetter(String s) {
		boolean result = false;
		for (int i = 0; i < s.length() -1; i++) {
			if (s.charAt(i) == s.charAt(i+1)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public void printQueue() {
		for (String vertex : queue) {
			System.out.print(vertex + ", ");
		}
		System.out.println();
	}
	public void printSiblings(List<String> siblings) {
		for (String vertex : siblings) {
			System.out.print(vertex + ", ");
		}
		System.out.println();
	}
	public void representTree(String startingNode) {
		
		System.out.println(startingNode);
		
	}
	
}
