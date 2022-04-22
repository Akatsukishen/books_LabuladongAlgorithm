package com.lss.algorithm.study;

public class Node<T> {

    T value;

    Node<T> next;

    public Node(T  value,Node<T> next){
        this.value = next.value;
    }
}
