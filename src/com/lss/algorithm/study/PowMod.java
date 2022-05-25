package com.lss.algorithm.study;

import java.util.Arrays;

public class PowMod {

    public static int base = 1337;

    /**
     * 求a^b % base 的结果
     * b的输入可能比较大，以数组的形式给出
     * 136 ^ [1,2,3,4] % base -> 136^1234 % base
     * 数字太大有溢出的风险
     *
     *  1.
     *  a^[1,2,3,4] = a ^ 4 * a ^ [1,2,3,0]
     *              = a ^ 4 * (a ^ [1,2,3])^10
     *  -> 规模递减
     *
     *  2.
     *  (a * b) % base = ((a % base) * (b % base) ) % base
     *
     *  a = K * base + A
     *  b = N * base + B
     *  a * b = K * base * N * base + K * base * B + A * N * base + AB
     *  a * b % base = AB % base = (a % base)(b % base) % base
     *
     *  3.
     *  a^[1,2,3,4] % base = (a ^ 4 % base) * ( (a ^ [1,2,3])^10 % base ) % base
     *
     * @param a
     * @param b
     * @return
     */
    public static int superPowMode(int a, int[] b){
        if(b.length == 0){
            return 1;
        }
        int last = b[b.length - 1];
        int[] newArray = Arrays.copyOf(b,b.length - 1);

        int part1 = powMode(a,last);
        int part2 = powMode(superPowMode(a,newArray),10);

        return (part1 * part2) % base;
    }

    /**
     * a ^ k % base 结果
     * a ^ k % base = (a * a^k-1)%base = ( a^k-1 % base ) * (a % base) % base
     * @param a
     * @param k
     * @return
     */
    private static int powMode(int a, int k){
        int res = 1;
        for(int i = 1 ; i <= k ; i ++){
            res = a * res;
            res %= base;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(superPowMode(7,new int[]{1,2,3,4}));
//        System.out.println(powMode(2,10));
    }
}
