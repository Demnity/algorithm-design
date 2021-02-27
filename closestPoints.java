import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class closestPoints {
    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2, 3));
        points.add(new Point(12, 30));
        points.add(new Point(40, 50));
        points.add(new Point(5, 1));
        points.add(new Point(12, 10));
        points.add(new Point(3, 4));
        System.out.println(closestPair(points));
    }

    public static double closestPair(List<Point> points) {
        if (points.size() <= 1) return Double.POSITIVE_INFINITY;
        List<Point> point_x = new ArrayList<>(points);
        List<Point> point_y = new ArrayList<>(points);
        Util.sortByX(point_x);
        Util.sortByY(point_y);
        return closestDistance(point_x, point_y);
    }

    public static double closestDistance(List<Point> point_x, List<Point> point_y) {
        if (point_x.size() <= 3) return Util.bruteForce(point_x);
        int mid = point_x.size() / 2;
        List<Point> Q_x = point_x.subList(0, mid);
        List<Point> R_x = point_x.subList(mid + 1, point_x.size() - 1);
        List<Point> Q_y = point_x.subList(0, mid);
        List<Point> R_y = point_x.subList(mid + 1, point_x.size() - 1);
        Util.sortByY(Q_y);
        Util.sortByY(R_y);
        double closest_Q = closestDistance(Q_x, Q_y);
        double closest_R = closestDistance(R_x, R_y);
        double delta = Math.min(closest_Q, closest_R);
        double x = Q_x.get(Q_x.size() - 1).x;
        List<Point> S_y = new ArrayList<>();
        for (Point point : point_y) {
            if (Math.abs(x - point.x) < delta) S_y.add(point);
        }
        for (int i = 0; i < S_y.size(); i++) {
            for (int j = 0; j < 15; j++) {
                if(i + j + 1 < S_y.size()) {
                    double distance = Util.distance(S_y.get(i), S_y.get(i + j + 1));
                    if (distance < delta) delta = distance;
                }
            }
        }
        return delta;
    }

    static class Point {

        public double x;

        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Useful methods for this assignment.
     */
    static class Util {

        /**
         * Takes two points and computes the euclidean distance between the two points.
         *
         * @param point1 - first point.
         * @param point2 - second point.
         * @return euclidean distance between the two points.
         * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">https://en.wikipedia.org/wiki/Euclidean_distance</a>
         */
        public static double distance(Point point1, Point point2) {
            return Math.sqrt(Math.pow(point1.x - point2.x, 2.0D) + Math.pow(point1.y - point2.y, 2.0D));
        }

        /**
         * Takes a list of points and sorts it on x (ascending).
         *
         * @param points - points that need to be sorted.
         */
        public static void sortByX(List<Point> points) {
            Collections.sort(points, Comparator.comparingDouble(point -> point.x));
        }

        /**
         * Takes a list of points and sorts it on y (ascending) .
         *
         * @param points - points that need to be sorted.
         */
        public static void sortByY(List<Point> points) {
            Collections.sort(points, Comparator.comparingDouble(point -> point.y));
        }

        /**
         * Takes a list of points and returns the distance between the closest pair.
         * This is done by brute forcing.
         *
         * @param points - list of points that need to be considered.
         * @return smallest pair-wise distance between points.
         */
        public static double bruteForce(List<Point> points) {
            int size = points.size();
            if (size <= 1)
                return Double.POSITIVE_INFINITY;
            double bestDist = Double.POSITIVE_INFINITY;
            for (int i = 0; i < size - 1; i++) {
                Point point1 = points.get(i);
                for (int j = i + 1; j < size; j++) {
                    Point point2 = points.get(j);
                    double distance = Util.distance(point1, point2);
                    if (distance < bestDist)
                        bestDist = distance;
                }
            }
            return bestDist;
        }
    }
}
