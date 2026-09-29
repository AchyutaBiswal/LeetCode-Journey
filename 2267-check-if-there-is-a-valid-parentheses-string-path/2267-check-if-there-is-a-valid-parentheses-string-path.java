class Solution {

    public boolean hasValidPath(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Length of every path
        int length = m + n - 1;

        // Valid parentheses string must have even length
        if (length % 2 != 0) {
            return false;
        }

        // First character must be '('
        if (grid[0][0] == ')') {
            return false;
        }

        // Last character must be ')'
        if (grid[m - 1][n - 1] == '(') {
            return false;
        }

        // dp[i][j][balance]
        boolean[][][] dp = new boolean[m][n][length + 1];

        // Start from (0,0)
        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                // Skip starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance <= length; balance++) {

                    int newBalance;

                    if (grid[i][j] == '(') {
                        newBalance = balance + 1;
                    } else {
                        newBalance = balance - 1;
                    }

                    // Balance cannot become negative
                    if (newBalance < 0 || newBalance > length) {
                        continue;
                    }

                    // Come from top
                    if (i > 0 && dp[i - 1][j][balance]) {
                        dp[i][j][newBalance] = true;
                    }

                    // Come from left
                    if (j > 0 && dp[i][j - 1][balance]) {
                        dp[i][j][newBalance] = true;
                    }
                }
            }
        }

        // At the end balance must be exactly 0
        return dp[m - 1][n - 1][0];
    }
}