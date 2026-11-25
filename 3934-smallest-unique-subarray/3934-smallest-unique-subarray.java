import java.util.*;

class Solution {

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return 1;
        }

        // Remaining code stays the same
        int[] sa = buildSuffixArray(nums);
        int[] lcp = buildLCP(nums, sa);

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int answer = n;

        for (int i = 0; i < n; i++) {

            int r = rank[i];

            int leftLCP = lcp[r];
            int rightLCP = (r + 1 < n) ? lcp[r + 1] : 0;

            int maxLCP = Math.max(leftLCP, rightLCP);

            int requiredLength = maxLCP + 1;

            // The subarray must fit inside the array
            if (requiredLength <= n - i) {
                answer = Math.min(answer, requiredLength);
            }
        }

        return answer;
    }

    // Build suffix array using doubling algorithm
    private int[] buildSuffixArray(int[] nums) {

        int n = nums.length;

        // Add a unique smallest sentinel at the end
        int N = n + 1;

        int[] arr = new int[N];

        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
        }

        arr[n] = 0;

        int[] p = new int[N];
        int[] c = new int[N];

        int maxValue = 100001;

        int[] cnt = new int[Math.max(N, maxValue)];

        // Initial sorting by element value
        for (int x : arr) {
            cnt[x]++;
        }

        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        for (int i = N - 1; i >= 0; i--) {
            p[--cnt[arr[i]]] = i;
        }

        // Assign initial classes
        c[p[0]] = 0;

        int classes = 1;

        for (int i = 1; i < N; i++) {

            if (arr[p[i]] != arr[p[i - 1]]) {
                classes++;
            }

            c[p[i]] = classes - 1;
        }

        // Doubling
        for (int len = 1; len < N; len <<= 1) {

            int[] pn = new int[N];

            for (int i = 0; i < N; i++) {
                pn[i] = p[i] - len;

                if (pn[i] < 0) {
                    pn[i] += N;
                }
            }

            Arrays.fill(cnt, 0, classes, 0);

            for (int x : pn) {
                cnt[c[x]]++;
            }

            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                p[--cnt[c[pn[i]]]] = pn[i];
            }

            int[] cn = new int[N];

            cn[p[0]] = 0;

            int newClasses = 1;

            for (int i = 1; i < N; i++) {

                int cur = p[i];
                int prev = p[i - 1];

                int curSecond = (cur + len) % N;
                int prevSecond = (prev + len) % N;

                if (c[cur] != c[prev] ||
                    c[curSecond] != c[prevSecond]) {

                    newClasses++;
                }

                cn[cur] = newClasses - 1;
            }

            c = cn;
            classes = newClasses;
        }

        // Remove the sentinel suffix
        int[] sa = new int[n];

        int idx = 0;

        for (int x : p) {
            if (x != n) {
                sa[idx++] = x;
            }
        }

        return sa;
    }

    // Kasai algorithm for LCP array
    private int[] buildLCP(int[] nums, int[] sa) {

        int n = nums.length;

        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int k = 0;

        for (int i = 0; i < n; i++) {

            int r = rank[i];

            if (r == 0) {
                k = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + k < n &&
                   j + k < n &&
                   nums[i + k] == nums[j + k]) {

                k++;
            }

            lcp[r] = k;

            if (k > 0) {
                k--;
            }
        }

        return lcp;
    }
}