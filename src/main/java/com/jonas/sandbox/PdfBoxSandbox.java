package com.jonas.sandbox;

import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfBoxSandbox {
	// Extract text from PDF Document
	static String pdftoText(String fileName) throws Exception {
		String parsedText = null;
		File file = new File(fileName);
		
		PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		COSDocument cosDoc = parser.getDocument();
		PDFTextStripper pdfStripper = new PDFTextStripper();
		PDDocument pdDoc = new PDDocument(cosDoc);
		parsedText = pdfStripper.getText(pdDoc);
		
		if (cosDoc != null)
			cosDoc.close();
		if (pdDoc != null)
			pdDoc.close();

		return parsedText;
	}
	
	public static void main(String args[]){
		try {
			System.out.println(pdftoText("data/700269-2.pdf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
