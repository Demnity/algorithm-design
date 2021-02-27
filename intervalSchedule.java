import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class intervalSchedule {
    public static void main(String[] args) {
        System.out.println(solve());
    }

    public static String solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Tuple[] tuples = new Tuple[n];
        for(int i = 0; i < n; i++) {
            int start = sc.nextInt();
            int end = start + sc.nextInt();
            tuples[i] = new Tuple(start, end);
        }

        Arrays.sort(tuples);

        int depth = 0;
        for (int i = 1; i < n; i++) {
            int count = 1;
            for (int j = 0; j < i; j++) {
                if (tuples[j].end > tuples[i].start) {
                    count++;
                }
            }
            if (count > depth) {
                depth = count;
            }
        }
        return Integer.toString(depth);
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
