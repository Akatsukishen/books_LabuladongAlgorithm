package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.List;

/**
 * LCA : Lowest Common Ancestor
 * 最近公共祖先
 */
public class LCA {

    public static List<BNode<Integer>> getParent(BNode<Integer> root, BNode<Integer> target){
        List<BNode<Integer>> parents = new ArrayList<>();
        findParent(root,target,parents);
        return parents;
    }

    private static BNode<Integer> findParent(BNode<Integer> root, BNode<Integer> target, List<BNode<Integer>> parents){
        if(root == null){
            return null;
        }
        if(target == root){
            parents.add(root);
            return root;
        }
        BNode<Integer> lP = findParent(root.left,target,parents);
        if(lP != null){
            parents.add(root);
            return lP;
        }
        BNode<Integer> rP = findParent(root.right,target,parents);
        if(rP != null){
            parents.add(root);
            return rP;
        }
        return null;
    }

    public static BNode<Integer> lowestCommonAncestor(BNode<Integer> root,BNode<Integer> p , BNode<Integer> q){
        if(root == null){
            return null;
        }
        if(root == p || root == q){
            return root;
        }
        BNode<Integer> left = lowestCommonAncestor(root.left,p,q);
        BNode<Integer> right = lowestCommonAncestor(root.right,p,q);
        if(left != null && right != null){ //在左右子树中都找到节点 p,q ==> 共有节点
            return root;
        }
        if(left == null && right == null){ //左右子树中都没找到
            return null;
        }
        return left == null ? right : left; //暂时返回包含一个节点，表明找到一个节点
    }

    /**
     *              8
     *             / \
     *            6   9
     *           / \
     *          4   7
     *         / \
     *        3   5
     */
    public static void main(String[] args) {
        BNode<Integer> node8 = new BNode<>(8);
        BNode<Integer> node9 = new BNode<>(9);
        BNode<Integer> node6 = new BNode<>(6);
        node8.left = node6;
        node8.right = node9;

        BNode<Integer> node4 = new BNode<>(4);
        BNode<Integer> node7 = new BNode<>(7);
        node6.left = node4;
        node6.right = node7;

        BNode<Integer> node3 = new BNode<>(3);
        BNode<Integer> node5 = new BNode<>(5);
        node4.left = node3;
        node4.right = node5;

        // BST.middleTraverse(node8);
        List<BNode<Integer>> parents3 = getParent(node8,node3);
        System.out.println("node3 parents");
        for(BNode<Integer> parent : parents3){
            System.out.println(parent.value);
        }

        List<BNode<Integer>> parents7 = getParent(node8,node7);
        System.out.println("node6 parents");
        for(BNode<Integer> parent : parents7){
            System.out.println(parent.value);
        }

        for(BNode<Integer> parent : parents3){
            if(parents7.contains(parent)){
                System.out.println("===> LCA = " + parent.value);
                break;
            }
        }

        System.out.println(lowestCommonAncestor(node8,node3,node7).value);
    }
}
