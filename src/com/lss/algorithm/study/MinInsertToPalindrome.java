package com.lss.algorithm.study;

/**
 * 给定一个字符串，求让字符串变成回文字符串的最小插入次数
 */
public class MinInsertToPalindrome {

    /**
     * dp[i][j] 为 字符串从索引i到索引j,[i,j]区间字符串经过最少次插入后变成回文字符串
     * 如果s[i] == s[j] 那么只需[i+1,j-1]变成回文即可。
     * 如果s[i] != s[j],那么 可尝试在i前插入s[j]即 s[j]{i..j-1}s[j] 变成回文字符串
     *                     也可尝试在j后传入s[i]即 s[i]{i+1..j}s[i]变成回文字符串
     *                 至于那种选择效果最好，求最小值就知道了
     * @param s 输入字符串
     * @return  最少插入次数，让输入字符串变成回文字符串。
     */
    public static int minInsertions(String s){
        int[][] dp = new int[s.length()][s.length()];

        for(int i = s.length() - 2 ;i >= 0 ; i --){
            for(int j = i + 1; j < s.length() ; j ++){
                if(s.charAt(i) == s.charAt(j)){
                    dp[i][j] = dp[i+1][j-1];
                } else {
                    dp[i][j] = Math.min(
                            dp[i + 1][j],
                            dp[i][j-1]
                    ) + 1;
                }
            }
        }

        return dp[0][s.length() - 1];
    }

    public static void main(String[] args) {
        System.out.println(minInsertions("abcbf"));
    }
}
