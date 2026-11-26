
class Solution {
    public boolean canAliceWin(int n) {

        int remove = 10;
        boolean aliceTurn = true;

        while (n >= remove) {

            n = n - remove;

            aliceTurn = !aliceTurn;

            remove--;
        }

        return !aliceTurn;
    }
}
