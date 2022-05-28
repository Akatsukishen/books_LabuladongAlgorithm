package com.lss.algorithm.study;

public class LongestPalindrome {

    public static int longestPalindrome(String s){
        char[] chars = s.toCharArray();
        int n = chars.length;
        int max = 0 ;

        for(int i = 0 ; i < n ; i ++){

            //偶数情况
            int left = i;
            int right = i + 1 ;
            int len = 0;
            while (left >= 0 && right < n){
                if(chars[left] == chars[right]){
                    len = len + 2;
                    left --;
                    right ++;
                } else {
                    break;
                }
            }

            if(max < len){
                max = len;
            }

            //奇数的情况
            len = 1;
            left = i-1;
            right = i + 1;

            while (left >= 0 && right < n){
                if(chars[left] == chars[right]){
                    len = len + 2;
                    left --;
                    right ++;
                } else {
                    break;
                }
            }
            if(max < len){
                max = len;
            }

        }

        return max;
    }

    public static String longestPalindrome2(String s){

        String res = "";
        for (int i = 0 ; i < s.length() ; i ++){
            //奇数的情况
            String s1 = palindrome(s,i,i);
            //偶数的情况
            String s2 = palindrome(s,i, i + 1);
            String max = s1.length() >= s2.length() ? s1 : s2;
            if(res.length() < max.length()){
                res = max;
            }
        }

        return res;
    }

    private static String palindrome(String s,int left,int right){
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left --;
            right ++;
        }
        return s.substring(left + 1, right);
    }

    public static void main(String[] args) {
        String s = "cbcadac"; //5
        s = "cbcaacd"; // 4
        s = "cbcaefac"; // 3
        System.out.println(longestPalindrome(s));

        System.out.println(longestPalindrome2("cbcaacd"));
        System.out.println(longestPalindrome2("cbcadacd"));
    }
}
