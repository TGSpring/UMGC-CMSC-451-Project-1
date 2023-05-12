
import java.io.*;
import java.util.*;

/**
 * @author tyler spring 
 * 4/10/2023 
 * Project 1 
 * CMSC 451 7380 This is the
 *         BenchMarkSorts class. Here is where the data for the two sorting
 *         methods radix and selection are created. As well as the warm up for
 *         the JVM. The data is generated randomly into array DATA_SET_SIZES
 *         with 12 elements in the array. From this 40 sets of data for each
 *         value are created and this is ran 40 times. After that the text files are created
 *         and the data is stored there. Then a call to the reportGen class
 *         is made to do the file selection and create/display the table.
 *
 */
public class BenchMarkSorts {
//finals for warm up runs and benchmark runs.
	private static final int WARMUP_ROUNDS = 5;
	private static final int BENCH_RUNS = 40;

	public static void main(String[] args) {
//array holding 12 elements of data.
		int[] DATA_SET_SIZES = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 15000, 20000 };
// arrays for selection and radix sorts. Set to 41 because if I have to deal
// with another out of bounds error I am giving up.
		long[][] selectResults = new long[12][41];
		long[][] radResults = new long[12][41];
		final Random rand = new Random();
//warming up JVM
		for (int i = 0; i < WARMUP_ROUNDS; i++) {
			int warmUp = DATA_SET_SIZES[i];
			int[] arr = new int[warmUp];
			System.out.println("Warming up JVM for n = " + warmUp);
			for (int j = 0; j < warmUp; j++) {
				arr[j] = rand.nextInt(warmUp);
			}
// warm up for selection sort
			selectSort WarmUpSelect = new selectSort();
			WarmUpSelect.startSort();
			WarmUpSelect.sort(arr);
			WarmUpSelect.endSort();
			verifySort(arr);
//warm up for radix
			int[] arrCopy = Arrays.copyOf(arr, arr.length);
			radixSort WarmUpradix = new radixSort();
			WarmUpradix.startSort();
			WarmUpradix.sort(arrCopy);
			WarmUpradix.endSort();
			verifySort(arrCopy);
		}
//running benchmarks.
		for (int i = 0; i < DATA_SET_SIZES.length; i++) {
			int n = DATA_SET_SIZES[i];
			System.out.println("Running benchmark for n = " + n);
//generates random numbers for arrays to be sorted.
			for (int j = 0; j < BENCH_RUNS; j++) {
				int[] arr = new int[n];
				for (int z = 0; z < n; z++) {
					arr[z] = rand.nextInt(n);
				}
//creates copy to be used on sorting class.
				int[] arrCopy = Arrays.copyOf(arr, arr.length);
				selectSort selectArr = new selectSort();
//calls to start, run, and stop sort.
				selectArr.startSort();
				selectArr.sort(arrCopy);
				selectArr.endSort();
//stores time and count
				long selectSortTotalTime = selectArr.getTime();
				long selectSortCounter = selectArr.getCount();
//verifies sorted.
				verifySort(arrCopy);
// creates copy to be used on sorting class.
				arrCopy = Arrays.copyOf(arr, arr.length);
				radixSort radArr = new radixSort();
// calls to start, run, and stop sort.
				radArr.startSort();
				radArr.sort(arrCopy);
				radArr.endSort();
// stores time and count
				long radSortTotalTime = radArr.getTime();
				long radSortCounter = radArr.getCount();
// verifies sorted.
				verifySort(arrCopy);
//stores data in separate results arrays to be printed to text files.
				selectResults[i][j] = selectSortCounter;
				selectResults[i][j] = selectSortTotalTime;
				radResults[i][j] = radSortCounter;
				radResults[i][j] = radSortTotalTime;
//method call print the results to text files.
				writeRes(selectResults, radResults);
			}
		}
//Here is the call to the reportGen class to bring up the JFileChooser and then display the table.
		reportGen.runReport();
	}

//method for reading in and printing the result arrays to two separate text files for each sorting method.
	public static void writeRes(long[][] selectResults, long[][] radResults) {
		String selectFileName = "selectSortResults.txt";
		String radFileName = "radixSortResults.txt";

		try {
//writer for selection sort.
			FileWriter selectFileWriter = new FileWriter(selectFileName);
			for (int i = 0; i < selectResults.length; i++) {
				for (int j = 0; j < selectResults[i].length; j++) {
					selectFileWriter.write(selectResults[i][j] + "\t");
				}
				selectFileWriter.write("\n");
			}
			selectFileWriter.close();
//writer for radix sort.
			FileWriter radFileWriter = new FileWriter(radFileName);
			for (int i = 0; i < radResults.length; i++) {
				for (int j = 0; j < radResults[i].length; j++) {
					radFileWriter.write(radResults[i][j] + "\t");
				}
				radFileWriter.write("\n");
			}
			radFileWriter.close();

		} catch (IOException e) {
			System.out.println("Error writing results to file: " + e.getMessage());
		}
	}

//method that verifies the data is sorted and throws exception if not.
	private static void verifySort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				throw new RuntimeException("Array not sorted!");
			}
		}

	}
}
