package com.lss.algorithm.study;

import java.util.Arrays;

/**
 * 斐波那契数列
 */
public class Fib {

    /**
     *              fib(10)
     *             /       \
     *           fib(9)    fib(8)
     *           /  \
     *       fib(8)fib(7)
     *       ...
     *       /    \
     *      fib(1)fib(2)
     *
     * @param n
     * @return 自顶而下,暴力递归,存在重复计算
     */
    public static int fib1(int n) {
        if(n == 0 ) return 0;
        if(n == 1 || n == 2) return 1;
        return fib1(n-1) + fib1(n-2);
    }

    /**
     * 带有备忘录的自顶而下的递归调用
     * @param n
     * @return
     */
    public static int fib2(int n){
        if(n == 0) return 0;
        int[] memory = new int[n + 1];
        Arrays.fill(memory, 0);
        return fib2Memory(n,memory);

    }

    private static int fib2Memory(int n , int[] memory){
        if(n == 1 || n == 2){
            return 1;
        }
        if(memory[n] != 0){
            return memory[n];
        }
        memory[n] = fib2Memory(n -1 ,memory ) + fib2Memory(n - 2,memory);
        return memory[n];
    }

    /**
     *
     * @param n
     * @return 自低而上、拥有状态转移方程。
     */
    private static int fib3(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1 || n == 2){
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        for(int i = 3 ; i < dp.length ; i ++){
            dp[i] = dp[i-1] + dp[i -2];
        }
        return dp[n];
    }

    /**
     *
     * @param n
     * @return 自低而上，状态转移，并且将状态变为2个，状态压缩~
     */
    public static int fib4(int n){
        if(n == 0){
            return  0;
        }
        if(n == 1 || n == 2){
            return 1;
        }
        int prevPrev = 1;
        int prv = 1;
        int cur = 0;
        for(int i = 3 ; i < n + 1; i ++){
            cur = prv + prevPrev;
            prevPrev = prv;
            prv = cur;
        }
        return cur;
    }

    public static void main(String[] args) {
        System.out.println(fib1(10));
        System.out.println(fib2(10));
        System.out.println(fib3(10));
        System.out.println(fib4(10));
    }

}
