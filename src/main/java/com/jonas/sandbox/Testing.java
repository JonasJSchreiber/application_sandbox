package com.jonas.sandbox;

public class Testing {

	public static void main(String[] args) {
		Foo a = new Foo();

		Foo b = a;

		a.setX(5);

		b.setX(10);
		System.out.println(a.getX());
		System.out.println(b.getX());
	}

}
