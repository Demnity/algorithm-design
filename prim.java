import java.io.InputStream;
import java.util.*;

public class prim {
    public static void main(String[] args) {
        System.out.println(solve());
    }

    public static String solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int budget = sc.nextInt();

        ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<>();
        int[] distance = new int[n];
        boolean[] visited = new boolean[n];
        for(int i = 0; i < n; i++) {
            nodes.add(new HashMap<>());
        }
        for(int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            nodes.get(u).put(v, cost);
            nodes.get(v).put(u, cost);
        }
        for(int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[0] = 0;
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        pq.add(new Tuple(0, 0));
        int minCost = 0;
        List<Integer> minEdges = new ArrayList<>();
        while(!pq.isEmpty()) {
            Tuple tuple = pq.poll();
            if(visited[tuple.id]) continue;
            minCost += tuple.weight;
            minEdges.add(tuple.weight);
            visited[tuple.id] = true;
            for(int v : nodes.get(tuple.id).keySet()) {
                if(!visited[v] && nodes.get(tuple.id).get(v) < distance[v]) {
                    distance[v] = nodes.get(tuple.id).get(v);
                    pq.add(new Tuple(v, distance[v]));
                }
            }
        }
        Arrays.sort(distance);
        int count = 0;
        int minBudget = 0;
        for(int i = 1; i < distance.length; i++) {
            minBudget += distance[i];
            if(minBudget <= budget) count++;
        }
        System.out.println(Arrays.toString(distance));
        return "" + minCost + " " + count;
    }

    static class Tuple implements Comparable<Tuple>{
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
