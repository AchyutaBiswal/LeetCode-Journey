class Solution {
    private static final int MOD = 1_000_000_007;
    private long[] sum, sq, lazy;

    private void apply(int o, int l, int r, long v) {
        long len = r - l + 1;
        // sq must use the old sum, so update sq first
        sq[o] = (sq[o] + 2 * v % MOD * sum[o] % MOD + v * v % MOD * len % MOD) % MOD;
        sum[o] = (sum[o] + v * len) % MOD;
        lazy[o] = (lazy[o] + v) % MOD;
    }

    private void push(int o, int l, int r) {
        if (lazy[o] != 0) {
            int m = (l + r) >>> 1;
            apply(2 * o, l, m, lazy[o]);
            apply(2 * o + 1, m + 1, r, lazy[o]);
            lazy[o] = 0;
        }
    }

    private void update(int o, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            apply(o, l, r, 1);
            return;
        }
        push(o, l, r);
        int m = (l + r) >>> 1;
        if (ql <= m) update(2 * o, l, m, ql, qr);
        if (qr > m) update(2 * o + 1, m + 1, r, ql, qr);
        sum[o] = (sum[2 * o] + sum[2 * o + 1]) % MOD;
        sq[o] = (sq[2 * o] + sq[2 * o + 1]) % MOD;
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long ans = 0;
        for (int j = 0; j < n; j++) {
            update(1, 0, n - 1, last[nums[j]] + 1, j);
            last[nums[j]] = j;
            ans = (ans + sq[1]) % MOD;
        }
        return (int) ans;
    }
}