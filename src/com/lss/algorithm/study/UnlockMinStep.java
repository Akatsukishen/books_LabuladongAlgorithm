package com.lss.algorithm.study;

import java.util.*;

public class UnlockMinStep {

    /**
     * 从初始密码开始，上下滚动一位，中间不能滚动到deadends中的密码，求最小滚动次数
     * @param deadends  不能滚动到的密码
     * @param target    目标密码
     * @return          最小滚动次数
     */
    public static int unlock(List<String> deadends,String target){
        String start = "0000";
        Queue<String> next = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int step = 0;
        next.add(start);

        while (!next.isEmpty()){
            int size = next.size();
            for(int i = 0 ; i < size ; i ++){
                String cur = next.poll();
                if(cur.equals(target)){
                    return step;
                }
                if(deadends.contains(cur)){
                    continue;
                }
                for(int j = 0; j < cur.length() ; j ++){
                    String plus = plusOne(cur,j);
                    if(!visited.contains(plus)){
                        visited.add(plus);
                        next.add(plus);
                    }
                    String minus = minusOne(cur,j);
                    if(!visited.contains(minus)){
                        visited.add(minus);
                        next.add(minus);
                    }
                }
            }
            step ++;
        }

        return -1;
    }


    private static String plusOne(String s, int j){
        char[] ch = s.toCharArray();
        if(ch[j] == '9'){
            ch[j] = '0';
        } else {
            ch[j] += 1;
        }
        return new String(ch);
    }

    private static String minusOne(String s, int j){
        char[] ch = s.toCharArray();
        if(ch[j] == '0'){
            ch[j] = '9';
        } else {
            ch[j] -= 1;
        }
        return new String(ch);
    }

    public static void main(String[] args) {
        List<String> deadends = new ArrayList<>();
        deadends.add("0100");
        deadends.add("1000");
        System.out.println(unlock(deadends,"0200"));

        deadends.clear();
        deadends.add("8887");
        deadends.add("8889");
        deadends.add("8878");
        deadends.add("8898");
        deadends.add("8788");
        deadends.add("8988");
        deadends.add("9888");
        deadends.add("7888");
        System.out.println(unlock(deadends,"8888"));
    }

}
