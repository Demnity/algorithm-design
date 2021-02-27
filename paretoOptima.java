import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class paretoOptima {
    public static List<Solution> getParetoOptima(List<Solution> S) {
        // TODO
        S.sort(Comparator.comparingInt(x -> x.numberOfHours));
        if(S.size() > 1) return findPareto(S, 0, S.size() - 1);
        return S;
    }

    public static List<Solution> findPareto(List<Solution> S, int l, int r) {
        List<Solution> list = new ArrayList<>();
        if(l == r) {
            list.add(S.get(l));
            return list;
        }
        int m = (l + r) / 2;
        List<Solution> left = findPareto(S, l, m);
        List<Solution> right = findPareto(S, m + 1, r);
        for(Solution sol_l : left) {
            for(Solution sol_r : right) {
                if(sol_l.quality > sol_r.quality) left.add(sol_r);
            }
        }
        return left;
    }

    static class Solution {

        int numberOfHours;

        int quality;

        public Solution(int numberOfHours, int quality) {
            this.numberOfHours = numberOfHours;
            this.quality = quality;
        }

        /*
                You should not need the equals and hashcode method below, we just use them in the test.
             */
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Solution that = (Solution) o;
            return numberOfHours == that.numberOfHours && quality == that.quality;
        }

        @Override
        public int hashCode() {
            return Objects.hash(numberOfHours, quality);
        }
    }
}
