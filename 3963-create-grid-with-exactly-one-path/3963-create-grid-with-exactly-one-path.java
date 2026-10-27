class Solution {
    public String[] createGrid(int m, int n) {
        String[] grid = new String[m];

        for (int i = 0; i < m; i++) {
            char[] row = new char[n];

            for (int j = 0; j < n; j++) {
                row[j] = '#';
            }

            grid[i] = new String(row);
        }

        char[][] arr = new char[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = '#';
            }
        }

        // Create one path: move right along row 0,
        // then down along the last column.
        for (int j = 0; j < n; j++) {
            arr[0][j] = '.';
        }

        for (int i = 1; i < m; i++) {
            arr[i][n - 1] = '.';
        }

        for (int i = 0; i < m; i++) {
            grid[i] = new String(arr[i]);
        }

        return grid;
    }
}