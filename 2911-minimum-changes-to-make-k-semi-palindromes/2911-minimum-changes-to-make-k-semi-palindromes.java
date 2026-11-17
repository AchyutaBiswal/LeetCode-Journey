
class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();

        // Step 1: Precompute cost for every substring
        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {

            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;

                cost[i][j] = Integer.MAX_VALUE;

                // Try every proper divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;
                    int groupSize = len / d;

                    // Compare symmetric characters
                    // in each group
                    for (int r = 0; r < d; r++) {

                        for (int t = 0; t < groupSize / 2; t++) {

                            int left = i + r + t * d;

                            int right = i + r
                                    + (groupSize - 1 - t) * d;

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                        }
                    }

                    cost[i][j] = Math.min(
                            cost[i][j], changes
                    );
                }
            }
        }

        // Step 2: Dynamic Programming
        int INF = 1000000;

        int[][] dp = new int[k + 1][n + 1];

        for (int g = 0; g <= k; g++) {
            for (int i = 0; i <= n; i++) {
                dp[g][i] = INF;
            }
        }

        // Base case
        dp[0][0] = 0;

        // Step 3: Try making exactly k groups
        for (int g = 1; g <= k; g++) {

            for (int i = 2 * g; i <= n; i++) {

                // Starting position of last substring
                for (int p = 2 * (g - 1); p <= i - 2; p++) {

                    if (dp[g - 1][p] == INF) {
                        continue;
                    }

                    dp[g][i] = Math.min(
                            dp[g][i],
                            dp[g - 1][p] + cost[p][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
