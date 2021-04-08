package com.jonas.interviews;

public class BinaryRep {

	public static int input = 5;
	
	public static void main(String[] args) {
		System.out.println(integerToBinary(input));
		System.out.println(bitwiseIntegerToBinary(input));
		System.out.println(Integer.toBinaryString(input));
		System.out.println(Integer.toString(input, 2));
	}
	
	public static String integerToBinary(int x) {
		char[] out = "0000000000000000000000000000000".toCharArray();
		int i = 1073741824;
		int j = -1;
		while (x > 0) {
			if (x / i == 1) {
				out[++j] = '1';
				x -= i;
			} else {
				out[++j] = '0';
			}
			i /= 2;
		}
//		return new String(out).replaceAll("^.0+", "").trim();
		return "0" + new String(out);
	}
	
	public static String bitwiseIntegerToBinary(int input) {
	    char[] bits = new char[32];
	    for (int i = 31; i >= 0; i--) {
	        bits[31 - i] = (input & (1 << i)) != 0 ? '1' : '0';
	    }

	    return new String(bits).trim();
	}
}
