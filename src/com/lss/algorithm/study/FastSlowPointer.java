package com.lss.algorithm.study;

/**
 * 双指针之快慢指针
 */
public class FastSlowPointer {

    /**
     * 判断链表是否有环
     * @param node 链表头节点
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean hasCycle(Node node){
        Node slow = node;
        Node fast = node;
        while(fast != null && fast.next != null){
            slow = node.next;
            fast = fast.next.next;
            if(fast == slow) return true;
        }
        return false;
    }


}
