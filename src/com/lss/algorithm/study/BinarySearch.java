package com.lss.algorithm.study;

/**
 * 二分搜索
 */
public class BinarySearch {

    /**
     * 有序数组中二分搜索找到数值为target的元素下标
     * 1. 因为right <= nums.length - 1,而left是以取到num.length -1,即left可以等于right
     * 2. 使用else if 把所有可能列出来，不使用else,这样逻辑清晰明了。
     * @param nums   有序数组
     * @param target 需要找到的值
     * @return       对应值的下标，不存在的情况,返回-1
     */
    public static int findByBinarySearch(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while (left <= right){
            int mid = left + (right - left ) / 2;
            if(nums[mid] == target){
                return mid;
            } else if(nums[mid] > target){
                right = mid - 1;
            } else if(nums[mid] < target){
                left = mid + 1;
            }
        }
        return  -1;
    }

    /**
     * 在可包含重复数据的有序数组中，查找数值为target的最左边下标
     *
     * @param nums   可包含重复数据的有序数组
     * @param target 目标数值
     * @return       target的最左边下标，如果没找到返回-1
     */
    public static int findLeftBoundByBinarySearch(int[] nums, int target) {
        int left = 0 ;
        int right = nums.length - 1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] == target){ //再向左边压缩一下，寻找一下可能
                right = mid - 1;
            } else if(nums[mid] < target){
                left = mid + 1;
            } else if(nums[mid] > target){
                right = mid - 1;
            }
        }
        //上面退出循环的条件 left= right + 1
        //如果存在target，其前一个条件 应该是 left =right && nums[right] = target，此时操作right = mid - 1 变成 ...right,left..
        //所以left值是前一个target的边界
        //left一直往右，可能超过了length
        if(left >= nums.length || nums[left] != target){
            return -1;
        }
        return left;
    }

    /**
     * 在可包含重复数据的有序数组中，查找数值为target的最右边下标
     * @param nums   可包含重复数据的有序数组
     * @param target 目标数值
     * @return       target的最左边下标，如果没找到返回-1
     */
    public static int findRightBoundByBinarySearch(int[] nums,int target){
        int left = 0 ;
        int right = nums.length - 1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] == target){
                left = mid + 1;
            } else if(nums[mid] > target){
                right = mid - 1;
            } else if(nums[mid] < target){
                left = mid + 1;
            }
        }
        //上面退出循环的条件left = right  + 1
        //如果存在target，那么前一个状态应该是，left == right,nums[mid] = target,而后left + 1
        //也就是说right是上一个可能的target的下标

        //判断是否越界，判断是否是上述的触发条件
        if(right < 0 || nums[right] != target){
            return  -1;
        }
        return right;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,7,8,9};
        System.out.println(findByBinarySearch(nums,1));
        System.out.println(findByBinarySearch(nums,7));
        System.out.println(findByBinarySearch(nums,9));
        System.out.println(findByBinarySearch(nums,2));

        nums = new int[] {1,2,3,3,3,5,7};
        System.out.println(findLeftBoundByBinarySearch(nums,3));
        System.out.println(findLeftBoundByBinarySearch(nums,4));
        System.out.println(findLeftBoundByBinarySearch(nums,0));
        System.out.println(findLeftBoundByBinarySearch(nums,9));

        System.out.println(findRightBoundByBinarySearch(nums,3));
        System.out.println(findRightBoundByBinarySearch(nums,2));
        System.out.println(findRightBoundByBinarySearch(nums,1));
        System.out.println(findRightBoundByBinarySearch(nums,9));
    }
}
