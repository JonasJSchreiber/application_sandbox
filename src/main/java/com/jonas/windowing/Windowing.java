package com.jonas.windowing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Windowing {
	class Product {
		String ID;
		int cnt;

		Product(String id, int c) {
			ID = id;
			cnt = c;
		}
	}

	Map<String, Integer> map = new HashMap<String, Integer>();
	Comparator<Product> comparator = new ProductComparator();
	Queue<Product> minTen = new PriorityQueue<Product>(10, comparator);

	void add(String product) {
		int cnt = 0;
		if (map.containsKey(product)) {
			map.put(product, cnt = map.get(product) + 1);
		} else
			map.put(product, cnt = 1);
		if (minTen.size() == 10)
			minTen.poll();
		minTen.add(new Product(product, cnt));
	}
	
	public class ProductComparator implements Comparator<Product> {
	    public int compare(Product x, Product y) {
	       return x.cnt - y.cnt;
	    }
	}

	List<String> top10() {
		List<String> result = new ArrayList<String>();

		for (Product p : minTen) {
			result.add(p.ID);
		}

		return result;
	}
}
