package com.jonas.sandbox;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;

public class PdfxStreamSandbox {
	// Extract text from PDF Document
	static String pdftoText(String fileName) throws Exception {
		Document pdf = PDF.open(fileName);
		StringBuilder text = new StringBuilder(1024);
		pdf.pipe(new OutputTarget(text));
		pdf.close();
		return text.toString();
	}
	
	public static void main(String args[]){
		try {
			System.out.println(pdftoText("data/703091-1.pdf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
