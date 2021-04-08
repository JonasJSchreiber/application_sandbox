package com.jonas.google;

import java.util.ArrayList;
import java.util.Comparator;

public class Interview1 {
	public static class Person {
		
		public static Person createPerson(int height, int index) {
			Person p = new Person();
			p.setHeight(height);
			p.setIndex(index);
			return p; 
		}
		int height;
		   
		int index;

		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
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
	
	public static Person[] sortByHeightAndOrder(ArrayList<Person> people) {
		Person[] persons = new Person[people.size()];
		for (Person p : people) {
			int tempIndex = p.getIndex();
			for (int i = 0; i < persons.length; i++) {
				if (persons[i] == null || persons[i].getHeight() >= p.getHeight()) {
					tempIndex--;
				}
				if (tempIndex == -1) {
					persons[i] = p;
					break;
				}
			}
		}
		return persons;
	}
}
