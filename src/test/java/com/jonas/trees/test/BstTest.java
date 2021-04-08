package com.jonas.trees.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jonas.treees.BST;

public class BstTest {

	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void testInsert() {
		BST tree = initializeTree();		
		BST node = tree.find(6);
		assertNotNull(node);
		assertEquals(4, tree.findMaxDepth());
	}
	
	@Test 
	public void testRemoveLeaf() {
		BST tree = new BST(3);
		tree.insert(4);
		tree.remove(3);
		tree.printTree();
	}
	
	@Test
	public void testRemovesQuick() {
		BST tree = initializeTree();	
		tree.remove(3);
		tree.remove(1);
		tree.remove(0);
		tree.remove(4);
		tree.remove(2);
		tree.remove(5);
		tree.printTree();
	}
	
	@Test
	public void testRemoves() {
		BST tree = initializeTree();		
		tree.printTree();
		tree.remove(3);
		tree.printTree();
		tree.remove(1);
		tree.printTree();
		tree.remove(0);
		tree.printTree();
		tree.remove(4);
		tree.printTree();
		tree.remove(2);
		tree.printTree();
		tree.remove(5);
		tree.printTree();
		tree.insert(3);
		tree.printTree();
	}
	
	@Test
	public void testCreateBigTree() {
		Random r = new Random();
		BST tree = new BST(5000);
		for (int i = 0; i < 100; i++) {
			tree.insert(r.nextInt(10000));
		}
		tree.printTree();
	}
	
	private BST initializeTree() {
		BST tree = new BST(5);
		tree.insert(3);
		tree.insert(1);
		tree.insert(4);
		tree.insert(2);
		tree.insert(0);
		tree.insert(8);
		tree.insert(6);
		tree.insert(10);
		tree.insert(5);
		tree.insert(7);
		tree.insert(9);
		return tree;
	}
}
