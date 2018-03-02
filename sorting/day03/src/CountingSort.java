import java.util.Collections;

public class CountingSort {

    /**
<<<<<<< HEAD
     * Use counting sort to sort positive integer array A.
     * Runtime: O(n+k)
=======
     * Use counting sort to sort non-negative integer array A.
     * Runtime: TODO
>>>>>>> 6ef5cd060b5c2a2e260f93ca04e6a62dc4bfacbc
     *
     * k: maximum element in array A
     */
    static void countingSort(int[] A) {
        int k = 0;
        for (int i : A) {
            if (i>k) {
                k = i;
            }
        }
        k++;
        int[] counts = new int[k];
        for (int i : A) {
            counts[i]++;
        }
        int i = 0;
        for (int j=0;j<k;j++) {
            while (counts[j]>0) {
                counts[j]--;
                A[i] = j;
                i++;
            }
        }
    }

}
