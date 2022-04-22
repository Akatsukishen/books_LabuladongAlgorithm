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

    /**
     *
     * @param head 链表
     * @return     返回链表中环开始的地方
     * 1.
     *  快慢指针在慢指针走了k步后相遇，相遇点距离环起点m处
     *  那么快指针走了2k步，也就是说，第一次到达m处时，已经走了k步，然后走了k步很多圈回到m处，
     *  --> k步可以相当于很多圈
     *
     *  2. 调整一个指针回到起点，然后以同样的速度开始走到环开始的地方，走了k-m步，
     *   另外一个指针也走了k-m步，k-m + 起点m = k
     *   上面可知，k步可以相当于很多圈。
     *   那再走k-m回到了以环开始的很多圈。
     *   --> 两指针在环开的地方相遇。
     *          k - m
     *  - - - - - - - - -
     *                 / \
     *                /   \
     *                \   /  在此相遇 m
     *                \  /
     *                 --
     *
     */
    @SuppressWarnings("rawtypes")
    public static Node detectCycleStart(Node head){
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) break;
        }
        slow = head;
        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     *
     *   1    ->   2   ->   3   -> null
     *  slow
     *  fast
     *  1    ->   2   ->   3   -> null
     *           slow
     *                    fast
     *
     *  1    ->   2   ->   3   ->  4  -->  null
     *  slow
     *  fast
     *  1    ->   2   ->   3   ->  4  -->  null
     *                    slow
     *                                     fast
     * @param head 链表头节点
     * @return     找到链表中间节点
     */
    @SuppressWarnings("rawtypes")
    public static Node findMiddle(Node head){
        Node fast = head;
        Node slow = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


}
