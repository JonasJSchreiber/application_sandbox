package com.jonas.interviews;

import java.util.ArrayList;

import org.junit.Test;

import com.jonas.treees.JonasTreeNode;
import com.jonas.treees.Tree;

/*
 * How do you put a Binary Search Tree in an array in an efficient manner
 */
public class BSTtoArray {


	ArrayList<Integer> searchArray = new ArrayList<Integer>();
	public ArrayList<Integer> storeBinarySearchTree(JonasTreeNode root) {
		inorderTraversal(root);
		return searchArray;
	}
	
	public void inorderTraversal(JonasTreeNode root) {
		if (root == null) {
			return;
		} 
		inorderTraversal(root.getLeft());
		searchArray.add(root.getValue());
		inorderTraversal(root.getRight());
		
	}
	
	@Test
	public void getArrayFromBST() {
		Tree tree = new Tree (5);
		tree.addToTree(tree.getRoot(), 3);
		tree.addToTree(tree.getRoot(), 1);
		tree.addToTree(tree.getRoot(), 4);
		tree.addToTree(tree.getRoot(), 2);
		tree.addToTree(tree.getRoot(), 0);
		tree.addToTree(tree.getRoot(), 8);
		tree.addToTree(tree.getRoot(), 6);
		tree.addToTree(tree.getRoot(), 7);
		tree.addToTree(tree.getRoot(), 10);
		tree.addToTree(tree.getRoot(), 9);
		tree.addToTree(tree.getRoot(), 3);
		ArrayList<Integer> array = storeBinarySearchTree(tree.getRoot());
		for (int i : array) {
			System.out.println(i);
		}
	}
}
