package com.jonas.interviews;

import java.util.Stack;

import org.junit.Test;

import com.jonas.treees.JonasTreeNode;
import com.jonas.treees.Tree;

/*
 * Find out the fifth maximum element in a Binary Search Tree in an efficient manner
 */

public class FindKthElementInTree {
	
	public static <T> void findKthSmallestInTree(JonasTreeNode node, int num){
	    Stack<JonasTreeNode> stack = new Stack<JonasTreeNode>();
	    JonasTreeNode current = node;
	    int tmp = num;

	    while(stack.size() > 0 || current!=null) {
	        if(current != null){
	            stack.add(current);
	            current = current.getLeft();
	        } else {
	            current = stack.pop();
	            tmp--;
	            if(tmp == 0) {
	                System.out.println(current.getValue());
	                return;
	            }
	            current = current.getRight();
	        }
	    }
	}

	@Test 
	public void findKthSmallestInTree() {
		Tree tree = new Tree (5);
		int toFind = 9;
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
		tree.printTree(tree.getRoot());
		System.out.print("Element number: " + toFind + " in tree: ");
		findKthSmallestInTree(tree.getRoot(), toFind);
	}
}
