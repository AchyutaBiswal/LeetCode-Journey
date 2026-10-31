
class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1000000007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        // Process every cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                long r = right[i][j];
                long d = down[i][j];

                // Starting position
                if (i == 0 && j == 0) {
                    r = 1;
                }

                if (grid[i][j] == 1) {

                    // Mirror:
                    // Entering from left -> move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (int)((down[i + 1][j] + r) % MOD);
                    }

                    // Entering from top -> move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (int)((right[i][j + 1] + d) % MOD);
                    }

                } else {

                    // Empty cell: both directions are possible
                    long ways = (r + d) % MOD;

                    // Move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (int)((right[i][j + 1] + ways) % MOD);
                    }

                    // Move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (int)((down[i + 1][j] + ways) % MOD);
                    }
                }
            }
        }

        // Total ways to reach destination
        return (int)(((long)right[m - 1][n - 1]
                    + down[m - 1][n - 1]) % MOD);
    }
}
