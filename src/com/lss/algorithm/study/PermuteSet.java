package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.List;

public class PermuteSet {

    /**
     * 排列问题
     * @param nums
     * @param k
     * @return
     */
    public static List<ArrayList<Integer>> permute(List<Integer> nums, int k){
        List<ArrayList<Integer>> result = new ArrayList<>();
        permute(nums,new ArrayList<>(),k,result);
        return result;
    }

    private static void permute(List<Integer> nums,List<Integer> picked,int k , List<ArrayList<Integer>> result) {
        if(picked.size() ==k){
            result.add(new ArrayList(picked));
            return;
        }
        for(int i = 0 ; i < nums.size() ; i ++){
            if(picked.contains(nums.get(i))) continue;
            picked.add(nums.get(i));
            permute(nums,picked,k, result);
            picked.remove(picked.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        List<ArrayList<Integer>> result  =permute(nums,3);
        for(ArrayList<Integer> res : result){
            System.out.println(res);
        }
    }

}
