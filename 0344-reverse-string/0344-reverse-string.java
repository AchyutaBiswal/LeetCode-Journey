
class Solution {

    public void reverseString(char[] s) {
        reverse(s, 0, s.length - 1);
    }

    public void reverse(char[] s, int left, int right) {

        // 1. Base case
        if (left >= right) {
            return;
        }

        // 2. Swap characters
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;

        // 3. Recursive call
        reverse(s, left + 1, right - 1);
    }
}
