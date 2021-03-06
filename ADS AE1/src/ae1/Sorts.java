package ae1;

/**
 * The class containing the sorting algorithms and a method for generating bad inputs for median of 3 partitioning.
 * 
 * Student solution to ADS Assessed Exercise 1
 * 
 * @author Adam Fairlie <2461352f@student.gla.ac.uk>
 */
public class Sorts {
	
	/**
	 * The quick sort algorithm for sorting an array
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 */
	public static void QuickSort(int[] A, int p, int r) {
		if(p < r) {
			//Partition array and sort each half
			int q = Partition(A, p, r);
			QuickSort(A, p, q-1);
			QuickSort(A, q+1, r);
		}
	}
	
	/**
	 * A helper method to sort elements around a chosen pivot (the last element)
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 */
	public static int Partition(int[] A, int p, int r) {
		
		//initialise pivot as last element
		int piv = A[r];
		int i = p - 1;
		
		for(int j = p; j < r; j++) {
			//increment lower region and move current element into lower region
			if(A[j] <= piv) {
				i++;
				Swap(A, i, j);
			}
		}
		//Put pivot between the regions and return its index
		i++;
		Swap(A, i, r);
		return i;
	}
	
	//Helper function to swap 2 indices in an array
	public static void Swap(int[] A, int i1, int i2) {
		int swap = A[i1];
		A[i1] = A[i2];
		A[i2] = swap;
	}
	
	
	/**
	 * The quick sort variant algorithm which uses insertion sort for smaller subarrays
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 * @param k The insertion constant (the maximum size of subarray to use insertion sort for)
	 */
	public static void HybridQuickSort(int[] A, int p, int r, int k) {
		if(p < r) {
			//Partition into 2 halves, then either sort recursively or with insertion sort depending
			//on the size of the chunks
			int q = Partition(A, p, r);
			if(q <= k) {
				InsertionSort(A, p, q-1);
				InsertionSort(A, q+1, r);
			}
			else {
				QuickSort(A, p, q-1);
				QuickSort(A, q+1, r);
			}

		}
	}
	
	/**
	 * The insertion sort algorithm for sorting subarrays
	 * 
	 * @param a The array to sort
	 * @param start The starting index of the portion to be sorted
	 * @param end The ending index of the portion to be sorted
	 */
	public static void InsertionSort(int[] a, int start, int end) {
		for(int j = start + 1; j < end + 1; j++) {
			//initialise key
			int key = a[j];
			int i = j - 1;
			//move up all larger elements before the key in the array
			while(i >= 0 && a[i] > key) {
				a[i+1] = a[i];
				i--;
			}
			//insert key in correct place
			a[i+1] = key;
		}	
	}
	
	
	/**
	 * The quick sort variant algorithm which uses median of 3 partitioning
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 */
	public static void QuickSortMO3(int[] A, int p, int r) {
		if(p < r) {
			//Partition array and sort each half
			int q = PartitionMO3(A, p, r);
			QuickSort(A, p, q-1);
			QuickSort(A, q+1, r);
		}
	}
	
	
	/**
	 * A helper method to sort elements around a chosen pivot (the median of the first, middle and last element)
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 */
	public static int PartitionMO3(int[] A, int p, int r) {
		
		//Sort the start, middle and end elements of the array so the median is last
		int q = (p + r) / 2;
		if(A[q] < A[p]) {
			Swap(A, q, p);
		}
		if(A[r] < A[p]) {
			Swap(A, r, p);
		}
		
		if(A[q] < A[r]) {
			Swap(A, q, r);
		}
		
		//initialise pivot (median of 3)
		int piv = A[r];
		
		int i = p - 1;
		
		for(int j = p; j < r; j++) {
			//if element is before pivot, increment lower region and add element to the end of the region
			if(A[j] <= piv) {
				i++;
				Swap(A, i, j);
			}
		}
		
		//insert pivot just after lower region
		Swap(A, i+1, r);
		return i + 1;
	}
	
	
	/**
	 * The quick sort variant algorithm which also sorts all elements equal to the current partition
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param s The ending index of the portion to be sorted
	 */
	public static void QuickSort3Way(int[] A, int p, int s) {
		if(p < s) {
			//get indices of start and end of section of repeated pivot values
			int[] indices = Partition3Way(A, p, s);
			int q = indices[0];
			int r = indices[1];
			
			//sort the strictly smaller and strictly larger halves recursively
			QuickSort3Way(A, p, q-1);
			QuickSort3Way(A, r+1, s);
		}
	}
	

