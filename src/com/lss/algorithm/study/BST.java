package com.lss.algorithm.study;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Binary Search Tree : 二叉搜索树
 *
 *     root
 *    /    \
 *   left  right
 *
 *  满足条件  left.val < root.val < right.val
 *
 *  普通二叉树遍历套路：
 *      void traverse(Node root) {
 *          // root需要做什么
 *          // 其他的不用root操心，抛给递归
 *          traverse(root.left)
 *          traverse(root.right)
 *      }
 *
 */
public class BST {

    /**
     * 两个树是否完全一样
     * @param root1
     * @param root2
     * @return
     */
    public static boolean isSameTree(BNode<Integer> root1, BNode<Integer> root2){
        if(root1 == null && root2 == null){
            return true;
        }
        if(root1 == null || root2 == null){
            return false;
        }
        if(root1.value != root2.value){
            return false;
        }
        return isSameTree(root1.left,root2.left) && isSameTree(root1.right,root2.right);
    }

    private static BNode<Integer> buildTestSameTree1(){
        BNode<Integer> root = new BNode<>(10);
        root.left = new BNode(5);
        BNode<Integer> node15 = new BNode<>(15);
        node15.left = new BNode(12);
        node15.right = new BNode(20);
        root.right = node15;
        return root;
    }

    private static BNode<Integer> buildTestSameTree2(){
        BNode<Integer> root = new BNode<>(10);
        root.left = new BNode(5);
        BNode<Integer> node15 = new BNode<>(15);
//        node15.left = new BNode(13);
        node15.left = new BNode(12);
        node15.right = new BNode(20);
        root.right = node15;
        return root;
    }

    /**
     * 二叉搜索树是否合法
     * @param root
     * @return
     */
    public static boolean isValidBST(BNode<Integer> root){
        return isValidBST(root,null,null);
    }

    private static boolean isValidBST(BNode<Integer> root, BNode<Integer> min ,BNode<Integer> max){
        if(root == null){
            return true;
        }
        if(min != null && root.value <= min.value){
            return false;
        }
        if(max != null && root.value >= max.value){
            return false;
        }
        return isValidBST(root.left,min,root)       //左子树，最大节点变更为当前节点
                || isValidBST(root.right,root,max); //右子树，最小节点变更为当前节点
    }

    private static BNode<Integer> buildTestTree(){
        BNode<Integer> root = new BNode<>(10);
        root.left = new BNode(5);
        BNode<Integer> node15 = new BNode<>(15);
        node15.left = new BNode(12);
        node15.right = new BNode(20);
        root.right = node15;
        return root;
    }

    /**
     * 利用二叉搜索树查找
     * @param root
     * @param target
     * @return
     */
    public static boolean isIn(BNode<Integer> root,int target){
        if(root == null){
            return false;
        }
        if(root.value == target){
            return true;
        }
        if(root.value < target){
            return isIn(root.right,target);
        }
        return isIn(root.left,target);
    }

    public static BNode<Integer> insertIntoBST(BNode<Integer> root,int val){
        if(root == null){
            return new BNode(val);
        }
        if(root.value == val){
            return root;
        }
        if(root.value < val){
            root.right = insertIntoBST(root.right,val);
        } else {
            root.left = insertIntoBST(root.left,val);
        }
        return root;
    }

    public static BNode<Integer> delete(BNode<Integer> root,int val){
        if(root ==  null){
            return null;
        }
        if(root.value == val){
            //删除当前节点
            //找右节点的最小值
            //叶子节点
            if(root.left == null && root.right == null){
                return null;
            }
            //右子树为空
            if(root.left != null && root.right == null){
                return root.left;
            }

            //左子树为空
            if(root.left == null && root.right != null){
                return root.right;
            }

            BNode<Integer> minNode = getMin(root.right);
            minNode.right = delete(root.right, minNode.value);
            minNode.left = root.left;

            return minNode;
        } else if(root.value < val){
            root.right = delete(root.right,val);
        } else {
            root.left = delete(root.left,val);
        }
        return root;
    }

    private static BNode<Integer> getMin(BNode<Integer> root){
        while (root.left != null){
            root = root.left;
        }
        return root;
    }

    //中序遍历
    public static void middleTraverse(BNode<Integer> root){
        if(root == null){
            return;
        }
        middleTraverse(root.left);
        System.out.println(root.value + " ");
        middleTraverse(root.right);
    }

    /**
     *
     * @param root 完全二叉树根节点
     * @return     完全二叉树节点个数
     */
    public static int getCompleteBinaryTreeNodeCount(BNode<Integer> root){
        BNode<Integer> l = root,r  = root;

        //左右两边高度
        int hl = 0 ,hr = 0;

        while(l != null){
            l = l.left;
            hl ++;
        }

        while (r != null){
            r = r.right;
            hr ++;
        }

        //左右高度一样是满二叉树
        if(hl == hr){
            return  (int) Math.pow(2,hl) - 1;
        }

        //完全二叉树左右子树依然是完全二叉树
        return 1 + getCompleteBinaryTreeNodeCount(root.left)  + getCompleteBinaryTreeNodeCount(root.right);
    }


    /**
     * 前序遍历字符串反序列化: # 代表空节点
     *  1,2,#,4,#,#,3,#,#
     * @param data
     * @return
     */
    public static BNode<Integer> deserializeByPre(String data){
        LinkedList<String> nodes = new LinkedList<>();
        for(String s : data.split(",")){
            nodes.add(s);
        }
        return deserializeByPre(nodes);
    }

