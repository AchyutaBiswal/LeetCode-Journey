
class Solution {
    public int maxLength(int[] nums) {

        int maxLen = 0;

        for (int i = 0; i < nums.length; i++) {

            long product = 1;
            long gcd = 0;
            long lcm = 1;

            for (int j = i; j < nums.length; j++) {

                product *= nums[j];

                gcd = findGCD(gcd, nums[j]);

                lcm = (lcm * nums[j]) / findGCD(lcm, nums[j]);

                if (product == gcd * lcm) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    private long findGCD(long a, long b) {

        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}
