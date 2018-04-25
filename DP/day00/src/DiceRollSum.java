public class DiceRollSum {

    // Runtime: O(N)
    // Space: O(N)
    // Subproblems, Guess, Recurrence Relation, Memorize, Solution
    public static int diceRollSum(int N) {
        int[] dp = new int[N+1];
        dp[0] = 1;
        for (int i=1;i<N+1;i++) {
            int sum = 0;
            for (int j=1;j<=6;j++) {
                if (i-j>=0) {
                    sum += dp[i-j];
                }
            }
            dp[i] = sum;
        }
        return dp[N];
    }

}
