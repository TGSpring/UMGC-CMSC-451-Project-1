/**
 * @author tyler spring 
 * 4/10/2023 
 * Project 1 
 * CMSC 451 7380 
 *        The radixSort class. Here an array is passed to be sorted via radix sort. 
 *        I used GeeksforGeeks to understand it better.
 *        GeeksforGeeks. (2023, April 5). Radix sort. GeeksforGeeks. Retrieved April 10, 2023, from https://www.geeksforgeeks.org/radix-sort/ 
 *			It has also been modified per the requirment to keep track of counting.
 *It extends the abstractSort class to use methods like incrementCount.
 */
public class radixSort extends abstractSort {
//constructor taking array.
	public void sort(int[] arr2) {
// call to find max digit
		int max = getMax(arr2);
		int exp = 1;
		int[] aux = new int[arr2.length];
//used to compare against max value to iterate through.
		for (int i = 0; i < max; i++) {
// counter array
			int[] counter = new int[10];
			for (int j = 0; j < arr2.length; j++) {
				int digit = (arr2[j] / exp) % 10;
				counter[digit]++;
// incrementCount method from the abstractSort class
				incrementCount();
			}
			for (int q = 1; q < 10; q++) {
				counter[q] += counter[q - 1];
				incrementCount();
			}

			for (int z = arr2.length - 1; z >= 0; z--) {
				int digit = (arr2[z] / exp) % 10;
				aux[counter[digit] - 1] = arr2[z];
				counter[digit]--;
				incrementCount();
			}
			for (int t = 0; t < arr2.length; t++) {
				arr2[t] = aux[t];
				incrementCount();
			}
			exp *= 10;
		}
		
	}

//Method for finding max value of array. Iterates through length of array
// and compares with first element. If i is bigger, that is the new max.
	private static int getMax(int[] arr2) {

		int mx = arr2[0];
		for (int i = 1; i < arr2.length; i++) {
			if (arr2[i] > mx) {
				mx = arr2[i];
			}
		}
		int num = 0;
		while (mx > 0) {
			num++;
			mx /= 10;
		}
		return num;
	}
}
