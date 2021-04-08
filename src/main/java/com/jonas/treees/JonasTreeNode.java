package com.jonas.treees;

public class JonasTreeNode {
	JonasTreeNode left;
	JonasTreeNode right;
	int value;

	public JonasTreeNode(int val) {
		value = val;
		left = null;
		right = null;
	}
	
	public JonasTreeNode getLeft() {
		return left;
	}

	public void setLeft(JonasTreeNode left) {
		this.left = left;
	}

	public JonasTreeNode getRight() {
		return right;
	}

	public void setRight(JonasTreeNode right) {
		this.right = right;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

