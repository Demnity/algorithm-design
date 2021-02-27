import java.io.InputStream;
import java.util.*;

public class dijkstra {
    public static void main(String[] args) {
    }

    public String solve() {
        Scanner sc = new Scanner(System.in);
        /*
         * We already parse the input for you and should not need to make changes to this part of the code.
         * You are free to change this input format however.
         */
        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        int t = sc.nextInt();
        ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nodes.add(new HashMap<>());
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            nodes.get(u).put(v, cost);
        }

        int[] distance = new int[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        PriorityQueue<Tuple> pq = new PriorityQueue<>();

        for(int i = 0; i < nodes.size(); i++) {
            distance[i] = i == s ? 0 : Integer.MAX_VALUE;
        }
        pq.add(new Tuple(s, 0));

        while(!pq.isEmpty()) {
            Tuple tuple = pq.poll();
            if(visited[tuple.id]) continue;
            visited[tuple.id] = true;

            for(int v : nodes.get(tuple.id).keySet()) {
                int dist = distance[tuple.id] + nodes.get(tuple.id).get(v) + nodes.get(tuple.id).size();
                if(!visited[v] && dist < distance[v]) {
                    distance[v] = dist;
                    pq.add(new Tuple(v, distance[v]));
                }
            }
        }
        return visited[t] ? Integer.toString(distance[t]) : "-1";
    }

    class Tuple implements Comparable<Tuple>{
        int id;
        int weight;

        public Tuple(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return id == tuple.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }


        @Override
        public int compareTo(Tuple tuple) {
            int res = this.weight - tuple.weight;
            if(res == 0) return this.id - tuple.id;
            return res;
        }
    }
}
