package com.jonas.graphs;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTreeOperations {
	
	public Graph g;
	public TreeOperations treeOps;
	@Before
	public void setup() throws Exception {
		treeOps = new TreeOperations();
		g = new Graph();
		g.addEdge("Y","B");
		g.addEdge("Y","T");
		g.addEdge("Y","N");
		g.addEdge("Y","O");
		g.addEdge("Y","S");
		g.addEdge("B","R");
		g.addEdge("B","L");
		g.addEdge("B","T");
		g.addEdge("B","Y");
		g.addEdge("B","O");
		g.addEdge("R","B");
		g.addEdge("R","L");
		g.addEdge("R","O");
		g.addEdge("R","E");
		g.addEdge("R","C");
		g.addEdge("C","R");
		g.addEdge("C","E");
		g.addEdge("C","G");
		g.addEdge("C","O");
		g.addEdge("C","H");
		g.addEdge("H","C");
		g.addEdge("H","G");
		g.addEdge("H","O");
		g.addEdge("H","W");
		g.addEdge("H","S");
		g.addEdge("S","H");
		g.addEdge("S","W");
		g.addEdge("S","O");
		g.addEdge("S","N");
		g.addEdge("S","Y");
		g.addEdge("T","B");
		g.addEdge("T","L");
		g.addEdge("T","O");
		g.addEdge("T","N");
		g.addEdge("T","Y");
		g.addEdge("L","B");
		g.addEdge("L","R");
		g.addEdge("L","E");
		g.addEdge("L","O");
		g.addEdge("L","T");
		g.addEdge("E","L");
		g.addEdge("E","R");
		g.addEdge("E","C");
		g.addEdge("E","G");
		g.addEdge("E","O");
		g.addEdge("G","O");
		g.addEdge("G","E");
		g.addEdge("G","C");
		g.addEdge("G","H");
		g.addEdge("G","W");
		g.addEdge("W","O");
		g.addEdge("W","G");
		g.addEdge("W","H");
		g.addEdge("W","S");
		g.addEdge("W","N");
		g.addEdge("N","T");
		g.addEdge("N","O");
		g.addEdge("N","S");
		g.addEdge("N","W");
		g.addEdge("N","W");
		g.addEdge("O","Y");
		g.addEdge("O","T");
		g.addEdge("O","L");
		g.addEdge("O","B");
		g.addEdge("O","R");
		g.addEdge("O","E");
		g.addEdge("O","C");
		g.addEdge("O","G");
		g.addEdge("O","H");
		g.addEdge("O","W");
		g.addEdge("O","S");
		g.addEdge("O","N");
		g.addEdge("O","Y");
		g.addEdge("B","B");
		g.addEdge("L","L");
		g.addEdge("R","R");
		g.addEdge("T","T");
		g.addEdge("E","E");
		g.addEdge("Y","Y");
		g.addEdge("O","O");
		g.addEdge("C","C");
		g.addEdge("N","N");
		g.addEdge("G","G");
		g.addEdge("W","W");
		g.addEdge("S","S");
		g.addEdge("H","H");

    }
	
	@After
	public void teardown() throws Exception {
		
	}
	
	@Test
	public void testDFS() {
		String actual = treeOps.bfs(g, "A", "O");
		String expected = "O";
		assertEquals(expected, actual);
	}
	
	@Test
	public void printAllFiveLetterStrings() {
		treeOps.printSixLetterStrings(g);
	}
}
