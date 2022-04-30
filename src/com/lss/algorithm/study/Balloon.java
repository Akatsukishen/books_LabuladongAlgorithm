package com.lss.algorithm.study;

public class Balloon {

    /**
     * 输入气球，每个气球代表一定的分数，每次点破气球，那么获得 当前气球分数 * 左边气球分数 * 右边气球分数
     * 如果左边或者右边没有气球，那么用分数1来表示
     *
     * 求戳破所有气球可以获得的最多分数
     * @param scores 气球分数数组
     * @return       戳破所有气球可以获得的最多分数
     */
    public static int maxScore(int[] scores){
        return maxScore(scores,0,scores.length);
    }

    private static int maxScore(int[] scores ,int res,int remainNum){
        if(remainNum == 0){
            return res;
        }

        int max = 0;
        //回溯法
        for(int  i = 0 ; i < scores.length ; i ++){

            if(scores[i] >= 0){

                int score = scores[i];

                int prev = 1;
                int prevIndex = i - 1;
                while (prevIndex >= 0 && scores[prevIndex] < 0){
                    prevIndex --;
                }
                if(prevIndex >= 0){
                    prev = scores[prevIndex];
                }

                int next = 1;
                int nextIndex = i + 1;
                while (nextIndex < scores.length && scores[nextIndex] < 0){
                    nextIndex ++;
                }
                if(nextIndex < scores.length){
                    next = scores[nextIndex];
                }

                scores[i] = -1 ;
                max = Math.max(maxScore(scores, res + score * prev * next, remainNum - 1),max);
                scores[i] = score;
            }
        }

        return max;
    }

    /**
     * 戳破中间的气球会影响其他气球的左右气球的分数
     * 这样的话，每步操作之间不是独立的。
     * 动态规划处理的问题，子问题之间必须是相互独立的。
     * 因此必须巧妙定义dp数组，避免子问题产生关联性。
     * scores[0]、scores[n]均可理解分数为1.
     *
     * dp[i][j]表示戳破【开区间（i,j)之间】的气球所获得的最大分数。
     * 区间相互独立，没有关联。
     * 那么原问题可以转换为求dp[0][n+1]
     *
     * 怎么戳破呢？如果在区间中遍历，然后再逐步操作便会进入溯源算法，导致算法复杂度高。
     *
     * 转换思路： 用k表示（i,j）中最后戳破的气球索引
     * dp[i][k]表示的是戳破（i，k）中所有气球的最高分
     * dp[k][j]表示的是戳破（k+1，j）中所有气球的最高分
     * 那么：
     *  dp[i][j] = dp[i][k] + dp[k][j] + points(i) * points(k) * points(j)
     * 至于哪个k最大,遍历一下即可。
     *
     * @param scores
     * @return
     */
    private static int maxScoresByDp(int[] scores){
        int n = scores.length;

        //左右插入两个虚拟气球
        int[][] dp = new int[n + 2] [ n + 2];
        int[] points = new int[n + 2];
        for(int i = 1 ; i <= n ; i ++){
            points[i] = scores[i - 1];
        }
        points[0] = 1;
        points[n + 1] = 1;

        // 0 <= i <= n + 1, j >= i + 1
        //base case 都为0

        //倒数第二行开始，右上半区域从下往上，从左往右依次遍历。
        for(int i = n; i >= 0 ; i --){
            for(int j = i + 1; j < n + 2 ; j ++){
                for(int k = i + 1; k < j ; k ++){
                    dp[i][j] = Math.max(
                            dp[i][j],
                            dp[i][k] + dp[k][j] + points[i] * points[k] * points[j]
                    );
                }
            }
        }

        return dp[0][n+1];
    }

    public static void main(String[] args) {
        int[] num = new int[]{2,5,3,4};
        System.out.println(maxScore(num));
        System.out.println(maxScoresByDp(num));
    }
}