    private static BNode<Integer> deserializeByPre(LinkedList<String> nodes){
        if(nodes.isEmpty()){
            return null;
        }
        String first = nodes.removeFirst();
        if("#".equals(first)){
            return null;
        }
        BNode<Integer> bNode = new BNode<>(Integer.parseInt(first));
        bNode.left = deserializeByPre(nodes);
        bNode.right = deserializeByPre(nodes);

        return bNode;
    }

    public static String serializeByPre(BNode<Integer> node){
        StringBuilder sb = new StringBuilder();
        serializeByPre(node,sb);
        return sb.toString();
    }

    private static void serializeByPre(BNode<Integer> root,StringBuilder sb){
        if(root == null){
            sb.append("#,");
            return;
        }

        sb.append(root.value).append(",");

        serializeByPre(root.left,sb);
        serializeByPre(root.right,sb);
    }

    /**
     * 后序遍历反序列化结果：
     * @param data
     * @return
     */
    public static BNode<Integer> deserializeByAfter(String data){
        LinkedList<String> nodes = new LinkedList<>();
        for(String s : data.split(",")){
            nodes.add(s);
        }
        return deserializeByAfter(nodes);
    }

    private static BNode<Integer> deserializeByAfter(LinkedList<String> nodes){
        if(nodes.isEmpty()){
            return null;
        }
        String last = nodes.removeLast();
        if("#".equals(last)){
            return null;
        }
        BNode<Integer> root = new BNode<>(Integer.parseInt(last));
        root.right = deserializeByAfter(nodes);
        root.left = deserializeByAfter(nodes);
        return root;
    }

    public static String serializeByAfter(BNode<Integer> root){
        StringBuilder sb = new StringBuilder();
        serializeByAfter(root,sb);
        return sb.toString();
    }

    private static void serializeByAfter(BNode<Integer> root, StringBuilder sb){
        if(root == null){
            sb.append("#").append(",");
            return;
        }

        serializeByAfter(root.left,sb);
        serializeByAfter(root.right,sb);

        sb.append(root.value).append(",");
    }

    public static String serializeByLevel(BNode<Integer> root){
        if(root == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Queue<BNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            BNode<Integer> cur = queue.poll();
            if(cur == null){
                sb.append("#").append(",");
            } else {
                sb.append(cur.value).append(",");
                queue.add(cur.left);
                queue.add(cur.right);
            }
        }

        return sb.toString();
    }

    public static BNode<Integer> deserializeByLevel(String serialize){
        LinkedList<String> inputs = new LinkedList<>();
        for(String s : serialize.split(",")){
            inputs.add(s);
        }
        return deserializeByLevel(inputs);
    }

    private static BNode<Integer> deserializeByLevel(LinkedList<String> inputs){

        if(inputs.isEmpty()){
            return null;
        }

        BNode<Integer> root ;
        LinkedList<BNode<Integer>> nodes = new LinkedList<>();

        String rootValue = inputs.poll();
        if("#".equals(rootValue)) {
            return null;
        } else {
            root = new BNode<>(Integer.parseInt(rootValue));
            nodes.add(root);
        }

        while (!nodes.isEmpty()){
            BNode<Integer> cur = nodes.poll();
            String left = inputs.poll();
            if(!"#".equals(left)){
                cur.left = new BNode<Integer>(Integer.parseInt(left));
                nodes.add(cur.left);
            }
            String right = inputs.poll();
            if(!"#".equals(right)){
                cur.right = new BNode<Integer>(Integer.parseInt(right));
                nodes.add(cur.right);
            }
        }

        return root;
    }

    public static void main(String[] args) {
        middleTraverse(buildTestTree());
        System.out.println(isSameTree(buildTestSameTree1(),buildTestSameTree2()));
        System.out.println(isValidBST(buildTestTree()));
        System.out.println(isIn(buildTestTree(),12));
        System.out.println(isIn(buildTestTree(),13));

        BNode<Integer> tree = insertIntoBST(null,8);
        insertIntoBST(tree,9);
        insertIntoBST(tree,2);
        insertIntoBST(tree,5);
        insertIntoBST(tree,26);
        insertIntoBST(tree,28);
        middleTraverse(tree);

        BNode<Integer> dTree = insertIntoBST(null,10);
        insertIntoBST(dTree,2);
        insertIntoBST(dTree,8);
        insertIntoBST(dTree,18);
        insertIntoBST(dTree,12);
        insertIntoBST(dTree,20);

        dTree = delete(dTree,18);
        System.out.println("After delete 18");
        middleTraverse(dTree);

        dTree = delete(dTree,12);
        System.out.println("After delete 12");
        middleTraverse(dTree);

        dTree = delete(dTree,2);
        System.out.println("After delete 2");
        middleTraverse(dTree);

        System.out.println("deserialize by pre");
        BNode<Integer> dePre = deserializeByPre("1,2,#,4,#,#,3,#,#");
        middleTraverse(dePre);

        System.out.println("serialize by pre :" + serializeByPre(dePre));

        System.out.println("deserialize by after");
        dePre = deserializeByAfter("#,#,#,4,2,#,#,3,1");
        middleTraverse(dePre);

        System.out.println("serialize by after :" + serializeByAfter(dePre));

        System.out.println("serialize by level");
        String result = serializeByLevel(dePre);
        System.out.println("serialize by level = " + result);

        System.out.println("deserialize by level");
        BNode<Integer> level = deserializeByLevel(result);
        middleTraverse(level);

    }
}
