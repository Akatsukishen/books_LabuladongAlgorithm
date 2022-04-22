package com.lss.algorithm.study;

/**
 * 双指针之左右指针
 */
public class LeftRightPointer {

    /**
     * 使用左右指针查找有序数组中的指定元素
     * @param nums   有序数组
     * @param target 需要查找的元素
     * @return       元素索引
     */
    public static int binarySearch(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if(target == nums[mid]){
                return target;
            }else if(nums[mid] < target){
                left = mid  + 1;
            } else {
                right = mid - 1;
            }
        }
        return  -1;
    }
}
