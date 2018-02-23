import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.*;

public class LargestSubArray {
    static int[] largestSubarray(int[] nums) {
        int[] sum = new int[nums.length+1];
        sum[0] = 0;
        for (int i=0;i<nums.length;i++) {
            if (nums[i]==1) {
                sum[i+1] = sum[i] + 1;
            } else {
                sum[i+1] = sum[i] -1;
            }
        }
        HashMap<Integer, List<Integer>> counter = new HashMap<>();
        for (int i=0;i<sum.length;i++) {
            if (!counter.containsKey(sum[i])) {
                counter.put(sum[i],new ArrayList<>(Arrays.asList(i)));
            } else {
                counter.get(sum[i]).add(1,i);
            }
        }
        int max_len = -1;
        int max_head = 0;
        int max_tail = 0;
        for (Map.Entry<Integer, List<Integer>> pair : counter.entrySet()) {
            List<Integer> range = pair.getValue();
            if (range.size()>1) {
                int curr = range.get(1) - range.get(0);
                if (curr > max_len) {
                    max_len = curr;
                    max_head = range.get(0);
                    max_tail = range.get(1)-1;
                }
            }
        }
        return new int[]{max_head,max_tail};
    }
}
