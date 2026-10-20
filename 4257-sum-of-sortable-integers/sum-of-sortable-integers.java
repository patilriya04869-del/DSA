class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int k = 1; k <= n; k++) {
            if (n % k == 0 && ok(nums, k)) ans += k;
        }
        return ans;
    }

    private boolean ok(int[] nums, int k) {
        int n = nums.length;
        int prevMax = 0;
        for (int s = 0; s < n; s += k) {
            int e = s + k - 1;
            int cnt = 0, p = -1;
            for (int j = s; j < e; j++) {
                if (nums[j] > nums[j + 1]) {
                    cnt++;
                    p = j;
                }
            }
            int min, max;
            if (cnt == 0) {
                min = nums[s];
                max = nums[e];
            } else if (cnt == 1 && nums[e] <= nums[s]) {
                min = nums[p + 1];
                max = nums[p];
            } else {
                return false;
            }
            if (min < prevMax) return false;
            prevMax = max;
        }
        return true;
    }
}