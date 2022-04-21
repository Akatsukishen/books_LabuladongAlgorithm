package com.lss.algorithm.study;

import javafx.geometry.Pos;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * N皇后问题
 * 在一个棋盘上，皇后可以共计同一列、同一行、左斜角斜线、右斜角斜线上的旗子。
 * 给定一个N*N的棋盘，给定N个皇后，计算出所有让皇后不相互攻击的摆法。
 */
public class NQueen {

    /**
     * n = 7 时 17s,answer=40
     * n = 8 时 1360.33s
     * @param n 横向、纵向的棋子数（n * n)
     * @return 返回所有的摆法 ArrayList<ArrayList<String>>
     *     其中里面ArrayList<String>是一种摆法，它里面的元素是每行的摆法
     *     暴力迭代，尝试所有的可能
     */
    public static ArrayList<ArrayList<String>> nQueen(int n){
        ArrayList<String> picked = new ArrayList<>();
        // 全部 一种摆法 一行摆法
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        nQueen(n,0,picked,result);
        return result;
    }

    private static void nQueen(int n, int startRow, ArrayList<String> picked,ArrayList<ArrayList<String>> result){
        if(startRow == n){
            if(isPickedCorrect(picked)){
                result.add(new ArrayList<>(picked));
            }
            return;
        }
        for(int col = 0 ; col < n ; col ++){

            picked.add(generateRowPicked(n,col)); //选择
            nQueen(n,startRow + 1, picked,result);
            picked.remove(picked.size() - 1);

            picked.add(generateRowPicked(n,n)); //不选择
            nQueen(n,startRow + 1, picked,result);
            picked.remove(picked.size() - 1);
        }
    }

