class Solution {
    public int majorityElement(int[] nums) {
        int candidate=0;
        int voteCount=0;
        for(int num:nums)
        {
            if(voteCount==0)
            {
                candidate=num;
            }
            if(num==candidate)
            {
                voteCount++;
            }
            else
            {
                voteCount--;
            }
        }
        return candidate;
    }
}