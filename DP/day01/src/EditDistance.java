import java.util.Arrays;

public class EditDistance {
    // space complexity: O(a.length*b.length)
    // time complexity: O(a.length*b.length)
    public static int minEditDist(String a, String b) {
        int[][] dp = new int[a.length()+1][b.length()+1];
        for (int[] line : dp) {
            Arrays.fill(line, -1);
        }
        for (int i=0;i<a.length()+1;i++) {
            dp[i][0] = i;
        }
        for (int i=0;i<b.length()+1;i++) {
            dp[0][i] = i;
        }
        return find_solution(a.length(), b.length(), dp, a, b);
    }

    public static int find_solution(int i, int j, int[][] dp, String a, String b) {
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (a.charAt(i-1)==b.charAt(j-1)) {
            dp[i][j] = find_solution(i-1, j-1, dp, a, b);
        } else {
            // if both from left and from top are achieved through insertion
            if (find_solution(i-1,j,dp,a,b)>find_solution(i-1,j-1,dp,a,b) && find_solution(i,j-1,dp,a,b)>find_solution(i-1,j-1,dp,a,b)) {
                // merge two insertions to one replacement
                dp[i][j] = Math.min(find_solution(i-1, j, dp, a, b), find_solution(i, j-1, dp, a, b));
            } else {
                dp[i][j] = Math.min(find_solution(i-1, j, dp, a, b), find_solution(i, j-1, dp, a, b))+1;
            }
        }
        return dp[i][j];
    }

}
