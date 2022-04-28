package com.lss.algorithm.study;

/**
 * 最长回文子序列的长度
 * 回文字符串: 左右对称 "abcba"
 * 回文子序列："aecda" -> "aca"
 */
public class LongestPalindromeSubSeq {

    /**
     * 回文子序列
     *
     * 因为最长子序列可能在任意长度的子字符串中
     * 所以包含的子字符串的开始和结束位置的坐标都是 从 0 到 s1.length() - 1
     *
     * dp[i][j] 表示从i开始到j结束的最长回文子序列
     * 如果s1[i] == s1[j] 那他们两加上从 i + 1到j -1 的最长回文子序列，肯定是[i,j]的最长回文子序列
     * 如图s1[i] != s1[j] 那么他们不可能同时出现的最长回文子序列中，那么他两加入到[i + 1,j]和[i,j-1]的子序列中，看哪个更长即可
     *
     * @param s1
     * @return
     */
    public static int getLongestPalindromeSubSeq(String s1){
        int[][] dp =  new int[s1.length()][s1.length()];
        for(int row = 0 ; row < s1.length() ;row ++){
            dp[row][row] = 1;
        }

        for(int i = s1.length() - 2; i >= 0 ; i --){
            for(int j = i + 1 ; j < s1.length() ; j ++){
                if(s1.charAt(i) == s1.charAt(j)){
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(
                            dp[i + 1][j],
                            dp[i][j-1]
                    ); //位置i,j 只跟左下角三个位置相关
                }
            }
        }

        return dp[0][s1.length() -1];
    }

    /**
     * 由前面的getLongestPalindromeSubSeq()方法可知：
     * 二维数组dp[i][j] 只和下面一行和左边的数据有关
     * 由底而上遍历
     * 如果使用一个数组dp保留下一行的结果，那么从下往上，从左往右遍历的时候，应该可以在左边和下面一行的基础上求得当前值
     *
     * 需要注意的是：
     *    从左往右遍历，[i,j]可能会依靠[i-1,j-1]处的值，而求取[i,j-1]位置后，会在[j-1]的位置处更新值
     *    所以需要保留[i-1][j-1]位置的值以便下一轮使用
     *
     * 也得注意baseCase
     *
     * @param s1
     * @return
     */
    public static int getLongestPalindromeSuSeqWithCompress(String s1){
        int[] dp = new int[s1.length()];
        for(int i = 0 ; i < s1.length() ; i ++){
            dp[i] = 1;
        }

        for(int i = s1.length() - 2; i >= 0 ; i --){
            int pre = 0;
            for(int j = i + 1; j < s1.length() ; j ++){
                int temp = dp[j];
                if(s1.charAt(i) == s1.charAt(j)){
                    dp[j] = pre + 2;
                } else {
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
                pre = temp;
            }

        }
        return dp[s1.length() - 1];
    }

    public static void main(String[] args) {
        System.out.println(getLongestPalindromeSubSeq("aecda"));
        System.out.println(getLongestPalindromeSubSeq("abcd"));
        System.out.println(getLongestPalindromeSuSeqWithCompress("aecda"));
    }
}
