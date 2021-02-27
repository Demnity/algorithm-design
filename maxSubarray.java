public class maxSubarray {
    public static void main(String[] args) {
        int n = 8;
        int[] h = { 0, 6, 5, 10, 12, 11, 13, 10, 2 };
        System.out.println(findBestTrainingDivideAndConquer(n, h));
    }
    public static int findBestTrainingDivideAndConquer(int n, int[] h) {
        // TODO
        return find(h, 1, n);
    }

    public static int find(int[] arr, int l, int r) {
        if(l == r) return 0;
        if(l + 1 == r) return evaluateJump(arr[l], arr[r]);
        int m = (l + r) / 2;
        int left = find(arr, l, m);
        int right = find(arr, m + 1, r);
        return Math.max(Math.max(find(arr, l, m), find(arr, m + 1, r)), merge(arr, l, r));
    }

    public static int merge(int[] arr, int l, int r) {
        int m = (l + r) / 2;
        int max_l = Integer.MIN_VALUE;
        int max_r = Integer.MIN_VALUE;
        int sum = 0;
        for(int i = m; i > l; i--) {
            sum += evaluateJump(arr[i - 1], arr[i]);
            if(sum > max_l) max_l = sum;
        }
        sum = 0;
        for(int i = m + 1; i < r; i++) {
            sum += evaluateJump(arr[i], arr[i + 1]);
            if(sum > max_r) max_r = sum;
        }
        int a = evaluateJump(arr[m], arr[m + 1]);
        return Math.max(Math.max(max_l, max_r), Math.max(Math.max(max_l + a, max_r + a), max_l + max_r + a));
    }

    public static int evaluateJump(int height1, int height2) {
        if (height1 <= height2) {
            return height2 - height1;
        } else {
            int diff = height1 - height2;
            return -(diff * diff);
        }
    }
}
