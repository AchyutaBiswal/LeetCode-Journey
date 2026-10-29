
import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s,
                                        String[] queries) {

        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        int[][] up = new int[LOG][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] order = new int[n];
        int[] next = new int[n];
        int[] stack = new int[n];

        // Iterative DFS
        int top = 0;
        int timer = 0;

        stack[0] = 0;
        up[0][0] = 0;
        tin[0] = timer;
        order[timer++] = 0;

        while (top >= 0) {
            int u = stack[top];

            if (next[u] < graph[u].size()) {
                int v = graph[u].get(next[u]++);

                if (v == up[0][u]) {
                    continue;
                }

                up[0][v] = u;
                depth[v] = depth[u] + 1;
                tin[v] = timer;
                order[timer++] = v;

                stack[++top] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }

        // Binary lifting for LCA
        for (int k = 1; k < LOG; k++) {
            for (int u = 0; u < n; u++) {
                up[k][u] = up[k - 1][up[k - 1][u]];
            }
        }

        char[] chars = s.toCharArray();
        int[] initialMask = new int[n];

        // Calculate root-to-node character parity masks
        for (int i = 0; i < n; i++) {
            int u = order[i];
            int letterMask = 1 << (chars[u] - 'a');

            if (u == 0) {
                initialMask[u] = letterMask;
            } else {
                initialMask[u] =
                    initialMask[up[0][u]] ^ letterMask;
            }
        }

        // Fenwick tree for subtree range XOR updates
        int[] bit = new int[n + 2];

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                // Update all root paths passing through this subtree
                if (delta != 0) {
                    rangeXor(bit, tin[u] + 1, tout[u] + 1, delta);
                }

                chars[u] = c;

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v, depth, up, LOG);

                int maskU = initialMask[u]
                          ^ pointQuery(bit, tin[u] + 1);

                int maskV = initialMask[v]
                          ^ pointQuery(bit, tin[v] + 1);

                int ancestorMask = 1 << (chars[ancestor] - 'a');

                int pathMask = maskU ^ maskV ^ ancestorMask;

                // At most one character can have an odd frequency
                answer.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return answer;
    }

    private void rangeXor(int[] bit, int left, int right, int delta) {
        update(bit, left, delta);
        update(bit, right + 1, delta);
    }

    private void update(int[] bit, int index, int value) {
        while (index < bit.length) {
            bit[index] ^= value;
            index += index & -index;
        }
    }

    private int pointQuery(int[] bit, int index) {
        int result = 0;

        while (index > 0) {
            result ^= bit[index];
            index -= index & -index;
        }

        return result;
    }

    private int lca(int u, int v, int[] depth,
                    int[][] up, int LOG) {

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }
}
