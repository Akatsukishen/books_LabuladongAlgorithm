package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Permute {

    /**
     *
     * @param nums 输入不重复的数字
     * @return     获得这些数字的全排列
     */
    private static List<List<Integer>> permute(int[] nums){
        LinkedList<List<Integer>> result = new LinkedList<>();
        backtrack(nums,new LinkedList<>(),result);
        return result;
    }

    private static void backtrack(int[] nums,List<Integer> picked,List<List<Integer>> result){
        if(picked.size() == nums.length){
            result.add(new LinkedList<>(picked));
            return;
        }
        for(int num : nums){
            Integer integer = num;
            if(picked.contains(num)) continue;
            picked.add(integer);
            backtrack(nums,picked,result);
            picked.remove(integer);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        System.out.println(permute(nums));
    }
}
