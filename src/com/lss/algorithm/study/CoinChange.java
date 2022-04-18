package com.lss.algorithm.study;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 凑钱币的问题
 *
 */
public class CoinChange {

    /**
     *
     * @param coins   钱币的面值，数量不限，
     * @param amount  需要凑齐的金额
     * @return        使用最少的钱币凑出指定金额，如果无法凑齐，返回-1
     */
    private static int coinChange(int[] coins,int amount){
        if(amount == 0) return 0;
        if(amount < 0) {
            return -1;
        }
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subProblem = coinChange(coins, amount - coin);
            if (subProblem >= 0 && subProblem < res) {
                res = 1 + subProblem;
            }
        }
        if(res == Integer.MAX_VALUE){
            res = -1;
        }
        return res;
    }

    /**
     * 使用备忘录，避免重叠子问题
     * @param coins   钱币的面值，数量不限，
     * @param amount  需要凑齐的金额
     * @return        使用最少的钱币凑出指定金额，如果无法凑齐，返回-1
     */
    private static int coinChangeWithMemory(int[] coins, int amount){
        HashMap<Integer,Integer> memory = new HashMap<>();
        return coinChangeWithMemory(coins,amount,memory);
    }

    private static int coinChangeWithMemory(int[] coins, int amount, HashMap<Integer,Integer> memory){
        if(memory.containsKey(amount)){
            return memory.get(amount);
        }
        if(amount == 0){
            return 0;
        }
        if(amount < 0){
            return -1;
        }
        int result = Integer.MAX_VALUE;
        for(int coin : coins){
            int subProblem = coinChangeWithMemory(coins,amount - coin,memory);
            if(subProblem >= 0 && subProblem < result){
                result = 1 + subProblem;
            }
        }
        if(result == Integer.MAX_VALUE){
            result = -1;
        }
        memory.put(amount,result);
        return result;
    }

    /**
     * 自底向上
     * @param coins   钱币的面值，数量不限，
     * @param amount  需要凑齐的金额
     * @return        使用最少的钱币凑出指定金额，如果无法凑齐，返回-1
     */
    private static int coinChangeFromBottom(int[] coins, int amount){
        int[] dp = new int[amount + 1];
        Arrays.fill(dp,amount + 1);
        dp[0] = 0;
        for(int i = 0 ; i < dp.length ; i ++){
            for(int coin : coins){
                if(i - coin < 0) continue;
                dp[i] = Math.min(dp[i] ,1 + dp[i - coin]);
            }
        }
        if(dp[amount] == amount + 1){
            return -1;
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[3];
        coins[0] = 1;
        coins[1] = 2;
        coins[2] = 5;
        System.out.println(coinChange(coins,28));
        System.out.println(coinChangeWithMemory(coins,28));
        System.out.println(coinChangeFromBottom(coins,28));
    }
}
