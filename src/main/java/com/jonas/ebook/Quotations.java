package com.jonas.ebook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class Quotations {

	public static String EBOOK = "C:/PerfLogs/1.epub";
	private static final char openSingleQuote = '‘';
	private static final char closeSingleQuote = '’';
	private static final char openDoubleQuote = '“';
	private static final char closeDoubleQuote = '”';
	private static final String candidateCloseQuotePattern = ".*‘[^”]*’[^A-Za-z].*";
	private static final String candidateUndo = ".*”[^‘\\s]*”.*";
	private static final String correctionCandidate = ".*“[^”]*“.*";
	
	public static String specialProcessing(String s) {
		if (s.indexOf(openSingleQuote) != -1 && openSingleQuoteBeforeOpenDoubleQuote(s)) {
			s = preprocess(s);
			int i = 0;
			while (s.matches(candidateCloseQuotePattern)) {
				i = s.indexOf(closeSingleQuote, i + 1);
				if (i == -1) {
					break;
				}
				if (isAQualifyingClosedSingleQuote(s, i)) {
					// is there a better i?
					int j = s.indexOf(closeSingleQuote, i + 1);
					if (isAQualifyingClosedSingleQuote(s, j)) {
						s = s.substring(0, j) + closeDoubleQuote + s.substring(j + 1, s.length());
					} else {
						s = s.substring(0, i) + closeDoubleQuote + s.substring(i + 1, s.length());
					}

				}
			}
			int offset = 0;
			if (s.matches(candidateUndo)) {
				i = s.indexOf(closeDoubleQuote, offset);
				if (i != -1) {
					// if the character preceding or following the i is a character then start
					// search from that character
					while (isCharacterPrecededOrFollowedByALowerCaseCharacter(s, i)) {
						if (s.indexOf(closeDoubleQuote, i) != -1) {
							i = s.indexOf(closeDoubleQuote, i);
						}
					}
					s = s.substring(0, i) + closeSingleQuote + s.substring(i + 1, s.length());
				}
			}
			s = postprocess(s);
			return postprocess2(s);
		}
		return s;
	}

	public static boolean isAQualifyingClosedSingleQuote(String s, int i) {
		return i > s.indexOf(openSingleQuote)
				&& !isCharacterSandwichedByTwoCharacters(s, i)
				&& isCharacterPrecededOrFollowedByALowerCaseCharacter(s, i)
				&& isCharacterNotPrecededByAnUpperCaseCharacter(s, i)
				&& isCharacterNotPrecededByASpace(s, i);
	}

	public static boolean isCharacterSandwichedByTwoCharacters(String s, int i) {
		return isACharacter(s, i - 1) && isACharacter(s, i + 1);
	}

	public static boolean isACharacter(String s, int i) {
		return (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
				|| (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z');
	}


	public static boolean isCharacterPrecededOrFollowedByALowerCaseCharacter(String s, int i) {
		return s.charAt(i - 1) >= 'a' || s.charAt(i - 1) <= 'z'
				|| s.charAt(i + 1) >= 'a'
				|| s.charAt(i + 1) <= 'z';
	}

	public static boolean isCharacterNotPrecededByAnUpperCaseCharacter(String s, int i) {
		return s.charAt(i - 1) < 'A' || s.charAt(i - 1) > 'Z';
	}

	public static boolean isCharacterNotPrecededByASpace(String s, int i) {
		return s.charAt(i - 1) != ' ';
	}

	public static boolean openSingleQuoteBeforeOpenDoubleQuote(String s) {
		return s.indexOf(openDoubleQuote) == -1
				|| s.indexOf(openSingleQuote) < s.indexOf(openDoubleQuote);
	}

	public static String preprocess(String s) {
		s = s.replace(Character.toString(openDoubleQuote), "||+");
		s = s.replace(Character.toString(closeDoubleQuote), "||-");
		return s;
	}

	public static String postprocess(String s) {
		s = s.replace(openSingleQuote, openDoubleQuote);
		s = s.replace("||+", Character.toString(openSingleQuote));
		s = s.replace("||-", Character.toString(closeSingleQuote));
		return s;
	}

	public static String postprocess2(String s) {
		if (s.matches(correctionCandidate)) {
			int target = s.indexOf(closeSingleQuote, s.indexOf(openDoubleQuote));
			int target2 = s.indexOf(closeSingleQuote, target+1);
			int secondQuote = s.indexOf(openDoubleQuote, s.indexOf(openDoubleQuote) + 1);
			if (target > 0 && secondQuote > 0) {
				if (isAQualifyingClosedSingleQuote(s, target)) s = s.substring(0, target) + closeDoubleQuote + s.substring(target + 1);
				else if (isAQualifyingClosedSingleQuote(s, target2)) s = s.substring(0, target2) + closeDoubleQuote + s.substring(target2 + 1);
			}
		}
		return s;
	}

	public static void main(String[] args) throws IOException {
		String outfile = EBOOK.replace(".epub", "-edited.epub").replace("C:/", "C:/");
		ZipFile zipFile = new ZipFile(EBOOK);

		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		FileOutputStream fout = new FileOutputStream(outfile);
		ZipOutputStream zout = new ZipOutputStream(fout);
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			ZipEntry ze = new ZipEntry(entry.getName());
			InputStream stream = zipFile.getInputStream(entry);
			zout.putNextEntry(ze);

			if (entry.getName().matches(".*htm.*")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

				StringBuilder sb = new StringBuilder();
				String s;
				while ((s = br.readLine()) != null) {
					sb.append(specialProcessing(s));
				}

				byte[] data = sb.toString().getBytes();
				zout.write(data, 0, data.length);

				br.close();
				stream.close();
			} else {
				IOUtils.copy(stream, zout);
			}
			zout.closeEntry();
		}

		zout.close();
		zipFile.close();
		System.out.println("Finished Processing");
	}

	@Test
	public void test() {
		String s = "<p id=\"hea0001722\" class=\"calibre6\">‘I’m not quite sure,’ he said, looking round the room with the curiosity of someone who had never been in a police cell before, despite the fact that he had spent a couple of years brooding over his fraudulent tax activities in the ’Joy. ‘I was in the bank when the Gardaí arrived and, I must admit, when they walked into my office I got a bit of a fright. I thought I was in trouble again! But, no, it was just to tell me that you were being held here and they needed a parent or guardian to be present while they questioned you and I suppose I’m the closest thing to either one of those. How are you anyway, Cyril?’</p>";
		System.out.println(specialProcessing(s));
	}

	@Test
	public void test2() {
		String s = "<p id=\"tit001877\" class=\"calibre10\">I saw the expression on your face harden as you digested this piece of information. Of course, I hadn’t yet spoken to you about staying on at UEA and had no idea how you might feel about it. But you didn’t pursue the conversation and, when George finally let go of your hand, you leaned down to kiss me on the cheek with a ‘Hello, gorgeous’ that was completely out of character for you.</p>";
		System.out.println(specialProcessing(s));
	}

	@Test
	public void test3() {
		String s = "<p class=\"indent\">‘At first, perhaps,’ she admitted, laughing and putting a hand over her mouth to contain her mirth. ‘But I shall meet a wealthy man soon enough. A prince, perhaps. And he will fall in love with <a id=\"page29\"></a>me and we will live together in a palace and I will have all the servants that I require and wardrobes filled with beautiful dresses. I’ll wear different jewellery every day – opals, sapphires, rubies, diamonds – and during the season we will dance together in the throne room of the Winter Palace, and everyone will look at me from morning till night and admire me and wish that they could stand in my place.’</p>";
		System.out.println(specialProcessing(s));
	}

	@Test
	public void test4() {
		String s = "OEBPS/Text/part0002.xhtml";
		if (s.matches(".*htm.*")) {
			System.out.println("true");
		}
	}

	@Test
	public void test5() {
		String s = "<p class=\"indent-para\">‘Given all that, some say calling a false alarm is shameful,’ Commander Ironfist had said. ‘But I say a Blackguard who doesn’t shout a Nine Kill once in their life isn’t working on edge. We protect the most important people in the world. Work on edge.’</p>";
		System.out.println(specialProcessing(s));
	}

	@Test
	public void test6() {
		String s = "<p class=\"para\">‘But you curse well,’ Christophe says, encouragingly. ‘Perhaps the best I have heard. Better than my father, who as you know was a great robber and feared through his province.’</p>";
		System.out.println(specialProcessing(s));
	}

	@Test
	public void test7() {
		String s = "<p class=\"indented\">‘I was going to force her to deal, yeah. I didn’t have anything to lose. Then all this shit happened.’ He flapped his hands at the cell. ‘And Lolo – I mean&#160;.&#160;.&#160;.&#160;fuck!’</p>";
		System.out.println(specialProcessing(s));
	}
}
