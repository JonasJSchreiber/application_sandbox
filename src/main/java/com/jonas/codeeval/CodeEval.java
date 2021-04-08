package com.jonas.codeeval;

import java.io.*;

public class CodeEval {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
           String[] ints = line.split(" ");
			int[] xyn = new int[ints.length];
			for (int i = 0; i < ints.length; i++) {
				xyn[i] = Integer.parseInt(ints[i]);
			}
			String row = "";
			for (int i = 1; i <= xyn[2]; i++) {
				String result = "";
				boolean fizzState = (i % xyn[0] == 0) ? true : false;
				boolean buzzState = (i % xyn[1] == 0) ? true : false;
				if (!(fizzState || buzzState)) {
					result = i + " ";
				} else {
					if (fizzState && buzzState) {
						result = "FB ";
					} else if (fizzState) {
						result = "F ";
					} else if (buzzState) {
						result = "B ";
					}
				}
				row += result;

			}
			System.out.println(row.trim());
        }
        buffer.close();
    }
}