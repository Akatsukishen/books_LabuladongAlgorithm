package com.lss.algorithm.study;

/**
 * 链表反转
 */
public class ListReverse {

    /**
     * 递归遍历链表
     * @param head
     * @return
     */
    public static Node<Integer> reverse(Node<Integer> head){
        if(head == null || head.next == null){
            return head;
        }
        Node<Integer> last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    /**
     * 反转前k个节点的链表
     * -> 反转以head开头的前k个节点，并返回链表头部
     * @param head
     * @param k
     * @return
     */
    public static Node<Integer> reversePreK(Node<Integer> head,int k){
        if(k == 1){
            successor = head.next; //后继节点
           return head;
        }
        Node<Integer> last = reversePreK(head.next,k-1);
        head.next.next = head;
        head.next = successor;
        return last;
    }

    private static Node<Integer> successor;

    /**
     * 反转链表中的【m,n】区间段的节点
     * m从1开始计数
     * 如果m变成1，那么相当于逆转前面的n个节点
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static Node<Integer> reverseBetween(Node<Integer> head, int m , int n){
        if(m == 1){
            return reversePreK(head,n);
        }
        head.next = reverseBetween(head.next,m-1,n-1);
        return head;
    }

    public static Node<Integer> reverseKGroup(Node<Integer> head, int k){

        Node<Integer> prev = null;
        Node<Integer> cur = head;

        int count = 1;

        Node<Integer> newHead = null;

        Node<Integer> preGroupEnd = null;
        Node<Integer> groupEnd = null;
        Node<Integer> groupStart = null;

        while(cur != null){

            if(count == 1){//本组第一个，即队尾
                groupEnd = cur;
            }

            if(count == k){ //本组最后一个，反转后第一个
                groupStart = cur;
                groupEnd.next = null;
                if(newHead == null){
                    newHead = groupStart;
                }
                if(preGroupEnd != null){
                    preGroupEnd.next = groupStart;
                }
                preGroupEnd = groupEnd;
            }

            Node<Integer> next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;

            count ++;
            if(count > k){
                count = 1;
            }
        }

        if(count > 1 && prev != null){ //最后一部分少于k
            cur = prev;
            prev = null;
            while (cur != null && k > 1){
                Node<Integer> next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
                k --;
            }
            if(preGroupEnd != null){
                preGroupEnd.next = prev;
            }
        }
        return newHead;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public static Node<Integer> reverseByIterator(Node<Integer> head){
        Node<Integer> pre = null,cur,next;
        cur = head;
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 反转【a,b)之间的链表
     * 反转整个链表本质上是反转 【head,null)
     * @param a
     * @param b
     * @return
     */
    public static Node<Integer> reverseBetween(Node<Integer> a,Node<Integer> b){
        Node<Integer> pre = null,cur = a,next= null;
        while (cur != b){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static Node<Integer> reverseKGroupIterator(Node<Integer> head, int k){
        Node<Integer> a,b;
        a = head;
        b = head;
        for(int i = 0 ; i < k ;i ++){
            if(b == null){
                return head;
            }
            b = b.next;
        }
        Node<Integer> newHead = reverseBetween(a,b);
        a.next = reverseKGroupIterator(b,k);
        return newHead;
    }


    public static void main(String[] args) {
        Node<Integer> head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(3);
        head.next.next.next = new Node<>(4);
        head.next.next.next.next = new Node<>(5);

        head.traverse();

//        Node<Integer> newHead = reverseByIterator(head);
//        Node<Integer> newHead = reverseBetween(head,null);
//        Node<Integer> newHead = reverseBetween(head,null);
        Node<Integer> newHead = reverseKGroupIterator(head,3);

        newHead.traverse();
//
//        Node<Integer> reversed = reverse(head);
//        reversed.traverse();

//         Node<Integer> newHead  = reversePreK(head,3);
//         newHead.traverse();

//        Node<Integer> reverseBetween = reverseBetween(head,2,3);
//        reverseBetween.traverse();
//        Node<Integer> newHead = reverseKGroup(head,2);
//        newHead.traverse();
    }
}
