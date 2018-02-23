import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TripleSum {

    static int tripleSum(int[] arr, int sum) {
        Arrays.sort(arr);
        if (arr[0] < 1) {
            int delta = Math.abs(arr[0]) +1;
            for (int i=0;i<arr.length;i++) {
                arr[i] = arr[i] + delta;
            }
            sum = sum + delta*3;
        }
        HashMap<Integer, List<List<Integer>>> counter = new HashMap<>();
        int ans = 0;
        for (int i=0;i<arr.length;i++) {
            for (int j=i+1;j<arr.length;j++) {
                List<List<Integer>> record = new ArrayList<>();
                if (counter.get(arr[i] + arr[j]) != null) {
                    record = counter.get(arr[i]+arr[j]);
                }
                List<Integer> temp = new ArrayList<>();
                temp.add(i);
                temp.add(j);
                record.add(temp);
                counter.put(arr[i]+arr[j],record);
            }
        }
        for (int i=0;i<arr.length;i++) {
            if (counter.containsKey(sum - arr[i])) {
                for (List<Integer> places : counter.get(sum-arr[i])) {
                    if ( i!=places.get(0) && i!= places.get(1)) {
                        ans++;
                    }
                }
            }
        }
        return ans / 3;
    }
}
