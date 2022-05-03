package com.lss.algorithm.study;

/**
 * 二叉树节点
 * @param <T>
 */
public class BNode<T> {
    public BNode left;
    public BNode right;
    T value;

    public BNode(T value){
        this.value = value;
    }

    /**
     *         10
     *      /     \
     *     9      8
     *      \    /  \
     *       7   6  5
     *          /    \
     *          4     3
     * @return
     */
    public static BNode<Integer> testTree(){
        BNode<Integer> tree = new BNode<>(10);
        BNode<Integer> node9 = new BNode(9);
        node9.right = new BNode(7);
        BNode<Integer>  node8 = new BNode<>(8);
        BNode<Integer>  node6 = new BNode<>(6);
        BNode<Integer>  node5 = new BNode<>(5);
        BNode<Integer>  node4 = new BNode<>(4);
        node6.left = node4;
        node8.right = node5;
        node8.left = node6;

        tree.left = node9;
        tree.right = node8;
        return tree;
    }
}
