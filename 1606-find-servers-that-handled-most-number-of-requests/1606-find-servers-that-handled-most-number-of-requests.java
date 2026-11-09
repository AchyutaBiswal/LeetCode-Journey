
class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        // Available servers
        TreeSet<Integer> available = new TreeSet<>();

        // Store finishing time and server ID
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];

        // Initially all servers are available
        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        // Process every request
        for (int i = 0; i < arrival.length; i++) {

            // Release servers whose requests are completed
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {

                long[] finished = busy.poll();

                available.add((int) finished[1]);
            }

            // If no server is available, drop the request
            if (available.isEmpty()) {
                continue;
            }

            // Find the next available server
            int server = i % k;

            Integer selected = available.ceiling(server);

            // Wrap around if necessary
            if (selected == null) {
                selected = available.first();
            }

            // Assign request to selected server
            available.remove(selected);

            count[selected]++;

            // Calculate finishing time
            long finishTime = (long) arrival[i] + load[i];

            busy.offer(new long[]{finishTime, selected});
        }

        // Find maximum number of requests handled
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        // Collect all busiest servers
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                ans.add(i);
            }
        }

        return ans;
    }
}
