package com.jonas.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import com.jonas.google.Interview1.Person;
public class Interview1Tester {

	@Test
	public void testInterview1Question() {
		ArrayList<Person> input = new ArrayList<Person>();
		input.add(Person.createPerson(7, 1));
		input.add(Person.createPerson(5, 2));
		input.add(Person.createPerson(4, 4));
		input.add(Person.createPerson(6, 1));
		input.add(Person.createPerson(7, 0));
		input.add(Person.createPerson(5, 0));
		Collections.sort(input, new CustomComparator());
		Person[] persons = Interview1.sortByHeightAndOrder(input);
		for (Person p : persons) {
			System.out.println("(" + p.getHeight() + "," + p.getIndex() + "),");
		}
	}
	
	public class CustomComparator implements Comparator<Person> {
	    public int compare(Person o1, Person o2) {
	        if (o1.getHeight() - o2.getHeight() == 0) {
	        	return o1.getIndex() - o2.getIndex();
	        } else {
	        	return o1.getHeight() - o2.getHeight(); 
	        }
	    }
	}
}
