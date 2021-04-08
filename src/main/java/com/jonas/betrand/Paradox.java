package com.jonas.betrand;

import java.awt.geom.Point2D;
import java.util.Random;

public class Paradox {
	final static double ROOT_THREE = Math.sqrt(3);
	
	public static void main(String[] args) {
		int greater = 0;
		int less = 0;
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			Point2D.Double a = getRandomPoint();
			Point2D.Double b = getRandomPoint();

			//pythagorean
			if (Math.abs(a.x - b.x) < 1 && Math.abs(a.y - b.y) < 1) {
				less++;
			} else {
				if (Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2)) > ROOT_THREE) {
					greater++;
				} else {
					less++;
				}
			}	
		}
		System.out.println("Time elapsed: " + ((double)(System.currentTimeMillis() - start)/1000) + "s");
		System.out.println("Probability Observerd: " + (double)greater/(greater+less));
	}
	
	public static Point2D.Double getRandomPoint() {
		//get an x such that -1 < x < 1
		double x = Math.random();
		Random r = new Random();
		if (!r.nextBoolean()) {
			x *= -1;
		}
		
		//circle centered on origin: x^2 + y^2 = r^2. r is 1. 
		double y = Math.sqrt(1 - (Math.pow(x, 2)));
		if (!r.nextBoolean()) {
			y *= -1;
		}
		
		if (r.nextBoolean()) {
			return new Point2D.Double(x, y);
		} else {
			return new Point2D.Double(y, x);
		}
	}
}
