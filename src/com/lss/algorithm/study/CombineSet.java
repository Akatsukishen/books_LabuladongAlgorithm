package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合问题
 */
public class CombineSet {

    /**
     * 输入nums, 求nums中k个数据的所有组合结果
     * nums[1,2,3,4]  k=2
     *
     * @param nums
     * @param k
     * @return
     */
    public static List<ArrayList<Integer>> combine(List<Integer> nums, int k){
        List<ArrayList<Integer>> result = new ArrayList<>();
        combine(nums,new ArrayList<>(),0,k,result);
        return result;
    }

    private static void combine(List<Integer> nums,List<Integer> picked,int start, int k,List<ArrayList<Integer>> result){
        if(picked.size() == k){
            result.add(new ArrayList<>(picked));
            return;
        }
        for(int i = start ; i < nums.size() ; i ++){
            picked.add(nums.get(i));
            combine(nums,picked,i + 1,k,result);
            picked.remove(picked.size() - 1);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        List<ArrayList<Integer>> result = combine(nums,2);
        for(ArrayList<Integer> res : result){
            System.out.println(res);
        }

    }

}
