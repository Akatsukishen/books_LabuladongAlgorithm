package com.lss.algorithm.study;

public class MinEditDistance {

    /**
     * 最小编辑距离
     * 字符串s1只能通过每次'删除'、'替换'、'插入'一个字符的方式编辑
     * 求s1变成s2所需的最少步数
     * @param s1
     * @param s2
     * @return
     */
    public static int minDistance(String s1, String s2){
        int[][] memory = new int[s1.length()][s2.length()];
        for(int row = 0;row < s1.length(); row ++){
            for(int col = 0 ; col < s2.length() ; col ++){
                memory[row][col] = -1;
            }
        }

        return minDistance(s1,s2,s1.length()  - 1,s2.length() - 1,memory);
    }

    private static int minDistance(String s1, String s2, int end1,int end2, int[][] memory){
        if(end1 < 0){
            return end2 + 1;
        }
        if(end2 < 0){
            return end1  + 1;
        }
        if(memory[end1][end2] > 0){
            return memory[end1][end2];
        }
        if(s1.charAt(end1) == s2.charAt(end2)){
            memory[end1][end2] = minDistance(s1,s2,end1 - 1, end2 -1 ,memory);
        } else {
            memory[end1][end2] = Math.min(
                    minDistance(s1,s2,end1,end2 -1,memory), //插入
                    Math.min(
                            minDistance(s1,s2,end1 -1,end2,memory), //删除
                            minDistance(s1,s2,end1 - 1, end2 - 1, memory) //替换
                    )
            ) + 1;
        }
        return memory[end1][end2];
    }

    public static int minDistanceWithDpTable(String s1, String s2){
        int m = s1.length();
        int n = s2.length();
        //0个字符串时，通过增加字符，也能变成s1个字符串
        //dp[i][j] 表示 s1中[0,i-1]字符到s2[0,j-1]个的最短编辑距离
        int[][] dp = new int[m + 1][ n + 1];

        //base case
        for(int row = 0 ; row <= m ; row ++){
            dp[row][0] = row;
        }
        for(int col = 0 ; col <= n ; col ++ ){
            dp[0][col] = col;
        }

        for(int row = 1 ;row <= m; row ++ ){
            for (int col = 1 ; col <= n ; col ++){
                if(s1.charAt(row - 1) == s2.charAt(col - 1)){
                    dp[row][col] = dp[row - 1][col - 1];
                } else {
                    dp[row][col] = Math.min(
                            dp[row - 1][col - 1] ,    // 替换
                            Math.min(
                                    dp[row - 1][col] , //删除
                                    dp[row][col - 1]  // 插入
                            )
                    ) + 1;
                }
            }
        }

        return dp[m][n];
    }

    public static int minDistanceWithMethod(String s1, String s2){
        int m = s1.length();
        int n = s2.length();

        Node[][] nodes = new Node[m + 1] [ n + 1];

        for(int row = 0 ; row <= m ; row ++){
            nodes[row][0] = new Node(row,Operation.DELETE);
        }

        for (int col = 0 ; col <= n ; col ++){
            nodes[0][col] = new Node(col,Operation.ADD);
        }

        for(int row = 1 ; row <= m ; row ++){
            for(int col = 1 ; col <= n ; col ++){
                if(s1.charAt(row - 1) == s2.charAt(col -1)){
                    nodes[row][col] = new Node(nodes[row-1][col-1].val,Operation.SKIP);
                } else {
                    nodes[row][col] = minNode(
                        nodes[row - 1 ][col -1],
                        nodes[row-1][col],
                        nodes[row][col-1]
                    );
                    nodes[row][col].val ++;
                }
            }
        }

        printResult(nodes,s1,s2);

        return nodes[m][n].val;

    }

    private static Node minNode(Node replace,Node delete, Node add){
        Node res = new Node(replace.val,Operation.REPLACE);

        if(res.val > delete.val){
            res.val = delete.val;
            res.choice = Operation.DELETE;
        }

        if(res.val > add.val){
            res.val = add.val;
            res.choice = Operation.ADD;
        }

        return res;

    }

    //从后往前操作打印
    private static void printResult(Node[][] nodes, String s1, String s2){
        int i = s1.length(),j = s2.length();
        System.out.println("Change s1=" + s1 + " to s2 =" + s2 + ":\n");
        while (i != 0 && j != 0){
            char c1 = s1.charAt(i - 1);
            char c2 = s2.charAt(j-1);
            Operation choice = nodes[i][j].choice;
            System.out.print("s1[" + (i-1) + "]:");
            switch (choice){
                case SKIP:
                    System.out.println("skip " + c1);
                    i --;
                    j --;
                    break;
                case ADD:
                    System.out.println("insert ' " + c2 + "'" );
                    j --;
                    break;
                case DELETE:
                    System.out.println("delete '" + c1 + "'");
                    i--;
                    break;
                case REPLACE:
                    System.out.println("replace '" + c1 + "' with '" + c2 + "'");
                    i --;
                    j --;
                    break;
            }
        }
        while (i > 0){
            System.out.print("s1[" + (i-1) + "]:");
            System.out.println("delete '" + s1.charAt(i-1) + "'");
            i--;
        }
        while (j > 0){
            System.out.print("s1[0]:");
            System.out.println("insert '" + s2.charAt(j-1) + "'");
            j--;
        }
    }

    private static class Node {
        int val;
        Operation choice;

        public Node(int val, Operation choice) {
            this.val = val;
            this.choice = choice;
        }
    }

    private static enum Operation {
        SKIP,DELETE,ADD,REPLACE;
    }

    public static void main(String[] args) {
        String s1 = "aa";
        String s2 = "cba";
        System.out.println(minDistance(s1,s2));

        s1 = "intention";
        s2 = "execution";
        System.out.println(minDistance(s1,s2));

        System.out.println(minDistanceWithDpTable(s1,s2));

        minDistanceWithMethod(s1,s2);

        minDistanceWithMethod("abcd","bdf");
        minDistanceWithMethod("abcd","bdfeg");
        minDistanceWithMethod("abd","abcd");
    }
}
