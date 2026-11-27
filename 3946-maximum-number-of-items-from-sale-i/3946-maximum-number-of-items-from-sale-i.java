class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        // Calculate free items for every item type
        int[] bonus = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    bonus[i]++;
                }
            }
        }

        // dp[b] = maximum total items using budget b
        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {

            int price = items[i][1];
            int freeItems = bonus[i];

            // Step 1: Buy the first copy (activation)
            // 0/1 Knapsack: iterate backward
            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b], dp[b - price] + 1 + freeItems);
            }

            // Step 2: Buy additional copies
            // Unbounded Knapsack: iterate forward
            for (int b = price; b <= budget; b++) {
                dp[b] = Math.max(dp[b], dp[b - price] + 1);
            }
        }

        return dp[budget];
    }
}