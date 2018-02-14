import java.util.HashMap;
import java.util.Map;

public class Boomerang {

    public static int numberOfBoomerangs(int[][] points) {
        int ans = 0;
        for (int[] center : points) {
            HashMap<Double, Integer> counter = new HashMap<>();
            for (int[] other : points) {
                if (other != center) {
                    double dist = Math.sqrt(Math.pow(center[0]-other[0],2) + Math.pow(center[1]-other[1],2));
                    if (counter.get(dist) == null) {
                        counter.put(dist,1);
                    } else {
                        counter.put(dist,counter.get(dist)+1);
                    }
                }
            }
            for (Map.Entry<Double,Integer> i : counter.entrySet()) {
                Integer n = i.getValue();
                ans = ans + n*(n-1);
            }
        }
        return ans;
    }
}

