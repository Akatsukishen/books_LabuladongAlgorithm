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
     * n = 7 时 0.017s,answer = 40                      0.008s
     * n = 8 时  0.008s,answer =92                      0.019s
     * n = 9 时 0.02s,answer = 352                      0.023s
     * n = 10 时 0.098s,answer = 724                    0.075s
     * n = 13 时 2.225s,answer = 73712                  1.392s
     * n = 14 时 12.572s,answer = 365596                4.407s
     * n = 15 时 95s,answer = 2279184                   28.151s
     * 主要耗时在isPossible()函数
     * @param n 棋盘大小
     * @return
     */
    public static ArrayList<ArrayList<String>> nQueenWithMemory(int n){
        ArrayList<Position> pickedMemory = new ArrayList<>();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        nQueenWithMemory(n,0,pickedMemory,result);
        return result;
    }

    private static void nQueenWithMemory(int n ,int row,
                                         ArrayList<Position> pickedMemory,
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
            if(isPossible(pickedMemory,row,col)){
                pickedMemory.add(new Position(row,col));
                nQueenWithMemory(n,row + 1, pickedMemory,result);
                pickedMemory.remove(pickedMemory.size() - 1);
            }
        }
    }

    private static boolean isPossible(ArrayList<Position> pickedMemory,int row, int col){
        for(Position position : pickedMemory){
            if(position.col == col || Math.abs(position.row - row) == Math.abs(position.col - col)){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * n = 7 已选 6.765s
     * @param n
     * @return
     */
    public static ArrayList<ArrayList<String>> nQueenWithMemory2(int n){
        int[][] isPossible = new int[n][n];
        for(int row = 0 ; row < n ; row ++){
            for(int col = 0 ; col < n ; col ++){
                isPossible[row][col] = 1; //1 可能，0 已选， -1不可能
            }
        }
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        nQueenWithMemory2(n,0,isPossible,result);
        return result;
    }

    private static void nQueenWithMemory2(int n , int row ,int[][] isPossible,ArrayList<ArrayList<String>> result){

        if(row == n){
            ArrayList<String> oneAnswer = new ArrayList<>();
            for(int rowIndex = 0 ; rowIndex < n ; rowIndex ++){
                for(int colIndex = 0 ; colIndex < n ; colIndex ++){
                    if(isPossible[rowIndex][colIndex] == 0){
                        oneAnswer.add(generateRowPicked(n,colIndex));
                    }
                }
            }
            if(oneAnswer.size() == n){
                result.add(oneAnswer);
            }
            return;
        }

        for(int col = 0 ; col < n ; col ++){
            if(isPossible[row][col] == 1){ //可能的
                //不选
                isPossible[row][col] = 0;
                //同行右边的数据不要管，反正直接到下一行
                //下面的数据标记成不可能

                //同列的
                for(int belowRow = row + 1 ; belowRow < n ; belowRow ++){
                    isPossible[belowRow][col] = -1;
                }

                //右下角
                for(int below = row + 1,rightCol = col + 1; below < n && rightCol < n ;){
                    isPossible[below][rightCol] = -1;
                    below ++;
                    rightCol ++;
                }

                //左下角
                for(int below = row + 1,rightCol = col - 1; below < n && rightCol >= 0 ;){
                    isPossible[below][rightCol] = -1;
                    below ++;
                    rightCol -- ;
                }

                nQueenWithMemory2(n,row + 1,isPossible,result);

                //不选,数据恢复
                isPossible[row][col] = 1;
                //同行右边的数据不要管，反正直接到下一行
                //下面的数据标记成不可能

                //同列的
                for(int belowRow = row + 1 ; belowRow < n ; belowRow ++){
                    isPossible[belowRow][col] = 1;
                }

                //右下角
                for(int below = row + 1,rightCol = col + 1; below < n && rightCol < n ;){
                    isPossible[below][rightCol] = 1;
                    below ++;
                    rightCol ++;
                }

                //左下角
                for(int below = row + 1,rightCol = col - 1; below < n && rightCol >= 0 ;){
                    isPossible[below][rightCol] = 1;
                    below ++;
                    rightCol -- ;
                }
                nQueenWithMemory2(n,row + 1,isPossible,result);

            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ArrayList<ArrayList<String>> result = nQueen(7);
//        for(int index = 0 ; index < result.size() ; index ++){
//            ArrayList<String> oneAnswer = result.get(index);
//            System.out.println("结果" + (index + 1));
//            for(String rowAnswer : oneAnswer){
//                System.out.println("\t" + rowAnswer);
//            }
//        }
        long end = System.currentTimeMillis();
        System.out.println("Finished.duration = " + ((end - start) / 1000f) + "s,answer = " + result.size());

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
