package com.jonas.superbowl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BoxEquity {

    public static void main (String[] args) throws IOException {
    	int[][] boxes = new int[10][10];
    	double[][] equities = new double[10][10];
        File file = new File("C:/nfl2015.csv");
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        int count = 0;
        while ((line = buffer.readLine()) != null) {
        	String[] fields = line.split(",");
        	if (fields.length >= 4 && fields[3].matches("\\d+")) {
        		int home = Integer.parseInt(fields[2]) % 10;
        		int away = Integer.parseInt(fields[3]) % 10;
        		boxes[home][away]++;
        		count++;
        	}
        }
        buffer.close();
        for(int i = 0; i < boxes.length; i++) {
    		for(int j = 0; j < boxes[0].length; j++) {
    			equities[i][j] = (double) boxes[i][j]/count;
    		}
    	}
        writeMatrixToCSV(equities);
    }
    
    public static void writeMatrixToCSV(double[][] equities) 
    		throws FileNotFoundException {
    	PrintWriter out = new PrintWriter(new File("C:/Output.csv"));
    	for(int i = 0; i < equities.length; i++) {
    		for(int j = 0; j < equities[0].length; j++) {
    			out.print(equities[i][j] + ",");
    		}
    		out.println();
    	}
    	out.close();
    }
}
