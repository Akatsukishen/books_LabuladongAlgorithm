package com.lss.algorithm.study;

/**
 * 背包问题
 */
public class Knapsack {

    /**
     * 0-1背包问题：
     *  给定一系列物品，每个物品有一定的重量，同时也有其价值
     *  给定一个可以承受最大重量为W的背包，求背包最终能背的物品的最大价值
     *
     * 例如：
     *  weights = [2,1,3]
     *  values =  [4,2,3]
     *  给定背包可承受重量W为3
     *  最大价值为6 ，选择前两个物品
     *
     *  0-1背包：加入的物品要么不放入(0),要么全部放入(1),不存在拆开放入的中间态。
     *
     *  使用溯源算法可以算出，但是时间复杂度较大。
     *
     *  使用动态规划：
     *      每个物品有两种选择：放入 or 不放入
     *  dp[i][j]表示 对于背包重量j,选择【前】i个商品,可以装下的最大价值。(套路）
     *
     * @return
     */
    public static int getMaxValue(int W,int[] weights, int[] values){
        int n = weights.length;
        int[][] dp = new int[n + 1][ W + 1];

        for(int i = 1; i <= n ; i ++){
            for (int j = 1 ; j <= W ; j ++){

                if(j - weights[i-1] < 0){
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = Math.max(
                            dp[i-1][j], //不放入i物品
                            dp[i-1][j-weights[i-1]] + values[i-1] //放入i物品
                    );
                }

            }
        }
        return dp[n][W];
    }

    /**
     * 背包问题：子集问题
     * @param nums
     * @return
     */
    public static boolean canPartition(int[] nums){
        int sum = 0;
        for(int i = 0 ; i < nums.length ; i ++){
            sum += nums[i];
        }
        if(sum % 2 != 0){
            return false;
        }
        sum = sum / 2;
        //dp[i][j] 是否使用前i个物品可以装满容量为j的背包
        boolean[][] dp = new boolean[nums.length + 1][sum + 1];
        for(int i = 0 ; i < nums.length + 1; i ++){
            dp[i][0] = true;
        }
        for(int i = 1 ; i <= nums.length ; i ++){
            for(int j = 1; j <= sum ; j ++){
                if(j - nums[j - 1] < 0) {
                    dp[i][j] = dp[i-1][j] ;
                } else {
                    //i不加入，看看前面的i-1个可不可以恰好凑齐
                    //i加入，看看前面的i-1是否可以恰好凑齐减去的数量
                    dp[i][j] = dp[i-1][j] || dp[i-1][j - nums[j - 1]];
                }
            }
        }
        return dp[nums.length][sum];

    }

    /**
     * 完全背包问题
     * 给定一系列硬币，每种硬币的数值coins，每种硬币的数量是【无限】的
     * 给定金额amount,求凑齐amount的方法总数
     *
     * dp[i][j] 表示只使用前i个硬币凑集金额j的方法总数
     *
     * 对于i，j,如果把第i个硬币放入，那么只需要使用前i个硬币凑齐j-coins[j-1]就行
     *      1. 第i个硬币可以使用多次，所以还可以再使用i
     *      2. 使用一次之后只需凑齐金额j-coins[j-1]
     *  如果不把i放进去那么，只需要前i-1个商品凑集金j即可。
     *
     * dp[i][j] = dp[i][j - coins[j-1]] + dp[i-1][j]
     *
     * 最基本的情况，dp[0][i] = 0，dp[i][0]=1
     *
     * 5, {1,2,5}
     * 1 + 1 + 1 + 1 + 1
     * 1 + 1 + 1 + 2
     * 1 + 2 + 2
     * 5
     *
     * @param amount
     * @param coins
     * @return
     */
    public static int change(int amount ,int[] coins){
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];

        for(int i = 0 ; i <= n ;i ++){
            dp[i][0] = 1;
        }

        for(int i = 1; i <=n ; i++){
            for(int j = 1 ; j <= amount ; j++){
                if(j - coins[i-1] < 0 ){
                    dp[i][j] =  dp[i-1][j];
                } else {
                    dp[i][j] = dp[i][j - coins[i-1]] + dp[i-1][j];
                }
            }
        }

        return dp[n][amount];
    }

    /**
     * 使用状态压缩计算凑齐硬币的种数
     * @param amount
     * @param coins
     * @return
     */
    public static int changeByCompress(int amount, int[] coins){
        int n = coins.length;
        int[] dp = new int[amount + 1];

        dp[0] = 1;

        for(int i = 1; i <=n ; i++){
            for(int j = 1 ; j <= amount ; j++){
                if(j - coins[i - 1] >= 0){
                    dp[j] = dp[j - coins[i-1]] + dp[j];
                }
            }
        }

        return dp[amount];
    }

    public static void main(String[] args) {
        System.out.println(getMaxValue(3,new int[]{2,1,3},new int[]{4,2,3}));
        System.out.println(canPartition(new int[]{1,3,2}));
        System.out.println(change(5,new int[]{1,2,5}));
        System.out.println(changeByCompress(5,new int[]{1,2,5}));
    }
}
