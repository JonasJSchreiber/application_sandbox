package com.jonas.interviews;

import org.junit.Test;

import com.jonas.sandbox.JonasLinkedList;
import com.jonas.sandbox.JonasLinkedList.JonasNode;

/**
 * Find the middle of a linked list *
 */

public class FindMiddleOfList {
	
	public int findMiddleOfList(JonasLinkedList a) {
		JonasNode tortoise = a.head;
		JonasNode hare = a.head;
		while (hare != null && hare.next != null) {
			tortoise = tortoise.next;
			hare = hare.next.next;
		}
		return tortoise.num;
	}
	
	@Test
	public void getMiddleOfListTest() {
		JonasLinkedList a = new JonasLinkedList(0);
		for (int i = 1; i <= 10; i++) {
			a.append(i);
		}
		System.out.println("Middle of list: " + findMiddleOfList(a));

	}
}
