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
                return mid;
            }else if(nums[mid] < target){
                left = mid  + 1;
            } else {
                right = mid - 1;
            }
        }
        return  -1;
    }

    public static int[] twoSum(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while (left < right){
            int sum = nums[left] + nums[right];
            if(sum == target){
                return new int[]{left,right};
            } else if(sum < target){
                left ++;  //让sum大一点
            } else {
                right --; //让sum小一点
            }
        }
        return new int[]{-1,-1};
    }

    public static void reverse(int[] nums){
        int left = 0;
        int right = nums.length - 1;
        while (left < right){
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left ++ ;
            right --;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1,3,9,12,17,30};

        System.out.println(binarySearch(nums,17));
        System.out.println(binarySearch(nums,8));

        int[] result = twoSum(nums,26);
        for (int num : result){
            System.out.println(num);
        }

        reverse(nums);
        for (int num : nums){
            System.out.println(num);
        }
    }
}
