package com.lss.algorithm.study;

import java.util.*;

/**
 *
 */
public class NSum {

    /**
     * 求数组中两数据相加恰好等于nums的元素的下标
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSumIndex(int[] nums, int target){
        HashMap<Integer,Integer> numIndex = new HashMap<>();
        for(int i = 0 ; i < nums.length ; i ++){
            numIndex.put(nums[i],i);
        }
        for(int i = 0; i < nums.length ; i ++){
            int other = target - nums[i];
            if(numIndex.containsKey(other) && numIndex.get(other) != i){
                return new int[]{i,numIndex.get(other)};
            }
        }
        return new int[]{-1,-1};
    }

    public static class TwoSum {

        Map<Integer,Integer> freq = new HashMap<>();

        public void add(int num){
            freq.put(num,freq.getOrDefault(num,0) + 1);
        }

        public boolean find(int target){
            for(Integer key : freq.keySet()){
                int other = target - key;
                if(other == key && freq.get(key) > 1){
                    return true;
                }
                if(other != key && freq.containsKey(key)){
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * 输入数组，求所有2数之和为target的不重复集合
     * 【1,1,2,4,5,6】 target = 6
     *  -> 【 【1,5】,【2,4】】
     * @param nums
     * @param target
     * @return
     */
    private static List<List<Integer>> twoSum(int[] nums, int target){
        //从小到大排列
        Arrays.sort(nums);
        int lo = 0 ,hi = nums.length - 1;
        List<List<Integer>> result = new ArrayList<>();
        while (lo < hi){
            int sum = nums[lo] + nums[hi];
            int left = nums[lo],right = nums[hi];
            if(sum == target){
                ArrayList<Integer> res = new ArrayList<>();
                res.add(nums[lo]);
                res.add(nums[hi]);
                result.add(res);

                while (lo < hi && nums[lo] == left){ //跟前面的数据重复了
                    lo ++;
                }
                while (lo < hi && nums[hi] == right){
                    hi --;
                }
            } else if(sum < target){
                while (lo < hi  && nums[lo] == left){
                    lo ++;
                }
            } else if(sum > target){
                while (lo < hi && nums[hi] == right){
                    hi --;
                }
            }
        }
        return result;
    }

    /**
     * 已经排序好的数组两个数之和为target的所有结果
     * @param nums
     * @param start
     * @param target
     * @return
     */
    private static List<List<Integer>> twoSum(int[] nums,int start, int target){

        int lo = start;
        int hi = nums.length - 1;

        List<List<Integer>> result = new ArrayList<>();

        while (lo < hi){
            int left = nums[lo];
            int right = nums[hi];
            int sum = left + right;
            if(sum == target){
                List<Integer> res = new ArrayList<>();
                res.add(left);
                res.add(right);
                result.add(res);

                while (lo < hi && nums[lo] == left){
                    lo ++;
                }

                while (lo < hi && nums[hi] == right){
                    hi --;
                }
            } else if(sum < target){
                while (lo < hi && nums[lo] == left){
                    lo ++;
                }
            } else if(sum > target){
                while (lo < hi && nums[hi] == right){
                    hi --;
                }
            }
        }

        return result;
    }

    public static List<List<Integer>> threeSum(int[] nums, int target){

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0 ; i < nums.length ; i ++){
            //以nums[i]开头
            List<List<Integer>> res = twoSum(nums,i + 1, target - nums[i]);
            for(List<Integer> op : res){
                op.add(0,nums[i]);
                result.add(op);
            }
            //跳过跟nums[i]重复的开头
            while (i < nums.length -  1 && nums[i] == nums[i + 1]){
                i ++;
            }
        }
        return result;
    }

    private static List<List<Integer>> nSum(int[] nums,int n,int target){
        Arrays.sort(nums);
        return nSum(nums,n,0,target);
    }

    private static List<List<Integer>> nSum(int[] nums, int n , int start, int target){

        List<List<Integer>> result = new ArrayList<>();

        if(nums.length < n || start >= nums.length || n + start >= nums.length){
            return result;
        }

        if(n == 2){
            int lo = start ;
            int hi = nums.length - 1;


            while (lo < hi){
                int left = nums[lo];
                int right = nums[hi];
                int sum = left + right;
                if(sum == target) {
                    ArrayList<Integer> parts = new ArrayList<>();
                    parts.add(left);
                    parts.add(right);
                    result.add(parts);
                    while (lo < hi && nums[lo] == left){
                        lo ++;
                    }
                    while (lo < hi && nums[hi] == right){
                        hi --;
                    }
                } else if(sum < target){
                    while (lo < hi && nums[lo] == left){
                        lo ++;
                    }
                } else if(sum > target){
                    while (lo < hi && nums[hi] == right){
                        hi --;
                    }
                }
            }
        } else {
            for(int i = start ; i < nums.length ; i ++){
                List<List<Integer>> parts = nSum(nums,n-1, i  + 1,target - nums[i]);
                for(List<Integer> part : parts){
                    part.add(0,nums[i]);
                    result.add(part);
                }
                while(i < nums.length - 1 && nums[i] == nums[i + 1] ){
                    i ++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//       int[]  indexs = twoSumIndex(new int[]{1,2,4,7},6);

//       for(int i = 0 ; i < indexs.length ; i ++){
//           System.out.print(indexs[i] + "\t");
//       }

//       List<List<Integer>> result = twoSum(new int[]{1,1,5,2,4,6},6);
       List<List<Integer>> result = nSum(new int[]{1,1,5,2,4,6},4,16);
       for(List<Integer> res : result){
           System.out.println(res);
       }

    }

}
