package com.jonas.sandbox;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ITextPdfSandbox {
	// Extract text from PDF Document
	static String pdftoText(String fileName) throws Exception {
		PdfReader reader = new PdfReader(fileName);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        StringBuilder text = new StringBuilder(1024);
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            text.append(strategy.getResultantText());
        }
        reader.close();
        return text.toString();
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
