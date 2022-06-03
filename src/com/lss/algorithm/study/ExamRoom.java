package com.lss.algorithm.study;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 考场位置编排：
 * 一排位置：每次进入一个考生，务必让其与周边的人的间隔最大
 * 如果有两个位置都可以满足间隔最大的要求的话，
 * 那么，返回最小的位置。
 * 考试过程中，考生可以随时考完立场leave,空出的位置，可以供后续进来的考生使用。
 *
 * 输入插入的过程中要保证数据的有序性，可以使用二叉堆和平衡二叉树。
 * 二叉堆每次只能删除最值，不能满足leave的要求。
 * 平衡二叉树，可以在插入和删除数据的过程中都保证数据的有序性，此题使用平衡二叉树作为底层数据结构。
 * TreeSet是有序数据结构，可以传入比较器来保证自定义顺序。
 * TreeSet本质上是Set，相等的元素只会保留一份。所以下面区间长度相等的情况下，还需要按照区间的先后排序，也恰好符合题目要求在两个位置都满足的情况下返回最小值的情况。
 *
 */
public class ExamRoom {

    private int N ;
    //以位置p开始的区间
    private Map<Integer,int[]> startMap = new HashMap<>();
    //以位置p结束的区间
    private Map<Integer,int[]> endMap = new HashMap<>();

    //int[]a a[0]表示位置0坐人了 a[1]表示位置1坐人了
    TreeSet<int[]> treeSet;

    public ExamRoom(int n){
        this.N = n;
        treeSet = new TreeSet<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(distance(o1) == distance(o2)){
                    return o2[0] - o1[0];
                }
                return distance(o1) - distance(o2);
            }
        });
        //假装在-1和n的位置安排两人
        int[] fake = new int[]{-1,n};
        addInterval(fake);
    }

    private int distance(int[] interval){
        return interval[1] - interval[0];
    }

    /**
     * 安排一个考生就位：
     * 要求间隔最大
     */
    public int seat(){
        int[] interval = treeSet.last();
        int start = interval[0];
        int end = interval[1];

        int seat;
        if(start == -1){
            seat = 0;
        } else if(end == N) {
            seat = N - 1;
        } else {
            seat = start + (end - start) /2;
        }

        int[] midEnd = new int[]{start,seat};
        int[] midStart = new int[] {seat,end};

        removeInterval(interval);
        addInterval(midEnd);
        addInterval(midStart);

        return seat;
    }

    /**
     * 第p个位置的人离开考试
     * @param p
     */
    public void leave(int p){
        int[] pEnd = endMap.get(p);
        int[] pStart = startMap.get(p);
        removeInterval(pEnd);
        removeInterval(pStart);

        int[] interval = new int[]{pEnd[0],pStart[1]};
        addInterval(interval);
    }

    private void addInterval(int[] interval){
        treeSet.add(interval);
        startMap.put(interval[0],interval);
        endMap.put(interval[1],interval);
    }

    private void removeInterval(int[] interval){
        treeSet.remove(interval);
        startMap.remove(interval[0]);
        endMap.remove(interval[1]);
    }

    public static void main(String[] args) {
        ExamRoom room = new ExamRoom(9);
        // 0	8	4	2	6	1	3	5	7
//        for(int i = 0 ; i < 9 ; i ++){
//            System.out.print (room.seat() + "\t");
//        }
        room.seat();
        room.seat();
        room.seat();
        room.seat();
        room.leave(4);
        System.out.println(room.seat());
    }

}
