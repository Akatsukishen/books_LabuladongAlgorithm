package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreSum {

    /**
     * 输入数组array,求数组中子数组和为k的情况
     * 子数组：连续的数
     * [1,1,1,2] k = 2
     * -> [1,1] [1,1] [2]
     *
     * 算出前i个数据和前j个数据的和，如果 sum(j) - sum(i) = k,那么 【i+1,j】就是符合要求的子数组
     * SubSum(i+1,j) = sum(j) - sum(i)
     *
     * preSum(i)表示 [0,i-1]的数据和，即i的前缀和。
     * 那么如果想求[i,k]的和 preSum[k+1] - preSum[i]即可
     *
     * @param array
     * @return
     */
    public static List<List<Integer>> getPreSubArray(int[] array,int k){

        int n = array.length;
        //preSum[i] [0,i-1]的数据和
        int[] preSum = new int[n + 1];
        preSum[0] = 0;
        for(int i = 0 ; i < n ; i ++){
            preSum[i + 1] = preSum[i] + array[i];
        }

        List<List<Integer>> res = new ArrayList<>();
        Map<Integer,List<Integer>> map = new HashMap<>();

        for(int i = 0 ; i <= n ; i ++){
            if(map.containsKey(preSum[i] - k)){
                List<Integer> preSumMatches = map.get(preSum[i] - k);
                for(int j = 0; j < preSumMatches.size() ; j ++){
                    int matchIndex = preSumMatches.get(j);
                    List<Integer> matchRes = new ArrayList<>();

                    for(int index = matchIndex; index < i ; index ++){
                        matchRes.add(array[index]);
                    }
                    res.add(matchRes);
                }
            }

            if(map.containsKey(preSum[i])){
                map.get(preSum[i]).add(i);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(preSum[i], list);
            }

        }

        return res;

    }

    /**
     * 计算和为k的子数组个数。
     * @param nums
     * @param k
     * @return
     */
    public static int subArraySum(int[] nums,int k){
        //记录前缀和为key值的个数
        HashMap<Integer,Integer> preSum = new HashMap<>();

        preSum.put(0,1);
        int ans = 0 ;
        int sum0_i = 0;
        for(int i = 0 ; i < nums.length ; i ++){
            sum0_i += nums[i];
            int sum0_j = sum0_i - k;
            if(preSum.containsKey(sum0_j)){
                ans += preSum.get(sum0_j);
            }
            preSum.put(
                    sum0_i,
                    preSum.getOrDefault(sum0_i,0) + 1
            );
        }
        return ans;
    }

    public static void main(String[] args) {
        List<List<Integer>> result = getPreSubArray(new int[] {2,1,1,1},2);
        for(List<Integer> res : result){
            System.out.println(res);
        }
        System.out.println(subArraySum(new int[]{1,1,1,2},3));
    }

}
