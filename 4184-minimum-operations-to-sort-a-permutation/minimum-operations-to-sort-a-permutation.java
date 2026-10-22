class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int p = 0;
        for (int i = 0; i < n; i++) if (nums[i] == 0) p = i;

        boolean asc = true, desc = true;
        for (int i = 0; i < n; i++) {
            if (nums[(p + i) % n] != i) asc = false;
            if (nums[((p - i) % n + n) % n] != i) desc = false;
        }

        if (asc) return Math.min(p, n - p + 2);
        if (desc) return Math.min(n - p, (p + 1) % n + 1);
        return -1;
    }
}