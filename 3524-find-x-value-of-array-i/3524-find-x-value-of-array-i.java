class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] dp = new long[k];
        long[] result = new long[k];

        for (int num : nums) {

            long[] next = new long[k];

            // Start a new subarray
            int rem = num % k;
            next[rem]++;

            // Extend previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] > 0) {
                    int newRem = (int) ((r * (long) num) % k);
                    next[newRem] += dp[r];
                }
            }

            // Add all subarrays ending at this index
            for (int r = 0; r < k; r++) {
                result[r] += next[r];
            }

            dp = next;
        }

        return result;
    }
}