package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    /**
     * 求给定数据的所有子集（空集和自身也是）
     * 【1,2,3】 -> [],[1],[2],[3],[1,2],[1,3],[2,3],[1,2,3]
     *  【3】 在前面 【1,2】子集的情况下 保持原有不变或者 逐个加入
     *  subset([1,2,3]) = subset([1,2] + ( subset([1,2]).forEach + 3)
     * @param nums
     * @return
     */
    public static ArrayList<ArrayList<Integer>> subsets(List<Integer> nums){
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for(int i = 0 ; i < nums.size() ; i ++){
            ArrayList<ArrayList<Integer>> newAddRes = new ArrayList<>();
            for(ArrayList<Integer> res : result){
                ArrayList<Integer> preRes = new ArrayList<>(res);
                preRes.add(nums.get(i));
                newAddRes.add(preRes);
            }
            result.addAll(newAddRes);
        }

        return result;
    }

    /**
     * 溯源： 每个节点都是一个操作结果，后面的数可以在前面数结果的基础上新增。
     * @param nums
     * @return
     */
    public static List<ArrayList<Integer>> subsetsByBacktrack(List<Integer> nums){
        ArrayList<Integer> visited = new ArrayList<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        backtrack(nums,0,visited,result);
        return result;
    }

    private static void backtrack(List<Integer> nums,int start,List<Integer> visited,List<ArrayList<Integer>> result){
        result.add(new ArrayList<>(visited));
        for(int i = start; i < nums.size() ; i ++){
            visited.add(nums.get(i));
            backtrack(nums,i+1,visited,result);
            visited.remove(visited.size() - 1);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        List<ArrayList<Integer>> result = subsetsByBacktrack(nums);
        for(ArrayList<Integer> res : result){
            System.out.println(res);
        }
    }
}
