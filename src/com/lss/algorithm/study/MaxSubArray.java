package com.lss.algorithm.study;

public class MaxSubArray {

    /**
     * 计算给定数组的最大子数组，返回这个最大子数组的和
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums){
        //以i结束的最大子数组的和
        int[] dp = new int[nums.length];
        int result = nums[0];
        for(int i = 1 ; i < dp.length ; i ++){
            dp[i] = Math.max(dp[i - 1] + nums[i],nums[i]);
            if(dp[i] > result){
                result = dp[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-3,1,3,-1,2,-4,2};
        System.out.println(maxSubArray(nums));
    }

}
