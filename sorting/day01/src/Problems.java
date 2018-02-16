import java.util.*;

public class Problems {

    private static PriorityQueue<Integer> minPQ() { return new PriorityQueue<>(11);  }

    private static PriorityQueue<Integer> maxPQ() { return new PriorityQueue<>(11, Collections.reverseOrder());  }

    private static double getMedian(List<Integer> A) {
        double median = (double) A.get(A.size() / 2);
        if (A.size() % 2 == 0)
            median = (median + A.get(A.size() / 2 - 1)) / 2.0;
        return median;
    }

    // Runtime of this algorithm is O(N^2). Sad! We provide it here for testing purposes
    public static double[] runningMedianReallySlow(int[] A) {
        double[] out = new double[A.length];
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            int j = 0;
            while (j < seen.size() && seen.get(j) < A[i])
                j++;
            seen.add(j, A[i]);
            out[i] = getMedian(seen);
        }
        return out;
    }


    /**
     *
     * @param inputStream an input stream of integers
     * @return the median of the stream, after each element has been added
     */
    public static double[] runningMedian(int[] inputStream) {
        PriorityQueue<Integer> min_list = minPQ();
        PriorityQueue<Integer> max_list = maxPQ();
        double[] runningMedian = new double[inputStream.length];
        if (inputStream.length == 0) {
            return runningMedian;
        }
        for (int i=0;i<inputStream.length;i++) {
            if (i % 2 == 0) {
                if (max_list.isEmpty() || inputStream[i] < min_list.peek()) {
                    max_list.offer(inputStream[i]);
                } else {
                    min_list.offer(inputStream[i]);
                }
                if (max_list.size() >= min_list.size()) {
                    runningMedian[i] = max_list.peek();
                } else {
                    runningMedian[i] = min_list.peek();
                }
            } else {
                if (max_list.size() > min_list.size()) {
                    if (inputStream[i] < max_list.peek()) {
                        min_list.offer(max_list.poll());
                        max_list.offer(inputStream[i]);
                    } else {
                        min_list.offer(inputStream[i]);
                    }
                } else {
                    if (inputStream[i] > min_list.peek()) {
                        max_list.offer(min_list.poll());
                        min_list.offer(inputStream[i]);
                    } else {
                        max_list.offer(inputStream[i]);
                    }
                }
                runningMedian[i] = ((double) max_list.peek() + (double) min_list.peek()) / 2;
            }
        }
        return runningMedian;
    }

}
