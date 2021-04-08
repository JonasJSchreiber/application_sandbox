package com.jonas.interviews;

public class VingereCipher {

	public static String inputText = "something";
	public static String cipherText = "mykey";
	
	public static void main(String[] args) {
		System.out.println(myEncrypt(inputText, cipherText));
		System.out.println(encrypt(inputText, cipherText));
	}
	
	public static String encrypt(String text, final String key) {
        String res = "";
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z') continue;
            res += (char)((c + key.charAt(j++ % key.length()) - 2 * 'a') % 26 + 'a');
        }
        return res;
    }
	
	public static String myEncrypt(String text, final String key) {
		int i = 0;
		char[] out = new char[text.length()];
		char[] cipher = key.toCharArray();
		for (char c : text.toCharArray()) {
			out[i] = (char) ((c % 'a' + cipher[i++ % cipher.length] % 'a') % 26 + 'a');
//			out[i] = (char) ((c + cipher[i++ % cipher.length]) % 'a' % 26 + 'a');
		}
		return new String(out);
	}

}
