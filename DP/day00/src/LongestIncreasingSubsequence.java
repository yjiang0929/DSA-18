public class LongestIncreasingSubsequence {

    // Runtime: O(N^2)
    // Space: O(N)
    public static int LIS(int[] A) {
        int[] dp = new int[A.length];
        int max = 0;
        for (int i=A.length-1;i>=0;i--) {
            dp[i] = 1;
            for (int j=i+1;j<A.length;j++) {
                if (A[i]<A[j] && dp[j]+1>dp[i]) {
                    dp[i] = dp[j]+1;
                }
            }
            if (dp[i]>max) {
                max = dp[i];
            }
        }
        return max;
    }
}