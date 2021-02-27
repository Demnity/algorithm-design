import java.util.*;

public class similarItems {
    public static void main(String[] args) {
        int n = 4;
        int k = 2;
        int[][] c = { { 0, 0, 0, 0, 0 }, { 0, 0, 2, 3, 4 }, { 0, 2, 0, 1, 100 }, { 0, 3, 1, 0, 1 }, { 0, 4, 100, 1, 0 } };
        boolean[][] e = new boolean[n + 1][n + 1];
        e[2][4] = e[4][2] = true;
        System.out.println(legalisingDocuments(n, k, c, e));
    }
    public static int legalisingDocuments(int n, int k, int[][] d, boolean[][] e) {
        // TODO
        List<Edge> distances = new ArrayList<>();
        UnionFind uf = new UnionFind(n + 1);
        for(int i = 1; i <= n; i++) {
            for(int j = i + 1; j <= n; j++) {
                if(e[i][j]) {
                    uf.join(i, j);
                    continue;
                }
                distances.add(new Edge(i, j, d[i][j]));
            }
        }
        Collections.sort(distances, Comparator.comparingInt(x -> x.l));

        if(uf.clusters().size() < k) return 0;
        int count = n - uf.clusters().size();
        for(int i = 0; i < distances.size() && count != n - k; i++) {
            Edge distance = distances.get(i);
            if(uf.join(distance.x, distance.y)) {
                count++;
            }
        }
        List<List<Integer>> clusters = uf.clusters();
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < clusters.size(); i++) {
            for(int j = i + 1; j < clusters.size(); j++) {
                for(Integer x : clusters.get(i)){
                    for(Integer y : clusters.get(j)) {
                        if(min > d[x][y]) min = d[x][y];
                    }
                }
            }
        }
        return min;
    }

    static class UnionFind {

        private int[] parent;

        private int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        // returns false if x and y are in same set
        public boolean join(int x, int y) {
            int xrt = find(x);
            int yrt = find(y);
            if (rank[xrt] > rank[yrt])
                parent[yrt] = xrt;
            else if (rank[xrt] < rank[yrt])
                parent[xrt] = yrt;
            else if (xrt != yrt)
                rank[parent[yrt] = xrt]++;
            return xrt != yrt;
        }

        private int find(int x) {
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }

       List<List<Integer>> clusters() {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 1; i < parent.length; i++) {
                int root = find(i);
                if (!map.containsKey(root))
                    map.put(root, new ArrayList<>());
                map.get(root).add(i);
            }
            return new ArrayList<>(map.values());
        }
    }

    static class Edge {

        // from, to and length
        int x, y, l;

        public Edge(int from, int to, int length) {
            x = from;
            y = to;
            l = length;
        }
    }
}
