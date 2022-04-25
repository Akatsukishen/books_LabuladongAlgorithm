package com.lss.algorithm.study;

import java.util.Arrays;
import java.util.Comparator;

public class LongestIncreaseSequence {

    /**
     * 求给定数组中最长的递增子序列
     * 子序列 不是子字符串的意思，中间可以跳过
     * 如 [2,3,1,8]
     * 子序列可以是[2,3,8]
     * 只要顺序对就行
     * @param nums  输入数组
     * @return      最大递增子序列
     */
    public static int get(int[] nums){
        //记录以当前数结束的递增子序列的长度
        int[] dp = new int[nums.length];
        int max = 0;
        for(int i = 0 ; i < dp.length ; i ++){
            dp[i] = 1;
            for(int j = 0 ; j < i ; j ++){
                if(nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i],dp[j] + 1);
                }
            }
            if(dp[i] > max){
                max = dp[i];
            }
        }
        return max;
    }

    public static  int maxEnvelopes(int[][] envelopes){
        int n = envelopes.length;
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] == b[0] ? b[1] - a[1] : a[0] - b[0];
            }
        });
        int[] height = new int[n];
        for(int i = 0 ; i < n ; i ++){
            height[i] = envelopes[i][1];
        }
        return get(height);
    }

    public static int maxEnvelopes2(int[][] envelopes){
        int n = envelopes.length;
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        int[] dp = new int[n];
        Arrays.fill(dp,1);
        int max = 1;
        for(int i = 0 ; i < envelopes.length ; i ++){
            for(int j = 0 ; j < i ; j ++){
                if(envelopes[i][0] >  envelopes[j][0]  && envelopes[i][1] > envelopes[j][1]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if(max < dp[i]){
                max = dp[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{8,3,4,2,7,5,6};
        System.out.println(get(nums));

        int[][] envelopes = new int[7][2];
        envelopes[0][0] = 1 ;
        envelopes[0][1] = 8 ;

        envelopes[1][0] = 2;
        envelopes[1][1] = 3;

        envelopes[2][0] = 5;
        envelopes[2][1] = 4;

        envelopes[3][0] = 5;
        envelopes[3][1] = 2;

        envelopes[4][0] = 6;
        envelopes[4][1] = 7;

        envelopes[5][0] = 6;
        envelopes[5][1] = 5;

        envelopes[6][0] = 8;
        envelopes[6][1] = 6;

        System.out.println(maxEnvelopes(envelopes));
        System.out.println(maxEnvelopes2(envelopes));
    }
}
