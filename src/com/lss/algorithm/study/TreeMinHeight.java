package com.lss.algorithm.study;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 获取树的最小高度问题:根节点到叶子节点的最短距离
 */
public class TreeMinHeight {

    /**
     * 使用BFS的方法，计算根节点到叶子节点的最短距离
     * 1. 使用队列Queue来保持同层的节点。
     * 2. 遍历同层节点前，使用size来记录同层节点数，以便后续的遍历。
     * 3. 从队列中拿出数据后判断数据是否满足要求
     * @param node 树根节点
     * @return  根节点到叶子节点的最短距离。
     */
    @SuppressWarnings("rawtypes")
    public static int getMinHeight(BNode node){
        if(node == null){
            return 0;
        }
        Queue<BNode> queue = new LinkedList<>();
        int depth = 1;
        queue.offer(node);
        while(!queue.isEmpty()){
            int size = queue.size(); //记录该层的总数，以便后续遍历
            for(int i = 0 ; i < size ; i ++){
                BNode curNode = queue.poll();
                if(curNode.left == null && curNode.right == null){
                    return depth;
                }
                if(curNode.left != null){
                    queue.offer(curNode.left);
                }
                if(curNode.right != null){
                    queue.offer(curNode.right);
                }
            }
            depth ++;
        }
        return depth;
    }

    public static void main(String[] args) {
        System.out.println(getMinHeight(BNode.testTree()));
    }
}
