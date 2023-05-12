import java.util.*;
/**
 * @author tyler spring 
 * 4/10/2023 
 * Project 1 
 * CMSC 451 7380 
 * The abstractSort class. Here is where the methods for analyzing the sorting methods are.
 * The methods here measure the time it takes for the sorts, log the counts of them,
 * increment the counts and retrieve the time it took for the sorts. It also generates a random array by taking size
 * and range as parameters.
 */
public abstract class abstractSort {

	//variables for tracking the counts, time, and creates random variable.
	private static long count;
	private static final Random rand = new Random();
	protected long time;

	public abstract void sort(int[] arr);
//startSort, just starts a timer in nano.
	public void startSort() {
		count = 0;
		time = System.nanoTime();
	}
//endSort, same thing but sets time equal to current time minus previous.
	public void endSort() {
		time = System.nanoTime() - time;
	}
//Just adds to count, honestly felt like this was redundant, but here we are.
	public static void incrementCount() {
		count++;
	}
//tracks the count.
	public long getCount() {
		return count;
	}
//returns the time already calculated, probably better way to merge these 3.
	public long getTime() {
		return time;

	}
//generates random array with given parameters.
	public static int[] randArr(int size, int range) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextInt(range);
		}
		return arr;
	}
}
