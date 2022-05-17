package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SlidingPuzzle {

    /**
     * 二维数组中有一个滑块0可以与周边的数进行交换位置
     * [
     *   [4,1,2]
     *   [5,0,3]
     * ]
     * 滑动到  target = '123450所需的最少步骤'
     * [
     *   [1,2,3]
     *   [4,5,0]
     * ]
     * @return
     */
    public static int slidingPuzzle(int[][] nums,String target){
        LinkedList<String> nexts = new LinkedList<>();
        HashSet<String> ignored = new HashSet<>();
        int row = nums.length;
        int column = nums[0].length;
        int step = 0;
        StringBuilder numSb = new StringBuilder();
        for(int i = 0 ; i < row ; i ++){
            for(int j = 0 ; j < column; j ++){
                numSb.append(nums[i][j]);
            }
        }
        nexts.add(numSb.toString());

        while (!nexts.isEmpty()){
            int size = nexts.size();
            for(int i = 0 ;i < size ; i ++){
                String str = nexts.removeFirst();
                if(str.equals(target)){
                    return step;
                } else {
                    ignored.add(str);
                    List<String> generated = nexts(str,row,column);
                    for(String generate : generated){
                        if(!ignored.contains(generate)){
                            nexts.add(generate);
                            ignored.add(generate);
                        }
                    }
                }
            }
            step ++;
        }

        return -1;
    }

    public static List<String> nexts(String input,int row,int column){
        int index = input.indexOf('0');
        int indexRow = index / column;
        int indexColumn = index % column;
        List<String> nexts = new ArrayList<>();

        //跟上面的交换
        if(indexRow > 0){
            char[] chars = input.toCharArray();
            chars[index] = chars[index - column];
            chars[index - column] = '0';
            nexts.add(new String(chars));
        }

        //跟下面的交互
        if(indexRow < row - 1){
            char[] chars = input.toCharArray();
            chars[index] = chars[index + column];
            chars[index + column] = '0';
            nexts.add(new String(chars));
        }

        //跟左边的交换
        if(indexColumn > 0){
            char[] chars = input.toCharArray();
            chars[index] = chars[index - 1];
            chars[index - 1] = '0';
            nexts.add(new String(chars));
        }

        //跟右边的交换
        if(indexColumn < column - 1){
            char[] chars = input.toCharArray();
            chars[index] = chars[index + 1];
            chars[index + 1] = '0';
            nexts.add(new String(chars));
        }

        return nexts;
    }

    public static void main(String[] args) {

        int[][] nums = new int[2][3];
        nums[0][0] = 4;
        nums[0][1] = 1;
        nums[0][2] = 2;
        nums[1][0] = 5;
        nums[1][1] = 0;
        nums[1][2] = 3;

        System.out.println(slidingPuzzle(nums,"123450"));

    }

}
