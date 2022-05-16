package com.lss.algorithm.study;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 括号生成
 */
public class ParenthesisGenerate {

    /**
     * 溯源返回
     * @param n
     * @return 返回所有符合条件的n对括号组成
     */
    public static List<String> generate(int n){
        List<String> result = new ArrayList<>();
        List<String> picked = new ArrayList<>();
        generate(n,picked,result,0,0);
        return result;
    }

    /**
     * 每个位置可以有两种选择
     * 但是 ())  对于这种状况应该提前终止
     * @param n
     * @param picked
     * @param result
     */
    private static void generate(int n , List<String> picked, List<String> result){
        if(picked.size() == n * 2){
            if(isValid(picked)){
                StringBuilder sb = new StringBuilder();
                for(String s : picked){
                    sb.append(s);
                }
                result.add(sb.toString());
            }
            return;
        }
        picked.add("(");
        generate(n,picked,result);
        picked.remove(picked.size() -1);

        picked.add(")");
        generate(n,picked,result);
        picked.remove(picked.size() -1);
    }

    private static boolean isValid(List<String> picked){
        Stack<String> stack = new Stack<>();
        if(!picked.isEmpty()){
            stack.push(picked.get(0));
            for(int i = 1 ; i < picked.size() ; i ++){
                if(picked.get(i).equals(")")){
                    if(stack.isEmpty() || !stack.peek().equals("(")){
                        return false;
                    }
                    stack.pop();
                } else {
                    stack.push("(");
                }
            }
        }
        return stack.isEmpty();
    }

    private static void generate(int n , List<String> picked, List<String> result, int left,int right){
        if(right > left || right > n || left > n){ //右括号已经比左括号都，不可能生成
            return;
        }
        if(left == n && right == n){
            if(isValid(picked)){
                StringBuilder sb = new StringBuilder();
                for(String s : picked){
                    sb.append(s);
                }
                result.add(sb.toString());
            }
            return;
        }

        picked.add("(");
        generate(n,picked,result,left + 1, right);
        picked.remove(picked.size() - 1);

        picked.add(")");
        generate(n,picked,result,left,right + 1);
        picked.remove(picked.size() - 1);
    }

    public static void main(String[] args) {
        for(String s : generate(3)){
            System.out.println(s);
        }
    }
}
