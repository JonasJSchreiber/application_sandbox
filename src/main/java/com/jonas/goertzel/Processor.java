package com.jonas.goertzel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Processor {

	public static void main(String[] args) {
		Processor processor = new Processor();
		processor.start();
	}

	public void start() {
		float sampleRate = (float) 44100.00;
		TargetDataLine line = null;

		try {
			line = getLine();
		} catch (LineUnavailableException e1) {
			System.exit(1);
		}

		while (true) {
			String filename = "data/output.wav";
			int thresholdHit = 0;
			boolean exceededThreshold = false;
			JavaSoundRecorder recorder = new JavaSoundRecorder();
			Long start = System.currentTimeMillis();
			recorder.record(filename, 200, line);
			try {
				double[] audioData = getAudioData(filename);
				for (int k = 2675; k < 2725; k += 5) {
					double ampl = -1;
					Goertzel goertzel = new Goertzel(sampleRate, (float) k, audioData);
					goertzel.initGoertzel();
					ampl = goertzel.evaluate();
					System.out.println("Amplitude at: " + k + "Hz: " + ampl);
					if (ampl > 100) {
						thresholdHit++;
						exceededThreshold = true;
						break;
					} else {
						thresholdHit = 0;
						exceededThreshold = false;
					}
				}
				System.out.println("\n\n\n");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (exceededThreshold) {
					if (thresholdHit > 13) {
						// Done all we can
					} else if (thresholdHit > 12) {
						// Send call
					} else if (thresholdHit > 6) {
						playAlarm();
					}
					try {
						// This assumes no overhead
						Thread.sleep(10000 - ((System.currentTimeMillis() - start) % 10000));
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(500);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	AudioFormat getAudioFormat() {
		float sampleRate = 44100;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
		return format;
	}

	TargetDataLine getLine() throws LineUnavailableException {
		AudioFormat format = getAudioFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		// checks if system supports the data line
		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("Line not supported");
			System.exit(0);
		}

		final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		return line;
	}

	public double[] getAudioData(String filename) throws Exception {
		final File audioFile = new File(filename);
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		// System.out.println(
		// "Sample size in bits : " + inputStream.getFormat().getSampleSizeInBits());
		// System.out.println("Encoding : " + inputStream.getFormat().getEncoding());
		// float sampleRate = (int) inputStream.getFormat().getSampleRate();
		// System.out.println("Sample rate : " + sampleRate);
		// System.out.println("Number of channels : " + inputStream.getFormat().getChannels());
		// System.out.println("Frame rate : " + inputStream.getFormat().getFrameRate());
		// System.out.println("Big-endian : " + inputStream.getFormat().isBigEndian());
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

	public void playAlarm() {
		AudioInputStream ais = null;
		Clip clip = null;
		try {
			ais = AudioSystem.getAudioInputStream(new File("data/CarAlarm.wav"));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();

			while (!clip.isRunning())
				Thread.sleep(10);
			while (clip.isRunning())
				Thread.sleep(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ais.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
