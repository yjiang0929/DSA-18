import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * This is the recursive step in which you split the array up into
     * a left and a right portion, sort them, and then merge them together.
     * Use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     *
     * TODO
     * Best-case runtime: O(n log n)
     * Worst-case runtime: O(n log n)
     * Average-case runtime: O(n log n)
     *
     * Space-complexity:O(n)
     */
    @Override
    public int[] sort(int[] array) {
        if (array.length <= INSERTION_THRESHOLD) {
            return new InsertionSort().sort(array);
        }
        int half = array.length / 2;
        int[] left = Arrays.copyOfRange(array,0, half);
        int[] right = Arrays.copyOfRange(array, half, array.length);
        left = sort(left);
        right = sort(right);
        return merge(left,right);
    }

    /**
     * Given two sorted arrays a and b, return a new sorted array containing
     * all elements in a and b. A test for this method is provided in `SortTest.java`
     */
    public int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length+b.length];
        int ia = 0;
        int ib = 0;
        while (ia<a.length || ib<b.length) {
            if (ib >= b.length) {
                res[ia+ib] = a[ia];
                ia++;
            } else {
                if (ia>=a.length) {
                    res[ia+ib] = b[ib];
                    ib++;
                } else {
                    if (b[ib]>a[ia]) {
                        res[ia+ib] = a[ia];
                        ia++;
                    } else {
                        res[ia+ib] = b[ib];
                        ib++;
                    }
                }
            }
        }
        return res;
    }

}
