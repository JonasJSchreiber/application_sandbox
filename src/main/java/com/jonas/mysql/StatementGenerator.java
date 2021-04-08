package com.jonas.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StatementGenerator {

	public static final String INPUT_FILE = "C:/simples.csv";

	public static String OUTPUT_FILE = "C:/Updates.sql";

	public static final String CSV_PATTERN = ",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))";

	public void specialProcessing(BufferedReader br, PrintWriter out) throws IOException {
		String s;
		List<String> verifyIds = new ArrayList<>();
		String deviceId = null;
		String recipId = null;
		s = br.readLine();
		while ((s = br.readLine()) != null) {
			String[] fields = s.split(CSV_PATTERN);
			if (recipId == null) {
				recipId = fields[0];
			}
			if (!fields[0].equals(recipId)) {
				for (String verifyId : verifyIds) {
					String s2 = "update recipient_dest set device_id = '" + deviceId
							+ "' where verify_id = " + verifyId + ";";
					out.println(s2);
				}
				verifyIds = new ArrayList<>();
				deviceId = null;
				recipId = fields[0];
			}
			verifyIds.add(fields[4].replaceAll("\"", ""));
			if (fields[9].replace("\"", "").equals("APP")) {
				deviceId = fields[11].replaceAll("\"", "");
			}
		}
		for (String verifyId : verifyIds) {
			String s2 = "update dest_verify set device_id = '" + deviceId + "' where verify_id = "
					+ verifyId + ";";
			out.println(s2);
		}
	}

	public static void main(String[] args) {
		StatementGenerator sg = new StatementGenerator();

		try {
			sg.go();
			System.out.println("Finished Processing");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void go() throws IOException {
		File file = new File(INPUT_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		File output = new File(INPUT_FILE.substring(0, INPUT_FILE.length() - 4) + "-edited"
				+ INPUT_FILE.substring(INPUT_FILE.length() - 4));
		FileOutputStream ostream = new FileOutputStream(output);
		PrintWriter out = new PrintWriter(ostream);

		specialProcessing(br, out);

		br.close();
		out.flush();
		out.close();
	}

}
