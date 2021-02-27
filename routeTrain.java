import java.io.InputStream;
import java.util.*;

public class routeTrain {
    public static void main(String[] args) {
        System.out.println(solve());
    }

    public static String solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            nodes.put(i, new Node());
        }
        for (int i = 1; i <= m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            sc.nextInt();
            nodes.get(from).outgoingEdges.add(nodes.get(to));
        }
        sc.close();
        {
            Queue<Node> q = new LinkedList<>();
            Node startNode = nodes.get(s);
            startNode.marked = true;
            q.add(startNode);
            while (!q.isEmpty()) {
                Node node = q.remove();
                for (Node to : node.outgoingEdges) {
                    if (to.marked) {
                        return "yes";
                    } else {
                        to.marked = true;
                        q.add(to);
                    }
                }
            }
        }
        return "no";
    }

    static class Node {

        List<Node> outgoingEdges;

        boolean marked;

        public Node() {
            this.outgoingEdges = new ArrayList<>();
            this.marked = false;
        }
    }
}
