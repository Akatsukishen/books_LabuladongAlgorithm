package com.lss.algorithm.study;

import java.util.HashSet;
import java.util.Set;

/**
 * 数独问题
 */
public class Sudoku {

    /**
     * 9*9的二维数组
     * 1. 每行不出现重复的数字
     * 2. 每列不出现重复的数字
     * 3. 每个3*3的小方格内不能出现重复的数字
     * 4. 未填用 . 表示
     * @param chars
     */
    public static void solveSudoku(char[][] chars){
        solveSudoku(chars,0,0);
    }

    private static boolean solveSudoku(char[][] chars, int row, int column){
        if(column == 9){
            row = row + 1;
            column = 0;
        }

        if(row == 9){
            return true ;
        }

        if(chars[row][column] != '.'){
            if(isValidColumn(chars,row,column)){
                return solveSudoku(chars,row,column + 1);
            } else {
                return false;
            }
        }

        for(int i = 1 ; i < 10; i ++){
            chars[row][column] = (char)(i + '0') ;
            if(isValid(chars,row,column)){
                if(solveSudoku(chars,row,column + 1)){
                    return true;
                } else {
                    chars[row][column] = '.';
                }
            } else {
                chars[row][column] = '.';
            }
        }

        return false;
    }

    private static boolean isValid(char[][] chars, int row, int column){
        return isValidRow(chars,row,column) && isValidColumn(chars,row,column)
                && isValidRoom(chars,row,column);

    }

    private static boolean isValidRow(char[][] chars, int row,int column){
        Set<Character> existed = new HashSet<>();
        for(int i = 0 ; i <= 8 ; i ++){
            if(chars[row][i] != '.'){
                if(existed.contains(chars[row][i])){
                    return false;
                } else {
                    existed.add(chars[row][i]);
                }
            }
        }
        return true;
    }

    private static boolean isValidColumn(char[][] chars ,int row, int column){
        Set<Character> existed = new HashSet<>();
        for(int i = 0 ; i <= 8 ; i ++){
            if(chars[i][column] != '.'){
                if(existed.contains(chars[i][column])){
                    return false;
                } else {
                    existed.add(chars[i][column]);
                }
            }
        }
        return true;
    }

    //3*3的区域内都正常
    //我们从上到下，从左到右添加，只需要保证下面填写b的位置时，左上角的区域内正常，因为其他位置会在其他的位置上再验证
    /**
     *  a  a  a
     *  a  a  a
     *  a  a  b
     */
    private static boolean isValidRoom(char[][] chars, int row, int column){
        Set<Character> existed = new HashSet<>();
        int roomRowStart = row / 3 * 3;
        int roomColumnStart = column / 3 * 3;

        for(int i = roomRowStart ; i <= roomRowStart + 2 ;i ++){
            for(int j = roomColumnStart; j<= roomColumnStart + 2 ; j ++){
                if(chars[i][j] != '.'){
                    if(existed.contains(chars[i][j])){
                        return false;
                    } else {
                        existed.add(chars[i][j]);
                    }
                }
            }
        }
        return true;
    }

    /***
     *  . 1 . . . . . . .
     *  2 . . . . . . . .
     *  . . . . . 5 . . .
     *  . . . . . . . . .
     *  . . . . . . . . .
     *  . . . 7 . . . . .
     *  . . . . . . . . .
     *  . . . . . . . . .
     *  . . . . . . . 9 .
     *
     */
    public static void main(String[] args) {
        char[][] chars = new char[9][9];
        for(int i = 0 ; i < 9 ; i ++){
            for(int j = 0 ; j < 9 ; j ++){
                chars[i][j] = '.';
            }
        }
        chars[0][1] = '1';
        chars[1][0] = '2';
        chars[2][5] = '5';
        chars[5][3] = '7';
        chars[8][7] = '9';

        long start = System.currentTimeMillis();
        solveSudoku(chars);
        System.out.println("duration = " + (System.currentTimeMillis() - start)/1000f);

        for(int i = 0 ; i < 9 ; i ++){
            for(int j = 0 ; j < 9 ; j ++){
                System.out.print(chars[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
