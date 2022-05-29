package com.lss.algorithm.study;

public class JumpGame {

    /**
     * 给定数组，nums[i]表示从位置i最多可以跳nums[i]步
     * 问是否可以跳到nums的length位置
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums){
        int n = nums.length;
        boolean[] enable = new boolean[n];

        enable[n-1] = true;

        for(int i = n - 2 ; i >=0  ; i --){
            int maxJump = nums[i];
            boolean canJumpThis = false;
            for(int step = 1 ; step <= maxJump ; step ++){
                if(enable[i + step]){
                    canJumpThis = true;
                    break;
                }
            }
            enable[i] = canJumpThis;
        }

        return enable[0];
    }

    /**
     * 对于每个位置i,可以最多跳转nums[i]步
     * 如果贪心地每步按照最大步骤跳，最后最远距离超过了length的话，
     * 说明，可以肯定能够跳到length位置: 可以前面的步骤不跳那么远
     * @param nums
     * @return
     */
    public static boolean canJumpGreedy(int[] nums){
        int farthest = 0;
        for(int i = 0 ;i < nums.length - 1 ; i ++){
            farthest = Math.max(farthest,i + nums[i]);
            if(farthest <= i){//可能碰到了0，卡住了
                return false;
            }
        }
        return farthest >= nums[nums.length - 1];
    }


    /**
     * 给定数组nums,位置i最多可以跳转nums[i]步骤
     * 求跳到length位置最少需要跳转多少步
     *
     * 并不是没一步跳转最远就一定得到最优解
     * [3,8,1,1,1....]
     * 第一步跳3步的效果明显没有跳转1步的效果好：跳一步到位置2，位置2可以最多跳转8步
     *
     * @param nums
     * @return
     */
    public static int leastJump(int[] nums){
        int step = 0;
        int farthest = 0;
        int end = 0; //前一步所能到达的最远距离
        for(int i = 0 ; i < nums.length - 1; i ++){
            farthest = Math.max(farthest,nums[i] + i);
            if(end == i){
                step ++;
                end = farthest;
            }
        }
        return step;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,1,1,4}; // true
//        nums = new int[]{3,2,1,0,4};//false
        System.out.println(canJump(nums));
        System.out.println(canJumpGreedy(nums));

        System.out.println(leastJump(nums));
    }
}
