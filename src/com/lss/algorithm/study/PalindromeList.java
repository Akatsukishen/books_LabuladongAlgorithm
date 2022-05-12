package com.lss.algorithm.study;

import java.util.Objects;

/**
 * 回文列表判断
 */
public class PalindromeList {

    public static boolean isPalindrome(Node<Integer> head){
        Node<Integer>[] left = new Node[]{head};
        return isPalindrome(head,left);
    }

    private static boolean isPalindrome(Node<Integer> right, Node<Integer>[] left) {
        if(right == null){
            return true;
        }
        boolean res = isPalindrome(right.next,left);
        //后续遍历
        res = res && Objects.equals(right.value, left[0].value);
        left[0] = left[0].next;
        return res;
    }

    public static void main(String[] args) {
        Node<Integer> head = new Node<>(1);
        Node<Integer> node2 =  new Node<>(2);
        Node<Integer> node22 = new Node<>(2);
        Node<Integer> node11 = new Node<>(1);
        node22.next = node11;
        node2.next = node22;
        head.next = node2;

        System.out.println(isPalindrome(head));
    }
}
