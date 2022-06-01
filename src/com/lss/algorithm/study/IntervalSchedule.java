package com.lss.algorithm.study;

import java.util.Arrays;
import java.util.Comparator;

public class IntervalSchedule {

    /**
     * 给定活动的时间安排，二维数组的元素为每个活动有开始时间和结束时间，
     * 要求在时间不冲突的情况下尽可能多的参加活动
     * [s1,e1] [s2,e2] s2 >= e1的情况下视为不冲突
     *
     * 做法：
     * 1. 将所有的活动时间按照结束时间的，从小到大排序
     * 2. 依次选出活动时间结束最早的不冲突的活动。
     *
     * 活动结束时间最早的活动必定是一个结果中的一员
     * 假设i1是活动1，且结束时间最早，Sa是最多活动的一个集合 ia是Sa中结束最早的活动
     * Sa = {ia,ib,ic,...im} = {ib,ic,...,im} U {ia}
     * {ib,ic,...,im} 都不与活动ia时间冲突，那么肯定也不与i1冲突
     * 所以 {i1,ib,ic,...,im}的集合肯定也是满足要求的结果集之一
     * 同理选中i1之后，剩下的不冲突的活动里结束时间最早的活动必定可以成为满足要求的活动
     * 这样，依次挑选结束时间最早的不冲突的活动可以获得一个符合要求的结果集
     * @param intvs 活动时间安排
     * @return      最多多少活动不重叠
     */
    public static int intervalSchedule(int[][] intvs){
        Arrays.sort(intvs, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[1] - b[1];
            }
        });
        int count = 1;
        int end = intvs[0][1];
        for(int[] interval : intvs){
            if(interval[0] >=  end){
                end = interval[1];
                count ++;
            }
        }
        return count;
    }



    public static void main(String[] args) {
        int[][] interval = new int[3][2];
        interval[0] = new int[]{1,3};
        interval[1] = new int[]{2,4};
        interval[2] = new int[]{3,6};
        System.out.println(intervalSchedule(interval));
    }
}