	/**
	 * A helper method to sort elements around a chosen pivot (the last element) including all equal values
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 */
	public static int[] Partition3Way(int[] A, int p, int r) {
		
		//initialise pivot as last value
		int piv = A[r];
		
		//initialise counters for smaller or equal values and strictly equal values
		int i = p - 1;
		int k = p - 1;
		
		for(int j = p; j < r; j++) {
			//if element is before pivot, increment smaller region and swap
			if(A[j] <= piv) {
				i++;
				Swap(A, i, j);
				//if element is the same as pivot, increment equal counter and move to start
				if(A[i] == piv) {
					k++;
					Swap(A, i, k);
				}
				
			}
		}
		
		//increment counters after initial swapping has finished
		k++;
		i++;
		
		//swap smaller and equal regions
		for(int l = 0; l < (i-k); l++) {
			Swap(A, l+p, l+k);
		}
		
		//move original pivot to just after equal region
		Swap(A, i, r);
		
		//return first and last element of equal regions
		int[] indices = {i-k+p, i};
		return indices;
	}
	
	
	/**
	 * The merge function for merging two subarrays in descending order
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param q The index splitting the two subarrays to be merged
	 * @param r The ending index of the portion to be sorted
	 */
	public static void Merge(int[] A, int p, int q, int r) {
		//Create an array for each half
		int n1 = q - p + 1;
		int n2 = r - q;
		
		int[] L = new int[n1 + 1];
		int[] R = new int[n2 + 1];
		
		//Copy halves over and add a max value sentinel at the end of each
		for(int c1 = p; c1 < q+1; c1++) {
			L[c1-p] = A[c1]; 
		}
		L[n1] = Integer.MAX_VALUE;
		
		for(int c2 = q+1; c2 < r+1; c2++) {
			R[c2-q-1] = A[c2]; 
		}
		R[n2] = Integer.MAX_VALUE;
		
		//merge 2 arrays
		int i = 0; int j = 0;
		for(int k = p; k < r + 1; k++) {
			//if left is smaller, increment i and add to main array
			if(L[i] <= R[j]) {
				A[k] = L[i];
				i++;
			}
			//if right is smaller, increment j and add to main array
			else {
				A[k] = R[j];
				j++;
			}
		}
	}
	
	/**
	 * The merge function for merging two subarrays in descending order
	 * 
	 * @param A The array to sort
	 * @param p The starting index of the portion to be sorted
	 * @param r The ending index of the portion to be sorted
	 */
	public static void  MergeSort(int[] A, int p, int r){
		if(p < r) {
			//Split into 2 halves
			int q = (p+r) / 2;
			MergeSort(A, p, q);
			MergeSort(A, q+1, r);
			//Merge 2 halves
			Merge(A, p, q, r);
		}
	}
	
	
	/**
	 * Creates an array designed to make the median of 3 partitioning scheme run in O(n) time
	 * 
	 * @param n The size of the desired array
	 * @return A The generated array
	 */	
	public static int[] pathologicalArray(int n) {
		
		//Create array of size n
		int[] A = new int[n];
		int l = n - 1;
		
		//Add to the tail, then the head, decrementing n and moving inwards
		for(int i = 0; i < (l+1)/2; i++) {
			A[i] = --n;	
			A[l - i] = --n;
		}
		
		//Return generated array
		return A;
	}
	
}
