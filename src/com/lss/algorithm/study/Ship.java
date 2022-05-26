package com.lss.algorithm.study;

public class Ship {

    /**
     * 给定一排货物，每个货物的重量weights
     * 货物要依次运输，每件货物必须整件运输，不能拆开
     *
     * 求在轮船每天一趟的情况下，在D天内运输完全部货物的最小运输能力
     * @param weights
     * @param D
     * @return
     */
    public static int minShipWithinDays(int[] weights, int D){
        int left = max(weights);
        int right = sum(weights) + 1;

        while (left < right){
            int mid = left + (right - left) / 2;
            if(canFinish(weights,mid,D)){
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private static boolean canFinish(int[]weight,int ship,int D){
        return times(weight,ship) <= D;
    }

    private static int times(int[] weight , int ship){
        int time = 1;
        int remain = ship;
        for(int i = 0 ; i < weight.length; i ++){
            if(remain >= weight[i]){
                //当前这一趟
                remain -= weight[i];
            } else {
                //另开一趟
                time ++;
                remain = ship - weight[i];
            }
        }
        return time;
    }

    private static int sum(int[] weights){
        int sum =0;
        for(int i = 0 ; i < weights.length ; i ++){
            sum += weights[i];
        }
        return sum;
    }

    private static int max(int[] weights){
        int max = 0;
        for(int i = 0 ; i < weights.length ; i ++){
            if(weights[i] > max){
                max = weights[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(times(new int[]{1,3,2,5,8,3},10));
        System.out.println(minShipWithinDays(new int[]{1,3,2,5,8,3},3));
        System.out.println(minShipWithinDays(new int[]{1,3,2,5,8,3},4));
    }

}
