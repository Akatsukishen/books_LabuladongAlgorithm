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
        next.offer(start);
        visited.add(start);

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
                        next.offer(plus);
                    }
                    String minus = minusOne(cur,j);
                    if(!visited.contains(minus)){
                        visited.add(minus);
                        next.offer(minus);
                    }
                }
            }
            step ++;
        }

        return -1;
    }

    /**
     * 如下情况
     *    单向dfs 需要遍历整个树节点
     *         *
     *       /   \
     *      *     *
     *     / \   / \
     *    *  *  *   *
     *   /\ /\  / \/ \
     *  * * * * t ** *
     * 双向dfs可以减少遍历的节点
     *         s
     *       /   \
     *      s1   s12
     *     / \   / \
     *    *  *  t1   *
     *   /\ /\  / \/ \
     *  * * * * t ** *
     *
     * @param deadEnds
     * @param target
     * @return
     */
    public static int unlockDoubleBFS(List<String> deadEnds,String target){
        Queue<String> q1 = new LinkedList<>();  //第一个需要扩散的队列
        Queue<String> q2 = new LinkedList<>(); // 第二个需要扩散的队列
        Set<String> visited = new HashSet<>();
        String start = "0000";
        q1.offer(start);
        q2.offer(target);
        visited.add(start);
        int step = 0;

        while (!q1.isEmpty() && !q2.isEmpty()){

            int size =  q1.size();
            for(int i = 0 ; i < size;i ++){
                String cur = q1.poll();
                if(deadEnds.contains(cur)) continue;
                if(q2.contains(cur)){
                    return step;
                }
                visited.add(cur);
                for(int j = 0; j < cur.length() ; j ++){
                    String plus = plusOne(cur,j);
                    if(!visited.contains(plus)){
                        q1.add(plus);
                    }
                    String minus = minusOne(cur,j);
                    if(!visited.contains(minus)){
                        q1.add(minus);
                    }
                }
            }
            step ++;
            Queue<String> temp = q1;
            q1 = q2;
            q2 = temp;
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
        System.out.println(unlockDoubleBFS(deadends,"0200"));

        deadends.clear();
        deadends.add("8887");
        deadends.add("8889");
        deadends.add("8878");
        deadends.add("8898");
        deadends.add("8788");
        deadends.add("8988");
        deadends.add("9888");
        deadends.add("7888");
        System.out.println(unlockDoubleBFS(deadends,"8888"));
    }

}
