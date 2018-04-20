import java.util.Arrays;
import java.util.PriorityQueue;

public class BarnRepair {
    public static int solve(int M, int[] occupied) {
        int block = 0;
        int result = occupied.length;
        Arrays.sort(occupied);
        PriorityQueue<Integer> hole = new PriorityQueue<>();
        boolean blockFlag = true;
        for (int i=0;i<occupied.length-1;i++) {
            if (occupied[i+1]-occupied[i]>1) {
                blockFlag = false;
            } else {
                blockFlag = true;
            }
            if (!blockFlag) {
                block += 1;
                hole.add(occupied[i+1]-occupied[i]-1);
            }
        }
        block += 1;
        M = block - M;
        if (M<=0) {
            return result;
        } else {
            while (M>0) {
                result += hole.poll();
                M -= 1;
            }31111111111111111111111
            return result;
        }
    }
}