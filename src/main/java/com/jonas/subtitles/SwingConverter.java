package com.jonas.subtitles;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

public class SwingConverter {

	/** Runs a sample program that shows dropped files */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		final Conversion convertsrt = new Conversion();
		javax.swing.JFrame frame = new javax.swing.JFrame("  SRT Converter");
		final String centertext = "\n\n    drag and drop \n    .srt files here";
		final javax.swing.JTextArea text = new javax.swing.JTextArea(centertext);
		Font f = new Font("Arial", 2, 25);
		Color fg = new Color(80, 80, 80);
		text.setForeground(fg);
		Color bg = new Color(225, 225, 225);
		text.setFont(f);
		text.setBackground(bg);
		frame.getContentPane().add(new javax.swing.JScrollPane(text), java.awt.BorderLayout.CENTER);

		new FileDropHandler(System.out, text, new FileDropHandler.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					File x = files[i];
					convertsrt.convert(x);
				}
			}
		});

		frame.setBounds(200, 200, 250, 210);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
