package com.lss.algorithm.study;

import java.util.HashMap;
import java.util.Map;

/**
 * 正则匹配
 */
public class PatternMatch {

    /**
     * 正则匹配：
     *  . 代表可以匹配任意字符一次
     *  * 代表可以匹配前面的字符任意次数（包括0）
     *  保证输入的模式串是有效的，即不会出现 *a 这种情况
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch(String s, String p){
        return dp(s,0,p,0,new HashMap<String,Boolean>());
    }

    private static boolean dp(String s, int sStartIndex, String p , int pStartIndex, Map<String,Boolean> memo){

        if(pStartIndex == p.length()){//模式串匹配完成
            return sStartIndex == s.length(); //那么匹配串得匹配完成
        }

        if(sStartIndex == s.length()){
            //模式串还没有匹配完成 但是成对出现，如 a*b*c* ，每个匹配0次，也匹配成功
            if((p.length()  - pStartIndex) % 2 == 1){
                return false;
            }
            for(; pStartIndex + 1 < p.length() ; pStartIndex+=2){
                if(p.charAt(pStartIndex + 1) != '*'){
                    return false;
                }
            }
            return true;
        }

        String key = s.substring(sStartIndex) + "," + p.substring(pStartIndex);
        if(memo.containsKey(key)){
            return memo.get(key);
        }

        char sChar = s.charAt(sStartIndex);
        char pChar = p.charAt(pStartIndex);

        boolean nextIsStar = pStartIndex < p.length() - 1 && p.charAt(pStartIndex + 1) == '*';

        boolean result;
        if(sChar == pChar || pChar == '.'){
            if(nextIsStar){
                //*匹配0次 aa a*aa
                //*匹配多次 aaaa  a*
                result =  dp(s,sStartIndex,p,pStartIndex + 2,memo)   //匹配0个
                         || dp(s,sStartIndex + 1,p,pStartIndex,memo); //匹配多个
            } else {
                result = dp(s,sStartIndex + 1, p ,pStartIndex + 1,memo);
            }

        } else {
            if(nextIsStar){
                //匹配0次 baaa   c*aaa
                result = dp(s,sStartIndex ,p,pStartIndex + 2,memo);
            } else {
                result = false;
            }
        }

        memo.put(key,result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aaaa","a*"));       //true
        System.out.println(isMatch("aa","a*aa"));       // true
        System.out.println(isMatch("abcd","a.caa"));    //false
        System.out.println(isMatch("abcd","a.c*da"));   //false
        System.out.println(isMatch("abcd","a.c*dda"));  //false
        System.out.println(isMatch("abcd","a.c*d*a"));  //false
        System.out.println(isMatch("abcd","a.c*d*a*")); // true
    }
}
