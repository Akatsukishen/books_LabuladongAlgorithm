package com.lss.algorithm.study;

import java.util.HashMap;

/**
 * 给定一个非负整数的数组，其可以添加 正号+ 和 负号-
 * 计算所有组合相加为指定target的组合数
 * 如 [1,3,1,4,2] target = 5
 * -1 + 3 + 1 + 4 - 2 = 5
 * +1 + 3 - 1 + 4 - 2 = 5
 * +1 - 3 + 1 + 4 + 2 = 5
 *
 */
public class TargetSum {

    /**
     * 使用回溯递归：  时间复杂度 2^N
     * @param nums
     * @param target
     * @return
     */
    public static int findTargetSumWay(int[] nums, int target){
        int[] ways = new int[1];
        findTargetSumWay(nums,0,target,ways);
        return ways[0];
    }

    private static void findTargetSumWay(int[] nums, int index,int res,int[] ways){
        if(index == nums.length){
            if(res == 0){
                ways[0] ++;
            }
            return;
        }

        // res =  + nums[index] ....
        findTargetSumWay(nums,index + 1,res - nums[index],ways);
        // res = -nums[index] ....
        findTargetSumWay(nums,index + 1,res + nums[index] ,ways);
    }

    /**
     * 使用备忘录来消除重叠子问题: 时间复杂度 N * target
     * @param nums
     * @param target
     * @return
     */
    public static int findTargetSumWayWithMemory(int[] nums, int target){
        return findTargetSumWayWithMemory(nums,0,target,new HashMap<>());
    }

    private static int findTargetSumWayWithMemory(int[] nums, int index, int res, HashMap<String,Integer> memo){
        if(index == nums.length){
            if(res == 0){
                return 1;
            }
            return 0;
        }

        String key = index + "," + res;
        if(memo.containsKey(key)){
            return memo.get(key);
        }

        int result = findTargetSumWayWithMemory(nums,index + 1, res - nums[index],memo) + findTargetSumWayWithMemory(nums,index + 1, res + nums[index],memo);
        memo.put(key,result);

        return result;
    }

    /**
     * 正数的集合 sumA
     * 负数的集合 sumB
     * sumA - sumB = target
     * sumA = sumB + target
     * sumA + sumA = sumB + sumA + target
     * 2 * sumA = sum + target
     * sumA = (sum + target ) / 2
     * 数组中集合sumA 恰好等于 (sum + target ) 的一半
     * 可以转换为背包问题：子集背包问题
     * 子集背包问题维度： 可选择的物品 和 背包的容量
     * dp[i][j] 使用前i个物品，当前容量为j, 最多有多少种方法恰好装满背包
     * dp[0][j] = 0 没有物品
     * dp[i][0] = 1 不装任何物品
     * dp[N][target]
     *
     * @param nums
     * @param target
     * @return
     */
    public static int findTargetSumWayWithDp(int[] nums,int target){
        int sum = 0;
        for(int i = 0 ; i < nums.length ; i ++){
            sum += nums[i];
        }
        target =  (sum + target)/2;

        int n  = nums.length;

        int[][] dp = new int[n+ 1][target + 1];
        for(int i = 0 ; i <= n ; i ++) {
            dp[i][0] = 1;
        }

        for(int i = 1 ; i <= n ; i ++){
            for(int j = 1 ; j <= target ; j ++){
                if(j - nums[i -1] >= 0){
                    dp[i][j] = dp[i-1][j] + dp[i-1][j - nums[i-1]];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][target];
    }

    public static int findTargetSumWayWithDpCompress(int[] nums,int target){
        int sum = 0;
        for(int i = 0 ; i < nums.length ; i ++){
            sum += nums[i];
        }
        target =  (sum + target)/2;

        int n  = nums.length;

        int[] dp = new int[target + 1];
        dp[0] = 1;

        for(int i = 1 ; i <= n ; i ++){
            for(int j = target ; j >= 0 ; j--){  //需要用到 j - nums[i-1]，所以倒序
                if(j - nums[i -1] >= 0){
                    dp[j] = dp[j] + dp[j - nums[i-1]];
                } else {
                    dp[j] = dp[j];
                }
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        System.out.println(findTargetSumWay(new int[]{1,3,1,4,2},5));
        System.out.println(findTargetSumWayWithMemory(new int[]{1,3,1,4,2},5));
        System.out.println(findTargetSumWayWithDp(new int[]{1,3,1,4,2},5));
        System.out.println(findTargetSumWayWithDpCompress(new int[]{1,3,1,4,2},5));
    }

}
