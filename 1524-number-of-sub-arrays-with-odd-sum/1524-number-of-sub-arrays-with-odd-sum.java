
class Solution {
    public int numOfSubarrays(int[] arr) {

        int mod = 1000000007;

        long odd = 0;
        long even = 1;
        long prefixSum = 0;
        long ans = 0;

        for (int num : arr) {

            prefixSum += num;

            if (prefixSum % 2 == 1) {

                ans = (ans + even) % mod;
                odd++;

            } else {

                ans = (ans + odd) % mod;
                even++;
            }
        }

        return (int) ans;
    }
}
