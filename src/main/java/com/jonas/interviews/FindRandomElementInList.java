package com.jonas.interviews;

import java.util.Random;

import org.junit.Test;

import com.jonas.sandbox.JonasLinkedList;
import com.jonas.sandbox.JonasLinkedList.JonasNode;

/**
 * Find a random element in a linked list
 */

public class FindRandomElementInList {
	
	public int findRandomElementInList(JonasLinkedList a) {
		int val = a.head.num;
		int count = 0;
		JonasNode pointer = a.head;
		Random rand = new Random();
		while (pointer.next != null) {
			count++;
			int chance = rand.nextInt(count) + 1;
			if (chance == count) {
				val = pointer.num;
			}
			pointer = pointer.next;

		}
		return val;
	}

	@Test
	public void findRandomElementInListTest() {
		JonasLinkedList a = new JonasLinkedList(0);
		for (int i = 1; i <= 100; i++) {
			a.append(i);
		}
		for (int i = 0; i < 1000; i++) {
			System.out.println(findRandomElementInList(a));
		}
	}
}
