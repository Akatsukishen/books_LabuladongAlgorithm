package com.lss.algorithm.study;

public class StoreWater {

    /**
     * 有宽度为1的柱子，
     * 柱子与柱子之间可以蓄水
     * 求所有柱子之间的蓄水总量
     *
     * 对于每根柱子，其自身上面能够存多少水取决于其所在左右两边挡板中较低的那个
     * 那左右两边挡板怎么求？
     * 其实：左边挡板是指往左边看，左边比它高的，就是其左边挡板，如果没有，那么可以认为左边挡板就是自己了
     * 同理，右边挡板也是这个理解。
     * 因此： 一根柱子的蓄水量，等于 min(maxLeft,maxRight) - height
     *
     * @param height
     * @return
     */
    public static int store(int[] height) {
        int res = 0;
        for(int i = 0 ; i < height.length ; i ++){
            int lMax= height[i];
            for(int j = i - 1 ; j >= 0 ; j --){
                if(lMax < height[j]){
                    lMax = height[j];
                }
            }
            int rMax = height[i];
            for(int j = i + 1 ; j < height.length ; j ++){
                if(rMax < height[j]){
                    rMax = height[j];
                }
            }
            res += Math.min(lMax,rMax) - height[i];
        }
        return res;
    }

    /**
     * 预先求好每个位置左边右边最高的挡板
     * @param height
     * @return
     */
    public static int store2(int[] height){
        int res = 0;
        int[] lMax = new int[height.length];
        int[] rMax = new int[height.length];

        for(int i = 0 ; i < height.length ; i ++){
            if(i == 0){
                lMax[0] = height[0];
            } else {
                lMax[i] = Math.max(lMax[i-1],height[i]);
            }
        }

        for(int i = height.length - 1; i >=0 ; i --){
            if(i == height.length - 1){
                rMax[height.length - 1] = height[height.length - 1];
            } else  {
                rMax[i] = Math.max(rMax[i + 1],height[i]);
            }
        }

        for(int i= 0 ;i < height.length;i++){
            res += Math.min(lMax[i],rMax[i]) - height[i];
        }
        return res;
    }

    /**
     * 左右指针法
     * @param height
     * @return
     */
    public static int store3(int[] height){
        int left = 0;
        int right = height.length - 1;
        int lMax = height[0];
        int rMax = height[height.length - 1];

        int res = 0;
        while (left <= right){
            lMax = Math.max(lMax,height[left]);
            rMax = Math.max(rMax,height[right]);

            if(lMax < rMax){
                //左边最大值 < 右边当前最大值 必然 左边最大值 < left右边的真正的最大值
                //那么 对于left对应的柱子来说， Math.max(lMax,rRealMax) == Math.max(lMax,rMax)
                res += lMax - height[left];
                left ++;
            } else {
                res += rMax - height[right];
                right --;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height = new int[] {0,1,0,2,1,0,1,3,1,1,2,1};

        //7
        System.out.println(store3(height));

        height = new int[] {3,1,7,2,3,2,2,4,9};
        //24
        System.out.println(store3(height));
    }

}
