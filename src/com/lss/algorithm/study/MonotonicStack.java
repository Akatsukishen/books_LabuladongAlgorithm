package com.lss.algorithm.study;

import java.util.Stack;

/**
 * 单调栈
 */
public class MonotonicStack {

    /**
     * 下一个更大的元素,如果没有返回-1
     * [2,1,2,4,3] ->【4，,2，,4，-1，-1】
     * @param nums
     * @return
     */
    public static int[] nextGreatElement(int[] nums){
        int[] ans = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = ans.length - 1 ; i >= 0 ; i --){
            while (!stack.isEmpty() && nums[i] >= stack.peek()){
                stack.pop(); //个子太矮，使用被来的高个子挡住了，前面的人是看不到你的。
            }
            ans[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return ans;
    }

    /**
     * 环形数组：即最后一个元素的下一个是第一个元素
     * 下一个更大的元素
     * [2,1,2,4,3] -> [4,2,4,-1,4]
     * 可以将原数组后面再接一个原数组
     * [2,1,2,4,3,2,1,2,4,3] 那么按照前面的算法就可以得到结果
     * 直接将索引最大扩大两倍，使用取余的操作，并不需要真正地拼接数组，在原有数组的基础上也能实现
     * @param nums
     * @return
     */
    public static int[] nextGreatElementCircle(int[] nums){
        int n = nums.length;
        int[] ans = new int[n];
        Stack<Integer> stack = new Stack<>();
        for(int i = 2 * n -1 ; i>=0 ; i--){ //索引扩大两倍
            while (!stack.isEmpty() && nums[i%n] >= stack.peek()){
                stack.pop();
            }
            ans[i%n] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i%n]);
        }
        return ans;
    }

    /**
     * 给定一个温度的数组，求下一个更热的天是间隔几天后,如果没有更热的天，返回0
     * [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0]
     * @param temperatures
     * @return
     */
    public static int[] dailyTemperatures(int[] temperatures){
        int[] ans = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = temperatures.length - 1 ; i >= 0 ;i --){
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]){
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] monotonicStack = nextGreatElement(new int[]{2,1,2,4,3});
        int[] monotonicStack = nextGreatElementCircle(new int[]{2,1,2,4,3});
//        int[] monotonicStack = dailyTemperatures(new int[]{73,74,75,71,69,72,76,73});
        for(int i = 0 ; i < monotonicStack.length ; i ++){
            System.out.println(monotonicStack[i]);
        }
    }

}
