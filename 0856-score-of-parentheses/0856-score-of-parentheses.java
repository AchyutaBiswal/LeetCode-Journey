class Solution {
    public int scoreOfParentheses(String s) {

        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Start a new group
                stack.push(0);
            } else {
                // Score inside the current ()
                int innerScore = stack.pop();

                // () = 1
                // (A) = 2 * A
                int score = (innerScore == 0) ? 1 : 2 * innerScore;

                // Add this score to the outer group
                stack.push(stack.pop() + score);
            }
        }

        return stack.pop();
    }
}