    private static String generateRowPicked(int n, int pickedPos){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < pickedPos;i++){
            sb.append("-");
        }
        if(pickedPos <= n - 1){
            sb.append("Q");
        }
        for(int i = pickedPos + 1; i < n ; i ++){
            sb.append("-");
        }
        return sb.toString();
    }

    private static boolean isPickedCorrect(ArrayList<String> picked){
        HashSet<Position> positions = new HashSet<>();
        for(int row = 0 ; row < picked.size() ; row ++){
            String rowResult = picked.get(row);
            int colPos = rowResult.indexOf("Q");
            if(colPos == -1){
                return false;
            } else {
                int finalRow = row;
                AtomicBoolean valid = new AtomicBoolean(true);
                positions.forEach(position -> {
                    if(position.row == finalRow
                            || position.col == colPos
                            || (Math.abs(position.row - finalRow) == Math.abs(position.col - colPos))
                    ) {
                        valid.set(false);
                    }
                });
                if(valid.get()){
                    positions.add(new Position(row,colPos));
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private static class Position {
        int row;
        int col;
        Position(int row,int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    /**
     * 带有备忘录的N皇后问题 在摆放时，提前确定是否跟之前的冲突，而不是等摆放到最后才做决定
     * n = 7 时 0.017s,answer = 40              加入列缓存: 0.008s,answer = 40
     * n = 8 时  0.008s,answer =92                        0.019s,answer = 92
     * n = 9 时 0.02s,answer = 352                        0.026s,answer = 352
     * n = 10 时 0.098s,answer = 724                      0.099s,answer = 724
     * n = 13 时 2.225s,answer = 73712                    1.891s,answer = 73712
     * n = 14 时 12.572s,answer = 365596                  9.57s,answer = 365596
     * n = 15 时 95s,answer = 2279184                     72.178s,answer = 2279184
     * 主要耗时在isPossible()函数
     * @param n 棋盘大小
     * @return
     */
    public static ArrayList<ArrayList<String>> nQueenWithMemory(int n){
        ArrayList<Position> pickedMemory = new ArrayList<>();
        HashSet<Integer> pickedCol = new HashSet<>();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        nQueenWithMemory(n,0,pickedMemory,pickedCol,result);
        return result;
    }

    private static void nQueenWithMemory(int n ,int row,
                                         ArrayList<Position> pickedMemory,
                                         HashSet<Integer> pickedCol,
                                         ArrayList<ArrayList<String>> result){
        if(row == n){
            ArrayList<String> oneAnswer = new ArrayList<>();
            for(Position position : pickedMemory){
                oneAnswer.add(generateRowPicked(n,position.col));
            }
            result.add(oneAnswer);
            return;
        }

        for(int col = 0; col < n ; col ++){
            if(isPossible(pickedMemory,pickedCol,row,col)){
                pickedMemory.add(new Position(row,col));
                pickedCol.add(col);
                nQueenWithMemory(n,row + 1, pickedMemory,pickedCol,result);
                pickedMemory.remove(pickedMemory.size() - 1);
                pickedCol.remove(col);
            }
        }
    }

    private static boolean isPossible(ArrayList<Position> pickedMemory,
                                      HashSet<Integer> pickedCol,
                                      int row, int col){
        if(pickedCol.contains(col)){
            return false;
        }
        for(Position position : pickedMemory){
            if(Math.abs(position.row - row) == Math.abs(position.col - col)){
                return false;
            }
        }
        return true;
    }

    /**
     * 使用数组进行棋盘摆放
     * 保存存放位置进行摆放统计                     使用数组进行棋盘摆放统计
     * n = 7 时 0.017s,answer = 40                   0.002s,answer = 40
     * n = 8 时  0.008s,answer =92                   0.02s,answer = 92
     * n = 9 时 0.02s,answer = 352                   0.021s,answer = 352
     * n = 10 时 0.098s,answer = 724                 0.054s,answer = 724
     * n = 13 时 2.225s,answer = 73712               3.395s,answer = 73712
     * n = 14 时 12.572s,answer = 365596             19.784s,answer = 365596
     * n = 15 时 95s,answer = 2279184                147.752s,answer = 2279184
     * @param n
     * @return
     */
    public static ArrayList<ArrayList<String>> nQueenWithArray(int n){
        String[][] board = new String[n][n];
        for(int row = 0 ; row < n ; row++){
            for(int col = 0 ; col < n ; col ++){
                board[row][col] = "-";
            }
        }
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        nQueenWithArray(n,board,0,result);
        return result;
    }

    private static void nQueenWithArray(int n , String[][] board,int row, ArrayList<ArrayList<String>> result){
        if(row == n){
            ArrayList<String> answer = new ArrayList<>();
            for(int rowIndex = 0 ;rowIndex < n ; rowIndex ++){
                StringBuilder sb = new StringBuilder();
                for(int colIndex = 0 ; colIndex < n ; colIndex ++){
                    sb.append(board[rowIndex][colIndex]);
                }
                answer.add(sb.toString());
            }
            result.add(answer);
            return;
        }
        for(int col = 0; col < n ; col++){
            if(isValid(board,row,col)){
                board[row][col] = "Q";
                nQueenWithArray(n,board,row + 1, result);
                board[row][col] = "-";
            }
        }
    }

    /**
     * 检查是否可以在row,col位置放置皇后
     * @param board
     * @param row
     * @param col
     * @return
     */
    private static boolean isValid(String[][]board, int row, int col){
        //检查前面行的同列是否已经放置皇后
        for(int rowIndex = 0 ; rowIndex < row ;rowIndex ++){
            if("Q".equals(board[rowIndex][col])){
                return false;
            }
        }
        //检查左上角是否已经放置皇后
        for(int rowIndex = row -1 , colIndex = col -1 ; rowIndex >= 0 && colIndex >=0;rowIndex--,colIndex--){
            if("Q".equals(board[rowIndex][colIndex])){
                return false;
            }
        }
        //检查右上角是否已经放置皇后
        for(int rowIndex = row -1 , colIndex = col + 1 ; rowIndex >= 0 && colIndex < board.length ;rowIndex--,colIndex++){
            if( "Q".equals(board[rowIndex][colIndex]) ){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ArrayList<ArrayList<String>> result =  nQueenWithArray(15);
//        for(int index = 0 ; index < result.size() ; index ++){
//            ArrayList<String> oneAnswer = result.get(index);
//            System.out.println("结果" + (index + 1));
//            for(String rowAnswer : oneAnswer){
//                System.out.println("\t" + rowAnswer);
//            }
//        }
        long end = System.currentTimeMillis();
        System.out.println("Finished.duration = " + ((end - start) / 1000f) +  "s,answer = " + result.size());

//        long start2 = System.currentTimeMillis();
//        ArrayList<ArrayList<String>> result2 = nQueenWithMemory2(7);
//        for(int index = 0 ; index < result2.size() ; index ++){
//            ArrayList<String> oneAnswer = result2.get(index);
//            System.out.println("结果" + (index + 1));
//            for(String rowAnswer : oneAnswer){
//                System.out.println("\t" + rowAnswer);
//            }
//        }
//        long end2 = System.currentTimeMillis();
//        System.out.println("Finished.duration2 = " + ((end2 - start2) / 1000f) + "s,answer = " + result2.size());
    }
}
