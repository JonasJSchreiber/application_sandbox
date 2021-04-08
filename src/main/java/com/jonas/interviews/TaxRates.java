package com.jonas.interviews;

import java.util.LinkedHashMap;
import java.util.Map;

public class TaxRates {

	public static int salary = 50000;
	
	public static Map<Integer, Double> taxrates =
			new LinkedHashMap<Integer, Double>(); // only necessary change 
	
	public static void main(String[] args) {
		int tempSalary = salary;
		double taxes = 0.0;
		initializeTaxRateMap();
		for (Map.Entry<Integer, Double> e : taxrates.entrySet()) {
			if (tempSalary > e.getKey()) {
				taxes += e.getKey() * e.getValue();
				tempSalary -= e.getKey();	
			} else {
				taxes += tempSalary * e.getValue();
				break;
			}
		}
		System.out.println("Total Taxes Paid: " + taxes);
		System.out.println("Marginal Tax Rate: " + (double) (taxes/salary));
	}
	
	public static void initializeTaxRateMap() {
		taxrates.put(9275, 0.10);
		taxrates.put(37650, 0.15); 
		taxrates.put(91150, 0.25);
		taxrates.put(190150, 0.28);
		taxrates.put(413350, 0.33);
		taxrates.put(415050, 0.35);
		taxrates.put(Integer.MAX_VALUE, 0.396);
	}
}
