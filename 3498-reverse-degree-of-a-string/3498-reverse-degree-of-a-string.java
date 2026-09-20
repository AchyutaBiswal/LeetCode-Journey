class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {

            // Position in reversed alphabet
            int reverseValue = 'z' - s.charAt(i) + 1;

            // Position in string (1-indexed)
            int position = i + 1;

            sum += reverseValue * position;
        }

        return sum;
    }
}