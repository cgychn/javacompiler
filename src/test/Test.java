package test;

public class Test {
	public int binSearch ( int array[], int key ) {
		int mid, low, high;
		low = 0;
		high = array.length-1;
		while ( low <= high ) {
			mid = (low +high)/2;
			if ( key == array [mid] )
				return mid;
			else if ( key < array [mid] )
				high = mid -1;
			else
				low = mid + 1;
			}
		return -1;
	}
}
