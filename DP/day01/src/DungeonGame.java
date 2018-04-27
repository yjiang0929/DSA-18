public class DungeonGame {
    // Space Complexity: O(N^2)
    // Time Complexity: O(N^2)
    public static int minInitialHealth(int[][] map) {
        int[][] dp = new int[map.length][map[0].length];
        int index = map.length + map[0].length - 3;
        dp[map.length-1][map[0].length-1] = 1 - map[map.length-1][map[0].length-1] ;
        while (index >= 0) {
            for (int i=0;i<=Math.min(index,map.length-1);i++) {
                int j = index - i;
                if (j>=0 && j<map[0].length) {
                    int from_right = Integer.MAX_VALUE;
                    int from_bottom = Integer.MAX_VALUE;
                    if (i+1 < map.length) {
                        from_bottom = Math.max(0, dp[i+1][j]-map[i][j]);
                    }
                    if (j+1 < map[0].length) {
                        from_right = Math.max(0, dp[i][j+1]-map[i][j]);
                    }
                    dp[i][j] = Math.min(from_bottom, from_right);
                }
            }
            index -= 1;
        }
        return dp[0][0];
    }
}
