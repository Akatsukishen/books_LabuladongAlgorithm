package com.lss.algorithm.study;

import java.util.HashMap;

public class Rob {

    /**
     * 街道上有一排房屋，每排房屋里面有金钱，
     * 可以从每个房间里取出金钱，但是从房间i中取出金钱后，不能从相邻的房屋里再取金钱
     * 求从这排房屋中最多能够取到多少钱
     * @param nums 房屋中的金钱数
     * @return     求从这排房屋中最多能够取到多少钱
     */
    public static int rob(int[] nums){
        int n = nums.length;
        //dp[i][j] 区间[i,j]中获得的最大金额
        int[][] dp = new int[n][n];

        for(int i = 0; i < n ; i++){
            dp[i][i] = nums[i];//斜对角
        }
        for(int i = 0 ; i < n ; i ++){
            for(int j= i + 1; j < n ;j++){
                if(j-2 >= 0){
                    dp[i][j] = Math.max(dp[i][j-2] + nums[j],dp[i][j-1]);
                } else {
                    dp[i][j] = Math.max(nums[j],dp[i][j-1]);
                }
            }
        }

        return dp[0][n -1];
    }

    /**
     *  面对一间房屋，可取可不取（选择）
     *  向后遍历，房屋取完之后，再遍历到下一间房屋（状态发生转移）
     *
     * @param nums
     * @return
     */
    public static int rob2(int[] nums){
        return rob2(nums,0);
    }

    private static int rob2(int[] nums, int start){
        if(start >= nums.length){
            return 0;
        }
        return Math.max(
                nums[start] + rob2(nums,start + 2), //从start中取
                rob2(nums,start + 1)   //不从start中取
        );
    }

    public static int rob2WithMemory(int [] nums){
        return rob2WithMemory(nums,0, new HashMap<Integer,Integer>());
    }

    private static int rob2WithMemory(int[] nums,int start, HashMap<Integer,Integer> memory){
        if(start >= nums.length){
            return 0;
        }
        if(memory.containsKey(start)){
            return memory.get(start);
        }
        int res = Math.max(
                rob2WithMemory(nums,start + 1,memory),
                rob2WithMemory(nums,start +2 ,memory) + nums[start]
        );
        memory.put(start,res);
        return res;
    }

    public static int rob3Backward(int[] nums){
        int n = nums.length;
        int[] dp = new int[n + 2];
        for(int i = n - 1; i >= 0 ; i--){
            dp[i] = Math.max(
                        nums[i] + dp[i + 2],//有个i + 2 ,后面放两个，避免逻辑复杂
                        dp[i + 1]
                    );
        }
        return dp[0];
    }

    public static int rob3BackwardCompress(int[] nums){
        int n = nums.length;
        int cur = 0;
        int cur2 = 0;
        int cur1 =0;
        for(int i = n -1 ; i >= 0; i --){
            cur = Math.max(
                    nums[i] + cur2,
                    cur1
            );
            cur2 = cur1;
            cur1 = cur;
        }
        return cur ;
    }

    /**
     * 房间不是并排的，而是环形的:
     *  1. 不取第一个也不取最后一个
     *  2. 取第一个不取最后一个
     *  3. 不取第一个，取最后一个 （2，3区间包括了1的区间）
     * @param nums
     * @return
     */
    public static int rob4Circle(int[] nums){
        if(nums.length == 1){
            return nums[0];
        }
        return Math.max(
                rob4(nums,0,nums.length - 2),
                rob4(nums,1,nums.length - 1)
        );
    }

    private static int rob4(int[] nums, int start, int end){
        int cur = 0;
        int cur1 = 0;
        int cur2 = 0;
        for(int i = end ; i >= start; i --){
            cur = Math.max(
                    nums[i] + cur2,
                    cur1
            );
            cur2 = cur1;
            cur1 = cur;
        }
        return cur;
    }

    /**
     * 树形抢劫：树的父子节点不能同时被抢
     * @param root
     * @return
     */
    public static int rob5Tree(BNode<Integer> root){
        return rob5Tree(root,new HashMap<>());
    }

    private static int rob5Tree(BNode<Integer> root,HashMap<BNode<Integer>,Integer> memory){
        if(root == null){
            return 0;
        }
        if(memory.containsKey(root)){
            return memory.get(root);
        }
        int nextLevel = rob5Tree(root.left) + rob5Tree(root.right);
        int temp = root.value;
        if(root.left != null){
            temp += rob5Tree(root.left.left);
            temp += rob5Tree(root.left.right);
        }
        if(root.right != null){
            temp += rob5Tree(root.right.left);
            temp += rob5Tree(root.right.right);
        }
        int res = Math.max(nextLevel,temp);
        memory.put(root,res);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{2,1,7,9,3,1}));
        System.out.println(rob2(new int[]{2,1,7,9,3,1}));
        System.out.println(rob2WithMemory(new int[]{2,1,7,9,3,1}));
        System.out.println(rob3Backward(new int[]{2,1,7,9,3,1}));
        System.out.println(rob3BackwardCompress(new int[]{2,1,7,9,3,1}));
        System.out.println(rob4Circle(new int[]{2,3,2}));

        System.out.println(rob5Tree(BNode.testTree()));
    }

}
