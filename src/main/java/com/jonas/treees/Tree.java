package com.jonas.treees;

public class Tree {
	JonasTreeNode root;

	public Tree(int val) {
		root = new JonasTreeNode(val);
	}
	
	public JonasTreeNode addToTree(JonasTreeNode jonasTreeNode, int val) {
		if (jonasTreeNode == null) {
			
		} else if (jonasTreeNode.getValue() == val) {
			
		} else if (jonasTreeNode.getValue() > val) {
			if (jonasTreeNode.getLeft() == null) {
				jonasTreeNode.setLeft(new JonasTreeNode(val));
			} else {
				jonasTreeNode.setLeft(addToTree(jonasTreeNode.getLeft(), val));
			}
		} else if (jonasTreeNode.getValue() < val) { 
			if (jonasTreeNode.getRight() == null) {
				jonasTreeNode.setRight(new JonasTreeNode(val));
			} else {
				jonasTreeNode.setRight(addToTree(jonasTreeNode.getRight(), val));
			}
		}
		return jonasTreeNode;
	}
	
	public void printTree(JonasTreeNode node) {
		if (node != null) 
			System.out.println(node.getValue());
		if (node.getLeft() != null) {
			System.out.print("Left Branch: ");
			printTree(node.getLeft());
		} if (node.getRight() != null) { 
			System.out.print("Right Branch: ");
			printTree(node.getRight());
		}
	}
	
	public JonasTreeNode getRoot() {
		return root;
	}

	public void setRoot(JonasTreeNode root) {
		this.root = root;
	}

}
