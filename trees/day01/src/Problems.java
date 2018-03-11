public class Problems {

    public static int leastSum(int[] A) {
        int[] counts = new int[10];
        for (int digit : A) {
            counts[digit]++;
        }
        int i = 0;
        for (int j=9; j>=0; j--) {
            while (counts[j]>0) {
                A[i] = j;
                counts[j]--;
                i++;
            }
        }
        i = 0;
        int result = 0;
        int multiplier = 1;
        boolean isSecond = false;
        while (i<A.length) {
            result = result + multiplier * A[i];
            if (isSecond) {
                multiplier = multiplier * 10;
                isSecond = false;
            } else {
                isSecond = true;
            }
            i++;
        }
        return result;
    }
}
