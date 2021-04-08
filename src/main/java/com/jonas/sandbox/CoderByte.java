package com.jonas.sandbox;

public class CoderByte {
	
	public CoderByte() {
		
	}
	
	public String FirstReverse(String str) throws Exception { 
		char[] strChars = str.toCharArray();
		for (int i = 0; i < (str.length() / 2); i++) {
			char temp = strChars[i];
			strChars[i] = strChars[str.length()-1-i];
			strChars[str.length()-1-i] = temp;
		}
		str = String.valueOf(strChars);
		return str;
	} 
	
	public String Manipulate(String str) throws Exception {
	    char[] strChars = str.toCharArray();
	    String vowels = "aeiou";
	    for(int i = 0; i < str.length(); i++) {
	    	char c = str.charAt(i);
	      if (Character.isLetter(c)) {
	         c = (char) (c+1); 
	      }
	      if (vowels.contains(String.valueOf(c))) {
	    	  c = String.valueOf(c).toUpperCase().charAt(0);
	      }
	      strChars[i] = c;
	    }
	    return String.valueOf(strChars);
	}
	
	public int getnnminus1over2(int x) {
		return (x * (x+1))/2;
	}
}
