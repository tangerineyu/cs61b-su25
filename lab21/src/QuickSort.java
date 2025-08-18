public class QuickSort {

    /**
     * @param arr
     *
     * Sort the array arr using quicksort with the 3-scan partition algorithm.
     * The quicksort algorithm is as follows:
     * 1. Select a pivot, partition array in place around the pivot.
     * 2. Recursively call quicksort on each subsection of the modified array.
     */
    public static int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length);
        return arr;
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Helper method for sort: runs quicksort algorithm on array from [start:end)
     */
    private static void quickSort(int[] arr, int start, int end) {
        // TODO: Implement quicksort
        if (end - start < 2) {
            return; // Base case: if the section has 0 or 1 elements, it is already sorted
        }
        int[] partitionIndices = partition(arr, start, end);
        int lessThanEnd = partitionIndices[0];
        int greaterThanStart = partitionIndices[1];
        quickSort(arr, start, lessThanEnd);
        quickSort(arr, greaterThanStart, end);
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Partition the array in-place following the 3-scan partitioning scheme.
     * You may assume that first item is always selected as the pivot.
     * 
     * Returns a length-2 int array of indices:
     * [end index of "less than" section, start index of "greater than" section]
     *
     * Most of the code for quicksort is in this function
     */
    private static int[] partition(int[] arr, int start, int end) {
        // TODO: Implement partition
        int pivot = arr[start];
        int LessThanPivot = start;
        int GreaterThanPivot = end - 1;
        int i = start + 1;
        while (i <= GreaterThanPivot) {
            if (arr[i] < pivot) {
                swap(arr, LessThanPivot + 1, i);
                i++;
                LessThanPivot++;
            }
            else if (arr[i] > pivot) {
                swap(arr, i, GreaterThanPivot);
                GreaterThanPivot--;
            }
            else {
                i++;
            }
        }
        swap(arr, start, LessThanPivot);
        return new int[] {LessThanPivot, GreaterThanPivot + 1};

    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}   
