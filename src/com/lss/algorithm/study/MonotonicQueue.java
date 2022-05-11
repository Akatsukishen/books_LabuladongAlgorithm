package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 单调队列
 * 每次从后面压入一个数，如果压入的数，比前面的数大，那么将前面的数出队列，
 * 这样，每次压入过后，队列中的数据都是从大到小排列的
 *
 * 【5,3,2】
 * -> 压入4之后
 * 【5,4】
 */
public class MonotonicQueue {

    private LinkedList<Integer> q = new LinkedList<>();

    public void push(int n){
        while (!q.isEmpty() && n > q.getLast()){
            q.pollLast(); //双向队列，从后面移除一个
        }
        q.addLast(n); //双向队列，从后面增加一个
    }

    public int max() {
        return q.getFirst();
    }

    /**
     * 如果n是双向队列的头部，将其移除。
     * @param n
     */
    public void pop(int n){
        if(n == q.getFirst()){
            q.pollFirst();
        }
    }

    /**
     *
     * @param nums  给定数组
     * @param k     滑窗大小
     * @return      窗口从走往右滑动的过程中，窗口的最大值
     */
    public int[] maxSlidingWindow(int[] nums,int k){
        List<Integer> res = new ArrayList<>();
        for(int i = 0 ; i < nums.length; i ++){
            if(i < k - 1){
                push(nums[i]);
            } else {
                push(nums[i]);
                res.add(max());
                pop(nums[i - k +1]);
            }
        }
        int[] arr = new int[res.size()];
        for(int i = 0 ; i < res.size() ;i ++){
            arr[i] = res.get(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        MonotonicQueue queue = new MonotonicQueue();
        int[] ans = queue.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3);
        for(int i = 0 ;i < ans.length ; i ++){
            System.out.print(ans[i] + " ");
        }
    }


}
