package com.lss.algorithm.study;

public class EatingBanana {

    /**
     * 给定一堆香蕉，每一堆的香蕉数量piles数组表示
     * 猴子Koko每个小时可以吃一定的香蕉，但是如果一个小时内，吃完了一堆香蕉，不会自动去吃下一堆里面的香蕉，要等下一小时，才会继续吃下一堆
     * 给定时间H小时，求如果要在H小时内将香蕉吃完，Koko每小时吃香蕉的速度
     *
     * 最小速度 1 ，最快是香蕉堆的最大堆，因为最快每小时吃一堆，吃完了，没事也不能吃下一堆
     * 二分搜索尝试找到最小的速度
     *
     * @param piles
     * @param H
     * @return
     */
    public static int minEatSpeed(int[] piles,int H){

        int left = 1;
        int right = max(piles) + 1;
        while (left < right){
            int mid = left + (right - left) / 2;
            if(canFinished(piles,mid,H)){
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private static int max(int[] miles){
        int max = -1;
        for(int i = 0 ; i < miles.length ; i ++){
            if(miles[i] > max){
                max = miles[i];
            }
        }
        return max;
    }

    private static boolean canFinished(int[] piles , int speed , int H){
        int times = 0;
        for(int i = 0 ; i < piles.length ; i ++){
            times += times(piles[i],speed);
        }
        return times <= H;
    }

    private static int times(int amount, int speed){
        if(amount % speed == 0) {
            return amount / speed;
        } else {
            return  amount / speed + 1;
        }
    }

    public static void main(String[] args) {

        int[] piles = new int[]{10,5,4,8};
        System.out.println(minEatSpeed(piles,6));
    }
}
