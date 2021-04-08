package com.jonas.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameClasses {

	public static void main(String[] args) {
		File folder = new File(
				"C:/Workspace/tsoft_xserver-trunk/src/test/java/com/talksoft/server/test/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				modifyClassName(listOfFiles[i]);
			}
		}
	}
	public static void modifyClassName(File f) {
		String oldFileName = f.getAbsolutePath();
		String tmpFileName = f.getAbsolutePath() + ".tmp";

		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(oldFileName));
			bw = new BufferedWriter(new FileWriter(tmpFileName));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("public class")) {
					String s = getStringByPattern(line, "[A-Z][^ ]*");
					line = line.replace(s, f.getName().replaceAll(".java", ""));
				}

				bw.write(line + "\n");
			}
		} catch (Exception e) {
			return;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				//
			}
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				//
			}
		}
		// Once everything is complete, delete old file..
		File oldFile = new File(oldFileName);
		oldFile.delete();

		// And rename tmp file's name to old file name
		File newFile = new File(tmpFileName);
		newFile.renameTo(oldFile);

	}

	public static String getStringByPattern(String s, String pattern) {
		String result = "";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		if (m.find()) {
			result = s.substring(m.start(), m.end());
		}
		return result.trim();
	}

}
