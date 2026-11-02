package editdistance;

public class TableEditDistance implements EditDistance {

    private long operations;

    public int distance(String a, String b) {

        /*
         * State: dp[i][j] stores the minimum edits between two prefixes.
         * Transition: match or min(top, left, diagonal) + 1.
         * Base case: dp[i][0] = i; dp[0][j] = j.
         */

        operations = 0;

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                operations++;

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j],
                            Math.min(
                                    dp[i][j - 1],
                                    dp[i - 1][j - 1]
                            )
                    );
                }
            }
        }

        return dp[n][m];
    }

    public long getOperations() {
        return operations;
    }
}
