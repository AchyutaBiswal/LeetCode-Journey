
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {

        int n = nums.size();
        int minSum = Integer.MAX_VALUE;

        // Starting index
        for (int i = 0; i < n; i++) {

            int sum = 0;

            // Ending index
            for (int j = i; j < n; j++) {

                sum += nums.get(j);

                int length = j - i + 1;

                // Check subarray length
                if (length >= l && length <= r) {

                    // Check positive sum
                    if (sum > 0) {
                        minSum = Math.min(minSum, sum);
                    }
                }

                // No need to check larger lengths
                if (length >= r) {
                    break;
                }
            }
        }

        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }
}
