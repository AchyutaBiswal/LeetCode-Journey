
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        // Find maximum factor and minimum price
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

        // Count frequency of each factor
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Count how many items each factor can unlock
        int[] total = new int[maxFactor + 1];

        for (int i = 1; i <= maxFactor; i++) {

            for (int j = i; j <= maxFactor; j += i) {
                total[i] += freq[j];
            }
        }

        // Sort items by increasing price
        Arrays.sort(items, (a, b) ->
            Integer.compare(a[1], b[1])
        );

        long remaining = budget;
        long ans = 0;

        // Buy bonus copies first
        for (int[] item : items) {

            int factor = item[0];
            int price = item[1];

            // Bonus purchase is not better than
            // buying two cheapest normal items
            if ((long) price >= 2L * minPrice) {
                break;
            }

            // Number of available free-item opportunities
            int gain = total[factor] - 1;

            if (gain == 0) {
                continue;
            }

            // Maximum bonus copies we can afford
            long take = Math.min(
                (long) gain,
                remaining / price
            );

            // Each bonus purchase gives 2 items
            ans += take * 2;

            remaining -= take * price;
        }

        // Spend remaining budget on cheapest normal items
        ans += remaining / minPrice;

        return (int) ans;
    }
}
