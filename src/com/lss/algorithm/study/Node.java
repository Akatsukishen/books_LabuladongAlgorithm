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
}
