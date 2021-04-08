package com.jonas.interviews;

import java.util.HashSet;

import org.junit.Test;

import com.jonas.sandbox.JonasLinkedList;
import com.jonas.sandbox.JonasLinkedList.JonasNode;

/**
 * Given two linked lists, return the intersection
 */

public class ListIntersection {
	
	public HashSet<Integer> findIntersection(JonasLinkedList a, JonasLinkedList b) {
		HashSet<Integer> set = new HashSet<Integer>();
		JonasNode pointer = a.head;
		HashSet<Integer> intersection = new HashSet<Integer>();
		do {
			set.add(pointer.num);
			pointer = pointer.next;
		} while (pointer.next != null);

		pointer = b.head;
		do {
			if (set.contains(pointer.num)) {
				intersection.add(pointer.num);
			}
			pointer = pointer.next;
		} while (pointer.next != null);
		return intersection;
	}
	
	@Test
	public void findIntersectionTest() {
		JonasLinkedList a = new JonasLinkedList(0);
		for (int i = 1; i <= 100; i++) {
			a.append(i);
		}

		JonasLinkedList b = new JonasLinkedList(90);
		for (int i = 91; i <= 200; i++) {
			b.append(i);
		}

		HashSet<Integer> intersection = findIntersection(a, b);
		for (int i : intersection) {
			System.out.println(i);
		}
	}

}
