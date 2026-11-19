
import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {

        int n = nums.length;

        // Sort the array
        Arrays.sort(nums);

        // Case 1: Change two smallest elements
        int case1 = nums[n - 1] - nums[2];

        // Case 2: Change smallest and largest elements
        int case2 = nums[n - 2] - nums[1];

        // Case 3: Change two largest elements
        int case3 = nums[n - 3] - nums[0];

        // Return minimum among three cases
        return Math.min(case1, Math.min(case2, case3));
    }
}
