
import java.util.*;

class Solution {

    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Step 1: Build original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Step 2: Store new parent
        int[] newParent = parent.clone();

        // Stores latest ancestor for each character
        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfs(0, tree, s, last, newParent);

        // Step 3: Build modified tree
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Step 4: Calculate subtree sizes
        int[] answer = new int[n];

        calculateSize(0, newTree, answer);

        return answer;
    }

    private void dfs(int node, List<Integer>[] tree,
                     String s, int[] last, int[] newParent) {

        int ch = s.charAt(node) - 'a';

        int previous = last[ch];

        if (previous != -1) {
            newParent[node] = previous;
        }

        // Save current node as latest ancestor
        last[ch] = node;

        for (int child : tree[node]) {
            dfs(child, tree, s, last, newParent);
        }

        // Backtrack
        last[ch] = previous;
    }

    private int calculateSize(int node,
                              List<Integer>[] tree,
                              int[] answer) {

        int size = 1;

        for (int child : tree[node]) {
            size += calculateSize(child, tree, answer);
        }

        answer[node] = size;

        return size;
    }
}
