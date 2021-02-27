import java.util.*;

public class zoomCalls {
    public static void main(String[] args) {
        int n = 3;
        Set<Connection> connections = new HashSet<>();
        connections.add(new Connection(1, 2, 0.8));
        connections.add(new Connection(1, 3, 0.7));
        // There is only one connection we can use to each
        // For student 2 a successrate of 0.8 so a failure rate of 0.2
        // For student 3 a successrate of 0.7 so a failure rate of 0.3
        // So the largest failure change is 0.3
        System.out.println(minimalLargestFailureChance(n, connections));
    }
    static class Connection {

        int computer1;

        int computer2;

        double successRate;

        public Connection(int computer1, int computer2, double successRate) {
            this.computer1 = computer1;
            this.computer2 = computer2;
            this.successRate = successRate;
        }
    }
    public static double minimalLargestFailureChance(int n, Set<Connection> connections) {
        // TODO
        Double max = Double.MIN_VALUE;
        for(int i = 2; i <= n; i++) {
            Double dist = 1 - getTo(n, i, 1, connections);
            if(max < dist) max = dist;
        }
        return max;
    }

    public static double getTo(int n, int s, int t, Set<Connection> connections) {
        ArrayList<HashMap<Integer, Double>> nodes = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nodes.add(new HashMap<>());
        }
        for (Connection connection : connections) {
            nodes.get(connection.computer1).put(connection.computer2, connection.successRate);
            nodes.get(connection.computer2).put(connection.computer1, connection.successRate);
        }

        double[] distance = new double[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        double[] prop = new double[nodes.size()];
        prop[s] = 1L;
        for(int i = 0; i < nodes.size(); i++) {
            distance[i] = i == s ? 0L : Double.MIN_VALUE;
        }
        pq.add(new Tuple(s, 0L));

        while(!pq.isEmpty()) {
            Tuple tuple = pq.poll();
            if(visited[tuple.id]) continue;
            visited[tuple.id] = true;

            for(int v : nodes.get(tuple.id).keySet()) {
                double dist = distance[tuple.id] + nodes.get(tuple.id).get(v);
                if(!visited[v] && dist > distance[v]) {
                    distance[v] = dist;
                    pq.add(new Tuple(v, distance[v]));
                    if(prop[v] < prop[tuple.id] * nodes.get(tuple.id).get(v)) prop[v] = prop[tuple.id] * nodes.get(tuple.id).get(v);
                }
            }
        }
        return visited[t] ? prop[t] : 0;
    }

    static class Tuple implements Comparable<Tuple>{
        int id;
        double weight;

        public Tuple(int id, double weight) {
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
            double res = - this.weight + tuple.weight;
            if(res == 0) return this.id - tuple.id;
            else if(res > 0) return 1;
            else return -1;
        }
    }
}
