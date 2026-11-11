
class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {

        long option1 = (long) need1 * cost1
                     + (long) need2 * cost2;

        long common = Math.min(need1, need2);

        long remaining = Math.max(need1, need2) - common;

        // Buy common items using type 3
        // Buy remaining items using their required type
        long remainingCost = need1 > need2 ? cost1 : cost2;

        long option2 = common * costBoth
                     + remaining * remainingCost;

        // Buy only type 3 items
        long option3 = (long) Math.max(need1, need2) * costBoth;

        return Math.min(option1, Math.min(option2, option3));
    }
}
