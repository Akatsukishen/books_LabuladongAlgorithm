package com.lss.algorithm.study;

import java.util.Stack;

/**
 * 计算器：
 *  输入合法的计算式,包含加减乘除和括号，求计算结果。
 *  3 * （2 - 6 / (3 - 7))
 *  要求：
 *  1.符合运算规则:括号优先级最高，先乘除后加减。
 *  2. 除法向0取整： 4/3 = 1 -5/2 = -2
 *
 */
public class Calculator {

    public static int calculate(String s){
        return calculate(s,0)[0];
    }

    /**
     *
     * @param s     字符串
     * @param start 开始位置
     * @return      返回遇到第一个')'的计算结果和索引，如果没有遇到')'也返回表达式结果，但是索引返回-1
     */
    private static int[] calculate(String s,int start){

        Stack<Integer> stack = new Stack<>();

        char sign = '+';
        int num = 0;
        char[] chars = s.toCharArray();
        int end = -1;
        for(int i = start ; i < chars.length ; i++ ){
            char c = chars[i];
            if(isDigit(c)){
                num = num * 10 + (c - '0');
            }
            if(c == '('){
                int[] next = calculate(s,i + 1); //看成返回一个数据
                i = next[1];
                num = next[0];
            }
            if((!isDigit(c) && c != ' ') || i == chars.length - 1){
                switch (sign){
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num);

                }
                sign = c;
                num = 0;

                if(c == ')') { //跳出循环
                    end = i;
                    break;
                }

            }
        }

        int res = 0;
        while (!stack.isEmpty()){
            res +=  stack.pop();
        }

        return new int[]{res,end};
    }

    private static boolean isDigit(char c){
        return c - '0' >= 0 && c - '0' <= '9' - '0';
    }

    public static void main(String[] args) {
        System.out.println(isDigit('9'));
        System.out.println(isDigit('a'));
        System.out.println( calculate("4 +  (2 + 3) / 2 - 3 * 2 + 7")) ;// 7
        System.out.println(calculate("(2 + 3) / 2 - 3 * 2 + 7")); // 3
        System.out.println(calculate("4 - 3 * 2 + 7 + +  (2 + 3)")); // 10
    }
}
