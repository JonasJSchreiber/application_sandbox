package com.jonas.goertzel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.junit.Test;

public class TestGoertzel {

	@Test
	public void test() throws Exception {
		float sampleRate = (float) 44100.00;
		double[] audioData = getAudioData("data/false.wav");
		try {
			for (int k = 2675; k < 2725; k++) {
				double ampl = -1;
				Goertzel goertzel = new Goertzel(sampleRate, (float) k, audioData);
				goertzel.initGoertzel();
				ampl = goertzel.evaluate();
				System.out.println("Amplitude at: " + k + "Hz: " + ampl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n\n");
		audioData = getAudioData("data/true.wav");
		try {
			for (int k = 2675; k < 2725; k++) {
				double ampl = -1;
				Goertzel goertzel = new Goertzel(sampleRate, (float) k, audioData);
				goertzel.initGoertzel();
				ampl = goertzel.evaluate();
				System.out.println("Amplitude at: " + k + "Hz: " + ampl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n\n");
		audioData = getAudioData("data/true2.wav");
		try {
			for (int k = 2675; k < 2725; k++) {
				double ampl = -1;
				Goertzel goertzel = new Goertzel(sampleRate, (float) k, audioData);
				goertzel.initGoertzel();
				ampl = goertzel.evaluate();
				System.out.println("Amplitude at: " + k + "Hz: " + ampl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double[] getAudioData(String filename) throws Exception {
		final File audioFile = new File(filename);
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.out.println(
				"Sample size in bits   : " + inputStream.getFormat().getSampleSizeInBits());
		System.out.println("Encoding              : " + inputStream.getFormat().getEncoding());
		float sampleRate = (int) inputStream.getFormat().getSampleRate();
		System.out.println("Sample rate           : " + sampleRate);
		System.out.println("Number of channels    : " + inputStream.getFormat().getChannels());
		System.out.println("Frame rate            : " + inputStream.getFormat().getFrameRate());
		System.out.println("Big-endian            : " + inputStream.getFormat().isBigEndian());
		final byte[] buff = new byte[1024];
		for (int read = 0; (read = inputStream.read(buff)) != -1;) {
			out.write(buff, 0, read);
		}
		out.close();
		final byte[] audioBytes = out.toByteArray();

		double[] audioData = new double[audioBytes.length / 2];
		for (int i = 0, j = 0; j < audioData.length;) {
			audioData[j++] = ((audioBytes[i++] & 0xff) | (audioBytes[i++] << 8)) / 32768.0;
		}
		return audioData;
	}
}
