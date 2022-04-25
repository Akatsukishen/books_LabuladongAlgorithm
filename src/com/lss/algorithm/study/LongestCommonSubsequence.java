package com.lss.algorithm.study;

/**
 * 最长公共子序列问题
 */
public class LongestCommonSubsequence {

    /**
     * "abcde"  "aceb"  -> "ace"
     *
     * @param s1  给定字符串1
     * @param s2  给定字符串2
     * @return    求字符串s1,s2的最长公共子序列
     */
    public static int getLongestCommonSubsequence(String s1, String s2){
        int m = s1.length();
        int n = s2.length();
        //dp[i][j]是s1[0,i-1]和s2[0,j-1]的最长公共子序列
        int[][] dp = new int[m + 1][n + 1];

        for(int row = 0 ; row <= m; row ++){
            dp[row][0] = 0;
        }
        for(int col = 0 ; col <= n; col ++ ){
            dp[0][col] = 0;
        }

        for(int row = 1; row <= m ; row ++){
            for(int col = 1 ; col <= n  ; col ++){

                if(s1.charAt(row - 1) == s2.charAt(col -1)){
                    dp[row][col] = dp[row-1][col -1] + 1;
                } else {
                    dp[row][col] = Math.max(dp[row-1][col],dp[row][col-1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        String s1 = "abccde";
        String s2 = "aceb";
        System.out.println(getLongestCommonSubsequence(s1,s2));
    }
}
