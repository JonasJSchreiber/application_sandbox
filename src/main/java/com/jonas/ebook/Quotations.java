package com.jonas.ebook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
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
	private static final String correctionCandidate = ".*" + closeDoubleQuote + "[^" + openDoubleQuote + "]*" + closeDoubleQuote + ".*";
	
	public static String specialProcessing(String s) {
		if (s.indexOf(openSingleQuote) != -1 && openSingleQuoteBeforeOpenDoubleQuote(s)) {
			s = preprocess(s);
			int i = 0;
			while (s.matches(candidateCloseQuotePattern)) {
				List<Integer> targets = new ArrayList<>();
				int target = s.indexOf(closeSingleQuote, s.indexOf(openDoubleQuote));
				while (target > 0) {
					targets.add(target);
					target = s.indexOf(closeSingleQuote, target+1);
				}
				if (!targets.isEmpty()) {
					String finalS = s;
					targets = targets.stream()
							.sorted(Comparator.comparing(j -> rankCharacterAccordingToLikelinessToBeASingleQuote(finalS, (int) j)).reversed())
							.collect(Collectors.toList());
					target = targets.get(0);
					if (isAQualifyingClosedSingleQuote(s, target)) s = s.substring(0, target) + closeDoubleQuote + s.substring(target + 1);
				}
				i = s.indexOf(closeSingleQuote, i + 1);
				if (i == -1) {
					break;
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
//			return postprocess2Head(s);
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

	public static boolean isCharacterPrecededByAnHtmlCharacterAndFollowedByACharacter(String s, int i) {
		return s.charAt(i-1) == '>' && (s.charAt(i + 1) >= 'a'
				|| s.charAt(i + 1) <= 'z');
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

	public static String postprocess2Head(String s) {
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

	public static String postprocess2(String s) {
		if (s.matches(correctionCandidate)) {
			List<Integer> targets = new ArrayList<>();
			int target = s.indexOf(closeDoubleQuote, s.indexOf(openDoubleQuote));
			while (target > 0) {
				targets.add(target);
				target = s.indexOf(closeDoubleQuote, target+1);
			}
			int secondQuote = s.indexOf(openDoubleQuote, s.indexOf(openDoubleQuote) + 1);
			if (!targets.isEmpty() && secondQuote > 0) {
				targets = targets.stream()
						.sorted(Comparator.comparing(i -> rankCharacterAccordingToLikelinessToBeASingleQuote(s, (int) i)).reversed())
						.collect(Collectors.toList());
				target = targets.get(0);
				return s.substring(0, target) + closeSingleQuote + s.substring(target + 1);
			}
		}
		return s;
	}

	public static Integer rankCharacterAccordingToLikelinessToBeASingleQuote(String s, int i) {
		if (isCharacterSandwichedByTwoCharacters(s, i)) return 0;
		if (isCharacterPrecededByAnHtmlCharacterAndFollowedByACharacter(s, i)) return 2;
		if (isCharacterPrecededOrFollowedByALowerCaseCharacter(s, i)) return 1;
		return 3;
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

}
