package com.lss.algorithm.study;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 高楼扔鸡蛋
 */
public class SupperEggDrop {

    /**
     * 高楼扔鸡蛋问题
     * 给定高楼层数n和指定的鸡蛋数k
     *
     * 鸡蛋在从楼层扔下后有两种结果 ： 碎或者不碎，不碎的鸡蛋可以继续扔
     * 找出【最坏情况】下怎么通过扔【最少次数】找到 鸡蛋不碎的最高楼层。
     *
     * 如果没有鸡蛋个数的限制：
     *  那么很容易想到二分搜索，从中间楼层n/2扔，如果没碎，从n/2 + 1 到 n的中间楼层再扔
     *  直至找到不碎的最高楼层。
     *
     * 但是如果手里只有一个鸡蛋，为了找出答案，那么就不可以随便尝试了：
     *   因为一旦试错了，就不能再扔了。比如跑到楼层n扔，万一鸡蛋碎了，傻眼了，不知道到底是下面哪个楼层了。
     * 只有一个鸡蛋的情况下，只能从一楼往上一层层扫楼，直到找到鸡蛋不碎的最高楼层。
     *   但如果鸡蛋一楼就碎了的话，就只要扔一次；如果鸡蛋到最高楼层还不碎的话，就要扔n次了
     *   所以在k=1的情况下，最坏的情况最少也得扔n次
     *
     * 需要求出的是鸡蛋有限的情景下，【最坏条件】下扔【最少多少次数】找到鸡蛋不碎的最高楼层。
     *
     * @param n  楼层最高n
     * @param k  鸡蛋个数k
     * @return   最坏情况下最少扔多少次找到鸡蛋不碎的最高楼层
     */
    public static int supperEggDrop(int n ,int k){
        if(k == 1){
            return n;
        }
        if(n == 0){
            return 0;
        }
        //做出选择
        //i表示这一次尝试从i层扔鸡蛋
        int res = Integer.MAX_VALUE;
        int bestChoice = -1;
        for(int i = 1 ; i <= n ; i ++){
            int broken = supperEggDrop(i-1,k-1) + 1;//碎了情况下,从1层到i-1层去找
            int nBroken = supperEggDrop(n - i,k) + 1;//没碎的情况下，从剩下的n-i层中去找
            int worst = Math.max(broken,nBroken);//最坏情况为上面两种可能中的最大值
            if(worst < res){
                bestChoice = i;
            }
            res = Math.min(res,worst);//最坏情况下的最少次数
        }
        System.out.println("n = " + n + ",k = " + k + ",bestChoice = " + bestChoice);
        return res;
    }

    public static int supperEggDropWithMemory(int n ,int k){
        return supperEggDropWithMemory(n,k,new HashMap());
    }

    private static int supperEggDropWithMemory(int n , int k, Map<String,Integer> memory){
        String key = n + "," + k;
        if(memory.containsKey(key)){
            return memory.get(key);
        }
        if(k == 1){
            return n;
        }
        if(n == 0){
            return 0;
        }
        //做出选择
        //i表示这一次尝试从i层扔鸡蛋
        int res = Integer.MAX_VALUE;
        for(int i = 1 ; i <= n ; i ++){
            int broken = supperEggDropWithMemory(i-1,k-1) + 1;//碎了情况下,从1层到i-1层去找
            int nBroken = supperEggDropWithMemory(n - i,k) + 1;//没碎的情况下，从剩下的n-i层中去找
            int worst = Math.max(broken,nBroken);//最坏情况为上面两种可能中的最大值
            res = Math.min(res,worst);//最坏情况下的最少次数
        }
        memory.put(key,res);
        return res;
    }

    /**
     *  K固定时，N递增的话，那么supperEggDrop(）肯定是递增的
     *  n,k固定后
     *  i递增的话
     *  supperEggDrop(i-1,k-1) 递增
     *  supperEggDrop(n - i,k) 递减
     *  \      /
     *    \   /
     *     \ /
     *     /\ （最坏最小）
     *    /  \
     *   /    \
     *  /      \
     *  先求两者最大，再递增求最小，
     *
     *
     * @param n
     * @param k
     * @return
     */
    public static int supperEggDropWithMemoryWithBinarySearch(int n , int k){
        return supperEggDropWithMemoryWithBinarySearch(n,k,new HashMap<>());
    }

    private static int supperEggDropWithMemoryWithBinarySearch(int n , int k, Map<String,Integer> memory){
        String key = n + "," + k;
        if(memory.containsKey(key)){
            return memory.get(key);
        }
        if(k == 1){
            return n;
        }
        if(n == 0){
            return 0;
        }
        int lo = 1,hi = n;
        int res = Integer.MAX_VALUE;
        while (lo <= hi){
            int mid = (lo + hi) / 2;
            int broken = supperEggDropWithMemoryWithBinarySearch(mid - 1,k - 1);
            int nBroken = supperEggDropWithMemoryWithBinarySearch(n - mid,k);
            if(broken > nBroken){
                hi = mid - 1;
                res = Math.min(res,broken + 1);
            } else {
                lo = mid + 1;
                res = Math.min(res,nBroken + 1);
            }
        }
        memory.put(key,res);
        return res;
    }

    /**
     * 可以在固定k个鸡蛋的情况下，使用增加次数来逼近可测试的最高楼层
     * dp[k,m] 表示在固定k个鸡蛋的情况下，使用m次最坏情况下可以测试的最高楼层数
     * 如果dp[k,m]==n，那么此时的m是最坏情况下的最少次数
     *
     * dp[k,m] = dp[k][m-1] + dp[k-1][m-1] + 1
     *
     * dp[k,m-1]表示鸡蛋数额不变，操作数减一，对应上面对应楼层扔鸡蛋不碎往上继续尝试的情况
     * dp[k-1,m-1] 表示鸡蛋减一，操作数减一，对应上面对应楼层扔鸡蛋碎往下继续尝试的情况
     *  可探测的上面楼层 + 可探测的下面楼层  + 1 = 可探测的最高楼层
     *
     * @param n  需要探测的楼层高度
     * @param k  鸡蛋
     * @return   最坏情况下的最少扔鸡蛋的次数
     */
    public static int supperEggToN(int n,int k){
        //最多n次（线性扫描）
        int[][] dp = new int[k + 1][ n + 1];
        //base case的情况下 k =0 ,m =0 显然0
        int m = 0;
        while (dp[k][m] < n) {
            m ++;
            for(int j = 1 ; j <= k ; j ++){
                dp[j][m] = dp[j][m-1] + dp[j-1][m-1] + 1;
            }
        }
        return m;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //8 0.009s
//        System.out.println(supperEggDropWithMemoryWithBinarySearch(30,2));
        //8 66.45s
//        System.out.println(supperEggDropWithMemory(30,2));
        // 0.0
        System.out.println(supperEggToN(30,2));
        long end = System.currentTimeMillis();
        System.out.println((end - start)/(1000f));
    }
}
