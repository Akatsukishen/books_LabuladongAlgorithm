package com.lss.algorithm.study;

public class FourKeyProblem {

    /**
     * 键盘上只有四种按键：
     * A  -> 每次输入一个A
     * Ctrl + A -> 全选
     * Ctrl +C  -> 复制
     * Ctrl + V  -> 黏贴
     * 3次 最多3个A
     * 7次 最多9个A  A A A Ctrl +A Ctrl +C Ctrl +V Ctrl +V
     * @param n  最多操作输入次数
     * @return   输入完成后A的最多个数
     */
    public static int maxA(int n){
        return maxA(n,0,0);
    }

    /**
     *
     * @param n         剩余的输入次数
     * @param aNum      当前A的个数
     * @param copyNum   当前复制的个数
     * @return
     */
    private static int maxA(int n, int aNum, int copyNum){
        if (n <= 0){
            return aNum;
        }
        int inputA = maxA(n - 1 , aNum + 1,copyNum);
        int ctrV = maxA(n -1 , aNum + copyNum,copyNum);
        // 全选复制才有可能出现最大：全选全选（浪费2次操作） 全选复制 复制全选和复制复制（copyNum没变，浪费两次）
        int ctrActrC = maxA(n - 2, aNum,aNum);
        return Math.max(inputA,Math.max(ctrV,ctrActrC));
    }

    /**
     * dp[i] 记录输入i次后A最多的个数
     *
     * 最后一次有输入肯定是最多的
     * 最后一次输入：
     *      1. 直接输入A  dp[i-1 ] + 1
     *      2. Ctrl + V  如果i次是黏贴，那么至少i-2的时候需要全选，然后黏贴
     *                   对于i来说，【最后一次全选】的时机可以是[2...i-2],针对每一次全选时机，可以比较最终产生的最多个数作为最优解
     *                   注意： CA CC CA CC 这种最后一次全选时机是后面那个
     *
     * @param  n
     * @return
     */
    public static int maxA2(int n){
        int[] dp = new int[n + 1];

        for(int i = 1 ; i <= n ; i ++){
            dp[i] = dp[i - 1] + 1; //直接输入A
            for(int j = 2; j < i ; j ++){ //dp[j-2]表示全选的个数,i - j赋值次数，+ 1为加上原有个数
                dp[i] = Math.max(dp[i],dp[j - 2]*(i - j + 1));
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(maxA(3));
        System.out.println(maxA(7));

        System.out.println(maxA2(3));
        System.out.println(maxA2(7));
    }

}
