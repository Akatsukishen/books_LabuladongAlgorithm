package com.lss.algorithm.study;

public class Node<T> {

    T value;

    Node<T> next;

    public Node(T  value,Node<T> next){
        this.value = next.value;
        this.next = next;
    }

    public Node(T value) {
        this.value = value;
    }

    public void traverse(){
        Node<T> cur = this;
        while (cur != null){
            System.out.print(cur.value + " --> " );
            cur = cur.next;
        }
        System.out.println("NULL");
    }
}
