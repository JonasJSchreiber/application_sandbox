package com.jonas.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.MultiValueMap;

public class HashMapTesting {
	static Map<String, List<String>> apptLists = new HashMap<String, List<String>>();
	
	public static void main(String[] args) {
		String appt1 = "a,b,c";
		String appt2 = "d,e,f";
		String appt3 = "g,h,i";
		
		String a = "702047";
		String b = "702055";
		addAppt(a, appt1);
		addAppt(a, appt2);
		addAppt(b, appt3);
//		printAppts();
		
		
		MultiValueMap<String, String> map = new MultiValueMap<String, String>();
		map.put("one", "one-a");
		map.put("one", "one-b");
		map.put("two", "two-a");
		
		for(Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(map.getCollection("one").iterator().next());
			for (String s : map.getCollection(entry.getKey())) {
				System.out.println(entry.getKey() + " : " + s);
			}
		}
	}
	
    public static void addAppt(String acct, String appt) {
        List<String> appts = HashMapTesting.apptLists.get(acct);
        if (appts == null) {
            apptLists.put(acct, appts = new ArrayList<String>());
        }
        appts.add(appt);
        System.out.println(apptLists.get("700300"));
        System.out.println(apptLists.get("702047"));
    }
    
    public static void printAppts() {
    	System.out.println("Account Appointment");
    	for (String s : apptLists.keySet()) {
    		if (s != null) {
    			List<String> appts = apptLists.get(s);
    			for(String t : appts) {
    				System.out.println(s + " " + t);
    			}
    		}
    	}
    }
}
