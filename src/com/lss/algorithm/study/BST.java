package com.lss.algorithm.study;

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
    }
}
