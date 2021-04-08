package com.jonas.sandbox;

import java.util.Stack;

public class JonasLinkedList {

	public JonasNode head;

	public JonasLinkedList(int val) {
		head = new JonasNode(val);
	}

	public void append(int val) {
		JonasNode tmpNode = head;
		while (tmpNode.next != null) {
			tmpNode = tmpNode.next;
		}
		tmpNode.next = new JonasNode(val);
	}

	public void insert(int val) {
		JonasNode currentNode = head;
		JonasNode nextNode = head.next;

		if (currentNode.num > val) {
			JonasNode tmpNode = head;
			head = new JonasNode(val);
			head.next = tmpNode;
			return;
		}

		if (nextNode != null && nextNode.num > val) {
			currentNode.next = new JonasNode(val);
			currentNode.next.next = nextNode;
			return;
		}

		while (nextNode != null && nextNode.num < val) {
			currentNode = nextNode;
			nextNode = nextNode.next;
		}

		currentNode.next = new JonasNode(val);
		currentNode.next.next = nextNode;
	}
	
	public class JonasNode {
		public JonasNode next;
		public int num;

		public JonasNode(int val) {
			num = val;
			next = null;
		}
	}
	
	@SuppressWarnings("unused")
	public void initStack() {
		Stack<Integer> ints = new Stack<Integer>();
	}
}

