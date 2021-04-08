package com.jonas.wordprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class Base64Processor {

	public static String TARGET_FILE = "C:/1.csv";

	public List<String> sqlString = new ArrayList<>();

	public static String OUTPUT_FILE = TARGET_FILE.substring(0, TARGET_FILE.length() - 4)
			+ "-edited"
			+ TARGET_FILE.substring(TARGET_FILE.length() - 4);

	private final String searchString = "2015 Talksoft - All rights reserved.</td>\n\t\t\t\t\t\t\t\t</tr>";
	private final String searchString2 = "2015 Talksoft - All rights reserved.</td>\n                                                                </tr>";
	private final String searchString3 = "2015 Talksoft - All rights reserved.</td>\r\n                                                                </tr>";
	private final String searchString4 = "&copy; 2015 Talksoft";

	private final String replaceString = "2017 Talksoft - All rights reserved.</td></tr>\r\n\r\n\t\t\t\t\t\t\t\t<tr><td align=\"center\" class=\"text-small align-center\" style=\"color: #555; text-align: center; font-size: 11px; line-height: 1.3; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; margin: 0; padding: 10px 0 0\"><a style=\"text-decoration: none;\" href=\":UNSUBSCRIBE_TXT\">Unsubscribe</a></td></tr>";
	private final String replaceString4 = "&copy; 2017 Talksoft";

	public void specialProcessing(BufferedReader br, PrintWriter out) throws IOException {
		String s;
		int emails = 0;
		int emailsNoMatch = 0;
		int emailsMatch = 0;
		while ((s = br.readLine()) != null) {
			String[] fields = s.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
			if (fields.length > 6 && fields[6].equalsIgnoreCase("'EMAIL'")
					&& fields[7].matches("'.*'")) {
				emails++;

				String base64 = fields[7].substring(1, fields[7].length() - 1);
				byte[] decoded = java.util.Base64.getDecoder().decode(base64.trim());
				StringReader sr = new StringReader(new String(decoded));

				String base64string = IOUtils.toString(sr);
				if (base64string.contains("2015 Talksoft")) {
					System.out.println("hello");
				}
				if (!base64string.contains("UNSUBSCRIBE_TXT")
						&& (base64string.contains(searchString)
								|| base64string.contains(searchString2)
								|| base64string.contains(searchString3))) {
					emailsMatch++;
					base64string = base64string.replace(searchString, replaceString);
					base64string = base64string.replace(searchString2, replaceString);
					base64string = base64string.replace(searchString3, replaceString);

					String encoded = Base64.getEncoder().encodeToString(base64string.getBytes());
					fields[7] = "'" + encoded + "'";
					String sql = "update template set template = " + fields[7]
							+ " where id = " + fields[0].replace("'", "") + ";";
					out.println(sql);
				} else if (base64string.contains(searchString4)) {
					base64string = base64string.replace(searchString4, replaceString4);
					String encoded = Base64.getEncoder().encodeToString(base64string.getBytes());
					fields[7] = "'" + encoded + "'";
					String sql = "update template set template = " + fields[7]
							+ " where id = " + fields[0].replace("'", "") + ";";
					out.println(sql);
				} else {
					emailsNoMatch++;
				}
			}

		}

		System.out.println("Emails: " + emails);
		System.out.println("Emails Match: " + emailsMatch);
		System.out.println("Emails No Match: " + emailsNoMatch);
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

	public static void main(String[] args) {
		Base64Processor sb = new Base64Processor();

		try {
			sb.go();
			System.out.println("Finished Processing");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void go() throws IOException {
		File file = new File(TARGET_FILE);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));

		File output = new File(
				TARGET_FILE.substring(0, TARGET_FILE.length() - 4)
						+ "-edited"
						+ TARGET_FILE.substring(TARGET_FILE.length() - 4));
		FileOutputStream ostream = new FileOutputStream(output);
		PrintWriter out = new PrintWriter(ostream);

		specialProcessing(br, out);

		br.close();
		out.flush();
		out.close();
	}

}
