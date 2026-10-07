import java.util.*;

class Solution {

    Set<String> result = new HashSet<>();

    public List<String> removeInvalidParentheses(String s) {

        int left = 0;
        int right = 0;

        // Find extra '(' and ')'
        for (char c : s.toCharArray()) {

            if (c == '(') {
                left++;
            } 
            else if (c == ')') {

                if (left > 0) {
                    left--;
                } 
                else {
                    right++;
                }
            }
        }

        // Start DFS
        dfs(s, 0, left, right, 0, new StringBuilder());

        return new ArrayList<>(result);
    }

    private void dfs(
            String s,
            int index,
            int left,
            int right,
            int balance,
            StringBuilder current) {

        // Invalid
        if (balance < 0) {
            return;
        }

        // End of string
        if (index == s.length()) {

            if (left == 0 && right == 0 && balance == 0) {
                result.add(current.toString());
            }

            return;
        }

        char c = s.charAt(index);

        // Option 1: Remove current character
        if (c == '(' && left > 0) {

            dfs(
                s,
                index + 1,
                left - 1,
                right,
                balance,
                current
            );
        }

        if (c == ')' && right > 0) {

            dfs(
                s,
                index + 1,
                left,
                right - 1,
                balance,
                current
            );
        }

        // Option 2: Keep current character
        current.append(c);

        if (c == '(') {

            dfs(
                s,
                index + 1,
                left,
                right,
                balance + 1,
                current
            );

        } else if (c == ')') {

            dfs(
                s,
                index + 1,
                left,
                right,
                balance - 1,
                current
            );

        } else {

            // Letter
            dfs(
                s,
                index + 1,
                left,
                right,
                balance,
                current
            );
        }

        // Backtrack
        current.deleteCharAt(current.length() - 1);
    }
}