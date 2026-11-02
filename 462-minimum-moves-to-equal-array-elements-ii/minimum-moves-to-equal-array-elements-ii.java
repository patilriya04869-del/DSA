class Solution {
    public int minMoves2(int[] nums) {
        int n=nums.length;
        Arrays.sort(nums);
        int median=nums[n/2];
        long ans=0;
        for(int i=0;i<n;i++)
        {
            ans+=Math.abs((long)nums[i]-median);
        }
        return(int) ans ;
        
    }
}