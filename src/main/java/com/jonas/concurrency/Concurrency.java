package com.jonas.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Concurrency {

	public int[][] oneThreadMatrix, futuresMatrix, matrix, m1, m2;

	public class CallableWorkerThread implements Callable<int[]> {
		int row;

		public CallableWorkerThread(int row) {
			this.row = row;
		}

		@Override
		public int[] call() throws Exception {
			int[] results = new int[m1.length];
			IntStream.range(0, m1.length).forEach(i -> IntStream.range(0, m1.length)
					.forEach(j -> results[i] += m1[row][j] * m2[j][i]));
			return results;
		}

	}

	public class WorkerThread implements Runnable {
		int row;
		WorkerThread(int row) {
			this.row = row;
		}

		@Override
		public void run() {
			IntStream.range(0, m1.length).forEach(i -> IntStream.range(0, m1.length)
					.forEach(j -> matrix[row][i] += m1[row][j] * m2[j][i]));
		}
	}

	public void initializeSquareRandomMatrices(int size) {
		m1 = new int[size][size];
		m2 = new int[size][size];
		matrix = new int[size][size];
		futuresMatrix = new int[size][size];
		oneThreadMatrix = new int[size][size];

		int maxRandomToRemainInteger = 8192 / (int) Math.sqrt((double) size);
		Random r = new Random();
		IntStream.range(0, size).forEach(i -> IntStream.range(0, size)
				.forEach(j -> m1[i][j] = r.nextInt(maxRandomToRemainInteger)));
		IntStream.range(0, size).forEach(i -> IntStream.range(0, size)
				.forEach(j -> m2[i][j] = r.nextInt(maxRandomToRemainInteger)));
	}

	public void spawnExecutorsWorkers() {
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(m1.length);
		IntStream.range(0, m1.length).forEach(i -> executor.execute(new WorkerThread(i)));
		System.out.println("Multi Threaded Execution with Executors Took: "
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public void spawnFutureWorkers() {
		ArrayList<Future<int[]>> results = new ArrayList<Future<int[]>>();
		long start = System.currentTimeMillis();

		List<Integer> indices = new ArrayList<Integer>();
		IntStream.range(0, m1.length).forEach(i -> indices.add(i));

		indices.forEach(i -> {
			CallableWorkerThread t = new CallableWorkerThread(i);
			FutureTask<int[]> task = new FutureTask<int[]>(t);
			results.add(task);
			Thread thread = new Thread(task);
			thread.start();
		});

		int i = 0;
		for (Future<int[]> f : results) {
			try {
				futuresMatrix[i] = f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			i++;
		}
		System.out.println("Multi Threaded Execution with Futures Took: "
				+ (System.currentTimeMillis() - start) + "ms");

	}

	public void singleThreadedMatrixMultiplication() {
		long start = System.currentTimeMillis();
		IntStream.range(0, m1.length).forEach(
				i -> IntStream.range(0, m1.length).forEach(j -> IntStream.range(0, m1.length)
						.forEach(k -> oneThreadMatrix[i][j] += m1[i][k] * m2[k][j])));
		System.out.println(
				"Single Threaded Execution Took: " + (System.currentTimeMillis() - start) + "ms");
	}

	public void printMatrix() {
		System.out.println("M1");
		Stream.of(m1).map(Arrays::toString).forEach(System.out::println);
		System.out.println("\nM2");
		Stream.of(m2).map(Arrays::toString).forEach(System.out::println);
		System.out.println("\nMulti Threaded Result");
		Stream.of(matrix).map(Arrays::toString).forEach(System.out::println);
		System.out.println("\nFutures Result");
		Stream.of(futuresMatrix).map(Arrays::toString).forEach(System.out::println);
		System.out.println("\nSingle Threaded Result");
		Stream.of(oneThreadMatrix).map(Arrays::toString).forEach(System.out::println);
	}

}
