import java.io.InputStream;
import java.util.*;

public class clusteringMST {
    public static void main(String[] args) {

    }

    public static String solve(InputStream in) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        List<House> houses = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            houses.add(new House(i, sc.nextInt(), sc.nextInt()));
        }
        int m = n * (n - 1) / 2;
        List<Distance> distances = new ArrayList<>(m);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances.add(new Distance(houses.get(i), houses.get(j)));
            }
        }
        UnionFind unionFind = new UnionFind(houses);
        //TODO
        distances.sort(Comparator.comparingLong(x -> x.distance));
        int count = 0;
        for(int i = 0; i < distances.size() && count != n - k; i++) {
            Distance distance = distances.get(i);
            if(unionFind.join(distance.a, distance.b)) {
                count++;
            }
        }
        StringBuilder str = new StringBuilder();
        for(List<House> clusters : unionFind.clusters()) {
            double avg_x = clusters.stream().mapToInt(x -> x.x).average().orElse(-1);
            double avg_y = clusters.stream().mapToInt(x -> x.y).average().orElse(-1);
            str.append("" + avg_x + " " + avg_y + '\n');
        }
        return str.toString();
    }

    static class House {

        int id, x, y;

        public House(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    static class Distance {

        House a, b;

        long distance;

        public Distance(House a, House b) {
            this.a = a;
            this.b = b;
            // Square Euclidean distance, to avoid floating-point errors
            this.distance = (long) (a.x - b.x) * (a.x - b.x) + (long) (a.y - b.y) * (a.y - b.y);
        }
    }

    static class UnionFind {

        private List<House> houses;

        private int[] parent;

        private int[] rank;

        UnionFind(List<House> houses) {
            this.houses = houses;
            int n = houses.size();
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        /**
         * Joins two disjoint sets together, if they are not already joined.
         * @return false if x and y are in same set, true if the sets of x and y are now joined.
         */
        boolean join(House x, House y) {
            int xrt = find(x.id);
            int yrt = find(y.id);
            if (rank[xrt] > rank[yrt])
                parent[yrt] = xrt;
            else if (rank[xrt] < rank[yrt])
                parent[xrt] = yrt;
            else if (xrt != yrt)
                rank[parent[yrt] = xrt]++;
            return xrt != yrt;
        }

        /**
         * @return The house that is indicated as the "root" of the set of house h.
         */
        House find(House h) {
            return houses.get(find(h.id));
        }

        private int find(int x) {
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }

        /**
         * @return All clusters of houses
         */
        Collection<List<House>> clusters() {
            Map<Integer, List<House>> map = new HashMap<>();
            for (int i = 0; i < parent.length; i++) {
                int root = find(i);
                if (!map.containsKey(root))
                    map.put(root, new ArrayList<>());
                map.get(root).add(houses.get(i));
            }
            return map.values();
        }
    }
}
