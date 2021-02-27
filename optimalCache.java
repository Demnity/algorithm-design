import java.util.Arrays;
import java.util.PriorityQueue;

public class optimalCache {
    public static void main(String[] args) {
        int n = 5;
        int m = 10;
        int[] start = { 0, 2, 1, 17, 3, 15 };
        int[] end = { 0, 6, 2, 7, 9, 6 };
        System.out.println(solve(n,m,start, end));
    }

    static int solve(int n, int m, int[] start, int[] duration) {
        Tuple[] tuples = new Tuple[n];
        for(int i = 1; i <= n; i++) {
            tuples[i - 1] = new Tuple(start[i], start[i] + duration[i]);
        }
        Arrays.sort(tuples);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int count = 0;
        for(Tuple tuple : tuples) {
            while(!pq.isEmpty()) {
                if(tuple.start < pq.peek()) break;
                int end = pq.poll();
                if(end + m >= tuple.start) {
                    count++;
                    break;
                }
            }
            pq.add(tuple.end);
        }
        return count;
    }

    static class Tuple implements Comparable<Tuple>{
        int start;
        int end;

        public Tuple(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Tuple tuple) {
            int res = this.start - tuple.start;
            if(res == 0) return this.end - tuple.end;
            return res;
        }
    }
}
