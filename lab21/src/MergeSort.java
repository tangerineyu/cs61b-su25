public class MergeSort {


    /**
     * @param arr
     *
     * Sort the array arr using merge sort.
     * The merge sort algorithm is as follows:
     * 1. Split the collection to be sorted in half.
     * 2. Recursively call merge sort on each half.
     * 3. Merge the sorted half-lists.
     *
     */
    public static int[] sort(int[] arr) {
        // TODO: Implement merge sort
        if (arr.length < 2) {
            return arr; // Base case: if the array has 0 or 1 elements, it is already sorted
        }
        int mid = arr.length / 2;
        int[] a = new int[mid];
        int[] b = new int[arr.length - mid];
        // Split the array into two halves
        for (int i = 0; i < mid; i++) {
            a[i] = arr[i];
        }
        for (int i = mid; i < arr.length; i++) {
            b[i - mid] = arr[i];
        }
        // Recursively sort each half
        a = sort(a);
        b = sort(b);
        // Merge the sorted halves
        return merge(a, b);
    }

    /**
     * @param a
     * @param b
     *
     * Merge the sorted half-lists.
     *
     * Suggested helper method that will make it easier for you to implement merge sort.
     */
    private static int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        // Merge the two sorted arrays a and b into c
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = b[j++];
            }
        }
        // If there are remaining elements in a, add them to c
        while (i < a.length) {
            c[k++] = a[i++];
        }
        // If there are remaining elements in b, add them to c
        while (j < b.length) {
            c[k++] = b[j++];
        }
        // Return the merged sorted array
        // TODO: Implement merge
        return c;
    }
}

