import java.util.LinkedList;

public class Problems {

    static void sortNumsBetween100s(int[] A) {
        for (int i=0;i<A.length;i++) {
            A[i] += 101;
        }
        RadixSort.radixSort(A,A.length);
        for (int i=0;i<A.length;i++) {
            A[i] -= 101;
        }
    }

    /**
     * @param n the character number, 0 is the rightmost character
     * @return
     */
    private static int getNthCharacter(String s, int n) {
        return s.charAt(s.length() - 1 - n) - 'a';
    }


    /**
     * Use counting sort to sort the String array according to a character
     *
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByCharacter(String[] A, int n) {
        int b = 26;
        LinkedList<String>[] L = new LinkedList[b];
        for (int i = 0; i < b; i++)
            L[i] = new LinkedList<>();
        for (String i : A) {
            int digit = getNthCharacter(i,n);
            L[digit].offer(i);
        }
        int j = 0; // index in A to place numbers
        for (LinkedList<String> list : L) {
            while (list.size()>0) {
                A[j] = list.removeFirst();
                j++;
            }
        }
    }

    /**
     * @param stringLength The length of each of the strings in S
     */
    static void sortStrings(String[] S, int stringLength) {
        for (int n=0;n<stringLength;n++) {
            countingSortByCharacter(S,n);
        }
    }

    /**
     * @param A The array to count swaps in
     */

    public static int countSwaps(int[] A) {
        // TODO
        return 0;
    }

}
