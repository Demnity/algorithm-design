import java.util.ArrayList;
import java.util.List;

public class optimalTree {
    public static void main(String[] args) {

    }


    public static int decisionTree(int d, List<Pair<Integer[], Integer>> samples) {
        // TODO
        if(d == 0 || samples.size() == 1) return maxLabel(samples);
        if(samples.size() == 0) return 0;
        int best_classification = 0;
        for(int i = 0; i < samples.get(0).getL().length; i++) {
            List<Pair<Integer[], Integer>> left = new ArrayList<>();
            List<Pair<Integer[], Integer>> right = new ArrayList<>();

            for(int j = 0; j < samples.size(); j++) {
                if(samples.get(j).getL()[i] == 0) left.add(samples.get(j));
                else right.add(samples.get(j));
            }

            best_classification = Math.max(best_classification, decisionTree(d - 1, left) + decisionTree(d - 1, right));
        }
        return best_classification;
    }

    public static int maxLabel(List<Pair<Integer[], Integer>> samples) {
        int count = 0;
        for(Pair<Integer[], Integer> pair : samples) {
            if(pair.getR() == 1) count++;
        }
        if(count < samples.size() - count) return samples.size() - count;
        return count;
    }

    class Pair<L, R> {

        private L l;

        private R r;

        /**
         * Constructor
         * @param l left element
         * @param r right element
         */
        public Pair(L l, R r) {
            this.l = l;
            this.r = r;
        }

        /**
         * @return the left element
         */
        public L getL() {
            return l;
        }

        /**
         * @return the right element
         */
        public R getR() {
            return r;
        }

        /**
         * @param l left element
         */
        public void setL(L l) {
            this.l = l;
        }

        /**
         * @param r right element
         */
        public void setR(R r) {
            this.r = r;
        }
    }

}
