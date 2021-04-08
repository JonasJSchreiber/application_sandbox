package com.jonas.jtransforms;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.jtransforms.fft.DoubleFFT_1D;
import org.junit.Test;

public class JTransformsTest {
	// The sound format properties
	static final int channels = 2;
	static final int bits = 16;
	static final float sampleRate = 44100.0f;
	static final AudioFormat format = new AudioFormat(sampleRate, bits, channels, true, false);

	private AudioInputStream audioInputStream;
	private String file;
	// A helper buffer to read the 4 bytes of the frame. One short for each channel.
	private final byte[] buffer = new byte[format.getFrameSize()];
	// We will read chunks of 1024 samples.
	public static int CHUNKSIZE = 1024;
	private double[] chunk = new double[JTransformsTest.CHUNKSIZE];
	private DoubleFFT_1D fft = new DoubleFFT_1D(CHUNKSIZE);

	public void open() throws Exception {
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));
		audioInputStream = AudioSystem.getAudioInputStream(format, ais);
	}

	public double[] chunk() {
		for (int i = 0; i < chunk.length; i++)
			chunk[i] = read();
		return chunk;
	}

	private double read() {
		try {
			int read = audioInputStream.read(buffer);
			if (read == -1) {
				audioInputStream.close();
				open();
				return 0;
			} else {
				short sample = (short) ((((int) buffer[1] & 0xff) << 8) + ((int) buffer[0] & 0xff));
				return (sample) / 32767.0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	public double[] apply(double[] params) {
		double[] output = new double[params.length * 2];
		System.arraycopy(params, 0, output, 0, params.length);
		fft.realForward(output);
		double[] half = new double[params.length];
		System.arraycopy(output, 0, half, 0, half.length);
		return half;
	}

	@Test
	public void test() {
		this.file = "data/output.wav";
		try {
			open();
			chunk();
			double[] array = apply(chunk);
			System.out.println(array.length);
			System.out.println(chunk.length);
			for (double d : array) {
				d = d + 1 - 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
