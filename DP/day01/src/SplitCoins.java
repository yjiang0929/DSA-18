public class SplitCoins {
    // space complexity: O(sum*n)
    // time complexity: O(sum*n)
    public static int splitCoins(int[] coins) {
        int n = coins.length;
        int sum = 0;
        for (int coin : coins) {
            sum += coin;
        }
        boolean[][] dp = new boolean[sum+1][n+1];
        for (int i=0;i<n+1;i++) {
            dp[0][i] = true;
        }
        for (int i=1;i<sum/2+1;i++) {
            for (int j=1;j<n+1;j++) {
                if (i<coins[j-1]) {
                    dp[i][j] = dp[i][j-1];
                } else {
                    dp[i][j] = dp[i][j-1] || dp[i-coins[j-1]][j-1];
                }
            }
        }
        int ans = 0;
        for (int j=sum/2;j>0;j--) {
            if (dp[j][n]) {
                ans = sum-j*2;
                break;
            }
        }
        return ans;
    }
}
