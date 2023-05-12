/**
 * @author tyler spring 
 * 4/10/2023 
 * Project 1 
 * CMSC 451 7380 
 * The selectionSort class. Selection sort is pretty straight forward in my opinion so I was able to make this.
 * It also has been modified to track counts. It also extends abstractSort for the incrementCount method.
 */
public class selectSort extends abstractSort {
	public void sort(int[] arr) {

		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			// Finds smallest element by going through every element in the array.
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
				// incrementCount method from abstractSort class.
				incrementCount();
			}
//Uses temp int for comparing the smallest element and swapping it.
			int temp = arr[minIndex];
			arr[minIndex] = arr[i];
			arr[i] = temp;

		}

	}
}
