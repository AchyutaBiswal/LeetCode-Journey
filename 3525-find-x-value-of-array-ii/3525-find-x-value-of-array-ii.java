class Solution {

    static class Node {
        int prod;
        long[] pref;
        long[] suff;
        long[] cnt;

        Node(int k) {
            pref = new long[k];
            suff = new long[k];
            cnt = new long[k];
        }
    }

    int k;
    int n;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.k = k;
        this.n = nums.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Persistent update
            update(1, 0, n - 1, index, value);

            // Get range [start, n - 1]
            Node res = query(1, 0, n - 1, start, n - 1);

            // IMPORTANT:
            // Remaining array after removing a suffix
            // is a PREFIX of [start ... n-1].
            ans[q] = (int) res.pref[x];
        }

        return ans;
    }

    private void build(int node, int left, int right, int[] nums) {

        if (left == right) {

            tree[node] = new Node(k);

            int r = nums[left] % k;

            tree[node].prod = r;
            tree[node].pref[r] = 1;
            tree[node].suff[r] = 1;
            tree[node].cnt[r] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, nums);
        build(node * 2 + 1, mid + 1, right, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node merge(Node a, Node b) {

        Node res = new Node(k);

        // Product of complete segment
        res.prod = (int) ((long) a.prod * b.prod % k);

        // -------------------------
        // PREFIX
        // -------------------------

        // Prefix completely inside left
        for (int r = 0; r < k; r++) {
            res.pref[r] += a.pref[r];
        }

        // Entire left + prefix of right
        for (int r = 0; r < k; r++) {

            int newR = (int) ((long) a.prod * r % k);

            res.pref[newR] += b.pref[r];
        }

        // -------------------------
        // SUFFIX
        // -------------------------

        // Suffix completely inside right
        for (int r = 0; r < k; r++) {
            res.suff[r] += b.suff[r];
        }

        // Suffix of left + entire right
        for (int r = 0; r < k; r++) {

            int newR = (int) ((long) r * b.prod % k);

            res.suff[newR] += a.suff[r];
        }

        // -------------------------
        // SUBARRAYS
        // -------------------------

        // Inside left
        for (int r = 0; r < k; r++) {
            res.cnt[r] += a.cnt[r];
        }

        // Inside right
        for (int r = 0; r < k; r++) {
            res.cnt[r] += b.cnt[r];
        }

        // Crossing middle
        for (int i = 0; i < k; i++) {

            if (a.suff[i] == 0)
                continue;

            for (int j = 0; j < k; j++) {

                if (b.pref[j] == 0)
                    continue;

                int r = (int) ((long) i * j % k);

                res.cnt[r] += a.suff[i] * b.pref[j];
            }
        }

        return res;
    }

    private void update(int node, int left, int right,
                        int index, int value) {

        if (left == right) {

            tree[node] = new Node(k);

            int r = value % k;

            tree[node].prod = r;
            tree[node].pref[r] = 1;
            tree[node].suff[r] = 1;
            tree[node].cnt[r] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node query(int node, int left, int right,
                       int ql, int qr) {

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return query(node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, right, ql, qr);
        }

        Node a = query(node * 2, left, mid, ql, qr);
        Node b = query(node * 2 + 1, mid + 1, right, ql, qr);

        return merge(a, b);
    }
}